layui.define(['table', 'laydate', 'form', 'upload'], function (exports) {
    var $ = layui.$
        , table = layui.table
        , form = layui.form
        , upload = layui.upload
        , laydate = layui.laydate;

    //查询表头部初始化
    var initSele = [
        {eleId:'laiyuan_sele',dictCode:'ys_rukulaiyuan',val:'id'},
        {eleId:'gongyingsha_sele',dictCode:'ys_gongyingshang',val:'id'}
    ];
    dictInitSele(initSele,false);
    form.render();
    laydate.render({
        elem: '#kaishiriqi_sele'
    });
    laydate.render({
        elem: '#jieshuriqi_sele'
    });

    //监听搜索
    form.on('submit(form_search)', function (data) {
        var field = data.field;
        table.reload('table', {
            where: field
        });
        return false;
    });

    //设置表格头
    var cols = [[
        {field: 'id', title: 'id', hide: true}
        , {field: 'goururiqi', title: '购入日期'}
        , {title: '品名',templet: repNull('yuanSha.pinming')}
        , {title: '批号',templet: repNull('yuanSha.pihao')}
        , {title: '支数',templet: repNull('yuanSha.zhishu')}
        , {title: '色别',templet: repNull('yuanSha.sebie')}
        , {title: '色号',templet: repNull('yuanSha.sehao')}
        , {title: '供应商',templet: repNull('yuanSha.gongyingshang.name')}
        , {title: '包装形式',templet: repNull('yuanSha.baozhuangxingshi.name')}
        , {title: '包重',field: 'baozhong'}
        , {title: '包数',field: 'baoshu'}
        , {title: '总重',field: 'zongzhong'}
        , {title: '来源',templet: repNull('laiyuan.name')}
        , {field: 'beizhu', title: '备注'}
        , {title: '操作', toolbar: '#caozuo', fixed: 'right',width:180}
    ]];

    //初始化表格
    initTable("table", 'yuanshaguanli/yuansharukudengji/query_page', 'get', cols, table);

    //添加
    $("#add").click(function () {
        var initsele = [
            {eleId:'gongyingshang_add',dictCode:'ys_gongyingshang',val:'id'},
            {eleId:'baozhuangxingshi_add',dictCode:'ys_baozhuangxingshi',val:'id'},
            {eleId:'laiyuan_add',dictCode:'ys_rukulaiyuan',val:'id'},
        ];
        dictInitSele(initsele,false);
        create_pihao();
        form.render();
        layer.open({
            type: 1
            , title: '原纱购入登记！'
            , content: $('#div_form_add')
            , area: ['70%', '60%']
            , btn: ['添加', '取消']
            , btn1: function (index, layero) {
                form.on('submit(form_add_submit)', function (data) {
                    var formData = data.field;
                    encObject(formData);
                    $.ajax({
                        url: layui.setter.host + 'yuanshaguanli/yuansharukudengji/add',
                        contentType: "application/json;charset=utf-8",
                        type: 'POST',
                        data: JSON.stringify(formData),
                        success: function (data) {
                            ajaxSuccess(data, table);
                            layer.close(index);
                        }
                    });
                });
                $("#form_add_submit").trigger('click');
            }
            , success: function () { //弹出层打开成功时的回调。
                laydate.render({
                    elem: '#goururiqi_add',
                    value: new Date()
                });
            }
        });
    });

    //监听操作列
    table.on('tool(table)', function (obj) {
        var data = obj.data;
        if (obj.event === 'del') {
            layer.confirm(
                "确定要删除入库登记信息吗？",
                {title: '删除提示'}, function (index) {
                    $.ajax({
                        url: layui.setter.host + 'yuanshaguanli/yuansharukudengji/delete',
                        type: 'get',
                        data: {'id': data.id},
                        success: function (data) {
                            ajaxSuccess(data, table);
                        }
                    });
                });
        } else if (obj.event === 'edit') {
            var initsele = [
                {eleId:'gongyingshang_edit',dictCode:'ys_gongyingshang',val:'id'},
                {eleId:'baozhuangxingshi_edit',dictCode:'ys_baozhuangxingshi',val:'id'},
                {eleId:'laiyuan_edit',dictCode:'ys_rukulaiyuan',val:'id'},
            ];
            dictInitSele(initsele,false);
            form.render();
            editI = layer.open({
                type: 1
                , title: '编辑原纱入库登记信息！'
                , content: $('#div_form_edit')
                , area: ['70%', '60%']
                , btn: ['修改', '取消']
                , btn1: function (editIndex, layero) {
                    form.on('submit(form_edit_submit)', function (data) {
                        layer.confirm('确定要修改订单信息么?'
                            , function (i) {
                                var formData = data.field;
                                encObject(formData);
                                $.ajax({
                                    url: layui.setter.host + 'yuanshaguanli/yuansharukudengji/update',
                                    contentType: "application/json;charset=utf-8",
                                    type: 'POST',
                                    data: JSON.stringify(formData),
                                    success: function (data) {
                                        ajaxSuccess(data, table);
                                    }
                                });
                                layer.close(i);
                                layer.close(editI);
                            });
                    });
                    $("#form_edit_submit").trigger('click');
                }
                , success: function () {
                    fromSetVel(form, 'form_edit', data);
                    laydate.render({
                        elem: '#goururiqi_edit'
                    });
                }
            })
        }
    });


    //自动生成批号
    function create_pihao() {
        $.ajax({
            url: layui.setter.host + 'yuanshaguanli/yuansharukudengji/create_pihao',
            type: 'get',
            async: false,
            success: function (data) {
                if (data.code == 0) {
                    $('#pihao_add').val(data.data[0].pihao);
                } else {
                    layer.msg("批号自动生成失败！");
                }
            }
        });
    }

    setJszz("_add");
    setJszz("_edit");
    //自动根据包数*包重自动计算出总重量
    function setJszz(Suffix) {
        $('#baoshu' + Suffix + ',#baozhong' + Suffix).blur(function () {
            var bs = $('#baoshu' + Suffix).val();
            var bz = $('#baozhong' + Suffix).val();
            var zzl = new Number(bs*bz).toFixed(2);
            $('#zongzhong' + Suffix).val(zzl);
        });
    }


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

    exports('yuansharukudengji', {})
});