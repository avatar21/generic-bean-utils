package com.github.avatar21.generics.utils;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Excel resources utility functions
 */
public class ExcelResources {
    private static Logger logger = Logger.getLogger(ExcelResources.class);

    /**
     * read from a "*.csv" file, return a List (row) of Maps
     *
     * @param clazz bean type class
     * @param excelFileName excel file name
     * @param columnMapping column header mapping to which field
     * @param ignoreNumOfRows ignore line number
     * @param <T> parse bean type
     * @return List of {@link T} objects, which allows you to retrieve row values by name instead of index
     * @throws IOException
     * @throws InvocationTargetException invocation target exception
     * @throws IllegalAccessException illegal access exception
     * @throws NoSuchFieldException no such method exception
     * @throws InstantiationException instantiation exception
     */
    public static <T> List<T> parseBeanCollectionFromExcel(Class<T> clazz, String excelFileName, Map<Integer, String> columnMapping, Integer ignoreNumOfRows)
        throws IOException, InvocationTargetException, IllegalAccessException, NoSuchFieldException, InstantiationException, NoSuchMethodException {
        List<T> listData = new ArrayList<>();
        int totalRows = 0;
        int parsedRows = 0;

        // Finds the workbook instance for XLSX file
        FileInputStream fis = new FileInputStream(excelFileName);
        XSSFSheet mySheet;
        try (XSSFWorkbook myWorkBook = new XSSFWorkbook(fis)) {

            // Return first sheet from the XLSX workbook
            mySheet = myWorkBook.getSheetAt(0);
        }

        totalRows = mySheet.getLastRowNum() + 1;

        // Get iterator to all the rows in current sheet
        Iterator<Row> rowIterator = mySheet.iterator();

        // ignore null value while setting properties, fixes a bug where setting nullable type (e.g.: Date)
        BeanUtilsBean.getInstance().getConvertUtils().register(false, false, 0);
        // Traversing over each row of XLSX file
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            ++parsedRows;
            if (ignoreNumOfRows != null && ignoreNumOfRows > 0 && parsedRows <= ignoreNumOfRows) {
                // do nothing
            } else {
                T instance = clazz.getDeclaredConstructor().newInstance();
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    Integer key = cell.getColumnIndex();

                    if (columnMapping.containsKey(key)) {
                        String cellVal = null;
                        String fieldName = columnMapping.get(key);
                        Class<?> fieldClazz = GenericBeanUtils.getDeclaredField(clazz, fieldName).getType();

                        switch (cell.getCellTypeEnum()) {
                            case STRING:
                                cellVal = cell.getStringCellValue();
                                break;
                            case NUMERIC:
                                cellVal = String.valueOf(cell.getNumericCellValue());
                                break;
                            case BOOLEAN:
                                cellVal = String.valueOf(cell.getBooleanCellValue());
                                break;
                            case _NONE:
                                cellVal = String.valueOf(cell.getStringCellValue());
                                break;
                            default:
                                cellVal = cell.getStringCellValue();
                                break;
                        }
                        GenericBeanUtils.setProperty(
                                instance,
                                fieldName,
                                GenericBeanUtils.parseStringToGenericType(fieldClazz, cellVal));
                        //logger.info(String.format("行#%d|列#%d[ %s(%s类) \uD83D\uDC49 %s]", row.getRowNum(), cell.getColumnIndex(), fieldName, ((fieldClazz != null)? fieldClazz.getSimpleName(): null), cellVal));
                    }
                }
                listData.add(instance);
            }
        }
        logger.info(String.format("\uD83D\uDE00 测试数据读入结果 = %d / %d", parsedRows, totalRows));

        return listData;
    }

    /**
     * parse given bean from excel file
     *
     * @param clazz given bean class
     * @param columnMapping column mapping with excel header
     * @param ignoreNumOfRows ignore line number
     * @param <T> given bean type
     * @return list of parsed bean
     */
    public static <T> List<T> parseBeanCollectionFromExcel(Class<T> clazz, Map<Integer, String> columnMapping, Integer ignoreNumOfRows) {
        List<T> result = null;
        try {
            String fileName = String.format("data/shared/excel/%s-list.xlsx", clazz.getSimpleName());
            URL url = Thread.currentThread().getContextClassLoader().getResource(fileName);
            System.out.println(String.format("filename = %s", fileName));
            if (url != null) {
                result = parseBeanCollectionFromExcel(clazz, url.getPath(), columnMapping, ignoreNumOfRows);
            } else {
                throw new FileNotFoundException(String.format("Following path not found: \"%s\"", fileName));
            }
        } catch (IOException | InvocationTargetException | IllegalAccessException | NoSuchFieldException | InstantiationException | NoSuchMethodException e) {
            logger.error(e.getLocalizedMessage());
        }
        return result;
    }
}
