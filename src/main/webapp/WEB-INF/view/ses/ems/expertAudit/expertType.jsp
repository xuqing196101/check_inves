<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="up" uri="/tld/upload"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
		<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/ses/ems/expertAudit/merge_jump.js"></script>
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
			
			//类型审核
			function reason(auditFieldId,auditContent){
				var status = ${expert.status};
				var sign = $("input[name='sign']").val();
				var html = "<div class='abolish'><img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></div>";
				//只能审核可以审核的状态
        if(status ==-2 || status == 0 || status == 15|| status == 16 || (sign ==1 && status ==9) || (sign ==3 && status ==6) || status ==4){
		    	var expertId = $("#expertId").val();   
		        var appear = auditFieldId + "_show";
		        var index = layer.prompt({
		          title : '请填写不通过的理由：', 
		          formType : 2, 
		          offset : '100px',
		          maxlength : '50',
		        }, 
		        function(text){
		          var text = trim(text);
		          if(text != null && text !=""){
		              $.ajax({
		                url:"${pageContext.request.contextPath}/expertAudit/auditReasons.html",
		                type:"post",
		                dataType:"json",
		                data:"suggestType=seven"+"&auditContent="+auditContent+"&auditReason="+text+"&expertId="+expertId+"&auditField="+auditContent +"&auditFieldId="+ auditFieldId+"&type=1" + "&auditFalg=" + sign,
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
		              
                  if(auditFieldId == "isTitle"){
                    $("#"+auditFieldId+"").after(html);
                    $("#" + auditFieldId + "").css('border-color', '#FF0000');
                  }
		          }else{
		            layer.msg('不能为空！', {offset:'100px'});
		          }
		        });
		      }
		  	}
		  	
		  	//执业资格审核
				function reasonInput(obj,id,auditFieldName){
					var status = ${expert.status};
	        var sign = $("input[name='sign']").val();
	        //只能审核可以审核的状态
          if(status ==-2 || status == 0 || status == 15|| status == 16 || (sign ==1 && status ==9) || (sign ==3 && status ==6) || status ==4){
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
						     maxlength : '50'
						}, 
				    function(text){
				    	var text = trim(text);
					  	if(text != null && text !=""){
							    $.ajax({
							      url:"${pageContext.request.contextPath}/expertAudit/auditReasons.html",
							      type:"post",
							      dataType:"json",
							      data:"suggestType=seven"+"&auditContent="+auditContent+"&auditReason="+text+"&expertId="+expertId+"&auditField="+auditField+"&type=2"  +"&auditFieldId="+id + "&auditFieldName="+auditFieldName + "&auditFalg=" + sign,
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
				      	}else{
				      		layer.msg('不能为空！', {offset:'100px'});
				      	}
					    });
	          }
			  	}
			  	
		  	//审核附件
		  	function reasonFile(obj,id,auditFieldName){
		  		var status = ${expert.status};
          var sign = $("input[name='sign']").val();
          //只能审核可以审核的状态
          if(status ==-2 || status == 0 || status == 15|| status == 16 || (sign ==1 && status ==9) || (sign ==3 && status ==6) || status ==4){
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
					     maxlength : '50'
						}, 
				    function(text){
				    	var text = trim(text);
					  	if(text != null && text !=""){
							    $.ajax({
							      url:"${pageContext.request.contextPath}/expertAudit/auditReasons.html",
							      type:"post",
							      dataType:"json",
							      data:"suggestType=seven"+"&auditContent="+auditContent+"&auditReason="+text+"&expertId="+expertId+"&auditField="+auditField+"&type=2"+"&auditFieldId="+id +"&auditFieldName="+auditFieldName + "&auditFalg=" + sign,
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
				       }else{
				       	layer.msg('不能为空！', {offset:'100px'});
				       }
					  });
          }
		  	}
		  	
		  	
		  	
		  	// 提示之前的信息
			function isCompare(inputName,fieldName, type){
				$.ajax({
					url: "${pageContext.request.contextPath}/expertAudit/getFieldContent.do",
					data: {"field":fieldName,"type":type,"expertId":"${expert.id}"},
					async: false,
					success: function(response){
						layer.tips("修改前:" + response, "#" + inputName, {
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
		<div class="container container_box">
			<div class=" content">
				<div class="col-md-12 col-sm-12 col-xs-12 tab-v2 job-content">
                    <%@include file="/WEB-INF/view/ses/ems/expertAudit/common_jump.jsp" %>
					<!-- 专家专业信息 -->
					<ul class="ul_list count_flow">
						<li class="mb10">
						
								<c:forEach items="${spList}" var="sp">
									<span <c:if test="${fn:contains(editFields,sp.id)}">style="color:#FF8C00"</c:if>   class="margin-left-20 hand" <c:if test="${fn:contains(expertType,sp.id)}">onclick="reason('${sp.id}','${sp.name}技术');"</c:if>><input type="checkbox"  disabled="disabled"  name="chkItem_1" value="${sp.id}" />${sp.name}技术 </span>
									<a class="b f18 ml10 red" id="${sp.id}_show" 
									<c:choose>
	                  <c:when test="${fn:contains(typeErrorField,sp.id)}">style="visibility:initial"</c:when>
	                  <c:otherwise>style="visibility:hidden"</c:otherwise>
	                </c:choose>>
									<img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
								</c:forEach>
								<c:forEach items="${jjList}" var="jj">
									<span  <c:if test="${fn:contains(editFields,jj.id)}">style="color:#FF8C00" </c:if>  class="margin-left-20 hand" <c:if test="${fn:contains(expertType,jj.id)}">onclick="reason('${jj.id}','${jj.name}');"</c:if>><input type="checkbox"  disabled="disabled" name="chkItem_2"  value="${jj.id}" />${jj.name} </span>
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
	                  <input readonly="readonly" value="有" type="text" id="isTitle" onclick="reason('isTitle','有无执业资格');" <c:if test="${fn:contains(typeErrorField,'isTitle')}"> style="border: 1px solid red;"</c:if>>
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
												<input class="hand" value="${expertTitle.qualifcationTitle}" readonly="readonly" id="${expertTitle.id}_qualifcationTitle" type="text" onclick="reasonInput(this,'${expertTitle.id}','qualifcationTitle');"  <c:if test="${fn:contains(engErrorField,expertTitle.id.concat('_qualifcationTitle'))}">style="border: 1px solid red;"</c:if>  <c:if test="${fn:contains(modifyFiled,expertTitle.id.concat('_qualifcationTitle'))}">style="border: 1px solid #FF8C00;" onMouseOver="showContent('qualifcationTitle','${expertTitle.id}');"</c:if>/>
												<c:if test="${fn:contains(engErrorField,expertTitle.id.concat('_qualifcationTitle'))}">
													<div class='abolish'><img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></div>
												</c:if>
										</li>
										<li class="col-md-4 col-sm-6 col-xs-12">
											<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5 hand" <c:if test="${fn:contains(fileModify, expertTitle.id)}"> style="border: 1px solid #FF8C00;"</c:if> onmouseover="this.style.background='#E8E8E8'" onmouseout="this.style.background='#FFFFFF'" id="tieleFile" onclick="reasonFile(this,'${expertTitle.id}','tieleFile');">执业资格：</span>
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
												<input class="hand" value="<fmt:formatDate type='date' value='${expertTitle.titleTime}' dateStyle='default' pattern='yyyy-MM'/>" readonly="readonly" id="${expertTitle.id}_titleTime" type="text" onclick="reasonInput(this,'${expertTitle.id}','titleTime');" <c:if test="${fn:contains(engErrorField,expertTitle.id.concat('_titleTime'))}">style="border: 1px solid red;"</c:if>  <c:if test="${fn:contains(modifyFiled,expertTitle.id.concat('_titleTime'))}">style="border: 1px solid #FF8C00;" onMouseOver="showContent('titleTime','${expertTitle.id}');"</c:if>/>
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
				<div class="col-md-12 add_regist tc">
					<a class="btn" type="button" onclick="lastStep();">上一步</a>
					<c:if test="${expert.status == -2 ||  expert.status == 0 || (sign ==1 && expert.status ==9) || (sign ==3 && expert.status ==6) || expert.status ==4}">
					  <a class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="zancun();">暂存</a>
					</c:if>
					<a class="btn" type="button" onclick="nextStep();">下一步</a>
				</div>
			</div>
		</div>
		<input value="${expert.id}" id="expertId" type="hidden">
		<form id="form_id" action="" method="post">
   	  <input name="expertId" value="${expert.id}" type="hidden">
   	  <input name="sign" value="${sign}" type="hidden">
   	  <input name="batchId" value="${batchId}" type="hidden">
    </form>
        <input id="status" value="${expert.status}" type="hidden">
	</body>
</html>