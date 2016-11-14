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



 <jsp:include page="/WEB-INF/view/common.jsp"/> 
 
<script type="text/javascript" src="${pageContext.request.contextPath}/public/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/upload/ajaxfileupload.js"></script>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/ztree/css/zTreeStyle.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ztree/jquery.ztree.core.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ztree/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ztree/jquery.ztree.exedit.js"></script>

<script type="text/javascript">
 	//跳转到增加页面
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
	//上传excel文件
	function upload(){
		  var name=$("#jhmc").val();
	 		 var no=$("#jhbh").val();
	 		 
	 		 
 		var type=$("#wtype").val();
 		name=encodeURI(name);
 		if(name==""){
 			layer.tips("计划名称不允许为空","#jhmc");
 		 } else if(no==""){
 			layer.tips("计划编号不允许为空","#jhbh");
 		 }else{
 			 
 		 
		            $.ajaxFileUpload (
		                {
		                    url: '${pageContext.request.contextPath}/purchaser/upload.do?type='+type+"&&planName="+name+"&&planNo="+no, //用于文件上传的服务器端请求地址
		                    secureuri: false, //一般设置为false
		                    fileElementId: 'fileName', //文件上传空间的id属性  <input type="file" id="file" name="file" />
		                  
		                 	 dataType: "text", //返回值类型 一般设置为json
		                    success: function (data)  //服务器成功响应处理函数
		                    { 
		                    	if(data=="ERROR"){
		                    		 alert("文件名错误");
		                    	}else if(data=="exception"){
		                    		alert("格式出错");
		                    	}
		                    	
		                    	else{
		                    		alert("上传成功");
		                    	}
		                   
		                    },
		                    error: function (data, status, e)//服务器响应失败处理函数
		                    {
		                    	  alert("上传失败");
		                    }
		                }
		            );
		            
 		 }
 		
		  	}    
	  
	  
	  function adds(){
		  var name=$("#jhmc").val();
	 		var no=$("#jhbh").val();
	 	  var type=$("#wtype").val();
	 		 if(name==""){
	 			layer.tips("计划名称不允许为空","#jhmc");
	 		 } else if(no==""){
	 			layer.tips("计划编号不允许为空","#jhbh");
	 		 }else if(type==""){
	 			layer.tips("物资类别不允许为空","#wtype");
	 		 } 
	 		 
	 		 else{
	 			$("#fjhmc").val(name);
	 	 		$("#fjhbh").val(no);
	 	 		$("#ptype").val(type);
	 	 		
	 	 	  $("#add_form").submit();   
	 		 }
	  }
	  function hide(){
		  $("#add_div").hide();
	  }
	  
 	 //动态添加
  	  function aadd(){
 		 var id=null;
  		 $.ajax({
 			 url:"<%=basePath%>purchaser/getId.html",
 			 type:"post",
 			 
 			 success:function(data){
 		 			id=data;
 		 			var tr = $("input[name=dyadds]").parent().parent().prev();
 		 			// var tr=$(obj).parent().parent();
 		 			$(tr).children(":first").children(":first").val(data);
 		 			
 		 			var  s=$("#count").val();
 			      	s++;
 			      	$("#count").val(s);
 			       // var trs = $(obj).parent().parent();
 			        $(tr).after("<tr><td class='tc'><input style='border: 0px;' type='hidden' name='list["+s+"].id' />"+
 			        "<input style='border: 0px;' type='text' name='list["+s+"].seq' /><input style='border: 0px;' value='"+id+"' type='hidden' name='list["+s+"].parentId' /></td>"+
 				       "<td class='tc'> <input  style='border: 0px;'  type='text' name='list["+s+"].department' /> </td>"+
 				       "<td class='tc'> <input  style='border: 0px;' type='text' name='list["+s+"].goodsName' /> </td>"+ 
 				       "<td class='tc'> <input  style='border: 0px;' type='text' name='list["+s+"].stand' /> </td>"+ 
 				       "<td class='tc'> <input  style='border: 0px;' type='text' name='list["+s+"].qualitStand' /> </td>"+ 
 				       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].item' /> </td>"+  
 				       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].purchaseCount' /> </td>"+  
 				       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].price' /> </td>"+  
 				       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].budget' /> </td>"+  
 				       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].deliverDate' /> </td>"+  
 				       	"<td class='tc'>  <select name='list["+s+"].purchaseType' style='width:100px'> <option value='' >请选择</option> <option value='gkzb' >公开招标</option> "+  
 				       "<option value='yqzb' >邀请招标</option> <option value='jzxtp' >竞争性谈判</option> <option value='xjcg' >询价采购</option> <option value='dyly'  >单一来源</option> </select></td>"+
 				       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].supplier' /> </td>"+  
 				       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].isFreeTax' /> </td>"+  
 				       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].goodsUse' /> </td>"+  
 				       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].useUnit' /> </td>"+
 				       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].memo' /> </td>"+ 
 			        +"<tr/>");
 			        
 			 },error:function(){
 				 
 			 }
 		 });
  		 
  		  
		  
	  }
 	 function incr(){
 		 
 		
 	  var name=$("#jhmc").val();
 		var no=$("#jhbh").val();
 	  var type=$("#wtype").val();
 		 if(name==""){
 			layer.tips("计划名称不允许为空","#jhmc");
 		 } else if(no==""){
 			layer.tips("计划编号不允许为空","#jhbh");
 		 } 
 		 
 		 else{
 			  $("#fjhmc").val(name);
 	 		$("#fjhbh").val(no);
 	 		$("#ptype").val(type);
 	 		<%-- $("#add_form").find("*").each(function(){ 
 	  			var elem = $(this); 
 	  			if (elem.prop("name") != null&&elem.prop("name") != "") { 
 	  			if(elem.val()==""){ 
 	  			elem.removeAttr("name"); 
 	  			} 
 	  			} 
 	  			}); 
 			 
 		 
 		 $.ajax({
 			 url:"<%=basePath%>purchaser/adddetail.html",
 			 type:"post",
 			 data:$("#add_form").serialize(),
 			 success:function(){
 				 alert("添加成功");
 				 window.location.href="<%=basePath%>purchaser/list.html";
 			 },error:function(){
 				 
 			 }
 		 }); --%>
 		 
 	  $("#add_form").submit();   
 		 
 		 }   
 		
 			 
 		 
	}
 	 
 	 function down(){
 		 window.location.href="<%=basePath%>purchaser/download.html?filename=模板.xlsx";
 	 }
 	 
 	 function delets(){
 		  var tr = $("input[name=delt]").parent().parent();
 	        $(tr).prev().remove();   
 	 }
 	var datas;
	var treeObj;
	$(function() {
		
		   
		   var setting={
					async:{
								autoParam:["id"],
								enable:true,
								url:"<%=basePath%>category/createtree.do",
								dataType:"json",
								type:"post",
							},
							callback:{
						    	onClick:zTreeOnClick,//点击节点触发的事件
						    	 //beforeRemove: zTreeBeforeRemove,
						    	//beforeRename: zTreeBeforeRename, 
								//onRemove: zTreeOnRemove,
			       			    //onRename: zTreeOnRename,
						    }, 
							data:{
								simpleData:{
									enable:true,
									idKey:"id",
									pIdKey:"pId",
									rootPId:0,
								}
						    },
						   check:{
							    chkboxType:{"Y" : "ps", "N" : "ps"},//勾选checkbox对于父子节点的关联关系  
				        		chkStyle:"checkbox", 
								enable: true
						   }
			  };
		      //控制树的显示和隐藏
			  var expertsTypeId = $("#expertsTypeId").val();
				 if(expertsTypeId==1 || expertsTypeId=="1"){
				 treeObj=$.fn.zTree.init($("#ztree"),setting,datas);
					 $("#ztree").show();
				 }else{
					 treeObj=$.fn.zTree.init($("#ztree"),setting,datas);
					 $("#ztree").hide();
				 }
	});
	
	function typeShow(){
	/* 	 var expertsTypeId = $("#expertsTypeId").val();
		 if(expertsTypeId==1 || expertsTypeId=="1"){ */
			 $("#ztree").show();
		 	$("#bt").show();
		 	$("#add_div").hide();
		/*  }else{
			 $("#ztree").hide();
		 } */
		
	}
	var treeid=null;
    /*树点击事件*/
    function zTreeOnClick(event,treeId,treeNode){
		treeid=treeNode.id;
		
    }
    function typehide(){
    	 $("#ztree").hide();
    	 $("#bt").hide();
    	 $("#add_div").show();
    }
    
    function same(){
    	 $.ajax({
 			 url:"<%=basePath%>purchaser/getId.html",
 			 type:"post",
 			 
 			 success:function(data){
 				 
 			
    	 	var tr = $("input[name=dyadds]").parent().parent().prev();;
			var id=$(tr).children(":first").children(":last").val();
			
			
    		var  s=$("#count").val();
	      	s++;
	      	$("#count").val(s);
	      //  var trs = $(obj).parent().parent();
	        $(tr).after("<tr><td class='tc'><input style='border: 0px;' type='hidden' name='list["+s+"].id' value='"+data+"' />"+
	        "<input style='border: 0px;' type='text' name='list["+s+"].seq' /><input style='border: 0px;' value='"+id+"' type='hidden' name='list["+s+"].parentId' /></td>"+
		       "<td class='tc'> <input  style='border: 0px;'  type='text' name='list["+s+"].department' /> </td>"+
		       "<td class='tc'> <input  style='border: 0px;' type='text' name='list["+s+"].goodsName' /> </td>"+ 
		       "<td class='tc'> <input  style='border: 0px;' type='text' name='list["+s+"].stand' /> </td>"+ 
		       "<td class='tc'> <input  style='border: 0px;' type='text' name='list["+s+"].qualitStand' /> </td>"+ 
		       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].item' /> </td>"+  
		       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].purchaseCount' /> </td>"+  
		       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].price' /> </td>"+  
		       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].budget' /> </td>"+  
		       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].deliverDate' /> </td>"+  
			       	"<td class='tc'>  <select name='list["+s+"].purchaseType' style='width:100px'> <option value='' >请选择</option> <option value='gkzb' >公开招标</option> "+  
				       "<option value='yqzb' >邀请招标</option> <option value='jzxtp' >竞争性谈判</option> <option value='xjcg' >询价采购</option> <option value='dyly'  >单一来源</option> </select></td>"+
		       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].supplier' /> </td>"+  
		       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].isFreeTax' /> </td>"+  
		       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].goodsUse' /> </td>"+  
		       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].useUnit' /> </td>"+
		       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].memo' /> </td>"+ 
		  
	        +"<tr/>");
 			 },error:function(){
 				 
 			 }
 		 });
    }
    
    function news(obj){
    	var  s=$("#count").val();
	      	s++;
	      	$("#count").val(s);
	        var trs = $(obj).parent().parent();
	        $(trs).after("<tr><td class='tc'><input style='border: 0px;' type='text' name='list["+s+"].id' />"+
	        "<input style='border: 0px;' type='text' name='list["+s+"].seq' /><input style='border: 0px;' value='"+id+"' type='hidden' name='list["+s+"].parentId' /></td>"+
		       "<td class='tc'> <input  style='border: 0px;'  type='text' name='list["+s+"].department' /> </td>"+
		       "<td class='tc'> <input  style='border: 0px;' type='text' name='list["+s+"].goodsName' /> </td>"+ 
		       "<td class='tc'> <input  style='border: 0px;' type='text' name='list["+s+"].stand' /> </td>"+ 
		       "<td class='tc'> <input  style='border: 0px;' type='text' name='list["+s+"].qualitStand' /> </td>"+ 
		       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].item' /> </td>"+  
		       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].purchaseCount' /> </td>"+  
		       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].price' /> </td>"+  
		       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].budget' /> </td>"+  
		       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].deliverDate' /> </td>"+  
		       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].purchaseType' /> </td>"+  
		       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].supplier' /> </td>"+  
		       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].isFreeTax' /> </td>"+  
		       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].goodsUse' /> </td>"+  
		       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].useUnit' /> </td>"+
		       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].memo' /> </td>"+ 
		     	"<td class='tc'><input class='add' name='dyadds' type='button' onclick='aadd(this)' value='添加子节点'>"+
				 "<input class='btn btn-windows add' name='delt' type='button' onclick='same(this)' value='添加同级节点'>"+
			 	" <input class='btn btn-windows add' name='delt' type='button' onclick='news(this)' value='新加任务'></td>"+  
	        +"<tr/>");
    	
    	
    }
