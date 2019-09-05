package escom.ipn.mx.socket.Types;

import java.io.File;
import java.io.Serializable;
import java.util.*;

public class T_File implements Serializable{
	
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
				+ destinyDirectory + ", size=" + size + "]";
	}


	public List<String> getListOfFiles(String path)
	{
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		List<String> fileList = new ArrayList<String>();

		for (int i = 0; i < listOfFiles.length; i++) {
		  if (listOfFiles[i].isFile()) {
			fileList.add(listOfFiles[i].getAbsolutePath());
		    //System.out.println("File " + listOfFiles[i].getName()+" path:"+listOfFiles[i].getPath());
		  } else if (listOfFiles[i].isDirectory()) {
			fileList.addAll(getListOfFiles(listOfFiles[i].getAbsolutePath()));
		    //System.out.println("Directory " + listOfFiles[i].getName()+" path:"+listOfFiles[i].getAbsolutePath());
		  }
		}
		return fileList;
	}
}
