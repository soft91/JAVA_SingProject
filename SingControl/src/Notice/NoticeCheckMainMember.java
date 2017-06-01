package Notice;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import SingCompany.SingCompany;

public class NoticeCheckMainMember {

	private boolean packFrame = false;

	public NoticeCheckMainMember() {
		NoticeCheckMember frame = new NoticeCheckMember();

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
			frame.sqlQuery("SELECT noticeboard_num,noticeboard_name,noticeboard_nametitle,noticeboard_title,noticeboard_date from noticeboard order by noticeboard_date desc ");
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		new NoticeCheckMainMember();
	}

}
