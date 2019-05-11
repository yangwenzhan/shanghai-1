layui.define(['table', 'form', 'laydate'], function(exports){
    var $ = layui.$
        ,table = layui.table
        ,form = layui.form
        ,laydate = layui.laydate;
    //年初始化
    laydate.render({
        elem: '#cx_nian',
        type: 'year',
        change: function(value, date, endDate) {
            $('#layui-laydate1').css("display", 'none');
            $('#cx_nian').val(value);
        },
    });

    dictInitSelect('add_kaishibanci,add_jieshubanci', 'banci');

    var cols = [
        {field: 'id', title: 'id',hide:true}
        ,{field: 'nian', sort:true,title: '年份'}
        ,{field: 'yue',sort:true, title: '月份'}
        ,{field: 'kaishiriqi',sort:true, title: '开始日期'}
        ,{templet: repNull('kaishibanci.name'),sort:true, title: '开始班次', field:'kaishibanci.name'}
        ,{field: 'jieshuriqi',sort:true, title: '结束日期'}
        ,{templet: repNull('jieshubanci.name'), title: '结束班次', field:'jieshubanci.name'}
        ,{align: 'center',title: '操作',toolbar: '#barDemo'}
    ];
    initTable("table", 'jichushezhi/pancunyue/findAll', 'get',[cols], table,"form");

    form.on('submit(form_search)',function(data){
        var field = getParams("form");
        table.reload('table',{where:field});
        return false;
    });

    //新增
    $("#add_btn").on("click", function() {
        laydate.render({
            elem: '#add_nian',
            type: 'year',
            value: new Date().getFullYear(),
            change: function(value, date, endDate) {
                $('#layui-laydate2').css("display", 'none');
                $('#add_nian').val(value);
            },
        });
        laydate.render({
            elem: '#add_kaishiriqi',
            value: new Date()
        });
        laydate.render({
            elem: '#add_jieshuriqi',
            value: new Date()
        });

        var set_yue = new Date().getMonth();
        if(set_yue<10){
            set_yue="0"+set_yue;
        }
        $('#add_yue').find("option").each(function(i){
            if($(this).val()==set_yue){
                $(this).prop("selected",true);
            }
        });
        form.render();
        layer.open({
            type: 1,
            title: ['添加盘存', 'font-size:12px;'],
            content: $("#add_form_div"),
            shadeClose: true, //点击遮罩关闭层
            shade: 0.8,
            area: ['70%', '70%'],
            btn: ['确定', '取消'],
            btnAlign: 'c',
            yes: function(index, layero) {
                if($('#add_jieshuriqi').val()=="" || $("#add_jieshuriqi")==""){
                    layer.open({
                        title:"消息提醒",content:"请将信息填写完整!",skin:"layui-layer-molv",offset: 'auto',time:1500,btn:[],shade: 0,anim: -1,icon:5
                    });
                    return false;
                }

                layer.confirm('确定新增?'
                    ,function(i){
                        form.on('submit(form_add_submit)', function (data) {
                            var formData = data.field;
                            formData.kaishibanci={id:$('#add_kaishibanci').val(),value:$('#add_kaishibanci option:selected').attr("name")};
                            formData.jieshubanci={id:$('#add_jieshubanci').val(),value:$('#add_jieshubanci option:selected').attr("name")};
                            $.ajax({
                                url:layui.setter.host+'jichushezhi/pancunyue/addPanCunYue',
                                type:'post',
                                contentType:"application/json;charset=utf-8",
                                data:JSON.stringify(formData),
                                success:function(data){
                                    ajaxSuccess(data,table);
                                    if(data.code==666){
                                        layer.open({
                                            title:"消息提醒",content:data.message,skin:"layui-layer-molv",offset: 'auto',time:3000,btn:[],shade: 0,anim: -1,icon:5
                                        });
                                    }else{
                                        layer.close(i);layer.close(index);
                                    }
                                }
                            });
                        });
                        $("#form_add_submit").trigger('click');
                    });
            }
        });
    });

    table.on('tool(table)',function(obj){
        var data = obj.data;
        laydate.render({
            elem: '#edit_kaishiriqi',
            value: data.kaishiriqi
        });
        laydate.render({
            elem: '#edit_jieshuriqi',
            value: data.jieshuriqi
        });
        if(obj.event === 'edit'){
            layer.open({
                type: 1
                ,title: '编辑 '+data.nian+"  "+data.yue+" 盘存日期"
                ,content: $('#edit_form_div')
                ,offset:'auto'
                ,area: ['70%', '70%']
                ,btn: ['修改', '取消']
                ,btnAlign: 'c'
                ,btn1: function(index, layero) {
                    if($('#edit_jieshuriqi').val()=="" || $("#edit_jieshuriqi")==""){
                        layer.open({
                            title:"消息提醒",content:"请将信息填写完整!",skin:"layui-layer-molv",offset: 'auto',time:1500,btn:[],shade: 0,anim: -1,icon:5
                        });
                        return false;
                    }

                    layer.confirm('确定要修改'+data.nian+'  '+data.yue+'盘存日期吗?'
                        ,function(i){
                            form.on('submit(form_edit_submit)', function (data) {
                                var formData = data.field;
                                formData.kaishibanci={id:$('#edit_kaishibanci').val()};
                                formData.jieshubanci={id:$('#edit_jieshubanci').val()};
                                $.ajax({
                                    url:layui.setter.host+'jichushezhi/pancunyue/updatePanCunYue',
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
            var obj = {id:data.id};
            layer.confirm('确定要删除'+data.nian+'  '+data.yue+'盘存日期吗?'
                ,function(i){
                    $.ajax({
                        url:layui.setter.host+'jichushezhi/pancunyue/deletePanCunYue',
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
     * 请求数据字典初始化select选项框
     * @param eleId
     * @param selectedId
     */
    function dictInitSelect(eleId, dictCode) {
        $.ajax({
            url: layui.setter.host + 'common/findAllDictVal',
            async: false,
            data: {
                code: dictCode
            },
            type: 'get',
            success: function (data) {
                var dictData = data.data.dicts.sort(sortSort);
                var reg = RegExp(/,/);
                var renderSelectId=[];
                if(reg.test(eleId)) {
                    renderSelectId = eleId.split(',');
                }
                for(var i=0;i<renderSelectId.length;i++){
                    $('#' + renderSelectId[i]).html("");
                    var select = document.getElementById(renderSelectId[i]);
                    for(var j=0;j<dictData.length;j++){
                        var option = document.createElement("option");
                        option.setAttribute("name", dictData[j].value);
                        option.setAttribute("value", dictData[j].id);
                        option.innerHTML = dictData[j].name;
                        select.appendChild(option);
                    }
                    form.render();
                }

            }
        });
    }

    //根据sort排序
    function sortSort(a,b){
        return a.sort-b.sort;
    }

    exports('pancunyue', {})
});