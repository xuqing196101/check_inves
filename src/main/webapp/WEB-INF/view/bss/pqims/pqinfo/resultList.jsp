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
          cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
          pages: "${info.pages}", //总页数
          skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
          skip: true, //是否开启跳页
          total: "${info.total}",
          startRow: "${info.startRow}",
          endRow: "${info.endRow}",
          groups: "${info.pages}" >= 3 ? 3 : "${info.pages}", //连续显示分页数
          curr: function() { //通过url获取当前页，也可以同上（pages）方式获取
            return "${info.pageNum}";
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

      function show(id,report) {
        var status = "1";
        window.location.href = "${pageContext.request.contextPath}/pqinfo/view.html?id=" + id + "&status=" + status+ "&report=" + report;
      }

      function clearSearch() {
        $("#contractName").attr("value", "");
        $("#contractCode").attr("value", "");
        $("#searchType option:selected").removeAttr("selected");
        $("#searchConclusion option:selected").removeAttr("selected");
      }
      function openUpload(id, report) {
          if(report == "0") {
            layer.msg("没有上传质检报告");
          } else {
            var a = "2";
            openViewDIv(id, report, a, null, null);
          }
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
        <div class="m_row_5">
        <div class="row">
          <div class="col-xs-2 col-sm-4 col-md-4 col-lg-3 mb10">
            <div class="row">
              <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">合同名称：</div>
              <div class="col-xs-8 f0 lh0">
                <input type="text" id="contractName" name="contract.name" value="${pqInfo.contract.name}" class="w100p h32 f14 mb0">
              </div>
            </div>
          </div>
          
          <div class="col-xs-2 col-sm-4 col-md-4 col-lg-3 mb10">
            <div class="row">
              <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">合同编号：</div>
              <div class="col-xs-8 f0 lh0">
                <input type="text" id="contractCode" name="contract.code" value="${pqInfo.contract.code}" class="w100p h32 f14 mb0">
              </div>
            </div>
          </div>
          
          <div class="col-xs-2 col-sm-4 col-md-4 col-lg-3 mb10">
            <div class="row">
              <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">验收类型：</div>
              <div class="col-xs-8 f0 lh0">
                <select id="searchType" name="type" class="w100p h32 f14">
                  <option value="" selected="selected">请选择</option>
                  <option value="0" <c:if test="${'0' eq pqInfo.type}">selected="selected"</c:if>>首件检验</option>
                  <option value="1" <c:if test="${'1' eq pqInfo.type}">selected="selected"</c:if>>生产验收</option>
                  <option value="2" <c:if test="${'2' eq pqInfo.type}">selected="selected"</c:if>>出厂验收</option>
                  <option value="3" <c:if test="${'3' eq pqInfo.type}">selected="selected"</c:if>>到货验收</option>
                </select>
              </div>
            </div>
          </div>
          
          <div class="col-xs-2 col-sm-4 col-md-4 col-lg-3 mb10">
            <div class="row">
              <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">质检结论：</div>
              <div class="col-xs-8 f0 lh0">
                <select id="searchConclusion" name="conclusion" class="w100p h32 f14">
                  <option value="" selected="selected">请选择</option>
                  <option value="0" <c:if test="${'0' eq pqInfo.conclusion}">selected="selected"</c:if>>合格</option>
                  <option value="1" <c:if test="${'1' eq pqInfo.conclusion}">selected="selected"</c:if>>不合格</option>
                </select>
              </div>
            </div>
          </div>
        </div>
        </div>
        
        <div class="tc">
          <button class="btn mb0" type="submit">查询</button>
          <button class="btn mb0 mr0" type="button" onclick="clearSearch()">重置</button>
        </div>
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
                <td class="tl pointer" onclick="show('${obj.id}','${obj.report}')">${obj.contract.name}</td>
                <td class="tl pointer" onclick="show('${obj.id}','${obj.report}')">${obj.contract.code}</td>
                <td class="tl pointer" onclick="show('${obj.id}','${obj.report}')">${obj.contract.purchaseDepName}</td>
                <td class="tl pointer" onclick="show('${obj.id}','${obj.report}')">${obj.contract.supplier.supplierName}</td>
                <td class="tc pointer" onclick="show('${obj.id}','${obj.report}')">
                  <c:if test="${'0' eq obj.type}">首件检验</c:if>
                  <c:if test="${'1' eq obj.type}">生产验收</c:if>
                  <c:if test="${'2' eq obj.type}">出厂验收</c:if>
                  <c:if test="${'3' eq obj.type}">到货验收</c:if>
                </td>
                <td class="tc pointer" onclick="show('${obj.id}','${obj.report}')">
                  <fmt:formatDate value='${obj.pqdate}' pattern='yyyy-MM-dd' />
                </td>
                <td class="tc pointer" onclick="show('${obj.id}','${obj.report}')">
                  <c:if test="${'0' eq obj.conclusion}">合格</c:if>
                  <c:if test="${'1' eq obj.conclusion}">不合格</c:if>
                </td>
                <td class="tc pointer">
                  <button type="button" onclick="openUpload('${obj.id}','${obj.report}')" class="btn">质检报告</button>
<%--                   <button type="button" onclick="openViewDIv('${obj.id}', '${obj.report}', '2', 'artice_show', 'this')" class="btn">质检报告</button> --%>
                </td>
              </tr>
            </c:forEach>
          </table>
        </div>
        <div id="pagediv" align="right"></div>
      </div>
    </body>

</html>