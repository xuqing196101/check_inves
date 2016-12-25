<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

    <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>
  <head>
  	<%@ include file="/WEB-INF/view/common.jsp" %>
    <title>评标管理</title>  
  <script type="text/javascript">
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
  
  /** 单选 */
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
   //项目评审
 /* function toAudit(){
	  var count = 0;
	  var ids = document.getElementsByName("chkItem");
 
     for(i=0;i<ids.length;i++) {
   		 if(document.getElementsByName("chkItem")[i].checked){
   		 var id = document.getElementsByName("chkItem")[i].value;
   		 var value = id.split(",");
   		 count++;
    }
  }   
  		if(count>1){
  			layer.alert("只能选择一条记录",{offset: ['222px', '390px'],shade:0.01});
  		}else if(count<1){
  			layer.alert("请选择一条记录",{offset: ['222px', '390px'],shade:0.01});
  		}else if(count==1){
  			window.location.href="${pageContext.request.contextPath}/expert/toFirstAudit.html?projectId="+value[0]+"&packageId="+value[1];
     	}
  }*/
	//符合性审查
 	function toAudit(projectId, packageId){
 		window.location.href="${pageContext.request.contextPath}/reviewFirstAudit/toAudit.html?projectId="+projectId+"&packageId="+packageId;
 	}
	//经济技术审查
	function toFirstAudit(projectId, packageId){
		// 下面代码是判断必须符合性审查通过才可以进入经济技术审查
		/*$.ajax({
			url: "${pageContext.request.contextPath}/expert/validateIsGrade.do",
			data: {"projectId": projectId, "packageId": packageId},
			success: function(response){
				// 1 代表isAudit==1 and isGrade != 1
				if (response == "1") {
					window.location.href="${pageContext.request.contextPath}/expert/toFirstAudit.html?projectId="+projectId+"&packageId="+packageId;
				} else {
					layer.alert("符合性审查全部通过之后才可以进行此项操作!", {
						offset : [ '222px', '700px' ],
						shade : 0.01
					});
				}
			}
		});*/
		// 临时代码,可直接进入经济技术审查
		window.location.href="${pageContext.request.contextPath}/reviewFirstAudit/toGrade.html?projectId="+projectId+"&packageId="+packageId;
	}
</script>
  </head>
  
  <body>
<!--面包屑导航开始-->
 <div class="margin-top-10 breadcrumbs ">
      <div class="container">
           <ul class="breadcrumb margin-left-0">
           <li><a href="javascript:void(0)"> 首页</a></li><li><a href="javascript:void(0)">项目评审</a></li>
           </ul>
        <div class="clear"></div>
      </div>
   </div>
<!-- 录入采购计划开始-->
 <div class="container">
<!-- 项目戳开始 -->
     <div class="container" id="tab-1">
   <div class="headline-v2 fl">
      <h2>项目评审</h2>
   </div> 
   </div>
   <div class="container">
   <span class="fl option_btn ml10">
        <!-- <button class="btn padding-left-10 padding-right-10 btn_back" onclick="toAudit();">项目评审</button> -->
      </span>
    	<div class="container margin-top-5">
               <table class="table table-striped table-bordered table-hover">
        <thead>
        <tr>
          <!-- <th class="info w30"><input type="checkbox" id="checkAll" onclick="selectAll()"  alt=""></th> -->
          <th class="info w50">序号</th>
          <th class="info">项目名称</th>
          <th class="info">项目编号</th>
          <th class="info">包名</th>
		  <%--<th class="info">总进度</th>
		  <th class="info">初审进度</th>
		  <th class="info">详审进度</th>--%>
		  <th class="info">操作</th>
		  </tr>
        </thead>
        <tbody id="tbody_id">
        
        <c:forEach items="${projectExtList}" var="obj" varStatus="vs">
            <tr style="cursor: pointer;">
              <%-- <td class="tc w30"><input type="checkbox" value="${obj.id },${obj.packageId}" name="chkItem" onclick="check()"  alt=""></td> --%>
              <td class="tc w50">${vs.count}</td>
              <td>${obj.name}</td>
              <td>${obj.projectNumber}</td>
              <td class="tc">${obj.packageName}</td>
              <td class="tc">
              	<c:forEach items="${obj.packageExperts}" var="pe">
              		<c:if test="${pe.expertId == sessionScope.loginUser.typeId && pe.packageId == obj.packageId && pe.isAudit == 0}">
              			<input type="button" class="btn padding-left-10 padding-right-10 btn_back" onclick="toAudit('${obj.id }','${obj.packageId}');" value="符合性审查">
              		</c:if>
              		<c:if test="${pe.expertId == sessionScope.loginUser.typeId && pe.packageId == obj.packageId && pe.isGrade == 0 && pe.isGather == 1}">
              			<input type="button" class="btn padding-left-10 padding-right-10 btn_back" onclick="toFirstAudit('${obj.id }','${obj.packageId}');" value="经济技术审查">
              		</c:if>
              		<c:if test="${pe.expertId == sessionScope.loginUser.typeId && pe.packageId == obj.packageId && pe.isAudit == 1 && pe.isGrade == 1 }">
              			评审结束
              		</c:if>
              	</c:forEach>
              </td>
			    <!-- 
              <td class="w260">
				  <div class="col-md-12 padding-0">
				  	  <span class="fl padding-5">
				  	  	<c:if test="${obj.reviewProgress.auditStatus == '0'}">未评审</c:if>
				  	  	<c:if test="${obj.reviewProgress.auditStatus == '1'}">初审中</c:if>
				  	  	<c:if test="${obj.reviewProgress.auditStatus == '2'}">初审完成</c:if>
				  	  	<c:if test="${obj.reviewProgress.auditStatus == '3'}">详审中</c:if>
				  	  	<c:if test="${obj.reviewProgress.auditStatus == '4'}">评审完成</c:if>
				  	  </span>
					  <div class="progress w55p fl margin-left-0">
			             <div class="progress-bar progress-bar-danger" role="progressbar" 
			                aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" 	
			                 style="width:${obj.reviewProgress.totalProgress*100}%;"> 
			             </div> 
			          </div>
					  <span class="fl padding-5">${obj.reviewProgress.totalProgress*100}%</span>
				  </div>
			    </td>
			    <td class="tc w200">
				  <div class="col-md-12 padding-0">
					  <div class="progress w55p fl margin-left-0">
			             <div class="progress-bar progress-bar-danger" role="progressbar" 
			                aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" 	
			                 style="width:${obj.reviewProgress.firstAuditProgress*100}%;"> 
			             </div> 
			          </div>
					  <span class="fl padding-5">${obj.reviewProgress.firstAuditProgress*100}%</span>
				  </div>
			    </td>
			    <td class="tc w200">
				  <div class="col-md-12 padding-0">
					  <div class="progress w55p fl margin-left-0">
			             <div class="progress-bar progress-bar-danger" role="progressbar" 
			                aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" 	
			                 style="width:${obj.reviewProgress.scoreProgress*100}%;"> 
			             </div> 
			          </div>
					  <span class="fl padding-5">${obj.reviewProgress.scoreProgress*100}%</span>
				  </div>
			    </td>
			     -->
            </tr>
         </c:forEach> 
        </tbody>
      </table>
      </div>
 </div>


 
     </body>
</html>
