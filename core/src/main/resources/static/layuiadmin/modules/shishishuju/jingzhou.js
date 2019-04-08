layui.define(['table', 'form'], function(exports){
    var $ = layui.$
        ,table = layui.table
        ,form = layui.form;

    //筛选条件
    dynamicForm(
        {code:"jingzhouzhuangtai",hasNull:true,defaultValue:""}
        ,{code:"jingzhoubianhao",hasNull:true,defaultValue:""}
    );

    $.ajax({
        url:layui.setter.host+'shishishuju/cur_jingzhou/cur_jingzhou_hyh',
        type:'get',
        async:false,
        success:function(data){
            if(data.code == 0){
                var html = '';
                html+='<option value= "" >全部</option>';
                for(var i = 0;i<data.data.length;i++){
                    html+='<option value= "'+data.data[i].id+'" >'+data.data[i].name+'</option>';
                }
                $('#hyh').append(html);
            }
        }
    });
    form.render();

    var cols =  [
        {field: 'id', title: 'id',hide:true}
        ,{field: 'zhouhao',sort:true, title: '经轴号'}
        ,{field: 'zhoukuan',sort:true, title: '轴宽'}
        ,{field: 'jingchang',sort:true, title: '计长'}
        ,{field: 'zt',sort:true, title: '状态'}
        ,{field: 'jitaihao',sort:true, title: '机台号'}
        ,{field: 'name',sort:true, title: '合约号'}
        ,{field: 'pibuguige',sort:true, title: '坯布规格'}
        ,{field: 'beizhu',sort:true, title: '备注'}
        ,{field: 'last_modify_time',sort:true, title: '最后更新时间'}
        ,{field: 'last_modify_ren',sort:true, title: '最后更新人'}
    ];

    cols = formatColumns(cols);

    table.render({
        elem: '#table'
        ,limit:100000
        ,method:'GET'
        ,url: layui.setter.host + 'shishishuju/cur_jingzhou/cur_jingzhou'
        ,where:{zt_id:$('#jz_zt').val(),
                heyuehao:$('#hyh').val(),
                zhouhao:$('#jz_bh').val()
        }
        ,cols: [cols]
    });


    form.on('submit(form_search)',function(data){
        var field = {
            zt_id:$('#jz_zt').val(),
            heyuehao:$('#hyh').val(),
            zhouhao:$('#jz_bh').val()
        };
        table.reload('table',{where:field});
        return false;
    });


    //按合约号汇总
    $('#heyuehaohuizong_btn').on("click",function(){
        $.ajax({
            url:layui.setter.host+'shishishuju/cur_jingzhou/cur_jingzhou_hyshz',
            type:'get',
            success:function(data){
                if(data.code==0){
                    var cols = [
                        {field: 'name',sort:true,width:200, title: '合约号'}
                        ,{field: 'pibuguige',sort:true, width:200,title: '坯布规格'}
                        ,{field: 'zhoushu',sort:true,width:200, title: '经轴数'}
                        ,{field: 'zongjingchang',sort:true,width:200, title: '总经长(米)'}
                        ,{field: 'jzbh',sort:true,width:200, title: '经轴编号'}
                    ];
                    cols = formatColumns(cols);
                    table.render({
                        elem: '#hyh_dataGrid',
                        id: 'hyh_dataGrid',
                        data: data.data,
                        cols: [cols],
                        skin: 'row', // 表格风格
                        even: true,
                        limit: 10000
                    });
                    layer.open({
                        type: 1,
                        title: ['按合约号汇总 '],
                        content: $("#hyh_hz_tck"),
                        shade: 0.8,
                        area: ['70%', '90%'],
                        offset: "10px"
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
        return false;
    });

    //按状态汇总
    $('#zhuangtaihuizong_btn').on("click",function(){
        $.ajax({
            url:layui.setter.host+'shishishuju/cur_jingzhou/cur_jingzhou_zthz',
            type:'get',
            success:function(data){
                if(data.code==0){
                    var cols = [
                        {field: 'zt',sort:true,width:200, title: '经轴状态'}
                        ,{field: 'zhoushu',sort:true,width:200, title: '经轴个数'}
                        ,{field: 'zongjingchang',sort:true,width:200, title: '总经长(米)'}
                        ,{field: 'jzbh',sort:true,width:200, title: '经轴号'}
                    ];
                    cols = formatColumns(cols);
                    table.render({
                        elem: '#zt_dataGrid',
                        id: 'zt_dataGrid',
                        data: data.data,
                        cols: [cols],
                        skin: 'row', // 表格风格
                        even: true,
                        limit: 10000
                    });
                    layer.open({
                        type: 1,
                        title: ['按状态汇总 '],
                        content: $("#zt_hz_tck"),
                        shade: 0.8,
                        area: ['70%', '90%'],
                        offset: "10px"
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
        return false;
    });

    //合约号详情
    form.on('submit(heyuehao_filter)',function(data){
        //获取合约号id
        console.log($(this).context.id);

        return false;
    });



    // 丰富列配置功能
    function formatColumns(cols) {
        for(var i = 0; i < cols.length; i++) {
            var col = cols[i];
            if(col.field == 'zt') {
                col.templet = "#mysfxx";
            }
            if(col.field == 'name'){
                col.templet = "<div><div id='{{d.heyuehao_id}}' lay-submit lay-filter='heyuehao_filter' title=\"点击查看合约号详情\">{{d.name}}</div></div>"
            }
        }
        return cols;
    }


    exports('jingzhou', {})
});