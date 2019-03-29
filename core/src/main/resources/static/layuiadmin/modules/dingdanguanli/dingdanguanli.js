layui.define(['table', 'laydate', 'form', 'upload'], function (exports) {
    var $ = layui.$
        , table = layui.table
        , form = layui.form
        , upload = layui.upload
        , laydate = layui.laydate;

    //打开页面初始化查询框
    dictInitSelect('status', null, 'dingdanzhuangtai', 'name', 'value');
    dictInitSelect('kehuxinxi', null, 'kehuxinxi', 'name', 'value');
    laydate.render({
        elem: '#xiadankaishiriqi',
    });
    laydate.render({
        elem: '#xiadanjieshuriqi',
    });
    laydate.render({
        elem: '#jiaohuoriqi',
    });


    //设置表格头
    var cols = [[
        {field: 'id', title: 'id', hide: true}
        , {field: 'createTime', title: '创建时间', width: 100}
        , {field: 'dingdanhao', title: '订单号', width: 100}
        , {field: 'pibuguige', title: '坯布规格', width: 100}
        , {field: 'rukuguige', title: '入库规格', width: 100}
        , {field: 'gongzhiguige', title: '公制规格', width: 100}
        , {field: 'xiadanriqi', title: '下单日期', width: 100}
        , {field: 'jiaohuoriqi', title: '交货日期', width: 100}
        , {field: 'xiadanshuliang', title: '下单数量', width: 100}
        , {field: 'baozhuangyaoqiu', title: '包装要求', width: 100}
        , {field: 'baozhuangmaitou', title: '包装唛头', width: 100}
        , {field: 'jingbaimiyongsha', title: '经百米用纱量', width: 100}
        , {field: 'weibaimiyongsha', title: '纬百米用纱量', width: 100}
        , {field: 'pingfangkezhong', title: '平方克重', width: 100}
        , {title: '原料类型', width: 100, templet: repNull('yuanliaoleixing.name')}
        , {title: '成品用途', width: 100, templet: repNull('chengpinyongtu.name')}
        , {field: 'teshuyueding', title: '特殊约定', width: 100}
        , {field: 'erpshangpindaima', title: 'erp商品代码', width: 100}
        , {title: '营销员', width: 100, templet: repNull('yingxiaoyuan.username')}
        , {title: '经理', width: 100, templet: repNull('jingli.username')}
        , {title: '客户信息', width: 100, templet: repNull('kehuxinxi.name')}
        , {title: '订单状态', width: 100, templet: repNull('status.name')}
        , {field: 'beizhu', title: '备注', width: 100}
        , {title: '操作', toolbar: '#caozuo', width: 255, fixed: 'right'}
    ]];

    //初始化表格
    initTable("table", 'dingdanguanli/dingdanguanli/query_page', 'get', cols, table);

    //下载
    $('#download').click(function () {
        $(window).attr('location', '/dingdanguanli/dingdanguanli/downloadExcel');
    });

    //执行实例
    var uploadInst = upload.render({
        accept: 'file'	//指定文件类型
        , exts: 'xlsx'	//指定文件后缀
        , elem: '#upload' //绑定元素
        , url: '/dingdanguanli/dingdanguanli/upload' //上传接口
        , done: function (res) {
            if (res.code == 500) {
                layer.msg('上传数据失败，数据格式异常！', {
                    icon: 2
                });
            } else {
                layer.msg('上传数据成功！', {
                    icon: 1
                });
            }
        }
    });

    //监听操作列
    table.on('tool(table)', function (obj) {
        var data = obj.data;
        if (obj.event === 'del') {
            layer.confirm(
                "确定要删除订单号" + data.dingdanhao + '吗?',
                {title: '删除提示'}, function (index) {
                    $.ajax({
                        url: layui.setter.host + 'dingdanguanli/dingdanguanli/deleteOrder',
                        type: 'get',
                        data: {'id': data.id},
                        success: function (data) {
                            ajaxSuccess(data, table);
                        }
                    });
                });
        } else if (obj.event === 'edit') {
            dictInitSelect('yuanliaoleixing_id_edit', null, 'yuanliaoleixing', 'name', 'id');//原料类型
            dictInitSelect('kehuxinxi_id_edit', null, 'kehuxinxi', 'name', 'id');//客户信息
            dictInitSelect('chengpinyongtu_id_edit', '15', 'dd_chengpinyongtu', 'name', 'id');//成品用图
            InitSelect('jingli_id_edit', null, 'dingdanguanli/dingdanguanli/getUser', 'get', {}, 'username', 'id');
            InitSelect('yingxiaoyuan_id_edit', null, 'dingdanguanli/dingdanguanli/getUser', 'get', {}, 'username', 'id');
            editI = layer.open({
                type: 1
                , title: '编辑数据订单信息！'
                , content: $('#div_form_edit')
                , area: ['80%', '80%']
                , btn: ['修改', '取消']
                , btn1: function (editIndex, layero) {
                    form.on('submit(form_edit_submit)', function (data) {
                        layer.confirm('确定要修改订单信息么?'
                            , function (i) {
                                var formData = data.field;
                                encObject(formData);
                                $.ajax({
                                    url: layui.setter.host + 'dingdanguanli/dingdanguanli/updateOrder',
                                    contentType: "application/json;charset=utf-8",
                                    type: 'POST',
                                    data: JSON.stringify(formData),
                                    success: function (data) {
                                        ajaxSuccess(data, table);
                                    }
                                });
                                layer.close(i);
                                layer.close(editI);
                            });
                    });
                    $("#form_edit_submit").trigger('click');
                }
                , success: function () {
                    fromSetVel(form, 'form_edit', data);
                    laydate.render({
                        elem: '#xiadanriqi_edit',
                        min: 0
                    });
                    laydate.render({
                        elem: '#jiaohuoriqi_edit',
                        min: 0
                    });
                }
            })
        }
    });

    //添加订单
    $("#add").click(function () {
        dictInitSelect('yuanliaoleixing_id_add', null, 'yuanliaoleixing', 'name', 'id');//原料类型
        dictInitSelect('kehuxinxi_id_add', null, 'kehuxinxi', 'name', 'id');//客户信息
        dictInitSelect('chengpinyongtu_id_add', '15', 'dd_chengpinyongtu', 'name', 'id');//成品用图
        InitSelect('jingli_id_add', null, 'dingdanguanli/dingdanguanli/getUser', 'get', {}, 'username', 'id');
        InitSelect('yingxiaoyuan_id_add', null, 'dingdanguanli/dingdanguanli/getUser', 'get', {}, 'username', 'id');
        var addOpen = layer.open({
            type: 1
            , title: '添加订单信息！'
            , content: $('#div_form_add')
            , area: ['80%', '80%']
            , btn: ['添加', '取消']
            , btn1: function (index, layero) {
                form.on('submit(form_add_submit)', function (data) {
                    var formData = data.field;
                    encObject(formData);
                    $.ajax({
                        url: layui.setter.host + 'dingdanguanli/dingdanguanli/addOrder',
                        contentType: "application/json;charset=utf-8",
                        type: 'POST',
                        data: JSON.stringify(formData),
                        success: function (data) {
                            layer.open({
                                content: '是否继续管理合约号？',
                                fixd: true,
                                closeBtn: 0,
                                resize: false,
                                anim: 1,//从上掉落
                                btn: ['是', '否'],
                                btn2: function () {
                                    ajaxSuccess(data, table);
                                    layer.close(addOpen);
                                },
                                btn1: function () {
                                    $(location).attr('href', '/dingdanguanli/heyuehaoguanli/heyuehao?id=' + data.data.id);
                                    ajaxSuccess(data, table);
                                    layer.close(addOpen);
                                }
                            });
                        }
                    });
                });
                $("#form_add_submit").trigger('click');
                //
            }
            , success: function () { //弹出层打开成功时的回调。
                laydate.render({
                    elem: '#xiadanriqi_add',
                    value: new Date(),
                    min: 0
                });
                laydate.render({
                    elem: '#jiaohuoriqi_add',
                    min: 0
                });
            }
        });
    });

    //动态拼接坯布规格等数据
    $(function () {
        setPPGG('_add');
        setPPGG('_edit');
    });

    function setPPGG(Suffix) {
        $('#fukuan' + Suffix + ',#jingmi' + Suffix + ',#weimi' + Suffix + ',#jingshachengfen' + Suffix + ',#jingshazhishu' + Suffix + ',#weishachengfen' + Suffix + ',#weishazhishu' + Suffix + ',#teshuyaoqiu' + Suffix).blur(function () {
            var fukuan = $('#fukuan' + Suffix).val();
            var jingmi = $('#jingmi' + Suffix).val();
            var weimi = $('#weimi' + Suffix).val();
            var jingshachengfen = $('#jingshachengfen' + Suffix).val();
            var jingshazhishu = $('#jingshazhishu' + Suffix).val();
            var weishachengfen = $('#weishachengfen' + Suffix).val();
            var weishazhishu = $('#weishazhishu' + Suffix).val();
            var teshuyaoqiu = $('#teshuyaoqiu' + Suffix).val();
            var pibuguige = fukuan + "\" " + jingshachengfen + jingshazhishu + "*" + weishachengfen + weishazhishu + " " + jingmi + "*" + weimi + " " + teshuyaoqiu;
            $('#pibuguige' + Suffix).val(pibuguige);
        });
    }

    //监听搜索
    form.on('submit(form_search)', function (data) {
        var field = getParams('form');
        table.reload('table', {
            where: field
        });
        return false;
    });


    /**
     * 2019/03/23 bjw
     * 添加和更新处理关联对象
     * @param obj
     */
    function encObject(obj) {
        $.each(obj, function (key, val) {
            val = !val ? "''" : "'"+val+"'";
            var arr = key.split('.');
            if (arr.length <= 1) {
                return true;
            }
            var textObj = 'obj';
            var currentObj = obj; //当前对象
            for (var i = 0; i < arr.length; i++) {
                if (i == arr.length - 1) {
                    eval(textObj + "." + arr[i] + "=" + val);
                } else {
                    textObj += '.' + arr[i];
                    if (currentObj[arr[i]] == undefined || null == currentObj[arr[i]]) {
                        eval(textObj + '= {}');
                    }
                }
            }
        });
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

    /**
     * 2019/03/24 bjw
     * 处理根据对象默认select选择列表。
     * @param formId  表单div的id
     * @param ObjVal  值对象
     */
    function fromSetVel(from, formId, data) {
        var arrObj = $('#' + formId).find(":input[name *= '.']");
        for (var i = 0; i < arrObj.length; i++) {
            var name = arrObj[i].name;
            var arr = name.split('.');
            if (arr.length <= 1) continue;
            var currentObj = data;
            for (var j = 0; j < arr.length; j++) {
                if (currentObj != undefined && null != currentObj)
                    currentObj = currentObj[arr[j]]
            }
            if (currentObj != undefined && null != currentObj)
                data[name] = currentObj;
        }
        form.val(formId, data);
    }

    /**
     * 请求数据字典初始化select选项框
     * @param eleId
     * @param selectedId
     */
    function dictInitSelect(eleId, selectedId, dictCode, key, val) {
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
                initDownList(dict_data, eleId, selectedId, key, val, true);
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
    function InitSelect(eleId, selectedId, url, type, data, key, val) {
        $.ajax({
            url: layui.setter.host + url,
            async: false,
            data: data,
            type: type,
            success: function (data) {
                initDownList(data, eleId, selectedId, key, val, true);
                form.render();
            }
        });
    }

    /**
     * 校验
     */
    form.verify({
        zmAndSz: [
            /^[A-Za-z0-9]+$/
            , '只能是数字和字母组成！'
        ],
        zm: [
            /^[A-Za-z]+$/
            , '只能是字母组成！'
        ],
        sz: [
            /^[0-9]+$/
            , '只能是数字组成！'
        ],
        int: [
            /^-?[1-9]+[0-9]*$/
            , '只能是整数类型！'
        ],
        num: function (value, item) {
            if (isNaN(value)) {
                return "只能输入数字类型！";
            }
        },
        length: function (value, item) { //value：表单的值、item：表单的DOM对象
            var valueSize = value ? value.length : 0;
            var maxNumber = $(item).attr('tq_length');
            if (maxNumber) {
                var arr = maxNumber.split('^');
                if (arr[0] != '' && arr[1] != '') {
                    if (valueSize < arr[0] || valueSize > arr[1]) return '不能少于' + arr[0] + '个字符和不能大于' + arr[1] + '个字符！';
                }
                if (arr.length == 1) {
                    if (valueSize != arr[0]) return '输入长度只能是' + arr[0] + '个字符！';
                }
                if (arr[0] == '' && arr[1] != '') {
                    if (valueSize > arr[1]) return "不能超过" + arr[1] + "个字符！";
                }
                if (arr[0] != '' && arr[1] == '') {
                    if (valueSize < arr[0]) return "不能少于" + arr[0] + "个字符！";
                }


            }
        }
    });

    exports('dingdanguanli', {})
});