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

    var id = null;
    table.on('tool(table)',function (obj) {
        var data = obj.data,layEvent = obj.event;
        id = data.id;
        if(layEvent == "detail"){

            //温湿度历史曲线
            showLSQX(id,'温度');

            //设置每次弹框默认为温度
            $('#cjzl_tab li').each(function() {
                $('#cjzl_tab li').removeClass('layui-this');
                $('#cjzl_tab li').eq(0).addClass('layui-this');
            });
            $('#s_tubiao .layui-tab-content .layui-tab-item').removeClass('layui-show');
            $('#s_tubiao .layui-tab-content #echarts_div').addClass('layui-show');

            layer.open({
                type: 1,
                title: ['详情 ', 'font-size:12px;'],
                content: $("#s_tubiao"),
                shadeClose: false, // 点击遮罩关闭层
                shade: 0.8,
                area: ['90%', '90%'],
                offset: 'auto'
            });

        }
    });

    function showLSQX(id,type){

        var myChart = echarts.init(document.getElementById('lsqx'));

        //假数据
        var base = +new Date(1968, 9, 3);
        var oneDay = 24 * 3600 * 1000;
        var date = [];

        var data = [Math.random() * 300];

        for (var i = 1; i < 20000; i++) {
            var now = new Date(base += oneDay);
            date.push([now.getFullYear(), now.getMonth() + 1, now.getDate()].join('/'));
            data.push(Math.round((Math.random() - 0.5) * 20 + data[i - 1]));
        }

        option = {
            /*legent:{
                width:'auto'
            },*/
            tooltip: {
                trigger: 'axis',
                position: function (pt) {
                    return [pt[0], '10%'];
                }
            },
            title: {
                left: 'center',
                text: '大数据量面积图',
            },
            toolbox: {
                feature: {
                    dataZoom: {
                        yAxisIndex: 'none'
                    },
                    restore: {},
                    saveAsImage: {}
                }
            },
            xAxis: {
                type: 'category',
                boundaryGap: false,
                data: date
            },
            yAxis: {
                type: 'value',
                boundaryGap: [0, '100%']
            },
            dataZoom: [{
                type: 'inside',
                start: 0,
                end: 10
            }, {
                start: 0,
                end: 10,
                handleIcon: 'M10.7,11.9v-1.3H9.3v1.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4v1.3h1.3v-1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z',
                handleSize: '80%',
                handleStyle: {
                    color: '#fff',
                    shadowBlur: 3,
                    shadowColor: 'rgba(0, 0, 0, 0.6)',
                    shadowOffsetX: 2,
                    shadowOffsetY: 2
                }
            }],
            series: [
                {
                    name:'模拟数据',
                    type:'line',
                    smooth:true,
                    symbol: 'none',
                    sampling: 'average',
                    itemStyle: {
                        color: 'rgb(255, 70, 131)'
                    },
                    areaStyle: {
                        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                            offset: 0,
                            color: 'rgb(255, 158, 68)'
                        }, {
                            offset: 1,
                            color: 'rgb(255, 70, 131)'
                        }])
                    },
                    data: data
                }
            ]
        };

        myChart.clear();
        setTimeout(function(){
            myChart.resize();
        },500);
        myChart.setOption(option);

    }

    $('#wendu_btn').on("click",function () {
        showLSQX(id,'温度');
    });
    $('#shidu_btn').on("click",function () {
        showLSQX(id,'湿度');
    });


    exports('wenshidu', {})
});