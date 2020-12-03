package chatTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import protocol.Chat;

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

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	class ClientInfo extends Thread {
		Socket socket;
		BufferedReader reader;
		PrintWriter writer;
		String id;

		public ClientInfo(Socket socket) {
			this.socket = socket;
			try {
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				writer = new PrintWriter(socket.getOutputStream(), true);
			} catch (Exception e) {
				e.printStackTrace();

			}

		}

		public void run() {
			try {

				writer.println("����Ͻ� ���̵� �Է��ϼ���.");
				id = reader.readLine();

				for (int i = 0; i < vc.size(); i++) {
					ClientInfo clientInfo = vc.get(i);
					clientInfo.writer.println("[" + id + "]" + "�Բ��� ������ �ϼ̽��ϴ�");
				}

				String input = null;
				while ((input = reader.readLine()) != null) {
					for (ClientInfo clientInfo : vc) {
						if (clientInfo != this) {
							clientInfo.writer.println("[" + id + "]" + input);
						}
					}
				}
			} catch (Exception e) {
				System.out.println("���� ���� ���� " + e.getMessage());
			}
		}

		public void router(String input) {
			String[] gubun = input.split(":"); // ALL:�ȳ� , MSG:white:�ȳ�
			String protocol = gubun[0];
			if (protocol.equals(Chat.ALL)) {
				String msg = gubun[1];
				allChat(msg);
			} else if (protocol.equals(Chat.ALL)) {
				writer.println("����(ALL�Է�)");
			}
		}

		public void allChat(String msg) {
			for (ClientInfo clientInfo : vc) {
				if (clientInfo != this) {
					clientInfo.writer.println("[" + id + " ]" + msg);
				}
			}
		}

	}

	public static void main(String[] args) {
		new ChatServer();
	}
}