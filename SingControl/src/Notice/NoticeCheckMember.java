package Notice;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
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
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class NoticeCheckMember extends JFrame implements ActionListener,
		MouseListener, WindowListener {
	String data[][] = new String[0][5];
	String title[] = { "�� ��ȣ", "�� ����", "�� ����", "�� ����", "�����" };

	DefaultTableModel Notice_model = new DefaultTableModel(data, title) { // ����
																			// ��
		// ��
		public boolean isCellEditable(int r, int c) {
			return false;
		}
	};
	JPanel panel;
	JPanel TitlePanel;
	JLabel notice;
	JTable notice_table;
	JComboBox combo_search;// �˻� �޺��ڽ�
	JTextField txt_search;
	JButton btn_search;
	JButton btn_exit;
	JButton btn_refresh;// ��ü ����
	BorderLayout borderLayout1;

	public NoticeCheckMember() {

		setSize(825, 415);
		setTitle("��������");
		JScrollPane sp_Title = new JScrollPane();
		TitlePanel = new JPanel();
		JPanel panel = new JPanel();

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(((screenSize.width) / 2) - 413,
				((screenSize.height) / 2) - 250);

		/*
		 * notice = new JLabel("�˻� : "); panel.add(notice);
		 * notice.setBounds(450,3,90,25);
		 */

		combo_search = new JComboBox();
		panel.add(combo_search);
		combo_search.setBounds(500, 3, 90, 25);

		btn_search = new JButton("�˻�");
		panel.add(btn_search);
		btn_search.setBounds(715, 3, 90, 25);
		btn_search.addActionListener(this);

		btn_refresh = new JButton("��ü����");
		panel.add(btn_refresh);
		btn_refresh.setBounds(1, 3, 90, 25);
		btn_refresh.addActionListener(this);

		btn_exit = new JButton("Ȯ��");
		panel.add(btn_exit);
		btn_exit.setBounds(715, 340, 90, 25);
		btn_exit.addActionListener(this);

		txt_search = new JTextField();
		panel.add(txt_search);
		txt_search.setBounds(595, 3, 115, 26);

		SingMemberTitleModel();

		TitlePanel.add(sp_Title, BorderLayout.CENTER);
		sp_Title.getViewport().add(notice_table, null);
		add(TitlePanel);

		combo_search.addItem("�� ��ȣ");
		combo_search.addItem("�� ����");
		combo_search.addItem("�� ����+����");

		add(panel);
		panel.setLayout(null);
		setVisible(true);
	}

	public void SingMemberTitleModel() {

		borderLayout1 = new BorderLayout();
		notice_table = new JTable(Notice_model);

		TitlePanel.setBorder(BorderFactory.createRaisedBevelBorder());
		TitlePanel.setBounds(new Rectangle(0, 30, 809, 298));
		TitlePanel.setLayout(borderLayout1);

		notice_table.setBackground(Color.lightGray);
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

		if (e.getSource() == btn_exit) {
			this.dispose();
		}

		if (e.getSource() == btn_search) {

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

				JOptionPane.showMessageDialog(this, "������ �ٸ��� �Է��ϼ���!", "�˻� ����!",
						JOptionPane.DEFAULT_OPTION);
			}

			else if (combo_search.getSelectedItem() == "�� ��ȣ") {
				if (sqlUpdate(sql)) {
					sqlQuery("select * from noticeboard where noticeboard_num like '%"
							+ this.txt_search.getText().trim() + "%'");
				}
			}

			else if (combo_search.getSelectedItem() == "�� ����") {
				if (sqlUpdate(sql2)) {
					sqlQuery("select * from noticeboard where noticeboard_name like '%"
							+ this.txt_search.getText().trim() + "%'");
				}

			} else if (combo_search.getSelectedItem() == "�� ����+����") {
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
		if (e.getSource() == this.btn_refresh) {
			String sql = "select * from noticeboard order by noticeboard_date desc";
			if (sqlUpdate(sql)) {
				sqlQuery("select * from noticeboard order by noticeboard_date desc");
			} else {
				return;
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		JTable t = (JTable) e.getSource();

		if (e.getClickCount() == 2) {
			TableModel m = t.getModel();
			Point pt = e.getPoint();
			int i = t.rowAtPoint(pt);
			if (i >= 0) {
				int row = t.convertRowIndexToModel(i);
				String a = String.format("%s", m.getValueAt(row, 0));
				String b = String.format("%s", m.getValueAt(row, 1));
				String c = String.format("%s", m.getValueAt(row, 2));
				String d = String.format("%s", m.getValueAt(row, 3));
				/*
				 * m.getValueAt(row, 1),m.getValueAt(row, 2),m.getValueAt(row,
				 * 3),m.getValueAt(row, 4));
				 */

				NoticeFrame start = new NoticeFrame();
				start.txt_num.setText(a);
				start.txt_name.setText(b);
				start.txt_nametitle.setText(c);
				start.txt_title.setText(d);
			}
		}
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
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		e.getWindow().dispose();
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
