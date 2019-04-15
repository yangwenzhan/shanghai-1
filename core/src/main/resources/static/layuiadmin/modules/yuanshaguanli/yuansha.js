layui.define(['table', 'laydate', 'form', 'upload'], function (exports) {
    var $ = layui.$
        , table = layui.table
        , form = layui.form
        , upload = layui.upload
        , laydate = layui.laydate;

//-----------------------------------------------------------------------------------------------------------添加选择原纱信息（以下）
    //选择原纱信息
    var yuansha_cols = [[
        {field: 'id', title: 'id', hide: true}
        , {field: 'createTime', title: '创建时间'}
        , {field: 'pinming', title: '品名'}
        , {field: 'pihao', title: '批号'}
        , {field: 'zhishu', title: '支数'}
        , {field: 'sehao', title: '色号'}
        , {field: 'sebie', title: '色别'}
        , {templet: repNull('gongyingshang.name'), title: '供应商'}
        , {field: 'kucunliang', title: '库存量'}
        , {title: '操作', toolbar: '#yuansha_caozuo', fixed: 'right'}
    ]];
    var layerOpenYuansha;
    var xzysId;
    $('#xzys_add,#xzys_edit').click(function () {
        xzysId = $(this).attr('id');
        layerOpenYuansha = layer.open({
            type: 1
            , title: '原纱信息！'
            , content: $('#yuanshaxinxiXZ')
            , area: ['80%', '80%']
            , success: function () { //弹出层打开成功时的回调。
                //初始化表格
                initTable("yuansha_table", 'dingdanguanli/heyuehaoyuansha/query_page', 'get', yuansha_cols, table);
            }
        })
    });
    table.on('tool(yuansha_table)', function (obj) {
        var data = obj.data;
        if (obj.event === 'xuanzeYS') {
            if (xzysId == 'xzys_add') {
                yuansha_add = data;
                fromSetVel(form, 'form_ysxx_add', yuansha_add);
            }
            if (xzysId == 'xzys_edit') {
                yuansha_edit = data;
                fromSetVel(form, 'form_ysxx_edit', yuansha_edit);
            }
            layer.close(layerOpenYuansha);
        }
    });

    //监听搜索
    form.on('submit(form_search)', function (data) {
        var field = data.field;
        table.reload('yuansha_table', {
            where: field
        });
        return false;
    });
//-----------------------------------------------------------------------------------------------------------添加选择原纱信息（以上）
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
    exports('yuansha', {})
});