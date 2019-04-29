package com.tianqiauto.textile.weaving.service.jihuaguanli;

import com.tianqiauto.textile.weaving.model.base.SheBei;
import com.tianqiauto.textile.weaving.model.sys.JiHua_ChuanZong;
import com.tianqiauto.textile.weaving.model.sys.JiHua_ChuanZong_Main;
import com.tianqiauto.textile.weaving.repository.JihuaChuanzongMainRepository;
import com.tianqiauto.textile.weaving.repository.JihuaChuanzongRepository;
import com.tianqiauto.textile.weaving.repository.ShebeiRepository;
import com.tianqiauto.textile.weaving.repository.dao.DictDao;
import com.tianqiauto.textile.weaving.util.model.ModelUtil;
import com.tianqiauto.textile.weaving.util.model.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

/**
 * @Author bjw
 * @Date 2019/4/24 8:49
 */
@Service
@Transactional
public class ChuanzongjihuazhixingServer {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private JihuaChuanzongMainRepository jihuaChuanzongMainRepository;

    @Autowired
    private JihuaChuanzongRepository jihuaChuanzongRepository;

    public Page<JiHua_ChuanZong> findAll(JiHua_ChuanZong jiHuaChuanZong, Pageable pageable) {
        Specification<JiHua_ChuanZong> specification = (root, criteriaQuery, criteriaBuilder) -> {
            ModelUtil<JiHua_ChuanZong> mu = new ModelUtil<>(jiHuaChuanZong,root);
            //开始日期和结束日期
            if(!StringUtils.isEmpty(jiHuaChuanZong.getKaishiriqi()) || !StringUtils.isEmpty(jiHuaChuanZong.getJieshuriqi())) {
                mu.addPred(criteriaBuilder.between(root.get("riqi"), jiHuaChuanZong.getKaishiriqi(),jiHuaChuanZong.getJieshuriqi()));
            }
            //班次
            Param banchi = mu.initParam("banci.id");
            if(!banchi.isEmpty()) {
                mu.addPred(criteriaBuilder.equal(banchi.getPath(),banchi.getValue()));
            }
            //合约号
            Param heyuehao = mu.initParam("heyuehao.id");
            if(!heyuehao.isEmpty()) {
                mu.addPred(criteriaBuilder.equal(heyuehao.getPath(),heyuehao.getValue()));
            }
            //优先级
            Param youxianji = mu.initParam("jiHuaChuanZongMain.youxianji.id");
            if(!youxianji.isEmpty()) {
                mu.addPred(criteriaBuilder.equal(youxianji.getPath(),youxianji.getValue()));
            }
            //状态
            Param status = mu.initParam("status.value");
            if(!status.isEmpty()) {
                mu.addPred(criteriaBuilder.equal(status.getPath(),status.getValue()));
            }
            return criteriaBuilder.and(mu.getPred());
        };
        return jihuaChuanzongRepository.findAll(specification,pageable);
    }

//    public List<Map<String,Object>> query_chuanzongji() {
//        String sql = "SELECT null AS id,'手工穿综' AS name UNION SELECT id,jitaihao FROM base_shebei WHERE EXISTS (SELECT 1 FROM base_gongxu WHERE parent_id = (SELECT id FROM base_gongxu WHERE name = '穿综') and base_gongxu.id = base_shebei.gongxu_id ) AND deleted = 0 ";
//        return jdbcTemplate.queryForList(sql);
//    }
//
//    @Autowired
//    private DictDao dictDao;
//
//    @Autowired
//    private ShebeiRepository shebeiRepository;
//
//    public JiHua_ChuanZong_Main save(JiHua_ChuanZong_Main jiHuaChuanZongMain) {
//        jiHuaChuanZongMain.setStatus(dictDao.findByTypecodeAndValue("czjh_main_zhuangtai","10"));
//        List<JiHua_ChuanZong> chuanzongs = jiHuaChuanZongMain.getJiHua_chuanZongs();
//        if(null != chuanzongs){
//            for (JiHua_ChuanZong chuanzong : chuanzongs){
//                chuanzong.setBanci(jiHuaChuanZongMain.getBanci());
//                chuanzong.setDeleted(0);
//                chuanzong.setHeyuehao(jiHuaChuanZongMain.getHeyuehao());
//                chuanzong.setStatus(dictDao.findByTypecodeAndValue("czjh_xq_zhuangtai","10"));
//                chuanzong.setRiqi(jiHuaChuanZongMain.getRiqi());
//                if(null != chuanzong.getJitaihao().getId()){
//                    SheBei sheBei = shebeiRepository.findById(chuanzong.getJitaihao().getId()).get();
//                    chuanzong.setJitaihao(sheBei);
//                }else{
//                    chuanzong.setJitaihao(null);
//                }
//            }
//        }
//        jiHuaChuanZongMain = jihuaChuanzongMainRepository.save(jiHuaChuanZongMain);
//        return jiHuaChuanZongMain;
//    }
//
//    public void deleteById(Long id) {
//        jihuaChuanzongMainRepository.deleteById(id);
//    }

}
