package escom.ipn.mx.socket;

import java.net.*;
import java.util.ArrayList;
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
			//System.out.println("Conexion establecida, preparada para intercambiar objetos");
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
			DataOutputStream dos = new DataOutputStream(this.cl.getOutputStream());
			dos.writeInt(1);
			ObjectOutputStream oos = new ObjectOutputStream(this.cl.getOutputStream());
			T_File fileToGet = new T_File(file.getName(),file.getAbsolutePath(),destiny,file.length());
			oos.writeObject(fileToGet);
			recFile();
			oos.close();
			dos.close();
			this.cl.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void uploadFile(File file, String destiny) {
		try {
			this.cl = new Socket(host, port);
			DataOutputStream dos = new DataOutputStream(this.cl.getOutputStream());
			dos.writeInt(2);
			ObjectOutputStream oos = new ObjectOutputStream(this.cl.getOutputStream());
			T_File fileToLoad = new T_File(file.getName(),file.getAbsolutePath(),destiny,file.length());
			oos.writeObject(fileToLoad);
			oos.close();
			dos.close();
			this.cl.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void recFile() {
		try {
			DataInputStream dis = new DataInputStream(this.cl.getInputStream());
            String nombre = dis.readUTF();
            String destinyDir = dis.readUTF();
            long tam = dis.readLong();
            System.out.println("Preparado para recibir el archivo: "+ nombre+" a guardar en:"+destinyDir+" Tam:"+tam);
            DataOutputStream dos = new DataOutputStream(new FileOutputStream(destinyDir+"/"+nombre));
            long r = 0;
            int n = 0, porcentaje = 0;
            while (r<tam) {
                byte[] b = new byte[65536];
                n = dis.read(b);
                r = r+n;
                dos.write(b, 0, n);
                dos.flush();
                porcentaje = (int)((r*100)/tam);
                System.out.println("Recibiste el "+porcentaje+"%");
            }
            dis.close();
            dos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
