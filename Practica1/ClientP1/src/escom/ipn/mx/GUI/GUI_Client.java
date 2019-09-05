package escom.ipn.mx.GUI;

import escom.ipn.mx.FyleSystemModel.*;
import escom.ipn.mx.socket.Client_Operations;
import escom.ipn.mx.socket.Types.T_File;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

public class GUI_Client extends JFrame {

	private static final long serialVersionUID = 1L;

	/* GUI atributes */
	private String rootDir = "/home/angeldlr/ClientFiles";
	private String rootDirServer;
	private Client_Operations co;
	private JTree fileTreeClient;
	private JTree fileTreeServer;
	private JScrollPane leftPane;
	private JScrollPane rightPane;
	private JLabel lblClientFiles;
	private JLabel lblSeverFiles;
	private JPopupMenu popUpClient;
	private JPopupMenu popUpServer;
	private JMenuItem menuItem;
	private FileSystemModel fileSystemModelClient;
	private FileSystemModel fileSystemModelServer;
	private File selectedServerDirectory = null;
	private File selectedClientDirectory = null;
	private File selectedServerFile = null;
	private File selectedClientFile = null;

	/* Getters and Setters */

	public FileSystemModel getFileSystemModelClient() {
		return fileSystemModelClient;
	}

	public void setFileSystemModelClient(String path) {
		this.fileSystemModelClient = new FileSystemModel(new File(path));
	}

	public FileSystemModel getFileSystemModelServer() {
		return fileSystemModelServer;
	}

	public void setFileSystemModelServer(FileSystemModel fileSystemModelServer) {
		this.fileSystemModelServer = fileSystemModelServer;
	}

	public File getSelectedClientDirectory() {
		return selectedClientDirectory;
	}

	public void setSelectedClientDirectory(File selectedClientDirectory) {
		this.selectedClientDirectory = selectedClientDirectory;
	}
	public String getRootDirServer() {
		return rootDirServer;
	}

	public void setRootDirServer(String rootDirServer) {
		this.rootDirServer = rootDirServer;
	}
	
	public void initClientOp() {
		co = new Client_Operations("127.0.0.1",7000);
	}
	/* Methods of GUI_Client class */

