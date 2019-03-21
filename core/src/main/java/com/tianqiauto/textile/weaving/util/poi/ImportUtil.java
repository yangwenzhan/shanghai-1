package com.tianqiauto.textile.weaving.util.poi;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author bjw
 * @Description: 导入工具类
 */
public class ImportUtil {
    private Workbook workBook;

    public ImportUtil(MultipartFile file) throws IOException {
        boolean isExcel2003 = file.getName().toLowerCase().endsWith("xls") ? true : false;
        if (isExcel2003) {
            workBook = new HSSFWorkbook(file.getInputStream());
        } else {
            workBook = new XSSFWorkbook(file.getInputStream());
        }
    }

    public SheetVo getSheet(int index) {
        return new SheetVo(workBook.getSheetAt(index));
    }

    public class SheetVo {
        private Sheet sheet;

        private SheetVo(Sheet sheet) {
            this.sheet = sheet;
        }

        public String getVal(int x, int y) {
            Row row = sheet.getRow(x);
            Cell cell = row.getCell(y);
            cell.setCellType(CellType.STRING);
            String val = cell.getStringCellValue().trim();
            return val;
        }
    }
}
