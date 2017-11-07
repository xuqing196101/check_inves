<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="/WEB-INF/view/common.jsp"></jsp:include>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>符合性审查项</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page"> 
    <!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->
  </head>
  <script type="text/javascript">
    $(function(){
    	if("${page}"=="1"){
    		$("#content1").show();
    		$("#content2").hide();
    		$("#content3").hide();
    		$("#content4").hide();
    	}else if("${page}"=="2"){
    		$("#content1").hide();
    		$("#content2").show();
    		$("#content3").hide();
    		$("#content4").hide();
    	}else if("${page}"=="3"){
    		$("#content1").hide();
    		$("#content2").hide();
    		$("#content3").show ();
    		$("#content4").hide();
    		var url='${pageContext.request.contextPath}/file/viewFile.html?id=${approvalFile[0].id}&key=2';
    		var html='<img data-original="'+url+'"  src="'+url+'"/>';
    		$("#img3").append(html);
    	}else if("${page}"=="4"){
    		$("#content1").hide();
    		$("#content2").hide();
    		$("#content3").hide();
    		$("#content4").show();
    		var url='${pageContext.request.contextPath}/file/viewFile.html?id=${bidFile[0].id}&key=2';
    		var html='<img data-original="'+url+'"  src="'+url+'"/>';
    		$("#img4").append(html);
    		
    	}
    });
  </script>
