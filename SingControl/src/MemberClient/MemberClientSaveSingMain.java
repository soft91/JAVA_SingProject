package MemberClient;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class MemberClientSaveSingMain {
	private boolean packFrame = false;

	public MemberClientSaveSingMain() {
		MemberClientSaveSing frame = new MemberClientSaveSing();

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
			frame.sqlQuery("SELECT sing_num,sing_name,sing_singer,sing_date from singtitle order by sing_num asc");

		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		new MemberClientSaveSingMain();

	}

}
