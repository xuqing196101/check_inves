<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
  <head>
    <title>采购人做题页面</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script src="<%=basePath%>public/ZHH/js/jquery.min.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(function(){
			var exam = document.getElementsByName("exam");
       		for(var i=1;i<=exam.length;i++){
       			if(i==1){
       				$("#pageNum"+i).show();
       			}else{
       				$("#pageNum"+i).hide();
       			}
       			
       		}
		})
		
		
		//答题时上一页下一页切换
        function setTab(index){
          	var exam = document.getElementsByName("exam");
          	for(var i=1;i<=exam.length;i++){
          		if(index==i){
          			$("#pageNum"+index).show();
          		}else{
          			$("#pageNum"+i).hide();
          		}
          	}
        }
		
		
	</script>
	
	
  </head>
  
  <body>
  <form action="<%=path %>/purchaserExam/savePurchaserScore.html" method="post">
  	<c:forEach items="${pageNum }" varStatus="l">
  		<c:choose>
  			<c:when test="${l.first}">
  				<div id="pageNum${l.index+1 }" name="exam">
  				<c:forEach items="${purchaserQue }" varStatus="p" var="pur" begin="${l.index*2 }" end="${l.index*2+1 }">
  					<div style="float:left;width:30px;height:180px;border:1px solid black;">${p.index+1 }</div>
  					<div style="float:left;width:240px;height:180px;border:1px solid black;">
		    		<div style="border:1px solid black;">
		    			${pur.topic }
		    		</div>
		    		<div style="border:1px solid black;">
		    			<c:if test="${pur.examQuestionType.name=='单选题' }">
		    				${fn:split(pur.items,';')[0]}<input type="radio" name="que${p.index+1 }" value="A"/>
		    				${fn:split(pur.items,';')[1]}<input type="radio" name="que${p.index+1 }" value="B"/>
		    				${fn:split(pur.items,';')[2]}<input type="radio" name="que${p.index+1 }" value="C"/>
		    				${fn:split(pur.items,';')[3]}<input type="radio" name="que${p.index+1 }" value="D"/>
		    			</c:if>
		    			<c:if test="${pur.examQuestionType.name=='多选题' }">
		    				${fn:split(pur.items,';')[0]}<input type="checkbox" name="que${p.index+1 }" value="A"/>
		    				${fn:split(pur.items,';')[1]}<input type="checkbox" name="que${p.index+1 }" value="B"/>
		    				${fn:split(pur.items,';')[2]}<input type="checkbox" name="que${p.index+1 }" value="C"/>
		    				${fn:split(pur.items,';')[3]}<input type="checkbox" name="que${p.index+1 }" value="D"/>
		    			</c:if>
		    			<c:if test="${pur.examQuestionType.name=='判断题' }">
		    				对:<input type="radio" name="que${p.index+1 }" value="对"/>
		    				错:<input type="radio" name="que${p.index+1 }" value="错"/>
		    			</c:if>
		    		</div>
		    		<div style="border:1px solid black;">
		    			答案:${pur.answer }
		    		</div>
		    		<div style="border:1px solid black;">
		    			分值:${pur.point }
		    		</div>
		    	</div>	
  				</c:forEach>
  				<input type="button" value="下一页" onclick="setTab(${l.index+2 })"/>
  			</div>
  			</c:when>
  			<c:when test="${l.last}">
  				<div id="pageNum${l.index+1 }" name="exam">
  				<c:forEach items="${purchaserQue }" varStatus="p" var="pur" begin="${l.index*2 }" end="${l.index*2+1 }">
  					<div style="float:left;width:30px;height:180px;border:1px solid black;">${p.index+1 }</div>
  					<div style="float:left;width:240px;height:180px;border:1px solid black;">
		    		<div style="border:1px solid black;">
		    			${pur.topic }
		    		</div>
		    		<div style="border:1px solid black;">
		    			<c:if test="${pur.examQuestionType.name=='单选题' }">
		    				${fn:split(pur.items,';')[0]}<input type="radio" name="que${p.index+1 }" value="A"/>
		    				${fn:split(pur.items,';')[1]}<input type="radio" name="que${p.index+1 }" value="B"/>
		    				${fn:split(pur.items,';')[2]}<input type="radio" name="que${p.index+1 }" value="C"/>
		    				${fn:split(pur.items,';')[3]}<input type="radio" name="que${p.index+1 }" value="D"/>
		    			</c:if>
		    			<c:if test="${pur.examQuestionType.name=='多选题' }">
		    				${fn:split(pur.items,';')[0]}<input type="checkbox" name="que${p.index+1 }" value="A"/>
		    				${fn:split(pur.items,';')[1]}<input type="checkbox" name="que${p.index+1 }" value="B"/>
		    				${fn:split(pur.items,';')[2]}<input type="checkbox" name="que${p.index+1 }" value="C"/>
		    				${fn:split(pur.items,';')[3]}<input type="checkbox" name="que${p.index+1 }" value="D"/>
		    			</c:if>
		    			<c:if test="${pur.examQuestionType.name=='判断题' }">
		    				对:<input type="radio" name="que${p.index+1 }" value="对"/>
		    				错:<input type="radio" name="que${p.index+1 }" value="错"/>
		    			</c:if>
		    		</div>
		    		<div style="border:1px solid black;">
		    			答案:${pur.answer }
		    		</div>
		    		<div style="border:1px solid black;">
		    			分值:${pur.point }
		    		</div>
		    	</div>	
  				</c:forEach>
  				<input type="button" value="上一页" onclick="setTab(${l.index })"/>
				<input type="submit" value="保存"/>
  			</div>
  			</c:when>
  			<c:otherwise>
  			<div id="pageNum${l.index+1 }" name="exam">
  				<c:forEach items="${purchaserQue }" varStatus="p" var="pur" begin="${l.index*2 }" end="${l.index*2+1 }">
  					<div style="float:left;width:30px;height:180px;border:1px solid black;">${p.index+1 }</div>
  					<div style="float:left;width:240px;height:180px;border:1px solid black;">
		    		<div style="border:1px solid black;">
		    			${pur.topic }
		    		</div>
		    		<div style="border:1px solid black;">
		    			<c:if test="${pur.examQuestionType.name=='单选题' }">
		    				${fn:split(pur.items,';')[0]}<input type="radio" name="que${p.index+1 }" value="A"/>
		    				${fn:split(pur.items,';')[1]}<input type="radio" name="que${p.index+1 }" value="B"/>
		    				${fn:split(pur.items,';')[2]}<input type="radio" name="que${p.index+1 }" value="C"/>
		    				${fn:split(pur.items,';')[3]}<input type="radio" name="que${p.index+1 }" value="D"/>
		    			</c:if>
		    			<c:if test="${pur.examQuestionType.name=='多选题' }">
		    				${fn:split(pur.items,';')[0]}<input type="checkbox" name="que${p.index+1 }" value="A"/>
		    				${fn:split(pur.items,';')[1]}<input type="checkbox" name="que${p.index+1 }" value="B"/>
		    				${fn:split(pur.items,';')[2]}<input type="checkbox" name="que${p.index+1 }" value="C"/>
		    				${fn:split(pur.items,';')[3]}<input type="checkbox" name="que${p.index+1 }" value="D"/>
		    			</c:if>
		    			<c:if test="${pur.examQuestionType.name=='判断题' }">
		    				对:<input type="radio" name="que${p.index+1 }" value="对"/>
		    				错:<input type="radio" name="que${p.index+1 }" value="错"/>
		    			</c:if>
		    		</div>
		    		<div style="border:1px solid black;">
		    			答案:${pur.answer }
		    		</div>
		    		<div style="border:1px solid black;">
		    			分值:${pur.point }
		    		</div>
		    	</div>	
  				</c:forEach>
  				<input type="button" value="上一页" onclick="setTab(${l.index })"/>
				<input type="button" value="下一页" onclick="setTab(${l.index+2 })"/>
  			</div>
  			</c:otherwise>
  		</c:choose>
  	</c:forEach>
  	<input type="hidden" value="${purQueAnswer }" name="purQueAnswer"/>
  	<input type="hidden" value="${paperId }" name="paperId"/>
  	<input type="hidden" value="${purQueType }" name="purQueType"/>
  	<input type="hidden" value="${purQueId }" name="purQueId"/>
  	<input type="hidden" value="${count }" name="count"/>
  	</form>
  </body>
</html>
