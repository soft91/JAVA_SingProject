package Notice;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class NoticeFrame extends JFrame implements ActionListener,
		WindowListener {

	JLabel lbl_num;// 글 번호 레이블
	JLabel lbl_nametitle;// 글 제목 레이블
	JLabel lbl_name;// 글 쓴이 레이블
	JTextField txt_name;// 글 쓴이 텍스트 필드
	JTextField txt_num;// 글 번호 텍스트 필드
	JTextField txt_nametitle;// 글 제목 텍스트 필드
	JLabel lbl_title;// 글 내용 레이블
	JTextArea txt_title;// 글 내용
	JButton btn_insert;// 글 추가
	JButton btn_exit;// 종료
	JScrollPane scroll_title;

	public NoticeFrame() {

		setSize(600, 450);
		setTitle("공지사항");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		connProcess();

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(((screenSize.width) / 2) - 300,
				((screenSize.height) / 2) - 225);

		JPanel panel = new JPanel();
		scroll_title = new JScrollPane();

		lbl_num = new JLabel("글 번호 : ");
		panel.add(lbl_num);
		lbl_num.setBounds(20, 10, 90, 30);

		lbl_name = new JLabel("글 쓴이 : ");
		panel.add(lbl_name);
		lbl_name.setBounds(20, 50, 90, 30);

		lbl_nametitle = new JLabel("글 제목 : ");
		panel.add(lbl_nametitle);
		lbl_nametitle.setBounds(20, 90, 90, 30);

		txt_num = new JTextField();
		panel.add(txt_num);
		txt_num.setBounds(80, 10, 90, 30);
		txt_num.setEnabled(false);

		txt_name = new JTextField();
		panel.add(txt_name);
		txt_name.setBounds(80, 50, 90, 30);
		txt_name.setEnabled(false);

		txt_nametitle = new JTextField();
		panel.add(txt_nametitle);
		txt_nametitle.setBounds(80, 90, 300, 30);
		txt_nametitle.setEnabled(false);

		lbl_title = new JLabel("글 내용");
		panel.add(lbl_title);
		lbl_title.setBounds(20, 120, 90, 30);

		txt_title = new JTextArea();
		panel.add(txt_title);
		txt_title.setBounds(20, 150, 545, 200);
		txt_title.setEnabled(false);

		btn_exit = new JButton("종료");
		panel.add(btn_exit);
		btn_exit.setBounds(475, 370, 90, 30);
		btn_exit.addActionListener(this);

		panel.setLayout(null);
		add(panel);
		setVisible(true);
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

	public void sqlQuery(String sql) {
		System.out.println("query SQL : " + sql);
		Statement stmt = null;

		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String data[] = { rs.getString(1), rs.getString(2),
						rs.getString(3), rs.getString(4), };
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

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if (e.getSource() == btn_exit) {
			this.dispose();
		}
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