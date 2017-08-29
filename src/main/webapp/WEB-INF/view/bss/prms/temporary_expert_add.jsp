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
		var formData = $("#form").serialize();
	    formData = decodeURIComponent(formData, true);
	    $.ajaxSetup({cache:false});
		$("#tab-1").load("${pageContext.request.contextPath}/ExpExtract/AddtemporaryExpert.do",encodeURI(encodeURI(formData)));
	}
</script>

<script type="text/javascript">
      function showExpertType() {
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
              enable: true,
              idKey: "id",
              pIdKey: "parentId"
            }
          },
          callback: {
            beforeClick: beforeClickEP,
            onCheck: onCheckEP
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
          left: cityOffset.left + 16 + "px",
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

      function beforeClickEP(treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("treeExpertType");
        zTree.checkNode(treeNode, !treeNode.checked, null, true);
        return false;
      }

      function onCheckEP(e, treeId, treeNode) {
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
        hideExpertType();
        
      }
      
      function goBack(){
      	var projectId = $("#projectId").val();
  	    var flowDefineId = $("#flowDefineId").val();
	  	var path = "${pageContext.request.contextPath}/packageExpert/assignedExpert.html?projectId="+projectId+"&flowDefineId="+flowDefineId;
	  	$("#tab-1").load(path);
      }
    </script>
    
    <script type="text/javascript">
      function showPackageType() {
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
          url: "${pageContext.request.contextPath}/SupplierExtracts/getpackage.do?projectId=${projectId}",
          dataType: "json",
          success: function(zNodes) {
            tree = $.fn.zTree.init($("#treePackageType"), setting, zNodes);
            tree.expandAll(true); //全部展开
          }
        });
        var cityObj = $("#packageName");
        var cityOffset = $("#packageName").offset();
        $("#packageContent").css({
          left: cityOffset.left + 16 + "px",
          top: cityOffset.top + cityObj.outerHeight() + "px"
        }).slideDown("fast");
        $("body").bind("mousedown", onBodyDownPackageType);
      }

      function onBodyDownPackageType(event) {
        if(!(event.target.id == "menuBtn" || $(event.target).parents("#packageContent").length > 0)) {
          hidePackageType();
        }
      }

      function hidePackageType() {
        $("#packageContent").fadeOut("fast");
        $("body").unbind("mousedown", onBodyDownPackageType);

      }

      function beforeClick(treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("treePackageType");
        zTree.checkNode(treeNode, !treeNode.checked, null, true);
        return false;
      }

      function onCheck(e, treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("treePackageType"),
          nodes = zTree.getCheckedNodes(true),
          v = "";
        var rid = "";
        for(var i = 0, l = nodes.length; i < l; i++) {
          v += nodes[i].name + ",";
          rid += nodes[i].id + ",";
        }
        if(v.length > 0) v = v.substring(0, v.length - 1);
        if(rid.length > 0) rid = rid.substring(0, rid.length - 1);
        var cityObj = $("#packageName");
        cityObj.attr("value", v);
        cityObj.attr("title", v);
        $("#packageId").val(rid);
      }
      
      //验证身份证号唯一
      function yzCardNumber(){
        var cardNumber = $("#idCardNumber").val();
        isCardNo(cardNumber);
        $.ajax({
          url: "${pageContext.request.contextPath }/ExpExtract/yzCardNumber.do",
          data:{
            cardNumber : cardNumber
          },
          type: "POST", //请求方式           
          success: function(data) {
            if(data == "error"){
              $("#error_cardNumber").html("已被占用");
            }else{
              $("#error_cardNumber").html("");
              return;
            }
          }
        });
      }
      function isCardNo(card) {  
         // 身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X  
         var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;  
         if(reg.test(card) === false)  
         {  
             layer.msg("身份证输入不合法");  
             return  ;  
         }  
      } 
      
    //验证电话号码唯一
      function yzMobile(){
        var mobile = $("#mobile").val();
        $.ajax({
          url: "${pageContext.request.contextPath }/ExpExtract/yzCardNumber.do",
          data:{
            mobile : mobile
          },
          type: "POST", //请求方式           
          success: function(data) {
            if(data == "error"){
              $("#error_mobile").html("联系电话已存在");
            }else{
              $("#error_mobile").html("");
              return;
            }
          }
        });
      }
      
    </script>

