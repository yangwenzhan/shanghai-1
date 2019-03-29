package com.tianqiauto.textile.weaving.service;

import com.tianqiauto.textile.weaving.model.sys.YuanSha;
import com.tianqiauto.textile.weaving.model.sys.YuanSha_RuKu;
import com.tianqiauto.textile.weaving.model.sys.YuanSha_RuKu_Shenqing;
import com.tianqiauto.textile.weaving.repository.YuanShaRepository;
import com.tianqiauto.textile.weaving.repository.YuanShaRuKuRepository;
import com.tianqiauto.textile.weaving.repository.YuanShaRuKuShenQingRepository;
import com.tianqiauto.textile.weaving.util.JPASql.Container;
import com.tianqiauto.textile.weaving.util.JPASql.DynamicUpdateSQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;

/**
 * @Author bjw
 * @Date 2019/3/19 14:41
 */
@Service
@Transactional
public class YuanShaRuKuService {

    @Autowired
    private YuanShaRuKuShenQingRepository yuanShaRuKuShenQingRepository;

    @Autowired
    private YuanShaRepository yuanShaRepository;

    @Autowired
    private YuanShaRuKuRepository yuanShaRuKuRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public YuanSha_RuKu_Shenqing save(@Valid YuanSha_RuKu_Shenqing yuanShaRuKuShenqing) {
        YuanSha yuansha = yuanShaRepository.save(yuanShaRuKuShenqing.getYuanSha());
        yuanShaRuKuShenqing.setYuanSha(yuansha);
        return yuanShaRuKuShenQingRepository.save(yuanShaRuKuShenqing);
    }

    public int updateShenqing(@Valid YuanSha_RuKu_Shenqing yuanShaRuKuShenqing) {
        YuanSha yuanSha = yuanShaRuKuShenqing.getYuanSha();
        Container RUSql = new DynamicUpdateSQL<>(yuanSha).getUpdateSql();
        jdbcTemplate.update(RUSql.getSql(),RUSql.getParam());

        Container RUSql2 = new DynamicUpdateSQL<>(yuanShaRuKuShenqing).getUpdateSql();
        return jdbcTemplate.update(RUSql2.getSql(),RUSql2.getParam());

    }

    public YuanSha_RuKu_Shenqing findByIdSQ(Long id) {
        return yuanShaRuKuShenQingRepository.findById(id).get();
    }

    public void deleteSQ(Long id) {
        yuanShaRuKuShenQingRepository.deleteById(id);
    }

    public YuanSha_RuKu addDengJi(@Valid YuanSha_RuKu yuanShaRuKu) {
        YuanSha yuansha = yuanShaRepository.save(yuanShaRuKu.getYuanSha());
        yuanShaRuKu.setYuanSha(yuansha);
        return yuanShaRuKuRepository.save(yuanShaRuKu);
    }

    public int updateDengji(@Valid YuanSha_RuKu yuanShaRuKu) {
        YuanSha yuanSha = yuanShaRuKu.getYuanSha();
        Container RUSql = new DynamicUpdateSQL<>(yuanSha).getUpdateSql();
        jdbcTemplate.update(RUSql.getSql(),RUSql.getParam());

        Container RUSql2 = new DynamicUpdateSQL<>(yuanShaRuKu).getUpdateSql();
        return jdbcTemplate.update(RUSql2.getSql(),RUSql2.getParam());
    }

    public YuanSha_RuKu findByIdDJ(Long id) {
        return yuanShaRuKuRepository.findById(id).get();
    }

    public void deleteDJ(Long id) {
        yuanShaRuKuRepository.deleteById(id);
    }
}
