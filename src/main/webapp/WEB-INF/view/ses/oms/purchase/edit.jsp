<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE html PUBLIC>
<html class=" js cssanimations csstransitions" lang="en">
<head>
<%@ include file="/WEB-INF/view/common.jsp"%>
<%@ include file="/WEB-INF/view/common/webupload.jsp" %>
<link href="${pageContext.request.contextPath}/public/ztree/css/ztree-extend.css" type="text/css" rel="stylesheet" >
<script src="${pageContext.request.contextPath}/js/oms/purchase/jquery.metadata.js"></script>
<script src="${pageContext.request.contextPath}/js/oms/purchase/layer-extend.js"></script>
<script src="${pageContext.request.contextPath}/js/oms/purchase/select-tree.js"></script>

<script type="text/javascript">
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


   /** 返回 */
  function back(){
	  var origin = $("input[name='origin']").val();
	  if (origin != null && origin != ""){
		  //返回到采购机构
		  if (origin == "1"){
			  window.location.href = "${pageContext.request.contextPath}/purchaseManage/purchaseUnitList.html";
		  }
		  //返回到组织机构
		  if (origin == "2"){
			  var srcOrgId = $("select[name='orgId']").val();
			  window.location.href = "${pageContext.request.contextPath}/purchaseManage/list.html?srcOrgId=" +srcOrgId;
		  }
		  
	  } else {
		  //返回到采购人员管理
		  window.location.href = "${pageContext.request.contextPath}/purchase/list.html";
	  }
  }
	
	
	
