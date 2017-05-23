<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
    <title>查看成绩页面</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript">
		$(function(){
			laypage({
			    cont: $("#pageDiv"), //容器。值支持id名、原生dom对象，jquery对象,
			    pages: "${userList.pages}", //总页数
			    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
			    skip: true, //是否开启跳页
			    total: "${userList.total}",
			    startRow: "${userList.startRow}",
			    endRow: "${userList.endRow}",
			    groups: "${userList.pages}">=5?5:"${userList.pages}", //连续显示分页数
			    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
			        var page = location.search.match(/page=(\d+)/);
			        return page ? page[1] : 1;
			    }(), 
			    jump: function(e, first){ //触发分页后的回调
			        if(!first){ //一定要加此判断，否则初始时会无限刷新
			        	var id = "${id}";
			            location.href = "${pageContext.request.contextPath }/expertExam/viewReference.do?id="+id+"&page="+e.curr;
			        }
			    }
			});
		})
		
		//打印预览
		function printReView(){
			var ruleId = $("#ruleId").val();
			window.location.href = "${pageContext.request.contextPath }/expertExam/printReView.do?ruleId="+ruleId;
		}
	</script>

  </head>
  
  <body>
   	 <!--面包屑导航开始-->
	   <div class="margin-top-10 breadcrumbs ">
	      <div class="container">
			   <ul class="breadcrumb margin-left-0">
			   <li><a href="javascript:void(0);">首页</a></li><li><a href="javascript:void(0);">支撑环境</a></li><li><a href="javascript:void(0);">专家考试规则管理</a></li>
			   </ul>
			<div class="clear"></div>
		  </div>
	   </div>
	   
  	<div class="container">
  		<div class="headline-v2">
	   		<h2>已考人员列表</h2>
	  	</div>
  	
  		<div class="col-md-12 pl20 mt10">
		    <input type="button" class="btn" value="打印预览" onclick="printReView()"/>
    	</div>
    
    	<!-- 表格开始 -->
  		<div class="content table_box">
	  		<table class="table table-bordered table-condensed table-hover">
				<thead>
					<tr class="info">
						<th class="w50">序号</th>
						<th class="w100">姓名</th>
						<th>证件号</th>
						<th>专家类型</th>
						<th>状态</th>
						<th>得分</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${userList.list }" varStatus="vs" var="paper">
						<tr class="tc">
							<td>${(vs.index+1)+(userList.pageNum-1)*(userList.pageSize)}</td>
							<td>${paper.relName }</td>
							<td>${paper.idNumber }</td>
							<td>${paper.userDuty }</td>
							<td>${paper.status }</td>
							<td>${paper.score }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div id="pageDiv" align="right"></div>
  	</div>
  	
  		<!-- 返回按钮 -->
  		<div class="col-md-12 col-sm-12 col-xs-12 mt10 tc">
	    	<input class="btn btn-windows back" value="返回" type="button" onclick="location.href='${pageContext.request.contextPath }/expertExam/ruleList.html'">	
	  	</div>
	  	
	  	<input type="hidden" value="${id }" id="ruleId"/>
  </body>
</html>
