package com.tianqiauto.textile.weaving.caiji.PicanolLoomModule.dao.repository;

import com.tianqiauto.textile.weaving.caiji.PicanolLoomModule.bean.PicanolHost;
import com.tianqiauto.textile.weaving.model.sys.Chengpin_ChuKu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

/**
 * @Author bjw
 * @Date 2019/3/9 15:34
 */
public interface PicanolHostRepository extends JpaRepository<PicanolHost,Long> ,JpaSpecificationExecutor<PicanolHost> {

    PicanolHost findByIp(String ip);

}