</script>
</head>

<body>
	<!--面包屑导航开始-->
	<div class="margin-top-10 breadcrumbs ">
		<div class="container">
			<ul class="breadcrumb margin-left-0">
				<li><a href="#"> 首页</a></li>
				<li><a href="#">障碍作业系统</a></li>
				<li><a href="#">采购计划管理</a></li>
				<li class="active"><a href="#">采购需求管理</a></li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>


	<div class="container clear margin-top-30">
		 <ul class="ul_list padding-left-20">
     <li class="col-md-4 margin-0 padding-0 ">
	   <span class="col-md-12 padding-left-5"><span style="color: red;">*</span> 计划名称</span>
	   <div class="input-append col-md-12 padding-0 ">
        <input type="text"  class="span5 col-md-8 padding-0 " name="name" id="jhmc" >
        <span class="add-on col-md-4">i</span>
       </div>
	 </li> 
     <li class="col-md-4 margin-0 padding-0 ">
	   <span class="col-md-12 padding-left-5"><span style="color: red;">*</span> 计划编号</span>
	   <div class="input-append col-md-12 padding-0 ">
        <input type="text"  class="span5 col-md-8 padding-0 " name="no" id="jhbh" >
        <span class="add-on col-md-4">i</span>
       </div>
	 </li> 
     <li class="col-md-4 margin-0 padding-0 ">
	   <span class="col-md-12 padding-left-5">物资类别</span>
		  <div style="width: 200px;">
		      <select style="width: 200px;" name="planType" id="wtype">
				<option value="1">货物</option>
				<option value="2">工程</option>
				<option value="3">服务</option>
			</select> 
	     </div>
	 </li> 
	 
	<!--  <li class="col-md-4 margin-0 padding-0 ">
	   <span class="col-md-12 padding-left-5"><span style="color: red;">*</span> 需求计划导入（Excel表格）：</span>
	   <div class="input-append col-md-12 padding-0 ">
        <input  class="span5 col-md-8 padding-0 " type="file" id="fileName" name="file" />  
        <span class="add-on col-md-4"><button class="btn padding-right-10 btn_back" onclick="upload()">导入</button></span>
       </div>
	 </li>  -->
	 
	 
	   <div style="float: left; margin-top: 15px;">
			<label class="fl">需求计划导入（Excel表格）：</label><input  style="float: left;" type="file" id="fileName" name="file" />   
			<button style="float: left;"  class="btn padding-right-10 btn_back" onclick="upload()">导入</button><span style="margin-left: 200px"></span>
		</div>  
   </ul>
   
	<!-- <span><span style="color: red;">*</span> 计划名称<input type="text" name="name" id="jhmc" ></span>
		<span><span style="color: red;">*</span> 计划编号<input type="text" name="no" id="jhbh" ></span>
		<span>物资类别
		<select name="planType" id="wtype">
		<option value="1">货物</option>
		<option value="2">工程</option>
		<option value="3">服务</option>
		</select> </span> -->
		
	<%-- 	<c:if test="${type=='1' }"> <input  type="text" name="" id="hw" value="货物" readonly="readonly" > </c:if> 
		<c:if test="${type=='2' }"><input type="text" name="" id="fw" value="工程" readonly="readonly"  ></c:if>
		 <c:if test="${type=='3' }"><input type="text" name="" id="gc" value="服务" readonly="readonly" ></c:if> --%>
		 
		
