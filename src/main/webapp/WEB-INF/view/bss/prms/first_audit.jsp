<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<jsp:include page="/WEB-INF/view/common.jsp"></jsp:include>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>初审项定义</title>
<script type="text/javascript">
	function submit1(){
		
		<%-- $.ajax({
			url:"<%=basePath %>firstAudit/add.html",
			data:$("#form1").serialize(),
			type:"post",
			success:function(){
				window.location.reload();
			},
			error:function(){
				layer.msg("添加失败",{offset: ['222px', '390px']});
			}
		}); --%>
		var name = $("#name").val();
		if(!name){
			layer.tips("请填写名称", "#name");
			return ;
		}
		var id=[]; 
		$('input[name="kind"]:checked').each(function(){ 
			id.push($(this).val());
		}); 
		if(id.length==0){
			layer.tips("请选择类型", "#kind");
			return ;
		}
		
		/* var creater = $("#creater").val();
		if(!creater){
			layer.tips("请填写名称", "#creater");
			return ;
		} */
		$("#form1").submit();
		//$("#form1").reset();
	}
	 var index;
	function cancel(){
	   layer.close(index);
	}
	function openWindow(){
		index = layer.open({
	          type: 1, //page层
	          area: ['700px', '300px'],
	          title: '手动添加初审项',
	          shade:0.01, //遮罩透明度
	          moveType: 1, //拖拽风格，0是默认，1是传统拖动
	          shift: 1, //0-6的动画形式，-1不开启
	          offset: ['220px', '250px'],
	          shadeClose: true,
	          content:$('#openWindow') //数组第二项即吸附元素选择器或者DOM $('#openWindow')
		 });
	}
	function remove(id){
		layer.confirm('您确定要删除吗?', {title:'提示',offset: ['222px','360px'],shade:0.01}, function(index){
	 			layer.close(index);
	 			$.ajax({
	 				url:"${pageContext.request.contextPath}/firstAudit/remove.html?id="+id,
	 				//data:{"id":id},
	 				//type:"post",
	 	       		success:function(){
	 	       			layer.msg('删除成功',{offset: ['222px', '390px']});
	 		       		window.setTimeout(function(){
	 		       			window.location.reload();
	 		       		}, 500);
	 	       		},
	 	       		error: function(){
	 					layer.msg("删除失败",{offset: ['222px', '390px']});
	 				}
	 	       	});
	 		});
		
	}
	function edit(id){
		  layer.open({
	          type: 2, //page层
	          area: ['700px', '300px'],
	          title: '修改初审项',
	          shade:0.01, //遮罩透明度
	          moveType: 1, //拖拽风格，0是默认，1是传统拖动
	          shift: 1, //0-6的动画形式，-1不开启
	          offset: ['220px', '250px'],
	          closeBtn: 1,
	          content:'${pageContext.request.contextPath}/firstAudit/toEdit.html?id='+id
	        		  //数组第二项即吸附元素选择器或者DOM $('#openWindow')
		 });
	}
	//打开模板窗口列表
	function openTemplat(){
		layer.open({
	          type: 2, //page层
	          area: ['1000px', '500px'],
	          title: '选择模板',
	          shade:0.01, //遮罩透明度
	          moveType: 1, //拖拽风格，0是默认，1是传统拖动
	          shift: 1, //0-6的动画形式，-1不开启
	          offset: ['100px', '200px'],
	          closeBtn: 1,
	          content:'${pageContext.request.contextPath}/firstAudit/toTemplatList.html'
	        		  //数组第二项即吸附元素选择器或者DOM $('#openWindow')
		 });
		
	}
