layui.define(['table', 'form', 'laydate'], function(exports){
    var $ = layui.$
        ,table = layui.table
        ,form = layui.form
        ,laydate = layui.laydate;




    function findHeyuehaoWithoutGongYi(){
        //查找未关联工艺的合约号
        $.ajax({
            async:false,
            url:layui.setter.host+'gongyi/findHeyuehaoWithoutGongYi',
            type: 'get',
            success: function (data) {
                var heyuehaoWithoutGongYi ={};
                heyuehaoWithoutGongYi.count = data.message;
                heyuehaoWithoutGongYi.data = data.data;
                if(parseInt(heyuehaoWithoutGongYi.count)>0){
                    $("#add_gongyi_btn i").html(heyuehaoWithoutGongYi.count+"个未设置工艺的合约号");
                    $("#add_gongyi_btn").show();
                }
                //填充单选的合约号列表
                var html = "";
                for (var i = 0; i < heyuehaoWithoutGongYi.data.length; i++) {
                    html = html + "<input type='radio' name='add_heyuehao' lay-filter='add_heyuehao' value='"+heyuehaoWithoutGongYi.data[i].id+"' title='"+heyuehaoWithoutGongYi.data[i].name+"'>";
                }
                $("#form_add_heyuehao").append(html);
            }
        });
    }
    findHeyuehaoWithoutGongYi();




    var heyuehao = {};
    form.on('radio(add_heyuehao)', function(data){
        heyuehao.name = data.elem.title;
        heyuehao.id = data.value;
    });







    //添加工艺
    $("#add_gongyi_btn").on("click", function() {
        $("#form_add")[0].reset();
        layer.open({
            type: 1,
            title: ['添加工艺（关联合约号）'],
            content: $("#div_form_add"),
            area: ['90%', '90%'],
            shade: [0.8, '#393D49'],
            btn: ['下一步', '取消'],
            yes: function(index, layero) {
                form.on('submit(form_add_submit)', function (data) {
                    if(!isEmptyObject(heyuehao)){
                        layer.confirm('确定要为 '+heyuehao.name+' 设置工艺么？'
                            ,function(i){
                                localStorage.setItem("gongyi_heyuehao",heyuehao.id);
                                // console.info(localStorage.getItem("gongyi_heyuehao"))
                                layer.close(i);layer.close(index);
                                $("#form_add_zongbiao")[0].reset();



                                layer.open({
                                    type: 1,
                                    title: ['添加工艺（工艺概况）'],
                                    content: $("#div_form_add_zongbiao"),
                                    area: ['90%', '90%'],
                                    shade: [0.8, '#393D49'],
                                    btn: ['下一步','取消'],
                                    btn1:function (index, layero) {
                                        form.on('submit(form_add_zongbiao_submit)', function (data) {
                                            var formData = data.field;
                                            formData.heyuehaoId = localStorage.getItem("gongyi_heyuehao");
                                            $.ajax({
                                                url:layui.setter.host+'gongyi/add_gaikuang',
                                                data:formData,
                                                success:function(data){
                                                    layer.close(index);
                                                    var cols = [[
                                                        {field: 'id', title: 'id', hide: true}
                                                        ,{type:'checkbox'}
                                                        , {field: 'pinming', title: '品名'}
                                                        , {field: 'pihao', title: '批号'}
                                                        , {field: 'zhishu', title: '支数'}
                                                        , {field: 'sehao', title: '色号'}
                                                        , {field: 'sebie', title: '色别'}
                                                        , {field: 'kucunliang', title: '库存量'}
                                                    ]];

                                                    //经排选择
                                                    $("#select_jingpai").on("click", function() {
                                                        table.render({
                                                            elem: "#table_yuansha"
                                                            ,id: "table_yuansha"
                                                            , url: layui.setter.host+"gongyi/add_yuansha"
                                                            , method: "get"
                                                            , cellMinWidth: 80
                                                            , cols: cols
                                                        });
                                                        layer.open({
                                                            type: 1,
                                                            title: ['原纱选择'],
                                                            content: $("#div_form_add_yuansha"),
                                                            area: ['90%', '90%'],
                                                            shade: [0.8, '#393D49'],
                                                            btn: ['确定','取消'],
                                                            btn1:function (index, layero) {

                                                            }
                                                        });
                                                    });




                                                    layer.open({
                                                        type: 1,
                                                        title: ['添加工艺（经排排花）'],
                                                        content: $("#div_form_add_jingpai"),
                                                        area: ['90%', '90%'],
                                                        shade: [0.8, '#393D49'],
                                                        btn: ['确定','取消'],
                                                        btn1:function (index, layero) {

                                                        }
                                                    });



                                                }
                                            });
                                        });
                                        $("#form_add_zongbiao_submit").trigger('click');
                                    }
                                });


                            });
                    }else {
                        layer.open({
                            content: '请选择要设置工艺的合约号'
                        });

                    }
                });
                $("#form_add_submit").trigger('click');
            }
        });

    });





    form.render();

    exports('gongyi', {})
});