<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
    <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    
    <title>供应商信息</title>  
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    
    


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
<link href="${pageContext.request.contextPath}/public/purchase/css/purchase.css" media="screen" rel="stylesheet" type="text/css" >

<script type="text/javascript" src="${pageContext.request.contextPath}/public/ZHH/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ZHH/js/jquery_ujs.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ZHH/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
<script src="${pageContext.request.contextPath}/public/laypage-v1.3/laypage/laypage.js"></script>

 
  <script type="text/javascript">
  
  
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
   //项目评审
  function toAudit(){
	     var projectId = "${projectId}";
	     var packageId = "${packageId}";
  			window.location.href="${pageContext.request.contextPath}/reviewFirstAudit/toAudit.html?projectId="+projectId+"&packageId="+packageId;
	  
  }
   //项目评分
  function toGrade(){
	     var projectId = "${projectId}";
	     var packageId = "${packageId}";
  			window.location.href="${pageContext.request.contextPath}/reviewFirstAudit/toGrade.html?projectId="+projectId+"&packageId="+packageId;
	  
  }
  </script>
  </head>
  
  <body>
<!--面包屑导航开始-->
 <div class="margin-top-10 breadcrumbs ">
      <div class="container">
           <ul class="breadcrumb margin-left-0">
           <li><a href="#"> 首页</a></li><li><a href="#">个人首页</a></li><li><a href="#">项目评审</a></li><li><a href="#">供应商信息</a></li>
           </ul>
        <div class="clear"></div>
      </div>
   </div>
<!-- 录入采购计划开始-->
 <div class="container">
<!-- 项目戳开始 -->
     <div class="clear"></div>
   <div class="headline-v2 fl">
      <h2>供应商信息</h2>
   </div> 
   <div class="container clear margin-top-30">
   <span class="fr option_btn margin-top-10">
   	  <c:if test="${packageExpert.isAudit == 1 }">
   	   <button class="btn padding-left-10 padding-right-10 btn_back" onclick="toGrade();">专家评分</button>
   	  </c:if>
   	   <c:if test="${packageExpert.isAudit != 1 }">
   	   <button class="btn padding-left-10 padding-right-10 btn_back" onclick="toAudit();">符合性检查</button>
   	  </c:if>
       <!--  <button class="btn padding-left-10 padding-right-10 btn_back" onclick="toAudit();">符合性检查</button> -->
        <button class="btn padding-left-10 padding-right-10 btn_back" onclick="">查看供应商报价</button>
      </span>
    <div class="container margin-top-5">
               <table class="table table-striped table-bordered table-hover">
        <thead>
        <tr>
          <th class="info w30"><input type="checkbox" id="checkAll" onclick="selectAll()"  alt=""></th>
          <th class="info w50">序号</th>
          <th class="info">供应商名称</th>
          <th class="info">联系人姓名</th>
          <th class="info">联系电话</th>
          <th class="info">联系地址</th>
          <th class="info">投标文件</th>
        </tr>
        </thead>
        <tbody id="tbody_id">
        
        <c:forEach items="${supplierList}" var="obj" varStatus="vs">
            <tr style="cursor: pointer;">
              <td class="tc w30"><input type="checkbox" value="${obj.suppliers.id }" name="chkItem" onclick="check()"  alt=""></td>
              <td class="tc w50">${vs.count}</td>
              <td class="tc">${obj.suppliers.supplierName }</td>
              <td class="tc">${obj.suppliers.contactName }</td>
              <td class="tc">${obj.suppliers.contactTelephone }</td>
              <td class="tc">${obj.suppliers.contactAddress }</td>
              <td class="tc"></td>
            </tr>
     
         </c:forEach> 
        </tbody>
      </table>
      </div>
   </div>
    <div class="headline-v2 fl">
          <h2>相关文件下载</h2>
    </div>
    <div class="container clear margin-top-30">
    <div class="container margin-top-5">
    
    
    
    
    <input class="btn btn-windows back" value="返回" type="button" onclick="location.href='javascript:history.go(-1);'"><br/>
    </div>
    </div>
 </div>
     </body>
</html>
