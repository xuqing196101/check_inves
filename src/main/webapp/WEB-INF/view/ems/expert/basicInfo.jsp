<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
//表单标示
	String tokenValue= new Date().getTime()+UUID.randomUUID().toString()+""; 
%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]--><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<title></title>

	<!-- Meta -->
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="">
	<link href="<%=basePath%>public/ZHH/css/common.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/bootstrap.min.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/style.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/animate.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/ui-dialog.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/dialog-select.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/line-icons.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/font-awesome.min.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/jquery.fileupload-ui.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/zTreeStyle.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/sky-forms.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/custom-sky-forms.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/jquery.fancybox.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/owl.carousel.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/owl.theme.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/style-switcher.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/shortcode_timeline2.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/app.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/blocks.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/datepicker.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/select2.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/application.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/header-v4.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/img-hover.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/brand-buttons.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/brand-buttons-inversed.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/blog_magazine.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/page_job.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/page_log_reg_v1.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/shop.style.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/masterslider.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/james.css" media="screen" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="<%=basePath%>public/ZHQ/css/validForm/style.css" type="text/css" media="all" />
<link href="<%=basePath%>public/ZHQ/css/validForm/demo.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="<%=basePath%>public/ZHH/js/messages_cn.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/hm.js"></script><script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery-migrate-1.2.1.min.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery_ujs.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.query.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/dialog-plus-min.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.fancybox.pack.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/smoothScroll.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.parallax.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/app.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/common.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/dota.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.dragsort-0.5.2.min.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/fancy-box.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/style-switcher.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/owl.carousel.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/owl-carousel.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/owl-recent-works.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.mCustomScrollbar.concat.min.js"></script>
<script type="text/javascript" src="<%=basePath%>public/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.form.min.js"></script>



<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.va.2.min.jsidate.min.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.maskedinput.min.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/masking.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/datepicker.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/timepicker.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/dialog-select.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/locale.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.ui.widget.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/load-image.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/canvas-to-blob.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/tmpl.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.fileupload.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.fileupload-fp.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.fileupload-ui.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery-fileupload.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/form.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/product-quantity.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/master-slider.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/shop.app.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/masterslider.min.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.easing.min.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/james.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/common.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/bootstrap.min.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/style.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/line-icons.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/app.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/application.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/header-v4.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/footer-v2.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/img-hover.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/page_job.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/shop.style.css" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ZHQ/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ZHQ/js/bootstrap.min.js"></script>

<script type="text/javascript">
	
	 function validataForm(inputText,fontId){
		var text = $(inputText).val();
		if(text.replace(/\s/g,"")==null || text.replace(/\s/g,"")==""){
			$("#"+fontId+"").html("不能为空").css('color','red');
		}else{
			$("#"+fontId+"").html("");
		}
	} 
	 function submitForm(){
		 	var x1=document.forms["form1"]["relName"].value;
			var x2=document.forms["form1"]["birthday"].value;
			var x3=document.forms["form1"]["idNumber"].value;
			var x4=document.forms["form1"]["nayion"].value;
			var x5=document.forms["form1"]["address"].value;
			var x6=document.forms["form1"]["graduateSchool"].value;
			var x7=document.forms["form1"]["professTechTitles"].value;
			var x8=document.forms["form1"]["timeToWork"].value;
			var x9=document.forms["form1"]["major"].value;
			var x10=document.forms["form1"]["timeStartWork"].value;
			var x11=document.forms["form1"]["workUnit"].value;
			var x12=document.forms["form1"]["unitAddress"].value;
			var x13=document.forms["form1"]["fixPhone"].value;
			var x14=document.forms["form1"]["fax"].value;
			var x15=document.forms["form1"]["zipCode"].value;
			var x16=document.forms["form1"]["makeTechDate"].value;
			var x17=document.forms["form1"]["degree"].value;
			var x18=document.forms["form1"]["healthState"].value;
			var x19=document.forms["form1"]["atDuty"].value;
			if (x1==null || x1.replace(/(^\s*)|(\s*$)/g, "")=="" || x2==null || x2.replace(/(^\s*)|(\s*$)/g, "")=="" || x3==null || x3.replace(/(^\s*)|(\s*$)/g, "")==""|| x4==null || x4.replace(/(^\s*)|(\s*$)/g, "")==""|| x5==null || x5.replace(/(^\s*)|(\s*$)/g, "")==""|| x6==null || x6.replace(/(^\s*)|(\s*$)/g, "")==""|| x7==null || x7.replace(/(^\s*)|(\s*$)/g, "")==""|| x8==null || x8.replace(/(^\s*)|(\s*$)/g, "")==""||  x9==null || x9.replace(/(^\s*)|(\s*$)/g, "")==""|| x10==null || x10.replace(/(^\s*)|(\s*$)/g, "")==""|| x11==null || x11.replace(/(^\s*)|(\s*$)/g, "")==""|| x12==null || x12.replace(/(^\s*)|(\s*$)/g, "")==""|| x13==null || x13.replace(/(^\s*)|(\s*$)/g, "")==""|| x14==null || x14.replace(/(^\s*)|(\s*$)/g, "")==""|| x15==null || x15.replace(/(^\s*)|(\s*$)/g, "")==""|| x16==null || x16.replace(/(^\s*)|(\s*$)/g, "")=="" || x17==null || x17.replace(/(^\s*)|(\s*$)/g, "")=="" || x18==null || x18.replace(/(^\s*)|(\s*$)/g, "")=="" || x19==null || x19.replace(/(^\s*)|(\s*$)/g, "")=="")
			  {
				return false;
			  }else{
				  return true;
			  }
	 }
	 function validateFlag(){
		 $("#hehe").val(1);
	 }
	 function validateFlag2(){
		 $("#hehe").val(2);
	 }
