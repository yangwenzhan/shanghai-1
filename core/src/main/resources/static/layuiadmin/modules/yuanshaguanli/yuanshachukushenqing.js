layui.define(['table', 'laydate', 'form', 'upload'], function (exports) {
    var $ = layui.$
        , table = layui.table
        , form = layui.form
        , upload = layui.upload
        , laydate = layui.laydate;

    //查询表头部初始化
    var initSele = [
        {eleId:'chukuleixing_sele',dictCode:'ys_chukuleixing',val:'id'},
        {eleId:'status_sele',dictCode:'ys_chukushenqingzhuangtai',val:'id'},
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
        , {field: 'createTime', title: '申请日期', sort: true}
        , {title: '品名',templet: repNull('yuanSha.pinming'), sort: true,field:'yuanSha.pinming'}
        , {title: '批号',templet: repNull('yuanSha.pihao'), sort: true,field:'yuanSha.pihao'}
        , {title: '支数',templet: repNull('yuanSha.zhishu'), sort: true,field:'yuanSha.zhishu'}
        , {title: '色别',templet: repNull('yuanSha.sebie'), sort: true,field:'yuanSha.sebie'}
        , {title: '色号',templet: repNull('yuanSha.sehao'), sort: true,field:'yuanSha.sehao'}
        , {title: '供应商',templet: repNull('yuanSha.gongyingshang.name')}
        , {title: '包装形式',templet: repNull('yuanSha.baozhuangxingshi.name')}
        , {title: '出库类型',templet: repNull('chukuleixing.name')}
        , {title: '申请状态',templet: repNull('status.name'), sort: true,field:'status.name'}
        , {title: '申请包重',field: 'baozhong'}
        , {title: '申请包数',field: 'baoshu'}
        , {title: '申请总重',field: 'zongzhong'}
        , {title: '申请备注',field: 'beizhu'}
        , {title: '实际包重',templet: repNull('yuanShaChuKu.baozhong')}
        , {title: '实际包数',templet: repNull('yuanShaChuKu.baoshu')}
        , {title: '实际总重',templet: repNull('yuanShaChuKu.zongzhong')}
        , {title: '登记备注',templet: repNull('yuanShaChuKu.beizhu')}
        , {title: '领用人',templet: repNull('yuanShaChuKu.lingyongren.xingming')}
        , {title: '领用时间',templet: repNull('yuanShaChuKu.lingyongshijian'), sort: true,field:'yuanShaChuKu.lingyongshijian'}
        , {title: '操作', toolbar: '#caozuo', fixed: 'right',width:180}
    ]];

    //初始化表格
    initTable("table", 'yuanshaguanli/yuanshachukushenqing/query_page', 'get', cols, table,"from");

    //添加
    $("#add").click(function () {
        var initsele = [
            {eleId:'chukuleixing_add',dictCode:'ys_chukuleixing',val:'id'}
        ];
        InitSelect('heyuehao_add', null, 'dingdanguanli/heyuehaoguanli/findAll', 'get', {}, 'name', 'id');
        dictInitSele(initsele,false);
        form.render();
        layer.open({
            type: 1
            , title: '原纱出库申请'
            , content: $('#div_form_add')
            , area: ['70%', '60%']
            , btn: ['添加', '取消']
            , btn1: function (index, layero) {
                if(null == yuansha_add){
                    layer.msg('原纱信息不能为空！', {
                        time: 20000, //20s后自动关闭
                        btnAlign: 'c',
                        btn: ['知道了']
                    });
                    return false;
                }

                if(yuansha_add.kucunliang < $('#zongzhong_add').val()){
                    layer.msg('原纱库存不足！', {
                        time: 20000, //20s后自动关闭
                        btnAlign: 'c',
                        btn: ['知道了']
                    });
                    return false;
                }
                form.on('submit(form_add_submit)', function (data) {
                    var formData = data.field;
                    formData.yuanSha = yuansha_add;
                    encObject(formData);
                    $.ajax({
                        url: layui.setter.host + 'yuanshaguanli/yuanshachukushenqing/add',
                        contentType: "application/json;charset=utf-8",
                        type: 'POST',
                        data: JSON.stringify(formData),
                        success: function (data) {
                            ajaxSuccess(data, table);
                            layer.close(index);
                            yuansha_add = null;
                            fromClear("form_add_submit");
                        }
                    });
                });
                $("#form_add_submit").trigger('click');
            }
            , success: function () { //弹出层打开成功时的回调。

            }
        });
    });

    // //监听操作列
    // table.on('tool(table)', function (obj) {
    //     var data = obj.data;
    //     if (obj.event === 'del') {
    //         layer.confirm(
    //             "确定要删除入库申请信息吗？",
    //             {title: '删除提示'}, function (index) {
    //                 $.ajax({
    //                     url: layui.setter.host + 'yuanshaguanli/yuansharukushenqing/delete',
    //                     type: 'get',
    //                     data: {'id': data.id},
    //                     success: function (data) {
    //                         ajaxSuccess(data, table);
    //                     }
    //                 });
    //             });
    //     } else if (obj.event === 'edit') {
    //         var initsele = [
    //             {eleId:'gongyingshang_edit',dictCode:'ys_gongyingshang',val:'id'},
    //             {eleId:'baozhuangxingshi_edit',dictCode:'ys_baozhuangxingshi',val:'id'},
    //             {eleId:'laiyuan_edit',dictCode:'ys_rukulaiyuan',val:'id'},
    //         ];
    //         dictInitSele(initsele,false);
    //         form.render();
    //         editI = layer.open({
    //             type: 1
    //             , title: '编辑原纱入库登记信息！'
    //             , content: $('#div_form_edit')
    //             , area: ['70%', '60%']
    //             , btn: ['修改', '取消']
    //             , btn1: function (editIndex, layero) {
    //                 form.on('submit(form_edit_submit)', function (data) {
    //                     layer.confirm('确定要修改订单信息么?'
    //                         , function (i) {
    //                             var formData = data.field;
    //                             encObject(formData);
    //                             $.ajax({
    //                                 url: layui.setter.host + 'yuanshaguanli/yuansharukushenqing/update',
    //                                 contentType: "application/json;charset=utf-8",
    //                                 type: 'POST',
    //                                 data: JSON.stringify(formData),
    //                                 success: function (data) {
    //                                     ajaxSuccess(data, table);
    //                                 }
    //                             });
    //                             layer.close(i);
    //                             layer.close(editI);
    //                         });
    //                 });
    //                 $("#form_edit_submit").trigger('click');
    //             }
    //             , success: function () {
    //                 fromSetVel(form, 'form_edit', data);
    //                 laydate.render({
    //                     elem: '#goururiqi_edit'
    //                 });
    //             }
    //         })
    //     }
    // });
    //
    //

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

    function fromClear(formId) {
        var arrObj = $('#' + formId).find(":input");
        for (var i = 0; i < arrObj.length; i++) {
            $(arrObj[i]).val("");
        }
    }

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


    exports('yuanshachukushenqing', {})
});