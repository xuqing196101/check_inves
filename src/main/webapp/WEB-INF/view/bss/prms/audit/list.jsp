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
    
    
    <title>评标管理</title>  
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    
    


<link href="<%=basePath%>public/ZHH/css/common.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/bootstrap.min.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/style.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/line-icons.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/app.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/application.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/header-v4.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/header-v5.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/brand-buttons.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/footer-v2.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/img-hover.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/page_job.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/shop.style.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/purchase/css/purchase.css" media="screen" rel="stylesheet" type="text/css" >

<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery_ujs.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=basePath%>public/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>public/layer/layer.js"></script>
<script src="<%=basePath%>public/laypage-v1.3/laypage/laypage.js"></script>

 
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
	  var count = 0;
	  var ids = document.getElementsByName("chkItem");
 
     for(i=0;i<ids.length;i++) {
   		 if(document.getElementsByName("chkItem")[i].checked){
   		 var id = document.getElementsByName("chkItem")[i].value;
   		 var value = id.split(",");
   		 count++;
    }
  }   
  		if(count>1){
  			layer.alert("只能选择一条记录",{offset: ['222px', '390px'],shade:0.01});
  		}else if(count<1){
  			layer.alert("请选择一条记录",{offset: ['222px', '390px'],shade:0.01});
  		}else if(count==1){
  			window.location.href="<%=basePath%>expert/toFirstAudit.html?projectId="+value[0]+"&packageId="+value[1];
     	}
	  
  }
  </script>
  </head>
  
  <body>
<!--面包屑导航开始-->
 <div class="margin-top-10 breadcrumbs ">
      <div class="container">
           <ul class="breadcrumb margin-left-0">
           <li><a href="#"> 首页</a></li><li><a href="#">个人首页</a></li><li><a href="#">项目评审</a></li>
           </ul>
        <div class="clear"></div>
      </div>
   </div>
<!-- 录入采购计划开始-->
 <div class="container">
<!-- 项目戳开始 -->
     <div class="clear"></div>
   <div class="headline-v2 fl">
      <h2>项目评审</h2>
   </div> 
   <div class="container clear margin-top-30">
   <span class="fr option_btn margin-top-10">
        <button class="btn padding-left-10 padding-right-10 btn_back" onclick="toAudit();">项目评审</button>
      </span>
    <div class="container margin-top-5">
               <table class="table table-striped table-bordered table-hover">
        <thead>
        <tr>
          <th class="info w30"><input type="checkbox" id="checkAll" onclick="selectAll()"  alt=""></th>
          <th class="info w50">序号</th>
          <th class="info">项目名称</th>
          <th class="info">项目编号</th>
          <th class="info">包名</th>
          <th class="info">采购方式</th>
          <th class="info">创建时间</th>
          <th class="info">当前处理人</th>
          <th class="info">项目状态</th>
        </tr>
        </thead>
        <tbody id="tbody_id">
        
        <c:forEach items="${projectExtList}" var="obj" varStatus="vs">
            <tr style="cursor: pointer;">
              <td class="tc w30"><input type="checkbox" value="${obj.id },${obj.packageId}" name="chkItem" onclick="check()"  alt=""></td>
              <td class="tc w50">${vs.count}</td>
              <td class="tc">${obj.name }</td>
              <td class="tc">${obj.projectNumber }</td>
              <td class="tc">${obj.packageName }</td>
              <td class="tc">${obj.purchaseType }</td>
              <td class="tc"><fmt:formatDate type='date' value='${obj.createAt }' dateStyle="default" pattern="yyyy-MM-dd"/></td>
              <td class="tc">${sessionScope.loginUser.loginName}</td>
              <td class="tc">
              <c:if test="${'1'==obj.status}">实施中</c:if>
              <c:if test="${'2'==obj.status}">已成交</c:if>
              <c:if test="${'3'==obj.status}">已立项</c:if>
              </td>
            </tr>
     
         </c:forEach> 
        </tbody>
      </table>
      </div>
   </div>
 </div>


 
     </body>
</html>
