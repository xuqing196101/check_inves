<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
  <head>
    <title>新增考卷</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script src="<%=basePath%>public/ZHH/js/jquery.min.js" type="text/javascript"></script>
	<script type="text/javascript">
		
	</script>

  </head>
  
  <body>
    <div>
    	新建考卷
    </div>
    <div>
    	编辑试卷
    </div>
    <form action="<%=path %>/purchaserExam/saveToExamPaper.html" method="post">
    <div>
    	试卷名称:<input type="text" name="paperName"/>
    	试卷编号:<input type="text" name="paperNo"/>
    </div>
    <div>
    	题型分布:<input type="text" name="singleNum" id="singleNum"/>条单选题<input type="text" name="singlePoint" id="singlePoint"/>分/条<br/>
    	<input type="text" name="multipleNum" id="multipleNum"/>条多选题<input type="text" name="multiplePoint" id="multiplePoint"/>分/条<br/>
    	<input type="text" name="judgeNum" id="judgeNum"/>条判断题<input type="text" name="judgePoint" id="judgePoint"/>分/条<br/>
    </div>
    <div>
    	总分值:<input type="text" name="totalPoint" id="totalPoint"/>分
    </div>
    <div>
    	考试开始时间:<input type="text" name="startTime" id="startTime"/>
    </div>
    <div>
    	考试用时:<input type="text" name="useTime" id="useTime"/>
    </div>
    <div>
    	首次考试不及格的是否允许30分钟内重考:<input type="checkbox" name="isAllowTrue" id="isAllowTrue">是
    	<input type="checkbox" name="isAllowFalse" id="isAllowFalse"/>否
    </div>
    <br/>
    <br/>
    <br/>
    <input type="submit" value="提交"/>
    </form>
  </body>
</html>
