package io.leopard.excel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractUrlBasedView;

import jxl.Workbook;
import jxl.format.CellFormat;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class ExcelView extends ModelAndView {

	/**
	 * 文件名
	 */
	private String fileName;

	private ByteArrayOutputStream output;

	private WritableWorkbook workbook;

	protected String sheetName;

	private WritableSheet sheet;

	private int currentRow;

	private AbstractUrlBasedView view = new AbstractUrlBasedView() {
		@Override
		protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
			response.setContentType("application/force-download");
			String fileName = getFileName();
			if (StringUtils.isEmpty(fileName)) {
				fileName = Long.toString(System.currentTimeMillis());
			}
			String filedisplay = URLEncoder.encode(fileName + ".xls", "UTF-8");
			response.addHeader("Content-Disposition", "attachment;filename=" + filedisplay);
			// System.out.println("filedisplay:" + filedisplay);
			workbook.write();
			workbook.close();

			InputStream input = new ByteArrayInputStream(output.toByteArray());
			java.io.OutputStream out = response.getOutputStream();
			byte[] b = new byte[1024];
			int i = 0;
			while ((i = input.read(b)) > 0) {
				out.write(b, 0, i);
			}
			input.close();
			out.flush();
			output.close();
		}
	};

	public ExcelView() throws IOException {
		this("sheet1");
	}

	public ExcelView(String sheetName) throws IOException {
		super.setView(view);
		output = new ByteArrayOutputStream();
		this.workbook = Workbook.createWorkbook(output);
		this.sheet = workbook.createSheet(sheetName, 0);
		this.currentRow = 1;
	}

	public void addColumnName(String... columnNames) throws WriteException {
		int columnCount = this.sheet.getColumns();
		// System.err.println("columnCount:" + columnCount);
		for (int i = 0; i < columnNames.length; i++) {
			// 通过函数WritableFont（）设置字体样式
			// 第一个参数表示所选字体
			// 第二个参数表示字体大小
			// 第三个参数表示粗体样式，有BOLD和NORMAL两种样式
			// 第四个参数表示是否斜体,此处true表示为斜体
			// 第五个参数表示下划线样式
			// 第六个参数表示颜色样式，此处为Red
			WritableFont wf = new WritableFont(WritableFont.TIMES, 11, WritableFont.BOLD);
			CellFormat cf = new WritableCellFormat(wf);
			Label label = new Label(i + columnCount, 0, columnNames[i], cf);
			sheet.addCell(label);
		}
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Row addRow() {
		Row row = new Row(sheet, currentRow);
		currentRow++;
		return row;
	}

	public File save(String fileId) throws IOException, WriteException {
		File file = new File("/tmp/" + fileId + ".xls");
		this.save(file);
		return file;
	}

	public void save(File file) throws IOException, WriteException {
		workbook.write();
		workbook.close();
		FileUtils.writeByteArrayToFile(file, output.toByteArray());
	}

}
