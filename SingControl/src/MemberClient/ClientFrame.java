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
	String title[] = { "노래 번호", "노래 제목", "가수명", "등록일" };

	DefaultTableModel Sing_model = new DefaultTableModel(data, title) { // 기존 곡
		// 모델
		public boolean isCellEditable(int r, int c) {
			return false;
		}
	};

	JPanel panel;
	JPanel panel2;
	JPanel panel3;
	JPanel panel4;

	JPanel TitlePanel;
	JLabel lbl_search;// 검색 레이블
	JLabel lbl_year;
	JLabel lbl_months;
	JLabel lbl_membercheck;
	JLabel lbl_membercheck2;
	JButton btn_search;// 검색 버튼
	JButton btn_download;
	JButton btn_exit;// 종료버튼
	JButton btn_notice;// 공지사항
	JButton btn_savesing;// 저장 된 곡 관리.
	JComboBox years;// 년도 콤보박스
	JComboBox month;// 날짜 콤보박스
	JTable sing_table;// 노래 목록
	JScrollPane sing_scroll;// 노래 스크롤패널
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
		titledBorder1 = new TitledBorder("월 별 업데이트 목록");

		setSize(1000, 500);
		setTitle("고객 클라이언트");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		SingMemberTitleModel();
		connProcess();

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(((screenSize.width) / 2) - 500,
				((screenSize.height) / 2) - 250);

		lbl_search = new JLabel("날짜 입력(년 월 단위) : ");
		lbl_search.setBounds(500, 30, 150, 30);
		panel4.add(lbl_search);

		lbl_year = new JLabel("년");
		lbl_year.setBounds(715, 30, 150, 30);
		panel4.add(lbl_year);

		lbl_months = new JLabel("월");
		lbl_months.setBounds(815, 30, 150, 30);
		panel4.add(lbl_months);

		btn_search = new JButton("검색");
		btn_search.setBounds(850, 30, 80, 30);
		panel4.add(btn_search);
		btn_search.addActionListener(this);

		btn_notice = new JButton("공지사항");
		btn_notice.setBounds(50, 30, 100, 30);
		panel4.add(btn_notice);
		btn_notice.addActionListener(this);

		btn_savesing = new JButton("저장 곡 관리");
		btn_savesing.setBounds(160, 30, 100, 30);
		panel4.add(btn_savesing);
		btn_savesing.addActionListener(this);

		btn_download = new JButton("다운로드");
		btn_download.setBounds(750, 410, 90, 30);
		panel4.add(btn_download);
		btn_download.addActionListener(this);

		btn_exit = new JButton("종료");
		btn_exit.setBounds(860, 410, 90, 30);
		panel4.add(btn_exit);
		btn_exit.addActionListener(this);

		lbl_membercheck = new JLabel();
		lbl_membercheck.setBounds(31, 410, 150, 30);
		panel4.add(lbl_membercheck);
		lbl_membercheck.setForeground(Color.BLUE);

		lbl_membercheck2 = new JLabel("님 안녕하세요^^");
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
				count++;
				System.out.println(count);
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

	public void sqlQueryTest(String sql) {
		System.out.println("query SQL : " + sql);
		Statement stmt = null;

		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

		} catch (Exception e) {
			System.out.println("데이타 Select 실패 : " + e.getMessage());
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
				JOptionPane.showMessageDialog(this, years.getSelectedItem()
						+ "년 " + month.getSelectedItem() + "월 신곡 목록입니다!",
						"신곡 목록 확인", JOptionPane.DEFAULT_OPTION);
				sqlQuery("select * from singtitle where sing_date like '%"
						+ month.getSelectedItem() + "%'" + " and "
						+ "sing_date like '%" + years.getSelectedItem() + "%'");
			} else {
				JOptionPane.showMessageDialog(this, years.getSelectedItem()
						+ "년 " + month.getSelectedItem() + "월 신곡 목록입니다!",
						"신곡 목록 확인", JOptionPane.DEFAULT_OPTION);
				return;
			}
		}
		if (ob == this.btn_download) {

			if (Sing_model.getRowCount() == 0) {
				JOptionPane.showMessageDialog(this, "업데이트 목록을 확인하세요^^",
						"업데이트 실패!", JOptionPane.DEFAULT_OPTION);
			} else {

				String str = "update sing_member set member_money ="
						+ count
						* 100
						+ ",member_lastupdate = to_char(sysdate,'yyyy/mm/dd') where member_id = '"
						+ lbl_membercheck.getText() + "'";
				sqlQueryTest(str);
				JOptionPane.showMessageDialog(this, "다운로드 완료되었습니다^^",
						"업데이트 성공!", JOptionPane.DEFAULT_OPTION);
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