<body>
  <body>
   <div id="packageContent" class="packageContent" style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
    <ul id="treePackageType" class="ztree" style="margin-top:0;"></ul>
  </div>
     <div id="expertTypeContent" class="expertTypeContent" style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
    <ul id="treeExpertType" class="ztree" style="margin-top:0;"></ul>
  </div>
<!-- 修改订列表开始-->
   <div class="">
   <sf:form id="form" action="${pageContext.request.contextPath}/ExpExtract/AddtemporaryExpert.do" method="post" modelAttribute="expert">
     <input type="hidden" value="${projectId}" name="projectId"/>
     <input type="hidden" value="${flowDefineId}" name="flowDefineId"/>
   <div>
    <h2 class="list_title">添加临时专家</h2>
   <ul class="ul_list">
      <li class="col-md-3 col-sm-6 col-xs-12 pl15">
         <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="star red">*</span>专家姓名：</span>
         <div class="input-append input_group col-sm-12 col-xs-12 p0">
         <input class="title col-md-12" maxlength="10" id="appendedInput" name="relName" value="${expert.relName}" type="text"/>
         <span class="add-on">i</span>
         <div class="cue"><sf:errors path="relName"/></div>
        </div>
	   </li>
<%-- 	    <li class="col-md-3 col-sm-6 col-xs-12 ">
	      <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="star red">*</span>证件类型：</span>
	        <div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
		        <select class="" name="idType"  id="idType">
		           <option value="">-请选择-</option>
		           <c:forEach items="${idType}" var="idtype">
		            <option value="${idtype.id}">${idtype.name}</option>
		           </c:forEach>
		         </select>
		        <div class="cue" ><sf:errors path="idType"/></div>
	       </div>
     </li> --%>
     <li class="col-md-3 col-sm-6 col-xs-12 ">
        <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="star red">*</span>居民身份证号码：</span>
        <div class="input-append input_group col-sm-12 col-xs-12 p0">
         <input class="title col-md-12" id="idCardNumber" name="idCardNumber" value="${expert.idCardNumber}" maxlength="18" type="text" onchange="yzCardNumber()">
         <span class="add-on">i</span>
             <div class="cue" id = "error_cardNumber"><sf:errors path="idCardNumber"/>${idCardNumberError}</div>
        </div>
	 </li>
	  <li class="col-md-3 col-sm-6 col-xs-12 ">
	    <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="star red">*</span>专家类别：</span>
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
        <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="star red">*</span>现任职务：</span>
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
       <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="star red">*</span>联系电话：</span>
        <div class="input-append input_group col-sm-12 col-xs-12 p0">
             <input class="title col-md-12" id="mobile" name="mobile" value="${expert.mobile}"  maxlength="11" type="text" onchange="yzMobile()">
         <span class="add-on">i</span>
          <div class="cue" id = "error_mobile">${mobile}</div>
        </div>
	 </li> 
	  <li class="col-md-3 col-sm-6 col-xs-12 ">
	    <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="star red">*</span>分配账户：</span>
	      <div class="input-append input_group col-sm-12 col-xs-12 p0">
              <input class="title col-md-12" id="appendedInput" name="loginName" maxlength="30" type="text">
         <span class="add-on">i</span>
         <div class="cue" >${loginNameError}</div>
        </div>
     </li>
      <li class="col-md-3 col-sm-6 col-xs-12 ">
        <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="star red">*</span>分配密码：</span>
         <div class="input-append input_group col-sm-12 col-xs-12 p0">
          <input class="title col-md-12" type="password" autocomplete="new-password" id="appendedInputPwd" name="loginPwd" minlength="6" maxlength="11" >
          <span class="add-on">i</span>
          <div class="cue" >${loginPwdError}</div>
        </div>
     </li>  
      <li class="col-md-3 col-sm-6 col-xs-12 ">
        <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="star red">*</span>添加包</span>
         <div class="input-append input_group col-sm-12 col-xs-12 p0">
            <input  class="title col-md-12" name="packageName" placeholder="请选择包"  readonly id="packageName" value="${packageName}" onclick="showPackageType();"   type="text">
              <input  readonly id="packageId" name="packageId" value="${packageId}"     type="hidden">
          <span class="add-on">i</span>
          <div class="cue" >${packageIdError}</div>
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
  <div  class="">
   <div class="" align="center">
      <button class="btn btn-windows save"  type="button" onclick="sumbits();" >保存</button>
      <button class="btn btn-windows back" type="button" onclick="goBack();">返回</button>
	</div>
  </div>
  </sf:form>
 </div>
</body>
</html>
