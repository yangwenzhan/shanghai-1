layui.define(['table', 'form'], function(exports){
    var $ = layui.$
        ,table = layui.table
        ,form = layui.form;

    table.render({
        elem: '#table'
        ,limit:100000
        ,method:'GET'
        ,url: layui.setter.host + 'jichushezhi/paiban/gongxupaiban/findAllGXPB'
        ,cols: [[
            {field: 'id', title: 'id',hide:true}
            ,{title: '工序', templet: repNull('gongxu.name')}
            ,{title: '运转方式', templet: repNull('pb_yunZhuanFangShi_xiangqing.yunZhuanFangShi.name')}
            ,{fixed: 'right',align: 'center',title: '操作',toolbar: '#barDemo'}
        ]]
    });

    form.on('select(edit_yzfs)', function(data) {
        localStorage.removeItem("cur_position");
        $.ajax({
            url: layui.setter.host + 'jichushezhi/paiban/yunzhuanfangshi/findAllById',
            data: {
                id:$('#edit_yzfs').val()
            },
            type: 'get',
            success: function (data) {
                var data = data.data.yunZhuanFangShi_xiangqingSet;
                if(data.length>0){
                    data = data.sort(sortPXH);
                    var cols = [
                        {field:'cur_num', width:150, fixed:'left',title:'当前运转位置'}
                        ,{field: 'sort', width:150, title:"排序"}
                        ,{title: '班次', width: 150, templet: repNull('banci.name')}
                        ,{title: '轮班', width: 150, templet: repNull('lunban.name')}
                    ];
                    cols = fixedColumn(cols,'upd');
                    table.render({
                        elem: '#edit_table'
                        ,limit:100000
                        ,cols: [cols]
                        ,data: data
                    });
                }
            }
        });


        //控制复选框变为单选框
        $('#see_table .layui-form-checkbox').click(function() {

            alert('aaaaa');

            $('#div_form_edit .layui-form-checkbox').removeClass('layui-form-checked');
            $(this).addClass('layui-form-checked');
        });


    });

    table.on('tool(table)', function(obj){
        var data = obj.data;
        if(obj.event === 'detail'){
            //查看运转方式详情
            initYunZhuanInfo(data);
            layer.open({
                type: 1,
                title: ['查看 '+data.gongxu.name+' 运转详情', 'font-size:12px;'],
                content: $("#div_form_see"),
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
            initYZFS('edit_yzfs',data.pb_yunZhuanFangShi_xiangqing.yunZhuanFangShi.id);
            search_editYZFS(data);
            // 修改
            layer.open({
                type: 1,
                title: ['修改  '+data.gongxu.name, 'font-size:12px;'],
                content: $("#div_form_edit"),
                shade: 0.8,
                area: ['80%', '80%'],
                btn: ['确定', '取消'],
                btnAlign: 'c',
                yes: function(index_one, layero) {
                    layer.confirm('确定要修改该运转方式吗？ ', {
                            btn: ['确定', '取消'],
                        },
                        function(index, layero) {
                            updGx_Yzfs(data, index_one);
                        });
                },
                btn2: function(index, layero) {
                    //按钮【按钮二】的回调
                    layer.close(index);
                }
            });
        }
    });

    function initYZFS(eleId,selectedId) {
        $.ajax({
            url: layui.setter.host + 'jichushezhi/paiban/yunzhuanfangshi/findAllYZFS',
            type: 'get',
            success: function (data) {
                initDownList(data, eleId, selectedId, 'name', 'id', false);
                form.render();
            }
        });
    }

    //修改渲染
    function search_editYZFS(data){
        var yz_data = [];
        yz_data = data.pb_yunZhuanFangShi_xiangqing.yunZhuanFangShi.yunZhuanFangShi_xiangqingSet;
        yz_data = yz_data.sort(sortPXH);
        var cols = [
            {field: 'id', title: 'id',hide:true}
            ,{field:'cur_num', width:150, fixed:'left',title:'当前运转位置'}
            ,{field: 'sort', width:150, title: '排序号'}
            ,{title: '班次', width: 150, templet: repNull('banci.name')}
            ,{title: '轮班', width: 150, templet: repNull('lunban.name')}
        ];
        cols = fixedColumn(cols,'upd');
        table.render({
            elem: '#edit_table'
            ,data:yz_data
            ,limit:10000
            ,cols: [cols]
        });
        $('.cur_yzfs_num_upd').each(function (i) {
            if($(this).val()==data.sort){
                $(this).prop("checked",true);
            }
        });
        form.render();
    }


    //详情
    function initYunZhuanInfo(data){
        console.log(data)

        var yz_data = [];
        yz_data = data.pb_yunZhuanFangShi_xiangqing.yunZhuanFangShi.yunZhuanFangShi_xiangqingSet;
        yz_data = yz_data.sort(sortPXH);

        var cols = [
            {field: 'id', title: 'id',hide:true}
            ,{field:'cur_num', width:150, fixed:'left',title:'当前运转位置'}
            ,{field: 'sort', width:150, title: '排序号'}
            ,{title: '班次', width: 150, templet: repNull('banci.name')}
            ,{title: '轮班', width: 150, templet: repNull('lunban.name')}
        ];
        cols = fixedColumn(cols,'sel');
        table.render({
            elem: '#see_table'
            ,data:yz_data
            ,limit:10000
            ,cols: [cols]
        });

        $('.cur_yzfs_num_sel').each(function (i) {
            console.log($(this).val())
            console.log()
           if($(this).val()==data.pb_yunZhuanFangShi_xiangqing.sort){
                $(this).prop("checked",true);
           }
        });
        form.render();
    }

    form.on('radio(radioInput)', function(data){
        localStorage.removeItem("cur_position");
        localStorage.setItem("cur_position",data.value);
    });

    //修改运转方式
    function updGx_Yzfs(data, index) {
        var cur_position = localStorage.getItem("cur_position");

        if(cur_position == null) {
            layer.open({
                title:"消息提醒",content:"请选择运转开始位置！",skin:"layui-layer-molv",offset: 'auto',btn:[],time:3000,shade: 0,anim: -1,icon:5
            });
        } else {
            $.ajax({
                url:layui.setter.host+'jichushezhi/paiban/gongxupaiban/updateGongXuYZFS',
                type:'get',
                data:{
                    xq_id:cur_position,
                    gxid:data.gongxu.id
                },
                success:function(data){
                    ajaxSuccess(data,table);
                    layer.close(index);
                }
            });
        }

    }

    //根据sort排序
    function sortPXH(a,b){
        return a.sort-b.sort;
    }

    /**
     * 2019/03/22 bjw
     * 通过三目运算符处理NULL异常
     * @param name 数据取值参数
     * @returns {string} 取值内容
     */
    function repNull(name) {
        var arr = name.split('.');
        var tem = "<div>{{ ";
        var currentObj = 'd';
        var smbds = '(';
        for (var i = 0; i < arr.length; i++) {
            currentObj += '.' + arr[i];
            if (i == (arr.length - 1)) {
                smbds += currentObj + "== null) ? '' : " + currentObj;
            } else {
                smbds += currentObj + "== null || ";
            }
        }
        tem += smbds;
        tem += ' }}</div>';
        return tem;
    }

    //丰富列配置功能
    function fixedColumn(cols, type) {
        for(var i = 0; i < cols.length; i++) {
            var col = cols[i];
            if(col.field == 'cur_num') {

                if(type == 'upd') {
                    col.templet = "#cur_yzfs_num_upd";
                } else {
                    col.templet = "#cur_yzfs_num_sel";
                }
            }
        }
        return cols;
    }

    exports('pancunyue', {})
});