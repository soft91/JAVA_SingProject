package Notice;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class NoticeCompanyControlFrame extends JFrame implements
		ActionListener, WindowListener, MouseListener {

	String data[][] = new String[0][5];
	String title[] = { "�� ��ȣ", "�� ����", "�� ����", "�� ����", "�����" };
	DefaultTableModel Notice_model = new DefaultTableModel(data, title) { // ����
		// ��
		// ��
		public boolean isCellEditable(int r, int c) {
			return false;
		}
	};

	JLabel lbl_name;// �� ���� ���̺�
	JLabel lbl_nametitle;// �� ����
	JTextField txt_name;// �� ���� �ؽ�Ʈ �ʵ�
	JTextField txt_nametitle;// �� ���� �ؽ�Ʈ �ʵ�
	JTextField txt_search;
	JLabel lbl_title;// �� ���� ���̺�
	JTextArea txt_title;// �� ����
	JButton btn_search;
	JButton btn_insert;// �� �߰�
	JButton btn_delete;// �� ����
	JButton btn_update;// �� ����
	JButton btn_refresh;// ��ü ����
	JButton btn_exit;// ����
	JScrollPane scroll_title;
	JTable notice_table;
	JComboBox combo_search;
	JPanel TitlePanel;
	BorderLayout borderLayout1;

	public NoticeCompanyControlFrame() {

		setSize(1400, 450);
		setTitle("�������� ����");

		connProcess();

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(((screenSize.width) / 2) - 700,
				((screenSize.height) / 2) - 225);

		JPanel panel = new JPanel();
		JScrollPane sp_Title = new JScrollPane();
		TitlePanel = new JPanel();
		borderLayout1 = new BorderLayout();

		SingMemberTitleModel();

		TitlePanel.add(sp_Title, BorderLayout.CENTER);
		sp_Title.getViewport().add(notice_table, null);
		add(TitlePanel);

		combo_search = new JComboBox();
		panel.add(combo_search);
		combo_search.setBounds(500, 7, 90, 25);

		combo_search.addItem("�� ��ȣ");
		combo_search.addItem("�� ����");
		combo_search.addItem("�� ����+����");

		txt_search = new JTextField();
		panel.add(txt_search);
		txt_search.setBounds(595, 7, 115, 26);

		lbl_name = new JLabel("�� ���� : ");
		panel.add(lbl_name);
		lbl_name.setBounds(810, 10, 90, 30);

		txt_name = new JTextField();
		panel.add(txt_name);
		txt_name.setBounds(870, 10, 90, 30);

		txt_nametitle = new JTextField();
		panel.add(txt_nametitle);
		txt_nametitle.setBounds(870, 50, 300, 30);

		lbl_title = new JLabel("�� �ۼ�");
		panel.add(lbl_title);
		lbl_title.setBounds(810, 90, 90, 30);

		lbl_nametitle = new JLabel("�� ����:");
		panel.add(lbl_nametitle);
		lbl_nametitle.setBounds(810, 50, 90, 30);

		txt_title = new JTextArea();
		panel.add(txt_title);
		txt_title.setBounds(810, 120, 545, 240);

		btn_insert = new JButton("���");
		panel.add(btn_insert);
		btn_insert.setBounds(810, 370, 90, 30);
		btn_insert.addActionListener(this);

		btn_update = new JButton("����");
		panel.add(btn_update);
		btn_update.setBounds(960, 370, 90, 30);
		btn_update.addActionListener(this);

		btn_delete = new JButton("����");
		panel.add(btn_delete);
		btn_delete.setBounds(1110, 370, 90, 30);
		btn_delete.addActionListener(this);

		btn_search = new JButton("�˻�");
		panel.add(btn_search);
		btn_search.setBounds(715, 7, 84, 25);
		btn_search.addActionListener(this);

		btn_refresh = new JButton("��ü����");
		panel.add(btn_refresh);
		btn_refresh.setBounds(1, 7, 90, 25);
		btn_refresh.addActionListener(this);

		btn_exit = new JButton("����");
		panel.add(btn_exit);
		btn_exit.setBounds(1265, 370, 90, 30);
		btn_exit.addActionListener(this);

		panel.setLayout(null);
		add(panel);
		setVisible(true);
	}

	public void SingMemberTitleModel() {

		borderLayout1 = new BorderLayout();
		notice_table = new JTable(Notice_model);

		TitlePanel.setBorder(BorderFactory.createRaisedBevelBorder());
		TitlePanel.setBounds(new Rectangle(0, 40, 800, 370));
		TitlePanel.setLayout(borderLayout1);

		notice_table.setBackground(Color.WHITE);
		notice_table.setBorder(BorderFactory.createEtchedBorder());

		notice_table.getColumnModel().getColumn(0).setPreferredWidth(50); // ��
																			// ��ȣ
		notice_table.getColumnModel().getColumn(1).setPreferredWidth(50); // ��
																			// ����
		notice_table.getColumnModel().getColumn(2).setPreferredWidth(50); // ��
																			// ����
		notice_table.getColumnModel().getColumn(3).setPreferredWidth(50); // ��
																			// ����

		notice_table.getColumnModel().getColumn(4).setPreferredWidth(50); // �������

		notice_table.setShowHorizontalLines(true); // ���� ���� �Ⱥ��̰� ó��
		notice_table.setShowVerticalLines(true); // ���� ���� �Ⱥ��̰� ó��
		notice_table.setSelectionBackground(Color.orange);
		notice_table.setSelectionForeground(Color.blue);
		notice_table.setRowMargin(0);
		notice_table.setIntercellSpacing(new Dimension(0, 0));
		notice_table.setRequestFocusEnabled(false); // Ư�� ���� ��Ŀ�� ���� �ȵǰ� ó��

		notice_table.setSelectionMode(0); // ���ϼ��� :0 , ���߼���:1, �������� ���� -2

		notice_table.getTableHeader().setReorderingAllowed(false); // �������
		// table.getTableHeader().setBackground(new Color(200,200,200));
		// table.getTableHeader().setForeground(new Color(100,100,100));
		notice_table.getTableHeader().setMaximumSize(new Dimension(140, 0));
		notice_table.getTableHeader().setMinimumSize(new Dimension(10, 0));
		// table.getTableHeader().setResizingAllowed(false); //��������Ұ���
		notice_table.setAlignmentX(JTable.CENTER_ALIGNMENT); // ����
		notice_table.setAlignmentY(JTable.CENTER_ALIGNMENT);

		this.notice_table.addMouseListener(this);

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

	public void sqlQuery(String sql) {
		System.out.println("query SQL : " + sql);
		Statement stmt = null;

		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			Notice_model.setNumRows(0); // ������ û��
			while (rs.next()) {
				String data[] = { rs.getString(1), rs.getString(2),
						rs.getString(3), rs.getString(4), rs.getString(5) };
				Notice_model.addRow(data);
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

		if (ob == this.btn_insert) {

			String sql = "insert into noticeboard values(noticeboard_seq.nextval,"
					+ "'"
					+ this.txt_name.getText().trim()
					+ "','"
					+ this.txt_nametitle.getText().trim()
					+ "','"
					+ this.txt_title.getText().trim() + "'," + "sysdate)";
			if (txt_name.equals("") == false) {
				if (sqlUpdate(sql)) {
					JOptionPane.showMessageDialog(this, "�������� ��� ����!",
							"�������� �߰�", JOptionPane.DEFAULT_OPTION);
					sqlQuery("SELECT noticeboard_num,noticeboard_name,noticeboard_nametitle,noticeboard_title,noticeboard_date from noticeboard order by noticeboard_date desc");
				} else {
					JOptionPane.showMessageDialog(this, "�������� ��� ����!",
							"�������� �߰� ����", JOptionPane.DEFAULT_OPTION);
					return;

				}
				txt_name.setText("");
				txt_nametitle.setText("");
				txt_title.setText("");
			}
		}
		if (ob == this.btn_search) {

			String sql = "select * from noticeboard where noticeboard_num like '%"
					+ this.txt_search.getText().trim() + "%'";
			String sql2 = "select * from noticeboard where noticeboard_name like '%"
					+ this.txt_search.getText().trim() + "%'";
			String sql3 = "select * from noticeboard where noticeboard_nametitle like '%"
					+ this.txt_search.getText().trim()
					+ "%'"
					+ " or "
					+ "noticeboard_title like '%"
					+ this.txt_search.getText().trim() + "%'";

			if (txt_search.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "������Ʈ ����� Ȯ���ϼ���^^",
						"������Ʈ ����!", JOptionPane.DEFAULT_OPTION);

			} else {
				if (combo_search.getSelectedItem() == "�� ��ȣ") {
					if (sqlUpdate(sql)) {
						sqlQuery("select * from noticeboard where noticeboard_num like '%"
								+ this.txt_search.getText().trim() + "%'");
					}
				}
				if (combo_search.getSelectedItem() == "�� ����") {
					if (sqlUpdate(sql2)) {
						sqlQuery("select * from noticeboard where noticeboard_name like '%"
								+ this.txt_search.getText().trim() + "%'");
					}
				}
				if (combo_search.getSelectedItem() == "�� ����+����") {
					if (sqlUpdate(sql3)) {
						sqlQuery("select * from noticeboard where noticeboard_nametitle like '%"
								+ this.txt_search.getText().trim()
								+ "%'"
								+ " or "
								+ "noticeboard_title like '%"
								+ this.txt_search.getText().trim() + "%'");
					}
				}
			}

		}

		if (ob == this.btn_delete) {// �������� ����
			int row = notice_table.getSelectedRow();
			if (row < 0) {
				JOptionPane.showMessageDialog(this, "���������� ������ �ּ���!",
						"���� �������� ����", JOptionPane.DEFAULT_OPTION);
				return;
			}
			String insert_num = (String) notice_table.getValueAt(row, 0);
			String sql2 = "DELETE noticeboard WHERE noticeboard_num = '"
					+ insert_num + "'";
			if (txt_name.equals("") == false) {
				if (sqlUpdate(sql2)) {
					JOptionPane.showMessageDialog(this, "�������� ���� ����!",
							"�������� ����", JOptionPane.DEFAULT_OPTION);
					sqlQuery("SELECT noticeboard_num,noticeboard_name,noticeboard_nametitle,noticeboard_title,noticeboard_date from noticeboard order by noticeboard_date desc");
					txt_name.setText("");
					txt_nametitle.setText("");
					txt_title.setText("");
				} else {
					JOptionPane.showMessageDialog(this, "�������� ���� ����!",
							"�������� ���� ����", JOptionPane.DEFAULT_OPTION);

					return;
				}
			}
		}
		if (ob == this.btn_update) {
			int row = notice_table.getSelectedRow();
			// ���̵�
			if (row < 0) { // JTable�� ���õ� ���� ���� ���
				JOptionPane.showMessageDialog(this, "������ ���������� ������ �ּ���!",
						"���� ����", JOptionPane.DEFAULT_OPTION);
				return;
			}
			String key_id = (String) notice_table.getValueAt(row, 0);
			String sql = "UPDATE noticeboard SET " + "noticeboard_name = '"
					+ this.txt_name.getText().trim() + "',"
					+ "noticeboard_nametitle = '"
					+ this.txt_nametitle.getText().trim() + "',"
					+ "noticeboard_title = '" + this.txt_title.getText().trim()
					+ "' where noticeboard_num ='" + key_id + "'";
			if (txt_name.equals("") == false) {
				if (sqlUpdate(sql)) {
					JOptionPane.showMessageDialog(this, "�������� ���� ����!",
							"�������� ���� ����", JOptionPane.DEFAULT_OPTION);
					sqlQuery("SELECT noticeboard_num,noticeboard_name,noticeboard_nametitle,noticeboard_title,noticeboard_date from noticeboard order by noticeboard_date desc");
					txt_name.setText("");
					txt_nametitle.setText("");
					txt_title.setText("");
				} else {
					JOptionPane.showMessageDialog(this, "�������� ���� ����!",
							"�������� ���� ����", JOptionPane.DEFAULT_OPTION);
					return;
				}
			}
		}
		if (e.getSource() == this.btn_refresh) {
			String sql = "select * from noticeboard order by noticeboard_num desc";
			if (sqlUpdate(sql)) {
				sqlQuery("select * from noticeboard order by noticeboard_num desc");
			} else {
				return;
			}
		}

		if (ob == this.btn_exit) {
			setVisible(false);
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
		int row = notice_table.getSelectedRow();

		if (row >= 0) {
			this.txt_name.setText((String) notice_table.getValueAt(row, 1));
			this.txt_nametitle
					.setText((String) notice_table.getValueAt(row, 2));
			this.txt_title.setText((String) notice_table.getValueAt(row, 3));

		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
}
