package com.tianqiauto.textile.weaving.model.result;

import com.tianqiauto.textile.weaving.model.base.User;
import com.tianqiauto.textile.weaving.model.sys.Shift_Zhengjing;
import com.tianqiauto.textile.weaving.util.copy.MyCopyProperties;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.Arrays;
import java.util.Date;

/**
 * @ClassName ResultShiftZhengJing
 * @Description TODO
 * @Author lrj
 * @Date 2019/4/24 10:34
 * @Version 1.0
 **/
@Data
public class ResultShiftZhengJing {

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

    private String flag;
    private String type;
    private String changdu;
    private String shifouwancheng;
    private String tiaoshu;
    private String beizhu;

    public static Page<ResultShiftZhengJing> convert(Page<Shift_Zhengjing> shift_zhengjings){
        Page<ResultShiftZhengJing> dtoPage = shift_zhengjings.map(shift_zhengjing -> {
            ResultShiftZhengJing dto = new ResultShiftZhengJing();
            MyCopyProperties.copyProperties(shift_zhengjing, dto, Arrays.asList("id","riqi","flag","changdu","shifouwancheng","tiaoshu","beizhu"));

            if(shift_zhengjing.getJitaihao()!=null){
                dto.setJitaihao(shift_zhengjing.getJitaihao().getJitaihao());
                dto.setJitaihao_id(shift_zhengjing.getJitaihao().getId().toString());
            }
            if(shift_zhengjing.getBanci()!=null){
                dto.setBanci(shift_zhengjing.getBanci().getName());
                dto.setBanci_id(shift_zhengjing.getBanci().getId().toString());
            }
            if(shift_zhengjing.getHeyuehao()!=null){
                dto.setHeyuehao(shift_zhengjing.getHeyuehao().getName());
                dto.setHeyuehao_id(shift_zhengjing.getHeyuehao().getId().toString());
                if(shift_zhengjing.getHeyuehao().getOrder()!=null){
                    dto.setPibuguige(shift_zhengjing.getHeyuehao().getOrder().getPibuguige());
                }
            }
            if(shift_zhengjing.getUsers().size()>0){
                String users = "";
                String users_id = "";
                for(User user : shift_zhengjing.getUsers()){
                    users = users + user.getUsername()+user.getXingming()+"、";
                    users_id = users_id + user.getId().toString()+",";
                }
                dto.setUsers(users.substring(0,users.length()-1));
                dto.setUsers_id(users_id.substring(0,users_id.length()-1));
            }
            if(shift_zhengjing.getFlag()!=null){
                //1轴 2桶
                if(shift_zhengjing.getFlag().equals(1)){
                    dto.setType("轴");
                }else{
                    dto.setType("桶");
                }
            }
            return dto;
        });
        return dtoPage;
    }

}
