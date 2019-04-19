layui.define(['table', 'form', 'laydate'], function(exports){
    var $ = layui.$
        ,table = layui.table
        ,form = layui.form;

    //筛选条件
    dynamicForm(
        {code:"zhizhouzhuangtai",hasNull:true,defaultValue:""}
        ,{code:"zhizhoubianhao",hasNull:true,defaultValue:""}
    );

    $.ajax({
        url:layui.setter.host+'shishishuju/cur_zhizhou/cur_zhizhou_hyh',
        type:'get',
        async:false,
        success:function(data){
            if(data.code == 0){
                var html = '';
                html+='<option value= "" >全部</option>';
                for(var i = 0;i<data.data.length;i++){
                    html+='<option value= "'+data.data[i].id+'" >'+data.data[i].name+'</option>';
                }
                $('#hyh').append(html);
            }
        }
    });
    form.render();

    function initZhiZhou() {
        var cols =  [
            {field: 'id', title: 'id',hide:true}
            ,{field: 'heyuehao',sort:true, title: '合约号',width:120,fixed:true}
            ,{field: 'zhouhao',sort:true, title: '织轴号',width:120,fixed:true}
            ,{field: 'pibuguige',sort:true,title: '坯布规格',width:120}
            ,{field: 'zongjingchang',sort:true, title: '总经长',width:80}
            ,{field: 'jixing',sort:true, title: '机型',width:120}
            ,{field: 'zt',sort:true, title: '织轴状态',width:120}
            ,{field: 'jitaihao',sort:true, title: '机台号',width:80}
            ,{field: 'jingchang',sort:true, title: '剩余经长',width:120}
            ,{field: 'chuanzong_flag',sort:true, title: '是否穿综',width:100}
            ,{field: 'weizhi',sort:true, title: '位置',width:80}
            ,{field: 'scsj',sort:true, title: '上车时间',width:120}
            ,{field: 'buji_xiaji_time',sort:true, title: '布机下车时间',width:120}
            ,{field: 'last_modify_ren',sort:true, title: '最后更新人',width:120}
            ,{field: 'last_modify_time',sort:true, title: '最后更新时间',width:120}
        ];

        cols = formatColumns(cols);

        table.render({
            elem: '#table'
            ,limit:100000
            ,method:'GET'
            ,url: layui.setter.host + 'shishishuju/cur_zhizhou/cur_zhizhou'
            ,where:{zt_id:$('#zz_zt').val(),
                heyuehao:$('#hyh').val(),
                zhouhao:$('#zz_bh').val(),
                weizhi:$('#zz_wz').val()
            }
            ,cols: [cols]
        });
    }
    initZhiZhou();
    setInterval(function(){
        initZhiZhou();
    }, 30000);


    form.on('submit(form_search)',function(data){
        var field = {
            zt_id:$('#zz_zt').val(),
            heyuehao:$('#hyh').val(),
            zhouhao:$('#zz_bh').val(),
            weizhi:$('#zz_wz').val()
        };
        table.reload('table',{where:field});
        return false;
    });


    //按合约号汇总
    $('#heyuehaohuizong_btn').on("click",function(){
        $.ajax({
            url:layui.setter.host+'shishishuju/cur_zhizhou/cur_zhizhou_hyshz',
            type:'get',
            success:function(data){
                if(data.code==0){
                    var cols = [
                        {field: 'heyuehao',sort:true,width:200, title: '合约号'}
                        ,{field: 'pibuguige',sort:true, width:200,title: '坯布规格'}
                        ,{field: 'zhoushu',sort:true,width:200, title: '织轴个数'}
                        ,{field: 'zongjingchang',sort:true,width:200, title: '总经长(米)'}
                        ,{field: 'zzbh',sort:true,width:200, title: '织轴号'}
                    ];
                    cols = formatColumns(cols);
                    table.render({
                        elem: '#hyh_dataGrid',
                        id: 'hyh_dataGrid',
                        data: data.data,
                        cols: [cols],
                        skin: 'row', // 表格风格
                        even: true,
                        limit: 10000
                    });
                    layer.open({
                        type: 1,
                        title: ['按合约号汇总 '],
                        content: $("#hyh_hz_tck"),
                        shade: 0.8,
                        area: ['70%', '90%'],
                        offset: "10px"
                    });
                }else{
                    layer.open({
                        title:"消息提醒",content:data.message,skin:"layui-layer-molv",btn:["查看错误信息"],anim: -1,icon:5,
                        btn1:function(index){
                            layer.open({content:data.data});
                            layer.close(index);
                        }
                    });
                }
            }
        });
        return false;
    });

    //按状态汇总
    $('#zhuangtaihuizong_btn').on("click",function(){
        $.ajax({
            url:layui.setter.host+'shishishuju/cur_zhizhou/cur_zhizhou_zthz',
            type:'get',
            success:function(data){
                if(data.code==0){
                    var cols = [
                        {field: 'zt',sort:true,width:200, title: '织轴状态'}
                        ,{field: 'zhoushu',sort:true,width:200, title: '织轴个数'}
                        ,{field: 'zongjingchang',sort:true,width:200, title: '总经长(米)'}
                        ,{field: 'zzbh',sort:true,width:200, title: '经轴号'}
                    ];
                    cols = formatColumns(cols);
                    table.render({
                        elem: '#zt_dataGrid',
                        id: 'zt_dataGrid',
                        data: data.data,
                        cols: [cols],
                        skin: 'row', // 表格风格
                        even: true,
                        limit: 10000
                    });
                    layer.open({
                        type: 1,
                        title: ['按状态汇总 '],
                        content: $("#zt_hz_tck"),
                        shade: 0.8,
                        area: ['70%', '90%'],
                        offset: "10px"
                    });
                }else{
                    layer.open({
                        title:"消息提醒",content:data.message,skin:"layui-layer-molv",btn:["查看错误信息"],anim: -1,icon:5,
                        btn1:function(index){
                            layer.open({content:data.data});
                            layer.close(index);
                        }
                    });
                }
            }
        });
        return false;
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
        for(var i = 0; i < cols.length; i++) {
            var col = cols[i];
            if(col.field == 'zt') {
                col.templet ="#mysfxx";
            }
            if(col.field == 'heyuehao'){
                col.templet = "<div><div id='{{d.heyuehao_id}}' lay-submit lay-filter='heyuehao_filter' title=\"点击查看合约号详情\">{{d.heyuehao}}</div></div>"
            }
        }
        return cols;
    }

    exports('zhizhou', {})
});