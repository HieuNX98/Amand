package com.amand.Utils;

import com.amand.constant.SystemConstant;
import com.amand.form.ProductForm;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class FileUtils {

    @Autowired
    private Cloudinary cloudinary;

    public String uploadFile(MultipartFile file) {
        try {
            log.info("Upload file to cloudinary with name {}", file.getOriginalFilename());
            Map result = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("resource_type","auto"));
            return (String) result.get("secure_url");
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    public static List<ProductForm> readExcel(MultipartFile file) throws IOException {
        List<ProductForm> productForms = new ArrayList<>();
        List<String> sizeNames = new ArrayList<>();
        List<String> colorNames = new ArrayList<>();
        Workbook workbook = getWorkBook(file);
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = sheet.iterator();
        while (iterator.hasNext()) {
            Row nextRow = iterator.next();
            if (nextRow.getRowNum() == 0) {
                continue;
            }
            Iterator<Cell> cellIterator = nextRow.iterator();
            ProductForm productForm = new ProductForm();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                Object cellValue = getCellValue(cell);
                String sizeName;
                String colorName;
                int status;
                if (cellValue == null || cellValue.toString().isEmpty()) {
                    continue;
                }
                int columnIndex = cell.getColumnIndex();
                switch(columnIndex) {
                    case SystemConstant.COLUMN_INDEX_NAME:
                        productForm.setName((String) cellValue);
                        break;
                    case SystemConstant.COLUMN_INDEX_AMOUNT:
                        productForm.setAmount(((Double) cellValue).intValue());
                        break;
                    case SystemConstant.COLUMN_INDEX_SALE_PRICE:
                        productForm.setSalePrice((Double) cellValue);
                        break;
                    case SystemConstant.COLUMN_INDEX_SEASON:
                        productForm.setSeason(String.valueOf(((Double) cellValue).intValue()));
                        break;
                    case SystemConstant.COLUMN_INDEX_CATEGORY_CODE:
                        productForm.setCategoryCode( cellValue.toString());
                        break;
                    case SystemConstant.COLUMN_INDEX_OLD_PRICE:
                        productForm.setOldPrice((Double) cellValue);
                        break;
                    case SystemConstant.COLUMN_INDEX_SIZE_M:
                        sizeName = "M";
                        status = ((Double) cellValue).intValue();
                        setSize(productForm, sizeNames, status, sizeName);
                        break;
                    case SystemConstant.COLUMN_INDEX_SIZE_L:
                        sizeName = "L";
                        status = ((Double) cellValue).intValue();
                        setSize(productForm, sizeNames, status, sizeName);
                        break;
                    case SystemConstant. COLUMN_INDEX_SIZE_38:
                        sizeName = "38";
                        status = ((Double) cellValue).intValue();
                        setSize(productForm, sizeNames, status, sizeName);
                        break;
                    case SystemConstant.COLUMN_INDEX_SIZE_39:
                        sizeName = "39";
                        status = ((Double) cellValue).intValue();
                        setSize(productForm, sizeNames, status, sizeName);
                        break;
                    case SystemConstant.COLUMN_INDEX_SIZE_40:
                        sizeName = "40";
                        status = ((Double) cellValue).intValue();
                        setSize(productForm, sizeNames, status, sizeName);
                        break;
                    case SystemConstant.COLUMN_INDEX_SIZE_41:
                        sizeName = "41";
                        status = ((Double) cellValue).intValue();
                        setSize(productForm, sizeNames, status, sizeName);
                        break;
                    case SystemConstant.COLUMN_INDEX_COLOR_WHITE:
                        colorName = "Trắng";
                        status = ((Double) cellValue).intValue();
                        setColor(productForm, colorNames, status, colorName);
                        break;
                    case SystemConstant.COLUMN_INDEX_COLOR_BROWN:
                        colorName = "Nâu";
                        status = ((Double) cellValue).intValue();
                        setColor(productForm, colorNames, status, colorName);
                        break;
                    case SystemConstant.COLUMN_INDEX_COLOR_BLACK:
                        colorName = "Đen";
                        status = ((Double) cellValue).intValue();
                        setColor(productForm, colorNames, status, colorName);
                        break;
                    case SystemConstant.COLUMN_INDEX_COLOR_VIOLET:
                        colorName = "Tím";
                        status = ((Double) cellValue).intValue();
                        setColor(productForm, colorNames, status, colorName);
                        break;
                }
            }
            productForms.add(productForm);
        }
        workbook.close();
        return productForms;
    }

    private static void setColor(ProductForm productForm, List<String> colorNames, int status, String colorName) {
        if (status != 1) return;
        colorNames.add(colorName);
        productForm.setColorNames(colorNames);
    }

    private static void setSize(ProductForm productForm, List<String> sizeNames, int status,  String sizeName) {
        if (status != 1) return;
        sizeNames.add(sizeName);
        productForm.setSizeNames(sizeNames);
    }

    private static Workbook getWorkBook(MultipartFile file) throws IOException {
        Workbook workbook;
        if (file.getOriginalFilename().endsWith("xlsx")) {
            workbook = new XSSFWorkbook(file.getInputStream());
        } else if (file.getOriginalFilename().endsWith("xls")) {
            workbook = new HSSFWorkbook(file.getInputStream());
        } else {
            throw new IllegalArgumentException("The specified file is not Excel file");
        }
        return workbook;
    }

    private static Object getCellValue(Cell cell) {
        Object cellValue = null;
        switch (cell.getCellType()) {
            case SystemConstant.CELL_TYPE_NUMERIC:
                cellValue = cell.getNumericCellValue();
                break;
            case SystemConstant.CELL_TYPE_STRING:
                cellValue = cell.getStringCellValue();
                break;
            case SystemConstant.CELL_TYPE_BLANK:
            case SystemConstant.CELL_TYPE_ERROR:
                break;
            default:
                break;
        }
        return cellValue;
    }


}