</script>
</head>
<body>
<div class="wrapper">
<!-- 修改订列表开始-->
   <div class="container">
   <form action="<%=basePath %>expert/edit.do" onsubmit="return submitForm()" method="post" id="form1" enctype="multipart/form-data" class="registerform"> 
   		<%
			session.setAttribute("tokenSession", tokenValue);
		%>
		 <input type="hidden"  name="token2" value="<%=tokenValue%>">
   <input type="hidden" name="id" value="${uuid }">
   <input type="hidden" id="hehe" name="zancun">
   <div>
    <jsp:include page="../../../../indexhead.jsp"></jsp:include>
   <div class="container clear margin-top-30" >
   			<h2 class="padding-20 mt40">
					<span class="new_step current fl"><i class="">1</i><div class="line"></div> <span class="step_desc_01">用户名密码</span> </span>
					<span class="new_step current fl"><i class="">2</i><div class="line"></div> <span class="step_desc_02">基本信息</span> </span> 
					<span class="new_step fl"><i class="">3</i><div class="line"></div> <span class="step_desc_01">专家类型</span> </span> 
					<span class="new_step fl"><i class="">4</i><div class="line"></div> <span class="step_desc_02">采购机构</span> </span> 
					<span class="new_step fl"><i class="">5</i><div class="line"></div> <span class="step_desc_01">打印申请表</span> </span> 
					<span class="new_step fl"><i class="">6</i> <span class="step_desc_02">上传申请表</span> </span> 
				<div class="clear"></div>
			</h2>
	</div>
	<br/><br/>
   <div class="headline-v2">
   <h2>评标专家信息</h2>
   </div>
   <ul class="list-unstyled list-flow p0_20">
     <li class="col-md-6 p0">
	   <span class="">姓名：</span>
        <input class="span2"  id="relName" maxlength="10"  name="relName" type="text" onblur="validataForm(this,'nameFont');">
          
       <font id="nameFont"></font>
	 </li>
     <li class="col-md-6  p0 ">
	   <span class="">性别：</span>
	   <select class="span2" name="sex" >
	   	<option value="男">男</option>
	   	<option value="女">女</option>
	   	<option value="其他">其他</option>
	   </select>
	    
	 </li>
     <li class="col-md-6  p0 ">
	   <span class="">出生日期：</span>
        <input class="span2 Wdate w220" onfocus="validataForm(this,'nameFont2');" onblur="validataForm(this,'nameFont2');" readonly="readonly" name="birthday" id="appendedInput" type="text" onclick='WdatePicker()'>
      <font id="nameFont2"></font>
	 </li> 
     <li class="col-md-6  p0 ">
	   <span class="">政治面貌：</span>
	   <select class="span2" name="politicsStatus" >
	   	<option value="党员">党员</option>
	   	<option value="团员">团员</option>
	   	<option value="群众">群众</option>
	   	<option value="其他">其他</option>
	   </select>
	 </li> 
     <li class="col-md-6  p0 ">
	   <span class="">证件类型：</span>
	   <select class="span2" name="idType" >
	   	<option value="身份证">身份证</option>
	   	<option value="士兵证">士兵证</option>
	   	<option value="驾驶证">驾驶证</option>
	   	<option value="工作证">工作证</option>
	   	<option value="护照">护照</option>
	   	<option value="其他">其他</option>
	   </select>
	 </li> 
     <li class="col-md-6  p0 ">
	   <span class="">证件号码：</span>
        <input class="span2" maxlength="30" onblur="validataForm(this,'nameFont3');" name="idNumber" id="appendedInput" type="text">
          
    <font id="nameFont3"></font>
	 </li> 
     <li class="col-md-6  p0 ">
	   <span class="">专家来源：</span>
	   <select class="span2" name="expertsFrom" >
	   	<option value="军队">军队</option>
	   	<option value="地方">地方</option>
	   	<option value="其他">其他</option>
	   </select>
	 </li>  
     <li class="col-md-6  p0 ">
	   <span class="">民族：</span>
        <input class="span2" maxlength="10" onblur="validataForm(this,'nameFont4');" name="nayion" id="appendedInput" type="text">
          
       <font id="nameFont4"></font>
	 </li> 
     <li class="col-md-6  p0 ">
	   <span class="">所在地区：</span>
        <input class="span2" maxlength="40" onblur="validataForm(this,'nameFont5');" name="address" id="appendedInput" type="text">
          
       <font id="nameFont5"></font>
	 </li>  
     <li class="col-md-6  p0 ">
	   <span class="">毕业院校：</span>
        <input class="span2" maxlength="40" onblur="validataForm(this,'nameFont6');" name="graduateSchool" id="appendedInput" type="text">
          
       <font id="nameFont6"></font>
	 </li> 
     <li class="col-md-6  p0 ">
	   <span class="">专业技术职称：</span>
        <input class="span2" maxlength="20" onblur="validataForm(this,'nameFont7');" name="professTechTitles" id="appendedInput" type="text">
          
       <font id="nameFont7"></font>
	 </li> 
     <li class="col-md-6  p0 ">
	   <span class="">参加工作时间：</span>
        <input class="span2 Wdate w220" onfocus="validataForm(this,'nameFont8');" onblur="validataForm(this,'nameFont8');" readonly="readonly" name="timeToWork" id="appendedInput" type="text" onclick='WdatePicker()'>
       <font id="nameFont8"></font>
	 </li> 
     <li class="col-md-6  p0 ">
	   <span class="">最高学历：</span>
	   <select class="span2" name="hightEducation" >
	   	<option value="博士">博士</option>
	   	<option value="硕士">硕士</option>
	   	<option value="研究生">研究生</option>
	   	<option value="本科">本科</option>
	   	<option value="专科">专科</option>
	   	<option value="高中">高中</option>
	   	<option value="其他">其他</option>
	   </select>
	 </li> 
     <li class="col-md-6  p0 ">
	   <span class="">专业：</span>
        <input class="span2" maxlength="20" onblur="validataForm(this,'nameFont9');" name="major" id="appendedInput" type="text">
          
       <font id="nameFont9"></font>
	 </li> 
	 <li class="col-md-6  p0 ">
	   <span class="">从事专业起始年度：</span>
        <input class="span2 Wdate w220" onfocus="validataForm(this,'nameFont10');" onblur="validataForm(this,'nameFont10');" readonly="readonly" name="timeStartWork" id="appendedInput" type="text" onclick='WdatePicker()'>
      <font id="nameFont10"></font>
	 </li> 
	  <li class="col-md-6  p0 ">
	   <span class="">工作单位：</span>
        <input class="span2" maxlength="40" onblur="validataForm(this,'nameFont11');" name="workUnit" id="appendedInput" type="text">
          
       <font id="nameFont11"></font>
	 </li> 
	  <li class="col-md-6  p0 ">
	   <span class="">单位地址：</span>
        <input class="span2" maxlength="40" onblur="validataForm(this,'nameFont12');" name="unitAddress" id="appendedInput" type="text">
          
       <font id="nameFont12"></font>
	 </li> 
	  <li class="col-md-6  p0 ">
	   <span class="">联系电话（固话）：</span>
        <input class="span2" maxlength="15" onblur="validataForm(this,'nameFont13');" name="fixPhone" id="appendedInput" type="text">
          
       <font id="nameFont13"></font>
	 </li> 
	  <li class="col-md-6  p0 ">
	   <span class="">传真：</span>
        <input class="span2" maxlength="10" onblur="validataForm(this,'nameFont14');" name="fax" id="appendedInput" type="text">
          
       <font id="nameFont14"></font>
	 </li> 
	  <li class="col-md-6  p0 ">
	   <span class="">邮政编码：</span>
        <input class="span2" maxlength="6" onblur="validataForm(this,'nameFont15');" name="zipCode" id="appendedInput" type="text">
          
       <font id="nameFont15"></font>
	 </li> 
	<li class="col-md-6  p0 ">
	   <span class="">取得技术职称时间：</span>
        <input class="span2 Wdate w220" onfocus="validataForm(this,'nameFont16');" onblur="validataForm(this,'nameFont16');" readonly="readonly" name="makeTechDate" id="appendedInput" type="text" onclick='WdatePicker()'>
       <font id="nameFont16"></font>
	 </li>  
	  <li class="col-md-6  p0 ">
	   <span class="">学位：</span>
        <input class="span2" maxlength="10" onblur="validataForm(this,'nameFont17');" name="degree" id="appendedInput" type="text">
          
       <font id="nameFont17"></font>
	 </li>
	  <li class="col-md-6  p0 ">
	   <span class="">健康状态：</span>
        <input class="span2" maxlength="10" onblur="validataForm(this,'nameFont18');" name="healthState" id="appendedInput" type="text">
          
       <font id="nameFont18"></font>
	 </li>  
	 <li class="col-md-6  p0 ">
	   <span class="">现任职务：</span>
        <input class="span2" maxlength="10" onblur="validataForm(this,'nameFont19');" name="atDuty" id="appendedInput" type="text">
        
       <font id="nameFont19"></font>
	 </li>
   </ul>
  </div> 
   
  <!-- 附件信息-->
  <div class="padding-top-10 clear">
   <div class="headline-v2 clear">
   <h2>上传附件</h2>
   </div>
  </div>
  <div class="padding-left-40 padding-right-20 clear">
   <ul class="list-unstyled list-flow p0_20">
   <li class="col-md-6  p0 ">
	   <span class="">身份证：</span>
	   <div >
        <input class="span2" name="files" id="appendedInput" type="file" >
       </div>
	 </li>
	 <li class="col-md-6  p0 ">
	   <span class="">学历证书：</span>
	   <div >
        <input class="span2" name="files" id="appendedInput" type="file">
       </div>
	 </li>
	 <li class="col-md-6  p0 ">
	   <span class="">职称证书：</span>
	   <div >
        <input class="span2" name="files" id="appendedInput" type="file">
       </div>
	 </li>
	  <li class="col-md-6  p0 ">
	   <span class="">学位证书：</span>
	   <div >
        <input class="span2" name="files" id="appendedInput" type="file">
       </div>
	  </li>
	  <li class="col-md-6  p0 ">
	   <span class="">本人照片：</span>
	   <div >
        <input class="span2" name="files" id="appendedInput" type="file">
       </div>
	 </li>
   </ul>
  </div>
  
  <div  class="col-md-12">
   <div class="fl padding-10">
	<button class="btn btn-windows save" type="submit" onclick="validateFlag();">暂存</button>
   <input class="btn btn-windows save" onclick="validateFlag2();" type="submit" value="下一步">
	<button class="btn btn-windows reset" type="reset">重置</button>
	<a class="btn btn-windows reset"  onclick="location.href='javascript:history.go(-1);'">返回</a>
	</div>
  </div>
  </form>
 </div>
 </div>
 <!--底部代码开始-->
<!-- <div class="footer-v2" id="footer-v2">
      <div class="footer">
            Address
              <address class="">
			  Copyright © 2016 版权所有：中央军委后勤保障部 京ICP备09055519号
              </address>
              <div class="">
		       浏览本网主页，建议将电脑显示屏的分辨率调为1024*768
              </div> 
      </div>
</div> -->
</body>
</html>
