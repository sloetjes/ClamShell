package clam.shell;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;


public class Database extends Thread implements Serializable {
	private static final long serialVersionUID = 1L;
	private String filename = null;
	private HashMap<Object, Object> map = new HashMap<Object, Object>();
	public boolean autosave = true;
	
	private Database(String filename) {
		this.filename = filename;
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				save();
			}
		});
	}
	
	public static synchronized Database load(final String filename) {
		try {
			FileInputStream fis = new FileInputStream(filename);
			ObjectInputStream ois = new ObjectInputStream(fis);
			Database db = (Database) ois.readUnshared();
			ois.close();
			db.filename = filename;
			final Database result = db;
			Runtime.getRuntime().addShutdownHook(new Thread() {
				public void run() {
					if (db.autosave) {
						result.save();	
					}
					
				}
			});
			return result;
		} catch (Exception e) {
			return new Database(filename);
		}
	}

	public synchronized void save() {
		synchronized (this) {
			if (runnables == null) {
				runnables = new ArrayList<Runnable>();
			}
			try {
				for (Runnable runnable : runnables) {
					runnable.run();
				}
				File file = new File(filename);
				FileOutputStream fos = new FileOutputStream(file);
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(this);
				fos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

	public Object get(String key) {
		return map.get(key);
	}

	public void set(Object key, Object value) {
		map.put(key, value);
	}

	public void remove(Object key) {
		map.remove(key);
	}

	public boolean containsKey(String key) {
		return map.containsKey(key);
	}

	/* CONVENIENCE METHODS */
	public long getLong(String key) {
		return ((Long) get(key)).longValue();
	}

	public int getInt(String key) {
		return getInt(key, 0);
	}

	public int getInt(String key, int defaultValue) {
		if (get(key) == null) {
			set(key, defaultValue);
		}
		return ((Integer) get(key)).intValue();
	}

	public String getString(String key) {
		return (String) get(key);
	}

	public void setLong(String key, long defaultValue) {
		set(key, defaultValue);
	}

	public void setInt(String key, int defaultValue) {
		set(key, defaultValue);
	}

	public void setString(String key, String defaultValue) {
		set(key, defaultValue);
	}

	public String getString(String key, String defaultValue) {
		if (getString(key) == null)
			return defaultValue;
		return getString(key);
	}

	public void setBoolean(String key, boolean selected) {
		setString(key, "" + selected);
	}

	public boolean getBoolean(String key, boolean defaultValue) {
		if (getString(key) == null)
			return defaultValue;
		return Boolean.parseBoolean("" + getString(key));
	}

	public boolean getBoolean(String key) {
		return getBoolean(key, false);
	}
	
	public void put(String key, Object defaultValue) {
		set(key, defaultValue);
	}

	public int get(String string, int defaultValue) {
		if (getString(string) == null)
			return defaultValue;
		return Integer.parseInt(getString(string));
	}

	public transient ArrayList<Runnable> runnables = new ArrayList<Runnable>();
	public void addShutdownHook(Runnable runnable) {
		if (runnables == null) {
			runnables = new ArrayList<Runnable>();
		}
		runnables.add(runnable);
	}

	public void removeShutdownHook(Runnable runnable) {
		runnables.remove(runnable);
	}

}