package com.tianqiauto.textile.weaving.service.chengpinguanli;

import com.tianqiauto.textile.weaving.model.base.Dict;
import com.tianqiauto.textile.weaving.model.base.User;
import com.tianqiauto.textile.weaving.model.sys.Chengpin_ChuKu;
import com.tianqiauto.textile.weaving.model.sys.Chengpin_ChuKu_Shenqing;
import com.tianqiauto.textile.weaving.model.sys.Chengpin_Current;
import com.tianqiauto.textile.weaving.model.sys.Heyuehao;
import com.tianqiauto.textile.weaving.repository.ChengpinchukuRepository;
import com.tianqiauto.textile.weaving.repository.ChengpinchukushenqingRepository;
import com.tianqiauto.textile.weaving.repository.ChengpincurrentRepository;
import com.tianqiauto.textile.weaving.repository.UserRepository;
import com.tianqiauto.textile.weaving.repository.dao.ChengpinCurrentDao;
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
import java.util.Date;

/**
 * @Author bjw
 * @Date 2019/4/1 18:10
 */
@Service
@Transactional
public class ChengpinchukudengjiServer {

    @Autowired
    private ChengpinchukuRepository chengpinchukuRepository;

    public Page<Chengpin_ChuKu> findAll(Chengpin_ChuKu chengpinChuKu, Pageable pageable) {
        Specification<Chengpin_ChuKu> specification = (root, criteriaQuery, criteriaBuilder) -> {
            ModelUtil<Chengpin_ChuKu> mu = new ModelUtil<>(chengpinChuKu,root);
            //合约号
            Param heyuehao = mu.initParam("heyuehao.id");
            if(!heyuehao.isEmpty()) {
                mu.addPred(criteriaBuilder.equal(heyuehao.getPath(),heyuehao.getValue()));
            }
            //购货单位
            Param gouhuodanwei = mu.initParam("gouhuodanwei");
            if(!heyuehao.isEmpty()) {
                mu.addPred(criteriaBuilder.like(gouhuodanwei.getPath(), "%" +gouhuodanwei.getValue() + "%"));
            }
            //营销员
            Param yingxiaoyuan = mu.initParam("yingxiaoyuan.id");
            if(!yingxiaoyuan.isEmpty()) {
                mu.addPred(criteriaBuilder.equal(yingxiaoyuan.getPath(),yingxiaoyuan.getValue()));
            }
            //出库类型
            Param chukuleixing = mu.initParam("chukuleixing.id");
            if(!chukuleixing.isEmpty()) {
                mu.addPred(criteriaBuilder.equal(chukuleixing.getPath(),chukuleixing.getValue()));
            }
            Param chukushenqing = mu.initParam("chengpin_chuKu_shenqing.id");
            mu.addPred(criteriaBuilder.isNull(chukushenqing.getPath()));
            return criteriaBuilder.and(mu.getPred());
        };
        return chengpinchukuRepository.findAll(specification,pageable);
    }

    @Autowired
    private DictDao dictDao;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChengpincurrentRepository chengpincurrentRepository;

    public Chengpin_ChuKu save(Chengpin_ChuKu chengpinchuku,Chengpin_Current chengpinCurrent) {
        User cangkuquerenren = userRepository.findById(chengpinchuku.getCangkuquerenren().getId()).get();
        chengpinchuku.setCangkuquerenren(cangkuquerenren);
        User yingxiaoyuan = userRepository.findById(chengpinchuku.getYingxiaoyuan().getId()).get();
        chengpinchuku.setYingxiaoyuan(yingxiaoyuan);
        chengpinchukuRepository.save(chengpinchuku);
        chengpincurrentRepository.save(chengpinCurrent);
        return chengpinchuku;
    }

    public void deleteById(Long id) {
        Chengpin_ChuKu chengpinChuKu = chengpinchukuRepository.findById(id).get();
        Heyuehao heyuehao = chengpinChuKu.getHeyuehao();
        Chengpin_Current chengpinCurrent = chengpincurrentRepository.findByHeyuehao(heyuehao);
        chengpinCurrent.setChangdu(chengpinCurrent.getChangdu()+chengpinChuKu.getChangdu());
        chengpincurrentRepository.save(chengpinCurrent);
        chengpinchukuRepository.deleteById(id);
    }

    @Autowired
    private ChengpinCurrentDao chengpinCurrentDao;

    public void update(Chengpin_ChuKu chengpinChuKu) {
        Chengpin_ChuKu chengpinChuKuDB = chengpinchukuRepository.findById(chengpinChuKu.getId()).get();
        Heyuehao heyuehaoDB = chengpinChuKuDB.getHeyuehao();
        chengpinCurrentDao.addChengpin(heyuehaoDB,chengpinChuKuDB.getChangdu());
        User yingxiaoyuan = userRepository.findById(chengpinChuKu.getYingxiaoyuan().getId()).get();
        chengpinChuKu.setYingxiaoyuan(yingxiaoyuan);
        User cangkuquerenren = userRepository.findById(chengpinChuKu.getCangkuquerenren().getId()).get();
        chengpinChuKu.setCangkuquerenren(cangkuquerenren);
        MyCopyProperties.copyProperties(chengpinChuKu,chengpinChuKuDB, Arrays.asList("heyuehao","yaoqiulingyongshijian","gouhuodanwei","shouhuodanwei","lianxiren","lianxidianhua","shouhuodizhi","chukuleixing","yingxiaoyuan","changdu","beizhu","cangkuquerenshijian"));
        chengpinChuKuDB.setCangkuquerenshijian(new Date());
        chengpinChuKuDB = chengpinchukuRepository.save(chengpinChuKuDB);
        chengpinCurrentDao.deleteChengpin(chengpinChuKuDB.getHeyuehao(),chengpinChuKu.getChangdu());
    }
}
