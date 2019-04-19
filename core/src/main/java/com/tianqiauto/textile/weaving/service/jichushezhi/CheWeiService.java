package com.tianqiauto.textile.weaving.service.jichushezhi;

import com.tianqiauto.textile.weaving.model.base.User;
import com.tianqiauto.textile.weaving.model.sys.CheWei;
import com.tianqiauto.textile.weaving.model.sys.CheWeiCurrent;
import com.tianqiauto.textile.weaving.repository.CheWeiCurrentRepository;
import com.tianqiauto.textile.weaving.repository.CheWeiRepository;
import com.tianqiauto.textile.weaving.repository.UserRepository;
import com.tianqiauto.textile.weaving.util.procedure.core.ProcedureParamUtlis;
import com.tianqiauto.textile.weaving.util.procedure.model.ProcedureContext;
import com.tianqiauto.textile.weaving.util.procedure.service.BaseService;
import com.tianqiauto.textile.weaving.util.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class CheWeiService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private BaseService baseService;

    @Autowired
    private CheWeiRepository cheWeiRepository;

    @Autowired
    private CheWeiCurrentRepository cheWeiCurrentRepository;

    @Autowired
    private UserRepository userRepository;

    //查询固定车位
    public Result findCheWei(String ssgx,String sslb,String user,String jtgx,String jtjx){
        ProcedureParamUtlis ppu=new ProcedureParamUtlis();
        ppu.addInVarchar(ssgx).addInVarchar(sslb).addInVarchar(user).addInVarchar(jtgx).addInVarchar(jtjx);
        ProcedureContext proc = baseService.callProcedure("pc_base_chewei",ppu.getList());
        return Result.ok("查询成功",proc.getDatas());
    }

    //批量修改固定车位信息
    public void updCheWei(List<CheWei> cheWeis){
        /*根据机台id轮班id查找车位实体，将实体delete，车位员工表级联删除，再重新保存车位，并级联保存车位员工表*/
        /*cheWei:{jitai_id,lunban_id,user_id}*/
        Set<User> userSet = new HashSet<>();
        for(User user : cheWeis.get(0).getUsers()){
            User userInDB = userRepository.getOne(user.getId());
            userSet.add(userInDB);
        }
        for(CheWei cheWei : cheWeis){
            CheWei cheWeiInDB = cheWeiRepository.findCheWeiByJitaihaoAndLunban(cheWei.getJitaihao(),cheWei.getLunban());
            if(StringUtils.isEmpty(cheWeiInDB)){
                cheWei.setUsers(userSet);
                cheWeiRepository.save(cheWei);
            }else{
                cheWeiInDB.setUsers(null);
                cheWeiRepository.save(cheWeiInDB);
                cheWeiInDB.setUsers(userSet);
                cheWeiRepository.save(cheWeiInDB);
            }
        }
    }


    //查询临时车位
    public Result findCheWei_Current(String ssgx,String sslb,String user,String jtgx,String jtjx){
        ProcedureParamUtlis ppu=new ProcedureParamUtlis();
        ppu.addInVarchar(ssgx).addInVarchar(sslb).addInVarchar(user).addInVarchar(jtgx).addInVarchar(jtjx);
        ProcedureContext proc = baseService.callProcedure("pc_base_chewei_current",ppu.getList());
        return Result.ok("查询成功",proc.getDatas());
    }

    //修改临时车位
    public void updCheWei_Current(List<CheWeiCurrent> cheWeiCurrents){
        /*根据机台id轮班id查找车位实体，将实体delete，车位员工表级联删除，再重新保存车位，并级联保存车位员工表*/
        /*cheWei:{jitai_id,lunban_id,user_id}*/
        Set<User> userSet = new HashSet<>();
        for(User user : cheWeiCurrents.get(0).getUsers()){
            User userInDB = userRepository.getOne(user.getId());
            userSet.add(userInDB);
        }
        for(CheWeiCurrent cheWeiCurrent : cheWeiCurrents){
            CheWeiCurrent cheWeiCurrentInDB = cheWeiCurrentRepository.findCheWeiCurrentByJitaihao(cheWeiCurrent.getJitaihao());
            if(StringUtils.isEmpty(cheWeiCurrentInDB)){
                cheWeiCurrent.setUsers(userSet);
                cheWeiCurrentRepository.save(cheWeiCurrent);
            }else{
                cheWeiCurrentInDB.setUsers(null);
                cheWeiCurrentRepository.save(cheWeiCurrentInDB);
                cheWeiCurrentInDB.setUsers(userSet);
                cheWeiCurrentRepository.save(cheWeiCurrentInDB);
            }
        }
    }


}
