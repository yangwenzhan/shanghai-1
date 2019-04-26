package com.tianqiauto.textile.weaving.repository;

import com.tianqiauto.textile.weaving.model.sys.Shift_Zhengjing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ShiftZhengJingRepository  extends JpaRepository<Shift_Zhengjing,Long>,JpaSpecificationExecutor<Shift_Zhengjing> {


}
