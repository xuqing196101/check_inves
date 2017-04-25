<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
  <head>
  <%@ include file="../../../../../common.jsp"%>
    
    <title>燃料动力费明细</title>

<script type="text/javascript">

function onStep(){
	var proId = $("#proId").val();
	window.location.href="${pageContext.request.contextPath}/specialCost/userGetAllCheck.do?productId="+proId;
}
function moneys(obj,indx){
	 var money=0;
	 var trs=$(obj).parent().parent().prevAll();
	 if($(obj).val()!=""){
		 money =parseFloat($(obj).val());
	}
	 sumMoney(trs,indx); 
	 
}
function sumMoney(trs,indx){
	var objSumValue; 
   var objTrs;
   var number;
   var flgOne=true;
   var flgTwo=true;
   var sumPrice=0;
   var tr0=$("#tr0").nextAll();
	for(var i=0;i<trs.length;i++){
		if(flgOne==true){
   		if($($(trs[i]).children()[0]).text().split(".").length==2){
	    		objSumValue=$($(trs[i]).children()[indx]).children(":first");
	    		objTrs=$(trs[i]).nextAll();
	    		number=$($(trs[i]).children()[0]).text();
	    		var money=0;
	    		for(var j=0;j<objTrs.length;j++){
	    			if($($(objTrs[j]).children()[0]).text().substring(0,$($(objTrs[j]).children()[0]).text().lastIndexOf("."))==number){
	    				if($($(objTrs[j]).children()[indx]).children(":first").val()!=""){
	    					money+=parseFloat($($(objTrs[j]).children()[indx]).children(":first").val());
	    				}else{
	    					money+=0;
	    				}
	    			}
	        	}
	    		objSumValue.val(money.toFixed(2));
	    		flgOne=false;
   		}
	   }
		if(flgTwo==true){
   		if($($(trs[i]).children()[0]).text().split(".").length==1){
   			objSumValue=$($(trs[i]).children()[indx]).children(":first");
	    		objTrs=$(trs[i]).nextAll();
	    		number=$($(trs[i]).children()[0]).text();
	    		var money=0;
	    		for(var j=0;j<objTrs.length;j++){
	    			if($($(objTrs[j]).children()[0]).text().substring(0,$($(objTrs[j]).children()[0]).text().lastIndexOf("."))==number){
	    				if($($(objTrs[j]).children()[indx]).children(":first").val()!=""){
	    					money+=parseFloat($($(objTrs[j]).children()[indx]).children(":first").val());
	    				}else{
	    					money+=0;
	    				}
	    			}
	        	}
	    		objSumValue.val(money.toFixed(2));
	    		flgTwo=false;
   		}
	    }
	}
	for(var i=1;i<tr0.length-1;i++){
		if($($(tr0[i]).children()[0]).text().split(".").length==1&&$($(tr0[i]).children()[indx]).children(":first").val()!=""){
			sumPrice+=parseFloat($($(tr0[i]).children()[indx]).children(":first").val());
		}else{
			sumPrice+=0;
		}
	}
	if(indx==15){
		$($(tr0[tr0.length-1]).children()[8]).children(":first").val(sumPrice.toFixed(2));
	}
	
}
</script>

  </head>
  
  <body>
  
  	<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0)">首页</a></li><li><a href="javascript:void(0)">审价人员复审</a></li><li><a href="javascript:void(0)">燃料动力费明细</a></li></ul>
		<div class="clear"></div>
	  </div>
   </div>
  
  <div class="container">
	 	<div class="headline-v2">
	  		 <h2>燃料动力费明细</h2>
	 	</div>
		
   </div>
	
	<input type="hidden" id="proId" name="contractProduct.id" class="w230 mb0" value="${proId }" readonly>
	
	<div class="container margin-top-5">
	 	<form action="${pageContext.request.contextPath}/burningPower/userUpdateCheck.html?productId=${proId }" method="post" enctype="multipart/form-data">
	 	<div class="container padding-left-25 padding-right-25">
			<table class="table table-bordered table-condensed">
					<tr  id="tr0">
						<th rowspan="2" class="info">序号</th>
						<th rowspan="2" class="info">一级项目</th>
						<th rowspan="2" class="info">计量单位</th>
						<th colspan="3" class="info">报价前2年</th>
						<th colspan="3" class="info">报价前1年</th>
						<th colspan="3" class="info">报价当年</th>
						<th rowspan="2" class="info">审核核准金额</th>
						<!-- <th rowspan="2" class="info">复审核准金额</th> -->
						<th rowspan="2" class="info">备   注</th>
					</tr>
					<tr>
						<th class="info">数量</th>
						<th class="info">平均单价</th>
						<th class="info">金额</th>
						<th class="info">数量</th>
						<th class="info">平均单价</th>
						<th class="info">金额</th>
						<th class="info">数量</th>
						<th class="info">平均单价</th>
						<th class="info">金额</th>
					</tr>
				<c:forEach items="${list}" var="bp" varStatus="vs">
					<tr>
						<td class="tc"><input type="hidden" name="listBurn[${vs.index }].id" value="${bp.id }" />${bp.serialNumber}</td>
						<td class="tc">${bp.firsetProduct }</td>
						<td class="tc">${bp.unit }</td>
						<td class="tc">${bp.tyaAcount }</td>
						<td class="tc">${bp.tyaAvgPrice }</td>
						<td class="tc">${bp.tyaMoney }</td>
						<td class="tc">${bp.oyaAcount }</td>
						<td class="tc">${bp.oyaAvgPrice }</td>
						<td class="tc">${bp.oyaMoney }</td>
						<td class="tc">${bp.newAcount }</td>
						<td class="tc">${bp.newAvgPrice }</td>
						<td class="tc">${bp.newMoney }</td>
						<td class="tc">${bp.approvedMoney }</td>
						<%-- <c:if test="${fn:length(fn:split(bp.serialNumber,'.'))>2}">
						<td class="tc"><input type="text" value='${bp.checkMoney }' class='m0 p0  border0 w80 tr' name="listBurn[${vs.index }].checkMoney" onblur='moneys(this,"13");'></td>
						</c:if>
						<c:if test="${fn:length(fn:split(bp.serialNumber,'.'))<=2}">
						<td class="tc"><input type="text" value='${bp.checkMoney }' class='m0 p0  border0 w80 tr' name="listBurn[${vs.index }].checkMoney" readonly="readonly"></td>
						</c:if> --%>
						<td class="tc">${bp.remark }</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		
		<div  class="col-md-12">
		   <div class="mt40 tc mb50">
		    <button class="btn" type="button" onclick="onStep()">上一步</button>
		    <button class="btn" type="submit">下一步</button>
		   </div>
	 	 </div>
	 	 </form> 
  </div>
  
  </body>
</html>
