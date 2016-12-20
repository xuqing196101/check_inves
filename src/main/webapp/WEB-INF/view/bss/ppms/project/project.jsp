<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

  <head>
  	<%@ include file="/WEB-INF/view/common.jsp"%>
    <script type="text/javascript">
      /*分页  */
      $(function() {
        laypage({
          cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
          pages: "${list.pages}", //总页数
          skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
          skip: true, //是否开启跳页
          groups: "${list.pages}" >= 5 ? 5 : "${list.pages}", //连续显示分页数
          curr: function() { //通过url获取当前页，也可以同上（pages）方式获取
            return "${list.pageNum}";
          },
          jump: function(e, first) { //触发分页后的回调
            if(!first) { //一定要加此判断，否则初始时会无限刷新
              location.href = '${pageContext.request.contextPath}/project/projectList.html?page=' + e.curr;
            }
          }
        });
      });

      function checkInfo(ele) {
        var flag = $(ele).prop("checked");
        var id = $(ele).val();
        
        $.ajax({
          url: "${pageContext.request.contextPath}/project/checkDeailTops.html",
          data: "id=" + id,
          type: "post",
          dataType: "json",
          success: function(result) {
            for(var i = 0; i < result.length; i++) {
              $("input[name='chkItems']").each(function() {
                var v1 = result[i].id;
                var v2 = $(this).val();
                if(v1 == v2) {
                  $(this).prop("checked", flag);
                }
              });
            }
          },
        });
      };

      //移除
      function remove() {
        var ids = [];
        $('input[name="chkItems"]:checked').each(function() {
          ids.push($(this).val());
        });
        if(ids.length > 0) {
          var name = $("#name").val();
          var projectNumber = $("#projectNumber").val();
          window.location.href = "${ pageContext.request.contextPath }/project/delete.html?ids=" + ids + "&id=${id}" + "&name=" + name + "&projectNumber=" + projectNumber;
        } else {
          layer.msg("请选择移除的信息", {
            offset: ['180px', '200px'],
            shade: 0.01
          });
        }
      }

      // 返回
      function bask() {
        window.location.href = "${pageContext.request.contextPath}/project/goBack.html?id=${id}";
      };

      //获取采购明细
      function chooce(projectId, id, name, projectNumber) {
        var name = $("#name").val();
        var projectNumber = $("#projectNumber").val();
        var orgId = $("#orgId").val();
        window.location.href = "${pageContext.request.contextPath}/project/addDetails.html?projectId=" + projectId + "&id=" + id + "&name=" + name + "&projectNumber=" + projectNumber+"&orgId="+orgId;
      };

      $(function() {
        var row = $("#table2 tr").length;
        if(row == 1) {
          $("table#table2").find("tr:eq('0')").remove();
          $("#remove").addClass("hide");
        }
      });

      function nextStep() {
        var name = $("#name").val();
        var projectNumber = $("#projectNumber").val();
        if(name == "") {
          layer.tips("项目名称不允许为空", "#name");
        } else if(projectNumber == "") {
          layer.tips("项目编号不允许为空", "#projectNumber");
        } else {
          window.location.href = "${pageContext.request.contextPath }/project/nextStep.html?id=${id}" + "&name=" + name + "&projectNumber=" + projectNumber;
        }
      }
    </script>
  </head>

  <body>
    <!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
          <li>
            <a href="javascript:void(0)">首页</a>
          </li>
          <li>
            <a href="javascript:void(0)">保障作业系统</a>
          </li>
          <li>
            <a href="javascript:void(0)">采购项目管理</a>
          </li>
          <li class="active">
            <a href="javascript:void(0)">新建采购项目</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>
    <div class="container container_box">
      <sf:form id="form1" action="${pageContext.request.contextPath}/project/create.html" method="post" modelAttribute="project">
        <div>
          <h2 class="count_flow"><i>1</i>添加信息</h2>
          <ul class="ul_list">
            <li class="col-md-3 col-sm-6 col-xs-12 pl15">
              <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><i class="star_red">*</i>项目名称</span>
              <div class="input-append input_group col-sm-12 col-xs-12 p0">
                <input type="hidden" id="id" class="input_group" name="id" value="${id}" />
                <input type="text" id="name" class="input_group" name="name" value="${name}" />
                <input type="hidden" id="orgId" class="input_group" name="orgId" value="${orgId}" />
                <span class="add-on">i</span>
                <div class="cue">${ERR_name}</div>
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12 pl15">
              <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><i class="star_red">*</i>项目编号</span>
              <div class="input-append input_group col-sm-12 col-xs-12 p0">
                <input id="projectNumber" type="text" class="input_group" name="projectNumber" value="${projectNumber}" />
                <span class="add-on">i</span>
                <div class="cue">${ERR_projectNumber}</div>
              </div>
            </li>
          </ul>
        </div>
        <div>
          <h2 class="count_flow"><i>2</i>选择采购明细</h2>
          <ul class="ul_list">
            <div class="content table_box">
              <table class="table table-bordered table-condensed table-hover">
                <thead>
                  <tr>
                    <th class="info w50">序号</th>
                    <th class="info">采购任务名称</th>
                    <th class="info">需求部门</th>
                    <th class="info">下达文件编号</th>
                    <th class="info">状态</th>
                    <th class="info">下达时间</th>
                    <th class="info">
                      <div class="star_red">*</div>操作</th>
                  </tr>
                </thead>
                <tbody id="task_id">
                  <c:forEach items="${list.list}" var="obj" varStatus="vs">
                    <tr style="cursor: pointer;">
                      <td class="tc w50">${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
                      <td class="tc">${obj.name}</td>
                      <td class="tc">${obj.purchaseId.name }</td>
                      <td class="tc">${obj.documentNumber}</td>
                      <td class="tc">
                        <c:if test="${'0'==obj.status}">
                          <span class="label rounded-2x label-u">受领</span>
                        </c:if>
                      </td>
                      <td class="tc">
                        <fmt:formatDate value="${obj.giveTime }" />
                      </td>
                      <td class="tc w30">
                        <button type="button" class="btn" onclick="chooce('${obj.id }','${id}','${name }','${projectNumber }')">选择</button>
                      </td>
                    </tr>
                  </c:forEach>
                </tbody>
              </table>
              <div id="pagediv" align="right"></div>
            </div>

            <div id="hide_detail">
              <div id="remove" class="col-md-12 pl20 mt10">
                <button class="btn" type="button" onclick="remove()">移除</button>
              </div>
              <div class="content table_box">
                <table id="table2" class="table table-bordered table-condensed table-hover">
                  <thead>
                    <tr>
                      <th class="info w50">序号</th>
                      <th class="info">需求部门</th>
                      <th class="info">物资名称</th>
                      <th class="info">规格型号</th>
                      <th class="info">质量技术标准</th>
                      <th class="info">计量单位</th>
                      <th class="info">采购数量</th>
                      <th class="info">单价（元）</th>
                      <th class="info">预算金额（万元）</th>
                      <th class="info">交货期限</th>
                      <th class="info">采购方式</th>
                      <th class="info">供应商名称</th>
                      <th class="info">是否申请办理免税</th>
                      <th class="info">物资用途（进口）</th>
                      <th class="info">使用单位（进口）</th>
                      <th class="info">备注</th>
                      <th class="info">操作</th>
                    </tr>
                  </thead>
                  <c:forEach items="${lists}" var="obj" varStatus="vs">
                    <tr style="cursor: pointer;">
                      <td class="tc w50"> ${obj.serialNumber}
                      <input type="hidden" value="${obj.requiredId }">
                      </td>
                      <td class="tc">
                      <c:if test="${orgnization.id == obj.department}"> 
						               ${orgnization.name}
						           </c:if>
                      </td>
                      <td class="tc">${obj.goodsName}
                      </td>
                      <td class="tc">${obj.stand}
                      </td>
                      <td class="tc">${obj.qualitStand}
                      </td>
                      <td class="tc">${obj.item}
                      </td>
                      <td class="tc">${obj.purchaseCount}
                      </td>
                      <td class="tc">${obj.price}
                      </td>
                      <td class="tc">${obj.budget}
                      </td>
                      <td class="tc">${obj.deliverDate}
                      </td>
                      <td class="tc">
                        <c:forEach items="${kind}" var="kind">
                          <c:if test="${kind.id == obj.purchaseType}">${kind.name}</c:if>
                        </c:forEach>
                      </td>
                      <td class="tc">${obj.supplier}
                      </td>
                      <td class="tc">${obj.isFreeTax}
                      </td>
                      <td class="tc">${obj.goodsUse}
                      </td>
                      <td class="tc">${obj.useUnit}
                      </td>
                      <td class="tc">${obj.memo}
                      </td>
                      <td class="tc w30">
                        <input type="checkbox" value="${obj.id }" name="chkItems" onclick="checkInfo(this)" alt="">
                      </td>
                    </tr>
                  </c:forEach>
                </table>
              </div>
            </div>
          </ul>
        </div>
        <div class="col-md-12 tc">
          <button class="btn" onclick="nextStep()" type="button">下一步</button>
          <button class="btn btn-windows back" onclick="bask()" type="button">返回</button>
        </div>
      </sf:form>
    </div>

  </body>

</html>