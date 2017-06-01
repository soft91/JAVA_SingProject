package SingMember;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import SingCompany.SingCompany;
import SingCompany.SingCompanyMain;

public class SingMemberMain {

	private boolean packFrame = false;

	public SingMemberMain() {
		SingMember frame = new SingMember();

		if (packFrame) {
			frame.pack();
		} else {
			frame.validate();
		}

		if (!frame.connProcess()) {
			JOptionPane.showMessageDialog(frame, "서버와 접속 실패하였습니다, 확인하세요!",
					"접속실패", JOptionPane.ERROR_MESSAGE);
			return;
		} else {
			frame.sqlQuery("SELECT sing_num,sing_name,sing_singer,sing_date from singtitle order by sing_num asc");
			frame.sqlQuery2("SELECT insert_num,insert_name,insert_singer,insert_date from sing_insert order by 1 asc");
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			// 실행되고 있는 시스템의 스타일로 설정(Window 와 X-window 와는 윈도우가 보여지는 스타일이 다르다)
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 마직막으로 자기 자신을 생성하므로서 main 메소드를 통해서 생성자 호출 하므로
		// 작업창이 생성되고 중앙에 배치된다.
		new SingMemberMain();
	}

}
