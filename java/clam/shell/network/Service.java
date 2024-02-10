package clam.shell.network;

import clam.shell.network.messages.Message;
import clam.shell.network.messages.Request;
import clam.shell.network.messages.Response;

public abstract class Service {
	public Service(String serviceName) {
		setServiceName(serviceName);
	}
	private String serviceName = null;
	
	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public abstract Response process(Request request) throws Exception;

	public abstract void handle(Message message) throws Exception;
}