layui.define(['table', 'form'], function(exports){
    var table = layui.table
        ,form = layui.form;

    var cols =  [
        {field: 'id', title: 'id',hide:true}
        ,{field: 'zhouhao',sort:true, title: '织轴号'}
        // ,{field: 'zhoukuan',sort:true, title: '轴宽'}
        ,{field: 'beizhu',sort:true, title: '备注'}
        ,{align: 'center',title: '操作',toolbar: '#barDemo'}
    ];
    initTable("table", 'jichushuju/zhou/jingzhou/findAllJingZhou', 'get',[cols], table);

    //新增
    $("#add_btn").on("click", function() {
        $('#add_zhouhao').val("");
        // $('#add_zhoukuan').val("");
        $('#add_beizhu').val("");
        layer.open({
            type: 1,
            title: ['添加经轴', 'font-size:12px;'],
            content: $("#add_form_div"),
            shadeClose: true, //点击遮罩关闭层
            shade: 0.8,
            area: ['70%', '70%'],
            btn: ['确定', '取消'],
            btnAlign: 'c',
            yes: function(index, layero) {
                if($('#add_zhouhao').val()==""){
                    layer.open({
                        title:"消息提醒",content:"经轴号不能为空",skin:"layui-layer-molv",offset: 'auto',time:1500,btn:[],shade: 0,anim: -1,icon:5
                    });
                    $('#add_zhouhao').focus();
                    return false;
                }
                /*if($('#add_zhoukuan').val()==""){
                    layer.open({
                        title:"消息提醒",content:"轴宽不能为空",skin:"layui-layer-molv",offset: 'auto',time:3000,btn:[],shade: 0,anim: -1,icon:5
                    });
                    $('#add_zhoukuan').focus();
                    return false;
                }*/

                layer.confirm('确定新增经轴?'
                    ,function(i){
                        form.on('submit(form_add_submit)', function (data) {
                            var formData = data.field;
                            $.ajax({
                                url:layui.setter.host+'jichushuju/zhou/jingzhou/addJingZhou',
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
        var data = obj.data;
        var id = data.id;
        if(obj.event === 'edit'){
            layer.open({
                type: 1
                ,title: '编辑 '+data.zhouhao
                ,content: $('#edit_form_div')
                ,offset:'auto'
                ,area: ['80%', '60%']
                ,btn: ['修改', '取消']
                ,btnAlign: 'c'
                ,btn1: function(index, layero) {
                    /*if($('#zhoukuan').val()==""){
                        layer.open({
                            title:"消息提醒",content:"轴宽不能为空",skin:"layui-layer-molv",offset: 'auto',time:1500,btn:[],shade: 0,anim: -1,icon:5
                        });
                        $('#zhoukuan').focus();
                        return false;
                    }*/

                    layer.confirm('确定要修改'+data.zhouhao+'信息吗?'
                        ,function(i){
                            form.on('submit(form_edit_submit)', function (data) {
                                var formData = data.field;
                                formData.id=id;
                                $.ajax({
                                    url:layui.setter.host+'jichushuju/zhou/jingzhou/updateJingZhou',
                                    type:'post',
                                    contentType:"application/json;charset=utf-8",
                                    data:JSON.stringify(formData),
                                    success:function(data){
                                        ajaxSuccess(data,table);
                                        layer.close(i);layer.close(index);
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
        }else if(obj.event === 'del'){
            var obj = {id:id};
            layer.confirm('确定要删除'+data.zhouhao+'信息吗?'
                ,function(i){
                    $.ajax({
                        url:layui.setter.host+'jichushuju/zhou/jingzhou/deleteJingZhou',
                        type:'post',
                        contentType:"application/json;charset=utf-8",
                        data:JSON.stringify(obj),
                        success:function(data){
                            ajaxSuccess(data,table);
                            layer.close(i);
                        }
                    });
                });
        }
    });


    exports('jingzhouguanli', {})
});