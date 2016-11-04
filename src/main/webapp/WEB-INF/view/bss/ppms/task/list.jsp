<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/tld/upload" prefix="f"%>
    <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    
    <title>任务管理</title>  
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/common.css" media="screen" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/bootstrap.min.css" media="screen" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/style.css" media="screen" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/line-icons.css" media="screen" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/app.css" media="screen" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/application.css" media="screen" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/header-v4.css" media="screen" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/header-v5.css" media="screen" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/brand-buttons.css" media="screen" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/footer-v2.css" media="screen" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/img-hover.css" media="screen" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/page_job.css" media="screen" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/shop.style.css" media="screen" rel="stylesheet" type="text/css">
    
    <script type="text/javascript" src="${pageContext.request.contextPath}/public/ZHH/js/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/public/ZHH/js/ajaxfileupload.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/public/ZHH/js/jquery.validate.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/public/ZHH/js/jquery_ujs.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/public/lodop/LodopFuncs.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/public/ZHH/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/public/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
    <script src="${pageContext.request.contextPath}/public/laypage-v1.3/laypage/laypage.js"></script>

 
  <script type="text/javascript">
  
  /*分页  */
  $(function(){
      laypage({
            cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
            pages: "${info.pages}", //总页数
            skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
            skip: true, //是否开启跳页
            total:"${info.total}",
            startRow:"${info.startRow}",
            endRow:"${info.endRow}",
            groups: "${info.pages}">=3?3:"${info.pages}", //连续显示分页数
            curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
               /* var page = location.search.match(/page=(\d+)/);
                  return page ? page[1] : 1; */
                return "${info.pageNum}";
            }(), 
            jump: function(e, first){ //触发分页后的回调
                    if(!first){ //一定要加此判断，否则初始时会无限刷新
                        $("#page").val(e.curr);
                        $("#form1").submit();
                    
                }  
            }
        });
  });
  
  
    /** 全选全不选 */
    function selectAll(){
         var checklist = document.getElementsByName ("chkItem");
         var checkAll = document.getElementById("checkAll");
         if(checkAll.checked){
               for(var i=0;i<checklist.length;i++)
               {
                  checklist[i].checked = true;
               } 
             }else{
              for(var j=0;j<checklist.length;j++)
              {
                 checklist[j].checked = false;
              }
            }
        }
    
    /** 单选 */
    function check(){
         var count=0;
         var checklist = document.getElementsByName ("chkItem");
         var checkAll = document.getElementById("checkAll");
         for(var i=0;i<checklist.length;i++){
               if(checklist[i].checked == false){
                   checkAll.checked = false;
                   break;
               }
               for(var j=0;j<checklist.length;j++){
                     if(checklist[j].checked == true){
                           checkAll.checked = true;
                           count++;
                       }
                 }
           }
    }
        /** 取消任务 */
        function deleted(){
        var id =[]; 
        $('input[name="chkItem"]:checked').each(function(){ 
            id.push($(this).val()); 
        }); 
        $("#ids").val(id);
        if(id.length==1){
           layer.open({
          type: 1, //page层
          area: ['500px', '300px'],
          title: '您是要取消任务吗？',
          shade:0.01, //遮罩透明度
          moveType: 1, //拖拽风格，0是默认，1是传统拖动
          shift: 1, //0-6的动画形式，-1不开启
          offset: ['220px', '630px'],
          shadeClose: true,
          content: $("#delTask")
        });
            
        }else if(id.length>1){
            layer.alert("只能选择一个",{offset: ['222px', '390px'], shade:0.01});
        }else{
            layer.alert("请选择需要取消的任务",{offset: ['222px', '390px'], shade:0.01});
        }
    }
    /** 受领任务 */
    function start(){
        var ids =[]; 
        $('input[name="chkItem"]:checked').each(function(){ 
            ids.push($(this).val()); 
        }); 
        var status = $("input[name='chkItem']:checked").parents("tr").find("td").eq(5).text();
        status = $.trim(status);
        
        if(ids.length>0){
        if(status == "审核"){
            layer.confirm('您确定要受领吗?', {title:'提示',offset: ['222px','360px'],shade:0.01}, function(index){
                layer.close(index);
                $.ajax({
                    url:"${pageContext.request.contextPath}/task/startTask.do",
                    data:"ids="+ids,
                    type:"post",
                    dateType:"json",
                    success:function(){
                        layer.msg("受领成功",{offset: ['222px', '390px']});
                        window.setTimeout(function(){
                            location.reload();
                        }, 1000);
                    },
                    error: function(){
                        layer.msg("受领失败",{offset: ['222px', '390px']});
                    }
                });
            });
            }else{
            layer.alert("任务已经受领",{offset: ['222px', '800px'], shade:0.01});
            } 
        }else{
            layer.alert("请选择要受领的任务",{offset: ['222px', '800px'], shade:0.01});
        }
       
    }
    /** 修改任务 */
    function edit(){
     var id =[]; 
        $('input[name="chkItem"]:checked').each(function(){ 
            id.push($(this).val()); 
        }); 
         if(id.length==1){
           window.location.href="${pageContext.request.contextPath}/task/edit.html?id="+id;
        }else if(id.length>1){
            layer.alert("只能选择一个",{offset: ['222px', '390px'], shade:0.01});
        }else{
            layer.alert("请选择需要调整的任务",{offset: ['222px', '390px'], shade:0.01});
        }
    }
    /** 查看任务 */
    function view(id){
           window.location.href="${pageContext.request.contextPath}/task/view.html?id="+id;
    }
    /** 重置任务 */
    function clearSearch(){
        $("#purchaseRequiredId").attr("value","");
        $("#documentNumber").attr("value","");
        $("#purchaseId").attr("value","");
        //还原select下拉列表只需要这一句
        $("#status option:selected").removeAttr("selected");
    }
    /** 上传附件 */
    function delTask(){
            var attach = $("input[name='attach']").val();
            if(!attach){
                layer.alert("请上传凭证",{offset: ['50px','90px'], shade:0.01});
            }else{
             layer.confirm('此操作后果严重，您确认要取消任务吗?',{
                offset: ['300px','600px'],
                shade:0.01,
                btn:['是','否'],
                },function(){
                    $("#att").submit();
                },function(){
                     parent.layer.close();
                }
              ); 
            }
               
}
    /** 关闭页面 */
    function cancel(){
                   layer.closeAll();
            }
  </script>
  </head>
  
  <body>
