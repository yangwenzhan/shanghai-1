package com.tianqiauto.textile.weaving.util.poi;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;

import java.util.*;

/**   
* @Description: 导入工具类
* @author Sunwg  
* @date 2017年12月3日 上午11:53:07
* *********************************************************************************
* 使用方法如下
*    String [] exts = {"xls","xlsx"};
		String fileName = file.getOriginalFilename();
		String ext = fileName.substring(fileName.lastIndexOf(".")+1);
		if (Arrays.asList(exts).contains(ext)) {
			try {
				Map<String, String> keyMap= new HashMap<String, String>();
				keyMap.put("name", "表名");
				keyMap.put("comment", "注释");
				HSSFWorkbook workBook = new HSSFWorkbook(file.getInputStream());
				List<Map<String, String>> ll = changeExcelToList(keyMap, workBook);
				System.out.println(ll);
				System.out.println(changeExcelToListWithTitle(workBook));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			System.out.println("文件类型错误");
		}
***********************************************************************************
*/
public class ImportUtils {
	/** 
	* @Title: changeExcelToList 
	* @Description: TODO 将有中文列头的Excel转换成List
	* @param keyMap 文件的列标题对应字段的键值对
	* @param workBook 需要转换的文件
	* @return List<Map<String,String>>
	*/
	public static List<Map<String,String>> changeExcelToList(Map<String, String> keyMap,HSSFWorkbook workBook){
		List<Map<String,String>> result = new ArrayList<Map<String,String>>();
		Set<String> keys = keyMap.keySet();
		HSSFSheet sheet = workBook.getSheetAt(0);
		HSSFRow row = sheet.getRow(0);
		int colCount = row.getLastCellNum();
		String[] rangeKeys = new String[keys.size()];
		HSSFCell cell = null;
		for (int i = 0; i < colCount; i++) {
			cell = row.getCell(i);
			String title = cell.getStringCellValue();
			A:for (String key : keys) {
				if (keyMap.get(key).equals(title)) {
					rangeKeys[i] = key;
					break A;
				}
			}
		}
		
		int rowCount = sheet.getLastRowNum();
		for (int i = 1; i <= rowCount; i++) {
			row = sheet.getRow(i);
			Map<String, String> item = new HashMap<String, String>();
			for (int j = 0; j < rangeKeys.length; j++) {
				cell = row.getCell(j);
				String val = cell.getStringCellValue().trim();
				item.put(rangeKeys[j],val == null ? "":val);
			}
			result.add(item);
		}
		return result;
	}
	
	public static List<Map<String,String>> changeExcelToListWithTitle(HSSFWorkbook workBook){
		List<Map<String,String>> result = new ArrayList<Map<String,String>>();
		HSSFSheet sheet = workBook.getSheetAt(0);
		HSSFRow row = sheet.getRow(0);
		int colCount = row.getLastCellNum();
		String[] rangeKeys = new String[colCount];
		HSSFCell cell = null;
		for (int i = 0; i < colCount; i++) {
			cell = row.getCell(i);
			String title = cell.getStringCellValue();
			rangeKeys[i] = title == null ? "" + i : title;
		}
		
		int rowCount = sheet.getLastRowNum();
		for (int i = 1; i <= rowCount; i++) {
			row = sheet.getRow(i);
			Map<String, String> item = new HashMap<String, String>();
			for (int j = 0; j < rangeKeys.length; j++) {
				cell = row.getCell(j);
				cell.setCellType(CellType.STRING);
				String val = cell.getStringCellValue();
				item.put(rangeKeys[j],val == null ? "":val);
			}
			result.add(item);
		}
		return result;
	}
	
}
