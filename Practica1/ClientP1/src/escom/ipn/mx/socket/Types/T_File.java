package escom.ipn.mx.socket.Types;

import java.io.Serializable;

public class T_File implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String name;
	String sourceDirectory;
	String destinyDirectory;
	long size;
	
	
	public T_File(String name, String sourceDirectory, String destinyDirectory, long size) {
		super();
		this.name = name;
		this.sourceDirectory = sourceDirectory;
		this.destinyDirectory = destinyDirectory;
		this.size = size;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getPath() {
		return sourceDirectory;
	}


	public void setPath(String path) {
		this.sourceDirectory = path;
	}


	public long getSize() {
		return size;
	}


	public void setSize(int size) {
		this.size = size;
	}

	
	public String getSourceDirectory() {
		return sourceDirectory;
	}


	public void setSourceDirectory(String sourceDirectory) {
		this.sourceDirectory = sourceDirectory;
	}


	public String getDestinyDirectory() {
		return destinyDirectory;
	}


	public void setDestinyDirectory(String destinyDirectory) {
		this.destinyDirectory = destinyDirectory;
	}

	

	@Override
	public String toString() {
		return "T_File [name=" + name + ", sourceDirectory=" + sourceDirectory + ", destinyDirectory="
				+ destinyDirectory + "]";
	}

}
