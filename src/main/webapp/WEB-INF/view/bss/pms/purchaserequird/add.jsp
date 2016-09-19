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




<link href="<%=basePath%>public/ZHH/css/common.css" media="screen"
	rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/bootstrap.min.css"
	media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/style.css" media="screen"
	rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/line-icons.css" media="screen"
	rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/app.css" media="screen"
	rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/application.css" media="screen"
	rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/header-v4.css" media="screen"
	rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/header-v5.css" media="screen"
	rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/brand-buttons.css"
	media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/footer-v2.css" media="screen"
	rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/img-hover.css" media="screen"
	rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/page_job.css" media="screen"
	rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/shop.style.css" media="screen"
	rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/purchase/css/purchase.css"
	media="screen" rel="stylesheet" type="text/css">
<script type="text/javascript"
	src="<%=basePath%>public/ZHH/js/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=basePath%>public/ZHH/js/jquery_ujs.js"></script>
<script type="text/javascript"
	src="<%=basePath%>public/ZHH/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="<%=basePath%>public/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>public/layer/layer.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/upload/ajaxfileupload.js"></script>

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
	 		 
	 		 
 		var type=$("#goodsType").val();
 		
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
		                    	}else if(data="exception"){
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
		  $("#add_div").show();
	  }
	  function hide(){
		  $("#add_div").hide();
	  }
	  
 	 //动态添加
  	  function aadd(){
		  var  s=$("#count").val();
	      	s++;
	      	$("#count").val(s);
	        var tr = $("input[name=dyadds]").parent().parent();
	        $(tr).before("<tr><td class='tc'><input type='text' name='list["+s+"].seq' /></td>"+
		       "<td class='tc'> <input  type='text' name='list["+s+"].department' /> </td>"+
		       "<td class='tc'> <input  type='text' name='list["+s+"].goodsName' /> </td>"+ 
		       "<td class='tc'> <input  type='text' name='list["+s+"].stand' /> </td>"+ 
		       "<td class='tc'> <input  type='text' name='list["+s+"].qualitStand' /> </td>"+ 
		       	"<td class='tc'> <input type='text' name='list["+s+"].item' /> </td>"+  
		       	"<td class='tc'> <input type='text' name='list["+s+"].purchaseCount' /> </td>"+  
		       	"<td class='tc'> <input type='text' name='list["+s+"].price' /> </td>"+  
		       	"<td class='tc'> <input type='text' name='list["+s+"].budget' /> </td>"+  
		       	"<td class='tc'> <input type='text' name='list["+s+"].deliverDate' /> </td>"+  
		       	"<td class='tc'> <input type='text' name='list["+s+"].purchaseType' /> </td>"+  
		       	"<td class='tc'> <input type='text' name='list["+s+"].supplier' /> </td>"+  
		       	"<td class='tc'> <input type='text' name='list["+s+"].isFreeTax' /> </td>"+  
		       	"<td class='tc'> <input type='text' name='list["+s+"].goodsUse' /> </td>"+  
		       	"<td class='tc'> <input type='text' name='list["+s+"].useUnit' /> </td>"+
		       	"<td class='tc'> <input type='text' name='list["+s+"].memo' /> </td>"+  
	        +"<tr/>");
	  }
 	 function incr(){
 		 
 		
 	  var name=$("#jhmc").val();
 		 var no=$("#jhbh").val();
 		 
 		 if(name==""){
 			layer.tips("计划名称不允许为空","#jhmc");
 		 } else if(no==""){
 			layer.tips("计划编号不允许为空","#jhbh");
 		 } 
 		 
 		 else{
 			 $("#fjhmc").val(name);
 	 		$("#fjhbh").val(no);
 	 		
 			 $("#add_form").find("*").each(function(){ 
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
 		 });
 		//  $("#add_form").submit();   
 		 
 		 }   
 		
 			 
 		 
	}
 	 
 	 function down(){
 		 window.location.href="<%=basePath%>purchaser/download.html?filename=模板.xlsx";
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
	<span>计划名称<input type="text" name="name" id="jhmc" ></span>
		<span>计划编号<input type="text" name="no" id="jhbh" ></span>
		<span>物资类别<c:if test="${type=='1' }"> <input type="text" name="" id="hw" value="货物" readonly="readonly" > </c:if> 
		<c:if test="${type=='2' }">工程 <input type="text" name="" id="fw" value="服务" readonly="readonly"  ></c:if>
		 <c:if test="${type=='3' }">服务<input type="text" name="" id="gc" value="工程" readonly="readonly" ></c:if>
		 </span>
		
		<h2 class="padding-10 border1">
			<ul class="demand_list" >
				<li class="fl"><label class="fl">需求计划导入（Excel表格）：</label><span><input
						type="file" id="fileName" name="file" /> </span> <a href="#"
					class="btn padding-left-10 padding-right-10 btn_back"
					onclick="upload()">导入</a></li>
			</ul>
			<span class="fr">
				<button class="btn padding-left-10 padding-right-10 btn_back" onclick="down()">下载Excel模板</button>
				<button class="btn padding-left-10 padding-right-10 btn_back">查看产品分类目录</button>
				<button class="btn padding-left-10 padding-right-10 btn_back"
					onclick="chakan()">查看编制说明</button> <!-- 	 <button class="btn padding-left-10 padding-right-10 btn_back">导入</button>
 -->
				<button class="btn padding-left-10 padding-right-10 btn_back"
					onclick="adds()">录入</button>
			</span>
			
		</h2>
	</div>




	<div class="container clear margin-top-30" id="add_div" style="display: none;">
	
	

		<form id="add_form" action="<%=basePath%>purchaser/adddetail.html" method="post">
			<input type="hidden" name="planName" id="fjhmc">
			<input type="hidden" name="planNo" id="fjhbh">
			<table class="">
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
					</tr>
				</thead>

				<tr>
					<td class="tc w50"><input type="text" name="list[0].seq"
						value=""></td>
					<td><input type="text" name="list[0].department" value=""></td>
					<td><input type="text" name="list[0].goodsName" value=""></td>
					<td class="tc"><input type="text" name="list[0].stand"
						value=""></td>
					<td class="tc"><input type="text" name="list[0].qualitStand"
						value=""></td>
					<td class="tc"><input type="text" name="list[0].item" value=""></td>
					<td class="tc"><input type="text" name="list[0].purchaseCount"
						value=""></td>
					<td class="tc"><input type="text" name="list[0].price"
						value=""></td>
					<td class="tc"><input type="text" name="list[0].budget"
						value=""></td>
					<td><input type="text" name="list[0].deliverDate" value=""></td>
					<td><input type="text" name="list[0].purchaseType" value=""></td>
					<td class="tc"><input type="text" name="list[0].supplier"
						value=""></td>
					<td class="tc"><input type="text" name="list[0].isFreeTax"
						value=""></td>
					<td class="tc"><input type="text" name="list[0].goodsUse"
						value=""></td>
					<td class="tc"><input type="text" name="list[0].useUnit"
						value=""></td>
					<td class="tc"><input type="text" name="list[0].memo" value=""></td>
				</tr>
				<tr>

					<td class="tc" colspan="16"> <input type="hidden" name="type" value="${type }"> <input class="btn btn-windows add" name="dyadds" type="button" onclick="aadd()" value="添加"></td>
				</tr>
			</table>
			<input class="btn btn-windows save" type="button" onclick="incr()"
				value="提交"> <input class="btn btn-windows reset" value="取消"
				type="button" onclick="hide()">
		</form>
	</div>


	<div id="content" class="div_show">
		<p align="center">编制说明
		<p class="bzsm">1、请严格按照序号顺序为：一、(一)、1、(1)、a、(a)的顺序填写序号</p>

		<p class="bzsm">2、任务明细最多为六级,请勿多于六级</p>

		<p class="bzsm">3、请勿空行填写</p>

		<p class="bzsm">4、需求单位名称不能为空</p>

		<p class="bzsm">5、请按表式填写计划明细。用户可以编辑行，但不能增加或删除列。</p>

		<p class="bzsm">6、最子级请严格按照填写说明填写，父级菜单请将序号与金额填写正确(金额=所有子项金额/10000)
		</p>

		<p class="bzsm">7、采购方式填写选项包括：公开招标、邀请招标、竞争性谈判、询价、单一来源。</p>

		<p class="bzsm">8、选择单一来源采购方式的，必须填写供应商名称；选择其他采购方式的不填。</p>

		<p class="bzsm">9、规格型号和质量技术标准内容分别不得超过250、1000字。超过此范围的，请以附件形式另报。</p>

		<p class="bzsm">10、采购数量、单价和预算金额必须为数字格式。其中单价单位为“元”，预算金额单位为“万元”。</p>
		<button class="btn padding-left-10 padding-right-10 btn_back qd"
			onclick="closeLayer()">确定</button>

	</div>

	<input type="hidden" id="goodsType" value="${type }">
	<input type="hidden" id="count" value="0">
</body>
</html>
