<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>专家黑名单修改</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/supplier/css/supplier.css" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
<style type="text/css">

.jbxx1 i{
    width: 24px;
    height: 30px;
    background: url(../../../../../zhbj/public/ZHQ/images/round.png) no-repeat center;
    color: #ffffff;
    font-size: 12px;
    text-align: center;
    display: block;
    float: left;
    line-height: 30px;
    font-style: normal;
    margin-right: 10px;
}
</style>
<script type="text/javascript">
  /** 初始化下拉框*/
  $(function(id) {
    var optionNodes = $("option");
    for(var i = 0; i < optionNodes.length; i++) {
      if("${expert.relName}" == $(optionNodes[i]).val()) {
        optionNodes[i].selected = true;
      }
    }
  });
</script>
</head>
<body>
  <!--面包屑导航开始-->
  <div class="margin-top-10 breadcrumbs ">
    <div class="container">
      <ul class="breadcrumb margin-left-0">
        <li><a href="#"> 首页</a></li><li><a href="#">评审专家管理</a></li><li><a href="#">不良专家名单管理</a></li><li class="active"><a href="#">修改信息</a></li>
      </ul>
    </div>
  </div>
<!-- 表格开始-->
  <div class="container">
    <form action="<%=basePath %>expert/updateBlacklist.html" method="post" id="form1" enctype="multipart/form-data" class="registerform">
      <input type="hidden" name="id" value="${expert.id}">
      <h2 class="f16 jbxx1">
         <i>01</i>基本信息
      </h2>
      <ul class="list-unstyled list-flow p0_20">
        <li class="col-md-6 p0">
          <span>专家姓名：</span>
            <select name="relName" class="span2">
              <option value="">请选择</option>
              <c:forEach var="expert"  items="${expertList}">
              <option value="${expert.relName}">${expert.relName}</option>
              </c:forEach>
            </select> 
        </li>
        <li class="col-md-6  p0 ">
          <span class="">入库时间：</span>
          <input class="span2 Wdate w220"  readonly="readonly" name="storageTime"  type="text" value="<fmt:formatDate value="${expert.storageTime}" pattern='yyyy-MM-dd'/>" onclick='WdatePicker()'>
          <font id="nameFont2"></font>
	      </li> 
        <li class="col-md-6  p0 ">
	        <span class="">处罚日期：</span>
          <input class="span2 Wdate w220"  readonly="readonly" name="dateOfPunishment"  type="text" value="<fmt:formatDate value="${expert.dateOfPunishment}" pattern='yyyy-MM-dd'/>" onclick='WdatePicker()'>
          <font id="nameFont2"></font>
	      </li> 
        <li class="col-md-6  p0 ">
	        <span class="">处罚时限：</span>
	        <select class="span2" name="punishDate">
			  		<option value="">请选择</option>
			  		<option value="3个月" <c:if test="${'3个月' eq expert.punishDate}">selected</c:if>>三个月</option>
			  		<option value="6个月" <c:if test="${'6个月' eq expert.punishDate}">selected</c:if>>半年</option>
			  		<option value="一年" <c:if test="${'一年' eq expert.punishDate}">selected</c:if>>一年</option>
			  		<option value="两年" <c:if test="${'两年' eq expert.punishDate}">selected</c:if>>两年</option>
			  		<option value="三年" <c:if test="${'三年' eq expert.punishDate}">selected</c:if>>三年</option>
		      </select>
	      </li> 
        <li class="col-md-6  p0 ">
	        <span class="">处罚方式：</span>
	  	    <select class="span2" name="punishType">
			  	  <option value="">请选择</option>
			  		<option value="1" <c:if test="${'1' eq expert.punishType}">selected</c:if>>警告</option>
			  		<option value="2" <c:if test="${'2' eq expert.punishType}">selected</c:if>>严重警告</option>
			  		<option value="3" <c:if test="${'3' eq expert.punishType}">selected</c:if>>取消资格</option>
		      </select>
				</li> 
				<li class="col-md-12 p0 mt10"><span class="fl">处罚理由：</span>
          <div class="col-md-9 mt5">
            <div class="row">
              <textarea class="text_area col-md-12" name="reason" title="不超过800个字" style="width:770px;">${expert.reason }</textarea>
            </div>
          </div>
        </li> 
			</ul>
			<div class=" margin-bottom-0 fl">
       <h2 class="f16 jbxx1">
        <i>02</i>附件上传
       </h2>
       <ul class="list-unstyled list-flow p0_20">
        <li >
          <span class="" ><i class="red">＊</i>批准文件:</span>
            <input class="span3" type="file" name="attachmentCertFile"/>
         </li>
       </ul>
     </div>
      <div class="margin-bottom-0  categories">
        <div class="col-md-12 add_regist tc">
          <button class="btn btn-windows save" type="submit">保存</button>
          <a class="btn btn-windows reset"  onclick="location.href='javascript:history.go(-1);'">返回</a>
        </div>
      </div>
    </form>
  </div>
</body>
</html>
