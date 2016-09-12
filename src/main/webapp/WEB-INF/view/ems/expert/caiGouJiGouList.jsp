<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
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
	<link href="<%=basePath%>public/ZHH/css/common.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/bootstrap.min.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/style.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/animate.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/ui-dialog.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/dialog-select.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/line-icons.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/font-awesome.min.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/jquery.fileupload-ui.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/zTreeStyle.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/sky-forms.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/custom-sky-forms.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/jquery.fancybox.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/owl.carousel.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/owl.theme.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/style-switcher.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/shortcode_timeline2.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/app.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/blocks.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/datepicker.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/WdatePicker.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/select2.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/application.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/header-v4.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/footer-v2.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/img-hover.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/brand-buttons.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/brand-buttons-inversed.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/blog_magazine.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/page_job.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/page_log_reg_v1.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/shop.style.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/header-v5.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/footer-v4.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/style(1).css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/masterslider.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/ZHH/css/james.css" media="screen" rel="stylesheet">
<link href="<%=basePath%>public/layer/skin/layer.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/layer/skin/layer.ext.css" media="screen" rel="stylesheet" type="text/css">

    <script src="<%=basePath%>public/ZHH/js/hm.js"></script><script src="<%=basePath%>public/ZHH/js/jquery.min.js"></script>
<script src="<%=basePath%>public/ZHH/js/WdatePicker.js"></script><link href="<%=basePath%>public/ZHH/css/WdatePicker(1).css" rel="stylesheet" type="text/css">

<script type="text/javascript" src="<%=basePath%>public/layer/layer.js"></script>
<script type="text/javascript" src="<%=basePath%>public/layer/extend/layer.ext.js"></script>
</head>
<body>
  <div class="wrapper">

<!-- 我的订单页面开始-->
   <div class="container">
   <div class="headline-v2">
   <h2>请选择采购机构</h2>
   </div>
   </div>
   <script type="text/javascript">
   $(function(){
	   var sup = $("#checked").val();
	   var radio=document.getElementsByName("check");
	   for(var i=0;i<radio.length;i++){
	 		if(sup==radio[i].value){
	 			radio[i].checked=true;
	 	 		break;
	 		}
	   }
   });
   function submitForm(flag){
	   if(flag==1 || flag=="1"){
		   
		   var val=$("input:radio[name='check']:checked").val();
           if(val==null){
        	   layer.alert("请选择一个采购机构",{offset: ['222px', '390px'],shade:0.01});
               return ;
           }else{
		   $("#flag").val(1);
		   $("#form1").submit();
           }
	   }else{
		   var val=$("input:radio[name='check']:checked").val();
           if(val==null){
        	   layer.alert("请选择一个采购机构",{offset: ['222px', '390px'],shade:0.01});
               return ;
           }else{
		   $("#flag").val(2);
		   $("#form1").submit();
           }
	   }
   }
   
   </script>
<!-- 表格开始-->
    <jsp:include page="../../../../indexhead.jsp"></jsp:include>
   <div class="container clear margin-top-30" >
   			<h2 class="padding-20 mt40">
					<span class="new_step current fl"><i class="">1</i><div class="line"></div> <span class="step_desc_01">用户名密码</span> </span>
					<span class="new_step current fl"><i class="">2</i><div class="line"></div> <span class="step_desc_02">基本信息</span> </span> 
					<span class="new_step current fl"><i class="">3</i><div class="line"></div> <span class="step_desc_01">专家类型</span> </span> 
					<span class="new_step current fl"><i class="">4</i><div class="line"></div> <span class="step_desc_02">采购机构</span> </span> 
					<span class="new_step fl"><i class="">5</i><div class="line"></div> <span class="step_desc_01">打印申请表</span> </span> 
					<span class="new_step fl"><i class="">6</i> <span class="step_desc_02">上传申请表</span> </span> 
				<div class="clear"></div>
			</h2>
	</div>
	<br/><br/>
   <div class="container margin-top-5">
     <div class="content padding-left-25 padding-right-25 padding-top-5">
     <form action="<%=basePath %>expert/addJiGou.do" method="post"  id="form1">
     <input type="hidden" id="flag" name="flag">
     <input type="hidden" name="id" value="${expert.id }">
     <input type="hidden"  value="${expert.purchaseDepId }">
        <table class="table table-bordered table-condensed">
		<thead>
		<tr>
		  <th class="info w30"><input type="radio" disabled="disabled"  id="allId" alt=""></th>
		  <th class="info w50">序号</th>
		  <th class="info">采购机构</th>
		  <th class="info">联系人</th>
		  <th class="info">联系地址</th>
		  <th class="info">联系电话</th>
		</tr>
		</thead>
		<%-- <c:forEach items="${expert }" var="e" varStatus="s"> --%>
		<tr>
		  <td class="tc w30"><input type="radio" name="check" id="checked" alt="" value="2"></td>
		  <td class="tc w50">1</td>
		  <td class="tc">哈哈</td>
		  <td class="tc">飒飒</td>
		  <td class="tc">北京</td>
		 <td class="tc">13333333333</td>
		 </tr>
		<%-- </c:forEach> --%>
        </table>
        </form>
     </div>
      <input class="btn btn-windows save" type="button" onclick="submitForm('1');" value="暂存">
         <input class="btn btn-windows add" type="button" onclick="submitForm('2');" value="下一步">
         <a class="btn btn-windows reset"  onclick="location.href='javascript:history.go(-1);'">返回</a>
   </div>
 </div>
<!--底部代码开始-->
<!-- <div class="footer-v2" id="footer-v2">

      <div class="footer">
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
