package com.stq.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;
import com.stq.util.HttpClientUts;
import com.sun.el.parser.ParseException;

import net.sf.json.JSONObject;

import org.apache.poi.ss.usermodel.Cell;  
import org.apache.poi.ss.usermodel.Row;  
import org.apache.poi.ss.usermodel.Sheet;  
import org.apache.poi.ss.usermodel.Workbook;  
import org.apache.poi.ss.usermodel.WorkbookFactory;  
import org.apache.poi.ss.usermodel.DateUtil;  


@ContextConfiguration(locations = {"/applicationContext.xml"})
public class TeTest3 extends SpringTransactionalTestCase{
	
	@Test
	public void test() throws IOException, ParseException {
		readExcel();
	}
	
	/**
	 * 主分类
	 */
	public void readExcel() {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			// 同时支持Excel 2003、2007
			File excelFile = new File("/Users/admin/Desktop/muban/关键词.xlsx"); // 创建文件对象
			FileInputStream is = new FileInputStream(excelFile); // 文件流
			Workbook workbook = WorkbookFactory.create(is); // 这种方式 Excel
															// 2003/2007/2010
															// 都是可以处理的
			int sheetCount = workbook.getNumberOfSheets(); // Sheet的数量
			// 遍历每个Sheet
			for (int s = 0; s < sheetCount; s++) {
				Sheet sheet = workbook.getSheetAt(s);
				int rowCount = sheet.getPhysicalNumberOfRows(); // 获取总行数
				// 遍历每一行
				int i = 1;
				for (int r = 1; r < rowCount; r++) {
					Row row = sheet.getRow(r);
					int cellCount = row.getPhysicalNumberOfCells(); // 获取总列数
					// 遍历每一列
					for (int c = 0; c < cellCount; c++) {
						if(c==0||c==3||c==11){
							Cell cell = row.getCell(c);
							if(cell!=null){
								int cellType = cell.getCellType();
								String cellValue = null;
								switch (cellType) {
								case Cell.CELL_TYPE_STRING: // 文本
									cellValue = cell.getStringCellValue();
									break;
								case Cell.CELL_TYPE_NUMERIC: // 数字、日期
									if (DateUtil.isCellDateFormatted(cell)) {
										cellValue = fmt.format(cell.getDateCellValue()); // 日期型
									} else {
										cellValue = String.valueOf(cell.getNumericCellValue()); // 数字
									}
									break;
								case Cell.CELL_TYPE_BOOLEAN: // 布尔型
									cellValue = String.valueOf(cell.getBooleanCellValue());
									break;
								case Cell.CELL_TYPE_BLANK: // 空白
									cellValue = cell.getStringCellValue();
									break;
								case Cell.CELL_TYPE_ERROR: // 错误
									cellValue = "错误";
									break;
								case Cell.CELL_TYPE_FORMULA: // 公式
									cellValue = "错误";
									break;
								default:
									cellValue = "错误";
								}
								if(c==0){
									System.out.println(cellValue);
									try {
										
								    	JSONObject obj   = new JSONObject();
								    	obj.put("keyword",cellValue);
								    	obj.put("startDate","2016-01-01");
								    	obj.put("endDate","");
								    	obj.put("category","ivst");
								        String resMessage=HttpClientUts.post("http://127.0.0.1/stq/api/v1/words/addWordsTask", obj.toString());
								        System.out.println(resMessage + "  " + cellValue + " " + i++);
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
    /**
     * B方法追加文件：使用FileWriter
     */
    public static void writeFile(String fileName, String content) {
        try {
            //打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            FileWriter writer = new FileWriter(fileName, true);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
