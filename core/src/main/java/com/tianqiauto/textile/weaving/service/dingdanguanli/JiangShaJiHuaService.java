package com.tianqiauto.textile.weaving.service.dingdanguanli;

import com.tianqiauto.textile.weaving.model.sys.JiHua_JiangSha;
import com.tianqiauto.textile.weaving.model.sys.JiHua_JiangSha_Main;
import com.tianqiauto.textile.weaving.model.sys.JiHua_JiangSha_ZhiZhou;
import com.tianqiauto.textile.weaving.repository.JiHuaJiangShaMainRepository;
import com.tianqiauto.textile.weaving.repository.JiHuaJiangShaRepository;
import com.tianqiauto.textile.weaving.repository.JiHuaJiangShaZhiZhouRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author bjw
 * @Date 2019/3/19 9:26
 */
@Service
@Transactional
public class JiangShaJiHuaService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private JiHuaJiangShaRepository jiHuaJiangShaRepository;

    @Autowired
    private JiHuaJiangShaMainRepository jiHuaJiangShaMainRepository;

    @Autowired
    private JiHuaJiangShaZhiZhouRepository jiHuaJiangShaZhiZhouRepository;

    public JiHua_JiangSha save(JiHua_JiangSha jiHuaJiangSha) {
//        jiHuaJiangSha.setStatus();//Fixme 设置默认状态
        JiHua_JiangSha_Main jihuajiangshamian = jiHuaJiangSha.getJiHua_jiangSha_main();
        jihuajiangshamian = jiHuaJiangShaMainRepository.save(jihuajiangshamian);
        jiHuaJiangSha.setJiHua_jiangSha_main(jihuajiangshamian);
        //------------------------------------------------------------以上保存main
        Set<JiHua_JiangSha_ZhiZhou> zhizhoujihuas = jiHuaJiangSha.getJiHua_jiangSha_zhiZhouSet();
        Set<JiHua_JiangSha_ZhiZhou> zhizhoujihuaset = new HashSet<>();
        for(JiHua_JiangSha_ZhiZhou zhizhou:zhizhoujihuas){
            zhizhou = jiHuaJiangShaZhiZhouRepository.save(zhizhou);
            zhizhoujihuaset.add(zhizhou);
        }
        jiHuaJiangSha.setJiHua_jiangSha_zhiZhouSet(zhizhoujihuaset);
        //------------------------------------------------------------以上保存浆纱织轴计划





        return jiHuaJiangShaRepository.save(jiHuaJiangSha);
    }

    public JiHua_JiangSha findById(Long id) {
        return jiHuaJiangShaRepository.findById(id).get();
    }
}
