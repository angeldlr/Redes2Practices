package escom.ipn.mx.socket;

import java.net.*;
import java.util.ArrayList;
import java.util.List;

import escom.ipn.mx.FyleSystemModel.FileSystemModel;
import escom.ipn.mx.socket.Types.T_File;

import java.io.*;

public class Server_Operations {
	int port;
	ServerSocket s;
	Socket cl;
	private String rootDir ;
	public Server_Operations(int port) {
		super();
		this.port = port;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public ServerSocket getS() {
		return s;
	}

	public void setS(ServerSocket s) {
		this.s = s;
	}
	
	
	public String getRootDir() {
		return rootDir;
	}

	public void setRootDir(String rootDir) {
		this.rootDir = rootDir;
	}

	public void initSocket() {
		try {
			this.s = new ServerSocket(getPort());
			System.out.println("Esperando conexión de cliente");
			for (;;) {
				this.cl = s.accept();
				System.out.println("Cliente conectado desde:" + this.cl.getInetAddress() + ":" + this.cl.getPort());
				
				DataInputStream di = new DataInputStream(this.cl.getInputStream());
				int request = di.readInt();
				
				switch (request) {
				/* if the received request is check file list(0) */
				case 0:
					sendFileList();
					break;
				/* if the received request is to download files(1) */
				case 1:
					checkDownloadReq();
					break;
				/* if the received request is to upload files(2) */
				case 2:
					checkUploadReq();
					break;
				default:
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void sendFileList() {
		FileSystemModel fileModelServer = new FileSystemModel(new File(rootDir));
		try {
			ObjectOutputStream oos = new ObjectOutputStream(this.cl.getOutputStream());
			oos.writeObject(fileModelServer);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void checkDownloadReq() {
		try {
			ObjectInputStream ois = new ObjectInputStream(this.cl.getInputStream());
			T_File filetoDownload = (T_File)ois.readObject();
			sendFile(filetoDownload);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void checkUploadReq() {
		try {
			ObjectInputStream ois = new ObjectInputStream(this.cl.getInputStream());
			T_File filetoUpload = (T_File)ois.readObject();
			System.out.println(filetoUpload);
			//sendFile(filetoDownload);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void sendFile(T_File fileInfo) {
		File fileToSend = new File(fileInfo.getSourceDirectory());
		try {
			DataOutputStream dos = new DataOutputStream(this.cl.getOutputStream());
            DataInputStream dis = new DataInputStream(new FileInputStream(fileToSend));
            dos.writeUTF(fileInfo.getName());
            dos.flush();
            dos.writeUTF(fileInfo.getDestinyDirectory());
            dos.flush();
            dos.writeLong(fileInfo.getSize());
            dos.flush();
            long e = 0;
            int n=0,porcentaje=0;
            while (e < fileToSend.length()) {
                byte[] b= new byte[65536];
                n= dis.read(b);
                e=e+n;
                dos.write(b,0,n);
                dos.flush();
                porcentaje = (int)((e*100)/fileToSend.length());
                System.out.println("Enviando el "+porcentaje+" %\n");
            }
            dis.close();
            dos.close();
		} catch (Exception e) {
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
