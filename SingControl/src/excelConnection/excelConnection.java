package excelConnection;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class excelConnection {
	/*
	 * }else if(eventBut.equals("����������")){//member.java���� ���� new
	 * ExcelWriteIn(title, table, dtm);//ExcelWriteIn Ŭ������ �̵� }
	 */
	public excelConnection(String title[], JTable table, DefaultTableModel dtm) {

		JFileChooser fileChooser = new JFileChooser();// ��ΰ� ���

		int status = fileChooser.showSaveDialog(null);// ���� �Ǵ� ������� ���
		if (status == JFileChooser.APPROVE_OPTION) {// ����
			File file = fileChooser.getSelectedFile();// ��ο� ���ϸ�
			// Ȯ����
			String path = file.getPath();
			String firstName = path.substring(0, path.lastIndexOf(""));
			String extName = path.substring(path.lastIndexOf(".") + 1);
			if (!extName.equals("xls")) {
				file = new File(firstName + ".xls");
			}
			try {
				// ����
				WritableWorkbook wb = Workbook.createWorkbook(file);
				WritableSheet sheet = wb.createSheet("�뷡 ������", 0);
				WritableCellFormat format = new WritableCellFormat();

				for (int i = 0; i < title.length; i++) {
					Label titleLbl = new Label(i, 0, title[i], format);
					sheet.addCell(titleLbl);
				}
				// ����Ÿ����Ʈ �߰�
				for (int row = 0; row < dtm.getRowCount(); row++) {// �������� ���ϱ�
					for (int col = 0; col < dtm.getColumnCount(); col++) {
						Label dataLabel = new Label(col, row + 1,
								(String) dtm.getValueAt(row, col), format);
						sheet.addCell(dataLabel);
					}
				}
				wb.write();
				wb.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}