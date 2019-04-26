layui.define(['table', 'laydate', 'form', 'upload'], function (exports) {
    var $ = layui.$
        , table = layui.table
        , form = layui.form
        , upload = layui.upload
        , laydate = layui.laydate;

    // //查询表头部初始化
    // var initSele = [
    //     {eleId:'banci_sele',dictCode:'banci',val:'id'},
    //     {eleId:'leixing_sele',dictCode:'bjjh_leixing',val:'id'},
    //     {eleId:'danshuangzhou_sele',dictCode:'bujidanshuangzhou',val:'id'},
    //     {eleId:'status_sele',dictCode:'bjjh_zhaungtai',val:'id'}
    // ];
    // dictInitSele(initSele,false,form);
    // var youxianjiSO = initSelectObj('youxianji_sele', 'jihuaguanli/bujijihuaxiada/query_distinctYouxianji','youxianji','youxianji');
    // InitSelect(youxianjiSO,form);
    // var jixingSO = initSelectObj('jixing_sele', 'common/findZhiJiJiXing','name','id');
    // InitSelect(jixingSO,form);
    // var jitaihaoSO = initSelectObj('jitaihao_sele', 'common/findByShebei_zhibu','jitaihao','id');
    // jitaihaoSO.data = {jixing_id:$("#jixing_sele").val()};
    // InitSelect(jitaihaoSO,form);
    // form.on('select(jixing_sele)', function(data) {
    //     jitaihaoSO.data = {jixing_id:$("#jixing_sele").val()};
    //     InitSelect(jitaihaoSO,form);//根据机型联动机台号
    // });
    // var heyuehaoSO = initSelectObj('heyuehao_sele', 'dingdanguanli/heyuehaoguanli/findAll','name','id');
    // InitSelect(heyuehaoSO,form);
    // var date = new Date();
    // laydate.render({
    //     elem: '#kaishiriqi_sele',
    //     value: (date.getFullYear()-1)+'-'+(date.getMonth()+1)+'-'+date.getDate()
    // });
    // laydate.render({
    //     elem: '#jieshuriqi_sele',
    //     value: date
    // });
    // //监听搜索
    // form.on('submit(form_search)', function (data) {
    //     var field = data.field;
    //     table.reload('table', {
    //         where: field
    //     });
    //     return false;
    // });
    //
    // //设置表格头
    // var cols = [[
    //       {field: 'id', title: 'id', hide: true}
    //     , {title: '计划日期',field: 'riqi', sort: true}
    //     , {title: '班次',templet: repNull('banci.name'),field: 'banci.name', sort: true}
    //     , {title: '类型',templet: repNull('leixing.name'),field: 'leixing.name', sort: true}
    //     , {title: '机型',templet: repNull('jitaihao.gongxu.name'),field: 'jjitaihao.gongxu.name', sort: true}
    //     , {title: '机台号',templet: repNull('jitaihao.jitaihao'),field: 'jitaihao.jitaihao', sort: true}
    //     , {title: '织轴',field: 'zhizhou'}
    //     , {title: '合约号',templet: repNull('heyuehao.name'),field: 'heyuehao.name', sort: true}
    //     , {title: '单双轴',templet: repNull('danshuangzhou.name'),field: 'danshuangzhou.name', sort: true}
    //     , {title: '优先级',field: 'youxianji', sort: true}
    //     , {title: '状态',templet: repNull('status.name'),field: 'status.name', sort: true}
    //     , {title: '计划备注',field: 'beizhu'}
    //     , {title: '上机日期',templet: repNull('zhiXingBuJi.riqi')}
    //     , {title: '上机班次',templet: repNull('zhiXingBuJi.banci.name')}
    //     , {title: '上机时间',templet: repNull('zhiXingBuJi.shijian')}
    //     , {title: '上机织轴',templet: repNull('zhiXingBuJi.zhizhou')}
    //     , {title: '上轴工',templet: repNull('shangzhougong.xingming')}
    //     , {title: '上机备注',templet: repNull('zhiXingBuJi.beizhu')}
    //     , {title: '操作', toolbar: '#caozuo', fixed: 'right',width:180}
    // ]];
    // //初始化表格
    // initTable("table", 'jihuaguanli/bujijihuaxiada/query_page', 'get', cols, table,"form");
    //
    // laydate.render({
    //     elem: '#jihuariqi_add',
    //     value: date
    // });
    // laydate.render({
    //     elem: '#jihuariqi_edit',
    //     value: date
    // });
    //
    // //添加
    // $("#add").click(function () {
    //     //查询表头部初始化
    //     var initSele = [
    //         {eleId:'banci_add',dictCode:'banci',val:'id'},
    //         {eleId:'leixing_add',dictCode:'bjjh_leixing',val:'id'},
    //         {eleId:'danshuangzhou_add',dictCode:'bujidanshuangzhou',val:'id',CheckVal:"1",isAll:false}
    //     ];
    //     dictInitSele(initSele,false,form);
    //     var jixingSO = initSelectObj('jixing_add', 'common/findZhiJiJiXing','name','id');
    //     InitSelect(jixingSO,form);
    //     var jitaihaoSO = initSelectObj('jitaihao_add', 'common/findByShebei_zhibu','jitaihao','id');
    //     jitaihaoSO.data = {jixing_id:$("#jixing_add").val()};
    //     InitSelect(jitaihaoSO,form);
    //     form.on('select(jixing_add)', function(data) {
    //         jitaihaoSO.data = {jixing_id:$("#jixing_add").val()};
    //         InitSelect(jitaihaoSO,form);//根据机型联动机台号
    //     });
    //     var heyuehaoSO = initSelectObj('heyuehao_add', 'dingdanguanli/heyuehaoguanli/findAll','name','id');
    //     InitSelect(heyuehaoSO,form);
    //     layer.open({
    //         type: 1
    //         , title: '布机计划下达'
    //         , content: $('#div_form_add')
    //         , area: ['60%', '60%']
    //         , btn: ['添加', '取消']
    //         , btn1: function (index, layero) {
    //             form.on('submit(form_add_submit)', function (data) {
    //                 var formData = data.field;
    //                 encObject(formData);
    //                 $.ajax({
    //                     url: layui.setter.host + 'jihuaguanli/bujijihuaxiada/add',
    //                     contentType: "application/json;charset=utf-8",
    //                     type: 'POST',
    //                     data: JSON.stringify(formData),
    //                     success: function (data) {
    //                         ajaxSuccess(data, table);
    //                         layer.close(index);
    //                         fromClear("form_add");
    //                     }
    //                 });
    //             });
    //             $("#form_add_submit").trigger('click');
    //         }
    //         , success: function () { //弹出层打开成功时的回调。
    //         }
    //     });
    // });
    //
    // //监听操作列
    // table.on('tool(table)', function (obj) {
    //     var data = obj.data;
    //     if (obj.event === 'del') {
    //         layer.confirm(
    //             "确定要删除吗？",
    //             {title: '删除提示'}, function (index) {
    //                 $.ajax({
    //                     url: layui.setter.host + 'jihuaguanli/bujijihuaxiada/delete',
    //                     type: 'get',
    //                     data: {'id': data.id},
    //                     success: function (data) {
    //                         ajaxSuccess(data, table);
    //                     }
    //                 });
    //             });
    //     } else if (obj.event === 'edit') {
    //         //查询表头部初始化
    //         var initSele = [
    //             {eleId:'banci_edit',dictCode:'banci',val:'id'},
    //             {eleId:'leixing_edit',dictCode:'bjjh_leixing',val:'id'},
    //             {eleId:'danshuangzhou_edit',dictCode:'bujidanshuangzhou',val:'id',CheckVal:"1",isAll:false}
    //         ];
    //         dictInitSele(initSele,false,form);
    //         var jixingSO = initSelectObj('jixing_edit', 'common/findZhiJiJiXing','name','id');
    //         InitSelect(jixingSO,form);
    //         var jitaihaoSO = initSelectObj('jitaihao_edit', 'common/findByShebei_zhibu','jitaihao','id');
    //         jitaihaoSO.data = {jixing_id:$("#jixing_edit").val()};
    //         InitSelect(jitaihaoSO,form);
    //         form.on('select(jixing_edit)', function(data) {
    //             jitaihaoSO.data = {jixing_id:$("#jixing_edit").val()};
    //             InitSelect(jitaihaoSO,form);//根据机型联动机台号
    //         });
    //         var heyuehaoSO = initSelectObj('heyuehao_edit', 'dingdanguanli/heyuehaoguanli/findAll','name','id');
    //         InitSelect(heyuehaoSO,form);
    //         editI = layer.open({
    //             type: 1
    //             , title: '成品申请信息编辑'
    //             , content: $('#div_form_edit')
    //             , area: ['70%', '60%']
    //             , btn: ['修改', '取消']
    //             , btn1: function (editIndex, layero) {
    //                 form.on('submit(form_edit_submit)', function (data) {
    //                     layer.confirm('确定要修改计划信息么?'
    //                         , function (i) {
    //                             var formData = data.field;
    //                             encObject(formData);
    //                             $.ajax({
    //                                 url: layui.setter.host + 'jihuaguanli/bujijihuaxiada/update',
    //                                 contentType: "application/json;charset=utf-8",
    //                                 type: 'POST',
    //                                 data: JSON.stringify(formData),
    //                                 success: function (data) {
    //                                     ajaxSuccess(data, table);
    //                                 }
    //                             });
    //                             layer.close(i);
    //                             layer.close(editI);
    //                         });
    //                 });
    //                 $("#form_edit_submit").trigger('click');
    //             }
    //             , success: function () {
    //                 fromSetVel(form, 'form_edit', data);
    //             }
    //         })
    //     }
    // });

    tq_verify(form);//给form添加自定义校验

    exports('chuanzongjihuaxiada', {})
});