package com.heshan.springboot.web.platform.common.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

    private int sheetPage = 0;
    private Workbook workbook = null;

    public ExcelUtils(InputStream input) throws Exception {
        setWorkBook(input);
    }

    public ExcelUtils(String filePath) throws Exception {
        setWorkBook(new FileInputStream(filePath));
    }

    private void setWorkBook(InputStream input) throws Exception {
        try {
            workbook = new XSSFWorkbook(input);
            input.close();
        } catch (IOException e1) {
            try {
                workbook = new HSSFWorkbook(input);
                input.close();
            } catch (IOException e2) {
                throw new Exception("文件读取失败");
            }
        }
    }

    public Workbook getWorkbook() {
        return workbook;
    }

    public void setWorkbook(Workbook workbook) {
        this.workbook = workbook;
    }

    /** 获取总页数 */
    public int getSheetSum() {
        return workbook.getNumberOfSheets();
    }

    /**
     * 设置当前页数
     */
    public void openSheet(int sheetPage) {
        this.sheetPage = sheetPage;
    }

    /**
     * 获取默认页
     */
    public Sheet getSheet() {
        return workbook.getSheetAt(sheetPage);
    }

    /**
     * 获取指定页
     */
    public Sheet getSheet(int sheetPage) {
        return workbook.getSheetAt(sheetPage);
    }

    /** 获取当前页总列数 */
    public int getColumnSums() {
        return getColumnSums(sheetPage);
    }

    /** 获取指定页总列数 */
    public int getColumnSums(int sheetPage) {
        return getSheet(sheetPage).getDefaultColumnWidth();
    }

    /** 获取当前页总行数 */
    public int getRowSums() {
        return getRowSums(sheetPage);
    }

    /** 获取指定页总行数 */
    public int getRowSums(int sheetPage) {
        return getSheet(sheetPage).getLastRowNum();
    }

    /**
     * 获取当前页数单元格内数据
     *
     * @param x 行
     * @param y 列
     */
    public String getContents(int x, int y) {
        return getContents(sheetPage, x, y);
    }

    /**
     * 获取指定单元格内数据
     *
     * @param sheetPage 页数
     * @param x 行
     * @param y 列
     */
    public String getContents(int sheetPage, int x, int y) {
        Sheet sheet = getSheet(sheetPage);
        Row row = null;
        Cell cell = null;
        if(sheet != null)
            row = sheet.getRow(x);
        if(row != null)
            cell = row.getCell(y);
        return getStringValueFromCell(cell);
    }

    /** 获取当前页页名 */
    public String getSheetName() {
        return getSheetName(sheetPage);
    }

    /** 获取指定页页名 */
    public String getSheetName(int sheetPage) {
        return getSheet(sheetPage).getSheetName();
    }

    public void close() throws IOException {
        workbook.close();
    }

    public String getStringValueFromCell(Cell cell) {
        SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");
        DecimalFormat decimalFormat = new DecimalFormat("#.######");
        String cellValue = "";
        if (cell == null || cell.toString().trim().equals("")) {
            return cellValue;
        } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
            cellValue = cell.getStringCellValue().trim();
        }

        else if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
            if (HSSFDateUtil.isCellDateFormatted(cell)) {
                double d = cell.getNumericCellValue();
                Date date = HSSFDateUtil.getJavaDate(d);
                cellValue = sFormat.format(date);
            } else {
                cellValue = decimalFormat.format((cell.getNumericCellValue()));
            }
        } else if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {
            cellValue = "";
        } else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
            cellValue = String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellType() == Cell.CELL_TYPE_ERROR) {
            cellValue = "";
        } else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
            cellValue = cell.getCellFormula().toString();
        }
        return cellValue;
    }

}