<body>  
<div class="content" id="content1">
    <h2 class="list_title"><span class="f16 b">${packages.name}资格性符合性检查项查看</a></h2>
        <table class="table table-bordered table-condensed table-hover">
            <thead>
               <tr>
                  <th class="info" colspan="2">评审名称</th>
                  <th class="info">评审内容</th>
               </tr>
            </thead>
            <c:forEach items="${dds_zg}" var="d" varStatus="vs">
               <!-- 如果没有评审项 ，显示空td-->
               <c:if test="${d.code == 'COMPLIANCE' && items1_zg.size() == 0}">
                 <tr id="${d.id}">
                    <td class="w150">
                        <span class="fl">${d.name}</span>
                    </td>
                    <td></td>
                    <td></td>
                 </tr>
               </c:if>
               <c:if test="${d.code == 'QUALIFICATION' && items2_zg.size() == 0}">
                 <tr id="${d.id}">
                    <td class="w150">
                        <span class="fl">${d.name}</span>
                    </td>
                    <td></td>
                    <td></td>
                 </tr>
               </c:if>
               <!-- 如果有评审项 ，加载符合性评审项-->
               <c:if test="${d.code == 'COMPLIANCE' && items1_zg.size() > 0}">
                 <tr id="${d.id}">
                    <td rowspan="${items1_zg.size() + 1}" class="w150">
                        <span class="fl">${d.name}</span>
                    </td>
                 </tr>
                 <c:forEach items="${items1_zg}" var="i" varStatus="iv">
                 <tr>
                     <td class="w260">
                         <c:if test="${i.kind == d.id}">
                             <span class="fl">${i.name}</span>
                         </c:if>
                     </td>
                     <td>
                         <c:if test="${i.kind == d.id}">
                         ${i.content}
                      </c:if>
                     </td>
                 </tr>  
                 </c:forEach>
                </c:if>
                <!-- 如果有评审项 ，加载资格性评审项-->
                <c:if test="${d.code == 'QUALIFICATION' && items2_zg.size() > 0}">
                 <tr id="${d.id}">
                    <td rowspan="${items2_zg.size() + 1}" class="w150">
                        <span class="fl">${d.name}</span>
                    </td>
                 </tr>
                 <c:forEach items="${items2_zg}" var="i" varStatus="iv">
                 <tr>
                     <td class="w260">
                         <c:if test="${i.kind == d.id}">
                             <span class="fl">${i.name}</span>
                         </c:if>
                     </td>
                     <td>
                         <c:if test="${i.kind == d.id}">
                            ${i.content}
                         </c:if>
                     </td>
                 </tr>  
                 </c:forEach>
                </c:if>
             </c:forEach>
        </table>
    </div>
    <div class="content" id="content2">
    <h2 class="list_title"><span class="f16 b">${packages.name}经济技术评审项查看</span></h2>
       <c:if test="${methodCode=='PBFF_JZJF'||methodCode=='PBFF_ZDJF'}">
       <table class="table table-bordered table-condensed table-hover">
            <thead>
               <tr>
                  <th class="info" colspan="2">评审名称</th>
                  <th class="info">评审内容</th>
               </tr>
            </thead>
            <c:forEach items="${dds_jz}" var="d" varStatus="vs">
               <!-- 如果没有评审项 ，显示空td-->
               <c:if test="${d.code == 'ECONOMY' && items1_jz.size() == 0}">
                 <tr id="${d.id}">
                    <td rowspan="2" class="w150">
                        <span class="fl">${d.name}</span>
                    </td>
                 </tr>
                 <tr>
                     <td></td>
                     <td></td>
                 </tr>
               </c:if>
               <c:if test="${d.code == 'TECHNOLOGY' && items2_jz.size() == 0}">
                 <tr id="${d.id}">
                    <td rowspan="2" class="w150">
                        <span class="fl">${d.name}</span>
                    </td>
                 </tr>
                 <tr>
                     <td></td>
                     <td></td>
                 </tr>
               </c:if>
               <!-- 如果有评审项 ，加载符合性评审项-->
               <c:if test="${d.code == 'ECONOMY' && items1_jz.size() > 0}">
                 <tr id="${d.id}">
                    <td rowspan="${items1_jz.size() + 1}" class="w150">
                        <span class="fl">${d.name}</span>
                    </td>
                 </tr>
                 <c:forEach items="${items1_jz}" var="i" varStatus="iv">
                 <tr>
                     <td class="w260">
                         <c:if test="${i.kind == d.id}">
                             <span class="fl">${i.name}</span>
                         </c:if>
                     </td>
                     <td>
                         <c:if test="${i.kind == d.id}">
                         ${i.content}
                      </c:if>
                     </td>
                 </tr>  
                 </c:forEach>
                </c:if>
                <!-- 如果有评审项 ，加载资格性评审项-->
                <c:if test="${d.code == 'TECHNOLOGY' && items2_jz.size() > 0}">
                 <tr id="${d.id}">
                    <td rowspan="${items2_jz.size() + 1}" class="w150">
                        <span class="fl">${d.name}</span>
                    </td>
                 </tr>
                 <c:forEach items="${items2_jz}" var="i" varStatus="iv">
                 <tr>
                     <td class="w260">
                         <c:if test="${i.kind == d.id}">
                             <span class="fl">${i.name}</span>
                         </c:if>
                     </td>
                     <td>
                         <c:if test="${i.kind == d.id}">
                            ${i.content}
                         </c:if>
                     </td>
                 </tr>  
                 </c:forEach>
                </c:if>
             </c:forEach>
        </table>
    </c:if>
    <c:if test="${methodCode=='OPEN_ZHPFF'}">
        <table class="table table-bordered table-condensed table-hover">
            <thead>
               <tr>
                   <th class="info" width="15%">评审类别</th>
                  <th class="info" width="20%">评审项目</th>
                  <th class="info" width="15%">评审指标</th>
                  <th class="info" width="10%">所属模型</th>
                  <th class="info" width="30%">评审内容</th>
                  <th class="info" width="10%">分值</th>
               </tr>
            </thead>
                <c:out value="${str_zh}" escapeXml="false"/>  
        </table>
    </c:if>
    
    </div>
    <div class="content" id="content3">
       <h2 class="list_title"><span class="f16 b">报批说明</span></h2>
       <div id="img3" class="tc"></div>
    </div>
    <div class="content" id="content4">
       <h2 class="list_title"><span class="f16 b">审批单</span></h2>
       <div id="img4" class="tc"></div>
    </div>
	    <div class="mt40 tc mb50 position_fixed" >
	        <c:if test="${page==1}">
	          <button class="btn btn-windows back" disabled="disabled" >上一页</button>
	          <button class="btn btn-windows back" onclick="window.location.href='${pageContext.request.contextPath}/intelligentScore/viewPackageModel.html?packId=${packageId}&projectId=${projectId}&page=${page+1}'">下一页</button>
	        </c:if>
	        <c:if test="${page!=1&&page!=4}">
	          <button class="btn btn-windows back" onclick="window.location.href='${pageContext.request.contextPath}/intelligentScore/viewPackageModel.html?packId=${packageId}&projectId=${projectId}&page=${page-1}'">上一页</button>
	          <button class="btn btn-windows back" onclick="window.location.href='${pageContext.request.contextPath}/intelligentScore/viewPackageModel.html?packId=${packageId}&projectId=${projectId}&page=${page+1}'">下一页</button>
	        </c:if>
	        <c:if test="${page==4}">
	          <button class="btn btn-windows back"  onclick="window.location.href='${pageContext.request.contextPath}/intelligentScore/viewPackageModel.html?packId=${packageId}&projectId=${projectId}&page=${page-1}'">上一页</button>
	          <button class="btn btn-windows back" disabled="disabled" >下一页</button>
	        </c:if>
	        <button class="btn btn-windows back" onclick="window.close();">关闭</button>
	    </div>
</body>
</html>