<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="../../../../../index_head.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>股东信息</title>
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

<link href="<%=basePath%>public/layer/skin/layer.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/layer/skin/layer.ext.css" media="screen" rel="stylesheet" type="text/css">
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
    $(this).find("a").eq(0).hide();
    });
  });

function reason(id){
  var supplierId=$("#supplierId").val();
  /* var auditFieldName=$("#"+id).text()+"股东"; */ //审批的字段名字
  var auditContent=$("#"+id).text()+"股东信息"; //审批的字段内容
  var auditType=$("#shareholder").text();//审核类型
  var fail = false;
   layer.prompt({title: '请填写不通过的理由：', formType: 2}, function(text){
    $.ajax({
        url:"${pageContext.request.contextPath}/supplierAudit/auditReasons.html",
        type:"post",
        data:"auditType="+auditType+"&auditFieldName=股东信息"+"&auditContent="+auditContent+"&suggest="+text+"&supplierId="+supplierId+"&auditField="+id,
        dataType:"json",
        success:function(result){
        result = eval("(" + result + ")");
        if(result.msg == "fail"){
          fail = true;
          layer.msg('该条信息已审核过！', {
                shift: 6 //动画类型
            });
        }
      }
      });
      if(!fail){
	      $("#"+id+"_show").show();
	      layer.msg("审核不通过的理由是："+text);
	      }
    });
}

function nextStep(url){
  $("#form_id").attr("action",url);
  $("#form_id").submit();
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
              <li class=""><a>详细信息</a></li>
              <li class=""><a>财务信息</a></li>
              <li class="active"><a id="shareholder">股东信息</a></li>
              <c:if test="${fn:contains(supplierTypeNames, '生产型')}">
              <li class=""><a >物资-生产型专业信息</a></li>
              </c:if>
              <c:if test="${fn:contains(supplierTypeNames, '销售型')}">
              <li class=""><a >物资-销售型专业信息</a></li>
              </c:if>
              <c:if test="${fn:contains(supplierTypeNames, '工程')}">
              <li class=""><a >工程-专业信息</a></li>
              </c:if>
              <c:if test="${fn:contains(supplierTypeNames, '服务')}">
              <li class=""><a >服务-专业信息</a></li>
              </c:if>
              <li class=""><a >品目信息</a></li>
              <li class=""><a >产品信息</a></li>
              <li class=""><a >申请表</a></li>
              <li class=""><a >审核汇总</a></li>
            </ul>
              <div class="tab-content padding-top-20">
                <div class="tab-pane fade active in height-450" id="tab-1">
                  <form id="form_id" action="" method="post" >
                      <input id="supplierId" name="supplierId" value="${supplierId}" type="hidden">
                     
                  </form>
                  <table class="table table-bordered table-condensed">
                    <thead>
                      <tr>
                        <th class="info w50">序号</th>
                        <th class="info">出资人</th>
                        <th class="info">出资人性质</th>
                        <th class="info">统一社会信用代码或身份证</th>
                        <th class="info">出资金额或股份(万元/份)</th>
                        <th class="info">比例(%)</th>
                        <th class="info w50"></th>
                      </tr>
                    </thead>
                    <c:forEach items="${shareholder}" var="s" varStatus="vs">
                      <tr>
                        <td class="tc">${vs.index + 1}</td>
                        <td class="tc" id="${s.id }" onclick="reason('${s.id}');">${s.name}</td>
                        <td class="tc" onclick="reason('${s.id}');">${s.nature}</td>
                        <td class="tc" onclick="reason('${s.id}');">${s.identity}</td>
                        <td class="tc" onclick="reason('${s.id}');">${s.shares}</td>
                        <td class="tc" onclick="reason('${s.id}');">${s.proportion}%</td>
                        <td class="tc" onclick="reason('${s.id}');">
                          <a  class="b f18 fl ml10 hand red" id="${s.id}_show">×</a>
                        </td>
                      </tr>
                    </c:forEach>
                  </table>
                  <div class="col-md-12 add_regist tc">
                    <a class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="zhancun();">暂存</a>
                    <a class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="nextStep('${url}');">下一步</a>
						      </div>
                </div>
              </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</body>
</html>
