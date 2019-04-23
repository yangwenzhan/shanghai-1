package com.tianqiauto.textile.weaving.service.chengpinguanli;

import com.tianqiauto.textile.weaving.model.base.Dict;
import com.tianqiauto.textile.weaving.model.base.User;
import com.tianqiauto.textile.weaving.model.sys.Chengpin_ChuKu;
import com.tianqiauto.textile.weaving.model.sys.Chengpin_ChuKu_Shenqing;
import com.tianqiauto.textile.weaving.repository.ChengpinchukuRepository;
import com.tianqiauto.textile.weaving.repository.ChengpinchukushenqingRepository;
import com.tianqiauto.textile.weaving.repository.UserRepository;
import com.tianqiauto.textile.weaving.repository.dao.DictDao;
import com.tianqiauto.textile.weaving.util.copy.MyCopyProperties;
import com.tianqiauto.textile.weaving.util.model.ModelUtil;
import com.tianqiauto.textile.weaving.util.model.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Optional;

/**
 * @Author bjw
 * @Date 2019/4/1 18:10
 */
@Service
@Transactional
public class ChengpinchukushenqingServer {

    @Autowired
    private ChengpinchukuRepository chengpinchukuRepository;

    @Autowired
    private ChengpinchukushenqingRepository chengpinchukushenqingRepository;


    public Page<Chengpin_ChuKu_Shenqing> findAll(Chengpin_ChuKu_Shenqing chengpinChuKuShenqing, Pageable pageable) {

        Specification<Chengpin_ChuKu_Shenqing> specification = (root, criteriaQuery, criteriaBuilder) -> {
            ModelUtil<Chengpin_ChuKu_Shenqing> mu = new ModelUtil<>(chengpinChuKuShenqing,root);
            //开始日期和结束日期
            if(!mu.paramIsEmpty("kaishiriqi") || !mu.paramIsEmpty("jieshuriqi")) {
                mu.addPred(criteriaBuilder.between(root.get("chengpinchuku").get("yaoqiulingyongshijian"), chengpinChuKuShenqing.getKaishiriqi(),chengpinChuKuShenqing.getJieshuriqi()));
            }
            //合约号
            Param heyuehao = mu.initParam("chengpinchuku.heyuehao.id");
            if(!heyuehao.isEmpty()) {
                mu.addPred(criteriaBuilder.equal(heyuehao.getPath(),heyuehao.getValue()));
            }
            //购货单位
            Param gouhuodanwei = mu.initParam("chengpinchuku.gouhuodanwei");
            if(!heyuehao.isEmpty()) {
                mu.addPred(criteriaBuilder.like(gouhuodanwei.getPath(), "%" +gouhuodanwei.getValue() + "%"));
            }
            //营销员
            Param yingxiaoyuan = mu.initParam("chengpinchuku.yingxiaoyuan.id");
            if(!yingxiaoyuan.isEmpty()) {
                mu.addPred(criteriaBuilder.equal(yingxiaoyuan.getPath(),yingxiaoyuan.getValue()));
            }
            //申请状态
            Param status = mu.initParam("status.id");
            if(!status.isEmpty()) {
                mu.addPred(criteriaBuilder.equal(status.getPath(),status.getValue()));
            }
            //出库类型
            Param chukuleixing = mu.initParam("chengpinchuku.chukuleixing.id");
            if(!chukuleixing.isEmpty()) {
                mu.addPred(criteriaBuilder.equal(chukuleixing.getPath(),chukuleixing.getValue()));
            }
            return criteriaBuilder.and(mu.getPred());
        };
        return chengpinchukushenqingRepository.findAll(specification,pageable);
    }

    @Autowired
    private DictDao dictDao;

    @Autowired
    private UserRepository userRepository;

    public Chengpin_ChuKu_Shenqing save(Chengpin_ChuKu_Shenqing chengpinChuKuShenqing) {
        Dict dict = dictDao.findByTypecodeAndValue("cp_chukushenqingzhuangtai","10");
        chengpinChuKuShenqing.setStatus(dict);
        User yingxiaoyuan = userRepository.findById(chengpinChuKuShenqing.getChengpinchuku().getYingxiaoyuan().getId()).get();
        chengpinChuKuShenqing.getChengpinchuku().setYingxiaoyuan(yingxiaoyuan);
        chengpinChuKuShenqing = chengpinchukushenqingRepository.save(chengpinChuKuShenqing);
        Chengpin_ChuKu chenpinchuku = chengpinChuKuShenqing.getChengpinchuku();
        chenpinchuku.setChengpin_chuKu_shenqing(chengpinChuKuShenqing);
        chengpinchukuRepository.save(chenpinchuku);
        return chengpinChuKuShenqing;
    }

    public void deleteById(Long id) {
        chengpinchukushenqingRepository.deleteById(id);
    }

    public void update(Chengpin_ChuKu_Shenqing chengpinChuKuShenqing) {
        Chengpin_ChuKu chengpinchukuKu = chengpinChuKuShenqing.getChengpinchuku();
        Chengpin_ChuKu chengpin_chuKuDB = chengpinchukuRepository.findById(chengpinchukuKu.getId()).get();
        User yingxiaoyuan = userRepository.findById(chengpinChuKuShenqing.getChengpinchuku().getYingxiaoyuan().getId()).get();
        chengpinchukuKu.setYingxiaoyuan(yingxiaoyuan);
        MyCopyProperties.copyProperties(chengpinchukuKu,chengpin_chuKuDB, Arrays.asList("heyuehao","yaoqiulingyongshijian","gouhuodanwei","shouhuodanwei","lianxiren","lianxidianhua","shouhuodizhi","chukuleixing","yingxiaoyuan"));
        Chengpin_ChuKu_Shenqing chengpinchuKuShenqingDB = chengpinchukushenqingRepository.findById(chengpinChuKuShenqing.getId()).get();
        MyCopyProperties.copyProperties(chengpinChuKuShenqing,chengpinchuKuShenqingDB, Arrays.asList("changdu","beizhu"));
        chengpinchuKuShenqingDB.setChengpinchuku(chengpin_chuKuDB);
        chengpinchukushenqingRepository.save(chengpinchuKuShenqingDB);
    }
}
