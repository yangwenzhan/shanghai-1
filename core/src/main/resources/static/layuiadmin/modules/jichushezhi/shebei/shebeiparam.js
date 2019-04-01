layui.define(['table', 'form'], function(exports){
    var table = layui.table
        ,form = layui.form;

    /*待完成：下拉框未初始化值 工序机型参数类别联动*/

    var cols =  [
        {fixed:'left',checkbox:true}
        ,{field: 'id', title: 'id',hide:true}
        ,{field: 'rownum', title: '',width:50}
        ,{field: 'name',sort:true, title: '参数类别'}
        ,{field: 'csm',sort:true, title: '参数名'}
        ,{field: 'danwei',sort:true, title: '参数单位'}
        ,{field: 'cunchuzhouqi',sort:true, title: '存储周期(秒)'}
        ,{field: 'cunchushichang',sort:true, title: '存储时长(时)'}
        ,{field: 'baojing_flag',sort:true, title: '是否报警'}
        ,{field: 'zhanshi_flag',sort:true, title: '是否报警'}
        ,{field: 'cunchu_flag',sort:true, title: '是否报警'}
        ,{field: 'xuhao',sort:true, title: '排序号'}
        ,{align: 'center',title: '操作',toolbar: '#barDemo'}
    ];
    cols = fixedColumn(cols);
    initTable_all("table", 'jichushuju/shebei/shebeiparam/findAll', 'get',[cols], table, "form");

    table.on('tool(table)',function(obj){
        var data = obj.data;
        if(obj.event === 'edit'){
            layer.open({
                type: 1
                ,title: '编辑 '+data.csm
                ,content: $('#edit_form_div')
                ,offset:'auto'
                ,area: ['80%', '60%']
                ,btn: ['修改', '取消']
                ,btnAlign: 'c'
                ,btn1: function(index_one, layero) {
                    if($('#dh_xgcsm').val()==""){
                        layer.open({
                            title:"消息提醒",content:"参数名不能为空",skin:"layui-layer-molv",offset: 'auto',time:3000,btn:[],shade: 0,anim: -1,icon:5
                        });
                        $('#dh_xgcsm').focus();
                        return false;
                    }
                    layer.confirm('确定要修改参数信息吗?'
                        ,function(i){
                            form.on('submit(form_edit_submit)', function (data) {
                                var formData = data.field;
                                var gongxu = {id:$('#gongxu').val()};
                                var jixing = {id:$('#jixing').val()};
                                var leibie = {id:$('#leiBie').val(),gongxu:gongxu,jixing:jixing}
                                formData.leibie=leibie;
                                $.ajax({
                                    url:layui.setter.host+'jichushuju/shebei/shebeiparam/updSheBeiParam',
                                    type:'post',
                                    contentType:"application/json;charset=utf-8",
                                    data:JSON.stringify(formData),
                                    success:function(data){
                                        ajaxSuccess(data,table);
                                        if(data.code==666){
                                            layer.close(i);
                                        }else{
                                            layer.close(i);layer.close(index);
                                        }
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
        }
    });

    //监听是否报警
    form.on('switch(switchSfbj)', function(data) {
        var id = (data.elem).id;
        //当前switch对应的value值
        var sfbj = this.checked ? "1" : "0";
        //更新switch对应的value
        (data.elem).value = sfbj;
        var sqlName = "baojing_flag";
        //调用更新字段方法
        upd_data(id, sfbj, sqlName);
    });

    //监听是否显示
    form.on('switch(switchSfxx)', function(data) {
        var id = (data.elem).id;
        //当前switch对应的value值
        var sfxs = this.checked ? "1" : "0";
        //更新switch对应的value
        (data.elem).value = sfxs;
        var sqlName = "zhanshi_flag";
        //调用更新字段方法
        upd_data(id, sfxs, sqlName);
    });

    //监听是否记录历史曲线
    form.on('switch(switchSfjllsqx)', function(data) {
        var id = (data.elem).id;
        //当前switch对应的value值
        var sfjllsqx = this.checked ? "1" : "0";
        //更新switch对应的value
        (data.elem).value = sfjllsqx;
        var sqlName = "cunchu_flag";
        //调用更新字段方法
        upd_data(id, sfjllsqx, sqlName);
    });

    //修改是否报警---是否显示---是否记录历史曲线
    function upd_data(id, updData, sqlName) {
        $.ajax({
            url:layui.setter.host+'jichushuju/shebei/shebeiparam/updSheBeiParam_flag',
            type:'get',
            data:{
                id:id,
                name:sqlName,
                val:updData
            },
            success:function(data){
                if(data.code==0){
                    layer.open({
                        title:"消息提醒",content:data.message,skin:"layui-layer-molv",offset: 'rb',time:3000,btn:[],shade: 0,anim: -1,icon:6
                    });
                }else{
                    layer.open({
                        title:"消息提醒",content:data.message,skin:"layui-layer-molv",btn:["查看错误信息"],anim: -1,icon:5,
                        btn1:function(index){
                            layer.open({content:data.data})
                            layer.close(index);
                        }
                    });
                }
            }
        });
    }

    $('#batch_edit_btn').on('click',function(){
        var cs_ids=new Array();
        var checkStatus = table.checkStatus('table');
        var data = checkStatus.data;

        if(data.length<=0){
            layer.open({
                title:"消息提醒",content:"至少选中一行!",skin:"layui-layer-molv",time:3000,anim: -1,icon:5
            });
            return false;
        }
        for(var i=0;i<data.length;i++){
            cs_ids.push(data[i].id);
        }

        var str = "";
        for(var i = 0; i < cs_ids.length; i++) {
            if(i == cs_ids.length - 1) {
                str += cs_ids[i].id;
            } else {
                str += cs_ids[i].id + ",";
            }
        }

        layer.open({
            type: 1,
            title: ['批量修改', 'font-size:12px;'],
            content: $("#batch_edit_form_div"),
            shadeClose: true, //点击遮罩关闭层
            shade: 0.8,
            offset:'auto',
            area: ['60%', '90%'],
            btn: ['确定', '取消'],
            btnAlign: 'c',
            yes: function(index, layero) {
                var sfbj = "",
                    sfzs = "",
                    sfjlqx = "",
                    dw = "",
                    cslb_id = "",
                    ccsc = "";
                if(!$('#xgplnr_cslb').prop('checked') && !$('#xgplnr_ccsc').prop('checked') && !$('#xgplnr_sfbj').prop('checked') && !$('#xgplnr_sfxs').prop('checked') && !$('#xgplnr_sfjlls').prop('checked') && !$('#xgplnr_dw').prop('checked')) {
                    layer.alert("请先勾选您要修改的项，或者点击取消按钮!");
                    return false;
                }
                if($('#xgplnr_cslb').prop('checked')) {
                    cslb_id = $("#pl_xgcslb").val();
                } else {
                    cslb_id = "";
                }
                if($('#xgplnr_ccsc').prop('checked')) {
                    ccsc = $("#pl_xgsc").val();
                } else {
                    ccsc = "";
                }
                if($('#xgplnr_dw').prop('checked')) {
                    dw = $("#pl_xgdw").val();
                } else {
                    dw = "";
                }
                layer.confirm('确定修改参数信息? ',
                    {title:'提示'},function(i) {
                        $.ajax({
                            url:layui.setter.host+'jichushuju/shebei/shebeiparam/updSheBeiParam_Batch',
                            type:'post',
                            data:{
                                "cslb_id": cslb_id,
                                "ccsc": ccsc,
                                "idStr": str,
                                "sfbj": sfbj,
                                "sfzs": sfzs,
                                "sfjlqx": sfjlqx,
                                "dw": dw
                            },
                            success:function(data){
                                ajaxSuccess(data,table);
                                layer.close(i);layer.close(index);
                            }
                        });
                    });
            }
        });


    });

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
    function fixedColumn(cols) {
        for(var i = 0; i < cols.length; i++) {
            var col = cols[i];

            if(col.field == 'baojing_flag') {
                col.templet = "#mysfbj";
            }
            if(col.field == 'zhanshi_flag') {
                col.templet = "#mysfxx";
            }
            if(col.field == 'cunchu_flag') {
                col.templet = "#mysfjllsqx";
            }
        }
        return cols;
    }

    exports('shebeiparam', {})
});