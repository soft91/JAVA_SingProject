package SingMain;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import CompanyClient.CompanyClientFrame;
import Notice.NoticeCompanyControlFrame;
import Notice.NoticeCompanyControlMain;
import SingCompany.SingCompanyMain;
import SingMemberControl.SingMemberBuyControlMain;

public class SingMainFrame extends JFrame implements ActionListener {

	JButton Member_sing;// �� ����(����)
	JButton Company_sing;// �� ����(ȸ���)
	JButton Member;// �� ����
	JButton logout;// �α� �ƿ�
	JButton Exit;// ���� ��ư

	// ����ȭ�� ������Ʈ
	public SingMainFrame() {
		JPanel panel = new JPanel();
		setSize(600, 220);
		setTitle("�뷡�� ��/ȸ�� ���� ���α׷�");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel.setLayout(null);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(((screenSize.width) / 2) - 300,
				((screenSize.height) / 2) - 110);

		Member_sing = new JButton("�������� ����");
		Member_sing.setFont(new Font("�������� ����", Font.BOLD, 13));
		panel.add(Member_sing);
		Member_sing.setBounds(25, 30, 150, 90);

		Company_sing = new JButton("�� ����(�����ڿ�)");
		Company_sing.setFont(new Font("�� ����(�����ڿ�)", Font.BOLD, 13));
		panel.add(Company_sing);
		Company_sing.setBounds(215, 30, 150, 90);

		Member = new JButton("�� ����");
		Member.setFont(new Font("�� ����", Font.BOLD, 13));
		panel.add(Member);
		Member.setBounds(400, 30, 150, 90);

		logout = new JButton("�α׾ƿ�");
		panel.add(logout);
		logout.setBounds(330, 140, 100, 30);

		Exit = new JButton("����");
		panel.add(Exit);
		Exit.setBounds(450, 140, 100, 30);

		Member_sing.addActionListener(this);
		Company_sing.addActionListener(this);
		logout.addActionListener(this);
		Member.addActionListener(this);
		Exit.addActionListener(this);

		add(panel);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == Member_sing) {
			NoticeCompanyControlMain sm = new NoticeCompanyControlMain();

		}
		if (e.getSource() == Company_sing) {
			SingCompanyMain h = new SingCompanyMain();

		}
		if (e.getSource() == Member) {
			SingMemberBuyControlMain a = new SingMemberBuyControlMain();

		}
		if (e.getSource() == logout) {
			CompanyClientFrame companyclient = new CompanyClientFrame();
			dispose();
		}
		if (e.getSource() == Exit) {
			this.dispose();
		}

	}

}
