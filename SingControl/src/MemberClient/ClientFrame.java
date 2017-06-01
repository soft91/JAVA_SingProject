package MemberClient;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import Notice.NoticeCheckMainMember;

public class ClientFrame extends JFrame implements ActionListener {

	String data[][] = new String[0][3];
	String title[] = { "�뷡 ��ȣ", "�뷡 ����", "������", "�����" };

	DefaultTableModel Sing_model = new DefaultTableModel(data, title) { // ���� ��
		// ��
		public boolean isCellEditable(int r, int c) {
			return false;
		}
	};

	JPanel panel;
	JPanel panel2;
	JPanel panel3;
	JPanel panel4;

	JPanel TitlePanel;
	JLabel lbl_search;// �˻� ���̺�
	JLabel lbl_year;
	JLabel lbl_months;
	JLabel lbl_membercheck;
	JLabel lbl_membercheck2;
	JButton btn_search;// �˻� ��ư
	JButton btn_download;
	JButton btn_exit;// �����ư
	JButton btn_notice;// ��������
	JButton btn_savesing;// ���� �� �� ����.
	JComboBox years;// �⵵ �޺��ڽ�
	JComboBox month;// ��¥ �޺��ڽ�
	JTable sing_table;// �뷡 ���
	JScrollPane sing_scroll;// �뷡 ��ũ���г�
	BorderLayout sing_border;

	TitledBorder titledBorder1;
	ClientFrame frame1;
	public static int count = 0;

