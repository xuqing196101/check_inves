<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/WEB-INF/view/front.jsp"%>
<!DOCTYPE html>
<html>
  <head>
    <title>开始考试页面</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript">
		window.onload = function(){
			var exam = document.getElementsByName("exam");
       		for(var i=1;i<=exam.length;i++){
       			if(i==1){
       				$("#pageNum"+i).show();
       			}else{
       				$("#pageNum"+i).hide();
       			}
       		}
		}
		
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
		 
		 //提交答案
		 function save(){
			 var num = 0;
			 var count = ${queCount};
			 for(var i=1;i<=count;i++){
				 for(var j=0;j<document.getElementsByName("que"+i).length;j++){
					 if(document.getElementsByName("que"+i)[j].checked){
						num++;
						break;
					 }else if(j==document.getElementsByName("que"+i).length-1){
						layer.confirm('您还有题目未作答,确认要提交吗?', {title:'提示',offset: ['222px','360px'],shade:0.01}, function(index){
							layer.close(index);
							$("#form").submit();	
						});
					 }
				 }
			 }
			 if(num==count){
				layer.confirm('您确认要提交吗?', {title:'提示',offset: ['222px','360px'],shade:0.01}, function(index){
					layer.close(index);
					$("#form").submit();	
				});
			 }
		 }
	</script>
  </head>
  
  <body>
  <div class="container">
  <div class="col-md-12 mb10 border1 bggrey mt10">
  	<div class="fl f18 gary b">${user.relName }考试进行中</div>
  	</div>
  <form action="${pageContext.request.contextPath }/expertExam/saveScore.html" method="post" id="form">
  <c:choose>
  	<c:when test="${pageSize==1 }">
	  <table class="clear table table-bordered table-condensed" id="pageNum1" name="exam">
	    <tbody>
		    <c:forEach items="${queRandom }" var="que" varStatus="l">
		      <tr>
		        <td class="col-md-1 tc">${l.index+1 }</td>
		        <td class="col-md-11">
		          <div><span>[${que.examQuestionType.name}]</span><span>${que.topic }</span></div>
		          
		          		<c:if test="${que.examQuestionType.name=='单选题' }">
				    		<c:forEach items="${fn:split(que.items,';')}" var="it">
				    		<div class="mt10 clear fl">
				    			<input type="radio" name="que${l.index+1 }" value="${fn:substring(it,0,1)}" class="mt0"/>${it }
				    		</div>
				    		</c:forEach>
				    	</c:if>
				    	<c:if test="${que.examQuestionType.name=='多选题' }">
				    		<c:forEach items="${fn:split(que.items,';')}" var="it">
				    		<div class="mt10 clear fl">
				    			<input type="checkbox" name="que${l.index+1 }" value="${fn:substring(it,0,1)}" class="mt0"/>${it}
				    		</div>
				    		</c:forEach>
				    	</c:if>
		        </td>
		      </tr>
		    </c:forEach>
	    </tbody>
	  </table>
	  	<div class="col-md-12 tc">
	    	<button class="btn" type="button" onclick="save()">提交</button>
	  	</div>
  </c:when>
  <c:otherwise>
  		<c:forEach items="${pageNum }" varStatus="p">
  		<c:choose>
  		<c:when test="${p.first}">
  		<div id="pageNum${p.index+1 }" name="exam">
  			<table class="clear table table-bordered table-condensed">
		  	
			    <c:forEach items="${queRandom }" var="que" varStatus="l" begin="${p.index*5 }" end="${p.index*5+4 }">
				    <tr>
		       			<td class="col-md-1 tc">${l.index+1 }</td>
				    	<td class="col-md-11">
					        <div><span>[${que.examQuestionType.name}]</span><span>${que.topic }</span></div>
					        
					    		<c:if test="${que.examQuestionType.name=='单选题' }">
						    		<c:forEach items="${fn:split(que.items,';')}" var="it">
						    		   <div class="mt10 clear fl">
						    			<input type="radio" name="que${l.index+1 }" value="${fn:substring(it,0,1)}" class="mt0"/>${it }
						    		    </div>
						    		</c:forEach>
						    	</c:if>
						    	<c:if test="${que.examQuestionType.name=='多选题' }">
						    		<c:forEach items="${fn:split(que.items,';')}" var="it">
						    		  <div class="mt10 clear fl">
						    			<input type="checkbox" name="que${l.index+1 }" value="${fn:substring(it,0,1)}" class="mt0"/>${it}
						    		  </div>
						    		</c:forEach>
						    	</c:if>
		        		</td>
		     		 </tr>
			    </c:forEach>
			    
		    </table>
		    <div class="col-md-12 tc">
    			<button class="btn" onclick="setTab(${p.index+2})" type="button">下一页</button>
  			</div>
		   </div>
		    </c:when>
		    
		    <c:when test="${p.last}">
		    <div id="pageNum${p.index+1 }" name="exam">
		    <table class="clear table table-bordered table-condensed">
		  	
			    <c:forEach items="${queRandom }" var="que" varStatus="l" begin="${p.index*5 }" end="${p.index*5+4 }">
				     <tr>
		       			 <td class="col-md-1 tc">${l.index+1 }</td>
				    	<td class="col-md-11">
				          <div><span>[${que.examQuestionType.name}]</span><span>${que.topic }</span></div>
				    			<c:if test="${que.examQuestionType.name=='单选题' }">
						    		<c:forEach items="${fn:split(que.items,';')}" var="it">
						    		  <div class="mt10 clear fl">
						    			<input type="radio" name="que${l.index+1 }" value="${fn:substring(it,0,1)}" class="mt0"/>${it }
						    		  </div>
						    		</c:forEach>
						    	</c:if>
						    	<c:if test="${que.examQuestionType.name=='多选题' }">
						    		<c:forEach items="${fn:split(que.items,';')}" var="it">
						    		   <div class="mt10 clear fl">
						    			<input type="checkbox" name="que${l.index+1 }" value="${fn:substring(it,0,1)}" class="mt0"/>${it}
						    		   </div>
						    		</c:forEach>
						    	</c:if>
		        		</td>
		     		 </tr>
			    </c:forEach>
			   
		    </table>
		     	<div class="col-md-12 tc">
			    	<button class="btn" type="button" onclick="setTab(${p.index})">上一页</button>
    				<button class="btn" type="button" onclick="save()">提交</button>
  				</div>
  				</div>
		    </c:when>
		    
		    <c:otherwise>
		    <div id="pageNum${p.index+1 }" name="exam">
		    <table class="clear table table-bordered table-condensed">
			    <c:forEach items="${queRandom }" var="que" varStatus="l" begin="${p.index*5 }" end="${p.index*5+4 }">
				    <tr>
		       			<td class="col-md-1 tc">${l.index+1 }</td>
				    	<td class="col-md-11">
				          <div><span>[${que.examQuestionType.name}]</span><span>${que.topic }</span></div>
				    			<c:if test="${que.examQuestionType.name=='单选题' }">
						    		<c:forEach items="${fn:split(que.items,';')}" var="it">
						    		  <div class="mt10 clear fl">
						    			<input type="radio" name="que${l.index+1 }" value="${fn:substring(it,0,1)}" class="mt0"/>${it }
						    		  </div>
						    		</c:forEach>
						    	</c:if>
						    	<c:if test="${que.examQuestionType.name=='多选题' }">
						    		<c:forEach items="${fn:split(que.items,';')}" var="it">
						    		  <div class="mt10 clear fl">
						    			<input type="checkbox" name="que${l.index+1 }" value="${fn:substring(it,0,1)}" class="mt0"/>${it}
						    		  </div>
						    		</c:forEach>
						    	</c:if>
		        		</td>
		     		 </tr>
			    </c:forEach>
		    </table>
		    <div class="col-md-12 tc">
		    	<button class="btn" onclick="setTab(${p.index})" type="button">上一页</button>
    			<button class="btn" onclick="setTab(${p.index+2})" type="button">下一页</button>
  			</div>
  			</div>
		    </c:otherwise>
		 </c:choose>
   	</c:forEach>
  </c:otherwise>
  
  </c:choose>
    <input type="hidden" name="queAnswer" value="${queAnswer}"/>
    <input type="hidden" name="queType" value="${queType}"/>
    <input type="hidden" name="queId" value="${queId }"/>
    </form>
    </div>
  </body>
</html>
