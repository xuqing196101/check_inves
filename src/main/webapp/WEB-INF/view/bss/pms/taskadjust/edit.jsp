<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/tld/upload" prefix="u"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<jsp:include page="/WEB-INF/view/common.jsp"/> 
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
  		
  		
  		window.location.href="${pageContext.request.contextPath}/purchaser/queryByNo.html?planNo="+no;
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
					url:"${pageContext.request.contextPath}/adjust/filed.html",
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
		var file=$("#required").val();
		if(file==""||file==null){
			 layer.tips("文件不允许为空","#required");
		}
		else if(flag==true){
			$("#adjust").submit();
		}
	}
	
	
	 function sum2(obj){  //数量
	        var purchaseCount = $(obj).val()-0;//数量
	        var price2 = $(obj).parent().next().children(":last").prev();//价钱
	        var price = $(price2).val()-0;
	        var sum = purchaseCount*price;
	        var budget = $(obj).parent().next().next().children(":last").prev();
	        $(budget).val(sum);
	      	var id=$(obj).next().val();
	      	aa(id);
	    } 
	    
	       function sum1(obj){
	        var purchaseCount = $(obj).val()-0; //价钱
	         var price2 = $(obj).parent().prev().children(":last").prev().val()-0;//数量
	      	 var sum = purchaseCount*price2;
	         $(obj).parent().next().children(":last").prev().val(sum);
		     	var id=$(obj).next().val();
		     	aa(id);
	    }
	
	       function aa(id){// id是指当前的父级parentid
	    	
	    	   var budget=0;
	    	   $("#table tr").each(function(){
	 	    		var cid= $(this).find("td:eq(8)").children(":last").val();
	 	    		var same= $(this).find("td:eq(8)").children(":last").prev().val()-0;
		 	       if(id==cid){
		 	    	   
		 	    	  budget=budget+same; //查出所有的子节点的值
		 	       }
	    	   });
	    	    $("#table tr").each(function(){
	    		var pid= $(this).find("td:eq(8)").children(":first").val();//上级id
	    		
	    		if(id==pid){
	    			$(this).find("td:eq(8)").children(":first").next().val(budget);
	    		}
	    	  	 $("#table tr").each(function(){
	 	    		var cid= $(this).find("td:eq(8)").children(":last").val();
	 	    		  if(pid==cid&&id!=pid){
	 	    		
	 	    			 $(this).find("td:eq(8)").children(":first").next().val(budget);
	 	    		  }
	 	    		});  
	    		});  
	    	 //  var did=$("#table tr:eq(1) td:eq(8)").children(":first").val();
	    	  // var did=$("table:eq(1) tr:eq(1) td:eq(1) input:eq(0)").val();
	    	  var did=$("table tr:eq(2)").find("td:eq(8)").children(":first").val();
	    	    var total=0;
	    	    $("#table tr").each(function(){
	    	    	
	 	    		var cid= $(this).find("td:eq(8)").children(":last").val();
	 	    		var same= $(this).find("td:eq(8)").children(":last").prev().val()-0;
	 	    		 if(did==cid){
	 	    			
	 	    			total=total+same;
	 	    		 }
	    	   }); 
	    	
	    	    $("table tr:eq(2)").find("td:eq(8)").children(":first").next().val(total);
	       }
	       
	       
	       function sel(obj){
	    	   var val=$(obj).val();
	    	   $("select option").each(function(){
	    		   var opt=$(this).val();
	    		   if(val==opt){
	    			   $(this).attr("selected", "selected");  
	    		   }
	    	   });
	       }  
	       
	/*        function ss(obj){
	    	   var val=$(obj).val();
	    	   $(obj).find().each(function(){
	    		   var opt=$(this).val();
	    		   if(val==opt){
	    			   $(this).attr("selected", "selected");  
	    		   }
	    	   });
	       } */
	      
	       function org(obj){
	    		 
	    	   var val=$(obj).val();
	    
	    	   $(".org option").each(function(){
	    	 
	    		   var opt=$(this).val();
	    		   if(val==opt){
	    			   $(this).attr("selected", "selected");  
	    		   }
	    	   });
	       }
	       
	       
</script>
</head>

