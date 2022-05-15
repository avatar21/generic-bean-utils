package com.github.avatar21.generics.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * CSV resources utility functions
 */
@Slf4j
public class CSVResources {

    /**
     * @param token specify the value seperator, by default a "," (comma), "\t" (tab)
     * @param line  for each line retrieved by reading the source csv file
     * @return a String Array containing the separated values
     */
    public static String[] splitCSV(String token, String line) {
        java.util.ArrayList<String> elements = new java.util.ArrayList<String>(); // JAVA >=1.5
        // java.util.ArrayList elements = new java.util.ArrayList(); // JAVA <=1.4
        String csv_pattern = "(?:^|" + token + ")(\"(?:[^\"]|\"\")*\"|[^" + token + "]*)";
        Matcher m = Pattern.compile(csv_pattern).matcher(line);

        while (m.find()) {
            elements.add(m.group().replaceAll("^" + token, "") // remove first comma if any
                    .replaceAll("^?\"(.*)\"$", "$1") // remove outer quotations if any
                    .replaceAll("\"\"", "\"")); // replace double inner quotations if any
        }

        return /*(String[])*/ elements.toArray(new String[0]);
    }

    /**
     * read from a "*.csv" file, return a List (row) of Maps
     * (columns, which using "column name" as key, and value stored in java.lang.* types)
     * csv format must be following below structure:
     * [column_name_1], [column_name_2], [...] &lt;- 1st line (ignore row)
     * [column_value_1], [column_value_2], [...] &lt;- 2nd line (1st row of data)
     * [column_value_1], [column_value_2], [...] &lt;- 3rdh line (2nd row of data, and so on)
     *
     * @param clazz bean class
     * @param csvFileName csv file name
     * @param columnMapping column/ field mapping
     * @param ignoreNumOfRows ignore line no.
     * @param <T> bean type
     * @return List of HashMap objects, which allows you to retrieve row values by name instead of index
     * @throws IOException i/o exception
     * @throws InvocationTargetException invocation exception
     * @throws IllegalAccessException illegal exception
     * @throws NoSuchFieldException no such file exception
     */
    public static <T> List<T> parseBeanFromCSVFile(Class<T> clazz, String csvFileName, Map<Integer, String> columnMapping, Integer ignoreNumOfRows) throws
            IOException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        List<T> listData = null;
        InputStream csvFis = null;
        File csvFile = new File(csvFileName);
        String[] csvRows = null;
        int totalRows = 0;
        int parsedRows = 0;

        try {
            // read currency data file
            //logger.info("csv file = " + csvFile);
            if (csvFile != null && csvFile.isFile()) {
                csvFis = new FileInputStream(csvFileName);

                byte[] bytes = IOUtils.toByteArray(csvFis);
                if (bytes != null) {
                    String fileContent = new String(bytes, "UTF8");
                    csvRows = fileContent.split("\\r?\\n");
                }
                totalRows = csvRows.length;
                parsedRows = 0;
                int startRow = 0;
                if (ignoreNumOfRows != null && ignoreNumOfRows > 0) {
                    startRow = ignoreNumOfRows;
                    parsedRows += ignoreNumOfRows;
                }
                String[] columnValues = null;

                if (columnMapping != null && !columnMapping.isEmpty()) {
                    listData = new ArrayList<>();
                    for (int rowIdx = startRow; rowIdx < totalRows; rowIdx++) {
                        columnValues = splitCSV(",", csvRows[rowIdx]); // total-ignore line (data)
                        if (columnValues != null) {
                            Set<Integer> keys = columnMapping.keySet();

                            //logger.info(String.format("keys = %s", JSON.toJSONString(keys)));
                            try {
                                T obj = null;
                                String strValue = "";
                                obj = clazz.getDeclaredConstructor().newInstance();
                                //logger.info(String.format("columnValues = %s", JSON.toJSONString(columnValues)));
                                for (Integer key : keys) {
                                    strValue = columnValues[key];
                                    String fieldName = columnMapping.get(key);
                                    Class<?> fieldClazz = GenericBeanUtils.getDeclaredField(clazz, columnMapping.get(key)).getType();
                                    //logger.info(String.format("%s(%s) = %s", key, fieldClazz.getCanonicalName(), strValue));
                                    GenericBeanUtils.setProperty(obj, fieldName, GenericBeanUtils.parseStringToGenericType(fieldClazz, strValue));
                                }
                                //logger.info(String.format("[%d] bean = %s", rowIdx, JSON.toJSONString(obj, true)));
                                listData.add(obj);
                                ++parsedRows;
                            } catch (Exception e) {
                                log.error(e.getLocalizedMessage());
                            }
                        }
                    }
                }
            } else {
                log.error(new StringBuffer()
                        .append("csv file[").append(csvFile)
                        .append("] not found").toString());
            }
        } catch (Exception e) {
            log.error("", e);
        } finally {
            if (csvFile != null) {
                csvFile = null;
            }
            if (csvFis != null) {
                csvFis.close();
            }
        }
        log.info(String.format("\uD83D\uDE00 测试数据读入结果 = %d / %d", parsedRows, totalRows));

        return listData;
    }
}
