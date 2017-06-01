package SingCompany;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class SingCompanyMain {

	private boolean packFrame = false;

	public SingCompanyMain() {
		SingCompany frame = new SingCompany();

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
			frame.sqlQuery2("select insert_num,insert_name,insert_singer,insert_date from sing_insert order by insert_num asc");
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		new SingCompanyMain();

	}

}
