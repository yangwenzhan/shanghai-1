package com.tianqiauto.textile.weaving.model.result;

import com.tianqiauto.textile.weaving.model.base.User;
import com.tianqiauto.textile.weaving.model.sys.Shift_JiangSha;
import com.tianqiauto.textile.weaving.util.copy.MyCopyProperties;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.Arrays;
import java.util.Date;

/**
 * @ClassName ResultShiftJiangSha
 * @Description TODO
 * @Author lrj
 * @Date 2019/4/25 8:40
 * @Version 1.0
 **/
@Data
public class ResultShiftJiangSha {

    private Long id;

    //班次，机台号，合约号，员工
    private String banci;
    private String banci_id;

    private String heyuehao;
    private String heyuehao_id;
    private String pibuguige;

    private String users;
    private String users_id;

    private String jitaihao;
    private String jitaihao_id;

    private Date riqi;

    private String changdu;
    private String shifouwancheng;
    private String beizhu;


    public static Page<ResultShiftJiangSha> convert(Page<Shift_JiangSha> shift_jiangShas){
        Page<ResultShiftJiangSha> dtoPage = shift_jiangShas.map(shift_jiangSha -> {
            ResultShiftJiangSha dto = new ResultShiftJiangSha();
            MyCopyProperties.copyProperties(shift_jiangSha, dto, Arrays.asList("id","riqi","changdu","shifouwancheng","beizhu"));
            if(shift_jiangSha.getJitaihao()!=null){
                dto.setJitaihao(shift_jiangSha.getJitaihao().getJitaihao());
                dto.setJitaihao_id(shift_jiangSha.getJitaihao().getId().toString());
            }
            if(shift_jiangSha.getBanci()!=null){
                dto.setBanci(shift_jiangSha.getBanci().getName());
                dto.setBanci_id(shift_jiangSha.getBanci().getId().toString());
            }
            if(shift_jiangSha.getHeyuehao()!=null){
                dto.setHeyuehao(shift_jiangSha.getHeyuehao().getName());
                dto.setHeyuehao_id(shift_jiangSha.getHeyuehao().getId().toString());
                if(shift_jiangSha.getHeyuehao().getOrder()!=null){
                    dto.setPibuguige(shift_jiangSha.getHeyuehao().getOrder().getPibuguige());
                }
            }
            if(shift_jiangSha.getUsers().size()>0){
                String users = "";
                String users_id = "";
                for(User user : shift_jiangSha.getUsers()){
                    users = users + user.getUsername()+user.getXingming()+"、";
                    users_id = users_id + user.getId().toString()+",";
                }
                dto.setUsers(users.substring(0,users.length()-1));
                dto.setUsers_id(users_id.substring(0,users_id.length()-1));
            }
            return dto;
        });
        return dtoPage;
    }

}
