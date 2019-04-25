package com.tianqiauto.textile.weaving.controller.gongyi;

import com.tianqiauto.textile.weaving.model.sys.GongYi;
import com.tianqiauto.textile.weaving.model.sys.Heyuehao;
import com.tianqiauto.textile.weaving.model.sys.YuanSha;
import com.tianqiauto.textile.weaving.repository.GongYiRepository;
import com.tianqiauto.textile.weaving.repository.HeYueHaoRepository;
import com.tianqiauto.textile.weaving.repository.YuanShaRepository;
import com.tianqiauto.textile.weaving.util.result.Result;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @ClassName GongYiController
 * @Description TODO
 * @Author xingxiaoshuai
 * @Date 2019-04-16 18:11
 * @Version 1.0
 **/

@RestController
@RequestMapping("gongyi")
public class GongYiController {



    @Autowired
    private GongYiRepository gongYiRepository;

    @Autowired
    private HeYueHaoRepository heYueHaoRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private YuanShaRepository yuanShaRepository;


    //查找未关联工艺的合约号
   @GetMapping("findHeyuehaoWithoutGongYi")
   public Result findHeyuehaoWithoutGongYi(){
       String sql = "select id,name from sys_heyuehao where gongyi_id is null";
       List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
       return Result.ok(list.size()+"",jdbcTemplate.queryForList(sql));
   }

   //工艺新增第一步：新增概况
    @GetMapping("add_gaikuang")
    public Result add_gaikuang(GongYi gongYi, Long heyuehaoId){

        GongYi gongyiSaved = gongYiRepository.save(gongYi);

        Heyuehao heyuehao = heYueHaoRepository.getOne(heyuehaoId);
        heyuehao.setGongYi(gongyiSaved);
        heYueHaoRepository.save(heyuehao);

       return Result.ok("概况新增成功",gongyiSaved);

    }


    //工艺：查询仓库中原纱库存>0的
    @GetMapping("add_yuansha")
    public Result add_yuansha(){
        List<YuanSha> list = yuanShaRepository.findAllByKucunliangGreaterThan(0d);
        return Result.ok(list);
    }




}
