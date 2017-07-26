<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE html>
<html class=" js cssanimations csstransitions" lang="en">
<!--<![endif]-->
<head>
<%@ include file="/WEB-INF/view/common.jsp"%>
<%@ include file="/WEB-INF/view/common/validate.jsp"%>
<link href="${pageContext.request.contextPath}/public/ztree/css/ztree-extend.css" type="text/css" rel="stylesheet" >
<script src="${pageContext.request.contextPath}/js/oms/purchase/jquery.metadata.js"></script>
<script src="${pageContext.request.contextPath}/js/oms/purchase/layer-extend.js"></script>
<script src="${pageContext.request.contextPath}/js/oms/purchase/select-tree.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/oms/purchase/province.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		var typeName = $("#typeName").val();
		var setting = {
				view : {
					dblClickExpand : false
				},
				async : {
					autoParam : [ "id" ],
					enable : true,
					url : "${pageContext.request.contextPath}/purchaseManage/getTree.do?typeName=" + typeName,
					dataType : "json",
					type : "post",
				},
				data : {
					simpleData : {
						enable : true,
						idKey : "id",
						pId : "pId",
						rootPId : -1,
					}
				},
				view: {
					showLine: false
				},
				callback : {
					beforeClick : beforeClick,
					onClick : onClick
				}
			};
		$.fn.zTree.init($("#treeDemo"), setting, datas);
		show();
	});
	
	//需求部门、采购机构、监管部门切换注册页面   0  是监管部门
	function show(){
		 var typeName = $("#typeName").val();
		 if(typeName!=null && typeName!="" && typeName == "2"){
		 	$(".monitor").show();
		 	$("#relaDeptId").hide();
		 }else{
		 	$(".monitor").hide();
		 	$("#show_org_cont").text("关联管理部门");
		 }
	}

 	/** 动态添加 */
    function dynamicAdd(){
    	var typeName = $("#typeName").val();
    	var title = "";
    	if(typeName!=undefined && typeName==2){
    		title = "添加采购机构";
    	}else{
    		title = "关联采购管理部门";
    	}
    	layer.open({
			type : 2, //page层
			area : [ '550px', '500px' ],
			title : title,
			shade : 0.01, //遮罩透明度
			moveType : 1, //拖拽风格，0是默认，1是传统拖动
			shift : 1, //0-6的动画形式，-1不开启
			shadeClose : true,
			content : '${pageContext.request.contextPath}/purchaseManage/addPurchaseOrg.html?typeName='+typeName
		 });
    }
    
   
	//提交表单前测试  获取选择机构id
	function check(){
		var depIds="";
		$("input[name='selectedItem']").each(function(){
			depIds += $(this).val() + ",";
		});
		depIds = depIds.substr(0,depIds.length-1);
		$("#depIds").val(depIds);
		return true;
	}
	
	/** 取消关联 */
	function dynamicCancel(){
		
		var selectedCount = $("input[name='selectedItem']:checked").length;
		
		if (selectedCount == 0){
			layer.msg("请选择需要取消的记录");
			return ;
		}
		
		$("input[name='selectedItem']:checked").each(function(){
			$(this).parents('tr').remove();
			
		});
		calIndex();
	}
	
	//重新计算index
	function calIndex(){
		var count = 0;
		$("input[name='selectedItem']").each(function(){
			count++;
			$(this).parents('tr').find('td').eq(1).text(count);
		});
	}
	
	//全选
	function selectAll(){
		if ($("#checkAll").prop("checked")) {  
            $("input[name=selectedItem]").each(function() {  
                $(this).prop("checked", true);  
            });  
        } else {  
            $("input[name=selectedItem]").each(function() {  
                $(this).prop("checked", false);  
            });  
        }   
	}
	
	
  //验证重复
  function verify(){
    var aa = 0;
    var name = $("input[name='name']").val();
    var parentId = $("#treeId").val();
    var id = $("input[name='id']").val();
    $.ajax({
          url: "${pageContext.request.contextPath}/purchaseManage/verify.html?name=" + name + "&parentId=" + parentId + "&id=" + id,
          type: "post",
          dataType: "json",
          success: function(data) {
            var datas = eval("(" + data + ")");
            if(datas == false) {
              aa = 1;
              $("#sps").html("机构已存在").css('color', 'red');
            } else {
              $("#sps").html("");
            }
          },
        });
        return aa;
  }
  
  
  function save(){
    //$("#formID").validForm();
     if(verify() == 0){
       $("#formID").submit();
     }else{
       $("input[name='name']").focus();
     }
  }
