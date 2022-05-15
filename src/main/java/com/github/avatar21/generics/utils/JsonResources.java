package com.github.avatar21.generics.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.avatar21.generics.constants.Regexp;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * JSON utility functions
 */
@Slf4j
public class JsonResources {

    /**
     * <p>parse json file into bean collection</p>
     *
     * @param <T> given bean class
     * @param jsonFileName "*.json" file path
     * @param clazz given bean class
     * @return 返回 {@link List}T typed array list
     * @throws Exception exception
     */
    public static <T> List<T> parseBeanCollectionFromJson(String jsonFileName, Class<T> clazz) throws Exception {
        List<T> listData = null;
        InputStream jsonFis = null;
        URL url = Thread.currentThread().getContextClassLoader().getResource(jsonFileName);
        String filePath = url.getPath();
        File jsonFile = new File(filePath);
        String fileContent;
        ObjectMapper jsonMapper = new ObjectMapper();
        jsonMapper.setDateFormat(Regexp.DATE_FORMAT_FULL_WITH_SEC);

        try {
            // read currency data file
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
            log.error("error while parsing bean from json", e);
            throw e;
        } finally {
            // housekeeping
            if (jsonFis != null) {
                jsonFis.close();
            }
        }
        log.info("\uD83D\uDE00 parsed result = {}", GenericBeanUtils.toJson(listData));

        return listData;
    }

    /**
     * <p>parse json into bean(single)</p>
     *
     * @param <T> bean type T
     * @param jsonFileName "*.json" file path
     * @param clazz bean class
     * @return parsed bean of type T instance
     */
    public static <T> T parseBeanFromJson(String jsonFileName, Class<T> clazz) {
        T bean = null;
        InputStream jsonFis = null;
        URL url = Thread.currentThread().getContextClassLoader().getResource(jsonFileName);
        String filePath = url.getPath();
        File jsonFile = new File(filePath);
        String fileContent = null;
        ObjectMapper jsonMapper = new ObjectMapper();
        jsonMapper.setDateFormat(Regexp.DATE_FORMAT_FULL_WITH_SEC);

        try {
            // read json formatted file
            log.debug("valid = {} | {}", (jsonFile != null), (jsonFile.isFile()));
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
            }
        } catch (Exception e) {
            log.error("error while parsing json to bean", e);
        } finally {
            if (jsonFis != null) {
                try {
                    jsonFis.close();
                } catch (IOException e) {
                    log.error(e.getLocalizedMessage());
                }
            }
        }
        log.info("\uD83D\uDE00 parsed bean = {}", GenericBeanUtils.toJson(bean));

        return bean;
    }
}
