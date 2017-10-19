<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>

	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/ses/ems/expertAudit/merge_jump.js"></script>
		<!-- <script type="text/javascript">

		function initTree(){
			showTree("tab-1");
			$("#tab-1").attr("style", "");
			$("li_id_1").attr("class", "active");
			$("li_1").attr("aria-expanded", "true");
			$("#tab-2").attr("style", "display: none");
			$("#tab-3").attr("style", "display: none");
		}
			function showTree(tabId) {
				var id = $("#" + tabId + "-value").val();
				var zTreeObj;
				var zNodes;
				var expertId = $("#expertId").val();
				$.ajax({
					url: "${pageContext.request.contextPath}/expertAudit/getCategory.do",
					async: false,
					data: {"categoryId": id,"expertId": expertId},
					success: function(data){
						zNodes = data;
					},
					dataType: "json"
				});
				var setting = {
					/*async: {
						autoParam: ["id"],
						enable: true,
						url: "${pageContext.request.contextPath}/expertAudit/getCategory.do",
						otherParam: {
							"categoryId": id,
							"expertId": expertId
						},
						dataFilter: ajaxDataFilter,
						dataType: "json",
						type: "get"
					},*/
					check: {
						enable: false,
						chkStyle: "checkbox",
						chkboxType: {
							"Y": "ps",
							"N": "ps"
						}, //勾选checkbox对于父子节点的关联关系  
						chkDisabledInherit: true
					},
					data: {
						simpleData: {
							enable: true,
							idKey: "id",
							pIdKey: "parentId",
						}
					},
					callback: {
						onClick: ztreeOnClick,
						showLine: true
					}
				};
				zTreeObj = $.fn.zTree.init($("#" + tabId), setting, zNodes);
				zTreeObj.expandAll(true); //全部展开
			}

			/*function ajaxDataFilter(treeId, parentNode, childNodes) {
				// 判断是否为空
				if(childNodes) {
					// 判断如果父节点是第三极,则将查询出来的子节点全部改为isParent = false
					if(parentNode != null && parentNode != "undefined" && parentNode.level == 2) {
						for(var i = 0; i < childNodes.length; i++) {
							childNodes[i].isParent += false;
						}
					}
				}
				return childNodes;
			}*/

			function showDivTree(obj) {
				$("#tab-1").attr("style", "display: none");
				$("#tab-2").attr("style", "display: none");
				$("#tab-3").attr("style", "display: none");
				var id = obj.id;
				var page = "tab-" + id.charAt(id.length - 1);
				$("#" + page).attr("style", "");
				showTree(page);
			}

			function initTree() {
				showTree("tab-1");
				$("#tab-1").attr("style", "");
				$("li_id_1").attr("class", "active");
				$("li_1").attr("aria-expanded", "true");
				$("#tab-2").attr("style", "display: none");
				$("#tab-3").attr("style", "display: none");
			}

			/** 点击tree **/
			function ztreeOnClick(event, treeId, treeNode) {
				if(treeNode != null) {
					if(!treeNode.isParent) {
						reason(treeNode.name, treeId);
					} else {
						layer.msg("请选择末级节点进行审核");
					}
				}

			}
			
			function reason(auditContent, str) {
				var expertId = $("#expertId").val();
				var auditField = $("input[name='" + str + "']").val();
				var index = layer.prompt({
						title: '请填写不通过的理由：',
						formType: 2,
						offset: '100px',
					},
					function(text) {
						$.ajax({
								url: "${pageContext.request.contextPath}/expertAudit/auditReasons.html",
								type: "post",
								dataType: "json",
								data: "suggestType=six" + "&auditContent=" + auditContent + "&auditReason=" + text + "&expertId=" + expertId + "&auditField=" + auditField,
								success: function(result) {
									result = eval("(" + result + ")");
									if(result.msg == "fail") {
										layer.msg('该条信息已审核过！', {
											shift: 6, //动画类型
											offset: '100px'
										});
									}
								}
							}),
							/* $(obj).after(html); */
							layer.close(index);
					});
			}
			
		</script> -->
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
					var path = "${pageContext.request.contextPath}/expertAudit/getCategories.html?expertId=" + expertId + "&typeId=" + matCodeId + "&sign=" + sign + "&batchId=" + batchId;
					$("#tbody_category").load(path);
				}else if(eng == "eng_page"){
					// 工程品目信息
					loading = layer.load(1);
					var path = "${pageContext.request.contextPath}/expertAudit/getCategories.html?expertId=" + expertId + "&typeId=" + engCodeId + "&sign=" + sign + "&batchId=" + batchId;
					$("#tbody_category").load(path);
				}else if(ser == "ser_page"){
					// 服务
					loading = layer.load(1);
					var path = "${pageContext.request.contextPath}/expertAudit/getCategories.html?expertId=" + expertId + "&typeId=" + serCodeId + "&sign=" + sign + "&batchId=" + batchId;
					$("#tbody_category").load(path);
				}else if(goodsProject == "goodsProject_page"){
					// 工程产品类别信息
					loading = layer.load(1);
					var path = "${pageContext.request.contextPath}/expertAudit/getCategories.html?expertId=" + expertId + "&typeId=" + goodsProjectId + "&sign=" + sign + "&batchId=" + batchId;
					$("#tbody_category").load(path);
				}else if(goodsEngInfo == "goodsEngInfo_page"){
					// 工程专业属性信息
					loading = layer.load(1);
					var path = "${pageContext.request.contextPath}/expertAudit/getCategories.html?expertId=" + expertId + "&typeId=" + goodsEngInfoId + "&sign=" + sign + "&batchId=" + batchId;
					$("#tbody_category").load(path);
				}
			});
			
			function showDivTree(code) {
					// 加载已选品目列表
					loading = layer.load(1);
					var expertId = $("#expertId").val();
					var sign = ${sign};
					var batchId = "${batchId}";
					var path = "${pageContext.request.contextPath}/expertAudit/getCategories.html?expertId=" + expertId + "&typeId=" + code + "&sign=" + sign + "&batchId=" + batchId;
					$("#tbody_category").load(path);
			};   
			
			//单个审核“不通过按钮”
			function reason(firstNode, secondNode, thirdNode, fourthNode, id) {
				var status = ${status};
				var sign = ${sign};
        //只能审核可以审核的状态
        if(status ==-2 || status == 0 || status == 15|| status == 16 || (sign ==1 && status ==9) || (sign ==3 && status ==6) || status ==4){
					var auditContent;
					var auditField;
					var expertId = $("#expertId").val();
					if(fourthNode != null && fourthNode !=""){
						auditContent = firstNode+"/"+secondNode+"/"+thirdNode+"/"+fourthNode;
						auditField = fourthNode;
					}else if(thirdNode !=null && thirdNode!=""){
						auditContent = firstNode+"/"+secondNode+"/"+thirdNode;
						auditField = thirdNode;
					}else if(secondNode !=null && secondNode !=""){
						auditContent = firstNode+"/"+secondNode;
						auditField = secondNode;
					}else{
						auditContent = firstNode;
						auditField = firstNode;
					}
					
					function trim(str){ //删除左右两端的空格
					return str.replace(/(^\s*)|(\s*$)/g, "");
					}
					
					var index = layer.prompt({
							title: '请填写不通过的理由：',
							formType: 2,
							offset: '100px',
							 maxlength : '50'
						},
						function(text) {
							var text = trim(text);
					  	if(text != null && text !=""){
									$.ajax({
										url: "${pageContext.request.contextPath}/expertAudit/auditReasons.html",
										type: "post",
										data: {
											"suggestType": "six",
											"auditReason": text,
											"expertId": expertId,
											"auditField": auditField,
											"auditContent": auditContent,
											"auditFieldId": id,
											"auditFalg" : sign
										},
										dataType: "json",
										success: function(result) {
											result = eval("(" + result + ")");
											if(result.msg == "fail") {
												layer.msg('该条信息已审核过！', {
													shift: 6, //动画类型
													offset: '300px'
												});
											}
										}
									});
									//隐藏通过按钮
									$("#" + id + "_hidden").hide();
					        $("#" + id + "_show").show();
									layer.close(index);
							}else{
								layer.msg('不能为空！', {offset:'100px'});
							}
						});
					}
				}
			
			//批量审核
			function batchSelection(){
				var status = ${status};
		        var sign = ${sign};
		        //只能审核可以审核的状态
        if(status ==-2 || status == 0 || status == 15|| status == 16 || (sign ==1 && status ==9) || (sign ==3 && status ==6) || status ==4){
					 var expAuditList=[];
					 var expertId = $("#expertId").val();
					 var ids="";
					 var count=$("input[name='chkItem']:checked").length;
					 if(count<=0){
						 layer.msg('请先选择目录,至少有一条！', {offset:'100px'});
						 return;
					 }
					 var index = layer.prompt({
							title: '请填写不通过的理由：',
							formType: 2,
							offset: '100px',
							maxlength: '100',
						}, function(text) {
							var text = $.trim(text);
						  if(text != null && text !=""){
							  if($.trim(text).length>900){
								  layer.msg('审核内容长度过长！', {offset:'100px'});
								  return;
							  }
							  $("input[name='chkItem']:checked").each(function(){ 
									 var index=$(this).val();
									 var itemsId=$("#itemsId"+index+"").val();
									 ids=ids+","+itemsId;
									 var firstNode=$("#firstNode"+index+"").val();
									 var secondNode=$("#secondNode"+index+"").val();
									 var thirdNode=$("#thirdNode"+index+"").val();
									 var fourthNode=$("#fourthNode"+index+"").val();
									 var auditContent;
									 var auditField;
									 if(fourthNode != null && fourthNode !=""){
										auditContent = firstNode+"/"+secondNode+"/"+thirdNode+"/"+fourthNode;
										auditField = fourthNode;
									 }else if(thirdNode !=null && thirdNode!=""){
										auditContent = firstNode+"/"+secondNode+"/"+thirdNode;
										auditField = thirdNode;
									 }else if(secondNode !=null && secondNode !=""){
										auditContent = firstNode+"/"+secondNode;
										auditField = secondNode;
									 }else{
										auditContent = firstNode;
										auditField = firstNode;
									 }
									 var expAudit=new Object();
									 expAudit.auditReason=text;
									 expAudit.expertId=expertId;
									 expAudit.suggestType="six";
									 expAudit.auditContent=auditContent;
									 expAudit.auditField=auditField;
									 expAudit.auditFieldId=itemsId;
									 expAudit.auditFalg=sign;
									 expAuditList.push(expAudit);
						       }); 
							  $.ajax({
									url: "${pageContext.request.contextPath}/expertAudit/batchSelection.do",
									type: "post",
									data: JSON.stringify(expAuditList),
									contentType:"application/json",
									success: function(result) {
										if(result.status==500){
											//location.reload();
											layer.msg(result.msg, {
												offset: '100px',
											});
											var checklist = document.getElementsByName ("chkItem");
		 									var checkAll = document.getElementById("checkAll");
		 									checkAll.checked=false;
		 									 for(var j=0;j<checklist.length;j++)
		 									  {
		 									     checklist[j].checked = false;
		 									  }
											ids=ids.substring(1, ids.length);
											var sp=ids.split(",");
											for ( var s in sp) {
												$("td[name='itemtd"+sp[s]+"']").css('border-color', '#FF0000');
												
												//为撤销按钮做校验用的
												$('input[name=del'+sp[s]+']').val(sp[s]);
											}
											 
										}else{
											layer.msg(result.msg, {
												shift: 6, // 动画类型
												offset: '100px',
											});
										}
											
									}
								});
							  
									layer.close(index);
						  }else{
						  		layer.msg('不能为空！', {offset:'100px'});
						  	};
			        })
		        }
			}
		</script>
		<script type="text/javascript">
			//下一步
			function nextStep() {
				var action = "${pageContext.request.contextPath}/expertAudit/expertFile.html";
				$("#form_id").attr("action", action);
				$("#form_id").submit();
			}

			//上一步
			function lastStep() {
				var action = "${pageContext.request.contextPath}/expertAudit/expertType.html";
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
				if(status ==-2 || status == 0 || status == 15|| status == 16 || (sign ==1 && status ==9) || (sign ==3 && status ==6) || status ==4){
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
	        	}else{
	            layer.msg("请选择复审过的", {offset : [ '100px' ]});
	          }
	        }else{
	        	 layer.msg("请选择", {offset : [ '100px' ]});
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
					<%@include file="/WEB-INF/view/ses/ems/expertAudit/common_jump.jsp" %>
					<div class="padding-top-10">
						<ul id="page_ul_id" class="nav nav-tabs bgdd supplier_tab hand">
							<c:set value="0" var="liCount" />
							<c:forEach items="${allCategoryList}" var="cate" varStatus="vs">
								<c:if test="${cate.code eq 'GOODS'}">
									<c:set value="${liCount+1}" var="liCount" />
									<%-- <li id="li_id_${vs.index + 1}" class="active" onclick="showDivTree(this);"> --%>
									<li id="li_id_${vs.index + 1}" class="active" onclick="showDivTree('${matCodeId }');">
										<a id="li_${vs.index + 1}" aria-expanded="true" data-toggle="tab" class="f18">物资产品类别信息</a>
										<input type="hidden" id="mat" value="mat_page">
										<input id="matCodeId" type="hidden" value="${matCodeId }">
									</li>
								</c:if>
								<c:if test="${cate.code eq 'PROJECT'}">
									<%-- <li id="li_id_${vs.index + 1}" class='<c:if test="${liCount == 0}">active</c:if>' onclick="showDivTree(this);"> --%>
									<li id="li_id_${vs.index + 1}" class='<c:if test="${liCount == 0}">active</c:if>' onclick="showDivTree('${engCodeId }');">
										<a id="li_${vs.index + 1}" aria-expanded="true" data-toggle="tab" class="f18">工程产品类别信息</a>
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
										<a id="li_${vs.index + 1}" aria-expanded="false" data-toggle="tab" class="f18">服务产品类别信息</a>
										<input type="hidden" id="ser" value="ser_page">
										<input id="serCodeId" type="hidden" value="${serCodeId }">
									</li>
									<c:set value="${liCount+1}" var="liCount" />
								</c:if>
								
								<!-- 经济 -->
								<c:if test="${cate.code eq 'GOODS_PROJECT'}">
									<li id="li_id_${vs.index + 1}" class='<c:if test="${liCount == 0}">active</c:if>' onclick="showDivTree('${goodsProjectId }');">
										<a id="li_${vs.index + 1}" aria-expanded="true" data-toggle="tab" class="f18">工程产品类别信息</a>
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
						<%-- <c:set var="count" value="0"></c:set>
						<div class="tag-box tag-box-v3 center" id="content_ul_id">
							<c:forEach items="${allCategoryList}" var="cate" varStatus="vs">
								<c:if test="${cate.name eq '物资'}">
									<c:set var="count" value="${count + 1}"></c:set>
									<ul id="tab-${vs.index + 1}" class="ztree_supplier mt30" style="display: none"></ul>
									<input id="tab-${vs.index + 1}-value" value="${cate.id}" type="hidden">
								</c:if>
								<c:if test="${cate.name eq '工程'}">
									<c:set var="count" value="${count + 1}"></c:set>
									<ul id="tab-${vs.index + 1}" class="ztree_supplier mt30" style="display: none"></ul>
									<input id="tab-${vs.index + 1}-value" value="${cate.id}" type="hidden">
								</c:if>
								<c:if test="${cate.name eq '服务'}">
									<c:set var="count" value="${count + 1}"></c:set>
									<ul id="tab-${vs.index + 1}" class="ztree_supplier mt30" style="display: none"></ul>
									<input id="tab-${vs.index + 1}-value" value="${cate.id}" type="hidden">
								</c:if>
							</c:forEach>
						</div> --%>
						
						<div class="mt20" id="tbody_category"></div>
							<div id="pagediv" align="right" class="mb50"></div>
					</div>
					<div class="col-md-12 add_regist tc">
						<a class="btn" type="button" onclick="lastStep();">上一步</a>
						<c:if test="${expert.status == -2 ||  expert.status == 0 || (sign ==1 && expert.status ==9) || (sign ==3 && expert.status ==6) || expert.status ==4}">
	            <a class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="zancun();">暂存</a>
	          </c:if>
						<a class="btn" type="button" onclick="nextStep();">下一步</a>
					</div>
				</div>
			</div>
		</div>
		<input value="${expertId}" id="expertId" type="hidden">
		<form id="form_id" action="" method="post">
			<input name="expertId" value="${expertId}" type="hidden">
			<input name="sign" value="${sign}" type="hidden">
			<input name="batchId" value="${batchId}" type="hidden">
		</form>
        <input id="status" value="${status}" type="hidden">
	</body>

</html>