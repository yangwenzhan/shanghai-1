/**

 @Name：layuiAdmin 公共业务
 @Author：贤心
 @Site：http://www.layui.com/admin/
 @License：LPPL

 */

layui.define(function (exports) {
    var $ = layui.$
        , layer = layui.layer
        , laytpl = layui.laytpl
        , setter = layui.setter
        , view = layui.view
        , admin = layui.admin

    //公共业务的逻辑处理可以写在此处，切换任何页面都会执行
    //……


    getParams = function (formId) {
        var _params = {};

        console.info("aaaa" + $('#' + formId).serializeArray());

        $.each($('#' + formId).serializeArray(), function (i, field) {
            if (null != field.value && "" != field.value) {
                _params[field.name] = field.value;
            }
        });
        console.info(_params);
        return _params;
    };


    //ajax请求成功处理函数
    ajaxSuccess = function (data, table) {
        table.reload('table');
        if (data.code == 0) {
            layer.open({
                title: "消息提醒",
                content: data.message,
                skin: "layui-layer-molv",
                offset: 'rb',
                time: 3000,
                btn: [],
                shade: 0,
                anim: -1,
                icon: 6
            });
        } else {
            layer.open({
                title: "消息提醒",
                content: data.message,
                skin: "layui-layer-molv",
                btn: ["查看错误信息"],
                anim: -1,
                icon: 5,
                btn1: function (index) {
                    layer.open({content: data.data})
                    layer.close(index);
                }
            });
        }
    }


    //初始化table
    initTable = function (ele, url, method, cols, table, formId, doneCallBack) {
        return table.render({
            elem: "#" + ele
            , id: ele
            , url: layui.setter.host + url
            , method: method
            , cellMinWidth: 80
            , cols: cols
            , where: getParams(formId)
            , page: {
                limits: [10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 65, 70, 75, 80, 85, 90]
                , limit: 20
            }
            , done: function (res) {
                if (typeof(doneCallBack) === "function") {
                    doneCallBack(res);
                }
            }
        });
    };

    //初始化table(不带分页)
    initTable_all = function (ele, url, method, cols, table, formId, doneCallBack) {
        return table.render({
            elem: "#" + ele
            , id: ele
            , url: layui.setter.host + url
            , method: method
            , cellMinWidth: 80
            , cols: cols
            , where: getParams(formId)
            , done: function (res) {
                if (typeof(doneCallBack) === "function") {
                    doneCallBack(res);
                }
            }
        });
    };


    //动态参数
    searchForm = function () {
        var args = Array.from(arguments);   //arguments不是Array类型，此方法是将arguments转换为Array。
        searchForm_dict(args, "lunban");
    }


    searchForm_dict = function (params, code) {
        if (params.indexOf(code) != -1) {
            $.ajax({
                type: 'get',
                async: false,
                url: layui.setter.host + 'xitongshezhi/shujuzidian/formSelect?code=' + code,
                success: function (data) {
                    createOption_dict(data, code);
                }
            });
        }
    }
    //动态创建option dict
    createOption_dict = function (data, code) {
        var html = '';
        var dicts = data.data.dicts;
        for (var i = 0; i < dicts.length; i++) {
            html += '<option value= "' + dicts[i].value + '" >' + dicts[i].name + '</option>';
        }
        console.info(html);
        $("#" + code).append(html);
    }


    //退出
    admin.events.logout = function () {
        //执行退出接口
        admin.req({
            url: layui.setter.base + 'json/user/logout.js'
            , type: 'get'
            , data: {}
            , done: function (res) { //这里要说明一下：done 是只有 response 的 code 正常才会执行。而 succese 则是只要 http 为 200 就会执行

                //清空本地记录的 token，并跳转到登入页
                admin.exit(function () {
                    location.href = 'user/login.html';
                });
            }
        });
    };


    //对外暴露的接口
    exports('common', {});
});