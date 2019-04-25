package com.tianqiauto.textile.weaving.model.result;

import com.tianqiauto.textile.weaving.model.base.User;
import com.tianqiauto.textile.weaving.model.sys.Shift_ChuanZong;
import com.tianqiauto.textile.weaving.util.copy.MyCopyProperties;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.Arrays;
import java.util.Date;

/**
 * @ClassName ResultShiftChuanZong
 * @Description TODO
 * @Author lrj
 * @Date 2019/4/25 10:23
 * @Version 1.0
 **/
@Data
public class ResultShiftChuanZong {

    private Long id;

    //班次，机台号，织轴号，合约号，员工
    private String banci;
    private String banci_id;

    private String heyuehao;
    private String heyuehao_id;
    private String pibuguige;

    private String users;
    private String users_id;

    private String jitaihao;
    private String jitaihao_id;

    private String zhiZhou;
    private String zhiZhou_id;

    private Date riqi;

    private String genshu;
    private String shifouwancheng;
    private String beizhu;

    public static Page<ResultShiftChuanZong> convert(Page<Shift_ChuanZong> shift_chuanZongs){
        Page<ResultShiftChuanZong> dtoPage = shift_chuanZongs.map(shift_chuanZong -> {
            ResultShiftChuanZong dto = new ResultShiftChuanZong();
            MyCopyProperties.copyProperties(shift_chuanZong, dto, Arrays.asList("id","riqi","genshu","shifouwancheng","beizhu"));
            if(shift_chuanZong.getJitaihao()!=null){
                dto.setJitaihao(shift_chuanZong.getJitaihao().getJitaihao());
                dto.setJitaihao_id(shift_chuanZong.getJitaihao().getId().toString());
            }
            if(shift_chuanZong.getBanci()!=null){
                dto.setBanci(shift_chuanZong.getBanci().getName());
                dto.setBanci_id(shift_chuanZong.getBanci().getId().toString());
            }
            if(shift_chuanZong.getHeyuehao()!=null){
                dto.setHeyuehao(shift_chuanZong.getHeyuehao().getName());
                dto.setHeyuehao_id(shift_chuanZong.getHeyuehao().getId().toString());
                if(shift_chuanZong.getHeyuehao().getOrder()!=null){
                    dto.setPibuguige(shift_chuanZong.getHeyuehao().getOrder().getPibuguige());
                }
            }
            if(shift_chuanZong.getZhiZhou()!=null){
                dto.setZhiZhou(shift_chuanZong.getZhiZhou().getZhouhao());
                dto.setZhiZhou_id(shift_chuanZong.getZhiZhou().getId().toString());
            }
            if(shift_chuanZong.getUsers().size()>0){
                String users = "";
                String users_id = "";
                for(User user : shift_chuanZong.getUsers()){
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
