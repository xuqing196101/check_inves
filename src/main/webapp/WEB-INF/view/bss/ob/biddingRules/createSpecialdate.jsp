<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
  <head>
   <%@ include file="/WEB-INF/view/common.jsp" %>
<title>创建特殊日期</title>

<script type="text/javascript">
	function submitForm(){
		var authType='${authType}'; 
	    if(authType!='4'){
	    layer.msg("只有资源服务中心才能操作");
	    return;
	    }
		$("#specialDateErr").html("");
		$("#dateTypeErr").html("");
		
		if($("#specialDate").val()==''){
			$("#specialDateErr").html("*请输入设置日期");
			return;
		}
		if($("#dateType").val()==''){
			$("#dateTypeErr").html("*请选择类型");
			return;
		}
		
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
			<li><a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a></li>
			<li><a href="javascript:void(0)">保障作业</a></li>
			<li><a href="javascript:void(0)">网上竞价</a></li>
			<li class="active"><a href="javascript:jumppage('${pageContext.request.contextPath}/obrule/holidayList.html')">节假日管理</a></li>
			<li class="active"><a href="javascript:void(0)">创建特殊日期</a></li>
		</ul>
        <div class="clear"></div>
      </div>
   </div>
   <!-- 发布定型产品页面开始 -->
   <div class="container">
	  <div class="bggrey p20 mt10">
  		<form action="" id="specialDataForm" name="specialDataForm" method="post">
		    <h2 class="list_title">添加特殊日期</h2>
		   <ul class="ul_list w100p">
		     <li class="col-md-3 col-sm-6 col-xs-12 pl15">
			   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div class="red star_red">*</div>设置日期</span>
			   <div class="input-append input_group col-sm-12 col-xs-12 p0">
					<input name="specialDate" id="specialDate" class="Wdate w100p mb0" type="text" id="d17" onfocus="WdatePicker({firstDayOfWeek:1})"/>
		        <div class="cue"><span><font id="specialDateErr" style="color: red"></font></span></div>
		       </div>
			 </li>
			 
		     <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>类型</span>
			   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
			        <select class="w100p" id="dateType" name="dateType">
	               		<option value="">--请选择--</option>
	               		<option value="1">上班</option>
	              		<option value="0">放假</option>
		            </select>
					<div class="cue"><span><font id="dateTypeErr" style="color: red"></font></span></div>
		       </div>
			 </li>
		   </ul>
       <div class="clear"></div>
		</form>
		 <div class="clear tc mt10">
	   		<button class="btn btn-windows save" type="button" onclick="submitForm()">保存</button>
	   		<button class="btn btn-windows back" type="button" onclick="history.go(-1)">返回</button>
		 </div>
		</div>
  </div>
</body>
</html>