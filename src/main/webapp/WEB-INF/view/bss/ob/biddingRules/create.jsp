<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
  <head>
   <%@ include file="/WEB-INF/view/common.jsp" %>
<title>创建竞价规则</title>

<script type="text/javascript">
	function submitForm(){
		$.post("${pageContext.request.contextPath}/obrule/addRule.do", $("#ruleForm").serialize(), function(data) {
			if (data.status == 200) {
				layer.confirm(data.data,{
					btn:['确定']
				},function(){
						window.location.href="${pageContext.request.contextPath}/obrule/ruleList.html";
					}
				) 
			}
			if(data.status == 500){
				layer.alert(data.msg);
			}
		});
	}
</script>
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
  		<form id="ruleForm" action="" method="post">
		  <table class="table table-bordered mt10">
		    <tbody>
			  <tr>
			    <td class="bggrey tr">竞价规则名称：</td>
			    <td >
			    	<input name="name" type="text" class="w230 mb0 border0">（天）
			    </td>
			  </tr>
			  <tr>
			    <td class="bggrey tr">间隔工作日：</td>
			    <td >
			    	<input name="intervalWorkday" type="text" class="w230 mb0 border0">（天）
			    </td>
			  </tr>
			  <tr>
			  	<td class="bggrey tr">具体时间点：</td>
			    <td >
			    	<input type="text" name="definiteTime"  id="d242" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'H:mm:ss'})" class="Wdate"/>
			    </td>
			  </tr>
			  
			  <tr>
			    <td class="bggrey tr">报价时间：</td>
			    <td >
			    	<input  name="quoteTime" type="text" class="w230 mb0 border0">（分钟）
			    </td>
			   <!--  <td class="bggrey tr">二次报价时间（分钟）：</td>
			    <td >
			    	<input id="" name="" value="" type="text" class="w230 mb0 border0">
			    </td> -->
			  </tr>
			  <tr>
			    <td class="bggrey tr">确认时间（第一轮）：</td>
			    <td >
			    	<input name="confirmTime" type="text" class="w230 mb0 border0">（分钟）
			    </td>
			  </tr>
			  <tr>
			    <td class="bggrey tr">确认时间（第二轮）：</td>
			    <td >
			    	<input name="confirmTimeSecond" type="text" class="w230 mb0 border0">（分钟）
			    </td>
			  </tr>
			</tbody>
		 </table>
	</form>
	 <div class="col-md-12 clear tc mt10">
   		<button class="btn btn-windows save" onclick="submitForm()">保存</button>
   		<button class="btn btn-windows back" type="button" onclick="history.go(-1)">返回</button>
	 </div>
	</div>
  </div>
 </div> 
</body>
</html>