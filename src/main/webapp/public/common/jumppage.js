/**
 * Created by lazy_mo on 2017/5/22.
 */

/**
 * @Author:changsongyang
 * @Description:面包屑点击跳转
 * @param path 传入打开路径
 * @Date:2017/5/23 16:45
 * @Version:1.0
 */
function jumppage(path) {
    jumpParam(path);
}

/**
 * @Author:changsongyang
 * @Description:HTML DOM 模拟post方式传参
 * @param path 传入路径
 * @Date:2017/5/23 16:41
 * @Version:1.0
 */
function jumpParam(path) {

    var form = document.createElement("form");        //创建form表单
    var url = globalPath+"/jumppage/jump.html";    //路由地址
    form.action = url
    form.method = "POST";                              //设置提交方式
    form.target = "_blank";                            //打开新页面

    var input = document.createElement("input");      //创建input表单
    input.setAttribute("type","hidden");               //设置隐藏
    input.setAttribute("name","returnUrl");           //设置name属性为returnUrl
    input.setAttribute("value",path);                   //设置传入路径

    form.appendChild(input);                             //将input隐藏域添加到form表单
    document.body.appendChild(form);                   //将form表单添加到文档域中
    form.submit();                                       //提交表单
    return form;
}

/**
 * GET方式传递参数
 * @param path 传入路径
 */
function jumpParam1(path) {
    //var returnUrl = window.parent.document.getElementById("iframepage").src=path;
    var url = globalPath+"/jumppage/jump.html?returnUrl="+path;
    window.open(url);

}

