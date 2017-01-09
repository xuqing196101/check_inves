<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
	<script type="text/javascript">
		/* 机构树 */
		
		function onClickOrg(e, treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("treeOrg");
			zTree.checkNode(treeNode, !treeNode.checked, null, true);
			return false;
		}
		function onCheckOrg(e, treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("treeOrg"),
			nodes = zTree.getCheckedNodes(true),
			v = "";
			for (var i=0, l=nodes.length; i<l; i++) {
				v += nodes[i].name + ",";
				$("#oId").val(nodes[i].id);
			}
			if (v.length > 0 ) v = v.substring(0, v.length-1);
			var cityObj = $("#orgSel");
			cityObj.attr("value", v);
			
			hideOrg();
		}
		function showOrg() {
		//	var typeName_id = $("#typeName_id").val();
			var orgType = $("#org_type").val();
			if (orgType == '3') {
				return;
			}
			var userId = $("#uId").val();
			var setting = {
				check: {
					enable: true,
					chkStyle: "radio",
					radioType: "all"
				},
				view: {
					dblClickExpand: false
				},
				data: {
					simpleData: {
						enable: true
					}
				},
				callback: {
					onClick: onClickOrg,
					onCheck: onCheckOrg
				}
			};
			$.ajax({
             type: "GET",
             async: false, 
             url: "${pageContext.request.contextPath}/user/getOrgTree.do?userId="+userId+"&orgType="+orgType,
             dataType: "json",
             success: function(zNodes){
                     for (var i = 0; i < zNodes.length; i++) { 
			            if (zNodes[i].isParent) {  
			  
			            } else {  
			                //zNodes[i].icon = "${ctxStatic}/images/532.ico";//设置图标  
			            }  
			        }  
			        tree = $.fn.zTree.init($("#treeOrg"), setting, zNodes);  
			       // tree.expandAll(true);//全部展开
               }
         	});
			var cityObj = $("#orgSel");
			var cityOffset = $("#orgSel").offset();
			$("#orgContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
			$("body").bind("mousedown", onBodyDownRole);
		}
		function hideOrg() {
			$("#orgContent").fadeOut("fast");
			$("body").unbind("mousedown", onBodyDownRole);
		}
		function onBodyDownRole(event) {
			if (!(event.target.id == "menuBtn" || event.target.id == "orgSel" || event.target.id == "orgContent" || $(event.target).parents("#orgContent").length>0)) {
				hideOrg();
			}
		}
		
		/* 角色 */
		function beforeClick(treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("treeRole");
			zTree.checkNode(treeNode, !treeNode.checked, null, true);
			return false;
		}
		
		function onCheck(e, treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("treeRole"),
			nodes = zTree.getCheckedNodes(true),
			v = "";
			var rid = "";
			for (var i=0, l=nodes.length; i<l; i++) {
				v += nodes[i].name + ",";
				rid += nodes[i].id + ",";
			}
			if (v.length > 0 ) v = v.substring(0, v.length-1);
			if (rid.length > 0 ) rid = rid.substring(0, rid.length-1);
			var cityObj = $("#roleSel");
			cityObj.attr("value", v);
			$("#rId").val(rid);
		}
		
		function showRole() {
			var userId = $("#uId").val();
			var setting = {
			check: {
					enable: true,
					chkboxType: {"Y":"", "N":""}
				},
				view: {
					dblClickExpand: false
				},
				data: {
					simpleData: {
						enable: true
					}
				},
				callback: {
					beforeClick: beforeClick,
					onCheck: onCheck
				}
			};
	        $.ajax({
             type: "GET",
             async: false, 
             url: "${pageContext.request.contextPath}/role/roletree.do?userId="+userId,
             dataType: "json",
             success: function(zNodes){
                     for (var i = 0; i < zNodes.length; i++) { 
			            if (zNodes[i].isParent) {  
			  
			            } else {  
			                //zNodes[i].icon = "${ctxStatic}/images/532.ico";//设置图标  
			            }  
			        }  
			        tree = $.fn.zTree.init($("#treeRole"), setting, zNodes);  
			        tree.expandAll(true);//全部展开
               }
         	});
			var cityObj = $("#roleSel");
			var cityOffset = $("#roleSel").offset();
			$("#roleContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
			$("body").bind("mousedown", onBodyDownOrg);
		}
		
		function hideRole() {
			$("#roleContent").fadeOut("fast");
			$("body").unbind("mousedown", onBodyDownOrg);
		}
		
		function onBodyDownOrg(event) {
			if (!(event.target.id == "menuBtn" || event.target.id == "roleSel" || event.target.id == "roleContent" || $(event.target).parents("#roleContent").length>0)) {
				hideRole();
			}
		}
		
		function goback(){
			
			var origin = $("input[name='origin']").val();
			var srcOrgId = $("input[name='orgId']").val();
			var deptTypeName = $("input[name='deptTypeName']").val();
			if (origin != null && origin != ""){
				window.location.href = '${pageContext.request.contextPath}/purchaseManage/list.html?srcOrgId='+srcOrgId + "&typeName=" + deptTypeName;
			} else {
				var currpage = $("#currpage").val();
				location.href = '${pageContext.request.contextPath}/user/list.html?page='+currpage;
			}
			
		}
		
		//控制显示输入框和下来框
		function viewOrgType(){
			//获取机构类型
			var orgType = $("#org_type").val();
			if (orgType == '3') {
				$("#oId").val("");
				$("#orgSel").hide();
				$("#oId").attr("type","text");
			} else {
				$("#orgSel").show();
				$("#oId").attr("type","hidden");
			}
		}
		
		//回显机构
		$(function(){
			var orgTypeName = "${user.typeName}";
			if (orgTypeName == '3') {
				$("#orgSel").hide();
				$("#oId").attr("type","text");
				$("#oId").val("${user.orgName}");
			}
		});
		
		function ajaxMoblie(){
			 var is_error = 0;
			 var mobile = $("#mobile").val();
			 var id = $("#uId").val();
			 $.ajax({
             type: "GET",
             async: false, 
             url: "${pageContext.request.contextPath}/user/ajaxMoblie.do?mobile="+mobile+"&id="+id,
             dataType: "json",
             success: function(data){
                     if (!data.success) {
						$("#ajax_mobile").html(data.msg);
						is_error = 1;
					 } else {
					 	$("#ajax_mobile").html("");
					 }
               }
         	});
         	return is_error;
		}
		
		function ajaxIdNumber(){
			 var is_error = 0;
			 var idNumber = $("#idNumber").val();
			 var id = $("#uId").val();
			 $.ajax({
             type: "GET",
             async: false, 
             url: "${pageContext.request.contextPath}/user/ajaxIdNumber.do?idNumber="+idNumber+"&id="+id,
             dataType: "json",
             success: function(data){
                     if (!data.success) {
						$("#ajax_idNumber").html(data.msg);
						is_error = 1;
					 } else {
					 	$("#ajax_idNumber").html("");
					 }
               }
         	});
         	return is_error;
		}
		
		$(document).ready(function(){  
    		$("#form1").bind("submit", function(){  
    			var error = 0;
    			if (ajaxIdNumber() == 1) {
					error += 1;
				} 
				if (ajaxMoblie() == 1){
					error += 1;
				} 
				if (error > 0) {
					return false;
				} else {
					return true;
				}
    		})
    	})
	</script>
</head>
<body>
   <c:if test="${empty  origin}">
     <!--面包屑导航开始-->
     <div class="margin-top-10 breadcrumbs ">
       <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">支撑系统</a></li><li><a href="#">后台管理</a></li><li class="active"><a href="#">用户管理</a></li><li class="active"><a href="#">修改用户</a></li>
		   </ul>
		<div class="clear"></div>
	   </div>
     </div>
   </c:if>
   
   
   <!-- 表单内容开始-->
   <div class="container container_box">
	   <div id="orgContent" class="orgContent" style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
			<ul id="treeOrg" class="ztree"></ul>
	   </div>
	   <div id="roleContent" class="roleContent" style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
			<ul id="treeRole" class="ztree mt0"></ul>
	   </div>
	   <sf:form id="form1" action="${pageContext.request.contextPath}/user/update.html" method="post" modelAttribute="user">
	   	  	<input type="hidden" name="origin"  value="${origin}"/>
	   	  	<input type="hidden" name="deptTypeName" value="${typeName}"/>
	   	   <div>
			    <h2 class="count_flow">修改用户</h2>
			    <input type="hidden" id="currpage" name="currpage" value="${currPage}">
			    <input class="span2" name="id" id="uId" type="hidden" value="${user.id}">
			   	<input class="span2" name="createdAt" type="hidden" value="<fmt:formatDate value='${user.createdAt}' pattern='yyyy-MM-dd  HH:mm:ss'/>">
			   	<input class="span2" name="isDeleted" type="hidden" value="${user.isDeleted}">
			   	<input class="span2" name="password" type="hidden" value="${user.password}">
			   	<input class="span2" name="password2" type="hidden" value="${user.password}">
			   	<input class="span2" name="randomCode" type="hidden" value="${user.randomCode}">
	   			<ul class="ul_list">
	   				<li class="col-md-3 col-sm-6 col-xs-12 pl15 col-lg-3">
					   <span class="col-md-12 col-sm-12 col-xs-12 col-lg-12 padding-left-5"><span class="star_red">*</span>用户名</span>
					   <div class="input-append input_group col-md-12 col-xs-12 col-sm-12 col-lg-12 p0">
				        <input id="loginName" name="loginName" readonly="readonly" value="${user.loginName }" maxlength="30" type="text">
				        <span class="add-on">i</span>
				       	<div class="cue"><sf:errors path="loginName"/></div>
				       	<div id="is_exist" class="cue">${exist }</div>
				       </div>
					</li>
					<li class="col-md-3 col-sm-6 col-xs-12 col-lg-3">
					    <span class="col-md-12 col-sm-12 col-xs-12 col-lg-12 padding-left-5"><span class="star_red">*</span>真实姓名</span>
					    <div class="input-append input_group col-md-12 col-xs-12 col-sm-12 col-lg-12 p0">
					        <input name="relName" value="${user.relName }" maxlength="10" type="text">
					        <span class="add-on">i</span>
					        <div class="cue"><sf:errors path="relName"/></div>
				       	</div>
				 	</li>
			 		<li class="col-md-3 col-sm-6 col-xs-12 col-lg-3 ">
					    <span class="col-md-12 col-sm-12 col-xs-12 col-lg-12 padding-left-5"><span class="star_red">*</span>性别</span>
				        <div class="select_common col-md-12 col-xs-12 col-sm-12 col-lg-12 p0">
				        <select id="gender" name="gender">
				        	<c:forEach items="${genders}" var="g" varStatus="vs">
				        		<option value="${g.id }" <c:if test="${g.id eq user.gender}">selected</c:if>>
				        			${g.name}
				        		</option>
				        	</c:forEach>
				        </select>
				        </div>
				 	</li>
			     	<li class="col-md-3 col-sm-6 col-xs-12 col-lg-3 ">
					    <span class="col-md-12 col-sm-12 col-xs-12 col-lg-12 padding-left-5"><span class="star_red">*</span>手机</span>
					    <div class="input-append input_group col-md-12 col-xs-12 col-sm-12 col-lg-12 p0" >
					        <input class="span5" id="mobile" name="mobile" value="${user.mobile }" maxlength="40" onblur="ajaxMoblie()" type="text">
					        <span class="add-on">i</span>
					        <div class="cue"><sf:errors path="mobile"/></div>
					        <div id="ajax_mobile" class="cue"></div>
				        </div>
				 	</li>
			        <li class="col-md-3 col-sm-6 col-xs-12 col-lg-3" >
					   	<span class="col-md-12 col-sm-12 col-xs-12 col-lg-12 padding-left-5">邮箱</span>
					   	<div class="input-append input_group col-md-12 col-xs-12 col-sm-12 col-lg-12 p0">
					        <input class="span5" name="email" value="${user.email }" maxlength="100" type="text">
					        <span class="add-on">i</span>
					        <div class="cue"><sf:errors path="email"/></div>
				       	</div>
				 	</li>
		     		<li class="col-md-3 col-sm-6 col-xs-12 col-lg-3">
					    <span class="col-md-12 col-sm-12 col-xs-12 col-lg-12 padding-left-5">职务：</span>
					    <div class="input-append input_group col-md-12 col-xs-12 col-sm-12 col-lg-12 p0">
					        <input class="span5" name="duties" value="${user.duties }" maxlength="40" type="text">
					        <span class="add-on">i</span>
				        </div>
			 		</li>
			 		<%-- <li class="col-md-3 col-sm-6 col-xs-12 col-lg-3 ">
					    <span class="col-md-12 col-sm-12 col-xs-12 col-lg-12 padding-left-5"><span class="star_red">*</span>类型</span>
					    <div class="select_common col-md-12 col-xs-12 col-sm-12 col-lg-12 p0">
					        <c:choose>
					        	<c:when test="${not empty origin}">
					        	  <select name="typeName" id="typeName_id">
					        	    <option value="${personTypeId}">${personTypeName}</option>
					        	  </select>
					        	</c:when>
					        	<c:otherwise>
					        		<select name="typeName" id="typeName_id">
					        		  <c:forEach items="${typeNames}" var="t" varStatus="vs">
						        		<c:if test="${t.code != 'SUPPLIER_U' && t.code != 'EXPERT_U' && t.code != 'IMP_SUPPLIER_U' && t.code != 'IMP_AGENT_U'}">
							        		<option value="${t.id }" <c:if test="${t.id eq user.typeName}">selected</c:if>>
												<c:if test="${'NEED_U' eq t.code}">需求人员</c:if>
												<c:if test="${'PURCHASER_U' eq t.code}">采购人员</c:if>
												<c:if test="${'PUR_MG_U' eq t.code}">采购管理人员</c:if>
												<c:if test="${'OTHER_U' eq t.code}">其他人员</c:if>
												<c:if test="${'SUPPLIER_U' eq t.code}">供应商</c:if>
												<c:if test="${'EXPERT_U' eq t.code}">专家</c:if>
												<c:if test="${'IMP_SUPPLIER_U' eq t.code}">进口供应商</c:if>
												<c:if test="${'IMP_AGENT_U' eq t.code}">进口代理商</c:if>
												<c:if test="${'SUPERVISER_U' eq t.code}">监督人员</c:if>
							        		</option>
						        		</c:if>
				        			</c:forEach>
					       		  </select>
					        	</c:otherwise>
					        </c:choose>
				        </div>
			 		</li> --%>
			 		<li class="col-md-3 col-sm-6 col-xs-12 col-lg-3">
				    <span class="col-md-12 col-sm-12 col-xs-12 col-lg-12 padding-left-5"><span class="star_red">*</span>发布类型</span>
			        <div class="select_common col-md-12 col-xs-12 col-sm-12 col-lg-12 p0">
			        <select id="publishType" name="publishType">
			        	<option value="0" <c:if test="${'0' eq user.publishType}">selected</c:if>>集中采购</option>
			        	<option value="1" <c:if test="${'1' eq user.publishType}">selected</c:if>>部队采购</option>
			        </select>
			        </div>
			 	 </li>
				 <li class="col-md-3 col-sm-6 col-xs-12 col-lg-3">
				    <span class="col-md-12 col-sm-12 col-xs-12 col-lg-12 padding-left-5">身份证号</span>
				    <div class="input-append input_group col-md-12 col-xs-12 col-sm-12 col-lg-12 p0">
			        	<input id="idNumber" name="idNumber" value="${user.idNumber}" onblur="ajaxIdNumber()" maxlength="20" type="text">
			        	<span class="add-on">i</span>
			        	<div id="ajax_idNumber" class="cue"></div>
			        </div>
				 </li>
				 <li class="col-md-3 col-sm-6 col-xs-12 col-lg-3">
				    <span class="col-md-12 col-sm-12 col-xs-12 col-lg-12 padding-left-5">军官证号</span>
				    <div class="input-append input_group col-md-12 col-xs-12 col-sm-12 col-lg-12 p0">
			        	<input  name="officerCertNo" value="${user.officerCertNo}"  maxlength="20" type="text">
			        	<span class="add-on">i</span>
			        </div>
				 </li>
				 <li class="col-md-3 col-sm-6 col-xs-12 col-lg-3 ">
					    <span class="col-md-12 col-sm-12 col-xs-12 col-lg-12 padding-left-5">座机电话</span>
					    <div class="input-append input_group col-md-12 col-xs-12 col-sm-12 col-lg-12 p0">
				        	<input class="span5" name="telephone" value="${user.telephone }" maxlength="40" type="text">
				        	<span class="add-on">i</span>
				        </div>
				    </li>
			 		<li class="col-md-3 col-sm-6 col-xs-12 col-lg-3">
					    <span class="col-md-12 col-sm-12 col-xs-12 col-lg-12 padding-left-5"><span class="star_red">*</span>机构类型</span>
				        <div class="select_common col-md-12 col-xs-12 col-sm-12 col-lg-12 p0">
					        <c:choose> 
						        <c:when  test="${not empty origin}">
						            <select id="org_type" name="typeName" >
							        	<option value="${typeName}" >
							        		<c:if test="${typeName == '1'}">采购机构</c:if>
							        		<c:if test="${typeName == '2'}">采购管理部门</c:if>
							        		<c:if test="${typeName == '0'}">需求部门</c:if>
							        		<c:if test="${typeName == '3'}">其他</c:if>
							        	</option>
							        </select>
						        </c:when >
						        <c:otherwise>
						        	<select id="org_type" name="typeName" onclick="viewOrgType()">
						        	<option value="1" <c:if test="${user.typeName == '1'}">selected</c:if>>采购机构</option>
						        	<option value="2" <c:if test="${user.typeName == '2'}">selected</c:if>>采购管理部门</option>
						        	<option value="0" <c:if test="${user.typeName == '0'}">selected</c:if>>需求部门</option>
						        	<option value="3" <c:if test="${user.typeName == '3'}">selected</c:if>>其他</option>	
						        </select>
						        </c:otherwise>
						    </c:choose>
					        <div class="cue"><sf:errors path="typeName"/></div>
				        </div>
				 	</li>
			 		<li class="col-md-3 col-sm-6 col-xs-12 col-lg-3">
					    <span class="col-md-12 col-sm-12 col-xs-12 col-lg-12 padding-left-5"><span class="star_red">*</span>所属机构</span>
					   	<div class="input-append input_group col-md-12 col-xs-12 col-sm-12 col-lg-12 p0">
						   	<input id="oId" name="orgId" type="hidden" value="${orgId}">
						   	<c:choose>
						   		<c:when test="${not empty origin}">
						   		   <input id="orgSel" class="span5" name="orgName" type="text" readonly value="${orgName}"  />
						   		</c:when>
						   		<c:otherwise>
						   			<input id="orgSel" class="span5" name="orgName" type="text" readonly value="${orgName}"  onclick="showOrg();" />
						   		</c:otherwise>
						   	</c:choose>
					        <div class="drop_up" onclick="showOrg();">
								   <img src="${pageContext.request.contextPath}/public/backend/images/down.png" class="margin-bottom-5"/>
					        </div>
					        <div class="cue"><sf:errors path="orgId"/></div>
				        </div>
			 		</li>
					<li class="col-md-3 col-sm-6 col-xs-12 col-lg-3 ">
					    <span class="col-md-12 col-sm-12 col-xs-12 col-lg-12 padding-left-5"><span class="star_red">*</span>角色</span>
					    <div class="input-append input_group col-md-12 col-xs-12 col-sm-12 col-lg-12 p0">
						   	<input id="rId" name="roleId" type="hidden" value="${roleId}">
					        <input id="roleSel" class="span5" name="roleName" type="text" readonly value="${roleName}"  onclick="showRole();" />
					        <div class="drop_up" onclick="showRole();">
							    <img src="${pageContext.request.contextPath}/public/backend/images/down.png" class="margin-bottom-5"/>					          
					        </div>
					        <div class="cue"><sf:errors path="roleId"/></div>
				        </div>
					 </li>
				     <li class="col-md-12 col-sm-12 col-xs-12 col-lg-12 ">
				 	   <span class="col-md-12 col-sm-12 col-xs-12 col-lg-12 padding-left-5">详细地址</span>
					   <div class="col-md-12 col-sm-12 col-xs-12 col-lg-12 p0">
				        	<textarea class="col-md-12 col-sm-12 col-xs-12 col-lg-12" style="height:130px" maxlength="100" name="address" title="不超过100个字">${user.address }</textarea>
				       </div>
				 	</li>
	   			</ul>
	  	  </div> 
	  	   <div class="col-md-12 col-sm-12 col-xs-12 col-lg-12  tc mt20" >
		    	<button class="btn btn-windows save" type="submit">更新</button>
		    	<button class="btn btn-windows back" onclick="goback()" type="button">返回</button>
		   </div>
  	   </sf:form>
 	</div>
</body>
</html>
