package com.tianqiauto.textile.weaving.service;

import com.tianqiauto.textile.weaving.model.sys.YuanSha_ChuKu;
import com.tianqiauto.textile.weaving.model.sys.YuanSha_ChuKu_Shenqing;
import com.tianqiauto.textile.weaving.repository.YuanShaChuKuRepository;
import com.tianqiauto.textile.weaving.repository.YuanShaChuKuShenQingRepository;
import com.tianqiauto.textile.weaving.util.JPASql.Container;
import com.tianqiauto.textile.weaving.util.JPASql.DynamicUpdateSQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

/**
 * @Author bjw
 * @Date 2019/3/19 15:45
 */
@Service
public class YuanShaChuKuService {

    @Autowired
    private YuanShaChuKuRepository yuanShaChuKuRepository;

    @Autowired
    private YuanShaChuKuShenQingRepository yuanShaChuKuShenQingRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public YuanSha_ChuKu_Shenqing save(@Valid YuanSha_ChuKu_Shenqing yuanShaChuKuShenqing) {
        return yuanShaChuKuShenQingRepository.save(yuanShaChuKuShenqing);
    }

    public int updateShenqing(@Valid YuanSha_ChuKu_Shenqing yuanShaChuKuShenqing){
        Container RUSql2 = new DynamicUpdateSQL<>(yuanShaChuKuShenqing).getUpdateSql();
        return jdbcTemplate.update(RUSql2.getSql(),RUSql2.getParam());
    }


    public YuanSha_ChuKu_Shenqing findByIdSQ(Long id) {
        return yuanShaChuKuShenQingRepository.findById(id).get();
    }

    public void deleteSQ(Long id) {
        yuanShaChuKuShenQingRepository.deleteById(id);
    }

    public YuanSha_ChuKu addDengJi(@Valid YuanSha_ChuKu yuanShaChuKu) {
        return yuanShaChuKuRepository.save(yuanShaChuKu);
    }

    public int updateDengji(@Valid YuanSha_ChuKu yuanShaChuKu) {
        Container RUSql2 = new DynamicUpdateSQL<>(yuanShaChuKu).getUpdateSql();
        return jdbcTemplate.update(RUSql2.getSql(),RUSql2.getParam());
    }

    public YuanSha_ChuKu findByIdDJ(Long id) {
        return yuanShaChuKuRepository.findById(id).get();
    }

    public void deleteDJ(Long id) {
        yuanShaChuKuRepository.deleteById(id);
    }
}
