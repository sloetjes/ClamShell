package clam.shell.network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;

import clam.shell.network.messages.AttachRequest;
import clam.shell.network.messages.CloseRequest;
import clam.shell.network.messages.ErrorResponse;
import clam.shell.network.messages.Message;
import clam.shell.network.messages.Request;
import clam.shell.network.messages.Response;

public class Nexus {
	private int port = 3125;
	/*
	 * Client
	 * 
	 */
	private String host = null;
	
	public Nexus(InputStream input, OutputStream output) throws Exception {		
		input = socket.getInputStream();
		output = socket.getOutputStream();
	}	
	
	public Nexus(Socket socket) throws Exception {
		this (socket.getInputStream(),socket.getOutputStream());
		this.socket = socket;		
	}

	public Nexus(String host, int port, boolean autoconnect) throws Exception {
		this.host = host;
		this.port = port;
		if (autoconnect) {
			this.connect();	
		}		
	}

	public synchronized void disconnect(boolean graceful) throws Exception {
		if (graceful) {
			process(new CloseRequest());
		}
		close();
		if (socket!=null && socket.isConnected()) {
			socket.close();
		}
	}
	private Socket socket = null;
	public  synchronized void connect() throws Exception {
		socket = buildSocket (host, port);
		input = socket.getInputStream();
		output = socket.getOutputStream();
	}
	
	public  synchronized void attach() throws Exception {
		Response response = process(new AttachRequest());
		if (response.b == true) {
			stopped = true;
			boolean stopped = false;
			try {
				while (!stopped) {
					try {
						Message message = read();
						Service service = getService(message.getServiceName());
						if (message instanceof CloseRequest) {
							stopped = true;
						} else if (message instanceof Request) {
							write(service.process((Request) message));					
						} else {
							service.handle(message);
						}
					} catch (Exception e) {
						e.printStackTrace();
						stopped = true;
					}
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} else {
			throw new Exception ("attach() denied");
		}
	}

	/*
	 * Server
	 * 
	 */
	private Vector<Service> services = new Vector<Service> ();
	public Nexus(int port) {
		this (port,false);
	}
	public Nexus(int port, boolean autoConnect) {
		this.port = port;
		if (autoConnect) {
			this.open();
		}
	}

	public  Service getService(String serviceName) throws Exception {
		Object[] objects = services.toArray();
		for (Object o : objects) {
			Service s = (Service) o;
			if (s.getServiceName().equals(serviceName)) {
				return s;
			}
		}
		throw new Exception("Service not found: " + serviceName);
	}

	public  boolean add(Service service) {
		Object[] objects = services.toArray();
		for (Object o : objects) {
			Service s = (Service) o;
			if (s.getServiceName().equals(service.getServiceName())) {
				return false;
			}
		}
		return services.add(service);
	}

	private boolean stopped = false;
	public ServerSocket buildServerSocket (int port) throws Exception {
		return new ServerSocket(port);
	}
	public Socket buildSocket (String host, int port) throws Exception {
		return new Socket (host,port);
	}
	public  void open() {
		new Thread() {
			public void run() {
				try {
					ServerSocket ss = buildServerSocket (port);
					while (!stopped) {
						final Socket socket = ss.accept();			
						Thread thread = new Thread() {
							public void run() {
								boolean stopped = false;
								try {
									final Nexus nexus = new Nexus(socket);
									while (!stopped) {
										try {
											Message message = nexus.read();
											Service service = getService(message.getServiceName());
											if (message instanceof CloseRequest) {
												stopped = true;
												nexus.close();
											} else if (message instanceof AttachRequest) {
												nexus.write(new Response(true));
												add (new Service ("attached.service") {
													public synchronized Response process(Request request) throws Exception {
														nexus.write(request);
														return (Response) nexus.read();
													}
													public synchronized void handle(Message message) throws Exception {			
														nexus.write(message);
													}													
												});
											} else if (message instanceof Request) {
												nexus.write(service.process((Request) message));
											} else if (message instanceof Message) {
												service.handle(message);
											} else if (message instanceof Response) {
												nexus.write(new ErrorResponse("Heard a Response."));
												nexus.close();
											}
										} catch (Exception e) {
											e.printStackTrace();
											stopped = true;
										}
									}
								} catch (Exception e1) {
									e1.printStackTrace();
								}
							}
						};
						thread.start();
					}
					ss.close();
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("Exception occurred, Server terminating");
					System.exit(0);
				}
			}
		}.start();
	}

	/*
	 * common I/O stuff
	 */
	private InputStream input = null;
	private OutputStream output = null;
	private byte[] buf = new byte[8];
	private String serviceName = "default.service";

	public synchronized  void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public synchronized  String getServiceName() {
		return serviceName;
	}

	protected synchronized  void write(Message message) throws Exception {
		message.setServiceName(getServiceName());
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		new ObjectOutputStream(baos).writeObject(message);
		byte[] data = baos.toByteArray();		
		output.write((byte) (0xff & (data.length >> 24)));
		output.write((byte) (0xff & (data.length >> 16)));
		output.write((byte) (0xff & (data.length >> 8)));
		output.write((byte) (0xff & data.length));
		output.write(data);
	}

	private synchronized  Message read() throws Exception {
		int len = 4;
		int offset = 0;
		while (len > 0) {
			int numread = input.read(buf, offset, len);
			if (numread < 0)
				throw new EOFException();
			len -= numread;
			offset += numread;
		}
		byte[] data = new byte[(((buf[0] & 0xff) << 24) | ((buf[1] & 0xff) << 16) | ((buf[2] & 0xff) << 8) | (buf[3] & 0xff))];
		input.read(data);		
		return (Message) new ObjectInputStream(new ByteArrayInputStream(data)).readObject();
	}

	public synchronized void close() throws Exception {
		stopped = true;
		input.close();
		output.close();
	}

	public synchronized Response process(Request request) throws Exception {
		write(request);
		return (Response) read();
	}

	public synchronized void handle(Message message) throws Exception {
		write(message);
	}	

	

	
}
