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
        ,{title: '班次',sort:true, templet: repNull('banci.name'),fixed:true, width:80,field: 'banci.name'}
        ,{title: '机台号',sort:true, templet: repNull('jitaihao.jitaihao'),width:100,field: 'jitaihao.jitaihao'}
        ,{title: '合约号',sort:true, templet: repNull('heyuehao.name'), width:100,field: 'heyuehao.name'}
        ,{title: '坯布规格',sort:true, templet: repNull('heyuehao.order.pibuguige'),width:170,field: 'heyuehao.order.pibuguige'}
        ,{title: '轮班',sort:true, templet: repNull('lunban.name'), width:80,field: 'lunban.name'}
        ,{title: '落布人',sort:true, templet: repNull('luoburen.xingming'), width:80,field: 'luoburen.xingming'}
        ,{title: '左织轴号',sort:true, templet: repNull('zhiZhou_left.zhouhao'),width:120,field: 'zhiZhou_left.zhouhao'}
        ,{title: '右织轴号',sort:true, templet: repNull('zhiZhou_right.zhouhao'),width:120,field: 'zhiZhou_right.zhouhao'}
        ,{field: 'changdu',sort:true, title: '落布长度m',width:120}
        ,{field: 'shedingchangdu',sort:true, title: '设定落布长度m',width:140}
        ,{field: 'luobushijian',sort:true, title: '落布时间',width:170}
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
            showZhuiZong(data,data.zhiZhou_left.id,1);
        }else{
            showZhuiZong(data,data.zhiZhou_right.id,2);
        }
    });

    function showZhuiZong(bugun_obj,shift_zhou_id,type){
        //布辊数据渲染
        var bugun_cols = [[
            {title: '合约号',sort:true, templet: repNull('heyuehao.name'), width:100, field: 'heyuehao.name'},
            {field: 'riqi',sort:true, title: '落布日期',width:120}
            ,{title: '落布班次',sort:true, templet: repNull('banci.name'),width:120, field: 'banci.name'}
            ,{title: '机台号',sort:true, templet: repNull('jitaihao.jitaihao'),width:100, field: 'jitaihao.jitaihao'}
            ,{field: 'changdu',sort:true, title: '落布长度m',width:120}
            ,{title: '落布人',sort:true, templet: repNull('luoburen.xingming'), width:80, field: 'luoburen.xingming'}
            ,{title: '左织轴号',sort:true, templet: repNull('zhiZhou_left.zhouhao'),width:120, field: 'zhiZhou_left.zhouhao'}
            ,{title: '右织轴号',sort:true, templet: repNull('zhiZhou_right.zhouhao'),width:120, field: 'zhiZhou_right.zhouhao'}
            ,{field: 'luobushijian',sort:true, title: '落布时间',width:170}
        ]];
        initTable_BugunZhuiSu('bugun_table',bugun_cols,[bugun_obj]);
        var zhibu_cols =[[
            {field: 'heyuehao',sort:true, title: '合约号',width:120}
            ,{field: 'riqi',sort:true, title: '上车登记日期',width:120}
            ,{field: 'banci',sort:true, title: '上车登记班次',width:120}
            ,{field: 'jitaihao',sort:true, title: '机台号',width:100}
            ,{field: 'zhouName',sort:true, title: '织轴号',width:100}
            ,{field: 'leixing',sort:true, title: '上车类型',width:100}
            ,{field: 'buji_shangche_time',sort:true, title: '上车时间',width:170}
            ,{field: 'xiache_time',sort:true, title: '下车时间',width:170}
            ,{field: 'ghxm',sort:true, title: '上车登记员工',width:120}
            ,{align: 'center',title: '操作',toolbar: '#gongyi_bar',width:80, fixed: 'right'}
        ]];
        var chuanzong_cols =[[
            {field: 'heyuehao',sort:true, title: '合约号',width:120}
            ,{field: 'riqi',sort:true, title: '登记日期',width:120}
            ,{field: 'banci',sort:true, title: '登记班次',width:100}
            ,{field: 'jitaihao',sort:true, title: '机台号',width:90}
            ,{field: 'zhouName',sort:true, title: '织轴号',width:90}
            ,{field: 'koufu',sort:true, title: '扣幅',width:90}
            ,{field: 'kougao',sort:true, title: '扣高',width:90}
            ,{field: 'koukuan',sort:true, title: '扣宽',width:90}
            ,{field: 'xiache_time',sort:true, title: '登记时间',width:170}
            ,{field: 'ghxm',sort:true, title: '上车登记员工',width:120}
            ,{align: 'center',title: '操作',toolbar: '#gongyi_bar',width:80, fixed: 'right'}
        ]];
        var jiangsha_cols =[[
            {field: 'heyuehao',sort:true, title: '合约号',width:120}
            ,{field: 'riqi',sort:true, title: '登记日期',width:120}
            ,{field: 'banci',sort:true, title: '登记班次',width:120}
            ,{field: 'jitaihao',sort:true, title: '机台号',width:100}
            ,{field: 'zhouName',sort:true, title: '织轴号',width:100}
            ,{field: 'changdu',sort:true, title: '织轴长度m',width:120}
            ,{field: 'ganghao',sort:true, title: '缸号',width:100}
            ,{field: 'xiache_time',sort:true, title: '登记时间',width:170}
            ,{field: 'ghxm',sort:true, title: '上车登记员工',width:120}
            ,{align: 'center',title: '操作',toolbar: '#gongyi_zhiliang_bar',width:130, fixed: 'right'}
        ]];
        var zhengjing_cols = [[
            {field: 'heyuehao',sort:true, title: '合约号',width:100}
            ,{field: 'riqi',sort:true, title: '登记日期',width:110}
            ,{field: 'banci',sort:true, title: '登记班次',width:100}
            ,{field: 'jitaihao',sort:true, title: '机台号',width:110}
            ,{field: 'zhouName',sort:true, title: '织轴号',width:90}
            ,{field: 'changdu',sort:true, title: '经轴长度m',width:120}
            ,{field: 'ganghao',sort:true, title: '缸号',width:90}
            ,{field: 'duantoushu',sort:true, title: '断头数',width:80}
            ,{field: 'xiache_time',sort:true, title: '登记时间',width:170}
            ,{field: 'ghxm',sort:true, title: '上车登记员工',width:120}
            ,{align: 'center',title: '操作',toolbar: '#gongyi_bar',width:80, fixed: 'right'}
        ]];
        var yuansha_cols = [[
            {field: 'leixing',sort:true, title: '类型',width:100}
            ,{field: 'genshu',sort:true, title: '根数',width:100}
            ,{field: 'baozhong',sort:true, title: '包重kg',width:120}
            ,{field: 'pihao',sort:true, title: '批号',width:120}
            ,{field: 'pinming',sort:true, title: '品名',width:120}
            ,{field: 'sebie',sort:true, title: '色别',width:100}
            ,{field: 'sehao',sort:true, title: '色号',width:100}
            ,{field: 'zhishu',sort:true, title: '支数',width:100}
            ,{field: 'baozhuangxingshi',sort:true, title: '包装形式',width:120}
            ,{field: 'gongyingshang',sort:true, title: '供应商',width:120}
            ,{align: 'center',title: '操作',toolbar: '#zhiliang_bar',width:80, fixed: 'right'}
        ]];

        $.ajax({
            url: layui.setter.host + 'bugun/bugunjilu/buGunZhuiSu',
            type: 'get',
            data:{
                shift_zhou_id:shift_zhou_id,
                type:type
            },
            async: false,
            success: function (data){
                var zhibu_data=[],chuanzong_data=[],zhengjing_data=[],jiangsha_data=[],yuansha_data=[];
                if(data.code==0){
                    for(var i=0; i<data.data.length;i++){
                        if(data.data[i].gongxu=="织布"){
                            zhibu_data.push(data.data[i]);
                        }else if(data.data[i].gongxu=="穿综"){
                            chuanzong_data.push(data.data[i]);
                        }else if(data.data[i].gongxu=="浆纱"){
                            jiangsha_data.push(data.data[i]);
                        }else if(data.data[i].gongxu=="整经"){
                            zhengjing_data.push(data.data[i]);
                        }else{
                            //原纱
                            yuansha_data.push(data.data[i]);
                        }
                    }

                    initTable_BugunZhuiSu('zhibu_table',zhibu_cols,zhibu_data);
                    initTable_BugunZhuiSu('chuankou_table',chuanzong_cols,chuanzong_data);
                    initTable_BugunZhuiSu('jiangsha_table',jiangsha_cols,jiangsha_data);
                    initTable_BugunZhuiSu('zhengjinig_table',zhengjing_cols,zhengjing_data);
                    initTable_BugunZhuiSu('yuansha_table',yuansha_cols,yuansha_data);

                    layer.open({
                        type: 1
                        ,title: '布辊追踪'
                        ,content: $('#zhuizong_div')
                        ,offset:'auto'
                        ,area: ['95%', '95%']
                        ,btnAlign: 'c'
                    });
                }else{
                    layer.open({      //系统异常
                        title:"消息提醒",
                        content:data.message,
                        skin:"layui-layer-molv",
                        btn:["查看错误信息"],
                        anim: -1,
                        icon:5,
                        btn1:function(index){
                            layer.open({content:data.data});
                            layer.close(index);
                        }
                    });
                }
            }
        });
    }

    //原纱质量
    table.on('tool(yuansha_table)',function(obj) {
        var data = obj.data;
        var yszl_id = data.yuansha_zhiliang_id;
        var pinming = data.pinming;
        var pihao = data.pihao;




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

    //初始化table（不带分页），无ajax请求
    function initTable_BugunZhuiSu(ele,cols,data){
        return table.render({
            elem: '#'+ele,
            id: ele,
            data: data,
            cols: cols,
            skin: 'row', //表格风格
            limit: 10000
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