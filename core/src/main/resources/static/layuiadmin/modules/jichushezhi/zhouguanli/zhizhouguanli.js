layui.define(['table', 'form'], function(exports){
    var table = layui.table
        ,form = layui.form;

    var cols =  [
        {field: 'id', title: 'id',hide:true}
        ,{field: 'zhouhao',sort:true, title: '织轴号'}
        ,{field: 'zhoukuan',sort:true, title: '轴宽'}
        ,{templet: repNull('jixing.name'),sort:true, title: '适用机型',field:'jixing.name'}
        ,{field: 'beizhu',sort:true, title: '备注'}
        ,{align: 'center',title: '操作',toolbar: '#barDemo'}
    ];
    initTable("table", 'jichushuju/zhou/zhizhou/findAllZhiZhou', 'get',[cols], table);

    //织机机型
    function initJX(elemId,selectedId,isAll){
        $.ajax({
            url: layui.setter.host + 'common/findZhiJiJiXing',
            type: 'get',
            success: function (data) {
                initDownList(data, elemId,selectedId, 'name', 'id', isAll);
                form.render();
            }
        });
    }


    //新增
    $("#add_btn").on("click", function() {
        initJX("add_jixing",null,true);
        $('#add_zhouhao').val("");
        $('#add_zhoukuan').val("");
        $('#add_beizhu').val("");
        layer.open({
            type: 1,
            title: ['添加织轴', 'font-size:12px;'],
            content: $("#add_form_div"),
            shadeClose: true, //点击遮罩关闭层
            shade: 0.8,
            area: ['70%', '70%'],
            btn: ['确定', '取消'],
            btnAlign: 'c',
            yes: function(index, layero) {
                if($('#add_zhouhao').val()==""){
                    layer.open({
                        title:"消息提醒",content:"织轴号不能为空",skin:"layui-layer-molv",offset: 'auto',time:1500,btn:[],shade: 0,anim: -1,icon:5
                    });
                    $('#add_zhouhao').focus();
                    return false;
                }
                if($('#add_zhoukuan').val()==""){
                    layer.open({
                        title:"消息提醒",content:"轴宽不能为空",skin:"layui-layer-molv",offset: 'auto',time:1500,btn:[],shade: 0,anim: -1,icon:5
                    });
                    $('#add_zhoukuan').focus();
                    return false;
                }

                layer.confirm('确定新增经轴?'
                    ,function(i){
                        form.on('submit(form_add_submit)', function (data) {
                            var formData = data.field;
                            if(!formData.jixing ){
                                delete formData.jixing;
                            }else{
                                formData.jixing={id:formData.jixing}
                            }
                            $.ajax({
                                url:layui.setter.host+'jichushuju/zhou/zhizhou/addZhiZhou',
                                type:'post',
                                contentType:"application/json;charset=utf-8",
                                data:JSON.stringify(formData),
                                success:function(data){
                                    ajaxSuccess(data,table);
                                    if(data.code==666){
                                        layer.open({
                                            title:"消息提醒",content:data.message,skin:"layui-layer-molv",offset: 'auto',time:1500,btn:[],shade: 0,anim: -1,icon:5
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
        var id =data.id;

        if(obj.event === 'edit'){
            initJX("jixing",data.jixing==null?null:data.jixing.id,true);
            layer.open({
                type: 1
                ,title: '编辑 '+data.zhouhao
                ,content: $('#edit_form_div')
                ,offset:'auto'
                ,area: ['80%', '60%']
                ,btn: ['修改', '取消']
                ,btnAlign: 'c'
                ,btn1: function(index, layero) {
                    if($('#zhoukuan').val()==""){
                        layer.open({
                            title:"消息提醒",content:"轴宽不能为空",skin:"layui-layer-molv",offset: 'auto',time:3000,btn:[],shade: 0,anim: -1,icon:5
                        });
                        $('#zhoukuan').focus();
                        return false;
                    }
                    layer.confirm('确定要修改'+data.zhouhao+'信息吗?'
                        ,function(i){
                            form.on('submit(form_edit_submit)', function (data) {
                                var formData = data.field;
                                if(!formData.jixing ){
                                    delete formData.jixing;
                                }else{
                                    formData.jixing={id:formData.jixing}
                                }
                                $.ajax({
                                    url:layui.setter.host+'jichushuju/zhou/zhizhou/updateZhiZhou',
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
                        url:layui.setter.host+'jichushuju/zhou/zhizhou/deleteZhiZhou',
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


    exports('zhizhouguanli', {})
});