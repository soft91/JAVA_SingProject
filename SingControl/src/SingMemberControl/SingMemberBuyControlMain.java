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
			JOptionPane.showMessageDialog(frame, "������ ���� �����Ͽ����ϴ�, Ȯ���ϼ���!",
					"���ӽ���", JOptionPane.ERROR_MESSAGE);
			return;
		} else {
			frame.sqlQuery("SELECT member_num,member_name,member_store,member_addr,member_phone,member_room,member_money,member_lastupdate,member_date from sing_member order by 1 asc");
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
		new SingMemberBuyControlMain();

	}

}
