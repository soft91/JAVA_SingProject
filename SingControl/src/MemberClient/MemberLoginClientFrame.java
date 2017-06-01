package MemberClient;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Notice.NoticeCheckMainMember;
import SingMain.SingMainFrame;

public class MemberLoginClientFrame extends JFrame implements ActionListener,
		WindowListener {

	JLabel lbl_main;
	JLabel lbl_id;
	JLabel lbl_pw;
	JTextField txt_id;
	JPasswordField txt_pw;
	JButton btn_login;
	JButton btn_sign;
	JButton btn_exit;

	public MemberLoginClientFrame() {

		setSize(700, 400);
		setTitle("�α���");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel();

		lbl_main = new JLabel("N.C.S");
		panel.add(lbl_main);
		lbl_main.setBounds(290, 38, 600, 50);
		lbl_main.setFont(new Font("���� ���", Font.BOLD, 40));

		lbl_id = new JLabel("ID : ");
		panel.add(lbl_id);
		lbl_id.setBounds(130, 110, 600, 50);
		lbl_id.setFont(new Font("���� ���", Font.BOLD, 40));

		lbl_pw = new JLabel("PW : ");
		panel.add(lbl_pw);
		lbl_pw.setBounds(130, 180, 600, 50);
		lbl_pw.setFont(new Font("���� ���", Font.BOLD, 40));

		txt_id = new JTextField();
		panel.add(txt_id);
		txt_id.setBounds(250, 115, 300, 50);
		txt_id.setFont(new Font("���� ���", Font.BOLD, 40));

		txt_pw = new JPasswordField();
		panel.add(txt_pw);
		txt_pw.setBounds(250, 185, 300, 50);
		txt_pw.setFont(new Font("���� ���", Font.BOLD, 40));

		btn_sign = new JButton("ȸ������");
		panel.add(btn_sign);
		btn_sign.setBounds(100, 280, 160, 50);
		btn_sign.setFont(new Font("���� ���", Font.BOLD, 30));
		btn_sign.addActionListener(this);

		btn_login = new JButton("�α���");
		panel.add(btn_login);
		btn_login.setBounds(270, 280, 160, 50);
		btn_login.setFont(new Font("���� ���", Font.BOLD, 30));
		btn_login.addActionListener(this);

		btn_exit = new JButton("����");
		panel.add(btn_exit);
		btn_exit.setBounds(440, 280, 160, 50);
		btn_exit.setFont(new Font("���� ���", Font.BOLD, 30));
		btn_exit.addActionListener(this);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(((screenSize.width) / 2) - 350,
				((screenSize.height) / 2) - 200);

		add(panel);
		panel.setLayout(null);
		setVisible(true);
		connProcess();
	}

	Connection con = null;// DB

	public boolean connProcess() {
		String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
		String user = "scott";
		String passwd = "tiger";
		System.out.println("�����ͺ��̽��� ������......");
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(url, user, passwd);
		} catch (ClassNotFoundException e1) {
			System.out.println("������� : ����̹� �ε� ����");
			return false;
		} catch (SQLException e2) {
			System.out.println("������� : url,user,passwd ������ �ùٸ��� ����");
			return false;
		}
		System.out.println("���� ����");
		return true;
	}

	public void loginCheck() {

		String id;
		String pass;
		String name;
		// �α���â�� �ؽ�Ʈ�ʵ忡 ������ ��������
		id = txt_id.getText().trim();
		pass = txt_pw.getText().trim();

		Statement stmt = null;

		// SELECT ������ �ۼ��Ѵ�. �ش��ϴ� ���̵��� �н����带 �˻��Ѵ�.
		String query = "SELECT member_id,member_pw FROM sing_member where member_id='"
				+ id + "'" + " and " + "member_pw = '" + pass + "'";

		System.out.println(query);

		if (txt_id.getText().equals("")) {

			JOptionPane.showMessageDialog(this, "���̵�� �н����带 Ȯ���ϼ���!", "�α��� ����!",
					JOptionPane.DEFAULT_OPTION);
		} else {
			try {
				stmt = con.createStatement();
				// executeQuery() �޼���� SELECT���� �����Ű�� ����� ResultSet ��ü�� �޴´�.
				ResultSet rs = stmt.executeQuery(query);
				// ���ڵ尡 �ִ��� �˻�

				if (rs.next()) {
					//
					id = rs.getString("member_id");

					// �ؽ�Ʈ�ʵ忡 ������ �����ͺ��̽��� �ִ� �н����� ���� ���Ѵ�.
					if (pass.equals(rs.getString("member_pw"))) {

						// ������ �α��μ��긦 ����ش�.
						ClientFrame a = new ClientFrame();
						a.lbl_membercheck.setText(id);
					}

				} else {
					JOptionPane.showMessageDialog(this, "���̵�� �н����带 Ȯ���ϼ���!",
							"�α��� ����!", JOptionPane.DEFAULT_OPTION);
				}

			} catch (Exception b) {
				b.printStackTrace();
			}
		}
	}

	// ����Ÿ���̽��� ���� ����
	public void disconnProcess() {
		try {
			if (this.con != null) {
				this.con.close();
			}
		} catch (Exception e) {
		}

		JOptionPane.showMessageDialog(this, "������ ������ �������ϴ�", "���� ����",
				JOptionPane.DEFAULT_OPTION);
		return;
	}

	public void sqlQuery(String sql) {
		System.out.println("query SQL : " + sql);
		Statement stmt = null;

		try {
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
		} catch (Exception e) {
			System.out.println("����Ÿ Select ���� : " + e.getMessage());
		} finally {
			try {
				stmt.close();
			} catch (Exception e1) {
			}
		}
	}

	public boolean sqlUpdate(String sql) {
		System.out.println("update SQL : " + sql);
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			int i = stmt.executeUpdate(sql);
			System.out.println(i + "���� ����Ÿ ������Ʈ ���� ");
			return true;
		} catch (Exception e) {
			System.out.println("����Ÿ ������Ʈ ���� : " + e.getMessage());
			return false;
		} finally {
			try {
				stmt.close();
			} catch (Exception e1) {
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if (e.getSource() == btn_login) {
			loginCheck();

			NoticeCheckMainMember b = new NoticeCheckMainMember();
		}

		if (e.getSource() == btn_sign) {
			MemberClientSignListFrame a = new MemberClientSignListFrame();
		}

		if (e.getSource() == btn_exit) {
			this.dispose();
		}

	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

}
