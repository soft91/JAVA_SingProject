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
	String title[] = { "노래 번호", "노래 제목", "가수명", "등록일" };

	String data1[][] = new String[0][3];
	String title1[] = { "노래 번호", "노래 제목", "가수명", "등록일" };

	DefaultTableModel Sing_Model = new DefaultTableModel(data, title) { // 기존 곡
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

	JLabel lbl_memberSing_label;// 곡 테이블 레이블
	JLabel lbl_memberSing_updatelist;// 신곡 테이블 레이블
	JLabel lbl_memberSing_date;// 날짜 레이블
	JTextField txt_member_search;// 날짜
	JButton btn_memberSing_search;// 날짜별 검색 버튼
	JButton SingMember_Exit;// 종료 버튼
	JButton btn_notice;// 공지사항 관리
	JPanel TitlePanel;// 기존 곡 테이블 패널
	JPanel UpdatePanel;// 업데이트 곡 테이블 패널
	BorderLayout SingTitle_Border;// 배치관리자
	BorderLayout SingUpdate_Border;// 배치관리자
	JTable SingTableTitle;// 기존 곡 테이블
	JTable SingTableUpdate;// 업데이트 곡 테이블
	JScrollPane sp_Title;// 기존 곡 스크롤
	JScrollPane sp_Update;// 업데이트 곡 스크롤
	TitledBorder titledBorder1;// 기존 곡 타이틀
	TitledBorder titledBorder2;// 업데이트 곡 타이틀

	// 컴포넌트 삽입
	public SingMember() {

		JPanel panel = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		JPanel panel4 = new JPanel();
		TitlePanel = new JPanel();
		UpdatePanel = new JPanel();
		JScrollPane sp_Title = new JScrollPane();
		JScrollPane sp_Update = new JScrollPane();
		titledBorder1 = new TitledBorder("곡(노래방) 데이터베이스");
		titledBorder2 = new TitledBorder("신곡(노래방) 업데이트");

		setSize(1000, 1000);
		setTitle("곡 관리 프로그램(고객용)");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(((screenSize.width) / 2) - 500,
				((screenSize.height) / 2) - 500);

		SingMemberTitleModel();// 기존 곡 테이블
		SingMemberUpdateModel();// 업데이트 곡 테이블

		lbl_memberSing_date = new JLabel("날짜 입력(월 단위) : ");
		lbl_memberSing_date.setFont(new Font("Monospaced", Font.BOLD, 12));
		lbl_memberSing_date.setBounds(550, 820, 150, 30);
		panel4.add(lbl_memberSing_date);

		txt_member_search = new JTextField();
		txt_member_search.setBounds(700, 820, 80, 30);
		panel4.add(txt_member_search);

		btn_memberSing_search = new JButton("검색");
		btn_memberSing_search.setBounds(810, 820, 80, 30);
		panel4.add(btn_memberSing_search);
		btn_memberSing_search.addActionListener(this);

		TitlePanel.add(sp_Title, BorderLayout.CENTER);
		sp_Title.getViewport().add(SingTableTitle, null);
		add(TitlePanel);
		UpdatePanel.add(sp_Update, BorderLayout.CENTER);
		sp_Update.getViewport().add(SingTableUpdate, null);
		add(UpdatePanel);

		btn_notice = new JButton("공지사항 관리");
		btn_notice.setFont(new Font("공지사항 관리", Font.BOLD, 13));
		panel4.add(btn_notice);
		btn_notice.setBounds(720, 900, 120, 30);
		btn_notice.addActionListener(this);

		SingMember_Exit = new JButton("종료");
		SingMember_Exit.setFont(new Font("종료", Font.BOLD, 13));
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

	// 기존곡 테이블 모델
	public void SingMemberTitleModel() {

		SingTitle_Border = new BorderLayout();
		SingTableTitle = new JTable(Sing_Model);

		TitlePanel.setBorder(BorderFactory.createRaisedBevelBorder());
		TitlePanel.setBounds(new Rectangle(80, 70, 809, 298));
		TitlePanel.setLayout(SingTitle_Border);

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

	// 업데이트 테이블 모델
	public void SingMemberUpdateModel() {

		SingUpdate_Border = new BorderLayout();
		SingTableUpdate = new JTable(Sing_Model_Update);

		UpdatePanel.setBorder(BorderFactory.createRaisedBevelBorder());
		UpdatePanel.setBounds(new Rectangle(80, 500, 809, 298));
		UpdatePanel.setLayout(SingUpdate_Border);

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
			Sing_Model.setNumRows(0); // 깨끗이 청소
			while (rs.next()) {
				String data[] = { rs.getString(1), rs.getString(2),
						rs.getString(3), rs.getString(4), };
				Sing_Model.addRow(data);
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
		if (e.getSource() == SingMember_Exit) {
			this.dispose();
		}

		Object ob = e.getSource();

		if (ob == this.btn_memberSing_search) {// 신곡 검색
			String sql = "select * from singtitle where sing_date like '%__"
					+ this.txt_member_search.getText().trim() + "__%'";
			if (sqlUpdate2(sql)) {
				JOptionPane.showMessageDialog(this, txt_member_search.getText()
						+ " 월달 신곡 목록입니다!", "월별 신곡 조회",
						JOptionPane.DEFAULT_OPTION);
				sqlQuery2("select * from singtitle where sing_date like '%__"
						+ this.txt_member_search.getText().trim() + "__%'");
				txt_member_search.setText("");
			} else {
				JOptionPane.showMessageDialog(this, "신곡 조회 실패입니다.", "신곡 조회 실패",
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
