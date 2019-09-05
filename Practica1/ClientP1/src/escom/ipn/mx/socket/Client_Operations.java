package escom.ipn.mx.socket;

import java.net.*;
import java.util.List;

import escom.ipn.mx.FyleSystemModel.FileSystemModel;
import escom.ipn.mx.socket.Types.T_File;

import java.io.*;

public class Client_Operations {
	
	int port;
	String host;
	Socket cl;
	/*Constructor*/
	public Client_Operations(String h,int p) {
		this.port = p;
		this.host = h;
	}
	public FileSystemModel receiveServerFileModel() {
		FileSystemModel fileModelServer = null;
		try {
			this.cl = new Socket(host, port);
			System.out.println("Conexion establecida, preparada para intercambiar objetos");
			DataOutputStream dos = new DataOutputStream(this.cl.getOutputStream());
			dos.writeInt(0);
			ObjectInputStream ois = new ObjectInputStream(this.cl.getInputStream());
			fileModelServer = (FileSystemModel) ois.readObject();
			ois.close();
			dos.close();
			this.cl.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return fileModelServer;
	}
	public void downloadFile(File file, String destiny) {
		try {
			this.cl = new Socket(host, port);
			System.out.println("Conexion establecida, preparada para intercambiar objetos");
			DataOutputStream dos = new DataOutputStream(this.cl.getOutputStream());
			dos.writeInt(1);
			T_File fileToSend = new T_File(file.getName(),file.getAbsolutePath(),destiny,file.length());
			ObjectOutputStream oos = new ObjectOutputStream(this.cl.getOutputStream());
			oos.writeObject(fileToSend);
			oos.close();
			dos.close();
			this.cl.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void recFile() {
		
	}
}
