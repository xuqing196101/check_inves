<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
  <head>
   <%@ include file="../../../common.jsp"%>
    <title>分配任务人员信息</title>
    <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath }/public/select2/js/select2.js"></script>
    <link href="${pageContext.request.contextPath }/public/select2/css/select2.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/public/layer/skin/layer.css" media="screen" rel="stylesheet" type="text/css">

  </head>
  <script type="text/javascript">
    function sure() {
      var user = $("#users").select2("val");
      if(user.trim() == "") {
        // layer.alert("审价人员不能为空",{offset: ['100px', '90px'], shade:0.01});
        $("#checkUser").html("审价人员不能为空");
      } else {
        $.ajax({
          type: "POST",
          url: "${pageContext.request.contextPath}/appraisalContract/updateDistribution.html",
          data: $("#form1").serializeArray(),
          dataType: 'json',
          success: function(result) {
            if(!result.success) {
              layer.msg(result.msg, {
                offset: ['150px', '180px']
              });
            } else {
              parent.window.setTimeout(function() {
                parent.window.location.href = "${pageContext.request.contextPath}/appraisalContract/selectDistribution.html";
              }, 1000);
              layer.msg(result.msg, {
                offset: ['150px', '180px']
              });
            }
          },
        });
      }
    }

    $(function() {
      $.ajax({
        contentType: "application/json;charset=UTF-8",
        url: "${pageContext.request.contextPath }/appraisalContract/selectUser.html",
        type: "POST",
        dataType: "json",
        success: function(users) {
          if(users) {
            $("#users").append("<option></option>");
            $.each(users, function(i, user) {
              if(user.relName != null && user.relName != '') {
                $("#users").append("<option value=" + user.id + ">" + user.relName + "</option>");
              }
            });
          }
          $("#users").select2();
        }
      });
    })

    function cancel() {
      window.parent.location.reload();
    }
  </script>

  <body>

    <div class="container mt10">
      <form id="form1" action="${pageContext.request.contextPath}/appraisalContract/updateDistribution.html" method="post">
        <input type="hidden" value="${id }" name="id" id="id">

        <ul class="list-unstyled mb20 over_hideen">
          <li class="col-md-6 col-sm-6 col-xs-6 pl15">
            <div class="col-md-12 col-sm-12 col-xs-12 padding-left-5">
              <div class="star_red">*</div>审价员：</div>
            <div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
              <select id="users" name="user.id">
              </select>
              <div class="cue" id="checkUser"></div>
            </div>
          </li>
          <li class="col-md-12 col-sm-12 col-xs-12">
            <div class="col-sm-12 col-xs-12 padding-left-5">审价任务：</div>
            <div class="col-md-12 p0 col-md-12 col-sm-12 col-xs-12">
              <textarea class="col-md-12 h80 col-xs-12 col-sm-12" id="appraisalTask" name="appraisalTask" title="不超过250个字" placeholder="不超过250个字"></textarea>
            </div>
          </li>
        </ul>

        <div class="col-md-12 col-sm-12 col-xs-12 tc mt20 clear">
          <button class="btn btn-windows add" type="button" onclick="sure()">确定</button>
          <button class="btn btn-windows cancel" type="button" onclick="cancel()">取消</button>
        </div>
      </form>

    </div>

  </body>

</html>