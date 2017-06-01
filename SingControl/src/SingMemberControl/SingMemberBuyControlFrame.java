package SingMemberControl;

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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import excelConnection.excelConnection;

public class SingMemberBuyControlFrame extends JFrame implements
		ActionListener, MouseListener, WindowListener {

	String data[][] = new String[0][9];
	String title[] = { "�� ��ȣ", "�� �̸�", "��ȣ��", "�ּ�", "��ȭ��ȣ", "�� ����", "���� �ݾ�",
			"������Ʈ��¥", "�����" };

	String data2[][] = new String[0][4];
	String title2[] = { "���� ��ȣ", "��ȣ��", "�ݾ�", "��������" };

	DefaultTableModel MemberModel = new DefaultTableModel(data, title) { // ��
																			// ��
																			// ��ü
		// ����
		public boolean isCellEditable(int r, int c) {
			return false;
		}
	};

	DefaultTableModel BuyModel = new DefaultTableModel(data2, title2) { // ���� ��
																		// ��ü ����

		public boolean isCellEditable(int r, int c) {
			return false;
		}
	};

	JPanel MemberPanel;// ���� �� ���̺� �г�
	BorderLayout borderLayout1;// ��ġ������
	JTable Member;// ���� �� ���̺�
	JScrollPane sp_Title;// ���� �� ��ũ��
	JLabel Member_Name;// �� �̸�
	JLabel Member_Store;// ��ȣ��
	JLabel Member_Addr;// ��ȣ �ּ�
	JLabel Member_Phone;// ��ȣ ��ȭ��ȣ
	JLabel Member_Room;// ���� ����
	JTextField txt_member_name;// �� �̸� �ؽ�Ʈ�ʵ�
	JTextField txt_member_store;// ��ȣ�� �ؽ�Ʈ�ʵ�
	JTextField txt_member_addr;// ��ȣ �ּ� �ؽ�Ʈ�ʵ�
	JTextField txt_member_phone;// ��ȣ ��ȭ��ȣ �ؽ�Ʈ�ʵ�
	JTextField txt_member_room;// �� ���� �ؽ�Ʈ�ʵ�
	JButton btn_member_update;// �� ���� ��ư
	JButton btn_member_delete;// �� ���� ��ư
	JButton btn_member_search;// �� �˻� ��ư
	JButton btn_member_refresh;// ���ΰ�ħ
	JButton btn_member_excel;// ������ ������ ����
	JButton btn_memberControl_Exit;// ����
	TitledBorder titledBorder1;// ��Ÿ��Ʋ

	// �� ���� ������Ʈ
	public SingMemberBuyControlFrame() {

		JPanel panel = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		JPanel panel4 = new JPanel();
		MemberPanel = new JPanel();
		JScrollPane sp_Title = new JScrollPane();
		titledBorder1 = new TitledBorder("�� ����");

		setSize(1400, 500);
		setTitle("�� ����");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(((screenSize.width) / 2) - 700,
				((screenSize.height) / 2) - 250);

		MemberModel();
		Member_Name = new JLabel("�� �̸�");
		Member_Name.setBounds(900, 65, 80, 30);
		panel4.add(Member_Name);
		Member_Store = new JLabel("��ȣ��");
		Member_Store.setBounds(900, 130, 80, 30);
		panel4.add(Member_Store);
		Member_Addr = new JLabel("�ּ�");
		Member_Addr.setBounds(900, 200, 80, 30);
		panel4.add(Member_Addr);
		Member_Phone = new JLabel("��ȭ��ȣ");
		Member_Phone.setBounds(900, 270, 80, 30);
		panel4.add(Member_Phone);
		Member_Room = new JLabel("���� ����");
		Member_Room.setBounds(900, 340, 80, 30);
		panel4.add(Member_Room);

		txt_member_name = new JTextField();
		txt_member_name.setBounds(1000, 65, 80, 30);
		panel4.add(txt_member_name);
		txt_member_store = new JTextField();
		txt_member_store.setBounds(1000, 130, 80, 30);
		panel4.add(txt_member_store);
		txt_member_addr = new JTextField();
		txt_member_addr.setBounds(1000, 200, 200, 30);
		panel4.add(txt_member_addr);
		txt_member_phone = new JTextField();
		txt_member_phone.setBounds(1000, 270, 80, 30);
		panel4.add(txt_member_phone);
		txt_member_room = new JTextField();
		txt_member_room.setBounds(1000, 340, 80, 30);
		panel4.add(txt_member_room);

		btn_member_update = new JButton("�� ����");
		btn_member_update.setBounds(1230, 70, 100, 30);
		panel4.add(btn_member_update);
		btn_member_update.addActionListener(this);
		btn_member_delete = new JButton("�� ����");
		btn_member_delete.setBounds(1230, 130, 100, 30);
		panel4.add(btn_member_delete);
		btn_member_delete.addActionListener(this);
		btn_member_search = new JButton("�� �˻�");
		btn_member_search.setBounds(1230, 190, 100, 30);
		panel4.add(btn_member_search);
		btn_member_search.addActionListener(this);
		btn_member_refresh = new JButton("���ΰ�ħ");
		btn_member_refresh.setBounds(1230, 255, 100, 30);
		panel4.add(btn_member_refresh);
		btn_member_refresh.addActionListener(this);
		btn_member_excel = new JButton("(��)������ ����");
		btn_member_excel.setBounds(1230, 320, 130, 30);
		panel4.add(btn_member_excel);
		btn_member_excel.addActionListener(this);

		btn_memberControl_Exit = new JButton("����");
		btn_memberControl_Exit.setBounds(1230, 410, 130, 30);
		panel4.add(btn_memberControl_Exit);
		btn_memberControl_Exit.addActionListener(this);

		MemberPanel.add(sp_Title, BorderLayout.CENTER);
		sp_Title.getViewport().add(Member, null);
		add(MemberPanel);
		// �� ����
		panel3.setBorder(titledBorder1);
		panel3.setBounds(new Rectangle(15, 40, 1355, 355));
		panel3.setOpaque(false);

		// ���� ����
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

	// �� ���̺�
	public void MemberModel() {

		borderLayout1 = new BorderLayout();
		Member = new JTable(MemberModel);

		MemberPanel.setBorder(BorderFactory.createRaisedBevelBorder());
		MemberPanel.setBounds(new Rectangle(40, 70, 840, 298));
		MemberPanel.setLayout(borderLayout1);

		Member.setBackground(Color.WHITE);
		Member.setBorder(BorderFactory.createEtchedBorder());

		Member.getColumnModel().getColumn(0).setPreferredWidth(50); // ���̵�
		Member.getColumnModel().getColumn(1).setPreferredWidth(50); // ��й�ȣ
		Member.getColumnModel().getColumn(2).setPreferredWidth(50); // �̸�
		Member.getColumnModel().getColumn(3).setPreferredWidth(50); // �ֹι�ȣ
		Member.getColumnModel().getColumn(4).setPreferredWidth(50); // ��й�ȣ
		Member.getColumnModel().getColumn(5).setPreferredWidth(50); // �̸�
		Member.getColumnModel().getColumn(6).setPreferredWidth(50); // �ֹι�ȣ
		Member.getColumnModel().getColumn(7).setPreferredWidth(50); // �ֹι�ȣ
		Member.getColumnModel().getColumn(8).setPreferredWidth(50); // �ֹι�ȣ

		Member.setShowHorizontalLines(true); // ���� ���� �Ⱥ��̰� ó��
		Member.setShowVerticalLines(true); // ���� ���� �Ⱥ��̰� ó��
		Member.setSelectionBackground(Color.orange);
		Member.setSelectionForeground(Color.blue);
		Member.setRowMargin(0);
		Member.setIntercellSpacing(new Dimension(0, 0));
		Member.setRequestFocusEnabled(false); // Ư�� ���� ��Ŀ�� ���� �ȵǰ� ó��

		Member.setSelectionMode(0); // ���ϼ��� :0 , ���߼���:1, �������� ���� -2

		Member.getTableHeader().setReorderingAllowed(false); // �������
		// table.getTableHeader().setBackground(new Color(200,200,200));
		// table.getTableHeader().setForeground(new Color(100,100,100));
		Member.getTableHeader().setMaximumSize(new Dimension(140, 0));
		Member.getTableHeader().setMinimumSize(new Dimension(10, 0));
		// table.getTableHeader().setResizingAllowed(false); //��������Ұ���
		Member.setAlignmentX(JTable.CENTER_ALIGNMENT); // ����
		Member.setAlignmentY(JTable.CENTER_ALIGNMENT);

		this.Member.addMouseListener(this);

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
			MemberModel.setNumRows(0); // ������ û��
			while (rs.next()) {
				String data[] = { rs.getString(1), rs.getString(2),
						rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getString(8),
						rs.getString(9) };
				MemberModel.addRow(data);
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

		if (e.getSource() == btn_memberControl_Exit) {
			dispose();
		}

		if (ob == this.btn_member_update) {// �� ����
			int row = Member.getSelectedRow();
			// ���̵�
			if (row < 0) {
				JOptionPane.showMessageDialog(this, "������ ���� ������ �ּ���!",
						"���� ����", JOptionPane.DEFAULT_OPTION);
				return;
			}
			String key_id = (String) MemberModel.getValueAt(row, 0);
			String sql = "UPDATE sing_member SET " + "member_name = '"
					+ this.txt_member_name.getText().trim() + "',"
					+ "member_store = '"
					+ this.txt_member_store.getText().trim() + "',"
					+ "member_addr = '" + this.txt_member_addr.getText().trim()
					+ "'," + "member_phone = '"
					+ this.txt_member_phone.getText().trim() + "',"
					+ "member_room = '" + this.txt_member_room.getText().trim()
					+ "' where member_num ='" + key_id + "'";
			if (txt_member_name.equals("") == false) {
				if (sqlUpdate(sql)) {
					JOptionPane.showMessageDialog(this, "�� ���� ����!",
							"�� ���� ����!", JOptionPane.DEFAULT_OPTION);
					sqlQuery("SELECT member_num,member_name,member_store,member_addr,member_phone,member_room,member_money,member_lastupdate,member_date from sing_member");
					txt_member_name.setText("");
					txt_member_store.setText("");
					txt_member_addr.setText("");
					txt_member_phone.setText("");
					txt_member_room.setText("");
				} else {
					JOptionPane.showMessageDialog(this, "�� ���� ����", "�� ���� ����",
							JOptionPane.DEFAULT_OPTION);
					return;
				}
			}
			txt_member_name.setText("");
			txt_member_store.setText("");
			txt_member_addr.setText("");
			txt_member_phone.setText("");
			txt_member_room.setText("");
		} else if (ob == this.btn_member_delete) {// �� ����
			int row = Member.getSelectedRow();
			if (row < 0) {
				JOptionPane.showMessageDialog(this, "������ ���� ������ �ּ���!",
						"���� ����", JOptionPane.DEFAULT_OPTION);
				return;
			}
			String key_id = (String) MemberModel.getValueAt(row, 0);
			String sql = "DELETE sing_member WHERE member_num = '" + key_id
					+ "'";
			if (txt_member_name.equals("") == false) {
				if (sqlUpdate(sql)) {
					JOptionPane.showMessageDialog(this, key_id + "��"
							+ " ������ ���� ����!", "���� ����",
							JOptionPane.DEFAULT_OPTION);
					sqlQuery("SELECT member_num,member_name,member_money,member_store,member_addr,member_phone,member_room,member_date,member_lastupdate from sing_member");
					txt_member_name.setText("");
					txt_member_store.setText("");
					txt_member_addr.setText("");
					txt_member_phone.setText("");
					txt_member_room.setText("");
				} else {
					JOptionPane.showMessageDialog(this, "������ ���� ����!", "���� ����",
							JOptionPane.DEFAULT_OPTION);
					return;
				}
			}
			txt_member_name.setText("");
			txt_member_store.setText("");
			txt_member_addr.setText("");
			txt_member_phone.setText("");
			txt_member_room.setText("");
		} else if (ob == this.btn_member_search) {
			if (txt_member_name.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "�˻� ������ Ȯ���ϼ���^^",
						"�˻� ������ Ȯ���ϼ���^^", JOptionPane.DEFAULT_OPTION);
			} else {
				String sql = "select * from sing_member where member_name like '%"
						+ this.txt_member_name.getText().trim()
						+ "%'"
						+ " and "
						+ "member_store like '%"
						+ this.txt_member_store.getText().trim()
						+ "%'"
						+ " and "
						+ "member_addr like '%"
						+ this.txt_member_addr.getText().trim()
						+ "%'"
						+ " and "
						+ "member_phone like '%"
						+ this.txt_member_phone.getText().trim()
						+ "%'"
						+ " and "
						+ "member_room like '%"
						+ this.txt_member_room.getText().trim() + "%'";
				if (sqlUpdate(sql)) {
					JOptionPane.showMessageDialog(this, "������ ��ȸ �����߾��!",
							"������ ��ȸ ����", JOptionPane.DEFAULT_OPTION);
					sqlQuery("select member_num,member_name,member_store,member_addr,member_phone,member_room,member_money,member_lastupdate,member_date from sing_member where member_name like '%"
							+ this.txt_member_name.getText().trim()
							+ "%'"
							+ " and "
							+ "member_store like '%"
							+ this.txt_member_store.getText().trim()
							+ "%'"
							+ " and "
							+ "member_addr like '%"
							+ this.txt_member_addr.getText().trim()
							+ "%'"
							+ " and "
							+ "member_phone like '%"
							+ this.txt_member_phone.getText().trim()
							+ "%'"
							+ " and "
							+ "member_room like '%"
							+ this.txt_member_room.getText().trim() + "%'");
					txt_member_name.setText("");
					txt_member_store.setText("");
					txt_member_addr.setText("");
					txt_member_phone.setText("");
					txt_member_room.setText("");
				}

			}
		} else if (ob == this.btn_member_refresh) {// ������ ���ΰ�ħ
			String sql = "SELECT member_num,member_name,member_store,member_addr,member_phone,member_room,member_money,member_lastupdate,member_date from sing_member order by 1 asc";
			System.out.println("query SQL : " + sql);

			if (sqlUpdate(sql)) {
				sqlQuery("SELECT member_num,member_name,member_store,member_addr,member_phone,member_room,member_money,member_lastupdate,member_date from sing_member order by 1 asc");
				txt_member_name.setText("");
				txt_member_store.setText("");
				txt_member_addr.setText("");
				txt_member_phone.setText("");
				txt_member_room.setText("");
			}

		} else if (e.getSource() == btn_member_excel) {
			excelConnection memberExcel = new excelConnection(title, Member,
					MemberModel);
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
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

		int row = Member.getSelectedRow();
		// �� ���� Ŭ��
		if (row >= 0) {
			this.txt_member_name.setText((String) MemberModel
					.getValueAt(row, 1));
			this.txt_member_store.setText((String) MemberModel.getValueAt(row,
					2));
			this.txt_member_addr.setText((String) MemberModel
					.getValueAt(row, 3));
			this.txt_member_phone.setText((String) MemberModel.getValueAt(row,
					4));
			this.txt_member_room.setText((String) MemberModel
					.getValueAt(row, 5));
		}

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
