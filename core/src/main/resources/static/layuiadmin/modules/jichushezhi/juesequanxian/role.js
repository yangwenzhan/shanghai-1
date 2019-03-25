layui.define(['table', 'form', 'laytpl'], function(exports){
    var $ = layui.$
        ,table = layui.table
        ,form = layui.form
        ,laytpl = layui.laytpl;

    //表格初始化
    initTable();
    function initTable(){
        table.render({
            elem: '#table'
            ,method:'GET'
            ,url: layui.setter.host + 'jichushezhi/juesequanxian/role/findAll'
            ,cols: [[
                {field: 'id', title: 'id',hide:true}
                ,{field: 'name', title: '角色名'}
                ,{field: 'beizhu', title: '角色描述'}
                ,{fixed: 'right',width: 350,align: 'center',title: '操作',toolbar: '#barDemo'}
            ]]
        });
    }

    //添加角色
    $("#add_role_btn").on("click", function() {
        layer.open({
            type: 1,
            title: ['添加角色', 'font-size:12px;'],
            content: $("#div_form_add"),
            shadeClose: true, //点击遮罩关闭层
            shade: 0.8,
            area: ['50%', '50%'],
            btn: ['确定', '取消'],
            btnAlign: 'c',
            yes: function(index, layero) {
                layer.confirm('确定新增该角色?'
                    ,function(i){
                        form.on('submit(form_add_submit)', function (data) {
                            var formData = data.field;
                            $.ajax({
                                url:layui.setter.host+'jichushezhi/juesequanxian/role/saveRole',
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
            },
            offset: '150px'
        });
    });

    //监听工具条
    table.on('tool(table)', function(obj){
        var data = obj.data;
        if(obj.event === 'del'){
            layer.confirm(
                "确定要删除  "+data.name+'?',
                {title:'删除提示'},function (index){
                    $.ajax({
                        url:layui.setter.host+'jichushezhi/juesequanxian/role/deleteRole',
                        type:'get',
                        data:{role_id:data.id},
                        success:function(data){
                            ajaxSuccess(data,table);
                            layer.close(index);
                        }
                    });
                });
        }else if(obj.event === 'edit'){
            layer.open({
                type: 1
                ,title: '编辑角色  '+data.name
                ,content: $('#div_form_edit')
                ,area: ['60%', '60%']
                ,btn: ['修改', '取消']
                ,btn1: function(index, layero){
                    layer.confirm('确定要修改角色么?'
                        ,function(i){

                            form.on('submit(form_edit_submit)', function (data) {
                                var formData = data.field;
                                $.ajax({
                                    url:layui.setter.host+'jichushezhi/juesequanxian/role/updateRole',
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
                    form.val('form_edit',data);
                }
            });
        }else if(obj.event === 'detail'){

            initTree(data);
            layer.open({
                type: 1,
                title: ['权限配置  '+data.name, 'font-size:12px;'],
                content: $("#div_power_edit"),
                shadeClose: true, //点击遮罩关闭层
                shade: 0.8,
                area: ['60%', '80%'],
                btn: ['确定', '取消'],
                btnAlign: 'c',
                yes: function(index, layero) {
                    //获取选中的节点id
                    var treeObj=$.fn.zTree.getZTreeObj("treeDemo");
                    var nodes=treeObj.getCheckedNodes(true);
                    var str = "";
                    for(var i=0;i<nodes.length;i++){
                        str += nodes[i].id + ",";
                    }
                    if (str.length == 0) {
                        layer.alert("请选择要分配的权限");
                        return false;
                    }
                    //选中的权限id
                    str = str.substring(0, str.length-1);

                    console.log(str);
                    $.ajax({
                        url:layui.setter.host+'jichushezhi/juesequanxian/role/updateRolePermission',
                        type:'get',
                        data:{
                            role_id:data.id,
                            permission_ids:str
                        },
                        success:function(data){
                            ajaxSuccess(data,table);
                            layer.close(index);
                        }
                    });

                },
                offset: '60px'
            });
        }else if(obj.event === 'user') {

            var usertpl = $("#user_list_tpl").html()
                ,container = $('#user_list_container');

            if(data.users.length==0){
                container.html("<div>暂无用户列表</div>");
            }else{
                laytpl(usertpl).render(data.users, function(content){
                    container.html(content);
                });
            }
            form.render();
            form.on('checkbox(delete_user_from_role)', function(userdata){
                layer.confirm('确定将用户从该角色中移除？', {
                    btn: ['确定','取消'] //按钮
                }, function(){
                    $.ajax({
                        url:layui.setter.host+'jichushezhi/juesequanxian/role/deleteUserFromRole',
                        type:'get',
                        data:{
                            role_id:data.id,
                            user_id:userdata.value
                        },
                        success : function(msg) {
                            $(userdata.elem).remove();
                            $(userdata.othis).remove();
                            form.render();
                            initTable();
                            layer.open({
                                title:"消息提醒",
                                content:msg.message,
                                skin:"layui-layer-molv",
                                offset: 'rb',
                                time:3000,
                                btn:[],
                                shade: 0,
                                anim: -1,
                                icon:6
                            });
                        },
                    });
                }, function(){
                    $(userdata.elem).attr("checked", true);
                    form.render();
                });
            });

            layer.open({
                type : 1,
                title : [ data.name + '  用户列表 '],
                content : $("#user_list_window"),
                shadeClose : true, //点击遮罩关闭层
                shade : 0.6,
                area : [ '60%', '80%' ],
                btn : ['关闭' ],
                btnAlign : 'c',
                yes : function(index, layero) {
                    layer.close(index)
                }
            });
        }
    });

    //初始化权限树
    function initTree(data){
        $.ajax({
            type: "get",
            url: layui.setter.host+'jichushezhi/juesequanxian/role/getTree',
            data:{role_id:data.id},
            success: function(data) {
                if(data.code == 0){
                    var setting = {
                        view: {
                            selectedMulti: false
                        },
                        check: {
                            enable: true
                        },
                        data: {
                            simpleData: {
                                enable: true
                            }
                        },
                        edit: {
                            enable: false
                        },
                        callback: {
                            onCheck: function(e, id, node) {
                                var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
                                var nodes = treeObj.getChangeCheckedNodes();
                            }
                        }
                    };
                    var zNodes = data.data;
                    $.fn.zTree.init($("#treeDemo"), setting, zNodes);
                }else {
                    layer.msg(data.msg);
                }
            }
        });

    }

    exports('role', {})
});