<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<link href="<%=basePath%>public/ZHH/css/footer-v2.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/img-hover.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/brand-buttons.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/brand-buttons-inversed.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/blog_magazine.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/page_job.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/page_log_reg_v1.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/shop.style.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/header-v5.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/footer-v4.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/masterslider.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/james.css" media="screen" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="<%=basePath%>public/ZHQ/css/validForm/style.css" type="text/css" media="all" />
<link href="<%=basePath%>public/ZHQ/css/validForm/demo.css" type="text/css" rel="stylesheet" />
<link href="<%=basePath%>public/layer/skin/layer.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/layer/skin/layer.ext.css" media="screen" rel="stylesheet" type="text/css">


<script type="text/javascript" src="<%=basePath%>public/ZHH/js/messages_cn.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/hm.js"></script><script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery-migrate-1.2.1.min.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery_ujs.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/back-to-top.js"></script>
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
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/select2.min.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/select2_locale_zh-CN.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/application.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.counterup.min.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/modernizr.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/touch.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/product-quantity.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/master-slider.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/shop.app.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/masterslider.min.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/james.js"></script>
<script type="text/javascript" src="<%=basePath%>public/layer/layer.js"></script>
<script type="text/javascript" src="<%=basePath%>public/layer/extend/layer.ext.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/lodop/LodopFuncs.js"></script>
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
	 //是否通过标示
	 function pass(flag){
		 $("#isPass").val(flag);
		 if(flag==1 || flag=="1"){
			 $("#form1").submit();
		 }else {
			var remark = $("#remark").val(); 
			 if(remark.replace(/(^\s*)|(\s*$)/g, "")=="" || remark==null){
				 layer.alert("请填写意见！",{offset: ['222px', '390px'],shade:0.01});
			 }else{
				 $("#form1").submit();
			 }
		 }
	 }
</script>
</head>
<body>
  <div class="wrapper">
	
