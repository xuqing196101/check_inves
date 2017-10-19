<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE html>
<html class=" js cssanimations csstransitions" lang="en">
  <!--<![endif]-->

  <head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title></title>
  </head>

  <script type="text/javascript">
  	function sumbits(){
			$.ajaxSetup({cache:false});
			var formData = $("#form").serialize();
		    formData = decodeURIComponent(formData, true);
		  	$("#tab-1").load("${pageContext.request.contextPath}/adSaleTender/updateTemporarySupplier.do",encodeURI(encodeURI(formData)));
	
		}

    /**返回*/
    function onback() {
      $.ajaxSetup({
        cache: false
      });
      var path = '${pageContext.request.contextPath}/adSaleTender/view.html?projectId=${projectId}&ix=${ix}';
      $("#tab-1").load(path);
    }
  </script>

  <body>
    <!-- 修改订列表开始-->
    <div class="">
      <form id="form" method="post">
        <input type="hidden" value="${projectId}" name="projectId" />
        <input type="hidden" value="${flowDefineId}" name="flowDefineId" />
        <input type="hidden" value="${ix}" name="ix">
        <input type="hidden" name="id" value="${supplier.id}">
        <div>
          <h2 class="list_title">修改临时供应商</h2>
          <ul class="ul_list">
            <li class="col-md-3 col-sm-6 col-xs-12 pl15">
              <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="star red">*</span>供应商名称：</span>
              <div class="input-append input_group col-sm-12 col-xs-12 p0">
                <input class="title col-md-12" maxlength="80" id="appendedInput" name="supplierName" value="${supplier.supplierName}" type="text" />
                <span class="add-on">i</span>
                <div class="cue">${supplierNameError}</div>
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12 ">
              <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="star red">*</span>统一社会信用代码：</span>
              <div class="input-append input_group col-sm-12 col-xs-12 p0">
                <input class="title col-md-12" id="appendedInput" name="creditCode" onkeyup="this.value=this.value.replace(/[\W]/g,'')" value="${supplier.creditCode}" maxlength="18" type="text">
                <span class="add-on">i</span>
                <span class="input-tip">不能为空，长度为18位</span>
                <div class="cue">${creditCodeError}</div>
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12 ">
              <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="star red">*</span>本单位军队业务联系人姓名：</span>
              <div class="input-append input_group col-sm-12 col-xs-12 p0">
                <input class="title col-md-12" id="appendedInput" name="armyBusinessName" value="${supplier.armyBusinessName}" maxlength="10" type="text">
                <span class="add-on">i</span>
                <div class="cue">${armyBusinessNameError}</div>
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12 ">
              <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="star red">*</span>本单位军队业务联系人电话：</span>
              <div class="input-append input_group col-sm-12 col-xs-12 p0">
                <input class="title col-md-12" id="appendedInput" name="armyBuinessTelephone" onkeyup="this.value=this.value.replace(/\D/g,'')" value="${supplier.armyBuinessTelephone}" maxlength="11" type="text">
                <span class="add-on">i</span>
                <div class="cue">${armyBuinessTelephoneError}</div>
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12 ">
              <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="star red">*</span>登录名：</span>
              <div class="input-append input_group col-sm-12 col-xs-12 p0">
                <input class="title col-md-12" id="appendedInput" name="loginName" onkeyup="value=value.replace(/[^a-zA-Z\-_@\.0-9]/g,'')" value="${loginName}" maxlength="30" type="text">
                <span class="add-on">i</span>
                <div class="cue">${loginNameError}</div>
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12 ">
              <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="star red">*</span>密码：</span>
              <div class="input-append input_group col-sm-12 col-xs-12 p0">
                <input class="title col-md-12" id="appendedInput" name="loginPwd" placeholder="******" value="" maxlength="11" type="password">
                <span class="add-on">i</span>
                <div class="cue">${loginPwdError}</div>
              </div>
            </li>
          </ul>
        </div>
        <div class="">
          <div class="" align="center">
            <button class="btn btn-windows save" type="button" onclick="sumbits();">更新</button>
            <button class="btn btn-windows back" type="button" onclick="onback();">返回</button>
          </div>
        </div>
      </form>
    </div>
  </body>

</html>