<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<jsp:include page="/WEB-INF/view/common.jsp"></jsp:include>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>供应商信息</title>  
  <script type="text/javascript">
  /* 全选全不选 */
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
	     var projectId = "${projectId}";
	     var packageId = "${packageId}";
  			window.location.href="${pageContext.request.contextPath}/reviewFirstAudit/toAudit.html?projectId="+projectId+"&packageId="+packageId;
	  
  }
   //项目评分
  function toGrade(){
	     var projectId = "${projectId}";
	     var packageId = "${packageId}";
  			window.location.href="${pageContext.request.contextPath}/reviewFirstAudit/toGrade.html?projectId="+projectId+"&packageId="+packageId;
	  
  }
   //供应商报价
   function supplierPrice(){
	   var packageId = "${packageId}";
	   var id = [];
		$('input[name="chkItem"]:checked').each(function() {
			id.push($(this).val());
		});
		if (id.length == 1) {
			window.location.href = "${pageContext.request.contextPath}/expert/supplierQuote.html?packageId="+packageId+"&supplierId="+id;
		} else if (id.length > 1) {
			layer.alert("只能选择一个", {
				offset : [ '222px', '390px' ],
				shade : 0.01
			});
		} else {
			layer.alert("请选择要查看的供应商", {
				offset : [ '222px', '390px' ],
				shade : 0.01
			});
		}
 }
  </script>
  </head>
  
  <body>
<!--面包屑导航开始-->
 <div class="margin-top-10 breadcrumbs ">
      <div class="container">
           <ul class="breadcrumb margin-left-0">
           <li><a href="javascript:void(0)"> 首页</a></li><li><a href="javascript:void(0)">个人首页</a></li><li><a href="javascript:void(0)">项目评审</a></li><li><a href="javascript:void(0)">供应商信息</a></li>
           </ul>
        <div class="clear"></div>
      </div>
   </div>
<!-- 录入采购计划开始-->
 <div class="container">
<!-- 项目戳开始 -->
     <div class="container"></div>
   <div class="headline-v2 fl">
      <h2>供应商信息</h2>
   </div> 
   </div>
   <div class="container clear">
   <span class="fl option_btn margin-top-10 ml10">
   	  <c:if test="${packageExpert.isAudit == 1 && packageExpert.isGrade != 1}">
   	   <button class="btn padding-left-10 padding-right-10 btn_back" onclick="toGrade();">评分</button>
   	  </c:if>
   	   <c:if test="${packageExpert.isAudit != 1  && packageExpert.isGrade == 0}">
   	   <button class="btn padding-left-10 padding-right-10 btn_back" onclick="toAudit();">符合性审查</button>
   	  </c:if>
   	  
       <!--  <button class="btn padding-left-10 padding-right-10 btn_back" onclick="toAudit();">符合性检查</button> -->
        <button class="btn padding-left-10 padding-right-10 btn_back" onclick="supplierPrice()">查看供应商报价</button>
        <input class="btn btn-windows back" value="返回" type="button" onclick="location.href='javascript:history.go(-1);'"><br/>
      </span>
    <div class="container margin-top-5">
               <table class="table table-striped table-bordered table-hover">
        <thead>
        <tr>
          <th class="info w30"><input type="checkbox" id="checkAll" onclick="selectAll()"  alt=""></th>
          <th class="info w50">序号</th>
          <th class="info">供应商名称</th>
          <th class="info">联系人姓名</th>
          <th class="info">联系电话</th>
          <th class="info">联系地址</th>
          <th class="info">投标文件</th>
        </tr>
        </thead>
        <tbody id="tbody_id">
        <c:set var="count" value="0"> </c:set>
        <c:forEach items="${supplierList}" var="obj" varStatus="vs">
        	<c:if test="${fn:contains(obj.packages,packageId)}">
        		<c:set var="count" value="${count+1}"></c:set>
	            <tr style="cursor: pointer;">
	              <td class="tc w30"><input type="checkbox" value="${obj.suppliers.id }" name="chkItem"   alt=""></td>
	              <td class="tc w50">${count}</td>
	              <td class="tc">${obj.suppliers.supplierName }</td>
	              <td class="tc">${obj.suppliers.contactName }</td>
	              <td class="tc">${obj.suppliers.contactTelephone }</td>
	              <td class="tc">${obj.suppliers.contactAddress }</td>
	              <td class="tc"></td>
	            </tr>
     		</c:if>
         </c:forEach> 
        </tbody>
      </table>
      </div>
   </div>
 </div>
</body>
</html>
