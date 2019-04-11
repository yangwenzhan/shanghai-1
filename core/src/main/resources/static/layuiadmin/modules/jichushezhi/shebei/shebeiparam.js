layui.define(['table', 'form'], function(exports){
    var table = layui.table
        ,form = layui.form;

    /*待完成：下拉框未初始化值 工序机型参数类别联动*/
    initGX("gongxu",null,false);
    initJX("jixing",null,false);
    initLB("leiBie",null,true);
    function initGX(elemId,selectedId,isAll) {
        $.ajax({
            url: layui.setter.host + 'common/findAllGX',
            type: 'get',
            async:false,
            success: function (data) {
                initDownList(data, elemId,selectedId, 'name', 'id', isAll);
                form.render();
            }
        });
    }
    function initJX(elemId,selectedId,isAll){
        $.ajax({
            url: layui.setter.host + 'common/findAllJX',
            type: 'get',
            async:false,
            data:{
                gongxu:$('#gongxu').val()
            },
            success: function (data) {
                initDownList(data, elemId,selectedId, 'name', 'id', isAll);
                form.render();
            }
        });
    }
    function initLB(elemId,selectedId,isAll){
        $.ajax({
            url: layui.setter.host + 'common/findAllCSLB',
            type: 'get',
            async:false,
            data:{
                gongxu:$('#gongxu').val(),
                jixing:$('#jixing').val()
            },
            success: function (data) {
                initDownList(data, elemId,selectedId, 'name', 'id', isAll);
                form.render();
            }
        });
    }
    form.on('select(gongxu)', function(data) {
        initJX("jixing",null,false);
        initLB("leiBie",null,true)
    });
    form.on('select(jixing)', function(data) {
        initLB("leiBie",null,true)
    });

    form.on('submit(form_search)',function(data){
        var field = getParams("form");
        table.reload('table',{where:field});
        return false;
    });

    var cols =  [
        {fixed:'left',checkbox:true}
        ,{field: 'id', title: 'id',hide:true}
        ,{field: 'rownum', title: '',width:50}
        ,{field: 'name',sort:true, title: '参数类别'}
        ,{field: 'csm',sort:true, title: '参数名'}
        ,{field: 'danwei',sort:true, title: '参数单位'}
        ,{field: 'cunchuzhouqi',sort:true, title: '存储周期(秒)'}
        ,{field: 'cunchushichang',sort:true, title: '存储时长(时)'}
        ,{field: 'baojing_flag',sort:true, title: '是否报警'}
        ,{field: 'zhanshi_flag',sort:true, title: '是否报警'}
        ,{field: 'cunchu_flag',sort:true, title: '是否报警'}
        ,{field: 'xuhao',sort:true, title: '排序号'}
        ,{align: 'center',title: '操作',toolbar: '#barDemo'}
    ];
    cols = fixedColumn(cols);

    //根据form获取参数
    initTable_all("table", 'jichushuju/shebei/shebeiparam/findAll', 'get',[cols], table, "form");

    table.on('tool(table)',function(obj){
        var data = obj.data;
        var id = data.id;
        if(obj.event === 'edit'){
            initLB("dh_xgcslb",data.cslb_id,false);
            layer.open({
                type: 1
                ,title: '编辑 '+data.csm
                ,content: $('#edit_form_div')
                ,offset:'auto'
                ,area: ['80%', '60%']
                ,btn: ['修改', '取消']
                ,btnAlign: 'c'
                ,btn1: function(index, layero) {
                    if($('#dh_xgcsm').val()==""){
                        layer.open({
                            title:"消息提醒",content:"参数名不能为空",skin:"layui-layer-molv",offset: 'auto',time:3000,btn:[],shade: 0,anim: -1,icon:5
                        });
                        $('#dh_xgcsm').focus();
                        return false;
                    }
                    layer.confirm('确定要修改参数信息吗?'
                        ,function(i){
                            form.on('submit(form_edit_submit)', function (data) {
                                var formData = data.field;
                                var gongxu = {id:$('#gongxu').val()};
                                var jixing = {id:$('#jixing').val()};
                                var leibie = {id:$('#dh_xgcslb').val(),gongxu:gongxu,jixing:jixing}
                                formData.leiBie=leibie;
                                formData.name = formData.csm;
                                formData.id = id;
                                $.ajax({
                                    url:layui.setter.host+'jichushuju/shebei/shebeiparam/updSheBeiParam',
                                    type:'post',
                                    contentType:"application/json;charset=utf-8",
                                    data:JSON.stringify(formData),
                                    success:function(data){
                                        if(data.code==666){
                                            layer.open({
                                                title:"消息提醒",content:data.message,skin:"layui-layer-molv",offset: 'auto',time:3000,btn:[],shade: 0,anim: -1,icon:5
                                            });
                                        }else{
                                            ajaxSuccess(data,table);
                                            layer.close(i);layer.close(index);
                                        }
                                    }
                                });
                            });

                            $("#form_edit_submit").trigger('click');
                        });
                },
                success:function(){
                    //渲染表格数据
                    form.val('form_edit',data);
                },
            });
        }
    });

    //监听是否报警
    form.on('switch(switchSfbj)', function(data) {
        var id = (data.elem).id;
        //当前switch对应的value值
        var sfbj = this.checked ? "1" : "0";
        //更新switch对应的value
        (data.elem).value = sfbj;
        var sqlName = "baojing_flag";
        //调用更新字段方法
        upd_data(id, sfbj, sqlName);
    });

    //监听是否显示
    form.on('switch(switchSfxx)', function(data) {
        var id = (data.elem).id;
        //当前switch对应的value值
        var sfxs = this.checked ? "1" : "0";
        //更新switch对应的value
        (data.elem).value = sfxs;
        var sqlName = "zhanshi_flag";
        //调用更新字段方法
        upd_data(id, sfxs, sqlName);
    });

    //监听是否记录历史曲线
    form.on('switch(switchSfjllsqx)', function(data) {
        var id = (data.elem).id;
        //当前switch对应的value值
        var sfjllsqx = this.checked ? "1" : "0";
        //更新switch对应的value
        (data.elem).value = sfjllsqx;
        var sqlName = "cunchu_flag";
        //调用更新字段方法
        upd_data(id, sfjllsqx, sqlName);
    });

    //修改是否报警---是否显示---是否记录历史曲线
    function upd_data(id, updData, sqlName) {
        $.ajax({
            url:layui.setter.host+'jichushuju/shebei/shebeiparam/updSheBeiParam_flag',
            type:'get',
            data:{
                id:id,
                name:sqlName,
                val:updData
            },
            success:function(data){
                if(data.code==0){
                    layer.open({
                        title:"消息提醒",content:data.message,skin:"layui-layer-molv",offset: 'rb',time:3000,btn:[],shade: 0,anim: -1,icon:6
                    });
                }else{
                    layer.open({
                        title:"消息提醒",content:data.message,skin:"layui-layer-molv",btn:["查看错误信息"],anim: -1,icon:5,
                        btn1:function(index){
                            layer.open({content:data.data})
                            layer.close(index);
                        }
                    });
                }
            }
        });
    }

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

        var str = "";
        for(var i=0;i<data.length;i++){
            if(i == data.length-1){
                str += data[i].id;
            }else{
                str += data[i].id+",";
            }
        }

        initLB("pl_xgcslb",data.cslb_id,false);

        layer.open({
            type: 1,
            title: ['批量修改', 'font-size:12px;'],
            content: $("#batch_edit_form_div"),
            shadeClose: true, //点击遮罩关闭层
            shade: 0.8,
            offset:'auto',
            area: ['60%', '90%'],
            btn: ['确定', '取消'],
            btnAlign: 'c',
            yes: function(index, layero) {
                var sfbj = "",
                    sfzs = "",
                    sfjlqx = "",
                    dw = "",
                    cslb_id = "",
                    ccsc = "",
                    cczq = "";
                if(!$('#xgplnr_cslb').prop('checked') && !$('#xgplnr_ccsc').prop('checked') && !$('#xgplnr_sfbj').prop('checked') && !$('#xgplnr_sfxs').prop('checked') && !$('#xgplnr_sfjlls').prop('checked') && !$('#xgplnr_dw').prop('checked')) {
                    layer.alert("请先勾选您要修改的项，或者点击取消按钮!");
                    return false;
                }
                if($('#xgplnr_cslb').prop('checked')) {
                    cslb_id = $("#pl_xgcslb").val();
                } else {
                    cslb_id = "";
                }
                if($('#xgplnr_ccsc').prop('checked')) {
                    ccsc = $("#pl_xgsc").val();
                } else {
                    ccsc = "";
                }
                if($('#xgplnr_cczq').prop('checked')) {
                    cczq = $("#pl_xgzq").val();
                } else {
                    cczq = "";
                }
                if($('#xgplnr_dw').prop('checked')) {
                    dw = $("#pl_xgdw").val();
                } else {
                    dw = "";
                }
                if($('#xgplnr_sfbj').prop('checked')) {
                    sfbj = $("#pl_xgsfbj").val();
                } else {
                    sfbj = "";
                }
                if($('#xgplnr_sfxs').prop('checked')) {
                    sfzs = $("#pl_xgsfxs").val();
                } else {
                    sfzs = "";
                }
                if($('#xgplnr_sfjlls').prop('checked')) {
                    sfjlqx = $("#pl_xgsfjlls").val();
                } else {
                    sfjlqx = "";
                }
                layer.confirm('确定修改参数信息? ',
                    {title:'提示'},function(i) {
                        $.ajax({
                            url:layui.setter.host+'jichushuju/shebei/shebeiparam/updSheBeiParam_Batch',
                            type:'post',
                            data:{
                                "cslb_id": cslb_id,
                                "ccsc": ccsc,
                                "cczq": cczq,
                                "idStr": str,
                                "sfbj": sfbj,
                                "sfzs": sfzs,
                                "sfjlqx": sfjlqx,
                                "dw": dw
                            },
                            success:function(data){
                                table.reload('table');
                                layer.close(i);layer.close(index);
                                if(data.code==0){
                                    if(data.data[0].boo==1){
                                        //成功
                                        layer.open({
                                            title:"消息提醒",content:"修改成功",skin:"layui-layer-molv",offset: 'rb',time:3000,btn:[],shade: 0,anim: -1,icon:6
                                        });
                                    }else{
                                        //失败
                                        layer.open({
                                            title:"消息提醒",content:data.message,skin:"layui-layer-molv",btn:["查看错误信息"],anim: -1,icon:5,
                                            btn1:function(index){
                                                layer.open({content:data.data});
                                                layer.close(index);
                                            }
                                        });
                                    }
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
                    });
            }
        });
    });

    //丰富列配置功能
    function fixedColumn(cols) {
        for(var i = 0; i < cols.length; i++) {
            var col = cols[i];

            if(col.field == 'baojing_flag') {
                col.templet = "#mysfbj";
            }
            if(col.field == 'zhanshi_flag') {
                col.templet = "#mysfxx";
            }
            if(col.field == 'cunchu_flag') {
                col.templet = "#mysfjllsqx";
            }
        }
        return cols;
    }

    exports('shebeiparam', {})
});