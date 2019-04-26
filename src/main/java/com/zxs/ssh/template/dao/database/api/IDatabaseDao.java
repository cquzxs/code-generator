package com.zxs.ssh.template.dao.database.api;

import java.util.List;
import java.util.Map;

/**
 * Project Name:code-generator
 * File Name:IDatabaseDao
 * Package Name:com.zxs.ssh.template.dao.database.api
 * Date:2019/4/25
 * Author:zengxueshan
 * Description:
 * Copyright (c) 2019, 重庆云凯科技有限公司 All Rights Reserved.
 */


public interface IDatabaseDao {
    /**
     * 根据表名，获取列名
     */
    List<Map<String, String>> queryColumns(String tableName) throws Exception;

    /**
     * 分页查询数据库表列表
     */
    List<Map<String, Object>> queryList(String tableNameKeyword, int pageIndex, int pageSize, boolean asc);
    /**
     * 查询数据库表总数
     */
    int queryTotal(String tableNameKeyword);
}
