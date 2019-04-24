layui.define(['table', 'laydate', 'form', 'upload'], function (exports) {
    var $ = layui.$
        , table = layui.table
        , form = layui.form
        , upload = layui.upload
        , laydate = layui.laydate;

    //查询表头部初始化
    var initSele = [
        {eleId:'chukuleixing_sele',dictCode:'cp_chukuleixing',val:'id'},
        {eleId:'status_sele',dictCode:'cp_chukushenqingzhuangtai',val:'id'}
    ];
    InitSelect('heyuehao_sele', null, 'dingdanguanli/heyuehaoguanli/findAll', 'get', {}, 'name', 'id');
    InitSelect('yingxiaoyuan_sele', null, 'dingdanguanli/dingdanguanli/getUser', 'get', {}, 'ghxm', 'id');
    dictInitSele(initSele,false);
    form.render();
    laydate.render({
        elem: '#yaoqiulingyongshijian_add'
    });
    laydate.render({
        elem: '#yaoqiulingyongshijian_edit'
    });
    // var date = new Date();
    // laydate.render({
    //     elem: '#jieshuriqi_sele',
    //     value: date
    // });
    // laydate.render({
    //     elem: '#kaishiriqi_sele',
    //     value: (date.getFullYear()-1)+'-'+(date.getMonth()+1)+'-'+date.getDate()
    // });
    //
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
        , {title: '申请时间',field: 'createTime', sort: true}
        , {title: '合约号',templet: repNull('chengpinchuku.heyuehao.name'),field: 'chengpinchuku.heyuehao.name', sort: true}
        , {title: '购货单位',templet: repNull('chengpinchuku.gouhuodanwei')}
        , {title: '收货单位',templet: repNull('chengpinchuku.shouhuodanwei')}
        , {title: '联系人',templet: repNull('chengpinchuku.lianxiren')}
        , {title: '联系电话',templet: repNull('chengpinchuku.lianxidianhua')}
        , {title: '收货地址',templet: repNull('chengpinchuku.shouhuodizhi')}
        , {title: '出库类型',templet: repNull('chengpinchuku.chukuleixing.name')}
        , {title: '营销员',templet: repNull('chengpinchuku.yingxiaoyuan.xingming')}
        , {title: '申请状态',templet: repNull('status.name')}
        , {title: '申请长度', field: 'changdu',sort: true}
        , {title: '申请备注',field: 'beizhu'}
        , {title: '要求领用时间',templet: repNull('chengpinchuku.yaoqiulingyongshijian'),field: 'chengpinchuku.yaoqiulingyongshijian', sort: true}
        , {title: '实际长度',templet: repNull('chengpinchuku.changdu')}
        , {title: '确认备注',templet: repNull('chengpinchuku.beizhu')}
        , {title: '确认时间',templet: repNull('chengpinchuku.cangkuquerenshijian')}
        , {title: '仓库确认人',templet: repNull('chengpinchuku.cangkuquerenren.xingming')}
        , {title: '操作', toolbar: '#caozuo', fixed: 'right',width:180}
    ]];

    //初始化表格
    initTable("table", 'chengpinguanli/chengpinchukushenqing/query_page', 'get', cols, table,"from");

    //添加
    $("#add").click(function () {
        var initSele = [
            {eleId:'chukuleixing_add',dictCode:'cp_chukuleixing',val:'id',CheckVal:"1",isAll:false}
        ];
        InitSelect('yingxiaoyuan_add', null, 'dingdanguanli/dingdanguanli/getUser', 'get', {}, 'ghxm', 'id');
        dictInitSele(initSele,false);
        form.render();
        layer.open({
            type: 1
            , title: '成品出库申请'
            , content: $('#div_form_add')
            , area: ['70%', '70%']
            , btn: ['添加', '取消']
            , btn1: function (index, layero) {

                if(null == chengpinCurrent_add){
                    layer.msg('合约号不能为空！', {
                        time: 20000, //20s后自动关闭
                        btnAlign: 'c',
                        btn: ['知道了']
                    });
                    return false;
                }
                if(chengpinCurrent_add.changdu < $('#changdu_add').val()){
                    layer.msg('合约号'+chengpinCurrent_add.heyuehao.name+'成品库存不足！', {
                        time: 20000, //20s后自动关闭
                        btnAlign: 'c',
                        btn: ['知道了']
                    });
                    return false;
                }
                form.on('submit(form_add_submit)', function (data) {
                    var formData = data.field;
                    formData.chengpinchuku = {};
                    formData.chengpinchuku.heyuehao = {};
                    formData.chengpinchuku.heyuehao.id = chengpinCurrent_add.heyuehao.id;
                    encObject(formData);
                    $.ajax({
                        url: layui.setter.host + 'chengpinguanli/chengpinchukushenqing/add',
                        contentType: "application/json;charset=utf-8",
                        type: 'POST',
                        data: JSON.stringify(formData),
                        success: function (data) {
                            ajaxSuccess(data, table);
                            layer.close(index);
                            fromClear("form_add");
                            chengpinCurrent_add==null;
                        }
                    });
                });
                $("#form_add_submit").trigger('click');
            }
            , success: function () { //弹出层打开成功时的回调。
                // laydate.render({
                //     elem: '#goururiqi_add',
                //     value: new Date()
                // });
            }
        });
    });

    //监听操作列
    table.on('tool(table)', function (obj) {
        var data = obj.data;
        if (obj.event === 'del') {
            layer.confirm(
                "确定要删除申请信息吗？",
                {title: '删除提示'}, function (index) {
                    $.ajax({
                        url: layui.setter.host + 'chengpinguanli/chengpinchukushenqing/delete',
                        type: 'get',
                        data: {'id': data.id},
                        success: function (data) {
                            ajaxSuccess(data, table);
                        }
                    });
                });
        } else if (obj.event === 'edit') {
            var initSele = [
                {eleId:'chukuleixing_edit',dictCode:'cp_chukuleixing',val:'id',CheckVal:"1",isAll:false}
            ];
            InitSelect('yingxiaoyuan_edit', null, 'dingdanguanli/dingdanguanli/getUser', 'get', {}, 'ghxm', 'id');
            dictInitSele(initSele,false);
            form.render();
            heyuehao = data.chengpinchuku.heyuehao;
            $.ajax({
                url: layui.setter.host + 'chengpinguanli/chengpinCurrent/getShengyuchangdu',
                contentType: "application/json;charset=utf-8",
                async: false,
                type: 'POST',
                data: JSON.stringify(heyuehao),
                success: function (data1) {
                    $("#shengyuchangdu_edit").val(data1.data.changdu);
                    chengpinCurrent_edit = data1.data;
                }
            });
            editI = layer.open({
                type: 1
                , title: '成品出库申请信息编辑'
                , content: $('#div_form_edit')
                , area: ['70%', '60%']
                , btn: ['修改', '取消']
                , btn1: function (editIndex, layero) {
                    if(null == chengpinCurrent_edit){
                        layer.msg('合约号不能为空！', {
                            time: 20000, //20s后自动关闭
                            btnAlign: 'c',
                            btn: ['知道了']
                        });
                        return false;
                    }
                    if(chengpinCurrent_edit.changdu < $('#changdu_edit').val()){
                        layer.msg('合约号'+chengpinCurrent_edit.heyuehao.name+'成品库存不足！', {
                            time: 20000, //20s后自动关闭
                            btnAlign: 'c',
                            btn: ['知道了']
                        });
                        return false;
                    }
                    form.on('submit(form_edit_submit)', function (data) {
                        layer.confirm('确定要修改申请信息么?'
                            , function (i) {
                                var formData = data.field;
                                formData.chengpinchuku = {};
                                formData.chengpinchuku.heyuehao = {};
                                formData.chengpinchuku.heyuehao.id = chengpinCurrent_edit.heyuehao.id;
                                encObject(formData);
                                $.ajax({
                                    url: layui.setter.host + 'chengpinguanli/chengpinchukushenqing/update',
                                    contentType: "application/json;charset=utf-8",
                                    type: 'POST',
                                    data: JSON.stringify(formData),
                                    success: function (data) {
                                        ajaxSuccess(data, table);
                                        chengpinCurrent_edit = null;
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
                }
            })
        }
    });

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

    /**
     * 2019/03/24 bjw
     * 处理根据对象默认select选择列表。
     * @param formId  表单div的id
     * @param ObjVal  值对象
     */
    fromSetVel = function (from, formId, data) {
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


    exports('chengpinchukushenqing', {})
});