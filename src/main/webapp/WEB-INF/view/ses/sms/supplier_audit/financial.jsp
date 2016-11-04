<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="../../../../../index_head.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>详细信息</title>
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
    $(this).find("a").eq(0).hide();
    });
    
    $("a").each(function() {
      $(this).parent("div").find("div").eq(0).hide();
    });
  });


function reason(id,auditField){
  var supplierId=$("#supplierId").val();
  /* var auditFieldName=$("#"+id).text()+"年财务";  *///审批的字段名字
  var auditType=$("#financial").text();//审核类型
  var auditContent=$("#"+id).text()+"年财务信息";//审批的字段内容
  var fail = false;
  layer.prompt({title: '请填写不通过的理由：', formType: 2}, function(text){
    $.ajax({
        url:"${pageContext.request.contextPath}/supplierAudit/auditReasons.html",
        type:"post",
        data:"auditType="+auditType+"&auditFieldName="+id+"&auditContent="+auditContent+"&suggest="+text+"&supplierId="+supplierId+"&auditField=表格",
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

function reason1(year, ele,auditField){
  var supplierId=$("#supplierId").val();
  var value = $(ele).parents("li").find("span").text().replace("：","");//审批的字段名字
 /*  var auditFieldName=year+"年"+value;//审批的字段名字 */
  var auditFieldName=year+"年";//审批的字段名字
  var auditType=$("#financial").text();//审核类型
  var fail = false;
    layer.prompt({title: '请填写不通过的理由：', formType: 2}, function(text){
      $.ajax({
          url:"${pageContext.request.contextPath}/supplierAudit/auditReasons.html",
          type:"post",
          /* data:"&auditField="+auditField+"&suggest="+text+"&supplierId="+supplierId, */
          data:"auditType="+auditType+"&auditFieldName="+auditFieldName+"&auditContent=附件"+"&suggest="+text+"&supplierId="+supplierId+"&auditField="+auditField,
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
	        $(ele).parent("li").find("div").eq(1).show(); //隐藏勾
	        layer.msg("审核不通过的理由是："+text);
        }
      });
}

function nextStep(){
  var action = "${pageContext.request.contextPath}/supplierAudit/shareholder.html";
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
  <div class="wrapper">
    <div class="container content height-350">
      <div class="row magazine-page">
        <div class="col-md-12 tab-v2 job-content">
          <div class="padding-top-10">
            <ul class="nav nav-tabs bgdd">
              <li class=""><a>详细信息</a></li>
              <li class="active"><a id="financial">财务信息</a></li>
              <li class=""><a >股东信息</a></li>
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
             <div class="tab-content padding-top-20" style="height:800px;">
                <div class="tab-pane fade active in height-450" id="tab-1">
                  <form id="form_id" action="" method="post" >
                      <input id="supplierId" name="supplierId" value="${supplierId}" type="hidden">
                  </form>
                  <table class="table table-bordered table-condensed">
                   <thead>
                     <tr>
                       <th class="info w50">序号</th>
                       <th class="info">年份</th>
                       <th class="info">会计事务所名称</th>
                       <th class="info">事务所联系电话</th>
                       <th class="info">审计人姓名</th>
                       <th class="info">指标</th>
                       <th class="info">资产总额</th>
                       <th class="info">负债总额</th>
                       <th class="info">净资产总额</th>
                       <th class="info">营业收入</th>
                       <th class="info w50"></th>
                     </tr>
                   </thead>
                     <c:forEach items="${financial}" var="f" varStatus="vs">
                       <tr>
                         <td class="tc">${vs.index + 1}</td>
                         <td class="tc" id="${f.id }" onclick="reason('${f.id}');">${f.year } </td>
                         <td class="tc" onclick="reason('${f.id}');" >${f.name }</td>
                         <td class="tc" onclick="reason('${f.id}');">${f.telephone }</td>
                         <td class="tc" onclick="reason('${f.id}');">${f.auditors }</td>
                         <td class="tc" onclick="reason('${f.id}');">${f.quota }</td>
                         <td class="tc" onclick="reason('${f.id}');">${f.totalAssets }</td>
                         <td class="tc" onclick="reason('${f.id}');">${f.totalLiabilities }</td>
                         <td class="tc" onclick="reason('${f.id}');">${f.totalNetAssets}</td>
                         <td class="tc" onclick="reason('${f.id}');">${f.taking}</td>
                         <td class="tc" >
                         <a id="${f.id}_show" class="b f18 fl ml10 hand red" >×</a>
                         </td>
                       </tr>
                     </c:forEach>
                  </table>
                  
                  <c:forEach items="${financial}" var="f" varStatus="vs">
                  <div class=" margin-bottom-0 fl">
                    <h2 class="f16 jbxx mt40">
                    <i>${vs.index + 1}</i>${f.year }年
                    </h2>
                    <ul class="list-unstyled list-flow hand">
                      <li class="col-md-6 p0 "><span class="" onclick="reason1('${f.year }', this,'auditOpinion');">财务审计报告意见表：</span>
                        <div class="input-append">
                          <c:if test="${f.auditOpinion != null}">
                            <a class="span3 green" onclick="downloadFile('${f.auditOpinion}')">附件下载</a>
                          </c:if>
                          <c:if test="${f.auditOpinion == null}">
                            <a class="span3 red">无附件下载</a>
                          </c:if>
                          <div  class="b f18 ml10 hand red">×</div>
                        </div>
                      </li>
                      <li class="col-md-6 p0 "><span class="" onclick="reason1('${f.year }', this,'liabilitiesList');">资产负债表：</span>
                        <div class="input-append">
                          <c:if test="${f.liabilitiesList !=null}">
                            <a class="span3 green" onclick="downloadFile('${f.liabilitiesList}')">附件下载</a>
                          </c:if>
                          <c:if test="${f.liabilitiesList == null}">
                            <a class="span3 red">无附件下载</a>
                          </c:if>
                          <div  class="b f18 fl ml10 hand red">×</div>
                        </div>
                      </li>
                      <li class="col-md-6 p0 "><span class="" onclick="reason1('${f.year }', this,'profitList');">利润表：</span>
                        <div class="input-append">
                          <c:if test="${f.profitList !=null}">
                            <a class="span3 green" onclick="downloadFile('${f.profitList}')">附件下载</a>
                          </c:if>
                          <c:if test="${f.profitList == null}">
                            <a class="span3 red">无附件下载</a>
                          </c:if>
                          <div  class="b f18 fl ml10 hand red">×</div>
                        </div>
                      </li>
                      <li class="col-md-6 p0 "><span class="" onclick="reason1('${f.year }', this,'cashFlowStatement');">现金流量表：</span>
                        <div class="input-append">
                          <c:if test="${f.cashFlowStatement !=null}">
                            <a class="span3 green" onclick="downloadFile('${f.cashFlowStatement}')">附件下载</a>
                          </c:if>
                          <c:if test="${f.cashFlowStatement == null}">
                            <a class="span3 red">无附件下载</a>
                          </c:if>
                          <div class="b f18 fl ml10 hand red">×</div>
                        </div>
                      </li>
                      <li class="col-md-6 p0 "><span class="" onclick="reason1('${f.year }', this,'changeList');">所有者权益变动表：</span>
                        <div class="input-append">
                          <c:if test="${f.changeList !=null}">
                            <a class="span3 green" onclick="downloadFile('${f.changeList}')">附件下载</a>
                          </c:if>
                          <c:if test="${f.changeList == null}">
                            <a class="span3 red">无附件下载</a>
                          </c:if>
                          <div class="b f18 fl ml10 hand red">×</div>
                        </div>
                      </li>
                    </ul>
                  </div>
                  </c:forEach>
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
