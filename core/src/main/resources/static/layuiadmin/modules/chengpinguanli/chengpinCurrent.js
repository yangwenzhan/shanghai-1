layui.define(['table', 'laydate', 'form', 'upload'], function (exports) {
    var $ = layui.$
        , table = layui.table
        , form = layui.form
        , upload = layui.upload
        , laydate = layui.laydate;

    //监听搜索
    form.on('submit(form_search_cp)', function (data) {
        var field = data.field;
        table.reload('chengpinCurrent_table', {
            where: field
        });
        return false;
    });

    //设置表格头
    var cp_cols = [[
        {field: 'id', title: 'id', hide: true}
        , {title: '订单号',templet: repNull('heyuehao.order.dingdanhao'),field: 'heyuehao.order.dingdanhao', sort: true,width:200}
        , {title: '合约号',templet: repNull('heyuehao.name'),field: 'heyuehao.name', sort: true,width:200}
        , {title: '客户编号描述',templet: repNull('heyuehao.kehubianhaomiaoshu'),width:200}
        , {title: '剩余长度',templet: repNull('changdu'),width:200}
        , {title: '操作', toolbar: '#chegping_caozhuo', fixed: 'right',width:200}
    ]];

    //初始化表格
    // initTable("chengpinCurrent_table", 'chengpinguanli/chengpinCurrent/query_page', 'get', cp_cols, table);

    var layerOpen;
    var xzcpId;
    $('#heyuehao_add,#heyuehao_edit').click(function () {
        xzcpId = $(this).attr('id');
        layerOpen = layer.open({
            type: 1
            , title: '原纱信息！'
            , content: $('#chengpinxinxiXZ')
            , area: ['80%', '80%']
            , success: function () { //弹出层打开成功时的回调。
                //初始化表格
                initTable("chengpinCurrent_table", 'chengpinguanli/chengpinCurrent/query_page', 'get', cp_cols, table);
            }
        })
    });
    table.on('tool(chengpinCurrent_table)', function (obj) {
        var data = obj.data;
        if (obj.event === 'xuanzeCP') {
            if (xzcpId == 'heyuehao_add') {
                chengpinCurrent_add = data;
                // fromSetVel(form, 'form_add', chengpinCurrent_add);
                $("#shengyuchangdu_add").val(chengpinCurrent_add.changdu);
                $("#heyuehao_add").val(chengpinCurrent_add.heyuehao.name)
            }
            if (xzcpId == 'heyuehao_edit') {
                chengpinCurrent_edit = data;
                // fromSetVel(form, 'form_edit', chengpinCurrent_add);
                $("#shengyuchangdu_edit").val(chengpinCurrent_edit.changdu);
                $("#heyuehao_edit").val(chengpinCurrent_edit.heyuehao.name)
            }
            layer.close(layerOpen);
        }
    });

    /**
     * 2019/03/23 bjw
     * 添加和更新处理关联对象
     * @param obj
     */
    function encObject(obj) {
        $.each(obj, function (key, val) {
            val = !val ? "''" : "'"+val+"'";
            var arr = key.split('.');
            if (arr.length <= 1) {
                return true;
            }
            var textObj = 'obj';
            var currentObj = obj; //当前对象
            for (var i = 0; i < arr.length; i++) {
                if (i == arr.length - 1) {
                    eval(textObj + "." + arr[i] + "=" + val);
                } else {
                    textObj += '.' + arr[i];
                    if (currentObj[arr[i]] == undefined || null == currentObj[arr[i]]) {
                        eval(textObj + '= {}');
                    }
                }
            }
        });
    }

    /**
     * 2019/03/24 bjw
     * 处理根据对象默认select选择列表。
     * @param formId  表单div的id
     * @param ObjVal  值对象
     */
    function fromSetVel(from, formId, data) {
        var arrObj = $('#' + formId).find(":input[name *= '.']");
        for (var i = 0; i < arrObj.length; i++) {
            var name = arrObj[i].name;
            var arr = name.split('.');
            if (arr.length <= 1) continue;
            var currentObj = data;
            for (var j = 0; j < arr.length; j++) {
                if (currentObj != undefined && null != currentObj)
                    currentObj = currentObj[arr[j]]
            }
            if (currentObj != undefined && null != currentObj)
                data[name] = currentObj;
        }
        form.val(formId, data);
    }

    tq_verify(form);//给form添加自定义校验

    /**
     * 渲染select
     * @param eleId select原素id
     * @param selectedId 默认选中值 value
     * @param url 查询值的路径
     * @param type 类型
     * @param data 查询的参数
     * @param key   取值的key-name
     * @param val   取值的val-name
     * @constructor
     */
    function InitSelect(eleId, selectedId, url, type, data, key, val) {
        $.ajax({
            url: layui.setter.host + url,
            async: false,
            data: data,
            type: type,
            success: function (data) {
                initDownList(data, eleId, selectedId, key, val, true);
                form.render();
            }
        });
    }


    exports('chengpinCurrent', {})
});