<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<jsp:include page="/WEB-INF/view/common.jsp"></jsp:include>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>My JSP 'expert_list.jsp' starting page</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">

  </head>
  <script type="text/javascript">
     /** 全选全不选 */
    function selectAll(index){
         var checklist = document.getElementsByName ("chkItemExpert"+index);
         var checkAll = document.getElementById("checkAllExpert"+index);
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
    function check(index){
         var count=0;
         var checklist = document.getElementsByName ("chkItemExpert"+index);
         var checkAll = document.getElementById("checkAllExpert"+index);
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
     function ycDiv(obj, index) {
  	  	  if ($(obj).hasClass("shrink") && !$(obj).hasClass("spread")) {
            $(obj).removeClass("shrink");
            $(obj).addClass("spread");
          } else {
            if ($(obj).hasClass("spread") && !$(obj).hasClass("shrink")) {
              $(obj).removeClass("spread");
              $(obj).addClass("shrink");
          }
          }
          var divObj = new Array();
          divObj = $(".p0" + index);
          for (var i =0; i < divObj.length; i++) {
              if ($(divObj[i]).hasClass("p0"+index) && $(divObj[i]).hasClass("hide")) {
                $(divObj[i]).removeClass("hide");
              } else {
                if ($(divObj[i]).hasClass("p0"+index)) {
                  $(divObj[i]).addClass("hide");
                };
              };
          };
      }
      
      $(function() {
       // $("#statusBid").find("option[value='${statusBid}']").attr("selected", true);
        var index=0;
        var divObj = $(".p0" + index);
        $(divObj).removeClass("hide");
        $("#package").removeClass("shrink");        
        $("#package").addClass("spread");
      })
      
      /**重置密码*/
	 function resetPwd(index){
 	   	var id=[]; 
        $('input[name="chkItemExpert'+index+'"]:checked').each(function(){ 
            id.push($(this).val());
        }); 
        if(id.length>=1){
     	   $.ajax({
                type: "GET",
                url: "${pageContext.request.contextPath}/ExpExtract/resetPwd.do?eid="+id+"&&flowDefineId=${flowDefineId}",
                dataType: "json",
                success: function(data){
             	   if("sccuess" == data){
                        layer.alert("重置成功！默认密码：123456",{offset: '50px', shade:0.01});
                           }else{
                         	   layer.alert("重置失败！请尝试重新重置",{offset: '50px', shade:0.01});
                           }
                         }
            });
        }else{
            layer.alert("请选择需要重置密码的专家",{offset: '50px', shade:0.01});
        }
	 }
      
  </script>
  <body>
        <h2 class="list_title">专家名单</h2>
        <c:forEach items="${packages}" var="pack" varStatus="vs">
        <div class="over_hideen">
			<h2 onclick="ycDiv(this,'${vs.index}')" class="count_flow shrink hand fl clear" id="package">包名:<span class="f15 blue">${pack.name}</span>
          	</h2>
       		<div class="fl mt20 ml10">
             <button class="btn" onclick="resetPwd('${vs.index}');" type="button">重置密码</button>
           	</div>
        </div>
       	<div class="p0${vs.index} hide">
        	<table class="table table-bordered table-condensed table-hover table-striped mt5 space_nowrap">
	            <thead>
	              <tr>
	                <th class="info w50"><input id="checkAllExpert${vs.index}" type="checkbox" onclick="selectAll('${vs.index}')" /></th>
	                <th class="info w50">序号</th>
	                <th class="info">专家姓名</th>
	                <th class="info">专家类别</th>
	                <th class="info">是否组长</th>
	                <th class="info">是否到场</th>
	                <th class="info">证件号</th>
	                <th class="info">现任职务</th>
	                <th class="info">联系电话</th>
	              </tr>
	            </thead>
            	<tbody>
            		<c:set var="count" value="0"/>
            		<c:forEach items="${expertSigneds}" var="packageExpert" varStatus="v">
            		<c:if test="${pack.id == packageExpert.packageId}">
            		<c:set var="count" value="${count+1}"/>
            		<tr>
            			<td class="tc opinter w50">
            				<input type="checkbox" value="${packageExpert.expert.id}" name="chkItemExpert${vs.index}" onclick="check('${vs.index}')">
						</td>
            			<td class="tc">${count}</td>
            			<td>
            				${packageExpert.expert.relName}
            			</td>
            			<td>
            			<c:forEach var="expertType" items="${ddList}">
		                   <c:if test="${packageExpert.expert.expertsTypeId eq expertType.id}">
		                    ${expertType.name}
		                   </c:if>
		                </c:forEach>
		                </td>
		                <td class="tc">
	                         <c:if test="${packageExpert.isGroupLeader == 1}">
	                          	 是
	                         </c:if>
	                      	<c:if test="${packageExpert.isGroupLeader == 0}">
	                           	否
	                      	</c:if>
		                </td>
		                <td>
		                	已到场
		                </td>
                		<td class="tc">${packageExpert.expert.idNumber}</td>
		                <td class="tc">${packageExpert.expert.atDuty}</td>
		                <td class="tc">${packageExpert.expert.mobile}</td>
		            </tr>
		            </c:if>
		            </c:forEach>
            	</tbody>
           	</table>
           </div>
       </c:forEach>
  </body>
</html>
