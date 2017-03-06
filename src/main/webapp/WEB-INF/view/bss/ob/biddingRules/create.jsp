<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
  <head>
   <%@ include file="/WEB-INF/view/common.jsp" %>
<title>创建竞价规则</title>
</head>
<body>
<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0)"> 首页</a></li><li><a href="javascript:void(0)">保障作业</a></li><li><a href="javascript:void(0)">定型产品竞价</a></li>
		   <li class="active"><a href="javascript:void(0)">竞价规则管理</a></li><li class="active"><a href="javascript:void(0)">创建竞价规则</a></li>
		</ul>
        <div class="clear"></div>
      </div>
   </div>
   <!-- 发布定型产品页面开始 -->
  <div class="wrapper mt10">
  <div class="container">
  <div class="headline-v2">
     	<h2>添加竞价规则</h2>
	</div> 
  <div class="mt10">
   </div> 
  <table class="table table-bordered mt10">
			    <tbody>
				  <tr>
				    <td class="bggrey tr">间隔工作日：</td>
				    <td >
				    	<input id="" name="" value="" type="text" class="w230 mb0 border0">
				    </td>
				    <td class="bggrey tr">具体时间点：</td>
				    <td >
				    	<input id="" name="" value="" type="text" class="w230 mb0 border0">
				    </td>
				  </tr>
				  <tr>
				    <td class="bggrey tr">报价时间（分钟）：</td>
				    <td >
				    	<input id="" name="" value="" type="text" class="w230 mb0 border0">
				    </td>
				    <td class="bggrey tr">二次报价时间（分钟）：</td>
				    <td >
				    	<input id="" name="" value="" type="text" class="w230 mb0 border0">
				    </td>
				  </tr>
				  <tr>
				    <td class="bggrey tr">确认时间（分钟）：</td>
				    <td >
				    	<input id="" name="" value="" type="text" class="w230 mb0 border0">
				    </td>
				  </tr>
				 </tbody>
			 </table>
			 
			 <div class="col-md-12 clear tc mt10">
	    		<button class="btn btn-windows save" type="submit">保存</button>
	    		<button class="btn btn-windows back" type="button" onclick="history.go(-1)">返回</button>
			 </div>
	</div>
  </div>
</body>
</html>