<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
    /** 全选全不选 */
    function selectAll() {
      var checklist = document.getElementsByName("chkItem");
      var checkAll = document.getElementById("checkAll");
      if(checkAll.checked) {
        for(var i = 0; i < checklist.length; i++) {
          checklist[i].checked = true;
        }
      } else {
        for(var j = 0; j < checklist.length; j++) {
          checklist[j].checked = false;
        }
      }
    }

    /** 单选 */
    function check() {
      var count = 0;
      var checklist = document.getElementsByName("chkItem");
      var checkAll = document.getElementById("checkAll");
      for(var i = 0; i < checklist.length; i++) {
        if(checklist[i].checked == false) {
          checkAll.checked = false;
          break;
        }
        for(var j = 0; j < checklist.length; j++) {
          if(checklist[j].checked == true) {
            checkAll.checked = true;
            count++;
          }
        }
      }
    }
    /**重置密码*/
    function resetPwd() {
      var id = [];
      $('input[name="chkItem"]:checked').each(function() {
        id.push($(this).val());
      });
      if(id.length >= 1) {
        $.ajax({
          type: "GET",
          url: "${pageContext.request.contextPath}/ExpExtract/resetPwd.do?eid=" + id + "&&flowDefineId=${flowDefineId}",
          dataType: "json",
          success: function(data) {
            if("sccuess" == data) {
              layer.alert("重置成功！默认密码：123456", {
                offset: ['100px', '300px'],
                shade: 0.01
              });
            } else {
              layer.alert("重置失败！请尝试重新重置", {
                offset: ['100px', '300px'],
                shade: 0.01
              });
            }
          }
        });
      } else {
        layer.alert("请选择需要重置密码的专家", {
          offset: ['100px', '300px'],
          shade: 0.01
        });
      }
    }

    //组长
    function relate() {
      var id = [];
      $('input[name="chkItem"]:checked').each(function() {
        id.push($(this).val());
      });
      if(id.length == 1) {
        window.location.href = "${pageContext.request.contextPath}/adPackageExpert/relate.html?packageId=${packageId}&&groupId=" + id + "&&flowDefineId=${flowDefineId}";
      } else if(id.length > 1) {
        layer.alert("只能选择一个", {
          offset: ['100px', '300px'],
          shade: 0.01
        });
      } else {
        layer.alert("请选择组长", {
          offset: ['100px', '300px'],
          shade: 0.01
        });
      }

    }
  </script>

  <body>
    <h2 class="list_title">专家抽取名单</h2>
    <c:if test="${execute ne 'SCCUESS'}">
      <div class="col-md-12 col-xs-12 col-sm-12 p0 mb5">
        <button class="btn " onclick="resetPwd();" type="button">重置密码</button>
        <button class="btn " onclick="relate();" type="button">分配组长</button>
      </div>
    </c:if>
    <table class="table table-bordered table-condensed table-hover table-striped">
      <thead>
        <tr>
          <th class="info w30"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
          <th class="info w50">序号</th>
          <th class="info">专家姓名</th>
          <th class="info">专家类型</th>
          <th class="info">证件号</th>
          <th class="info">现任职务</th>
          <th class="info">联系电话</th>
        </tr>
      </thead>
      <c:forEach items="${expertList }" var="expert" varStatus="vs">
        <tr>
          <td class="tc w30"><input type="checkbox" value="${expert.expert.id}" name="chkItem" onclick="check()"></td>
          <td class="tc w30">${vs.count }</td>
          <td class="tc">${expert.expert.relName }</td>
          <c:set value="" var="typeId"></c:set>
          <c:forEach items="${expert.expert.expertsTypeId}" var="split">
            <c:forEach var="project" items="${ddList}">
              <c:if test="${split eq project.id}">
                <c:set value="${typeId},${project.name}" var="typeId"></c:set>
              </c:if>
            </c:forEach>
          </c:forEach>
          <td class="tc">${fn:substring(typeId,1,typeId.length() )}</td>
          <td class="tc">${expert.expert.idNumber }</td>
          <td class="tc">${expert.expert.atDuty }</td>
          <td class="tc">${expert.expert.mobile }</td>
        </tr>
      </c:forEach>
    </table>
    <div class="col-md-12 tc">
      <button class="btn btn-windows back" onclick="history.go(-1)" type="button">返回</button>
    </div>
  </body>

</html>