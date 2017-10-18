<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
<head>  
  <title>My JSP 'expert_list.jsp' starting page</title>
  <script type="text/javascript">
	function ycDiv(obj, index){
		if ($(obj).hasClass("spread") && !$(obj).hasClass("shrink")) {
	    	$(obj).removeClass("spread");
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
			layer.msg("没有显示出来的包为暂未结束评审状态!",{offset: '200px'});
		}
	});
	
	function pack(packId,projectId){
		$.ajax({
			url:'${pageContext.request.contextPath}/reviewFirstAudit/save_score.html',
			data:{"packId":packId,"projectId":projectId},
			type:"post",
			dataType:'JSON',
			success:function(data){
				 var datas=JSON.parse(data);
				 var spriceScore=datas.spriceScore;
				 var ranks=datas.ranks;
				 var scores=datas.scores;
				 if(spriceScore.length>0){
					 for(var i=0;i<spriceScore.length;i++){
							$("#price_"+spriceScore[i].id).text(spriceScore[i].pScore);
						}
				 }
				 if(scores.length>0){
					 for(var i=0;i<scores.length;i++){
							$("#score_"+scores[i].id).text(scores[i].score);
						}
				 }
				 if(ranks.length>0){
					 for(var i=0;i<ranks.length;i++){
							$("#rank_"+ranks[i].id).text(ranks[i].rank);
						}
				 }
						/* for(var i=0;i<data.length;i++){
							$("#price_"+data[i].supplierId+"_"+data[i].packageId).text(data[i].score);
						} */
			}
		});
	}
  </script>
