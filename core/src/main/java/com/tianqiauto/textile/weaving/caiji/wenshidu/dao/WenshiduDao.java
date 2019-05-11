package com.tianqiauto.textile.weaving.caiji.wenshidu.dao;

import com.tianqiauto.textile.weaving.model.sys.WenShiDu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author bjw
 * @Date 2019/5/11 13:38
 */
@Repository
public class WenshiduDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public void batchUpdateParam(List<WenShiDu> wenShiDus) {
        String sql = " UPDATE SYS_WENSHIDU SET [NAME]=?, SHIDU=?, WENDU=?,last_modified_date=? WHERE ID = ? ";
        List<Object[]> list = new ArrayList();
        for (WenShiDu shiDu : wenShiDus) {
            Object[] array = {shiDu.getName(),shiDu.getShidu(),shiDu.getWendu(),shiDu.getLastModifiedDate(),shiDu.getId()};
            list.add(array);
        }
        jdbcTemplate.batchUpdate(sql,list);
    }
}
