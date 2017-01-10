<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

  <head>
    <%@ include file="/WEB-INF/view/common.jsp"%>
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.6.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/public/backend/js/table.js" ></script>
    <script type="text/javascript">
      function sum2(obj) { //数量
        var id = $(obj).next().val();
        var projectId = $("#id").val();
        $.ajax({
          url: "${pageContext.request.contextPath}/project/viewIds.html?id=" + id + "&projectId=" + projectId,
          type: "post",
          dataType: "json",
          success: function(data) {
            var purchaseCount = $(obj).val() - 0; //数量
            var price2 = $(obj).parent().next().children(":last").prev(); //价钱
            var price = $(price2).val() - 0;
            var sum = purchaseCount * price;
            sum = sum / 1000;
            var budgets = $(obj).parent().next().next().children(":last").prev();
            $(budgets).val(sum);
            var budget = 0;
            $("#table tr").each(function() {
              var cid = $(this).find("td:eq(8)").children(":last").val();
              var same = $(this).find("td:eq(8)").children(":last").prev().val() - 0;
              if(id == cid) {
                budget = budget + same; //查出所有的子节点的值
              }
            });
            for(var i = 0; i < data.length; i++) {
              var v1 = data[i].id;
              $("#table tr").each(function() {
                var pid = $(this).find("td:eq(8)").children(":first").val(); //上级id
                if(data[i].id == pid) {
                  $(this).find("td:eq(8)").children(":first").next().val(budget);
                }
              });
            }
          },
        });
      }

      //单价
      function sum1(obj) {
        var id = $(obj).next().val();
        var projectId = $("#id").val();
        $.ajax({
          url: "${pageContext.request.contextPath}/project/viewIds.html?id=" + id + "&projectId=" + projectId,
          type: "post",
          dataType: "json",
          success: function(data) {
            var purchaseCount = $(obj).val() - 0; //价钱
            var price2 = $(obj).parent().prev().children(":last").prev().val() - 0; //数量
            var sum = purchaseCount * price2;
            sum = sum / 1000;
            $(obj).parent().next().children(":last").prev().val(sum);
            var budget = 0;
            $("#table tr").each(function() {
              var cid = $(this).find("td:eq(8)").children(":last").val();
              var same = $(this).find("td:eq(8)").children(":last").prev().val() - 0;
              if(id == cid) {
                budget = budget + same; //查出所有的子节点的值
              }
            });
            for(var i = 0; i < data.length; i++) {
              var v1 = data[i].id;
              $("#table tr").each(function() {
                var pid = $(this).find("td:eq(8)").children(":first").val(); //上级id
                if(data[i].id == pid) {
                  $(this).find("td:eq(8)").children(":first").next().val(budget);
                }
              });
            }
          },
        });
      }

      var flag = true;

      function verify() {
        var projectNumber = $("input[name='projectNumber']").val();
        $.ajax({
          url: "${pageContext.request.contextPath}/project/verify.html",
          type: "post",
          data: "projectNumber=" + projectNumber,
          dataType: "json",
          success: function(data) {
            var datas = eval("(" + data + ")");
            if(datas == false) {
              $("#sps").html("已存在").css('color', 'red');
              flag = false;
            } else {
              $("#sps").html("");
              flag = true;
            }

          },
        });
      }

      //修改
      function edit() {
        var name = $("input[name='name']").val();
        var projectNumber = $("input[name='projectNumber']").val();
        if(name == "") {
          layer.tips("项目名称不能为空", "#jname");
        } else if(projectNumber == "") {
          layer.tips("项目编号不能为空", "#projectNumber");
        } else {
          layer.confirm('您确定要修改吗?', {
              offset: ['300px', '800px'],
              shade: 0.01,
              btn: ['是', '否'],
            },
            function() {
              if(flag == true) {
                $("#form1").submit();
              }
            },
            function() {
              parent.layer.close();
            });
        }
      }

      //重置
      function sel(obj) {
        var val = $(obj).val();
        $("select option").each(function() {
          var opt = $(this).val();
          if(val == opt) {
            $(this).attr("selected", "selected");
          }
        });
      }

      //分包
      function subPackage() {
        var num = "0";
        var id = $("#id").val();
        window.location.href = "${pageContext.request.contextPath}/project/subPackage.html?id=" + id + "&num=" + num;
      }

      function goBack() {
        window.location.href = "${pageContext.request.contextPath }/project/list.html";
      }
    </script>
  </head>

  <body>
    <!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
          <li>
            <a href="javascript:void(0)"> 首页</a>
          </li>
          <li>
            <a href="javascript:void(0)">保障作业系统</a>
          </li>
          <li>
            <a href="javascript:void(0)">项目管理</a>
          </li>
          <li class="active">
            <a href="javascript:void(0)">项目调整</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>
    <!-- 录入采购计划开始-->
    <div class="container container_box">
      <sf:form action="${pageContext.request.contextPath}/project/update.html" id="form1" method="post" modelAttribute="project">
        <div>
          <h2 class="count_flow"><i>1</i>修改项目内容</h2>
          <ul class="ul_list">
            <li class="col-md-3 col-sm-6 col-xs-12 pl15">
              <input type="hidden" id="id" name="id" value="${project.id}" />
              <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">项目名称</span>
              <div class="input-append input_group col-sm-12 col-xs-12 p0">
                <input type="text" id="jname" name="name" class="input_group" value="${project.name}" />
                <span class="add-on">i</span>
                <div class="cue">${ERR_name}</div>
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">项目编号</span>
              <div class="input-append input_group col-sm-12 col-xs-12 p0">
                <input type="text" id="projectNumber" maxlength="20" name="projectNumber" onblur="verify();" class="input_group" value="${project.projectNumber}" />
                <span class="add-on">i</span>
                <div class="cue" id="sps">${ERR_projectNumber}</div>
              </div>
            </li>
          </ul>
        </div>
        <div>
          <h2 class="count_flow"><i>2</i>修改项目明细</h2>
          <div class="ul_list">
            <div class="content table_box ">
              <table id="table" class="table table-bordered table_wrap left_table table_input">
                <thead>
                  <tr class="info">
                    <th class="w50">序号</th>
                    <th>需求部门</th>
                    <th>物资名称</th>
                    <th>规格型号</th>
                    <th>质量技术标准</th>
                    <th>计量单位</th>
                    <th>采购数量</th>
                   <!--  <th>单价（元）</th>
                    <th>预算金额（万元）</th> -->
                    <th>交货期限</th>
                    <th>采购方式建议</th>
                    <th>供应商名称</th>
                    <th>是否申请办理免税</th>
                    <th>物资用途（进口）</th>
                    <th>使用单位（进口）</th>
                    <th>备注</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach items="${lists}" var="obj" varStatus="vs">

                    <tr class="${obj.parentId}" style="cursor: pointer;">
                      <td class="tc w50">${obj.serialNumber}</td>
                      <td>
                        <div class="pl20 pr5">
                          ${obj.department} 
                        </div>
                      </td>
                      <td>
                        <div class="pl20 pr5">${obj.goodsName}</div>
                      </td>
                      <td>
                        <div class="pl20 pr5">${obj.stand}</div>
                      </td>
                      <td>
                        <div class="pl20 pr5">${obj.qualitStand}</div>
                      </td>
                      <td class="tc">${obj.item}</td>
                      <td class="tc">
                        <c:if test="${obj.purchaseCount!=null }">
                          <input type="hidden" name="ss" value="${obj.id }">
                          <input maxlength="11" id="purchaseCount" onblur="sum2(this);" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" name="lists[${vs.index }].purchaseCount" value="${obj.purchaseCount}" type="text" />
                          <input type="hidden" name="ss" value="${obj.parentId }">
                        </c:if>
                        <c:if test="${obj.purchaseCount==null }">
                          <input style="border: 0px;" disabled="disabled" type="text" name="lists[${vs.index }].purchaseCount" value="${obj.purchaseCount }">
                        </c:if>
                      </td>
                     <%--  <td class="tc p0">
                        <c:if test="${obj.price!=null }">
                          <input type="hidden" name="ss" value="${obj.id }">
                          <input maxlength="11" id="price" name="lists[${vs.index }].price" type="text" onblur="sum1(this);" value="${obj.price}" />
                          <input type="hidden" name="ss" value="${obj.parentId }">
                        </c:if>
                        <c:if test="${obj.price==null}">
                          <input style="border: 0px;" readonly="readonly" type="text" name="lists[${vs.index }].price" value="${obj.price }">
                        </c:if>
                      </td>
                      <td class="tc p0">
                        <input type="hidden" name="ss" value="${obj.id }">
                        <input maxlength="11" id="budget" name="lists[${vs.index }].budget" class="border0 tr pr20" type="text" readonly="readonly" value="${obj.budget}" />
                        <input type="hidden" name="ss" value="${obj.parentId }">
                      </td> --%>
                      <td class="tc">${obj.deliverDate}</td>
                      <td class="tc advice">
                        <c:if test="${null!=obj.purchaseType && obj.purchaseType != ''}">
                          <c:choose>
                            <c:when test="${obj.detailStatus==0 }">

                            </c:when>
                            <c:otherwise>
                              <select name="lists[${vs.index }].purchaseType" onchange="sel(this);" style="width:100px" id="select">
                                <c:forEach items="${kind}" var="kind">
                                  <option value="${kind.id}" <c:if test="${kind.id == obj.purchaseType}">selected="selected" </c:if>> ${kind.name}</option>
                        </c:forEach>
                        </select>
                        </c:otherwise>
                        </c:choose>

                        </c:if>
                        <input type="hidden" id="idss" name="lists[${vs.index }].id" value="${obj.id }">
                        <input type="hidden" name="lists[${vs.index }].requiredId" value="${obj.requiredId }">
                        <input type="hidden" name="lists[${vs.index }].project" value="${obj.project.id}">
                      </td>
                      <td>
                        <div class="pl20 pr5">${obj.supplier}</div>
                      </td>
                      <td>
                        <div class="pl20 pr5">${obj.isFreeTax}</div>
                      </td>
                      <td>
                        <div class="pl20 pr5">${obj.goodsUse}</div>
                      </td>
                      <td>
                        <div class="pl20 pr5">${obj.useUnit}</div>
                      </td>
                      <td>
                        <div class="pl20 pr5">${obj.memo}</div>
                      </td>
                    </tr>
                  </c:forEach>
                </tbody>
              </table>
            </div>
          </div>
        </div>
        <div class="col-md-12 tc col-sm-12 col-xs-12 mt20">
          <button class="btn" type="button" onclick="subPackage()">分包</button>
          <button class="btn btn-windows git" type="button" onclick="edit()">修改</button>
          <button class="btn btn-windows back" type="button" onclick="goBack();">返回</button>
        </div>
      </sf:form>
    </div>
  </body>

</html>