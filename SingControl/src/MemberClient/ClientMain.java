package MemberClient;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import Notice.NoticeCheckMainMember;

public class ClientMain {
	private boolean packFrame = false;

	public ClientMain() {
		ClientFrame frame = new ClientFrame();

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

		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		new ClientMain();
		NoticeCheckMainMember check = new NoticeCheckMainMember();
	}

}
