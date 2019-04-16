/*
 * 弹出层
 */

//绘制机台详细信息表格
function darwTable_my(startIndex, data) {
    var table = $("<table/>");
    table.attr("id", "xxcs" + startIndex);
    table.attr("class", "layui-table");
    if(data.datas.length > 0){
        table.append("<caption>" + data.datas[startIndex].MingCheng + "</caption>");
    }
    var result = $("<div/>");
    result.append(table);
    result.append("<div id='show_xxcs" + startIndex + "'/>	");
    return result.html();
}

//render数据
function renderTableData(startIndex, endIndex, data) {
    var tbdata = [];
    for (var i = startIndex; i < endIndex; i++) {
        tbdata.push(data.datas[i]);
    }

    var t = layui.table;

    // 展示已知数据
    t.render({
        elem: '#show_xxcs' + startIndex,
        height: 'auto',
        data: tbdata,
        cols: [[{
            field: 'CanShuMing', title: '参数名',width:'30%'
        }, {
            field: 'CanShuZhi', title: '参数值',width:'30%'
        }, {
            field: 'DanWei', title: '单位',width:'30%'
        }]],
        skin: 'row', // 表格风格
        even: true
        , page: false //是否显示分页
        , limit: 10000
    });
}



//查询实时数据详细参数
function showXxInfo(jt_id,jth) {
    var loadingIndex = layer.load(0, {
        shade: [0.5, '#aaa']
    });
    //设置每次弹框默认为详细参数
    $('#cjzl_tab li').each(function () {
        $('#cjzl_tab li').removeClass('layui-this');
        $('#cjzl_tab li').eq(0).addClass('layui-this');
    });
    $.ajax({
        url:layui.setter.host+'shishishuju/cur_gongxu/queryXxcsByJtid',
        type:'get',
        data:{
            jt_id:jt_id
        },
        success:function (data) {
            layer.close(loadingIndex);
            if(data.code==0){

                console.log(data)

                if(data.data.length>0) {
                    //参数类别的index数组
                    var dataNumberArray = new Array();
                    dataNumberArray.push(0);
                    var newTable = "";
                    //遍历datas获取所有的不重复的参数类别的开始index
                    for (var i = 1; i < data.data.length; i++) {
                        if (data.data[i].MingCheng != data.data[i - 1].MingCheng) {
                            dataNumberArray.push(i);
                        }
                    }
                    for (var j = 0; j < dataNumberArray.length; j++) {
                        newTable += darwTable_my(dataNumberArray[j], data);
                    }
                    //创建表格
                    $("#show_xxcs").html(newTable);
                    //给表格渲染数据render
                    for (var k = 0; k < dataNumberArray.length; k++) {
                        if (k == dataNumberArray.length - 1) {
                            renderTableData(dataNumberArray[k], data.data.length, data);
                        } else {
                            renderTableData(dataNumberArray[k], dataNumberArray[k + 1], data);
                        }
                    }
                }
                // 查看
                layer.open({
                    type: 1,
                    title: [jth + '详情 ', 'font-size:12px;'],
                    content: $("#s_tubiao"),
                    shadeClose: false, // 点击遮罩关闭层
                    shade: 0.8,
                    area: ['1000px', '80%'],
                    offset: '60px'
                }); // 弹出 end

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


/*
 * 历史图标数据加载顺序
 * 1.获取右侧按钮
 * 2.根据按钮ID绘制图表
 *
 */
//查询历史曲线按钮
function queryLsqxBtn(jt_id) {
    var loadingIndex = layer.load(0, {
        shade: [0.5, '#aaa']
    });
    $.ajax({
        url:layui.setter.host+'shishishuju/cur_gongxu/cur_buji',
        type:'get',
        data:{
            jt_id:jt_id
        },
        success:function (data) {
            layer.close(loadingIndex);
            if(data.code==0){
                var qxList = $("#show_lsqx").html();
                var btnList = $("#show_btn").html();
                for (var i = 0; i < data.data.length; i++) {
                    qxList += "<div class='echarts'  id='echarts" + i + "'></div>";
                    btnList += "<button class='layui-btn' onclick=\"showLsqx('" + data.data[i].id + "','"+data.data[i].name+"')\">" + data.data[i].name + "</button>";
                }
                $("#show_lsqx").html(qxList);
                $("#show_btn").html(btnList);
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


//查询历史曲线
function showLsqx(id,csm) {
    var loadingIndex = layer.load(0, {
        shade: [0.5, '#aaa']
    });
    $.ajax({
        url: layui.setter.host+'shishishuju/cur_wenshidu/findHistory',
        type: "get",
        data:{id:data.id},
        success:function(data){
            if(data.code==0){
                var config = {
                    id : 'show_lsqx',	//渲染目标
                    type : 'area',			//图表类型, 可以传递一个数组，例如 type: ['line','bar','scatter'],   可选 line , bar , pie, area , scatter
                    smooth: true,			//不传递默认为false
                    showDataZoom : true ,	//不传递默认为false,是否显示区域缩放的工具条,大多用于渲染历史数据,配合 area 图使用
                    labels : ['时间',type],		//中文名
                    columns : ["shijian","wendu"],		//字段名
                    datas : data.data
                }
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

/*故障报警*/
function showGZBJ(jth) {
    var loadingIndex = layer.load(0, {
        shade: [0.5, '#aaa']
    });
    $.ajax({
        url:layui.setter.host+'shishishuju/cur_gongxu/queryBjcsByJtid',
        type:'get',
        data:{
            jt_id:jt_id
        },
        success:function (data) {
            layer.close(loadingIndex);
            if(data.code==0){

                var cols =  [
                    {field: 'rownum',width:55,fixed:true}
                    ,{field: 'jitaihao',sort:true, title: '机台号',width:90,fixed:true}
                ];

                //自定义化后台返回列配置
                var cols = fixedColumn(cols);
                //展示已知数据
                table.render({
                    elem: '#show_gzcs'
                    , id: 'show_gzcs'
                    , data: data.data
                    , cols: [cols]
                    , skin: 'row' //表格风格
                    , even: true
                    , page: false //是否显示分页
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

//丰富列配置功能
function fixedColumn(cols) {
    for(var i = 0; i < cols.length; i++) {
        var col = cols[i];
        if(col.fixed === '1')
            col.fixed = true;
        else col.fixed = false;

        if(col.field == 'bl') {
            col.templet = "<div><div style=\"color:{{d.bl>=100.00? 'red':'green'}}\">{{d.bl}}%</div></div>"
        }

        //报警值为1  表明是当前是报警状态。
        if(col.field == 'cs_value') {
            col.templet = "<div>{{d.cs_value == 1 ?  '<i class=\"fa fa-warning (alias)\" style=\"color:red\"></i>' : d.cs_value}}</div></div>"
        }

    }
    return cols;
}
