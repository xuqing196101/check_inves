<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="/WEB-INF/view/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>项目分包</title>  
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <script type="text/javascript" src="${ pageContext.request.contextPath }/public/layer/layer.js"></script>
	<script type="text/javascript" src="${ pageContext.request.contextPath }/public/layer/extend/layer.ext.js"></script>
	<script src="<%=basePath%>public/laypage-v1.3/laypage/laypage.js" type="text/javascript"></script>
	<link href="${ pageContext.request.contextPath }/public/layer/skin/layer.css" rel="stylesheet" type="text/css" />
	<link href="${ pageContext.request.contextPath }/public/layer/skin/layer.ext.css" rel="stylesheet" type="text/css" />
  	<script type="text/javascript">
		//全选方法
		function selectAll(){
			var info = document.getElementsByName("info");
			var selectAll = document.getElementById("selectAll");
			if(selectAll.checked){
				for(var i = 0;i<info.length;i++){
					info[i].checked = true;
				}
			}else{
				for(var i = 0;i<info.length;i++){
					info[i].checked = false;
				}
			}
		}
    
    //分包
    function check(){
    	var count = 0;
		var ids = "";
		var info = document.getElementsByName("info");
		for(var i = 0;i<info.length;i++){
			if(info[i].checked == true){
				count++;
			}
		}
		if(count == 0){
			layer.alert("请选择删除内容",{offset: ['222px', '390px']});
			$(".layui-layer-shade").remove();
			return;
		}
		for(var i=0;i < info.length;i++){    
	        if(info[i].checked){    
	        	ids += info[i].value+',';
	        }
		}
		layer.confirm('您确定要删除吗?', {title:'提示',offset: ['222px','360px'],shade:0.01}, function(index){
			layer.close(index);
			$.ajax({
				type:"POST",
				dataType:"json",
				url:"<%=path%>/expertExam/deleteById.html?ids="+ids,
		       	success:function(data){
		       		layer.msg('删除成功',{offset: ['222px', '390px']});
			       	window.setTimeout(function(){
			       		window.location.href="<%=path%>/expertExam/searchLawExpPool.html";
			       	}, 1000);
		       	}
	       	});
		});
    }
  </script>
  </head>
  
  <body>
	<!--面包屑导航开始-->
	 <div class="margin-top-10 breadcrumbs ">
	      <div class="container">
	           <ul class="breadcrumb margin-left-0">
	           </ul>
	        <div class="clear"></div>
	      </div>
	   </div>

	<div class="container">
    	<span>项目名称：${project.name}</span>
     	<span>项目编号：${project.projectNumber}</span> 
   	</div>   
     
     <div class="container">
	   <div class="headline-v2">
	      <h2>明细列表</h2>
	   </div>
	 </div>
	  
   <div class="container clear margin-top-30">
        <table class="table table-bordered table-condensed mt5">
        	<thead>
        		<tr class="info">
        		<th class="w30"><input type="checkbox" id="selectAll" onclick="selectAll()"  alt=""></th>
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
			        <c:if test="${project.isEntrance==1 }">
			        	<th>是否申请办理免税</th>
				        <th>物资用途（进口）</th>
				        <th>使用单位（进口）</th>
			        </c:if>
        		</tr>
        	</thead>
          <c:forEach items="${list}" var="obj" varStatus="vs">
            <tr class="tc pointer">
            	<td class="w30"><input type="checkbox" value="${obj.id }" name="info"></td>
	            <td class="w50">${obj.seq}</td>
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
	            <c:if test="${project.isEntrance==1 }">
	            	<td>${obj.isFreeTax}</td>
		            <td>${obj.goodsUse}</td>
		            <td>${obj.useUnit}</td>
	            </c:if>
            </tr>
         </c:forEach> 
      </table>
   </div>
   		<!-- 按钮 -->
  		<div class="padding-top-10 clear">
			<div class="col-md-12 pl200 ">
				<div class="mt40 tc mb50">
				 	<button class="btn btn-windows pl13" type="button" onclick="pack()">分包</button>
	    			<input class="btn btn-windows pl13" value="下一步" type="button">
				</div>
	  		</div>
	  	</div>
  </body>
</html>
