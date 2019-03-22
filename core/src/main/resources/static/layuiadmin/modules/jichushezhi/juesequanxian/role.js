layui.define(['table', 'form'], function(exports){
    var $ = layui.$
        ,table = layui.table
        ,form = layui.form;

    //表格初始化
    table.render({
        elem: '#table'
        ,method:'GET'
        ,url: layui.setter.host + 'jichushezhi/juesequanxian/role/findAll'
        ,cols: [[
            {field: 'id', title: 'id',hide:true}
            ,{field: 'name', title: '角色名'}
            ,{field: 'beizhu', title: '角色描述'}
            ,{fixed: 'right',width: 280,align: 'center',title: '操作',toolbar: '#barDemo'}
        ]]
    });

    //添加角色
    $("#add_role_btn").on("click", function() {
        layer.open({
            type: 1,
            title: ['增加角色 ', 'font-size:12px;'],
            content: $("#add_js_tck"),
            shadeClose: true, //点击遮罩关闭层
            shade: 0.8,
            area: ['50%', '50%'],
            btn: ['确定', '取消'],
            btnAlign: 'c',
            yes: function(index, layero) {
                addRole();
            },
            offset: '150px'
        })
    });

    function addRole() {
        $.ajax({
            url:layui.setter.host+'jichushezhi/juesequanxian/role/saveRole',
            type:'post',
            contentType:"application/json;charset=utf-8",
            data:{
                'name':$('#add_js_name').val(),
                'beizhu':$('#add_ms').val()
            },
            success:function(data){
                ajaxSuccess(data,table);
            }
        });
    }

    exports('role', {})
});