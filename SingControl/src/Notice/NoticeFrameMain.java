package Notice;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class NoticeFrameMain {

	private boolean packFrame = false;

	public NoticeFrameMain() {
		NoticeFrame frame = new NoticeFrame();

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
			frame.sqlQuery("select noticeboard_num,noticeboard_name,noticeboard_nametitle,noticeboard_title from noticeboard");
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		new NoticeFrameMain();
	}

}
