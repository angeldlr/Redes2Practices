package escom.ipn.mx.main;

import escom.ipn.mx.GUI.GUI_Client;

public class Main {
	public static void main(String[] args) {
		GUI_Client serverClient = new GUI_Client();
		serverClient.setHost("127.0.0.1");
		serverClient.setPort(7000);
		serverClient.setRootDir("/home/angeldlr/ClientFiles");
		serverClient.initcomponents();
	}
}
