var Common = function () {

    //searchTable table的搜索功能，调用layui table模块的的重载功能，参数组建思路：获取序列化的表单，组装成json对象
    var searchTable = function (formId, tableIns) {
        var queryParams = getParams(formId);
        tableIns.reload({
            where: {condition: queryParams},
            page: {
                curr: 1 //重新从第 1 页开始
            }
        });
    };









    // upload 文件上传
    // var upload = function (eleId, layUpload, done, error, accept, exts) {
    //     layUpload.render({
    //         elem: eleId //绑定元素
    //         , url: '/upload/' //上传接口
    //         , accept: accept === undefined ? 'file' : accept
    //         , exts: exts === undefined ? 'jpg|png|gif|bmp|jpeg' : exts
    //         , done: function (res) {
    //             //上传完毕回调
    //             if (typeof (done) === 'function') {
    //                 done(res)
    //             }
    //         }
    //         , error: function () {
    //             //请求异常回调
    //             if (typeof (error) === 'function') {
    //                 error()
    //             }
    //         }
    //     });
    // };

    // openFrame 打开弹出层，基于最顶层弹出
    // var openFrame = function (url, title, width, height) {
    //     width = width === undefined ? '900px' : width;
    //     height = height === undefined ? '500px' : height;
    //     return top.layer.open({
    //         area: [width, height],
    //         type: 2,
    //         title: title,
    //         content: url //这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://sentsin.com', 'no']
    //     });
    // };


    return {
        initTable: function (ele, url, cols, table, doneCallBack) {
            return initTable(ele, url, cols, table, doneCallBack);
        },
        searchTable: function (formId, table) {
            searchTable(formId, table);
        },
        ajaxSuccess:function(data){
            ajaxSuccess(data);
        }
        // ,uploadFile: function (eleId, layUpload, done, error, accept, exts) {
        //     upload(eleId, layUpload, done, error, accept, exts);
        // }
        // ,openFrame: function (url, title, width, height) {
        //     return openFrame(url, title, width, height);
        // }
    }
}();




//table
//1.上面form为搜索内容部分
// 2.中间一个table为主窗口
// 3.注意一个id="toolBars"的js，为工具条，用于追加在每一列的后面
// 4.可以看到下面页面初始化等都调用了Common.js中的对应方法
// <!DOCTYPE html>
// <html lang="en" xmlns="http://www.w3.org/1999/xhtml"
// xmlns:th="http://www.thymeleaf.org">
//     <head>
//     <title>Title</title>
//     <div th:replace="common/links::header"></div>
//     <div th:replace="common/script::js_footer"></div>
//     </head>
//     <body>
//     <fieldset class="layui-elem-field">
//     <legend>条件搜索</legend>
//     <form class="layui-form" style="text-align: center" id="searchForm">
//     <div class="layui-form-item">
//     <div class="layui-inline">
//     <label class="layui-form-label">手机号</label>
//     <div class="layui-input-inline">
//     <input type="tel" name="mobile"  autocomplete="off" class="layui-input">
//     </div>
//     </div>
//     <div class="layui-inline">
//     <label class="layui-form-label">用户名</label>
//     <div class="layui-input-inline">
//     <input type="text" name="username"  autocomplete="off" class="layui-input">
//     </div>
//     </div>
//     </div>
//     <div class="layui-form-item">
//     <div class="layui-input-block">
//     <button class="layui-btn" id="searchBtn" type="button">搜索</button>
//     <button type="reset" class="layui-btn layui-btn-primary">重置</button>
//     <button type="reset" class="layui-btn layui-btn-primary">新增</button>
//     </div>
//     </div>
//     </form>
//     </fieldset>
//     <hr class="layui-bg-green">
//     <table class="layui-hide" id="userTable" lay-filter="userFilter"></table>
//     <script type="text/html" id="toolBars">
//     <a class="layui-btn layui-btn-xs" lay-event="detail">查看</a>
//     <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
//     <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
//
//     <!-- 这里同样支持 laytpl 语法，如： -->
//     <a class="layui-btn layui-btn-xs" lay-event="check">审核</a>
// </script>
// <script>
// cols=[[
//     {checkbox: true, fixed: true}
//     ,{field:'username', title: '用户名'} //width 支持：数字、百分比和不填写。你还可以通过 minWidth 参数局部定义当前单元格的最小宽度，layui 2.2.1 新增
//     ,{field:'mobile', title: '手机号', sort: true}
//     ,{field:'nickname', title: '昵称'}
//     ,{field:'lastLoginTime', title: '最后登录时间'}
//     ,{field:'ip', title: '最后登录ip', align: 'center'} //单元格内容水平居中
//     ,{field:'op',title: '操作', align:'center', toolbar: '#toolBars'} //这里的toolbar值是模板元素的选择器
// ]];
//
// layui.use('table', function(){
//     var table = layui.table,$ = layui.jquery;
//     table.on('tool(userFilter)', function(obj){
//         var data = obj.data; //获得当前行数据
//         var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
//         if(layEvent === 'detail'){ //查看
//             //TODO detail
//         } else if(layEvent === 'del'){ //删除
//             top.layer.confirm('真的删除行么', function(index){
//                 //TODO do delete
//             });
//         } else if(layEvent === 'edit'){ //编辑
//             Common.openFrame('/sys/user/to-edit?id=' + data.id,'修改用户信息');
//         }
//     });
//     var initTable = Common.initTable('#userTable','/sys/user/query-page',cols,table);
//     $('#searchBtn').on('click',function () {
//         Common.searchTable('searchForm',initTable);
//     })
// });
// </script>
// </body>
// </html>


