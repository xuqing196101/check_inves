<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>


<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
        <%@ include file="/WEB-INF/view/common/webupload.jsp"%>
		
      <!--  <script type="text/javascript" src="http://code.jquery.com/jquery-1.6.1.min.js"></script> -->
	  <!-- script src="${pageContext.request.contextPath}/public/backend/js/lock_table_head.js" ></script -->
	  
	  
<title>采购需求管理</title>
<!-- <meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page"> -->

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
		 $("#treson").val(value);
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
			 var defValue;
			 var org=$(obj).val();
			 var price=$(obj).parent().prev().prev().prev().prev().val();
			 if(price==""){
				var id=$(obj).prev().val();
				 /*  $("#table tr").each(function(){
					  var opt= $(this).find("td:eq(11)").children(":last").val() ;
					  var pid=$(this).prev().val();
			 		   if(val==opt){
			 			   $(this).attr("selected", "selected");  
			 		   }  
			 	   }); */
			 	   
			 	  $.ajax({
			          url: "${pageContext.request.contextPath}/accept/detail.html",
			          data: "id=" + id,
			          type: "post",
			          dataType: "json",
			          success: function(result) {
			            for(var i = 0; i < result.length; i++) {
			                var v1 = result[i].id;
			                $("#table tr").each(function(){
			      			  var opt= $(this).find("td:eq(11)").children(":first").val() ;
			      	 		   if(v1==opt){
			      	 			 var td=$(this).find("td:eq(11)");
			      	 			var options= $(td).find("option");
				      	 		  $(options).each(function(){
						      	 	 defValue=$(this).parent().parent().parent().children(":last").children(":last").val();

						      	 		
				      	  		   var opt=$(this).val();
				      	  		   if(org==opt){
				      	  			$(this).prop("selected",true);
				      	  			  // $(this).attr("selected", "selected"); 
				      	  			  
						      	  		if(defValue==org){
					      	  			    $(this).parent().next().val("");	
					      	  			}else{
					      	  			    var prevId=$(this).parent().prev().val();
					      	  			    $(this).parent().next().val(prevId);	
					      	  			}
				      	  		   }else{
				      	  			$(this).prop("selected",false);
				      	  			//$(this).removeAttr("selected");
				      	  		   }
					      	  	   });
			      	 		   }  
			      	 	   });
			            }
			           }
			          });
			          
			          
			 }
 
    }
 
 function acc(){
     	var bool=true;
		    $("#table tr:gt(0)").each(function(i){
		    	var  val1= $(this).find("td:eq(11)").children(":last").prev().val();//上级id
		    	var  text= $(this).find("td:eq(6)").text();//上级id
		    	if($.trim(text) != ""){
		    		if($.trim(val1) == "") {
			    		  bool=false;
			    		  i=i+1;
			    		  layer.msg("第"+i+"行，请选择采购机构！");
			    	  }
		    	}
		    	  
		    });
		    checkSpace()////退回理由空格校验
		  if(bool==true){
			 $("#table").find("#acc_form").submit();
		  }  
 }
    //选择采购方式
    function purchaseType(obj){
        var purchaseType = $(obj).find("option:selected").text(); //选中的文本
        var org=$(obj).val();
        var price=$(obj).parent().prev().prev().prev().prev().val();
        if(price==""){

            var id=$(obj).prev().val();
            $.ajax({
                url: "${pageContext.request.contextPath}/accept/detail.html",
                data: "id=" + id,
                type: "post",
                dataType: "json",
                success: function(result) {
                    for(var i = 0; i < result.length; i++) {
                        var v1 = result[i].id;
                        $("#table tr").each(function(){
                            var opt= $(this).find("td:eq(10)").children(":first").val() ;
                            if(v1==opt){
                                var td=$(this).find("td:eq(10)");
                                var options= $(td).find("option");
                                $(options).each(function(){
                                    var opt=$(this).val();
                                    if(org==opt){
                                        $(this).prop("selected",true);
                                        if($.trim(purchaseType) == "单一来源") {
                                            $(this).parent().parent().next().next().find("input").removeAttr("readonly");
                                        } else {
                                            $(this).parent().parent().next().next().find("input").attr("readonly", "readonly");
                                            $(this).parent().parent().next().next().find("input").val("");
                                        }
                                    }else{
                                        $(this).prop("selected",false);
                                        // $(this).removeAttr("selected");
                                    }
                                });
                            }
                        });
                    }
                }
            });
        }
    }
    //退回理由空格校验
    function checkSpace(){
    	var space = $("#reson").val();
    	var valid=/\s/g;
        if(valid.test(space)){
        	layer.msg("退回理由中不能添加空格!");
        	return false;
        }
    }
    
  //校验供应商名称
    function checkSupplierName(obj) {
        var name=$(obj).val();
        if(name!=null){
            $.ajax({
                type: "POST",
                async:false,
                dataType: "text",
                data:{
                    "name":name
                },
                url: "${pageContext.request.contextPath }/purchaser/checkSupplierName.do",
                success: function(data) {
                        if(data=='true'){
                            $(obj).val("");
                            layer.alert("库中没有此供应商，请重新输入");
                        }
                }
             });
        }
    }