</script>
</head>
<body>
 <!--面包屑导航开始-->
  <div class="margin-top-10 breadcrumbs ">
    <div class="container">
      <ul class="breadcrumb margin-left-0">
		  <li><a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a></li>
		  <li><a href="javascript:void(0)">支撑系统</a></li>
		  <li><a href="javascript:void(0)">机构管理</a></li>
		  <li><a href="javascript:jumppage('${pageContext.request.contextPath}/purchaseManage/list.html?typeName=0');">需求部门</a></li>
        <c:if test="${orgnization.typeName == '0'}">
		  <li class="active"><a href="javascript:void(0)">编辑需求部门</a></li>
		</c:if>
		<c:if test="${orgnization.typeName == '2'}">
		  <li class="active"><a href="javascript:void(0)">编辑采购管理部门</a></li>
		</c:if>
      </ul>
      <div class="clear"></div>
    </div>
  </div>

  <!-- 修改订列表开始-->
  <div class="container container_box">
    <sf:form action="${pageContext.request.contextPath}/purchaseManage/update.do" method="post" onsubmit="return check();" id="formID" modelAttribute="orgnization">
	  <input type="hidden" id="typeName" name="typeName" value="${orgnization.typeName}" />
	  <div>
		 <c:if test="${orgnization.typeName == '0'}">
	      <h2 class="count_flow "><i>1</i>基本信息</h2>
	    </c:if>
	    <c:if test="${orgnization.typeName == '2'}">
	      <h2 class="list_title">基本信息</h2>
	    </c:if>
		<input type="hidden" name="depIds" id="depIds"/>
		<input type="hidden" name="id" value="${orgnization.id}"/>
		<ul class="ul_list">
		  <li class="col-md-3 col-sm-6 col-xs-12 pl15">
		    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>名称</span>
			<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
			  <input class="input_group" name="name" type="text" required  onblur="verify();" value="${orgnization.name}" maxlength="100"> 
			  <span class="add-on">i</span>
			  <div class="cue" id="sps"><sf:errors path="name"/></div>
			</div>
		  </li>
		  
		  <li class="col-md-3 col-sm-6 col-xs-12">
		    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>简称</span>
			<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
			  <input class="input_group" name="shortName" type="text" required maxlength="20" value="${orgnization.shortName}"> 
			  <span class="add-on">i</span>
			  <div class="cue"><sf:errors path="shortName"/></div>
			</div>
		  </li>
		  
		  
		  <li class="col-md-3 col-sm-6 col-xs-12"> 
		    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">上级</span>
			<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
			  <input id="proSec" class="input_group" type="text" readonly value="${orgnization.parentName }" name="parentName" onclick="showMenu(); return false;"/>
			  <input type="hidden"  id="treeId" name="parentId" value="${orgnization.parentId }"  class="text"/>
			</div>
		  </li>
		  
		  <li class="col-md-3 col-sm-6 col-xs-12 pl15"> 
		    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>省/直辖市</span>
			<div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
			  <select name="provinceId" id="provinceId"  onchange="loadCity()"> 
				<c:forEach items="${areaList}" var="area">
				  <option value="${area.id}" <c:if test="${orgnization.provinceId == area.id}"> selected="selected"</c:if> >${area.name}</option>
				</c:forEach>
			  </select>
			</div>
		  </li>	
		  
		  <li class="col-md-3 col-sm-6 col-xs-12 pl15"> 
		    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>市/区</span>
			<div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
			  <select id="cityId" name="cityId"> 
				<c:forEach items="${cityList}" var="city">
				  <option value="${city.id}" <c:if test="${orgnization.cityId == city.id}"> selected="selected"</c:if>>${city.name}</option>
				</c:forEach>
			  </select>
			</div>
		  </li>
		  	
		  <li class="col-md-3 col-sm-6 col-xs-12"> 
		    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">详细地址</span>
			<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
			  <input class="input_group" name="address" type="text" maxlength="100" value="${orgnization.address }"> 
			  <span class="add-on">i</span>
			</div>
		  </li>
		  
		  <li class="col-md-3 col-sm-6 col-xs-12"> 
		    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">值班室电话</span>
			<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
			  <input class="input_group" name="telephone" type="text" maxlength="25" value="${orgnization.telephone }"> 
			  <span class="add-on">i</span>
			</div>
		  </li>
		  
		  <li class="col-md-3 col-sm-6 col-xs-12"> 
		    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">邮编</span>
			<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
			  <input class="input_group" name="postCode" isZipCode="true" onkeyup="this.value=this.value.replace(/\D/g,'')" type="text" value="${orgnization.postCode }"> 
			  <span class="add-on">i</span>
			</div>
		  </li>
		  
		  <li class="col-md-3 col-sm-6 col-xs-12"> 
		    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">传真</span>
			<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
			  <input class="input_group" name="fax" type="text" maxlength="25" value="${orgnization.fax }"> 
			  <span	class="add-on">i</span>
			</div>
		  </li>
		  
		  
		  <li class="col-md-3 col-sm-6 col-xs-12 hide monitor"> 
		    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">负责人</span>
			<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
			  <input class="input_group" name="princinpal" type="text" maxlength="50" value="${orgnization.princinpal}"> 
			  <span class="add-on">i</span>
			</div>
		  </li>
		  
		  
		   <li class="col-md-3 col-sm-6 col-xs-12 hide monitor"> 
		    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">采购管理部门等级</span>
			<div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
			  <select name="nature">
			    <c:forEach items="${levelList}" var="level">
			      <option value="${level.id}" <c:if test="${orgnization.nature == level.id}"> selected="selected" </c:if> >${level.name}</option>
			    </c:forEach>
			  </select>
			</div>
		  </li>
		</ul>
		<div class="padding-top-10 clear" id="relaDeptId">
          <h2 class="count_flow"><i>2</i>
            <span id="show_org_cont">
	          <c:choose>
	            <c:when test="${orgnization.typeName == '1'}">关联采购机构</c:when>
	            <c:when test="${orgnization.typeName == '2'}">关联采购管理部门</c:when>
	            <c:when test="${orgnization.typeName == '0'}">关联采购管理部门</c:when>
	          </c:choose>
	         </span>
          </h2>
          <ul class="ul_list">
            <div class="col-md-12 col-sm-12 col-xs-12 mt10">
              <button type="button" class="btn btn-windows add"  id="dynamicAddId" onclick="dynamicAdd();">关联</button>
              <button type="button" class="btn btn-windows cancel"  onclick="dynamicCancel();">取消</button>
            </div>
            <div class="col-md-12 col-xs-12 col-sm-12 mt5">
              <table class="table table-bordered table-condensed table-hover table-striped" id="tab">
                <thead>
                  <tr>
                    <th class="info w30"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
                    <th class="info w50">序号</th>
                    <c:if test="${orgnization.typeName == '0'}">
                      <th class="info">管理部门名称</th>
                    </c:if>
                    <c:if test="${orgnization.typeName == '2'}">
                      <th class="info">采购机构名称</th>
                    </c:if>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach  items="${relaList}" var="dept" varStatus="deptStatus">
                    <tr class="tc" id="${dept.id}">
                      <td><input type="checkbox" name="selectedItem" value="${dept.id}" /></td>
                      <td>${deptStatus.index +1}</td>
                      <td class="tl pl20">${dept.name}</td>
                    </tr>
                  </c:forEach>
                </tbody>
              </table>
            </div>
          </ul>
        </div>
      </div>
	  <div class="col-md-12">
		<div class="mt40 tc mb50">
		  <button type="button" class="btn btn-windows save" onclick="save()">保存</button>
		  <input type="button" class="btn btn-windows back" onclick="history.go(-1)" value="返回"/>
		</div>
	  </div>
	</sf:form>
	<!-- tree -->
	<div id="menuContent" class="menuContent divpopups menutree">
	  <ul id="treeDemo" class="ztree"></ul>
	</div>
  </div>
</body>
</html>
