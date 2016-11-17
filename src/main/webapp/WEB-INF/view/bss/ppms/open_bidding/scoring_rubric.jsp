<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="${pageContext.request.contextPath}/">
    
    <title>包关联初审项</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
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
	 	function addBidMethod(projectId){
	 		var id=[]; 
			$('input[name="chkItem"]:checked').each(function(){ 
				id.push($(this).val());
			}); 
			if(id.length==1){
				layer.open({
					  type: 2,
					  title: '制定评标办法',
					  shadeClose: true,
					  shade: 0.4,
					  area: ['980px', '30%'],
					  content: "${pageContext.request.contextPath}/intelligentScore/bidMethod?projectId="+projectId+"&packageId="+id[0] //iframe的url
				}); 
			}else if(id.length>1){
				layer.alert("只能选择一个",{offset: ['222px', '390px'], shade:0.01});
			}else{
				layer.alert("至少选择一个",{offset: ['222px', '390px'], shade:0.01});
			}
		}
		function show(packageId){
			window.location.href = "${pageContext.request.contextPath}/intelligentScore/scoreModelList.html?packageId="+packageId+"&proid=${projectId}";
		}
</script>
  </head>
  
  <body>
	<div class="col-md-12 p0">
		<ul class="flow_step">
			<li><a
				href="${pageContext.request.contextPath}/firstAudit/toAdd.html?projectId=${projectId}&flowDefineId=${flowDefineId}">01、符合性</a>
				<i></i>
			</li>

			<li><a
				href="${pageContext.request.contextPath}/firstAudit/toPackageFirstAudit.html?projectId=${projectId}&flowDefineId=${flowDefineId}">02、符合性关联</a>
				<i></i>
			</li>
			<li class="active"><a
				href="${pageContext.request.contextPath}/intelligentScore/packageList.html?projectId=${projectId}&flowDefineId=${flowDefineId}">03、评标细则</a>
				<i></i>
			</li>
			<li><a  href="${pageContext.request.contextPath}/open_bidding/bidFile.html?id=${projectId}&flowDefineId=${flowDefineId}" >
			<c:if test="${type eq 'gkzb' }">
		     04、招标文件
		     </c:if>
		    <c:if test="${type eq 'jzxtp' }">
		     04、竞谈文件
		    </c:if>
			</a>
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
				<%-- <button class="btn btn-windows edit" onclick="addBidMethod('${projectId}');">制定评标办法</button> --%>
				
				<c:if test="${project.confirmFile==0 || project.confirmFile==null}">
					<button class="btn btn-windows edit" onclick="addMarkTerm();">制定评分细则</button>
				</c:if>
		    </div>
		   
			<div class="container clear margin-top-30" id="package">
				<div>
					<h2 class="f16 count_flow"><span id="projectName">项目名称:${project.name }</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<span id="projectCode">项目编号:${project.projectNumber }</span></h2>
				</div>
				<table class="table table-bordered table-condensed mt5">
					<thead>
						<tr>
							<th class="info w30"><input type="checkbox" id="checkAll"
								onclick="selectAll()" alt=""></th>
							<th>序号</th>
							<th>包名</th>
							<th>评分办法名称</th>
							<th>评标方法</th>
						</tr>
					</thead>
					<c:forEach items="${packagesList }" var="p" varStatus="vs">
						<thead>
							<tr >
								<td class="tc w30"><input type="checkbox" value="${p.id }" name="chkItem">
								</td>
								<td align="center">${vs.index+1 }</td>
								<td align="center">${p.name }</td>
								<td align="center">
									<a href="javascript:void(0)" onclick="show('${p.id}');">${p.bidMethodName }</a>
								</td>
								<td align="center">
									<c:choose>
										<c:when test="${p.bidMethodTypeName==0  }">综合评标法</c:when>
										<c:when test="${p.bidMethodTypeName==1  }">最低评标法</c:when>
										<c:when test="${p.bidMethodTypeName==2  }">基准价评标法</c:when>
										<c:when test="${p.bidMethodTypeName==3  }">性价比评标法</c:when>
										<c:otherwise></c:otherwise>
									</c:choose>
								</td>
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
