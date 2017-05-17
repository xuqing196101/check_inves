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

  </head>
  <script type="text/javascript">
    function showPic(url, name) {
      layer.open({
        type: 1,
        title: false,
        closeBtn: 0,
        area: '516px',
        skin: 'layui-layer-nobg', //没有背景色
        shadeClose: true,
        content: $("#photo")
      });
    }

    function goback() {
      window.location.href = "${pageContext.request.contextPath}/pqinfo/getAll.html";

    }
    
    function view(){
        var code = $("#contractCode").val();
        var type = "1";
        $.ajax({
          type: "POST",
          dataType: "json",
          url: "${pageContext.request.contextPath}/purchaseContract/viewAfter.html?code=" + code,
          success: function(result) {
            if(result == 0) {
              layer.msg("此合同没录入售后服务信息！");
            }else if(result == 2) {
              layer.msg("请输入合同编号！");
            }else{
              window.location.href = "${pageContext.request.contextPath}/after_sale_ser/list.html?code=" + code + "&type=" + type;
            }
          }
        });
      }
  </script>

  <body>

    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
          <li>
            <a href="javascript:void(0)"> 首页</a>
          </li>
          <li>
            <a href="javascript:void(0)">保障作业</a>
          </li>
          <li class="active">
            <a href="javascript:void(0)">产品质量管理</a>
          </li>
          <li class="active">
            <a href="javascript:void(0)">质检信息详情</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>

    <div class="container content pt0">
      <div class="row magazine-page">
        <div class="col-md-12 col-sm-12 col-cs-12 tab-v2">
          <div class="padding-top-10">
            <ul class="nav nav-tabs bgwhite">
              <li class="active">
                <a aria-expanded="true" href="javascript:void(0)" data-toggle="tab" class="f18">质检信息详情</a>
              </li>
            </ul>
            <div class="tab-content padding-top-20 over_hideen">
              <div class="tab-pane fade active in" id="tab-1">
                <h2 class="count_flow jbxx">基本信息</h2>
                <table class="table table-bordered">
                  <tbody>
                    <tr>
                      <td width="15%" class="bggrey ">合同名称：</td>
                      <td colspan="3">${pqinfo.contract.name}</td>
                    </tr>
                    <tr>
	                    <td width="15%" class="bggrey ">合同编号：</td>
	                    <td width="35%">${pqinfo.contract.code}</td>
	                    <td width="15%" class="bggrey ">项目类别：</td>
	                    <td width="35%">${pqinfo.contract.purchaseType}</td>
                    </tr>
                    <tr>
                      <td width="15%" class="bggrey ">供应商名称：</td>
                      <td width="35%">${pqinfo.contract.supplier.supplierName}</td>
                      <td width="15%" class="bggrey ">供应商组织机构代码：</td>
                      <td width="35%">${pqinfo.contract.supplier.creditCode}</td>
                    </tr>
                    <tr>
                      <td width="15%" class="bggrey ">售后服务信息详情：</td>
                      <td width="35%"><button class="btn" onclick="view();" type="button">查看</button></td>
                      <td width="15%" class="bggrey "><input type="hidden" id="contractCode" value="${pqinfo.contract.code}"/></td>
                      <td width="35%"></td>
                    </tr>
                  </tbody>
                </table>

                <h2 class="count_flow jbxx">质检信息</h2>
                <table class="table table-bordered">
                  <tbody>
                    <tr>
                      <td width="15%" class="bggrey ">质检单位：</td>
                      <td width="35%">${pqinfo.unit}</td>
                      <td width="15%" class="bggrey ">质检类型：</td>
                      <td width="35%">${pqinfo.type}</td>
                    </tr>
                    <tr>
                      <td width="15%" class="bggrey ">质检地点：</td>
                      <td width="35%">${pqinfo.place}</td>
                      <td width="15%" class="bggrey ">质检日期：</td>
                      <td width="35%">
                        <fmt:formatDate value='${pqinfo.date}' pattern='yyyy-MM-dd' />
                      </td>
                    </tr>
                    <tr>
                      <td width="15%" class="bggrey ">质检人员：</td>
                      <td width="35%">${pqinfo.inspectors}</td>
                      <td width="15%" class="bggrey ">质检情况：</td>
                      <td width="35%">${pqinfo.condition}</td>
                    </tr>
                    <tr>
                      <td width=15% class="bggrey ">质检结论：</td>
                      <td width="35%">${pqinfo.conclusion}</td>
                      <td width=15% class="bggrey ">质检报告：</td>
                      <td width="35%">
                        <button type="button" onclick="openViewDIv('${pqinfo.id}','${pqinfo.report}','2','artice_show','this')" class="btn">质检报告</button>
                      </td>
                    </tr>
                    <tr>
                      <td width=15% class="bggrey ">详细情况：</td>
                      <td colspan="3">${pqinfo.detail}</td>
                    </tr>
                  </tbody>
                </table>
                <!-- 底部按钮 -->
                <c:if test="${type eq '1'}">
                <div class="col-md-12 col-sm-12 col-cs-12 tc mb10">
                  <button class="btn btn-windows back" onclick="goback()" type="button">返回</button>
                </div>
                </c:if>
              </div>
            </div>
          </div>
        </div>
      </div>
  </body>

</html>