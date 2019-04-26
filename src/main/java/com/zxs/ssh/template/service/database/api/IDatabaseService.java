package com.zxs.ssh.template.service.database.api;

import java.util.List;
import java.util.Map;

/**
 * Project Name:code-generator
 * File Name:IDatabaseService
 * Package Name:com.zxs.ssh.template.service.database.api
 * Date:2019/4/25
 * Author:zengxueshan
 * Description:
 * Copyright (c) 2019, 重庆云凯科技有限公司 All Rights Reserved.
 */


public interface IDatabaseService {
    /**
     * 根据表名，获取列名
     */
    List<Map<String, String>> queryColumns(String tableName) throws Exception;

    /**
     * 生成代码
     *
     * @param tableName 表名
     * @return 文件字节流
     * @throws Exception 异常
     */
    byte[] generatorCode(String tableName) throws Exception;
}
