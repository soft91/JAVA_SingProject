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
	String title[] = { "글 번호", "글 쓴이", "글 제목", "글 내용", "등록일" };
	DefaultTableModel Notice_model = new DefaultTableModel(data, title) { // 기존
		// 곡
		// 모델
		public boolean isCellEditable(int r, int c) {
			return false;
		}
	};

	JLabel lbl_name;// 글 쓴이 레이블
	JLabel lbl_nametitle;// 글 제목
	JTextField txt_name;// 글 쓴이 텍스트 필드
	JTextField txt_nametitle;// 글 제목 텍스트 필드
	JTextField txt_search;
	JLabel lbl_title;// 글 내용 레이블
	JTextArea txt_title;// 글 내용
	JButton btn_search;
	JButton btn_insert;// 글 추가
	JButton btn_delete;// 글 삭제
	JButton btn_update;// 글 수정
	JButton btn_refresh;// 전체 보기
	JButton btn_exit;// 종료
	JScrollPane scroll_title;
	JTable notice_table;
	JComboBox combo_search;
	JPanel TitlePanel;
	BorderLayout borderLayout1;

	public NoticeCompanyControlFrame() {

		setSize(1400, 450);
		setTitle("공지사항 관리");

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

		combo_search.addItem("글 번호");
		combo_search.addItem("글 쓴이");
		combo_search.addItem("글 제목+내용");

		txt_search = new JTextField();
		panel.add(txt_search);
		txt_search.setBounds(595, 7, 115, 26);

		lbl_name = new JLabel("글 쓴이 : ");
		panel.add(lbl_name);
		lbl_name.setBounds(810, 10, 90, 30);

		txt_name = new JTextField();
		panel.add(txt_name);
		txt_name.setBounds(870, 10, 90, 30);

		txt_nametitle = new JTextField();
		panel.add(txt_nametitle);
		txt_nametitle.setBounds(870, 50, 300, 30);

		lbl_title = new JLabel("글 작성");
		panel.add(lbl_title);
		lbl_title.setBounds(810, 90, 90, 30);

		lbl_nametitle = new JLabel("글 제목:");
		panel.add(lbl_nametitle);
		lbl_nametitle.setBounds(810, 50, 90, 30);

		txt_title = new JTextArea();
		panel.add(txt_title);
		txt_title.setBounds(810, 120, 545, 240);

		btn_insert = new JButton("등록");
		panel.add(btn_insert);
		btn_insert.setBounds(810, 370, 90, 30);
		btn_insert.addActionListener(this);

		btn_update = new JButton("수정");
		panel.add(btn_update);
		btn_update.setBounds(960, 370, 90, 30);
		btn_update.addActionListener(this);

		btn_delete = new JButton("삭제");
		panel.add(btn_delete);
		btn_delete.setBounds(1110, 370, 90, 30);
		btn_delete.addActionListener(this);

		btn_search = new JButton("검색");
		panel.add(btn_search);
		btn_search.setBounds(715, 7, 84, 25);
		btn_search.addActionListener(this);

		btn_refresh = new JButton("전체보기");
		panel.add(btn_refresh);
		btn_refresh.setBounds(1, 7, 90, 25);
		btn_refresh.addActionListener(this);

		btn_exit = new JButton("종료");
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

		notice_table.getColumnModel().getColumn(0).setPreferredWidth(50); // 글
																			// 번호
		notice_table.getColumnModel().getColumn(1).setPreferredWidth(50); // 글
																			// 쓴이
		notice_table.getColumnModel().getColumn(2).setPreferredWidth(50); // 글
																			// 제목
		notice_table.getColumnModel().getColumn(3).setPreferredWidth(50); // 글
																			// 내용

		notice_table.getColumnModel().getColumn(4).setPreferredWidth(50); // 등록일자

		notice_table.setShowHorizontalLines(true); // 수평 라인 안보이게 처리
		notice_table.setShowVerticalLines(true); // 수직 라인 안보이게 처리
		notice_table.setSelectionBackground(Color.orange);
		notice_table.setSelectionForeground(Color.blue);
		notice_table.setRowMargin(0);
		notice_table.setIntercellSpacing(new Dimension(0, 0));
		notice_table.setRequestFocusEnabled(false); // 특정 셀에 포커스 설정 안되게 처리

		notice_table.setSelectionMode(0); // 단일선택 :0 , 다중선택:1, 선택하지 않음 -2

		notice_table.getTableHeader().setReorderingAllowed(false); // 헤더고정
		// table.getTableHeader().setBackground(new Color(200,200,200));
		// table.getTableHeader().setForeground(new Color(100,100,100));
		notice_table.getTableHeader().setMaximumSize(new Dimension(140, 0));
		notice_table.getTableHeader().setMinimumSize(new Dimension(10, 0));
		// table.getTableHeader().setResizingAllowed(false); //헤더조절불가능
		notice_table.setAlignmentX(JTable.CENTER_ALIGNMENT); // 정렬
		notice_table.setAlignmentY(JTable.CENTER_ALIGNMENT);

		this.notice_table.addMouseListener(this);

	}

	Connection conn = null;// DB

	public boolean connProcess() {
		String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
		String user = "scott";
		String passwd = "tiger";
		System.out.println("데이터베이스와 연결중......");
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, user, passwd);
		} catch (ClassNotFoundException e1) {
			System.out.println("연결실패 : 드라이버 로딩 실패");
			return false;
		} catch (SQLException e2) {
			System.out.println("연결실패 : url,user,passwd 정보가 올바르지 않음");
			return false;
		}
		System.out.println("연결 성공");
		return true;
	}

	// 데이타베이스와 접속 끊기
	public void disconnProcess() {
		try {
			if (this.conn != null) {
				this.conn.close();
			}
		} catch (Exception e) {
		}

		JOptionPane.showMessageDialog(this, "서버와 연결을 끊었습니다", "끊기 성공",
				JOptionPane.DEFAULT_OPTION);
		return;
	}

	public void sqlQuery(String sql) {
		System.out.println("query SQL : " + sql);
		Statement stmt = null;

		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			Notice_model.setNumRows(0); // 깨끗이 청소
			while (rs.next()) {
				String data[] = { rs.getString(1), rs.getString(2),
						rs.getString(3), rs.getString(4), rs.getString(5) };
				Notice_model.addRow(data);
			}
		} catch (Exception e) {
			System.out.println("데이타 Select 실패 : " + e.getMessage());
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
			System.out.println(i + "개의 데이타 업데이트 성공 ");
			return true;
		} catch (Exception e) {
			System.out.println("데이타 업데이트 실패 : " + e.getMessage());
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
					JOptionPane.showMessageDialog(this, "공지사항 등록 성공!",
							"공지사항 추가", JOptionPane.DEFAULT_OPTION);
					sqlQuery("SELECT noticeboard_num,noticeboard_name,noticeboard_nametitle,noticeboard_title,noticeboard_date from noticeboard order by noticeboard_date desc");
				} else {
					JOptionPane.showMessageDialog(this, "공지사항 등록 실패!",
							"공지사항 추가 실패", JOptionPane.DEFAULT_OPTION);
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
				JOptionPane.showMessageDialog(this, "업데이트 목록을 확인하세요^^",
						"업데이트 실패!", JOptionPane.DEFAULT_OPTION);

			} else {
				if (combo_search.getSelectedItem() == "글 번호") {
					if (sqlUpdate(sql)) {
						sqlQuery("select * from noticeboard where noticeboard_num like '%"
								+ this.txt_search.getText().trim() + "%'");
					}
				}
				if (combo_search.getSelectedItem() == "글 쓴이") {
					if (sqlUpdate(sql2)) {
						sqlQuery("select * from noticeboard where noticeboard_name like '%"
								+ this.txt_search.getText().trim() + "%'");
					}
				}
				if (combo_search.getSelectedItem() == "글 제목+내용") {
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

		if (ob == this.btn_delete) {// 공지사항 삭제
			int row = notice_table.getSelectedRow();
			if (row < 0) {
				JOptionPane.showMessageDialog(this, "공지사항을 선택해 주세요!",
						"선택 공지사항 오류", JOptionPane.DEFAULT_OPTION);
				return;
			}
			String insert_num = (String) notice_table.getValueAt(row, 0);
			String sql2 = "DELETE noticeboard WHERE noticeboard_num = '"
					+ insert_num + "'";
			if (txt_name.equals("") == false) {
				if (sqlUpdate(sql2)) {
					JOptionPane.showMessageDialog(this, "공지사항 삭제 성공!",
							"공지사항 삭제", JOptionPane.DEFAULT_OPTION);
					sqlQuery("SELECT noticeboard_num,noticeboard_name,noticeboard_nametitle,noticeboard_title,noticeboard_date from noticeboard order by noticeboard_date desc");
					txt_name.setText("");
					txt_nametitle.setText("");
					txt_title.setText("");
				} else {
					JOptionPane.showMessageDialog(this, "공지사항 삭제 실패!",
							"공지사항 삭제 실패", JOptionPane.DEFAULT_OPTION);

					return;
				}
			}
		}
		if (ob == this.btn_update) {
			int row = notice_table.getSelectedRow();
			// 아이디
			if (row < 0) { // JTable에 선택된 행이 없는 경우
				JOptionPane.showMessageDialog(this, "수정할 공지사항을 선택해 주세요!",
						"선택 오류", JOptionPane.DEFAULT_OPTION);
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
					JOptionPane.showMessageDialog(this, "공지사항 수정 성공!",
							"공지사항 수정 성공", JOptionPane.DEFAULT_OPTION);
					sqlQuery("SELECT noticeboard_num,noticeboard_name,noticeboard_nametitle,noticeboard_title,noticeboard_date from noticeboard order by noticeboard_date desc");
					txt_name.setText("");
					txt_nametitle.setText("");
					txt_title.setText("");
				} else {
					JOptionPane.showMessageDialog(this, "공지사항 수정 실패!",
							"공지사항 수정 실패", JOptionPane.DEFAULT_OPTION);
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
