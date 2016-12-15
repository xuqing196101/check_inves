<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="../../../common.jsp"%>
<%@ taglib prefix="up" uri="/tld/upload"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

	<head>
		<title>审核汇总</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<script type="text/javascript">

		  $(function() {
				//审核按钮状态
				var num = ${num};
				if(num == 0) {
					$("#tuihui").attr("disabled", true);
				}
				if(num != 0) {
					$("#tongguo").attr("disabled", true);
				};
			});

			//提交审核
			function shenhe(status) {
				if(status == 3){
					updateStepNumber("one");
				}
				$("#status").val(status);
				$("#form_shenhe").submit();
			}
			
			function updateStepNumber(stepNumber){
				$.ajax({
					url:"${pageContext.request.contextPath}/expert/updateStepNumber.do",
					data:{"expertId":$("#id").val(),"stepNumber":stepNumber},
					async:false,
				});
			}
			
			/** 全选全不选 */
			function selectAll() {
				var checklist = document.getElementsByName("chkItem");
				var checkAll = document.getElementById("checkAll");
				if(checkAll.checked) {
					for(var i = 0; i < checklist.length; i++) {
						checklist[i].checked = true;
					}
				} else {
					for(var j = 0; j < checklist.length; j++) {
						checklist[j].checked = false;
					}
				}
			}

		  //移除
	    function dele(){
	  		var expertId = $("input[name='expertId']").val();
				var ids =[];
				$('input[name="chkItem"]:checked').each(function(){ 
			  	ids.push($(this).val());
				});
				if(ids.length>0){
			      layer.confirm('您确定要移除吗?', {title:'提示！',offset: ['200px']}, function(index){
				    layer.close(index);
				      $.ajax({
				        url:"${pageContext.request.contextPath}/expertAudit/deleteByIds.html",
				        data:"ids="+ids,
				        type:"post",
				        dataType:"json",
								success:function(result){
										result = eval("(" + result + ")");
										if(result.msg == "yes"){
											layer.msg("删除成功!",{offset : '100px'});
					       			window.setTimeout(function(){
					       				var action = "${pageContext.request.contextPath}/expertAudit/reasonsList.html";
							    			$("#form_id").attr("action",action);
							    			$("#form_id").submit();
					       			}, 1000);
									}
		       			},
		       				error: function(){
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
			function jump(str) {
				var action;
				if(str == "basicInfo") {
					action = "${pageContext.request.contextPath}/expertAudit/basicInfo.html";
				}
				if(str == "experience") {
					action = "${pageContext.request.contextPath}/expertAudit/experience.html";
				}
				if(str=="expertFile"){
			    action = "${pageContext.request.contextPath}/expertAudit/expertFile.html";
			  }
				if(str == "product") {
					action = "${pageContext.request.contextPath}/expertAudit/product.html";
				}
				$("#form_id").attr("action", action);
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
						<a href="javascript:void(0)">首页</a>
					</li>
					<li>
						<a href="javascript:void(0)">支撑系统</a>
					</li>
					<li>
						<a href="javascript:void(0)">专家管理</a>
					</li>
					<li>
						<a href="javascript:void(0)">专家审核</a>
					</li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
		<div class="container container_box">
			<div class="content">
				<div class="col-md-12 tab-v2 job-content">
					<ul class="nav nav-tabs bgdd">
						<li onclick="jump('basicInfo')">
							<a aria-expanded="false" href="#tab-1" data-toggle="tab">基本信息</a><i></i>
						</li>
						<li onclick="jump('experience')">
							<a aria-expanded="false" href="#tab-1" data-toggle="tab">经历经验</a><i></i>
						</li>
						<li onclick="jump('product')">
							<a aria-expanded="false" href="#tab-1" data-toggle="tab">产品目录</a><i></i>
						</li>
						<li onclick="jump('expertFile')">
							<a aria-expanded="false" href="#tab-1" data-toggle="tab">附件</a><i></i>
						</li>
						<li class="active">
							<a aria-expanded="false" href="#tab-1" data-toggle="tab">审核汇总</a>
						</li>
					</ul>
					<ul class="ul_list count_flow">
						<button class="btn btn-windows delete" type="button" onclick="dele();" style=" border-bottom-width: -;margin-bottom: 7px;">移除</button>
						<table class="table table-bordered table-condensed table-hover">
							<thead>
								<tr>
									<th class="info w30"><input type="checkbox" onclick="selectAll();"  id="checkAll"></th>
									<th class="info w50">序号</th>
									<th class="info">审批类型</th>
									<th class="info">审批字段</th>
									<th class="info">审批内容</th>
									<th class="info">不通过理由</th>
								</tr>
							</thead>
							<c:forEach items="${reasonsList }" var="reasons" varStatus="vs">
								<input id="auditId" value="${reasons.id}" type="hidden">
								<tr>
									<td class="tc w30"><input type="checkbox" value="${reasons.id }" name="chkItem"  id="${reasons.id}"></td>
									<td class="tc">${vs.index + 1}</td>
									<td class="tc">
										<c:if test="${reasons.suggestType eq 'one'}">基本信息</c:if>
										<c:if test="${reasons.suggestType eq 'two'}">经历经验</c:if>
										<c:if test="${reasons.suggestType eq 'six'}">产品目录</c:if>
										<c:if test="${reasons.suggestType eq 'five'}">附件</c:if>
									</td>
									<td class="tc">${reasons.auditField }</td>
									<td class="tc">${reasons.auditContent}</td>
									<td class="tc">${reasons.auditReason}</td>
								</tr>
							</c:forEach>
						</table>
					</ul>
					<div class="col-md-12 add_regist tc">
						<form id="form_shenhe" action="${pageContext.request.contextPath}/expertAudit/updateStatus.html" >
							<input name="id" value="${expertId}" type="hidden">
							<input type="hidden" name="status" id="status" />
								<div class="col-md-12 add_regist tc">
									<div class="col-md-12 add_regist tc">
										<c:if test="${status == 0 }">
											<input class="btn btn-windows git" type="button" onclick="shenhe(1);" value="初审通过 " id="tongguo">
											<input class="btn btn-windows reset" type="button" onclick="shenhe(2);" value="初审不通过">
											<input class="btn btn-windows reset" type="button" onclick="shenhe(3);" value="退回" id="tuihui">
										</c:if>
										<c:if test="${status == 1 }">
											<input class="btn btn-windows git" type="button" onclick="shenhe(4);" value="复审通过 " id="tongguo">
											<input class="btn btn-windows edit" type="button" onclick="shenhe(5);" value="踢出">
											<!-- <input class="btn btn-windows reset" type="button" onclick="shenhe(6);" value="退回" id="tuihui"> -->
										</c:if>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
			<input value="${expertId}" id="expertId" type="hidden">
		<form id="form_id" action="" method="post">
			<input name="expertId" value="${expertId}" type="hidden">
		</form>
	</body>
</html>