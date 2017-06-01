package SingMember;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import Notice.NoticeCompanyControlFrame;

public class SingMember extends JFrame implements ActionListener,
		MouseListener, WindowListener {

	String data[][] = new String[0][3];
	String title[] = { "�뷡 ��ȣ", "�뷡 ����", "������", "�����" };

	String data1[][] = new String[0][3];
	String title1[] = { "�뷡 ��ȣ", "�뷡 ����", "������", "�����" };

	DefaultTableModel Sing_Model = new DefaultTableModel(data, title) { // ���� ��
		// ��
		public boolean isCellEditable(int r, int c) {
			return false;
		}
	};
	DefaultTableModel Sing_Model_Update = new DefaultTableModel(data1, title1) { // �Ű�
		// ��
		public boolean isCellEditable(int r, int c) {
			return false;
		}
	};

	JLabel lbl_memberSing_label;// �� ���̺� ���̺�
	JLabel lbl_memberSing_updatelist;// �Ű� ���̺� ���̺�
	JLabel lbl_memberSing_date;// ��¥ ���̺�
	JTextField txt_member_search;// ��¥
	JButton btn_memberSing_search;// ��¥�� �˻� ��ư
	JButton SingMember_Exit;// ���� ��ư
	JButton btn_notice;// �������� ����
	JPanel TitlePanel;// ���� �� ���̺� �г�
	JPanel UpdatePanel;// ������Ʈ �� ���̺� �г�
	BorderLayout SingTitle_Border;// ��ġ������
	BorderLayout SingUpdate_Border;// ��ġ������
	JTable SingTableTitle;// ���� �� ���̺�
	JTable SingTableUpdate;// ������Ʈ �� ���̺�
	JScrollPane sp_Title;// ���� �� ��ũ��
	JScrollPane sp_Update;// ������Ʈ �� ��ũ��
	TitledBorder titledBorder1;// ���� �� Ÿ��Ʋ
	TitledBorder titledBorder2;// ������Ʈ �� Ÿ��Ʋ

	// ������Ʈ ����
	public SingMember() {

		JPanel panel = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		JPanel panel4 = new JPanel();
		TitlePanel = new JPanel();
		UpdatePanel = new JPanel();
		JScrollPane sp_Title = new JScrollPane();
		JScrollPane sp_Update = new JScrollPane();
		titledBorder1 = new TitledBorder("��(�뷡��) �����ͺ��̽�");
		titledBorder2 = new TitledBorder("�Ű�(�뷡��) ������Ʈ");

		setSize(1000, 1000);
		setTitle("�� ���� ���α׷�(����)");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(((screenSize.width) / 2) - 500,
				((screenSize.height) / 2) - 500);

		SingMemberTitleModel();// ���� �� ���̺�
		SingMemberUpdateModel();// ������Ʈ �� ���̺�

		lbl_memberSing_date = new JLabel("��¥ �Է�(�� ����) : ");
		lbl_memberSing_date.setFont(new Font("Monospaced", Font.BOLD, 12));
		lbl_memberSing_date.setBounds(550, 820, 150, 30);
		panel4.add(lbl_memberSing_date);

		txt_member_search = new JTextField();
		txt_member_search.setBounds(700, 820, 80, 30);
		panel4.add(txt_member_search);

		btn_memberSing_search = new JButton("�˻�");
		btn_memberSing_search.setBounds(810, 820, 80, 30);
		panel4.add(btn_memberSing_search);
		btn_memberSing_search.addActionListener(this);

		TitlePanel.add(sp_Title, BorderLayout.CENTER);
		sp_Title.getViewport().add(SingTableTitle, null);
		add(TitlePanel);
		UpdatePanel.add(sp_Update, BorderLayout.CENTER);
		sp_Update.getViewport().add(SingTableUpdate, null);
		add(UpdatePanel);

		btn_notice = new JButton("�������� ����");
		btn_notice.setFont(new Font("�������� ����", Font.BOLD, 13));
		panel4.add(btn_notice);
		btn_notice.setBounds(720, 900, 120, 30);
		btn_notice.addActionListener(this);

		SingMember_Exit = new JButton("����");
		SingMember_Exit.setFont(new Font("����", Font.BOLD, 13));
		panel4.add(SingMember_Exit);
		SingMember_Exit.setBounds(850, 900, 80, 30);
		SingMember_Exit.addActionListener(this);

		panel2.setBorder(titledBorder1);
		panel2.setBounds(new Rectangle(30, 13, 920, 400));
		panel2.setOpaque(false);
		panel3.setBorder(titledBorder2);
		panel3.setBounds(new Rectangle(30, 450, 920, 430));
		panel3.setOpaque(false);

		add(panel);
		add(panel2);
		add(panel3);
		add(panel4);
		panel.setLayout(null);
		panel2.setLayout(null);
		panel3.setLayout(null);
		panel4.setLayout(null);
		setVisible(true);
	}

	// ������ ���̺� ��
	public void SingMemberTitleModel() {

		SingTitle_Border = new BorderLayout();
		SingTableTitle = new JTable(Sing_Model);

		TitlePanel.setBorder(BorderFactory.createRaisedBevelBorder());
		TitlePanel.setBounds(new Rectangle(80, 70, 809, 298));
		TitlePanel.setLayout(SingTitle_Border);

		SingTableTitle.setBackground(Color.WHITE);
		SingTableTitle.setBorder(BorderFactory.createEtchedBorder());

		SingTableTitle.getColumnModel().getColumn(0).setPreferredWidth(50); // ���̵�
		SingTableTitle.getColumnModel().getColumn(1).setPreferredWidth(50); // ��й�ȣ
		SingTableTitle.getColumnModel().getColumn(2).setPreferredWidth(50); // �̸�
		SingTableTitle.getColumnModel().getColumn(3).setPreferredWidth(50); // �ֹι�ȣ

		SingTableTitle.setShowHorizontalLines(true); // ���� ���� �Ⱥ��̰� ó��
		SingTableTitle.setShowVerticalLines(true); // ���� ���� �Ⱥ��̰� ó��
		SingTableTitle.setSelectionBackground(Color.orange);
		SingTableTitle.setSelectionForeground(Color.blue);
		SingTableTitle.setRowMargin(0);
		SingTableTitle.setIntercellSpacing(new Dimension(0, 0));
		SingTableTitle.setRequestFocusEnabled(false); // Ư�� ���� ��Ŀ�� ���� �ȵǰ� ó��

		SingTableTitle.setSelectionMode(0); // ���ϼ��� :0 , ���߼���:1, �������� ���� -2

		SingTableTitle.getTableHeader().setReorderingAllowed(false); // �������
		// table.getTableHeader().setBackground(new Color(200,200,200));
		// table.getTableHeader().setForeground(new Color(100,100,100));
		SingTableTitle.getTableHeader().setMaximumSize(new Dimension(140, 0));
		SingTableTitle.getTableHeader().setMinimumSize(new Dimension(10, 0));
		// table.getTableHeader().setResizingAllowed(false); //��������Ұ���
		SingTableTitle.setAlignmentX(JTable.CENTER_ALIGNMENT); // ����
		SingTableTitle.setAlignmentY(JTable.CENTER_ALIGNMENT);

		this.SingTableTitle.addMouseListener(this);

	}

	// ������Ʈ ���̺� ��
	public void SingMemberUpdateModel() {

		SingUpdate_Border = new BorderLayout();
		SingTableUpdate = new JTable(Sing_Model_Update);

		UpdatePanel.setBorder(BorderFactory.createRaisedBevelBorder());
		UpdatePanel.setBounds(new Rectangle(80, 500, 809, 298));
		UpdatePanel.setLayout(SingUpdate_Border);

		SingTableUpdate.setBackground(Color.WHITE);
		SingTableUpdate.setBorder(BorderFactory.createEtchedBorder());

		SingTableUpdate.getColumnModel().getColumn(0).setPreferredWidth(50); // ���̵�
		SingTableUpdate.getColumnModel().getColumn(1).setPreferredWidth(50); // ��й�ȣ
		SingTableUpdate.getColumnModel().getColumn(2).setPreferredWidth(50); // �̸�
		SingTableUpdate.getColumnModel().getColumn(3).setPreferredWidth(50); // �ֹι�ȣ

		SingTableUpdate.setShowHorizontalLines(true); // ���� ���� �Ⱥ��̰� ó��
		SingTableUpdate.setShowVerticalLines(true); // ���� ���� �Ⱥ��̰� ó��
		SingTableUpdate.setSelectionBackground(Color.orange);
		SingTableUpdate.setSelectionForeground(Color.blue);
		SingTableUpdate.setRowMargin(0);
		SingTableUpdate.setIntercellSpacing(new Dimension(0, 0));
		SingTableUpdate.setRequestFocusEnabled(false); // Ư�� ���� ��Ŀ�� ���� �ȵǰ� ó��

		SingTableUpdate.setSelectionMode(0); // ���ϼ��� :0 , ���߼���:1, �������� ���� -2

		SingTableUpdate.getTableHeader().setReorderingAllowed(false); // �������
		// table.getTableHeader().setBackground(new Color(200,200,200));
		// table.getTableHeader().setForeground(new Color(100,100,100));
		SingTableUpdate.getTableHeader().setMaximumSize(new Dimension(140, 0));
		SingTableUpdate.getTableHeader().setMinimumSize(new Dimension(10, 0));
		// table.getTableHeader().setResizingAllowed(false); //��������Ұ���
		SingTableUpdate.setAlignmentX(JTable.CENTER_ALIGNMENT); // ����
		SingTableUpdate.setAlignmentY(JTable.CENTER_ALIGNMENT);

		this.SingTableUpdate.addMouseListener(this);

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

	public void sqlQuery(String sql2) {
		System.out.println("query SQL : " + sql2);
		Statement stmt = null;

		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql2);
			Sing_Model.setNumRows(0); // ������ û��
			while (rs.next()) {
				String data[] = { rs.getString(1), rs.getString(2),
						rs.getString(3), rs.getString(4), };
				Sing_Model.addRow(data);
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

	public void sqlQuery2(String sql) {
		System.out.println("query SQL : " + sql);
		Statement stmt = null;

		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			Sing_Model_Update.setNumRows(0); // ������ û��
			while (rs.next()) {
				String data[] = { rs.getString(1), rs.getString(2),
						rs.getString(3), rs.getString(4), };
				Sing_Model_Update.addRow(data);
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

	public boolean sqlUpdate2(String sql2) {
		System.out.println("update SQL : " + sql2);
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			int i = stmt.executeUpdate(sql2);
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
		if (e.getSource() == SingMember_Exit) {
			this.dispose();
		}

		Object ob = e.getSource();

		if (ob == this.btn_memberSing_search) {// �Ű� �˻�
			String sql = "select * from singtitle where sing_date like '%__"
					+ this.txt_member_search.getText().trim() + "__%'";
			if (sqlUpdate2(sql)) {
				JOptionPane.showMessageDialog(this, txt_member_search.getText()
						+ " ���� �Ű� ����Դϴ�!", "���� �Ű� ��ȸ",
						JOptionPane.DEFAULT_OPTION);
				sqlQuery2("select * from singtitle where sing_date like '%__"
						+ this.txt_member_search.getText().trim() + "__%'");
				txt_member_search.setText("");
			} else {
				JOptionPane.showMessageDialog(this, "�Ű� ��ȸ �����Դϴ�.", "�Ű� ��ȸ ����",
						JOptionPane.DEFAULT_OPTION);
				return;
			}
		}
		if (ob == this.btn_notice) {
			new NoticeCompanyControlFrame();
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

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
