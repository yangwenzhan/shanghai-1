layui.define(['table', 'form', 'laydate'], function(exports){
    var $ = layui.$
        ,table = layui.table
        ,form = layui.form;

    var jt_id = null,jth=null;

    //筛选条件
    dynamicForm(
        {code:"yunxingzhuangtai",hasNull:true,defaultValue:""}
        ,{code:"zhijijixing",hasNull:true,defaultValue:""}
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

    initBuJi();
    setInterval(function(){
        initBuJi();
        }, 30000);

    function initBuJi(){
        $.ajax({
            url:layui.setter.host+'shishishuju/cur_gongxu/cur_buji',
            type:'get',
            async:false,
            data:{
                hyh_id:$('#hyh').val(),
                jx_id:$('#jixing').val(),
                yxzt_id:$('#yunxingzhuangtai').val()
            },
            success:function (data) {
                var cols =  [
                    {field: 'rownum',width:55,fixed:true}
                    ,{field: 'jitaihao',sort:true, title: '机台号',width:90,fixed:true}
                    ,{field: 'yxzt',sort:true, title: '运行状态',width:100}
                    ,{field: 'jixing',sort:true, title: '机型',width:100}
                    ,{field: 'heyuehao',sort:true, title: '合约号',width:100}
                    ,{field: 'pibuguige',sort:true,title: '坯布规格',width:120}
                    ,{field: 'zhou_name',sort:true, title: '织轴号',width:90}
                    ,{field: 'jingchang',sort:true, title: '当前经长m',width:120}
                    ,{field: 'zz_zjc',sort:true, title: '总经长m',width:100}
                    ,{field: 'buchang',sort:true, title: '当前布长m',width:120}
                    ,{field: 'shedingbuchang',sort:true, title: '设定布长m',width:120}
                    ,{field: 'luobushijian',sort:true, title: '落布倒计时min',width:150}
                    ,{field: 'liaojishijian',sort:true, title: '了机倒计时min',width:150}
                    ,{field: 'jingting',sort:true, title: '经停',width:80}
                    ,{field: 'weiting',sort:true, title: '纬停',width:80}
                    ,{field: 'zongting',sort:true, title: '总停',width:80}
                    ,{field: 'chesu',sort:true, title: '车速',width:80}
                    ,{field: 'xiaolv',sort:true, title: '效率',width:80}
                    ,{field: 'ygxm',sort:true, title: '员工',width:120}
                    ,{field: 'last_modify_time',sort:true, title: '最后更新时间',width:220}
                ];
                cols = formatColumns(cols);
                cols.push({
                    fixed: 'right',
                    width: 70,
                    align: 'center',
                    title: '操作',
                    toolbar: '#barDemo'
                });
                if(data.code==0){
                    var buji_data=[];
                    for(var i=0;i<data.data.length;i++){
                        if(data.data[i].px==1){
                            buji_data.push(data.data[i]);
                        }else{
                            $('#total_info_container').empty();

                            $('#total_info_container').html(
                                '<span>总布长m：<b>'+(data.data[i].zongbuchang == null ? "0" : data.data[i].zongbuchang)+'</b></span>， '+
                                '<span>总停次数：<b>' + (data.data[i].zongtingcishu == null ? "0" : data.data[i].zongtingcishu) + '</b></span>， ' +
                                '<span style="color:green;">开台数：<b>'+(data.data[i].kts == null ? "0" : data.data[i].kts)+'</b></span> '
                            );
                        }
                    }
                    table.render({
                        elem: '#table',
                        id: 'table',
                        data: buji_data,
                        cols: [cols],
                        skin: 'row', // 表格风格
                        page: false,
                        height: 'full-110',
                        limit: 1000000,
                        even: true
                    });
                }else{
                    layer.open({      //系统异常
                        title:"消息提醒",content:data.message,skin:"layui-layer-molv",btn:["查看错误信息"],anim: -1,icon:5,
                        btn1:function(index){
                            layer.open({content:data.data});
                            layer.close(index);
                        }
                    });
                }
            }
        });
    }

    table.on('tool(table)', function(obj) {
        var data = obj.data;
        var layEvent = obj.event;
        jt_id = data.jitai_id;
        jth = data.jitaihao;
        if(layEvent === 'detail') {
            showXxInfo(jt_id,jth);
            queryLsqxBtn(jt_id);
        }
    });

    form.on('submit(form_search)',function(data){
        initBuJi();
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

    //故障参数
    $('#windowpop_gzcs').on("click",function () {
        showGZBJ(jt_id);
    });
    //历史曲线
    $('#windowpop_lsqx').on("click",function () {
        initChart();
    });

    // 丰富列配置功能
    function formatColumns(cols) {
        for(var i = 0; i < cols.length; i++) {
            var col = cols[i];
            if(col.field == 'yxzt') {
                col.templet ="#mysfxx";
            }
            if(col.field == 'heyuehao'){
                col.templet = "<div><div id='{{d.heyuehao_id}}' lay-submit lay-filter='heyuehao_filter' title=\"点击查看合约号详情\">{{d.heyuehao}}</div></div>"
            }
        }
        return cols;
    }

    exports('buji', {})
});