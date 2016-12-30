<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
  <head>
    
    <title>My JSP 'expert_list.jsp' starting page</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">

</head>

<script type="text/javascript">
$(function idType(){

    $("#idType").find("option[value='${expert.idType}']").attr("selected",true);
    $("#expertsTypeId").find("option[value='${expert.expertsTypeId}']").attr("selected",true);

});
 
function sumbits(){
	alert("as");
	$.ajax({
        cache: true,
        type: "POST",
        url:"${pageContext.request.contextPath}/ExpExtract/AddtemporaryExpert.do",
        data:$('#form').serialize(),// 你的formid
        async: false,
        dataType:"json",
        error: function(request) {
        },
        success: function(data) {
        	alert("asd");
        	alert(data.sccuess);
            if(data.sccuess == "sccuess"){
            	alert(data.sccuess);
            	  window.location.href = '${pageContext.request.contextPath}/packageExpert/assignedExpert.html?projectId='+id;
            }
        }
    });
	}
</script>

<script type="text/javascript">
      function showExpertType() {
        var setting = {
          check: {
            enable: true,
            chkboxType: {
              "Y": "",
              "N": ""
            }
          },
          view: {
            dblClickExpand: false
          },
          data: {
            simpleData: {
              enable: true,
              idKey: "id",
              pIdKey: "parentId"
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
          url: "${pageContext.request.contextPath}/ExpExtract/projectType.do",
          dataType: "json",
          success: function(zNodes) {
            tree = $.fn.zTree.init($("#treeExpertType"), setting, zNodes);
            tree.expandAll(true); //全部展开
          }
        });
        var cityObj = $("#expertsTypeName");
        var cityOffset = $("#expertsTypeName").offset();
        $("#expertTypeContent").css({
          left: cityOffset.left + "px",
          top: cityOffset.top + cityObj.outerHeight() + "px"
        }).slideDown("fast");
        $("body").bind("mousedown", onBodyDownExpertType);
      }

      function onBodyDownExpertType(event) {
        if(!(event.target.id == "menuBtn" || $(event.target).parents("#expertTypeContent").length > 0)) {
          hideExpertType();
        }
      }

      function hideExpertType() {
        $("#expertTypeContent").fadeOut("fast");
        $("body").unbind("mousedown", onBodyDownExpertType);

      }

      function beforeClick(treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("treeExpertType");
        zTree.checkNode(treeNode, !treeNode.checked, null, true);
        return false;
      }

      function onCheck(e, treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("treeExpertType"),
          nodes = zTree.getCheckedNodes(true),
          v = "";
        var rid = "";
        for(var i = 0, l = nodes.length; i < l; i++) {
          v += nodes[i].name + ",";
          rid += nodes[i].id + ",";
        }
        if(v.length > 0) v = v.substring(0, v.length - 1);
        if(rid.length > 0) rid = rid.substring(0, rid.length - 1);
        var cityObj = $("#expertsTypeName");
        cityObj.attr("value", v);
        cityObj.attr("title", v);
        $("#expertsTypeId").val(rid);
        
      }
      
      function goBack(){
      	var projectId = $("#projectId").val();
  	    var flowDefineId = $("#flowDefineId").val();
	  	var path = "${pageContext.request.contextPath}/packageExpert/assignedExpert.html?projectId="+projectId+"&flowDefineId="+flowDefineId;
	  	$("#tab-1").load(path);
      }
    </script>

<body>

   

     <div id="expertTypeContent" class="expertTypeContent" style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
    <ul id="treeExpertType" class="ztree" style="margin-top:0;"></ul>
  </div>
<!-- 修改订列表开始-->
   <div class="container container_box">
   <sf:form id="form" action="${pageContext.request.contextPath}/ExpExtract/AddtemporaryExpert.do" method="post" modelAttribute="expert">
     <input type="hidden" value="${projectId}" name="projectId"/>
     <input type="hidden" value="${flowDefineId}" name="flowDefineId"/>
   <div>
    <h2 class="count_flow">添加临时专家</h2>
   <ul class="ul_list">
      <li class="col-md-3 col-sm-6 col-xs-12 pl15">
         <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">专家姓名：</span>
         <div class="input-append input_group col-sm-12 col-xs-12 p0">
         <input class="title col-md-12" maxlength="10" id="appendedInput" name="relName" value="${expert.relName}" type="text"/>
         <span class="add-on">i</span>
         <div class="cue"><sf:errors path="relName"/></div>
        </div>
	   </li>
	    <li class="col-md-3 col-sm-6 col-xs-12 ">
	      <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">证件类型：</span>
	        <div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
		        <select class="" name="idType"  id="idType">
		           <option value="">-请选择-</option>
		           <c:forEach items="${idType}" var="idtype">
		            <option value="${idtype.id}">${idtype.name}</option>
		           </c:forEach>
		         </select>
		        <div class="cue" ><sf:errors path="idType"/></div>
	       </div>
     </li>
     <li class="col-md-3 col-sm-6 col-xs-12 ">
        <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">证件号码：</span>
        <div class="input-append input_group col-sm-12 col-xs-12 p0">
         <input class="title col-md-12" id="appendedInput" name="idNumber" value="${expert.idNumber}" maxlength="18" type="text">
         <span class="add-on">i</span>
             <div class="cue" ><sf:errors path="idNumber"/></div>
        </div>
	 </li>
	  <li class="col-md-3 col-sm-6 col-xs-12 ">
	    <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">专家类别：</span>
	     <div class="input-append input_group col-sm-12 col-xs-12 p0">
	         <c:set value="${fn:split(expert.expertsTypeId,',')}" var="expertType"></c:set>
	        <c:set value="" var="typeId"></c:set>
                <c:forEach items="${expertType}" var="split">
                  <c:forEach var="project" items="${ddList}">
                   <c:if test="${split eq project.id}">
                    <c:set value="${typeId},${project.name}" var="typeId"></c:set>
                   </c:if>
                  </c:forEach>
                </c:forEach>
           <input   id="expertsTypeName" class="title col-md-12"  type="text" readonly name="expertsTypeName" value="${fn:substring(typeId,1,typeId.length() )}" onclick="showExpertType();" />
           <input type="hidden" name="expertsTypeId" id="expertsTypeId"  value="${expert.expertsTypeId}"  />
           <span class="add-on">i</span>
		   <div class="cue" ><sf:errors path="expertsTypeId"/></div>
       </div>
         
     </li>
     <li class="col-md-3 col-sm-6 col-xs-12 ">
        <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">现任职务：</span>
         <div class="input-append input_group col-sm-12 col-xs-12 p0">
          <input class="title col-md-12" id="appendedInput" name="atDuty" value="${expert.atDuty}" maxlength="10" type="text">
         <span class="add-on">i</span>
           <div class="cue" ><sf:errors path="atDuty"/></div>
        </div>
	 </li> 
<!--      <li class="col-md-3 col-sm-6 col-xs-12 "> -->
<!--        <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">联系地址：</span> -->
<!--         <div class="input-append input_group col-sm-12 col-xs-12 p0"> -->
<!--             <input class="title col-md-12" id="appendedInput" name="unitAddress" maxlength="20" type="text"> -->
<!--          <span class="add-on">i</span> -->
<!--             <div class="cue" id="unitAddressError">不能为空</div> -->
<!--         </div> -->
	   
<!-- 	 </li>  -->
     <li class="col-md-3 col-sm-6 col-xs-12 ">
       <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">联系电话：</span>
        <div class="input-append input_group col-sm-12 col-xs-12 p0">
             <input class="title col-md-12" id="appendedInput" name="mobile" value="${expert.mobile}"  maxlength="11" type="text">
         <span class="add-on">i</span>
          <div class="cue"><sf:errors path="mobile"/></div>
        </div>
	 </li> 
	  <li class="col-md-3 col-sm-6 col-xs-12 ">
	    <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">分配账户：</span>
	      <div class="input-append input_group col-sm-12 col-xs-12 p0">
              <input class="title col-md-12" id="appendedInput" name="loginName" value="${loginName}"  maxlength="11" type="text">
         <span class="add-on">i</span>
         <div class="cue" >${loginNameError}</div>
        </div>
     </li> 
      <li class="col-md-3 col-sm-6 col-xs-12 ">
        <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">分配密码：</span>
         <div class="input-append input_group col-sm-12 col-xs-12 p0">
          <input class="title col-md-12" id="appendedInput" name="loginPwd" value="${loginPwd}" maxlength="11" type="password">
          <span class="add-on">i</span>
          <div class="cue" >${loginPwdError}</div>
        </div>
     </li>  
      <li class="col-md-12 col-sm-12 col-xs-12">
       <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">备注</span>
       <div class="col-md-12 col-sm-12 col-xs-12 p0">
          <textarea class="col-md-12 col-sm-12 col-xs-12 h100" name="remarks"   maxlength="200"  title="不超过200个字" placeholder="不超过200个字">${expert.remarks}</textarea>
       </div>
       
     </li> 
   </ul>
   </div>
  <div  class="col-md-12">
   <div class="col-md-6" align="center">
      <button class="btn btn-windows save"  type="submit">保存</button>
      <button class="btn btn-windows back" type="button" onclick="goBack()">返回</button>
	</div>
  </div>
  </sf:form>
 </div>
</body>
</html>
