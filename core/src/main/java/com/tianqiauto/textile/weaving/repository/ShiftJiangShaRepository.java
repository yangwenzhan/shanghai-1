package com.tianqiauto.textile.weaving.repository;

import com.tianqiauto.textile.weaving.model.sys.Shift_JiangSha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ShiftJiangShaRepository  extends JpaRepository<Shift_JiangSha,Long>,JpaSpecificationExecutor<Shift_JiangSha> {
}
