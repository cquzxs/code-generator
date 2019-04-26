package com.zxs.ssh.template.controller;

import com.zxs.ssh.template.model.response.ResponseResult;
import com.zxs.ssh.template.service.database.api.IDatabaseService;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Project Name:code-generator
 * File Name:DatabaseController
 * Package Name:com.zxs.ssh.template.controller
 * Date:2019/4/25
 * Author:zengxueshan
 * Description:
 * Copyright (c) 2019, 重庆云凯科技有限公司 All Rights Reserved.
 */

@RestController("databaseController")
@RequestMapping("database")
public class DatabaseController {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseController.class);

    @Resource(name = "databaseService")
    private IDatabaseService databaseService;

    /**
     * 根据表名，获取列名
     *
     * @param tableName
     */
    @RequestMapping("queryColumns")
    public Object queryColumns(@RequestParam(name = "tableName", defaultValue = "all") String tableName) throws Exception{
        try{
            List<Map<String, String>> list = this.databaseService.queryColumns(tableName);
            return new ResponseResult(0, ResponseResult.ResponseState.SUCCESS, list, "操作成功");
        }catch (Exception e){
            logger.error("操作异常", e);
            return new ResponseResult(1, ResponseResult.ResponseState.FAILURE,new HashMap<>(),e.getMessage());
        }
    }
    /**
     * 生成代码
     *
     * @param tableName 表名
     */
    @RequestMapping("generatorCode")
    public Object generatorCode(@RequestParam(name = "tableName", defaultValue = "all") String tableName, HttpServletResponse response) throws Exception{
        try{
            byte[] data = this.databaseService.generatorCode(tableName);
            response.reset();
            response.setHeader("Content-Disposition", "attachment; filename=\"framework.zip\"");
            response.addHeader("Content-Length", "" + data.length);
            response.setContentType("application/octet-stream; charset=UTF-8");

            IOUtils.write(data, response.getOutputStream());
            return new ResponseResult(0, ResponseResult.ResponseState.SUCCESS, new HashMap<>(), "操作成功");
        }catch (Exception e){
            logger.error("操作异常", e);
            return new ResponseResult(1, ResponseResult.ResponseState.FAILURE,new HashMap<>(),e.getMessage());
        }
    }
}
