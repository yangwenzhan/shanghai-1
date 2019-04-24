layui.define(['table', 'form', 'laydate', 'formSelects'], function(exports){
    var $ = layui.$
        ,table = layui.table
        ,form = layui.form
        ,laydate = layui.laydate
        ,formSelects = layui.formSelects;

    //筛选条件
    getGongXu("ssgx,jtgx", null, true);
    getJiXing("jtjx", null, true, $('#jtgx').val());
    getDict("sslb", null, true, "lunban");
    getUsername("yg", null, true, $('#ssgx').val(), $('#sslb').val());

    //查询条件下拉框监听
    form.on('select(ssgx)', function() {
        getUsername("yg", null, true, $('#ssgx').val(), $('#sslb').val());
    });
    form.on('select(sslb)', function() {
        getUsername("yg", null, true, $('#ssgx').val(), $('#sslb').val());
    });
    form.on('select(jtgx)', function() {
        getJiXing("jtjx", null, true, $('#jtgx').val());
    });

    //批量修改下拉框监听
    form.on('select(plxg_ssgx)', function() {
        getUsername("plxg_yg", null, true, $('#plxg_ssgx').val(), $('#plxg_sslb').val());
    });
    form.on('select(plxg_sslb)', function() {
        getUsername("plxg_yg", null, true, $('#plxg_ssgx').val(), $('#plxg_sslb').val());
    });

    //新增下拉框监听
    form.on('select(add_ssgx)', function() {
        getUsername("add_yg", null, true, $('#plxg_ssgx').val(), $('#plxg_sslb').val());
    });
    form.on('select(add_sslb)', function() {
        getUsername("add_yg", null, true, $('#plxg_ssgx').val(), $('#plxg_sslb').val());
    });
    form.on('select(add_gx)', function() {
        getJiXing("add_jx", null, true, $('#add_gx').val());
        getJiTaiHao("add_jth", null, false, $('#add_gx').val(), $('#add_jx').val());
    });
    form.on('select(add_jx)', function() {
        getJiTaiHao("add_jth", null, false, $('#add_gx').val(), $('#add_jx').val());
    });

    var cols = [
        {fixed:'left',checkbox:true}
        ,{field: 'rownum', width:55}
        ,{field: 'jitai_id', title: 'jitai_id',hide:true}
        ,{field: 'jitaihao', sort:true,title: '机台号',width:120}
        ,{field: '甲班',sort:true, title: '甲班',width:300}
        ,{field: '乙班',sort:true, title: '乙班',width:300}
        ,{field: '丙班',sort:true, title: '丙班',width:300}
    ];

    initTable_all("table", 'jichushezhi/chewei/findCheWei', 'get',[cols], table,"form");

    form.on('submit(form_search)',function(data){
        initTable_all("table", 'jichushezhi/chewei/findCheWei', 'get',[cols], table,"form");
        return false;
    });

    //修改
    $('#batch_edit_btn').on('click',function(){
        var cs_ids=new Array();
        var checkStatus = table.checkStatus('table');
        var data = checkStatus.data;

        if(data.length<=0){
            layer.open({
                title:"消息提醒",content:"至少选中一行!",skin:"layui-layer-molv",time:1500,anim: -1,icon:5
            });
            return false;
        }

        var jitai_str="";
        for(var i=0;i<data.length;i++){
            if(i == data.length-1){
                jitai_str += data[i].jitaihao;
            }else{
                jitai_str += data[i].jitaihao+",";
            }
        }

        $('#plxg_jth').empty();
        $('#plxg_jth').html(jitai_str);
        getGongXu("plxg_ssgx", null, true);
        getDict("plxg_sslb", null, true, "lunban");
        getDict("plxg_lb", null, false, "lunban");
        getUsername("plxg_yg", null, true, $('#plxg_ssgx').val(), $('#plxg_sslb').val());

        layer.open({
            type: 1,
            title: ['批量修改', 'font-size:12px;'],
            content: $("#div_form_batch_edit"),
            shadeClose: true, //点击遮罩关闭层
            shade: 0.8,
            offset:'auto',
            area: ['80%', '90%'],
            btn: ['确定', '取消'],
            btnAlign: 'c',
            yes: function(index_one, layero) {
                //获取员工数据，若没选员工，弹框提示
                var user_id_arr = formSelects.value('setUser', 'val');
                if(user_id_arr.length == 0){
                    verifyWindow("请选择员工!");
                    return false;
                }
                var user_list = [],user_obj=new Object();
                for(var i=0; i<user_id_arr.length;i++){
                    user_obj={id:user_id_arr[i]};
                    user_list.push(user_obj);
                }

                var chewei_list = [];
                for(var i=0; i<data.length; i++){
                    var chewei_obj=new Object();
                    chewei_obj.jitaihao = {id:data[i].jitai_id};
                    chewei_obj.lunban = {id:$('#plxg_lb').val()};
                    chewei_obj.users = user_list;
                    chewei_list.push(chewei_obj);
                }

                layer.confirm('确定修改车位信息? ',
                    {title:'提示'},function(i) {
                        $.ajax({
                            url:layui.setter.host+'jichushezhi/chewei/updCheWei',
                            type:'post',
                            contentType:"application/json;charset=utf-8",
                            data:JSON.stringify(chewei_list),
                            success:function(data){
                                ajaxSuccess(data,table);
                                layer.close(i);layer.close(index_one);
                            }
                        });
                    });
            }
        });
    });

    //新增
    $('#add_chewei_btn').on('click',function(){

        getGongXu("add_gx,add_ssgx", null, true);
        getJiXing("add_jx", null, true, $('#add_gx').val());
        getJiTaiHao("add_jth", null, false, $('#add_gx').val(), $('#add_jx').val());
        getDict("add_lb", null, false, "lunban");
        getDict("add_sslb", null, true, "lunban");
        getUsername("add_yg", null, true, $('#add_ssgx').val(), $('#add_sslb').val());

        layer.open({
            type: 1,
            title: ['新增', 'font-size:12px;'],
            content: $("#div_form_add"),
            shadeClose: true, //点击遮罩关闭层
            shade: 0.8,
            offset:'auto',
            area: ['80%', '90%'],
            btn: ['确定', '取消'],
            btnAlign: 'c',
            yes: function(index_one, layero) {
                //获取员工数据，若没选员工，弹框提示
                var user_id_arr = formSelects.value('setAddUser', 'val');
                var jitai_id_arr = formSelects.value('setAddJiTaiHao', 'val');
                if(jitai_id_arr.length == 0){
                    verifyWindow("请选择机台号!");
                    return false;
                }
                if(user_id_arr.length == 0){
                    verifyWindow("请选择员工!");
                    return false;
                }
                var user_list = [],user_obj=new Object();
                for(var i=0; i<user_id_arr.length;i++){
                    user_obj={id:user_id_arr[i]};
                    user_list.push(user_obj);
                }

                var chewei_list = [];
                for(var i=0; i<jitai_id_arr.length; i++){
                    var chewei_obj=new Object();
                    chewei_obj.jitaihao = {id:jitai_id_arr[i]};
                    chewei_obj.lunban = {id:$('#add_lb').val()};
                    chewei_obj.users = user_list;
                    chewei_list.push(chewei_obj);
                }

                layer.confirm('确定新增车位信息? ',
                    {title:'提示'},function(i) {
                        $.ajax({
                            url:layui.setter.host+'jichushezhi/chewei/updCheWei',
                            type:'post',
                            contentType:"application/json;charset=utf-8",
                            data:JSON.stringify(chewei_list),
                            success:function(data){
                                ajaxSuccess(data,table);
                                layer.close(i);layer.close(index_one);
                            }
                        });
                    });
            }
        });
    });



    exports('zhibuchewei', {})
});