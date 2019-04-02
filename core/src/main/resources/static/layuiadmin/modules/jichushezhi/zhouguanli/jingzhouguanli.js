layui.define(['table', 'form'], function(exports){
    var table = layui.table
        ,form = layui.form;

    var cols =  [
        {field: 'id', title: 'id',hide:true}
        ,{field: 'zhouhao',sort:true, title: '织轴号'}
        ,{field: 'zhoukuan',sort:true, title: '轴宽'}
        ,{field: 'beizhu',sort:true, title: '备注'}
        ,{align: 'center',title: '操作',toolbar: '#barDemo'}
    ];
    initTable("table", 'jichushuju/zhou/jingzhou/findAllJingZhou', 'get',[cols], table);

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
        }
    });

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

    exports('jingzhouguanli', {})
});