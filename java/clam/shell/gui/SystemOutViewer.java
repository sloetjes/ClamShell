package clam.shell.gui;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;


public class SystemOutViewer extends Screen {
	public static SystemOutViewer instance = new SystemOutViewer();
	boolean hasMouse = false;

	public JTextArea out = new JTextArea ();
	public JTextArea err = new JTextArea ();
	PrintStream printOut = System.out;
	public SystemOutViewer() {
		super ("Console & Error Streams");
		System.setOut(new PrintStream(new OutputStream() {
			public void write(int x) throws IOException {
				printOut.write(x);
				out.append(String.valueOf((char) x));
				out.setCaretPosition(out.getDocument().getLength());
				validate();
			}
		}));
		out.setFont(new Font("monospaced", Font.BOLD, 12));
		out.setBackground(new Color (64,0,128));
		out.setForeground(Color.WHITE);
		out.setEditable(false);
		//setLayout(new BorderLayout());
		JTabbedPane tabs = new JTabbedPane ();
		
		DefaultCaret caret = (DefaultCaret)out.getCaret();
		caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
		
		caret = (DefaultCaret)err.getCaret();
		caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
		PrintStream printErr = System.err;
		System.setErr(new PrintStream(new OutputStream() {
			public void write(int x) throws IOException {	
				printErr.write(x);
				err.append(String.valueOf((char) x));
				err.setCaretPosition(err.getDocument().getLength());
				validate();
			}
		}));
		err.setFont(new Font("monospaced", Font.BOLD, 12));
		err.setBackground(new Color (64,0,128));
		err.setForeground(Color.WHITE);
		err.setEditable(false);
		
		//JScrollPane s1 = new JScrollPane (out);
		
		tabs.addTab("Output", new JScrollPane(out));
		tabs.addTab("Error", new JScrollPane(err));
		
		//setLayout(new BorderLayout());
		add(tabs);
		setPreferredSize(new Dimension(640, 680));
		setMinimumSize(new Dimension(256, 256));
	//	this.addTab("System.out",new JScrollPane(out));
		
	}
	public SystemOutViewer(File file) throws Exception {
		this();	
		FileOutputStream fos = new FileOutputStream (file);
		
		PrintStream old = System.out;
		PrintStream ps = new PrintStream (fos) {
			public void println (String c) {
				old.println("["+new Date()+"] "+c);
				try {
					fos.write(("["+new Date()+"] "+c+"\r\n").getBytes());
				} catch (IOException e) {
					e.printStackTrace();
				}
				//flush();
			}
			public void println () {
				println ("");
			}
		};
		System.setOut(ps);
		//System.out.println("logging beginning...");
		Runtime.getRuntime().addShutdownHook (new Thread () {
			public void run () {
				
				
				try {
					Thread.sleep (1000);					
					fos.flush();
					fos.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
	}
	public boolean usesScrollPanel () {
		return true;
	}
}
