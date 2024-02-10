package clam.shell.synchro;

import clam.shell.network.Nexus;
import clam.shell.network.Service;
import clam.shell.network.messages.Message;
import clam.shell.network.messages.Request;
import clam.shell.network.messages.Response;

public class SynchroServer extends Nexus {
	String serverFolder = null;
	public String getServerFolder() {
		return serverFolder;
	}
	public void setServerFolder(String serverFolder) {
		this.serverFolder = serverFolder;
	}

	public SynchroServer(int port, String serverFolder) {
		super(3125);
		this.setServerFolder(serverFolder);
		this.add(new Service ("clam.synchro") {
			public Response process(Request request) throws Exception {
				return null;
			}
			public void handle(Message message) throws Exception {
			}
		});
	}

	public static void main(String[] args) {
		try {
			if (args.length == 0) {
				args = new String[] { "3125", "e:/synchro"};
			}

			int port = Integer.parseInt(args[0]);
			new SynchroServer(port, args[1]);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
