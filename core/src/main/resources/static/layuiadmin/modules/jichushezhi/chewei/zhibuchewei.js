layui.define(['table', 'form', 'laydate'], function(exports){
    var $ = layui.$
        ,table = layui.table
        ,form = layui.form
        ,laydate = layui.laydate;

    //筛选条件初始化



    var cols =  [
        {fixed:'left',checkbox:true}
        ,{field: 'id', title: 'id',hide:true}
        ,{field: 'rownum', title: '',width:50}
        ,{field: 'name',sort:true, title: '参数类别'}
        ,{field: 'csm',sort:true, title: '参数名'}
        ,{field: 'danwei',sort:true, title: '参数单位'}
        ,{field: 'cunchuzhouqi',sort:true, title: '存储周期(秒)'}
        ,{field: 'cunchushichang',sort:true, title: '存储时长(时)'}
        ,{field: 'baojing_flag',sort:true, title: '是否报警'}
        ,{field: 'zhanshi_flag',sort:true, title: '是否报警'}
        ,{field: 'cunchu_flag',sort:true, title: '是否报警'}
        ,{field: 'xuhao',sort:true, title: '排序号'}
    ];

    //根据form获取参数
    initTable_all("table", 'jichushuju/shebei/shebeiparam/findAll', 'get',[cols], table, "form");





    exports('zhibuchewei', {})
});