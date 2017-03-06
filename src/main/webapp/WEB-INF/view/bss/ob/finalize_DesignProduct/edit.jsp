<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
  <head>
   <%@ include file="/WEB-INF/view/common.jsp" %>
<title>发布定型产品页面</title>
<script type="text/javascript">
	


</script>
</head>
<body>
<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0)"> 首页</a></li><li><a href="javascript:void(0)">保障作业</a></li><li><a href="javascript:void(0)">定型产品竞价</a></li>
		   <li class="active"><a href="javascript:void(0)">定型产品管理</a></li>
		</ul>
        <div class="clear"></div>
      </div>
   </div>
   <!-- 修改定型产品页面开始 -->
  <div class="wrapper mt10">
  <div class="container">
  <div class="headline-v2">
     	<h2>修改定型产品</h2>
	</div> 
  <div class="mt10">
   </div> 
  <table class="table table-bordered mt10">
			    <tbody>
				  <tr>
				    <td class="info"><div class="star_red">*</div>产品代码</td>
				    <td>
				    	<input id="" name="" value="sss" type="text" class="w230 mb0 border0" readonly>
				    </td>
				    <td class="info"><div class="star_red">*</div>产品名称</td>
				    <td>
				    	<input id="" name="" value="" type="text" class="w230 mb0 border0" readonly>
				    </td>
				  </tr>
				  <tr>
				    <td class="info"><div class="star_red">*</div>采购机构</td>
				    <td>
				    	<input id="" name="" value="" type="text" class="w230 mb0 border0" readonly>
					</td>
					<td colspan="2"></td>
				  </tr>
				   <tr>
				    <td class="info">选择目录</td>
				    <td colspan="3">
				    	<button class="btn">选择目录</button>
				    </td>
				  </tr>
				  <tr>
				    <td class="info">规格型号</td>
				    <td colspan="3">
				   		<div class="col-md-12 col-sm-12 col-xs-12 p0">
        					<textarea class="col-md-12 col-sm-12 col-xs-12" style="height:130px"></textarea>
       					</div>
				   	</td>
				  </tr>
				  <tr>
				    <td class="info">质量技术标准</td>
				    <td colspan="3">
				   		<div class="col-md-12 col-sm-12 col-xs-12 p0">
        					<textarea class="col-md-12 col-sm-12 col-xs-12" style="height:130px"></textarea>
       					</div>
				   	</td>
				  </tr>
				 </tbody>
			 </table>
			 
			 <div class="col-md-12 clear tc mt10">
			 	<button class="btn btn-windows edit" type="submit">修改</button>
	    		<button class="btn btn-windows back" type="button" onclick="history.go(-1)">返回</button>
			 </div>
	</div>
  </div>
</body>
</html>