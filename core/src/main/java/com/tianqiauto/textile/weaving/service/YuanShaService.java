package com.tianqiauto.textile.weaving.service;

import com.tianqiauto.textile.weaving.model.sys.YuanSha;
import com.tianqiauto.textile.weaving.repository.YuanShaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @Author bjw
 * @Date 2019/3/19 15:45
 */
@Service
public class YuanShaService {

    @Autowired
    private YuanShaRepository yuanShaRepository;

    public Page<YuanSha> findAll(Pageable pageable) {
        return yuanShaRepository.findAll(pageable);
    }
}
