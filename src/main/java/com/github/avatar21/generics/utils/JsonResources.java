package com.github.avatar21.generics.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * JSON utility functions
 */
public class JsonResources {
    private static Logger log = Logger.getLogger(JsonResources.class);

    /**
     * <p>从json文件解析通用类集合</p>
     *
     * @param <T> 通用类
     * @param jsonFileName "*.json"文件路径
     * @param clazz 通用类的class
     * @return 返回 {@link List}T类的集合
     */
    public static <T> List<T> parseBeanCollectionFromJson(String jsonFileName, Class<T> clazz) throws Exception {
        List<T> listData = null;
        InputStream jsonFis = null;
        URL url = Thread.currentThread().getContextClassLoader().getResource(jsonFileName);
        String filePath = url.getPath();
        File jsonFile = new File(filePath);
        String fileContent;
        ObjectMapper jsonMapper = new ObjectMapper();
        // TODO remove date format here
        SimpleDateFormat fmtDate = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        jsonMapper.setDateFormat(fmtDate);

        try {
            // read currency data file
            //log.info("csv file = " + csvFile);
            if (jsonFile != null && jsonFile.isFile()) {
                jsonFis = new FileInputStream(jsonFile);

                byte[] bytes = IOUtils.toByteArray(jsonFis);
                if (bytes != null) {
                    fileContent = new String(bytes, "UTF8");
                    if (fileContent != null && fileContent.length() > 0) {
                        listData = jsonMapper.readValue(fileContent, jsonMapper.getTypeFactory().constructCollectionType(List.class, clazz));

                        if (listData != null && listData.size() > 0) {
                            for (T obj : listData) {
                                log.debug(String.format("obj = %s", GenericBeanUtils.toJson(obj)));
                            }
                        }
                    }
                }
            } else {
                log.error(new StringBuffer()
                        .append("json file[").append(jsonFile)
                        .append("] not found").toString());
            }
        } catch (Exception e) {
            log.error("获取地区数据错误", e);
            throw e;
        } finally {
            if (jsonFile != null) {
                jsonFile = null;
            }
            if (jsonFis != null) {
                jsonFis.close();
            }
        }
        log.info(String.format("\uD83D\uDE00 测试数据读入结果 = %s", GenericBeanUtils.toJson(listData)));

        return listData;
    }

    /**
     * <p>从json文件解析通用类集合</p>
     *
     * @param <T> 通用类
     * @param clazz 通用类的class
     * @return 返回 {@link List} T类的集合
     */
    public static <T> List<T> parseBeanCollectionFromJson(Class<T> clazz) {
        String jsonFileName = String.format("data/shared/json/%s-list.json", clazz.getSimpleName());
        System.out.println(String.format("filename = %s", jsonFileName));

        List<T> result = null;
        try {
            result = parseBeanCollectionFromJson(jsonFileName, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * <p>从json文件解析通用类</p>
     *
     * @param <T> 数据类T
     * @param clazz 数据类 T.class
     * @return 数据类实例
     */
    public static <T> T parseBeanFromJson(Class<T> clazz) {
        String jsonFileName = String.format("data/shared/json/%s.json", clazz.getSimpleName());
        System.out.println(String.format("filename = %s", jsonFileName));

        return parseBeanFromJson(jsonFileName, clazz);
    }

    /**
     * <p>从json文件解析通用类</p>
     *
     * @param <T> 数据类T
     * @param jsonFileName "*.json"文件路径
     * @param clazz 数据类 T.class
     * @return 数据类实例
     */
    public static <T> T parseBeanFromJson(String jsonFileName, Class<T> clazz) {
        T bean = null;
        InputStream jsonFis = null;
        URL url = Thread.currentThread().getContextClassLoader().getResource(jsonFileName);
        String filePath = url.getPath();
        File jsonFile = new File(filePath);
        String fileContent = null;
        ObjectMapper jsonMapper = new ObjectMapper();
        SimpleDateFormat fmtDate = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        jsonMapper.setDateFormat(fmtDate);

        try {
            // read json formatted file
            System.out.println(String.format("valid = %s | %s", (jsonFile != null), (jsonFile.isFile())));
            if (jsonFile != null && jsonFile.isFile()) {
                jsonFis = new FileInputStream(jsonFile);

                byte[] bytes = IOUtils.toByteArray(jsonFis);
                if (bytes != null) {
                    fileContent = new String(bytes, "UTF8");
                    if (fileContent != null && fileContent.length() > 0) {
                        bean = jsonMapper.readValue(fileContent, clazz);
                        System.out.println(String.format("parsed bean = %s", GenericBeanUtils.toJson(bean)));
                    }
                }
            } else {
                log.error(new StringBuffer()
                        .append("json file[").append(jsonFile)
                        .append("] not found").toString());
                System.out.println(new StringBuffer()
                        .append("json file[").append(jsonFile)
                        .append("] not found").toString());
            }
        } catch (Exception e) {
            log.error("获取json数据错误", e);
            e.printStackTrace();
        } finally {
            if (jsonFile != null) {
                jsonFile = null;
            }
            if (jsonFis != null) {
                try {
                    jsonFis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        log.info(String.format("\uD83D\uDE00 json数据读入结果 = %s", GenericBeanUtils.toJson(bean)));

        return bean;
    }
}
