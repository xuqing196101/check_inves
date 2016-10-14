<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>专家黑名单添加</title>

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
  $(function() {
    $("#expert_name").click(function() {
      layer.open({
        type : 2,
        title : '选择专家',
        // skin : 'layui-layer-rim', //加上边框
        area : [ '800px', '500px' ], //宽高
        offset : '80px',
        scrollbar : false,
        content : '${pageContext.request.contextPath}/expert/expert_list.html', //url
        closeBtn : 1, //不显示关闭按钮
      });
    });

  });
</script>
</head>
<body>
	<!--面包屑导航开始-->
	<div class="margin-top-10 breadcrumbs ">
	  <div class="container">
	    <ul class="breadcrumb margin-left-0">
	      <li><a href="#"> 首页</a></li><li><a href="#">评审专家管理</a></li><li><a href="#">不良专家名单管理</a></li><li class="active"><a href="#">增加信息</a></li>
			</ul>
	  </div>
	</div>
	<!-- 表格开始-->
  <div class="container">
    <form action="<%=basePath %>expert/saveBlacklist.do" method="post" id="form1" enctype="multipart/form-data" class="registerform">
    <input type="hidden" name="operationType" value="0">
      <h2 class="f16 jbxx1">
         <i>01</i>基本信息
      </h2>
      <ul class="list-unstyled list-flow p0_20">
		    <li class="col-md-6 p0">
          <span><i class="red">＊</i>专家姓名：</span>
            <input class="span2"  name="relName"  type="text" id="expert_name" readonly="readonly" value="${relName }">
        </li>
	      <li class="col-md-6  p0 ">
		      <span class=""><i class="red">＊</i>入库时间：</span>
	        <input class="span2 Wdate w220"  readonly="readonly" name="storageTime"  type="text" onclick='WdatePicker()'>
	        <font id="nameFont2"></font>
		    </li> 
	      <li class="col-md-6  p0 ">
		      <span class=""><i class="red">＊</i>处罚日期：</span>
	        <input class="span2 Wdate w220"  readonly="readonly" name="dateOfPunishment"  type="text" onclick='WdatePicker()'>
	        <font id="nameFont2"></font>
		    </li> 
		    <li class="col-md-6  p0 ">
	       <span class=""><i class="red">＊</i>处罚时限：</span>
			    <select class="span2" name="punishDate">
			  		<option value="">请选择</option>
			  		<option value="3个月">3月</option>
            <option value="6个月">6个月</option>
			  		<option value="一年">一年</option>
			  		<option value="两年">两年</option>
			  		<option value="三年">三年</option>
				  </select>
			  </li> 
	      <li class="col-md-6  p0 ">
		      <span class=""><i class="red">＊</i>处罚方式：</span>
		  	  <select class="span2" name="punishType">
			  	  <option value="">请选择</option>
			  		<option value="1">警告</option>
			  		<option value="2">严重警告</option>
			  		<option value="3">取消资格</option>
			    </select>
		    </li> 
		    <li class="col-md-12 p0 mt10"><span class="fl"><i class="red">＊</i>处罚理由：</span>
          <div class="col-md-9 mt5">
            <div class="row">
              <textarea class="text_area col-md-12" name="reason" title="不超过800个字" style="width:770px;"></textarea>
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
		    <a class="btn btn-windows reset"  onclick="location.href='${pageContext.request.contextPath}/expert/blacklist.html'">返回</a>
		   </div>
	  </div>
	 </form>
  </div>
</body>
</html>
