package clam.shell.network.messages;

public class Request extends Message {
	public Request (String serviceName) {
		super(serviceName);
	}
	public Request () {
		this ("default.service");
	}
}
