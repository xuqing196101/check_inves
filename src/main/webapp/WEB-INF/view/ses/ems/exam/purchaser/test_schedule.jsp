<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/view/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
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
             <table class="table table-bordered table-condensed table-hover table-striped">
		    	<thead>
		    		<tr class="info tc">
			    		<th class="w50">序号</th>
			    		<th>考卷名称</th>
			    		<th>考卷编号</th>
			    		<th>考试开始时间</th>
			    		<th>考试截止时间</th>
			    		<th>答题用时</th>
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
		    				<td>${test.testTime }分钟</td>
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
		<div class="ml15">2.考生必须在答题时间内做题，答题时间过了系统会自动提交</div>
		<div class="ml15">3.过了考试截止时间考生无法登录考试系统考试,成绩计为0分</div>
    </div>
  </body>
</html>
