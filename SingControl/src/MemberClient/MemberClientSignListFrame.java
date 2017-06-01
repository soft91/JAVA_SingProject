package MemberClient;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class MemberClientSignListFrame extends JFrame implements ActionListener {
	JButton distinct_id;// 중복검사 버튼
	JButton add_member;// 회원가입 등록 버튼
	JButton exit;// 우편번호 검색 버튼

	JLabel member;// 회원가입 변수
	JLabel member_id;// 아이디 변수
	JLabel member_pw;// 비밀번호 변수
	JLabel member_pw2;// 비밀번호 확인 변수
	JLabel member_name;// 이름 변수
	JLabel member_store;// 이메일 변수
	JLabel member_add;// 우편번호 변수
	JLabel member_phone;// 주소 변수
	JLabel member_room;// 휴대폰 변수

	JTextField txt_member_id;// 아이디 변수
	JPasswordField txt_member_pw;// 비밀번호 변수
	JPasswordField txt_member_pw2;// 비밀번호 확인 변수
	JTextField txt_member_name;// 이름 변수
	JTextField txt_member_store;// 이메일 변수
	JTextField txt_member_add;// 우편번호 변수
	JTextField txt_member_phone;// 주소 변수
	JTextField txt_member_room;// 휴대폰 변수

	Connection con = null;

	public MemberClientSignListFrame() {

		String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
		String id = "scott";
		String password = "tiger";

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(url, id, password);
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버를 찾을 수 없습니다.");
		} catch (SQLException e) {
			System.out.println("연결에 실패하였습니다.");
		}

		setSize(600, 800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("회 원 가 입");

		// 버튼 변수
		distinct_id = new JButton("중복 검색");
		add_member = new JButton("회원 등록");
		exit = new JButton("취소");
		add_member.addActionListener(this);
		distinct_id.addActionListener(this);
		exit.addActionListener(this);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(((screenSize.width) / 2) - 300,
				((screenSize.height) / 2) - 400);

		member_Label();
		member_txt();
		panel();

		setVisible(true);
	}

	// 패널
	public void panel() {
		JPanel panel = new JPanel();
		panel.setLayout(null);

		// 레이블
		panel.add(member);
		member.setBounds(200, 15, 200, 30);
		member.setFont(new Font("굴림체", Font.BOLD, 30));
		panel.add(member_id);
		member_id.setBounds(50, 100, 150, 30);
		member_id.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		panel.add(member_pw);
		member_pw.setBounds(50, 170, 150, 30);
		member_pw.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		panel.add(member_pw2);
		member_pw2.setBounds(50, 240, 150, 30);
		member_pw2.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		panel.add(member_name);
		member_name.setBounds(50, 310, 150, 30);
		member_name.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		panel.add(member_store);
		member_store.setBounds(50, 380, 150, 30);
		member_store.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		panel.add(member_add);
		member_add.setBounds(50, 450, 150, 30);
		member_add.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		panel.add(member_phone);
		member_phone.setBounds(50, 520, 150, 30);
		member_phone.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		panel.add(member_room);
		member_room.setBounds(50, 590, 150, 30);
		member_room.setFont(new Font("맑은 고딕", Font.BOLD, 15));

		// 텍스트 필드
		panel.add(txt_member_id);
		txt_member_id.setBounds(160, 105, 120, 25);
		txt_member_id.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		panel.add(txt_member_pw);
		txt_member_pw.setBounds(160, 175, 120, 25);
		txt_member_pw.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		panel.add(txt_member_pw2);
		txt_member_pw2.setBounds(160, 245, 120, 25);
		txt_member_pw2.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		panel.add(txt_member_name);
		txt_member_name.setBounds(160, 315, 120, 25);
		txt_member_name.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		panel.add(txt_member_store);
		txt_member_store.setBounds(160, 385, 200, 25);
		txt_member_store.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		panel.add(txt_member_add);
		txt_member_add.setBounds(160, 455, 400, 25);
		txt_member_add.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		panel.add(txt_member_phone);
		txt_member_phone.setBounds(160, 525, 120, 25);
		txt_member_phone.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		panel.add(txt_member_room);
		txt_member_room.setBounds(160, 595, 120, 25);
		txt_member_room.setFont(new Font("맑은 고딕", Font.BOLD, 15));

		// 버튼
		panel.add(distinct_id);
		distinct_id.setBounds(300, 105, 100, 20);
		panel.add(add_member);
		add_member.setBounds(190, 700, 100, 30);
		panel.add(exit);
		exit.setBounds(300, 700, 100, 30);

		add(panel);

	}

	// 레이블 정의
	public void member_Label() {
		member = new JLabel("회 원 가 입");
		member_id = new JLabel("사용자 ID : ");
		member_pw = new JLabel("비밀번호 : ");
		member_pw2 = new JLabel("비밀번호 확인 : ");
		member_name = new JLabel("성명 : ");
		member_store = new JLabel("상호명 : ");
		member_add = new JLabel("주소 : ");
		member_phone = new JLabel("전화번호 : ");
		member_room = new JLabel("방 갯수 : ");
	}

	// 텍스트 필드 정의
	public void member_txt() {
		txt_member_id = new JTextField(8);
		txt_member_pw = new JPasswordField(16);
		txt_member_pw2 = new JPasswordField(16);
		txt_member_name = new JTextField(8);
		txt_member_store = new JTextField(8);
		txt_member_add = new JTextField(8);
		txt_member_phone = new JTextField(8);
		txt_member_room = new JTextField(8);
	}

	public void sqlQuery(String sql) {
		System.out.println("query SQL : " + sql);
		Statement stmt = null;

		try {
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
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
			stmt = con.createStatement();
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

	public void IDCheck() {

		String id;
		String pass;
		String name;
		// 로그인창의 텍스트필드에 쓴값을 가져오기
		id = txt_member_id.getText().trim();

		Statement stmt = null;

		// SELECT 쿼리를 작성한다. 해당하는 아이디값의 패스워드를 검색한다.
		String query = "SELECT member_id FROM sing_member where member_id='"
				+ id + "'";

		System.out.println(query);

		try {
			stmt = con.createStatement();
			// executeQuery() 메서드로 SELECT문의 실행시키고 결과로 ResultSet 객체를 받는다.
			ResultSet rs = stmt.executeQuery(query);
			// 레코드가 있는지 검사

			if (rs.next()) {

				// 텍스트필드에 쓴값과 데이터베이스에 있는 아이디 값을 비교한다.
				if (id.equals(rs.getString("member_id"))) {
					JOptionPane.showMessageDialog(this, "이미 사용된 아이디가 있습니다.",
							"아이디 중복 확인", JOptionPane.DEFAULT_OPTION);
				}
			} else {
				JOptionPane.showMessageDialog(this, "사용할 수 있는 아이디입니다.",
						"아이디 중복 확인", JOptionPane.DEFAULT_OPTION);
			}

		} catch (Exception b) {
			b.printStackTrace();
		}
	}

	public void add_member() {
		System.out.println("추가 버튼 클릭!");
		String sql = "insert into sing_member(member_num,member_id,member_pw,member_pwcheck,member_name,member_store,member_addr,member_phone,member_room,member_date) values(sing_member_seq.nextval"
				+ ",'"
				+ this.txt_member_id.getText().trim()
				+ "','"
				+ this.txt_member_pw.getText().trim()
				+ "','"
				+ this.txt_member_pw2.getText().trim()
				+ "','"
				+ this.txt_member_name.getText().trim()
				+ "','"
				+ this.txt_member_store.getText().trim()
				+ "','"
				+ this.txt_member_add.getText().trim()
				+ "','"
				+ this.txt_member_phone.getText().trim()
				+ "','"
				+ this.txt_member_room.getText().trim() + "',sysdate)";
		if (txt_member_name.equals("") == false) {
			if (sqlUpdate(sql)) {
				JOptionPane.showMessageDialog(this, "고객정보 추가 성공!", "고객추가 성공",
						JOptionPane.DEFAULT_OPTION);
				sqlQuery("SELECT member_num,member_name,member_store,member_addr,member_phone,member_room,member_date from sing_member");
			} else {
				JOptionPane.showMessageDialog(this, "빈칸이 있거나 아이디 중복 오류 입니다.",
						"고객추가 실패!", JOptionPane.DEFAULT_OPTION);
				txt_member_id.setText("");
				return;
			}
		}
		txt_member_id.setText("");
		txt_member_pw.setText("");
		txt_member_pw2.setText("");
		txt_member_phone.setText("");
		txt_member_name.setText("");
		txt_member_store.setText("");
		txt_member_add.setText("");
		txt_member_phone.setText("");
		txt_member_room.setText("");
		dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if (e.getSource() == add_member) {

			add_member();
		}

		if (e.getSource() == distinct_id) {
			if (txt_member_id.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "아이디를 입력해주세요!", "아이디 입력",
						JOptionPane.DEFAULT_OPTION);
			} else {
				IDCheck();
			}

		}
		if (e.getSource() == exit) {
			dispose();
		}

	}
}
