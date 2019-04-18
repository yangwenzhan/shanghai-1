layui.define(['table', 'form'], function(exports){
    var table = layui.table
        ,form = layui.form;

    initGX();
    initJX();
    function initGX() {
        $.ajax({
            url: layui.setter.host + 'common/findAllGX',
            type: 'get',
            success: function (data) {
                initDownList(data, "gongxu", null, 'name', 'id', true);
                form.render();
            }
        });
    }
    function initJX(){
        $.ajax({
            url: layui.setter.host + 'common/findAllJX',
            type: 'get',
            data:{
                gongxu:$('#gongxu').val()
            },
            success: function (data) {
                initDownList(data, 'jixing', null, 'name', 'id', true);
                form.render();
            }
        });
    }

    var cols =  [
        {field: 'id', title: 'id',hide:true}
        ,{field: 'rownum', title: '',width:55}
        ,{field: 'gongxu',sort:true, title: '工序'}
        ,{field: 'jixing',sort:true, title: '机型'}
        ,{field: 'jitaihao',sort:true, title: '机台号'}
        ,{field: 'jihuatingtai',sort:true, title: '计划停台'}
        ,{field: 'ip',sort:true, title: 'IP地址'}
        ,{field: 'port',sort:true, title: '端口号'}
        ,{field: 'zhizaoshang',sort:true, title: '制造商'}
        ,{align: 'center',title: '操作',toolbar: '#barDemo'}
    ];
    cols = fixedColumn(cols);

    initTable_all("table", 'jichushuju/shebei/shebei/findAllSheBei', 'get',[cols], table,"form");

    form.on('select(gongxu)', function(data) {
        initJX();
    });

    table.on('tool(table)',function(obj){
        var data = obj.data;
        if(obj.event === 'edit'){
            layer.open({
                type: 1
                ,title: '编辑 '+data.jitaihao
                ,content: $('#edit_form_div')
                ,offset:'auto'
                ,area: ['80%', '60%']
                ,btn: ['修改', '取消']
                ,btnAlign: 'c'
                ,btn1: function(index_one, layero) {
                    layer.confirm('确定要修改'+data.jitaihao+'信息吗?'
                        ,function(i){
                            form.on('submit(form_edit_submit)', function (data2) {
                                $.ajax({
                                    url:layui.setter.host+'jichushuju/shebei/shebei/updateInfo',
                                    type:'get',
                                    data:{
                                        id:data.id,
                                        zhizaoshang:$('#edit_zhizaoshang').val(),
                                        ip:$('#edit_ip').val(),
                                        port:$('#edit_port').val()
                                    },
                                    success:function(data){
                                        ajaxSuccess(data,table);
                                        layer.close(i);layer.close(index_one);
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

    form.on('submit(form_search)',function(data){
        var field = getParams("form");
        table.reload('table',{where:field});
        return false;
    });

    //监听是否报警
    form.on('switch(switchjhtc)', function(data) {
        var id = (data.elem).id;
        //当前switch对应的value值
        var jhtc = this.checked ? "1" : "0";
        //更新switch对应的value
        (data.elem).value = sfbj;
        //调用更新字段方法
        $.ajax({
            url:layui.setter.host+'jichushuju/shebei/shebei/updateJHTC',
            type:'get',
            data:{
                id:id,
                jihuatingtai:jhtc
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
                            layer.open({content:data.data});
                            layer.close(index);
                        }
                    });
                }
            }
        });
    });

    //丰富列配置功能
    function fixedColumn(cols) {
        for(var i = 0; i < cols.length; i++) {
            var col = cols[i];
            if(col.field == 'jihuatingtai') {
                col.templet = "#jihuatingtai";
            }
        }
        return cols;
    }

    exports('shebeiguanli', {})
});