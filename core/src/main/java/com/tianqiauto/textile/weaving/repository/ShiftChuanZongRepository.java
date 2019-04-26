package com.tianqiauto.textile.weaving.repository;

import com.tianqiauto.textile.weaving.model.sys.Shift_ChuanZong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ShiftChuanZongRepository  extends JpaRepository<Shift_ChuanZong,Long>,JpaSpecificationExecutor<Shift_ChuanZong> {
}
