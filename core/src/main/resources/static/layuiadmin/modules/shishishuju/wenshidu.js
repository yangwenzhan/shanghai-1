layui.define(['table', 'form'], function(exports){
    var $ = layui.$
        ,table = layui.table
        ,form = layui.form;

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

        //假数据
        var base = +new Date(1968, 9, 3);
        var oneDay = 24 * 3600 * 1000;
        var datas=[];
        for (var i = 1; i < 2000; i++) {
            var now = new Date(base += oneDay);
            var obj = {date:[now.getFullYear(), now.getMonth() + 1, now.getDate()].join('/'),
                data:Math.round((Math.random() - 0.5) * 20 + Math.random() * 300)};
            datas.push(obj);
        }

        var config = {
            id : 'lsqx',	//渲染目标
            title : '',	//图表标题,不传不显示
            type : 'area',			//图表类型, 可以传递一个数组，例如 type: ['line','bar','scatter'],   可选 line , bar , pie, area , scatter
            toolbox : false,		//不传默认为true
            smooth: true,			//不传递默认为false
            showDataZoom : true ,	//不传递默认为false,是否显示区域缩放的工具条,大多用于渲染历史数据,配合 area 图使用
            labels : ['日期',type],		//中文名
            columns : ['date','data'],		//字段名
            yAxisNames:['%'],
            yAxisIndexs: [0,1],
            datas : datas,	//数据
            onClick : function(params){
                console.log(params)
            }
        }

        TIS.renderEcharts(config);

    }

    $('#wendu_btn').on("click",function () {
        showLSQX(obj_data,'温度');
    });
    $('#shidu_btn').on("click",function () {
        showLSQX(obj_data,'湿度');
    });


    exports('wenshidu', {})
});