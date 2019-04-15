layui.define(['table', 'form'], function(exports){
    var table = layui.table
        ,form = layui.form;

    var cols =  [[
        {field: 'id', title: 'id',hide:true}
        ,{field: 'name', title: '页面名称',width:200}
        ,{field: 'url', title: 'URL',width:400}
        ,{field: 'tingliushichang', title: '停留时长(秒)',width:200}
        ,{field: 'sort', title: '排序',width:200}
        ,{align: 'center',title: '操作',toolbar: '#barDemo'}
    ]]

    initTable_all ("table", 'jichushezhi/daping/zhanshiyemian/findAll', 'get',cols, table);

    $('#add_dpym_btn').on('click',function(){
        $('#new_px').val("");
        $('#new_ymmc').val("");
        $('#new_ymlj').val("");
        $('#new_ymtlsc').val("");
        layer.open({
            type: 1,
            title: ['新增展示页面', 'font-size:12px;'],
            content: $("#div_form_add"),
            shadeClose: true, //点击遮罩关闭层
            shade: 0.8,
            offset:'auto',
            area: ['40%', '80%'],
            btn: ['确定', '取消'],
            btnAlign: 'c',
            yes: function(index, layero) {
                //数据验证
                if($('#new_ymmc').val()==""){
                    layer.open({
                        title:"消息提醒",content:"页面名称不能为空!",skin:"layui-layer-molv",offset: 'auto',time:3000,btn:[],shade: 0,anim: -1,icon:5
                    });
                    $('#new_ymmc').focus();
                    return false;
                }
                if($('#new_ymlj').val()==""){
                    layer.open({
                        title:"消息提醒",content:"页面路径不能为空!",skin:"layui-layer-molv",offset: 'auto',time:3000,btn:[],shade: 0,anim: -1,icon:5
                    });
                    $('#new_ymlj').focus();
                    return false;
                }
                if($('#new_ymtlsc').val()==""){
                    layer.open({
                        title:"消息提醒",content:"停留时长不能为空!",skin:"layui-layer-molv",offset: 'auto',time:3000,btn:[],shade: 0,anim: -1,icon:5
                    });
                    $('#new_ymtlsc').focus();
                    return false;
                }
                if($('#new_px').val()==""){
                    layer.open({
                        title:"消息提醒",content:"排序不能为空!",skin:"layui-layer-molv",offset: 'auto',time:3000,btn:[],shade: 0,anim: -1,icon:5
                    });
                    $('#new_px').focus();
                    return false;
                }

                layer.confirm('确定新增该展示页面?'
                    ,function(i){
                        form.on('submit(form_add_submit)', function (data) {
                            var formData = data.field;
                            $.ajax({
                                url:layui.setter.host+'jichushezhi/daping/zhanshiyemian/addZSYM',
                                type:'post',
                                contentType:"application/json;charset=utf-8",
                                data:JSON.stringify(formData),
                                success:function(data){
                                    ajaxSuccess(data,table);
                                    if(data.code==666){
                                        layer.open({
                                            title:"消息提醒",content:data.message,skin:"layui-layer-molv",offset: 'auto',time:3000,btn:[],shade: 0,anim: -1,icon:5
                                        });
                                    }else{
                                        layer.close(i);layer.close(index);
                                    }
                                }
                            });
                        });
                        $("#form_add_submit").trigger('click');
                    });
            }
        });
    });

    table.on('tool(table)',function(obj){
        var data = obj.data,layEvent = obj.event;
        var id = data.id;
        if(layEvent === 'edit'){
            layer.open({
                success:function(){
                    //渲染表格数据
                    form.val('form_edit',data);
                },
                type: 1,
                title: "修改 "+data.name, //不显示标题栏
                area: ['40%', '80%'],
                shade: 0.8,
                id: 'layuipro4', //设定一个id，防止重复弹出
                btn: ['确  定', '取   消'],
                btnAlign: 'c',
                moveType: 1, //拖拽模式，0或者1
                content: $("#div_form_edit"),
                btn1: function(index, layero) {
                    //数据验证
                    if($('#yztlsc').val()==""){
                        layer.open({
                            title:"消息提醒",content:"停留时长不能为空!",skin:"layui-layer-molv",offset: 'auto',time:3000,btn:[],shade: 0,anim: -1,icon:5
                        });
                        $('#yztlsc').focus();
                        return false;
                    }
                    if($('#xg_px').val()==""){
                        layer.open({
                            title:"消息提醒",content:"排序不能为空!",skin:"layui-layer-molv",offset: 'auto',time:3000,btn:[],shade: 0,anim: -1,icon:5
                        });
                        $('#xg_px').focus();
                        return false;
                    }

                    layer.confirm('确定要修改页面么?'
                        ,function(i){

                            form.on('submit(form_edit_submit)', function (data) {
                                var formData = data.field;
                                formData.id = id;
                                $.ajax({
                                    url:layui.setter.host+'jichushezhi/daping/zhanshiyemian/updZSYM',
                                    type:'post',
                                    contentType:"application/json;charset=utf-8",
                                    data:JSON.stringify(formData),
                                    success:function(data){
                                        ajaxSuccess(data,table);
                                        if(data.code==666){
                                            layer.open({
                                                title:"消息提醒",content:data.message,skin:"layui-layer-molv",offset: 'auto',time:3000,btn:[],shade: 0,anim: -1,icon:5
                                            });
                                        }else{
                                            layer.close(i);layer.close(index);
                                        }
                                    }
                                });

                            });

                            $("#form_edit_submit").trigger('click');
                        });
                }
            });
        }else if(layEvent === 'del'){
            var zsym={id:data.id};
            layer.confirm('确定要删除'+data.name+'?'
                ,function(i){
                    $.ajax({
                        url:layui.setter.host+'jichushezhi/daping/zhanshiyemian/delZSYM',
                        type:'post',
                        contentType:"application/json;charset=utf-8",
                        data:JSON.stringify(zsym),
                        success:function(data){
                            ajaxSuccess(data,table);
                            layer.close(i);
                        }
                    });
                });
        }
    });


    exports('zhanshiyemian', {})
});