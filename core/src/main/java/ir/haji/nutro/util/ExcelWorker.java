package ir.haji.nutro.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * Created by Saeedon 8/6/2019.
 */
public class ExcelWorker {
    XSSFWorkbook workbook = new XSSFWorkbook();
    XSSFSheet activeSheet;
    Row activeRow;
    private int rowIndex;
    private int columnIndex;

    public ExcelWorker addSheet(String sheetName) {
        activeSheet = workbook.createSheet(sheetName);
        activeSheet.setRightToLeft(true);
        rowIndex = 0;
        return this;
    }

    public ExcelWorker merge(int rowFrom, int rowTo, int colFrom, int colTo) {
        activeSheet.addMergedRegion(new CellRangeAddress(rowFrom, rowTo, colFrom, colTo));
        return this;
    }

    public XSSFSheet getActiveSheet() {
        if (activeSheet == null)
            addSheet(null);
        return activeSheet;
    }

    public ExcelWorker addRow() {
        activeRow = activeSheet.createRow(rowIndex++);
        columnIndex = 0;
        return this;
    }

    XSSFCellStyle defaultStyle = null;

    public ExcelWorker setCellValue(Object value) {

        if (defaultStyle == null) {
            defaultStyle = workbook.createCellStyle();
            defaultStyle.setAlignment(HorizontalAlignment.CENTER);
            defaultStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            defaultStyle.getFont().setBold(false);
            defaultStyle.getFont().setFontHeight((short) (13 * 20));
        }
        setCellValue(value, defaultStyle);
        return this;
    }

    public ExcelWorker setCellFormula(String formula) {
        Cell cell = activeRow.createCell(columnIndex++);
        if (formula == null) {
            cell.setCellValue("");
        } else {
            cell.setCellFormula(formula);
        }
        return this;
    }

    public ExcelWorker setCellValue(Object value, CellStyle style) {
        Cell cell = activeRow.createCell(columnIndex++);
        if (style != null)
            cell.setCellStyle(style);

        if (value == null) {
            cell.setCellValue("");
        } else if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        } else if (value instanceof BigDecimal) {
            cell.setCellValue(((BigDecimal) value).doubleValue());
        } else {
            cell.setCellValue(value.toString());
        }
        return this;
    }

    public byte[] toByteArray() throws IOException {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        outputStream.close();
        outputStream.flush();
        return outputStream.toByteArray();

    }

    public StyleMaker getStyleMaker() {
        return new StyleMaker();
    }

    public class StyleMaker {

        private XSSFCellStyle style = workbook.createCellStyle();
        ;

        public StyleMaker center() {
            style.setAlignment(HorizontalAlignment.CENTER);
            style.setVerticalAlignment(VerticalAlignment.CENTER);
            return this;
        }

        public StyleMaker fontSize(int fontSize) {
            style.getFont().setFontHeight((short) (fontSize * 20));
            return this;
        }

        public StyleMaker bold() {
            style.getFont().setBold(true);
            return this;
        }

        public XSSFCellStyle getStyle() {
            return style;
        }
    }

    private static String alphabet = "ABCDEFGHIJKLMNOPQRSTUIWXYZ";

    public static String getColumnAddress(Integer index) {
        if (index <= 26)
            return alphabet.substring(index - 1, index);
        Integer bigIndex = index / 26;
        index = index - (bigIndex * 26);
        index = index == 0 ? 26 : index;
        return getColumnAddress(bigIndex) + getColumnAddress(index);
    }
}
