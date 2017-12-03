package com.secsc.utils.excel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;

/**
 * 
 * @author 吴俊杰
 * @Date 2017/07/09
 */
@Service(value = "excelUtil")
public class ExcelUtil {

	private Workbook wb;

	public ExcelUtil() {
	}

	public ExcelUtil(Workbook wb) {
		this.wb = wb;
	}

	/**
	 * @Description 读取Excel文件
	 * @param Excel文件
	 * @throws IOException
	 * @throws InvalidFormatException
	 * @throws EncryptedDocumentException
	 */
	public void readExcelFile(File excelFIle) throws EncryptedDocumentException,
			InvalidFormatException, IOException {
		wb = WorkbookFactory.create(excelFIle);

	}

	/**
	 * @Description 获取表格所有的工作表
	 * @return 所有工作表名
	 */
	public List<String> getSheets() {
		List<String> list = new ArrayList<String>();
		int sheetnum = wb.getNumberOfSheets(); // 获取工作簿总数
		for (int i = 0; i < sheetnum - 1; i++) {
			list.add(wb.getSheetName(i)); // 遍历每个工作簿获取名称
		}
		return list;
	}

	/**
	 * @Description 获取每个工作表的所有列的属性名
	 * @param 工作表名称
	 * 
	 */
	public List<String> getTitleOfSheet(String sheetName) {
		int row = 0, col = 0;
		List<String> list = new ArrayList<String>();
		Sheet sheet = wb.getSheet(sheetName); // 获取指定工作表
		row = sheet.getPhysicalNumberOfRows(); // 获取行数
		col = sheet.getRow(0).getPhysicalNumberOfCells(); // 获取列数
		Row row1 = sheet.getRow(0);
		for (int i = 0; i < col; i++) {
			list.add(row1.getCell(i).getStringCellValue().trim()); // 遍历第一列的所有属性名
		}
		return list;
	}

	/**
	 * @Description 获取每个工作表的数据的实体对象
	 * @param 工作表名称
	 * @return 数据实体
	 */

	public DataTable getContentOfSheet(String sheetName) {
		int row = 0, col = 0;
		Sheet sheet = wb.getSheet(sheetName);
		row = sheet.getPhysicalNumberOfRows() - 1;
		col = sheet.getRow(0).getPhysicalNumberOfCells();
		DataTable dataTable = new DataTable();
		List<Object[]> data = new ArrayList<Object[]>();
		for (int i = 0; i < row; i++) { // 遍历每一行每一列
			Object[] obj = new Object[col];
			for (int j = 0; j < col; j++) {
				Cell cell = sheet.getRow(i + 1).getCell(j);
				if (cell == null) { // 判断为空则赋值为""
					obj[j] = "";
				} else if (cell.getCellType() == 0) { // 判断类型为数值
					if (cell.getCellStyle().getDataFormat() == 14) {
						obj[j] = cell.getDateCellValue().getTime();
					} else {
						cell.setCellType(CellType.STRING);
						obj[j] = cell.getStringCellValue();
					}
				} else if (cell.getCellType() == 1) { // 判断类型为字符串
					obj[j] = String.valueOf(cell.getStringCellValue());
				} else if (cell.getCellType() == 3) { // 判断类型为空
					obj[j] = "";
				} else if (cell.getCellStyle().getDataFormat() == HSSFDataFormat
						.getBuiltinFormat("yyyy/MM/dd")) {
					obj[j] = cell.getDateCellValue();
				}
			}
			data.add(obj);

		}

		dataTable.setRowSize(row);
		dataTable.setColSize(col);
		dataTable.setTitle(getTitleOfSheet(sheetName));
		dataTable.setData(data);
		return dataTable;
	}

}
