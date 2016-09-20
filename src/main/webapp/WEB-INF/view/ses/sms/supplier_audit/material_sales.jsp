<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE html>
<html class=" js cssanimations csstransitions" lang="en">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>物资-销售型专业信息</title>
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
              <li class=""><a aria-expanded="fale" href="#tab-1" data-toggle="tab" onclick="location='<%=basePath%>supplierAudit/essential.html'">基本信息</a></li>
              <li class=""><a aria-expanded="fale" href="#tab-2" data-toggle="tab" onclick="location='<%=basePath%>supplierAudit/financial.html'">财务信息</a></li>
              <li class=""><a aria-expanded="fale" href="#tab-3" data-toggle="tab" onclick="location='<%=basePath%>supplierAudit/shareholder.html'">股东信息</a></li>
              <li class=""><a aria-expanded="fale" href="#tab-2" data-toggle="tab" onclick="location='<%=basePath%>supplierAudit/materialProduction.html'">物资-生产型专业信息</a></li>
              <li class="active"><a aria-expanded="true" href="#tab-3" data-toggle="tab" onclick="location='<%=basePath%>supplierAudit/materialSales.html'">物资-销售型专业信息</a></li>
              <li class=""><a aria-expanded="false" href="#tab-3" data-toggle="tab" onclick="location='<%=basePath%>supplierAudit/engineering.html'">工程-专业信息</a></li>
              <li class=""><a aria-expanded="false" href="#tab-3" data-toggle="tab" >服务-专业信息</a></li>
              <li class=""><a aria-expanded="false" href="#tab-2" data-toggle="tab" >品目信息</a></li>
              <li class=""><a aria-expanded="false" href="#tab-3" data-toggle="tab" >产品信息</a></li>
              <li class=""><a aria-expanded="false" href="#tab-2" data-toggle="tab" onclick="location='<%=basePath%>supplierAudit/reasonsList.html'">审核汇总</a></li>
            </ul>
              <div class="tab-content padding-top-20" style="height:1400px;">
                <div class="tab-pane fade active in height-450" id="tab-1">
                  <table class="table table-bordered table-condensed">
                    <thead>
                      <tr>
                        <th class="info w50">序号</th>
                        <th class="info">出资人</th>
                        <th class="info">出资人性质</th>
                        <th class="info">统一社会信用代码或身份证</th>
                        <th class="info">出资金额或股份(万元/份)</th>
                        <th class="info">比例</th>
                      </tr>
                    </thead>
                    <c:forEach items="${shareholder}" var="s" >
                      <tr>
                        <td class="tc w50"></td>
                        <td class="tc">${s.name}</td>
                        <td class="tc">${s.nature}</td>
                        <td class="tc">${s.identity}</td>
                        <td class="tc">${s.shares}</td>
                        <td class="tc">${s.proportion}</td>
                      </tr>
                    </c:forEach>
                  </table>
                </div>
              </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</body>
</html>
