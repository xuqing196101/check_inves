<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>任务管理</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<link rel="stylesheet"
    href="${pageContext.request.contextPath}/public/supplier/css/supplieragents.css"
    type="text/css">



<script type="text/javascript">
  
  /*分页  */
	  $(function(){
          laypage({
                cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
                pages: "${list.pages}", //总页数
                skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
                skip: true, //是否开启跳页
                total: "${list.total}",
                startRow: "${list.startRow}",
                endRow: "${list.endRow}",
                groups: "${list.pages}">=5?5:"${list.pages}", //连续显示分页数
                curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
                   
                    return "${list.pageNum}";
                }(), 
                jump: function(e, first){ //触发分页后的回调
                    if(!first){ //一定要加此判断，否则初始时会无限刷新
                    	location.href = '${pageContext.request.contextPath}/SupplierExtracts/Extraction.html?projectId=${projectId}&page='+e.curr;
                    }
                }
            });
      });
  
  
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
    
    /** 单选 */projectName
    function check(){
         var count=0;
         var checklist = document.getElementsByName ("chkItem");
         var checkAll = document.getElementById("checkAll");
         for(var i=0;i<checklist.length;i++){
               if(checklist[i].checked == false){
                   checkAll.checked = false;
                   break;
               }
               for(var j=0;j<checklist.length;j++){
                     if(checklist[j].checked == true){
                           checkAll.checked = true;
                           count++;
                       }
                 }
           }
    }
    
    function add(){
    	var projectName = $("#projectName").val();
    	var projectNumber = $("#projectNumber").val();
    	
    	 location.href = '${pageContext.request.contextPath}/SupplierExtracts/addExtraction.html?projectId=${projectId}&&projectName='+projectName+'&&projectNumber='+projectNumber+'&&typeclassId=${typeclassId}';
    }
    
    function extract(id,btn){
    	  layer.open({
              type: 2, //page层
              area: ['90%', '50%'],
              title: '抽取专家 项目名称： ${projectName}',
              closeBtn: 1,
              shade:0.01, //遮罩透明度
              shadeClose: true,
              offset: '30px',
              move:false,
              content: '${pageContext.request.contextPath}/SupplierExtracts/extractCondition.html?cId='+id
            });
    	  $(btn).next().remove();
    	 $(btn).parent().parent().find("td:eq(2)").html("已抽取");
    	  
    }
    function update(id){
    	  location.href = '${pageContext.request.contextPath}/SupplierCondition/showSupplierCondition.html?Id='+id+'&&typeclassId=${typeclassId}';
  }
  </script>
</head>

