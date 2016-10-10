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


<title>采购需求管理</title>
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

<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery_ujs.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=basePath%>public/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>public/layer/layer.js"></script>

<script type="text/javascript">
	/** 全选全不选 */
	function selectAll(){
		 var checklist = document.getElementsByName ("chkItem");
		 var checkAll = document.getElementById("checkAll");
		 if(checkAll.checked){
			   for(var i=0;i<checklist.length;i++)
			   {
			      checklist[i].checked = true;
			   } 
			 }else{
			  for(var j=0;j<checklist.length;j++)
			  {
			     checklist[j].checked = false;
			  }
		 	}
		}
	
	/** 单选 */
	function check(){
		 var count=0;
		 var checklist = document.getElementsByName ("chkItem");
		 var checkAll = document.getElementById("checkAll");
		 for(var i=0;i<checklist.length;i++){
			   if(checklist[i].checked == false){
				   checkAll.checked = false;
				   break;
			   }
			   for(var j=0;j<checklist.length;j++){
					 if(checklist[j].checked == true){
						   checkAll.checked = true;
						   count++;
					   }
				 }
		   }
	}
	
  	function view(no){
  		
  		
  		window.location.href="<%=basePath%>purchaser/queryByNo.html?planNo="+no;
  	}
  	
  	 function aadd(){
		  var  s=$("#count").val();
	      	s++;
	      	$("#count").val(s);
	        var tr = $("input[name=dyadds]").parent().parent();
	        $(tr).before("<tr><td class='tc'><input type='text' name='list["+s+"].seq' /></td>"+
		       "<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].department' /> </td>"+
		       "<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].goodsName' /> </td>"+ 
		       "<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].stand' /> </td>"+ 
		       "<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].qualitStand' /> </td>"+ 
		       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].item' /> </td>"+  
		       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].purchaseCount' /> </td>"+  
		       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].price' /> </td>"+  
		       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].budget' /> </td>"+  
		       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].deliverDate' /> </td>"+  
		       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].purchaseType' /> </td>"+  
		       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].supplier' /> </td>"+  
		       	"<td class='tc'> <input style='border: 0px;'type='text' name='list["+s+"].isFreeTax' /> </td>"+  
		       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].goodsUse' /> </td>"+  
		       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].useUnit' /> </td>"+
		       	"<td class='tc'> <input style='border: 0px;' type='text' name='list["+s+"].memo' /> </td>"+  
	        +"<tr/>");
	  }
  	 
  	var flag=true;
	function checks(obj){
		  var name=$(obj).attr("name");
		  var planNo=$("#pNo").val();
		  var val=$(obj).val();
		  var defVal=obj.defaultValue;
			if(val!=defVal){
				$.ajax({
					url:"<%=basePath%>adjust/filed.html",
					type:"post",
					data:{
						planNo:planNo,
						name:name
					},
					success: function(data){
						 if(data=='exit'){
							 flag=false;
							 layer.tips("该字段不允许修改",obj);
						 }
					 },
					error:function(data){
						 
					 }
					 
				});
			} 
	}
	
	function sub(){
		if(flag==true){
			$("#adjust").submit();
		}
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
	<div class="container">
		<div class="headline-v2 fl">
			<h2>计划明细</h2>
		</div>
		<div class="container clear margin-top-30">

			<form id="adjust" action="<%=basePath%>adjust/update.html" method="post" enctype="multipart/form-data">
				<table class="table table-bordered table-condensed mt5">
					<thead>
						<tr>
						<th class="info" colspan="16">事业部门需求</th>
						<th class="info" colspan="3">一轮评审意见</th>
						<th class="info" colspan="2">二轮评审意见</th>
						</tr>
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
							<th class="info">采购方式</th>
							<th class="info">采购机构</th>
							<th class="info">其他建议</th>
							<th class="info">技术参数意见</th>
							<th class="info">其他建议</th>
						</tr>
					</thead>

					<c:forEach items="${list }" var="obj" varStatus="vs">
						<tr>
							<td class="tc w50"><input style="border: 0px;" type="text" name="list[${vs.index }].seq" value="${obj.seq }"><input style="border: 0px;" type="hidden" name="list[${vs.index }].id" value="${obj.id }">
							</td>
							<td><input style="border: 0px;" type="text" name="list[${vs.index }].department" onblur="checks(this)" value="${obj.department }"></td>
							<td><input style="border: 0px;" type="text" name="list[${vs.index }].goodsName"  value="${obj.goodsName }"></td>
							<td class="tc"><input style="border: 0px;" type="text" name="list[${vs.index }].stand" value="${obj.stand }"></td>
							<td class="tc"><input style="border: 0px;" type="text" name="list[${vs.index }].qualitStand" value="${obj.qualitStand }"></td>
							<td class="tc"><input style="border: 0px;" type="text" name="list[${vs.index }].item" value="${obj.item }"></td>
							<td class="tc"><input style="border: 0px;" type="text" name="list[${vs.index }].purchaseCount" onblur="checks(this)"  value="${obj.purchaseCount }"></td>
							<td class="tc"><input style="border: 0px;" type="text" name="list[${vs.index }].price" value="${obj.price }"></td>
							<td class="tc"><input style="border: 0px;" type="text" name="list[${vs.index }].budget" onblur="checks(this)"  value="${obj.budget }"></td>
							<td><input style="border: 0px;" type="text" name="list[${vs.index }].deliverDate" onblur="checks(this)" value="${obj.deliverDate }"></td>
							<td><input style="border: 0px;" type="text" name="list[${vs.index }].purchaseType" value="${obj.purchaseType }"></td>
							<td class="tc"><input style="border: 0px;" type="text" name="list[${vs.index }].supplier" value="${obj.supplier }"></td>
							<td class="tc"><input style="border: 0px;" type="text" name="list[${vs.index }].isFreeTax" value="${obj.isFreeTax }"></td>
							<td class="tc"><input style="border: 0px;"type="text" name="list[${vs.index }].goodsUse" value="${obj.goodsUse }"></td>
							<td class="tc"><input  style="border: 0px;" type="text" name="list[${vs.index }].useUnit" value="${obj.useUnit }"></td>
							<td class="tc"><input style="border: 0px;" type="text" name="list[${vs.index }].memo" value="${obj.memo }">
							<input type="hidden" name="list[${vs.index }].planName" value="${obj.planName }">
							<input type="hidden" name="list[${vs.index }].planNo" value="${obj.planNo }">
							<input type="hidden" name="list[${vs.index }].planType" value="${obj.planType }">
							<input type="hidden" name="list[${vs.index }].parentId" value="${obj.parentId }">
							<input type="hidden" name="list[${vs.index }].historyStatus" value="${obj.historyStatus }">
							<input type="hidden" name="list[${vs.index }].goodsType" value="${obj.goodsType }">
							<input type="hidden" name="list[${vs.index }].organization" value="${obj.organization }">
							<input type="hidden" name="list[${vs.index }].auditDate" value="${obj.auditDate }">
							<input type="hidden" name="list[${vs.index }].isMaster" value="${obj.isMaster }">
							<input type="hidden" name="list[${vs.index }].isDelete" value="${obj.isDelete }">
							<input type="hidden" name="list[${vs.index }].status" value="${obj.status }">
							<input type="hidden" name="list[${vs.index }].threePurchaseType" value="${obj.threePurchaseType }">
							<input type="hidden" name="list[${vs.index }].threeOrganiza" value="${obj.threeOrganiza }">
							<input type="hidden" name="list[${vs.index }].threeAdvice" value="${obj.threeAdvice }">
							<input type="hidden" name="list[${vs.index }].createAt" value="${obj.createdAt }">
							<input type="hidden" name="list[${vs.index }].isCollect" value="${obj.isCollect }">
							</td>
							<td class="tc">
							<input type="text"  style="border: 0px;" name="list[${vs.index }].onePurchaseType" value="${obj.onePurchaseType }">
							</td>
							<td class="tc">
							<input type="text"  style="border: 0px;" name="list[${vs.index }].oneOrganiza" value="${obj.oneOrganiza }">
							</td>
							<td class="tc">
							<input type="text"  style="border: 0px;" name="list[${vs.index }].oneAdvice" value="${obj.oneAdvice }">
							</td>
							<td class="tc">
							<input type="text"  style="border: 0px;" name="list[${vs.index }].twoTechAdvice" value="${obj.twoTechAdvice }">
							</td>
							<td class="tc">
							<input type="text"  style="border: 0px;" name="list[${vs.index }].twoAdvice" value="${obj.twoAdvice }">
							</td>
							
						</tr>

					</c:forEach>
					
					<tr>

					<td class="tc" colspan="16"> <input type="hidden" name="type" value="${fn:length(list)}"> <input class="btn btn-windows add" name="dyadds" type="button" onclick="aadd()" value="添加"></td>
				</tr>
				
				</table>
				<div class=""><a class="upload">上传附件</a><input type="file" name="file"> </div>
				<input class="btn btn-windows save" type="button" value="提交" onclick="sub()">
				<input class="btn btn-windows reset" value="返回" type="button" onclick="location.href='javascript:history.go(-1);'">
			</form>
		</div>
	</div>
	<input type="hidden" id="pNo" name="" value="${planNo }">
</body>
</html>
