<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<html>

  <head>
    <title>工程资质证书</title>
    <%@ include file="/WEB-INF/view/common.jsp" %>
    <%@ include file="/WEB-INF/view/common/webupload.jsp" %>
  </head>

  <body>
    <div class="container">
      <div class="content table_box">
        <ul class="ul_list">
          <table class="table table-bordered table-condensed table-hover">
            <thead>
              <tr>
                <th class="info">证书名称</th>
                <th class="info">证书编号</th>
                <th class="info">资质等级</th>
                <th class="info">发证机关或机构</th>
                <th class="info">发证日期</th>
                <th class="info">有效截止日期</th>
                <th class="info">证书状态</th>
              </tr>
            </thead>
            <c:forEach items="${supplierCertEngs}" var="s" varStatus="vs">
              <tr>
                <td class="tc" id="certType_${s.id }">${s.certType }</td>
                <td class="tc" id="certCode_${s.id }">${s.certCode }</td>
                <td class="tc" id="certMaxLevel_${s.id }">${s.certMaxLevel }</td>
                <td class="tc" id="licenceAuthorith_${s.id }">${s.licenceAuthorith }</td>
                <td class="tc " id="expStartDate_${s.id }">
                  <fmt:formatDate value="${s.expStartDate }" pattern='yyyy-MM-dd' />
                </td>
                <td class="tc" id="expEndDate_${s.id }">
                  <fmt:formatDate value="${s.expEndDate }" pattern='yyyy-MM-dd' />
                </td>
                <td class="tc" id="certStatus_${s.id }">${s.certStatus}</td>
              </tr>
            </c:forEach>
          </table>

          <table class="table table-bordered table-condensed table-hover">
            <thead>
              <tr>
                <th class="info">证书名称</th>
                <th class="info">证书编号</th>
                <th class="info">资质类型</th>
                <th class="info">资质序列</th>
                <th class="info">专业类别</th>
                <th class="info">资质等级</th>
                <th class="info">是否主项资质</th>
                <th class="info w50">证书图片</th>
              </tr>
            </thead>
            <c:forEach items="${supplierAptitutes}" var="s" varStatus="vs">
              <tr>
                <td class="tc">${s.certName }</td>
                <td class="tc">${s.certCode }</td>
                <td class="tc">
                  <c:forEach items="${typeList}" var="type">
                    <c:if test="${s.certType eq type.id}">${type.name}</c:if>
                  </c:forEach>
                </td>
                <td class="tc">${s.aptituteSequence }</td>
                <td class="tc">${s.professType }</td>
                <td class="tc">${s.aptituteLevel }</td>
                <td class="tc">
                  <c:if test="${s.isMajorFund==0 }">否</c:if>
                  <c:if test="${s.isMajorFund==1 }">是</c:if>
                </td>
                <td>
                  <u:show showId="apt_show${vs.index+1}" delete="false" businessId="${s.id}" typeId="${supplierDictionaryData.supplierEngCert}" sysKey="${sysKey}" />
                </td>
              </tr>
            </c:forEach>
          </table>
        </ul>
      </div>
    </div>
  </body>

</html>