<!--面包屑导航开始-->
 <div class="margin-top-10 breadcrumbs ">
      <div class="container">
           <ul class="breadcrumb margin-left-0">
           <li><a href="#"> 首页</a></li><li><a href="#">保障作业系统</a></li><li><a href="#">采购任务管理</a></li><li class="active"><a href="#">采购任务管理</a></li>
           </ul>
        <div class="clear"></div>
      </div>
   </div>
<!-- 录入采购计划开始-->
 <div class="container">
   <div class="headline-v2">
      <h2>查询条件</h2>
   </div>
<!-- 项目戳开始 -->
 
  <div class="container clear">
  <div class="p10_25">
     <h2 class="padding-10 border1">
     <form id="form1" action="${pageContext.request.contextPath}/task/list.html" method="post" >
     <input type="hidden" name="page" id="page">
     <ul class="demand_list">
       <li class="fl">
         <label class="fl">需求部门：<input type="text" name="purchaseRequiredId" id="purchaseRequiredId" value="${task.purchaseRequiredId }"/></label>
       </li>
     <%--  <label class="fl">年度：<select name="giveTime" style="width:70px" id="select">
    <option selected="selected" value="">请选择</option>
       <c:forEach items="${task}" var="task">
                            
                            <option value="${task.giveTime}">${task.giveTime}</option>
                         
       </c:forEach>  
  </select> </label>--%>
       <%--  <li class="fl">
         <label class="fl">采购方式：
           <select name="procurementMethod" style="width:100px" id="select">
             <option selected="selected" value="">请选择</option>
             <option value="1" <c:if test="${'1'==task.procurementMethod}">selected="selected"</c:if>>公开招标</option>
             <option value="2" <c:if test="${'2'==task.procurementMethod}">selected="selected"</c:if>>邀请招标</option>
           </select>
          </label> 
  </li> --%>
  <li class="fl">
       <label class="fl">采购机构：<input type="text" name="purchaseId" id="purchaseId" value="${task.purchaseId }"/></label>
       </li>
       <li class="fl">
      <label class="fl">状态：<select name="status" style="width:70px" id="status">
        <option selected="selected" value="">请选择</option>
        <option value="1" <c:if test="${'1'==task.status}">selected="selected"</c:if>>审核</option>
        <option value="0" <c:if test="${'0'==task.status}">selected="selected"</c:if>>受领</option>
        </select>
       </label>
     </li>
   <li class="fl">
       <label class="fl">文件编号：<input type="text" name="documentNumber" id="documentNumber" value="${task.documentNumber }"/></label>
       </li>
         <button class="btn" type="submit">查询</button>
         <button type="reset" class="btn" onclick="clearSearch();">重置</button> 
     </ul>
     <div class="clear"></div>
    </form>
   </h2>
   </div>
  </div>
  
   <div class="headline-v2 fl">
      <h2>任务列表
      </h2>
   </div> 
      <span class="fr option_btn margin-top-10 mr5">
        <button class="btn btn-windows edit" onclick="edit()">任务调整</button>
        <button class="btn btn-windows delete"  onclick="deleted()">任务取消</button>
        <button class="btn btn-windows git" onclick="start()">受领</button>
      </span>
    <div class="container">
     <div class="content padding-left-25 padding-right-25 padding-top-5">
        <table class="table table-bordered table-condensed table-hover">
        <thead>
        <tr>
          <th class="info w30"><input type="checkbox" id="checkAll" onclick="selectAll()"  alt=""></th>
          <th class="info w50">序号</th>
          <th class="info">采购任务名称</th>
          <th class="info">采购管理部门</th>
          <th class="info">下达文件编号</th>
          <th class="info">状态</th>
          <th class="info">下达时间</th>
        </tr>
        </thead>
         <c:forEach items="${info.list}" var="obj" varStatus="vs">
            <tr style="cursor: pointer;">
              <td class="tc w30"><input type="checkbox" value="${obj.id }" name="chkItem" onclick="check()"  alt=""></td>
              <td class="tc w50">${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
              <td class="tc"><a href="#" onclick="view('${obj.id}');">${obj.name}</a></td>
              <td class="tc"><a href="#" onclick="view('${obj.id}');">${obj.purchaseId }</a></td>
              <td class="tc"><a href="#" onclick="view('${obj.id}');">${obj.documentNumber }</a></td>
              <td class="tc">
               <c:if test="${'1'==obj.status}"><span class="label rounded-2x label-u">已下达</span></c:if>
               <c:if test="${'0'==obj.status}"><span class="label rounded-2x label-u">受领</span></c:if>
               <c:if test="${'2'==obj.status}"><span class="label rounded-2x label-dark">已取消</span></c:if>
              </td>
              <td class="tc" ><fmt:formatDate value="${obj.giveTime }"/></td>
            </tr>
     
         </c:forEach> 
         

    </table>
     </div>
        <div id="delTask" style="display: none;">
             <form id="att" action="${pageContext.request.contextPath}/task/delTask.html" 
        method="post" name="form1" class="simple" target="_parent" enctype="multipart/form-data">
        <input type="hidden" id="ids" name="id"/>
        <span class="f14 fl">上传附件：</span>
        <div class="fl" id="uploadAttach" >
          <input id="pic" type="file" class="toinline" name="attach"/>
        
        </div>
        <br/><br/><br/>
     <a class="btn btn-windows save" onclick="delTask();">确认</a>
         <input class="btn btn-windows reset" value="取消" type="button" onclick="cancel();">
    </form>
        </div>
   </div>
      <div id="pagediv" align="right"></div>
   </div>
 
     </body>
</html>
