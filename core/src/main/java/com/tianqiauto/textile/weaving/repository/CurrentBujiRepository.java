package com.tianqiauto.textile.weaving.repository;

import com.tianqiauto.textile.weaving.model.sys.Current_BuJi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CurrentBuJiRepository  extends JpaRepository<Current_BuJi,Long>,JpaSpecificationExecutor<Current_BuJi> {
}
