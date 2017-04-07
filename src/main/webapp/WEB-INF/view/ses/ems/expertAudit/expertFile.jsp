<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="up" uri="/tld/upload"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
			<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
		<script type="text/javascript">
			function jump(str) {
				var action;
				if(str == "basicInfo") {
					action = "${pageContext.request.contextPath}/expertAudit/basicInfo.html";
				}
				if(str == "experience") {
					action = "${pageContext.request.contextPath}/expertAudit/experience.html";
				}
				if(str=="expertType"){
			    action = "${pageContext.request.contextPath}/expertAudit/expertType.html";
			  }
				if(str == "product") {
					action = "${pageContext.request.contextPath}/expertAudit/product.html";
				}
				if(str == "reasonsList") {
					action = "${pageContext.request.contextPath}/expertAudit/reasonsList.html";
				}
				$("#form_id").attr("action", action);
				$("#form_id").submit();
			}
			
			
			//下一步
			function nextStep() {
				var action = "${pageContext.request.contextPath}/expertAudit/reasonsList.html";
				$("#form_id").attr("action", action);
				$("#form_id").submit();
			}

			//上一步
			function lastStep() {
				var action = "${pageContext.request.contextPath}/expertAudit/product.html";
				$("#form_id").attr("action", action);
				$("#form_id").submit();
			}
		</script>
		<script type="text/javascript">
			function reason(obj,str){
			  var expertId = $("#expertId").val();
			  var showId =  obj.id+"1";
		    $("#"+obj.id+"").each(function() {
		      auditField = $(this).parents("li").find("span").text().replace("：","");
    		});
    		var auditContent = auditField + "附件信息";
				var index = layer.prompt({
			    title : '请填写不通过的理由：', 
			    formType : 2, 
			    offset : '100px',
				}, 
		    function(text){
				    $.ajax({
				      url:"${pageContext.request.contextPath}/expertAudit/auditReasons.html",
				      type:"post",
				      dataType:"json",
				      data:"suggestType=five"+"&auditContent="+auditContent+"&auditReason="+text+"&expertId="+expertId+"&auditField="+auditField,
				      success:function(result){
				        result = eval("(" + result + ")");
				        if(result.msg == "fail"){
				           layer.msg('该条信息已审核过！', {	            
				             shift: 6, //动画类型
				             offset:'100px'
				          });
				        }
				      }
				    });
					 $("#"+showId+"").css('visibility', 'visible');
		      layer.close(index);
			    });
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
			<div class=" content height-350">
				<div class="col-md-12 tab-v2 job-content">
					<ul class="flow_step">
						<li onclick="jump('basicInfo')">
							<a aria-expanded="false" href="#tab-1" data-toggle="tab">基本信息</a><i></i>
						</li>
						<!-- <li onclick="jump('experience')">
							<a aria-expanded="false" href="#tab-1" data-toggle="tab">经历经验</a><i></i>
						</li> -->
						<li onclick="jump('expertType')">
							<a aria-expanded="false" href="#tab-1" data-toggle="tab">专家类别</a><i></i>
						</li>
						<li onclick="jump('product')">
							<a aria-expanded="false" href="#tab-1" data-toggle="tab">产品类别</a><i></i>
						</li>
						<li class="active">
							<a aria-expanded="false" href="#tab-1" data-toggle="tab">附件</a><i></i>
						</li>
						<li onclick="jump('reasonsList')">
							<a aria-expanded="false" href="#tab-1" data-toggle="tab">审核汇总</a>
						</li>
					</ul>
					
					<ul class="ul_list hand count_flow">
						<li class="col-md-6 p0 mt10 mb25">
							<span class="col-md-5 padding-left-5" onmouseover="this.style.background='#E8E8E8'" onmouseout="this.style.background='#FFFFFF'" id="application" onclick="reason(this);">军队评审专家承诺书：</span>
								<up:show showId="14" delete="false" businessId="${expertId}" sysKey="${expertKey}" typeId="14" />
								<a style="visibility:hidden" id="application1"><img style="padding-left: 10px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
								<c:if test="${fn:contains(conditionStr,'军队评审专家承诺书')}"> <img style="padding-left: 10px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'></c:if>
						</li>
						<li class="col-md-6 p0 mt10 mb25">
							<span class="col-md-5 padding-left-5" onmouseover="this.style.background='#E8E8E8'" onmouseout="this.style.background='#FFFFFF'" id="contract" onclick="reason(this);">军队评审专家入库申请表：</span>
								<up:show showId="13"  delete="false" businessId="${expertId}" sysKey="${expertKey}" typeId="13" />
								<a style="visibility:hidden" id="contract1"><img style="padding-left: 10px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
								<c:if test="${fn:contains(conditionStr,'军队评审专家入库申请表')}"> <img style="padding-left: 10px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'></c:if>
						</li>
					</ul>
				</div>
				<div class="col-md-12 add_regist tc">
					<a class="btn" type="button" onclick="lastStep();">上一步</a>
					<a class="btn" type="button" onclick="nextStep();">下一步</a>
				</div>
				
			</div>
		</div>

		<input value="${expertId}" id="expertId" type="hidden">

		<form id="form_id" action="" method="post">
			<input name="expertId" value="${expertId}" type="hidden">
			<input name="sign" value="${sign}" type="hidden">
		</form>
	</body>

</html>