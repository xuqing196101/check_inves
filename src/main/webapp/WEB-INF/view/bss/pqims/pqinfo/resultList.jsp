<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE html>
<html>

  <head>
    <%@ include file="../../../common.jsp"%>
    <script type="text/javascript" src="${pageContext.request.contextPath}/public/webupload/js/display.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/public/upload/upload.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/public/upload/upload.css" type="text/css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/public/webupload/css/uploadView.css" type="text/css" />

    <script type="text/javascript">
      $(function() {
        laypage({
          cont: $("#pageDiv"), //容器。值支持id名、原生dom对象，jquery对象,
          pages: "${info.pages}", //总页数
          skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
          skip: true, //是否开启跳页
          total: "${info.total}",
          startRow: "${info.startRow}",
          endRow: "${info.endRow}",
          groups: "${info.pages}" >= 5 ? 5 : "${info.pages}", //连续显示分页数
          curr: function() { //通过url获取当前页，也可以同上（pages）方式获取
            var page = location.search.match(/page=(\d+)/);
            if(page == null) {
              page = {};
              page[0] = "${info.pageNum}";
              page[1] = "${info.pageNum}";
            }
            return page ? page[1] : 1;
          }(),
          jump: function(e, first) { //触发分页后的回调
            if(!first) { //一定要加此判断，否则初始时会无限刷新
              $("#page").val(e.curr);
              $("#form1").submit();
            }
          }
        });
      });

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

      function show(id) {
        window.location.href = "${pageContext.request.contextPath}/pqinfo/view.html?id=" + id;
      }

      function clearSearch() {
        $("#contractName").attr("value", "");
        $("#contractCode").attr("value", "");
        $("#searchType option:selected").removeAttr("selected");
        $("#searchConclusion option:selected").removeAttr("selected");
      }
    </script>

    <body>
      <!--面包屑导航开始-->
      <div class="margin-top-10 breadcrumbs">
        <div class="container">
          <ul class="breadcrumb margin-left-0">
            <li>
              <a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a>
            </li>
            <li>
              <a href="javascript:void(0)">保障作业</a>
            </li>
            <li>
              <a href="javascript:void(0)">产品质量管理</a>
            </li>
            <li>
              <a href="javascript:jumppage('${pageContext.request.contextPath}/pqinfo/getAll.html')">产品质量结果登记 </a>
            </li>
            <li class="active">
              <a href="javascript:void(0)">产品质量结果查询</a>
            </li>
          </ul>
          <div class="clear"></div>
        </div>
      </div>
      <div class="container">
        <div class="headline-v2">
          <h2>质量结果查询</h2>
        </div>
        <div class="search_detail">
          <form id="form1" action="${pageContext.request.contextPath}/pqinfo/getAllReasult.html" method="post" enctype="multipart/form-data" class="mb0">
            <input type="hidden" name="page" id="page">
            <ul class="demand_list">
            <li class="fl">
              <label class="fl">合同名称： </label>
              <span><input type="text" id="contractName" name="contract.name" value="${pqInfo.contract.name}" class="mb0"/></span>
            </li>
            <li class="fl">
              <label class="fl">合同编号：</label>
              <span><input type="text" id="contractCode" name="contract.code" value="${pqInfo.contract.code}" class="mb0"/></span>
            </li>
            <li class="fl"><label class="fl">验收类型：</label>
              <span>
                  <select id="searchType" name="type" class="w100">
                    <option value="" selected="selected">请选择</option>
                    <option value="0" <c:if test="${'0' eq pqInfo.type}">selected="selected"</c:if>>首件检验</option>
                    <option value="1" <c:if test="${'1' eq pqInfo.type}">selected="selected"</c:if>>生产验收</option>
                    <option value="2" <c:if test="${'2' eq pqInfo.type}">selected="selected"</c:if>>出厂验收</option>
                    <option value="3" <c:if test="${'3' eq pqInfo.type}">selected="selected"</c:if>>到货验收</option>
                  </select>
                </span>
            </li>
            <li class="fl mr15"><label class="fl">质检结论：</label>
              <span>
                  <select id="searchConclusion" name="conclusion" class="w80">
                    <option value="" selected="selected">请选择</option>
                    <option value="0" <c:if test="${'0' eq pqInfo.conclusion}">selected="selected"</c:if>>合格</option>
                    <option value="1" <c:if test="${'1' eq pqInfo.conclusion}">selected="selected"</c:if>>不合格</option>
                  </select>
                </span>
            </li>
          </ul>
          <div class="col-md-12 clear tc mt10">
            <button class="btn" type="submit">查询</button>
            <button class="btn" type="button" onclick="clearSearch()">重置</button>
          </div>
           <div class="clear"></div>
          </form>
        </div>
        <!-- 表格开始-->
        <div class="content table_box">
          <table class="table table-bordered table-condensed">
            <thead>
              <tr>
                <th class="info w50">序号</th>
                <th class="info" width="18%">合同名称</th>
                <th class="info" width="11%">合同编号</th>
                <th class="info" width="15%">采购机构</th>
                <th class="info" width="15%">供应商名称</th>
                <th class="info" width="8%">验收类型</th>
                <th class="info" width="10%">质检日期</th>
                <th class="info" width="7%">质检结论</th>
                <th class="info">查看</th>
              </tr>
            </thead>
            <c:forEach items="${info.list}" var="obj" varStatus="vs">
              <tr>
                <td class="tc" onclick="show('${obj.id}')">${(vs.index+1)+(info.pageNum-1)*(info.pageSize)}</td>
                <td class="tl pointer" onclick="show('${obj.id}')">${obj.contract.name}</td>
                <td class="tl pointer" onclick="show('${obj.id}')">${obj.contract.code}</td>
                <td class="tl pointer" onclick="show('${obj.id}')">${obj.contract.purchaseDepName}</td>
                <td class="tl pointer" onclick="show('${obj.id}')">${obj.contract.supplier.supplierName}</td>
                <td class="tc pointer" onclick="show('${obj.id}')">
                  <c:if test="${'0' eq obj.type}">首件检验</c:if>
                  <c:if test="${'1' eq obj.type}">生产验收</c:if>
                  <c:if test="${'2' eq obj.type}">出厂验收</c:if>
                  <c:if test="${'3' eq obj.type}">到货验收</c:if>
                </td>
                <td class="tc pointer" onclick="show('${obj.id}')">
                  <fmt:formatDate value='${obj.pqdate}' pattern='yyyy-MM-dd' />
                </td>
                <td class="tc pointer" onclick="show('${obj.id}')">
                  <c:if test="${'0' eq obj.conclusion}">合格</c:if>
                  <c:if test="${'1' eq obj.conclusion}">不合格</c:if>
                </td>
                <td class="tc pointer">
                  <button type="button" onclick="openViewDIv('${obj.id}', '${obj.report}', '2', 'artice_show', 'this')" class="btn">质检报告</button>
                </td>
              </tr>
            </c:forEach>
          </table>
        </div>
        <div id="pagediv" align="right"></div>
      </div>
    </body>

</html>