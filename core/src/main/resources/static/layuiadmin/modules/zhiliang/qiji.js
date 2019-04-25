layui.define(['table', 'form', 'laydate'], function(exports){
    var $ = layui.$
        ,table = layui.table
        ,form = layui.form
        ,laydate = layui.laydate;

    //筛选条件初始化
    var initValue = formatDate(addDate(new Date(),0));
    laydate.render({
        elem: '#kaishiriqi',
        type: 'date',
        value: initValue
    });
    laydate.render({
        elem: '#jieshuriqi',
        type: 'date',
        value: initValue
    });
    $('#kaishiriqi').val(initValue);
    $('#jieshuriqi').val(initValue);

    function getHeYueHao(downID, selectedId, isAll, riqi, banci, jitaihao) {
        $.ajax({
            url: layui.setter.host + 'zhiliang/qijizhiliang/findHeYueHao',
            type: 'get',
            async: false,
            data: {
                riqi:riqi,
                banci_id:banci,
                jitaihao:jitaihao
            },
            success: function (data) {
                $('#' + downID).html("");
                if(data.data.length>0){
                    var str = "";
                    if(isAll){
                        str += "<option name='' value=''>全部</option>";
                    }else{
                        for(var i=0;i<data.data.length;i++){
                            if(data.data[i].id==selectedId){
                                str += "<option name='"+data.data[i].pibuguige+"' value='"+data.data[i].id+"' selected='selected'>"+data.data[i].name+"</option>";
                            }else{
                                str += "<option name='"+data.data[i].pibuguige+"' value='"+data.data[i].id+"' selected='selected'>"+data.data[i].name+"</option>";
                            }
                        }
                    }
                }
                $('#' + downID).html(str);
                form.render();
            }
        });
    }
    function getJiTaiHao(downID, selectedId, isAll, gongxu, jixing){
        var reg = RegExp(/,/);
        var downId_arr=[];
        if(downID==null){
            downId_arr=null;
        }else if(reg.test(downID)){
            downId_arr = downID.split(',');
        }else{
            downId_arr.push(downID);
        }
        $.ajax({
            url: layui.setter.host + 'common/findJiTaiHao',
            type: 'get',
            async: false,
            data: {
                "gongxu": gongxu,
                "jixing": jixing
            },
            success: function (data) {
                for(var i = 0;i<downId_arr.length;i++){
                    initDownList(data, downId_arr[i], selectedId, 'jitaihao', 'id', isAll);
                }
                layui.form.render();
            }
        });
    }
    function getBanCi(downID, selectedId, isAll){
        var reg = RegExp(/,/);
        var downId_arr=[];
        if(downID==null){
            downId_arr=null;
        }else if(reg.test(downID)){
            downId_arr = downID.split(',');
        }else{
            downId_arr.push(downID);
        }
        $.ajax({
            url: layui.setter.host + 'common/findAllDictVal',
            type: 'get',
            data:{
                code:"banci"
            },
            async: false,
            success: function (data) {
                var dict_data = {code:data.code,data:data.data.dicts,message:data.message};
                for(var i = 0;i<downId_arr.length;i++){
                    initDownList(dict_data, downId_arr[i], selectedId, 'name', 'id', isAll);
                }
                form.render();
            }
        });
    }
    function getAllHeYueHao(downID, selectedId, isAll) {
        var reg = RegExp(/,/);
        var downId_arr=[];
        if(downID==null){
            downId_arr=null;
        }else if(reg.test(downID)){
            downId_arr = downID.split(',');
        }else{
            downId_arr.push(downID);
        }
        $.ajax({
            url: layui.setter.host + 'common/findHeYueHao',
            type: 'get',
            async: false,
            success: function (data) {
                for(var i = 0;i<downId_arr.length;i++){
                    initDownList(data, downId_arr[i], selectedId, 'name', 'id', isAll);
                }
                form.render();
            }
        });
    }

    getAllHeYueHao("heyuehao", null, true);
    getJiTaiHao("jitaihao", null, true, '织布', null);

    var cols =  [
        {field: 'id', title: 'id',hide:true}
        ,{field: 'riqi',sort:true, title: '日期',fixed:true, width:120}
        ,{title: '班次',sort:true, templet: repNull('banci.name'),fixed:true, width:80}
        ,{title: '机台号',sort:true, templet: repNull('jitaihao.jitaihao'),fixed:true,width:100}
        ,{title: '合约号',sort:true, templet: repNull('heyuehao.name'), width:100}
        ,{title: '坯布规格',sort:true, templet: repNull('heyuehao.order.pibuguige'),width:170}
        ,{field: 'kouhao',sort:true, title: '筘号',width:100}
        ,{field: 'zongjing',sort:true, title: '总经',width:100}
        ,{field: 'jingmi',sort:true, title: '经密',width:100}
        ,{field: 'weimi',sort:true, title: '纬密',width:100}
        ,{field: 'koufu',sort:true, title: '筘幅/cm',width:100}
        ,{field: 'bufu',sort:true, title: '布幅/cm',width:100}
        ,{field: 'jishangkongzhiweimi',sort:true, title: '机上控制纬密',width:120}
        ,{field: 'beizhu',sort:true, title: '备注',width:100}
        ,{align: 'center',title: '操作',toolbar: '#barDemo',width:170, fixed: 'right'}
    ];
    cols = formatColumns(cols);

    initTable("table", 'zhiliang/qijizhiliang/findAll', 'get',[cols], table, "form");

    form.on('submit(form_search)',function(data){
        var field = data.field;
        table.reload('table', {
            where: field
        });
        return false;
    });

    form.on('select(upd_jitaihao)', function() {
        getHeYueHao("upd_heyuehao", null, false,$('#upd_riqi').val(),$('#upd_banci').val(),$('#upd_jitaihao').val());
    });
    form.on('select(upd_banci)', function() {
        getHeYueHao("upd_heyuehao", null, false,$('#upd_riqi').val(),$('#upd_banci').val(),$('#upd_jitaihao').val());
    });
    form.on('select(upd_heyuehao)', function() {
        $('#upd_pibuguige').html("");
        $('#upd_pibuguige').val($('#upd_heyuehao option:selected').attr("name"));
    });


    form.on('select(add_jitaihao)', function() {
        getHeYueHao("add_heyuehao", null, false,$('#add_riqi').val(),$('#add_banci').val(),$('#add_jitaihao').val());
    });
    form.on('select(add_banci)', function() {
        getHeYueHao("add_heyuehao", null, false,$('#add_riqi').val(),$('#add_banci').val(),$('#add_jitaihao').val());
    });
    form.on('select(add_heyuehao)', function() {
        $('#pibuguige').html("");
        $('#pibuguige').val($('#add_heyuehao option:selected').attr("name")=='null'?'':$('#add_heyuehao option:selected').attr("name"));
    });


    //新增
    $('#add_btn').on('click',function(){

        laydate.render({
            elem: '#add_riqi',
            type: 'date',
            value: new Date(),
            done:function (value, date, endDate) {
                getHeYueHao("add_heyuehao", null, false,value,$('#add_banci').val(),$('#add_jitaihao').val());
            }
        });
        getBanCi("add_banci",null,false);
        getJiTaiHao("add_jitaihao", null, false, '织布', null);
        getHeYueHao("add_heyuehao", null, false,$('#add_riqi').val(),$('#add_banci').val(),$('#add_jitaihao').val());
        $('#pibuguige').html("");
        $('#pibuguige').val($('#add_heyuehao option:selected').attr("name")=='null'?'':$('#add_heyuehao option:selected').attr("name"));

        $('#jingmi').val("");
        $('#weimi').val("");
        $('#bufu').val("");
        $('#kouhao').val("");
        $('#zongjing').val("");
        $('#koufu').val("");
        $('#jishangkongzhiweimi').val("");
        $('#beizhu').val("");

        layer.open({
            type: 1,
            title: ['新增起机质量', 'font-size:12px;'],
            content: $("#add_form_div"),
            shadeClose: true, //点击遮罩关闭层
            shade: 0.8,
            offset:'auto',
            area: ['80%', '90%'],
            btn: ['确定', '取消'],
            btnAlign: 'c',
            yes: function(index, layero) {
                if($('#add_riqi').val()==""){verifyWindow("日期不能为空!");return false;}
                // if($('#add_banci').val()==""){verifyWindow("班次不能为空!");return false;}
                if($('#add_heyuehao').val()==""){verifyWindow("合约号不能为空!");return false;}
                if($('#add_jitaihao').val()==""){verifyWindow("机台号不能为空!");return false;}

                layer.confirm('确定新增?'
                    ,function(i){
                        form.on('submit(form_add_submit)', function (data) {
                            var formData = data.field;
                            encObject(formData);
                            $.ajax({
                                url:layui.setter.host+'zhiliang/qijizhiliang/save',
                                type:'post',
                                contentType:"application/json;charset=utf-8",
                                data:JSON.stringify(formData),
                                success:function(data){
                                    ajaxSuccess(data,table);
                                    layer.close(i);layer.close(index);
                                }
                            });
                        });
                        $("#form_add_submit").trigger('click');
                    });
            }
        });

    });

    //修改
    table.on('tool(table)',function(obj) {
        var data = obj.data;
        var id= data.id;
        laydate.render({
            elem: '#upd_riqi',
            type: 'date',
            value: data.riqi,
            done: function(value, date, endDate) {
                getHeYueHao("upd_heyuehao", null, false,value,$('#upd_banci').val(),$('#upd_jitaihao').val());
            }
        });
        getBanCi("upd_banci",data.banci.id,false);
        getJiTaiHao("upd_jitaihao", data.jitaihao.id, false, '织布', null);
        getHeYueHao("upd_heyuehao", data.heyuehao.id, false,$('#upd_riqi').val(),$('#upd_banci').val(),$('#upd_jitaihao').val());
        $('#upd_pibuguige').html("");
        $('#upd_pibuguige').val(data.heyuehao.order.pibuguige);

        if(obj.event === 'edit'){
            layer.open({
                type: 1
                ,title: '编辑日期: '+data.riqi+' 机台号: '+data.jitaihao.jitaihao+' 起机质量信息'
                ,content: $('#edit_form_div')
                ,offset:'auto'
                ,area: ['80%', '80%']
                ,btn: ['修改', '取消']
                ,btnAlign: 'c'
                ,btn1: function(index, layero) {
                    if($('#upd_riqi').val()==""){verifyWindow("日期不能为空!");return false;}
                    // if($('#upd_banci').val()==""){verifyWindow("班次不能为空!");return false;}
                    if($('#upd_heyuehao').val()==""){verifyWindow("合约号不能为空!");return false;}
                    if($('#upd_jitaihao').val()==""){verifyWindow("机台号不能为空!");return false;}

                    layer.confirm('确定修改日期: '+data.riqi+' 机台号: '+data.jitaihao.jitaihao+' 起机质量信息?'
                        ,function(i){
                            form.on('submit(form_edit_submit)', function (data) {
                                var formData = data.field;
                                formData.id = id;
                                encObject(formData);
                                $.ajax({
                                    url:layui.setter.host+'zhiliang/qijizhiliang/save',
                                    type:'post',
                                    contentType:"application/json;charset=utf-8",
                                    data:JSON.stringify(formData),
                                    success:function(data){
                                        ajaxSuccess(data,table);
                                        layer.close(i);layer.close(index);
                                    }
                                });
                            });
                            $("#form_edit_submit").trigger('click');
                        });
                },
                success:function(){
                    //渲染表格数据
                    fromSetVel(form, 'form_edit', data);
                }
            });
        }else if(obj.event === 'del'){
            var obj = {id:id};
            layer.confirm('确定要删除日期: '+data.riqi+' 机台号: '+data.jitaihao.jitaihao+' 成品质量信息吗?'
                ,function(i){
                    $.ajax({
                        url:layui.setter.host+'zhiliang/qijizhiliang/delete',
                        type:'post',
                        contentType:"application/json;charset=utf-8",
                        data:JSON.stringify(obj),
                        success:function(data){
                            ajaxSuccess(data,table);
                            layer.close(i);
                        }
                    });
                });
        }
    });

    //合约号详情
    form.on('submit(heyuehao_filter)',function(data){
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
        ]];
        //渲染经纱table
        initTable_jws('hyh_js_table', 'dingdanguanli/heyuehaoguanli/getYuanSha', 'get',jws_cols, table,{type:'jingsha',id:$(this).context.id});
        // 渲染纬纱table
        initTable_jws('hyh_ws_table', 'dingdanguanli/heyuehaoguanli/getYuanSha', 'get',jws_cols, table,{type:'weisha',id:$(this).context.id});
        layer.open({
            type: 1,
            title: ['合约号 '+$(this).context.innerHTML+' 原纱信息'],
            content: $("#hyh_ys_tck"),
            shade: 0.8,
            area: ['90%', '90%'],
            offset: "10px"
        });
        return false;
    });


    //初始化table(不带分页)
    function initTable_jws(ele, url, method,cols, table,data,doneCallBack) {
        return table.render({
            elem: "#" + ele
            , id: ele
            , url: layui.setter.host + url
            , method: method
            , cellMinWidth: 80
            , cols: cols
            , where: data
            , done: function (res) {
                if (typeof(doneCallBack) === "function") {
                    doneCallBack(res);
                }
            }
        });
    }


    // 丰富列配置功能
    function formatColumns(cols) {
        var reg = RegExp(/heyuehao.name/);
        for(var i = 0; i < cols.length; i++) {
            var col = cols[i];
            if(reg.test(col.templet)) {
                col.templet = "<div><div id='{{d.heyuehao.id}}' lay-submit lay-filter='heyuehao_filter' title=\"点击查看合约号详情\">{{d.heyuehao.name}}</div></div>"
            }
        }
        return cols;
    }


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

    exports('qiji', {})
});