<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<html>

  <head>
    <%@ include file="/WEB-INF/view/common.jsp" %>
    <%@ include file="/WEB-INF/view/common/webupload.jsp"%>
    <title>供应商类型</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <script type="text/javascript">
      //文件下載
      function download(id, key) {
        var form = $("<form>");
        form.attr('style', 'display:none');
        form.attr('method', 'post');
        form.attr('action', globalPath + '/file/download.html?id=' + id + '&key=' + key);
        $('body').append(form);
        form.submit();
      }
    </script>
  </head>

  <body>
    <div class="container">
      <div class="content table_box">
        <ul class="ul_list">
          <ul id="page_ul_id" class="nav nav-tabs bgdd supplier_tab">
            <c:set value="0" var="liCountPro" />
            <c:set value="0" var="liCountSell" />
            <c:set value="0" var="liCountEng" />
            <c:set value="0" var="liCountSer" />
            <c:if test="${fn:contains(supplierTypeCode, 'PRODUCT')}">
              <c:set value="${liCountPro+1}" var="liCountPro" />
              <li class="active">
                <a aria-expanded="true" href="#tab-1" data-toggle="tab">物资-生产型专业信息</a>
              </li>
            </c:if>
            <c:if test="${fn:contains(supplierTypeCode, 'SALES')}">
              <li class='<c:if test="${liCountPro == 0}">active <c:set value="${liCountSell+1}" var="liCountSell" /></c:if>'>
                <a aria-expanded="false" href="#tab-2" data-toggle="tab">物资-销售型专业信息</a>
              </li>
            </c:if>
            <c:if test="${fn:contains(supplierTypeCode, 'PROJECT')}">
              <li class='<c:if test="${liCountSell == 0 && liCountPro == 0}">active <c:set value="${liCountEng+1}" var="liCountEng" /></c:if>'>
                <a aria-expanded="false" href="#tab-3" data-toggle="tab">工程专业信息</a>
              </li>
            </c:if>
            <c:if test="${fn:contains(supplierTypeCode, 'SERVICE')}">
              <li class='<c:if test="${liCountSell == 0 && liCountPro == 0 && liCountEng == 0}">active <c:set value="${liCountSer+1}" var="liCountSer" /></c:if>'>
                <a aria-expanded="false" href="#tab-4" data-toggle="tab">服务专业信息</a>
              </li>
            </c:if>
          </ul>

          <div class="tab-content padding-top-20" id="tab_content_div_id">
            <c:if test="${fn:contains(supplierTypeCode, 'PRODUCT')}">
              <div class="tab-pane fade active in height-300" id="tab-1">
                <div class="clear"></div>
                <div>
                  <table class="table table-bordered table-condensed table-hover">
                    <thead>
                      <tr>
                        <th class="info w50">序号</th>
                        <th class="info">资质证书名称</th>
                        <th class="info">证书图片</th>
                      </tr>
                    </thead>
                    <c:forEach items="${materialProduction}" var="m" varStatus="vs">
                      <tr>
                        <td class="tc">${vs.index + 1}</td>
                        <td class="tl pl20">${m.name }</td>
                        <td class="tc">
                          <u:show showId="pro_show${vs.index+1}" delete="false" businessId="${m.id}" typeId="${supplierDictionaryData.supplierProCert}" sysKey="${sysKey}" />
                        </td>
                      </tr>
                    </c:forEach>
                  </table>
                </div>
              </div>
            </c:if>

            <c:if test="${fn:contains(supplierTypeCode, 'SALES')}">
              <div class="tab-pane <c:if test=" ${liCountSell==1} ">active in</c:if> fade  in height-200" id="tab-2">
                <div>
                  <table class="table table-bordered table-condensed table-hover">
                    <thead>
                      <tr>
                        <th class="info w50">序号</th>
                        <th class="info">资质证书名称</th>
                        <th class="info">证书图片</th>
                      </tr>
                    </thead>
                    <c:forEach items="${supplierCertSell}" var="s" varStatus="vs">
                      <tr>
                        <td class="tc">${vs.index + 1}</td>
                        <td class="tl pl20">${s.name }</td>
                        <td class="tc">
                          <u:show showId="sale_show_${vs.index+1}" delete="false" businessId="${s.id}" typeId="${supplierDictionaryData.supplierSellCert}" sysKey="${sysKey}" />
                        </td>
                      </tr>
                    </c:forEach>
                  </table>
                </div>
              </div>
            </c:if>

            <c:if test="${fn:contains(supplierTypeCode, 'PROJECT')}">
              <div class="tab-pane <c:if test=" ${liCountEng==1} ">active in</c:if> fade height-200" id="tab-3">
                <div>
                  <table class="table table-bordered table-condensed table-hover">
                    <thead>
                      <tr>
                        <th class="info w50">序号</th>
                        <th class="info">资质证书名称</th>
                        <th class="info">证书图片</th>
                      </tr>
                    </thead>
                    <c:forEach items="${supplierEngQuas}" var="s" varStatus="vs">
                      <tr>
                        <td class="tc">${vs.index + 1}</td>
                        <td class="tc">${s.name }</td>
                        <td class="tc">
                          <div class="w110 fl">
                            <u:show showId="eng_up_show${vs.index+1}" businessId="${s.id}" delete="false" typeId="${supplierDictionaryData.supplierEngQua}" sysKey="${sysKey}" />
                          </div>
                        </td>
                      </tr>
                    </c:forEach>
                  </table>
                </div>

                <div>
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
                </div>

                <div>
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
                </div>
              </div>
            </c:if>

            <c:if test="${fn:contains(supplierTypeCode, 'SERVICE')}">
              <div class="tab-pane <c:if test=" ${liCountSer==1} ">active in</c:if> fade height-200" id="tab-4">
                <div>
                  <table class="table table-bordered table-condensed table-hover">
                    <thead>
                      <tr>
                        <th class="info w50">序号</th>
                        <th class="info">资质证书名称</th>
                        <th class="info">证书图片</th>
                      </tr>
                    </thead>
                    <c:forEach items="${supplierCertSes}" var="s" varStatus="vs">
                      <tr>
                        <td class="tc">${vs.index + 1}</td>
                        <td class="tc">${s.name }</td>
                        <td class="tc">
                          <div class="w110 fl">
                            <u:show showId="ser_show${vs.index+1}" businessId="${s.id}" delete="false" typeId="${supplierDictionaryData.supplierServeCert}" sysKey="${sysKey}" />
                          </div>
                        </td>
                      </tr>
                    </c:forEach>
                  </table>
                </div>
              </div>
            </c:if>
          </div>
        </ul>
      </div>
    </div>

    <form target="_blank" id="download_form_id" action="${pageContext.request.contextPath}/supplierAudit/download.html" method="post">
      <input type="hidden" name="fileName" />
    </form>
  </body>

</html>