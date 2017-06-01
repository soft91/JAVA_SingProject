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
	String title[] = { "고객 번호", "고객 이름", "상호명", "주소", "전화번호", "방 갯수", "구매 금액",
			"업데이트날짜", "등록일" };

	String data2[][] = new String[0][4];
	String title2[] = { "구매 번호", "상호명", "금액", "구매일자" };

	DefaultTableModel MemberModel = new DefaultTableModel(data, title) { // 고객
																			// 모델
																			// 객체
		// 설정
		public boolean isCellEditable(int r, int c) {
			return false;
		}
	};

	DefaultTableModel BuyModel = new DefaultTableModel(data2, title2) { // 구매 모델
																		// 객체 설정

		public boolean isCellEditable(int r, int c) {
			return false;
		}
	};

	JPanel MemberPanel;// 기존 곡 테이블 패널
	BorderLayout borderLayout1;// 배치관리자
	JTable Member;// 기존 곡 테이블
	JScrollPane sp_Title;// 기존 곡 스크롤
	JLabel Member_Name;// 고객 이름
	JLabel Member_Store;// 상호명
	JLabel Member_Addr;// 상호 주소
	JLabel Member_Phone;// 상호 전화번호
	JLabel Member_Room;// 방의 갯수
	JTextField txt_member_name;// 고객 이름 텍스트필드
	JTextField txt_member_store;// 상호명 텍스트필드
	JTextField txt_member_addr;// 상호 주소 텍스트필드
	JTextField txt_member_phone;// 상호 전화번호 텍스트필드
	JTextField txt_member_room;// 방 갯수 텍스트필드
	JButton btn_member_update;// 고객 수정 버튼
	JButton btn_member_delete;// 고객 삭제 버튼
	JButton btn_member_search;// 고객 검색 버튼
	JButton btn_member_refresh;// 새로고침
	JButton btn_member_excel;// 고객정보 엑셀로 저장
	JButton btn_memberControl_Exit;// 종료
	TitledBorder titledBorder1;// 고객타이틀

	// 고객 관리 컴포넌트
	public SingMemberBuyControlFrame() {

		JPanel panel = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		JPanel panel4 = new JPanel();
		MemberPanel = new JPanel();
		JScrollPane sp_Title = new JScrollPane();
		titledBorder1 = new TitledBorder("고객 정보");

		setSize(1400, 500);
		setTitle("고객 관리");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(((screenSize.width) / 2) - 700,
				((screenSize.height) / 2) - 250);

		MemberModel();
		Member_Name = new JLabel("고객 이름");
		Member_Name.setBounds(900, 65, 80, 30);
		panel4.add(Member_Name);
		Member_Store = new JLabel("상호명");
		Member_Store.setBounds(900, 130, 80, 30);
		panel4.add(Member_Store);
		Member_Addr = new JLabel("주소");
		Member_Addr.setBounds(900, 200, 80, 30);
		panel4.add(Member_Addr);
		Member_Phone = new JLabel("전화번호");
		Member_Phone.setBounds(900, 270, 80, 30);
		panel4.add(Member_Phone);
		Member_Room = new JLabel("방의 갯수");
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

		btn_member_update = new JButton("고객 수정");
		btn_member_update.setBounds(1230, 70, 100, 30);
		panel4.add(btn_member_update);
		btn_member_update.addActionListener(this);
		btn_member_delete = new JButton("고객 삭제");
		btn_member_delete.setBounds(1230, 130, 100, 30);
		panel4.add(btn_member_delete);
		btn_member_delete.addActionListener(this);
		btn_member_search = new JButton("고객 검색");
		btn_member_search.setBounds(1230, 190, 100, 30);
		panel4.add(btn_member_search);
		btn_member_search.addActionListener(this);
		btn_member_refresh = new JButton("새로고침");
		btn_member_refresh.setBounds(1230, 255, 100, 30);
		panel4.add(btn_member_refresh);
		btn_member_refresh.addActionListener(this);
		btn_member_excel = new JButton("(고객)엑셀로 저장");
		btn_member_excel.setBounds(1230, 320, 130, 30);
		panel4.add(btn_member_excel);
		btn_member_excel.addActionListener(this);

		btn_memberControl_Exit = new JButton("종료");
		btn_memberControl_Exit.setBounds(1230, 410, 130, 30);
		panel4.add(btn_memberControl_Exit);
		btn_memberControl_Exit.addActionListener(this);

		MemberPanel.add(sp_Title, BorderLayout.CENTER);
		sp_Title.getViewport().add(Member, null);
		add(MemberPanel);
		// 고객 정보
		panel3.setBorder(titledBorder1);
		panel3.setBounds(new Rectangle(15, 40, 1355, 355));
		panel3.setOpaque(false);

		// 구매 정보
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

	// 고객 테이블
	public void MemberModel() {

		borderLayout1 = new BorderLayout();
		Member = new JTable(MemberModel);

		MemberPanel.setBorder(BorderFactory.createRaisedBevelBorder());
		MemberPanel.setBounds(new Rectangle(40, 70, 840, 298));
		MemberPanel.setLayout(borderLayout1);

		Member.setBackground(Color.WHITE);
		Member.setBorder(BorderFactory.createEtchedBorder());

		Member.getColumnModel().getColumn(0).setPreferredWidth(50); // 아이디
		Member.getColumnModel().getColumn(1).setPreferredWidth(50); // 비밀번호
		Member.getColumnModel().getColumn(2).setPreferredWidth(50); // 이름
		Member.getColumnModel().getColumn(3).setPreferredWidth(50); // 주민번호
		Member.getColumnModel().getColumn(4).setPreferredWidth(50); // 비밀번호
		Member.getColumnModel().getColumn(5).setPreferredWidth(50); // 이름
		Member.getColumnModel().getColumn(6).setPreferredWidth(50); // 주민번호
		Member.getColumnModel().getColumn(7).setPreferredWidth(50); // 주민번호
		Member.getColumnModel().getColumn(8).setPreferredWidth(50); // 주민번호

		Member.setShowHorizontalLines(true); // 수평 라인 안보이게 처리
		Member.setShowVerticalLines(true); // 수직 라인 안보이게 처리
		Member.setSelectionBackground(Color.orange);
		Member.setSelectionForeground(Color.blue);
		Member.setRowMargin(0);
		Member.setIntercellSpacing(new Dimension(0, 0));
		Member.setRequestFocusEnabled(false); // 특정 셀에 포커스 설정 안되게 처리

		Member.setSelectionMode(0); // 단일선택 :0 , 다중선택:1, 선택하지 않음 -2

		Member.getTableHeader().setReorderingAllowed(false); // 헤더고정
		// table.getTableHeader().setBackground(new Color(200,200,200));
		// table.getTableHeader().setForeground(new Color(100,100,100));
		Member.getTableHeader().setMaximumSize(new Dimension(140, 0));
		Member.getTableHeader().setMinimumSize(new Dimension(10, 0));
		// table.getTableHeader().setResizingAllowed(false); //헤더조절불가능
		Member.setAlignmentX(JTable.CENTER_ALIGNMENT); // 정렬
		Member.setAlignmentY(JTable.CENTER_ALIGNMENT);

		this.Member.addMouseListener(this);

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
			MemberModel.setNumRows(0); // 깨끗이 청소
			while (rs.next()) {
				String data[] = { rs.getString(1), rs.getString(2),
						rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getString(8),
						rs.getString(9) };
				MemberModel.addRow(data);
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

		if (e.getSource() == btn_memberControl_Exit) {
			dispose();
		}

		if (ob == this.btn_member_update) {// 고객 수정
			int row = Member.getSelectedRow();
			// 아이디
			if (row < 0) {
				JOptionPane.showMessageDialog(this, "수정할 고객을 선택해 주세요!",
						"선택 오류", JOptionPane.DEFAULT_OPTION);
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
					JOptionPane.showMessageDialog(this, "고객 수정 성공!",
							"고객 수정 성공!", JOptionPane.DEFAULT_OPTION);
					sqlQuery("SELECT member_num,member_name,member_store,member_addr,member_phone,member_room,member_money,member_lastupdate,member_date from sing_member");
					txt_member_name.setText("");
					txt_member_store.setText("");
					txt_member_addr.setText("");
					txt_member_phone.setText("");
					txt_member_room.setText("");
				} else {
					JOptionPane.showMessageDialog(this, "고객 수정 실패", "고객 수정 실패",
							JOptionPane.DEFAULT_OPTION);
					return;
				}
			}
			txt_member_name.setText("");
			txt_member_store.setText("");
			txt_member_addr.setText("");
			txt_member_phone.setText("");
			txt_member_room.setText("");
		} else if (ob == this.btn_member_delete) {// 고객 삭제
			int row = Member.getSelectedRow();
			if (row < 0) {
				JOptionPane.showMessageDialog(this, "삭제할 고객을 선택해 주세요!",
						"선택 오류", JOptionPane.DEFAULT_OPTION);
				return;
			}
			String key_id = (String) MemberModel.getValueAt(row, 0);
			String sql = "DELETE sing_member WHERE member_num = '" + key_id
					+ "'";
			if (txt_member_name.equals("") == false) {
				if (sqlUpdate(sql)) {
					JOptionPane.showMessageDialog(this, key_id + "번"
							+ " 고객정보 삭제 성공!", "삭제 성공",
							JOptionPane.DEFAULT_OPTION);
					sqlQuery("SELECT member_num,member_name,member_money,member_store,member_addr,member_phone,member_room,member_date,member_lastupdate from sing_member");
					txt_member_name.setText("");
					txt_member_store.setText("");
					txt_member_addr.setText("");
					txt_member_phone.setText("");
					txt_member_room.setText("");
				} else {
					JOptionPane.showMessageDialog(this, "고객정보 삭제 실패!", "삭제 실패",
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
				JOptionPane.showMessageDialog(this, "검색 조건을 확인하세요^^",
						"검색 조건을 확인하세요^^", JOptionPane.DEFAULT_OPTION);
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
					JOptionPane.showMessageDialog(this, "고객정보 조회 성공했어요!",
							"고객정보 조회 성공", JOptionPane.DEFAULT_OPTION);
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
		} else if (ob == this.btn_member_refresh) {// 고객정보 새로고침
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
		// 고객 정보 클릭
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
