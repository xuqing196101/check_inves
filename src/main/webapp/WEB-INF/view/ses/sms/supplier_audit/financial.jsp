<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE html>
<html class=" js cssanimations csstransitions" lang="en">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>财务信息</title>
<!-- Meta -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script type="text/javascript">
function reason(id){
  var id1=id+"1";
  var id2=id+"2";
  var supplierName=$("#"+id2+"").text().replaceAll("＊","");
    layer.confirm('确认审核不通过？', {
      btn: ['不通过','通过'] //按钮
  }, function(){
      $("#"+id1+"").hide();
      layer.prompt({title: '请填写不通过理由', formType: 2}, function(text){
          var ul=document.getElementById("reason");             
          var obj=document.createElement("li"); 
          obj.className="col-md-6 p0";
          obj.innerHTML="<span>"+supplierName+"</span><div class='input-append'><input class='span3 red' id='supplierType' name="+id+" value="+text+"  type='text'></div>";
          ul.appendChild(obj); 
        layer.msg("审核不通过的理由是："+text);
    });
  });
}

function tijiao(str){
  var action;
  if(str=="essential"){
     action ="<%=basePath%>supplierAudit/essential.html";
  }
  if(str=="financial"){
    action = "<%=basePath%>supplierAudit/financial.html";
  }
  if(str=="shareholder"){
    action = "<%=basePath%>supplierAudit/shareholder.html";
  }
  if(str=="materialProduction"){
    action = "<%=basePath%>supplierAudit/materialProduction.html";
  }
  if(str=="materialProduction"){
    action = "<%=basePath%>supplierAudit/materialSales.html";
  }
  if(str=="engineering"){
    action = "<%=basePath%>supplierAudit/engineering.html";
  }
  if(str=="reasonsList"){
    action = "<%=basePath%>supplierAudit/reasonsList.html";
  }
  $("#form_id").attr("action",action);
  $("#form_id").submit();
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
              <li class=""><a aria-expanded="fale" href="#tab-1" data-toggle="tab" onclick="tijiao('essential');">基本信息</a></li>
              <li class="active"><a aria-expanded="true" href="#tab-2" data-toggle="tab" onclick="tijiao('financial');">财务信息</a></li>
              <li class=""><a aria-expanded="fale" href="#tab-3" data-toggle="tab" onclick="tijiao('shareholder');">股东信息</a></li>
              <li class=""><a aria-expanded="fale" href="#tab-2" data-toggle="tab" onclick="tijiao('materialProduction');">物资-生产型专业信息</a></li>
              <li class=""><a aria-expanded="fale" href="#tab-3" data-toggle="tab" onclick="tijiao('materialSales');">物资-销售型专业信息</a></li>
              <li class=""><a aria-expanded="false" href="#tab-3" data-toggle="tab" onclick="tijiao('engineering');">工程-专业信息</a></li>
              <li class=""><a aria-expanded="false" href="#tab-3" data-toggle="tab" >服务-专业信息</a></li>
              <li class=""><a aria-expanded="false" href="#tab-2" data-toggle="tab" >品目信息</a></li>
              <li class=""><a aria-expanded="false" href="#tab-3" data-toggle="tab" >产品信息</a></li>
              <li class=""><a aria-expanded="false" href="#tab-2" data-toggle="tab" onclick="tijiao('reasonsList');">审核汇总</a></li>
            </ul>
              <div class="tab-content padding-top-20" style="height:1400px;">
                <div class="tab-pane fade active in height-450" id="tab-1">
                  <form id="form_id" action="" method="post"  enctype="multipart/form-data">
                      <input name="supplierId" value="${supplierId}" type="hidden">
                  </form>
                  <table class="table table-bordered table-condensed">
                   <thead>
                     <tr>
                       <th class="info w50">序号</th>
                       <th class="info">会计事务所名称</th>
                       <th class="info">事务所联系电话</th>
                       <th class="info">审计人姓名</th>
                       <th class="info">指标</th>
                       <th class="info">资产总额</th>
                       <th class="info">负债总额</th>
                       <th class="info">净资产总额</th>
                       <th class="info">营业收入</th>
                     </tr>
                   </thead>
                     <c:forEach items="${financial}" var="f" >
                       <tr>
                         <td class="tc w50"></td>
                         <td class="tc">${f.name }</td>
                         <td class="tc">${f.telephone }</td>
                         <td class="tc">${f.auditors }</td>
                         <td class="tc">${f.quota }</td>
                         <td class="tc">${f.totalAssets }</td>
                         <td class="tc">${f.totalLiabilities }</td>
                         <td class="tc">${f.totalNetAssets}</td>
                         <td class="tc">${f.taking}</td>
                       </tr>
                     </c:forEach>
                  </table>
                  
                  <div class=" margin-bottom-0">
                    <ul class="list-unstyled list-flow">
                      <li class="col-md-6 p0 "><span class=""><i class="red">＊</i>近三年财务审计报告意见表：</span>
                        <div class="input-append">
                          <a class="span3">附件下载</a>
                          <div class="b f18 fl ml10 red hand">√</div>
                          <div onclick="reason()" class="b f18 fl ml10 hand">×</div>
                        </div>
                      </li>
                      <li class="col-md-6 p0 "><span class=""><i class="red">＊</i>资产负债表：</span>
                        <div class="input-append">
                          <a class="span3">附件下载</a>
                          <div class="b f18 fl ml10 red hand">√</div><div onclick="reason()" class="b f18 fl ml10 hand">×</div>
                        </div>
                      </li>
                      <li class="col-md-6 p0 "><span class=""><i class="red">＊</i>近三年利润表：</span>
                        <div class="input-append">
                          <a class="span3">附件下载</a>
                          <div class="b f18 fl ml10 red hand">√</div><div onclick="reason()" class="b f18 fl ml10 hand">×</div>
                        </div>
                      </li>
                      <li class="col-md-6 p0 "><span class=""><i class="red">＊</i>近三年现金流量表：</span>
                        <div class="input-append">
                          <a class="span3">附件下载</a>
                          <div class="b f18 fl ml10 red hand">√</div><div onclick="reason()" class="b f18 fl ml10 hand">×</div>
                        </div>
                      </li>
                      <li class="col-md-6 p0 "><span class=""><i class="red">＊</i>所有者权益变动表：</span>
                        <div class="input-append">
                          <a class="span3">附件下载</a>
                          <div class="b f18 fl ml10 red hand">√</div><div onclick="reason()" class="b f18 fl ml10 hand">×</div>
                        </div>
                      </li>
                    </ul>
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
