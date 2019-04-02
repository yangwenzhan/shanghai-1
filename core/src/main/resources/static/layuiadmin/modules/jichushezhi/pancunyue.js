layui.define(['table', 'form', 'laydate'], function(exports){
    var $ = layui.$
        ,table = layui.table
        ,form = layui.form
        ,laydate = layui.laydate;
    //年初始化
    laydate.render({
        elem: '#nian',
        type: 'year',
        change: function(value, date, endDate) {
            // $('#layui-laydate1').css("display", 'none');
            $('#cx_nian').val(value);
        },
    });

    table.render({
        elem: '#table'
        ,method:'GET'
        ,url: layui.setter.host + 'jichushezhi/pancunyue/query_page'
        ,cols: [[
            {field: 'id', title: 'id',hide:true}
            ,{type: 'checkbox', fixed: 'left'}
            ,{field: 'nian', title: '年份'}
            ,{field: 'yue', title: '月份'}
            ,{align: 'center',title: '操作',toolbar: '#barDemo'}
        ]]
    });



    exports('pancunyue', {})
});