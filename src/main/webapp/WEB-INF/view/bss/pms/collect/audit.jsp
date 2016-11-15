<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/tld/upload" prefix="u"%>
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



<jsp:include page="/WEB-INF/view/common.jsp"/> 

<c:set var="contextPath" value="${pageContext.request.contextPath}" scope="application"/> 
<script>
	var globalPath = "${contextPath}";
</script>

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
  	 
  	 
	
 	function sets(){
			 var id=$("#cid").val(); 
 		
			window.location.href="<%=basePath%>set/list.html?id="+id;
	  
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
 /* 	function ss(obj){
   	   var val=$(obj).val();
   	   $("select option").each(function(){
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
				<li><a href="#">障碍作业系统</a></li>
				<li><a href="#">采购计划管理</a></li>
				<li class="active"><a href="#">采购计划审核</a></li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>
	<div class="container">
		<div class="headline-v2 fl">
			<h2>计划明细</h2>
		</div>
		<div class="container clear margin-top-30">
	<button class="btn padding-left-10 padding-right-10 btn_back" onclick="sets()">调整审核人员</button>
		<a class="btn padding-left-10 padding-right-10 btn_back" href="<%=basePath%>look/report.html?id=${id}">生成评审报告页面pdf</a>
		
			<form action="${pageContext.request.contextPath}/look/audit.html" method="post">
				<table class="table table-bordered table-condensed mt5">
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
							<td class="tc w50"><input style="border: 0px;" type="text" name="list[${vs.index }].seq" value="${obj.seq }"><input style="border: 0px;" type="hidden" name="list[${vs.index }].id" value="${obj.id }">
							</td>
							<td><input style="border: 0px;" type="text" name="list[${vs.index }].department" value="${obj.department }"></td>
							<td><input style="border: 0px;" type="text" name="list[${vs.index }].goodsName" value="${obj.goodsName }"></td>
							<td class="tc"><input style="border: 0px;" type="text" name="list[${vs.index }].stand" value="${obj.stand }"></td>
							<td class="tc"><input style="border: 0px;" type="text" name="list[${vs.index }].qualitStand" value="${obj.qualitStand }"></td>
							<td class="tc"><input style="border: 0px;" type="text" name="list[${vs.index }].item" value="${obj.item }"></td>
							<td class="tc"><input style="border: 0px;" type="text" name="list[${vs.index }].purchaseCount" value="${obj.purchaseCount }"></td>
							<td class="tc"><input style="border: 0px;" type="text" name="list[${vs.index }].price" value="${obj.price }"></td>
							<td class="tc"><input style="border: 0px;" type="text" name="list[${vs.index }].budget" value="${obj.budget }"></td>
							<td><input style="border: 0px;" type="text" name="list[${vs.index }].deliverDate" value="${obj.deliverDate }"></td>
							<td>
							
							<select  onchange="sel(this)" name="list[${vs.index }].purchaseType">
							<option value="gkzb">公开招标</option>
							<option value="yqzb">邀请招标</option>
							<option value="dzjp">电子反拍</option>
							<option value="jzxtp">竞争性谈判</option>
							<option value="dyly" <c:if test="${'dyly'==obj.purchaseType}">selected="selected"</c:if>>单一来源</option>
							</select>
							</td>
							<td class="tc">
							<select class="org"  onchange="org(this)"  name="list[${vs.index }].organization">
								<c:forEach items="${org }" var="ss">
								  <option value="${ss.name }"  <c:if test="${ss.name==obj.organization }">selected="selected" </c:if>  >${ss.name}</option>
								</c:forEach>
								
							</td>
							
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
							</td>
								<c:forEach items="${all }" var="al" varStatus="avs">
										<td class="tc">
									 	<c:if test="${al.param=='1' }">
									 		<input type="hidden" name="audit[${vs.index*5+avs.index }].purchaseId" value="${obj.id }">
									 	<%-- 	<c:forEach items="${bean }"  var="s">
									 			<c:if test=""> --%>
									 			<input type="hidden" name="audit[${vs.index*5+avs.index }].auditParamId" value="${al.id }">
									 	<%-- 		</c:if>
									 		</c:forEach> --%>
									 		<select onchange="ss(this)" name="audit[${vs.index*5+avs.index}].paramValue">
												<option value="gkzb">公开招标</option>
												<option value="yqzb">邀请招标</option>
												<option value="dyly">单一来源</option>
												<option value="jzxtp">竞争性谈判</option>
												<option value="xjcg">询价采购</option>
											</select>
									 	</c:if>
									  
									  <c:if test="${al.param=='2' }">
									  	<input type="hidden" name="audit[${vs.index*5+avs.index }].purchaseId" value="${obj.id }">
									  	<input type="hidden" name="audit[${vs.index*5+avs.index }].auditParamId" value="${al.id }">
									 		<select onchange="ss(this)" name="audit[${vs.index*5+avs.index }].paramValue">
											<c:forEach items="${org }" var="ss">
											  <option value="${ss.name }" >${ss.name}</option>
											</c:forEach>
											</select>
									 	</c:if>
									 	<c:if test="${al.param=='3' or al.param=='4' }">
									 		<input type="hidden" name="audit[${vs.index*5+avs.index }].purchaseId" value="${obj.id }">
									  	<input type="hidden" name="audit[${vs.index*5+avs.index }].auditParamId" value="${al.id }">
									 		 <input type="text" name="audit[${vs.index*5+avs.index }].paramValue" value="">
									 	</c:if>
										</td>
								</c:forEach>
						</tr>

					</c:forEach>
					
				<%-- 	<tr>
					<td class="tc" colspan="16"> <input type="hidden" name="type" value="${fn:length(list)}"> <input class="btn btn-windows add" name="dyadds" type="button" onclick="aadd()" value="添加"></td>
				</tr> --%>
				
				</table>
				<div style="float: left">
				<u:upload id="cgjh" groups="cgjh,audit" businessId="${id }" sysKey="2" typeId="${aid }"/>
				<u:show showId="cgjh" groups="cgjh,audit" businessId="${id }" sysKey="2" typeId="${aid }"/>
				</div>
				<input class="btn btn-windows save" type="submit" value="提交">
				<input class="btn btn-windows reset" value="返回" type="button" onclick="location.href='javascript:history.go(-1);'">
			</form>
		</div>
	</div>

</body>
</html>
