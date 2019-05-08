package com.tianqiauto.textile.weaving.repository.dao;

import com.tianqiauto.textile.weaving.model.base.Dict;
import com.tianqiauto.textile.weaving.model.sys.Beam_ZhiZhou_Shift;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class BeamzhizhoushiftDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Beam_ZhiZhou_Shift findByHeyuehaoAndZhizhou(Long heyuehaoId,Long zhizhouId){
        String sql = "SELECT top 1 * FROM sys_beam_zhizhou_shift WHERE heyuehao_id = ? AND  zhizhou_id = ? ORDER BY CREATE_time DESC";
        return jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<Beam_ZhiZhou_Shift>(Beam_ZhiZhou_Shift.class),heyuehaoId,zhizhouId);
    }

    public void updateChuanzongTime(Long id,Date chuanzongTime){
        String sql = " update sys_beam_zhizhou_shift SET chuanzong_time = ? WHERE id = ? ";
        jdbcTemplate.update(sql, chuanzongTime, id);
    }

}