</script>
</head>
<body>
 <div class="container clear margin-top-30" id="package">
   <h1>01、项目分包信息</h1>
	   <c:forEach items="${packageList }" var="pack" varStatus="p">
	   		<span>包名:<span>${pack.name }</span>
	   		<input type="hidden" value="${pack.id }"/>
	   		</span>
	   		
	   		<table class="table table-bordered table-condensed mt5">
        	<thead>
        		<tr class="info">
          			<th class="w50">序号</th>
         			<th>需求部门</th>
			        <th>物资名称</th>
			        <th>规格型号</th>
			        <th>质量技术标准</th>
			        <th>计量单位</th>
			        <th>采购数量</th>
			        <th>单价（元）</th>
			        <th>预算金额（万元）</th>
			        <th>交货期限</th>
			        <th>采购方式建议</th>
			        <th>供应商名称</th>
			        <th>是否申请办理免税</th>
				    <th>物资用途（进口）</th>
				    <th>使用单位（进口）</th>
        		</tr>
        	</thead>
          <c:forEach items="${pack.projectDetails}" var="obj">
            <tr class="tc">
	            <td class="w50">${obj.serialNumber }</td>
	            <td>${obj.department}</td>
	            <td>${obj.goodsName}</td>
	            <td>${obj.stand}</td>
	            <td>${obj.qualitStand}</td>
	            <td>${obj.item}</td>
	            <td>${obj.purchaseCount}</td>
	            <td>${obj.price}</td>
	            <td>${obj.budget}</td>
	            <td>${obj.deliverDate}</td>
	            <td>${obj.purchaseType}</td>
	            <td>${obj.supplier}</td>
	            <td>${obj.isFreeTax}</td>
		        <td>${obj.goodsUse}</td>
		        <td>${obj.useUnit}</td>
            </tr>
         </c:forEach> 
      </table>
	   </c:forEach>
   </div>
<div class="container clear margin-top-30" id="package">
 <h1>02、初审项定义</h1>
  <form action="">
  <input type="button" value="选择初审项模板" onclick="openTemplat();" class="btn btn-windows add"/>
  <input type="button" value="手动添加初审项" onclick="openWindow();" class="btn btn-windows ht_add"/>
    <table class="table table-bordered table-condensed mt5">
    <thead>
      <tr>
        <th>初审项名称</th>
        <th>要求类型</th>
        <th>创建人</th>
        <th>操作</th>
      </tr>
     </thead>
      <c:forEach items="${list }" var="l" varStatus="vs">
      <thead>
       <tr>
        <td align="center">${l.name }</td>
        <td align="center">${l.kind }</td>
        <td align="center">${l.creater }</td>
        <td align="center" width="200px;">
          <input type="button" value="修改" class="btn btn-windows edit" onclick="edit('${l.id}');">
          <input type="button" value="删除" class="btn btn-windows delete" onclick="remove('${l.id}');">
        </td>
      </tr>
      </thead>
      </c:forEach>
    </table>
  </form>
</div>
<!-- 按钮 -->
  	<div class="padding-top-10 clear">
		<div class="col-md-12 pl200 ">
			<div class="mt40 tc mb50">
	    		<input class="btn btn-windows back" value="返回" type="button" onclick="location.href='javascript:history.go(-1);'">
			</div>
	  	</div>
	 </div>	
<div id="openWindow"  style="display: none;">
	<form action="${pageContext.request.contextPath}/firstAudit/add.html" method="post" id="form1">
     <table class="table table-bordered table-condensed">
     <thead>
      <tr>
        <th>初审项名称:</th><td><input type="text" required="true" maxlength="30" name="name" id="name"></td>
        <th>要求类型:</th><td><input type="radio"  name="kind" value="商务" >商务&nbsp;<input type="radio" name="kind" id="kind" value="技术" >技术</td>
      </tr>
      <input name="creater" readonly="readonly" required="true" maxlength="10" id="creater" type="hidden" value="${sessionScope.loginUser.relName}">
      <input type="hidden" name="projectId" id="projectId" value="${projectId }">
     </thead>
    </table>
    <input type="button"  value="添加" onclick="submit1();" class="btn btn-windows add"/>
    <input type="button"  value="取消" onclick="cancel();"  class="btn btn-windows cancel"/>
  </form>
</div>
</body>
</html>