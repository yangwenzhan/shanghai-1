layui.define(['table', 'form'], function(exports){
    var table = layui.table
        ,form = layui.form;

    var cols =  [[
        {field: 'id', title: 'id',hide:true}
        ,{field: 'name', title: '方案名称'}
        ,{field: 'weizhi', title: '位置'}
        ,{field: 'yms', title: '页面个数'}
        ,{field: 'ymmc', title: '页面名称'}
        ,{align: 'center',title: '操作',toolbar: '#barDemo'}
    ]];

    initTable_all ("table", 'jichushezhi/daping/dianshifangan/findAll', 'get',cols, table);

    $('#add_dsfa_btn').on('click',function(){
        $('#name').val("");
        $('#weizhi').val("");
        layer.open({
            type: 1,
            title: ['新增展示方案', 'font-size:12px;'],
            content: $("#div_form_add"),
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
                        title:"消息提醒",content:"方案名称不能为空!",skin:"layui-layer-molv",offset: 'auto',time:3000,btn:[],shade: 0,anim: -1,icon:5
                    });
                    $('#name').focus();
                    return false;
                }
                layer.confirm('确定新增该方案?'
                    ,function(i){
                        form.on('submit(form_add_submit)', function (data) {
                            var formData = data.field;
                            $.ajax({
                                url:layui.setter.host+'jichushezhi/daping/dianshifangan/addDSFA',
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
            //调用查询所有页面名称方法
            selAllYeMian(data.ymid);
            //弹出框
            layer.open({
                type: 1,
                title: data.name + " 管理页面", //不显示标题栏
                area: ['80%', '86%'],
                offset: '20px',
                shade: 0.8,
                id: 'layuipro', //设定一个id，防止重复弹出
                btn: ['确  定', '取   消'],
                btnAlign: 'c',
                moveType: 1, //拖拽模式，0或者1
                content: $("#szym"),
                btn1: function(index_one, layero) {
                    layer.confirm('确定要设置 ' + data.name + ' 管理页面吗？ ', {
                            btn: ['确定', '取消'],
                        },
                        function(index, layero) {
                            updFangAn(data, index_one);
                        });
                }
            });
        }else if(layEvent === 'del'){
            layer.confirm('确定要删除'+data.name+'?'
                ,function(i){
                    $.ajax({
                        url:layui.setter.host+'jichushezhi/daping/dianshifangan/delDSFA',
                        type:'get',
                        data:{id:data.id},
                        success:function(data){
                            ajaxSuccess(data,table);
                            layer.close(i);
                        }
                    });
                });
        }
    });


    //查询所有页面名称ajax请求
    function selAllYeMian(ymid) {
        $.ajax({
            url: layui.setter.host+'jichushezhi/daping/zhanshiyemian/findAll',
            type: "get",
            dataType: "JSON",
            success: function(data) {
                if(data.code == 0){
                    var data = data.data;
                    $('#yemian').empty();
                    for(var i = 0; i < data.length; i++) {
                        $('#yemian').append('<input type="checkbox" id="' + data[i].id + '" title="' + data[i].name + '" lay-skin="primary"/>');
                    }
                    layui.use('form', function() {
                        var form = layui.form;
                        if(ymid != null){
                            var ymidStr = ymid.split(",");
                            for(var i = 0; i < ymidStr.length; i++) {
                                $('#yemian input').each(function() {
                                    if($(this).attr("id") == ymidStr[i]) {
                                        $(this).attr("checked", true);
                                    }
                                });
                            }
                        }
                    });
                    form.render('checkbox');
                }else{
                    layer.open({
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

    //修改方案ajax请求
    function updFangAn(data, index) {
        //点击确定按钮后，创建遮罩层，防止重复点击
        var waitloading = layer.load(0, {
            shade: [0.5, '#aaa']
        });

        var ymids = [];
        $('#yemian input').each(function() {
            if($(this).prop("checked")) {
                var id = $(this).attr("id");
                ymids.push(id);
            }
        });
        if(ymids.length == 0) {
            ymids = "";
        }
        $.ajax({
            url: layui.setter.host+'jichushezhi/daping/dianshifangan/updDSFA',
            type: "get",
            data:{
                id:data.id,
                ymids:ymids
            },
            traditional: true,
            success: function(data){
                //关闭遮罩层
                layer.closeAll('loading');

                if(data.code == 0){
                    table.reload('table');
                    layer.open({
                        title:"消息提醒",
                        content:data.message,
                        skin:"layui-layer-molv",
                        offset: 'rb',
                        time:3000,
                        btn:[],
                        shade: 0,
                        anim: -1,
                        icon:6
                    });
                    layer.close(index);
                }else{
                    layer.open({
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


    exports('dianshifangan', {})
});