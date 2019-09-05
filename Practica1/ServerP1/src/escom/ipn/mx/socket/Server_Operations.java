package escom.ipn.mx.socket;

import java.net.*;
import java.util.List;

import escom.ipn.mx.FyleSystemModel.FileSystemModel;
import escom.ipn.mx.socket.Types.T_File;

import java.io.*;

public class Server_Operations {
	int port;
	ServerSocket s;
	Socket cl;
	private static String rootDir = "/home/angeldlr/ServerFiles";
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
		String file,destinyDir;
		try {
			System.out.println("Aca");
			ObjectInputStream ois = new ObjectInputStream(this.cl.getInputStream());
			T_File filetoDownload = (T_File)ois.readObject();
			System.out.println("Name:"+filetoDownload.getName()+" Dest:"+filetoDownload.getDestinyDirectory()+" Size:"+filetoDownload.getSize());
			//sendFile(file,destinyDir);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void sendFile(String file,String dirDest) {
		File fileToSend = new File(file);
		try {
			DataOutputStream dos = new DataOutputStream(this.cl.getOutputStream());
            DataInputStream dis = new DataInputStream(new FileInputStream(fileToSend.getAbsolutePath()));
            dos.writeUTF(fileToSend.getName());
            dos.flush();
            dos.writeLong(fileToSend.length());
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
                System.out.println("Enviando el "+porcentaje+" %");
            }
            dis.close();
            dos.close();
		} catch (Exception e) {
		}
	}

	public static void main(String[] args) {
		Server_Operations s = new Server_Operations(7000);
		s.initSocket();
	}

}
