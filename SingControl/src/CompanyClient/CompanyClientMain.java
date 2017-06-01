package CompanyClient;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import Notice.NoticeCompanyControlFrame;

public class CompanyClientMain {

	private boolean packFrame = false;

	public CompanyClientMain() {
		CompanyClientFrame frame = new CompanyClientFrame();

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
			frame.sqlQuery("SELECT login_id,login_pw from login");
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		new CompanyClientFrame();
	}

}
