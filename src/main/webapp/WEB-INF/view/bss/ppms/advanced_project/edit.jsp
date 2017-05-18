<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

  <head>
    <%@ include file="/WEB-INF/view/common.jsp"%>
    <%@ include file="/WEB-INF/view/common/validate.jsp"%>
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.6.1.min.js"></script>
    <script type="text/javascript">
      function sum2(obj) { //数量
        var id = $(obj).next().val();
        var projectId = $("#projectId").val();
        $.ajax({
          url: "${pageContext.request.contextPath}/advancedProject/viewIds.html?id=" + id + "&projectId=" + projectId,
          type: "post",
          dataType: "json",
          success: function(data) {
            var purchaseCount = $(obj).val() - 0; //数量
            var price2 = $(obj).parent().next().children(":last").prev(); //价钱
            var price = $(price2).val() - 0;
            var sum = purchaseCount * price;
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
        var projectId = $("#projectId").val();
        $.ajax({
          url: "${pageContext.request.contextPath}/advancedProject/viewIds.html?id=" + id + "&projectId=" + projectId,
          type: "post",
          dataType: "json",
          success: function(data) {
            var purchaseCount = $(obj).val() - 0; //价钱
            var price2 = $(obj).parent().prev().children(":last").prev().val() - 0; //数量
            var sum = purchaseCount * price2;
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
        projectNumber = $.trim(projectNumber);
        $.ajax({
          url: "${pageContext.request.contextPath}/advancedProject/verify.html",
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
          layer.confirm('您确定要修改吗?', {
              offset: ['300px', '800px'],
              shade: 0.01,
              btn: ['是', '否'],
            },
            function() {
              if(flag == true) {
                $("#form1").submit();
                $("#form1").validForm();
              }
            },
            function() {
              parent.layer.close();
            });
      }


      //分包
      function subPackage() {
        var num = "0";
        var id = $("#id").val();
        window.location.href = "${pageContext.request.contextPath}/advancedProject/subPackage.html?id=" + id + "&num=" + num;
      }

      function goBack() {
        window.location.href = "${pageContext.request.contextPath }/advancedProject/list.html";
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
            <a href="javascript:void(0)">预研项目管理</a>
          </li>
          <li class="active">
            <a href="javascript:void(0)">预研项目调整</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>
    <!-- 录入采购计划开始-->
    <div class="container container_box">
      <sf:form action="${pageContext.request.contextPath}/advancedProject/update.html" id="form1" method="post" modelAttribute="project">
        <div>
          <h2 class="count_flow"><i>1</i>修改项目内容</h2>
          <ul class="ul_list">
            <li class="col-md-3 col-sm-6 col-xs-12 pl15">
              <input type="hidden" id="id" name="id" value="${project.id}" />
              <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><i class="star_red">*</i>项目名称</span>
              <div class="input-append input_group col-sm-12 col-xs-12 p0">
                <input type="text" id="jname"  name="name" class="input_group" value="${project.name}" />
                <span class="add-on">i</span>
                <div class="cue">${ERR_name}</div>
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><i class="star_red">*</i>项目编号</span>
              <div class="input-append input_group col-sm-12 col-xs-12 p0">
                <input type="text" id="projectNumber"  maxlength="20" name="projectNumber" onblur="verify();" class="input_group" value="${project.projectNumber}" />
                <span class="add-on">i</span>
                <div class="cue" id="sps">${ERR_projectNumber}</div>
              </div>
            </li>
          </ul>
        </div>
        <div>
          <h2 class="count_flow"><i>2</i>查看项目明细</h2>
          <div class="ul_list">
            <div class="col-md-12 col-sm-12 col-xs-12 p0 over_auto" id="content">
              <table id="table" class="table table-bordered table-condensed lockout">
                <thead>
                  <tr class="space_nowrap">
                    <th class="info seq">序号</th>
                    <th class="info department">需求部门</th>
                    <th class="info goodsname">物资名称</th>
                    <th class="info stand">规格型号</th>
                    <th class="info qualitstand">质量技术标准<br/>(技术参数)</th>
                    <th class="info item">计量<br/>单位</th>
                    <th class="info purchasecount">采购<br/>数量</th>
                    <th class="info deliverdate">交货期限</th>
                    <th class="info purchasetype">采购方式</th>
                    <th class="info purchasename">供应商名称</th>
                    <th class="info freetax">是否申请<br/>办理免税</th>
                    <th class="info goodsuse">物资用途<br/>（进口）</th>
                    <th class="info useunit">使用单位<br/>（进口）</th>
                    <th class="info memo">备注</th>
                  </tr>
                </thead>
                <c:forEach items="${lists}" var="obj" varStatus="vs">
                  <tr class="${obj.parentId}" style="cursor: pointer;">
                    <td class="tc seq">${obj.serialNumber}</td>
                    <td>
                     <div class="department">
                      <c:if test="${obj.department == orgnization.id}">
                        ${orgnization.name}
                      </c:if>
                     </div>
                    </td>
                    <td>
                      <div class="goodsname">${obj.goodsName}</div>
                    </td>
                    <td>
                      <div class="stand">${obj.stand}</div>
                    </td>
                    <td>
                      <div class="qualitstand">${obj.qualitStand}</div>
                    </td>
                    <td>
                      <div class="item">${obj.item}</div>
                    </td>
                    <td>
                     <div class="purchasecount">
                      <c:if test="${obj.purchaseCount!=null }">
                        <input type="hidden" name="ss" value="${obj.id }">
                        <input maxlength="11" class=" m0 border0 tc" id="purchaseCount" onblur="sum2(this);" type="text" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" name="detail[${vs.index }].purchaseCount" value="${obj.purchaseCount}" />
                        <input type="hidden" name="ss" value="${obj.parentId }">
                      </c:if>
                      <c:if test="${obj.purchaseCount==null }">
                        <input class="m0 border0 tc" disabled="disabled" type="text" name="detail[${vs.index }].purchaseCount" value="${obj.purchaseCount }">
                      </c:if>
                     </div>
                    </td>
                    <%-- <td class="tc p0">
					          <c:if test="${obj.price!=null }">
					            <input type="hidden" name="ss" value="${obj.id }">
					              <input maxlength="11" id="price" name="detail[${vs.index }].price" type="text" class="m0 w80"  onblur="sum1(this);" value="${obj.price}" />
					            <input type="hidden" name="ss" value="${obj.parentId }">
					          </c:if>
					            <c:if test="${obj.price==null}">
					            <input class=" m0 w80"  readonly="readonly"  type="text" name="detail[${vs.index }].price" value="${obj.price }">
					          </c:if>
					          </td>
					          <td class="tc p0">
					           <input type="hidden" name="ss" value="${obj.id }">
					          <input maxlength="11" id="budget" name="detail[${vs.index }].budget" class=" m0 w80" readonly="readonly" value="${obj.budget}" type="text"/>
					          <input type="hidden" name="ss" value="${obj.parentId }">
					          </td> --%>
                    <td>
                      <div class="deliverdate">${obj.deliverDate}</div>
                    </td>
                    <td class="advice">
                     <div class="purchasetype">
                      <c:if test="${null!=obj.purchaseType && obj.purchaseType != ''}">
                          <c:forEach items="${kind}" var="kind">
                            <c:if test="${kind.id == obj.purchaseType}"> ${kind.name}</c:if>
                      </c:forEach>
                      </c:if>
                      <input type="hidden" id="idss" name="detail[${vs.index }].id" value="${obj.id }">
                     </div>
                    </td>
                    <td>
                      <div class="purchasename">${obj.supplier}</div>
                    </td>
                    <td>
                      <div class="freetax">${obj.isFreeTax}</div>
                    </td>
                    <td>
                      <div class="goodsuse">${obj.goodsUse}</div>
                    </td>
                    <td>
                      <div class="useunit">${obj.useUnit}</div>
                    </td>
                    <td>
                      <div class="memo">${obj.memo} <input type="hidden" id="projectId" value="${obj.advancedProject }">
                      </div>
                    </td>
                  </tr>
                </c:forEach>
              </table>
            </div>
          </div>
        </div>
        <div class="col-md-12 tc col-xs-12 col-sm-12 mt20">
          <!-- <button class="btn" type="button" onclick="subPackage()">分包</button> -->
          <button class="btn btn-windows save" type="button" onclick="edit()">保存</button>
          <button class="btn btn-windows back" type="button" onclick="goBack();">返回</button>
        </div>
      </sf:form>
     </div>
 
  </body>

</html>
