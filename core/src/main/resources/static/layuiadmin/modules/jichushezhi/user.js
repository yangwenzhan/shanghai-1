layui.define(['table', 'form', 'laydate', 'formSelects'], function(exports){
    var $ = layui.$
        ,table = layui.table
        ,form = layui.form
        ,laydate = layui.laydate
        ,formSelects = layui.formSelects;

    dynamicForm(
        {code:"lunban",hasNull:true,defaultValue:""}
        ,{code:"gongxu",hasNull:true,defaultValue:""}
        ,{code:"zu",hasNull:true,defaultValue:""}
        ,{code:"role",hasNull:true,defaultValue:""}
        );
    form.render();
    var cols = [[
        {fixed:'left',checkbox:true}
        ,{field: 'id', title: 'id',hide:true}
        ,{field: 'username', title: '工号姓名',width:120,sort:true,fixed:true}
        ,{field: 'gongxu', title: '工序',width:70,sort:true}
        ,{field: 'lunban', title: '轮班',width:70,sort:true}
        ,{field: 'zu', title: '组',width:60,sort:true}
        ,{field: 'roles', title: '角色',width:150,sort:true}
        ,{field: 'sex', title: '性别',width:70,sort:true}
        ,{field: 'shifouzaizhi', title: '在职',width:70,sort:true}
        ,{field: 'email', title: '电子邮箱',width:140,sort:true}
        ,{field: 'mobile', title: '联系电话',width:120,sort:true}
        ,{field: 'birthday', title: '生日',width:120,sort:true}
        ,{fixed: 'right',width: 250,align: 'center',title: '操作',toolbar: '#barDemo'}
    ]];
    initTable ("table", 'jichushezhi/user/findAllUser', 'get',cols, table,"form");

    //监听搜索
    form.on('submit(form_search)', function(data){
        var field = data.field;
        table.reload('table', {
            where: field
        });
        return false;
    });

    //设置组
    $('#set_zu_btn').on("click",function () {
        var user_ids='';
        var checkStatus = table.checkStatus('table');
        var data = checkStatus.data;

        if(data.length<=0){
            layer.open({
                title:"消息提醒",
                content:"至少选中一行!",
                skin:"layui-layer-molv",
                time:3000,
                anim: -1,
                icon:5
            });
            return false;
        }
        for(var i=0;i<data.length;i++){
            user_ids+=data[i].id+',';
        }

        initZu('set_zu');
        layer.open({
            type: 1,
            title: ['设置组', 'font-size:12px;'],
            content: $("#set_zu_div"),
            shadeClose: true, //点击遮罩关闭层
            shade: 0.8,
            offset:'auto',
            area: ['70%', '70%'],
            btn: ['确定', '取消'],
            btnAlign: 'c',
            yes: function(index, layero) {
                layer.confirm(
                    '确定修改组?',
                    {title:'提示'},function (i){
                        $.ajax({
                            url:layui.setter.host+'jichushezhi/user/updateUserZu',
                            type:'post',
                            data:{
                                zu:$('#set_zu').val(),
                                user_ids:user_ids
                            },
                            success:function(data){
                                ajaxSuccess(data,table);
                                layer.close(i);layer.close(index);
                            }
                        });
                    });
            },
            offset: '150px'
        });
    });

    //设置角色
    $('#set_role_btn').on("click",function () {
        var user_ids=[];
        var checkStatus = table.checkStatus('table');
        var data = checkStatus.data;

        if(data.length<=0){
            layer.open({
                title:"消息提醒",
                content:"至少选中一行!",
                skin:"layui-layer-molv",
                time:3000,
                anim: -1,
                icon:5
            });
            return false;
        }
        for(var i=0;i<data.length;i++){
            user_ids.push(data[i].id);
        }

        // initRole('set_role',null);
        layer.open({
            type: 1,
            title: ['设置角色', 'font-size:12px;'],
            content: $("#set_role_div"),
            shadeClose: true, //点击遮罩关闭层
            shade: 0.8,
            offset:'auto',
            area: ['70%', '70%'],
            btn: ['确定', '取消'],
            btnAlign: 'c',
            yes: function(index, layero) {
                layer.confirm(
                    '确定修改角色?',
                    {title:'提示'},function (i){
                        $.ajax({
                            url:layui.setter.host+'jichushezhi/user/updateUserRole',
                            type:'post',
                            data:{
                                role_ids:formSelects.value('selectRole', 'val'),
                                user_ids:user_ids
                            },
                            traditional:true,
                            success:function(data){
                                ajaxSuccess(data,table);
                                layer.close(i);layer.close(index);
                            }
                        });
                    });
            },
            offset: '150px'
        });
    });

    //添加用户
    $("#add_user_btn").on("click", function() {

        //数据清空
        $('#add_username').val("");
        $('#add_xingming').val("");
        $('#add_email').val("");
        $('#add_mobile').val("");


        laydate.render({
            elem: '#add_birthday',
            value: new Date(),
            max: 0
        });

        layer.open({
            type: 1,
            title: ['添加用户', 'font-size:12px;'],
            content: $("#div_form_add"),
            shadeClose: true, //点击遮罩关闭层
            shade: 0.8,
            offset:'auto',
            area: ['90%', '90%'],
            btn: ['确定', '取消'],
            btnAlign: 'c',
            yes: function(index, layero) {
                //必输项检验
                if($('#add_username').val()==""){
                    layer.open({
                        title:"消息提醒",
                        content:"工号不能为空",
                        skin:"layui-layer-molv",
                        offset: 'auto',
                        time:3000,
                        btn:[],
                        shade: 0,
                        anim: -1,
                        icon:5
                    });
                    $('#add_username').focus();
                    return false;
                }
                if($('#add_xingming').val()==""){
                    layer.open({
                        title:"消息提醒",
                        content:"姓名不能为空",
                        skin:"layui-layer-molv",
                        offset: 'auto',
                        time:3000,
                        btn:[],
                        shade: 0,
                        anim: -1,
                        icon:5
                    });
                    $('#add_xingming').focus();
                    return false;
                }

                //邮箱格式验证
                var email = $.trim($('#add_email').val());
                var isEmail = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;

                if(!(email=="")){
                    if(!(isEmail.test(email))){
                        layer.open({
                            title:"消息提醒",
                            content:"邮箱格式不正确",
                            skin:"layui-layer-molv",
                            offset: 'auto',
                            time:3000,
                            btn:[],
                            shade: 0,
                            anim: -1,
                            icon:5
                        });
                        $('#add_email').focus();
                        return false;
                    }
                }

                layer.confirm('确定新增该员工?'
                    ,function(i){
                        form.on('submit(form_add_submit)', function (data) {
                            var formData = data.field;

                            var gongxu = {id:$('#add_gongxu').val()};
                            var lunban = {id:$('#add_lunban').val()};
                            var user_yuanGong={zu:$('#add_zu').val(),gongxu:gongxu,lunban:lunban};

                            formData.user_yuanGong=user_yuanGong;
                            var role_arry = formSelects.value('add_select_role', 'val');
                            var role_obj = [];
                            for(var i=0;i<role_arry.length;i++){
                                var id={id:role_arry[i]};
                                role_obj.push(id);
                            }
                            formData.roles=role_obj;

                            $.ajax({
                                url:layui.setter.host+'jichushezhi/user/saveUser',
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
        $('#add_username').focus();

    });

    //监听工具条
    table.on('tool(table)', function(obj){
        var data = obj.data;
        if(obj.event === 'edit'){
            layer.open({
                type: 1
                ,title: '编辑用户  '+data.ghxm
                ,content: $('#div_form_edit')
                ,offset:'auto'
                ,area: ['90%', '90%']
                ,btn: ['修改', '取消']
                ,btnAlign: 'c'
                ,btn1: function(index, layero){
                    if($('#edit_xingming').val()==""){
                        layer.open({
                            title:"消息提醒",
                            content:"姓名不能为空",
                            skin:"layui-layer-molv",
                            offset: 'auto',
                            time:3000,
                            btn:[],
                            shade: 0,
                            anim: -1,
                            icon:5
                        });
                        $('#edit_xingming').focus();
                        return false;
                    }

                    //邮箱格式验证
                    var email = $.trim($('#edit_email').val());
                    var isEmail = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;

                    if(!(email=="")){
                        if(!(isEmail.test(email))){
                            layer.open({
                                title:"消息提醒",
                                content:"邮箱格式不正确",
                                skin:"layui-layer-molv",
                                offset: 'auto',
                                time:3000,
                                btn:[],
                                shade: 0,
                                anim: -1,
                                icon:5
                            });
                            $('#edit_email').focus();
                            return false;
                        }
                    }

                    layer.confirm('确定要修改员工信息么?'
                        ,function(i){
                            form.on('submit(form_edit_submit)', function (data) {
                                var formData = data.field;

                                var gongxu = {id:$('#edit_gongxu').val()};
                                var lunban = {id:4};
                                var user_yuanGong={zu:$('#edit_zu').val(),gongxu:gongxu,lunban:lunban,id:data.user_yg_id};

                                formData.user_yuanGong=user_yuanGong;
                                var role_arry = formSelects.value('edit_select_role', 'val');
                                var role_obj = [];
                                for(var i=0;i<role_arry.length;i++){
                                    var id={id:role_arry[i]};
                                    role_obj.push(id);
                                }
                                formData.roles=role_obj;

                                $.ajax({
                                    url:layui.setter.host+'jichushezhi/user/updateUserInfo',
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
                }
                ,success: function(){
                    //渲染表格数据
                    laydate.render({
                        elem: '#edit_birthday',
                        max: 0
                    });

                }
            });
        }else if(obj.event === 'lizhi'){
            layer.confirm(
                "确定  "+data.ghxm+'  离职?',
                {title:'离职提示'},function (index){
                    $.ajax({
                        url:layui.setter.host+'jichushezhi/user/updateUserZaiZhi',
                        type:'get',
                        data:{user_id:data.id,zaizhi:0},
                        success:function(data){
                            ajaxSuccess(data,table);
                            layer.close(index);
                        }
                    });
                });
        }else if(obj.event === 'fuzhi') {
            layer.confirm(
                "确定  "+data.ghxm+'  复职?',
                {title:'复职提示'},function (index){
                    $.ajax({
                        url:layui.setter.host+'jichushezhi/user/updateUserZaiZhi',
                        type:'get',
                        data:{user_id:data.id,zaizhi:1},
                        success:function(data){
                            ajaxSuccess(data,table);
                            layer.close(index);
                        }
                    });
                });
        }else if(obj.event === 'reset'){
            layer.confirm(
                "确定将  "+data.ghxm+'  密码重置为 123456 ?',
                {title:'重置密码提示'},function (index){
                    $.ajax({
                        url:layui.setter.host+'jichushezhi/user/updateUserPwd',
                        type:'get',
                        data:{user_id:data.id,pwd:123456},
                        success:function(data){
                            ajaxSuccess(data,table);
                            layer.close(index);
                        }
                    });
                });
        }
    });

    //根据sort排序
    function sortPXH(a,b){
        return a.sort-b.sort;
    }


    exports('user', {})
});