<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>


<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
       <script type="text/javascript" src="http://code.jquery.com/jquery-1.6.1.min.js"></script>
		<script src="${pageContext.request.contextPath}/public/backend/js/lock_table_head.js" ></script>
<title>采购需求管理</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

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
  	
 
  
    
/* 	  function sel(obj){
   	   var val=$(obj).val();
   	   $("select option").each(function(){
   		   var opt=$(this).val();
   		   if(val==opt){
   			   $(this).attr("selected", "selected");  
   		   }
   	   });
      }  */
	  
	  $(function() {
				$("td").each(function() {
					$(this).find("p").hide();
				});
			});
	  
 function ss(){
	 var value=$("#reson").val();

	 if(value!=null&&value!=""){
		 $("#status").val(4);
		 $("#table").find("#acc_form").submit();
	 }else{
			layer.tips("退回理由不允许为空","#reson");
	 }
 }
 
 function reason(id, auditFieldName) {
 				var id = "${planNo}";
				var supplierId = $("#supplierId").val();
				var index = layer.prompt({
						title: '请填写不通过的理由：',
						formType: 2,
						offset: '100px'
					}
					/**  function(text) {
						$.ajax({
							url: "${pageContext.request.contextPath}/supplierAudit/auditReasons.html",
							type: "post",
							data: "auditType=finance_page" + "&auditFieldName=" + auditFieldName + "&auditContent=" + auditContent + "&suggest=" + text + "&supplierId=" + supplierId + "&auditField=" + id,
							dataType: "json",
							success: function(result) {
								result = eval("(" + result + ")");
								if(result.msg == "fail") {
									layer.msg('该条信息已审核过！', {
										shift: 6, //动画类型
										offset: '100px'
									});
								}
							}
						});
						if(auditFieldName == "财务信息") {
							$("#" + id + "_hidden1").hide();
							$("#" + id + "_show").show();
						} else {
							$("#" + id + "_hidden2").hide();
							$("#" + id + "_fileShow").show();
						}
						layer.close(index);}*/
						);
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
 
 function acc(){
 
     	var bool=true;
		    $("#table tr:gt(0)").each(function(i){
		    	var  val1= $(this).find("td:eq(11)").children(":first").val();//上级id
		    	  if($.trim(val1) == "") {
		    		  bool=false;
		    		  i=i+1;
		    		  layer.msg("第"+i+"行，请选择采购机构！");
		    	  }
		    });
		    
		  if(bool==true){
			  $("#table").find("#acc_form").submit();
		  }  
 }
 
 function purchaseType(obj){
 	   var val=$(obj).val();
 	   $(".type option").each(function(){
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
	<div class="container container_box">
				<h2 class="count_flow"><i>1</i>计划主信息</h2>
				<ul class="ul_list">
					<li class="col-md-3 col-sm-6 col-xs-12 pl15">
						<span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">计划名称</span>
						<div class="input-append input_group col-sm-12 col-xs-12 p0">
							<input type="text" class="input_group" disabled="true" name="name" id="jhmc" value="${list[0].planName}">
							<span class="add-on">i</span>
						</div>
					</li>
					<li class="col-md-3 col-sm-6 col-xs-12">
						<span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">计划编号</span>
						<div class="input-append input_group col-sm-12 col-xs-12 p0">
							<input type="text" class="input_group" name="no" value="${list[0].planNo}" disabled="true" >
							<span class="add-on">i</span>
						</div>
					</li>
					
					<li class="col-md-3 col-sm-6 col-xs-12">
						<span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">计划文号</span>
						<div class="input-append input_group col-sm-12 col-xs-12 p0">
							<input type="text" class="input_group"  disabled="true"  value="${list[0].referenceNo}" >
							<span class="add-on">i</span>
						</div>
					</li>
					
					
					
					<li class="col-md-3 col-sm-6 col-xs-12">
						<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">类别</span>
						<div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
							<select name="planType" id="wtype" onchange="gtype(this)" disabled="true">
								<c:forEach items="${types }" var="tp" >
									<option value="${tp.id }">${tp.name }</option>
								</c:forEach>
							</select>
						</div>
					</li>
					
				  
					<li class="col-md-3 col-sm-6 col-xs-12">
						<span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">录入人手机号</span>
						<div class="input-append input_group col-sm-12 col-xs-12 p0">
							<input type="text" class="input_group" disabled="true" id="mobile" value="${list[0].recorderMobile }"> 
							<span class="add-on">i</span>
						</div>
					</li>
					<li class="col-md-3 col-sm-6 col-xs-12 mt25 ml5" style="display:none" id="dnone" >
			            <div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
			                <input type="checkbox" name="" onchange="" value="进口" />进口
			            </div>
			         </li>
          
             <li class="col-md-3 col-sm-6 col-xs-12">
                     <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">计划附件</span>
                       <u:upload id="detail"  multiple="true" buttonName="上传附件"    businessId="${list[0].fileId}" sysKey="2" typeId="${typeId}" auto="true" />
                        <u:show showId="detailshow"  businessId="${list[0].fileId}" sysKey="2" typeId="${typeId}" />
             </li>
          
          
	   </ul>
	
	 <h2 class="count_flow"><i>2</i>计划明细</h2>
		<div class="content ul_list"  id="content">
				<table id="table" class="table table-bordered table-condensed lockout">
					<thead>
						<tr class="space_nowrap">
							<th class="info w50">序号</th>
							<th class="info w260">需求部门</th>
							<th class="info w200">物资类别<br>及名称</th>
							<th class="info w200">规格型号</th>
							<th class="info w140">质量技术标准</br>（技术参数）</th>
							<th class="info w50">计量<br>单位</th>
							<th class="info w50">采购<br>数量</th>
							<th class="info w80">单价<br>（元）</th>
							<th class="info w80">预算金额</br>（万元）</th>
							<th class="info w150">交货期限</th>
							<th class="info w100">采购方式</br>建议</th>
							<th class="info w200">采购机构</th>
							<th class="info w260">供应商名称</th>
							<th class="info w80">是否申请</br>办理免税</th>
						<!-- 	<th class="info">物资用途（仅进口）</th>
							<th class="info">使用单位（仅进口）</th> -->
							<th class="info w260">备注</th>
							<!-- <th class="info">操作</th> -->
						</tr>
					</thead>
		<form id="acc_form" action="${pageContext.request.contextPath}/accept/update.html" method="post">
					
					<c:forEach items="${list }" var="obj" varStatus="vs">
						<tr>
							<td class="tc w50">
							    <div class="w50">
							        ${obj.seq } <input type="hidden" value="${obj.id }" name="list[${vs.index }].id">
							    </div>
							</td>
							<td class="tl"> 
							   <div class="w260">
							    ${obj.department }	<input type="hidden" name="list[${vs.index }].userId" value="${obj.userId }">
							    <c:forEach items="${requires }" var="re">
								  <c:if test="${re.id==obj.department }">${re.name } </c:if>
							    </c:forEach>
							   </div>
							  </td>
							<td class="tl">
							  <div class="w200">
							     ${obj.goodsName }
							  </div>
							</td>
							<td class="tl">
							    <div class="w200">
							       ${obj.stand }
							    </div>
							</td>
							<td class="tl">
							  <div class="w140"> ${obj.qualitStand }</div>
							</td>
							<td class="tc">
							    <div class="w50">${obj.item }</div>
							</td>
							<td class="tc">
							    <div class="w50">${obj.purchaseCount }</div>
							</td>
							<td class="tr">
							    <div class="w80">${obj.price }</div>
							</td>
							<td class="tr">
							     <div class="w80">${obj.budget }</div>
							</td>
							<td> 
							     <div class="w150">${obj.deliverDate }</div> 
							</td>
							
							<td class="p0">
							<%-- <c:if test="${obj.purchaseCount!=null }">  --%>
							<select class="type w120"  onchange="purchaseType(this)" required="required" name="list[${vs.index }].purchaseType" id="select">
									 <c:forEach items="${kind}" var="kind" >
			                           <option value="${kind.id}" <c:if test="${kind.id == obj.purchaseType}">selected="selected" </c:if>> ${kind.name}</option>
			                        </c:forEach>
			                </select>
			                
			              <%--   </c:if> --%>
							</td>
							<td class="tc p0">
							<%-- <c:if test="${obj.purchaseCount!=null }">  --%>
							<select  class="org w200" required="required"  name="list[${vs.index }].organization" onchange="org(this)">
						<!-- 	<option value="">请选择</option> -->
								<c:forEach items="${org }" var="ss">
									<option value="${ss.orgId }" <c:if test="${ss.orgId==obj.organization }">selected="selected" </c:if> >${ss.name}</option>
								</c:forEach>
							</select>
						<%-- 	</c:if> --%>
							</td>
							
							<td class="tl">
							     <div class="w260">${obj.supplier }</div>
							</td>
							<td class="tc">
							    <div class="w80">${obj.isFreeTax }</div>
							</td>
						<%-- 	<td class="tl pl20">${obj.goodsUse }</td>
							<td class="tl">${obj.useUnit }</td> --%>
							<td class="tl">
							    <div class="w260">${obj.memo }</div>
							</td>
							<%-- <td class="tc w50">
										<a onclick="reason('${f.id}','');" id="${f.id}_hidden1" class="btn">退回</a>
										<p id="${f.id}_show" class="b red">×</p>
									</td> --%>
						</tr>

					</c:forEach>
					<input type="hidden" name="planNo" value="${planNo }">
				    <input type="hidden" id="status" name="status" value="3">
					</form>
				</table>
				</div>
				  <div class="col-md-12 col-xs-12 col-sm-12 p0" >
				     <div class="col-md-12 col-xs-12 col-sm-12 p0"> 退回理由：</div>
				     <div class="col-md-12 col-xs-12 col-sm-12 p0">
				         <textarea id="reson" name="reason" class="h80 col-md-10 col-xs-10 col-sm-12" title="不超过800个字"></textarea>
                     </div>
                  </div>
                  <div class="col-md-12 col-xs-12 col-sm-12 tc mt20">
				     <button class="btn btn-windows save" style="margin-left: 100px;"  onclick="acc()" type="button"> 受理</button> 
				     <button class="btn btn-windows back" type="button" onclick="ss();">退回</button> 
				     <input class="btn btn-windows back" value="返回" type="button" onclick="location.href='javascript:history.go(-1);'">
			      </div> 
			  
	</div>

</body>
</html>
