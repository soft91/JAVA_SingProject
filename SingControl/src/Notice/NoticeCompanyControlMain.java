package Notice;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class NoticeCompanyControlMain {

	private boolean packFrame = false;

	public NoticeCompanyControlMain() {
		NoticeCompanyControlFrame frame = new NoticeCompanyControlFrame();

		if (packFrame) {
			frame.pack();
		} else {
			frame.validate();
		}

		if (!frame.connProcess()) {
			JOptionPane.showMessageDialog(frame, "������ ���� �����Ͽ����ϴ�, Ȯ���ϼ���!",
					"���ӽ���", JOptionPane.ERROR_MESSAGE);
			return;
		} else {
			frame.sqlQuery("SELECT noticeboard_num,noticeboard_name,noticeboard_nametitle,noticeboard_title,noticeboard_date from noticeboard order by noticeboard_date desc");
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		new NoticeCompanyControlMain();
	}

}
