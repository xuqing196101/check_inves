<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>

  <head>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">

  </head>
  <script type="text/javascript">
    $(function() {
      //获取查看或操作权限
      var isOperate = $('#isOperate', window.parent.document).val();
      if(isOperate == 0) {
        $(":button").each(function() {
          $(this).hide();
        });
      }
    });
    /** 全选全不选 */
    function selectAll() {
      var checklist = document.getElementsByName("chkItemExp");
      var checkAll = document.getElementById("checkAllExp");
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
      var checklist = document.getElementsByName("chkItemExp");
      var checkAll = document.getElementById("checkAllExp");
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

    //返回
    function goBack() {
      var projectId = $("#projectId").val();
      var flowDefineId = $("#flowDefineId").val();
      $("#tab-5").load("${pageContext.request.contextPath}/adPackageExpert/toFirstAudit.html?projectId=" + projectId + "&flowDefineId=" + flowDefineId);
    }
    //查看专家对所有供应商的初审明细
    function viewByExpert(obj, packageId, projectId, flowDefineId) {
      var expertId = $('input:radio[name="firstAuditByExpert"]:checked').val();
      if(typeof(expertId) == "undefined") {
        layer.msg("请选择一名评委的初审记录", {
          offset: "100px",
          shade: 0.01
        });
      }
      if(typeof(expertId) != "undefined") {
        window.location.href = "${pageContext.request.contextPath}/adPackageExpert/viewByExpert.html?id=" + expertId + "&packageId=" + packageId + "&projectId=" + projectId + "&flowDefineId=" + flowDefineId;
      }
    }

    //查看所有专家对供应商的初审明细
    function viewBySupplier(obj, packageId, projectId, flowDefineId) {
      var supplierId = $('input:radio[name="firstAuditBySupplier"]:checked').val();
      if(typeof(supplierId) == "undefined") {
        layer.msg("请选择一名供应商的初审记录", {
          offset: "100px",
          shade: 0.01
        });
      }
      if(typeof(supplierId) != "undefined") {
        window.location.href = "${pageContext.request.contextPath}/adPackageExpert/viewBySupplier.html?supplierId=" + supplierId + "&packageId=" + packageId + "&projectId=" + projectId + "&flowDefineId=" + flowDefineId;
      }
    }

    //结束符合性审查
    function isFirstGather(projectId, packageId, flowDefineId) {
      var count = 0;
      debugger;
      var supplierNumber = '${supplierNumber}';
      $('#tabId tr:last td').each(function() {
        var s = $(this).find('div').text();
        if(s == '合格') {
          count++;
        }
      });
      if(count>=supplierNumber){
        $.ajax({
		      url: "${pageContext.request.contextPath}/packageExpert/isFirstGather.do",
		      data: {"projectId": projectId, "packageId": packageId, "flowDefineId":flowDefineId},
		      dataType:'json',
		      success:function(result){
		            if(!result.success){
		                      layer.msg(result.msg,{offset: ['150px']});
		            }else{
		              layer.msg("符合性检查结束",{offset: ['150px']});
		              $("#tab-5").load("${pageContext.request.contextPath}/adPackageExpert/toFirstAudit.html?projectId="+projectId+"&flowDefineId="+flowDefineId);
		            }
		                },
		            error: function(result){
		                layer.msg("符合性检查结束失败",{offset: ['222px']});
		            }
		    });
      }
    }

    //退回复核
    function sendBack(projectId, packageId, flowDefineId) {
      var ids = [];
      $('input[name="chkItemExp"]:checked').each(function() {
        ids.push($(this).val());
      });
      if(ids.length > 0) {
        layer.confirm('您确定要退回复核吗?', {
          title: '提示',
          offset: '100px',
          shade: 0.01
        }, function(index) {
          $.ajax({
            url: "${pageContext.request.contextPath}/adPackageExpert/isSendBack.do?expertIds=" + ids,
            data: {
              "projectId": projectId,
              "packageId": packageId
            },
            dataType: 'json',
            success: function(result) {
              if(!result.success) {
                layer.msg(result.msg, {
                  offset: ['100px']
                });
              } else {
                if(result.msg == '' || result.msg == null) {
                  $.ajax({
                    url: "${pageContext.request.contextPath}/packageExpert/sendBack.do?expertIds=" + ids,
                    data: {
                      "projectId": projectId,
                      "packageId": packageId
                    },
                    dataType: 'json',
                    success: function(result) {
                      if(!result.success) {
                        layer.msg(result.msg, {
                          offset: ['100px']
                        });
                      } else {
                        layer.close(index);
                        $("#tab-5").load("${pageContext.request.contextPath}/adPackageExpert/toFirstAudit.html?projectId=" + projectId + "&flowDefineId=" + flowDefineId);
                      }
                    },
                    error: function(result) {
                      layer.msg("退回复核失败", {
                        offset: ['100px']
                      });
                    }
                  });
                } else {
                  layer.confirm(result.msg, {
                    title: '提示',
                    offset: '100px',
                    shade: 0.01
                  }, function(index) {
                    $.ajax({
                      url: "${pageContext.request.contextPath}/packageExpert/sendBack.do?expertIds=" + ids,
                      data: {
                        "projectId": projectId,
                        "packageId": packageId
                      },
                      dataType: 'json',
                      success: function(result) {
                        if(!result.success) {
                          layer.msg(result.msg, {
                            offset: ['100px']
                          });
                        } else {
                          layer.close(index);
                          $("#tab-5").load("${pageContext.request.contextPath}/adPackageExpert/toFirstAudit.html?projectId=" + projectId + "&flowDefineId=" + flowDefineId);
                        }
                      },
                      error: function(result) {
                        layer.msg("退回复核失败", {
                          offset: ['100px']
                        });
                      }
                    });
                  });
                }
              }
            },
            error: function(result) {
              layer.msg("退回复核失败", {
                offset: ['100px']
              });
            }
          });
        });
      } else {
        layer.alert("请选择专家", {
          offset: '100px',
          shade: 0.01
        });
      }
    }

    function endPrice(projectId, packId, flowDefineId) {
      $.ajax({
        url: "${pageContext.request.contextPath}/adPackageExpert/endPrice.do",
        data: {
          "packageId": packId
        },
        dataType: 'json',
        success: function(result) {
          $('#againPrice').attr("disabled", true);
          layer.msg("已结束报价", {
            offset: ['100px']
          });
        },
        error: function(result) {
          layer.msg("结束报价失败", {
            offset: ['100px']
          });
        }
      });
    }

    function openPrint(projectId, packageId) {
      window.open("${pageContext.request.contextPath}/adPackageExpert/openPrint.html?packageId=" + packageId + "&projectId=" + projectId, "打印检查汇总表");
    }

    function openDetailPrint(projectId, packageId) {
      window.open("${pageContext.request.contextPath}/adPackageExpert/openAllPrint.html?packageId=" + packageId + "&projectId=" + projectId, "打印所有检查表");
    }
  </script>

  <body>
    <h2 class="list_title">${pack.name}符合性审查查看</h2>
    <div class="mb5 fr">
      <c:if test="${isEnd != 1}">
        <button class="btn" onclick="sendBack('${projectId}','${pack.id}','${flowDefineId}')" type="button">复核检查</button>
        <button class="btn" onclick="isFirstGather('${projectId}','${pack.id}','${flowDefineId}');" type="button">结束符合性检查</button>
        <c:if test="${purcahseCode == 'JZXTP' || purcahseCode == 'DYLY'}">
          <button <c:if test="${pack.isEndPrice == '1'}">disabled="disabled"</c:if> id="againPrice" class="btn" onclick="endPrice('${projectId}','${pack.id}','${flowDefineId}');" type="button">结束报价</button>
        </c:if>
      </c:if>
      <c:if test="${isEnd == 1}">
        <button class="btn" disabled="disabled" onclick="sendBack('${projectId}','${pack.id}','${flowDefineId}')" type="button">复核检查</button>
        <button class="btn" disabled="disabled" onclick="isFirstGather('${projectId}','${pack.id}','${flowDefineId}');" type="button">结束符合性检查</button>
        <c:if test="${purcahseCode == 'JZXTP' || purcahseCode == 'DYLY'}">
          <button disabled="disabled" class="btn" onclick="endPrice('${projectId}','${pack.id}','${flowDefineId}');" type="button">结束报价</button>
        </c:if>
      </c:if>
      <button class="btn" onclick="openPrint('${projectId}','${pack.id}')" type="button">检查汇总表</button>
      <button class="btn" onclick="openDetailPrint('${projectId}','${pack.id}')" type="button">打印检查数据</button>
    </div>
    <input type="hidden" id="projectId" value="${projectId}">
    <input type="hidden" id="flowDefineId" value="${flowDefineId}">
    <div class="over_scroll col-md-12 col-xs-12 col-sm-12 p0 m0">
      <table id="tabId" class="table table-bordered table-condensed table-hover table-striped  p0 m_resize_table_width">
        <thead>
          <tr>
            <th class="info" width="30"><input id="checkAllExp" type="checkbox" onclick="selectAll()" /></th>
            <th class="info" width="120">评委/供应商</th>
            <c:forEach items="${supplierList }" var="supplier" varStatus="vs">
              <th class="info" width="120">${supplier.suppliers.supplierName }</th>
            </c:forEach>
          </tr>
        </thead>
        <tbody id="content">
          <c:forEach items="${packExpertExtList}" var="ext" varStatus="vs">
            <tr>
              <td class="tc"><input onclick="check()" type="checkbox" name="chkItemExp" value="${ext.expert.id}" /></td>
              <td class="tc">
                <a href="${pageContext.request.contextPath}/adPackageExpert/printView.html?projectId=${projectId}&packageId=${pack.id}&expertId=${ext.expert.id}" target="view_window" title="评审明细">${ext.expert.relName}</a>
              </td>
              <c:forEach items="${supplierList}" var="supplier" varStatus="vs">
                <td class="tc">
                  <c:forEach items="${supplierExtList}" var="supplierExt">
                    <c:if test="${supplierExt.supplierId eq supplier.suppliers.id && ext.expert.id eq supplierExt.expertId}">
                      <c:if test="${supplierExt.suppIsPass == 0}">
                        <input type="hidden" value="${supplierExt.suppIsPass}"> 不合格
                      </c:if>
                      <c:if test="${supplierExt.suppIsPass == 1}">
                        <input type="hidden" value="${supplierExt.suppIsPass}"> 合格
                      </c:if>
                      <c:if test="${supplierExt.suppIsPass == 2}">
                        <input type="hidden" value="${supplierExt.suppIsPass}"> 未提交
                      </c:if>
                    </c:if>
                  </c:forEach>
                </td>
              </c:forEach>
            </tr>
          </c:forEach>
          <tr>
            <th class='info' colspan='2'>评审结果</th>
            <c:forEach items="${supplierList}" var="supplier" varStatus="vs">
              <td class="tc">
                <c:if test="${supplier.isFirstPass == 0}">
                  <div class='red'>不合格</div>
                </c:if>
                <c:if test="${supplier.isFirstPass == 1}">
                  <div>合格</div>
                </c:if>
                <c:if test="${supplier.isFirstPass == null}">
                  <div>暂无</div>
                </c:if>
              </td>
            </c:forEach>
          </tr>
        </tbody>
      </table>
    </div>
    <div class="clear col-md-12 pl20 mt10 tc">
      <button class="btn btn-windows back" onclick="goBack();" type="button">返回</button>
    </div>

    <script type="text/javascript">
      function resize_table_width() {
        $('.m_resize_table_width').each(function() {
          var table_width = 0;
          var parent_width = $(this).parent().width();
          $(this).find('thead th').each(function() {
            if(typeof($(this).attr('width')) != 'undefined') {
              table_width += parseInt($(this).attr('width'));
            }
          });
          if(table_width > parent_width) {
            $(this).css({
              width: table_width,
              maxWidth: table_width
            });
          }
        });
      }
      $(function() {
        resize_table_width();
      });
    </script>
  </body>

</html>