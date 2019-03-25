layui.define(['table', 'form', 'laytpl'], function(exports){
    var $ = layui.$
        ,table = layui.table
        ,form = layui.form
        ,laytpl = layui.laytpl;

    initTree();
    queryPermissionByPId(1);

    function queryPermissionByPId(node_id){
        table.render({
            elem: '#table'
            ,method:'GET'
            ,url: layui.setter.host + 'jichushezhi/juesequanxian/permission/findAllByParent_id'
            ,where:{id:node_id}
            ,cols: [[
                {field: 'id', title: 'id',hide:true}
                ,{field: 'pId', title: 'pId',hide:true}
                ,{field: 'permissionCode',width:200, title: '权限编码'}
                ,{field: 'permissionName',width:200, title: '权限名称'}
                ,{fixed: 'right',width: 350,align: 'center',title: '操作',toolbar: '#barDemo'}
            ]]
        });
    }

    var node_id = "";
    var node_name = "";
    //初始化权限树
    function initTree(){
        $.ajax({
            type: "get",
            url: layui.setter.host+'jichushezhi/juesequanxian/permission/getTree',
            success: function(data) {
                if(data.code == 0){
                    var setting = {
                        view: {
                            selectedMulti: false
                        },
                        check: {
                            enable: false
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
                            onClick: function(e, id, node) {
                                var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
                                var nodes = treeObj.getSelectedNodes();
                                //加载列表
                                var sNodes = treeObj.getSelectedNodes();
                                var parentNode = sNodes[0].getParentNode();
                                if (parentNode!=null) {
                                    parentNodeName=parentNode.name;
                                }
                                node_id = node.id;
                                node_name = nodes[0].name
                                queryPermissionByPId(node_id);
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

    //添加权限
    $("#add_role_btn").on("click", function() {
        //初始化新增弹框的树
        // initTree_add();

        if(node_id == "" || node_name == "") {
            $('#parentId_add_id').val('1');
            $('#parentId_add_name').val('系统权限');
        } else {
            $('#parentId_add_id').val(node_id);
            $('#parentId_add_name').val(node_name);
        }

        layer.open({
            type: 1,
            title: ['增加权限', 'font-size:12px;'],
            content: $("#div_form_add"),
            shadeClose: true, //点击遮罩关闭层
            shade: 0.8,
            area: ['60%', '60%'],
            btn: ['确定', '取消'],
            btnAlign: 'c',
            yes: function(index, layero) {
                layer.confirm('确定新增该权限?'
                    ,function(i){
                        form.on('submit(form_add_submit)', function (data) {
                            var formData = data.field;
                            $.ajax({
                                url:layui.setter.host+'jichushezhi/juesequanxian/permission/savePermission',
                                type:'post',
                                contentType:"application/json;charset=utf-8",
                                data:JSON.stringify(formData),
                                success:function(data){
                                    ajaxSuccess(data,table);
                                    layer.close(i);layer.close(index);
                                    initTree();
                                }
                            });
                        });
                        $("#form_add_submit").trigger('click');
                    });
            },
            offset: '150px'
        });
    });

    //新增弹框的树
    /*function initTree_add(){

        treeSelect.render({
            // 选择器
            elem: '#tree_add',
            // 数据
            data: layui.setter.host + 'jichushezhi/juesequanxian/permission/getSelectTree',
            // 异步加载方式：get/post，默认get
            type: 'get',
            // 占位符
            placeholder: '修改默认提示信息',
            // 是否开启搜索功能：true/false，默认false
            search: true,
            // 点击回调
            click: function(d){
                console.log(d);
            },
            // 加载完成后的回调函数
            success: function (d) {
                console.log(d);
//                选中节点，根据id筛选
//                treeSelect.checkNode('tree', 3);

//                获取zTree对象，可以调用zTree方法
//                var treeObj = treeSelect.zTree('tree');
//                console.log(treeObj);

//                刷新树结构
//                treeSelect.refresh();
            }
        });
    }*/

    //监听工具条
    table.on('tool(table)', function(obj){
        var data = obj.data;
        if(obj.event === 'del'){
            layer.confirm(
                "确定要删除"+data.permissionName+'?',
                {title:'删除提示'},function (index){
                    $.ajax({
                        url:layui.setter.host+'jichushezhi/juesequanxian/permission/deletePermission',
                        type:'get',
                        data:{id:data.id},
                        success:function(data){
                            ajaxSuccess(data,table);
                            layer.close(index);
                            initTree();
                        }
                    });
                });
        }else if(obj.event === 'edit'){
            layer.open({
                type: 1
                ,title: '编辑权限'
                ,content: $('#div_form_edit')
                ,area: ['60%', '60%']
                ,btn: ['修改', '取消']
                ,btn1: function(index, layero){
                    layer.confirm('确定要修改权限么?'
                        ,function(i){

                            form.on('submit(form_edit_submit)', function (data) {
                                var formData = data.field;
                                $.ajax({
                                    url:layui.setter.host+'jichushezhi/juesequanxian/permission/updatePermission',
                                    type:'post',
                                    contentType:"application/json;charset=utf-8",
                                    data:JSON.stringify(formData),
                                    success:function(data){
                                        ajaxSuccess(data,table);
                                        layer.close(i);layer.close(index);
                                        initTree();
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
        }else if(obj.event === 'role') {

            var usertpl = $("#role_list_tpl").html()
                ,container = $('#role_list_container');

            if(data.roles.length==0){
                container.html("<div>暂无角色列表</div>");
            }else{
                laytpl(usertpl).render(data.roles, function(content){
                    container.html(content);
                });
            }
            form.render();
            form.on('checkbox(delete_role_from_permission)', function(roledata){
                layer.confirm('确定将角色从该权限中移除？', {
                    btn: ['确定','取消'] //按钮
                }, function(){
                    $.ajax({
                        url:layui.setter.host+'jichushezhi/juesequanxian/permission/deleteRole',
                        type:'get',
                        data:{
                            permission_id:data.id,
                            role_id:roledata.value
                        },
                        success : function(msg) {
                            $(roledata.elem).remove();
                            $(roledata.othis).remove();
                            form.render();
                            queryPermissionByPId(data.parentId);
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
                    $(roledata.elem).attr("checked", true);
                    form.render();
                });
            });

            layer.open({
                type : 1,
                title : [ data.permissionName + '角色列表 '],
                content : $("#role_list_window"),
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



    exports('permission', {})
});

