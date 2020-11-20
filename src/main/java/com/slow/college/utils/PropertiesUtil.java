
package com.slow.college.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.*;

/**
 * Properties文件操作工具类
 *
 * @author yuan
 */
public class PropertiesUtil {
    private Properties propertie;
    private FileInputStream inputFile;
    private FileOutputStream outputFile;
    private String filePath;
    private static final Logger LOG = LogManager.getLogger(NetUtils.class);
    public PropertiesUtil(String filePath) throws IOException {
        this.filePath = null;
        propertie = new Properties();
        this.filePath = filePath;
        try {
            inputFile = new FileInputStream(filePath);
            propertie.load(inputFile);
            inputFile.close();
        } catch (FileNotFoundException ex) {
            LOG.error(ex.getMessage(), ex);
            throw new FileNotFoundException(ex.getMessage());
        } catch (IOException ex) {
            LOG.error(ex.getMessage(), ex);
            throw new IOException(ex);
        }
    }

    public boolean keyExist(String key) {
        return propertie.containsKey(key);
    }

    public String getValue(String key) {
        if (propertie.containsKey(key)) {
            String value = propertie.getProperty(key);
            return value;
        } else {
            return null;
        }
    }

    public void setValue(String key, String value)
            throws IOException {
        propertie.setProperty(key, value);
        try {
            outputFile = new FileOutputStream(filePath);
            propertie.store(outputFile, "");
            outputFile.close();
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            throw new IOException(e.getMessage(), e);
        }
    }

    public void saveFile(String description)
            throws IOException {
        try {
            outputFile = new FileOutputStream(filePath);
            propertie.store(outputFile, description);
            outputFile.close();
        } catch (FileNotFoundException e) {
            LOG.error(e.getMessage(), e);
            throw new FileNotFoundException(e.getMessage());
        } catch (IOException ioe) {
            LOG.error(ioe.getMessage(), ioe);
            throw new IOException(ioe.getMessage(), ioe);
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public Map getAllProperties() {
        Map map = new HashMap();
        Set keys = propertie.keySet();
        String key;
        String value;
        for (Iterator i$ = keys.iterator(); i$.hasNext(); map.put(key, value)) {
            Object obj = i$.next();
            key = obj.toString();
            value = getValue(key);
        }

        return map;
    }

    public boolean delPro(String keys[])
            throws Exception {
        boolean bool = false;
        try {
            String arr$[] = keys;
            int len$ = arr$.length;
            for (int i$ = 0; i$ < len$; i$++) {
                String key = arr$[i$];
                propertie.remove(key);
            }

            int taskCount = Integer.parseInt(propertie.getProperty("taskCount"));
            propertie.setProperty("taskCount", (new StringBuilder()).append("").append(taskCount - 1).toString());
            File file = new File(filePath);
            file.delete();
            file.createNewFile();
            propertie.store(new FileOutputStream(file), "");
            bool = true;
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
            throw new Exception(ex.getMessage(), ex);
        }
        return bool;
    }

    public void closeIO() throws IOException {
        try {
            if (inputFile != null)
                inputFile.close();
            if (outputFile != null)
                outputFile.close();
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            throw new IOException(e.getMessage(), e);
        }
    }
}
