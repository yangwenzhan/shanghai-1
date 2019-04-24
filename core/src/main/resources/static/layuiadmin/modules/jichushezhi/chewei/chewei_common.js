
    //工序
    function getGongXu(downID, selectedId, isAll) {
        var reg = RegExp(/,/);
        var downId_arr=[];
        if(downID==null){
            downId_arr=null;
        }else if(reg.test(downID)){
            downId_arr = downID.split(',');
        }else{
            downId_arr.push(downID);
        }
        $.ajax({
            url: layui.setter.host + 'common/findAllGX',
            type: 'get',
            async: false,
            success: function (data) {
                for(var i = 0;i<downId_arr.length;i++){
                    initDownList(data, downId_arr[i], selectedId, 'name', 'id', isAll);
                }
                layui.form.render();
            }
        });
    }

    //机型
    function getJiXing(downID, selectedId, isAll, gxid) {
        var reg = RegExp(/,/);
        var downId_arr=[];
        if(downID==null){
            downId_arr=null;
        }else if(reg.test(downID)){
            downId_arr = downID.split(',');
        }else{
            downId_arr.push(downID);
        }
        $.ajax({
            url: layui.setter.host + 'common/findAllJX',
            type: 'get',
            async: false,
            data: {
                "gongxu": gxid
            },
            success: function (data) {
                for(var i = 0;i<downId_arr.length;i++){
                    initDownList(data, downId_arr[i], selectedId, 'name', 'id', isAll);
                }
                layui.form.render();
            }
        });
    }

    function getJiTaiHao(downID, selectedId, isAll, gxid, jxid){
        var reg = RegExp(/,/);
        var downId_arr=[];
        if(downID==null){
            downId_arr=null;
        }else if(reg.test(downID)){
            downId_arr = downID.split(',');
        }else{
            downId_arr.push(downID);
        }
        $.ajax({
            url: layui.setter.host + 'jichushuju/shebei/shebei/findAllSheBei',
            type: 'get',
            async: false,
            data: {
                "gongxu": gxid,
                "jixing": jxid
            },
            success: function (data) {
                for(var i = 0;i<downId_arr.length;i++){
                    initDownList(data, downId_arr[i], selectedId, 'jitaihao', 'id', isAll);
                }
                layui.form.render();
                layui.formSelects.render('setAddJiTaiHao');
            }
        });
    }

    //轮班
    function getDict(downID, selectedId, isAll, code) {
        var reg = RegExp(/,/);
        var downId_arr=[];
        if(downID==null){
            downId_arr=null;
        }else if(reg.test(downID)){
            downId_arr = downID.split(',');
        }else{
            downId_arr.push(downID);
        }
        $.ajax({
            type: 'get',
            async: false,
            data: {
                "code": code
            },
            url: layui.setter.host + 'xitongshezhi/shujuzidian/formSelect',
            success: function (data) {
                if(data.code==0){
                    var lunban = data.data.dicts;
                    lunban = lunban.sort(sortPXH);
                    var dicts = {code:0,data:lunban,message:null};
                    for(var i = 0;i<downId_arr.length;i++){
                        initDownList(dicts, downId_arr[i], selectedId, 'name', 'id', isAll);
                    }
                }else{
                    layer.open({
                        title:"消息提醒",content:data.message,skin:"layui-layer-molv",btn:["查看错误信息"],anim: -1,icon:5,
                        btn1:function(index){
                            layer.open({content:data.data});
                            layer.close(index);
                        }
                    });
                }
            }
        });
    }

    //根据sort排序
    function sortPXH(a,b){
        return a.sort-b.sort;
    }

    //工号姓名
    function getUsername(downID, selectedId, isAll, gxid, lbid) {
        $.ajax({
            async: false,
            url: layui.setter.host + 'common/findUser',
            type: 'get',
            data: {
                "gxid": gxid,
                "lbid": lbid
            },
            success: function (data) {
                initDownList(data, downID, selectedId, 'ygxm', 'id', isAll);
                layui.form.render();
                layui.formSelects.render('setUser');
                layui.formSelects.render('setAddUser');
            }
        });
    }
