layui.define(['form'], function(exports){
  var $ = layui.$
  ,layer = layui.layer
  ,form = layui.form;


    var $body = $('body');
  
  //自定义验证
  form.verify({
    //我们既支持上述函数式的方式，也支持下述数组的形式
    //数组的两个值分别代表：[正则匹配、匹配不符时的提示文字]
    pass: [
      /^[\S]{6,12}$/
      ,'密码必须6到12位，且不能出现空格'
    ]
    
    //确认密码
    ,repass: function(value){
      if(value !== $('#LAY_password').val()){
        return '两次密码输入不一致';
      }
    }
  });

  //设置密码
  form.on('submit(setmypass)', function(obj){
      var param = {
          id:obj.field.id,
          oldpwd:obj.field.oldPassword,
          newpwd:obj.field.password
      }
      layer.confirm(
          '确定修改信息?',
          {title:'提示'},function (i){
              $.ajax({
                  url:layui.setter.host+'jichushezhi/user/resetPwd',
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
                                  layer.open({content:data.data})
                                  layer.close(index);
                              }
                          });
                      }
                  }
              });
              layer.close(i);
          });
  });

  
  //对外暴露的接口
  exports('set', {});
});