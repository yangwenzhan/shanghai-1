package com.tianqiauto.textile.weaving.service.jichushezhi;

import com.tianqiauto.textile.weaving.model.base.PanCunYue;
import com.tianqiauto.textile.weaving.repository.PanCunYueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName PanCunYueService
 * @Description TODO
 * @Author lrj
 * @Date 2019/4/1 18:59
 * @Version 1.0
 **/
@Service
@Transactional
public class PanCunYueService {

    @Autowired
    private PanCunYueRepository panCunYueRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Page<PanCunYue> findAll(PanCunYue panCunYue, Pageable pageable){

        Specification<PanCunYue> specification = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList();

            if(!StringUtils.isEmpty(panCunYue.getNian())) {
                predicates.add(criteriaBuilder.equal(root.get("nian"),panCunYue.getNian()));
            }
            if(!StringUtils.isEmpty(panCunYue.getYue())) {
                predicates.add(criteriaBuilder.equal(root.get("yue"),panCunYue.getYue()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        return panCunYueRepository.findAll(specification,pageable);
    }

    //新增
    public void addPanCunYue(PanCunYue panCunYue){
        //将上个月盘存结束和下个月盘存开始日期修改掉

        String ksrq = panCunYue.getKaishiriqi();
        Long ksbc = panCunYue.getKaishibanci().getId();
        panCunYue.setKaishi(ksrq,panCunYue.getKaishibanci());
        String ks = panCunYue.getKaishi();
        String jsrq = panCunYue.getJieshuriqi();
        Long jsbc = panCunYue.getJieshubanci().getId();
        panCunYue.setJieshu(jsrq,panCunYue.getJieshubanci());
        String js = panCunYue.getJieshu();
        String nian = panCunYue.getNian();
        String yue = panCunYue.getYue();

        String sql1 = "insert into base_pancunyue(kaishiriqi,kaishibanci_id,kaishi,jieshuriqi,jieshubanci_id,jieshu,nian,yue) values(?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql1,ksrq,ksbc,ks,jsrq,jsbc,js,nian,yue);

        Map<String,String> map = getRQBC(nian,yue,ksrq,ksbc.toString(),jsrq,jsbc.toString());
        if(!StringUtils.isEmpty(map.get("bf_id"))){
            String sql6="update base_pancunyue set jieshuriqi=?,jieshubanci_id=?,jieshu=? where id=?";
            jdbcTemplate.update(sql6,map.get("bf_rq"),map.get("bf_bcid"),map.get("bf_js"),map.get("bf_id"));
        }

        if(!StringUtils.isEmpty(map.get("nt_id"))){
            String sql7="update base_pancunyue set kaishiriqi=?,kaishibanci_id=?,kaishi=? where id=?";
            jdbcTemplate.update(sql7,map.get("nt_rq"),map.get("nt_bcid"),map.get("nt_ks"),map.get("nt_id"));
        }

    }

    //修改
    public void updatePanCunYue(PanCunYue panCunYue){

        Long id = panCunYue.getId();
        String ksrq = panCunYue.getKaishiriqi();
        Long ksbc = panCunYue.getKaishibanci().getId();
        panCunYue.setKaishi(ksrq,panCunYue.getKaishibanci());
        String ks = panCunYue.getKaishi();
        String jsrq = panCunYue.getJieshuriqi();
        Long jsbc = panCunYue.getJieshubanci().getId();
        panCunYue.setJieshu(jsrq,panCunYue.getJieshubanci());
        String js = panCunYue.getJieshu();
        String nian = panCunYue.getNian();
        String yue = panCunYue.getYue();

        //将上个月盘存结束和下个月盘存开始日期修改掉
        String sql1 = "update base_pancunyue set kaishiriqi=?,kaishibanci_id=?,kaishi=?,jieshuriqi=?,jieshubanci_id=?,jieshu=? where id=?";
        jdbcTemplate.update(sql1,ksrq,ksbc,ks,jsrq,jsbc,js,id);

        Map<String,String> map = getRQBC(nian,yue,ksrq,ksbc.toString(),jsrq,jsbc.toString());
        if(!StringUtils.isEmpty(map.get("bf_id"))){
            String sql6="update base_pancunyue set jieshuriqi=?,jieshubanci_id=?,jieshu=? where id=?";
            jdbcTemplate.update(sql6,map.get("bf_rq"),map.get("bf_bcid"),map.get("bf_js"),map.get("bf_id"));
        }

        if(!StringUtils.isEmpty(map.get("nt_id"))){
            String sql7="update base_pancunyue set kaishiriqi=?,kaishibanci_id=?,kaishi=? where id=?";
            jdbcTemplate.update(sql7,map.get("nt_rq"),map.get("nt_bcid"),map.get("nt_ks"),map.get("nt_id"));
        }

    }

    public Map<String,String> getRQBC(String nian,String yue,String ksrq,String ksbc,String jsrq,String jsbc){
        //获取上个月盘存id
        String sql2 = "select top 1 id from base_pancunyue where nian<=? and yue<? order by nian desc,yue desc";
        //获取下个月盘存id
        String sql5 = "select top 1 * from base_pancunyue where nian>=? and yue>? order by nian,yue";

        //查询班次名称
        String sql3 = "select name from base_dict where id=?";

        //查询数据库中存早夜班次的id
        String sql4 = "select * from base_dict where type_id=(select id from base_dict_type where code='banci')";

        String dbin_zb_id="";
        String dbin_yb_id="";
        List<Map<String,Object>> bc_List = jdbcTemplate.queryForList(sql4);
        if(bc_List.size()>0){
            for(Map<String,Object> map : bc_List){
                if(map.get("name").toString().contains("早")){dbin_zb_id=map.get("id").toString();}
                if(map.get("name").toString().contains("夜")){dbin_yb_id=map.get("id").toString();}
            }
        }

        //设置上个月结束盘存日期班次，下个月开始盘存日期班次
        String bf_bcid="",bf_rq="",bf_js="",bf_id="",
               nt_bcid="",nt_rq="",nt_ks="",nt_id="";

        List<Map<String,Object>> bef_id = jdbcTemplate.queryForList(sql2,nian,yue);
        if(bef_id.size()>0){
            //设置上个月的盘存结束日期班次
            List<Map<String,Object>> ksbc_name = jdbcTemplate.queryForList(sql3,ksbc);
            if(ksbc_name.size()>0){
                String ksbcStr = ksbc_name.get(0).get("name").toString();
                if(ksbcStr.contains("早")){
                    bf_bcid = dbin_yb_id;
                    bf_rq = getSpecifiedDayBefore(ksrq);
                }else{
                    bf_bcid = dbin_zb_id;
                    bf_rq = ksrq;
                }
            }
            bf_id = bef_id.get(0).get("id").toString();
        }
        List<Map<String,Object>> next_id = jdbcTemplate.queryForList(sql5,nian,yue);
        if(next_id.size()>0){
            //设置下个月的盘存开始日期班次
            List<Map<String,Object>> jsbc_name = jdbcTemplate.queryForList(sql3,jsbc);
            if(jsbc_name.size()>0){
                String jsbcStr = jsbc_name.get(0).get("name").toString();
                if(jsbcStr.contains("夜")){
                    nt_bcid = dbin_zb_id;
                    nt_rq = getSpecifiedDayAfter(jsrq);
                }else{
                    nt_bcid = dbin_yb_id;
                    nt_rq = jsrq;
                }
            }
            nt_id = next_id.get(0).get("id").toString();
        }


        System.out.println(bf_rq);
        System.out.println(nt_rq);

        bf_js=bf_rq.replaceAll("-","")+bf_bcid;
        nt_ks=nt_rq.replaceAll("-","")+nt_bcid;


        Map<String,String> map = new HashMap<>();
        map.put("bf_bcid",bf_bcid);
        map.put("bf_rq",bf_rq);
        map.put("bf_js",bf_js);
        map.put("bf_id",bf_id);
        map.put("nt_bcid",nt_bcid);
        map.put("nt_rq",nt_rq);
        map.put("nt_ks",nt_ks);
        map.put("nt_id",nt_id);

        return map;

    }

    //获取指定日期的前一天
    public String getSpecifiedDayBefore(String specifiedDay){
        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        Date date=null;
        try {
            date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day=c.get(Calendar.DATE);
        c.set(Calendar.DATE,day-1);

        String dayBefore=new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        return dayBefore;
    }

    //获取指定日期的后一天
    public String getSpecifiedDayAfter(String specifiedDay){
        Calendar c = Calendar.getInstance();
        Date date=null;
        try {
            date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day=c.get(Calendar.DATE);
        c.set(Calendar.DATE,day+1);

        String dayAfter=new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        return dayAfter;
    }

}
