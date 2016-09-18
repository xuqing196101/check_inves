<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/WEB-INF/view/common.jsp"%>
<!DOCTYPE html>
<html>
  <head>
    <title>开始考试页面</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="">
	<script type="text/javascript">
		$(function(){
          	//默认显示第一页
			var exam = document.getElementsByName("exam");
       		for(var i=1;i<=exam.length;i++){
       			if(i==1){
       				$("#pageNum"+i).show();
       			}else{
       				$("#pageNum"+i).hide();
       			}
       			
       		}
          	
        });
		
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
		
		//考试倒计时
        var timeLeft = ${examRule.testTime}*60*1000;//这里设定的时间是10分钟 
		function countTime(){ 
		     if(timeLeft == 0){//这里就是时间到了之后应该执行的动作了，这里只是弹了一个警告框 
		         alert("时间到！http://www.daimajiayuan.com/"); 
		         return; 
		     } 
		     var startMinutes = parseInt(timeLeft / (60 * 1000), 10); 
		     var startSec = parseInt((timeLeft - startMinutes * 60 * 1000)/1000); 
		     document.getElementById("second").innerHTML = startMinutes + "分钟" + startSec + "秒"; 
		     timeLeft = timeLeft - 1000; 
		     setTimeout('countTime()',1000); 
		 } 
	</script>
  </head>
  
  <body onload="countTime()">
  <div>
  	<div style="float:left;">XXX考试进行中</div>
  	<div style="float:left;" id="time">距离考试还有<span id="second"></span></div>
  </div>
  <form action="<%=path %>/expertExam/saveScore.html" method="post">
  <c:choose>
  	<c:when test="${pageSize==1 }">
  		<div style="clear:both;" id="pageNum1" name="exam">
			    <c:forEach items="${queRandom }" var="que" varStatus="l">
				    <div style="clear:both;">
				    	<div style="float:left;width:150px;height:200px;border:1px solid black;">${l.index+1 }</div>
				    	<div style="float:left;width:700px;height:200px;border:1px solid black;">
				    		<div style="border:1px solid black;">
				    			${que.topic }
				    		</div>
				    		<div style="border:1px solid black;">
				    			<c:if test="${que.examQuestionType.name=='单选题' }">
				    				${fn:split(que.items,';')[0]}<input type="radio" name="que${l.index+1 }" value="A"/>
				    				${fn:split(que.items,';')[1]}<input type="radio" name="que${l.index+1 }" value="B"/>
				    				${fn:split(que.items,';')[2]}<input type="radio" name="que${l.index+1 }" value="C"/>
				    				${fn:split(que.items,';')[3]}<input type="radio" name="que${l.index+1 }" value="D"/>
				    			</c:if>
				    			<c:if test="${que.examQuestionType.name=='多选题' }">
				    				${fn:split(que.items,';')[0]}<input type="checkbox" name="que${l.index+1 }" value="A"/>
				    				${fn:split(que.items,';')[1]}<input type="checkbox" name="que${l.index+1 }" value="B"/>
				    				${fn:split(que.items,';')[2]}<input type="checkbox" name="que${l.index+1 }" value="C"/>
				    				${fn:split(que.items,';')[3]}<input type="checkbox" name="que${l.index+1 }" value="D"/>
				    			</c:if>
				    		</div>
				    		<div style="border:1px solid black;">
				    			答案:${que.answer }
				    		</div>
				    		<div style="border:1px solid black;">
				    			分值:${que.point }
				    		</div>
				    	</div>	
				    </div>
			    </c:forEach>
			    <input type="submit" value="提交"/>
		    </div>
  		
  	</c:when>
  	<c:otherwise>
  		<c:forEach items="${pageNum }" varStatus="p">
  	<c:choose>
  		<c:when test="${p.first}">
		  	<div style="clear:both;" id="pageNum${p.index+1 }" name="exam">
			    <c:forEach items="${queRandom }" var="que" varStatus="l" begin="${p.index*5 }" end="${p.index*5+4 }">
				    <div style="clear:both;">
				    	<div style="float:left;width:150px;height:200px;border:1px solid black;">${l.index+1 }</div>
				    	<div style="float:left;width:700px;height:200px;border:1px solid black;">
				    		<div style="border:1px solid black;">
				    			${que.topic }
				    		</div>
				    		<div style="border:1px solid black;">
				    			<c:if test="${que.examQuestionType.name=='单选题' }">
				    				${fn:split(que.items,';')[0]}<input type="radio" name="que${l.index+1 }" value="A"/>
				    				${fn:split(que.items,';')[1]}<input type="radio" name="que${l.index+1 }" value="B"/>
				    				${fn:split(que.items,';')[2]}<input type="radio" name="que${l.index+1 }" value="C"/>
				    				${fn:split(que.items,';')[3]}<input type="radio" name="que${l.index+1 }" value="D"/>
				    			</c:if>
				    			<c:if test="${que.examQuestionType.name=='多选题' }">
				    				${fn:split(que.items,';')[0]}<input type="checkbox" name="que${l.index+1 }" value="A"/>
				    				${fn:split(que.items,';')[1]}<input type="checkbox" name="que${l.index+1 }" value="B"/>
				    				${fn:split(que.items,';')[2]}<input type="checkbox" name="que${l.index+1 }" value="C"/>
				    				${fn:split(que.items,';')[3]}<input type="checkbox" name="que${l.index+1 }" value="D"/>
				    			</c:if>
				    		</div>
				    		<div style="border:1px solid black;">
				    			答案:${que.answer }
				    		</div>
				    		<div style="border:1px solid black;">
				    			分值:${que.point }
				    		</div>
				    	</div>	
				    </div>
			    </c:forEach>
			    <input type="button" value="下一页" onclick="setTab(${p.index+2})"/>
		    </div>
		    </c:when>
		    <c:when test="${p.last}">
		  	<div style="clear:both;" id="pageNum${p.index+1 }" name="exam">
			    <c:forEach items="${queRandom }" var="que" varStatus="l" begin="${p.index*5 }" end="${p.index*5+4 }">
				    <div style="clear:both;">
				    	<div style="float:left;width:150px;height:200px;border:1px solid black;">${l.index+1 }</div>
				    	<div style="float:left;width:700px;height:200px;border:1px solid black;">
				    		<div style="border:1px solid black;">
				    			${que.topic }
				    		</div>
				    		<div style="border:1px solid black;">
				    			<c:if test="${que.examQuestionType.name=='单选题' }">
				    				${fn:split(que.items,';')[0]}<input type="radio" name="que${l.index+1 }" value="A"/>
				    				${fn:split(que.items,';')[1]}<input type="radio" name="que${l.index+1 }" value="B"/>
				    				${fn:split(que.items,';')[2]}<input type="radio" name="que${l.index+1 }" value="C"/>
				    				${fn:split(que.items,';')[3]}<input type="radio" name="que${l.index+1 }" value="D"/>
				    			</c:if>
				    			<c:if test="${que.examQuestionType.name=='多选题' }">
				    				${fn:split(que.items,';')[0]}<input type="checkbox" name="que${l.index+1 }" value="A"/>
				    				${fn:split(que.items,';')[1]}<input type="checkbox" name="que${l.index+1 }" value="B"/>
				    				${fn:split(que.items,';')[2]}<input type="checkbox" name="que${l.index+1 }" value="C"/>
				    				${fn:split(que.items,';')[3]}<input type="checkbox" name="que${l.index+1 }" value="D"/>
				    			</c:if>
				    		</div>
				    		<div style="border:1px solid black;">
				    			答案:${que.answer }
				    		</div>
				    		<div style="border:1px solid black;">
				    			分值:${que.point }
				    		</div>
				    	</div>	
				    </div>
			    </c:forEach>
			    <input type="button" value="上一页" onclick="setTab(${p.index})"/>
			    <input type="submit" value="提交"/>
		    </div>
		    </c:when>
		    <c:otherwise>
		    	<div style="clear:both;" id="pageNum${p.index+1 }" name="exam">
			    <c:forEach items="${queRandom }" var="que" varStatus="l" begin="${p.index*5 }" end="${p.index*5+4 }">
				    <div style="clear:both;">
				    	<div style="float:left;width:150px;height:200px;border:1px solid black;">${l.index+1 }</div>
				    	<div style="float:left;width:700px;height:200px;border:1px solid black;">
				    		<div style="border:1px solid black;">
				    			${que.topic }
				    		</div>
				    		<div style="border:1px solid black;">
				    			<c:if test="${que.examQuestionType.name=='单选题' }">
				    				${fn:split(que.items,';')[0]}<input type="radio" name="que${l.index+1 }" value="A"/>
				    				${fn:split(que.items,';')[1]}<input type="radio" name="que${l.index+1 }" value="B"/>
				    				${fn:split(que.items,';')[2]}<input type="radio" name="que${l.index+1 }" value="C"/>
				    				${fn:split(que.items,';')[3]}<input type="radio" name="que${l.index+1 }" value="D"/>
				    			</c:if>
				    			<c:if test="${que.examQuestionType.name=='多选题' }">
				    				${fn:split(que.items,';')[0]}<input type="checkbox" name="que${l.index+1 }" value="A"/>
				    				${fn:split(que.items,';')[1]}<input type="checkbox" name="que${l.index+1 }" value="B"/>
				    				${fn:split(que.items,';')[2]}<input type="checkbox" name="que${l.index+1 }" value="C"/>
				    				${fn:split(que.items,';')[3]}<input type="checkbox" name="que${l.index+1 }" value="D"/>
				    			</c:if>
				    		</div>
				    		<div style="border:1px solid black;">
				    			答案:${que.answer }
				    		</div>
				    		<div style="border:1px solid black;">
				    			分值:${que.point }
				    		</div>
				    	</div>	
				    </div>
			    </c:forEach>
			    <input type="button" value="上一页" onclick="setTab(${p.index})"/>
			    <input type="button" value="下一页" onclick="setTab(${p.index+2})"/>
		    </div
		    </c:otherwise>
		    </c:choose>
   </c:forEach>
  	</c:otherwise>
  </c:choose>
  
    <input type="hidden" name="lawAnswer" value="${queAnswer}"/>
    <input type="hidden" name="lawPoint" value="${quePoint}"/>
    
    </form>
    
  </body>
</html>
