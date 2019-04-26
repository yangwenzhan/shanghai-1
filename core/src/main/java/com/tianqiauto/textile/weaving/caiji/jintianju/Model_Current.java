package com.tianqiauto.textile.weaving.caiji.jintianju;

import io.github.biezhi.excel.plus.annotation.ExcelColumn;
import io.swagger.models.auth.In;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @ClassName Model_Current
 * @Description TODO
 * @Author xingxiaoshuai
 * @Date 2019-04-23 19:15
 * @Version 1.0
 **/

@Data
public class Model_Current {

    @ExcelColumn(index = 0)
    private String  date;

    @ExcelColumn(index = 1)
    private String  time;
    private Date time_computed;   //时间
    public Date getTime_computed() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/M/d HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(this.date + " " + this.time, dtf);
        return  Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    @ExcelColumn(index = 2)
    private String status;  //状态

    @ExcelColumn(index = 19)  //设定落布长度
    private Integer doffing_length;
    public Double getDoffing_length() {
        return doffing_length*0.1;
    }

    @ExcelColumn(index = 20)  //运行时间s
    private Integer yunxingshijian;
    public Integer getYunxingshijian() {
        return yunxingshijian*6;
    }


    @ExcelColumn(index = 22)  //当前布辊布长
    private Integer buchang;
    public Double getBuchang() {
        return buchang*0.1;
    }


    @ExcelColumn(index = 24)  //轴设定经长
    private Integer jingchang;
    public Double getJingchang() {
        return jingchang*0.1;
    }

    @ExcelColumn(index = 29)  //预计落布时间 min
    private Integer yujiluobu;

    @ExcelColumn(index = 31)  //预计了解时间 hour
    private Integer yujiliaoji;

}
