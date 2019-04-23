layui.define(['table', 'form', 'laydate'], function(exports){
    var $ = layui.$
        ,table = layui.table
        ,form = layui.form
        ,laydate = layui.laydate;

    //日期初始化
    /*laydate.render({
        elem: '#ksrq',
        value: (new Date().getFullYear()-1)+'-'+(new Date().getMonth()+1)+'-'+new Date().getDate()
    });
    laydate.render({
        elem: '#jsrq',
        value: new Date()
    });*/

    var cols =  [[
        {field: 'id', title: 'id',hide:true}
        ,{field: 'pihao',sort:true, title: '批号',fixed:true,width:120}
        ,{field: 'pinming',sort:true, title: '品名',fixed:true,width:120}
        ,{title: '实际号数tex',sort:true, templet: repNull('yuanSha_zhiLiang.shijihaoshu'),width:120}
        ,{title: '克重回潮%',sort:true, templet: repNull('yuanSha_zhiLiang.kezhonghuichao'),width:120}
        ,{title: '号数偏差率%',sort:true, templet: repNull('yuanSha_zhiLiang.haoshupianchalv'),width:120}
        ,{title: '单强cn',sort:true, templet: repNull('yuanSha_zhiLiang.danqiang'),width:120}
        ,{title: '条干CV%',sort:true, templet: repNull('yuanSha_zhiLiang.tiaogan'),width:120}
        ,{title: '细节-50%',sort:true, templet: repNull('yuanSha_zhiLiang.xijie'),width:120}
        ,{title: '粗结+50%',sort:true, templet: repNull('yuanSha_zhiLiang.cujie'),width:120}
        ,{title: '棉节+200%',sort:true, templet: repNull('yuanSha_zhiLiang.mianjie'),width:120}
        ,{title: '每包实重kg',sort:true, templet: repNull('yuanSha_zhiLiang.meibaoshizhong'),width:120}
        ,{title: '每包偏差kg',sort:true, templet: repNull('yuanSha_zhiLiang.meibaopiancha'),width:120}
        ,{title: '盈亏重量kg',sort:true, templet: repNull('yuanSha_zhiLiang.yingkuizhongliang'),width:120}
        ,{title: '称重回潮%',sort:true, templet: repNull('yuanSha_zhiLiang.chengzhonghuichao'),width:120}
        ,{title: '包装带重量kg',sort:true, templet: repNull('yuanSha_zhiLiang.baozhuangdaizhongliang'),width:120}
        ,{title: '纸管重量kg',sort:true, templet: repNull('yuanSha_zhiLiang.zhiguanzhongliang'),width:120}
        ,{title: '每包筒子个数',sort:true, templet: repNull('yuanSha_zhiLiang.tongzigeshu'),width:120}
        ,{title: '备注',sort:true, templet: repNull('yuanSha_zhiLiang.beizhu'),width:120}
        ,{align: 'center',title: '操作',toolbar: '#barDemo',width:100, fixed: 'right'}
    ]];

    initTable("table", 'zhiliang/yuanshazhiliang/findAll', 'get',cols, table, "form");

    form.on('submit(form_search)',function(data){
        var field = data.field;
        table.reload('table', {
            where: field
        });
        return false;
    });

    table.on('tool(table)',function(obj) {
        var data = obj.data;
        var ys_id = data.id;

        var yszl_data = data.yuanSha_zhiLiang;

        if(obj.event === 'edit'){
            layer.open({
                type: 1
                ,title: '编辑 '+data.pihao+' '+data.pinming+' 原纱质量信息'
                ,content: $('#edit_form_div')
                ,offset:'auto'
                ,area: ['80%', '80%']
                ,btn: ['修改', '取消']
                ,btnAlign: 'c'
                ,btn1: function(index, layero) {
                    layer.confirm('确定要修改原纱 '+data.pihao+' '+data.pinming+' 质量信息吗?'
                        ,function(i){

                            form.on('submit(form_edit_submit)', function (data) {
                                var formData = data.field;
                                var yuansha = new Object();
                                if(yszl_data != null){
                                    formData.id=yszl_data.id;
                                }
                                yuansha = {id:ys_id,yuanSha_zhiLiang:formData};

                                $.ajax({
                                    url:layui.setter.host+'zhiliang/yuanshazhiliang/updYuanShaZhiLiang',
                                    type:'post',
                                    contentType:"application/json;charset=utf-8",
                                    data:JSON.stringify(yuansha),
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
                    form.val('form_edit',yszl_data);
                },
            });
        }
    });

    exports('yuansha', {})
});