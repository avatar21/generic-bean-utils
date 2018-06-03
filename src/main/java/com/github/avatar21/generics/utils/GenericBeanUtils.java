package com.github.avatar21.generics.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.avatar21.generics.constants.Regexp;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

/**
 * <p>generic bean utility functions</p>
 */
public class GenericBeanUtils {
    private static final Logger logger = LoggerFactory.getLogger(GenericBeanUtils.class);

    private static ObjectMapper jsonMapper;
    /**
     * <p>
     *     parse and convert into supported Java & enum type (from string), e.g:
     *     <pre><code>
     *         Double mDouble = (Double) GenericBeanUtils.parseStringToGenericType(Double.class, "1.2");
     *         SexEnum sex = (SexEnum) GenericBeanUtils.parseStringToGenericType(SexEnum.class, "MALE");
     *         Boolean mBoolean = (Boolean) GenericBeanUtils.parseStringToGenericType(Boolean.class, "0");
     *         Boolean mBoolean2 = (Boolean) GenericBeanUtils.parseStringToGenericType(Boolean.class, "true");
     *     </code></pre>
     * </p>
     *
     * @param fieldClazz field/ property class
     * @param fieldVal field/ property value (in String)
     * @return parsed typed-instance
     */
    public static Object parseStringToGenericType(Class fieldClazz, String fieldVal) {
        Object retVal = null;
        if (fieldClazz.isEnum()) {
            // remove non-generic parsing to resolve recursive dependency issue
            /*if (fieldClazz.isAssignableFrom(IDescriptiveEnum.class)) {
                try {
                    retVal = EnumObjectUtils.getByGenericCode(fieldClazz, fieldVal);
                } catch (ClassNotFoundException e) {
                    logger.error(e.getLocalizedMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {*/
                retVal = parseStringToGenericEnumType(fieldClazz, fieldVal);
            //}
        } else {
            retVal = parseStringToJavaType(fieldClazz, fieldVal);
        }
        return retVal;
    }

    /**
     * <p>
     *     parse and convert into supported Java type, e.g.:
     *     <pre>
     *         Double mDouble = GenericBeanUtils.parseStringToJavaType(Double.class, "1.2");
     *         Boolean mBoolean = GenericBeanUtils.parseStringToJavaType(Boolean.class, "0");
     *         Boolean mBoolean2 = GenericBeanUtils.parseStringToJavaType(Boolean.class, "true");
     *     </pre>
     * </p>
     *
     * @param fieldClazz field/ property class
     * @param fieldVal field/ property value
     * @param <T> fieldClazz type
     * @return T parsed typed-instance
     */
    public static <T> T parseStringToJavaType(Class<T> fieldClazz, String fieldVal) {
        T retVal = null;

        // Enum & DescriptiveEnum can be handle generically
        if (!fieldClazz.isEnum() && !StringUtils.isEmpty(fieldVal)) {
            switch (fieldClazz.getCanonicalName()) {
                case "java.util.Date":
                    retVal = fieldClazz.cast(genericParseDate(fieldVal));
                    break;
                case "java.lang.Byte":
                    retVal = fieldClazz.cast((fieldVal.matches(Regexp.BYTE_REGEX)) ? Byte.valueOf(fieldVal) : null);
                    break;
                case "java.lang.Character":
                    retVal = fieldClazz.cast((fieldVal.length() == 1) ? fieldVal.charAt(0) : null);
                    break;
                case "java.lang.Short":
                    retVal = fieldClazz.cast((fieldVal.matches(Regexp.SHORT_REGEX)) ? Short.valueOf(fieldVal) : null);
                    break;
                case "java.lang.Integer":
                    retVal = fieldClazz.cast((fieldVal.matches(Regexp.INTEGER_REGEX)) ? Integer.valueOf(fieldVal) : null);
                    break;
                case "java.lang.Float":
                    retVal = fieldClazz.cast((fieldVal.matches(Regexp.NUMBER_REGEX)) ? Float.valueOf(fieldVal) : null);
                    break;
                case "java.lang.Double":
                    retVal = fieldClazz.cast((fieldVal.matches(Regexp.NUMBER_REGEX)) ? Double.valueOf(fieldVal) : null);
                    break;
                case "java.lang.Long":
                    retVal = fieldClazz.cast((fieldVal.matches(Regexp.LONG_REGEX)) ? Long.valueOf(fieldVal) : null);
                    break;
                case "java.math.BigDecimal":
                    retVal = fieldClazz.cast((fieldVal.matches(Regexp.NUMBER_REGEX)) ? new BigDecimal(fieldVal) : null);
                    break;
                case "java.lang.Boolean":
                    Boolean isBoolean = (fieldVal.matches(Regexp.BOOLEAN_REGEX));
                    Boolean isBinary = (fieldVal.matches(Regexp.BINARY_REGEX));
                    retVal = fieldClazz.cast(isBoolean ? Boolean.parseBoolean(fieldVal) : (isBinary ? (fieldVal.equals("1") ? true : false) : null));
                    break;
                case "java.lang.String":
                    retVal = (T) fieldVal;
                    break;
                default:
                    retVal = fieldClazz.cast(fieldVal);
                    break;
            }
        }

        return retVal;
    }

    /**
     * <p>
     *     可解析并自动转换的支持枚举类, 示例:
     *     <pre>
     *         SexEnum sex = (SexEnum) GenericBeanUtils.parseStringToGenericEnumType(SexEnum.class, "MALE");
     *     </pre>
     * </p>
     *
     * @param fieldClazz enum type class
     * @param fieldVal enum value in string
     * @param <E> fieldClazz given enum type
     * @return E parsed given enum type instance
     */
    public static <E extends Enum<E>> E parseStringToGenericEnumType(Class<E> fieldClazz, String fieldVal) {
        //return EnumObjectUtils.stringToEnum(fieldClazz, fieldVal);
        return E.valueOf(fieldClazz, fieldVal);
    }

