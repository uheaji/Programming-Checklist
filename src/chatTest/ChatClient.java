package chatTest;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatClient extends JFrame {

	private ChatClient chatClient = this;
	private final static String TAG = "ChatClient : ";

	private static final int PORT = 10000;

	private JButton btnConnect, btnSend;
	private JTextField tfHost, tfChat;
	private JTextArea taChatList;
	private ScrollPane scrollPane;

	private JPanel topPanel, bottomPanel;

	private Socket socket;
	private BufferedReader reader;
	private PrintWriter writer;

	public ChatClient() {
		init();
		setting();
		batch();
		listener();

		setVisible(true);
	}

	public void init() {
		btnConnect = new JButton("Connect");
		btnSend = new JButton("보내기");
		tfHost = new JTextField("127.0.0.1", 20);
		tfChat = new JTextField(20);
		taChatList = new JTextArea(10, 30);
		scrollPane = new ScrollPane();
		topPanel = new JPanel();
		bottomPanel = new JPanel();
	}

	public void setting() {
		setTitle("채팅 다대다 클라이언트");
		setSize(350, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		taChatList.setBackground(Color.orange);
		taChatList.setForeground(Color.blue);

	}

	public void batch() {
		topPanel.add(tfHost);
		topPanel.add(btnConnect);
		bottomPanel.add(tfChat);
		bottomPanel.add(btnSend);
		scrollPane.add(taChatList);

		add(topPanel, BorderLayout.NORTH);
		add(scrollPane, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);
	}

	public void listener() {
		btnConnect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				connect();

			}
		});

		btnSend.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				send();
			}
		});
	}

	class ReaderThread extends Thread {
		// while을 돌면서 서버로부터 메시지를 받아서 taChatList에 뿌리기
		@Override
		public void run() {
			try {
				String line = null;
				while ((line = reader.readLine()) != null) {
					System.out.println("from server : " + line);
					taChatList.append("[상대방]" + line + "\n");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void connect() {
		String host = tfHost.getText();
		try {
			socket = new Socket(host, PORT);
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new PrintWriter(socket.getOutputStream(), true);
			ReaderThread rt = new ReaderThread();
			rt.start();

		} catch (Exception e) {
			System.out.println(TAG + "서버 연결 에러" + e.getMessage());
		}
	}

	public void send() {
		try {
			String chat = tfChat.getText();
			// 1번 taChatList 뿌리기
			taChatList.append("[내 메시지] " + chat + "\n");
			// 2번 서버로 전송
			writer.println(chat);
			// 3번 tfChat 비우기
			tfChat.setText(" ");
			
			FileWriter fw = new FileWriter("D:\\chatTest.text");
			fw.write(taChatList.getText());
			fw.close();
			
		} catch (Exception e) {

		}

	}

	public static void main(String[] args) {
		new ChatClient();

	}

}
