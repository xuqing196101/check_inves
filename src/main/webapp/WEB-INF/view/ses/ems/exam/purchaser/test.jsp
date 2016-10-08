<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/WEB-INF/view/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>采购人做题页面</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="${ pageContext.request.contextPath }/public/layer/layer.js"></script>
	<script type="text/javascript" src="${ pageContext.request.contextPath }/public/layer/extend/layer.ext.js"></script>
	<link href="${ pageContext.request.contextPath }/public/layer/skin/layer.css" rel="stylesheet" type="text/css" />
	<link href="${ pageContext.request.contextPath }/public/layer/skin/layer.ext.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript">
		var retake;
		
		$(function(){
			if("${time}"){
				retake = $("#retake").val();
			}
			document.getElementById("second").innerHTML = "${examPaper.testTime}" + "分钟" + 0 + "秒"; 
			var exam = document.getElementsByName("exam");
       		for(var i=1;i<=exam.length;i++){
       			if(i==1){
       				$("#pageNum"+i).show();
       			}else{
       				$("#pageNum"+i).hide();
       			}
       		}
       		if("${time}"){
       			countReTakeTime();
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
		
		//考试倒计时
        var timeLeft = "${examPaper.testTime}"*60*1000-1000;//这里设定考试用时
		function countTime(){ 
		     if(timeLeft == 0){//时间到了,系统自动提交
		    	if("${time}"){
		        	$("#form").attr("action","<%=path %>/purchaserExam/savePurchaserScore.do?time="+retake);
		        	$("#form").submit();
		        }else{
		        	$("#form").submit();
		        }
		        return; 
		     }
		     var startMinutes = parseInt(timeLeft / (60 * 1000), 10); 
		     var startSec = parseInt((timeLeft - startMinutes * 60 * 1000)/1000); 
		     document.getElementById("second").innerHTML = startMinutes + "分钟" + startSec + "秒"; 
		     timeLeft = timeLeft - 1000; 
		     setTimeout('countTime()',1000); 
		} 
        
        //计时重考的时间
        function countReTakeTime(){
    		retake = parseInt(retake,10) - 1000; 
    		setTimeout('countReTakeTime()',1000);
        }
        
        //提交方法
        function git(){
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
							if("${time}"){
				        		$("#form").attr("action","<%=path %>/purchaserExam/savePurchaserScore.do?time="+retake);
				        		$("#form").submit();
				        	}else{
				        		$("#form").submit();
				        	}	
						});
					 }
				 }
			 }
			 if(num==count){
				layer.confirm('您确认要提交吗?', {title:'提示',offset: ['222px','360px'],shade:0.01}, function(index){
					layer.close(index);
					if("${time}"){
		        		$("#form").attr("action","<%=path %>/purchaserExam/savePurchaserScore.do?time="+retake);
		        		$("#form").submit();
		        	}else{
		        		$("#form").submit();
		        	}	
				});
			 }
        }
	</script>
	
	
  </head>
  
  <body onload="countTime()">
   	<div class="container">
  	<div class="col-md-12 mb10 border1 bggrey">
	  	<div class="fl f18 gary b">XXX考试进行中</div>
	  	<div class="fr red mt5" id="time">距离考试还有<span id="second"></span></div>
  	</div>
  <form action="<%=path %>/purchaserExam/savePurchaserScore.html" method="post" id="form">
  <c:choose>
  	<c:when test="${pageSize==1 }">
	  <table class="clear table table-bordered table-condensed" id="pageNum1" name="exam">
	  	<tbody>
		    <c:forEach items="${purchaserQue }" var="pur" varStatus="l">
		      <tr>
		        <td class="col-md-1 tc">${l.index+1 }</td>
		        <td class="col-md-11">
			        <div>${pur.topic }</div>
			        <div class="mt10">
		          	<c:if test="${pur.examQuestionType.name=='单选题' }">
				    	<div class="fl mr10"><input type="radio" name="que${l.index+1 }" value="A" class="mt0"/>${fn:split(pur.items,';')[0]}</div>
				    	<div class="fl mr10"><input type="radio" name="que${l.index+1 }" value="B" class="mt0"/>${fn:split(pur.items,';')[1]}</div>
				    	<div class="fl mr10"><input type="radio" name="que${l.index+1 }" value="C" class="mt0"/>${fn:split(pur.items,';')[2]}</div>
				    	<div class="fl"><input type="radio" name="que${l.index+1 }" value="D" class="mt0"/>${fn:split(pur.items,';')[3]}</div>
				  	</c:if>
				 	<c:if test="${pur.examQuestionType.name=='多选题' }">
				    	<div class="fl mr10"><input type="checkbox" name="que${l.index+1 }" value="A" class="mt0"/>${fn:split(pur.items,';')[0]}</div>
				    	<div class="fl mr10"><input type="checkbox" name="que${l.index+1 }" value="B" class="mt0"/>${fn:split(pur.items,';')[1]}</div>
				    	<div class="fl mr10"><input type="checkbox" name="que${l.index+1 }" value="C" class="mt0"/>${fn:split(pur.items,';')[2]}</div>
				    	<div class="fl"><input type="checkbox" name="que${l.index+1 }" value="D" class="mt0"/>${fn:split(pur.items,';')[3]}</div>
				   	</c:if>
				   	<c:if test="${pur.examQuestionType.name=='判断题' }">
		    			<div class="fl mr10"><input type="radio" name="que${l.index+1 }" value="对" class="mt0"/>对</div>
		    			<div class="fl"><input type="radio" name="que${l.index+1 }" value="错" class="mt0"/>错</div>
		    		</c:if>
		          </div>
		        </td>
		      </tr>
		    </c:forEach>
	    </tbody>
	  </table>
	  	<div class="col-md-12 tc">
	    	<button class="btn" type="button" onclick="git()">提交</button>
	  	</div>
  </c:when>
	  <c:otherwise>
  		<c:forEach items="${pageNum }" varStatus="p">
  		<c:choose>
  		<c:when test="${p.first}">
  		<div id="pageNum${p.index+1 }" name="exam">
  			<table class="clear table table-bordered table-condensed">
		  	
			    <c:forEach items="${purchaserQue }" var="pur" varStatus="l" begin="${p.index*5 }" end="${p.index*5+4 }">
				    <tr>
		       			 <td class="col-md-1 tc">${l.index+1 }</td>
				    	<td class="col-md-11">
				          <div>${pur.topic }</div>
				          <div class="mt10">
				    			<c:if test="${pur.examQuestionType.name=='单选题' }">
				    				<div class="fl mr10"><input type="radio" name="que${l.index+1 }" value="A" class="mt0"/>${fn:split(pur.items,';')[0]}</div>
				    				<div class="fl mr10"><input type="radio" name="que${l.index+1 }" value="B" class="mt0"/>${fn:split(pur.items,';')[1]}</div>
				    				<div class="fl mr10"><input type="radio" name="que${l.index+1 }" value="C" class="mt0"/>${fn:split(pur.items,';')[2]}</div>
				    				<div class="fl"><input type="radio" name="que${l.index+1 }" value="D" class="mt0"/>${fn:split(pur.items,';')[3]}</div>
				    			</c:if>
				    			<c:if test="${pur.examQuestionType.name=='多选题' }">
				    				<div class="fl mr10"><input type="checkbox" name="que${l.index+1 }" value="A" class="mt0"/>${fn:split(pur.items,';')[0]}</div>
				    				<div class="fl mr10"><input type="checkbox" name="que${l.index+1 }" value="B" class="mt0"/>${fn:split(pur.items,';')[1]}</div>
				    				<div class="fl mr10"><input type="checkbox" name="que${l.index+1 }" value="C" class="mt0"/>${fn:split(pur.items,';')[2]}</div>
				    				<div class="fl"><input type="checkbox" name="que${l.index+1 }" value="D" class="mt0"/>${fn:split(pur.items,';')[3]}</div>
				    			</c:if>
				    			<c:if test="${pur.examQuestionType.name=='判断题' }">
					    			<div class="fl mr10"><input type="radio" name="que${l.index+1 }" value="对" class="mt0"/>对</div>
					    			<div class="fl"><input type="radio" name="que${l.index+1 }" value="错" class="mt0"/>错</div>
		    					</c:if>
				    		 </div>
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
		  	
			    <c:forEach items="${purchaserQue }" var="pur" varStatus="l" begin="${p.index*5 }" end="${p.index*5+4 }">
				     <tr>
		       			 <td class="col-md-1 tc">${l.index+1 }</td>
				    	<td class="col-md-11">
				          <div>${pur.topic }</div>
				          <div class="mt10">
				    			<c:if test="${pur.examQuestionType.name=='单选题' }">
				    				<div class="fl mr10"><input type="radio" name="que${l.index+1 }" value="A" class="mt0"/>${fn:split(pur.items,';')[0]}</div>
				    				<div class="fl mr10"><input type="radio" name="que${l.index+1 }" value="B" class="mt0"/>${fn:split(pur.items,';')[1]}</div>
				    				<div class="fl mr10"><input type="radio" name="que${l.index+1 }" value="C" class="mt0"/>${fn:split(pur.items,';')[2]}</div>
				    				<div class="fl"><input type="radio" name="que${l.index+1 }" value="D" class="mt0"/>${fn:split(pur.items,';')[3]}</div>
				    			</c:if>
				    			<c:if test="${pur.examQuestionType.name=='多选题' }">
				    				<div class="fl mr10"><input type="checkbox" name="que${l.index+1 }" value="A" class="mt0"/>${fn:split(pur.items,';')[0]}</div>
				    				<div class="fl mr10"><input type="checkbox" name="que${l.index+1 }" value="B" class="mt0"/>${fn:split(pur.items,';')[1]}</div>
				    				<div class="fl mr10"><input type="checkbox" name="que${l.index+1 }" value="C" class="mt0"/>${fn:split(pur.items,';')[2]}</div>
				    				<div class="fl"><input type="checkbox" name="que${l.index+1 }" value="D" class="mt0"/>${fn:split(pur.items,';')[3]}</div>
				    			</c:if>
				    			<c:if test="${pur.examQuestionType.name=='判断题' }">
					    			<div class="fl mr10"><input type="radio" name="que${l.index+1 }" value="对" class="mt0"/>对</div>
					    			<div class="fl"><input type="radio" name="que${l.index+1 }" value="错" class="mt0"/>错</div>
		    					</c:if>
				    		</div>
		        		</td>
		     		 </tr>
			    </c:forEach>
			   
		    </table>
		     	<div class="col-md-12 tc">
			    	<button class="btn" type="button" onclick="setTab(${p.index})">上一页</button>
    				<button class="btn" type="button" onclick="git()">提交</button>
  				</div>
  				</div>
		    </c:when>
		    
		    <c:otherwise>
		    <div id="pageNum${p.index+1 }" name="exam">
		    <table class="clear table table-bordered table-condensed">
		    	
			    <c:forEach items="${purchaserQue }" var="pur" varStatus="l" begin="${p.index*5 }" end="${p.index*5+4 }">
				    <tr>
		       			 <td class="col-md-1 tc">${l.index+1 }</td>
				    	<td class="col-md-11">
				          <div>${pur.topic }</div>
				          <div class="mt10">
				    			<c:if test="${pur.examQuestionType.name=='单选题' }">
				    				<div class="fl mr10"><input type="radio" name="que${l.index+1 }" value="A" class="mt0"/>${fn:split(pur.items,';')[0]}</div>
				    				<div class="fl mr10"><input type="radio" name="que${l.index+1 }" value="B" class="mt0"/>${fn:split(pur.items,';')[1]}</div>
				    				<div class="fl mr10"><input type="radio" name="que${l.index+1 }" value="C" class="mt0"/>${fn:split(pur.items,';')[2]}</div>
				    				<div class="fl"><input type="radio" name="que${l.index+1 }" value="D" class="mt0"/>${fn:split(pur.items,';')[3]}</div>
				    			</c:if>
				    			<c:if test="${pur.examQuestionType.name=='多选题' }">
				    				<div class="fl mr10"><input type="checkbox" name="que${l.index+1 }" value="A" class="mt0"/>${fn:split(pur.items,';')[0]}</div>
				    				<div class="fl mr10"><input type="checkbox" name="que${l.index+1 }" value="B" class="mt0"/>${fn:split(pur.items,';')[1]}</div>
				    				<div class="fl mr10"><input type="checkbox" name="que${l.index+1 }" value="C" class="mt0"/>${fn:split(pur.items,';')[2]}</div>
				    				<div class="fl"><input type="checkbox" name="que${l.index+1 }" value="D" class="mt0"/>${fn:split(pur.items,';')[3]}</div>
				    			</c:if>
				    			<c:if test="${pur.examQuestionType.name=='判断题' }">
					    			<div class="fl mr10"><input type="radio" name="que${l.index+1 }" value="对" class="mt0"/>对</div>
					    			<div class="fl"><input type="radio" name="que${l.index+1 }" value="错" class="mt0"/>错</div>
		    					</c:if>
				    		</div>
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
	
	
	<c:if test="${time!=null&&time!='' }">
		<input type="hidden" value="${time }" name="time" id="retake"/>
	</c:if>
  			
  	<input type="hidden" value="${purQueAnswer }" name="purQueAnswer"/>
  	<input type="hidden" value="${paperId }" name="paperId"/>
  	<input type="hidden" value="${purQueType }" name="purQueType"/>
  	<input type="hidden" value="${purQueId }" name="purQueId"/>
  	</form>
  </body>
</html>