<body>
	<!--面包屑导航开始-->
	<div class="margin-top-10 breadcrumbs ">
		<div class="container">
			<ul class="breadcrumb margin-left-0">
				<li><a href="#"> 首页</a></li>
				<li><a href="#">保障作业系统</a></li>
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
		<div class="col-md-12 pl20 mt10">
		  <div style="float: left">
                <u:upload id="cgjh" businessId="${id }" sysKey="2" typeId="${aid }"/>
                <u:show showId="cgjh"   businessId="${id }" sysKey="2" typeId="${aid }"/>
                </div>
                <!-- <div class=""><a class="upload">上传附件</a><input id="required" type="file" name="file"> </div> -->
                <input class="btn btn-windows save"  type="button" value="提交" onclick="sub()">
                <input class="btn btn-windows back" value="返回" type="button" onclick="location.href='javascript:history.go(-1);'">
		</div>
		 <div class="content table_box">

			<form id="adjust" action="${pageContext.request.contextPath}/adjust/update.html" method="post" enctype="multipart/form-data">
				<table id="table" class="table table-bordered table-condensed mt5">
					<thead>
						<tr>
						<th class="info" colspan="17">事业部门需求</th>
						
						
							<c:forEach items="${bean }" var="obj">
								<th class="info" colspan="${obj.size}q">${obj.name }</th>
							</c:forEach>
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
							<th class="info">采购机构</th>
							<th class="info">供应商名称</th>
							<th class="info">是否申请办理免税</th>
							<th class="info">物资用途（仅进口）</th>
							<th class="info">使用单位（仅进口）</th>
							<th class="info">备注</th>
							
					<c:forEach items="${all }" var="p">
											<th class="info">
											  <c:if test="${p.param=='1'}">
												  	采购方式
												  </c:if>
												   <c:if test="${p.param=='2'}">
												  	采购机构
												  </c:if>
												
												   <c:if test="${p.param=='3'}">
												     	其他建议
														
												  </c:if>
												    <c:if test="${p.param=='4'}">
													 技术参数意见
										  </c:if>
					  
					  
											</th>
							</c:forEach>
						</tr>
					</thead>

					<c:forEach items="${list }" var="obj" varStatus="vs">
						<tr>
							<td class="tc w50"><input style="border: 0px;" readonly="readonly" type="text" name="list[${vs.index }].seq" value="${obj.seq }"><input style="border: 0px;" type="hidden" name="list[${vs.index }].id" value="${obj.id }">
							</td>
							<td><input style="border: 0px;" readonly="readonly" type="text" name="list[${vs.index }].department" onblur="checks(this)" value="${obj.department }"></td>
							<td><input type="text" name="list[${vs.index }].goodsName"  value="${obj.goodsName }"></td>
							<td class="tc"><input style="border: 0px;" readonly="readonly" type="text" name="list[${vs.index }].stand" value="${obj.stand }"></td>
							<td class="tc"><input  type="text" name="list[${vs.index }].qualitStand" value="${obj.qualitStand }"></td>
							<td class="tc"><input  type="text" name="list[${vs.index }].item" value="${obj.item }"></td>
							<td class="tc">
							<input   type="hidden" name="ss"   value="${obj.id }">
							<input  onblur="sum2(this)"  type="text" name="list[${vs.index }].purchaseCount" onblur="checks(this)"  value="${obj.purchaseCount }">
							<input type="hidden" name="ss"   value="${obj.parentId }">
							</td>
							<td class="tc">
							<input   type="hidden" name="ss"   value="${obj.id }">
							<input onblur="sum1(this)"  type="text" name="list[${vs.index }].price" value="${obj.price }">
							<input type="hidden" name="ss"   value="${obj.parentId }">
							</td>
							<td class="tc">
							<input type="hidden" name="ss"    value="${obj.id}">
							<input   type="text" name="list[${vs.index }].budget" onblur="checks(this)"  value="${obj.budget }">
							<input type="hidden" name="ss"  value="${obj.parentId }">
							</td>
							
							<td><input type="text" name="list[${vs.index }].deliverDate" onblur="checks(this)" value="${obj.deliverDate }"></td>
							<td>
							 <select name="list[${vs.index }].purchaseType" onchange="sel(this)" style="width:100px" id="select">
              				    <option value="" >请选择</option>
	                            <c:forEach items="${dicType }" var="mt">
								  <option value="${mt.id }"<c:if test="${mt.id==obj.purchaseType }"> selected="selected"</c:if> >${mt.name}</option>
								</c:forEach>
								
			                </select>
							
							</td>
							<td class="tc">
