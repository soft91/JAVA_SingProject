package SingMemberControl;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import SingCompany.SingCompany;
import SingCompany.SingCompanyMain;
import SingMain.SingMainFrame;

public class SingMemberBuyControlMain {

	private boolean packFrame = false;

	public SingMemberBuyControlMain() {
		SingMemberBuyControlFrame frame = new SingMemberBuyControlFrame();

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
			frame.sqlQuery("SELECT member_num,member_name,member_store,member_addr,member_phone,member_room,member_money,member_lastupdate,member_date from sing_member order by 1 asc");
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
		new SingMemberBuyControlMain();

	}

}
