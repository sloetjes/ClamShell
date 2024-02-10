package clam.shell.network.messages;

public class Response extends Message {
	public int i = 0;
	public boolean b = false;
	public String s = "";
	public Response() {
		super("default.service");
	}
	public Response(int i) {
		super("default.service");
		this.i = i;
	}

	public Response(boolean b) {
		super("default.service");
		this.b = b;
	}

	public Response(String s) {
		super("default.service");
		this.s = s;
	}
}
