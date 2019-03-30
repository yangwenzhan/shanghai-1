layui.define(['table', 'form'], function(exports){
    var table = layui.table
        ,form = layui.form;

    var cols =  [[
        {field: 'id', title: 'id',hide:true}
        ,{field: 'name', title: '名称'}
        ,{field:'neirong', title: '内容'}
        ,{align: 'center',title: '操作',toolbar: '#barDemo'}
    ]]

    initTable_all ("table", 'jichushezhi/daping/tongzhigonggao/findAll', 'get',cols, table);

    table.on('tool(table)',function(obj){
        var data = obj.data;
        if(obj.event === 'edit'){

        }
    });

    $('#add_btn').on('click',function(){
        $('#name').val("");
        layer.open({
            type: 1,
            title: ['新增通知公告', 'font-size:12px;'],
            content: $("#add_tck"),
            shadeClose: true, //点击遮罩关闭层
            shade: 0.8,
            offset:'auto',
            area: ['50%', '50%'],
            btn: ['确定', '取消'],
            btnAlign: 'c',
            yes: function(index, layero) {
                //数据验证
                if($('#name').val()==""){
                    layer.open({
                        title:"消息提醒",content:"通知名称不能为空!",skin:"layui-layer-molv",offset: 'auto',time:3000,btn:[],shade: 0,anim: -1,icon:5
                    });
                    $('#name').focus();
                    return false;
                }
                layer.confirm('确定新增该通知公告?'
                    ,function(i){
                        form.on('submit(form_add_submit)', function (data) {
                            var formData = data.field;
                            $.ajax({
                                url:layui.setter.host+'jichushezhi/daping/tongzhigonggao/addTZGG',
                                type:'post',
                                contentType:"application/json;charset=utf-8",
                                data:JSON.stringify(formData),
                                success:function(data){
                                    ajaxSuccess(data,table);
                                    if(data.code==666){
                                        layer.close(i);
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
        if(layEvent === 'edit'){
            layer.open({
                success:function(){
                    setTimeout(function(){
                        var text=data.neirong;
                        window.editor.html(text);
                    }, 200)
                    setTimeout(function(){
                        window.editor.fullscreen(false);
                    }, 100)
                },
                type: 1,
                title: "修改 "+data.name, //不显示标题栏
                area: ['90%', '85%'],
                shade: 0.8,
                id: 'layuipro4', //设定一个id，防止重复弹出
                btn: ['确  定', '取   消'],
                btnAlign: 'c',
                moveType: 1, //拖拽模式，0或者1
                content: $("#upd_mp"),
                btn1: function(index, layero) {
                    layer.confirm('确定要修改通知内容?'
                        ,function(i){
                            $.ajax({
                                url:layui.setter.host+'jichushezhi/daping/tongzhigonggao/updTZGG',
                                type:'post',
                                data:{
                                    id:data.id,
                                    neirong:window.editor.html()
                                },
                                success:function(data){
                                    ajaxSuccess(data,table);
                                    if(data.code==666){
                                        layer.close(i);
                                    }else{
                                        layer.close(i);layer.close(index);
                                    }
                                }
                            });
                        });
                },btn2: function(index, layero) {
                    //按钮【按钮二】的回调

                }
            });
        }
    });


    exports('tongzhigonggao', {})
});