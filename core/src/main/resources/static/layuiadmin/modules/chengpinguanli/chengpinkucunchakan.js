layui.define(['table', 'laydate', 'form', 'upload'], function (exports) {
    var $ = layui.$
        , table = layui.table
        , form = layui.form
        , upload = layui.upload
        , laydate = layui.laydate;

    //监听搜索
    form.on('submit(form_search_cp)', function (data) {
        var field = data.field;
        table.reload('chengpinCurrent_table', {
            where: field
        });
        return false;
    });

    //设置表格头
    var cp_cols = [[
        {field: 'id', title: 'id', hide: true}
        , {title: '订单号',templet: repNull('heyuehao.order.dingdanhao'),field: 'heyuehao.order.dingdanhao', sort: true}
        , {title: '合约号',templet: repNull('heyuehao.name'),field: 'heyuehao.name', sort: true}
        , {title: '客户编号描述',templet: repNull('heyuehao.kehubianhaomiaoshu')}
        , {title: '剩余长度',templet: repNull('changdu')}
    ]];

    //初始化表格
    initTable("chengpinCurrent_table", 'chengpinguanli/chengpinCurrent/query_page', 'get', cp_cols, table);

    exports('chengpinCurrent', {})
});