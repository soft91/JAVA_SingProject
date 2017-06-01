package MemberClient;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class MemberClientSignListFrame extends JFrame implements ActionListener {
	JButton distinct_id;// �ߺ��˻� ��ư
	JButton add_member;// ȸ������ ��� ��ư
	JButton exit;// �����ȣ �˻� ��ư

	JLabel member;// ȸ������ ����
	JLabel member_id;// ���̵� ����
	JLabel member_pw;// ��й�ȣ ����
	JLabel member_pw2;// ��й�ȣ Ȯ�� ����
	JLabel member_name;// �̸� ����
	JLabel member_store;// �̸��� ����
	JLabel member_add;// �����ȣ ����
	JLabel member_phone;// �ּ� ����
	JLabel member_room;// �޴��� ����

	JTextField txt_member_id;// ���̵� ����
	JPasswordField txt_member_pw;// ��й�ȣ ����
	JPasswordField txt_member_pw2;// ��й�ȣ Ȯ�� ����
	JTextField txt_member_name;// �̸� ����
	JTextField txt_member_store;// �̸��� ����
	JTextField txt_member_add;// �����ȣ ����
	JTextField txt_member_phone;// �ּ� ����
	JTextField txt_member_room;// �޴��� ����

	Connection con = null;

	public MemberClientSignListFrame() {

		String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
		String id = "scott";
		String password = "tiger";

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(url, id, password);
		} catch (ClassNotFoundException e) {
			System.out.println("����̹��� ã�� �� �����ϴ�.");
		} catch (SQLException e) {
			System.out.println("���ῡ �����Ͽ����ϴ�.");
		}

		setSize(600, 800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("ȸ �� �� ��");

		// ��ư ����
		distinct_id = new JButton("�ߺ� �˻�");
		add_member = new JButton("ȸ�� ���");
		exit = new JButton("���");
		add_member.addActionListener(this);
		distinct_id.addActionListener(this);
		exit.addActionListener(this);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(((screenSize.width) / 2) - 300,
				((screenSize.height) / 2) - 400);

		member_Label();
		member_txt();
		panel();

		setVisible(true);
	}

	// �г�
	public void panel() {
		JPanel panel = new JPanel();
		panel.setLayout(null);

		// ���̺�
		panel.add(member);
		member.setBounds(200, 15, 200, 30);
		member.setFont(new Font("����ü", Font.BOLD, 30));
		panel.add(member_id);
		member_id.setBounds(50, 100, 150, 30);
		member_id.setFont(new Font("���� ���", Font.BOLD, 15));
		panel.add(member_pw);
		member_pw.setBounds(50, 170, 150, 30);
		member_pw.setFont(new Font("���� ���", Font.BOLD, 15));
		panel.add(member_pw2);
		member_pw2.setBounds(50, 240, 150, 30);
		member_pw2.setFont(new Font("���� ���", Font.BOLD, 15));
		panel.add(member_name);
		member_name.setBounds(50, 310, 150, 30);
		member_name.setFont(new Font("���� ���", Font.BOLD, 15));
		panel.add(member_store);
		member_store.setBounds(50, 380, 150, 30);
		member_store.setFont(new Font("���� ���", Font.BOLD, 15));
		panel.add(member_add);
		member_add.setBounds(50, 450, 150, 30);
		member_add.setFont(new Font("���� ���", Font.BOLD, 15));
		panel.add(member_phone);
		member_phone.setBounds(50, 520, 150, 30);
		member_phone.setFont(new Font("���� ���", Font.BOLD, 15));
		panel.add(member_room);
		member_room.setBounds(50, 590, 150, 30);
		member_room.setFont(new Font("���� ���", Font.BOLD, 15));

		// �ؽ�Ʈ �ʵ�
		panel.add(txt_member_id);
		txt_member_id.setBounds(160, 105, 120, 25);
		txt_member_id.setFont(new Font("���� ���", Font.BOLD, 15));
		panel.add(txt_member_pw);
		txt_member_pw.setBounds(160, 175, 120, 25);
		txt_member_pw.setFont(new Font("���� ���", Font.BOLD, 15));
		panel.add(txt_member_pw2);
		txt_member_pw2.setBounds(160, 245, 120, 25);
		txt_member_pw2.setFont(new Font("���� ���", Font.BOLD, 15));
		panel.add(txt_member_name);
		txt_member_name.setBounds(160, 315, 120, 25);
		txt_member_name.setFont(new Font("���� ���", Font.BOLD, 15));
		panel.add(txt_member_store);
		txt_member_store.setBounds(160, 385, 200, 25);
		txt_member_store.setFont(new Font("���� ���", Font.BOLD, 15));
		panel.add(txt_member_add);
		txt_member_add.setBounds(160, 455, 400, 25);
		txt_member_add.setFont(new Font("���� ���", Font.BOLD, 15));
		panel.add(txt_member_phone);
		txt_member_phone.setBounds(160, 525, 120, 25);
		txt_member_phone.setFont(new Font("���� ���", Font.BOLD, 15));
		panel.add(txt_member_room);
		txt_member_room.setBounds(160, 595, 120, 25);
		txt_member_room.setFont(new Font("���� ���", Font.BOLD, 15));

		// ��ư
		panel.add(distinct_id);
		distinct_id.setBounds(300, 105, 100, 20);
		panel.add(add_member);
		add_member.setBounds(190, 700, 100, 30);
		panel.add(exit);
		exit.setBounds(300, 700, 100, 30);

		add(panel);

	}

	// ���̺� ����
	public void member_Label() {
		member = new JLabel("ȸ �� �� ��");
		member_id = new JLabel("����� ID : ");
		member_pw = new JLabel("��й�ȣ : ");
		member_pw2 = new JLabel("��й�ȣ Ȯ�� : ");
		member_name = new JLabel("���� : ");
		member_store = new JLabel("��ȣ�� : ");
		member_add = new JLabel("�ּ� : ");
		member_phone = new JLabel("��ȭ��ȣ : ");
		member_room = new JLabel("�� ���� : ");
	}

	// �ؽ�Ʈ �ʵ� ����
	public void member_txt() {
		txt_member_id = new JTextField(8);
		txt_member_pw = new JPasswordField(16);
		txt_member_pw2 = new JPasswordField(16);
		txt_member_name = new JTextField(8);
		txt_member_store = new JTextField(8);
		txt_member_add = new JTextField(8);
		txt_member_phone = new JTextField(8);
		txt_member_room = new JTextField(8);
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

	public void IDCheck() {

		String id;
		String pass;
		String name;
		// �α���â�� �ؽ�Ʈ�ʵ忡 ������ ��������
		id = txt_member_id.getText().trim();

		Statement stmt = null;

		// SELECT ������ �ۼ��Ѵ�. �ش��ϴ� ���̵��� �н����带 �˻��Ѵ�.
		String query = "SELECT member_id FROM sing_member where member_id='"
				+ id + "'";

		System.out.println(query);

		try {
			stmt = con.createStatement();
			// executeQuery() �޼���� SELECT���� �����Ű�� ����� ResultSet ��ü�� �޴´�.
			ResultSet rs = stmt.executeQuery(query);
			// ���ڵ尡 �ִ��� �˻�

			if (rs.next()) {

				// �ؽ�Ʈ�ʵ忡 ������ �����ͺ��̽��� �ִ� ���̵� ���� ���Ѵ�.
				if (id.equals(rs.getString("member_id"))) {
					JOptionPane.showMessageDialog(this, "�̹� ���� ���̵� �ֽ��ϴ�.",
							"���̵� �ߺ� Ȯ��", JOptionPane.DEFAULT_OPTION);
				}
			} else {
				JOptionPane.showMessageDialog(this, "����� �� �ִ� ���̵��Դϴ�.",
						"���̵� �ߺ� Ȯ��", JOptionPane.DEFAULT_OPTION);
			}

		} catch (Exception b) {
			b.printStackTrace();
		}
	}

	public void add_member() {
		System.out.println("�߰� ��ư Ŭ��!");
		String sql = "insert into sing_member(member_num,member_id,member_pw,member_pwcheck,member_name,member_store,member_addr,member_phone,member_room,member_date) values(sing_member_seq.nextval"
				+ ",'"
				+ this.txt_member_id.getText().trim()
				+ "','"
				+ this.txt_member_pw.getText().trim()
				+ "','"
				+ this.txt_member_pw2.getText().trim()
				+ "','"
				+ this.txt_member_name.getText().trim()
				+ "','"
				+ this.txt_member_store.getText().trim()
				+ "','"
				+ this.txt_member_add.getText().trim()
				+ "','"
				+ this.txt_member_phone.getText().trim()
				+ "','"
				+ this.txt_member_room.getText().trim() + "',sysdate)";
		if (txt_member_name.equals("") == false) {
			if (sqlUpdate(sql)) {
				JOptionPane.showMessageDialog(this, "������ �߰� ����!", "���߰� ����",
						JOptionPane.DEFAULT_OPTION);
				sqlQuery("SELECT member_num,member_name,member_store,member_addr,member_phone,member_room,member_date from sing_member");
			} else {
				JOptionPane.showMessageDialog(this, "��ĭ�� �ְų� ���̵� �ߺ� ���� �Դϴ�.",
						"���߰� ����!", JOptionPane.DEFAULT_OPTION);
				txt_member_id.setText("");
				return;
			}
		}
		txt_member_id.setText("");
		txt_member_pw.setText("");
		txt_member_pw2.setText("");
		txt_member_phone.setText("");
		txt_member_name.setText("");
		txt_member_store.setText("");
		txt_member_add.setText("");
		txt_member_phone.setText("");
		txt_member_room.setText("");
		dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if (e.getSource() == add_member) {

			add_member();
		}

		if (e.getSource() == distinct_id) {
			if (txt_member_id.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "���̵� �Է����ּ���!", "���̵� �Է�",
						JOptionPane.DEFAULT_OPTION);
			} else {
				IDCheck();
			}

		}
		if (e.getSource() == exit) {
			dispose();
		}

	}
}
