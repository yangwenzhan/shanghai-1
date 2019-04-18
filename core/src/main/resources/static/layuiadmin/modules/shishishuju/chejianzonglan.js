layui.define(['table', 'form', 'laydate'], function(exports){
    var $ = layui.$
        ,table = layui.table
        ,form = layui.form
        ,laydate = layui.laydate;

    showCjzl();
    setInterval(function(){
        showCjzl();
    }, 30000);

    var jt_id = null,jth = null;
    //设置每台车的图表弹窗等
    $("#wrap").find("p").click(function(){
        jt_id = $(this).attr('hidden_jth_id');
        jth = $(this).html();
        //以布机开头
        var reg = /^布机.*/;
        if(jth.indexOf("T")>=0){
            //温湿度弹框
            showWSD(jt_id,jth);
        }else if(reg.test(jth)){
            //布机弹框
            showXxInfo(jt_id,jth);
            queryLsqxBtn(jt_id);
        }else{

        }
    });
    $("#wrap").find("p").mouseover(function(){
        var hyh = $(this).attr("hidden_jth_hyh");
        var pbgg = $(this).attr("hidden_jth_pbgg");
        var ygxm = $(this).attr("hidden_ygxm");
        var wendu = $(this).attr("hidden_wendu");
        var shidu = $(this).attr("hidden_shidu");
        var str = $(this).html();
        var message = "";
        if(str.indexOf("T")>=0){
            message = "温度:" + wendu + "<br/>" + "湿度:" + shidu;
        }else{
            if(hyh == "" || pbgg=="") {
                message = "未生产!";
            }else if(hyh==undefined || pbgg==undefined){
                message = "无数据";
            }else {
                message = "员  工：" + ygxm +"<br/>" + "合约号： " + hyh + "<br/>" + "坯布规格：" + pbgg;
            }
        }
        layer.tips(message, $(this), {
            tips: [1, '#46698e'],
            time: 2000 //还可配置颜色
        });
    });

    function showCjzl(){
        $.ajax({
            url:layui.setter.host+'shishishuju/cur_gongxu/cur_chejianzonglan',
            type:'get',
            success:function (data) {
                if(data.code==0){
                    var dataList = data.data;
                    for(var i=0;i<dataList.length;i++){
                        var obj = dataList[i];
                        //P标签
                        var pContainer = $("div p:contains('"+obj.jitaihao+"')");
                        if(dataList[i].shebei==1){
                            //获取机台号的div
                            var jthDiv = pContainer.parent();
                            //运行状态和效率
                            jthDiv.next().empty();
                            jthDiv.next().attr("class","");
                            jthDiv.next().html(obj.xiaolv==null?'0%':obj.xiaolv+'%');
                            if("运行"==obj.yxzt){
                                jthDiv.next().addClass("xl green");
                            }else if("停止"==obj.yxzt){
                                jthDiv.next().addClass("xl red");
                            }else{
                                jthDiv.next().addClass("xl grey");
                            }

                            //进度条（ span标签的宽度）
                            var spanContainer = pContainer.prev();
                            spanContainer.css("width",(obj.bili==null?'0%':obj.bili+'%')>=100?'100%':(obj.bili==null?'0%':obj.bili+'%'));
                            //提示框
                            pContainer.attr("hidden_jth_pbgg", obj.pibuguige==null?'':obj.pibuguige);
                            pContainer.attr("hidden_jth_hyh", obj.heyuehao == null ? '' : obj.heyuehao);
                        }else{
                            //温湿度采集点数据
                            pContainer.attr("hidden_wendu", obj.wendu==null?'':obj.wendu);
                            pContainer.attr("hidden_shidu", obj.shidu==null?'':obj.shidu);
                        }
                        pContainer.attr("hidden_jth_id", obj.jitai_id == null ? '' : obj.jitai_id);
                        pContainer.attr("hidden_ygxm", obj.ygxm == null ? '' : obj.ygxm);
                    }

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

    //故障参数
    $('#windowpop_gzcs').on("click",function () {
        showGZBJ(jt_id);
    });
    //历史曲线
    $('#windowpop_lsqx').on("click",function () {
        initChart();
    });


    function showWSD(id,name){
        //温湿度历史曲线
        showWSD_LSQX(id,'温度');

        //设置每次弹框默认为温度
        $('#wsd_tab li').each(function() {
            $('#wsd_tab li').removeClass('layui-this');
            $('#wsd_tab li').eq(0).addClass('layui-this');
        });
        $('#wsd_tubiao .layui-tab-content .layui-tab-item').removeClass('layui-show');
        $('#wsd_tubiao .layui-tab-content #echarts_div').addClass('layui-show');

        layer.open({
            type: 1,
            title: [name+' 详情', 'font-size:12px;'],
            content: $("#wsd_tubiao"),
            shadeClose: false, // 点击遮罩关闭层
            shade: 0.8,
            area: ['90%', '90%'],
            offset: '10px'
        });
    }

    function showWSD_LSQX(id,type){
        var column=[];
        if(type=="温度"){
            column=["shijian","wendu"];
        }else{
            column=["shijian","shidu"];
        }
        $.ajax({
            url: layui.setter.host+'shishishuju/cur_wenshidu/findHistory',
            type: "get",
            data:{id:id},
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

    //温湿度
    $('#wendu_btn').on("click",function () {
        showWSD_LSQX(jt_id,'温度');
    });
    $('#shidu_btn').on("click",function () {
        showWSD_LSQX(jt_id,'湿度');
    });



    exports('chejianzonglan', {})
});