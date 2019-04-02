layui.define(['table', 'form'], function(exports){
    var table = layui.table
        ,form = layui.form;

    var cols =  [
        {field: 'id', title: 'id',hide:true}
        ,{field: 'zhouhao',sort:true, title: '织轴号'}
        ,{field: 'zhoukuan',sort:true, title: '轴宽'}
        ,{templet: repNull('jixing.name'),sort:true, title: '适用机型'}
        ,{field: 'beizhu',sort:true, title: '备注'}
        ,{align: 'center',title: '操作',toolbar: '#barDemo'}
    ];
    initTable("table", 'jichushuju/zhou/zhizhou/findAllZhiZhou', 'get',[cols], table);

    table.on('tool(table)',function(obj){
        var data = obj.data;
        var id =data.id;
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
                    if($('#zhoukuan').val()==""){
                        layer.open({
                            title:"消息提醒",
                            content:"轴款不能为空",
                            skin:"layui-layer-molv",
                            offset: 'auto',
                            time:3000,
                            btn:[],
                            shade: 0,
                            anim: -1,
                            icon:5
                        });
                        $('#zhoukuan').focus();
                        return false;
                    }
                    layer.confirm('确定要修改'+data.zhouhao+'信息吗?'
                        ,function(i){
                            form.on('submit(form_edit_submit)', function (data) {
                                var formData = data.field;
                                formData.id = id;
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
        }
    });


    exports('zhizhouguanli', {})
});