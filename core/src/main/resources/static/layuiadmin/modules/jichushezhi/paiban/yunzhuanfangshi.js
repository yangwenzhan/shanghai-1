layui.define(['table', 'form', 'laydate'], function(exports){
    var $ = layui.$
        ,table = layui.table
        ,form = layui.form
        ,laydate = layui.laydate;

    table.render({
        elem: '#table'
        ,method:'GET'
        ,limit:10000
        ,url: layui.setter.host + 'jichushezhi/paiban/yunzhuanfangshi/findAllYZFS'
        ,cols: [[
            {field: 'id', title: 'id',hide:true}
            ,{field: 'name', title: '名称'}
            ,{field: 'lunbanshu', title: '运转轮班个数'}
            ,{field: 'paibanshu', title: '运转排班个数'}
            ,{fixed: 'right',width: 200,align: 'center',title: '操作',toolbar: '#barDemo'}
        ]]
    });

    table.on('tool(table)', function(obj){
        var data = obj.data;
        if(obj.event === 'detail'){
            //查看运转方式详情
            initYunZhuanInfo(data);
            layer.open({
                type: 1,
                title: ['查看 '+data.name+' 详情', 'font-size:12px;'],
                content: $("#s_chakan"),
                shade: 0.8,
                area: ['80%', '80%'],
                btn: ['关闭'],
                btnAlign: 'c',
                yes: function(index, layero) {
                    layer.close(index);
                }
            });
        }else if(obj.event === 'edit'){
            //渲染该运转方式
            initEditYunZhuanInfo(data);
            // 修改
            layer.open({
                type: 1,
                title: ['修改  '+data.name, 'font-size:12px;'],
                content: $("#s_ttxiugai"),
                shade: 0.8,
                area: ['80%', '80%'],
                btn: ['确定', '取消'],
                btnAlign: 'c',
                yes: function(index_one, layero) {
                    layer.confirm('确定要修改该运转方式吗？ ', {
                            btn: ['确定', '取消'],
                        },
                        function(index, layero) {
                            upd_yzfsInfo(data, index_one);
                        },
                        function(index) {

                        });
                },
                btn2: function(index, layero) {
                    //按钮【按钮二】的回调
                    layer.close(index);
                }
            });
        }
    });

    function initYunZhuanInfo(data){
        var yz_data = [];
        yz_data = data.yunZhuanFangShi_xiangqingSet;
        yz_data = yz_data.sort(sortPXH);
        table.render({
            elem: '#xq_table'
            ,data:yz_data
            ,limit:10000
            ,cols: [[
                {field: 'id', title: 'id',hide:true}
                ,{field: 'sort', width:150, title: '排序号'}
                ,{title: '班次', width: 150, templet: repNull('banci.name')}
                ,{title: '轮班', width: 150, templet: repNull('lunban.name')}
                ,{title: '班次开始时间', width: 200, field: 'kaishishijian'}
                ,{title: '班次结束时间', width: 200, field: 'jieshushijian'}
            ]]
        });
    }

    function initEditYunZhuanInfo(data){
        var yz_data = [];
        yz_data = data.yunZhuanFangShi_xiangqingSet;
        yz_data = yz_data.sort(sortPXH);

        var cols = [
            {field: 'id', title: 'id',hide:true}
            ,{field: 'sort', width:150, title: '排序号'}
            ,{title: '班次', width: 150, templet: repNull('banci.name')}
            ,{title: '轮班', width: 150, templet: repNull('lunban.name')}
            ,{title: '班次开始时间', width: 200, field: 'kaishishijian'}
            ,{title: '班次结束时间', width: 200, field: 'jieshushijian'}
        ];

        cols = fixedColumn(cols);
        //展示已知数据
        table.render({
            elem: '#upd_table'
            ,data:yz_data
            ,cols: [cols]
        });

        //渲染select组件
        form.render("select");
        for(var i = 0; i < yz_data.length; i++) {
            var lb = yz_data[i].lunban.name;
            $("#" + (i + 1) + " option").each(function() {
                if($(this).text() == lb) {
                    $(this).attr("selected", true);
                }
            });
        }
        //渲染时分秒组件
        for(var i = 0; i < yz_data.length; i++) {
            var start_time = yz_data[i].kaishishijian;
            var end_time = yz_data[i].jieshushijian;
            laydate.render({
                elem: '#ks_time' + (i + 1),
                type: 'time',
                value: start_time
            });
            laydate.render({
                elem: '#js_time' + (i + 1),
                type: 'time',
                value: end_time
            });
        }

    }

    function upd_yzfsInfo(data, index){
        //点击确定按钮后，创建遮罩层，防止重复点击
        var waitloading = layer.load(0, {
            shade: [0.5, '#aaa']
        });
        //data.id 运转方式id
        var id = data.id;
        var arry = [];

        //获取tbody中所有tr,并遍历tr。获取排序号  轮班id
        var trList = $("#upd_div table tbody").children("tr");

        for(var i = 0; i < trList.length; i++) {
            //获取所有td
            var tdArr = trList.eq(i).find("td");

            var pxh = tdArr.eq(1).text();
            var lbid = tdArr.eq(3).find('div').find('select').val();
            var start_time = $('#ks_time' + (i + 1)).val();
            var end_time = $('#js_time' + (i + 1)).val();

            //将要修改的字段  yzfs_id,pxh,lbid 封装到对象中，并把对象放入arry数组
            var obj = new Object();
            /*运转详情对象*/
            obj.lunban_id = lbid;
            obj.yzfs_id = id;
            obj.sort = pxh;
            obj.kaishishijian = start_time;
            obj.jieshushijian = end_time;

            arry.push(obj);
        }

        var YunZhuanXiangQing = [], YunZhuanFangShi= new Object();
        for(var i=0;i<arry.length;i++){
            var lunban = {id:arry[i].lunban_id};
            var yunZhuanFangShi_xiangqingSet = {sort:arry[i].sort,lunban:lunban,kaishishijian:arry[i].kaishishijian,jieshushijian:arry[i].jieshushijian};
            YunZhuanXiangQing.push(yunZhuanFangShi_xiangqingSet);
        }
        YunZhuanFangShi = {id:id,yunZhuanFangShi_xiangqingSet:YunZhuanXiangQing};

        $.ajax({
            url:layui.setter.host+'jichushezhi/paiban/yunzhuanfangshi/upd_yzfs_Info',
            type:'post',
            data:JSON.stringify(YunZhuanFangShi),
            traditional:true,
            contentType:"application/json;charset=utf-8",
            success:function(data){
                ajaxSuccess(data,table);
                layer.close(waitloading);
                layer.close(index);
            }
        });
    }

    //新增运转方式
    $("#ttadd").on("click", function() {
        $('#lbmc').val('');
        $('#yzbgs').val('');
        $('#lbgs').val('');
        $("#san,#er").hide();
        $("#yi").show();

        layer.open({
            type: 1,
            title: ['增加运转方式', 'font-size:12px;'],
            content: $("#s_ttadd"),
            shade: 0.8,
            area: ['80%', '80%'],
            offset: 'auto',
            btnAlign: 'c',
            success: function(layero, index_one) {
                $('#qd_btn').click(function() {
                    layer.confirm('确定要新增该运转方式吗？ ', {
                            btn: ['确定', '取消'],
                        },
                        function(index, layero) {
                            addYzfs(index_one);
                        },
                        function(index) {

                        });
                });
                $('.cancel').click(function() {
                    layer.close(index_one);
                })
            }
        });
        $('#lbmc').focus();
    });

    //确定新增运转方式
    function addYzfs(index){
        //点击确定按钮后，创建遮罩层，防止重复点击
        var waitloading = layer.load(0, {
            shade: [0.5, '#aaa']
        });

        var obj = {};//运转方式对象

        //存储新增的运转方式
        var yzfs_name = $('#lbmc').val();
        var yzfs_bc = $('#yzbgs').val();
        var yzfs_lb = $('#lbgs').val();
        obj.name = yzfs_name;
        obj.paibanshu = yzfs_bc;
        obj.lunbanshu = yzfs_lb;

        //获取班次起始时间  数据库暂未存班次起始时间
        var yzbc_num = $('#bcqz_sj').children().length;
        var bc_arr = [];
        for(var i = 1; i <= yzbc_num; i++) {
            var bc_obj = {};
            bc_obj.bcid = $('#bc' + i).val();
            bc_obj.kssj = $('#kssj' + i).val();
            bc_obj.jssj = $('#jssj' + i).val();
            bc_arr.push(bc_obj);
        }

        //放第二步的详细排班信息     pai_table获取该table下td的轮班信息
        var lb_arr = [];
        $('#pai_table tr').children('td').find('select').each(function(i) { // 遍历 tr 的各个 td
            var lb_obj = {};
            lb_obj.pxh = i + 1;
            lb_obj.lb_id = $(this).val();
            if(i < yzbc_num) {
                lb_obj.bc_id = bc_arr[i].bcid;
                if(bc_arr[i].bcid == 1) {
                    lb_obj.dayDiff = 0;
                } else {
                    lb_obj.dayDiff = -1;
                }
                lb_obj.bc_kssj = bc_arr[i].kssj;
                lb_obj.bc_jssj = bc_arr[i].jssj;
            } else {
                while(i >= yzbc_num) {
                    i = i - yzbc_num;
                }
                lb_obj.bc_id = bc_arr[i].bcid;
                if(bc_arr[i].bcid == 1) {
                    lb_obj.dayDiff = 0;
                } else {
                    lb_obj.dayDiff = -1;
                }
                lb_obj.bc_kssj = bc_arr[i].kssj;
                lb_obj.bc_jssj = bc_arr[i].jssj;
            }
            var lunban_obj = {id:lb_obj.lb_id};
            var banci_obj = {id:lb_obj.bc_id};
            var xq_obj = {sort:lb_obj.pxh,lunban:lunban_obj,banci:banci_obj,kaishishijian:bc_arr[i].kssj,jieshushijian:bc_arr[i].jssj};
            lb_arr.push(xq_obj);
        });

        obj.yunZhuanFangShi_xiangqingSet=lb_arr;

        $.ajax({
            url:layui.setter.host+'jichushezhi/paiban/yunzhuanfangshi/add_new_yzfs',
            type:'post',
            data:JSON.stringify(obj),
            traditional:true,
            contentType:"application/json;charset=utf-8",
            success:function(data){
                ajaxSuccess(data,table);
                layer.close(waitloading);
                layer.close(index);
            }
        });

    }

    //第一步 验证输入及 存储 运转班个数 等
    $("#yzbgs").blur(function() {
        if(!localStorage.yzbgs) {
            j = 1;
            localStorage.yzbgs = $("#yzbgs").val();
        } else if($("#yzbgs").val() !== localStorage.yzbgs) {
            j = 1;
            localStorage.yzbgs = $("#yzbgs").val();
            $("#pai_table").empty();
        }
    });

    /* 验证轮班个数 */
    $("#lbgs").blur(function() {
        if(!localStorage.lbgs) {
            localStorage.lbgs = $("#lbgs").val();
        } else if($("#lbgs").val() !== localStorage.lbgs) {
            localStorage.lbgs = $("#lbgs").val();
            $("#pai_table").empty();
        }
    });
    /* 验证运转方式名称 */
    $("#lbmc").blur(function() {
        if(!localStorage.lbmc) {
            localStorage.lbmc = $("#lbmc").val();
        } else if($("#lbmc").val() !== localStorage.lbmc) {
            localStorage.lbmc = $("#lbmc").val();
            $("#pai_table").empty();
        }
    });

    function next1() {
        var yzbgs = $("#yzbgs").val();
        var lbgs = $("#lbgs").val();
        var lbmc = $("#lbmc").val();

        if(!isNaN(lbmc) || lbmc == "") {
            layer.open({
                title:"消息提醒",content:"请输入正确的名称！",skin:"layui-layer-molv",offset: 'auto',btn:[],time:3000,shade: 0,anim: -1,icon:5
            });
            $('#lbmc').focus();
            return false;
        } else if(yzbgs < 0 || yzbgs == "") {
            layer.open({
                title:"消息提醒",content:"请输入正确的运转班个数！",skin:"layui-layer-molv",offset: 'auto',btn:[],time:3000,shade: 0,anim: -1,icon:5
            });
            $('#yzbgs').focus();
            return false;
        } else if(lbgs < 0 || lbgs == "") {
            layer.open({
                title:"消息提醒",content:"请输入正确的轮班个数！",skin:"layui-layer-molv",offset: 'auto',btn:[],time:3000,shade: 0,anim: -1,icon:5
            });
            $('#lbgs').focus();
            return false;
        } else if(yzbgs > lbgs) {
            layer.open({
                title:"消息提醒",content:"运转班个数必须小于等于轮班个数！",skin:"layui-layer-molv",offset: 'auto',btn:[],time:3000,shade: 0,anim: -1,icon:5
            });
            return false;
        } else {
            localStorage.lbmc = $("#lbmc").val();
            localStorage.yzbgs = $("#yzbgs").val();
            localStorage.lbgs = $("#lbgs").val();
            j = 1;
            $('#pai_table').empty();
            render();
            $("#yi,#san").hide();
            $("#er").show();
        }
    }

    //第二步 渲染加载 table
    var j = 1;
    function render() {
        var pai_table = document.getElementById("pai_table");
        var pai_tr = document.createElement("tr");
        pai_table.append(pai_tr);
        //生成轮班select
        var td_con = "<select>" + str.html() + "</select>";
        var td = document.createElement("td");
        td.innerHTML = '第' + j + '天';
        pai_tr.append(td);
        for(var i = 0; i < localStorage.yzbgs; i++) {
            var td = document.createElement("td");
            td.innerHTML = td_con;
            pai_tr.append(td);
        }
        j++;
        layui.form.render('select');
    }
    // 回执table
    function addtr() {
        render();
    }

    function next2() {
        $("#yi,#er").hide();
        $("#san").show();
        pbgl();
    }

    function back1() {
        $("#pai_tr").empty();
        $("#san,#er").hide();
        $("#yi").show();
    }


// 第三步 根据班次个数生成班次条数
    function pbgl() {

        $('#bcqz_sj').empty();

        for(var h = 1; h <= localStorage.yzbgs; h++) {

            $('#bcqz_sj').append('<div class="layui-form-item">' +
                '<div class="layui-inline">' +
                '<label class="layui-form-label" style="width: auto;">' +
                '</label>' +
                '<div class="layui-input-inline">' +
                '<select id="bc' + h + '">' + strBC.html() +
                '</select>' +
                '</div>' +
                '</div>' +
                '<div class="layui-inline">' +
                '<label class="layui-form-label" style="width: auto;">  开始时间</label>' +
                '<div class="layui-input-inline">' +
                '<input class="layui-input" id="kssj' + h + '" placeholder="HH:mm:ss" type="text" />' +
                '</div>' +
                '</div>' +
                '<div class="layui-inline">' +
                '<label class="layui-form-label" style="width: auto;">  结束时间</label>' +
                '<div class="layui-input-inline">' +
                '<input class="layui-input" id="jssj' + h + '" placeholder="HH:mm:ss" type="text" />' +
                '</div>' +
                '</div>' +
                '</div>');
        }
        form.render('select');
        for(var i = 1; i <= localStorage.yzbgs; i++) {
            laydate.render({
                elem: '#kssj' + i,
                type: 'time',
                value: '8:30:00'
            });
            laydate.render({
                elem: '#jssj' + i,
                type: 'time',
                value: '20:30:00'
            });
        }
    }

    function back2() {
        $("#san,#yi").hide();
        $("#er").show();
    }

    //给按钮添加监听
    $('#next1').on("click",function(){
        next1();
    });
    $('#addtr').on("click",function(){
        addtr();
    });
    $('#next2').on("click",function(){
        next2();
    });
    $('#back1').on("click",function(){
        back1();
    });
    $('#back2').on("click",function(){
        back2();
    });



//通用方法--------------------------------------------------------------------------------------------------
    //运转详情修改的轮班下拉框加载
    var str = $("<select/>");
    $.ajax({
        url: layui.setter.host + 'common/findAllDictVal',
        data: {
            code: 'lunban'
        },
        type: 'get',
        success: function (data) {
            var lb_data = data.data.dicts;
            lb_data = lb_data.sort(sortId);
            for(var i = 0; i < lb_data.length; i++) {
                str.append("<option value=" + lb_data[i].id + ">" + lb_data[i].name + "</option>");
            }
        }
    });

    var strBC = $("<select/>");
    $.ajax({
        url: layui.setter.host + 'common/findAllDictVal',
        data: {
            code: 'banci'
        },
        type: 'get',
        success: function (data) {
            var bc_data = data.data.dicts;
            bc_data = bc_data.sort(sortId);
            for(var i = 0; i < bc_data.length; i++) {
                strBC.append("<option value=" + bc_data[i].id + ">" + bc_data[i].name + "</option>");
            }
        }
    });

    //根据id排序
    function sortId(a,b){
        return a.id-b.id;
    }
    //根据sort排序
    function sortPXH(a,b){
        return a.sort-b.sort;
    }

    //丰富列配置功能
    function fixedColumn(cols) {
        var reg = RegExp(/lunban/);
        for(var i = 0; i < cols.length; i++) {
            var col = cols[i];
            if(reg.test(col.templet)) {
                col.templet = "<div><select id='{{d.sort}}' lay-ignore style='width:80%;height:95%;border:#ccc 1px slide'>" + str.html() + "</select></div>";
            }
            if(col.field == 'kaishishijian') {
                col.templet = "<div class='layui-input-inline'><input class='layui-input' id='ks_time{{d.sort}}' placeholder='HH:mm:ss' type='text' /></div>";
            }
            if(col.field == 'jieshushijian') {
                col.templet = "<div class='layui-input-inline'><input class='layui-input' id='js_time{{d.sort}}' placeholder='HH:mm:ss' type='text' /></div>";
            }
        }
        return cols;
    }

    /**
     * 2019/03/22 bjw
     * 通过三目运算符处理NULL异常
     * @param name 数据取值参数
     * @returns {string} 取值内容
     */
    function repNull(name) {
        var arr = name.split('.');
        var tem = "<div>{{ ";
        var currentObj = 'd';
        var smbds = '(';
        for (var i = 0; i < arr.length; i++) {
            currentObj += '.' + arr[i];
            if (i == (arr.length - 1)) {
                smbds += currentObj + "== null) ? '' : " + currentObj;
            } else {
                smbds += currentObj + "== null || ";
            }
        }
        tem += smbds;
        tem += ' }}</div>';
        return tem;
    }

    exports('yunzhuanfangshi', {})
});