<%-- 							<input type="hidden" name="list[${vs.index }].organization" value="${obj.organization }">
 --%>							<select class="org"  onchange="org(this)"  name="list[${vs.index }].organization">
	 								<option value="">请选择</option>
									<c:forEach items="${org }" var="ss">
									<c:if test="${obj.organization==ss.name }">
									<option value="${ss.name }" selected="selected">${ss.name}</option>
									</c:if>
									<c:if test="${obj.organization!=ss.name }">
									<option value="${ss.name }" <c:if test="${ss.name==obj.organization }">selected="selected" </c:if>  >${ss.name}</option>
									</c:if>
								</c:forEach>
							</select>
							
							</td>
							
							<td class="tc"><input style="border: 0px;" readonly="readonly" type="text" name="list[${vs.index }].supplier" value="${obj.supplier }"></td>
							<td class="tc"><input style="border: 0px;" readonly="readonly" type="text" name="list[${vs.index }].isFreeTax" value="${obj.isFreeTax }"></td>
							<td class="tc"><input style="border: 0px;" readonly="readonly" type="text" name="list[${vs.index }].goodsUse" value="${obj.goodsUse }"></td>
							<td class="tc"><input  style="border: 0px;" readonly="readonly" type="text" name="list[${vs.index }].useUnit" value="${obj.useUnit }"></td>
							<td class="tc"><input style="border: 0px;" readonly="readonly" type="text" name="list[${vs.index }].memo" value="${obj.memo }">
							<input type="hidden" name="list[${vs.index }].planName" value="${obj.planName }">
							<input type="hidden" name="list[${vs.index }].planNo" value="${obj.planNo }">
							<input type="hidden" name="list[${vs.index }].planType" value="${obj.planType }">
							<input type="hidden" name="list[${vs.index }].parentId" value="${obj.parentId }">
							<input type="hidden" name="list[${vs.index }].historyStatus" value="${obj.historyStatus }">
							<input type="hidden" name="list[${vs.index }].goodsType" value="${obj.goodsType }">
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
						<c:forEach items="${all }" var="al" varStatus="avs">
										<td class="tc">
											<c:forEach items="${audits }" var="as">
										<c:if test="${as.purchaseId==obj.id and as.auditParamId==al.id }">
									 	<c:if test="${al.param=='1' }">
									 	
									 		<input type="hidden" name="audit[${vs.index*5+avs.index }].purchaseId" value="${obj.id }">
									 			<input type="hidden" name="audit[${vs.index*5+avs.index }].auditParamId" value="${al.id }">
									 			<select onchange="ss(this)" name="audit[${vs.index*5+avs.index}].paramValue">
												<option value="gkzb" <c:if test="${as.paramValue =='公开招标' }">  selected="selected" </c:if> >公开招标</option>
												<option value="yqzb" <c:if test="${as.paramValue =='邀请招标' }">  selected="selected" </c:if> >邀请招标</option>
												<option value="dyly" <c:if test="${as.paramValue =='单一来源'  }">  selected="selected" </c:if> >单一来源</option>
												<option value="jzxtp" <c:if test="${as.paramValue =='竞争性谈判' }">  selected="selected" </c:if> >竞争性谈判</option>
												<option value="xj" <c:if test="${as.paramValue =='询价' }">  selected="selected" </c:if> >询价</option>
											</select>
									 	</c:if>
									  
									  <c:if test="${al.param=='2' }">
									  	<input type="hidden" name="audit[${vs.index*5+avs.index }].purchaseId" value="${obj.id }">
									  	<input type="hidden" name="audit[${vs.index*5+avs.index }].auditParamId" value="${al.id }">
									 		<select onchange="ss(this)"  name="audit[${vs.index*5+avs.index }].paramValue">
											<c:forEach items="${org }" var="ss">
											  <option value="${ss.name }" <c:if test="${as.paramValue ==ss.name }">  selected="selected" </c:if> >${ss.name}</option>
											</c:forEach>
											</select>
									 	</c:if>
									 	<c:if test="${al.param=='3' or al.param=='4' }">
									 		<input type="hidden" name="audit[${vs.index*5+avs.index }].purchaseId" value="${obj.id }">
									  	<input type="hidden" name="audit[${vs.index*5+avs.index }].auditParamId" value="${al.id }">
									 		 <input type="text" name="audit[${vs.index*5+avs.index }].paramValue" value="${as.paramValue }">
									 	</c:if>
									 	
									 		</c:if>
								 </c:forEach>
										</td>
								</c:forEach>
								
								
							
						</tr>

					</c:forEach>
					<%-- 
					<tr>

					<td class="tc" colspan="16"> <input type="hidden" name="type" value="${fn:length(list)}"> <input class="btn btn-windows add" name="dyadds" type="button" onclick="aadd()" value="添加"></td>
				</tr> --%>
				
				</table>
				
			</form>
		</div>
	<!-- 	<input class="btn btn-windows save"  type="button" value="提交" onclick="sub()">
				<input class="btn btn-windows reset" value="返回" type="button" onclick="location.href='javascript:history.go(-1);'"> -->
	</div>
	
	<input type="hidden" id="pNo" name="" value="${planNo }">
</body>
</html>
