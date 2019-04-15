package com.tianqiauto.textile.weaving.controller.yuanshaguanli;

import com.tianqiauto.textile.weaving.model.sys.YuanSha;
import com.tianqiauto.textile.weaving.model.sys.YuanSha_ChuKu;
import com.tianqiauto.textile.weaving.service.yuanshaguanli.YuanshakucunchakanServer;
import com.tianqiauto.textile.weaving.util.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author bjw
 * @Date 2019/4/11 13:46
 */
@RestController
@RequestMapping("yuanshaguanli/yuanshakucunchakan")
public class YuanshakucunchakanController {

    @Autowired
    private YuanshakucunchakanServer yuanshakucunchakanServer;

    @GetMapping("query_page")
    public Result findAll(YuanSha yuanSha, @PageableDefault( sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable) {
        return Result.ok(yuanshakucunchakanServer.findAll(yuanSha,pageable));
    }


}
