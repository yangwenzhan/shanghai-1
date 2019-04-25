layui.define(['table', 'form'], function(exports){
    var table = layui.table
        ,form = layui.form;

    /*待完成：下拉框未初始化值，工序机型联动，新增修改参数类别已存在的情况测试*/
    initGX("gongxu",null,true);
    initJX("jixing",null,true);
    function initGX(elemId,selectedId,isAll) {
        $.ajax({
            url: layui.setter.host + 'common/findAllGX',
            type: 'get',
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
            data:{
                gongxu:$('#gongxu').val()
            },
            success: function (data) {
                initDownList(data, elemId,selectedId, 'name', 'id', isAll);
                form.render();
            }
        });
    }
    form.on('select(gongxu)', function(data) {
        initJX("jixing",null,true);
    });

    var cols =  [[
        {field: 'id', title: 'id',hide:true}
        ,{title: '工序',sort:true, templet: repNull('gongxu.name')}
        ,{title: '机型',sort:true, templet: repNull('jixing.name')}
        ,{field: 'name',sort:true, title: '参数类别名'}
        ,{field: 'xuhao',sort:true, title: '排序号'}
        ,{align: 'center',title: '操作',toolbar: '#barDemo'}
    ]]

    initTable("table", 'jichushuju/shebei/shebeiparamleibie/findAll', 'get',cols, table, "form");

    table.on('tool(table)',function(obj){
        var data = obj.data;
        var id = data.id;
        if(obj.event === 'edit'){

            initGX("edit_gongxu",data.gongxu.id,false);
            initJX("edit_jixing",data.jixing.id,false);
            form.on('select(edit_gongxu)', function(data) {
                initJX("edit_jixing",null,false);
            });

            layer.open({
                type: 1
                ,title: '编辑参数类别'
                ,content: $('#edit_form_div')
                ,offset:'auto'
                ,area: ['40%', '80%']
                ,btn: ['修改', '取消']
                ,btnAlign: 'c'
                ,btn1: function(index, layero){
                    if($('#edit_name').val()==""){
                        layer.open({
                            title:"消息提醒",content:"参数类别名称不能为空",skin:"layui-layer-molv",offset: 'auto',time:3000,btn:[],shade: 0,anim: -1,icon:5
                        });
                        $('#edit_name').focus();
                        return false;
                    }
                    layer.confirm('确定要修改参数类别信息吗?'
                        ,function(i){
                            form.on('submit(form_edit_submit)', function (data) {
                                var formData = data.field;
                                var gongxu = {id:$('#edit_gongxu').val()};
                                var jixing = {id:$('#edit_jixing').val()};
                                formData.gongxu=gongxu;
                                formData.jixing=jixing;
                                formData.id = id;
                                $.ajax({
                                    url:layui.setter.host+'jichushuju/shebei/shebeiparamleibie/updParamLeiBie',
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
                }
                ,success: function(){
                    //渲染表格数据
                    form.val('form_edit',data);
                }
            });
        }
    });

    $('#add_btn').on('click',function(){
        $('#add_name').val("");
        initGX("add_gongxu",null,false);
        initJX("add_jixing",null,false);
        form.on('select(add_gongxu)', function(data) {
            initJX("add_jixing",null,false);
        });

        layer.open({
            type: 1,
            title: ['新增参数类别', 'font-size:12px;'],
            content: $("#add_form_div"),
            shadeClose: true, //点击遮罩关闭层
            shade: 0.8,
            offset:'auto',
            area: ['40%', '80%'],
            btn: ['确定', '取消'],
            btnAlign: 'c',
            yes: function(index, layero) {
                //数据验证
                if($('#add_name').val()==""){
                    layer.open({
                        title:"消息提醒",content:"参数类别名称不能为空!",skin:"layui-layer-molv",offset: 'auto',time:3000,btn:[],shade: 0,anim: -1,icon:5
                    });
                    $('#add_name').focus();
                    return false;
                }
                layer.confirm('确定新增参数类别?'
                    ,function(i){
                        form.on('submit(form_add_submit)', function (data) {
                            var formData = data.field;
                            var gongxu = {id:$('#add_gongxu').val()};
                            var jixing = {id:$('#add_jixing').val()};
                            formData.gongxu=gongxu;
                            formData.jixing=jixing;
                            $.ajax({
                                url:layui.setter.host+'jichushuju/shebei/shebeiparamleibie/addParamLeibie',
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
                        $("#form_add_submit").trigger('click');
                    });
            }
        });
    });

    exports('shebeiparamtype', {})
});