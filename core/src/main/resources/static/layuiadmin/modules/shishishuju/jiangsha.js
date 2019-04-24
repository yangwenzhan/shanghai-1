layui.define(['table', 'form', 'laydate'], function(exports){
    var $ = layui.$
        ,table = layui.table
        ,form = layui.form;

    initJiangSha();
    setInterval(function(){
        initJiangSha();
        }, 30000);

    function initJiangSha(){
        $.ajax({
            url:layui.setter.host+'shishishuju/cur_gongxu/cur_jiangsha',
            type:'get',
            success:function (data) {
                var cols =  [
                    {field: 'rownum',width:55,fixed:true}
                    ,{field: 'jitaihao',sort:true, title: '机台号'}
                    ,{field: 'jixing',sort:true, title: '机型'}
                    ,{field: 'heyuehao',sort:true, title: '合约号'}
                    ,{field: 'pibuguige',sort:true,title: '坯布规格'}
                    ,{field: 'ygxm',sort:true, title: '员工'}
                    ,{field: 'shijian',sort:true, title: '最后登记时间'}
                ];
                cols = formatColumns(cols);
                if(data.code==0){
                    table.render({
                        elem: '#table',
                        id: 'table',
                        data: data.data,
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
            if(col.field == 'heyuehao'){
                col.templet = "<div><div id='{{d.heyuehao_id}}' lay-submit lay-filter='heyuehao_filter' title=\"点击查看合约号详情\">{{d.heyuehao == null ? '' : d.heyuehao}}</div></div>"
            }
        }
        return cols;
    }

    exports('jiangsha', {})
});