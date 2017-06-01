package MemberClient;

import javax.swing.UIManager;

public class MemberClientSignListMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		MemberClientSignListFrame a = new MemberClientSignListFrame();
	}

}
