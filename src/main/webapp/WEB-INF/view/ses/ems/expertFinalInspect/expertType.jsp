<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="up" uri="/tld/upload"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
		<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/ses/ems/expertFinalInspect/merge_jump.js"></script>
		<script type="text/javascript">
			$(function() {
			    // 导航栏显示
                $("#reverse_of_two").attr("class","active");
                $("#reverse_of_two").removeAttr("onclick");

				var typeIds = "${expert.expertsTypeId}";
				var ids = typeIds.split(",");
				//回显
				var checklist1 = document.getElementsByName("chkItem_1");
				for(var i = 0; i < checklist1.length; i++) {
					var vals = checklist1[i].value;
					for(var j = 0; j < ids.length; j++) {
						if(ids[j] == vals) {
							checklist1[i].checked = true;
						}
					}
				}
				var checklist2 = document.getElementsByName("chkItem_2");
				for(var i = 0; i < checklist2.length; i++) {
					var vals = checklist2[i].value;
					for(var j = 0; j < ids.length; j++) {
						if(ids[j] == vals) {
							checklist2[i].checked = true;
						}
					}
				}
			});
			
			function trim(str){ //删除左右两端的空格
				return str.replace(/(^\s*)|(\s*$)/g, "");
			}
			
			
			//下一步
			function nextStep() {
				var action = "${pageContext.request.contextPath}/finalInspect/product.html";
				$("#form_id").attr("action", action);
				$("#form_id").submit();
			}

			//上一步
			function lastStep() {
				var action = "${pageContext.request.contextPath}/finalInspect/basicInfo.html";
				$("#form_id").attr("action", action);
				$("#form_id").submit();
			}
			
			
			// 提示之前的信息
			function showContent(field, id) {
				 var expertId = $("#expertId").val();
				$.ajax({
					url: "${pageContext.request.contextPath}/expertAudit/showModify.do",
					data: {"expertId":expertId, "field":field, "relationId":id},
					async: false,
					success: function(result) {
						layer.tips("修改前:" + result, "#" + id + "_" + field, {
							tips: 3
						});
					}
				});
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
		</script>
	</head>

	<body>
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
		<div class="container container_box pb70">
			<div class=" content">
				<div class="col-md-12 col-sm-12 col-xs-12 tab-v2 job-content">
                    <%@include file="/WEB-INF/view/ses/ems/expertFinalInspect/common_jump.jsp" %>
					<!-- 专家专业信息 -->
					<ul class="ul_list count_flow">
						<li class="mb10">
						
								<c:forEach items="${spList}" var="sp">
									<span <c:if test="${fn:contains(editFields,sp.id)}">style="color:#FF8C00"</c:if>   class="margin-left-20 hand" ><input type="checkbox"  disabled="disabled"  name="chkItem_1" value="${sp.id}" />${sp.name}技术 </span>
									<a class="b f18 ml10 red" id="${sp.id}_show" 
									<c:choose>
	                  <c:when test="${fn:contains(typeErrorField,sp.id)}">style="visibility:initial"</c:when>
	                  <c:otherwise>style="visibility:hidden"</c:otherwise>
	                </c:choose>>
									<img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
								</c:forEach>
								<c:forEach items="${jjList}" var="jj">
									<span  <c:if test="${fn:contains(editFields,jj.id)}">style="color:#FF8C00" </c:if>  class="margin-left-20 hand" ><input type="checkbox"  disabled="disabled" name="chkItem_2"  value="${jj.id}" />${jj.name} </span>
									<a class="b f18 ml10 red" id="${jj.id}_show" 
									 <c:choose>
                    <c:when test="${fn:contains(typeErrorField,jj.id)}">style="visibility:initial"</c:when>
                    <c:otherwise>style="visibility:hidden"</c:otherwise>
                  </c:choose>>
                  <img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
								</c:forEach>
							</li>
				<c:if test="${'1' eq isShow }">
						  <li class="col-md-4 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">有无执业资格:</span>
						    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
							    <c:if test="${expert.isTitle eq '2'}">
							      <input readonly="readonly" value="无" type="text" id="isTitle" onclick="reason('isTitle','有无执业资格');" <c:if test="${fn:contains(typeErrorField,'isTitle')}"> style="border: 1px solid red;"</c:if> >
							    </c:if>
	                  
	                <c:if test="${expert.isTitle eq '1'}">
	                  <input readonly="readonly" value="有" type="text" id="isTitle" >
	                </c:if>
	                <c:if test="${fn:contains(typeErrorField,'isTitle')}">
	                   <div class='abolish'><img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></div>
	                </c:if>
				</c:if>
                </div>
						  </li>
							<li class="clear"></li>
								<c:if test="${isProject eq 'project' and expert.isTitle eq '1'}">
									<c:forEach items="${expertTitleList }" var="expertTitle" varStatus="vs">
										<li class="col-md-4 col-sm-6 col-xs-12">
											<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">执业资格职称：</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input class="hand" value="${expertTitle.qualifcationTitle}" readonly="readonly" id="${expertTitle.id}_qualifcationTitle" type="text"   <c:if test="${fn:contains(engErrorField,expertTitle.id.concat('_qualifcationTitle'))}">style="border: 1px solid red;"</c:if>  <c:if test="${fn:contains(modifyFiled,expertTitle.id.concat('_qualifcationTitle'))}">style="border: 1px solid #FF8C00;" onMouseOver="showContent('qualifcationTitle','${expertTitle.id}');"</c:if>/>
												<c:if test="${fn:contains(engErrorField,expertTitle.id.concat('_qualifcationTitle'))}">
													<div class='abolish'><img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></div>
												</c:if>
										</li>
										<li class="col-md-4 col-sm-6 col-xs-12">
											<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5 hand" <c:if test="${fn:contains(fileModify, expertTitle.id)}"> style="border: 1px solid #FF8C00;"</c:if> onmouseover="this.style.background='#E8E8E8'" onmouseout="this.style.background='#FFFFFF'" id="tieleFile">执业资格：</span>
				             				<up:show showId="expter_${vs.index+1 }" delete="false" businessId="${expertTitle.id}" sysKey="${expertKey}" typeId="9"/>
				          					<a 
				          					<c:choose>
					                    <c:when test="${fn:contains(engErrorField,expertTitle.id.concat('_tieleFile'))}">style="visibility:initial"</c:when>
					                    <c:otherwise>style="visibility:hidden"</c:otherwise>
					                  </c:choose>
					                   id="${expertTitle.id}_tieleFile"><img style="padding-left: 10px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
				           				</li>
										<li class="col-md-4 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">取得执业资格时间：</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input class="hand" value="<fmt:formatDate type='date' value='${expertTitle.titleTime}' dateStyle='default' pattern='yyyy-MM'/>" readonly="readonly" id="${expertTitle.id}_titleTime" type="text" <c:if test="${fn:contains(engErrorField,expertTitle.id.concat('_titleTime'))}">style="border: 1px solid red;"</c:if>  <c:if test="${fn:contains(modifyFiled,expertTitle.id.concat('_titleTime'))}">style="border: 1px solid #FF8C00;" onMouseOver="showContent('titleTime','${expertTitle.id}');"</c:if>/>
												<c:if test="${fn:contains(engErrorField,expertTitle.id.concat('_titleTime'))}">
													<div class='abolish'><img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></div>
												</c:if>
											</div>
										</li>
										<div class="clear"></div>
									</c:forEach>
								</c:if>
					</ul>
				</div>
				<div class="col-md-12 add_regist tc m_btn_ab">
					<a class="btn" type="button" onclick="lastStep();">上一步</a>
					<a class="btn" type="button" onclick="nextStep();">下一步</a>
					<a class="btn" type="button" onclick="jump('expertAttachment')">转至复查</a>
				</div>
			</div>
		</div>
		<input value="${expert.id}" id="expertId" type="hidden">
		<form id="form_id" action="" method="post">
   	  <input name="expertId" value="${expert.id}" type="hidden">
   	  <input name="sign" value="${sign}" type="hidden">
   	  <input name="batchId" value="${batchId}" type="hidden">
   	  <input name="isReviewRevision" value="${isReviewRevision}" type="hidden">
   	  <input name="isCheck" value="${isCheck}" type="hidden">
   	  <input id="over" name="over" value="${over}" type="hidden">
   	   <input id="notCount" name="notCount" value="${notCount}" type="hidden">
   	  <input id="finalInspectNumber" name="finalInspectNumber" value="${expert.finalInspectCount==null?1:expert.finalInspectCount+1}" type="hidden">
    </form>
        <input id="status" value="${expert.status}" type="hidden">
	</body>
</html>