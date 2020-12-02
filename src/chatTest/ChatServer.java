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
				System.out.println(TAG + "클라이언트 연결 대기중...");
				Socket socket = serverSocket.accept();
				System.out.println(TAG + "연결 완료");
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
				System.out.println("서버 연결 실패" + e.getMessage());
			}
		}

	}

	public static void main(String[] args) {
		new ChatServer();
	}
}
