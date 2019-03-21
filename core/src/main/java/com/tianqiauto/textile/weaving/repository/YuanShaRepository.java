package com.tianqiauto.textile.weaving.repository;

import com.tianqiauto.textile.weaving.model.sys.YuanSha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface YuanShaRepository extends JpaRepository<YuanSha,Long> {

    @Query(value = "select * from sys_yuansha where pihao=isnull(?1,pihao) and pinming=isnull(?2,pinming) and zhishu=isnull(?3,zhishu)",nativeQuery = true)
    List<YuanSha> findAllByPihaoAndPinmingAndZhishu(String pihao, String pinming, Integer zhishu);

    @Query(value = "update sys_yuansha set zhishu=?1,gongyingshang_id=?2,sebie=?3,sehao=?4,baozhuangxingshi_id=?5,beizhu=?6 where id=?7",nativeQuery = true)
    @Modifying
    void updPinZhong(Integer zhishu, Long gongyishang_id, String sebie, String sehao, Long baozhuangxingshi_id, String beizhu, Long id);

}