<body>
	<!--面包屑导航开始-->
	<c:if test="${typeclassId!=null && typeclassId !='' }">
	<div class="margin-top-10 breadcrumbs ">
        <div class="container">
            <ul class="breadcrumb margin-left-0">
                <li><a href="#"> 首页</a></li>
                <li><a href="#">支撑环境系统</a></li>
                <li><a href="#">供应商管理</a></li>
                       <li><a href="#">供应商抽取</a></li>
                <li class="active"><a href="#">供应商抽取列表</a></li>
            </ul>
            <div class="clear"></div>
        </div>
    </div>
	</c:if>
	<!-- 录入采购计划开始-->
	<div class="container">
		<!-- 项目戳开始 -->
		<form id="add_form" action="${pageContext.request.contextPath}/project/list.html"
			method="post">
		</form>
		<div class="container clear margin-top-30">
		  <div class="clear"></div>
          <span class="fl mt5  margin-top-10">
                 <span class="fl margin-top-6">
                 <c:if test="${projectId == null || projectId == ''}">
                 <span class="red">*</span>
                 </c:if>项目名称 ：</span>
                 <c:if test="${projectId!=null&&projectId!=''}">
	                 <span class=" fl" title="${projectName}">
                    <c:choose>
                        <c:when test="${fn:length(projectName) > 50}">
                          ${fn:substring(projectName, 0, 50)}......
                        </c:when>
                        <c:otherwise>
                        ${projectName}
                        </c:otherwise>
                    </c:choose>
                    </span>
                    <input type="hidden" class="fl" value="${projectName}" />
                 </c:if>
                 <c:if test="${projectId == null || projectId == ''}">
                   <input type="text" id="projectName"  class="fl" value="${projectName}" />
                   
                 </c:if>
                <div class="b f14 red tip fl w150" id="projectName">${projectNameError}</div> 
        </span>
         <span class="fl mt5 ml20 margin-top-10">
                 <span class="fl margin-top-6">
                   <c:if test="${projectId == null || projectId == ''}">
                 <span class="red">*</span>
                 </c:if>
                                               项目编号：</span>
                   <c:if test="${projectId!=null&&projectId!=''}">
                    <span class=" fl" title="${projectNumber}">
                    <c:choose>
                        <c:when test="${fn:length(projectNumber) > 50}">
                          ${fn:substring(projectNumber, 0, 50)}......
                        </c:when>
                        <c:otherwise>
                        ${projectNumber}
                        </c:otherwise>
                    </c:choose>
                    </span>
                    <input type="hidden" class="fl"  value="${projectNumber}" />
                 </c:if>
                 <c:if test="${projectId == null || projectId == ''}">
                    <input type="text" class="fl" id="projectNumber"  value="${projectNumber}" />
                    
                 </c:if>
                  <div class="b f14 red tip fl w150" id="projectNumber">${projectNumberError}</div> 
               
        </span>
        <span class="fr option_btn margin-top-10">
            <button class="btn padding-left-10 padding-right-10 btn_back"
                onclick="add();">添加抽取条件</button>
        </span>
			<table class="table table-bordered table-condensed mt5">
				<thead>
					<tr>
						<th class="info w50">序号</th>
						<th class="info">抽取条件</th>
						<th class="info">状态</th>
						<th class="info">操作</th>
					</tr>
				</thead>
				<c:forEach items="${list.list}" var="obj" varStatus="vs">
					<tr >
						<td class="tc w50">${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
						<td class="w800">第【${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}】次抽取，供应商所在地区【${ obj.address}】
						<c:forEach items="${obj.conTypes }" var="contypes">
					
						 <c:choose>
						  <c:when test="${'1^2^' == contypes.supplieTypeId  }">
						                   ，  供应商类型【 生产型,销售型 】
						  </c:when>
						  <c:when test='${contypes.supplieTypeId == "1^"}'>
						                                 ，  供应商类型【生产型】
						  </c:when>
						  <c:when test='${contypes.supplieTypeId == "2^" }'>
						    ， 供应商类型【销售型】
						  </c:when>
						 </c:choose>
                          <c:if test="${contypes.categoryName!=null}">
                          <c:set var="re" value="${fn:substring(contypes.categoryName, 0, contypes.categoryName.length()-1)}"/>
                                                                               ， 采购类别【${fn:replace(re,'^',',')}】
                          </c:if>   
                          <c:if test="${contypes.supplieCount!=null }">
                                                                         ，供应商数量【${contypes.supplieCount }】
                        </c:if>  
						</c:forEach>
						</td>
						<td class="tc w75" id="status"><c:if test="${obj.status==1}">
                                                                           待抽取
                      </c:if> <c:if test="${obj.status==2}">
		                                                     已抽取
		                </c:if>
                </td>
						<td class="tc w100" align="center" >
							<button
								class="btn"
								id="save" type="button" onclick="extract('${obj.id }',this);">抽取</button>
								<c:if test="${obj.status==1 }">
								        <button
                                class="btn margin-top-10"
                                id="save" type="button" onclick="update('${obj.id }');">修改</button>
								</c:if>
						</td>
					</tr>
				</c:forEach>
			</table>

			<div id="pagediv" align="right"></div>
		</div>
	</div>




</body>
</html>
