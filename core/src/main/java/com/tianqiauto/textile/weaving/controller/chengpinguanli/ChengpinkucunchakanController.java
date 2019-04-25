package com.tianqiauto.textile.weaving.controller.chengpinguanli;

import com.tianqiauto.textile.weaving.model.sys.Chengpin_Current;
import com.tianqiauto.textile.weaving.model.sys.Heyuehao;
import com.tianqiauto.textile.weaving.service.chengpinguanli.ChengpinCurrentServer;
import com.tianqiauto.textile.weaving.service.chengpinguanli.ChengpinkucunchakanServer;
import com.tianqiauto.textile.weaving.util.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

/**
 * @Author bjw
 * @Date 2019/3/30 11:04
 */
@RestController
@RequestMapping("chengpinguanli/chengpinkucunchakan")
public class ChengpinkucunchakanController {

    @Autowired
    private ChengpinkucunchakanServer chengpinkucunchakanServer;

    @GetMapping("query_page")
    public Result findAll(Chengpin_Current chengpin_current, @PageableDefault( sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable) {
        return Result.ok(chengpinkucunchakanServer.findAll(chengpin_current,pageable));
    }

}
