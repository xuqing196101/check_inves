<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>

	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/ses/ems/expertFinalInspect/merge_jump.js"></script>
		
		<script type="text/javascript">
			$(function(){
        // 导航栏显示
        $("#reverse_of_three").attr("class","active");
        $("#reverse_of_three").removeAttr("onclick");

				var expertId = $("#expertId").val();
				var mat = $("#mat").val();
				var eng = $("#eng").val();
				var ser = $("#ser").val();
				var goodsProject = $("#goodsProject").val();
				var goodsEngInfo = $("#goodsEngInfo").val();
				var engInfo = $("#engInfo").val();

				var matCodeId = $("#matCodeId").val();
				var engCodeId = $("#engCodeId").val();
				var serCodeId = $("#serCodeId").val();
				var goodsProjectId = $("#goodsProjectId").val();
				var goodsEngInfoId = $("#goodsEngInfoId").val();
				
				var sign = ${sign};
				var batchId = "${batchId}";
				if(mat == "mat_page"){
					// 物资品目信息
					loading = layer.load(1);
					var path = "${pageContext.request.contextPath}/finalInspect/getCategories.html?expertId=" + expertId + "&typeId=" + matCodeId + "&sign=" + sign + "&batchId=" + batchId;
					$("#tbody_category").load(path);
				}else if(eng == "eng_page"){
					// 工程品目信息
					loading = layer.load(1);
					var path = "${pageContext.request.contextPath}/finalInspect/getCategories.html?expertId=" + expertId + "&typeId=" + engCodeId + "&sign=" + sign + "&batchId=" + batchId;
					$("#tbody_category").load(path);
				}else if(ser == "ser_page"){
					// 服务
					loading = layer.load(1);
					var path = "${pageContext.request.contextPath}/finalInspect/getCategories.html?expertId=" + expertId + "&typeId=" + serCodeId + "&sign=" + sign + "&batchId=" + batchId;
					$("#tbody_category").load(path);
				}else if(goodsProject == "goodsProject_page"){
					// 工程参评类别信息
					loading = layer.load(1);
					var path = "${pageContext.request.contextPath}/finalInspect/getCategories.html?expertId=" + expertId + "&typeId=" + goodsProjectId + "&sign=" + sign + "&batchId=" + batchId;
					$("#tbody_category").load(path);
				}else if(goodsEngInfo == "goodsEngInfo_page"){
					// 工程专业属性信息
					loading = layer.load(1);
					var path = "${pageContext.request.contextPath}/finalInspect/getCategories.html?expertId=" + expertId + "&typeId=" + goodsEngInfoId + "&sign=" + sign + "&batchId=" + batchId;
					$("#tbody_category").load(path);
				}
			});
			
			function showDivTree(code) {
					// 加载已选品目列表
					loading = layer.load(1);
					var expertId = $("#expertId").val();
					var sign = ${sign};
					var batchId = "${batchId}";
					var path = "${pageContext.request.contextPath}/finalInspect/getCategories.html?expertId=" + expertId + "&typeId=" + code + "&sign=" + sign + "&batchId=" + batchId;
					$("#tbody_category").load(path);
			};   
			
			
					
					function trim(str){ //删除左右两端的空格
					return str.replace(/(^\s*)|(\s*$)/g, "");
					}
					
					
			
			
		</script>
		<script type="text/javascript">
			//下一步
			function nextStep() {
				var action = "${pageContext.request.contextPath}/finalInspect/expertFile.html";
				$("#form_id").attr("action", action);
				$("#form_id").submit();
			}

			//上一步
			function lastStep() {
				var action = "${pageContext.request.contextPath}/finalInspect/expertType.html";
				$("#form_id").attr("action", action);
				$("#form_id").submit();
			}
			
			//暂存
       function zancun(){
         var expertId = $("#expertId").val();
         $.ajax({
           url: "${pageContext.request.contextPath}/expertAudit/temporaryAudit.do",
           dataType: "json",
           data:{expertId : expertId},
           success : function (result) {
               layer.msg(result, {offset : [ '100px' ]});
           },error : function(){
             layer.msg("暂存失败", {offset : [ '100px' ]});
           }
         });
       }
			
			
			//撤销
			function revokeCategoryAudit(){
				var isCheck = '${isCheck}';
				var sign = ${sign};
				if((status ==-2 || status == 0 || status == 15|| status == 16 || (sign ==1 && status ==9) || (sign ==3 && status ==6) || status ==4) && isCheck == 'no'){
					var expertId = $("#expertId").val();
					var sign = ${sign};
					var categoryIds = [];
					var falg = true;
	        $('input[name="chkItem"]:checked').each(function () {
	          var index=$(this).val();
	          var itemsId =$("#itemsId"+index+"").val();
	          categoryIds.push(itemsId);
	          
	          ii = $('input[name=del'+itemsId+']').val();
	          if(ii != itemsId){
	              falg = false;
	            }
	         });
	        if(categoryIds.length > 0){
	        	if(falg){
	        		var auditReason="";
	        		$.ajax({
	  	              url: "${pageContext.request.contextPath}/expertAudit/selectCategoryAudit.do",
	  	              type: "post",
	  	              traditional:true,
	  	              data:{"expertId" : expertId, "categoryIds" : categoryIds, "sign" : sign},
	  	              success : function(result){
	  	            	auditReason=result.msg;
	  	            	 var index = layer.prompt({
							    title : '请修改不通过的理由：', 
							    formType : 2, 
							    offset : '100px',
							    maxlength : '50',
							    value:auditReason,
							    btn:['确认','撤销','取消'],
							    btn3:function(){
							    	layer.close(index);
				                },
				                btn2:function(){
				                	$.ajax({
				      	              url: "${pageContext.request.contextPath}/expertAudit/revokeCategoryAudit.do",
				      	              type: "post",
				      	              traditional:true,
				      	              data:{"expertId" : expertId, "categoryIds" : categoryIds, "sign" : sign},
				      	              success : function(result){
				      	                layer.msg(result.msg, {offset : [ '100px' ]});
				      	                if(result.status == 500){
				      	                  window.setTimeout(function () {
				      	                    var action = "${pageContext.request.contextPath}/expertAudit/product.html";
				      	                    $("#form_id").attr("action", action);
				      	                    $("#form_id").submit();
				      	                  }, 1000);
				      	                }
				      	              }
				      	            });
				                }
								},function(text){
									  if(text != null && text !=""){
										    $.ajax({
										      url:"${pageContext.request.contextPath}/expertAudit/updateCategoryAudit.html",
										      type:"post",
										      data:{"expertId" : expertId, "categoryIds" : categoryIds+"", "sign" : sign,"auditReason":text},
										      success:function(){
										           layer.msg('审核理由修改成功', {	            
										             shift: 4, //动画类型
										             offset:'100px'
										          });
										      }
										    });
									      layer.close(index);
								      }else{
								      	layer.msg('不能为空！', {offset:'100px'});
								      }
								    });
			           }
			         });
	  	             
	        		
	        	}else{
	            layer.msg("请选择已审核不通过的产品目录", {offset : [ '100px' ]});
	          }
	        }else{
	        	 layer.msg("请选择", {offset : [ '100px' ]});
	        }
			  }
			}
		</script>
	</head>

	<body >
		<!--面包屑导航开始-->
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li>
						<a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a>
					</li>
					<li>
						<a href="javascript:void(0)">支撑系统</a>
					</li>
					<li>
						<a href="javascript:void(0)">专家管理</a>
					</li>
					<c:if test="${sign == 1}">
						<li>
							<a href="javascript:void(0)" onclick="jumppage('${pageContext.request.contextPath}/expertAudit/list.html?sign=1')">专家初审</a>
						</li>
					</c:if>
					<c:if test="${sign == 2}">
						<li>
							<a href="javascript:void(0)" onclick="jumppage('${pageContext.request.contextPath}/expertAgainAudit/findBatchDetailsList.html?batchId=${batchId}')">专家复审</a>
						</li>
					</c:if>
					<c:if test="${sign == 3}">
						<li>
							<a href="javascript:void(0)" onclick="jumppage('${pageContext.request.contextPath}/expertAudit/list.html?sign=3')">专家复查</a>
						</li>
					</c:if>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
		<input type="hidden" name="id" id="id" value="${expertId}" />
		<input value="物资" type="hidden" name="tab-1">
		<input value="工程" type="hidden" name="tab-2">
		<input value="服务" type="hidden" name="tab-3">

		<div class="container container_box">
			<div class=" content">
				<div class="col-md-12 tab-v2 job-content">
					<%@include file="/WEB-INF/view/ses/ems/expertFinalInspect/common_jump.jsp" %>
					<div class="padding-top-10">
						<ul id="page_ul_id" class="nav nav-tabs bgdd supplier_tab hand">
							<c:set value="0" var="liCount" />
							<c:forEach items="${allCategoryList}" var="cate" varStatus="vs">
								<c:if test="${cate.code eq 'GOODS'}">
									<c:set value="${liCount+1}" var="liCount" />
									<%-- <li id="li_id_${vs.index + 1}" class="active" onclick="showDivTree(this);"> --%>
									<li id="li_id_${vs.index + 1}" class="active" onclick="showDivTree('${matCodeId }');">
										<a id="li_${vs.index + 1}" aria-expanded="true" data-toggle="tab" class="f18">物资参评类别信息</a>
										<input type="hidden" id="mat" value="mat_page">
										<input id="matCodeId" type="hidden" value="${matCodeId }">
									</li>
								</c:if>
								<c:if test="${cate.code eq 'PROJECT'}">
									<%-- <li id="li_id_${vs.index + 1}" class='<c:if test="${liCount == 0}">active</c:if>' onclick="showDivTree(this);"> --%>
									<li id="li_id_${vs.index + 1}" class='<c:if test="${liCount == 0}">active</c:if>' onclick="showDivTree('${engCodeId }');">
										<a id="li_${vs.index + 1}" aria-expanded="true" data-toggle="tab" class="f18">工程参评类别信息</a>
										<input type="hidden" id="eng" value="eng_page">
										<input id="engCodeId" type="hidden" value="${engCodeId }">
									</li>
									<c:set value="${liCount+1}" var="liCount" />
								</c:if>
								<c:if test="${cate.code eq 'PROJECT'}">
									<li id="li_id_${vs.index + 1}" class='<c:if test="${liCount == 0}">active</c:if>' onclick="showDivTree('${engInfoId }');">
										<a id="li_${vs.index + 1}" aria-expanded="true" data-toggle="tab" class="f18">工程专业属性信息</a>
										<input type="hidden" id="engInfo" value="engInfo_page">
										<input id="engInfoId" type="hidden" value="${engInfoId }">
									</li>
									<c:set value="${liCount+1}" var="liCount" />
								</c:if>
								<c:if test="${cate.code eq 'SERVICE'}">
									<%-- <li id="li_id_${vs.index + 1}" class='<c:if test="${liCount == 0}">active</c:if>' onclick="showDivTree(this);"> --%>
										<li id="li_id_${vs.index + 1}" class='<c:if test="${liCount == 0}">active</c:if>' onclick="showDivTree('${serCodeId }');">
										<a id="li_${vs.index + 1}" aria-expanded="false" data-toggle="tab" class="f18">服务参评类别信息</a>
										<input type="hidden" id="ser" value="ser_page">
										<input id="serCodeId" type="hidden" value="${serCodeId }">
									</li>
									<c:set value="${liCount+1}" var="liCount" />
								</c:if>
								
								<!-- 经济 -->
								<c:if test="${cate.code eq 'GOODS_PROJECT'}">
									<li id="li_id_${vs.index + 1}" class='<c:if test="${liCount == 0}">active</c:if>' onclick="showDivTree('${goodsProjectId }');">
										<a id="li_${vs.index + 1}" aria-expanded="true" data-toggle="tab" class="f18">工程参评类别信息</a>
										<input type="hidden" id="goodsProject" value="goodsProject_page">
										<input id=goodsProjectId type="hidden" value="${goodsProjectId }">
									</li>
									<c:set value="${liCount+1}" var="liCount" />
								</c:if>
								<c:if test="${cate.code eq 'GOODS_PROJECT'}">
									<li id="li_id_${vs.index + 1}" class='<c:if test="${liCount == 0}">active</c:if>' onclick="showDivTree('${engInfoId }');">
										<a id="li_${vs.index + 1}" aria-expanded="true" data-toggle="tab" class="f18">工程专业属性信息</a>
										<input type="hidden" id="goodsEngInfo" value="goodsEngInfo_page">
										<input id="goodsEngInfoId" type="hidden" value="${engInfoId }">
									</li>
									<c:set value="${liCount+1}" var="liCount" />
								</c:if>

							</c:forEach>
						</ul>
						<div class="mt20" id="tbody_category"></div>
					</div>
					<div class="col-md-12 add_regist tc m_btn_ab">
						<a class="btn" type="button" onclick="lastStep();">上一步</a>
						<a class="btn" type="button" onclick="nextStep();">下一步</a>
						 <a class="btn" type="button" onclick="jump('expertAttachment')">转至复查</a>
					</div>
				</div>
			</div>
		</div>
		<input value="${expertId}" id="expertId" type="hidden">
		<form id="form_id" action="" method="post">
			<input name="expertId" value="${expertId}" type="hidden">
			<input name="sign" value="${sign}" type="hidden">
			<input name="batchId" value="${batchId}" type="hidden">
			<input name="isReviewRevision" value="${isReviewRevision}" type="hidden">
			<input name="isCheck" value="${isCheck}" type="hidden">
			<input id="finalInspectNumber" name="finalInspectNumber" value="${expert.finalInspectCount==null?1:expert.finalInspectCount+1}" type="hidden">
		</form>
        <input id="status" value="${status}" type="hidden">
	</body>

</html>