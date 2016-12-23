<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
  <head>
  <%@ include file="/WEB-INF/view/common.jsp" %>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title></title>

    <!-- Meta -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <script type="text/javascript">

      function add(packId) {
        var kindName = $("#kindName").val();
        if(kindName == "公开招标") {
          var projectId = $("#projectId").val();
          window.location.href = "${pageContext.request.contextPath}/saleTender/showAllSuppliers.html?projectId=" + projectId + "&packId=" + packId;
        } else {
          var id = [];
          $('input[name="chkItem"]:checked').each(function() {
            id.push($(this).val());
          });
          var packId = $("#packId").val();
          var projectId = $("#projectId").val();
          var statusBid = $("input[name='chkItem']:checked").parents("tr").find("td").eq(5).text();
          if(id.length == 1) {
            if($.trim(statusBid) == "已缴纳") {
              layer.msg("该标书费已缴纳", {
                offset: ['180px', '200px'],
                shade: 0.01,
              });
            } else {
              window.location.href = "${pageContext.request.contextPath }/saleTender/register.html?id=" + id + "&packId=" + packId + "&projectId=" + projectId;
            }
          } else if(id.length > 1) {
            layer.msg("请选择一个登记的信息", {
              offset: ['180px', '200px'],
              shade: 0.01
            });
          } else {
            layer.msg("请选择登记的信息", {
              offset: ['180px', '200px'],
              shade: 0.01
            });
          }
        }

      }

      function ycDiv(obj, index) {
    	  if ($(obj).hasClass("shrink") && !$(obj).hasClass("spread")) {
              $(obj).removeClass("shrink");
              $(obj).addClass("spread");
            } else {
              if ($(obj).hasClass("spread") && !$(obj).hasClass("shrink")) {
                $(obj).removeClass("spread");
                $(obj).addClass("shrink");
              }
            }
            
            var divObj = new Array();
            divObj = $(".p0" + index);
            for (var i =0; i < divObj.length; i++) {
                if ($(divObj[i]).hasClass("p0"+index) && $(divObj[i]).hasClass("hide")) {
                  $(divObj[i]).removeClass("hide");
                } else {
                  if ($(divObj[i]).hasClass("p0"+index)) {
                    $(divObj[i]).addClass("hide");
                  };
                };
            };
      }

      function resetQuery() {
        $("#form1").find(":input").not(":button,:submit,:reset,:hidden").val("").removeAttr("checked").removeAttr("selected");
      }
      
     function provisional(packId) {
    	   var projectId = $("#projectId").val();
    	  window.location.href = "${pageContext.request.contextPath }/SupplierExtracts/showTemporarySupplier.html?packageId=" + packId + "&&projectId=" + projectId + "&flowDefineId=${flowDefineId}";
     }

      $(function() {
        $("#statusBid").find("option[value='${statusBid}']").attr("selected", true);
        var index=0;
        var divObj = $(".p0" + index);
        $(divObj).removeClass("hide");
        $("#package").removeClass("shrink");        
        $("#package").addClass("spread");
      })
    </script>
  </head>

  <body>

    <%-- <h2 class="list_title">项目编号：${project.projectNumber }  项目名称：${project.name }</h2> --%>

    <div class="search_detail ml0">
      <form action="${pageContext.request.contextPath}/saleTender/view.html?projectId=${project.id}" method="post" id="form1" class="mb0">
        <ul class="demand_list">
          <li><label class="fl">供应商名称：</label>
            <span>
            <input type="text" id="topic" class="w147" value="${supplierName}" name="supplierName" />
          </span>
          </li>
          <li><label class="fl">联系人电话：</label>
            <span>
            <input type="text" id="topic" class="w147" value="${contactTelephone}" name="contactTelephone" />
          </span>
          </li>
          <!-- <li><label class="fl">标书费状态：</label>
            <span>
            <select id="statusBid" name="statusBid" class="w147">
              <option value="" selected="selected" >-请选择-</option>
              <option value="2">已缴纳</option>
              <option value="1">未缴纳</option>
            </select>
          </span>
          </li> -->

        </ul>
        <div class="fl">
          <input type="submit" class="btn fl" value="查询" />
          <button type="button" onclick="resetQuery();" class="btn fl">重置</button>
        </div>
        <div class="clear"></div>
      </form>
    </div>

    <!-- 表格开始-->
    <input type="hidden" id="projectId" value="${project.id }" />

    <c:forEach items="${kind}" var="kind">
      <c:if test="${kind.id == project.purchaseType}"><input type="hidden" id="kindName" value="${kind.name}" /></c:if>
    </c:forEach>

    <div class="clear">
      <c:forEach items="${packageList }" var="pack" varStatus="p">

        <c:set value="${p.index}" var="index"></c:set>

        <div class="over_hideen">
          <h2 onclick="ycDiv(this,'${index}')" class="count_flow shrink hand fl clear" id="package">包名:<span class="f15 blue">${pack.name }</span>
          </h2>
          <div class="fl mt20 ml10">
             <button class="btn btn-windows add" onclick="add('${pack.id }')" type="button">登记</button>
             <button class="btn btn-windows add" onclick="provisional('${pack.id}');" type="button">添加临时供应商</button>
           </div>
             
          <input type="hidden" id="packId" value="${pack.id }" />

        </div>

        <div class="p0${index} hide">
          <table class="table table-bordered table-condensed table-hover table-striped mt5">
            <thead>
              <tr>
                <th class="info w50">选择</th>
                <th class="info ">供应商名称</th>
                <th class="info w100">联系人</th>
                <th class="info w110">联系电话</th>
                <th class="info w150">发售日期</th>
                <th class="info w100">标书费状态</th>
              </tr>
            </thead>
            <tbody>
              <c:forEach items="${pack.saleTenderList}" var="obj" varStatus="vs">
                <tr>
                  <td class="tc opinter w50"><input onclick="check()" type="radio" name="chkItem" value="${obj.id}" /></td>
                  <td class="tc opinter " title="${obj.suppliers.supplierName}">
                  <c:choose>
			              <c:when test="${fn:length(obj.suppliers.supplierName) > 12}">
			                ${fn:substring(obj.suppliers.supplierName, 0, 10)}......
			              </c:when>
			              <c:otherwise>
			                ${obj.suppliers.supplierName}
			              </c:otherwise>
			            </c:choose>
                  </td>
                  <td class="tc opinter w100">${obj.suppliers.contactName}</td>

                  <td class="tc opinter w110">${obj.suppliers.contactTelephone}</td>

                  <td class="tc opinter w150">
                    <fmt:formatDate value='${obj.createdAt}' pattern='yyyy-MM-dd' />
                  </td>
                  <td class="tc opinter w100">
                    <c:if test="${obj.statusBid==1}">
                      未缴纳
                    </c:if>
                    <c:if test="${obj.statusBid==2}">
                      已缴纳
                    </c:if>
                  </td>
                </tr>
              </c:forEach>
            </tbody>
          </table>
        </div>
      </c:forEach>
    </div>
  </body>

</html>