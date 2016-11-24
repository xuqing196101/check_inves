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
  	<script type="text/javascript">
  		$(function(){
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
   
    	//勾选明细
    	function selectedBox(ele){
    		var projectId = $("#projectId").val();
	    	var flag = $(ele).prop("checked");
	        //var purchaseType = $("input[name='info']:checked").parents("tr").find("td").eq(11).text();
           // purchaseType = $.trim(purchaseType);
		    var id = $(ele).val();
		    var count = 0;
		    var len = 0;
			var info = document.getElementsByName("info");
			var selectAll = document.getElementById("selectAll");
			for(var i=0;i<info.length;i++){
				if(info[i].checked == false){
					selectAll.checked = false;
					break;
				}
			}
			for(var i=0;i<info.length;i++){
				var dis = $(info[i]).prop("disabled");
				if(!dis){
					if(info[i].checked == true){
						count++;
					}
					len++;
				}
			}
			if(count==len){
				selectAll.checked = true;
			}
		    $.ajax({
                   url:"${pageContext.request.contextPath }/project/checkProjectDeail.do?id="+id+"&projectId="+projectId,
                   type:"post",
                   dataType:"json",
                   success:function(result){
                      for (var i=0;i<result.length;i++) {
                          $("input[name='info']").each(function() {
                               var v1 = result[i].id;
	                           var v2 = $(this).val();
	                           if (v1 == v2) {
	                              $(this).prop("checked", flag);
	                           }
                          });
                      }
                   }
               });
   		 }
    
    //修改包名
    function edit(obj){
    	var name = $(obj).parent().prev().find($("span[name='packageName']")).html();
    	var packageId = $(obj).next().next().next().val();
    	var content = "<input type='text' name='pack' value='"+name+"'/>";
    	$(obj).parent().prev().find($("span[name='packageName']")).html(content);
    	$(obj).parent().prev().find($("span[name='packageName']")).find($("input[name='pack']")).focus();
    	$(obj).next().show();
    	$(obj).hide();
    }
    
    //确定按钮
    function sure(obj){
    	var projectId = $("#projectId").val();
    	var name = $(obj).parent().prev().find($("span[name='packageName']")).find($("input[name='pack']")).val();
    	var packageId = $(obj).next().next().val();
    	$.ajax({
            url:"${pageContext.request.contextPath }/project/editPackName.do?name="+name+"&id="+packageId,
            type:"post",
            dataType:"json",
            success:function(data){
            	layer.msg('修改成功',{offset: ['70%', '40%']});
				window.setTimeout(function(){
				    window.location.href="${pageContext.request.contextPath }/project/subPackage.do?id="+projectId;
				}, 1000);
		   	}
        });
    }
    
    //删除包
    function deletePackage(obj){
    	var projectId = $("#projectId").val();
    	var packageId = $(obj).next().val();
    	layer.confirm('您确定要删除这个包吗?', {title:'提示',offset: ['70%', '40%'],shade:0.01}, function(index){
			layer.close(index);
			$.ajax({
				type:"POST",
				dataType:"json",
				url:"${pageContext.request.contextPath }/project/deletePackageById.do?id="+packageId,
		       	success:function(data){
		       		layer.msg('删除成功',{offset: ['70%', '40%']});
			       	window.setTimeout(function(){
			       	 	window.location.href="${pageContext.request.contextPath }/project/subPackage.do?id="+projectId;
			       	}, 1000);
		       	}
	       	});
		});
    }
    
    //添加分包
    function addPack(){
    	var projectId = $("#projectId").val();
    	var count = 0;
		var ids = "";
		var info = document.getElementsByName("info");
		for(var i = 0;i<info.length;i++){
			if(info[i].checked == true){
				count++;
			}
		}
		if(count == 0){
			layer.alert("请选择明细",{offset: ['20%', '40%']});
			$(".layui-layer-shade").remove();
			return;
		}
		for(var i=0;i<info.length;i++){    
	        if(info[i].checked){  
	        	if($(info[i]).attr("disabled")){
	        		
	        	}else{
	        		ids += info[i].value+',';
	        	}
	        }
		}
		for(var i=0;i<info.length;i++){ 
			if($(info[i]).prop("disabled")==false){
				break;
			}else if(i==info.length-1){
				layer.alert("项目中已经没有明细可以用于分包",{offset: ['30%', '40%']});
				$(".layui-layer-shade").remove();
				return;
			}
		}
		$.ajax({
			type:"POST",
			dataType:"json",
			url:"${pageContext.request.contextPath }/project/addPack.do?id="+ids+"&projectId="+projectId,
		    success:function(data){
		    	layer.msg('添加成功',{offset: ['40%', '45%']});
				window.setTimeout(function(){
				    window.location.href="${pageContext.request.contextPath }/project/subPackage.do?id="+projectId;
				}, 1000);
		    }
	    });
    }
    
    //返回
    function back(){
    	window.location.href = "${pageContext.request.contextPath }/project/list.html";
    }
  </script>
  </head>
  
  <body>
	<!--面包屑导航开始-->
	<div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#">首页</a></li><li><a href="#">保障作业</a></li><li><a href="#">采购项目管理</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>

	<div class="container">
		<h2 class="tc dangan_file">项目名称：${project.name}</h2>
     	<input type="hidden" id="projectId" value="${project.id }"/>
		<div class="headline-v2">
		   <h2>明细列表</h2>
		</div>
     	
    	<!-- 按钮开始-->
   		<div class="col-md-12 pl20 mt10">
		    <button class="btn btn-windows add" type="button" onclick="addPack()">添加分包</button>
		    <span class="fr mt10">项目编号：${project.projectNumber}</span>
    	</div>
	 
	  <div class="content table_box" id="package">
	  	<table class="table table-bordered table-condensed table-hover">
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
			        <th>采购方式</th>
			        <th>供应商名称</th>
			        <c:if test="${project.isImport==1 }">
				        <th>是否申请办理免税</th>
					    <th>物资用途（进口）</th>
					    <th>使用单位（进口）</th>
			        </c:if>
	  			</tr>
	  		</thead>
	  		<c:forEach items="${lists}" var="obj">
	            <tr class="tc">
		            <c:choose>
		            	<c:when test="${obj.packages.id==null||obj.packages.id=='' }">
		            		<td class="w30"><input type="checkbox" value="${obj.id }" name="info" onclick="selectedBox(this)"></td>
		            	</c:when>
		            	<c:otherwise>
		            		<td class="w30"><input type="checkbox" value="${obj.id }" name="info" disabled="disabled"></td>
		            	</c:otherwise>
		            </c:choose>
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
		            <c:if test="${project.isImport==1 }">
			            <td>${obj.isFreeTax}</td>
				        <td>${obj.goodsUse}</td>
				        <td>${obj.useUnit}</td>
			        </c:if>
	            </tr>
         	</c:forEach> 
         	</table>	
	  </div>
	  
	  
   	<div class="content table_box">
	   	<c:forEach items="${packageList }" var="pack" varStatus="p">
	   		<div class="col-md-6 col-sm-6 col-xs-12 p0">
	   		  	<span class="f16 b">包名:</span>
	   		  	<span class="f14 blue" name="packageName">${pack.name }</span>
	   		</div>
	   		<div class="col-md-6 col-sm-6 col-xs-12 tr p0 mb5">
	   		    <input class="btn btn-windows edit" type="button" onclick="edit(this)" value="修改包名"/>
	   			<input class="btn" name="sure" type="button" onclick="sure(this)" value="确定"/>
	   			<input class="btn btn-windows delete" type="button" onclick="deletePackage(this)" value="删除分包"/>
	   			<input type="hidden" value="${pack.id }"/>
	   		</div>
	   		
	   		<table class="table table-bordered table-condensed table-hover">
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
			        <c:if test="${pack.isImport==1 }">
			        	<th>是否申请办理免税</th>
					    <th>物资用途（进口）</th>
					    <th>使用单位（进口）</th>
			        </c:if>
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
	            <c:if test="${pack.isImport==1 }">
		            <td>${obj.isFreeTax}</td>
			        <td>${obj.goodsUse}</td>
			        <td>${obj.useUnit}</td>
		        </c:if>
            </tr>
         </c:forEach> 
      </table>
	   </c:forEach>
        
   </div>
   	<!-- 按钮 -->
  	<div class="col-md-12 mt10 tc">
	    <input class="btn btn-windows back" value="返回" type="button" onclick="back()">
	</div>	
  </body>
</html>