    /**
     * <p>以"函数名"获取所属类内的"属性名"</p>
     *
     * @param declaringClass 函数所属的类 {@link Class}
     * @param method 函数 {@link Method}
     * @param <T> 函数所属的泛类T
     * @return 属性名
     */
    public static <T> String getFieldName(Class<T> declaringClass, Method method) {
        try {
            Class<?> clazz = (declaringClass == null) ? method.getDeclaringClass() : declaringClass;
            BeanInfo info = Introspector.getBeanInfo(clazz);
            PropertyDescriptor[] props = info.getPropertyDescriptors();
            //logger.debug("pd.properties = {}", JSON.toJSONString(props, true));
            for (PropertyDescriptor pd : props)
            {
                if(method.equals(pd.getWriteMethod()) || method.equals(pd.getReadMethod()))
                {
//                    logger.debug("{}.{}", clazz.getSimpleName(), pd.getName());
                    return pd.getName();
                }
            }
        } catch (IntrospectionException e) {
            logger.error(e.getLocalizedMessage());
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
        }

        return null;
    }

    /**
     * <p>从一个Bean(源)同名属性的值拷贝/赋值到另一个Bean(目的)里</p>
     *
     * @param dest 目的Bean/ 子类Bean
     * @param source 源Bean/ 父类Bean
     */
    public static void copyProperties(Object dest, Object source) {
        /*try {
            BeanUtils.copyProperties(dest, source);
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
        }*/
        // 兼容 枚举类
        BeanUtilsBean beanUtilsBean = new BeanUtilsBean(new ConvertUtilsBean() {
            @Override
            public Object convert(String value, Class clazz) {
                if (clazz.isEnum()){
                    return Enum.valueOf(clazz, value);
                } else {
                    return super.convert(value, clazz);
                }
            }
        });
        try {
            beanUtilsBean.copyProperties(dest, source);
        } catch (ConversionException | IllegalAccessException | InvocationTargetException e) {
            logger.error("Bean赋值报错", e);
        }
    }

    /**
     * return single instance of ObjectMapper
     *
     * @return {@link ObjectMapper} instance
     */
    private static ObjectMapper getJsonMapper() {
        if (jsonMapper == null) {
            jsonMapper = new ObjectMapper();
            jsonMapper.enable(SerializationFeature.INDENT_OUTPUT);
        }
        return jsonMapper;
    }

    /**
     * <p>解析对象为json格式字符串</p>
     *
     * @param obj 对象
     * @return obj以json格式呈现
     */
    public static String toJson(Object obj) {
        String json = null;
        try {
            json = getJsonMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            logger.error("无法解析对象为json格式", e);
        }

        return json;
    }

    /**
     * <p>以字符串解析日期</p>
     *
     * @param strDateVal 日期字符串
     * <p>
     *   支持日期格式:
     *   <ul>
     *     <li>yyyy-MM-dd</li>
     *     <li>yyyy-MM-dd HH:mm:ss</li>
     *     <li>yyyy-MM-dd HH:mm:ss.SSS</li>
     *   </ul>
     * </p>
     * @return
     */
    public static Date genericParseDate(String strDateVal) {
        Date date = null;
        try {
            if (strDateVal.matches(Regexp.DATE_FORMAT_DATE_ONLY_REGEX)) {
                date = Regexp.DATE_FORMAT_DATE_ONLY.parse(strDateVal);
            } else if (strDateVal.matches(Regexp.DATE_FORMAT_FULL_WITH_SEC_REGEX)) {
                date = Regexp.DATE_FORMAT_FULL_WITH_SEC.parse(strDateVal);
            } else if (strDateVal.matches(Regexp.DATE_FORMAT_FULL_WITH_MISEC_REGEX)) {
                date = Regexp.DATE_FORMAT_FULL_WITH_MISEC.parse(strDateVal);
            } else {
                date = null;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    /**
     * <p>Set the specified property value, performing type conversions as
     * required to conform to the type of the destination property.</p>
     *
     * <p>For more details see <code>BeanUtilsBean</code>.</p>
     *
     * @param bean Bean on which setting is to be performed
     * @param name Property name (can be nested/indexed/mapped/combo)
     * @param value Value to be set
     *
     * @exception IllegalAccessException if the caller does not have
     *  access to the property accessor method
     * @exception InvocationTargetException if the property accessor method
     *  throws an exception
     * @see BeanUtilsBean#setProperty
     */
    public static void setProperty(Object bean, String name, Object value)
            throws IllegalAccessException, InvocationTargetException {

        BeanUtilsBean.getInstance().setProperty(bean, name, value);
    }

    /**
     * get declared field from class by property name
     *
     * @param clazz belonging class
     * @param propertyName property name
     * @return
     * @throws NoSuchFieldException
     */
    public static Field getDeclaredField(Class clazz, String propertyName) throws NoSuchFieldException {
        Assert.notNull(clazz);
        Assert.hasText(propertyName);
        Class superClass = clazz;

        while(superClass != Object.class) {
            try {
                return superClass.getDeclaredField(propertyName);
            } catch (NoSuchFieldException var4) {
                superClass = superClass.getSuperclass();
            }
        }

        throw new NoSuchFieldException("No such field: " + clazz.getName() + '.' + propertyName);
    }
}
