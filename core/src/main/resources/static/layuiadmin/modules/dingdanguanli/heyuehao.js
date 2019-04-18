layui.define(['table', 'laydate', 'form', 'upload'], function (exports) {
    var $ = layui.$
        , table = layui.table
        , form = layui.form
        , upload = layui.upload
        , laydate = layui.laydate;

    tq_verify(form);

    //设置表格头
    var cols = [[
        {field: 'id', title: 'id', hide: true}
        , {field: 'createTime', title: '创建时间'}
        , {field: 'name', title: '合约号'}
        , {field: 'kehubianhaomiaoshu', title: '客户编号描述'}
        , {field: 'beizhu', title: '备注'}
        , {type: 'radio', fixed: 'right', title: '选择'}
        , {title: '操作', toolbar: '#hyh_caozuo', width: 150, fixed: 'right'}
    ]];

    //经纬纱信息表头
    var jws_cols = [[
        {field: 'id', title: 'id', hide: true}
        , {field: 'createTime', title: '创建时间'}
        , {templet: repNull('yuanSha.id'), hide: true}
        , {templet: repNull('yuanSha.pinming'), title: '品名'}
        , {templet: repNull('yuanSha.pihao'), title: '批号'}
        , {templet: repNull('yuanSha.gongyingshang.name'), title: '供应商'}
        , {templet: repNull('yuanSha.zhishu'), title: '支数'}
        , {templet: repNull('yuanSha.sehao'), title: '色号'}
        , {templet: repNull('yuanSha.sebie'), title: '色别'}
        , {field: 'genshu', title: '根数'}
        , {field: 'beizhu', title: '备注'}
        , {title: '操作', toolbar: '#caozuo', width: 150, fixed: 'right'}

    ]];

    //初始化合约号表格
    initTableTemp('hyh_table', heyuehaos, cols);
    //初始化经纱表格
    initTableTemp('jsxx_table', [], jws_cols);
    //初始化纬纱表格
    initTableTemp('wsxx_table', [], jws_cols);

    //监听单选框
    table.on('radio(hyh_table)', function (obj) {
        // heyuehao_temp = obj.data;
        // //重新渲染经纱表格
        // var jsData = obj.data.jingsha;
        // initTableTemp('jsxx_table', jsData, jws_cols);
        // //重新渲染纬纱表格
        // var wsData = obj.data.weisha;
        // initTableTemp('wsxx_table', wsData, jws_cols);

        var data = obj.data;
        heyuehao_temp = data;
        //渲染经纱table
        initTable_jws('jsxx_table', 'dingdanguanli/heyuehaoguanli/getYuanSha', 'get',jws_cols, table,{type:'jingsha',id:data.id});
        // 渲染纬纱table
        initTable_jws('wsxx_table', 'dingdanguanli/heyuehaoguanli/getYuanSha', 'get',jws_cols, table,{type:'weisha',id:data.id});
        $('#hyhxx_jsxx,#hyhxx_wsxx').html(data.name);
    });

    //合约号删除和修改
    table.on('tool(hyh_table)', function (obj) {
        var data = obj.data;
        if (obj.event === 'hyh_dele') {
            layer.confirm(
                "确定要删除合约号" + data.name + '吗?',
                {title: '删除提示'}, function (index) {
                    $.ajax({
                        url: layui.setter.host + 'dingdanguanli/heyuehaoguanli/deleteById',
                        type: 'POST',
                        data: {'id': data.id},
                        success: function (data) {
                            if (data.code == 0) {
                                heyuehaos.splice(jQuery.inArray(data, heyuehaos), 1);
                                //初始化合约号表格
                                initTableTemp('hyh_table', heyuehaos, cols);
                                //初始化经纱表格
                                initTableTemp('jsxx_table', [], jws_cols);
                                //初始化纬纱表格
                                initTableTemp('wsxx_table', [], jws_cols);
                            }
                            ajaxSuccess(data, table, "hyh_table");
                        }
                    });
                });
        }
        else if (obj.event === 'hyh_edit') {
            editI = layer.open({
                type: 1
                , title: '编辑合约号信息！'
                , content: $('#div_form_edit')
                , area: ['50%', '40%']
                , btn: ['修改', '取消']
                , btn1: function (index, layero) {
                    form.on('submit(form_edit_submit)', function (data) {
                        layer.confirm('确定要修改合约号信息吗?', function (i) {
                            var formData = data.field;
                            encObject(formData);
                            $.ajax({
                                url: layui.setter.host + 'dingdanguanli/heyuehaoguanli/update',
                                contentType: "application/json;charset=utf-8",
                                type: 'POST',
                                data: JSON.stringify(formData),
                                success: function (data) {
                                    if (data.code == 0) {
                                        var hyh = data.data;
                                        for (var i = 0; i < heyuehaos.length; i++) {
                                            if (hyh.id == heyuehaos[i].id) {
                                                heyuehaos[i] = hyh;
                                            }
                                        }
                                        //初始化合约号表格
                                        initTableTemp('hyh_table', heyuehaos, cols);
                                        //初始化经纱表格
                                        initTableTemp('jsxx_table', [], jws_cols);
                                        //初始化纬纱表格
                                        initTableTemp('wsxx_table', [], jws_cols);
                                        layer.close(i);
                                        layer.close(editI);
                                    }
                                    ajaxSuccess(data, table, "hyh_table");
                                }
                            });
                        });
                    });
                    $("#form_edit_submit").trigger('click');
                }
                , success: function () {
                    $('#dingdanhao_edit').val(order.dingdanhao);
                    data.order = {'id': order.id};
                    fromSetVel(form, 'form_edit', data);
                }
            })
        }
    });

    //添加合约号
    $("#wsxz,#jsxz").click(function () {
        var thisId = $(this).attr('id');
        var flag = null;
        if (thisId == 'wsxz') {
            flag = 'huanwei';
        } else if (thisId == 'jsxz') {
            flag = 'huanjing';
        }
        $.ajax({
            url: layui.setter.host + 'dingdanguanli/heyuehaoguanli/create_heyuehao',
            type: 'get',
            async:false,
            data: {'order_id': order.id, 'flag': flag},
            success: function (data) {
                if (data.code == 0) {
                    $('#name_add').val(data.data[0].heyuehao);
                    $('#kehubianhaomiaoshu_add').val(data.data[0].heyuehao);
                } else {
                    ajaxSuccess(data, table, tableId)
                }
            }
        });
        layer.open({
            type: 1
            , title: '添加合约号信息！'
            , content: $('#div_form_add')
            , area: ['50%', '50%']
            , btn: ['添加', '取消']
            , btn1: function (index, layero) {
                form.on('submit(form_add_submit)', function (data) {
                    var formData = data.field;
                    formData.order = order;
                    $.ajax({
                        url: layui.setter.host + 'dingdanguanli/heyuehaoguanli/addHeyuegao',
                        contentType: "application/json;charset=utf-8",
                        type: 'POST',
                        data: JSON.stringify(formData),
                        success: function (data) {
                            if (data.code == 0) {
                                var hyh = data.data;
                                heyuehaos.push(hyh);
                                //初始化合约号表格
                                initTableTemp('hyh_table', heyuehaos, cols);
                                //初始化经纱表格
                                initTableTemp('jsxx_table', [], jws_cols);
                                //初始化纬纱表格
                                initTableTemp('wsxx_table', [], jws_cols);
                            }
                            ajaxSuccess(data, table, "hyh_table");
                        }
                    });
                    layer.close(index);
                });
                $("#form_add_submit").trigger('click');
                //
            }
            , success: function () { //弹出层打开成功时的回调。
                $('#dingdanhao_add').val(order.dingdanhao);
            }
        })
    });


    table.on('tool(wsxx_table)', function (obj) {
        jwstableOnClick(obj, "wsxx_table");
    });
    table.on('tool(jsxx_table)', function (obj) {
        jwstableOnClick(obj, 'jsxx_table');
    });

    //经纬纱表格的监听事件
    function jwstableOnClick(obj, tableId) {
        var data = obj.data;
        if (obj.event === 'jws_dele') {
            layer.confirm(
                "确定要删除原纱信息吗?",
                {title: '删除提示'}, function (index) {
                    $.ajax({
                        url: layui.setter.host + 'dingdanguanli/heyuehaoyuansha/deleteById',
                        type: 'get',
                        data: {'id': data.id},
                        success: function (data) {
                            if (tableId == 'wsxx_table') {
                                ajaxSuccess(data, table, "wsxx_table");
                            } else if (tableId == 'jsxx_table') {
                                ajaxSuccess(data, table, "jsxx_table");
                            }
                        }
                    });
                });
        } else if (obj.event === 'jws_edit') {
            yuansha_temp_edit = data.yuanSha;
            fromSetVel(form, 'form_jws_edit', data);
            $("#heyuehao_ys_edit").val(heyuehao_temp.name);
            var jwsxxxgIndex = layer.open({
                type: 1
                , title: '编辑经纬纱信息！'
                , content: $('#div_form_jws_edit')
                , area: ['50%', '50%']
                , btn: ['修改', '取消']
                , btn1: function (index, layero) {
                    form.on('submit(form_jws_submit_edit)', function (data) {
                        layer.confirm('确定要修改经纬纱信息么?'
                            , function (i) {
                                var formData = data.field;
                                formData.yuanSha = {};
                                formData.yuanSha.id = yuansha_temp_edit.id;
                                $.ajax({
                                    url: layui.setter.host + 'dingdanguanli/heyuehaoyuansha/update',
                                    contentType: "application/json;charset=utf-8",
                                    type: 'POST',
                                    data: JSON.stringify(formData),
                                    success: function (data) {
                                        if (tableId == 'jsxx_table') {//渲染经纱
                                            ajaxSuccess(data, table, "jsxx_table");
                                        } else if (tableId == 'wsxx_table') {//渲染纬纱
                                            ajaxSuccess(data, table, "wsxx_table");
                                        }
                                    }
                                });
                                layer.close(i);
                                layer.close(jwsxxxgIndex);
                            });
                    });
                    $("#form_jws_submit_edit").trigger('click');
                }
                , success: function () {
                    fromSetVel(form, 'form_edit', data);
                    laydate.render({
                        elem: '#xiadanriqi_edit',
                        min: 0
                    });
                    laydate.render({
                        elem: '#jiaohuoriqi_edit',
                        min: 0
                    });
                }
            })
        }
    }

    //初始化表格默认部分页，data渲染
    function initTableTemp(tableId, data, cols) {
        table.render({
            elem: "#" + tableId
            , id: tableId
            , cellMinWidth: 80
            , data: data
            , cols: cols
            , done: function (res) {
                if (typeof(doneCallBack) === "function") {
                    doneCallBack(res);
                }
            }
        });
    }
    setPPGG('_add');
    setPPGG('_edit');

    function setPPGG(Suffix) {
        $('#name' + Suffix).blur(function () {
            var name = $('#name' + Suffix).val();
            $("#kehubianhaomiaoshu" + Suffix).val(name);
        });
    }

//-----------------------------------------------------------------------------------------------------------添加选择原纱信息（以下）
    //添加经纬纱
    $("#jsxx_add,#wsxx_add").click(function () {
        var onClickId = $(this).attr("id");
        if (!heyuehao_temp) {
            layer.msg('您还没有选择对应的合约号！<br>请选择对应的合约号才能添加原纱信息！', {
                time: 20000, //20s后自动关闭
                btnAlign: 'c',
                btn: ['知道了']
            });
        } else {
            layer.open({
                type: 1
                , title: '添加合约号对应原纱信息！'
                , content: $('#div_form_jws_add')
                , area: ['70%', '70%']
                , btn: ['添加', '取消']
                , btn1: function (index, layero) {
                    form.on('submit(form_jws_submit)', function (data) {
                        var formData = data.field;
                        formData.yuanSha = {};
                        formData.yuanSha.id = yuansha_temp.id;
                        if (onClickId == 'jsxx_add') {//纬纱添加
                            formData.jingsha = [{id: heyuehao_temp.id}];
                        } else if (onClickId == 'wsxx_add') {//经纱添加
                            formData.weisha = [{id: heyuehao_temp.id}];
                        }
                        $.ajax({
                            url: layui.setter.host + 'dingdanguanli/heyuehaoyuansha/add',
                            contentType: "application/json;charset=utf-8",
                            type: 'POST',
                            data: JSON.stringify(formData),
                            success: function (data) {
                                if (onClickId == 'jsxx_add') {//渲染经纱
                                    ajaxSuccess(data, table, "jsxx_table");
                                } else if (onClickId == 'wsxx_add') {//渲染纬纱
                                    ajaxSuccess(data, table, "wsxx_table");
                                }
                                layer.close(index);
                            }
                        });
                    });
                    $("#form_jws_submit").trigger('click');
                    //
                }
                , success: function () { //弹出层打开成功时的回调。
                    $("#heyuehao_ys").val(heyuehao_temp.name);
                }
            })
        }
    });
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
    $('#xzys,#xzys_edit').click(function () {
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
            if (xzysId == 'xzys') {
                yuansha_temp = data;
                fromSetVel(form, 'form_ysxx', yuansha_temp);
            }
            if (xzysId == 'xzys_edit') {
                yuansha_temp_edit = data;
                fromSetVel(form, 'form_ysxx_edit', yuansha_temp_edit);
            }
            layer.close(layerOpenYuansha);
        }
    });

//-----------------------------------------------------------------------------------------------------------添加选择原纱信息（以上）
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


    //ajax请求成功处理函数
    function ajaxSuccess(data, table, tableId) {
        table.reload(tableId);
        if (data.code == 0) {
            layer.open({
                title: "消息提醒",
                content: data.message,
                skin: "layui-layer-molv",
                offset: 'rb',
                time: 3000,
                btn: [],
                shade: 0,
                anim: -1,
                icon: 6
            });
        } else {
            layer.open({
                title: "消息提醒",
                content: data.message,
                skin: "layui-layer-molv",
                btn: ["查看错误信息"],
                anim: -1,
                icon: 5,
                btn1: function (index) {
                    layer.open({content: data.data});
                    layer.close(index);
                }
            });
        }
    }

    //监听搜索
    form.on('submit(form_search)', function (data) {
        var field = data.field;
        table.reload('yuansha_table', {
            where: field
        });
        return false;
    });

    //初始化table(不带分页)
    function initTable_jws(ele, url, method,cols, table,data,doneCallBack) {
        return table.render({
            elem: "#"+ele
            ,id: ele
            , url: layui.setter.host+url
            , method: method
            , cellMinWidth: 80
            , cols: cols
            ,where:data
            , done: function (res) {
                if (typeof(doneCallBack) === "function") {
                    doneCallBack(res);
                }
            }
        });
    }

    exports('heyuehao', {})
});