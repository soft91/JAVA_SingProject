package SingMain;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import SingCompany.SingCompany;
import SingCompany.SingCompanyMain;

public class SingMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {

			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		new SingMainFrame();

	}
}
