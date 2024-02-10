package clam.shell;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;

public class Cache extends HashMap<String, byte[]> implements Serializable {	
	int size = 0;
	public InputStream open(String filename) {
		try {
			byte[] contents = this.get(filename);
			if (contents == null) {				
				contents = new byte[(int) new File(filename).length()];
				FileInputStream fis = new FileInputStream(filename);
				fis.read(contents);
				fis.close();
				this.put(filename, contents);
				//System.out.println("non-cached "+(size+=contents.length)+" "+filename);
			} else {
				//System.out.println("cached "+(size)+" "+filename);
			}
			return new ByteArrayInputStream (contents);
		} catch (IOException e) {
			e.printStackTrace();			
		}
		System.exit(0);
		return null;
	}
	
}
