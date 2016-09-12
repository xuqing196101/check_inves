<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
	<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    
    <title>版块管理</title>  
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	


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
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery_ujs.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=basePath%>public/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>public/layer/layer.js"></script>
 
 <script type="text/javascript" src="${pageContext.request.contextPath}/public/upload/ajaxfileupload.js"></script>
 
  <script type="text/javascript">
 
  
  
  
    
    function add(){
    	window.location.href="<%=basePath%>purchaser/add.html";
    }
    
    
	//鼠标移动显示全部内容
 var index;
	function chakan(){
		index=layer.open({
			  type: 1, //page层
			  area: ['600px', '500px'],
			  title: '编制说明',
			  closeBtn: 1,
			  shade:0.01, //遮罩透明度
			  moveType: 1, //拖拽风格，0是默认，1是传统拖动
			  shift: 1, //0-6的动画形式，-1不开启
			  offset: ['80px', '400px'],
			  content: $('#content'),
			});
	}
	
	function closeLayer(){
		layer.close(index);	
	}
	
	  function upload(){
		            $.ajaxFileUpload (
		                {
		                    url: '${pageContext.request.contextPath}/purchaser/upload.do', //用于文件上传的服务器端请求地址
		                    secureuri: true, //一般设置为false
		                    fileElementId: 'fileName', //文件上传空间的id属性  <input type="file" id="file" name="file" />
		                  
		                   dataType: "text", //返回值类型 一般设置为json
		                  
		                    success: function (data,status)  //服务器成功响应处理函数
		                    { 
		                    alert("上传成功");
		                    },
		                    error: function (data, status, e)//服务器响应失败处理函数
		                    {
		                    	  alert("上传失败");
		                    }
		                }
		            );
		            
		  
		  	}    
	  
	  
  </script>
  </head>
  
  <body>
<!--面包屑导航开始-->
 <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">障碍作业系统</a></li><li><a href="#">采购计划管理</a></li><li class="active"><a href="#">采购需求管理</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
 
 
 <div class="container clear margin-top-30">
   <h2 class="padding-10 border1">
	 <ul class="demand_list">
	   <li class="fl"><label class="fl">需求计划导入（Excel表格）：</label><span><input class="mmm400" type="file" id="fileName" name="file" /> </span>
       <a href="#" class="upload" onclick="upload()">上传</a>
       </li>
	 </ul>
	 <span class="fr">
	 <button class="btn padding-left-10 padding-right-10 btn_back">下载Excel模板</button>
	 <button class="btn padding-left-10 padding-right-10 btn_back">查看产品分类目录</button>
     <button class="btn padding-left-10 padding-right-10 btn_back" onclick="chakan()" >查看编制说明</button>
	 <button class="btn padding-left-10 padding-right-10 btn_back">导入</button>
     <button class="btn padding-left-10 padding-right-10 btn_back">录入</button>
	 </span>
   </h2>
  </div>
 
 <div id="content" style="display: none;">
	 <p align="center">
	             编制说明
	
	
	
	  <span>&nbsp;&nbsp;</span>  <p> 1、请严格按照序号顺序为：一、(一)、1、(1)、a、(a)的顺序填写序号 </p>
	
	   <p> 2、任务明细最多为六级,请勿多于六级 </p>
	
	    <p>3、请勿空行填写 </p>
	
	   <p> 4、需求单位名称不能为空 </p>
	
	  <p>  5、请按表式填写计划明细。用户可以编辑行，但不能增加或删除列。 </p>
	
	  <p>  6、最子级请严格按照填写说明填写，父级菜单请将序号与金额填写正确(金额=所有子项金额/10000) </p>
	
	   <p> 7、采购方式填写选项包括：公开招标、邀请招标、竞争性谈判、询价、单一来源。 </p>
	
	   <p> 8、选择单一来源采购方式的，必须填写供应商名称；选择其他采购方式的不填。 </p>
	
	   <p> 9、规格型号和质量技术标准内容分别不得超过250、1000字。超过此范围的，请以附件形式另报。 </p>
	
	   <p> 10、采购数量、单价和预算金额必须为数字格式。其中单价单位为“元”，预算金额单位为“万元”。
	    </p>
	     <button class="btn padding-left-10 padding-right-10 btn_back" onclick="closeLayer()" >确定</button>
	    
 </div>
 
	 </body>
</html>
