layui.define(['table', 'laydate', 'form', 'upload'], function (exports) {
    var $ = layui.$
        , table = layui.table
        , form = layui.form
        , upload = layui.upload
        , laydate = layui.laydate;

    var currentHeYeHao;
    var yuansha_add;
    var yuansha_edit;

    //设置表格头
    var cols = [[
        {field: 'id', title: 'id', hide: true}
        , {field: 'createTime', title: '创建时间'}
        , {templet: repNull('order.dingdanhao'), title: '订单号'}
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

    //初始化表格
    initTable("hyh_table", 'dingdanguanli/heyuehaoguanli/findAllPage', 'get', cols, table,null,function () {
        currentHeYeHao = null;
        $('#hyhxx_jsxx,#hyhxx_wsxx').html("");
        //初始化经纱表格
        initTableTemp('jsxx_table', [], jws_cols);
        //初始化纬纱表格
        initTableTemp('wsxx_table', [], jws_cols);
    });

    //监听搜索
    form.on('submit(hyh_form_search)', function (data) {
        var field = getParams('hyh_form');
        table.reload('hyh_table', {
            where: field
        });
        return false;
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
                                    ajaxSuccess(data, table, "hyh_table");
                                    layer.close(i);
                                    layer.close(editI);
                                }
                            });
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

    //监听单选框
    table.on('radio(hyh_table)', function (obj) {
        var data = obj.data;
        currentHeYeHao = data;
        //渲染经纱table
        initTable_jws('jsxx_table', 'dingdanguanli/heyuehaoguanli/getYuanSha', 'get',jws_cols, table,{type:'jingsha',id:data.id});
        // 渲染纬纱table
        initTable_jws('wsxx_table', 'dingdanguanli/heyuehaoguanli/getYuanSha', 'get',jws_cols, table,{type:'weisha',id:data.id});
        $('#hyhxx_jsxx,#hyhxx_wsxx').html(data.name);
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
    };

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
    };

    //-----------------------------------------------------------------------------------------------------------添加选择原纱信息（以下）
    //监听搜索
    form.on('submit(ys_form_search)', function (data) {
        var field = getParams('ys_form');
        table.reload('yuansha_table', {
            where: field
        });
        return false;
    });
    //添加经纬纱
    $("#jsxx_add,#wsxx_add").click(function () {
        var onClickId = $(this).attr("id");
        if (!currentHeYeHao) {
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
                        formData.yuanSha = yuansha_add;
                        if (onClickId == 'jsxx_add') {//纬纱添加
                            formData.jingsha = [{id: currentHeYeHao.id}];
                        } else if (onClickId == 'wsxx_add') {//经纱添加
                            formData.weisha = [{id: currentHeYeHao.id}];
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
                    $("#heyuehao_ys").val(currentHeYeHao.name);
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
    var xzysId;
    var layerOpenYuansha;
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
                yuansha_add = data;
                fromSetVel(form, 'form_ysxx', data);
            }
            if (xzysId == 'xzys_edit') {
                yuansha_edit = data;
                fromSetVel(form, 'form_ysxx_edit', data);
            }
            layer.close(layerOpenYuansha);
        }
    });

//-----------------------------------------------------------------------------------------------------------添加选择原纱信息（以上）
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
            yuansha_edit = data.yuanSha;
            fromSetVel(form, 'form_jws_edit', data);
            $("#heyuehao_ys_edit").val(currentHeYeHao.name);
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
                                formData.yuanSha = yuansha_edit;
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




    //ajax请求成功处理函数
    ajaxSuccess = function(data,table,tableId){
        table.reload(tableId);
        if(data.code == 0){
            layer.open({
                title:"消息提醒",
                content:data.message,
                skin:"layui-layer-molv",
                offset: 'rb',
                time:3000,
                btn:[],
                shade: 0,
                anim: -1,
                icon:6
            });
        }else {
            layer.open({
                title:"消息提醒",
                content:data.message,
                skin:"layui-layer-molv",
                btn:["查看错误信息"],
                anim: -1,
                icon:5,
                btn1:function(index){
                    layer.open({content:data.data})
                    layer.close(index);
                }
            });
        }
    }

    /**
     * 2019/03/22 bjw
     * 通过三目运算符处理NULL异常
     * @param name 数据取值参数
     * @returns {string} 取值内容
     */
    function repNull(name) {
        var arr = name.split('.');
        var tem = "<div>{{ ";
        var currentObj = 'd';
        var smbds = '(';
        for (var i = 0; i < arr.length; i++) {
            currentObj += '.' + arr[i];
            if (i == (arr.length - 1)) {
                smbds += currentObj + "== null) ? '' : " + currentObj;
            } else {
                smbds += currentObj + "== null || ";
            }
        }
        tem += smbds;
        tem += ' }}</div>';
        return tem;
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

    /**
     * 校验
     */
    form.verify({
        heyuehao: [
            /^([0-9]{5})([A-Z]{1})([0-9]{1})$/,
            '合约号格式不正确！'
        ],
        zmAndSz: [
            /^[A-Za-z0-9]+$/
            , '只能是数字和字母组成！'
        ],
        zm: [
            /^[A-Za-z]+$/
            , '只能是字母组成！'
        ],
        sz: [
            /^[0-9]+$/
            , '只能是数字组成！'
        ],
        int: [
            /^-?[1-9]+[0-9]*$/
            , '只能是整数类型！'
        ],
        num: function (value, item) {
            if (isNaN(value)) {
                return "只能输入数字类型！";
            }
        },
        length: function (value, item) { //value：表单的值、item：表单的DOM对象
            var valueSize = value ? value.length : 0;
            var maxNumber = $(item).attr('tq_length');
            if (maxNumber) {
                var arr = maxNumber.split('^');
                if (arr[0] != '' && arr[1] != '') {
                    if (valueSize < arr[0] || valueSize > arr[1]) return '不能少于' + arr[0] + '个字符和不能大于' + arr[1] + '个字符！';
                }
                if (arr.length == 1) {
                    if (valueSize != arr[0]) return '输入长度只能是' + arr[0] + '个字符！';
                }
                if (arr[0] == '' && arr[1] != '') {
                    if (valueSize > arr[1]) return "不能超过" + arr[1] + "个字符！";
                }
                if (arr[0] != '' && arr[1] == '') {
                    if (valueSize < arr[0]) return "不能少于" + arr[0] + "个字符！";
                }


            }
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

    exports('heyuehaoguanli', {})
});