package clam.shell.synchro;

import clam.shell.network.Nexus;
import clam.shell.synchro.messages.ConnectMessage;

public class SynchroClient extends Nexus {
	
	String clientFolder = null;
	public String getClientFolder() {
		return clientFolder;
	}
	public void setClientFolder(String clientFolder) {
		this.clientFolder = clientFolder;
	}

	String serverFolder = null;
	public String getServerFolder() {
		return serverFolder;
	}
	public void setServerFolder(String serverFolder) {
		this.serverFolder = serverFolder;
	}

	public SynchroClient(String host, int port, String clientFolder, String serverFolder) throws Exception {
		super(host, port, false);
		setClientFolder(clientFolder);
		setServerFolder(serverFolder);	
		this.connect();
		this.write(new ConnectMessage());
	}

	public static void main(String[] args) {
		try {
			if (args.length == 0) {
				args = new String[] { "localhost", "3125", "e:/synchro", "c:/synchro" };
			}
			String host = args[0];
			int port = Integer.parseInt(args[1]);
			new SynchroClient(host, port, args[2], args[3]);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
