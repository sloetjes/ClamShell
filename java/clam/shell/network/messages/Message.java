package clam.shell.network.messages;

import java.io.Serializable;

public class Message implements Serializable {
	private String serviceName = null;
	
	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public Message (String serviceName) {
		this.serviceName = serviceName;
	}
	
}