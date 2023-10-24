package com.amand.Utils;

import com.amand.constant.SystemConstant;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ProductExcelExporter {

    private XSSFWorkbook workbook;

    private XSSFSheet sheet;

    private void writeHeaderLine() {
        sheet = workbook.createSheet("Products");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(13);
        style.setFont(font);
        createCell(row, SystemConstant.COLUMN_INDEX_NAME, SystemConstant.NAME_PRODUCT, style );
        createCell(row, SystemConstant.COLUMN_INDEX_AMOUNT, SystemConstant.AMOUNT, style );
        createCell(row, SystemConstant.COLUMN_INDEX_SALE_PRICE, SystemConstant.SALE_PRICE, style );
        createCell(row, SystemConstant.COLUMN_INDEX_SEASON, SystemConstant.SEASON, style );
        createCell(row, SystemConstant.COLUMN_INDEX_CATEGORY_CODE, SystemConstant.CATEGORY_CODE, style );
        createCell(row, SystemConstant.COLUMN_INDEX_OLD_PRICE, SystemConstant.OLD_PRICE, style );
        createCell(row, SystemConstant.COLUMN_INDEX_SIZE_M, SystemConstant.SIZE_M, style );
        createCell(row, SystemConstant.COLUMN_INDEX_SIZE_L, SystemConstant.SIZE_L, style );
        createCell(row, SystemConstant.COLUMN_INDEX_SIZE_38, SystemConstant.SIZE_38, style );
        createCell(row, SystemConstant.COLUMN_INDEX_SIZE_39, SystemConstant.SIZE_39, style );
        createCell(row, SystemConstant.COLUMN_INDEX_SIZE_40, SystemConstant.SIZE_40, style );
        createCell(row, SystemConstant.COLUMN_INDEX_SIZE_41, SystemConstant.SIZE_41, style );
        createCell(row, SystemConstant.COLUMN_INDEX_COLOR_WHITE, SystemConstant.COLOR_WHITE, style );
        createCell(row, SystemConstant.COLUMN_INDEX_COLOR_BROWN, SystemConstant.COLOR_BROWN, style );
        createCell(row, SystemConstant.COLUMN_INDEX_COLOR_BLACK, SystemConstant.COLOR_BLACK, style );
        createCell(row, SystemConstant.COLUMN_INDEX_COLOR_VIOLET, SystemConstant.COLOR_VIOLET, style );

    }

    private void createCell(Row row, int columnCount, Object value, CellStyle cellStyle) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof String) {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(cellStyle);

    }

    public void export(HttpServletResponse response) throws IOException {
        workbook = new XSSFWorkbook();
        writeHeaderLine();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}
