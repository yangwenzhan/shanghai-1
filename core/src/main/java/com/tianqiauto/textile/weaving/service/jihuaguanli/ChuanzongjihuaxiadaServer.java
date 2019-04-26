package com.tianqiauto.textile.weaving.service.jihuaguanli;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @Author bjw
 * @Date 2019/4/24 8:49
 */
@Service
@Transactional
public class ChuanzongjihuaxiadaServer {

//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    public List<Map<String,Object>> query_distinctYouxianji() {
//        String sql = "SELECT DISTINCT youxianji FROM sys_jihua_buji WHERE DELETED = 0 ORDER BY youxianji";
//        return jdbcTemplate.queryForList(sql);
//    }
//
//
//    @Autowired
//    private JihuaBujiRepository jihuaBujiRepository;
//
//    public Page<JiHua_BuJi> findAll(JiHua_BuJi jiHuaBuJi, Pageable pageable) {
//        Specification<JiHua_BuJi> specification = (root, criteriaQuery, criteriaBuilder) -> {
//            ModelUtil<JiHua_BuJi> mu = new ModelUtil<>(jiHuaBuJi,root);
//            //开始日期和结束日期
//            if(!StringUtils.isEmpty(jiHuaBuJi.getKaishiriqi()) || !StringUtils.isEmpty(jiHuaBuJi.getJieshuriqi())) {
//                mu.addPred(criteriaBuilder.between(root.get("riqi"), jiHuaBuJi.getKaishiriqi(),jiHuaBuJi.getJieshuriqi()));
//            }
//            //班次
//            Param banchi = mu.initParam("banci.id");
//            if(!banchi.isEmpty()) {
//                mu.addPred(criteriaBuilder.equal(banchi.getPath(),banchi.getValue()));
//            }
//            //类型
//            Param leixing = mu.initParam("leixing.id");
//            if(!leixing.isEmpty()) {
//                mu.addPred(criteriaBuilder.equal(leixing.getPath(),leixing.getValue()));
//            }
//            //机型
//            Param jixing = mu.initParam("jitaihao.gongxu.id");
//            if(!jixing.isEmpty()) {
//                mu.addPred(criteriaBuilder.equal(jixing.getPath(),jixing.getValue()));
//            }
//            //机台号
//            Param jitaihao = mu.initParam("jitaihao.id");
//            if(!jitaihao.isEmpty()) {
//                mu.addPred(criteriaBuilder.equal(jitaihao.getPath(),jitaihao.getValue()));
//            }
//            //合约号
//            Param heyuehao = mu.initParam("heyuehao.id");
//            if(!heyuehao.isEmpty()) {
//                mu.addPred(criteriaBuilder.equal(heyuehao.getPath(),heyuehao.getValue()));
//            }
//            //单双轴
//            Param danshuangzhou = mu.initParam("danshuangzhou.id");
//            if(!danshuangzhou.isEmpty()) {
//                mu.addPred(criteriaBuilder.equal(danshuangzhou.getPath(),danshuangzhou.getValue()));
//            }
//            //优先级
//            Param youxianji = mu.initParam("youxianji");
//            if(!youxianji.isEmpty()) {
//                mu.addPred(criteriaBuilder.equal(youxianji.getPath(),youxianji.getValue()));
//            }
//            //状态
//            Param status = mu.initParam("status.id");
//            if(!status.isEmpty()) {
//                mu.addPred(criteriaBuilder.equal(status.getPath(),status.getValue()));
//            }
//            mu.addPred(criteriaBuilder.equal(root.get("deleted"),0));
//            return criteriaBuilder.and(mu.getPred());
//        };
//        return jihuaBujiRepository.findAll(specification,pageable);
//    }
//
//    @Autowired
//    private DictDao dictDao;
//
//
//    @Autowired
//    private ShebeiRepository shebeiRepository;
//
//    @Autowired
//    private HeYueHaoRepository heYueHaoRepository;
//
//    public JiHua_BuJi save(JiHua_BuJi jiHuaBuJi) {
//        Dict status = dictDao.findByTypecodeAndValue("bjjh_zhaungtai","1");
//        jiHuaBuJi.setStatus(status);
//        SheBei jitaihao = shebeiRepository.findById(jiHuaBuJi.getJitaihao().getId()).get();
//        jiHuaBuJi.setJitaihao(jitaihao);
//        Heyuehao heyuehao = heYueHaoRepository.findById(jiHuaBuJi.getHeyuehao().getId()).get();
//        jiHuaBuJi.setHeyuehao(heyuehao);
//        jiHuaBuJi.setDeleted(0);
//        jiHuaBuJi = jihuaBujiRepository.save(jiHuaBuJi);
//        return jiHuaBuJi;
//    }
//
//    public void deleteById(Long id) {
//        JiHua_BuJi jiHuaBuJi = jihuaBujiRepository.findById(id).get();
//        jiHuaBuJi.setDeleted(1);
//        jihuaBujiRepository.save(jiHuaBuJi);
//    }
//
//    @Autowired
//    private BaseService baseService;
//
//    public List<Object> getZhizhou(String jixing_id, String heyuehao_id, String leixing_id) {
//        ProcedureParamUtlis ppu=new ProcedureParamUtlis();
//        ppu.batchInAdd(jixing_id,heyuehao_id,leixing_id);
//        ProcedureContext pro=baseService.callProcedure("pc_jihua_getZhizhou", ppu.getList());
//        List<Object> aa = pro.getDatas();
//        return aa;
//    }
//
//
//    public void update(JiHua_BuJi jiHuaBuJi) {
//        Dict status = dictDao.findByTypecodeAndValue("bjjh_zhaungtai","1");
//        jiHuaBuJi.setStatus(status);
//        SheBei jitaihao = shebeiRepository.findById(jiHuaBuJi.getJitaihao().getId()).get();
//        jiHuaBuJi.setJitaihao(jitaihao);
//        Heyuehao heyuehao = heYueHaoRepository.findById(jiHuaBuJi.getHeyuehao().getId()).get();
//        jiHuaBuJi.setHeyuehao(heyuehao);
//        JiHua_BuJi jiHuaBuJiDB = jihuaBujiRepository.findById(jiHuaBuJi.getId()).get();
//        MyCopyProperties.copyProperties(jiHuaBuJi,jiHuaBuJiDB, Arrays.asList("riqi","banci","leixing","jitaihao","zhizhou","heyuehao","danshuangzhou","youxianji","beizhu"));
//        jihuaBujiRepository.save(jiHuaBuJiDB);
//    }

}
