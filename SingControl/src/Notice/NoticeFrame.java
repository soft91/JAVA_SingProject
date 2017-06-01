package Notice;

import java.awt.Dimension;
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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class NoticeFrame extends JFrame implements ActionListener,
		WindowListener {

	JLabel lbl_num;// �� ��ȣ ���̺�
	JLabel lbl_nametitle;// �� ���� ���̺�
	JLabel lbl_name;// �� ���� ���̺�
	JTextField txt_name;// �� ���� �ؽ�Ʈ �ʵ�
	JTextField txt_num;// �� ��ȣ �ؽ�Ʈ �ʵ�
	JTextField txt_nametitle;// �� ���� �ؽ�Ʈ �ʵ�
	JLabel lbl_title;// �� ���� ���̺�
	JTextArea txt_title;// �� ����
	JButton btn_insert;// �� �߰�
	JButton btn_exit;// ����
	JScrollPane scroll_title;

	public NoticeFrame() {

		setSize(600, 450);
		setTitle("��������");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		connProcess();

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(((screenSize.width) / 2) - 300,
				((screenSize.height) / 2) - 225);

		JPanel panel = new JPanel();
		scroll_title = new JScrollPane();

		lbl_num = new JLabel("�� ��ȣ : ");
		panel.add(lbl_num);
		lbl_num.setBounds(20, 10, 90, 30);

		lbl_name = new JLabel("�� ���� : ");
		panel.add(lbl_name);
		lbl_name.setBounds(20, 50, 90, 30);

		lbl_nametitle = new JLabel("�� ���� : ");
		panel.add(lbl_nametitle);
		lbl_nametitle.setBounds(20, 90, 90, 30);

		txt_num = new JTextField();
		panel.add(txt_num);
		txt_num.setBounds(80, 10, 90, 30);
		txt_num.setEnabled(false);

		txt_name = new JTextField();
		panel.add(txt_name);
		txt_name.setBounds(80, 50, 90, 30);
		txt_name.setEnabled(false);

		txt_nametitle = new JTextField();
		panel.add(txt_nametitle);
		txt_nametitle.setBounds(80, 90, 300, 30);
		txt_nametitle.setEnabled(false);

		lbl_title = new JLabel("�� ����");
		panel.add(lbl_title);
		lbl_title.setBounds(20, 120, 90, 30);

		txt_title = new JTextArea();
		panel.add(txt_title);
		txt_title.setBounds(20, 150, 545, 200);
		txt_title.setEnabled(false);

		btn_exit = new JButton("����");
		panel.add(btn_exit);
		btn_exit.setBounds(475, 370, 90, 30);
		btn_exit.addActionListener(this);

		panel.setLayout(null);
		add(panel);
		setVisible(true);
	}

	Connection conn = null;// DB

	public boolean connProcess() {
		String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
		String user = "scott";
		String passwd = "tiger";
		System.out.println("�����ͺ��̽��� ������......");
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, user, passwd);
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

	// ����Ÿ���̽��� ���� ����
	public void disconnProcess() {
		try {
			if (this.conn != null) {
				this.conn.close();
			}
		} catch (Exception e) {
		}

		JOptionPane.showMessageDialog(this, "������ ������ �������ϴ�", "���� ����",
				JOptionPane.DEFAULT_OPTION);
		return;
	}

	public boolean sqlUpdate(String sql) {
		System.out.println("update SQL : " + sql);
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
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

	public void sqlQuery(String sql) {
		System.out.println("query SQL : " + sql);
		Statement stmt = null;

		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String data[] = { rs.getString(1), rs.getString(2),
						rs.getString(3), rs.getString(4), };
			}
		} catch (Exception e) {
			System.out.println("����Ÿ Select ���� : " + e.getMessage());
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

		if (e.getSource() == btn_exit) {
			this.dispose();
		}
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		e.getWindow().dispose();
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