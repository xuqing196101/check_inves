<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>包关联初审项</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link href="<%=basePath%>public/ZHH/css/bootstrap.min.css" media="screen" rel="stylesheet">
	<link href="<%=basePath%>public/ZHH/css/common.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/style.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/line-icons.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/app.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/application.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/header-v4.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/header-v5.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/footer-v2.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/img-hover.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/page_job.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/shop.style.css" media="screen" rel="stylesheet">
	<link href="<%=basePath%>public/ZHH/css/brand-buttons.css" media="screen" rel="stylesheet" type="text/css">
    <script src="<%=basePath%>public/ZHH/js/jquery.min.js"></script>
    <script src="<%=basePath%>public/ZHH/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>public/layer/layer.js"></script>
<script src="<%=basePath%>public/laypage-v1.3/laypage/laypage.js"></script>
<script type="text/javascript">
    $(function(){
    	var packageId=	$("input[name='packageId']").val();
    	var flag="${flag}";
    	if(flag=="success"){
    		layer.msg("关联成功",{offset: ['222px', '390px']});
    	}
    })
	 /** 全选全不选 */
    function selectAll(){
         var checklist = document.getElementsByName ("chkItem");
         var checkAll = document.getElementById("checkAll");
         if(checkAll.checked){
               for(var i=0;i<checklist.length;i++)
               {
                  checklist[i].checked = true;
               }
             }else{
              for(var j=0;j<checklist.length;j++)
              {
                 checklist[j].checked = false;
              }
            }
        }
        function addMarkTerm(){
	    	var id=[]; 
			$('input[name="chkItem"]:checked').each(function(){ 
				id.push($(this).val());
			}); 
			if(id.length==1){
				window.location.href="${pageContext.request.contextPath}/intelligentScore/list.do?id="+id[0]+"&projectId=${projectId}";
			}else if(id.length>1){
				layer.alert("只能选择一个",{offset: ['222px', '390px'], shade:0.01});
			}else{
				layer.alert("至少选择一个",{offset: ['222px', '390px'], shade:0.01});
			}
    	}
	 
</script>
  </head>
  
  <body>
	<div class="col-md-12 p0">
		<ul class="flow_step">
			<li><a
				href="<%=basePath%>firstAudit/toAdd_cn.html?projectId=${projectId}">01、符合性</a>
				<i></i>
			</li>

			<li><a
				href="<%=basePath%>firstAudit/toPackageFirstAudit_cn.html?projectId=${projectId}">02、符合性关联</a>
				<i></i>
			</li>
			<li class="active"><a
				href="<%=basePath%>intelligentScore/packageList_cn.html?projectId=${projectId}">03、评标细则</a>
				<i></i>
			</li>
			<li><a href="<%=basePath%>pub_tran/bidFile.html?id=${projectId}" >04、竞谈文件</a>
			</li>
		</ul>
	</div>
	<div class="tab-content clear step_cont">
		<!--第一个  -->
		<!--第二个 -->
		<div class=class= "col-md-12 tab-pane active"  id="tab-1">
			<h1 class="f16 count_flow">
				<i>01</i>制定评分细则
			</h1>
			<div class="fr pr15 mt10">
				<button class="btn btn-windows edit" onclick="addMarkTerm();">制定评分办法</button>
		    </div>
			<div class="container clear margin-top-30" id="package">
				<table class="table table-bordered table-condensed mt5">
					<thead>
						<tr>
							<th class="info w30"><input type="checkbox" id="checkAll"
								onclick="selectAll()" alt=""></th>
							<th>序号</th>
							<th>包名</th>
						</tr>
					</thead>
					<c:forEach items="${packagesList }" var="p" varStatus="vs">
						<thead>
							<tr>
								<td class="tc w30"><input type="checkbox" value="${p.id }" name="chkItem">
								</td>
								<td align="center">${vs.index+1 }</td>
								<td align="center">${p.name }</td>
							</tr>
						</thead>
					</c:forEach>
				</table>
			</div>
			<div class="container clear margin-top-30" id="package"></div>
		</div>
	</div>
</body>
</html>
