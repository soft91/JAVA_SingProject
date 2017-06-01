package MemberClient;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.table.DefaultTableModel;

public class MemberClientSaveSing extends JFrame implements ActionListener {

	String data[][] = new String[0][3];
	String title[] = { "�뷡 ��ȣ", "�뷡 ����", "������", "�����" };

	DefaultTableModel Sing_model = new DefaultTableModel(data, title) { // ���� ��
		// ��
		public boolean isCellEditable(int r, int c) {
			return false;
		}
	};
	JButton btn_search;
	JButton btn_title;
	JButton btn_exit;
	JLabel lbl_year;
	JLabel lbl_month;
	JComboBox years;// �⵵ �޺��ڽ�
	JComboBox month;// ��¥ �޺��ڽ�
	JTable sing_table;// �뷡 ���
	JScrollPane sing_scroll;// �뷡 ��ũ���г�
	BorderLayout sing_border;
	JPanel TitlePanel;
	JPanel panel;

	public MemberClientSaveSing() {
		setSize(800, 440);
		setTitle("�� Ŭ���̾�Ʈ(���� �� ���)");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		TitlePanel = new JPanel();
		panel = new JPanel();
		sing_scroll = new JScrollPane();

		lbl_year = new JLabel("��");
		lbl_year.setBounds(580, 25, 70, 20);
		panel.add(lbl_year);

		lbl_month = new JLabel("��");
		lbl_month.setBounds(680, 25, 70, 20);
		panel.add(lbl_month);

		btn_title = new JButton("��ü����");
		btn_title.setBounds(10, 10, 110, 40);
		panel.add(btn_title);
		btn_title.addActionListener(this);

		btn_search = new JButton("�˻�");
		btn_search.setBounds(700, 25, 70, 20);
		panel.add(btn_search);
		btn_search.addActionListener(this);

		btn_exit = new JButton("����");
		btn_exit.setBounds(700, 353, 70, 40);
		panel.add(btn_exit);
		btn_exit.addActionListener(this);

		years = new JComboBox();
		years.setBounds(500, 25, 70, 20);
		panel.add(years);

		month = new JComboBox();
		month.setBounds(605, 25, 70, 20);
		panel.add(month);

		SingMemberTitleModel();

		for (int j = 1; j < 13; j++) {
			month.addItem(j);
		}

		for (int i = 2014; i > 1990; i--) {
			years.addItem(i);
		}

		TitlePanel.add(sing_scroll, BorderLayout.CENTER);
		sing_scroll.getViewport().add(sing_table, null);
		add(TitlePanel);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(((screenSize.width) / 2) - 400,
				((screenSize.height) / 2) - 220);

		panel.setLayout(null);
		add(TitlePanel);
		add(panel);
		setVisible(true);
		connProcess();
	}

	public void SingMemberTitleModel() {
		sing_border = new BorderLayout();
		sing_table = new JTable(Sing_model);

		TitlePanel.setBorder(BorderFactory.createRaisedBevelBorder());
		TitlePanel.setBounds(new Rectangle(0, 55, 785, 290));
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
				JOptionPane
						.showMessageDialog(this, this.years.getSelectedItem()
								+ "�⵵ " + this.month.getSelectedItem()
								+ "���� �Ű� ����Դϴ�!", "���� �Ű� ��ȸ",
								JOptionPane.DEFAULT_OPTION);
				sqlQuery("select * from singtitle where sing_date like '%"
						+ month.getSelectedItem() + "%'" + " and "
						+ "sing_date like '%" + years.getSelectedItem() + "%'");
			} else {
				JOptionPane.showMessageDialog(this, "�Ű� ��ȸ �����Դϴ�.", "�Ű� ��ȸ ����",
						JOptionPane.DEFAULT_OPTION);
				return;
			}
		} else if (ob == this.btn_title) {// ���� �� Ȯ��

			String sql = "select sing_num,sing_name,sing_singer,sing_date FROM singtitle order by sing_num asc";
			System.out.println("query SQL : " + sql);

			if (sqlUpdate(sql)) {
				sqlQuery("select sing_num,sing_name,sing_singer,sing_date FROM singtitle order by sing_num asc");
			}
		}
	}
}
