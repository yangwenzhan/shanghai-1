layui.define(['table', 'laydate', 'form', 'upload'], function (exports) {
    var $ = layui.$
        , table = layui.table
        , form = layui.form
        , upload = layui.upload
        , laydate = layui.laydate;

    //查询表头部初始化
    var initSele = [
        {eleId:'gongyingsha_sele',dictCode:'ys_gongyingshang',val:'id'}
    ];
    dictInitSele(initSele,false);
    form.render();

    //监听搜索
    form.on('submit(form_search)', function (data) {
        var field = data.field;
        table.reload('table', {
            where: field
        });
        return false;
    });

    //设置表格头
    var cols = [[
        {field: 'id', title: 'id', hide: true}
        , {field: 'createTime', title: '日期', sort: true}
        , {field: 'pinming',title: '品名',templet: repNull('pinming'), sort: true}
        , {field: 'pihao',title: '批号',templet: repNull('pihao'), sort: true}
        , {field: 'zhishu',title: '支数',templet: repNull('zhishu'), sort: true}
        , {field: 'sebie',title: '色别',templet: repNull('sebie'),sort: true}
        , {field: 'sehao',title: '色号',templet: repNull('sehao'),sort: true}
        , {title: '供应商',templet: repNull('gongyingshang.name')}
        , {title: '包装形式',templet: repNull('baozhuangxingshi.name')}
        , {field: 'kucunliang',title: '库存量',templet: repNull('kucunliang'),sort: true}
        , {title: '备注', field: 'beizhu'}
    ]];

    //初始化表格
    initTable("table", 'yuanshaguanli/yuanshakucunchakan/query_page', 'get', cols, table,"from");

    exports('yuanshakucunchakan', {})
});