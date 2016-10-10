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
  		$(function(){
  			$("#packages").hide();
  			var sure = document.getElementsByName("sure");
  			for(var i=0;i<sure.length;i++){
  				$(sure[i]).hide();
  			}
  		});
  		
  		
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
    
    //添加分包
    function addPackage(){
    	var count = 0;
		var ids = "";
		var info = document.getElementsByName("info");
		for(var i = 0;i<info.length;i++){
			if(info[i].checked == true){
				count++;
			}
		}
		if(count == 0){
			layer.alert("请选择明细",{offset: ['222px', '390px']});
			$(".layui-layer-shade").remove();
			return;
		}
		for(var i=0;i < info.length;i++){    
	        if(info[i].checked){    
	        	ids += info[i].value+',';
	        }
		}
		$.ajax({
			type:"POST",
			dataType:"json",
			url:"<%=path%>/project/addPackage.do?id="+ids,
	       	success:function(data){
	       		if(data){
	       			var html = "";
	       			html = html+"<span>包名:<span>分包</span><input class='btn btn-windows pl13' type='button' onclick='edit(this)' value='修改包名'/>"+
	       			"<input class='btn btn-windows pl13' name='sure' type='button' onclick='sure(this)' value='确定'/>"+
	       			"<input class='btn btn-windows pl13' type='button' onclick='deletePackage(this)' value='删除分包'>"+
	    			"</span><table class='table table-bordered table-condensed mt5'><thead><tr class='info'>"+
	    			"<th class='w50'>序号</th><th>需求部门</th><th>物资名称</th><th>规格型号</th><th>质量技术标准</th>"+
	    			"<th>计量单位</th><th>采购数量</th><th>单价（元）</th><th>预算金额（万元）</th><th>交货期限</th>"+
	    			"<th>采购方式建议</th><th>供应商名称</th><th>是否申请办理免税</th><th>物资用途（进口）</th><th>使用单位（进口）</th>"+
	    	        "</tr></thead>";
					for(var i=0;i<data.length;i++){
	       				html = html + "<tr class='tc'>";
		            	html = html + "<td>"+data[i].seq+"</td>"
		            	html = html + "<td>"+data[i].department+"</td>";
		            	html = html + "<td>"+data[i].goodsName+"</td>";
		            	html = html + "<td>"+data[i].stand+"</td>";
		            	html = html + "<td>"+data[i].qualitStand+"</td>";
		            	html = html + "<td>"+data[i].item+"</td>"
		            	html = html + "<td>"+data[i].purchaseCount+"</td>";
		            	html = html + "<td>"+data[i].price+"</td>";
		            	html = html + "<td>"+data[i].budget+"</td>";
		            	html = html + "<td>"+data[i].deliverDate+"</td>";
		            	html = html + "<td>"+data[i].purchaseType+"</td>";
		            	html = html + "<td>"+data[i].supplier+"</td>";
		            	html = html + "<td>"+data[i].isFreeTax+"</td>";
		            	html = html + "<td>"+data[i].goodsUse+"</td>";
		            	html = html + "<td>"+data[i].useUnit+"</td>";
		            	html = html + "</tr>";
	       			}
	          		html = html+"</table>";
	       			$("#package").append(html);
	       		}
	       	}
       	});
    }
    
    //选中效果
    function selectedBox(obj){
    	//var info = document.getElementsByName("info");
    	//var obj = $(obj).val();
    	//if($(obj).is(':checked')){
        	//$.ajax({
    			//type:"POST",
    			//dataType:"json",
    			//url:"<%=path%>/project/select.do?id="+obj,
    	       	//success:function(data){
    	       	//if(data){
    	       			//for(var i=0;i<data.length;i++){
    	       				//for(var j=0;j<info.length;j++){
    	       					//if(data[i].id==$(info[j]).val()){
    	       						//$(info[j]).attr("checked",true);
    	       						//break;
    	       					//}
    	       				//}
    	       			//}
    	       		//}
    	       	//}
           	//});
    	//}
    }
    
    //修改包名
    function edit(obj){
    	var name = $(obj).prev().html();
    	var content = "<input type='text' id='pack' value='"+name+"'/>";
    	$(obj).prev().html(content);
    	$("#pack").focus();
    	$(obj).next().show();
    	$(obj).hide();
    }
    
    //确定按钮
    function sure(obj){
    	var name = $("#pack").val();
    	$(obj).prev().prev().html(name);
    	$(obj).prev().show();
    	$(obj).hide();
    }
    
    //删除包
    function deletePackage(obj){
    	
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
     
     <!-- 按钮开始-->
   	<div class="container">
   		<div class="col-md-12">
		    <button class="btn btn-windows pl13" type="button" onclick="addPackage()">添加分包</button>
		    
		</div>
    </div>
     
     <div class="container">
	   <div class="headline-v2">
	      <h2>明细列表</h2>
	   </div>
	 </div>
	  
   <div class="container clear margin-top-30" id="package">
	   <c:forEach items="${packageList }" var="pack" varStatus="p">
	   		<span>包名:<span>${pack.name }</span>
	   		<input class="btn btn-windows pl13" type="button" onclick="edit(this)" value="修改包名"/>
	   		<input class="btn btn-windows pl13" name="sure" type="button" onclick="sure(this)" value="确定"/>
	   		<input class="btn btn-windows pl13" type="button" onclick="deletePackage(this)" value="删除分包"/>
	   		<input type="hidden" value="${pack.id }"/>
	   		</span>
	   		
	   		<table class="table table-bordered table-condensed mt5">
        	<thead>
        		<tr class="info">
        		<th class="w30"><input type="checkbox" id="selectAll" onclick="selectAll()"></th>
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
            	<td class="w30"><input type="checkbox" value="${obj.id }" name="info" onclick="selectedBox(this)"></td>
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
   	
   	<!-- 按钮 -->
  	<div class="padding-top-10 clear">
		<div class="col-md-12 pl200 ">
			<div class="mt40 tc mb50">
	    		<input class="btn btn-windows back" value="返回" type="button" onclick="location.href='javascript:history.go(-1);'">
			</div>
	  	</div>
	 </div>	
  </body>
</html>
