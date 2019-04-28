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
        {eleId:'status_sele',dictCode:'czjh_main_zhuangtai',val:'id'}
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
        , {title: '状态',templet: repNull('status.name'),field: 'status.name', sort: true}
        , {title: '班次',templet: repNull('banci.name'),field: 'banci.name', sort: true}
        , {title: '合约号',templet: repNull('heyuehao.name'),field: 'heyuehao.name', sort: true}
        , {title: '轴数',field: 'zhoushu', sort: true}
        , {title: '优先级',templet: repNull('youxianji.name'),field: 'youxianji.name', sort: true}
        , {title: '备注',field: 'beizhu'}
        , {title: '操作', toolbar: '#caozuo', fixed: 'right',width:240}
    ]];
    //初始化表格
    initTable("table", 'jihuaguanli/chuanzongjihuaxiada/query_page', 'get', cols, table,"form");

    laydate.render({
        elem: '#jihuariqi_edit',
        value: date
    });


    //设置表格头
    var cols_xq = [[
         {title: '计划日期',field: 'riqi',width:200}
        , {title: '班次',field: 'banci.name',width:200}
        , {title: '合约号',field: 'heyuehao.name',width:200}
        , {title: '机台号',toolbar: '#jitaihao',width:200}
        , {title: '优先级',field: 'youxianji.name',width:200}
        // , {title: '操作', toolbar: '#caozuo_xq',width:200}
    ]];

    //添加
    $("#add").click(function () {
        //查询表头部初始化
        var initSele = [
            {eleId:'banci_add',dictCode:'banci',val:'id'},
            {eleId:'youxianji_add',dictCode:'youxianji',val:'id',CheckVal:'66',isAll:false},
            {eleId:'status_add',dictCode:'czjh_main_zhuangtai',val:'id'}
        ];
        dictInitSele(initSele,false,form);
        var heyuehaoSO = initSelectObj('heyuehao_add', 'dingdanguanli/heyuehaoguanli/findAll','name','id');
        InitSelect(heyuehaoSO,form);

        layer.open({
            type: 1
            , title: '穿综计划下达'
            , content: $('#div_form_add')
            , area: ['60%', '60%']
            , btn: ['下一步', '取消']
            , yes: function(index, layero) {
                form.on('submit(form_add_submit)', function (data) {
                    var jihua_chuanzong_main = data.field;
                    var banci = $("#banci_add").find("option:selected").text();
                    var heyuehao = $("#heyuehao_add").find("option:selected").text();
                    var youxianji = $("#youxianji_add").find("option:selected").text();
                    init_table_xq(jihua_chuanzong_main,banci,heyuehao,youxianji);
                    layer.close(index);
                    fromClear("form_add");
                    layer.open({
                        type: 1,
                        title: ['穿综计划详情'],
                        content: $("#div_xq"),
                        area: ['60%', '70%'],
                        shade: [0.8, '#393D49'],
                        btn: ['提交','取消'],
                        btn1:function (index, layero) {
                            encObject(jihua_chuanzong_main);
                            jihua_chuanzong_main.jiHua_chuanZongs =  get_sele_id();
                            $.ajax({
                                url: layui.setter.host + 'jihuaguanli/chuanzongjihuaxiada/add',
                                contentType: "application/json;charset=utf-8",
                                type: 'POST',
                                data: JSON.stringify(jihua_chuanzong_main),
                                success: function (data) {
                                    ajaxSuccess(data, table);
                                    layer.close(index);
                                }
                            });
                        }
                    });
                });
                $("#form_add_submit").trigger('click');
            }
            , success: function () { //弹出层打开成功时的回调。
                laydate.render({
                    elem: '#jihuariqi_add',
                    value: date
                });
            }
        });
    });


    
    function init_table_xq(jihua_chuanzong_main,banci,heyuehao,youxianji) {
        $.ajax({
            url: layui.setter.host + 'jihuaguanli/chuanzongjihuaxiada/query_chuanzongji',
            async: false,//是否需要异步
            type: 'GET',
            success: function (data) {
                var jitaihao = data.data;
                var arr = new Array();
                for (var i=0; i<jihua_chuanzong_main.zhoushu; i++){
                    var obj = {};
                    obj.jitaihao = jitaihao;
                    obj.riqi = jihua_chuanzong_main.riqi;
                    obj['banci.name'] = banci;
                    obj["heyuehao.name"]=heyuehao;
                    obj["youxianji.name"]=youxianji;
                    arr[i] = obj;
                }
                table.render({
                    elem: "#table_xq"
                    , cols: cols_xq
                    // , height: '90%'
                    ,data:arr
                });
                form.render();
            }
        });
    }

    function get_sele_id(){
        var arrays = $("div").find('[lay-id=table_xq]').find('select[name=jitaihao]');
        var chuanzhongjihuas = [];
        arrays.each(function(){
            var chuanzhongjihua = {};
            chuanzhongjihua.jitaihao = {};
            chuanzhongjihua.jitaihao.id = $(this).val();
            chuanzhongjihuas.push(chuanzhongjihua);
        });
        return chuanzhongjihuas;
    }

    //监听操作列
    table.on('tool(table)', function (obj) {
        var data = obj.data;
        if (obj.event === 'del') {
            layer.confirm(
                "确定要删除吗？",
                {title: '删除提示'}, function (index) {
                    $.ajax({
                        url: layui.setter.host + 'jihuaguanli/chuanzongjihuaxiada/delete',
                        type: 'get',
                        data: {'id': data.id},
                        success: function (data) {
                            ajaxSuccess(data, table);
                        }
                    });
                });
        } else if (obj.event === 'edit') {
            // //查询表头部初始化
            // var initSele = [
            //     {eleId:'banci_edit',dictCode:'banci',val:'id'},
            //     {eleId:'leixing_edit',dictCode:'bjjh_leixing',val:'id'},
            //     {eleId:'danshuangzhou_edit',dictCode:'bujidanshuangzhou',val:'id',CheckVal:"1",isAll:false}
            // ];
            // dictInitSele(initSele,false,form);
            // var jixingSO = initSelectObj('jixing_edit', 'common/findZhiJiJiXing','name','id');
            // InitSelect(jixingSO,form);
            // var jitaihaoSO = initSelectObj('jitaihao_edit', 'common/findByShebei_zhibu','jitaihao','id');
            // jitaihaoSO.data = {jixing_id:$("#jixing_edit").val()};
            // InitSelect(jitaihaoSO,form);
            // form.on('select(jixing_edit)', function(data) {
            //     jitaihaoSO.data = {jixing_id:$("#jixing_edit").val()};
            //     InitSelect(jitaihaoSO,form);//根据机型联动机台号
            // });
            // var heyuehaoSO = initSelectObj('heyuehao_edit', 'dingdanguanli/heyuehaoguanli/findAll','name','id');
            // InitSelect(heyuehaoSO,form);
            // editI = layer.open({
            //     type: 1
            //     , title: '成品申请信息编辑'
            //     , content: $('#div_form_edit')
            //     , area: ['70%', '60%']
            //     , btn: ['修改', '取消']
            //     , btn1: function (editIndex, layero) {
            //         form.on('submit(form_edit_submit)', function (data) {
            //             layer.confirm('确定要修改计划信息么?'
            //                 , function (i) {
            //                     var formData = data.field;
            //                     encObject(formData);
            //                     $.ajax({
            //                         url: layui.setter.host + 'jihuaguanli/bujijihuaxiada/update',
            //                         contentType: "application/json;charset=utf-8",
            //                         type: 'POST',
            //                         data: JSON.stringify(formData),
            //                         success: function (data) {
            //                             ajaxSuccess(data, table);
            //                         }
            //                     });
            //                     layer.close(i);
            //                     layer.close(editI);
            //                 });
            //         });
            //         $("#form_edit_submit").trigger('click');
            //     }
            //     , success: function () {
            //         fromSetVel(form, 'form_edit', data);
            //     }
            // })
        }else if(obj.event === 'info'){ //--------详情
            layer.open({
                type: 1,
                title: ['穿综计划详情'],
                content: $("#div_xq"),
                area: ['60%', '70%'],
                shade: [0.8, '#393D49'],
                success: function () { //弹出层打开成功时的回调。
                    table.render({
                        elem: "#table_xq"
                        , cols: [[
                              {title: '计划日期',field: 'riqi',width:200, sort: true}
                            , {title: '班次',templet: repNull('banci.name'),field: 'banci.name', sort: true,width:200}
                            , {title: '合约号',templet: repNull('heyuehao.name'),field: 'heyuehao.name',width:200}
                            , {title: '机台号',templet: repNull('jitaihao.jitaihao',"手工穿综"),field: 'jitaihao.jitaihao',width:200}
                            , {title: '状态',templet: repNull('status.name'),field: 'youxianji.name',width:200}
                            // , {title: '操作', toolbar: '#caozuo_xq',width:200}
                        ]]
                        // , height: '90%'
                        ,data:data.jiHua_chuanZongs
                    });
                }
            });
        }
    });

    tq_verify(form);//给form添加自定义校验

    exports('chuanzongjihuaxiada', {})
});