	public void initcomponents() {
		this.setTitle("APP NETWORK COMUNICATIONS P1");
		this.setSize(600, 530);
		this.setLayout(null);

		this.lblClientFiles = new JLabel("CLIENT FILES");
		this.lblClientFiles.setFont(new Font("Arial", Font.BOLD, 24));
		this.lblClientFiles.setOpaque(true);
		this.add(this.lblClientFiles);
		this.lblClientFiles.setBounds(60, 30, 200, 50);

		this.lblSeverFiles = new JLabel("SERVER FILES");
		this.lblSeverFiles.setFont(new Font("Arial", Font.BOLD, 24));
		this.lblSeverFiles.setOpaque(true);
		this.add(this.lblSeverFiles);
		this.lblSeverFiles.setBounds(360, 30, 200, 50);

		initClientOp();
		getFileList();
		this.fileTreeClient = new JTree(getFileSystemModelClient());
		this.fileTreeServer = new JTree(getFileSystemModelServer());
			
		this.leftPane = new JScrollPane(this.fileTreeClient);
		this.leftPane.setMaximumSize(new Dimension(290, 360));
		this.rightPane = new JScrollPane(this.fileTreeServer);
		this.rightPane.setMaximumSize(new Dimension(290, 360));

		/* rigth click menu items client */
		this.popUpClient = new JPopupMenu();
		this.popUpClient.add(this.menuItem = new JMenuItem("Upload to root"));
		this.menuItem.addActionListener(new MenuListener());
		this.popUpClient.add(this.menuItem = new JMenuItem("Upload to selected directory"));
		this.menuItem.addActionListener(new MenuListener());
		/* rigth click menu items server */
		this.popUpServer = new JPopupMenu();
		this.popUpServer.add(this.menuItem = new JMenuItem("Download to root"));
		this.menuItem.addActionListener(new MenuListener());
		this.popUpServer.add(this.menuItem = new JMenuItem("Download to selected directory"));
		this.menuItem.addActionListener(new MenuListener());

		/* Adding listeners */
		this.fileTreeClient.addTreeSelectionListener(new ListenerFileSelectorClient());
		this.fileTreeServer.addTreeSelectionListener(new ListenerFileSelectorServer());
		this.fileTreeClient.addMouseListener(new RightClickSelectionNode(this.fileTreeClient, this.popUpClient));
		this.fileTreeServer.addMouseListener(new RightClickSelectionNode(this.fileTreeServer, this.popUpServer));

		Box box = Box.createHorizontalBox();
		box.add(this.leftPane, BorderLayout.WEST);
		box.add(this.rightPane, BorderLayout.EAST);
		this.getContentPane().add(box, BorderLayout.CENTER);
		box.setBounds(10, 80, 580, 360);

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	private void getFileList() {
		
		setFileSystemModelClient(rootDir);
		setFileSystemModelServer(co.receiveServerFileModel());
	}
	private void updateDirs() {
		this.fileTreeServer.repaint();
		this.fileTreeClient.repaint();
	}
	private String getFileDetails(File file) {
		if (file == null)
			return "";
		StringBuffer buffer = new StringBuffer();
		buffer.append("Name: " + file.getName() + "\n");
		buffer.append("Path: " + file.getPath() + "\n");
		buffer.append("Size: " + file.length() + "\n");
		return buffer.toString();
	}

	/* Listeners implementation */
	private class ListenerFileSelectorClient implements TreeSelectionListener {

		@Override
		public void valueChanged(TreeSelectionEvent e) {
			JTree fileTree = (JTree) e.getSource();
			File file = (File) fileTree.getLastSelectedPathComponent();
			if (file.isDirectory()) {
				GUI_Client.this.selectedClientFile = file;
				GUI_Client.this.selectedClientDirectory = file;
			} else {
				GUI_Client.this.selectedClientFile = file;
			}
		}

	}

	private class ListenerFileSelectorServer implements TreeSelectionListener {

		@Override
		public void valueChanged(TreeSelectionEvent e) {
			JTree fileTree = (JTree) e.getSource();
			File file = (File) fileTree.getLastSelectedPathComponent();
			if (file.isDirectory()) {
				GUI_Client.this.selectedServerFile = file;
				GUI_Client.this.selectedServerDirectory = file;
			} else {
				GUI_Client.this.selectedServerFile = file;
			}
		}

	}

	private class RightClickSelectionNode implements MouseListener {

		private JTree fileTree;
		private JPopupMenu popMenu;

		public RightClickSelectionNode(JTree t, JPopupMenu pm) {
			this.fileTree = t;
			this.popMenu = pm;
		}

		@Override
		public void mousePressed(MouseEvent e) {
			if (SwingUtilities.isRightMouseButton(e)) {
				/*
				 * If there are not selected files then we need to choose the selected file when
				 * right click is pressed
				 */
				int selRow = this.fileTree.getRowForLocation(e.getX(), e.getY());
				TreePath selPath = this.fileTree.getPathForLocation(e.getX(), e.getY());
				this.fileTree.setSelectionPath(selPath);
				if (selRow > -1) {
					this.fileTree.setSelectionRow(selRow);
					this.popMenu.show(this.fileTree, e.getX(), e.getY());
				}
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

	}

	private class MenuListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			File selectFile = null;
			File selectDirectory = null;
			
			switch (e.getActionCommand()) {
			case "Upload to root":
				selectFile =  GUI_Client.this.selectedClientFile;
				selectDirectory = GUI_Client.this.selectedServerDirectory;
				System.out.println("File:\n"+GUI_Client.this.getFileDetails(selectFile));
				System.out.println("Src:\n"+GUI_Client.this.getFileDetails(selectDirectory));
				break;
			case "Upload to selected directory":
				// method to upload selected file in client to selected directory in server
				
				break;
			case "Download to root":
				// method to download selected file in server to root directory in client
				selectFile =  GUI_Client.this.selectedServerFile;
				GUI_Client.this.co.downloadFile(selectFile, GUI_Client.this.rootDir);
				break;
			case "Download to selected directory":
				// method to download selected file in client to selected directory in server
				selectFile =  GUI_Client.this.selectedServerFile;
				selectDirectory = GUI_Client.this.selectedClientDirectory;
				
				if(selectDirectory != null && selectFile != null && selectDirectory.isDirectory()) {
					System.out.println("File:\n"+GUI_Client.this.getFileDetails(selectFile));
					System.out.println("Dest:\n"+GUI_Client.this.getFileDetails(selectDirectory));
				}else {
					System.out.println("bad choise");
					JOptionPane.showMessageDialog(null,"Please choose a valid directory in client files!!","Directory error",JOptionPane.WARNING_MESSAGE);
				}
				break;
			default:
				break;
			}
		}
	}

	public static void main(String[] args) {
		GUI_Client serverClient = new GUI_Client();
		serverClient.initcomponents();
	}

}
