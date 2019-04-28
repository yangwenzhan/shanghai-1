package com.tianqiauto.textile.weaving.repository;

import com.tianqiauto.textile.weaving.model.sys.BuGun;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BuGunRepository  extends JpaRepository<BuGun,Long>,JpaSpecificationExecutor<BuGun> {


}
