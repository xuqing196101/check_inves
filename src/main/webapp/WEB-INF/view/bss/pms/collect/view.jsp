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
	 function returns(){
		
		 var value=$("#reson").val();
		 if(value!=null){
			 $("#status").val(3);
			 $("#acc_form").submit();
		 }else{
				layer.tips("计划名称不允许为空","#reson");
		 }
		 
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
	  
 function ss(){
	 var value=$("#reson").val();

	 if(value!=null&&value!=""){
		 $("#status").val(3);
		 $("#acc_form").submit();
	 }else{
			layer.tips("退回理由不允许为空","#reson");
	 }
 }
 
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
		<div class="container clear margin-top-30">

			<form id="acc_form" action="${pageContext.request.contextPath}/accept/update.html" method="post">
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
							<th class="info">采购机构</th>
							<th class="info">供应商名称</th>
							<th class="info">是否申请办理免税</th>
							<th class="info">物资用途（仅进口）</th>
							<th class="info">使用单位（仅进口）</th>
							<th class="info">备注</th>
						</tr>
					</thead>

					<c:forEach items="${list }" var="obj" varStatus="vs">
						<tr>
							<td class="tc w50">${obj.seq } <input type="hidden" value="${obj.id }" name="list[${vs.index }].id">
							</td>
							<td> ${obj.department }	<input type="hidden" name="list[${vs.index }].userId" value="${obj.userId }">  </td>
							<td>${obj.goodsName }</td>
							<td class="tc"> ${obj.stand }</td>
							<td class="tc"> ${obj.qualitStand }</td>
							<td class="tc"> ${obj.item }</td>
							<td class="tc">${obj.purchaseCount }</td>
							<td class="tc">${obj.price }</td>
							<td class="tc">${obj.budget }</td>
							<td>${obj.deliverDate } </td>
							<td>
							<select onchange="sel(this)" name="list[${vs.index }].purchaseType" style="width:100px" id="select">
	                               <%-- <c:forEach items="${list2 }" var="purtype">
		                               <c:if test="${purtype.id==obj.purchaseType}">
											<option value="${obj.id }" selected="selected" >${purtype.name }</option>
										</c:if>
										 <c:if test="${purtype.id!=obj.purchaseType}">
	                                        <option value="${obj.id }" >${purtype.name }</option>
	                                    </c:if>
									 </c:forEach> --%>
									 <c:forEach items="${kind}" var="kind" >
                           <option value="${kind.id}" <c:if test="${kind.id == obj.purchaseType}">selected="selected" </c:if>> ${kind.name}</option>
                        </c:forEach>
			                </select>
			                
							</td>
							<td class="tc">
							<select class="org"  onchange="org(this)" name="list[${vs.index }].organization">
	 						<option value="">请选择</option>
								<c:forEach items="${org }" var="ss">
									<option value="${ss.name }" <c:if test="${ss.name==obj.organization }">selected="selected" </c:if> >${ss.name}</option>
								</c:forEach>
							</select>
							
							</td>
							
							<td class="tc">${obj.supplier }</td>
							<td class="tc">${obj.isFreeTax }</td>
							<td class="tc">${obj.goodsUse }</td>
							<td class="tc">${obj.useUnit }</td>
							<td class="tc">${obj.memo }
						
							</td>
						</tr>

					</c:forEach>
				</table>
				
				退回理由： <br><textarea   id="reson" name="reason" style="height:100px;width: 600px;margin-bottom: 20px;" title="不超过800个字"></textarea><br>
				 <input type="hidden" name="planNo" value="${planNo }">
				  <input type="hidden" id="status" name="status" value="4">
				 <input class="btn btn-windows save" style="margin-left: 100px;" type="submit" value="受理"> 
				<!--  <input class="btn btn-windows save" type="button" onclick="returns();" value="退回">    -->
				 <button class="btn btn-windows back" type="button" onclick="ss();">退回</button>
				<input class="btn btn-windows reset" value="返回" type="button" onclick="location.href='javascript:history.go(-1);'">
			</form>
		</div>
	</div>

</body>
</html>