</script>
</head>
<body>

  <!--面包屑导航开始-->
  <c:if test="${empty origin}">
    <div class="margin-top-10 breadcrumbs ">
	  <div class="container">
	    <ul class="breadcrumb margin-left-0">
		  <li><a href="javascript:void(0);"> 首页</a></li>
		  <li><a href="javascript:void(0);">支撑系统</a></li>
		  <li><a href="javascript:void(0);">采购人管理</a></li>
		  <li class="active"><a href="javascript:void(0);">修改采购人</a></li>
		</ul>
		<div class="clear"></div>
	  </div>
	</div>
  </c:if>
  
  <!-- 修改订列表开始-->
  <div class="container container_box">
	<div id="roleContent" class="roleContent" style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
	  <ul id="treeRole" class="ztree" style="margin-top:0;"></ul>
	</div>
	
	<sf:form action="${pageContext.request.contextPath}/purchase/update.html" method="post" id="formID" modelAttribute="purchaseInfo">
	  <input type="hidden" name="id"  value="${mainId}" />
	  <input type="hidden" name ="origin" value="${origin}"/>
	  <input type="hidden" name ="originOrgId" value="${originOrgId}"/>
	  <input  name="password" value="${purchaseInfo.password}"  type="hidden" />
	  <input  name="password2" value="${purchaseInfo.password}"  type="hidden" />
	  <input type="hidden" name = "typeName" value="1">
	  <div>
	    <h2 class="count_flow"><i>1</i>基本信息</h2>
		  <ul class="ul_list">
			<li class="col-md-3 col-sm-6 col-xs-12 pl15 col-lg-3">
			  <span class="col-md-12 col-sm-12 col-xs-12 col-lg-12 padding-left-5"><span class="star_red">*</span>用户名</span>
			  <div class="input-append input_group col-md-12 col-xs-12 col-sm-12 col-lg-12 p0">
			    <input  name="loginName" readonly="readonly" value="${purchaseInfo.loginName}" maxlength="30" type="text">
			    <span class="add-on">i</span>
			    <div class="cue"><sf:errors path="loginName"/></div>
                <div class="cue">${exist}</div>
			  </div>
			</li>
				
			<%-- <li class="col-md-3 col-sm-6 col-xs-12 col-lg-3">
			  <span class="col-md-12 col-sm-12 col-xs-12 col-lg-12 padding-left-5"><span class="star_red">*</span>类型</span>
			  <div class="select_common col-md-12 col-xs-12 col-sm-12 col-lg-12 p0">
			    <select name="typeName" id="typeName_id">
			      <c:forEach items="${purchaserTypeList}" var="dict" >
			        <option value="${dict.id}" <c:if test="${dict.id == purchaseInfo.typeName}"> selected="selected"</c:if> >${dict.name}</option>
			      </c:forEach>
			    </select>
			  </div>
			</li> --%>
			
			<li class="col-md-3 col-sm-6 col-xs-12 col-lg-3">
			  <span class="col-md-12 col-sm-12 col-xs-12 col-lg-12 padding-left-5"><span class="star_red">*</span>采购机构</span>
			  <div class="select_common col-md-12 col-xs-12 col-sm-12 col-lg-12 p0">
				<select name="orgId" id="typeName_id">
				  <c:forEach items="${purchaserOrgList}" var="org" >
				    <option value="${org.id}" <c:if test="${org.id == purchaseInfo.orgId}">selected="selected"</c:if>>${org.name}</option>
				  </c:forEach>
				</select>
			  </div>
			</li>
		     	
			<li class="col-md-3 col-sm-6 col-xs-12 col-lg-3">
			  <span class="col-md-12 col-sm-12 col-xs-12 col-lg-12 padding-left-5"><span class="star_red">*</span>角色</span>
			  <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 col-lg-12 p0">
				<input id="rId" name="roleId"  type="hidden" value="${purchaseInfo.roleId}">
			    <input id="roleSel"  type="text" name="roleName" readonly value="${roleName}"  onclick="showRole();" />
				<div class="drop_up" onclick="showRole();">
				  <img src="${pageContext.request.contextPath}/public/backend/images/down.png" class="margin-bottom-5"/>
				</div>
				<div class="cue"><sf:errors path="roleId"/></div>
			  </div>
			</li>
			
            <li class="col-md-3 col-sm-6 col-xs-12"> 
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">人员类别</span>
              <div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
                <select  name="purcahserType">
                  <option value="">-请选择-</option>
                  <option value="0" <c:if test="${purchaseInfo.purcahserType == '0' }">selected="true"</c:if>>军人</option>
                  <option value="1" <c:if test="${purchaseInfo.purcahserType == '1' }"> selected="selected"</c:if>>文职</option>
                  <option value="2" <c:if test="${purchaseInfo.purcahserType == '2' }"> selected="selected"</c:if>>职工</option>
                  <option value="3" <c:if test="${purchaseInfo.purcahserType == '3' }"> selected="selected"</c:if>>战士</option>
                </select>
              </div>
            </li>
            
            <li class="col-md-3 col-sm-6 col-xs-12"> 
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">职称</span>
              <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                <input class="input_group" name="professional" type="text" value="${ purchaseInfo.professional}"> 
                <span class="add-on">i</span>
              </div>
            </li>
            
			<li class="col-md-3 col-sm-6 col-xs-12"> 
			  <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">职务</span>
              <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                <input class="input_group" name="duties" type="text" value="${purchaseInfo.duties}"> 
                <span class="add-on">i</span>
              </div>
            </li> 
		  </ul>
		</div>
		
		<div class="padding-top-10 clear">
		  <h2 class="count_flow"><i>2</i>专业信息</h2>
		  <ul class="ul_list">
			<li class="col-md-3 col-sm-6 col-xs-12 pl15">  
			  <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">学历</span>
              <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                <input class="input_group" name="topStudy" value="${purchaseInfo.topStudy}" type="text"> 
                <span class="add-on">i</span>
              </div>
            </li>
            
            <li class="col-md-3 col-sm-6 col-xs-12 ">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">毕业院校</span>
              <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                <input class="input_group" name="graduateSchool" value="${purchaseInfo.graduateSchool}" type="text"> 
                <span class="add-on">i</span>
              </div>
            </li>
            
            <li class="col-md-12 col-sm-12 col-xs-12">
			  <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">工作经历</span>
			  <div class="col-md-12 col-sm-12 col-xs-12 p0">
				<textarea  class="w100p h130" name="workExperience" title="不超过800个字">${purchaseInfo.workExperience}</textarea>
			  </div>
			</li> 
			
			<li class="col-md-12 col-sm-12 col-xs-12 mt15">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">培训经历</span>
              <div class="col-md-12 col-sm-12 col-xs-12 p0">
                <textarea  class="w100p h130"  name="trainExperience" title="不超过800个字">${purchaseInfo.trainExperience}</textarea>
              </div>
            </li>
		  </ul>
		</div>
		
		<div class="padding-top-10 clear">
		  <h2 class="count_flow"><i>3</i>资质信息</h2>
		  <ul class="ul_list">
			<li class="col-md-3 col-sm-6 col-xs-12 pl15">  
			  <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">资质编号</span>
			  <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
				<input class="input_group" name="quaCode" value="${purchaseInfo.quaCode}" type="text"> 
				<span class="add-on">i</span>
			  </div>
			</li>
			
			<li class="col-md-3 col-sm-6 col-xs-12"> 
			  <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">采购资质范围</span>
			  <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
				<input class="input_group" name="quaRank" value="${purchaseInfo.quaRank}" type="text"> 
				<span class="add-on">i</span>
			  </div>
			</li>
			
			<li class="col-md-3 col-sm-6 col-xs-12"> 
			  <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>采购资质开始日期</span>
			  <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
			    <input class="input_group" type="text" readonly="readonly" onClick="WdatePicker()" name="quaStartDate" value="<fmt:formatDate value="${purchaseInfo.quaStartDate}" pattern="yyyy-MM-dd" />"  /> 
			  </div>
			</li>
			
			<li class="col-md-3 col-sm-6 col-xs-12"> 
			  <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>采购资质截止日期</span>
			  <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
				<input class="input_group" type="text" readonly="readonly" onClick="WdatePicker()" name="quaEdndate" value="<fmt:formatDate value="${purchaseInfo.quaEdndate}" pattern="yyyy-MM-dd" />"   /> 
			  </div>
			</li>
			
			<li class="col-md-3 col-sm-6 col-xs-12"> 
			  <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">采购资质等级</span>
			  <div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
				<select name="quaLevel">
				  <option value="">-请选择-</option>
                  <option value="0" <c:if test="${purchaseInfo.quaLevel == '0'}"> selected="selected"</c:if> >初</option>
                  <option value="1" <c:if test="${purchaseInfo.quaLevel == '1'}"> selected="selected"</c:if> >中</option>
                  <option value="2" <c:if test="${purchaseInfo.quaLevel == '2'}"> selected="selected"</c:if> >高</option>
				</select>
			  </div>
			</li>
			
			<li class="col-md-3 col-sm-6 col-xs-12">
			  <span class=""><span class="star_red">*</span>采购资格证书</span>
			  <div class="uploader orange m0">
				<u:upload id="purUploadId" auto="true" businessId="${mainId}" sysKey="2" />
				<u:show showId="purShowId" businessId="${mainId}" sysKey="2"/>
			  <div class="cue">${mainId_msg}</div>
			  </div>
			</li>
		  </ul>
		</div>
		
		<div class="padding-top-10 clear">
		  <h2 class="count_flow"><i>4</i>个人信息</h2>
			<ul class="ul_list">
			  <li class="col-md-3 col-sm-6 col-xs-12 pl15 col-lg-3">
				<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>姓名</span>
				<div class="input-append input_group col-md-12 col-xs-12 col-sm-12 col-lg-12 p0">
				  <input  name="relName" value="${purchaseInfo.relName}"  type="text">
				  <span class="add-on">i</span>
				  <div class="cue"><sf:errors path="relName"/></div>
				</div>
			  </li>
								 	 
			  <li class="col-md-3 col-sm-6 col-xs-12">
				<span class="col-md-12 col-sm-12 col-xs-12 col-lg-12 padding-left-5"><span class="star_red">*</span>性别</span>
				<div class="select_common col-md-12 col-xs-12 col-sm-12 col-lg-12 p0">
				  <select id="gender" name="gender">
				    <c:forEach items="${genders}" var="g" varStatus="vs">
					  <option value="${g.id }" <c:if test="${g.id eq purchaseInfo.gender}">selected</c:if>>
						<c:if test="${'M' eq g.code}">男</c:if>
						<c:if test="${'F' eq g.code}">女</c:if>
					  </option>
					</c:forEach>
				  </select>
				</div>
			  </li>
							     	
			  <li class="col-md-3 col-sm-6 col-xs-12"> 
				<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">民族</span>
				<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
				  <input class="input_group" name="nation" value="${purchaseInfo.nation}" type="text">
				  <span class="add-on">i</span>
				</div>
			  </li>
								    
			  <li class="col-md-3 col-sm-6 col-xs-12"> 
				<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">出生年月</span>
				<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
				  <input class="input_group" readonly="readonly" onClick="WdatePicker()" name="birthAt" value="<fmt:formatDate value="${purchaseInfo.birthAt}" pattern="yyyy-MM-dd" />" type="text">
				</div>
			  </li>
			  <li class="col-md-3 col-sm-6 col-xs-12 col-lg-3">
			    <span class="col-md-12 col-sm-12 col-xs-12 col-lg-12 padding-left-5"><span class="star_red">*</span>发布类型</span>
		        <div class="select_common col-md-12 col-xs-12 col-sm-12 col-lg-12 p0">
		        <select id="publishType" name="publishType">
		        	<option value="0" <c:if test="${'0' eq purchaseInfo.publishType}">selected</c:if>>集中采购</option>
		        	<option value="1" <c:if test="${'1' eq purchaseInfo.publishType}">selected</c:if>>部队采购</option>
		        </select>
		        </div>
		 	 </li>
			 <li class="col-md-3 col-sm-6 col-xs-12 col-lg-3">
			    <span class="col-md-12 col-sm-12 col-xs-12 col-lg-12 padding-left-5">军官证号</span>
			    <div class="input-append input_group col-md-12 col-xs-12 col-sm-12 col-lg-12 p0">
		        	<input  name="officerCertNo" value="${purchaseInfo.officerCertNo}"  maxlength="20" type="text">
		        	<span class="add-on">i</span>
		        </div>
			 </li>
			  <li class="col-md-3 col-sm-6 col-xs-12"> 
			    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>身份证号</span>
				<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
			      <input class="input_group" name="idCard" value="${purchaseInfo.idCard}" type="text"> 
				  <span class="add-on">i</span>
				  <div class="cue"><sf:errors path="idCard"/></div>
				  <div class="cue">${exist_idCard}</div>
				</div>
			  </li>
				                     
			  <li class="col-md-3 col-sm-6 col-xs-12"> 
				<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">政治面貌</span>
				<div class="select_common col-md-12 col-xs-12 col-sm-12 col-lg-12 p0">
				  <select name="political">
				    <c:forEach items="${politicals}" var="poli">
				      <option value="${poli.id}" <c:if test="${purchaseInfo.political == poli.id }"> selected="selected"</c:if>>${poli.name}</option>
				    </c:forEach>
				  </select>
				</div>
			  </li>
				                     
			  <li class="col-md-3 col-sm-6 col-xs-12 pl15">  
			    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>手机号码</span>
				<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
				  <input class="input_group" name="mobile" value="${purchaseInfo.mobile}" type="text"> 
				  <span class="add-on">i</span>
				  <div class="cue"><sf:errors path="mobile"/></div>
				</div>
			  </li>
			  
			  <li class="col-md-3 col-sm-6 col-xs-12"> 
			    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">办公号码</span>
				<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
				  <input class="input_group" name="telephone" value="${purchaseInfo.telephone}" type="text"> 
				  <span class="add-on">i</span>
				</div>
			  </li>
			  
			  <li class="col-md-3 col-sm-6 col-xs-12"> 
			    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">传真号码</span>
				<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
				  <input class="input_group" name="fax" value="${purchaseInfo.fax}" type="text"> 
				  <span class="add-on">i</span>
				</div>
			  </li>
			  
			  <li class="col-md-3 col-sm-6 col-xs-12 col-lg-3" >
				<span class="col-md-12 col-sm-12 col-xs-12 col-lg-12 padding-left-5"><div class="star_red">*</div>邮箱</span>
				<div class="input-append input_group col-md-12 col-xs-12 col-sm-12 col-lg-12 p0">
				  <input  name="email" value="${purchaseInfo.email}" maxlength="100" type="text">
				  <span class="add-on">i</span>
				  <div class="cue"><sf:errors path="email"/></div>
				</div>
			  </li>
			  
			  <li class="col-md-3 col-sm-6 col-xs-12"> 
			    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">邮政编码</span>
				<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
				  <input class="input_group" name="postCode" value="${purchaseInfo.postCode}" type="text"> 
				  <span class="add-on">i</span>
				</div>
			  </li>
			  
			  <li class="col-md-12 col-sm-12 col-xs-12"> 
			    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">联系地址</span>
				<div class="col-md-12 col-sm-12 col-xs-12 p0">
				  <textarea  class="w100p" title="不超过800个字" name="address" >${purchaseInfo.address}</textarea>
				</div>
			  </li>
			</ul>
		  </div>
		  
		  <!-- 伸缩层 -->
		  <div class="col-md-12">
            <div class="mt40 tc  mb50 ">
              <button type="submit" class="btn btn-windows save" >保存</button>
              <button type="button" class="btn btn-windows cancel" onclick="back();">取消</button>
            </div>
          </div>
		</sf:form>
      </div>
</body>
</html>
