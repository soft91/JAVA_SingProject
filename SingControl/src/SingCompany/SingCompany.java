package SingCompany;

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
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import excelConnection.excelConnection;

public class SingCompany extends JFrame implements ActionListener,
		MouseListener, WindowListener {

	String data[][] = new String[0][3];
	String title[] = { "노래 번호", "노래 제목", "가수명", "등록일" };

	String data1[][] = new String[0][3];
	String title1[] = { "노래 번호", "노래 제목", "가수명", "등록일" };

	DefaultTableModel Sing_model = new DefaultTableModel(data, title) { // 기존 곡
																		// 모델
		public boolean isCellEditable(int r, int c) {
			return false;
		}
	};

	DefaultTableModel Sing_Model_Update = new DefaultTableModel(data1, title1) { // 신곡
																					// 모델
		public boolean isCellEditable(int r, int c) {
			return false;
		}
	};

	JPanel TitlePanel;// 기존 곡 테이블 패널
	JPanel UpdatePanel;// 업데이트 곡 테이블 패널
	JPanel panel3;
	BorderLayout borderLayout1;// 배치관리자
	BorderLayout borderLayout2;// 배치관리자
	JTable SingTableTitle;// 기존 곡 테이블
	JTable SingTableUpdate;// 업데이트 곡 테이블
	JScrollPane sp_Title;// 기존 곡 스크롤
	JScrollPane sp_Update;// 업데이트 곡 스크롤
	JButton btn_Sing_Search;// 곡 검색
	JButton btn_Sing_Delete;// 곡 삭제
	JButton btn_Sing_Update;// 곡 수정
	JButton btn_Sing_Refresh;// 목록 새로고침
	JButton btn_Update_Insert;// 신곡 등록
	JButton btn_Update_Update;// 신곡 수정
	JButton btn_Update_Search;// 신곡 검색
	JButton btn_Update_Delete;// 신곡 삭제
	JButton btn_Update_UpdateInsert;// 신곡 업데이트
	JButton btn_excel;// 엑셀 저장
	JButton btn_Company_exit;// 종료
	JComboBox years;// 년도 콤보박스
	JComboBox month;// 날짜 콤보박스

	JLabel lbl_SingName;// 곡 명 레이블
	JLabel lbl_Singer;// 가수명 레이블
	JLabel lbl_date;// 가수명 레이블
	JLabel lbl_year;// 가수명 레이블
	JLabel lbl_month;// 가수명 레이블

	JTextField txt_SingName;// 곡 명 텍스트필드
	JTextField txt_Singer;// 가수명 텍스트필드
	TitledBorder titledBorder1;// 기존곡 타이틀
	TitledBorder titledBorder2;// 신곡 타이틀
	TitledBorder titledBorder3;// 곡 정보 입력 타이틀
	TitledBorder titledBorder4;// 날짜별 조건 타이틀

	SingCompany frame1;

	// 레이블, 버튼, 테이블 컴포넌트
	public SingCompany() {
		JPanel panel = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		JPanel panel4 = new JPanel();

		TitlePanel = new JPanel();
		UpdatePanel = new JPanel();
		titledBorder1 = new TitledBorder("신곡 및 기존곡 목록");
		titledBorder2 = new TitledBorder("신곡 입력");
		titledBorder3 = new TitledBorder("곡 정보");
		titledBorder3 = new TitledBorder("등록 및 조회(날짜포함 등록 불가)");
		JScrollPane sp_Title = new JScrollPane();
		JScrollPane sp_Update = new JScrollPane();

		SingMemberTitleModel();
		SingMemberUpdateModel();

		setSize(1600, 450);
		setTitle("곡 관리 프로그램(회사용)");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(((screenSize.width) / 2) - 800,
				((screenSize.height) / 2) - 225);

		// 테이블
		TitlePanel.add(sp_Title, BorderLayout.CENTER);
		sp_Title.getViewport().add(SingTableTitle, null);
		add(TitlePanel);
		UpdatePanel.add(sp_Update, BorderLayout.CENTER);
		sp_Update.getViewport().add(SingTableUpdate, null);
		add(UpdatePanel);

		// 레이블
		lbl_SingName = new JLabel("곡 제목 :");
		panel4.add(lbl_SingName);
		lbl_SingName.setBounds(670, 110, 90, 30);

		lbl_Singer = new JLabel("가수명 :");
		panel4.add(lbl_Singer);
		lbl_Singer.setBounds(670, 160, 90, 30);

		lbl_date = new JLabel("날짜(년,월) : ");
		panel4.add(lbl_date);
		lbl_date.setBounds(670, 210, 90, 30);

		lbl_year = new JLabel("년");
		panel4.add(lbl_year);
		lbl_year.setBounds(805, 210, 90, 30);

		lbl_month = new JLabel("월");
		panel4.add(lbl_month);
		lbl_month.setBounds(880, 210, 90, 30);

		// 텍스트필드
		txt_SingName = new JTextField();
		panel4.add(txt_SingName);
		txt_SingName.setBounds(750, 110, 150, 30);

		txt_Singer = new JTextField();
		panel4.add(txt_Singer);
		txt_Singer.setBounds(750, 160, 150, 30);

		// 버튼
		btn_Sing_Search = new JButton("곡 검색");
		panel4.add(btn_Sing_Search);
		btn_Sing_Search.setBounds(540, 80, 90, 30);
		btn_Sing_Search.addActionListener(this);

		btn_Sing_Delete = new JButton("곡 삭제");
		panel4.add(btn_Sing_Delete);
		btn_Sing_Delete.setBounds(540, 150, 90, 30);
		btn_Sing_Delete.addActionListener(this);

		btn_Sing_Update = new JButton("곡 수정");
		panel4.add(btn_Sing_Update);
		btn_Sing_Update.setBounds(540, 230, 90, 30);
		btn_Sing_Update.addActionListener(this);

		btn_Sing_Refresh = new JButton("새로고침");
		panel4.add(btn_Sing_Refresh);
		btn_Sing_Refresh.setBounds(540, 308, 90, 30);
		btn_Sing_Refresh.addActionListener(this);

		btn_Update_Insert = new JButton("신곡 등록");
		panel4.add(btn_Update_Insert);
		btn_Update_Insert.setBounds(950, 80, 90, 30);
		btn_Update_Insert.addActionListener(this);

		btn_Update_Update = new JButton("신곡 수정");
		panel4.add(btn_Update_Update);
		btn_Update_Update.setBounds(950, 135, 90, 30);
		btn_Update_Update.addActionListener(this);

		btn_Update_Search = new JButton("신곡 검색");
		panel4.add(btn_Update_Search);
		btn_Update_Search.setBounds(950, 193, 90, 30);
		btn_Update_Search.addActionListener(this);

		btn_Update_Delete = new JButton("신곡 삭제");
		panel4.add(btn_Update_Delete);
		btn_Update_Delete.setBounds(950, 250, 90, 30);
		btn_Update_Delete.addActionListener(this);

		btn_Update_UpdateInsert = new JButton("업데이트");
		panel4.add(btn_Update_UpdateInsert);
		btn_Update_UpdateInsert.setBounds(950, 308, 90, 30);
		btn_Update_UpdateInsert.addActionListener(this);

		btn_excel = new JButton("엑셀로 저장");
		panel4.add(btn_excel);
		btn_excel.setBounds(1330, 360, 110, 30);
		btn_excel.addActionListener(this);

		btn_Company_exit = new JButton("종료");
		panel4.add(btn_Company_exit);
		btn_Company_exit.setBounds(1460, 360, 90, 30);
		btn_Company_exit.addActionListener(this);

		years = new JComboBox();
		panel4.add(years);
		years.setBounds(750, 210, 50, 30);
		years.addActionListener(this);

		month = new JComboBox();
		panel4.add(month);
		month.setBounds(825, 210, 50, 30);
		month.addActionListener(this);

		panel3.setBorder(titledBorder1);
		panel3.setBounds(new Rectangle(15, 13, 630, 340));
		panel3.setOpaque(false);
		panel2.setBorder(titledBorder2);
		panel2.setBounds(new Rectangle(930, 13, 633, 340));
		panel2.setOpaque(false);
		panel.setBorder(titledBorder3);
		panel.setBounds(new Rectangle(660, 80, 250, 180));
		panel.setOpaque(false);

		years.addItem("선택");
		month.addItem("선택");

		for (int j = 1; j < 13; j++) {
			month.addItem(j);
		}

		for (int i = 2014; i > 1990; i--) {
			years.addItem(i);
		}

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

	// 테이블 컨포넌트
	public void SingMemberTitleModel() {

		borderLayout1 = new BorderLayout();
		SingTableTitle = new JTable(Sing_model);

		TitlePanel.setBorder(BorderFactory.createRaisedBevelBorder());
		TitlePanel.setBounds(new Rectangle(25, 40, 500, 298));
		TitlePanel.setLayout(borderLayout1);

		SingTableTitle.setBackground(Color.WHITE);
		SingTableTitle.setBorder(BorderFactory.createEtchedBorder());

		SingTableTitle.getColumnModel().getColumn(0).setPreferredWidth(50); // 아이디
		SingTableTitle.getColumnModel().getColumn(1).setPreferredWidth(50); // 비밀번호
		SingTableTitle.getColumnModel().getColumn(2).setPreferredWidth(50); // 이름
		SingTableTitle.getColumnModel().getColumn(3).setPreferredWidth(50); // 주민번호

		SingTableTitle.setShowHorizontalLines(true); // 수평 라인 안보이게 처리
		SingTableTitle.setShowVerticalLines(true); // 수직 라인 안보이게 처리
		SingTableTitle.setSelectionBackground(Color.orange);
		SingTableTitle.setSelectionForeground(Color.blue);
		SingTableTitle.setRowMargin(0);
		SingTableTitle.setIntercellSpacing(new Dimension(0, 0));
		SingTableTitle.setRequestFocusEnabled(false); // 특정 셀에 포커스 설정 안되게 처리

		SingTableTitle.setSelectionMode(0); // 단일선택 :0 , 다중선택:1, 선택하지 않음 -2

		SingTableTitle.getTableHeader().setReorderingAllowed(false); // 헤더고정
		// table.getTableHeader().setBackground(new Color(200,200,200));
		// table.getTableHeader().setForeground(new Color(100,100,100));
		SingTableTitle.getTableHeader().setMaximumSize(new Dimension(140, 0));
		SingTableTitle.getTableHeader().setMinimumSize(new Dimension(10, 0));
		// table.getTableHeader().setResizingAllowed(false); //헤더조절불가능
		SingTableTitle.setAlignmentX(JTable.CENTER_ALIGNMENT); // 정렬
		SingTableTitle.setAlignmentY(JTable.CENTER_ALIGNMENT);

		this.SingTableTitle.addMouseListener(this);

	}

	// 신곡 테이블
	public void SingMemberUpdateModel() {

		borderLayout2 = new BorderLayout();
		SingTableUpdate = new JTable(Sing_Model_Update);

		UpdatePanel.setBorder(BorderFactory.createRaisedBevelBorder());
		UpdatePanel.setBounds(new Rectangle(1050, 40, 500, 298));
		UpdatePanel.setLayout(borderLayout2);

		SingTableUpdate.setBackground(Color.WHITE);
		SingTableUpdate.setBorder(BorderFactory.createEtchedBorder());

		SingTableUpdate.getColumnModel().getColumn(0).setPreferredWidth(50); // 아이디
		SingTableUpdate.getColumnModel().getColumn(1).setPreferredWidth(50); // 비밀번호
		SingTableUpdate.getColumnModel().getColumn(2).setPreferredWidth(50); // 이름
		SingTableUpdate.getColumnModel().getColumn(3).setPreferredWidth(50); // 주민번호

		SingTableUpdate.setShowHorizontalLines(true); // 수평 라인 안보이게 처리
		SingTableUpdate.setShowVerticalLines(true); // 수직 라인 안보이게 처리
		SingTableUpdate.setSelectionBackground(Color.orange);
		SingTableUpdate.setSelectionForeground(Color.blue);
		SingTableUpdate.setRowMargin(0);
		SingTableUpdate.setIntercellSpacing(new Dimension(0, 0));
		SingTableUpdate.setRequestFocusEnabled(false); // 특정 셀에 포커스 설정 안되게 처리

		SingTableUpdate.setSelectionMode(0); // 단일선택 :0 , 다중선택:1, 선택하지 않음 -2

		SingTableUpdate.getTableHeader().setReorderingAllowed(false); // 헤더고정
		// table.getTableHeader().setBackground(new Color(200,200,200));
		// table.getTableHeader().setForeground(new Color(100,100,100));
		SingTableUpdate.getTableHeader().setMaximumSize(new Dimension(140, 0));
		SingTableUpdate.getTableHeader().setMinimumSize(new Dimension(10, 0));
		// table.getTableHeader().setResizingAllowed(false); //헤더조절불가능
		SingTableUpdate.setAlignmentX(JTable.CENTER_ALIGNMENT); // 정렬
		SingTableUpdate.setAlignmentY(JTable.CENTER_ALIGNMENT);

		this.SingTableUpdate.addMouseListener(this);

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

	public void sqlQuery(String sql2) {
		System.out.println("query SQL : " + sql2);
		Statement stmt = null;

		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql2);
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

	public void sqlQuery2(String sql) {
		System.out.println("query SQL : " + sql);
		Statement stmt = null;

		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			Sing_Model_Update.setNumRows(0); // 깨끗이 청소
			while (rs.next()) {
				String data[] = { rs.getString(1), rs.getString(2),
						rs.getString(3), rs.getString(4), };
				Sing_Model_Update.addRow(data);
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

	public boolean sqlUpdate2(String sql2) {
		System.out.println("update SQL : " + sql2);
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			int i = stmt.executeUpdate(sql2);
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

		if (e.getSource() == btn_Company_exit) {
			this.dispose();
		}

		Object ob = e.getSource();

		if (ob == this.btn_Update_Insert) {// 신곡 데이타 추가
			System.out.println("추가 버튼 클릭!");
			String sql = "insert into sing_insert values(sing_seq.nextval"
					+ ",'" + this.txt_SingName.getText().trim() + "','"
					+ this.txt_Singer.getText().trim() + "',"
					+ "to_char(sysdate,'yyyy/mm/dd'))";
			if (txt_SingName.equals("") == false) {
				if (sqlUpdate(sql)) {
					JOptionPane.showMessageDialog(this, "신곡 추가 성공!", "신곡 추가",
							JOptionPane.DEFAULT_OPTION);
					sqlQuery2("SELECT insert_num,insert_name,insert_singer,insert_date FROM sing_insert order by insert_num asc");
				} else {
					JOptionPane.showMessageDialog(this, "신곡 추가 실패!",
							"신곡 추가 실패", JOptionPane.DEFAULT_OPTION);
					return;
				}
			}
			txt_SingName.setText("");
			txt_Singer.setText("");
		} else if (ob == this.btn_Update_Delete) {// 신곡 삭제버튼
			int row = SingTableUpdate.getSelectedRow();
			if (row < 0) {
				JOptionPane.showMessageDialog(this, "삭제할 곡을 선택해 주세요!",
						"선택 곡 오류", JOptionPane.DEFAULT_OPTION);
				return;
			}
			String insert_num = (String) Sing_Model_Update.getValueAt(row, 0);
			String sql = "DELETE sing_insert WHERE insert_num = '" + insert_num
					+ "'";
			if (sqlUpdate(sql)) {
				JOptionPane.showMessageDialog(this, "신곡 삭제 성공!", "곡 삭제",
						JOptionPane.DEFAULT_OPTION);
				sqlQuery2("SELECT insert_num,insert_name,insert_singer,insert_date FROM sing_insert order by insert_num asc");
				txt_SingName.setText("");
				txt_Singer.setText("");
			} else {
				JOptionPane.showMessageDialog(this, "신곡 삭제 실패!", "곡 삭제 실패",
						JOptionPane.DEFAULT_OPTION);
				return;
			}

		} else if (ob == this.btn_Sing_Delete) {// 기존 곡 삭제
			int row = SingTableTitle.getSelectedRow(); // 선택된 행 얻기
			if (row < 0) { // JTable에 선택된 행이 없는 경우
				JOptionPane.showMessageDialog(this, "삭제할 곡을 선택해 주세요!",
						"선택 곡 오류", JOptionPane.DEFAULT_OPTION);
				return;
			}
			String key_id = (String) Sing_model.getValueAt(row, 0);
			String sql = "DELETE singtitle WHERE sing_num = '" + key_id + "'";
			if (sqlUpdate(sql)) {
				JOptionPane.showMessageDialog(this, "기존곡 삭제 성공!", "곡 삭제",
						JOptionPane.DEFAULT_OPTION);
				sqlQuery("SELECT sing_num,sing_name,sing_singer,sing_date FROM singtitle order by sing_num asc");
				txt_SingName.setText("");
				txt_Singer.setText("");
			} else {
				JOptionPane.showMessageDialog(this, "기존곡 삭제 실패!", "기존곡 삭제 실패",
						JOptionPane.DEFAULT_OPTION);
				return;
			}
		} else if (ob == this.btn_Update_Search) {// 신곡 검색
			if (txt_SingName.getText().equals("")
					&& txt_Singer.getText().equals("") && years.equals("선택")
					&& month.equals("선택")) {
				JOptionPane.showMessageDialog(this, "곡 조회 실패!", "곡 조회 실패",
						JOptionPane.DEFAULT_OPTION);
			} else {
				String sql = "select * from sing_insert where insert_name like '%"
						+ this.txt_SingName.getText().trim()
						+ "%'"
						+ " and "
						+ "insert_singer like '%"
						+ this.txt_Singer.getText().trim()
						+ "%' or "
						+ "insert_date like '%"
						+ years.getSelectedItem()
						+ "%'"
						+ " and "
						+ "insert_date like '%"
						+ month.getSelectedItem()
						+ "%' order by insert_num asc";

				if (sqlUpdate(sql)) {
					JOptionPane.showMessageDialog(this, "조회 성공!", "곡 조회 성공",
							JOptionPane.DEFAULT_OPTION);
					sqlQuery2("select * from sing_insert where insert_name like '%"
							+ this.txt_SingName.getText().trim()
							+ "%'"
							+ " and "
							+ "insert_singer like '%"
							+ this.txt_Singer.getText().trim()
							+ "%' or insert_date like '%"
							+ years.getSelectedItem()
							+ "%'"
							+ " and "
							+ "insert_date like '%"
							+ month.getSelectedItem()
							+ "%' order by insert_num asc");

					txt_SingName.setText("");
					txt_Singer.setText("");
				}
			}
		}

		else if (ob == this.btn_Sing_Refresh) {// 업데이트 확인

			String sql2 = "select sing_num,sing_name,sing_singer,sing_date FROM singtitle order by sing_date desc";
			System.out.println("query SQL : " + sql2);

			if (sqlUpdate(sql2)) {
				sqlQuery("select sing_num,sing_name,sing_singer,sing_date FROM singtitle order by sing_date desc");
				txt_SingName.setText("");
				txt_Singer.setText("");
			}

			Sing_Model_Update.setNumRows(0); // 깨끗이 청소

			txt_SingName.setText("");
			txt_Singer.setText("");

		} else if (ob == this.btn_Update_Update) {// 신곡 수정 버튼
			int row = SingTableUpdate.getSelectedRow();
			// 아이디
			if (row < 0) { // JTable에 선택된 행이 없는 경우
				JOptionPane.showMessageDialog(this, "수정할 신곡을 선택해 주세요!",
						"선택 오류", JOptionPane.DEFAULT_OPTION);
				return;
			}
			String key_id = (String) Sing_Model_Update.getValueAt(row, 0);
			String sql = "UPDATE sing_insert SET " + "insert_name = '"
					+ this.txt_SingName.getText().trim() + "',"
					+ "insert_singer = '" + this.txt_Singer.getText().trim()
					+ "'" + " where insert_num ='" + key_id + "'";
			if (txt_SingName.equals("") == false) {
				if (sqlUpdate(sql)) {
					JOptionPane.showMessageDialog(this, "신곡 수정 성공!",
							"신곡 수정 성공", JOptionPane.DEFAULT_OPTION);
					sqlQuery2("SELECT insert_num,insert_name,insert_singer,insert_date FROM sing_insert order by insert_num asc");
					txt_SingName.setText("");
					txt_Singer.setText("");
				} else {
					JOptionPane.showMessageDialog(this, "신곡 수정 실패!",
							"신곡 수정 실패", JOptionPane.DEFAULT_OPTION);
					return;
				}
			}
		} else if (ob == this.btn_Sing_Search) {// 기존 곡 검색
			if (txt_SingName.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "곡 조회 실패!", "곡 조회 실패",
						JOptionPane.DEFAULT_OPTION);
			}

			else {
				String sql = "select * from singtitle where sing_name like '%"
						+ this.txt_SingName.getText().trim() + "%'" + " and "
						+ "sing_singer like '%"
						+ this.txt_Singer.getText().trim()
						+ "%' or sing_date like '%" + month.getSelectedItem()
						+ "%'" + " and " + "sing_date like '%"
						+ years.getSelectedItem() + "%' order by sing_num asc";

				if (sqlUpdate(sql)) {
					JOptionPane.showMessageDialog(this, "곡 조회 성공!", "곡 조회 성공",
							JOptionPane.DEFAULT_OPTION);
					sqlQuery("select * from singtitle where sing_name like '%"
							+ this.txt_SingName.getText().trim() + "%'"
							+ " and " + "sing_singer like '%"
							+ this.txt_Singer.getText().trim()
							+ "%' or sing_date like '%"
							+ month.getSelectedItem() + "%'" + " and "
							+ "sing_date like '%" + years.getSelectedItem()
							+ "%' order by sing_num asc");
					txt_SingName.setText("");
					txt_Singer.setText("");
				}
			}

		} else if (ob == this.btn_Update_UpdateInsert) { // 신곡 업데이트(테이블 모든 열
															// 사라짐)
			String sql = "insert into singtitle select * from sing_insert";
			String sql2 = "delete from sing_insert";
			if (sqlUpdate(sql)) {
				JOptionPane.showMessageDialog(this, "신곡 추가 성공!", "신곡 추가",
						JOptionPane.DEFAULT_OPTION);
				sqlQuery("select sing_num,sing_name,sing_singer,sing_date FROM singtitle order by sing_num asc");
			} else {
				JOptionPane.showMessageDialog(this, "신곡 추가 실패!", "신곡 추가 실패",
						JOptionPane.DEFAULT_OPTION);
				return;
			}

			if (sqlUpdate(sql2)) {
				Sing_Model_Update.setNumRows(0); // 깨끗이 청소
			} else {
				JOptionPane.showMessageDialog(this, "신곡 추가 실패!", "신곡 추가 실패",
						JOptionPane.DEFAULT_OPTION);
				return;
			}

			txt_SingName.setText("");
			txt_Singer.setText("");
		} else if (ob == this.btn_Update_Delete) {// 신곡 삭제버튼
			int row = SingTableUpdate.getSelectedRow();
			if (row < 0) {
				JOptionPane.showMessageDialog(this, "삭제할 곡을 선택해 주세요!",
						"선택 곡 오류", JOptionPane.DEFAULT_OPTION);
				return;
			}
			String insert_num = (String) Sing_Model_Update.getValueAt(row, 0);
			String sql2 = "DELETE sing_insert WHERE insert_num = '"
					+ insert_num + "'";
			if (sqlUpdate(sql2)) {
				JOptionPane.showMessageDialog(this, "신곡 삭제 성공!", "곡 삭제",
						JOptionPane.DEFAULT_OPTION);
				sqlQuery2("SELECT insert_num,insert_name,insert_singer,insert_date FROM sing_insert order by insert_date desc");
				txt_SingName.setText("");
				txt_Singer.setText("");
			} else {
				JOptionPane.showMessageDialog(this, "신곡 삭제 실패!", "곡 삭제 실패",
						JOptionPane.DEFAULT_OPTION);
				return;
			}

		} else if (ob == this.btn_Sing_Update) {// 기존 곡 수정
			int row = SingTableTitle.getSelectedRow();
			// 아이디
			if (row < 0) {
				JOptionPane.showMessageDialog(this, "수정할 곡을 선택해 주세요!",
						"곡 선택 오류", JOptionPane.DEFAULT_OPTION);
				return;
			}
			String key_id = (String) Sing_model.getValueAt(row, 0);
			String sql = "UPDATE singtitle SET " + "sing_name = '"
					+ this.txt_SingName.getText().trim() + "',"
					+ "sing_singer = '" + this.txt_Singer.getText().trim()
					+ "'" + "where sing_num ='" + key_id + "'";
			if (txt_SingName.equals("") == false) {
				if (sqlUpdate(sql)) {
					JOptionPane.showMessageDialog(this, "곡 수정 성공했어요!",
							"곡 수정 성공", JOptionPane.DEFAULT_OPTION);
					sqlQuery("select sing_num,sing_name,sing_singer,sing_date FROM singtitle order by sing_date desc");
					txt_SingName.setText("");
					txt_Singer.setText("");
				} else {
					JOptionPane.showMessageDialog(this, "곡 수정 실패했어요!",
							"곡 수정 실패", JOptionPane.DEFAULT_OPTION);
					return;
				}
			}
		}
		if (e.getSource() == btn_excel) {
			excelConnection excel = new excelConnection(title, SingTableTitle,
					Sing_model);
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
		int row = SingTableTitle.getSelectedRow();
		int row2 = SingTableUpdate.getSelectedRow();

		if (row >= 0) {
			this.txt_SingName.setText((String) Sing_model.getValueAt(row, 1));
			this.txt_Singer.setText((String) Sing_model.getValueAt(row, 2));
		}
		if (row2 >= 0) {
			this.txt_SingName.setText((String) Sing_Model_Update.getValueAt(
					row2, 1));
			this.txt_Singer.setText((String) Sing_Model_Update.getValueAt(row2,
					2));

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
