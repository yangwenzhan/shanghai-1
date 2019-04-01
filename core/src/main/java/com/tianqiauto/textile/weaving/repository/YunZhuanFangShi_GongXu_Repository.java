package com.tianqiauto.textile.weaving.repository;

import com.tianqiauto.textile.weaving.model.base.PB_YunZhuanFangShi_Xiangqing_Gongxu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface YunZhuanFangShi_GongXu_Repository extends JpaRepository<PB_YunZhuanFangShi_Xiangqing_Gongxu,Long> {

    @Transactional
    @Modifying
    @Query(value = "update base_pb_yunzhuanfangshi_xiangqing_gongxu set xiangqing_id=?1 where gongxu_id=?2",nativeQuery = true)
    void updateGongXuYZFS(String xq_id,String gxid);
}
