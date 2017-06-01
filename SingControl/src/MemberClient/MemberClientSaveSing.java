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
	String title[] = { "노래 번호", "노래 제목", "가수명", "등록일" };

	DefaultTableModel Sing_model = new DefaultTableModel(data, title) { // 기존 곡
		// 모델
		public boolean isCellEditable(int r, int c) {
			return false;
		}
	};
	JButton btn_search;
	JButton btn_title;
	JButton btn_exit;
	JLabel lbl_year;
	JLabel lbl_month;
	JComboBox years;// 년도 콤보박스
	JComboBox month;// 날짜 콤보박스
	JTable sing_table;// 노래 목록
	JScrollPane sing_scroll;// 노래 스크롤패널
	BorderLayout sing_border;
	JPanel TitlePanel;
	JPanel panel;

	public MemberClientSaveSing() {
		setSize(800, 440);
		setTitle("고객 클라이언트(저장 곡 목록)");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		TitlePanel = new JPanel();
		panel = new JPanel();
		sing_scroll = new JScrollPane();

		lbl_year = new JLabel("년");
		lbl_year.setBounds(580, 25, 70, 20);
		panel.add(lbl_year);

		lbl_month = new JLabel("월");
		lbl_month.setBounds(680, 25, 70, 20);
		panel.add(lbl_month);

		btn_title = new JButton("전체보기");
		btn_title.setBounds(10, 10, 110, 40);
		panel.add(btn_title);
		btn_title.addActionListener(this);

		btn_search = new JButton("검색");
		btn_search.setBounds(700, 25, 70, 20);
		panel.add(btn_search);
		btn_search.addActionListener(this);

		btn_exit = new JButton("종료");
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

		sing_table.getColumnModel().getColumn(0).setPreferredWidth(50); // 아이디
		sing_table.getColumnModel().getColumn(1).setPreferredWidth(50); // 비밀번호
		sing_table.getColumnModel().getColumn(2).setPreferredWidth(50); // 이름
		sing_table.getColumnModel().getColumn(3).setPreferredWidth(50); // 주민번호

		sing_table.setShowHorizontalLines(true); // 수평 라인 안보이게 처리
		sing_table.setShowVerticalLines(true); // 수직 라인 안보이게 처리
		sing_table.setSelectionBackground(Color.orange);
		sing_table.setSelectionForeground(Color.blue);
		sing_table.setRowMargin(0);
		sing_table.setIntercellSpacing(new Dimension(0, 0));
		sing_table.setRequestFocusEnabled(false); // 특정 셀에 포커스 설정 안되게 처리

		sing_table.setSelectionMode(0); // 단일선택 :0 , 다중선택:1, 선택하지 않음 -2

		sing_table.getTableHeader().setReorderingAllowed(false); // 헤더고정
		// table.getTableHeader().setBackground(new Color(200,200,200));
		// table.getTableHeader().setForeground(new Color(100,100,100));
		sing_table.getTableHeader().setMaximumSize(new Dimension(140, 0));
		sing_table.getTableHeader().setMinimumSize(new Dimension(10, 0));
		// table.getTableHeader().setResizingAllowed(false); //헤더조절불가능
		sing_table.setAlignmentX(JTable.CENTER_ALIGNMENT); // 정렬
		sing_table.setAlignmentY(JTable.CENTER_ALIGNMENT);

		/* this.sing_table.addMouseListener(this); */

	}

	Connection conn = null;// DB

	// DB 접속
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
			Sing_model.setNumRows(0); // 깨끗이 청소
			while (rs.next()) {
				String data[] = { rs.getString(1), rs.getString(2),
						rs.getString(3), rs.getString(4), };
				Sing_model.addRow(data);
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
								+ "년도 " + this.month.getSelectedItem()
								+ "월달 신곡 목록입니다!", "월별 신곡 조회",
								JOptionPane.DEFAULT_OPTION);
				sqlQuery("select * from singtitle where sing_date like '%"
						+ month.getSelectedItem() + "%'" + " and "
						+ "sing_date like '%" + years.getSelectedItem() + "%'");
			} else {
				JOptionPane.showMessageDialog(this, "신곡 조회 실패입니다.", "신곡 조회 실패",
						JOptionPane.DEFAULT_OPTION);
				return;
			}
		} else if (ob == this.btn_title) {// 기존 곡 확인

			String sql = "select sing_num,sing_name,sing_singer,sing_date FROM singtitle order by sing_num asc";
			System.out.println("query SQL : " + sql);

			if (sqlUpdate(sql)) {
				sqlQuery("select sing_num,sing_name,sing_singer,sing_date FROM singtitle order by sing_num asc");
			}
		}
	}
}
