package escom.ipn.mx.main;

import escom.ipn.mx.socket.Server_Operations;

public class Main {
	public static void main(String[] args) {
		Server_Operations s = new Server_Operations(7000);
		s.setRootDir("/home/angeldlr/ServerFiles");
		s.initSocket();
	}
}
