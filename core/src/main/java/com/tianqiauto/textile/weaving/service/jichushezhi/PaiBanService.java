package com.tianqiauto.textile.weaving.service.jichushezhi;

import com.tianqiauto.textile.weaving.model.base.PB_YunZhuanFangShi;
import com.tianqiauto.textile.weaving.model.base.PB_YunZhuanFangShi_Xiangqing;
import com.tianqiauto.textile.weaving.repository.YunZhuanFangShi_GongXu_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class PaiBanService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private YunZhuanFangShi_GongXu_Repository yunZhuanFangShi_gongXu_repository;


    //修改运转方式详情
    @Transactional
    public void upd_yzfs_Info(PB_YunZhuanFangShi paibanRole){

        List<Object[]> list = new ArrayList<>();
        String yzfs_id = paibanRole.getId().toString();
        Set<PB_YunZhuanFangShi_Xiangqing> xqSet = paibanRole.getYunZhuanFangShi_xiangqingSet();

        for(PB_YunZhuanFangShi_Xiangqing xq:xqSet){
            String lb_id = xq.getLunban().getId().toString();
            String pxh = xq.getSort().toString();
            String kssj = xq.getKaishishijian();
            String jssj = xq.getJieshushijian();

            String[] arr = new String[5];
            arr[0] = lb_id;
            arr[1] = kssj;
            arr[2] = jssj;
            arr[3] = yzfs_id;
            arr[4] = pxh;
            list.add(arr);
        }

        String sql = "update base_pb_yunzhuanfangshi_xiangqing set lunban_id=?,kaishishijian=?,jieshushijian=? where yunzhuanfangshi_id=? and sort=?";
        jdbcTemplate.batchUpdate(sql,list);
    }

    /**
     * 新增运转方式详情
     * 班次起始时间暂时没写，因数据库表中没有，字段不完整
     * @return
     */
    @Transactional
    public void add_new_yzfs(PB_YunZhuanFangShi paibanRole){

        String sql1 = "insert into base_pb_yunzhuanfangshi(lunbanshu,name,paibanshu) values(?,?,?)";
        String sql2 = "insert into base_pb_yunzhuanfangshi_xiangqing(sort,banci_id,lunban_id,yunzhuanfangshi_id,kaishishijian,jieshushijian) values(?,?,?,?,?,?)";
        String sql3 = "select id from base_pb_yunzhuanfangshi where name=?";

        List<Object[]> list = new ArrayList<>();
        String name = paibanRole.getName();
        String lunbanshu = paibanRole.getLunbanshu().toString();
        String paibanshu = paibanRole.getPaibanshu().toString();

        int result = jdbcTemplate.update(sql1,lunbanshu,name,paibanshu);
        if(result>0){
            Map<String,Object> map = jdbcTemplate.queryForMap(sql3,name);
            String yzfs_id = map.get("id").toString();

            Set<PB_YunZhuanFangShi_Xiangqing> xqSet = paibanRole.getYunZhuanFangShi_xiangqingSet();
            for(PB_YunZhuanFangShi_Xiangqing xq:xqSet){
                String bc_id = xq.getBanci().getId().toString();
                String lb_id = xq.getLunban().getId().toString();
                String pxh = xq.getSort().toString();
                String kssj = xq.getKaishishijian();
                String jssj = xq.getJieshushijian();

                String[] arr = new String[6];
                arr[0] = pxh;
                arr[1] = bc_id;
                arr[2] = lb_id;
                arr[3] = yzfs_id;
                arr[4] = kssj;
                arr[5] = jssj;
                list.add(arr);
            }
            jdbcTemplate.batchUpdate(sql2,list);
        }

    }


}