</head>
  <body>
    <h2 class="list_title">供应商排名</h2>
	  <div class="tab-pane fade active in" id="tab-1">
        <c:forEach items="${packagesList}" var="pack" varStatus="vs">
          <div class="over_scroll col-md-12 col-xs-12 col-sm-12 p0 m0">
          <h2 onclick="ycDiv(this,'${vs.index}')" class="count_flow hand fl clear spread">${pack.name}</h2>
          
          <c:if test="${'PBFF_JZJF' eq pack.bidMethodTypeName}">
             <div class="clear"></div>
         		<c:set var="isDone" value="0" scope="page"></c:set>
         		<c:forEach items="${supplierList}" var="supplier">
                  <c:if test="${isDone ne '1' && supplier.packages eq pack.id}">
	                                                基准价：<fmt:formatNumber type="number" value="${supplier.jzjf.benchmarkPrice}" pattern="0.0000" maxFractionDigits="4"/>   
	                                                浮动比例：<fmt:formatNumber type="number" value="${supplier.jzjf.floatingRatio}" pattern="0.00" maxFractionDigits="2"/>%     
	                                                中标参考价：<fmt:formatNumber type="number" value="${supplier.jzjf.bidPrice}" pattern="0.0000" maxFractionDigits="4"/>     
	                                                有效平均报价：<fmt:formatNumber type="number" value="${supplier.jzjf.effectiveAverageQuotation}" pattern="0.0000" maxFractionDigits="4"/>     
                  	<c:set var="isDone" value="1" scope="page"></c:set>
                  </c:if>
                </c:forEach>
          </c:if>
          <c:if test="${'OPEN_ZHPFF' eq pack.bidMethodTypeName}">
           <div class="fl mt20 ml10">
             <button class="btn" onclick="pack('${pack.id}','${pack.projectId}');" type="button">计算报价得分</button>
           	</div>
           	<div class="clear"></div>
         		<c:set var="isDone1" value="0" scope="page"></c:set>
         		<c:forEach items="${supplierList}" var="supplier">
                  <c:if test="${isDone1 ne '1' && supplier.packages eq pack.id}">
         			<c:set var="num1" value="0" scope="page"></c:set>
         			<c:forEach var="msg" items="${fn:split(supplier.reviewResult, '_')}">
                        <c:if test="${num1 eq '1' }">
		                                                有效经济技术平均分（不含价格因素）：<fmt:formatNumber type="number" value="${msg}" pattern="0.00" maxFractionDigits="2"/>     
		                <c:set var="num1" value="2" scope="page"></c:set>                               
                        </c:if>
                        <c:if test="${num1 eq '0' }">
		                                                有效平均报价：<fmt:formatNumber type="number" value="${msg}" pattern="0.0000" maxFractionDigits="4"/>     
		                <c:set var="num1" value="1" scope="page"></c:set>                               
                        </c:if>
                    </c:forEach>
                  	<c:set var="isDone1" value="1" scope="page"></c:set>
                  </c:if>
                </c:forEach>
           
          </c:if>
          
          <div class="p0${vs.index}">
            <table class="table table-bordered table-condensed table-hover    p0 space_nowrap">
              <tr>
                <td class="tc" rowspan="2">分类</td>
                <td class="tc" rowspan="2">评委名称</td>
                <c:forEach items="${supplierList}" var="supplier">
                  <c:if test="${supplier.packages eq pack.id}">
	                <td class="tc" colspan="2">${supplier.suppliers.supplierName}</td>
                  </c:if>
                </c:forEach>
              </tr>
              <tr>
              <c:forEach items="${supplierList}" var="supplier">
                  <c:if test="${supplier.packages eq pack.id}">
	                <td class="info tc" colspan="2">检查结果</td>
                  </c:if>
                </c:forEach>
              </tr>
              <!-- 综合评分法 -->
          	  <c:if test="${'OPEN_ZHPFF' eq pack.bidMethodTypeName}">
          	     <tr>
          	      <td colspan="2" class="tc">报价得分</td>
	          	     <c:forEach items="${supplierList}" var="supplier">
		                  <c:if test="${supplier.packages eq pack.id}">
		                  	  <td class="tc" colspan="2" id="price_${supplier.suppliers.id}_${pack.id}">
		                  	  <c:forEach items="${scorePrice}" var="price">
		                  	    <c:if test="${price.packageId eq pack.id and price.supplierId eq supplier.suppliers.id}">
		                          <fmt:formatNumber value="${price.score}" pattern="0.00"></fmt:formatNumber>  
		                        </c:if>
		                  	  </c:forEach>
		                  	  </td>
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
		                    <td class="tc" colspan="2">
		                      <c:forEach items="${expertScoreList}" var="score">
		                        <c:if test="${score.packageId eq pack.id and score.supplierId eq supplier.suppliers.id and score.expertId eq expert.expert.id}">
		                          <fmt:formatNumber value="${score.score}" pattern="0.00"></fmt:formatNumber> 
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
		                <td class="tc" colspan="2" id="score_${supplier.suppliers.id}_${pack.id}">
		                  <c:forEach items="${rankList}" var="rank">
		                    <c:if test="${rank.packageId eq pack.id && rank.supplierId eq supplier.suppliers.id}">
		                       <c:if test="${rank.econScore!=0&&rank.techScore!=0&&rank.sumScore!=0&&rank.econScore!=null&&rank.techScore!=null}">
		                         <fmt:formatNumber value="${rank.priceScore}" pattern="0.00"></fmt:formatNumber>(价格)+<fmt:formatNumber value="${rank.econScore}" pattern="0.00"></fmt:formatNumber>(经济)+<fmt:formatNumber value="${rank.techScore}" pattern="0.00"></fmt:formatNumber>(技术)=<fmt:formatNumber value="${rank.sumScore}" pattern="0.00"></fmt:formatNumber>
		                       </c:if>
		                    </c:if>
		                  </c:forEach>
		                </td>
	                  </c:if>
	                </c:forEach>
	              </tr>
          	  </c:if>
              <!-- 最低价法 -->
              <c:if test="${'PBFF_ZDJF' eq pack.bidMethodTypeName}">
          	  	  <c:forEach items="${expertList}" var="expert">
	                <c:if test="${expert.packageId eq pack.id}">
	                  <tr>
	                  	<td class="tc w100" rowspan="${expert.count}" <c:if test="${expert.count eq '0' or expert.count == 0}">style="display: none"</c:if> >${expert.reviewTypeId}</td>
	                    <td class="tc w100">${expert.expert.relName}</td>
	                    <c:forEach items="${supplierList}" var="supplier">
	                  	  <c:if test="${supplier.packages eq pack.id}">
		                    <td class="tc" colspan="2">
		                      	符合
		                    </td>
	                      </c:if>
	                    </c:forEach>
	                  </tr>
	                </c:if>
	              </c:forEach>
          	  	  <tr>
	                <td class="tc" colspan="2">报价</td>
	                <c:forEach items="${supplierList}" var="supplier">
	                  <c:if test="${supplier.packages eq pack.id}">
		                <td class="tr" colspan="2">
		                	<fmt:formatNumber type="number" value="${fn:substringBefore(supplier.reviewResult,'_')}" pattern="0.0000" maxFractionDigits="4"/>
		                </td>
	                  </c:if>
	                </c:forEach>
	              </tr>
	              <tr>
	                <td class="tc" colspan="2">总结</td>
	                <c:forEach items="${supplierList}" var="supplier">
	                  <c:if test="${supplier.packages eq pack.id}">
		                <td class="tc" colspan="2">
		                  	符合
		                </td>
	                  </c:if>
	                </c:forEach>
	              </tr>
          	  </c:if>
          	  <!-- 基准价法 -->
          	  <c:if test="${'PBFF_JZJF' eq pack.bidMethodTypeName}">
          	  	  <tr>
          	  	  	<td class="tc w100">报价</td>
          	  	  	<td class="tc w100">差价（与中标参考价的差价）</td>
          	  	  	<c:forEach items="${supplierList}" var="supplier">
	                  <c:if test="${supplier.packages eq pack.id}">
		                <td class="tr">
		                  	<fmt:formatNumber type="number" value="${supplier.jzjf.supplierPrice}" pattern="0.0000" maxFractionDigits="4"/>
		                </td>
		                <td class="tr">
		                	<c:if test="${supplier.jzjf.supplierPrice >= supplier.jzjf.bidPrice}">
			                	<fmt:formatNumber type="number" value="${supplier.jzjf.supplierPrice - supplier.jzjf.bidPrice}" pattern="0.0000" maxFractionDigits="4"/>
		                	</c:if>
		                	<c:if test="${supplier.jzjf.supplierPrice <= supplier.jzjf.bidPrice}">
			                	<fmt:formatNumber type="number" value="${supplier.jzjf.bidPrice - supplier.jzjf.supplierPrice}" pattern="0.0000" maxFractionDigits="4"/>
		                	</c:if>
		                </td>
	                  </c:if>
	                </c:forEach>
          	  	  </tr>
          	  	  <tr>
          	  	  	<td class="tc" colspan="2">总结</td>
          	  	  	<c:forEach items="${supplierList}" var="supplier">
	                  <c:if test="${supplier.packages eq pack.id}">
		                <td class="tc" colspan="2">符合</td>
		              </c:if>
		            </c:forEach>
          	  	  </tr>
          	  </c:if>
              <tr>
                <td class="tc" colspan="2">排名</td>
                <c:if test="${'PBFF_JZJF' eq pack.bidMethodTypeName}">
                	<c:forEach items="${supplierList}" var="supplier">
	                  <c:if test="${supplier.packages eq pack.id}">
		                <td class="tc" colspan="2">
		                	${supplier.jzjf.rank}
		                </td>
	                  </c:if>
	                </c:forEach>
                </c:if>
                <c:if test="${'OPEN_ZHPFF' eq pack.bidMethodTypeName}">
                <c:forEach items="${supplierList}" var="supplier">
                  <c:if test="${supplier.packages eq pack.id}">
	                <td class="tc" colspan="2" id="rank_${supplier.suppliers.id}_${pack.id}">
	                  <c:forEach items="${rankList}" var="rank">
	                    <c:if test="${rank.packageId eq pack.id and rank.supplierId eq supplier.suppliers.id and (rank.reviewResult == null or rank.reviewResult eq '')}">
	                      <c:if test="${rank.rank!=0}">
	                      ${rank.rank}
	                      </c:if>
	                    </c:if>
	                    <c:if test="${rank.packageId eq pack.id and rank.supplierId eq supplier.suppliers.id and rank.reviewResult != null and rank.reviewResult ne ''}">
		         			<c:set var="num2" value="0" scope="page"></c:set>
		         			<c:forEach var="msg" items="${fn:split(rank.reviewResult, '_')}">
				                <c:set var="num2" value="${num2+1}" scope="page"></c:set>                               
		                        <c:if test="${num2 eq '3' }">
		                        	${msg}
		                        </c:if>
		                    </c:forEach>
	                    </c:if>
	                  </c:forEach>
	                </td>
                  </c:if>
                </c:forEach>
                </c:if>
                <c:if test="${'PBFF_ZDJF' eq pack.bidMethodTypeName}">
                <c:forEach items="${supplierList}" var="supplier">
                  <c:if test="${supplier.packages eq pack.id}">
	                <td class="tc" colspan="2">
	                  ${fn:substringAfter(supplier.reviewResult,"_")}
	                </td>
                  </c:if>
                </c:forEach>
                </c:if>
              </tr>
			</table>
			</div>
          </div>
        </c:forEach>
      </div>
  </body>
</html>