<!-- 		<div class="padding-10 border1">
			 <label class="fl">需求计划导入（Excel表格）：</label><input  style="float: left;" type="file" id="fileName" name="file" />   
			<button style="float: left;"  class="btn padding-right-10 btn_back" onclick="upload()">导入</button><span style="margin-left: 200px"></span>
			 -->
				 <!-- 	 <button class="btn padding-left-10 padding-right-10 btn_back">导入</button>
 -->
			<!-- 	<button class="btn padding-left-10 padding-right-10 btn_back"
					onclick="adds()">录入明细</button> -->
			 
			
	<!-- 	</div> -->
	</div>

	<button style="margin-left: 106px;margin-top: 20px;"  class="btn padding-right-10 btn_back" onclick="aadd()">添加子节点</button>
	<button style="margin-top: 20px;" class="btn padding-left-10 padding-right-10 btn_back" onclick="same()">添加同级节点</button>
	<button  style="margin-top: 20px;" class="btn padding-left-10 padding-right-10 btn_back" onclick="down()">下载Excel模板</button>
	<button  style="margin-top: 20px;" class="btn padding-left-10 padding-right-10 btn_back" onclick="typeShow()">查看产品分类目录</button>
	<button  style="margin-top: 20px;" class="btn padding-left-10 padding-right-10 btn_back" onclick="chakan()">查看编制说明</button>
				
