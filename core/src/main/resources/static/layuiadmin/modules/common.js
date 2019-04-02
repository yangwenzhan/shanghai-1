/**
 @Name：layuiAdmin 公共业务
 */
 
layui.define(function(exports){
  var $ = layui.$
  ,layer = layui.layer
  ,laytpl = layui.laytpl
  ,setter = layui.setter
  ,view = layui.view
  ,admin = layui.admin
  ,form = layui.form;

  //公共业务的逻辑处理可以写在此处，切换任何页面都会执行
  //……

  getParams = function (formId) {
    var _params = {};

    $.each($('#' + formId).serializeArray(), function (i, field) {
      if (null != field.value && "" != field.value) {
      _params[field.name] = field.value;
      }
    });
    return _params;
  };


  //ajax请求成功处理函数
  ajaxSuccess = function(data,table){
    table.reload('table');
    if(data.code == 0){
      layer.open({
        title:"消息提醒",
        content:data.message,
        skin:"layui-layer-molv",
        offset: 'rb',
        time:3000,
        btn:[],
        shade: 0,
        anim: -1,
        icon:6
      });
    }else if(data.code == 666){
        layer.open({
            title:"错误提示",
            content:data.message,
            skin:"layui-layer-molv",
            btn:["确定"],
            anim: -1,
            icon:5,
            btn1:function(index){
                layer.close(index);
            }
        });
    }
    else {
      layer.open({
        title:"消息提醒",
        content:data.message,
        skin:"layui-layer-molv",
        btn:["查看错误信息"],
        anim: -1,
        icon:5,
        btn1:function(index){
          layer.open({content:data.data});
          layer.close(index);
        }
      });
    }
  };



  //初始化table
   initTable = function (ele, url, method,cols, table,formId, doneCallBack) {
     table.render({
      elem: "#"+ele
      ,id: ele
      ,autoSort: false  //后端排序
      , url: layui.setter.host+url
      , method: method
      , cellMinWidth: 80
      , cols: cols
      ,where:getParams(formId)
      ,page:{
        limits:[10,15,20,25,30,35,40,45,50,55,60,65,70,75,80,85,90]
        ,limit:10
      }
      , done: function (res) {
        if (typeof(doneCallBack) === "function") {
          doneCallBack(res);
        }
      }
    });


   table.on('sort('+ele+')', function(obj){
       var paramObject = getParams(formId);
       paramObject.sort = obj.field+','+obj.type;
       table.reload(ele, { //testTable是表格容器id
           where: paramObject
       });
   });

  };

  //初始化table(不带分页)
  initTable_all = function (ele, url, method,cols, table,formId, doneCallBack) {
    table.render({
      elem: "#"+ele
      ,id: ele
      ,autoSort: false  //后端排序
      , url: layui.setter.host+url
      , method: method
      , cellMinWidth: 80
      , cols: cols
      ,where:getParams(formId)
      , done: function (res) {
        if (typeof(doneCallBack) === "function") {
          doneCallBack(res);
        }
      }
    });

  table.on('sort('+ele+')', function(obj){
      var paramObject = getParams(formId);
      paramObject.sort = obj.field+','+obj.type;
      console.info(paramObject);
      table.reload(ele, { //testTable是表格容器id
          where: paramObject
      });
  });

  };

















  //form查询表单收缩
    formBack = function(){
    var shou_suo = false; //未收缩
    $('#shou_suo_cxtj').click(function() {
        if (shou_suo) {
            $('.layui-card-header').slideDown(500, function () {
                $('#shou_suo_cxtj').html('<img class="layui-nav-img" style="margin-right:0" src="/images/up.png"/>');
                shou_suo = false;
            });
        } else {
            $('.layui-card-header').slideUp(500, function () {
                $('#shou_suo_cxtj').html('<img class="layui-nav-img" style="margin-right:0" src="/images/down.png"/>');
                shou_suo = true;
            });
        }
    })};








//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //动态参数
    dynamicForm = function(){
        // {code:"lunban",hasNull:true,defaultValue:""};   //defaultValue：数据字典传value；工序传工序名称


        var args = Array.from(arguments);   //arguments不是Array类型，此方法是将arguments转换为Array。

        //数据字典
        $.ajax({
            type:"get",
            url:layui.setter.host+"xitongshezhi/shujuzidian/getcodes",
            async:false,
            success:function (data) {
                var datas = data.data;
                for (var i = 0; i < datas.length; i++) {
                    searchForm_dict(args,datas[i].code);
                }
            }
        });

        //工序
        var gongxu= args.filter(function(item){return item.code == "gongxu";});
        if(gongxu.length>0){
            $.ajax({
                url: layui.setter.host + 'common/findAllGX',
                type: 'get',
                async:false,
                success: function (data) {
                    var html = '';
                    if(gongxu[0].hasNull){
                        html+='<option value= "" >全部</option>';
                    }
                    for(var i = 0;i<data.data.length;i++){
                        if(gongxu[0].defaultValue == data.data[i].name){
                            html+='<option selected value= "'+data.data[i].id+'" >'+data.data[i].name+'</option>';
                        }else {
                            html+='<option value= "'+data.data[i].id+'" >'+data.data[i].name+'</option>';
                        }
                    }
                    $("select[name='"+gongxu[0].code+"']").append(html);
                }
            });
        }


        //组
        var zu= args.filter(function(item){return item.code == "zu";});
        if(zu.length>0){
            var data = [{zu:1},{zu:2},{zu:3},{zu:4},{zu:5},{zu:6},{zu:7},{zu:8},{zu:9},{zu:10}];
            var html = '';
            if(zu[0].hasNull){
                html+='<option value= "" >全部</option>';
            }
            for(var i = 0;i<data.length;i++){
                if(zu[0].defaultValue == data[i].zu){
                    html+='<option selected value= "'+data[i].zu+'" >'+data[i].zu+'</option>';
                }else {
                    html+='<option  value= "'+data[i].zu+'" >'+data[i].zu+'</option>';
                }
            }
            $("select[name='"+zu[0].code+"']").append(html);
        }

        //角色
        var roles= args.filter(function(item){return item.code == "role";});
        if(roles.length>0){
            $.ajax({
                url:layui.setter.host+'jichushezhi/juesequanxian/role/findAll',
                type: 'get',
                async:false,
                success: function (data) {
                    var html = '';
                    if(roles[0].hasNull){
                        html+='<option value= "" >全部</option>';
                    }
                    for(var i = 0;i<data.data.length;i++){
                        if(roles[0].defaultValue == data.data[i].name){
                            html+='<option selected value= "'+data.data[i].id+'" >'+data.data[i].name+'</option>';
                        }else {
                            html+='<option value= "'+data.data[i].id+'" >'+data.data[i].name+'</option>';
                        }
                    }
                    $("select[name='"+roles[0].code+"']").append(html);
                }
            });
        }


    };

    searchForm_dict = function(params,code){
        var param= params.filter(function(item){
            return item.code == code;
        });
        if(param.length>0){
            $.ajax({
                type:'get',
                async:false,
                url:layui.setter.host+'xitongshezhi/shujuzidian/formSelect?code='+code,
                success:function(data){
                    createOption_dict(data,param[0]);
                }
            });
        }
    };
    //动态创建option dict
    createOption_dict = function(data,param){
        var html = '';
        if(param.hasNull){
            html+='<option value= "" >全部</option>';
        }
        var dicts = data.data.dicts;
        for(var i = 0;i<dicts.length;i++){
            if(param.defaultValue == dicts[i].value){
                html+='<option selected value= "'+dicts[i].id+'" >'+dicts[i].name+'</option>';
            }else {
                html+='<option value= "'+dicts[i].id+'" >'+dicts[i].name+'</option>';
            }
        }
        $("select[name='"+param.code+"']").append(html);
    };

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    //ajax请求成功处理下拉框函数
    initDownList = function(data,downID,selectedId,valueName,valueID,isall){
        $('#' + downID).html("");
        if(data.code == 0) {
            if(data.data.length == 0) {
                return false;
            }
            var reg = RegExp(/,/);
            var selectedArr=[];
            if(selectedId==null){
                selectedArr=null;
            }else if(reg.test(selectedId)){
                selectedArr = selectedId.split(',');
            }else{
                selectedArr.push(selectedId);
            }

            var str = "";
            if(!isall) {
                for(var i = 0; i < data.data.length; i++) {
                    str += "<option value='" + data.data[i][valueName] + "'>"
                        + data.data[i].valueName
                        + "</option>";
                }
                $('#' + downID).html(str);
            } else {
                str += "<option value=''>全部</option>";
                for(var i = 0; i < data.data.length; i++) {

                    if(selectedArr==null){
                        str += "<option value='" + data.data[i][valueID] + "'>"
                            + data.data[i][valueName]
                            + "</option>";
                    }else{
                        for(var j=0;j< selectedArr.length;j++){
                            if(data.data[i][valueID]==selectedArr[j]){
                                str += "<option value='" + data.data[i][valueID] + "' selected='selected'>"
                                    + data.data[i][valueName]
                                    + "</option>";
                                break;
                            }
                            if(j==selectedArr.length-1 && data.data[i][valueID]!=selectedArr[j]){
                                str += "<option value='" + data.data[i][valueID] + "'>"
                                    + data.data[i][valueName]
                                    + "</option>";
                                break;
                            }
                        }
                    }
                }
                $('#' + downID).html(str);
            }

        } else {
            layer.open({
                title:"消息提醒",
                content:data.message,
                skin:"layui-layer-molv",
                btn:["查看错误信息"],
                anim: -1,
                icon:5,
                btn1:function(index){
                    layer.open({content:data.data});
                    layer.close(index);
                }
            });
        }
    };



    /**
     * 2019/03/22 bjw
     * 通过三目运算符处理thymeleaf表达式中的内容有NULL异常
     * @param name 数据取值参数
     * @returns {string} 取值内容
     */
    repNull = function(name) {
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
    };

    /**
     * 校验
     */
    tq_verify = function(form){
        form.verify({
            heyuehao: [
                /^([0-9]{5})([A-Z]{1})([0-9]{1})$/,
                '合约号格式不正确！'
            ],
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
            length: function (value, item) { //校验字符长度，配合tq_length 标签。
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
    };

  exports('common', {});
});