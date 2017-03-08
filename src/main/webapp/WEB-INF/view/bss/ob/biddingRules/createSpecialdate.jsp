<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
  <head>
   <%@ include file="/WEB-INF/view/common.jsp" %>
<title>创建特殊日期</title>

<script type="text/javascript">
	function submitForm(){
		$.post("${pageContext.request.contextPath}/obrule/addSpecialdate.do", $("#specialDataForm").serialize(), function(data) {
			if (data.status == 200) {
				layer.confirm(data.data,{
					btn:['确定']
				},function(){
						window.location.href="${pageContext.request.contextPath}/obrule/holidayList.html";
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
		   <li class="active"><a href="javascript:void(0)">竞价规则管理</a></li><li class="active"><a href="javascript:void(0)">创建特殊日期</a></li>
		</ul>
        <div class="clear"></div>
      </div>
   </div>
   <!-- 发布定型产品页面开始 -->
  <div class="wrapper mt10">
  <div class="container">
  <div class="headline-v2">
     	<h2>添加特殊日期</h2>
	</div> 
  <div class="mt10">
   </div>
   <form action="" id="specialDataForm" name="specialDataForm" method="post">
	  <table class="table table-bordered mt10">
	    <tbody>
		  <tr>
		    <td class="bggrey tr">设置日期：</td>
		    <td >
		    	<input name="specialDate" class="Wdate" type="text" id="d17" onfocus="WdatePicker({firstDayOfWeek:1})"/>
		    </td>
		  </tr>
		  <tr>
		    <td class="bggrey tr">类型：</td>
		    <td>
		    	<select class="w230" id="dateType" name="dateType">
               		<option value="">--请选择--</option>
               		<option value="1">上班</option>
              		<option value="0">放假</option>
	            </select>
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
</body>
</html>