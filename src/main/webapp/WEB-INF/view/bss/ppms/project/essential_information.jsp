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
    
    
    <title>项目管理</title>  
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
    function print(id){
        window.location.href = "<%=basePath%>project/print.html?id="+id;
    }
  </script>
  </head>
  
  <body>
<!--面包屑导航开始-->
 <div class="margin-top-10 breadcrumbs ">
      <div class="container">
           <ul class="breadcrumb margin-left-0">
           <li><a href="#"> 首页</a></li><li><a href="#">保障作业系统</a></li><li><a href="#">采购项目管理</a></li><li class="active"><a href="#">立项管理</a></li>
           </ul>
        <div class="clear"></div>
      </div>
<div class="container bggrey border1 mt20">
   <form action="" method="post">
   <div>
   <div class="headline-v2 bggrey">
   <h2>项目基本信息</h2>
   </div>
   <div class="tag-box tag-box-v4 col-md-9">
        <table class="table table-bordered">
        <tr>
          <td class="bggrey tr">项目编号:</td><td>${project.projectNumber}</td>
          <td class="bggrey tr">项目名称:</td><td>${project.name}</td>
        </tr>
        <tr>
          <td class="bggrey tr">负责人姓名:</td><td>${project.principal}</td>
          <td class="bggrey tr">负责人联系电话:</td><td>${project.ipone}</td>
        </tr>
        <tr>
          <td class="bggrey tr">联系人姓名:</td><td><input name="linkman" style="border-style:none" value="${project.linkman}"/></td>
          <td class="bggrey tr">联系人联系电话:</td><td><input name="linkmanIpone" style="border-style:none" value="${project.linkmanIpone}"/></td>
        </tr>
        <tr>
          <td class="bggrey tr">招标单位:</td><td>${project.sectorOfDemand}</td>
          <td class="bggrey tr">联系地址:</td><td>${project.address}</td>
        </tr>
        <tr>
          <td class="bggrey tr">邮编:</td><td>${project.postcode}</td>
          <td class="bggrey tr">最少供应商人数:</td><td><input name="linkmanIpone" style="border-style:none" value="${project.supplierNumber}"/></td>
        </tr>
        <tr>
          <td class="bggrey tr">报价标准分值:</td><td>${project.offerStandard}</td>
          <td class="bggrey tr">预算报价（万元）:</td><td>${project.budgetAmount}</td>
        </tr>
       <%--  <tr>
        <c:forEach items="${info.list}" var="obj" varStatus="vs">
          <td class="bggrey tr">${obj.name}密码:</td><td>${obj.passWord}</td>
          </c:forEach>
          <td class="bggrey tr">评分细则:</td><td>${project.scoringRubric}</td>
        </tr> --%>
        <tr>
          <td class="bggrey tr">采购方式:</td><td>${project.purchaseType}</td>
          <td class="bggrey tr">投标截止时间:</td><td>${project.deadline}</td>
        </tr>
        <tr>
          <td class="bggrey tr">开标时间:</td><td>${project.bidDate}</td>
          <td class="bggrey tr">开标地点:</td><td>${project.bidAddress}</td>
        </tr>
        <tr>
          <td class="bggrey tr">招标文件报批时间:</td><td>${project.approvalTime}</td>
          <td class="bggrey tr">招标文件批复时间:</td><td>${project.replyTime}</td>
        </tr>
        <%-- <tr>
          <td class="bggrey tr">需求计划提报时间:</td><td>${project.demandFromTime}</td>
          <c:forEach items="${info.list}" var="obj" varStatus="vs">
          <td class="bggrey tr">${obj.name}采购任务下达时间:</td><td><fmt:formatDate value='${obj.giveTime}' pattern='yyyy年MM月dd日  HH:mm:ss'/></td>
          </c:forEach>
        </tr> --%>
       <%--  <tr>
        <c:forEach items="${info.list}" var="obj" varStatus="vs">
          <td class="bggrey tr">${obj.name}采购任务受理时间:</td><td><fmt:formatDate value='${o.taskGiveTime}' pattern='yyyy年MM月dd日  HH:mm:ss'/></td>
          </c:forEach>
          <td class="bggrey tr">采购项目立项时间:</td><td><fmt:formatDate value='${project.createAt}' pattern='yyyy年MM月dd日  HH:mm:ss'/></td>
        </tr> --%>
        <tr>
          <td class="bggrey tr">采购项目实施时间:</td><td><fmt:formatDate value='${project.startTime}' pattern='yyyy年MM月dd日  HH:mm:ss'/></td>
          <td class="bggrey tr">招标公告发布时间:</td><td>${project.noticeNewsTime}</td>
        </tr>
        <tr>
          <td class="bggrey tr">招标公告审批时间:</td><td>${project.appTime}</td>
          <td class="bggrey tr">供应商报名时间:</td><td>${project.signUpTime}</td>
        </tr>
        <tr>
          <td class="bggrey tr">报名截止时间:</td><td>${project.applyDeanline}</td>
          <td class="bggrey tr">售后维护时间:</td><td>${project.maintenanceTime}</td>
        </tr>
        <tr>
          <td class="bggrey tr">发送中标通知书时间:</td><td>${project.noticeTime}</td>
          <td class="bggrey tr">项目结束时间:</td><td>${project.endTime}</td>
        </tr>
        <tr>
          <td class="bggrey tr">合同签订时间:</td><td>${project.signingTime}</td>
          <td class="bggrey tr">验收时间:</td><td>${project.acceptanceTime}</td>
        </tr>
      </table>
       <div id="pagediv" align="right"></div>
      <div class="headline-v2 bggrey">
           <h2>项目明细</h2>
      </div>
      <div class="headline-v2 bggrey">
           <h2>打印报批文件 &nbsp;&nbsp;<button class="btn padding-left-10 padding-right-10 btn_back" onclick="print('${project.id}')" type="button">打印报批文件</button></h2>
      </div>
      </div>
     
      </div>
      <div class="col-md-12 tc mt20" >
        <button class="btn btn-windows back" onclick="history.go(-1)" type="button">返回</button>
       </div>
      </form>
   </div>
 </div>
    

     </body>
</html>
