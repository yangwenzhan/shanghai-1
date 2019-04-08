layui.define(['table', 'form'], function(exports){
    var table = layui.table
        ,form = layui.form;

    var cols =  [[
        {field: 'id', title: 'id',hide:true}
        ,{field: 'name', title: '页面名称',width:200}
        ,{field: 'url', title: 'URL',width:400}
        ,{field: 'tingliushichang', title: '停留时长(秒)',width:200}
        ,{field: 'sort', title: '排序',width:200}
        ,{align: 'center',title: '操作',toolbar: '#barDemo'}
    ]]

    initTable_all ("table", 'jichushezhi/daping/zhanshiyemian/findAll', 'get',cols, table);




    exports('dianshifangan', {})
});