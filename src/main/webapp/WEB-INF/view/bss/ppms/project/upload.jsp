<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
<link href="${pageContext.request.contextPath }/public/select2/css/select2.css" rel="stylesheet">
<%@ include file="/WEB-INF/view/common.jsp"%>
<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
<%@ include file="/WEB-INF/view/common/validate.jsp"%>
<script src="${pageContext.request.contextPath }/public/select2/js/select2.js"></script>
<script src="${pageContext.request.contextPath }/public/select2/js/select2_locale_zh-CN.js"></script>	
<script type="text/javascript">
  var flag = true;
  function start(){
    layer.confirm('您确认要启动项目吗?',{
      shade:0.01,
      btn:['是','否'],
      },function(){
        //$("#att").validForm();
        var name = $("#user").val();
        name = $.trim(name);
        if(name == ""){
          $("#sps").html("负责人不能为空").css('color', 'red');
          flag = false;
        }else{
          $("#att").submit();
        }
      },function(){
        if(flag){
          var index=parent.layer.getFrameIndex(window.name);
          parent.layer.close(index);
        } else {
           layer.close();
        }
      }
    ); 
  }
  
  //取消
  function cancel(){
    var index=parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
  }
  
  $(function() {
	  var id = "${project.id}";
		$.ajax({
			url: "${ pageContext.request.contextPath }/project/getUserForSelect.do?id="+id,
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

	function change(id) {
		$("#userId").val(id);
	}
</script>  
</head>

<body>
<div class="container">
  <form id="att" action="${pageContext.request.contextPath}/project/savePrincipal.html"  method="post" name="form1" class="simple" target="_parent">
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
            <label class="col-md-12 pl20 col-xs-12"><span class="red star_red">*</span>项目负责人</label>
            <div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
								<select id="user" name="principal"  class="col-md-12 col-sm-12 col-xs-12 p0" onchange="change(this.options[this.selectedIndex].value)"></select>
								<div class="cue" id="sps">${ERR_principal}</div>
						</div>
            
          </li>
           <div class="clear"></div>
        </ul>
      </div>
    </div>
    <div class="tc mt10 col-md-12">
      <a class="btn btn-windows save" onclick="start();">确认</a>
      <input class="btn btn-windows reset" value="取消" type="button" onclick="cancel();">
    </div>
  </form>
</div>
</body>
</html>
