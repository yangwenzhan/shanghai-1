/*
 * 弹出层
 * 详细参数从sys_current_buji 取
 */

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
    $('#s_tubiao .layui-tab-content .layui-tab-item').removeClass('layui-show');
    $('#s_tubiao .layui-tab-content #xxcs_div').addClass('layui-show');

    $.ajax({
        url:layui.setter.host+'shishishuju/cur_gongxu/queryXxcs_curBuji',
        type:'get',
        data:{
            jt_id:jt_id
        },
        success:function (data) {
            layer.close(loadingIndex);
            if(data.code==0){
                var cols = [
                    {field: 'csm', width: 200, title: '参数名'}
                    , {field: 'csz', width: 200, title: '参数值'}
                ];
                layui.table.render({
                    elem: '#xxcs_table'
                    ,data:data.data
                    ,limit:10000
                    ,cols: [cols]
                });

                // 查看
                layer.open({
                    type: 1,
                    title: [jth + '详情 ', 'font-size:12px;'],
                    content: $("#s_tubiao"),
                    shadeClose: false, // 点击遮罩关闭层
                    shade: 0.8,
                    area: ['90%', '90%'],
                    offset: '10px'
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
    $.ajax({
        url:layui.setter.host+'shishishuju/cur_gongxu/findHisDataBtn',
        type:'get',
        data:{
            jt_id:jt_id
        },
        success:function (data) {
            if(data.code==0){
                $("#show_lsqx").empty();
                $("#show_btn").empty();
                var qxList = $("#show_lsqx").html();
                var btnList = $("#show_btn").html();
                for (var i = 0; i < data.data.length; i++) {
                    qxList += "<div class='echarts'  id='echarts" + i + "'></div>";
                    btnList += "<button class='layui-btn' onclick=\"showLsqx('" + data.data[i].id + "','"+data.data[i].name+"','"+jt_id+"')\">" + data.data[i].name + "</button>";
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
//fixme 假数据
function showLsqx(id,csm,jt_id) {

    $.ajax({
        url: layui.setter.host+'shishishuju/cur_gongxu/queryLsqx',
        type: "get",
        data:{
            param_id:id,
            jth_id:jt_id
        },
        success:function(data){
            if(data.code==0){
                var config = {
                    id : 'show_lsqx',	//渲染目标
                    type : 'area',			//图表类型, 可以传递一个数组，例如 type: ['line','bar','scatter'],   可选 line , bar , pie, area , scatter
                    smooth: true,			//不传递默认为false
                    showDataZoom : true ,	//不传递默认为false,是否显示区域缩放的工具条,大多用于渲染历史数据,配合 area 图使用
                    labels : ['时间',csm],		//中文名
                    columns : ["createTime","value"],		//字段名
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

function initChart(){
    var btns = $("#show_btn button");
    if(btns.length > 0){
        btns[0].click();
    }
}

/*故障报警*/
function showGZBJ(jt_id) {
    $.ajax({
        url:layui.setter.host+'shishishuju/cur_gongxu/queryBjcsByJtid',
        type:'get',
        data:{
            jt_id:jt_id
        },
        success:function (data) {
            if(data.code==0){
                var cols =  [
                    {field: 'name', title: '参数名',width:300}
                    ,{field: 'value', title: '报警状态',width:300}
                ];

                //自定义化后台返回列配置
                var cols = fixedColumn(cols);
                //展示已知数据
                layui.table.render({
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
        if(col.field == 'value') {
            col.templet = "<div>{{d.value == 1 ?  '<i class=\"fa fa-warning (alias)\" style=\"color:red\"></i>' : d.value}}</div></div>"
        }

    }
    return cols;
}