<!-- 修改订列表开始-->
   <div class="container">
   <form action="<%=basePath %>expert/toUploadExpertTable.html"  method="post" id="form1" enctype="multipart/form-data" class="registerform"> 
   		<%
			session.setAttribute("tokenSession", tokenValue);
		%>
		 <input type="hidden"  name="token2" value="<%=tokenValue%>">
   <input type="text" name="id" value="${expert.id }">
   <input type="hidden" name="isPass" id="isPass"/>
   <div>
    <jsp:include page="../../../../indexhead.jsp"></jsp:include>
   <div class="container clear margin-top-30" >
   			<h2 class="padding-20 mt40">
					<span class="new_step current fl"><i class="">1</i><div class="line"></div> <span class="step_desc_01">用户名密码</span> </span>
					<span class="new_step current fl"><i class="">2</i><div class="line"></div> <span class="step_desc_02">基本信息</span> </span> 
					<span class="new_step current fl"><i class="">3</i><div class="line"></div> <span class="step_desc_01">专家类型</span> </span> 
					<span class="new_step current fl"><i class="">4</i><div class="line"></div> <span class="step_desc_02">采购机构</span> </span> 
					<span class="new_step current fl"><i class="">5</i><div class="line"></div> <span class="step_desc_01">打印申请表</span> </span> 
					<span class="new_step fl"><i class="">6</i> <span class="step_desc_02">上传申请表</span> </span> 
				<div class="clear"></div>
			</h2>
	</div>
	<br/><br/>
   <div class="headline-v2">
   <h2>打印申请表</h2>
   </div>
   <h5 align="center">军队评标专家申请表</h5>
   <table class="table table-bordered table-condensed">
   	<tr>
   		<td align="center">姓名</td>
   		<td align="center">${expert.relName }</td>
   		<td align="center">性别</td>
   		<td align="center">${expert.sex}</td>
   		<td align="center" rowspan="4">照片</td>
   	</tr>
   <tr>
   		<td align="center">出生日期</td>
   		<td align="center"><fmt:formatDate type='date' value='${expert.birthday }' dateStyle="default" pattern="yyyy-MM-dd"/></td>
   		<td align="center">政治面貌</td>
   		<td align="center">${expert.politicsStatus}</td>
   </tr>
   <tr>
   		<td align="center">所在地区</td>
   		<td align="center">${expert.address }</td>
   		<td align="center">职称</td>
   		<td align="center">${expert.professTechTitles }</td>
   </tr>
   <tr>
   		<td align="center">身份证号码</td>
   		<td align="center" colspan="3">${expert.idNumber }</td>
   </tr>
   <tr>
   		<td align="center">从事专业类别</td>
   		<td align="center">${expert.expertsTypeId }</td>
   		<td align="center">从事年限</td>
   		<td align="center" colspan="2"><fmt:formatDate type='date' value='${expert.timeToWork }' dateStyle="default" pattern="yyyy-MM-dd"/></td>
   </tr>
   <tr>
   		<td align="center">最高学历</td>
   		<td align="center">${expert.hightEducation }</td>
   		<td align="center">最高学位</td>
   		<td align="center" colspan="2">${expert.degree }</td>
   
   </tr>
   <tr>
   		<td align="center">执业资格1</td>
   		<td align="center"></td>
   		<td align="center">注册证书编号1</td>
   		<td align="center" colspan="2"></td>
   </tr>
   <tr>
   		<td align="center">执业资格2</td>
   		<td align="center"></td>
   		<td align="center">注册证书编号2</td>
   		<td align="center" colspan="2"></td>
   </tr>
   <tr>
   		<td align="center">执业资格3</td>
   		<td align="center"></td>
   		<td align="center">注册证书编号3</td>
   		<td align="center" colspan="2"></td>
   </tr>
   <tr>
   		<td align="center">近两年是否接受过评标业务培训</td>
   		<td align="center"></td>
   		<td align="center">是否愿意成为应急专家</td>
   		<td align="center" colspan="2"></td>
   </tr>
   <tr>
   		<td align="center">所属行业</td>
   		<td align="center"></td>
   		<td align="center">报送部门</td>
   		<td align="center" colspan="2"></td>
   </tr>
   <tr>
   		<td align="center">手机号码</td>
   		<td align="center">${expert.mobile }</td>
   		<td align="center">单位电话</td>
   		<td align="center" colspan="2">${expert.fixPhone }</td>
   </tr>
   <tr>
   		<td align="center">住宅电话</td>
   		<td align="center"></td>
   		<td align="center">电子邮箱</td>
   		<td align="center" colspan="2"></td>
   </tr>
   <tr>
   		<td align="center">毕业院校及专业</td>
   		<td align="center" colspan="4"></td>
   </tr>
   <tr>
   		<td align="center">单位名称</td>
   		<td align="center" colspan="4">${expert.workUnit }</td>
   </tr>
   <tr>
   		<td align="center">单位地址 </td>
   		<td align="center" >${expert.unitAddress }</td>
   		<td align="center">单位邮编</td>
   		<td align="center" colspan="2">${expert.zipCode }</td>
   </tr>
   <tr>
   		<td align="center">家庭地址 </td>
   		<td align="center" ></td>
   		<td align="center">家庭邮编</td>
   		<td align="center" colspan="2"></td>
   </tr>
   <tr>
   		<td align="center">评标专业一</td>
   		<td align="center" colspan="4"></td>
   </tr>
   <tr>
   		<td align="center">评标专业二</td>
   		<td align="center" colspan="4"></td>
   </tr>
   <tr>
   		<td align="center">评标专业三</td>
   		<td align="center" colspan="4"></td>
   </tr>
   <tr>
   		<td align="center">评标专业四</td>
   		<td align="center" colspan="4"></td>
   </tr>
   <tr>
   		<td align="center">评标专业五</td>
   		<td align="center" colspan="4"></td>
   </tr>
   <tr>
   		<td align="center">评标专业六</td>
   		<td align="center" colspan="4"></td>
   </tr>
   <tr>
   		<td align="center" colspan="5">工作经历</td>
   </tr>
   <tr>
   		<td align="center">起止年月</td>
   		<td align="center" colspan="3">单位及职务</td>
   		<td align="center">证明人</td>
   </tr>
   <tr>
   		<td align="center">至 </td>
   		<td align="center" colspan="3"> </td>
   		<td align="center"> </td>
   </tr>
   <tr>
   		<td align="center"> 至</td>
   		<td align="center" colspan="3"> </td>
   		<td align="center"> </td>
   </tr>
   <tr>
   		<td align="center">至 </td>
   		<td align="center" colspan="3"> </td>
   		<td align="center"> </td>
   </tr>
   <tr>
   		<td align="center">至 </td>
   		<td align="center" colspan="3"> </td>
   		<td align="center"> </td>
   </tr>
   <tr>
   		<td align="center">至 </td>
   		<td align="center" colspan="3"> </td>
   		<td align="center"> </td>
   </tr>
   </table>
  </div> 
   
  <script type="text/javascript">
  function dayin() {
		var LODOP = getLodop();
		if (LODOP) {
			LODOP.ADD_PRINT_HTM(0, -50, "100%", "100%",
					document.documentElement.innerHTML);
			LODOP.PREVIEW();
		}
	}
  </script>
  <div  class="col-md-12">
   <div class="fl padding-10">
   <input class="btn btn-windows add" type="button" onclick="dayin();" value="打印">
    <!-- <button class="btn btn-windows add" type="submit">下一步</button> -->
	<!-- <button class="btn btn-windows delete" type="submit">删除</button> -->
	<!-- <input class="btn btn-windows delete" type="button" onclick="" value="下载"> -->
	<a class="btn btn-windows delete" href="<%=basePath %>expert/download.html?id=${expert.id }">下载</a>
		<input class="btn btn-windows delete" type="submit"  value="下一步">
	<!-- <button class="btn btn-windows delete" onclick="pass('1');" type="submit">不通过</button> -->
	<a class="btn btn-windows reset"  onclick="location.href='javascript:history.go(-1);'">返回</a>
	</div>
  </div>
  </form>
 </div>
 <br/> <br/>
 <!--底部代码开始-->
<div >
      <div class="footer">
              <address class="">
			  	Copyright © 2016 版权所有：中央军委后勤保障部 京ICP备09055519号
              </address>
              <div class="">
		       	浏览本网主页，建议将电脑显示屏的分辨率调为1024*768
              </div> 
      </div>
      </div>
</div>
</body>
</html>
