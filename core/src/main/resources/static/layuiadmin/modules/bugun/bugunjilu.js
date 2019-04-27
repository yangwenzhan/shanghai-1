layui.define(['table', 'form', 'laydate'], function(exports){
    var $ = layui.$
        ,table = layui.table
        ,form = layui.form
        ,laydate = layui.laydate;

    //筛选条件初始化
    var initValue = formatDate(addDate(new Date(),0));
    laydate.render({
        elem: '#kaishiriqi',
        type: 'date',
        value: initValue
    });
    laydate.render({
        elem: '#jieshuriqi',
        type: 'date',
        value: initValue
    });
    $('#kaishiriqi').val(initValue);
    $('#jieshuriqi').val(initValue);

    function getAllHeYueHao(downID, selectedId, isAll) {
        var reg = RegExp(/,/);
        var downId_arr=[];
        if(downID==null){
            downId_arr=null;
        }else if(reg.test(downID)){
            downId_arr = downID.split(',');
        }else{
            downId_arr.push(downID);
        }
        $.ajax({
            url: layui.setter.host + 'common/findHeYueHao',
            type: 'get',
            async: false,
            success: function (data) {
                for(var i = 0;i<downId_arr.length;i++){
                    initDownList(data, downId_arr[i], selectedId, 'name', 'id', isAll);
                }
                form.render();
            }
        });
    }
    function getJiTaiHao(downID, selectedId, isAll, gongxu, jixing){
        var reg = RegExp(/,/);
        var downId_arr=[];
        if(downID==null){
            downId_arr=null;
        }else if(reg.test(downID)){
            downId_arr = downID.split(',');
        }else{
            downId_arr.push(downID);
        }
        $.ajax({
            url: layui.setter.host + 'common/findJiTaiHao',
            type: 'get',
            async: false,
            data: {
                "gongxu": gongxu,
                "jixing": jixing
            },
            success: function (data) {
                for(var i = 0;i<downId_arr.length;i++){
                    initDownList(data, downId_arr[i], selectedId, 'jitaihao', 'id', isAll);
                }
                layui.form.render();
            }
        });
    }
    function getBanCi(downID, selectedId, isAll){
        var reg = RegExp(/,/);
        var downId_arr=[];
        if(downID==null){
            downId_arr=null;
        }else if(reg.test(downID)){
            downId_arr = downID.split(',');
        }else{
            downId_arr.push(downID);
        }
        $.ajax({
            url: layui.setter.host + 'common/findAllDictVal',
            type: 'get',
            data:{
                code:"banci"
            },
            async: false,
            success: function (data) {
                var dicts = data.data.dicts;
                dicts = dicts.sort(sortSort);
                var dict_data = {code:data.code,data:dicts,message:data.message};
                for(var i = 0;i<downId_arr.length;i++){
                    initDownList(dict_data, downId_arr[i], selectedId, 'name', 'value', isAll);
                }
                form.render();
            }
        });
    }
    getBanCi("kaishibanci,jieshubanci",null,false);
    getAllHeYueHao("heyuehao", null, true);
    getJiTaiHao("jitaihao", null, true, '织布', null);


    var cols =  [
        {field: 'id', title: 'id',hide:true}
        ,{field: 'riqi',sort:true, title: '日期',fixed:true,width:110}
        ,{title: '班次',sort:true, templet: repNull('banci.name'),fixed:true, width:80}
        ,{title: '机台号',sort:true, templet: repNull('jitaihao.jitaihao'),width:100}
        ,{title: '合约号',sort:true, templet: repNull('heyuehao.name'), width:100}
        ,{title: '坯布规格',sort:true, templet: repNull('heyuehao.order.pibuguige'),width:170}
        ,{title: '轮班',sort:true, templet: repNull('lunban.name'), width:80}
        ,{title: '落布人',sort:true, templet: repNull('luoburen.xingming'), width:80}
        ,{title: '左织轴号',sort:true, templet: repNull('zhiZhou_left.zhouhao'),width:120}
        ,{title: '右织轴号',sort:true, templet: repNull('zhiZhou_right.zhouhao'),width:120}
        ,{field: 'changdu',sort:true, title: '落布长度m',width:120}
        ,{field: 'shedingchangdu',sort:true, title: '设定落布长度m',width:140}
        ,{align: 'center',title: '操作',toolbar: '#barDemo',width:150, fixed: 'right'}
    ];
    cols = formatColumns(cols);

    initTable("table", 'bugun/bugunjilu/findAll', 'get',[cols], table, "form");

    form.on('submit(form_search)',function(data){
        var field = data.field;
        var ksbc = field.kaishibanci == "" ? "10" : field.kaishibanci;
        var jsbc = field.jieshubanci == "" ? "30" : field.jieshubanci;
        if(field.kaishiriqi != null && field.kaishiriqi != ""){
            field.kaishixuhao = field.kaishiriqi.replace(/-/g,'')+ksbc;
        }
        if(field.jieshuriqi !=null && field.jieshuriqi != ""){
            field.jieshuxuhao = field.jieshuriqi.replace(/-/g,'')+jsbc;
        }
        table.reload('table', {
            where: field
        });
        return false;
    });


    //修改
    table.on('tool(table)',function(obj) {
        var data = obj.data;

        if(obj.event === 'left_detail'){
            showZhuiZong(data.zhiZhou_left.id);
        }else{
            showZhuiZong(data.zhiZhou_right.id);
        }
    });

    function showZhuiZong(shift_zhou_id){




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
        var reg = RegExp(/heyuehao.name/);
        for(var i = 0; i < cols.length; i++) {
            var col = cols[i];
            if(reg.test(col.templet)) {
                col.templet = "<div><div id='{{d.heyuehao.id}}' lay-submit lay-filter='heyuehao_filter' title=\"点击查看合约号详情\">{{d.heyuehao.name}}</div></div>"
            }
        }
        return cols;
    }

    //根据sort排序
    function sortSort(a,b){
        return a.sort-b.sort;
    }

    exports('bugunjilu', {})
});