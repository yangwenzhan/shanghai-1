package com.tianqiauto.textile.weaving.caiji.jintianju;

import io.github.biezhi.excel.plus.annotation.ExcelColumn;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @ClassName Model_Shift_Current
 * @Description TODO
 * @Author xingxiaoshuai
 * @Date 2019-04-24 08:46
 * @Version 1.0
 **/

@Data
public class Model_Shift_Current {


    @ExcelColumn(index = 3)
    private String  date;

    @ExcelColumn(index = 4)
    private String  time;
    private Date time_computed;   //时间
    public Date getTime_computed() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/M/d H:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(this.date + " " + this.time, dtf);
        return  Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }






    @ExcelColumn(index = 6)  //车速
    private String  chesu;



    @ExcelColumn(index = 9)
    private Integer  daweicishu;  //打纬次数
    public Integer getDaweicishu() {
        return daweicishu*100;
    }

    @ExcelColumn(index = 10)
    private Integer  buchang;  //当班布长
    public Double getBuchang() {
        return buchang*0.1;
    }

    @ExcelColumn(index = 11)
    private Integer  zongtingtime;  //总停时间
    public Integer getZongtingtime() {
        return zongtingtime*6;
    }

    @ExcelColumn(index = 12)
    private Integer  weitingtime;  //纬停时间
    public Integer getWeitingtime() {
        return weitingtime*6;

    }

    @ExcelColumn(index = 13)
    private Integer  jingtingtime;  //经停时间
    public Integer getJingtingtime() {
        return jingtingtime*6;

    }


    @ExcelColumn(index = 15)
    private Integer  zongtimecishu;  //总停次数

    @ExcelColumn(index = 16)
    private Integer  weitingcishu;  //纬停次数

    @ExcelColumn(index = 17)
    private Integer  jingtingcishu;  //经停次数




    @ExcelColumn(index = 19)
    private Integer  xiaolv;  //效率
    public Double getXiaolv(){
        return  xiaolv*0.001;
    }












}
