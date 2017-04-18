<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

  <head>
    <base href="${pageContext.request.contextPath}/">
    <%@ include file="/WEB-INF/view/common.jsp"%>
    <script type="text/javascript">
      $(function() {
          if($("#tipMsg").val() == "noSecond") {
            $("#tipMsg").val("");
            layer.msg("请先完成经济、技术审查项的编写", {
              offset: '50px'
            });
          }
          var packageId = $("input[name='packageId']").val();
          var flag = "${flag}";
          if(flag == "success") {
            layer.msg("关联成功", {
              offset: ['222px', '390px']
            });
          }
        })
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

      function addMarkTerm() {
        var id = [];
        $('input[name="chkItem"]:checked').each(function() {
          id.push($(this).val());
        });
        if(id.length == 1) {
          window.location.href = "${pageContext.request.contextPath}/adIntelligentScore/list.do?id=" + id[0] + "&projectId=${projectId}";
        } else if(id.length > 1) {
          layer.alert("只能选择一个", {
            offset: ['222px', '390px'],
            shade: 0.01
          });
        } else {
          layer.alert("至少选择一个", {
            offset: ['222px', '390px'],
            shade: 0.01
          });
        }
      }

      function addBidMethod(projectId) {
        var id = [];
        $('input[name="chkItem"]:checked').each(function() {
          id.push($(this).val());
        });
        if(id.length == 1) {
          layer.open({
            type: 2,
            title: '制定评标办法',
            shadeClose: true,
            shade: 0.4,
            area: ['980px', '30%'],
            content: "${pageContext.request.contextPath}/adIntelligentScore/bidMethod?projectId=" + projectId + "&packageId=" + id[0] //iframe的url
          });
        } else if(id.length > 1) {
          layer.alert("只能选择一个", {
            offset: ['222px', '390px'],
            shade: 0.01
          });
        } else {
          layer.alert("至少选择一个", {
            offset: ['222px', '390px'],
            shade: 0.01
          });
        }
      }

      function show(packageId) {
        window.location.href = "${pageContext.request.contextPath}/adIntelligentScore/scoreModelList.html?packageId=" + packageId + "&proid=${projectId}";
      }

      function jump(url) {
        $("#open_bidding_main").load(url);
      }

      function confirmOk(obj, id, flowDefineId) {
        layer.confirm('您已经确认了吗?', {
          title: '提示',
          offset: ['100px'],
          shade: 0.01
        }, function(index) {
          layer.close(index);
          $.ajax({
            url: "${pageContext.request.contextPath}/open_bidding/confirmOk.html?projectId=" + id + "&flowDefineId=" + flowDefineId,
            dataType: 'json',
            success: function(result) {
              $("#queren").after("<a href='javascript:volid(0);' >05、已确认</a>");
              $("#queren").remove();
            },
            error: function(result) {
              layer.msg("确认失败", {
                offset: '222px'
              });
            }
          });
        });
      }

      //编辑模板内容
      function editPackageFirstAudit(packageId, projectId) {
        window.location.href = "${pageContext.request.contextPath}/adIntelligentScore/editPackageScore.html?packageId=" + packageId + "&projectId=" + projectId + "&flowDefineId=" + '${flowDefineId}';
      }

      function editScoreMehtod(packageId, projectId) {
        window.location.href = "${pageContext.request.contextPath}/adIntelligentScore/editScoreMethod.html?packageId=" + packageId + "&projectId=" + projectId;
      }

      function addScoreMethod(packageId, projectId) {
        window.location.href = "${pageContext.request.contextPath}/adIntelligentScore/addScoreMethod.html?packageId=" + packageId + "&projectId=" + projectId + "&flowDefineId=" + '${flowDefineId}';
      }

      function show(packageId, projectId) {
        window.location.href = "${pageContext.request.contextPath}/adIntelligentScore/showScoreMethod.html?packageId=" + packageId + "&projectId=" + projectId;
      }

      function view(packageId, projectId) {
        window.open("${pageContext.request.contextPath}/adIntelligentScore/viewModel.html?packageId=" + packageId + "&projectId=" + projectId);
      }
    </script>
  </head>

  <body>
    <div class="col-md-12 p0">
      <ul class="flow_step">
        <c:if test="${ope != 'view' }">
          <li>
            <a href="${pageContext.request.contextPath}/adFirstAudit/toAdd.html?projectId=${projectId}&flowDefineId=${flowDefineId}">01、资格性和符合性审查</a>
            <i></i>
          </li>

          <li class="active">
            <a href="${pageContext.request.contextPath}/adIntelligentScore/packageList.html?projectId=${projectId}&flowDefineId=${flowDefineId}">02、经济和技术评审细则</a>
            <i></i>
          </li>
          <li>
            <a href="${pageContext.request.contextPath}/Adopen_bidding/bidFile.html?id=${projectId}&flowDefineId=${flowDefineId}">
              03、采购文件
            </a>
            <i></i>
          </li>
          <li>
		         <a  href="${pageContext.request.contextPath}/AdAuditbidding/viewAudit.html?projectId=${project.id}&flowDefineId=${flowDefineId}">04、审核意见</a>
		       </li>
        </c:if>
        <c:if test="${ope == 'view' }">
          <li>
            <a href="${pageContext.request.contextPath}/Adopen_bidding/firstAduitView.html?projectId=${projectId}&flowDefineId=${flowDefineId}">01、资格性和符合性审查</a>
            <i></i>
          </li>
          <li class="active">
            <a href="${pageContext.request.contextPath}/adIntelligentScore/packageListView.html?projectId=${projectId}&flowDefineId=${flowDefineId}">02、经济和技术评审细则</a>
            <i></i>
          </li>
          <li>
            <a href="${pageContext.request.contextPath}/Adopen_bidding/bidFileView.html?id=${projectId}&flowDefineId=${flowDefineId}">
              03、采购文件
            </a>
            <i></i>
          </li>
          <li>
             <a  href="${pageContext.request.contextPath}/AdAuditbidding/viewAudit.html?projectId=${project.id}&flowDefineId=${flowDefineId}">04、审核意见</a>
           </li>
          <li>
            <c:if test="${project.confirmFile == 0 || project.confirmFile==null}">
              <a onclick="confirmOk(this,'${projectId}','${flowDefineId }');" id="queren">05、确认</a>
            </c:if>
            <c:if test="${project.confirmFile == 1 }">
              <a>05、已确认</a>
            </c:if>
          </li>
        </c:if>
      </ul>
    </div>
    <input type="hidden" id="tipMsg" value="${msg}">
    <div id="package">
      <div>
        <h2 class="f16 count_flow"><span id="projectName">项目名称:${project.name }</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <span id="projectCode">项目编号:${project.projectNumber }</span></h2>
      </div>
      <table class="table table-bordered table-condensed mt5">
          <thead>
            <tr>
              <th>序号</th>
              <th>包名</th>
              <th>状态</th>
              <th>评分办法</th>
              <th>操作</th>
            </tr>
          </thead>
          <c:forEach items="${packagesList }" var="p" varStatus="vs">
            <thead>
              <tr >
                <td class="tc w50">${vs.index+1 }</td>
                <td class="tc">${p.name}</td>
                <td class="tc">
                <c:if test="${p.isEditSecond == 0}">请选择评分办法</c:if>
                <c:if test="${p.isEditSecond == 1}">未维护评审数据</c:if>
                <c:if test="${p.isEditSecond == 2}">已维护评审数据</c:if>
                </td>
                <td class="tc">
                  <c:forEach items="${ddList}" var="list" varStatus="vs">
                    <c:if test="${vs.index == p.bidMethodTypeName }"><a onclick="show('${p.id}','${p.projectId }')" class="pointer">${list.name }</a></c:if>
                  </c:forEach>
                </td>
                 <td class="tc">
                   <c:if test="${p.isHaveScoreMethod == 1 and project.confirmFile != 1}">
                               <button class="btn" type="button" onclick="editPackageFirstAudit('${p.id}','${projectId}')">编辑</button>
                   </c:if>
                   <c:if test="${p.isHaveScoreMethod == 2 and project.confirmFile != 1}">
                               <button class="btn" type="button" onclick="addScoreMethod('${p.id}','${projectId}')">选择评分办法</button>
                   </c:if>
                   <c:if test="${project.confirmFile == 1}">
                               <button class="btn" type="button" onclick="view('${p.id}','${projectId}')">查看</button>
                   </c:if>
                        </td>
              </tr>
            </thead>
          </c:forEach>
        </table>
    </div>
    <div class="container clear margin-top-30" id="package"></div>
  </body>

</html>