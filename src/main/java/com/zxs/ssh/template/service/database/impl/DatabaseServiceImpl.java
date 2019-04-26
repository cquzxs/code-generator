package com.zxs.ssh.template.service.database.impl;

import com.zxs.ssh.template.dao.database.api.IDatabaseDao;
import com.zxs.ssh.template.dao.database.impl.DatabaseDaoImpl;
import com.zxs.ssh.template.service.database.api.IDatabaseService;
import org.apache.commons.io.IOUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Project Name:code-generator
 * File Name:DatabaseServiceImpl
 * Package Name:com.zxs.ssh.template.service.database.impl
 * Date:2019/4/25
 * Author:zengxueshan
 * Description:
 * Copyright (c) 2019, 重庆云凯科技有限公司 All Rights Reserved.
 */

@Service("databaseService")
public class DatabaseServiceImpl implements IDatabaseService{

    private static final Logger logger = LoggerFactory.getLogger(DatabaseServiceImpl.class);

    @Resource(name = "databaseDao")
    private IDatabaseDao databaseDao;

    /**
     * 根据表名，获取列名
     *
     * @param tableName
     */
    @Override
    public List<Map<String, String>> queryColumns(String tableName) throws Exception{
        return this.databaseDao.queryColumns(tableName);
    }

    /**
     * 生成代码
     *
     * @param tableName 表名
     * @return 文件字节流
     * @throws Exception 异常
     */
    @Override
    public byte[] generatorCode(String tableName) throws Exception {
        initVelocity();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);


        // 封装模板数据
        Map<String, Object> map = new HashMap<>();
        map.put("tableName", "user");
        map.put("projectName", "code-generator");
        map.put("comments", "用户");
        map.put("className", "user");
        map.put("classname", "user");
        map.put("package", "com.zxs.ssh.template");
        map.put("author", "zengxueshan");
        map.put("email", "zengxueshan@yun-kai.com");
        map.put("datetime", new Timestamp(System.currentTimeMillis()));
        VelocityContext context = new VelocityContext(map);

        // 渲染模板
        String template = "template/Dao.java.vm";
        StringWriter sw = new StringWriter();
        Template tpl = Velocity.getTemplate(template, "UTF-8");
        tpl.merge(context, sw);

        try {
            // 添加到zip
            zip.putNextEntry(new ZipEntry("dao/IUserDao.java"));
            IOUtils.write(sw.toString(), zip, "UTF-8");
            IOUtils.closeQuietly(sw);
            zip.closeEntry();
        } catch (IOException e) {
            throw new Exception("渲染模板失败，表名：" + "user", e);
        }

        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }
    /**
     * 初始化Velocity引擎
     * --VelocityEngine是单例模式，线程安全
     * @throws Exception
     */
    public static void initVelocity() throws Exception {
        Properties p = new Properties();
        /**
         * velocity.properties配置定义
         * file.resource.loader.class = org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader
         * ENCODING_DEFAULT = UTF-8
         * OUTPUT_ENCODING = UTF-8
         */
        //加载classpath目录下的vm文件
        p.setProperty("file.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        //定义字符集
        p.setProperty(Velocity.ENCODING_DEFAULT, "UTF-8");
        p.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");
        // 初始化Velocity引擎，指定配置Properties
        Velocity.init(p);
    }
}
