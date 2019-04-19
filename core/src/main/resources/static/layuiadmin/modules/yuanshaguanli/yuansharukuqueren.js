layui.define(['table', 'laydate', 'form', 'upload'], function (exports) {
    var $ = layui.$
        , table = layui.table
        , form = layui.form
        , upload = layui.upload
        , laydate = layui.laydate;

    //查询表头部初始化
    var initSele = [
        {eleId: 'laiyuan_sele', dictCode: 'ys_rukulaiyuan', val: 'id'},
        {eleId: 'gongyingsha_sele', dictCode: 'ys_gongyingshang', val: 'id'}
    ];
    dictInitSele(initSele, false);
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
        , {field: 'yuanSha.pinming', title: '品名', templet: repNull('yuanSha.pinming'), sort: true}
        , {field: 'yuanSha.pihao', title: '批号', templet: repNull('yuanSha.pihao'), sort: true}
        , {field: 'yuanSha.zhishu', title: '支数', templet: repNull('yuanSha.zhishu'), sort: true}
        , {title: '色别', templet: repNull('yuanSha.sebie')}
        , {title: '色号', templet: repNull('yuanSha.sehao')}
        , {title: '供应商', templet: repNull('yuanSha.gongyingshang.name')}
        , {title: '包装形式', templet: repNull('yuanSha.baozhuangxingshi.name')}
        , {title: '来源', templet: repNull('laiyuan.name')}
        // , {title: '申请状态',templet: repNull('status.name')}
        , {title: '申请包重', field: 'baozhong'}
        , {title: '申请包数', field: 'baoshu'}
        , {title: '申请总重', field: 'zongzhong'}
        , {title: '申请备注', field: 'beizhu'}
        , {title: '登记包重', templet: repNull('yuanShaRuKu.baozhong')}
        , {title: '登记包数', templet: repNull('yuanShaRuKu.baoshu')}
        , {title: '登记总重', templet: repNull('yuanShaRuKu.zongzhong')}
        , {title: '登记备注', templet: repNull('yuanShaRuKu.beizhu')}
        , {title: '操作', toolbar: '#caozuo', fixed: 'right', width: 180}
    ]];

    //初始化表格
    initTable("table", 'yuanshaguanli/yuansharukuqueren/query_page', 'get', cols, table, "from");


    //监听操作列
    table.on('tool(table)', function (obj) {
        var data = obj.data;
        if (obj.event === 'edit') {   //点击确认
            InitSelect('lingyongren_edit', null, 'dingdanguanli/dingdanguanli/getUser', 'get', {}, 'ghxm', 'id');
            form.render();
            editI = layer.open({
                type: 1
                , title: '原纱入库申请确认！'
                , content: $('#div_form_edit')
                , area: ['70%', '70%']
                , btn: ['确认', '取消']
                , btn1: function (editIndex, layero) {
                    form.on('submit(form_edit_submit)', function (data) {
                        layer.confirm('确定要修改订单信息么?'
                            , function (i) {
                                var formData = data.field;
                                encObject(formData);
                                $.ajax({
                                    url: layui.setter.host + 'yuanshaguanli/yuansharukuqueren/dengji',
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
                    $("#baoshu_edit").val(data.baoshu);
                    $("#baozhong_edit").val(data.baozhong);
                    $("#zongzhong_edit").val(data.zongzhong);
                    fromSetVel(form, 'form_edit', data);
                    laydate.render({
                        elem: '#goururiqi_edit',
                        value: new Date()
                    });
                }
            })
        }
    });


    setJszz("_edit");

    //自动根据包数*包重自动计算出总重量
    function setJszz(Suffix) {
        $('#baoshu' + Suffix + ',#baozhong' + Suffix).blur(function () {
            var bs = $('#baoshu' + Suffix).val();
            var bz = $('#baozhong' + Suffix).val();
            var zzl = Number(bs * bz).toFixed(2);
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
            val = !val ? "''" : "'" + val + "'";
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

    exports('yuansharukuqueren', {})
});