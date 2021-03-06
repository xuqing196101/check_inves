<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]--><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<title></title>
</head>

<script type="text/javascript">

/* function sumbits(){
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
	} */
	
function sumbits(){
		$.ajaxSetup({cache:false});
		var formData = $("#form").serialize();
	    formData = decodeURIComponent(formData, true);
	  $("#tab-1").load("${pageContext.request.contextPath}/SupplierExtracts/AddtemporarySupplier.do",encodeURI(encodeURI(formData)));

	  }
	  
	/**返回*/
	function onback(){
			$.ajaxSetup({cache:false});
		  var path = '${pageContext.request.contextPath}/saleTender/view.html?projectId=${projectId}&ix=${ix}';
	         $("#tab-1").load(path);
	}	
	/*统一社会信用代码的唯一验证  */
	function referenceNO(){
        var creditCode = $("input[name='creditCode']").val();
        var supplier="${supplier}"
        if(creditCode == ''){
        	layer.msg("统一社会信用代码不能为空");
            return;
        }        
        $.ajax({
            url: '${pageContext.request.contextPath}/SupplierExtracts/selectUniqueReferenceNO.do',
            data:{
                "creditCode": creditCode,
                "supplier":supplier
            },
            success: function(data) {
                if(data == 2){
                	layer.msg("统一社会信用代码已经被占用");
                	$("input[name='creditCode']").val("");
                }
            }
        });
    }
	//验证临时供应商名称
	function checkSupplierName(obj){
		var packageId ="${packageId}";
		var projectId = "${projectId}";
		var supplierName = $(obj).val();
		$.ajax({
			url:"${pageContext.request.contextPath}/SupplierExtracts/checkSupplierName.do",
			type: "POST",
			data:{
				"packageId":packageId,
				"projectId":projectId,
			},
			dataType: "json",
			 success: function(data) {
				 $.each(data,function(index,item){
					 if(supplierName==item){
						 layer.msg("供应商名称已添加");
				         $("#appendedInput").val("");
				         return;
					 }
			      
			   });
	         }
			
		});
	}
	function checkPwd(){
		var password = $("input[name='loginPwd']").val();
		if(password.length<6){
			layer.msg("密码至少大于等于6位");
		    $("input[name='loginPwd']").val("");
		    return;

		}
		/* var reg = /^[0-9a-zA-Z]+$/;
		if(!reg.test(password)){
			layer.msg("密码只能由数字和字母组成");
		    $("input[name='loginPwd']").val("");
		      return;
		} */
	}
</script>

<script type="text/javascript">
$(function(){
})
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
    </script>

<body>

   

     <div id="expertTypeContent" class="expertTypeContent" style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
    <ul id="treeExpertType" class="ztree" style="margin-top:0;"></ul>
  </div>
<!-- 修改订列表开始-->
   <div class="">
   <form id="form"  method="post" >
     <input type="hidden" value="${projectId}" name="projectId"/>
     <input type="hidden" value="${packageId}" name="packageId"/>
     <input type="hidden" value="${flowDefineId}" name="flowDefineId"/>
     <input type="hidden" value="${ix}" name="ix">
   <div>
    <h2 class="list_title">添加临时供应商</h2>
   <ul class="ul_list">
      <li class="col-md-3 col-sm-6 col-xs-12 pl15">
         <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="star red">*</span>供应商名称：</span>
         <div class="input-append input_group col-sm-12 col-xs-12 p0">
         <input class="title col-md-12" maxlength="80" id="appendedInput" name="supplierName" value="${supplier.supplierName}" onblur="checkSupplierName(this)" type="text"/>
         <span class="add-on">i</span>
         <div class="cue">${supplierNameError}</div>
        </div>
	   </li>
	    <li class="col-md-3 col-sm-6 col-xs-12 ">
        <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="star red">*</span>统一社会信用代码：</span>
        <div class="input-append input_group col-sm-12 col-xs-12 p0">
         <input class="title col-md-12" id="appendedInput" name="creditCode" onkeyup="this.value=this.value.replace(/[\W]/g,'')" value="${supplier.creditCode}" maxlength="18" type="text" onblur="referenceNO()">
         <span class="add-on">i</span>
         <span class="input-tip">不能为空，长度为18位</span>
         <div class="cue" >${creditCodeError}</div>
        </div>
    </li>
     <li class="col-md-3 col-sm-6 col-xs-12 ">
        <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="star red">*</span>本单位军队业务联系人姓名：</span>
         <div class="input-append input_group col-sm-12 col-xs-12 p0">
          <input class="title col-md-12" id="appendedInput" name="armyBusinessName" value="${supplier.armyBusinessName}" maxlength="10" type="text">
         <span class="add-on">i</span>
           <div class="cue" >${armyBusinessNameError}</div>
        </div>
	 </li> 
     <li class="col-md-3 col-sm-6 col-xs-12 ">
       <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="star red">*</span>本单位军队业务联系人电话：</span>
        <div class="input-append input_group col-sm-12 col-xs-12 p0">
             <input class="title col-md-12" id="appendedInput" name="armyBuinessTelephone" onkeyup="this.value=this.value.replace(/\D/g,'')" value="${supplier.armyBuinessTelephone}"  maxlength="11" type="text">
         <span class="add-on">i</span>
          <div class="cue">${armyBuinessTelephoneError}</div>
        </div>
	 </li> 
	     
	  <li class="col-md-3 col-sm-6 col-xs-12 ">
	    <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="star red">*</span>登录名：</span>
	      <div class="input-append input_group col-sm-12 col-xs-12 p0">
              <input class="title col-md-12" id="appendedInput" name="loginName" onkeyup="value=value.replace(/[^a-zA-Z\-_@\.0-9]/g,'')" value="${loginName}"  maxlength="30" type="text">
         <span class="add-on">i</span>
         <div class="cue" >${loginNameError}</div>
        </div>
     </li> 
      <li class="col-md-3 col-sm-6 col-xs-12 ">
        <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="star red">*</span>密码：</span>
         <div class="input-append input_group col-sm-12 col-xs-12 p0">
          <input class="title col-md-12" id="appendedInput" autocomplete="new-password" name="loginPwd" value="${loginPwd}" maxlength="11" type="password" onblur="checkPwd()">
          <span class="add-on">i</span>
          <div class="cue" >${loginPwdError}</div>
        </div>
     </li>  
<!--       <li class="col-md-12 col-sm-12 col-xs-12"> -->
<!--        <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">备注</span> -->
<!--        <div class="col-md-12 col-sm-12 col-xs-12 p0"> -->
<%--           <textarea class="col-md-12 col-sm-12 col-xs-12 h100" name="remarks"   maxlength="200"  title="不超过200个字" placeholder="不超过200个字">${supplier.remarks}</textarea> --%>
<!--        </div> -->
<!--      </li>  -->
   </ul>
   </div>
   <div class="clear"></div>
   <div class="tc mt10">
      <button class="btn btn-windows save"  type="button" onclick="sumbits();">保存</button>
      <button class="btn btn-windows back" type="button" onclick="onback();">返回</button>
	</div>
  </form>
 </div>
</body>
</html>