//form
// <!DOCTYPE html>
// <html lang="en" xmlns="http://www.w3.org/1999/xhtml"
// xmlns:th="http://www.thymeleaf.org">
//     <head>
//     <title>Title</title>
//     <div th:replace="common/links::header"></div>
//     <div th:replace="common/script::js_footer"></div>
//     <script th:src="@{/js/common/verify.js}"></script>
//     </head>
//     <body class="form-body">
//     <form class="layui-form " action="">
//     <div class="layui-form-item layui-col-md6 layui-col-md-offset2">
//     <label class="layui-form-label">用户名</label>
//     <div class="layui-input-inline form-length350">
//     <input type="text" name="username" th:value="${user.username}" lay-verify="required|username" placeholder="请输入用户名" autocomplete="off" class="layui-input">
//     </div>
//     <div class="layui-form-mid layui-word-aux">*6-12个字符</div>
// </div>
// <div class="layui-form-item layui-col-md4 layui-col-md-offset2">
//     <label class="layui-form-label">密码框</label>
//     <div class="layui-input-inline">
//     <input type="password" name="password"  lay-verify="required|password" placeholder="请输入密码" autocomplete="off" class="layui-input">
//     </div>
//     <div class="layui-form-mid layui-word-aux">*包含a_z、A_Z、1-9中的两种，且长度6-20</div>
// </div>
// <div class="layui-form-item layui-col-md4 layui-col-md-offset2">
//     <label class="layui-form-label">手机号</label>
//     <div class="layui-input-inline">
//     <input type="text" name="mobile"  th:value="${user.mobile}" lay-verify="required|phone" placeholder="请输入手机号" autocomplete="off" class="layui-input">
//     </div>
//     <div class="layui-form-mid layui-word-aux">*手机号</div>
//     </div>
//     <div class="layui-form-item layui-col-md4 layui-col-md-offset2">
//     <label class="layui-form-label">昵称</label>
//     <div class="layui-input-inline">
//     <input type="text" name="nickname" th:value="${user.nickname}" lay-verify="required" placeholder="请输入昵称" autocomplete="off" class="layui-input">
//     </div>
//     <div class="layui-form-mid layui-word-aux">*不多于30个字符</div>
//     </div>
//     <div class="layui-form-item">
//     <div class="layui-col-md4 layui-col-md-offset2">
//     <label class="layui-form-label">头像</label>
//     <div class="layui-input-inline">
//     <button type="button" class="layui-btn" id="test1">
//     <i class="layui-icon">&#xe67c;</i>上传图片
// </button>
// </div>
// </div>
// </div>
// <div class="layui-form-item layui-col-md-offset4">
//     <div class="layui-input-block">
//     <button class="layui-btn" lay-submit lay-filter="formDemo">立即提交</button>
//     <button type="reset" class="layui-btn layui-btn-primary">重置</button>
//     </div>
//     </div>
//     </form>
//     <script>
//     layui.use('form', function () {
//         var form = layui.form, upload = layui.upload;
//         form.render();
//         Common.uploadFile('#test1', upload, function (data) {
//             console.log(data);
//         });
//         //监听提交
//         form.on('submit(formDemo)', function (data) {
//             layer.msg(JSON.stringify(data.field));
//             //TODO ajax提交表单
//             return false;
//         });
//     });
// </script>
// </body>
// </html>



//layui.use('form', function () {
//     var form = layui.form;
//     //自定义验证规则
//     form.verify({
//         username: function (value) {
//             if (value.length < 6 || value.length > 12) {
//                 return '请输入6到12位的用户名';
//             }
//         }, password: function (value) {
//             if (value.length < 4) {
//                 return '内容请输入至少4个字符';
//             }
//         }
//         , phone: [/^1[3|4|5|7|8]\d{9}$/, '手机必须11位，只能是数字！']
//         , email: [/^[a-z0-9._%-]+@([a-z0-9-]+\.)+[a-z]{2,4}$|^1[3|4|5|7|8]\d{9}$/, '邮箱格式不对']
//     });
// })