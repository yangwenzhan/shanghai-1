package com.tianqiauto.textile.weaving.service.jihuaguanli;

import com.tianqiauto.textile.weaving.model.base.Dict;
import com.tianqiauto.textile.weaving.model.sys.*;
import com.tianqiauto.textile.weaving.repository.*;
import com.tianqiauto.textile.weaving.repository.dao.BeamzhizhoushiftDao;
import com.tianqiauto.textile.weaving.repository.dao.DictDao;
import com.tianqiauto.textile.weaving.util.model.ModelUtil;
import com.tianqiauto.textile.weaving.util.model.Param;
import com.tianqiauto.textile.weaving.util.procedure.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.Date;
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

    @Autowired
    private ZhixingchuanzongRepository zhixingchuanzongRepository;


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


    @Autowired
    private BaseService baseService;

    public List<Map<String,Object>> getZhizhou(String heyuehao_id) {

        String sql= "SELECT sys_beam_zhizhou.id,sys_beam_zhizhou.zhouhao FROM sys_beam_zhizhou_current " +
                "LEFT JOIN sys_beam_zhizhou ON sys_beam_zhizhou_current.zhizhou_id = sys_beam_zhizhou.id " +
                "LEFT JOIN base_dict ON base_dict.id = sys_beam_zhizhou_current.status_id " +
                "LEFT JOIN base_dict_type ON base_dict_type.id = base_dict.type_id " +
                "WHERE sys_beam_zhizhou_current.heyuehao_id = ? AND base_dict_type.code = 'zhizhouzhuangtai' " +
                "AND base_dict.[value] IN ('30','40') ";
                 return jdbcTemplate.queryForList(sql,heyuehao_id);
    }
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DictDao dictDao;

    @Autowired
    private BeamzhizhoucurrentRepository beanzhizhoucurrentRepository;
//    @Autowired
    private BeamzhizhoushiftDao beamzhizhoushiftDao;

    private BeamzhizhoushiftRepository beamzhizhoushiftRepository;

    public ZhiXing_ChuanZong update(JiHua_ChuanZong jiHuaChuanZong) {
        JiHua_ChuanZong jihua = jihuaChuanzongRepository.findById(jiHuaChuanZong.getId()).get();
        ZhiXing_ChuanZong zhixing = jiHuaChuanZong.getZhiXing_chuanZong();
        zhixing.setJiHua_chuanZong(jihua);
        zhixing.setChuanzonggong(userRepository.findAllById(zhixing.getChuanzonggong().getId()));//初始化穿综工
        zhixing = zhixingchuanzongRepository.save(zhixing);
        //改变织轴状态Beam_ZhiZhou_Current
        Beam_ZhiZhou_Current beamZhiZhouCurrent = beanzhizhoucurrentRepository.findById(zhixing.getZhizhou().getId()).get();

        String value = beamZhiZhouCurrent.getStatus().getValue();
        if(value == "30"){//机下满未穿综。
            Dict dict = dictDao.findByTypecodeAndValue("zhizhouzhuangtai","20");
            beamZhiZhouCurrent.setStatus(dict);
        }else if(value == "40"){
            Dict dict = dictDao.findByTypecodeAndValue("zhizhouzhuangtai","60");
            beamZhiZhouCurrent.setStatus(dict);
        }
        beanzhizhoucurrentRepository.save(beamZhiZhouCurrent);
        //  Beam_ZhiZhou_Shift 判断穿综时间是否填充，没有填充的填充。 判断创建时间最大的那一条
        Beam_ZhiZhou_Shift beamzhizhoushift = beamzhizhoushiftDao.findByHeyuehaoAndZhizhou(jihua.getHeyuehao().getId(), zhixing.getZhizhou().getId());
        Date chuanzongTime = beamzhizhoushift.getChuanzong_time();
        if(null == chuanzongTime){
            beamzhizhoushiftDao.updateChuanzongTime(beamzhizhoushift.getId(),new Date());
        }
        //fixme Shift_ChuanZong 挡车工问题和
        return zhixing;
    }

}
