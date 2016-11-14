<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/view/front.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>考试安排</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript">
		$(function(){
			laypage({
			    cont: $("#pageDiv"), //容器。值支持id名、原生dom对象，jquery对象,
			    pages: "${testSchedule.pages}", //总页数
			    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
			    skip: true, //是否开启跳页
			    total: "${testSchedule.total}",
			    startRow: "${testSchedule.startRow}",
			    endRow: "${testSchedule.endRow}",
			    groups: "${testSchedule.pages}">=5?5:"${testSchedule.pages}", //连续显示分页数
			    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
			        var page = location.search.match(/page=(\d+)/);
			        return page ? page[1] : 1;
			    }(), 
			    jump: function(e, first){ //触发分页后的回调
			        if(!first){ //一定要加此判断，否则初始时会无限刷新
			            location.href = "${pageContext.request.contextPath }/purchaserExam/testSchedule.do?page="+e.curr;
			        }
			    }
			});	
		})
		
	</script>	

  </head>
  
  <body>
    	<div class="container">
		   <div class="headline-v2">
		   		<h2>考试安排</h2>
		   </div>
   
   
 			<div class="content table_box">
	   		<table class="table table-bordered table-condensed table-hover">
		    	<thead>
		    		<tr class="info">
			    		<th>序号</th>
			    		<th>考卷名称</th>
			    		<th>考卷编号</th>
			    		<th>考试开始时间</th>
			    		<th>考试截止时间</th>
			    		<th>答题用时</th>
			    		<th>是否可以重考</th>
		    		</tr>
		    	</thead>
		    	<tbody>
		    		<c:forEach items="${testSchedule.list }" var="test" varStatus="vs">
		    			<tr class="tc">
		    				<td>${(vs.index+1)+(testSchedule.pageNum-1)*(testSchedule.pageSize)}</td>
		    				<td>${test.name }</td>
		    				<td>${test.code }</td>
		    				<td>${test.startDate }</td>
		    				<td>${test.offDate }</td>
		    				<c:choose>
		    					<c:when test="${test.testTime==null }">
		    						<td></td>
		    					</c:when>
		    					<c:otherwise>
		    						<td>${test.testTime }分钟</td>
		    					</c:otherwise>
		    				</c:choose>
		    				<c:if test="${test.isAllowRetake==1 }">
		    					<td>是</td>
		    				</c:if>
		    				<c:if test="${test.isAllowRetake==0 }">
		    					<td>否</td>
		    				</c:if>
		    			</tr>
		    		</c:forEach>
		    	</tbody>
		    </table>
   		</div>
   		<div id="pageDiv" align="right"></div>
    </div>
    
    <div class="pl20 mt10 red container">
    	<div class="ml15">*注意</div>
		<div class="ml15">1.考卷开始时间到了才能登录考试系统考试</div>
		<div class="ml15">2.设置可以重考的考卷的考卷才有答题用时,不允许重考的考卷的答题用时为当前时间和考试截止时间的时间差</div>
		<div class="ml15">3.如果该考卷允许重考,重考次数不限,重考时间为30分钟</div>
		<div class="ml15">4.过了考试截止时间考生无法登录考试系统考试,在规定时间内考生如果没有登录考试系统考试,计为0分</div>
    </div>
  </body>
</html>
