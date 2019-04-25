layui.define(['table', 'form', 'laydate','formSelects'], function(exports){
    var $ = layui.$
        ,table = layui.table
        ,form = layui.form
        ,laydate = layui.laydate
        ,formSelects = layui.formSelects;
    //筛选条件初始化
    var initValue = formatDate(addDate(new Date(),0));
    laydate.render({
        elem: '#rq',
        type: 'date',
        value: initValue
    });
    $('#rq').val(initValue);
    dictInitSelect('banci', null, 'banci', 'name', 'id', true);

    var cols =  [
        {field: 'id', title: 'id',hide:true}
        ,{field: 'riqi',sort:true, title: '日期',width:120}
        ,{field: 'banci',sort:true, title: '班次',width:75}
        ,{field: 'heyuehao',sort:true, title: '合约号',width:100}
        ,{field: 'pibuguige',sort:true, title: '坯布规格',width:170}
        ,{field: 'jitaihao',sort:true, title: '机台号',width:100}
        ,{field: 'type',sort:true, title: '类型',width:75}
        ,{field: 'changdu',sort:true, title: '经长m',width:90}
        ,{field: 'tiaoshu',sort:true, title: '条数',width:85}
        ,{field: 'users',sort:true, title: '整经工',width:150}
        ,{field: 'beizhu',sort:true, title: '备注',width:120}
        ,{align: 'center',title: '操作',toolbar: '#barDemo',width:160, fixed: 'right'}
    ];
    cols = formatColumns(cols);

    initTable("table", 'jiaoban/zhengjingdengji/findAll', 'get',[cols], table, "form");

    form.on('submit(form_search)',function(data){
        var field = data.field;
        table.reload('table', {
            where: field
        });
        return false;
    });

    form.on('select(dj_ssgx)',function (data) {
        InitSelect('dj_zjg', null, 'common/findUser', 'get', {gxid:data.value}, 'ygxm', 'id',false);
        formSelects.render('addUser');
    });
    form.on('select(xg_dj_ssgx)',function (data) {
        InitSelect('xg_dj_zjg', null, 'common/findUser', 'get', {gxid:data.value}, 'ygxm', 'id',false);
        formSelects.render('editUser');
    });

    //新增
    $('#add_btn').on('click',function(){

        $('#dj_ts').val("");$('#dj_jc').val("");$('#dj_bz').val("");

        //fixme  begin 当前整经工序日期班次
        laydate.render({
            elem: '#dj_rq',
            type: 'date',
            value: initValue
        });
        $('#rq').val(initValue);
        dictInitSelect('dj_bc', null, 'banci', 'name', 'id', false);
        //fixme  end
        //工序传整经工序id 1
        InitSelect('dj_hyh', null, 'jiaoban/zhengjingdengji/findHeYueHao', 'get', {}, 'name', 'id',false);
        InitSelect('dj_jth', null, 'jichushuju/shebei/shebei/findAllSheBei', 'get', {gongxu:1}, 'jitaihao', 'id',false);
        // InitSelect('dj_zh', null, 'jichushuju/zhou/jingzhou/findAllJingZhou', 'get', {}, 'zhouhao', 'id',false);
        InitSelect('dj_ssgx', 1, 'common/findAllGX', 'get', {}, 'name', 'id',true);
        InitSelect('dj_zjg', null, 'common/findUser', 'get', {gxid:1}, 'ygxm', 'id',false);
        formSelects.render('addUser');
        formSelects.value('addUser',[$('#id').val()]);

        layer.open({
            type: 1,
            title: ['整经交班产量登记', 'font-size:12px;'],
            content: $("#add_form_div"),
            shade: 0.8,
            offset:'auto',
            area: ['100%', '100%'],
            btn: ['确定', '取消'],
            btnAlign: 'c',
            yes: function(index, layero) {
                if($('#dj_jc').val()==""){ verifyWindow("经长不能为空");$('#dj_jc').focus();return false;}
                if($('#dj_zht').val()==2){
                    if($('#dj_ts').val()==""){ verifyWindow("条数不能为空");$('#dj_ts').focus();return false;}
                }
                if($('#dj_hyh').val()==""){ verifyWindow("合约号不能为空");$('#dj_hyh').focus();return false;}
                var user_id_arr = formSelects.value('addUser', 'val');
                if(user_id_arr.length == 0){verifyWindow("请选择整经工!");return false;}

                var user_list = [],user_obj=new Object();
                for(var i=0; i<user_id_arr.length;i++){
                    user_obj={id:user_id_arr[i]};
                    user_list.push(user_obj);
                }
                layer.confirm('确定登记整经产量?'
                    ,function(i){
                        form.on('submit(form_add_submit)', function (data) {
                            var formData = data.field;
                            formData.users=user_list;
                            formData.jitaihao = {id:formData.jitaihao};
                            formData.banci = {id:formData.banci};
                            formData.heyuehao = {id:formData.heyuehao};
                            $.ajax({
                                url:layui.setter.host+'jiaoban/zhengjingdengji/save',
                                type:'post',
                                contentType:"application/json;charset=utf-8",
                                data:JSON.stringify(formData),
                                success:function(data){
                                    ajaxSuccess(data,table);
                                    layer.close(i);layer.close(index);
                                }
                            });
                        });
                        $("#form_add_submit").trigger('click');
                    });
            }
        });

    });

    //修改
    table.on('tool(table)',function(obj) {
        var data = obj.data;
        var id = data.id;
        //fixme  begin 当前整经工序日期班次
        laydate.render({
            elem: '#xg_dj_rq',
            type: 'date',
            value: data.riqi
        });
        $('#rq').val(data.riqi);
        dictInitSelect('xg_dj_bc', data.banci_id, 'banci', 'name', 'id', false);
        //fixme  end
        //工序传整经工序id 1
        InitSelect('xg_dj_hyh', data.heyuehao_id, 'jiaoban/zhengjingdengji/findHeYueHao', 'get', {}, 'name', 'id',false);
        InitSelect('xg_dj_jth', data.jitaihao_id, 'jichushuju/shebei/shebei/findAllSheBei', 'get', {gongxu:1}, 'jitaihao', 'id',false);
        InitSelect('xg_dj_ssgx', null, 'common/findAllGX', 'get', {}, 'name', 'id',true);
        InitSelect('xg_dj_zjg', data.users_id, 'common/findUser', 'get', {}, 'ygxm', 'id',false);
        formSelects.render('editUser');

        if(data.users_id != null){
            var arr = data.users_id.split(",");
            formSelects.value('editUser',arr);
        }
        // 1轴 2桶
        if(data.type == '轴'){
            //轴
            $('#xg_dj_ts_div').css("display","none");
        }else{
            //桶
            $('#xg_dj_ts_div').css("display","block");
        }

        if(obj.event === 'edit'){
            layer.open({
                type: 1
                ,title: '编辑: '+data.riqi+data.banci+data.jitaihao+' 整经登记产量'
                ,content: $('#edit_form_div')
                ,offset:'auto'
                ,area: ['100%', '100%']
                ,btn: ['修改', '取消']
                ,btnAlign: 'c'
                ,btn1: function(index, layero) {
                    if($('#xg_dj_jc').val()==""){ verifyWindow("经长不能为空");$('#xg_dj_jc').focus();return false;}
                    if($('#xg_dj_zht').val()==2){
                        if($('#xg_dj_ts').val()==""){ verifyWindow("条数不能为空");$('#xg_dj_ts').focus();return false;}
                    }
                    if($('#xg_dj_hyh').val()==""){ verifyWindow("合约号不能为空");$('#xg_dj_hyh').focus();return false;}
                    var user_id_arr = formSelects.value('editUser', 'val');
                    if(user_id_arr.length == 0){verifyWindow("请选择整经工!");return false;}

                    var user_list = [],user_obj=new Object();
                    for(var i=0; i<user_id_arr.length;i++){
                        user_obj={id:user_id_arr[i]};
                        user_list.push(user_obj);
                    }

                    layer.confirm('确定修改 '+data.riqi+data.banci+data.jitaihao+' 整经登记产量?'
                        ,function(i){
                            form.on('submit(form_edit_submit)', function (data) {
                                var formData = data.field;
                                formData.id = id;
                                formData.users=user_list;
                                formData.jitaihao = {id:formData.jitaihao_id};
                                formData.banci = {id:formData.banci_id};
                                formData.heyuehao = {id:formData.heyuehao_id};

                                $.ajax({
                                    url:layui.setter.host+'jiaoban/zhengjingdengji/save',
                                    type:'post',
                                    contentType:"application/json;charset=utf-8",
                                    data:JSON.stringify(formData),
                                    success:function(data){
                                        ajaxSuccess(data,table);
                                        layer.close(i);layer.close(index);
                                    }
                                });
                            });

                            $("#form_edit_submit").trigger('click');
                        });
                },
                success:function(){
                    //渲染表格数据
                    form.val('form_edit',data);
                },
            });
        }else if(obj.event === 'del'){
            var obj = {id:id};
            layer.confirm('确定删除 '+data.riqi+data.banci+data.jitaihao+' 整经登记产量?'
                ,function(i){
                    $.ajax({
                        url:layui.setter.host+'jiaoban/zhengjingdengji/delete',
                        type:'post',
                        contentType:"application/json;charset=utf-8",
                        data:JSON.stringify(obj),
                        success:function(data){
                            ajaxSuccess(data,table);
                            layer.close(i);
                        }
                    });
                });
        }
    });


    /**
     * 新增
     * 整经机台号是分批：若选桶，则轴号，经织轴类型隐藏，条数显示；若选轴，默认为经轴，轴号，经织轴类型显示，条数隐藏
     * 整经机台号是分条：若选桶，则轴号，经织轴类型隐藏，条数显示；若选轴，默认为织轴，轴号，经织轴类型显示，条数隐藏
     */
    /*form.on('select(dj_jth)',function () {
        var reg = RegExp(/分批/);
        var jitaihao = $('#dj_jth option:selected').text();
        var type = $('#dj_zht').val(); // 1轴 2桶
        if(reg.test(jitaihao)){
            //分批整经
            $('#dj_jzz').val(1);//默认显示经轴
            form.render();
            if(type == 1){
                //轴
                $('#dj_zh_div').css("display","block");
                $('#dj_jzz_div').css("display","block");
                $('#dj_ts_div').css("display","none");
            }else{
                //桶
                $('#dj_zh_div').css("display","none");
                $('#dj_jzz_div').css("display","none");
                $('#dj_ts_div').css("display","block");
            }
        }else{
            //分条整经
            $('#dj_jzz').val(2);//默认显示织轴
            form.render();
            if(type == 1){
                //轴
                $('#dj_zh_div').css("display","block");
                $('#dj_jzz_div').css("display","block");
                $('#dj_ts_div').css("display","none");
            }else{
                //桶
                $('#dj_zh_div').css("display","none");
                $('#dj_jzz_div').css("display","none");
                $('#dj_ts_div').css("display","block");
            }
        }
    });*/
    form.on('select(dj_zht)',function () {
        var type = $('#dj_zht').val(); // 1轴 2桶
        if(type == 1){
            //轴
            // $('#dj_zh_div').css("display","block");
            // $('#dj_jzz_div').css("display","block");
            $('#dj_ts_div').css("display","none");
        }else{
            //桶
            // $('#dj_zh_div').css("display","none");
            // $('#dj_jzz_div').css("display","none");
            $('#dj_ts_div').css("display","block");
        }
    });
    /*form.on('select(dj_jzz)',function () {
        var type = $('#dj_jzz').val(); // 1经轴 2织轴
        if(type == 1){
            InitSelect('dj_zh', null, 'jichushuju/zhou/jingzhou/findAllJingZhou', 'get', {}, 'zhouhao', 'id',false);
        }else{
            InitSelect('dj_zh', null, 'jichushuju/zhou/zhizhou/findAllZhiZhou', 'get', {}, 'zhouhao', 'id',false);
        }
    });*/

    /**
     * 修改
     * 整经机台号是分批：若选桶，则轴号，经织轴类型隐藏，条数显示；若选轴，默认为经轴，轴号，经织轴类型显示，条数隐藏
     * 整经机台号是分条：若选桶，则轴号，经织轴类型隐藏，条数显示；若选轴，默认为织轴，轴号，经织轴类型显示，条数隐藏
     */
    /*form.on('select(xg_dj_jth)',function () {
        var reg = RegExp(/分批/);
        var jitaihao = $('#xg_dj_jth option:selected').text();
        var type = $('#xg_dj_zht').val(); // 1轴 2桶
        if(reg.test(jitaihao)){
            //分批整经
            $('#xg_dj_jzz').val(1);//默认显示经轴
            form.render();
            if(type == 1){
                //轴
                $('#xg_dj_zh_div').css("display","block");
                $('#xg_dj_jzz_div').css("display","block");
                $('#xg_dj_ts_div').css("display","none");
            }else{
                //桶
                $('#xg_dj_zh_div').css("display","none");
                $('#xg_dj_jzz_div').css("display","none");
                $('#xg_dj_ts_div').css("display","block");
            }
        }else{
            //分条整经
            $('#xg_dj_jzz').val(2);//默认显示织轴
            form.render();
            if(type == 1){
                //轴
                $('#xg_dj_zh_div').css("display","block");
                $('#xg_dj_jzz_div').css("display","block");
                $('#xg_dj_ts_div').css("display","none");
            }else{
                //桶
                $('#xg_dj_zh_div').css("display","none");
                $('#xg_dj_jzz_div').css("display","none");
                $('#xg_dj_ts_div').css("display","block");
            }
        }
    });*/
    form.on('select(xg_dj_zht)',function () {
        var type = $('#xg_dj_zht').val(); // 1轴 2桶
        if(type == 1){
            //轴
            // $('#xg_dj_zh_div').css("display","block");
            // $('#xg_dj_jzz_div').css("display","block");
            $('#xg_dj_ts_div').css("display","none");
        }else{
            //桶
            // $('#xg_dj_zh_div').css("display","none");
            // $('#xg_dj_jzz_div').css("display","none");
            $('#xg_dj_ts_div').css("display","block");
        }
    });
    /*form.on('select(xg_dj_jzz)',function () {
        var type = $('#xg_dj_jzz').val(); // 1经轴 2织轴
        if(type == 1){
            InitSelect('xg_dj_zh', null, 'jichushuju/zhou/jingzhou/findAllJingZhou', 'get', {}, 'zhouhao', 'id',false);
        }else{
            InitSelect('xg_dj_zh', null, 'jichushuju/zhou/zhizhou/findAllZhiZhou', 'get', {}, 'zhouhao', 'id',false);
        }
    });*/


    //合约号详情
    form.on('submit(heyuehao_filter)',function(data){
        //经纬纱信息表头
        var jws_cols = [[
            {field: 'id', title: 'id', hide: true}
            , {field: 'createTime', title: '创建时间'}
            , {templet: repNull('yuanSha.id'), hide: true}
            , {templet: repNull('yuanSha.pinming'), title: '品名'}
            , {templet: repNull('yuanSha.pihao'), title: '批号'}
            , {templet: repNull('yuanSha.gongyingshang.name'), title: '供应商'}
            , {templet: repNull('yuanSha.zhishu'), title: '支数'}
            , {templet: repNull('yuanSha.sehao'), title: '色号'}
            , {templet: repNull('yuanSha.sebie'), title: '色别'}
            , {field: 'genshu', title: '根数'}
            , {field: 'beizhu', title: '备注'}
        ]];
        //渲染经纱table
        initTable_jws('hyh_js_table', 'dingdanguanli/heyuehaoguanli/getYuanSha', 'get',jws_cols, table,{type:'jingsha',id:$(this).context.id});
        // 渲染纬纱table
        initTable_jws('hyh_ws_table', 'dingdanguanli/heyuehaoguanli/getYuanSha', 'get',jws_cols, table,{type:'weisha',id:$(this).context.id});
        layer.open({
            type: 1,
            title: ['合约号 '+$(this).context.innerHTML+' 原纱信息'],
            content: $("#hyh_ys_tck"),
            shade: 0.8,
            area: ['90%', '90%'],
            offset: "10px"
        });
        return false;
    });


    //初始化table(不带分页)
    function initTable_jws(ele, url, method,cols, table,data,doneCallBack) {
        return table.render({
            elem: "#" + ele
            , id: ele
            , url: layui.setter.host + url
            , method: method
            , cellMinWidth: 80
            , cols: cols
            , where: data
            , done: function (res) {
                if (typeof(doneCallBack) === "function") {
                    doneCallBack(res);
                }
            }
        });
    }


    // 丰富列配置功能
    function formatColumns(cols) {
        for(var i = 0; i < cols.length; i++) {
            var col = cols[i];
            if(col.field == 'heyuehao'){
                col.templet = "<div><div id='{{d.heyuehao_id}}' lay-submit lay-filter='heyuehao_filter' title=\"点击查看合约号详情\">{{d.heyuehao}}</div></div>"
            }
        }
        return cols;
    }

    /**
     * 请求数据字典初始化select选项框
     * @param eleId
     * @param selectedId
     */
    function dictInitSelect(eleId, selectedId, dictCode, key, val, isAll) {
        $.ajax({
            url: layui.setter.host + 'common/findAllDictVal',
            async: false,
            data: {
                code: dictCode
            },
            type: 'get',
            success: function (data) {
                var dict_data = {
                    code: 0,
                    data: data.data.dicts,
                    message: "查询成功"
                };
                initDownList(dict_data, eleId, selectedId, key, val, isAll);
                form.render();
            }
        });
    }

    /**
     * 渲染select
     * @param eleId select原素id
     * @param selectedId 默认选中值 value
     * @param url 查询值的路径
     * @param type 类型
     * @param data 查询的参数
     * @param key   取值的key-name
     * @param val   取值的val-name
     * @constructor
     */
    function InitSelect(eleId, selectedId, url, type, data, key, val,isall) {
        $.ajax({
            url: layui.setter.host + url,
            async: false,
            data: data,
            type: type,
            success: function (data) {
                initDownList(data, eleId, selectedId, key, val, isall);
                form.render();
            }
        });
    }

    exports('zhengjingdengji', {})
});