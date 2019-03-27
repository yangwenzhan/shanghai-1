package com.tianqiauto.textile.weaving.service;

import com.tianqiauto.textile.weaving.model.sys.Heyuehao_YuanSha;
import com.tianqiauto.textile.weaving.repository.Heyuehao_YuanShaRepository;
import com.tianqiauto.textile.weaving.util.JPASql.Container;
import com.tianqiauto.textile.weaving.util.JPASql.DynamicUpdateSQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author bjw
 * @Date 2019/3/19 10:28
 */
@Service
@Transactional
public class HeYueHaoYuanShaService {

    @Autowired
    private Heyuehao_YuanShaRepository heyuehaoYuanShaRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Heyuehao_YuanSha save(Heyuehao_YuanSha heyuehaoYuanSha) {
        return heyuehaoYuanShaRepository.save(heyuehaoYuanSha);
    }

    public void deleteById(Long id) {
        heyuehaoYuanShaRepository.deleteById(id);
    }

    public Heyuehao_YuanSha update(Heyuehao_YuanSha heyuehaoYuanSha) {
        Container RUSql = new DynamicUpdateSQL<>(heyuehaoYuanSha).getUpdateSql();
        jdbcTemplate.update(RUSql.getSql(),RUSql.getParam());
        return heyuehaoYuanShaRepository.findById(heyuehaoYuanSha.getId()).get();
    }
}
