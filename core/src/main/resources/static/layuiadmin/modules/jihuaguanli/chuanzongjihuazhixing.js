layui.define(['table', 'laydate', 'form', 'upload'], function (exports) {
    var $ = layui.$
        , table = layui.table
        , form = layui.form
        , upload = layui.upload
        , laydate = layui.laydate;

       //查询表头部初始化
    var initSele = [
        /*{eleId:'banci_sele',dictCode:'banci',val:'id'},*/
        {eleId:'youxianji_sele',dictCode:'youxianji',val:'id'},
        {eleId:'status_sele',dictCode:'czjh_xq_zhuangtai',val:'id',isAll:false}
    ];
    dictInitSele(initSele,false,form);
    var heyuehaoSO = initSelectObj('heyuehao_sele', 'dingdanguanli/heyuehaoguanli/findAll','name','id');
    InitSelect(heyuehaoSO,form);
    var date = new Date();
    laydate.render({
        elem: '#kaishiriqi_sele',
        value: (date.getFullYear()-1)+'-'+(date.getMonth()+1)+'-'+date.getDate()
    });
    laydate.render({
        elem: '#jieshuriqi_sele',
        value: date
    });

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
        , {title: '计划日期',field: 'riqi', sort: true}
        , {title: '班次',templet: repNull('banci.name'),field: 'banci.name', sort: true}
        , {title: '合约号',templet: repNull('heyuehao.name'),field: 'heyuehao.name', sort: true}
        , {title: '机台号',templet: repNull('jitaihao.jitaihao','手动穿综'),field: 'jitaihao.jitaihao', sort: true}
        , {title: '优先级',templet: repNull('jiHuaChuanZongMain.youxianji.name'),field: 'jiHuaChuanZongMain.youxianji.name', sort: true}
        , {title: '操作', toolbar: '#caozuo', fixed: 'right',width:200}
    ]];
    //初始化表格
    initTable("table", 'jihuaguanli/chuanzongjihuazhixing/query_page', 'get', cols, table,"form");


    //监听操作列
    table.on('tool(table)', function (obj) {
        var data = obj.data;
        if(null == data.jitaihao || null == data.jitaihao.jitaihao){
            data['jitaihao.jitaihao'] = "手动穿综";
        }

       if (obj.event === 'edit') {
           var chuanzonggongSO = initSelectObj('chuanzonggong_edit', 'dingdanguanli/dingdanguanli/getUser','ghxm','id');
           InitSelect(chuanzonggongSO,form);
           // zhizhou_edit 穿筘织轴
           var zhizhouSO = initSelectObj('zhizhou_edit', 'jihuaguanli/chuanzongjihuazhixing/getZhizhou','zhouhao','id');
           zhizhouSO.data={
               heyuehao_id:data.heyuehao.id
           };
           InitSelect(zhizhouSO,form);
            editI = layer.open({
                type: 1
                , title: '穿综计划登记'
                , content: $('#div_form_edit')
                , area: ['70%', '60%']
                , btn: ['登记', '取消']
                , btn1: function (editIndex, layero) {
                    form.on('submit(form_edit_submit)', function (data) {
                        layer.confirm('确定要登记信息么?'
                            , function (i) {
                                var formData = data.field;
                                encObject(formData);
                                $.ajax({
                                    url: layui.setter.host + 'chengpinguanli/chengpinchukuzhixing/update',
                                    contentType: "application/json;charset=utf-8",
                                    type: 'POST',
                                    data: JSON.stringify(formData),
                                    success: function (data) {
                                        ajaxSuccess(data, table);
                                    }
                                });
                                layer.close(i);
                                layer.close(editI);
                            });
                    });
                    $("#form_edit_submit").trigger('click');
                }
                , success: function () {
                    fromSetVel(form, 'form_edit', data);
                }
            })
        }
    });



    //------------------------------------------------------------------------------------------------------------------------------------------------------------已执行
    //查询表头部初始化
    var initSele = [
        {eleId:'youxianji_sele_yzx',dictCode:'youxianji',val:'id'},
        {eleId:'status_sele_yzx',dictCode:'czjh_xq_zhuangtai',val:'id',isAll:false}
    ];
    dictInitSele(initSele,false,form);
    var heyuehaoSO = initSelectObj('heyuehao_sele_yzx', 'dingdanguanli/heyuehaoguanli/findAll','name','id');
    InitSelect(heyuehaoSO,form);
    var date = new Date();
    laydate.render({
        elem: '#kaishiriqi_sele_yzx',
        value: (date.getFullYear()-1)+'-'+(date.getMonth()+1)+'-'+date.getDate()
    });
    laydate.render({
        elem: '#jieshuriqi_sele_yzx',
        value: date
    });
    //监听搜索
    form.on('submit(form_search_yzx)', function (data) {
        var field = data.field;
        table.reload('table_yzx', {
            where: field
        });
        return false;
    });
    //设置表格头
    var cols_yzx = [[
        {field: 'id', title: 'id', hide: true}
        , {title: '计划日期',field: 'riqi', sort: true,width:120}
        , {title: '计划班次',templet: repNull('banci.name'),field: 'banci.name', sort: true,width:120}
        , {title: '状态',templet: repNull('status.name'),field: 'status.name', sort: true,width:120}
        , {title: '合约号',templet: repNull('heyuehao.name'),field: 'heyuehao.name', sort: true,width:120}
        , {title: '机台号',templet: repNull('jitaihao.jitaihao','手动穿综'),field: 'jitaihao.jitaihao', sort: true,width:120}
        , {title: '优先级',templet: repNull('jiHuaChuanZongMain.youxianji.name'),field: 'jiHuaChuanZongMain.youxianji.name', sort: true,width:120}
        , {title: '下机日期',templet: repNull('zhiXing_chuanZong.riqi'),field: 'zhiXing_chuanZong.riqi', sort: true,width:120}
        , {title: '下机时间',templet: repNull('zhiXing_chuanZong.shijian'),field: 'zhiXing_chuanZong.shijian', sort: true,width:120}
        , {title: '下机班次',templet: repNull('zhiXing_chuanZong.banci.name'),field: 'zhiXing_chuanZong.banci.name', sort: true,width:120}
        , {title: '织轴号',templet: repNull('zhiXing_chuanZong.zhizhou.zhouhao'),field: 'zhiXing_chuanZong.zhizhou.zhouhao', sort: true,width:120}
        , {title: '筘高',templet: repNull('zhiXing_chuanZong.kougao'),field: 'zhiXing_chuanZong.kougao', sort: true,width:120}
        , {title: '筘幅',templet: repNull('zhiXing_chuanZong.koufu'),field: 'zhiXing_chuanZong.koufu', sort: true,width:120}
        , {title: '筘宽',templet: repNull('zhiXing_chuanZong.koukuan'),field: 'zhiXing_chuanZong.koukuan', sort: true,width:120}
        , {title: '穿综工',templet: repNull('zhiXing_chuanZong.chuanzonggong.xingming'),field: 'zhiXing_chuanZong.chuanzonggong.xingming',width:120}
        , {title: '下机备注',templet: repNull('zhiXing_chuanZong.xiajibeizhu') ,width:120}

    ]];
    //初始化表格
    initTable("table_yzx", 'jihuaguanli/chuanzongjihuazhixing/query_page', 'get', cols_yzx, table,"form_yzx");
    tq_verify(form);//给form添加自定义校验
    exports('chuanzongjihuazhixing', {})
});