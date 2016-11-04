<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="../../../../../index_head.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>产品信息</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/common.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/bootstrap.min.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/style.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/line-icons.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/app.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/application.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/header-v4.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/footer-v2.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/img-hover.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/page_job.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/shop.style.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/supplier/css/supplier.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/upload/upload.css" type="text/css" />

<link href="${pageContext.request.contextPath}/public/layer/skin/layer.css" media="screen" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/public/layer/skin/layer.ext.css" media="screen" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/upload/upload.js"></script>
<script src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
<script src="${pageContext.request.contextPath}/public/layer/extend/layer.ext.js"></script>
<style type="text/css">
td {
  cursor:pointer;
}
</style>
<script type="text/javascript">
  //默认不显示叉
   $(function() {
    $("td").each(function() {
    $(this).parent("tr").find("td").eq(12).find("a").hide();
    });
  });

function reason(id){
  var supplierId=$("#supplierId").val();
  var auditFieldName="序号为："+$("#"+id+"_index").text(); //审批的字段名字
  var auditContent="产品名称为："+$("#"+id+"_name").text()+"的信息"; //审批的字段内容
  var auditType=$("#product").text();//审核类型
  var fail = false;
   layer.prompt({title: '请填写不通过的理由：', formType: 2}, function(text){
    $.ajax({
        url:"${pageContext.request.contextPath}/supplierAudit/auditReasons.html",
        type:"post",
        /* data:"&auditField="+auditField+"&suggest="+text+"&supplierId="+supplierId, */
        data:"auditType="+auditType+"&auditFieldName="+auditFieldName+"&auditContent="+auditContent+"&suggest="+text+"&supplierId="+supplierId+"&auditField=表格",
        dataType:"json",
        success:function(result){
        result = eval("(" + result + ")");
        if(result.msg == "fail"){
          fail = true;
          layer.msg("该条信息已审核过！");
        }
      }
      });
      if(!fail){
		    $("#"+id+"_show").show();
		    layer.msg("审核不通过的理由是："+text);
		    }
    });
}

function nextStep(){
  var action = "${pageContext.request.contextPath}/supplierAudit/applicationForm.html";
  $("#form_id").attr("action",action);
  $("#form_id").submit();
}

//文件下載
  function downloadFile(fileName) {
    $("input[name='fileName']").val(fileName);
    $("#download_form_id").submit();
  }
</script>
<script type="text/javascript">
  function zhancun(){
    var supplierId=$("#supplierId").val();
    $.ajax({
      url:"${pageContext.request.contextPath}/supplierAudit/temporaryAudit.html",
      type:"post",
      data:"id="+supplierId,
      dataType:"json",
      success:function(result){
        result = eval("(" + result + ")");
        if(result.msg == "success"){
          layer.msg("暂存成功！");
        }
      },error:function(){
        layer.msg("暂存失败！");
      }
    });
  }
</script>
</head>
  
<body>
  <!-- 项目戳开始 -->
  <div class="container clear margin-top-30">
    <!--详情开始-->
    <div class="container content height-350">
      <div class="row magazine-page">
        <div class="col-md-12 tab-v2 job-content">
          <div class="padding-top-10">
            <ul class="nav nav-tabs bgdd">
              <li class=""><a >详细信息</a></li>
              <li class=""><a >财务信息</a></li>
              <li class=""><a >股东信息</a></li>
              <c:if test="${fn:contains(supplierTypeNames, '生产型')}">
              <li class=""><a>物资-生产型专业信息</a></li>
              </c:if>
              <c:if test="${fn:contains(supplierTypeNames, '销售型')}">
              <li class=""><a>物资-销售型专业信息</a></li>
              </c:if>
              <c:if test="${fn:contains(supplierTypeNames, '工程')}">
              <li class=""><a >工程-专业信息</a></li>
              </c:if>
              <c:if test="${fn:contains(supplierTypeNames, '服务')}">
              <li class=""><a >服务-专业信息</a></li>
              </c:if>
              <li class=""><a>品目信息</a></li>
              <li class="active"><a id="product">产品信息</a></li>
              <li class=""><a>申请表</a></li>
              <li class=""><a>审核汇总</a></li>
            </ul>
              <div class="tab-content padding-top-20">
                <div class="tab-pane fade active in height-450" id="tab-1">
                  <form id="form_id" action="" method="post">
                      <input id="supplierId" name="supplierId" value="${supplierId}" type="hidden">
                  </form>
                  <table id="share_table_id" class="table table-bordered table-condensed">
                      <thead>
                        <tr>
                          <th class="info w50">序号</th>
                          <th class="info">所属类别</th>
                          <th class="info">产品名称</th>
                          <th class="info">品牌</th>
                          <th class="info">规格型号</th>
                          <th class="info">尺寸</th>
                          <th class="info">生产产地</th>
                          <th class="info">保质期</th>
                          <th class="info">生产商</th>
                          <th class="info">参考价格</th>
                          <th class="info">产品图片</th>
                          <th class="info">商品二维码</th>
                          <th class="info w50"></th>
                        </tr>
                      </thead>
                      <tbody id="products_tbody_id">
                        <c:forEach items="${productsList}" var="products" varStatus="vs">
                          <tr>
                            <td class="tc" id="${products.id}_index">${vs.index+1 }</td>
                            <td id="${products.categoryId}" class="tc">${products.categoryName}</td>
                            <td class="tc" id="${products.id}_name" onclick="reason('${products.id}');">${products.name}</td>
                            <td class="tc" onclick="reason('${products.id}');">${products.brand}</td>
                            <td class="tc" onclick="reason('${products.id}');">${products.models}</td>
                            <td class="tc" onclick="reason('${products.id}');">${products.proSize}</td>
                            <td class="tc" onclick="reason('${products.id}');">${products.orgin}</td>
                            <td class="tc" onclick="reason('${products.id}');"><fmt:formatDate value="${products.expirationDate }" pattern="yyyy-MM-dd"/></td>
                            <td class="tc" onclick="reason('${products.id}');">${products.producer}</td>
                            <td class="tc" onclick="reason('${products.id}');">${products.referencePrice}</td>
                            <td class="tc">
                              <c:if test="${products.productPic != null}">
                                <a class="green" onclick="downloadFile('${products.productPic}')">下载附件</a>
                              </c:if>
                              <c:if test="${products.productPic == null}"><a class="red">无附件下载</a></c:if>
                            </td>
                            <td class="tc" >
                              <c:if test="${products.qrCode != null}">
                                <a class="green" onclick="downloadFile('${products.qrCode}')">下载附件</a>
                              </c:if>
                              <c:if test="${products.qrCode == null}"><a class="red">无附件下载</a></c:if>
                            </td>
                            <td class="tc">
                              <a  id="${products.id }_show" class="b f18 fl ml10 hand red">×</a>
                            </td>
                          </tr>
                        </c:forEach>
                      </tbody>
                    </table>
                    <div class="col-md-12 add_regist tc">
                      <a class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="zhancun();">暂存</a>
                      <a class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="nextStep();">下一步</a>
                    </div>
                </div>
              </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <form target="_blank" id="download_form_id" action="${pageContext.request.contextPath}/supplierAudit/download.html" method="post">
   <input type="hidden" name="fileName" />
  </form>
</body>
</html>
