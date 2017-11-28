<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]--><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
  <title>专家评分详情</title>

  <!-- Meta -->
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta name="description" content="">
  <meta name="author" content="">
<jsp:include page="../../ses/bms/page_style/backend_common.jsp"></jsp:include>
<jsp:include page="../../common.jsp"></jsp:include>
<script type="text/javascript">
   
function printResult(projectId,packageId){
     window.location.href="${pageContext.request.contextPath}/packageExpert/showViewWord.html?projectId="+projectId+"&packId="+packageId;

} 
</script>
</head>
<body>
  <div class="container">
   <div class="tc"><h2>评审记录</h2></div>
  <div align="right">
  <button class="btn" onclick="printResult('${projectId}','${packId}');" type="button">打印</button>
  </div>
  <!-- 表格开始-->
  <div>
  
  <div class="content mt0">
     <c:forEach items="${selectList}" var="lis">
             <div class="content" id="content">
             <h4>评审人员：${lis.expert.relName}</h4>
          <table id="table" class="table table-bordered table-condensed table_input left_table lockout mb0">
        <tr>
               <th class="tc" rowspan="2" width="120">评审项目</th>
              <th class="tc" rowspan="2" width="180">评审指标</th>
              <th class="tc" rowspan="2" width="100">标准分值</th>
              <c:forEach items="${supplierList}" var="supplier">
              <th class="tc" colspan="2">${supplier.suppliers.supplierName}</th>
            </c:forEach>
        </tr>
        <tr>
        <c:forEach items="${supplierList}" var="supplier">
                  <th class="tc">参数</th>
                  <th class="tc">得分</th>
                </c:forEach>
        </tr>
          <c:forEach items="${lis.markTerms}" var="markTerm">
            <c:if test="${markTerm.checkedPrice!=1}">
            <c:forEach items="${lis.scoreModels}" var="score" varStatus="vs">
              <c:if test="${score.markTerm.pid eq markTerm.id}">
                <tr>
                  <!-- 所属模型 -->
                  <c:set var="model" value=""/>
                  <c:if test="${score.typeName == 0}"><c:set var="model" value="模型一A"/></c:if>
                  <c:if test="${score.typeName == 1}"><c:set var="model" value="模型二"/></c:if>
                <c:if test="${score.typeName == 2}"><c:set var="model" value="模型三"/></c:if>
                <c:if test="${score.typeName == 3}"><c:set var="model" value="模型四 A"/></c:if>
                  <c:if test="${score.typeName == 4}"><c:set var="model" value="模型五"/></c:if>
                  <c:if test="${score.typeName == 5}"><c:set var="model" value="模型六"/></c:if>
                  <c:if test="${score.typeName == 6}"><c:set var="model" value="模型七"/></c:if>
                <c:if test="${score.typeName == 7}"><c:set var="model" value="模型八"/></c:if>
                <c:if test="${score.typeName == 8}"><c:set var="model" value="模型一B"/></c:if>
                <c:if test="${score.typeName == 9}"><c:set var="model" value="模型四B"/></c:if>
                  <td class="tc" rowspan="${score.count}" <c:if test="${score.count eq '0' or score.count == 0}">style="display: none"</c:if> >${markTerm.name}</td>
                  <td class="tc">
                    <a href="javascript:void();" title='所 属 模 型 : ${model}&#10;评 审 指 标 : ${score.name}&#10;评 审 内 容 : ${score.reviewContent}'>
                      <c:if test="${fn:length(score.name) <= 10}">${score.name}</c:if>
                      <c:if test="${fn:length(score.name) > 10}">${fn:substring(score.name, 0, 10)}...</c:if>
                    </a>
                  </td>
                <td class="tc">${score.standardScore}</td>
                <c:forEach items="${supplierList}" var="supplier">
                <td class="tc">
                   <c:forEach items="${lis.expertScores}" var="sco">
                      <c:if test="${sco.packageId eq lis.packageId and sco.expertId eq lis.expertId and sco.supplierId eq supplier.suppliers.id and sco.scoreModelId eq score.id}">
                           ${sco.expertValue }
                       </c:if>
                    </c:forEach>
                </td>
                <td class="tc">
                  <input type="hidden" name="supplierId"  value="${supplier.suppliers.id}"/>
                  <input type="hidden" name="expertScore" readonly="readonly"
                    <c:forEach items="${lis.expertScores}" var="sco">
                      <c:if test="${sco.packageId eq lis.packageId and sco.expertId eq lis.expertId and sco.supplierId eq supplier.suppliers.id and sco.scoreModelId eq score.id}">value="${sco.score}"</c:if>
                    </c:forEach>
                  />
                  <span><c:forEach items="${lis.expertScores}" var="sco">
                      <c:if test="${sco.packageId eq lis.packageId and sco.expertId eq lis.expertId and sco.supplierId eq supplier.suppliers.id and sco.scoreModelId eq score.id}"><font color="red" class="f18">${sco.score}</font></c:if>
                    </c:forEach></span>
                </td>
                </c:forEach>
              </tr>
            </c:if>
          </c:forEach>
          </c:if>
         </c:forEach>
         <tr>
          <td class="tc">合计</td>
          <td class="tc">--</td>
          <td class="tc">--</td>
          <c:forEach items="${supplierList}" var="supplier">
              <td class="tc"></td>
              <td class="tc"  >
                <input type="hidden" name="${supplier.suppliers.id}_total"/>
                <span>
                  <c:set var="sum_score" value="0"/>
                  <c:forEach items="${lis.expertScores}" var="sco">
                    <c:if test="${sco.packageId eq lis.packageId and sco.expertId eq lis.expertId and sco.supplierId eq supplier.suppliers.id}">
                      <c:set var="sum_score" value="${sum_score+sco.score}"/>
                    </c:if>
                  </c:forEach>
                  <font color="red" class="f18">${sum_score}</font>
                  <c:set var="sum_score" value="0"/>
                </span>
              </td>
            </c:forEach>
         </tr>
         </table>
         </div>
         </c:forEach>
         </div>
  </div>
</div>
</body>
</html>
