<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
  <head>
<%@ include file="/WEB-INF/view/common.jsp"%>
<%@ include file="/WEB-INF/view/common/validate.jsp"%>
<%@ include file="/WEB-INF/view/common/webupload.jsp" %>
    <script type="text/javascript">
      var tab = 1;
      $(function() {
    	  
        $("#page_ul_id").find("li").click(function() {
          var id = $(this).attr("id");
          tab = id;
          var page = "tab-" + id.charAt(id.length - 1);

          $("input[name='defaultPage']").val(page);
        });
        
        var defaultPage = "${defaultPage}";
        if(defaultPage) {
          var num = defaultPage.charAt(defaultPage.length - 1);
          
          $("#page_ul_id").find("li").each(function(index) {
            if(index == num - 1) {
              $(this).attr("class", "active");
            } else {
              $(this).removeAttr("class");
            }
          });
          
          $(".tab-pane").each(function() {
            var id = $(this).attr("id");
            if(id == defaultPage) {
              $(this).attr("class", "tab-pane fade height-200 active in");
            } else {
              $(this).attr("class", "tab-pane fade height-200");
            }
          });
        }
        
        loadProvice();
        
        var name = $("#isAuditSupplier").val();
        name = $.trim(name);
        if(name == "1"){
          $("li").removeClass("dnone");
        }else{
          $("#contact").addClass("dnone");
          $("#phone").addClass("dnone");
          $("#address").addClass("dnone");
          $("#postcode").addClass("dnone");
          $("#experContact").addClass("dnone");
          $("#experPhone").addClass("dnone");
          $("#experAddress").addClass("dnone");
          $("#experPostcode").addClass("dnone");
        }
      });
      
      /** 全选 **/
      function selectAll(obj,name){
    	  if ($(obj).prop("checked")) {  
              $("input[name="+name+"]").each(function() {  
                  $(this).prop("checked", true);  
              });  
          } else {  
              $("input[name="+name+"]").each(function() {  
                  $(this).prop("checked", false);  
              });  
          }   
      }
      
      /** 加载省份 **/
      function loadProvice() {
        var proviceId = $("#pid").val();
        $.ajax({
          type: 'post',
          url: "${pageContext.request.contextPath}/purchaseManage/getProvinceList.do?",
          data: {
            pid: 0
          },
          success: function(data) {
            $("#city").append("<option value=''>请选择</option>");
            $("#province").append("<option value=''>请选择</option>");
            $.each(data, function(idx, item) {
              if(item.id == proviceId) {

                var html = "<option value='" + item.id + "' selected>" + item.name +
                  "</option>";
                $("#province").append(html);
                loadCities(proviceId);
              } else {
                var html = "<option value='" + item.id + "'>" + item.name +
                  "</option>";
                $("#province").append(html);
              }
            });
          }
        });
      }
	
      /** 加载城市 **/
      function loadCities(pid) {
        $("#pid").val(pid);
        var cityId = $("#cid").val();
        $("#city").empty();
        $.ajax({
          type: 'post',
          url: "${pageContext.request.contextPath}/purchaseManage/getProvinceList.do?",
          data: {
            pid: pid
          },
          success: function(data) {
          $("#city").append("<option value=''>请选择</option>");
            $.each(data, function(idx, item) {
              
              if(item.id == cityId) {
                var html = "<option value='" + item.id + "' selected>" + item.name +
                  "</option>";
                $("#city").append(html);
              } else {
                var html = "<option value='" + item.id + "'>" + item.name +
                  "</option>";
                $("#city").append(html);
              }

            });
          }
        });
      }

      //添加场所
      function addPlace() {
        $("#tab-position").append("<tr  align='center'>" +
          "<td class='tc'><input type='checkbox' name='checkbo' /> </td>" +
          "<td></td>" +
          "<td><select  name='siteType'><option selected='selected' value=''>请选择</option><option  value='1'>办公室</option>"+
          "<option  value='2'>会议室</option><option  value='3'>招标室</option><option  value='4'>评标室</option></select></td>" +
          "<td><input type='text' name='siteNumber'/></td>" +
          "<td><input type='text' name='location'/></td>" +
          "<td><input type='text' name='area'/></td>" +
          "<td><input type='text' name='crewSize'/></td>" +
          "</tr>");
        calIndex('checkbo');
      }
      
      /** 删除场所 **/
      function delPlace(){
          var count = 0;
          $("input[name='checkbo']:checked").each(function(){ 
        	  count++;
          }); 
          
          if(count == 0){
        	  layer.msg("请选择需要删除的记录");
        	  return ;
          }
          
          $("input[name='checkbo']:checked").each(function(){ 
        	  $(this).parents('tr').remove();
          });
          calIndex('checkbo');
       }
      
      //关联采购管理部门
      function addManageDept() {
        var typeName = $("input[name='typeName']").val();
         layer.open({
          type: 2, 
          area : [ '550px', '500px' ],
          title: '关联采购管理部门',
          shadeClose: true,
          content:"${pageContext.request.contextPath}/purchaseManage/addPurchaseOrg.do?typeName=" + typeName
         });
      }
      
      /** 删除管理部门 **/
      function delManageDept(){
    	    var  count = 0;
    	    $("input[name='selectedItem']:checked").each(function(){ 
    	    	count++;
    	    }); 
    	    
    	    if(count == 0){
    	      layer.msg("请选择需要删除的管理部门");
    	      reurn ;
    	    }
    	  
    	    $("input[name='selectedItem']:checked").each(function(){ 
    	    	 $(this).parents('tr').remove();
    	    }); 
    	    calIndex('selectedItem');
       }
    
      /** 添加部门信息 **/
      function addDept() {
        $("#tab-orgnization").append("<tr  align='center'>" +
          "<td class='tc'><input type='checkbox' name='checkboxs' /> </td>" +
          "<td></td>" +
          "<td><input type='text' maxlength='10' name='purchaseUnitName'/></td>" +
          "<td><input type='text' maxlength='30' name='purchaseUnitDuty'/></td>" +
          "</tr>");
        calIndex('checkboxs');
      }
	
      /** 删除部门信息 **/
      
      function delDept() {
        var count = 0;
		    $("input[name='checkboxs']:checked").each(function(){ 
		    	count ++ ;
		    }); 
		    
		   	if (count == 0){
		   		layer.msg("请选择需要删除的部门");
		   		return ;
		   	}
		   	
		   	$("input[name='checkboxs']:checked").each(function(){ 
		    	$(this).parents('tr').remove();
		    }); 
		   	calIndex('checkboxs');
      }
      
      /** 计算下标 **/
      function calIndex(name){
    	  var count = 0;
  		$("input[name="+name+"]").each(function(){
  			count++;
  			$(this).parents('tr').find('td').eq(1).text(count);
  		});
      }
      
		  //验证重复
		  function verify(ele){
		    var name = $(ele).val();
		    $.ajax({
		          url: "${pageContext.request.contextPath}/purchaseManage/verify.html?name=" + name,
		          type: "post",
		          dataType: "json",
		          success: function(data) {
		            var datas = eval("(" + data + ")");
		            if(datas == false) {
		              $("#sps").html("机构已存在").css('color', 'red');
		            } else {
		              /* $("#sps").html(""); */
		            }
		          },
		        });
		  }
      
      /** 保存  **/
      function save(){
        var name = $("input[name='name']").val();
        var quaStartDate = $("input[name='quaStartDate']").val();
        var quaEdndate = $("input[name='quaEdndate']").val();
        if(quaStartDate > quaEdndate){
          layer.msg("资质截止开始时间必须大于截止时间");
        } else {
          $.ajax({
              url: "${pageContext.request.contextPath}/purchaseManage/verify.html?name=" + name,
              type: "post",
              dataType: "json",
              success: function(data) {
                var datas = eval("(" + data + ")");
                if(datas == false) {
                  $("input[name='name']").focus();
                  $("#sps").html("机构已存在").css('color', 'red');
                } else {
                  $("#sps").html("");
                  var id = [];
                  $("input[name='selectedItem']").each(function(){ 
                      id.push($(this).val());
                  }); 
                  $("#ids").val(id);
                  
                  $("#formID").validForm();
                  
                  $("#formID").submit();
                }
              },
            });
        }
      }
      
      /** 返回 **/
      function back(){
    	  window.location.href="${pageContext.request.contextPath}/purchaseManage/purchaseUnitList.html"; 
      }
      
      
      function isAudit(){
        var name = $("#isAuditSupplier").val();
        name = $.trim(name);
        if(name == "1"){
          $("li").removeClass("dnone");
        }else{
          $("#contact").addClass("dnone");
          $("#phone").addClass("dnone");
          $("#address").addClass("dnone");
          $("#postcode").addClass("dnone");
          $("#experContact").addClass("dnone");
          $("#experPhone").addClass("dnone");
          $("#experAddress").addClass("dnone");
          $("#experPostcode").addClass("dnone");
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
                <li><a href="javascript:void(0);">支撑系统</a></li>
                <li><a href="javascript:void(0);">机构管理</a> </li>
                <li class="active"><a href="javascript:jumppage('${pageContext.request.contextPath}/purchaseManage/purchaseUnitList.html')">采购机构管理</a></li>
              </ul>
			</div>
		</div>
    <div class="container mt20">
      <div class="tab-content">
        <div class="tab-v2">
          <ul class="nav nav-tabs bgwhite">
            <li id="li_id_1" class="active">
              <a aria-expanded="true" href="#tab-1" data-toggle="tab" class="f18">详细信息</a>
            </li>
            <li id="li_id_2" class="">
              <a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18">部门信息</a>
            </li>
            <li id="li_id_3" class="">
              <a aria-expanded="false" href="#tab-3" data-toggle="tab" class="f18">场所信息</a>
            </li>
            <li id="li_id_3" class="">
              <a aria-expanded="false" href="#tab-4" data-toggle="tab" class="f18">关联采购管理部门信息</a>
            </li>
          </ul>
          <sf:form action="${pageContext.request.contextPath}/purchaseManage/savePurchaseDep.html" method="post"  id="formID" modelAttribute="purchaseDep">
            <div class="tab-content padding-top-20">
              <div class="tab-pane fade active in" id="tab-1">
                <h2 class="count_flow"><i>1</i>基本信息</h2>
                <input class="hide" name="orgnization.id" type="hidden" value="${purchaseDep.orgId}">
                <input class="hide" name="id" type="hidden" value="${purchaseDepIds}">
                <input class="hide" name="ids" id="ids" type="hidden" >
                <ul class="ul_list">
                  <li class="col-md-3 col-sm-6 col-xs-12 pl15"><span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="star_red">*</span>采购机构名称</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="name" required onblur="verify(this);"	maxlength="100" value="${purchaseDep.name}" type="text">
                      <span class="add-on">i</span>
                      <div class="cue" id="sps">${ERR_name}</div>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>采购机构简称</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="shortName" required 	maxlength="100" type="text" value="${purchaseDep.shortName }"> <span class="add-on">i</span>
                      <div class="cue">${ERR_shortName}</div>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>联系人姓名</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="contactName" required  maxlength="20" type="text" value="${purchaseDep.contactName }"> <span class="add-on">i</span>
                      <div class="cue">${ERR_contactName}</div>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>联系人电话</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="contactMobile" required maxlength="30"  type="text" value="${purchaseDep.contactMobile }"> <span class="add-on">i</span>
                      <div class="cue">${ERR_contactMobile}</div>
                    </div>
                  </li>

                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>采购机构单位级别</span>
                    <div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
                      <select name="levelDep">
                        <c:forEach items="${unitLevelList}" var="unitLevel">
                          <option value="${unitLevel.id}"<c:if test="${purchaseDep.levelDep == unitLevel.id}">selected="selected"</c:if>>${unitLevel.name}</option>
                        </c:forEach>
                      </select>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>行政隶属单位</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="subordinateOrgName" required maxlength="250"  value="${purchaseDep.subordinateOrgName }" type="text">
                      <span class="add-on">i</span>
                      <div class="cue">${ERR_subordinateOrgName}</div>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>省</span>
                    <div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
                      <select name="provinceId" id="province" onchange="loadCities(this.value);" required>
                      </select> <input type="hidden"  id="pid" value="${purchaseDep.provinceId }">
                      <div class="cue">${ERR_provinceId}</div>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>市</span>
                    <div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
                      <select name="cityId" id="city" required>
                      </select> <input type="hidden"  id="cid" value="${purchaseDep.cityId }">
                      <div class="cue">${ERR_cityId}</div>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>采购机构地址</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="address" type="text" value="${purchaseDep.address }" required maxlength="250"> <span class="add-on">i</span>
                      <div class="cue">${ERR_address}</div>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>邮编</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="postCode" value="${purchaseDep.postCode}" required isZipCode="true" onkeyup="this.value=this.value.replace(/\D/g,'')" type="text"> <span class="add-on">i</span>
                      <div class="cue">${ERR_postCode}</div>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>传真号</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="fax" type="text" required maxlength="20" value="${purchaseDep.fax}"> <span class="add-on">i</span>
                      <div class="cue">${ERR_fax}</div>
                    </div>
                  </li>

                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>值班室电话</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="dutyRoomPhone" type="text" required maxlength="20"  value="${purchaseDep.dutyRoomPhone}"> <span class="add-on">i</span>
                      <div class="cue">${ERR_dutyRoomPhone}</div>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>是否具有审核供应商/专家资格</span>
                    <div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
                      <select name="isAuditSupplier" id="isAuditSupplier" required onchange="isAudit();">
                       <option  value="" <c:if test="${null eq purchaseDep.isAuditSupplier}">selected="selected" </c:if>>请选择</option>
                        <option value="1" <c:if test="${'1' eq purchaseDep.isAuditSupplier}">selected="selected" </c:if>>是</option>
                        <option value="0" <c:if test="${'0' eq purchaseDep.isAuditSupplier}">selected="selected" </c:if>>否</option>
                      </select>
                      <div class="cue">${ERR_isAuditSupplier}</div>
                    </div>
                  </li>
                  
                  <li id="contact" class="col-md-3 col-sm-6 col-xs-12 dnone"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>供应商注册联系人</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="supplierContact" type="text" required maxlength="50"  value="${purchaseDep.supplierContact}"> <span class="add-on">i</span>
                      <div class="cue">${ERR_supplierContact}</div>
                    </div>
                  </li>
                  
                  <li id="phone" class="col-md-3 col-sm-6 col-xs-12 dnone"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>供应商注册联系人电话</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="supplierPhone" type="text" required maxlength="20"  value="${purchaseDep.supplierPhone}"> <span class="add-on">i</span>
                      <div class="cue">${ERR_supplierPhone}</div>
                    </div>
                  </li>
                  
                  <li id="address" class="col-md-3 col-sm-6 col-xs-12 dnone"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>供应商注册联系地址</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="supplierAddress" type="text" required maxlength="100" value="${purchaseDep.supplierAddress}"> <span class="add-on">i</span>
                      <div class="cue">${ERR_supplierAddress}</div>
                    </div>
                  </li>
                  
                  <li id="postcode" class="col-md-3 col-sm-6 col-xs-12 dnone"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>供应商注册邮编</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="supplierPostcode" type="text" required isZipCode="true" value="${purchaseDep.supplierPostcode}"> <span class="add-on">i</span>
                      <div class="cue">${ERR_supplierPostcode}</div>
                    </div>
                  </li>
                  
                  <li id="experContact" class="col-md-3 col-sm-6 col-xs-12 dnone"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>专家注册联系人</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="experContact" type="text" required maxlength="50"  value="${purchaseDep.experContact}"> <span class="add-on">i</span>
                      <div class="cue">${ERR_experContact}</div>
                    </div>
                  </li>
                  
                  <li id="experPhone" class="col-md-3 col-sm-6 col-xs-12 dnone"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>专家注册联系人电话</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="experPhone" type="text" required maxlength="20"  value="${purchaseDep.experPhone}"> <span class="add-on">i</span>
                      <div class="cue">${ERR_experPhone}</div>
                    </div>
                  </li>
                  
                  <li id="experAddress" class="col-md-3 col-sm-6 col-xs-12 dnone"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>专家注册联系人地址</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="experAddress" type="text" required maxlength="100" value="${purchaseDep.experAddress}"> <span class="add-on">i</span>
                      <div class="cue">${ERR_experAddress}</div>
                    </div>
                  </li>
                  
                  <li id="experPostcode" class="col-md-3 col-sm-6 col-xs-12 dnone"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>专家注册联系人邮编</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="experPostcode" type="text" required isZipCode="true" value="${purchaseDep.experPostcode}"> <span class="add-on">i</span>
                      <div class="cue">${ERR_experPostcode}</div>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>排序</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="position" value="${purchaseDep.position}" required  onkeyup="this.value=this.value.replace(/\D/g,'')" type="text"> <span class="add-on">i</span>
                      <div class="cue">${ERR_position}</div>
                    </div>
                  </li>
                  
                  <li class="col-md-12 col-sm-12 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>采购业务范围</span>
                    <div class="col-md-12 col-sm-12 col-xs-12 p0">
							        <textarea class="col-md-12 col-sm-12 col-xs-12 h130" name="businessRange" required maxlength="800"  title="不超过800个字">${purchaseDep.businessRange }</textarea>
							        <div class="cue">${ERR_businessRange}</div>
							       </div>
                  </li>
                </ul>
                
                <h2 class="count_flow"><i>2</i>资质信息</h2>
                <ul class="ul_list">
                  <li class="col-md-3 col-sm-6 col-xs-12 pl15">
                    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">采购资质等级</span>
                    <div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
                      <select name="quaLevel">
                        <option  value="" selected="selected">请选择</option>
                        <option value="1" <c:if test="${'1' eq purchaseDep.quaLevel}">selected="selected" </c:if>>一级</option>
                        <option value="2" <c:if test="${'2' eq purchaseDep.quaLevel}">selected="selected" </c:if>>二级</option>
                        <option value="3" <c:if test="${'3' eq purchaseDep.quaLevel}">selected="selected" </c:if>>三级</option>
                        <option value="4" <c:if test="${'4' eq purchaseDep.quaLevel}">selected="selected" </c:if>>四级</option>
                        <option value="5" <c:if test="${'5' eq purchaseDep.quaLevel}">selected="selected" </c:if>>五级</option>
                        <option value="6" <c:if test="${'6' eq purchaseDep.quaLevel}">selected="selected" </c:if>>六级</option>
                        <option value="7" <c:if test="${'7' eq purchaseDep.quaLevel}">selected="selected" </c:if>>七级</option>
                        <option value="8" <c:if test="${'8' eq purchaseDep.quaLevel}">selected="selected" </c:if>>八级</option>
                        <option value="9" <c:if test="${'9' eq purchaseDep.quaLevel}">selected="selected" </c:if>>九级</option>
                      </select>
                      <div class="cue">${ERR_quaLevel}</div>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">采购资质范围</span>
                    <div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
                      <select name="quaRange">
                        <option  value="" <c:if test="${null eq purchaseDep.quaRange}">selected="selected" </c:if>>请选择</option>
                        <option value="1" <c:if test="${'1' eq purchaseDep.quaRange}">selected="selected" </c:if>>综合</option>
                        <option value="2" <c:if test="${'2' eq purchaseDep.quaRange}">selected="selected" </c:if>>物资</option>
                        <option value="3" <c:if test="${'3' eq purchaseDep.quaRange}">selected="selected" </c:if>>工程</option>
                        <option value="4" <c:if test="${'4' eq purchaseDep.quaRange}">selected="selected" </c:if>>服务</option>
                      </select>
                      <div class="cue">${ERR_quaRange}</div>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">采购资质开始日期</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="Wdate w230" type="text" readonly="readonly" onClick="WdatePicker()" name="quaStartDate" value="<fmt:formatDate type='date' value='${purchaseDep.quaStartDate }' dateStyle="default" pattern="yyyy-MM-dd"/>" />
                      <div class="cue">${ERR_quaStartDate}</div>
                    </div>
                  </li>
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">采购资质截止日期</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="Wdate w230" type="text" readonly="readonly" onClick="WdatePicker()"  name="quaEdndate" value="<fmt:formatDate type='date' value='${purchaseDep.quaEdndate }' dateStyle="default" pattern="yyyy-MM-dd"/>" />
                      <div class="cue">${ERR_quaEdndate}</div>
                    </div>
                  </li>
                                    
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">采购资质编号</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="quaCode" type="text" maxlength="50" value="${purchaseDep.quaCode}"> <span class="add-on">i</span>
                      <div class="cue">${ERR_quaCode}</div>
                    </div>
                  </li>
                  
                  <li class="col-md-5 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">采购资格证书图片</span>
                    <div class="uploader orange m0">
                      <u:upload id="purchaseQuaFile" businessId="${purchaseDepIds}" typeId="${purchaseTypeId}"  multiple="true" exts="png,jpeg,jpg,bmp" sysKey="2" auto="true"/>
                      <u:show showId="pqId" businessId="${purchaseDepIds}" sysKey="2" typeId="${purchaseTypeId}"  />
                    </div>
                    <div class="cue">${ERR_msg}</div>
                  </li>
                </ul>
                
                
                <h2 class="count_flow"><i>3</i>人员信息</h2>
                <ul class="ul_list">
                  <li class="col-md-3 col-sm-6 col-xs-12 pl15"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">单位主要领导姓名</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="leaderTelephone" maxlength="30" value="${purchaseDep.leaderTelephone}" type="text">
                      <span class="add-on">i</span>
                      <div class="cue">${ERR_leaderTelephone}</div>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">军官编制人数</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="officerCountnum" value="${purchaseDep.officerCountnum}" onkeyup="this.value=this.value.replace(/\D/g,'')" type="text">
                      <span class="add-on">i</span>
                      <div class="cue">${ERR_officerCountnum}</div>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">军官现有人数</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="officerNowCounts" value="${purchaseDep.officerNowCounts}" onkeyup="this.value=this.value.replace(/\D/g,'')" type="text">
                      <span class="add-on">i</span>
                      <div class="cue">${ERR_officerNowCounts}</div>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">士兵现有人数</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="soldierNowCounts" value="${purchaseDep.soldierNowCounts}" onkeyup="this.value=this.value.replace(/\D/g,'')" type="text">
                      <span class="add-on">i</span>
                      <div class="cue">${ERR_soldierNowCounts}</div>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">士兵编制人数</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="soldierNum" value="${purchaseDep.soldierNum}" onkeyup="this.value=this.value.replace(/\D/g,'')" type="text">
                      <span class="add-on">i</span>
                      <div class="cue">${ERR_soldierNum}</div>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">职工编制人数</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="staffNum" value="${purchaseDep.staffNum}" onkeyup="this.value=this.value.replace(/\D/g,'')" type="text"> <span class="add-on">i</span>
                      <div class="cue">${ERR_staffNum}</div>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">职工现有人数</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="staffNowCounts" value="${purchaseDep.staffNowCounts}" onkeyup="this.value=this.value.replace(/\D/g,'')" type="text">
                      <span class="add-on">i</span>
                      <div class="cue">${ERR_staffNowCounts}</div>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">具备采购资格人员数量</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="purchasersCount" value="${purchaseDep.purchasersCount}" onkeyup="this.value=this.value.replace(/\D/g,'')" type="text">
                      <span class="add-on">i</span>
                      <div class="cue">${ERR_purchasersCount}</div>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">初级采购师人数</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="juniorPurCount" value="${purchaseDep.juniorPurCount}" onkeyup="this.value=this.value.replace(/\D/g,'')" type="text">
                      <span class="add-on">i</span>
                      <div class="cue">${ERR_juniorPurCount}</div>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">高级采购师人数</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="seniorPurCount" value="${purchaseDep.seniorPurCount}" onkeyup="this.value=this.value.replace(/\D/g,'')" type="text">
                      <span class="add-on">i</span>
                      <div class="cue">${ERR_seniorPurCount}</div>
                    </div>
                  </li>
                </ul>
                
                
                <h2 class="count_flow"><i>4</i>甲方信息</h2>
                <ul class="ul_list">
                  <li class="col-md-3 col-sm-6 col-xs-12 pl15"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">单位名称</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="depName" maxlength="250" value="${purchaseDep.depName}" type="text"> <span class="add-on">i</span>
                      <div class="cue">${ERR_depName}</div>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">法定代表人</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="legal" maxlength="40"  value="${purchaseDep.legal}" type="text"> <span class="add-on">i</span>
                      <div class="cue">${ERR_legal}</div>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">委托代理人</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="agent" maxlength="40" value="${purchaseDep.agent}" type="text"> <span class="add-on">i</span>
                      <div class="cue">${ERR_agent}</div>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">联系人</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="contact" maxlength="40" value="${purchaseDep.contact}" type="text"> <span class="add-on">i</span>
                      <div class="cue">${ERR_contact}</div>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">联系电话</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="contactTelephone" maxlength="40" value="${purchaseDep.contactTelephone}" onkeyup="this.value=this.value.replace(/\D/g,'')" type="text">
                      <span class="add-on">i</span>
                      <div class="cue">${ERR_contactTelephone}</div>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">通讯地址</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="contactAddress" maxlength="250" value="${purchaseDep.contactAddress}" type="text">
                      <span class="add-on">i</span>
                      <div class="cue">${ERR_contactAddress}</div>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">邮政编码</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="unitPostCode" isZipCode="true" value="${purchaseDep.unitPostCode}" onkeyup="this.value=this.value.replace(/\D/g,'')" type="text">
                      <span class="add-on">i</span>
                      <div class="cue">${ERR_unitPostCode}</div>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">付款单位</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="payDep" maxlength="250" value="${purchaseDep.payDep}" type="text"> <span class="add-on">i</span>
                      <div class="cue">${ERR_payDep}</div>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">开户银行</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group"  name="bank" maxlength="40" value="${purchaseDep.bank}" type="text"> <span class="add-on">i</span>
                      <div class="cue">${ERR_bank}</div>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">银行账号</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="bankAccount" maxlength="30" onkeyup="this.value=this.value.replace(/\D/g,'')" value="${purchaseDep.bankAccount}" type="text">
                      <span class="add-on">i</span>
                      <div class="cue">${ERR_bankAccount}</div>
                    </div>
                  </li>
                </ul>
                <div class="clear"></div>
              </div>


              <!-- 财务信息 -->
              <div class="tab-pane fade height-450" id="tab-2">
                <div class="headline-v2">
                  <h2>部门信息</h2>
                </div>
                <div class="col-md-12 pl20 mt10">
                  <button type="button" class="btn btn-windows add" id="dynamicAdd" onclick="addDept();">添加</button>
                  <button type="button" class="btn btn-windows delete" id="dynamicAdd" onclick="delDept();">删除</button>
                </div>
                <div class="content table_box">
                  <table class="table table-bordered table-condensed table_input left_table" id="tab-orgnization">
                    <thead>
                      <tr>
                        <th class="info w30"><input id="checkAll" type="checkbox" onclick="selectAll(this,'checkboxs');" /></th>
                        <th class="info f13">序号</th>
                        <th class="info f13">部门名称</th>
                        <th class="info f13">主要职责</th>
                      </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${orgInfos}" var="obj" varStatus="vs">
                          <tr>
                             <td class="tc w50"><input type="checkbox" value="${obj.id}" name="checkboxs"></td>
                              <td class="tc w50">${(vs.index+1)}</td>
                              <td class="tc"><input type="text" name="purchaseUnitName" maxlength="40" value="${obj.purchaseUnitName}"/></td>
                              <td class="tc"><input type="text" name="purchaseUnitDuty" maxlength="40" value="${obj.purchaseUnitDuty}"/></td>
                             </tr>
                        </c:forEach>
                    </tbody>
                  </table>
                </div>
                <div class="clear"></div>
              </div>

              <!-- 场所信息 -->
              <div class="tab-pane fade height-200" id="tab-3">
                <h2 class="count_flow"><i>1</i>基本信息</h2>
                <ul class="ul_list">
                  <li class="col-md-3 col-sm-6 col-xs-12 pl15"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">办公场地总面积(平方米)</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="officeArea" type="text" maxlength="40" value="${purchaseDep.officeArea}"> <span class="add-on">i</span>
                      <div class="cue">${ERR_officeArea}</div>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">办公室数量</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="officeCount" value="${purchaseDep.officeCount}" onkeyup="this.value=this.value.replace(/\D/g,'')" type="text">
                      <span class="add-on">i</span>
                      <div class="cue">${ERR_officeCount}</div>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">会议室数量</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="mettingRoomCount" value="${purchaseDep.mettingRoomCount}" onkeyup="this.value=this.value.replace(/\D/g,'')" type="text">
                      <span class="add-on">i</span>
                      <div class="cue">${ERR_mettingRoomCount}</div>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">招标室数量</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="inviteRoomCount" value="${purchaseDep.inviteRoomCount}" onkeyup="this.value=this.value.replace(/\D/g,'')" type="text">
                      <span class="add-on">i</span>
                      <div class="cue">${ERR_inviteRoomCount}</div>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">评标室数量</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="bidRoomCount" value="${purchaseDep.bidRoomCount}" onkeyup="this.value=this.value.replace(/\D/g,'')" type="text">
                      <span class="add-on">i</span>
                      <div class="cue">${ERR_bidRoomCount}</div>
                    </div>
                  </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="">是否接入网络</span>
                    <div class="select_check">
                        <input name="accessNetwork" maxlength="10" type="radio" value="0" <c:if test="${purchaseDep.accessNetwork eq '0' }">checked="true"</c:if>>是
                        <input name="accessNetwork"  maxlength="10" type="radio" value="1" <c:if test="${purchaseDep.accessNetwork eq '1' }">checked="true"</c:if>>否
                    </div>
                </li>
                  
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">接入方式</span>
                    <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                      <input class="input_group" name="accessWay" maxlength="40" value="${purchaseDep.accessWay}"  type="text">
                      <span class="add-on">i</span>
                      <div class="cue">${ERR_accessWay}</div>
                    </div>
                  </li>
                  <li class="col-md-3 col-sm-6 col-xs-12"><span class="">是否具备视频监控系统</span>
                    <div class="select_check">
                        <input name="videoSurveillance" maxlength="10" type="radio" value="0" <c:if test="${purchaseDep.videoSurveillance eq '0' }">checked="true"</c:if>>是
                        <input name="videoSurveillance"  maxlength="10" type="radio" value="1" <c:if test="${purchaseDep.videoSurveillance eq '1' }">checked="true"</c:if>>否
                    </div>
                </li>
                  
                </ul>
                
                
                <h2 class="count_flow"><i>2</i>添加场所</h2>
                <ul class="ul_list">
                  <div class="col-md-12 pl20 mt10">
                    <button class="btn btn-windows add" type="button" id="dynamicAdd" onclick="addPlace();">添加</button>
                    <button class="btn btn-windows delete" type="button"  onclick="delPlace();">删除</button>
                  </div>
                  <div class="content table_box">
                    <table class="table table-bordered table-condensed table_input left_table" id="tab-position">
                      <thead>
                        <tr>
                          <th class="info w30"><input id="checkAll" type="checkbox" onclick="selectAll(this,'checkbo');" /></th>
                          <th class="info f13">序号</th>
                          <th class="info f13">类型</th>
                          <th class="info f13">编号</th>
                          <th class="info f13">位置</th>
                          <th class="info f13">面积(平方米)</th>
                          <th class="info f13">容纳人员数量</th>
                        </tr>
                      </thead>
                      <tbody>
                          <c:forEach items="${locales}" var="obj" varStatus="vs">
                            <tr>
                              <td class="tc w50"><input type="checkbox" value="${obj.id}" name="checkbo" ></td>
                              <td class="tc w50">${(vs.index+1)}</td>
                              <td class="tc">
                                <select name="siteType">
                                  <option value="1" <c:if test="${'1' eq obj.siteType}">selected="selected" </c:if>>办公室</option>
                                  <option value="2" <c:if test="${'2' eq obj.siteType}">selected="selected" </c:if>>会议室</option>
                                  <option value="3" <c:if test="${'3' eq obj.siteType}">selected="selected" </c:if>>招标室</option>
                                  <option value="4" <c:if test="${'4' eq obj.siteType}">selected="selected" </c:if>>评标室</option>
                                </select>
                              </td>
                              <td class="tc"><input type="text" name="siteNumber" maxlength="40" value="${obj.siteNumber}"/></td>
                              <td class="tc"><input type="text" name="location" maxlength="100" value="${obj.location}"/></td>
                              <td class="tc"><input type="text" name="area" maxlength="40" value="${obj.area}"/></td>
                              <td class="tc"><input type="text" name="crewSize" maxlength="40" value="${obj.crewSize}"/></td>
                            </tr>
                         </c:forEach>
                      </tbody>
                    </table>
                    <div class="clear"></div>
                    </div>
                </ul>
             </div>
                
                <div class="tab-pane fade height-200" id="tab-4">
                  <div class="headline-v2">
                    <h2>采购管理部门</h2>
                  </div>
                  <div class="col-md-12 pl20 mt10">
                    <button type="button" class="btn btn-windows add" id="dynamicAdd" onclick="addManageDept();">关联</button>
                    <button type="button" class="btn btn-windows delete" id="deleted" onclick="delManageDept();">删除</button>
                    <input type="hidden" name="typeName" value="1"/>
                  </div>
                  <div class="content table_box">
                    <table class="table table-bordered table-condensed table-hover table-striped" id="tab">
                      <thead>
                        <tr>
                          <th class="info w30"><input type="checkbox" id="checkAlls"onclick="selectAll(this,'selectedItem')"></th>
                          <th class="info w50">序号</th>
                          <th class="info f13">采购管理部门</th>
                        </tr>
                      </thead>
                      <tbody>
                      <c:if test="${lists != null}">
                        <c:forEach items="${lists}" var="obj" varStatus="vs">
                           <tr style="cursor: pointer;">
                              <td class="tc w50"><input type="checkbox" value="${obj.id }" name="selectedItem" ></td>
                              <td class="tc w50">${(vs.index+1)}</td>
                              <td class="tl pl20">${obj.name}</td>
                            </tr>
                          </c:forEach>
                          </c:if>
                      </tbody>
                    </table>
                  </div>

                </div>
              </div>

              <div class="mt20 tc col-md-12 col-sm-12 col-xs-12">
                <input type="button"  onclick="save();" class="btn btn-windows save"  value="保存" />
                <input type="button" class="btn btn-windows back" onclick="back();" value="返回" />
              </div>
          </sf:form>
          </div>
        </div>
      </div>
  </body>

</html>