<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<jsp:include page="/WEB-INF/view/common.jsp"></jsp:include>
    <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
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
	function toAudit(){
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
  			layer.alert("请先选择一条记录",{offset: ['222px', '390px'],shade:0.01});
  		}else if(count==1){
  			window.location.href="${pageContext.request.contextPath}/expert/projectList.html?projectId="+value[0];
     	}
  	}
</script>
  </head>
  
  <body>
<!--面包屑导航开始-->
 <div class="margin-top-10 breadcrumbs ">
      <div class="container">
           <ul class="breadcrumb margin-left-0">
           <li><a href="javascript:void(0)"> 首页</a></li><li><a href="javascript:void(0)">个人首页</a></li><li><a href="javascript:void(0)">我的项目</a></li>
           </ul>
        <div class="clear"></div>
      </div>
   </div>
<!-- 项目戳开始 -->
     <div class="container" id="tab-1">
   <div class="headline-v2 fl">
      <h2>我的项目</h2>
   </div> 
   </div>
  <div class="container">
  <span class="fl option_btn  ml10"><button class="btn padding-left-10 padding-right-10 btn_back" onclick="toAudit();">详细评审</button></span>
    <div class="container margin-top-5">
	      <table class="table table-striped table-bordered table-hover">
	        <thead>
	        <tr>
	          <th class="info w30"><input type="checkbox" id="checkAll" onclick="selectAll()"  alt=""></th>
	          <th class="info w50">序号</th>
	          <th class="info">项目名称</th>
	          <th class="info">项目编号</th>
	          <th class="info">采购方式</th>
	          <th class="info">项目状态</th>
	        </tr>
	        </thead>
	        <tbody id="tbody_id">
	        
	        <c:forEach items="${projectExtList}" var="obj" varStatus="vs">
	            <tr style="cursor: pointer;">
	              <td class="tc w30"><input type="checkbox" value="${obj.id }" name="chkItem" onclick="check()"  alt=""></td>
	              <td class="tc w50">${vs.count}</td>
	              <td class="tc">${obj.name }</td>
	              <td class="tc">${obj.projectNumber }</td>
	              <td class="tc">${obj.dictionary.name }</td>
	              <td class="tc">
	              <c:if test="${'1'==obj.status}">实施中</c:if>
	              <c:if test="${'2'==obj.status}">已成交</c:if>
	              <c:if test="${'3'==obj.status}">已立项</c:if>
	              </td>
	            </tr>
	     
	         </c:forEach> 
	        </tbody>
	      </table>
     </div>
  </div>
</body>
</html>
