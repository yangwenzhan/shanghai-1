layui.define(['table', 'form', 'laydate'], function(exports){
    var $ = layui.$
        ,table = layui.table
        ,form = layui.form;

    initLuoBuYuCe();
    setInterval(function(){
        initLuoBuYuCe();
        }, 30000);

    var datas = null;
    function initLuoBuYuCe(){
        $.ajax({
            url:layui.setter.host+'shishishuju/cur_gongxu/cur_luobuyuce',
            type:'get',
            success:function (data) {
                if(data.code==0){
                    datas = data.data;
                    showEcharts(datas);
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

    function showEcharts(data){
        //机台号，了机剩余时长，了机时刻
        var arr_jth=[],arr_sysc=[],arr_lbsk=[];
        for(var i=data.length-1;i>=0;i--){
            var dataObj = data[i];
            arr_jth.push(dataObj.jitaihao);
            arr_sysc.push(dataObj.luobushijian==null?"":dataObj.luobushijian);
            arr_lbsk.push(dataObj.lbsk==null?"":dataObj.lbsk);
        }
        var myChart = echarts.init(document.getElementById('show_tubiao_luobu'));
        var option = {
            tooltip: {
                show:true,
                trigger: 'axis',
                axisPointer: {
                    type: 'shadow'
                },
                formatter:function(arr_jth,xxxx){
                    var jth=arr_jth[0];
                    var state = "";
                    var index = jth["dataIndex"];
                    state = arr_sysc[index];
                    var str = jth.name+"<br>"
                        +jth.seriesName+":"+state+"分钟<br>"
                        +"预计落布时刻:"+jth.data;
                    return str;
                }
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis: {
                min:new Date(),
                type: 'time',
                boundaryGap: [0, 0.01],
                position:'top'
            },
            yAxis: {
                type: 'category',
                axisLabel:{
                    interval:0
                },
                data: arr_jth
            },
            series: [
                {
                    name: '剩余时长',
                    type: 'bar',
                    data: arr_lbsk,
                    itemStyle:{
                        normal:{
                            color:function(dt){
                                var index =dt.dataIndex;
                                if(arr_sysc[index]>=60){
                                    return "#81C0C0";
                                }else{
                                    return "#E15457";
                                }
                            }
                        }
                    }
                }
            ]
        };
        // 为echarts对象加载数据
        myChart.clear();
        myChart.setOption(option);
        setTimeout(function() {
            myChart.resize();
        }, 200);
    }

    function showTable(data){
        //渲染表格
        var cols =  [
            {field: 'rownum',width:55,fixed:true}
            ,{field: 'jitaihao',sort:true, title: '机台号',width:90,fixed:true}
            ,{field: 'luobushijian',sort:true, title: '落布倒计时min',width:130}
            ,{field: 'lbsk',sort:true, title: '预计落布时刻',width:170}
            ,{field: 'yxzt',sort:true, title: '运行状态',width:100}
            ,{field: 'jixing',sort:true, title: '机型',width:100}
            ,{field: 'heyuehao',sort:true, title: '合约号',width:100}
            ,{field: 'pibuguige',sort:true,title: '坯布规格',width:120}
            ,{field: 'zhouhao',sort:true, title: '织轴号',width:100}
            ,{field: 'buchang',sort:true, title: '当前布长m',width:110}
            ,{field: 'shedingbuchang',sort:true, title: '设定布长m',width:110}
            ,{field: 'bugunzongchang',sort:true, title: '已产布长m',width:110}
            ,{field: 'chesu',sort:true, title: '车速',width:80}
        ];
        cols = formatColumns(cols);
        table.render({
            elem: '#table',
            id: 'table',
            data: data,
            cols: [cols],
            skin: 'row', // 表格风格
            page: false,
            height: 'full-110',
            limit: 1000000,
            even: true
        });
    }

    $('#tubiao_btn').on('click',function(){
        $('#tubiao_div_luobu').css("display","block");
        $('#shuju_div').css("display","none");
        $('#tubiao_btn').removeClass("layui-btn-primary");
        $('#shuju_btn').addClass("layui-btn-primary");
        showEcharts(datas);
    });
    $('#shuju_btn').on('click',function(){
        $('#tubiao_div_luobu').css("display","none");
        $('#shuju_div').css("display","block");
        $('#tubiao_btn').addClass("layui-btn-primary");
        $('#shuju_btn').removeClass("layui-btn-primary");
        showTable(datas);
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
            if(col.field == 'yxzt') {
                col.templet ="#mysfxx";
            }
            if(col.field == 'heyuehao'){
                col.templet = "<div><div id='{{d.heyuehao_id}}' lay-submit lay-filter='heyuehao_filter' title=\"点击查看合约号详情\">{{d.heyuehao == null ? '' : d.heyuehao}}</div></div>"
            }
        }
        return cols;
    }

    exports('luobuyuce', {})
});