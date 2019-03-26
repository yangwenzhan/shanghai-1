layui.define(['table', 'form'], function(exports){
        var table = layui.table
            ,form = layui.form;
    searchForm("lunban","gongxu");

    var cols =  [[
        {field: 'id', title: 'id',hide:true}
        ,{field: 'name', title: '类别名称'}
        ,{field:'code', title: '类别编码'}
        ,{field:'fixed',title:'用户不可编辑',templet: '#tpl_edit'}
        ,{title: '操作', toolbar: '#caozuo'}
    ]]


    initTable ("table", 'xitongshezhi/shujuzidian/query_page', 'get',cols, table,"form");


    //监听搜索
    form.on('submit(form_search)', function(data){
        var field = data.field;
        table.reload('table', {
            where: field
        });
        return false;
    });

   form.on('switch(form_fixed)',function(data){
       var id = data.elem.id;
       var value = this.checked?"1":"0";
       $.ajax({
           url:layui.setter.host+'xitongshezhi/shujuzidian/edit_fixed',
           type:'get',
           data:{id:id,value:value},
           success:function(data){
               ajaxSuccess(data,table);
           }
       });
   });


    //监听工具条
    table.on('tool(table)', function(obj){
        var data = obj.data;
        if(obj.event === 'del'){
            layer.confirm(
                "确定要删除"+data.name+'么?',
                {title:'删除提示'},function (index){
                    layer.close(index);
            });
        }else if(obj.event === 'edit'){
            layer.open({
                 type: 1
                ,title: '编辑数据字典'
                ,content: $('#div_form_edit')
                ,area: ['60%', '60%']
                ,btn: ['修改', '取消']
                ,btn1: function(index, layero){
                     layer.confirm('确定要修改数据字典么?'
                         ,function(i){

                         form.on('submit(form_edit_submit)', function (data) {
                             var formData = data.field;
                             if(formData.hasOwnProperty('fixed')){
                                 formData.fixed = "1";
                             }else{
                                 formData.fixed = "0";
                             }
                             $.ajax({
                                 url:layui.setter.host+'xitongshezhi/shujuzidian/edit',
                                 type:'get',
                                 data:formData,
                                 success:function(data){
                                     ajaxSuccess(data,table);
                                 }
                             });

                         });

                         $("#form_edit_submit").trigger('click');
                       layer.close(i);layer.close(index);

                     })
                }
                ,success: function(){
                     form.val('form_edit',data);
                }
            })
        }
    });

    exports('shujuzidian', {})
});