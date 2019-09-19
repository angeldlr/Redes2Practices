package socket;

import java.net.*;

import board.Board;

import java.io.*;

public class Server_Operations{
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
				BufferedReader in = new BufferedReader(new InputStreamReader(this.cl.getInputStream()));
				PrintWriter out = new PrintWriter(this.cl.getOutputStream(),true);
				
				String level = in.readLine();
				System.out.println("Nivel seleccinado:"+level);

				Board b = new Board(Integer.parseInt(level));
				System.out.println(b.getJSONBoard());
				out.write(b.getJSONBoard());
				out.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		new Server_Operations(7000).initSocket();
	}
}
