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
			JOptionPane.showMessageDialog(frame, "������ ���� �����Ͽ����ϴ�, Ȯ���ϼ���!",
					"���ӽ���", JOptionPane.ERROR_MESSAGE);
			return;
		} else {
			frame.sqlQuery("SELECT sing_num,sing_name,sing_singer,sing_date from singtitle order by sing_num asc");
			frame.sqlQuery2("SELECT insert_num,insert_name,insert_singer,insert_date from sing_insert order by 1 asc");
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			// ����ǰ� �ִ� �ý����� ��Ÿ�Ϸ� ����(Window �� X-window �ʹ� �����찡 �������� ��Ÿ���� �ٸ���)
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		// ���������� �ڱ� �ڽ��� �����ϹǷμ� main �޼ҵ带 ���ؼ� ������ ȣ�� �ϹǷ�
		// �۾�â�� �����ǰ� �߾ӿ� ��ġ�ȴ�.
		new SingMemberMain();
	}

}
