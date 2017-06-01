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
	 * }else if(eventBut.equals("엑셀로저장")){//member.java에서 연동 new
	 * ExcelWriteIn(title, table, dtm);//ExcelWriteIn 클래스로 이동 }
	 */
	public excelConnection(String title[], JTable table, DefaultTableModel dtm) {

		JFileChooser fileChooser = new JFileChooser();// 경로가 담김

		int status = fileChooser.showSaveDialog(null);// 저장 또는 취소인지 담김
		if (status == JFileChooser.APPROVE_OPTION) {// 저장
			File file = fileChooser.getSelectedFile();// 경로와 파일명
			// 확정자
			String path = file.getPath();
			String firstName = path.substring(0, path.lastIndexOf(""));
			String extName = path.substring(path.lastIndexOf(".") + 1);
			if (!extName.equals("xls")) {
				file = new File(firstName + ".xls");
			}
			try {
				// 엑셀
				WritableWorkbook wb = Workbook.createWorkbook(file);
				WritableSheet sheet = wb.createSheet("노래 데이터", 0);
				WritableCellFormat format = new WritableCellFormat();

				for (int i = 0; i < title.length; i++) {
					Label titleLbl = new Label(i, 0, title[i], format);
					sheet.addCell(titleLbl);
				}
				// 데이타리스트 추가
				for (int row = 0; row < dtm.getRowCount(); row++) {// 몇행인지 구하기
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