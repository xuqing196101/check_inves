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
  	
    function edit(){
    	var id=[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			id.push($(this).val());
		}); 
		if(id.length==1){
			
			window.location.href="<%=basePath%>purchaser/queryByNo.html?planNo="+no;
		}else if(id.length>1){
			layer.alert("只能选择一个",{offset: ['222px', '390px'], shade:0.01});
		}else{
			layer.alert("请选择需要修改的版块",{offset: ['222px', '390px'], shade:0.01});
		}
    }
    
    function del(){
    	var id =[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			id.push($(this).val()); 
		}); 
		if(id.length>0){
			layer.confirm('您确定要删除吗?', {title:'提示',offset: ['222px','360px'],shade:0.01}, function(index){
				layer.close(index);
				window.location.href="<%=basePath%>park/delete.html?id="+id;
			});
		}else{
			layer.alert("请选择要删除的版块",{offset: ['222px', '390px'], shade:0.01});
		}
    }
    var index;
    function add(){
    	
    	index=layer.open({
			  type: 1, //page层
			  area: ['300px', '200px'],
			  title: '',
			  closeBtn: 1,
			  shade:0.01, //遮罩透明度
			  moveType: 1, //拖拽风格，0是默认，1是传统拖动
			  shift: 1, //0-6的动画形式，-1不开启
			  offset: ['80px', '600px'],
			  content: $('#content'),
			});
    	
   
    }
    
	//鼠标移动显示全部内容
	function out(content){
	if(content.length>10){
	layer.msg(content, {
			icon:6,
			shade:false,
			area: ['600px'],
			time : 1000    //默认消息框不关闭
		});//去掉msg图标
	}else{
		layer.closeAll();//关闭消息框
	}
}
	
	function closeLayer(){
		var val=$("input[name='goods']:checked").val();
		
		window.location.href="<%=basePath%>purchaser/add.html?type=" + val;
		layer.close(index);
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

			<form action="<%=basePath%>purchaser/update.html" method="post">
				<table class="table table-bordered table-condensed mt5">
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

					<c:forEach items="${list }" var="obj" varStatus="vs">
						<tr>
							<td class="tc w50"><input type="text"
								name="list[${vs.count }].seq" value="${obj.seq }"><input
								type="hidden" name="list[${vs.count }].id" value="${obj.id }">
							</td>
							<td><input type="text" name="list[${vs.count }].department" value="${obj.department }"></td>
							<td><input type="text" name="list[${vs.count }].goodsName" value="${obj.goodsName }"></td>
							<td class="tc"><input type="text" name="list[${vs.count }].stand" value="${obj.stand }"></td>
							<td class="tc"><input type="text" name="list[${vs.count }].qualitStand" value="${obj.qualitStand }"></td>
							<td class="tc"><input type="text" name="list[${vs.count }].item" value="${obj.item }"></td>
							<td class="tc"><input type="text" name="list[${vs.count }].purchaseCount" value="${obj.purchaseCount }"></td>
							<td class="tc"><input type="text" name="list[${vs.count }].price" value="${obj.price }"></td>
							<td class="tc"><input type="text" name="list[${vs.count }].budget" value="${obj.budget }"></td>
							<td><input type="text" name="list[${vs.count }].deliverDate" value="${obj.deliverDate }"></td>
							<td><input type="text" name="list[${vs.count }].purchaseType" value="${obj.purchaseType }"></td>
							<td class="tc"><input type="text" name="list[${vs.count }].supplier" value="${obj.supplier }"></td>
							<td class="tc"><input type="text" name="list[${vs.count }].isFreeTax" value="${obj.isFreeTax }"></td>
							<td class="tc"><input type="text" name="list[${vs.count }].goodsUse" value="${obj.goodsUse }"></td>
							<td class="tc"><input type="text" name="list[${vs.count }].useUnit" value="${obj.useUnit }"></td>
							<td class="tc"><input type="text" name="list[${vs.count }].memo" value="${obj.memo }">
							<input type="hidden" name="list[${vs.count }].planName" value="${obj.planName }">
							<input type="hidden" name="list[${vs.count }].planNo" value="${obj.planNo }">
							<input type="hidden" name="list[${vs.count }].planType" value="${obj.planType }">
							<input type="hidden" name="list[${vs.count }].parentId" value="${obj.parentId }">
							<input type="hidden" name="list[${vs.count }].historyStatus" value="${obj.historyStatus }">
							<input type="hidden" name="list[${vs.count }].goodsType" value="${obj.goodsType }">
							</td>
						</tr>

					</c:forEach>
				</table>
				<input class="btn btn-windows save" type="submit" value="提交">
				<input class="btn btn-windows reset" value="返回" type="button"
					onclick="location.href='javascript:history.go(-1);'">
			</form>
		</div>
	</div>

</body>
</html>
