package test01;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.json.simple.JSONObject;

import lombok.Data;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;


public class Swing01 extends JFrame {
	private Container c;
	private JPanel jp1;
	private GridLayout grid;
	private JLabel la1, la2;
	private JTextField tf1, tf2;
	private JButton btn1, btn2;

	public Swing01() {
		setSize(300, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		c = getContentPane();
		jp1 = new JPanel();
		grid = new GridLayout(3, 2);

		la1 = new JLabel("전화번호");
		la2 = new JLabel("메시지");
		tf1 = new JTextField("");
		tf2 = new JTextField("");
		btn1 = new JButton("전송");
		btn2 = new JButton("초기화");

		c.add(jp1);
		jp1.setLayout(grid);
		jp1.add(la1);
		jp1.add(tf1);
		jp1.add(la2);
		jp1.add(tf2);
		jp1.add(btn1);
		jp1.add(btn2);

		btn1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String phone = tf1.getText();
				String msg = tf2.getText();
				new ExampleSend(phone, msg);
			}
		});

		btn2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tf1.setText("");
				tf2.setText("");
			}
		});

		setSize(300, 200);
		setVisible(true);
	}

	public static void main(String[] args) {
		new Swing01();

	}
}