</script>

</head>

<body>
	<!--面包屑导航开始-->
	<div class="margin-top-10 breadcrumbs ">
		<div class="container">
			<ul class="breadcrumb margin-left-0">
				<li><a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a></li>
				<li><a href="javascript:void(0);">保障作业系统</a></li>
				<li><a href="javascript:void(0);">采购计划管理</a></li>
				<li class="active"><a href="javascript:jumppage('${pageContext.request.contextPath}/accept/list.html');">采购需求受理</a></li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>
	<div class="container container_box">
				<h2 class="count_flow"><i>1</i>需求主信息</h2>
				<ul class="ul_list">
					<li class="col-md-3 col-sm-6 col-xs-12 pl15">
						<span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">需求名称</span>
						<div class="input-append input_group col-sm-12 col-xs-12 p0">
							<input type="text" class="input_group" disabled="true" name="name" id="jhmc" value="${list[0].planName}">
							<span class="add-on">i</span>
						</div>
					</li>
					<li class="col-md-3 col-sm-6 col-xs-12">
						<span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">需求编号</span>
						<div class="input-append input_group col-sm-12 col-xs-12 p0">
							<input type="text" class="input_group" name="no" value="${list[0].planNo}" disabled="true" >
							<span class="add-on">i</span>
						</div>
					</li>
					
					<li class="col-md-3 col-sm-6 col-xs-12">
						<span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">需求文号</span>
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
									<option value="${tp.id }" <c:if test="${list[0].planType==tp.id}">selected="selected" </c:if>>${tp.name }</option>
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
					<c:if test="${list[0].planType=='FC9528B2E74F4CB2A9E74735A8D6E90A'}">
					<li class="col-md-3 col-sm-6 col-xs-12 mt25 ml5"  id="dnone" >
			            <div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
			                <input type="checkbox" name="" disabled="true" onchange="" <c:if test="${list[0].enterPort==1}">checked="checked"</c:if> value="" />进口
			            </div>
			         </li>
			         </c:if>
          
             <li class="col-md-3 col-sm-6 col-xs-12">
                     <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">需求附件</span>
                     <u:show showId="detailshow"  delete="false" businessId="${list[0].fileId}" sysKey="2" typeId="${typeId}" />
                        <%-- <u:show showId="detailshow"  businessId="${list[0].fileId}" sysKey="2" typeId="${typeId}" /> --%>
             </li>
          
          
	   </ul>
	
	 <h2 class="count_flow"><i>2</i>需求明细</h2>
		<div class="content require_ul_list"  id="content">
				<table id="table" class="table table-bordered table-condensed lockout">
					<thead>
						<tr class="space_nowrap">
							<th class="info w50">序号</th>
							<th class="info">需求部门</th>
							<th class="info">物资类别<br>及名称</th>
							<th class="info">规格型号</th>
							<th class="info">质量技术标准</br>（技术参数）</th>
							<th class="info">计量<br>单位</th>
							<th class="info">采购<br>数量</th>
							<th class="info">单价<br>（元）</th>
							<th class="info">预算金额</br>（万元）</th>
							<th class="info">交货期限</th>
							<th class="info">采购方式</br>建议</th>
							<th class="info">采购机构<br>建议</th>
							<th class="info">供应商名称</th>
							<th class="info">是否申请</br>办理免税</th>
						<!-- 	<th class="info">物资用途（仅进口）</th>
							<th class="info">使用单位（仅进口）</th> -->
							<th class="info">备注</th>
							<th class="info">附件</th>
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
							   <div class="department">
							    ${obj.department }	<input type="hidden" name="list[${vs.index }].userId" value="${obj.userId }">
							    <c:forEach items="${requires }" var="re">
								  <c:if test="${re.id==obj.department }">${re.name } </c:if>
							    </c:forEach>
							   </div>
							  </td>
							<td class="tl">
							  <div class="goodsname">
							     ${obj.goodsName }
							  </div>
							</td>
							<td title="${obj.stand}" class="tl">
							 <div class="stand">
							   <c:if test="${fn:length (obj.stand) > 8}">${fn:substring(obj.stand,0,7)}...</c:if>
							   <c:if test="${fn:length(obj.stand) <= 8}">${obj.stand}</c:if>
							 </div>
							</td >
							<td title="${obj.qualitStand}" class="tl">
						  	 <div class="qualitstand">
							   <c:if test="${fn:length (obj.qualitStand) > 8}">${fn:substring(obj.qualitStand,0,7)}...</c:if>
							   <c:if test="${fn:length(obj.qualitStand) <= 8}">${obj.qualitStand}</c:if>
							 </div>
							</td >
							
							<td class="tc">
							    <div class="item">${obj.item }</div>
							</td>
							<td class="tc">
							    <div class="purchasecount">${obj.purchaseCount }</div>
							</td>
							<td class="tr">
							    <div class="price"><fmt:formatNumber  type="number" pattern="#,##0.00" value="${obj.price }"/></div>
							</td>
							<td class="tr">
							     <div class="budget"><fmt:formatNumber type="number" pattern="#,##0.00"  value="${obj.budget}"/></div>
							</td>
							<td> 
							     <div class="deliverdate">${obj.deliverDate }</div> 
							</td>
							
							<td class="p0">
							<%-- <c:if test="${obj.purchaseCount!=null }">  --%>
							<input type="hidden" name="ss" value="${obj.id}"  >
							<select class="type w120"  onchange="purchaseType(this)" required="required" name="list[${vs.index }].purchaseType" id="select">
									 <option value=""  <c:if test="${obj.price==null }"> selected="selected" </c:if>>请选择</option>
									 <c:forEach items="${kind}" var="kind" >
									
			                           <option value="${kind.id}" <c:if test="${kind.id == obj.purchaseType}">selected="selected" </c:if>> ${kind.name}</option>
			                        </c:forEach>
			                </select>
			              
			              <%--   </c:if> --%>
							</td>
							<td class="tc p0">
							<%-- <c:if test="${obj.purchaseCount!=null }">  --%>
							<input type="hidden" name="ss" value="${obj.id}"  >
							<select  class="org w200" required="required"  name="list[${vs.index }].organization" onchange="org(this)">
							<option value="">请选择</option>
								<c:forEach items="${org }" var="ss">
									<option value="${ss.orgId }" <c:if test="${ss.orgId==obj.organization }">selected="selected" </c:if> >${ss.shortName}</option>
								</c:forEach>
							</select>
						 <input type="hidden"  name="history" value="">  
						<%-- 	</c:if> --%>
							</td>
							
							<td class="tl">
								<div class="w80"><input name="list[${vs.index }].supplier" readonly="readonly" value="${obj.supplier }" onblur="checkSupplierName(this)" /></div>
							</td>
							<td class="tc">
							    <div class="w80">${obj.isFreeTax }</div>
							</td>
						<%-- 	<td class="tl pl20">${obj.goodsUse }</td>
							<td class="tl">${obj.useUnit }</td> --%>
							<td class="tl">
							    <div class="w160">${obj.memo }</div>
							</td>
							
							<td class="p0">
							<u:show showId="pShow${vs.index}"  delete="false" businessId="${obj.id}" sysKey="2" typeId="${typeId}" />
										<%-- 	<div class="w150">
													<u:upload id="pUp${vs.index}" businessId="${obj.id}" buttonName="上传文件" sysKey="2" typeId="${typeId}" auto="true" />
													<u:show showId="pShow${vs.index}"  businessId="${obj.id}" sysKey="2" typeId="${typeId}" />
											   </div> --%>	
								<input type="hidden" name="s" value="${obj.organization}"/>
											   
							</td>
							
							
							<%-- <td class="tc w50">
										<a onclick="reason('${f.id}','');" id="${f.id}_hidden1" class="btn">退回</a>
										<p id="${f.id}_show" class="b red">×</p>
									</td> --%>
						</tr>

					</c:forEach>
					<input type="hidden" name="planNo" value="${planNo }">
				    <input type="hidden" id="status" name="status" value="3">
				     <input type="hidden" id="treson" name="reason" value="">
					</form>
				</table>
				</div>
				  <div class="col-md-12 col-xs-12 col-sm-12 p0" >
				     <div class="col-md-12 col-xs-12 col-sm-12 p0"> 退回理由：</div>
				     <div class="col-md-12 col-xs-12 col-sm-12 p0">
				         <textarea id="reson" name="reason" maxlength="800" class="h80 col-md-10 col-xs-10 col-sm-12" title="不超过800个字" onblur="checkSpace(this);"></textarea>
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
