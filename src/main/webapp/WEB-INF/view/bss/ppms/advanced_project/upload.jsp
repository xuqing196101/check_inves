<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/WEB-INF/view/common.jsp"%>
<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
<script src="${pageContext.request.contextPath }/public/select2/js/select2.js"></script>
<script src="${pageContext.request.contextPath }/public/select2/js/select2_locale_zh-CN.js"></script> 
<link href="${pageContext.request.contextPath }/public/select2/css/select2.css" rel="stylesheet">
<script type="text/javascript">
  function start(){
    var principal = $("#user").val();
    principal = $.trim(principal);
    if(principal == null || principal == ""){
      layer.msg("项目负责人不能为空")
    }else{
      layer.confirm('您确认要启动项目吗?',{
	      offset: ['50px','90px'],
	      shade:0.01,
	      btn:['是','否'],
	      },function(){
	        $("#att").submit();
	      },function(){
	        var index=parent.layer.getFrameIndex(window.name);
	        parent.layer.close(index);
	      }
      );
    }
     
  }
  
  
  function cancel(){
    var index=parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
  }
  
    $(function() {
    var id = "${project.id}";
    $.ajax({
      url: "${ pageContext.request.contextPath }/advancedProject/getUserForSelect.do?id="+id,
      contentType: "application/json;charset=UTF-8",
      dataType: "json", //返回格式为json
      type: "POST", //请求方式           
      success: function(users) {
        if(users) {
          $("#user").html("<option></option>");
          $.each(users, function(i, user) {
            if(user.relName != null && user.relName != '') {
              $("#user").append("<option  value=" + user.userId + ">" + user.relName + "</option>");
            }
          });
        }
        $("#user").select2();
      }
    });
  });
</script>  
</head>

<body>
<div class="container">
  <form id="att" action="${pageContext.request.contextPath}/advancedProject/savePrincipal.html"  method="post" name="form1" class="simple" target="_parent">
    <input type="hidden" name="id" value="${project.id}"/>
    <input type="hidden" id="userId" name="userId"/>
    <div id="openDiv" class="layui-layer-wrap" >
      <div class="drop_window">
        <ul class="list-unstyled">
           <li class="col-sm-6 col-md-6 col-lg-6 col-xs-6">
             <label class="col-md-12 pl20 col-xs-12">上传项目批文</label>
            <span class="col-md-12 col-xs-12">
              <u:upload id="upload_ids" groups="show_ids,upload_ids" multiple="true" auto="true" businessId="${project.id}" typeId="${dataIds}" sysKey="2"/>
              <u:show showId="show_ids" groups="show_ids,upload_ids" businessId="${project.id}" sysKey="2" typeId="${dataIds}"/>
            </span>
          </li>
          <li class="col-sm-6 col-md-6 col-lg-6 col-xs-6">
            <label class="col-md-12 pl20 col-xs-12"><i class="red star_red">*</i>项目负责人</label>
            <div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
                <select id="user" name="principal" class="col-md-12 col-sm-12 col-xs-12 p0" onchange="change(this.options[this.selectedIndex].value)"></select>
                <div class="cue">${ERR_principal}</div>
            </div>
            </li>
          <%-- <li class="col-sm-6 col-md-6 col-lg-6 col-xs-6">
             <label class="col-md-12 pl20 col-xs-12">项目负责人</label>
            <span class="col-md-12">
              <select name="principal" class="w180 mb10">
                <option selected="selected" value="">-请选择-</option>
                  <c:forEach items="${purchaseInfo}" var="info">
                    <option value="${info.relName}">${info.relName}</option>
                  </c:forEach> 
              </select>
            </span>
          </li> --%>
           <div class="clear"></div>
        </ul>
      </div>
    </div>
    <div class="tc mt10 col-md-12">
      <a class="btn btn-windows save" onclick="start();">确认</a>
      <input class="btn btn-windows cancel" value="取消" type="button" onclick="cancel();">
    </div>
  </form>
</div>
</body>
</html>
