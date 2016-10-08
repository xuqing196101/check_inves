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
			$("#submitNoResult").hide();
			$("#submitYesResult").hide();
			if("${time}"){
				retake = $("#retake").val();
			}
			document.getElementById("second").innerHTML = "${examPaper.testTime}" + "分钟" + 0 + "秒"; 
			document.getElementById("surplusNo").innerHTML = "${examPaper.testTime}" + "分钟" + 0 + "秒"; 
			document.getElementById("surplusYes").innerHTML = "${examPaper.testTime}" + "分钟" + 0 + "秒"; 
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
       		countSurPlusNo();
       		countSurPlusYes();
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
        var timeYes = "${examPaper.testTime}"*60*1000;
        var timeNo = "${examPaper.testTime}"*60*1000;
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
        
        function countSurPlusNo(){
		     var startMinutes = parseInt(timeNo / (60 * 1000), 10); 
		     var startSec = parseInt((timeNo - startMinutes * 60 * 1000)/1000); 
		     document.getElementById("surplusNo").innerHTML = startMinutes + "分钟" + startSec + "秒"; 
		     timeNo = timeNo - 1000; 
		     setTimeout('countSurPlusNo()',1000); 
        }
        
        function countSurPlusYes(){
		     var startMinutes = parseInt(timeYes / (60 * 1000), 10); 
		     var startSec = parseInt((timeYes - startMinutes * 60 * 1000)/1000); 
		     document.getElementById("surplusYes").innerHTML = startMinutes + "分钟" + startSec + "秒"; 
		     timeYes = timeYes - 1000; 
		     setTimeout('countSurPlusYes()',1000); 
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
						layer.open({
						 	type: 1, //page层
							area: ['430px', '200px'],
							closeBtn: 1,
							shade:0.01, //遮罩透明度
							moveType: 1, //拖拽风格，0是默认，1是传统拖动
							shift: 1, //0-6的动画形式，-1不开启
							offset: ['120px', '550px'],
							shadeClose: false,
							content : $('#submitNoResult')
						});
						$(".layui-layer-shade").remove();
					 }
				 }
			 }
			 if(num==count){
				layer.open({
					type: 1, //page层
					area: ['430px', '200px'],
					closeBtn: 1,
					shade:0.01, //遮罩透明度
					moveType: 1, //拖拽风格，0是默认，1是传统拖动
					shift: 1, //0-6的动画形式，-1不开启
					offset: ['120px', '550px'],
					shadeClose: false,
					content : $('#submitYesResult')
				});
				$(".layui-layer-shade").remove();
			 }
        }
        
        //确定方法
        function sure(){
			if("${time}"){
	        	$("#form").attr("action","<%=path %>/purchaserExam/savePurchaserScore.do?time="+retake);
	        	$("#form").submit();
	        }else{
	        	$("#form").submit();
	        }
        }
        
        //取消方法
        function cancel(){
        	layer.closeAll();
        }
	</script>
	
	
  </head>
  
  <body onload="countTime()">
  	<div id="submitNoResult">
  		<div class="red tc mt20">您还有题目未作答,确定交卷吗?</div>
  		<div class="tc mt10">剩余时间：<span id="surplusNo"></span></div>
  		
  		<div class="col-md-12 tc mt20">
  		  <button class="btn" type="button" onclick="sure()">确定</button>
  		  <button class="btn" type="button" onclick="cancel()">取消</button>
  		</div>
  	</div>
  	<div id="submitYesResult">
  		剩余时间：<span id="surplusYes"></span>
  		<span>确定交卷吗?</span>
  		<button class="btn" type="button" onclick="sure()">确定</button>
  		<button class="btn" type="button" onclick="cancel()">取消</button>
  	</div>
  	
   	<div class="container mt10">
  	<div class="col-md-12 mb10 border1 bggrey">
	  	<div class="fl f18 gary b">${user.relName }考试进行中</div>
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
  	</div>
  </body>
</html>