<!-- 	<button class="btn padding-left-10 padding-right-10 btn_back" onclick="typeShow()">查看产品分类目录</button> -->


	<div class="container clear margin-top-30" id="add_div" >

		<form id="add_form" action="${pageContext.request.contextPath}/purchaser/adddetail.html" method="post">
		<!-- 	<input type="hidden" name="planName" id="fjhmc">
			<input type="hidden" name="planNo" id="fjhbh">
			<input type="hidden" name="planType" value="" id="ptype"> -->
			<table class="table table-bordered table-condensed mt5" >
				<thead>
					<tr>
						<th class="info w50">序号</th>
						<th class="info">需求部门</th>
						<th class="info">物资类别及物种名称</th>
						<th class="info">规格型号</th>
						<th class="info">质量技术标准（技术参数）</th>
						<th class="info">计量单位</th>
						<th class="info">采购数量</th>
						<th class="info">单位（元）</th>
						<th class="info">预算金额（万元）</th>
						<th class="info">交货期限</th>
						<th class="info">采购方式建议</th>
						<th class="info">供应商名称</th>
						<th class="info">是否申请办理免税</th>
						<th class="info">物资用途（仅进口）</th>
						<th class="info">使用单位（仅进口）</th>
						<th class="info">备注</th>
						<!-- <th class="info">操作</th> -->
					</tr>
				</thead>

				<tr>
					<td class="tc w50">
					<input style="border: 0px;" type="hidden" name="list[0].id" id="purid" value="">
					<input style="border: 0px;width: 60px;" type="text" name="list[0].seq" value="">
					</td>
					<td><input style="border: 0px;width: 130px;" type="text" name="list[0].department" value=""></td>
					<td><input style="border: 0px;width: 100px;"  type="text" name="list[0].goodsName" value=""></td>
					<td class="tc"><input style="border: 0px;width: 70px;"  type="text" name="list[0].stand" value=""></td>
					<td class="tc"><input style="border: 0px;width: 80px;"  type="text" name="list[0].qualitStand" value=""></td>
					<td class="tc"><input style="border: 0px;width: 55px;"  type="text" name="list[0].item" value=""></td>
					<td class="tc"><input style="border: 0px;width: 80px;"  type="text" name="list[0].purchaseCount" value=""></td>
					<td class="tc"><input style="border: 0px;width: 80px;"  type="text" name="list[0].price" value=""></td>
					<td class="tc"><input style="border: 0px;width: 80px;"  type="text" name="list[0].budget" value=""></td>
					<td><input style="border: 0px;width: 90px;"  type="text" name="list[0].deliverDate" value=""></td>
					<td>
					 <select name="list[0].purchaseType" style="width:90px" id="select">
              				    <option value="" >请选择</option>
	                            <option value="gkzb" >公开招标</option>
	                            <option value="yqzb" >邀请招标</option>
	                            <option value="jzxtp" >竞争性谈判</option>
	                            <option value="xjcg" >询价采购</option>
	                            <option value="dyly"  >单一来源</option>
			           </select>
			                
					</td>
					<td class="tc"><input style="border: 0px;width:50px;"  type="text" name="list[0].supplier" value=""></td>
					<td class="tc"><input style="border: 0px;width:50px;"  type="text" name="list[0].isFreeTax" value=""></td>
					<td class="tc"><input style="border: 0px;"  type="text" name="list[0].goodsUse" value=""></td>
					<td class="tc"><input style="border: 0px;"  type="text" name="list[0].useUnit" value=""></td>
					<td class="tc"><input style="border: 0px;"  type="text" name="list[0].memo" value=""></td>
						<!-- <td class="tc">
							 <input class="btn btn-windows add" name="dyadds" type="button" onclick="aadd(this)" value="添加子节点">
							 <input class="btn btn-windows add" name="delt" type="button" onclick="same(this)" value="添加同级节点">
						 	 <input class="btn btn-windows add" name="delt" type="button" onclick="news(this)" value="新加任务">
					 
						</td> -->
				</tr>
				<!-- <tr>
					<td class="tc w50"><input style="border: 0px;" type="text" name="list[1].seq" value=""></td>
					<td><input style="border: 0px;" type="text" name="list[1].department" value=""></td>
					<td><input style="border: 0px;"  type="text" name="list[1].goodsName" value=""></td>
					<td class="tc"><input style="border: 0px;"  type="text" name="list[1].stand" value=""></td>
					<td class="tc"><input style="border: 0px;"  type="text" name="list[1].qualitStand" value=""></td>
					<td class="tc"><input style="border: 0px;"  type="text" name="list[1].item" value=""></td>
					<td class="tc"><input style="border: 0px;"  type="text" name="list[1].purchaseCount" value=""></td>
					<td class="tc"><input style="border: 0px;"  type="text" name="list[1].price" value=""></td>
					<td class="tc"><input style="border: 0px;"  type="text" name="list[1].budget" value=""></td>
					<td><input style="border: 0px;"  type="text" name="list[1].deliverDate" value=""></td>
					<td><input style="border: 0px;"  type="text" name="list[1].purchaseType" value=""></td>
					<td class="tc"><input style="border: 0px;"  type="text" name="list[1].supplier" value=""></td>
					<td class="tc"><input style="border: 0px;"  type="text" name="list[1].isFreeTax" value=""></td>
					<td class="tc"><input style="border: 0px;"  type="text" name="list[1].goodsUse" value=""></td>
					<td class="tc"><input style="border: 0px;"  type="text" name="list[1].useUnit" value=""></td>
					<td class="tc"><input style="border: 0px;"  type="text" name="list[1].memo" value=""></td>
				</tr>
				<tr>
					<td class="tc w50"><input style="border: 0px;" type="text" name="list[2].seq" value=""></td>
					<td><input style="border: 0px;" type="text" name="list[2].department" value=""></td>
					<td><input style="border: 0px;"  type="text" name="list[2].goodsName" value=""></td>
					<td class="tc"><input style="border: 0px;"  type="text" name="list[2].stand" value=""></td>
					<td class="tc"><input style="border: 0px;"  type="text" name="list[2].qualitStand" value=""></td>
					<td class="tc"><input style="border: 0px;"  type="text" name="list[2].item" value=""></td>
					<td class="tc"><input style="border: 0px;"  type="text" name="list[2].purchaseCount" value=""></td>
					<td class="tc"><input style="border: 0px;"  type="text" name="list[2].price" value=""></td>
					<td class="tc"><input style="border: 0px;"  type="text" name="list[2].budget" value=""></td>
					<td><input style="border: 0px;"  type="text" name="list[2].deliverDate" value=""></td>
					<td><input style="border: 0px;"  type="text" name="list[2].purchaseType" value=""></td>
					<td class="tc"><input style="border: 0px;"  type="text" name="list[2].supplier" value=""></td>
					<td class="tc"><input style="border: 0px;"  type="text" name="list[2].isFreeTax" value=""></td>
					<td class="tc"><input style="border: 0px;"  type="text" name="list[2].goodsUse" value=""></td>
					<td class="tc"><input style="border: 0px;"  type="text" name="list[2].useUnit" value=""></td>
					<td class="tc"><input style="border: 0px;"  type="text" name="list[2].memo" value=""></td>
				</tr>
				<tr>
					<td class="tc w50"><input style="border: 0px;" type="text" name="list[3].seq" value=""></td>
					<td><input style="border: 0px;" type="text" name="list[3].department" value=""></td>
					<td><input style="border: 0px;"  type="text" name="list[3].goodsName" value=""></td>
					<td class="tc"><input style="border: 0px;"  type="text" name="list[3].stand" value=""></td>
					<td class="tc"><input style="border: 0px;"  type="text" name="list[3].qualitStand" value=""></td>
					<td class="tc"><input style="border: 0px;"  type="text" name="list[3].item" value=""></td>
					<td class="tc"><input style="border: 0px;"  type="text" name="list[3].purchaseCount" value=""></td>
					<td class="tc"><input style="border: 0px;"  type="text" name="list[3].price" value=""></td>
					<td class="tc"><input style="border: 0px;"  type="text" name="list[3].budget" value=""></td>
					<td><input style="border: 0px;"  type="text" name="list[3].deliverDate" value=""></td>
					<td><input style="border: 0px;"  type="text" name="list[3].purchaseType" value=""></td>
					<td class="tc"><input style="border: 0px;"  type="text" name="list[3].supplier" value=""></td>
					<td class="tc"><input style="border: 0px;"  type="text" name="list[3].isFreeTax" value=""></td>
					<td class="tc"><input style="border: 0px;"  type="text" name="list[3].goodsUse" value=""></td>
					<td class="tc"><input style="border: 0px;"  type="text" name="list[3].useUnit" value=""></td>
					<td class="tc"><input style="border: 0px;"  type="text" name="list[3].memo" value=""></td>
				</tr>
				<tr>
					<td class="tc w50"><input style="border: 0px;" type="text" name="list[4].seq" value=""></td>
					<td><input style="border: 0px;" type="text" name="list[4].department" value=""></td>
					<td><input style="border: 0px;"  type="text" name="list[4].goodsName" value=""></td>
					<td class="tc"><input style="border: 0px;"  type="text" name="list[4].stand" value=""></td>
					<td class="tc"><input style="border: 0px;"  type="text" name="list[4].qualitStand" value=""></td>
					<td class="tc"><input style="border: 0px;"  type="text" name="list[4].item" value=""></td>
					<td class="tc"><input style="border: 0px;"  type="text" name="list[4].purchaseCount" value=""></td>
					<td class="tc"><input style="border: 0px;"  type="text" name="list[4].price" value=""></td>
					<td class="tc"><input style="border: 0px;"  type="text" name="list[4].budget" value=""></td>
					<td><input style="border: 0px;"  type="text" name="list[4].deliverDate" value=""></td>
					<td><input style="border: 0px;"  type="text" name="list[4].purchaseType" value=""></td>
					<td class="tc"><input style="border: 0px;"  type="text" name="list[4].supplier" value=""></td>
					<td class="tc"><input style="border: 0px;"  type="text" name="list[4].isFreeTax" value=""></td>
					<td class="tc"><input style="border: 0px;"  type="text" name="list[4].goodsUse" value=""></td>
					<td class="tc"><input style="border: 0px;"  type="text" name="list[4].useUnit" value=""></td>
					<td class="tc"><input style="border: 0px;"  type="text" name="list[4].memo" value=""></td>
				</tr>
				<tr>
					<td class="tc w50"><input style="border: 0px;" type="text" name="list[5].seq" value=""></td>
					<td><input style="border: 0px;" type="text" name="list[5].department" value=""></td>
					<td><input style="border: 0px;"  type="text" name="list[5].goodsName" value=""></td>
					<td class="tc"><input style="border: 0px;"  type="text" name="list[5].stand" value=""></td>
					<td class="tc"><input style="border: 0px;"  type="text" name="list[5].qualitStand" value=""></td>
					<td class="tc"><input style="border: 0px;"  type="text" name="list[5].item" value=""></td>
					<td class="tc"><input style="border: 0px;"  type="text" name="list[5].purchaseCount" value=""></td>
					<td class="tc"><input style="border: 0px;"  type="text" name="list[5].price" value=""></td>
					<td class="tc"><input style="border: 0px;"  type="text" name="list[5].budget" value=""></td>
					<td><input style="border: 0px;"  type="text" name="list[5].deliverDate" value=""></td>
					<td><input style="border: 0px;"  type="text" name="list[5].purchaseType" value=""></td>
					<td class="tc"><input style="border: 0px;"  type="text" name="list[5].supplier" value=""></td>
					<td class="tc"><input style="border: 0px;"  type="text" name="list[5].isFreeTax" value=""></td>
					<td class="tc"><input style="border: 0px;"  type="text" name="list[5].goodsUse" value=""></td>
					<td class="tc"><input style="border: 0px;"  type="text" name="list[5].useUnit" value=""></td>
					<td class="tc"><input style="border: 0px;"  type="text" name="list[5].memo" value=""></td>
				</tr>   -->
			 <tr style="display: none">

					<td class="tc" colspan="16"> <input type="hidden" name="planType" value="" id="ptype">
					 <input class="btn btn-windows add" name="dyadds" type="button" onclick="aadd()" value="添加">
					<!--  <input class="btn btn-windows delete" name="delt" type="button" onclick="delets()" value="删除"> -->
					 
					 </td>
				</tr>  
			</table>
			<!--  <input class="btn btn-windows reset" value="取消"
				type="button" onclick="hide()"> -->
		</form>
					<input class="btn btn-windows save" style="margin-left: 500px;" type="button" onclick="incr()" value="提交"> <button  class="btn padding-left-20 padding-right-20 btn_back"  onclick="location.href='javascript:history.go(-1);'">返回</button>
		
	</div>


	<div id="content" class="dnone">
		<p align="center">编制说明
		<p  style="margin-left: 20px;" >1、请严格按照序号顺序为：一、(一)、1、(1)、a、(a)的顺序填写序号</p>

		<p style="margin-left: 20px;">2、任务明细最多为六级,请勿多于六级</p>

		<p style="margin-left: 20px;">3、请勿空行填写</p>

		<p style="margin-left: 20px;">4、需求单位名称不能为空</p>

		<p style="margin-left: 20px;">5、请按表式填写计划明细。用户可以编辑行，但不能增加或删除列。</p>

		<p style="margin-left: 20px;">6、最子级请严格按照填写说明填写，父级菜单请将序号与金额填写正确(金额=所有子项金额/10000)
		</p>

		<p style="margin-left: 20px;">7、采购方式填写选项包括：公开招标、邀请招标、竞争性谈判、询价、单一来源。</p>

		<p style="margin-left: 20px;">8、选择单一来源采购方式的，必须填写供应商名称；选择其他采购方式的不填。</p>

		<p style="margin-left: 20px;">9、规格型号和质量技术标准内容分别不得超过250、1000字。超过此范围的，请以附件形式另报。</p>

		<p style="margin-left: 20px;">10、采购数量、单价和预算金额必须为数字格式。其中单价单位为“元”，预算金额单位为“万元”。</p>
		<button class="btn padding-left-10 padding-right-10 btn_back" style="margin-left: 230px;"
			onclick="closeLayer()">确定</button>

	</div>

<%-- 	<input type="hidden" id="goodsType" value="${type }"> --%>
	<input type="hidden" id="count" value="0">
	<div  style="margin-left: 600px;">
	   <div id="ztree" class="ztree"></div>
	   <button id="bt" style="display: none;" class="btn padding-left-10 padding-right-10 btn_back" onclick="typehide()">取消</button>
	  </div> 
	  
	  <form id="" action="<%=basePath%>purchaser/ztree.html" method="post">
			<input type="hidden" name="planName" id="fjhmc">
			<input type="hidden" name="planNo" id="fjhbh">
			<input type="hidden" name="type" value="" id="ptype">
			
			</form>
</body>
</html>
