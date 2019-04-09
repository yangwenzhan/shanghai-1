layui.define(['form','laydate'], function(exports){
  var $ = layui.$
  ,layer = layui.layer
  ,form = layui.form
  ,laydate = layui.laydate;


    var $body = $('body');
  
  //自定义验证
  form.verify({
      xingming: function(value, item){ //value：表单的值、item：表单的DOM对象
      if(!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(value)){
        return '用户名不能有特殊字符';
      }
      if(/(^\_)|(\__)|(\_+$)/.test(value)){
        return '用户名首尾不能出现下划线\'_\'';
      }
      if(/^\d+\d+\d$/.test(value)){
        return '用户名不能全为数字';
      }
    }
  });
  
  //设置资料
  form.on('submit(setmyinfo)', function(obj){

      if($('#xingming').val()==""){
          layer.open({
              title:"消息提醒",
              content:"姓名不能为空",
              skin:"layui-layer-molv",
              offset: 'auto',
              time:3000,
              btn:[],
              shade: 0,
              anim: -1,
              icon:5
          });
          $('#xingming').focus();
          return false;
      }
      //邮箱格式验证
      var email = $.trim($('#email').val());
      var isEmail = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;

      if(!(email=="")){
          if(!(isEmail.test(email))){
              layer.open({
                  title:"消息提醒",
                  content:"邮箱格式不正确",
                  skin:"layui-layer-molv",
                  offset: 'auto',
                  time:3000,
                  btn:[],
                  shade: 0,
                  anim: -1,
                  icon:5
              });
              $('#email').focus();
              return false;
          }
      }



      var param = {
          id:obj.field.id,
          xingming:obj.field.xingming,
          sex:obj.field.sex,
          birthday:obj.field.birthday,
          mobile:obj.field.mobile,
          email:obj.field.email
      };
      layer.confirm(
          '确定修改信息?',
          {title:'提示'},function (i){
              $.ajax({
                  url:layui.setter.host+'jichushezhi/user/setUserInfo',
                  type:'get',
                  data:param,
                  success:function(data,index){
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
                      }else {
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
                  }
              });
              layer.close(i);
          });

  });

  initUserInfo();
  function initUserInfo(){

      $.ajax({
          url:layui.setter.host+'jichushezhi/user/findByUserId',
          type:'get',
          data:{id:$('#id').val()},
          success:function(data){
              if(data.code == 0){

                  $('#my_roles').val("");
                  var roles = [],str="";
                  roles = data.data.roles;

                  if(roles.length>0){
                    for(var i=0;i<roles.length;i++){
                        str+=roles[i].name+'  ';
                    }
                  }else{
                      $('#my_roles').val("暂无角色");
                  }
                  $('#my_roles').val(str);
                  $('#mobile').val(data.data.mobile);
                  $('#email').val(data.data.email);
                  $('#xingming').val(data.data.xingming);

                  if(data.data.sex==0){
                      $('#sex_boy').attr("checked","checked");
                  }else{
                      $('#sex_girl').attr("checked","checked");
                  }

                  laydate.render({
                      elem: '#birthday',
                      value:data.data.birthday.substr(0,10),
                      max: 0
                  });
                  form.render();
              }
          }
      });
  }



  
  //对外暴露的接口
  exports('info', {});
});