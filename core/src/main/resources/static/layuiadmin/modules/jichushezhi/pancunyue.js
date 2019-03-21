layui.define(['table', 'form'], function(exports){
    var $ = layui.$
        ,table = layui.table
        ,form = layui.form;



    table.render({
        elem: '#table'
        ,method:'GET'
        ,url: layui.setter.host + 'jichushezhi/pancunyue/query_page'
        ,cols: [[
            {type: 'checkbox', fixed: 'left'}
            ,{field: 'nian', title: '年份'}
            ,{field: 'yue', title: '月份'}
        ]]
    });



    exports('pancunyue', {})
});