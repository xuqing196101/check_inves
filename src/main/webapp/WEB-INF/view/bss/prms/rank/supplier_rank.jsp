<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
<head>  
  <title>My JSP 'expert_list.jsp' starting page</title>
  <script type="text/javascript">
	function ycDiv(obj, index){
		if ($(obj).hasClass("spread") && !$(obj).hasClass("shrink")) {
	    	$(obj).removeClass("shrink");
	    	$(obj).addClass("shrink");
	  	} else {
	    	if ($(obj).hasClass("shrink") && !$(obj).hasClass("spread")) {
	      		$(obj).removeClass("shrink");
	      		$(obj).addClass("spread");
	   		}
	    }

		var divObj = new Array();
		divObj = $(".p0" + index);
		for (var i =0; i < divObj.length; i++) {
			if ($(divObj[i]).hasClass("p0"+index) && $(divObj[i]).hasClass("hide")) {
		    	$(divObj[i]).removeClass("hide");
		    } else {
		        if ($(divObj[i]).hasClass("p0"+index)) {
		    		$(divObj[i]).addClass("hide");
		        }
			}
		}
	}
	$(function(){
		var flag = "${flag}";
		if (flag == '1') {
			layer.msg("没有显示出来的包为暂未结束评审状态!",{offset: ['200px', '260px']});
		}
	});
  </script>
</head>
  <body>
    <h2 class="list_title">供应商排名</h2>
	  <div class="tab-pane fade active in" id="tab-1">
        <c:forEach items="${packagesList}" var="pack" varStatus="vs">
          <h2 onclick="ycDiv(this,'${vs.index}')" class="count_flow spread hand">${pack.name}</h2>
          <div class="p0${vs.index}">
            <table class="table table-bordered table-condensed table-hover table-striped">
              <tr>
                <td class="tc" colspan="2">专家/供应商</td>
                <c:forEach items="${supplierList}" var="supplier">
                  <c:if test="${supplier.packages eq pack.id}">
	                <td class="tc">${supplier.suppliers.supplierName}</td>
                  </c:if>
                </c:forEach>
              </tr>
              <c:forEach items="${expertList}" var="expert">
                <c:if test="${expert.packageId eq pack.id}">
                  <tr>
                  	<td class="tc w100" rowspan="${expert.count}" <c:if test="${expert.count eq '0' or expert.count == 0}">style="display: none"</c:if> >${expert.reviewTypeId}</td>
                    <td class="tc w100">${expert.expert.relName}</td>
                    <c:forEach items="${supplierList}" var="supplier">
                  	  <c:if test="${supplier.packages eq pack.id}">
	                    <td class="tc">
	                      <c:forEach items="${expertScoreList}" var="score">
	                        <c:if test="${score.packageId eq pack.id and score.supplierId eq supplier.suppliers.id and score.expertId eq expert.expert.id}">
	                          ${score.score}
	                        </c:if>
	                      </c:forEach>
	                    </td>
                      </c:if>
                    </c:forEach>
                  </tr>
                </c:if>
              </c:forEach>
              <tr>
                <td class="tc" colspan="2">总分</td>
                <c:forEach items="${supplierList}" var="supplier">
                  <c:if test="${supplier.packages eq pack.id}">
	                <td class="tc">
	                  <c:forEach items="${rankList}" var="rank">
	                    <c:if test="${rank.packageId eq pack.id && rank.supplierId eq supplier.suppliers.id}">
	                      ${rank.econScore}(经济)+${rank.techScore}(技术)=${rank.sumScore}
	                    </c:if>
	                  </c:forEach>
	                </td>
                  </c:if>
                </c:forEach>
              </tr>
              <tr>
                <td class="tc" colspan="2">排名</td>
                <c:forEach items="${supplierList}" var="supplier">
                  <c:if test="${supplier.packages eq pack.id}">
	                <td class="tc">
	                  <c:forEach items="${rankList}" var="rank">
	                    <c:if test="${rank.packageId eq pack.id and rank.supplierId eq supplier.suppliers.id and (rank.reviewResult == null or rank.reviewResult eq '')}">
	                      ${rank.rank}
	                    </c:if>
	                    <c:if test="${rank.packageId eq pack.id and rank.supplierId eq supplier.suppliers.id and rank.reviewResult != null and rank.reviewResult ne ''}">
	                      ${rank.reviewResult}
	                    </c:if>
	                  </c:forEach>
	                </td>
                  </c:if>
                </c:forEach>
              </tr>
			</table>
          </div>
        </c:forEach>
      </div>
  </body>
</html>
