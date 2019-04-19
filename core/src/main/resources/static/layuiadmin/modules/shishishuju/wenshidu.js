layui.define(['table', 'form'], function(exports){
    var $ = layui.$
        ,table = layui.table
        ,form = layui.form;

    function initWSD(){
        var cols =  [
            {field: 'id', title: 'id',hide:true}
            ,{field: 'name',sort:true, title: '名称'}
            ,{field: 'weizhi',sort:true, title: '位置'}
            ,{field: 'wendu',sort:true, title: '温度'}
            ,{field: 'shidu',sort:true, title: '湿度'}
            ,{field: 'lastModifiedDate',sort:true, title: '更新时间'}
            ,{align: 'center',title: '操作',toolbar: '#barDemo'}
        ];

        table.render({
            elem: '#table'
            ,limit:100000
            ,method:'GET'
            ,url: layui.setter.host + 'shishishuju/cur_wenshidu/findAll'
            ,cols: [cols]
        });
    }

    initWSD();
    setInterval(function(){
        initWSD();
    }, 30000);

    var obj_data = null;
    table.on('tool(table)',function (obj) {
        var data = obj.data,layEvent = obj.event;
        obj_data = data;
        if(layEvent == "detail"){

            //温湿度历史曲线
            showLSQX(obj_data,'温度');

            //设置每次弹框默认为温度
            $('#cjzl_tab li').each(function() {
                $('#cjzl_tab li').removeClass('layui-this');
                $('#cjzl_tab li').eq(0).addClass('layui-this');
            });
            $('#s_tubiao .layui-tab-content .layui-tab-item').removeClass('layui-show');
            $('#s_tubiao .layui-tab-content #echarts_div').addClass('layui-show');

            layer.open({
                type: 1,
                title: [data.name+' 详情', 'font-size:12px;'],
                content: $("#s_tubiao"),
                shadeClose: false, // 点击遮罩关闭层
                shade: 0.8,
                area: ['90%', '90%'],
                offset: 'auto'
            });

        }
    });

    function showLSQX(data,type){
        var column=[];
        if(type=="温度"){
            column=["shijian","wendu"];
        }else{
            column=["shijian","shidu"];
        }

        $.ajax({
            url: layui.setter.host+'shishishuju/cur_wenshidu/findHistory',
            type: "get",
            data:{id:data.id},
            success:function(data){
                if(data.code==0){
                    var config = {
                        id : 'lsqx',	//渲染目标
                        title : '',	//图表标题,不传不显示
                        type : 'area',			//图表类型, 可以传递一个数组，例如 type: ['line','bar','scatter'],   可选 line , bar , pie, area , scatter
                        toolbox : false,		//不传默认为true
                        smooth: true,			//不传递默认为false
                        showDataZoom : true ,	//不传递默认为false,是否显示区域缩放的工具条,大多用于渲染历史数据,配合 area 图使用
                        labels : ['时间',type],		//中文名
                        columns : column,		//字段名
                        yAxisNames:['°C'],
                        yAxisIndexs: [0,1],
                        datas : data.data
                    };
                    TIS.renderEcharts(config);
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

    }

    $('#wendu_btn').on("click",function () {
        showLSQX(obj_data,'温度');
    });
    $('#shidu_btn').on("click",function () {
        showLSQX(obj_data,'湿度');
    });


    exports('wenshidu', {})
});