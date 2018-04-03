package org.scnu.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Properties;

/**
 * @author fanzhe
 * @email fansy1990@foxmail.com
 * @date 2018/4/3 下午9:12.
 */
public class PropertyUtils {
    private final  static String CHARACTER = "utf-8";
    private final static Logger logger = LoggerFactory.getLogger(PropertyUtils.class);

    /**
     * 从系统属性文件中获取相应的值
     *
     * @param key key
     * @return 返回value
     */
    public static String getValue(String key) {
        return System.getProperty(key);
    }


    /**
     * @param classpath 属性文件 （classpath）
     * @return
     */
    public static Properties loadProperties(String classpath) {
        Properties properties = new Properties();
        try {
            try (InputStream in = PropertyUtils.class.getClassLoader().getResourceAsStream(classpath)) {
                properties.load(new InputStreamReader(in, CHARACTER));
                return properties;
            }
        } catch (IOException e) {
            logger.error("加载属性文件出错:" + e.getMessage());
            return null;
        }
    }

    /**
     * 根据Key读取Value
     *
     * @param classPath 属性文件 （classpath）
     * @param key       需要读取的属性
     */
    public static String getValue(String classPath, String key) {
        try {
            Properties properties = loadProperties(classPath);
            return properties.getProperty(key);
        } catch (Exception e) {
            logger.error("获取属性出错:" + e.getMessage());
            return null;
        }
    }

    /**
     * 根据Key读取Value
     *
     * @param classPath    属性文件 （classpath）
     * @param key          需要读取的属性
     * @param defaultValue 属性为null或空时返回默认值
     * @returned 属性值
     */
    public static String getValue(String classPath, String key, String defaultValue) {
        try {
            Properties properties = loadProperties(classPath);
            String value = properties.getProperty(key);

            if ("".equals(value)) {
                value = defaultValue;
            }
            return value;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 根据Key读取Value
     *
     * @param classPath 属性文件 （classpath）
     * @param key       需要读取的属性
     */
    public static void setValue(String classPath, String key, String value) {

        Properties properties = loadProperties(classPath);

        if (properties.containsKey(key)) {
            properties.setProperty(key, value);
            try {
                OutputStream outputStream = new FileOutputStream(PropertyUtils.class.getClassLoader().getResource(classPath).getPath());
                properties.store(outputStream, null);
                outputStream.flush();
                outputStream.close();
            } catch (Exception ex) {
                logger.error("edit value fail, message: {}", ex.getMessage());
            }
        } else {
            throw new NullPointerException("未能在配置文件中查找到对应的key");
        }

    }

    private static LinkedHashMap<String, String> loadProperties(InputStream in) {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        Properties properties = new Properties();
        try {
            properties.load(new InputStreamReader(in, CHARACTER));
        } catch (IOException e) {
            logger.error("属性文件加载出错:" + e.getMessage());
            return null;
        }
        Enumeration en = properties.propertyNames();
        while (en.hasMoreElements()) {
            String strKey = (String) en.nextElement();
            String strValue = properties.getProperty(strKey);
            map.put(strKey, strValue);
        }
        return map;
    }

    /**
     * 读取Properties的全部信息
     *
     * @param file 读取的属性文件
     * @return 返回所有的属性 key:value<>key:value
     */
    public static LinkedHashMap<String, String> getProperties(File file) {
        try {
            try (InputStream in = new BufferedInputStream(new FileInputStream(file))) {
                return loadProperties(in);
            }
        } catch (IOException e) {
            logger.error("属性文件加载出错:" + e.getMessage());
        }
        return null;
    }

    /**
     * 读取Properties的全部信息
     *
     * @param filePath 属性文件路径（classpath）
     * @return 返回所有的属性 key:value<>key:value
     */
    public static LinkedHashMap<String, String> getProperties(String filePath) {

        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        Properties properties = null;

        try {
            properties = loadProperties(filePath);
        } catch (Exception e) {
            logger.error("属性文件加载出错:" + e.getMessage());
            return null;
        }

        Enumeration en = properties.propertyNames();
        while (en.hasMoreElements()) {
            String strKey = (String) en.nextElement();
            String strValue = properties.getProperty(strKey);
            map.put(strKey, strValue);
        }
        return map;
    }
}
