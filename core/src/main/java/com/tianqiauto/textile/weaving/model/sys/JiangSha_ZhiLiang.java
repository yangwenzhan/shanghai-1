package com.tianqiauto.textile.weaving.model.sys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @ClassName JiangSha_ZhiLiang
 * @Description TODO
 * @Author xingxiaoshuai
 * @Date 2019-02-27 16:15
 * @Version 1.0
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "sys_jiangsha_zhiliang")
public class JiangSha_ZhiLiang {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "heyuehao_id")
    private Heyuehao heyuehao;

    private String ganghao;  //缸号


    private String niandumiao;  //粘度秒
    private String jiangtonghangulv;  //浆桶含固率
    private String jiangcaohangulv;  //浆槽含固率
    private String shangjianglv;  //上浆率%
    private String shangjianghouqiangli;  //上浆后强力cn
    private String qianglizengqiang;  //强力增强%
    private String shangjianghuichao;  //上浆回潮%
    private String zhengjingzongchangdu;  //整经重长度m
    private String jiangshazongchangdu;  //浆纱总长度m
    private String shenchang;  //伸长




}
