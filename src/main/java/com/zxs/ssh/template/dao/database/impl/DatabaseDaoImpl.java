package com.zxs.ssh.template.dao.database.impl;

import com.zxs.ssh.template.dao.common.api.ICommonDao;
import com.zxs.ssh.template.dao.database.api.IDatabaseDao;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Project Name:code-generator
 * File Name:DatabaseDaoImpl
 * Package Name:com.zxs.ssh.template.dao.database.impl
 * Date:2019/4/25
 * Author:zengxueshan
 * Description:
 * Copyright (c) 2019, 重庆云凯科技有限公司 All Rights Reserved.
 */

@Repository("databaseDao")
@Transactional
public class DatabaseDaoImpl implements IDatabaseDao{

    private static final Logger logger = LoggerFactory.getLogger(DatabaseDaoImpl.class);

    @Resource(name = "commonDao")
    private ICommonDao commonDao;

    /**
     * 根据表名，获取列名
     *
     * @param tableName
     */
    @Override
    public List<Map<String, String>> queryColumns(String tableName) throws Exception{
        String sql = "select column_name columnName, data_type dataType, column_comment columnComment, column_key columnKey, extra " +
                "from information_schema.columns " +
                "where table_name = '"+tableName+"' and table_schema = (select database()) order by ordinal_position";
        List<Map<String, String>> result = new ArrayList<>();
        try{
            Session session = this.commonDao.getSession();
            Query query = session.createSQLQuery(sql);
            List list = query.list();
            for (int i = 0; i < list.size(); i++) {
                Object o = list.get(i);
                Object[] values = (Object[])o;
                Map<String,String> map = new HashMap<>();
                map.put("columnName",values[0].toString());
                map.put("dataType",values[1].toString());
                map.put("columnComment",values[2].toString());
                map.put("columnKey",values[3].toString());
                map.put("extra",values[4].toString());
                result.add(map);
            }
        }catch (Exception e){
            logger.error("根据表名，获取列名失败",e);
        }
        return result;
    }

    /**
     * 分页查询数据库表列表
     *
     * @param tableNameKeyword 表名关键字
     * @param pageIndex 页码
     * @param pageSize 页面大小
     * @param asc 是否升序
     */
    @Override
    public List<Map<String, Object>> queryList(String tableNameKeyword, int pageIndex, int pageSize, boolean asc) {
        return null;
    }

    /**
     * 查询数据库表总数
     *
     * @param tableNameKeyword 表名关键字
     */
    @Override
    public int queryTotal(String tableNameKeyword) {
        System.out.println();
        return 0;
    }
}