	public ClientFrame() {

		panel = new JPanel();
		panel2 = new JPanel();
		panel3 = new JPanel();
		panel4 = new JPanel();
		TitlePanel = new JPanel();
		sing_scroll = new JScrollPane();
		titledBorder1 = new TitledBorder("�� �� ������Ʈ ���");

		setSize(1000, 500);
		setTitle("�� Ŭ���̾�Ʈ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		SingMemberTitleModel();
		connProcess();

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(((screenSize.width) / 2) - 500,
				((screenSize.height) / 2) - 250);

		lbl_search = new JLabel("��¥ �Է�(�� �� ����) : ");
		lbl_search.setBounds(500, 30, 150, 30);
		panel4.add(lbl_search);

		lbl_year = new JLabel("��");
		lbl_year.setBounds(715, 30, 150, 30);
		panel4.add(lbl_year);

		lbl_months = new JLabel("��");
		lbl_months.setBounds(815, 30, 150, 30);
		panel4.add(lbl_months);

		btn_search = new JButton("�˻�");
		btn_search.setBounds(850, 30, 80, 30);
		panel4.add(btn_search);
		btn_search.addActionListener(this);

		btn_notice = new JButton("��������");
		btn_notice.setBounds(50, 30, 100, 30);
		panel4.add(btn_notice);
		btn_notice.addActionListener(this);

		btn_savesing = new JButton("���� �� ����");
		btn_savesing.setBounds(160, 30, 100, 30);
		panel4.add(btn_savesing);
		btn_savesing.addActionListener(this);

		btn_download = new JButton("�ٿ�ε�");
		btn_download.setBounds(750, 410, 90, 30);
		panel4.add(btn_download);
		btn_download.addActionListener(this);

		btn_exit = new JButton("����");
		btn_exit.setBounds(860, 410, 90, 30);
		panel4.add(btn_exit);
		btn_exit.addActionListener(this);

		lbl_membercheck = new JLabel();
		lbl_membercheck.setBounds(31, 410, 150, 30);
		panel4.add(lbl_membercheck);
		lbl_membercheck.setForeground(Color.BLUE);

		lbl_membercheck2 = new JLabel("�� �ȳ��ϼ���^^");
		lbl_membercheck2.setBounds(80, 410, 150, 30);
		panel4.add(lbl_membercheck2);
		lbl_membercheck2.setForeground(Color.BLUE);

		years = new JComboBox();
		years.setBounds(640, 35, 70, 20);
		panel4.add(years);

		month = new JComboBox();
		month.setBounds(740, 35, 70, 20);
		panel4.add(month);

		TitlePanel.add(sing_scroll, BorderLayout.CENTER);
		sing_scroll.getViewport().add(sing_table, null);
		add(TitlePanel);

		panel2.setBorder(titledBorder1);
		panel2.setBounds(new Rectangle(30, 13, 920, 380));
		panel2.setOpaque(false);

		for (int j = 1; j < 13; j++) {
			month.addItem(j);
		}

		for (int i = 2014; i > 1990; i--) {
			years.addItem(i);
		}

		add(panel2);
		add(panel4);

		panel2.setLayout(null);
		panel4.setLayout(null);
		setVisible(true);
	}

	public void SingMemberTitleModel() {
		sing_border = new BorderLayout();
		sing_table = new JTable(Sing_model);

		TitlePanel.setBorder(BorderFactory.createRaisedBevelBorder());
		TitlePanel.setBounds(new Rectangle(50, 65, 880, 310));
		TitlePanel.setLayout(sing_border);

		sing_table.setBackground(Color.WHITE);
		sing_table.setBorder(BorderFactory.createEtchedBorder());

		sing_table.getColumnModel().getColumn(0).setPreferredWidth(50); // ���̵�
		sing_table.getColumnModel().getColumn(1).setPreferredWidth(50); // ��й�ȣ
		sing_table.getColumnModel().getColumn(2).setPreferredWidth(50); // �̸�
		sing_table.getColumnModel().getColumn(3).setPreferredWidth(50); // �ֹι�ȣ

		sing_table.setShowHorizontalLines(true); // ���� ���� �Ⱥ��̰� ó��
		sing_table.setShowVerticalLines(true); // ���� ���� �Ⱥ��̰� ó��
		sing_table.setSelectionBackground(Color.orange);
		sing_table.setSelectionForeground(Color.blue);
		sing_table.setRowMargin(0);
		sing_table.setIntercellSpacing(new Dimension(0, 0));
		sing_table.setRequestFocusEnabled(false); // Ư�� ���� ��Ŀ�� ���� �ȵǰ� ó��

		sing_table.setSelectionMode(0); // ���ϼ��� :0 , ���߼���:1, �������� ���� -2

		sing_table.getTableHeader().setReorderingAllowed(false); // �������
		// table.getTableHeader().setBackground(new Color(200,200,200));
		// table.getTableHeader().setForeground(new Color(100,100,100));
		sing_table.getTableHeader().setMaximumSize(new Dimension(140, 0));
		sing_table.getTableHeader().setMinimumSize(new Dimension(10, 0));
		// table.getTableHeader().setResizingAllowed(false); //��������Ұ���
		sing_table.setAlignmentX(JTable.CENTER_ALIGNMENT); // ����
		sing_table.setAlignmentY(JTable.CENTER_ALIGNMENT);

		/* this.sing_table.addMouseListener(this); */

	}

	Connection conn = null;// DB

	// DB ����
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

	public void sqlQuery(String sql) {
		System.out.println("query SQL : " + sql);
		Statement stmt = null;

		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			Sing_model.setNumRows(0); // ������ û��
			while (rs.next()) {
				String data[] = { rs.getString(1), rs.getString(2),
						rs.getString(3), rs.getString(4), };
				count++;
				System.out.println(count);
				Sing_model.addRow(data);
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

	public void sqlQueryTest(String sql) {
		System.out.println("query SQL : " + sql);
		Statement stmt = null;

		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

		} catch (Exception e) {
			System.out.println("����Ÿ Select ���� : " + e.getMessage());
		}
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

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		Object ob = e.getSource();

		if (ob == this.btn_exit) {
			this.dispose();
		}
		if (ob == this.btn_search) {
			String sql = "select * from singtitle where sing_date like '%"
					+ month.getSelectedItem() + "%'" + " and "
					+ "sing_date like '%" + years.getSelectedItem() + "%'";
			if (sqlUpdate(sql)) {
				JOptionPane.showMessageDialog(this, years.getSelectedItem()
						+ "�� " + month.getSelectedItem() + "�� �Ű� ����Դϴ�!",
						"�Ű� ��� Ȯ��", JOptionPane.DEFAULT_OPTION);
				sqlQuery("select * from singtitle where sing_date like '%"
						+ month.getSelectedItem() + "%'" + " and "
						+ "sing_date like '%" + years.getSelectedItem() + "%'");
			} else {
				JOptionPane.showMessageDialog(this, years.getSelectedItem()
						+ "�� " + month.getSelectedItem() + "�� �Ű� ����Դϴ�!",
						"�Ű� ��� Ȯ��", JOptionPane.DEFAULT_OPTION);
				return;
			}
		}
		if (ob == this.btn_download) {

			if (Sing_model.getRowCount() == 0) {
				JOptionPane.showMessageDialog(this, "������Ʈ ����� Ȯ���ϼ���^^",
						"������Ʈ ����!", JOptionPane.DEFAULT_OPTION);
			} else {

				String str = "update sing_member set member_money ="
						+ count
						* 100
						+ ",member_lastupdate = to_char(sysdate,'yyyy/mm/dd') where member_id = '"
						+ lbl_membercheck.getText() + "'";
				sqlQueryTest(str);
				JOptionPane.showMessageDialog(this, "�ٿ�ε� �Ϸ�Ǿ����ϴ�^^",
						"������Ʈ ����!", JOptionPane.DEFAULT_OPTION);
				Sing_model.setNumRows(0);
			}

		}
		if (ob == this.btn_notice) {
			NoticeCheckMainMember a = new NoticeCheckMainMember();
		}

		if (ob == this.btn_savesing) {
			MemberClientSaveSing nn = new MemberClientSaveSing();
		}
	}
}
