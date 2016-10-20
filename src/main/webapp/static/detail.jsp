<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
<link href="<%=basePath%>public/ZHH/css/line-icons.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/app.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/application.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/header-v4.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/header-v5.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/brand-buttons.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/footer-v2.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/img-hover.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/page_job.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/shop.style.css" media="screen" rel="stylesheet" type="text/css">

<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=path %>/public/ZHH/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery_ujs.js"></script>
<script type="text/javascript" src="<%=basePath%>public/lodop/LodopFuncs.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=basePath%>public/My97DatePicker/WdatePicker.js"></script>
</head>
<body>


<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">业务管理</a></li><li><a href="#">订单中心</a></li><li class="active"><a href="#">修改订单</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   
<!-- 修改订列表开始-->
   <div class="container bggrey border1 mt20">
   <form>
   <div>
   <h2 class="f16 count_flow mt40"><i>01</i>修改订单</h2>
   <ul class="list-unstyled list-flow ul_list">
     <li class="col-md-6 p0">
	   <span class="">采购单位：</span>
	   <div class="input-append">
        <input class="span2" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li>
     <li class="col-md-6  p0 ">
	   <span class="">发票抬头：</span>
	   <div class="input-append">
        <input class="span2" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li>
     <li class="col-md-6  p0 ">
	   <span class="">采购单位联系人：</span>
	   <div class="input-append">
        <input class="span2" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
     <li class="col-md-6  p0 ">
	   <span class="">采购单位联系人座机：</span>
	   <div class="input-append">
        <input class="span2" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
     <li class="col-md-6  p0 ">
	   <span class="">采购单位联系人手机：</span>
	   <div class="input-append">
        <input class="span2" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
     <li class="col-md-6  p0 ">
	   <span class="">采购单位地址：</span>
	   <div class="input-append">
        <input class="span2" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
     <li class="col-md-6  p0 ">
	   <span class="">供应商名称：</span>
	   <div class="input-append">
        <input class="span2" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li>  
     <li class="col-md-6  p0 ">
	   <span class="">供应商单位联系人：</span>
	   <div class="input-append">
        <input class="span2" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
     <li class="col-md-6  p0 ">
	   <span class="">供应商单位联系人座机：</span>
	   <div class="input-append">
        <input class="span2" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li>  
     <li class="col-md-6  p0 ">
	   <span class="">供应商单位联系人手机：</span>
	   <div class="input-append">
        <input class="span2" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
     <li class="col-md-6  p0 ">
	   <span class="">供应商单位地址：</span>
	   <div class="input-append">
        <input class="span2" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
     <li class="col-md-6  p0 ">
	   <span class="">交付日期：</span>
	   <div class="input-append">
        <input class="span2 Wdate w250" id="appendedInput" type="text" onclick='WdatePicker()'>
       <%--  <span class="add-on"  onclick='WdatePicker()'>
        <img src="<%=basePath%>public/ZHH/images/time_icon.png" class="mb10" onclick='WdatePicker()'/>
        </span> --%>
       </div>
	 </li> 
     <li class="col-md-6  p0 ">
	   <span class="">预算金额（元）：</span>
	   <div class="input-append">
        <input class="span2" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
     <li class="col-md-6  p0 ">
	   <span class="">发票编号：</span>
	   <div class="input-append">
        <input class="span2" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
     <li class="col-md-12 p0">
	   <span class="fl">备注：</span>
	   <div class="col-md-12 pl200 fn mt5 pwr9">
        <textarea class="text_area col-md-12 " title="不超过800个字" placeholder="不超过800个字"></textarea>
       </div>
	 </li> 
	 
   </ul>
  </div> 
   
  <!-- 产品明细开始-->
  <div class="padding-top-10 clear">
   <!--<div class="headline-v2 bggrey">
   <h2>产品明细</h2>
   </div>-->
   <h2 class="f16 count_flow mt40"><i>02</i>产品明细</h2>
   <ul class="list-unstyled list-flow ul_list ">

     <li class="col-md-6 p0">
	   <span class="">品目：</span>
	   <div class="input-append">
         <input class="span2" id="appendedInput" type="text">
		 <div class="btn-group ">
          <button class="btn dropdown-toggle add-on" data-toggle="dropdown">
		  <img src="<%=basePath%>public/ZHH/images/down.png" class="margin-bottom-5"/>
          </button>
          <ul class="dropdown-menu list-unstyled">
          </ul>
       </div>
      </div>
	 </li> 
     <li class="col-md-6 p0 ">
	   <span class="">品牌：</span>
	   <div class="input-append">
        <input class="span2" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li>  
     <li class="col-md-6  p0 ">
	   <span class="">型号：</span>
	   <div class="input-append">
        <input class="span2" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li>  
     <li class="col-md-6 p0 ">
	   <span class=" ">版本号：</span>
	   <div class="input-append">
        <input class="span2" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li>  
     <li class="col-md-6 p0 ">
	   <span class=" ">市场单价（元）：</span>
	   <div class="input-append">
        <input class="span2" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
     <li class="col-md-6 p0 ">
	   <span class=" ">成交单价（元）：</span>
	   <div class="input-append">
        <input class="span2" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
     <li class="col-md-6 p0 ">
	   <span class=" ">数量：</span>
	   <div class="input-append">
        <input class="span2" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
     <li class="col-md-6 p0 ">
	   <span class=" ">单位：</span>
	   <div class="input-append">
        <input class="span2" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
     <li class="col-md-6 p0 ">
	   <span class=" ">小计（元）：</span>
	   <div class="input-append">
        <input class="span2" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
	 <li class="col-md-6 p0 ">
	   <span class=" ">下拉选项：</span>
         <div class="select_common mb10">
         <select class="w250 ">
           <option>选项1</option>
           <option>选项2</option>
           <option>选项3</option>
         </select>
         </div>
	 </li>
	 <li class="col-md-6 p0 ">
	   <span class=" ">右侧下箭头：</span>
         <div class="select_common">
         <input class="w250" id="appendedInput" type="text">
	      <i class="input_icon ">
		  <img src="<%=basePath%>public/ZHH/images/down.png" class="margin-bottom-5"/>
          </i>
         </div>
	 </li>
	 

     <li class="col-md-12 p0">
	   <span class="fl">备注：</span>
	   <div class="col-md-12 pl200 fn mt5 pwr9">
        <textarea class="text_area col-md-12 " title="不超过800个字" placeholder="不超过800个字"></textarea>
       </div>
	 </li> 
	 </ul>
	 
	 <div class="tab-v1 clear col-md-12 margin-20 padding-right-40">
    </div>
	 <ul class="list-unstyled list-flow p0_20 ul_list">
     <li class="col-md-6 p0 ">
	   <span class=" ">运费（元）：</span>
	   <div class="input-append">
        <input class="span2" id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
     <li class="col-md-6 p0 ">
	   <span class="fl">其他费用（元）：</span>
	   <div class="input-append ">
        <input class="span2 " id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li> 
     <li class="col-md-6 p0 ">
	   <span class="fl">其他费用说明：</span>
	   <div class="input-append  ">
        <input class=" span2 " id="appendedInput" type="text">
        <span class="add-on">i</span>
       </div>
	 </li>
     <li class="col-md-6 p0 ">
      <div class="input-append"><a href="" class="upload">附件上传</a></div>
	 </li> 
   </ul>
   <div class="clear padding-left-20 total f22"><span class="fl block">总计：</span><span>¥10000</span></div>
