package com.tianqiauto.textile.weaving.repository;

import com.tianqiauto.textile.weaving.model.base.PB_YunZhuanFangShi_Xiangqing_Gongxu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface YunZhuanFangShi_GongXu_Repository extends JpaRepository<PB_YunZhuanFangShi_Xiangqing_Gongxu,Long> {

    @Modifying
    @Query(value = "update base_pb_yunzhuanfangshi_xiangqing_gongxu set xiangqing_id=?1,sort=?2 where gongxu_id=?3",nativeQuery = true)
    PB_YunZhuanFangShi_Xiangqing_Gongxu updateGongXuYZFS(Long xq_id,int sort,Long gx_id);
}
