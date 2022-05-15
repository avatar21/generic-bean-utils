package com.github.avatar21.generics.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.concurrent.atomic.AtomicReference;

/**
 * <p>XML utility functions</p>
 */
@Slf4j
public class XmlUtils {

    /**
     * <p>格式化xml内容</p>
     *
     * @param unformattedXml - 未格式化的 XML 字符串内容
     * @return 格式化好的 XML {@link String} 类
     */
    public static String format(String unformattedXml) {
        try {

            Writer out = new StringWriter();
            AtomicReference<Document> document = new AtomicReference<>(parseXml(unformattedXml));
            OutputFormat format = new OutputFormat((Document) document);
            format.setLineWidth(65);
            format.setIndenting(true);
            format.setIndent(2);
            XMLSerializer serializer = new XMLSerializer(out, format);
            serializer.serialize((Document) document);

            return out.toString();
        } catch (IOException | NullPointerException e) {
            log.debug("格式化xml文件异常", e);
            return "";
        }
    }

    /**
     * <p>把xml字符串内容转换成Dom类方便解析</p>
     *
     * @param content - XML 内容
     * @return {@link Document} 类
     */
    public static Document parseXml(String content) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(content));
            return db.parse(is);
        } catch (ParserConfigurationException e) {
            log.error("获取资源异常", e);
            throw new RuntimeException(e);
        } catch (SAXException e) {
            log.error("获取资源异常", e);
            throw new RuntimeException(e);
        } catch (IOException e) {
            log.debug("获取资源异常, 返回null", e);
        }
        return null;
    }
}