<!--  <div class="headline-v2 clear bggrey">
   <h2>上传附件</h2>
   </div>-->
   <h2 class="f16 count_flow mt40"><i>03</i>修改订单</h2>
  </div>
  <div class="padding-left-40 padding-right-20 clear">
   <ul class="list-unstyled  bg8 padding-20">
    <li>1 . 仅支持jpg、jpeg、png、pdf等格式的文件;</li>
	<li>2 . 单个文件大小不能超过1M;</li>
	<li>3 . 上传文件的数量不超过10个;</li>
   </ul>
  </div>
  
  <div  class="col-md-12">
   <div class="fl padding-left-20">
    <button class="btn btn-windows add" type="submit">新增</button>
	<button class="btn btn-windows delete" type="submit">删除</button>
	<button class="btn btn-windows save" type="submit">保存</button>
	<button class="btn btn-windows reset" type="submit">重置</button>
	</div>
	<div class="fr padding-top-15"><input type="checkbox"  class="margin-top-0 fl"/><span class="margin-left-5 fl padding-right-25">选中全部文件</span></div>
  </div>
  </form>
  <div class="padding-left-40 padding-right-20 clear  ">
   <ul class="list-unstyled bgdd">
	<li> 
    <div class="col-md-4 padding-10 fl">
	 <div class="col-md-3 tc h60 fl"><input type="checkbox"/></div>
	 <div class="col-md-9 padding-0 fl">
	   <div class="fl suolue"> 
        <a href="#" class="thumbnail mb0 suolue">
         <img src="<%=basePath%>public/ZHH/images/suolue.jpg" class="suolue"/>
        </a>
	   </div>
	 </div>
	</div>
	<div class="col-md-8 padding-10 h60 fl">
	 <div class="col-md-9 fl">1oa－1000乘370.jpg</div>
	 <div class="col-md-3 fl">614.82KB</div>
	</div>
	<div class="clear"></div>
  </li>
<li> 
    <div class="col-md-4 padding-10 fl">
	 <div class="col-md-3 tc h60 fl"><input type="checkbox"/></div>
	 <div class="col-md-9 padding-0 fl">
	   <div class="fl suolue"> 
        <a href="#" class="thumbnail mb0 suolue">
         <img src="<%=basePath%>public/ZHH/images/suolue.jpg" class="suolue"/>
        </a>
	   </div>
	 </div>
	</div>
	<div class="col-md-8 padding-10 h60 fl">
	 <div class="col-md-9 fl">1oa－1000乘370.jpg</div>
	 <div class="col-md-3 fl">614.82KB</div>
	</div>
	<div class="clear"></div>
  </li>
  </ul>
  </div>
 </div>



</body>
</html>
