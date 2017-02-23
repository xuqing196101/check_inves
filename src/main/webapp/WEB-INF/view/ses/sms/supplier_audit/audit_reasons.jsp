<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
	<%@ include file="/WEB-INF/view/common.jsp" %>
	<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
	<title>审核汇总</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
    <script type="text/javascript">
      //只读
      $(function() {
        $(":input").each(function() {
        $(this).attr("readonly", "readonly");
      });
        
       //审核按钮
       var num = ${num};
       if(num == 0){
         $("#tuihui").attr("disabled", true);
         $("#butongguo").attr("disabled", true);
         $("#buhege").attr("disabled", true);
       }
       if(num != 0){
         $("#tongguo").attr("disabled", true);
         $("#hege").attr("disabled", true);
         };
       });

	   /* function tijiao(status){
	     $("#supplierStatus").val(status);
		 form1.submit();
	   } */
			
		//上一步
	   function lastStep(){
	     var action = "${pageContext.request.contextPath}/supplierAudit/applicationForm.html";
			 $("#form_id").attr("action",action);
			 $("#form_id").submit();
	   }
			
	   //审核
	   function shenhe(status){
	   			var supplierId = $("input[name='supplierId']").val();
	   			if(status == 6){
	   				var index = layer.prompt({
						title: '请填写理由：',
						formType: 2,
						offset: '100px',
						}, function(text) {
							$.ajax({
								url: "${pageContext.request.contextPath}/supplierAudit/recordNotPassed.html",
								data: {"reason" : text , "supplierId" : supplierId},
								success: function() {
									//提交审核
									$("#status").val(status);
								  $("#form_shen").submit();
								}
							});
						});
	   			}else{
	   				$("#status").val(status);
	   				$("#status").val(status);
						$("#form_shen").submit();
	   			}
				}
			
		/** 全选全不选 */
	    function selectAll(){
	      var checklist = document.getElementsByName ("chkItem");
	      var checkAll = document.getElementById("checkAll");
	      if(checkAll.checked){
	        for(var i=0;i<checklist.length;i++){
		      checklist[i].checked = true;
		    } 
		  }else{
		    for(var j=0;j<checklist.length;j++){
		      checklist[j].checked = false;
		      }
	 	    }
		  }
		
		  //移除
	    function dele(){
	  		var supplierId = $("input[name='supplierId']").val();
				var ids =[];
				$('input[name="chkItem"]:checked').each(function(){ 
			  	ids.push($(this).val());
				});
				if(ids.length>0){
			      layer.confirm('您确定要移除吗?', {title:'提示！',offset: ['200px']}, function(index){
				    layer.close(index);
				      $.ajax({
				        url:"${pageContext.request.contextPath}/supplierAudit/deleteById.html",
				        data:"ids="+ids,
				        dataType:"json",
								success:function(result){
									result = eval("(" + result + ")");
									if(result.msg == "yes"){
										layer.msg("删除成功!",{offset : '100px'});
			       			  window.setTimeout(function(){
				       				var action = "${pageContext.request.contextPath}/supplierAudit/reasonsList.html";
						    			$("#form_id").attr("action",action);
						    			$("#form_id").submit();
				       			}, 1000);
									}
		       			},
		       				error: function(message){
									layer.msg("删除失败",{offset : '100px'});
									}
								});
				   		});
				 		}else{
			        layer.alert("请选择需要移除的信息！",{offset:'100px'});
				 	  }
	        }
    </script>
    <script type="text/javascript">
			function jump(str){
			  var action;
			  if(str=="essential"){
			     action ="${pageContext.request.contextPath}/supplierAudit/essential.html";
			  }
			  if(str=="financial"){
			    action = "${pageContext.request.contextPath}/supplierAudit/financial.html";
			  }
			  if(str=="shareholder"){
			    action = "${pageContext.request.contextPath}/supplierAudit/shareholder.html";
			  }
			  /*if(str=="materialProduction"){
			    action = "${pageContext.request.contextPath}/supplierAudit/materialProduction.html";
			  }
			  if(str=="materialSales"){
			    action = "${pageContext.request.contextPath}/supplierAudit/materialSales.html";
			  }
			  if(str=="engineering"){
			    action = "${pageContext.request.contextPath}/supplierAudit/engineering.html";
			  }
			  if(str=="serviceInformation"){
			    action = "${pageContext.request.contextPath}/supplierAudit/serviceInformation.html";
			  }
			  */
			  if(str=="items"){
			    action = "${pageContext.request.contextPath}/supplierAudit/items.html";
			  }
			  if(str == "aptitude") {
					action = "${pageContext.request.contextPath}/supplierAudit/aptitude.html";
				}
			  if(str=="contract"){
			    action = "${pageContext.request.contextPath}/supplierAudit/contract.html";
			  }
			  if(str=="applicationForm"){
			    action = "${pageContext.request.contextPath}/supplierAudit/applicationForm.html";
			  }
			  if(str=="reasonsList"){
			    action = "${pageContext.request.contextPath}/supplierAudit/reasonsList.html";
			  }
			  if(str == "supplierType") {
					action = "${pageContext.request.contextPath}/supplierAudit/supplierType.html";
			   }
			  $("#form_id").attr("action",action);
			  $("#form_id").submit();
			}
		</script>
  </head>

  <body>
		<!--面包屑导航开始-->
		<div class="margin-top-10 breadcrumbs ">
		  <div class="container">
		    <ul class="breadcrumb margin-left-0">
		      <li>
		        <a href="#"> 首页</a>
		      </li>
		      <li>
						<a href="#">支撑环境</a>
					</li>
		      <li>
		        <a href="#">供应商管理</a>
		      </li>
		      <li>
		        <a href="#">供应商审核</a>
		      </li>
		    </ul>
		  </div>
		</div> 
    <div class="container container_box">
      <div class="content">
        <div class="col-md-12 tab-v2 job-content">
	          <ul class="flow_step">
		          <li onclick = "jump('essential')">
		            <a aria-expanded="false" href="#tab-1">详细信息</a>
		            <i></i>
		          </li>
		          <li onclick = "jump('financial')">
		            <a aria-expanded="true" href="#tab-2">财务信息</a>
		            <i></i>                            
		          </li>
		          <li onclick = "jump('shareholder')" >
		            <a aria-expanded="false" href="#tab-3">股东信息</a>
		            <i></i>
		          </li>
		          <%--<c:if test="${fn:contains(supplierTypeNames, '生产')}">
		            <li onclick = "jump('materialProduction')">
		              <a aria-expanded="false" href="#tab-4">生产信息</a>
		              <i></i>
		            </li>
		          </c:if>
		          <c:if test="${fn:contains(supplierTypeNames, '销售')}">
		            <li onclick = "jump('materialSales')" >
		              <a aria-expanded="false" href="#tab-4" >销售信息</a>
		              <i></i>
		            </li>
		          </c:if>
		          <c:if test="${fn:contains(supplierTypeNames, '工程')}">
		            <li onclick = "jump('engineering')">
		              <a aria-expanded="false" href="#tab-4">工程信息</a>
		              <i></i>
		            </li>
		          </c:if>
		          <c:if test="${fn:contains(supplierTypeNames, '服务')}">
		            <li onclick = "jump('serviceInformation')" >
		              <a aria-expanded="false" href="#tab-4" >服务信息</a>
		              <i></i>
		            </li>
		          </c:if>
		          --%>
		          <li onclick = "jump('supplierType')">
		           	<a aria-expanded="false">供应商类型</a>
		           	<i></i>
			      </li>
		          <li onclick = "jump('items')">
	            	<a aria-expanded="false" href="#tab-4" >产品类别</a>
	            	<i></i>
	          	</li>
	          	<li onclick="jump('aptitude')">
								<a aria-expanded="false">资质文件维护</a>
								<i></i>
							</li>
		          <li onclick = "jump('contract')" >
		            <a aria-expanded="false" href="#tab-4">销售合同</a>
		             <i></i>
		          </li>    
		          <li onclick = "jump('applicationForm')" >
		            <a aria-expanded="false" href="#tab-4" >承诺书和申请表</a>
		            <i></i>
		          </li>
		          <li onclick = "jump('reasonsList')"  class="active">
		            <a aria-expanded="false" href="#tab-4" data-toggle="tab">审核汇总</a>
		          </li>
		        </ul>
        
          <form id="form_id" action="" method="post">
              <input name="supplierId" value="${supplierId}" type="hidden">
              <input name="supplierStatus" value="${supplierStatus}" type="hidden">
          </form>
          
          <c:if test="${supplierStatus == 3 }">
             <h2 class="count_flow"><i>1</i>问题汇总</h2>
          </c:if>
          
          <ul class="ul_list count_flow">

            <button class="btn btn-windows delete" type="button" onclick="dele();" style=" border-bottom-width: -;margin-bottom: 7px;">移除</button>
            <table class="table table-bordered table-condensed table-hover">
             <thead>
               <tr>
               	 <th class="info w30"><input type="checkbox" onclick="selectAll();"  id="checkAll"></th>
                 <th class="info w50">序号</th>
                 <th class="info">审批类型</th>
                 <th class="info">审批字段名字</th>
                 <th class="info">审批内容</th>
                 <th class="info">不通过理由</th>
               </tr>
             </thead>
               <c:forEach items="${reasonsList }" var="reasons" varStatus="vs">
                <input id="auditId" value="${list.id}" type="hidden">
                 <tr>
                   <td class="tc w30"><input type="checkbox" value="${reasons.id }" name="chkItem"  id="${reasons.id}"></td>
                   <td class="tc">${vs.index + 1}</td>
                   <td class="tc">
                     <c:if test="${reasons.auditType eq 'basic_page'}">详细信息</c:if>
                     <c:if test="${reasons.auditType eq 'finance_page'}">财务信息</c:if>
                     <c:if test="${reasons.auditType eq 'shareholder_page'}">股东信息</c:if>
                     <%-- <c:if test="${reasons.auditType == 'mat_pro_page'}">物资-生产信息</c:if>
                     <c:if test="${reasons.auditType == 'mat_sell_page'}">物资-销售信息</c:if>
                     <c:if test="${reasons.auditType == 'mat_eng_page'}">工程信息</c:if>
                     <c:if test="${reasons.auditType == 'mat_serve_page'}">服务信息</c:if> --%>
                     <c:if test="${reasons.auditType eq 'mat_pro_page' || reasons.auditType eq 'mat_sell_page' || reasons.auditType eq 'mat_eng_page' || reasons.auditType eq 'mat_serve_page' || reasons.auditType eq 'supplierType_page'}">供应商类型</c:if>
                     <%-- <c:if test="${reasons.auditType == 'mat_serve_page' || reasons.auditType == 'item_sell_page' || reasons.auditType == 'item_eng_page' || reasons.auditType == 'item_serve_page'}">品目信息</c:if> --%>
                     <c:if test="${reasons.auditType eq 'items_page'}">品目信息</c:if>
                     <c:if test="${reasons.auditType eq 'aptitude_page'}">资质文件</c:if>
                     <c:if test="${reasons.auditType eq 'contract_page'}">品目合同</c:if>
                     <c:if test="${reasons.auditType eq 'download_page'}">申请表</c:if>
                   </td>
                   <td class="tl pl20">${reasons.auditFieldName }</td>
                   <td class="tl pl20">${reasons.auditContent}</td>
                   <td class="tl pl20">${reasons.suggest}</td>
                 </tr>
               </c:forEach>
            </table>
            <div class="col-md-12 add_regist tc">
	           <a class="btn"  type="button" onclick="lastStep();">上一步</a>
	         </div>
          </ul>
	        <c:if test="${supplierStatus == 5}">
		        <!-- <h2 class="count_flow"><i></i>上传考察表报告</h2> -->
			      <ul class="ul_list">
		          <li class="col-md-6 p0 mb25">
		            <input name="supplierId" value="${supplierId}" type="hidden">
		            <span class="col-md-5 padding-left-5" ><a class="star_red">*</a>考察报告:</span>
		            <div style="margin-bottom: 25px">
		              <u:upload id="inspect" businessId="${suppliers.id}" buttonName="上传考察报告" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierInspectList}" auto="true" /> 
		              <u:show showId="inspect_show" businessId="${suppliers.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierInspectList}" />
		            </div>
		          </li>
	          </ul>
	        </c:if>
	        <div class="col-md-12 add_regist tc">
	          <form id="form_shen" action="${pageContext.request.contextPath}/supplierAudit/updateStatus.html"  enctype="multipart/form-data">
	            <input name="supplierId" value="${supplierId}" type="hidden">
	            <input name="status" id="status" type="hidden">
	            <input name="id" type="hidden">
	            <div class="margin-bottom-0  categories">
	              <div class="col-md-12 add_regist tc">
	              <div class="col-md-12 add_regist tc">
          			<!-- <a class="btn"  type="button" onclick="lastStep();">上一步</a> -->
		            <c:if test="${supplierStatus == 0}">
		              <input class="btn btn-windows git"  type="button" onclick="shenhe(1)" value="审核通过 " id="tongguo">
		              <input class="btn btn-windows back"  type="button" onclick="shenhe(2)" value="退回修改" id="tuihui">
		              <input class="btn btn-windows cancel"  type="button" onclick="shenhe(3)" value="审核不通过" id="butongguo">
		            </c:if>
		            <c:if test="${supplierStatus == 4}">
		              <input class="btn btn-windows git"  type="button" onclick="shenhe(5)" value="复核通过 " id="tongguo">
		              <input class="btn btn-windows cancel"  type="button" onclick="shenhe(6)" value="复核不通过" id="butongguo">
		            </c:if>
		            <c:if test="${supplierStatus == 5}">
		              <input class="btn btn-windows git"  type="button" onclick="shenhe(7)" value="合格 " id="hege">
		              <input class="btn btn-windows cancel"  type="button" onclick="shenhe(8)" value="不合格" id="buhege">
		            </c:if>
	              </div>
	            </div>
	          </form>
          </div>
        </div>     
      </div>
    </div>
  </body>
</html>
