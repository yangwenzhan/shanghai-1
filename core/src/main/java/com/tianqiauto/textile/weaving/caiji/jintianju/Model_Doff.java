package com.tianqiauto.textile.weaving.caiji.jintianju;

import io.github.biezhi.excel.plus.annotation.ExcelColumn;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @Author xingxiaoshuai
 * @Date 2019-04-23 19:15
 * @Version 1.0
 **/

@Data
public class Model_Doff {

    @ExcelColumn(index = 1)
    private String  date;

    @ExcelColumn(index = 2)
    private String  time;
    private Date time_computed;   //时间
    public Date getTime_computed() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/M/d HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(this.date + " " + this.time, dtf);
        return  Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    @ExcelColumn(index = 6)
    private Integer buchang;  //布长
    public Double getBuchang(){
        return buchang*0.1;
    }

}
