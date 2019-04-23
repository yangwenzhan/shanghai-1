layui.define(['table', 'form', 'laydate'], function(exports){
    var $ = layui.$
        ,table = layui.table
        ,form = layui.form;

    //筛选条件
    function getHeYueHao(downID, selectedId, isAll) {
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
    function getGangHao(downID, selectedId, isAll,hyh_id){
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
            url: layui.setter.host + 'zhiliang/jiangshazhiliang/findGangHao',
            type: 'get',
            async: false,
            data: {
                "heyuehao_id": hyh_id
            },
            success: function (data) {
                for(var i = 0;i<downId_arr.length;i++){
                    initDownList(data, downId_arr[i], selectedId, 'ganghao', 'ganghao', isAll);
                }
                layui.form.render();
            }
        });
    }
    getHeYueHao("heyuehao", null, true);

    var cols =  [
        {field: 'id', title: 'id',hide:true}
        ,{title: '合约号',sort:true, templet: repNull('heyuehao.name'),fixed:true, width:100}
        ,{title: '坯布规格',sort:true, templet: repNull('heyuehao.order.pibuguige'),fixed:true, width:170}
        ,{field: 'ganghao',sort:true, title: '缸号',fixed:true, width:80}
        ,{field: 'niandumiao',sort:true, title: '粘度秒',width:100}
        ,{field: 'jiangtonghangulv',sort:true, title: '浆桶含固率',width:120}
        ,{field: 'jiangcaohangulv',sort:true, title: '浆槽含固率',width:120}
        ,{field: 'shangjianglv',sort:true, title: '上浆率%',width:100}
        ,{field: 'shangjianghouqiangli',sort:true, title: '上浆后强力cn',width:120}
        ,{field: 'qianglizengqiang',sort:true, title: '强力增强%',width:120}
        ,{field: 'shangjianghuichao',sort:true, title: '上浆回潮%',width:120}
        ,{field: 'zhengjingzongchangdu',sort:true, title: '整经重长度m',width:120}
        ,{field: 'jiangshazongchangdu',sort:true, title: '浆纱总长度m',width:120}
        ,{field: 'shenchang',sort:true, title: '伸长%',width:100}
        ,{field: 'beizhu',sort:true, title: '备注',width:100}
        ,{align: 'center',title: '操作',toolbar: '#barDemo',width:100, fixed: 'right'}
    ];
    cols = formatColumns(cols);

    initTable("table", 'zhiliang/jiangshazhiliang/findAll', 'get',[cols], table, "form");


    form.on('select(add_heyuehao)', function() {
        getGangHao("add_ganghao", null, false, $('#add_heyuehao').val());
    });
    form.on('submit(form_search)',function(data){
        var field = data.field;
        table.reload('table', {
            where: field
        });
        return false;
    });

    //新增
    $('#add_btn').on('click',function(){
        getHeYueHao("add_heyuehao", $('#heyuehao').val(), false);
        getGangHao("add_ganghao", null, false, $('#add_heyuehao').val());
        layer.open({
            type: 1,
            title: ['新增参数类别', 'font-size:12px;'],
            content: $("#add_form_div"),
            shadeClose: true, //点击遮罩关闭层
            shade: 0.8,
            offset:'auto',
            area: ['80%', '90%'],
            btn: ['确定', '取消'],
            btnAlign: 'c',
            yes: function(index, layero) {
                layer.confirm('确定新增?'
                    ,function(i){
                        form.on('submit(form_add_submit)', function (data) {
                            var formData = data.field;
                            formData.heyuehao = {id:$('#add_heyuehao').val()};
                            $.ajax({
                                url:layui.setter.host+'zhiliang/jiangshazhiliang/updJiangShaZhiLiang',
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
        var js_id = data.id;
        var hyh_id = data.heyuehao.id;

        if(obj.event === 'edit'){
            layer.open({
                type: 1
                ,title: '编辑合约号: '+data.heyuehao.name+' 缸号: '+data.ganghao+' 浆纱质量信息'
                ,content: $('#edit_form_div')
                ,offset:'auto'
                ,area: ['80%', '80%']
                ,btn: ['修改', '取消']
                ,btnAlign: 'c'
                ,btn1: function(index, layero) {
                    layer.confirm('确定修改合约号: '+data.heyuehao.name+' 缸号: '+data.ganghao+' 质量信息?'
                        ,function(i){

                            form.on('submit(form_edit_submit)', function (data) {
                                var formData = data.field;
                                formData.id = js_id;
                                formData.heyuehao = {id:hyh_id}
                                $.ajax({
                                    url:layui.setter.host+'zhiliang/jiangshazhiliang/updJiangShaZhiLiang',
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
                    // form.val('form_edit',data);
                },
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

    exports('jiangsha', {})
});