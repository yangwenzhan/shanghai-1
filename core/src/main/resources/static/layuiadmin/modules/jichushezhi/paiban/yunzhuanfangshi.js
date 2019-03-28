layui.define(['table', 'form'], function(exports){
    var $ = layui.$
        ,table = layui.table
        ,form = layui.form;

    table.render({
        elem: '#table'
        ,method:'GET'
        ,url: layui.setter.host + 'jichushezhi/paiban/yunzhuanfangshi/findAllYZFS'
        ,cols: [[
            {field: 'id', title: 'id',hide:true}
            ,{field: 'lunbanshu', title: '运转轮班个数'}
            ,{field: 'paibanshu', title: '运转排班个数'}
            ,{fixed: 'right',width: 200,align: 'center',title: '操作',toolbar: '#barDemo'}
        ]]
    });

    table.on('tool(table)', function(obj){
        var data = obj.data;
        if(obj.event === 'detail'){
            //查看运转方式详情
            initYunZhuanInfo(data);
            layer.open({
                type: 1,
                title: ['查看运转方式', 'font-size:12px;'],
                content: $("#s_chakan"),
                shade: 0.8,
                area: ['80%', '80%'],
                btn: ['关闭'],
                btnAlign: 'c',
                yes: function(index, layero) {
                    layer.close(index);
                }
            });
        }else if(obj.event === 'edit'){
            //渲染该运转方式
            initEditYunZhuanInfo(data);
            // 修改
            layer.open({
                type: 1,
                title: ['修改', 'font-size:12px;'],
                content: $("#s_ttxiugai"),
                shade: 0.8,
                area: ['60%', '86%'],
                btn: ['确定', '取消'],
                btnAlign: 'c',
                yes: function(index_one, layero) {
                    layer.confirm('确定要修改该运转方式吗？ ', {
                            btn: ['确定', '取消'],
                        },
                        function(index, layero) {
                            upd_yzfsInfo(data, index_one);
                        },
                        function(index) {

                        });
                },
                btn2: function(index, layero) {
                    //按钮【按钮二】的回调
                    layer.close(index);
                }
            });
        }
    });

    function initYunZhuanInfo(data){
        var yz_data = [];
        yz_data = data.yunZhuanFangShi_xiangqingSet;



    }

    function initEditYunZhuanInfo(data){

    }


    exports('pancunyue', {})
});