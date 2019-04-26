package com.tianqiauto.textile.weaving.service.common;

import com.tianqiauto.textile.weaving.model.base.Dict;
import com.tianqiauto.textile.weaving.model.base.SheBei;
import com.tianqiauto.textile.weaving.repository.Dict_TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName CommonService
 * @Description TODO
 * @Author lrj
 * @Date 2019/3/25 15:14
 * @Version 1.0
 **/
@Service
public class CommonService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private Dict_TypeRepository dict_typeRepository;

    public List<Map<String,Object>> findUserZu(){
        String sql = "select distinct zu from base_user_yuangong";
        return jdbcTemplate.queryForList(sql);
    }
    public List<Map<String,Object>> findCSLB(String gongxu,String jixing){
        String sql = "select * from sys_param_leibie where gongxu_id=isnull(?,gongxu_id) and jixing_id=isnull(?,jixing_id)";
        return jdbcTemplate.queryForList(sql, StringUtils.isEmpty(gongxu)?null:gongxu,StringUtils.isEmpty(jixing)?null:jixing);
    }

    public List<Map<String,Object>> findZhiJiJiXing(){
        String sql = "select * from base_gongxu where parent_id=(select id from base_gongxu where name='织布')";
        return jdbcTemplate.queryForList(sql);
    }

    @Transactional
    public Map<String,List<Dict>> DictFindAllByCodes(Set<String> codes) {
        String sql = "SELECT id,name,sort,[value] FROM base_dict WHERE exists(SELECT 1 FROM base_dict_type WHERE base_dict.type_id = base_dict_type.id AND base_dict_type.code = ? ) ORDER BY sort";
        Map<String,List<Dict>> map = new HashMap<>();
        for (String code:codes){
            List dicts = jdbcTemplate.query(sql,new BeanPropertyRowMapper<Dict>(Dict.class),code);
            map.put(code,dicts);
        }
        return map;
    }

    public List<SheBei> findByShebei_zhibu(String jixing_id) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT id,jitaihao,sort,zhizaoshang,gongxu_id FROM base_shebei WHERE  exists ");
        sb.append("( select 1 from base_gongxu where parent_id=(select id from base_gongxu where name='织布') AND base_shebei.gongxu_id = base_gongxu.id) AND deleted = 0 ");
        if(!StringUtils.isEmpty(jixing_id)){
            sb.append(" AND gongxu_id = '"+jixing_id+"' ");
        }
        sb.append("order BY sort ");
        return jdbcTemplate.query(sb.toString(),new BeanPropertyRowMapper<SheBei>(SheBei.class));
    }
  
    //查询员工
    public List<Map<String,Object>> findUser(String gxid, String lbid, String roleid){
        gxid = StringUtils.isEmpty(gxid)?null:gxid;
        lbid = StringUtils.isEmpty(lbid)?null:lbid;
        roleid = StringUtils.isEmpty(roleid)?null:roleid;
        String sql = "select distinct a.id,username,xingming,username+'  '+xingming as ygxm from base_user a " +
                "left join base_user_role b on a.id=b.user_id " +
                "where shifouzaizhi=1 " +
                "and (gongxu_id=isnull(?,gongxu_id) or isnull(?,gongxu_id) is null) " +
                "and (lunban_id=isnull(?,lunban_id) or isnull(?,lunban_id) is null) " +
                "and (b.role_id=isnull(?,b.role_id) or isnull(?,b.role_id) is null) ";
        return jdbcTemplate.queryForList(sql,gxid,gxid,lbid,lbid,roleid,roleid);
    }

    //查询所有合约号
    public List<Map<String,Object>> findHeYueHao(Long id){
        String sql = "select a.id,a.name,b.pibuguige from sys_heyuehao a left join sys_order b on a.order_id=b.id where a.id=isnull(?,a.id) order by name desc";
        return jdbcTemplate.queryForList(sql,id);
    }

    //根据工序机型名称查询机台号
    public List<Map<String,Object>> findJiTaiHao(String gongxu,String jixing){
        String sql = "select * from base_shebei where gongxu_id in ( " +
                " select id from base_gongxu where parent_id in (select id from base_gongxu where name=isnull(?,name)) " +
                " and id in (select id from base_gongxu where name=isnull(?,name)) " +
                " )";
        return jdbcTemplate.queryForList(sql,StringUtils.isEmpty(gongxu)?null:gongxu,StringUtils.isEmpty(jixing)?null:jixing);
    }

    //查询工序id
    public Integer findGongXuID(String name){
        String sql = "select top 1 id from base_gongxu where name=?";
        return jdbcTemplate.queryForObject(sql,Integer.class,name);
    }

    //查询工序当前班次轮班
    public List<Map<String,Object>> findCurrentBCLB_NativeQuery(String name){
        String sql = " select * from( " +
                " select bancijieshushijian,bancikaishishijian,riqi, " +
                " banci_id,(select name from base_dict with(nolock) where id=banci_id) as banci, " +
                " gongxu_id,(select name from base_gongxu with(nolock) where id=gongxu_id) as gongxu, " +
                " lunban_id,(select name from base_dict with(nolock) where id=lunban_id) as lunban " +
                " from base_pb_current with(nolock) " +
                " ) as T where T.gongxu=?";
        return jdbcTemplate.queryForList(sql,name);
    }

}