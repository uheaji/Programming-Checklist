package chatTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class ChatServer {

	private static final String TAG = "ChatServer : ";
	private ServerSocket serverSocket;
	private Vector<ClientInfo> vc;

	public ChatServer() {
		try {
			serverSocket = new ServerSocket(10000);
			vc = new Vector<>();

			while (true) {
				System.out.println(TAG + "Ŭ���̾�Ʈ ���� �����...");
				Socket socket = serverSocket.accept();
				System.out.println(TAG + "���� �Ϸ�");
				ClientInfo clientInfo = new ClientInfo(socket);
				clientInfo.start();
				vc.add(clientInfo);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	class ClientInfo extends Thread {
		Socket socket;
		BufferedReader reader;
		PrintWriter writer;

		public ClientInfo(Socket socket) {
			this.socket = socket;
		}

		public void run() {
			try {
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				writer = new PrintWriter(socket.getOutputStream(), true);

				String input = null;

				while ((input = reader.readLine()) != null) {
					for (ClientInfo clientInfo : vc) {
						clientInfo.writer.println(input);
					}

				}
			} catch (Exception e) {
				System.out.println("���� ���� ����" + e.getMessage());
			}
		}

	}

	public static void main(String[] args) {
		new ChatServer();
	}
}
