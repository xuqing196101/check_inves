<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="up" uri="/tld/upload"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
		<%@ include file="/WEB-INF/view/common/webupload.jsp"%>	
		<script type="text/javascript">
			$(function() {
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
			
			//类型审核
			function reason(auditFieldId,auditContent){
			  var expertId = $("#expertId").val();		
			  var appear = auditFieldId + "_show";
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
				      data:"suggestType=seven"+"&auditContent="+auditContent+"&auditReason="+text+"&expertId="+expertId+"&auditField="+auditContent +"&auditFieldId="+ auditFieldId+"&type=1",
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
				    $("#"+appear+"").css('visibility', 'visible');
		      layer.close(index);
			    });
		  	}
		  	
		  	//执业资格审核
				function reasonInput(obj,id,auditFieldName){
				  var expertId = $("#expertId").val();
				  var auditField;
				  var auditContent;
				  var html = "<div class='abolish'><img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></div>";
			    $("#"+obj.id+"").each(function() {
			      auditField = $(this).parents("li").find("span").text().replace("：","").trim();
	          auditContent = $(this).parents("li").find("input").val();
	    		});
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
					      data:"suggestType=seven"+"&auditContent="+auditContent+"&auditReason="+text+"&expertId="+expertId+"&auditField="+auditField+"&type=2"  +"&auditFieldId="+id + "&auditFieldName="+auditFieldName,
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
					   $("#"+obj.id+"").css('border-color','#FF0000');
							$(obj).after(html);
			      	layer.close(index);
				    });
			  	}
			  	
		  	//审核附件
		  	function reasonFile(obj,id,auditFieldName){
				  var expertId = $("#expertId").val();
				  var showId =  id+ "_" +obj.id;
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
					      data:"suggestType=seven"+"&auditContent="+auditContent+"&auditReason="+text+"&expertId="+expertId+"&auditField="+auditField+"&type=2"+"&auditFieldId="+id +"&auditFieldName="+auditFieldName,
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
		  	
		  	
		  	
		  	// 提示之前的信息
			function isCompare(inputName,fieldName, type){
				$.ajax({
					url: "${pageContext.request.contextPath}/expertAudit/getFieldContent.do",
					data: {"field":fieldName,"type":type,"expertId":"${expert.id}"},
					async: false,
					success: function(response){
						layer.tips("原值:" + response, "#" + inputName, {
		    				tips : 3
		    			});
					}
				});
			}
			
			//下一步
			function nextStep() {
				var action = "${pageContext.request.contextPath}/expertAudit/product.html";
				$("#form_id").attr("action", action);
				$("#form_id").submit();
			}

			//上一步
			function lastStep() {
				var action = "${pageContext.request.contextPath}/expertAudit/basicInfo.html";
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
		</script>
		<script type="text/javascript">
			function jump(str){
			  var action;
			  if(str == "basicInfo") {
					action = "${pageContext.request.contextPath}/expertAudit/basicInfo.html";
				}
			  if(str=="experience"){
			     action ="${pageContext.request.contextPath}/expertAudit/experience.html";
			  }
			  if(str=="product"){
			    action = "${pageContext.request.contextPath}/expertAudit/product.html";
			  }
			  if(str=="expertFile"){
			    action = "${pageContext.request.contextPath}/expertAudit/expertFile.html";
			  }
			  if(str=="reasonsList"){
			    action = "${pageContext.request.contextPath}/expertAudit/reasonsList.html";
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
							<a aria-expanded="false" href="#tab-1" data-toggle="tab">基本信息</a>
							<i></i>
						</li>
						<!-- <li onclick="jump('experience')">
							<a aria-expanded="false" href="#tab-1" data-toggle="tab">经历经验</a>
							<i></i>
						</li> -->
						<li class="active">
							<a aria-expanded="false" href="#tab-1" data-toggle="tab">专家类别</a>
							<i></i>
						</li>
						<li onclick="jump('product')">
							<a aria-expanded="false" href="#tab-1" data-toggle="tab">产品类别</a>
							<i></i>
						</li>
						<li onclick="jump('expertFile')">
							<a aria-expanded="false" href="#tab-1" data-toggle="tab">附件</a>
							<i></i>
						</li>
						<li onclick="jump('reasonsList')">
							<a aria-expanded="false" href="#tab-1" data-toggle="tab">审核汇总</a>
						</li>
					</ul>
					<!-- 专家专业信息 -->
					<ul class="ul_list count_flow">
						<li>
							<div>
								<c:forEach items="${spList}" var="sp">
									<span <c:if test="${fn:contains(editFields,sp.id)}">style="color:#FF8C00"</c:if>   class="margin-left-30 hand" onclick="reason('${sp.id}','${sp.name}技术');"><input type="checkbox"  disabled="disabled"  name="chkItem_1" value="${sp.id}" />${sp.name}技术 </span>
									<a class="b f18 ml10 red" id="${sp.id}_show" style="visibility:hidden"><img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
									<c:if test="${fn:contains(typeErrorField,sp.id)}"><img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></c:if> 
								</c:forEach>
								<c:forEach items="${jjList}" var="jj">
									<span  <c:if test="${fn:contains(editFields,jj.id)}">style="color:#FF8C00" </c:if>  class="margin-left-30 hand" onclick="reason('${jj.id}','${jj.name}');"><input type="checkbox"  disabled="disabled" name="chkItem_2"  value="${jj.id}" />${jj.name} </span>
									<a class="b f18 ml10 red" id="${jj.id}_show" style="visibility:hidden"><img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
									<c:if test="${fn:contains(typeErrorField,jj.id)}"> <img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></c:if>
								</c:forEach>
								
								<c:if test="${isProject eq 'project'}">
									<c:forEach items="${expertTitleList }" var="expertTitle" varStatus="vs">
										<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">执业资格职称：</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input class="hand" value="${expertTitle.qualifcationTitle}" readonly="readonly" id="${expertTitle.id}_qualifcationTitle" type="text" onclick="reasonInput(this,'${expertTitle.id}','qualifcationTitle');"  <c:if test="${fn:contains(engErrorField,expertTitle.id.concat('_qualifcationTitle'))}">style="border: 1px solid red;"</c:if>  <c:if test="${fn:contains(modifyFiled,expertTitle.id.concat('_qualifcationTitle'))}">style="border: 1px solid #FF8C00;" onMouseOver="showContent('qualifcationTitle','${expertTitle.id}');"</c:if>/>
											</div>
										</li>
										<li class="col-md-3 col-sm-6 col-xs-12">
											<span class="hand" <c:if test="${fn:contains(fileModify, expertTitle.id)}"> style="border: 1px solid #FF8C00;"</c:if> onmouseover="this.style.background='#E8E8E8'" onmouseout="this.style.background='#FFFFFF'" id="tieleFile" onclick="reasonFile(this,'${expertTitle.id}','tieleFile');">执业资格：</span>
				             	<up:show showId="expter_${vs.index+1 }" delete="false" businessId="${expertTitle.id}" sysKey="${expertKey}" typeId="9"/>
				          			<a style="visibility:hidden" id="${expertTitle.id}_tieleFile"><img style="padding-left: 10px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
				           			<c:if test="${fn:contains(engErrorField,expertTitle.id.concat('_tieleFile'))}"> <p><img style="padding-left: 125px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'></p></c:if>
				           	
				           	<li>
										<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">取得执业资格时间：</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input class="hand" value="<fmt:formatDate type='date' value='${expertTitle.titleTime}' dateStyle='default' pattern='yyyy-MM'/>" readonly="readonly" id="${expertTitle.id}_titleTime" type="text" onclick="reasonInput(this,'${expertTitle.id}','titleTime');" <c:if test="${fn:contains(engErrorField,expertTitle.id.concat('_titleTime'))}">style="border: 1px solid red;"</c:if>  <c:if test="${fn:contains(modifyFiled,expertTitle.id.concat('_titleTime'))}">style="border: 1px solid #FF8C00;" onMouseOver="showContent('titleTime','${expertTitle.id}');"</c:if>/>
											</div>
										</li>
										<div class="clear"></div>
									</c:forEach>
								</c:if>
							</div>
						</li>
					</ul>
				</div>
				<div class="col-md-12 add_regist tc">
					<a class="btn" type="button" onclick="lastStep();">上一步</a>
					<a class="btn" type="button" onclick="nextStep();">下一步</a>
				</div>
			</div>
		</div>
		<input value="${expert.id}" id="expertId" type="hidden">
		<form id="form_id" action="" method="post">
   	  <input name="expertId" value="${expert.id}" type="hidden">
   	  <input name="sign" value="${sign}" type="hidden">
    </form>
	</body>
</html>