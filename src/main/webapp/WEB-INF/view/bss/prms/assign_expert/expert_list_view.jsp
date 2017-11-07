<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
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
  	$(function() {
  		//获取查看或操作权限
       	var isOperate = $('#isOperate', window.parent.document).val();
       	if (isOperate == 0) {
       		//只具有查看权限，隐藏操作按钮
			$(":button").each(function(){ 
                $(this).hide();
            }); 
		}
    })
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
			<h2 onclick="ycDiv(this,'${vs.index}')" 
			<c:if test="${pack.projectStatus eq 'YZZ' || pack.projectStatus eq 'ZJZXTP' || pack.projectStatus eq 'ZJTSHZ' || pack.projectStatus eq 'ZJTSHBTG'}">class="count_flow hand fl spread"</c:if>class="count_flow shrink hand fl clear" id="package">包名:<span class="f15 blue">${pack.name}</span>
			<c:if test="${pack.projectStatus eq 'YZZ'}"><span class="star_red">[该包已终止]</span></c:if>
			<c:if test="${pack.projectStatus eq 'ZJZXTP'}"><span class="star_red">[该包已转竞谈]</span></c:if>
			<c:if test="${pack.projectStatus eq 'ZJTSHZ'}"><span class="star_red">[该包转竞谈审核中]</span></c:if>
			<c:if test="${pack.projectStatus eq 'ZJTSHBTG'}"><span class="star_red">[该包转竞谈审核不通过]</span></c:if>
          	</h2>
       		<div class="fl mt20 ml10">
             <button class="btn"  <c:if test="${pack.projectStatus eq 'YZZ' || pack.projectStatus eq 'ZJZXTP' || pack.projectStatus eq 'ZJTSHZ' || pack.projectStatus eq 'ZJTSHBTG'}">disabled="disabled"</c:if>onclick="resetPwd('${vs.index}');" type="button">重置密码</button>
           	</div>
        </div>
        <c:if test="${pack.projectStatus ne 'YZZ' || pack.projectStatus ne 'ZJZXTP' || pack.projectStatus ne 'ZJTSHZ' || pack.projectStatus ne 'ZJTSHBTG'}">
       	<div class="p0${vs.index} hide">
        	<table class="table table-bordered table-condensed table-hover table-striped mt5 space_nowrap">
	            <thead>
	              <tr>
	                <th class="info w50"><input id="checkAllExpert${vs.index}" type="checkbox" onclick="selectAll('${vs.index}')" /></th>
	                <th class="info w50">序号</th>
	                <th class="info">用户名</th>
	                <th class="info w70">专家姓名</th>
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
            		<c:if test="${pack.id eq packageExpert.packageId}">
            		<c:set var="count" value="${count+1}"/>
            		<tr>
            			<td class="tc opinter w50">
            				<input type="checkbox" value="${packageExpert.expert.id}" name="chkItemExpert${vs.index}" onclick="check('${vs.index}')">
						</td>
            			<td class="tc">${count}</td>
            			<td>
		            				${packageExpert.expertId}
		            			</td>
            			<td>
            				${packageExpert.expert.relName}
            			</td>
            			<td>
            			<c:forEach var="type" items="${reviewTypes}">
		                   <c:if test="${packageExpert.reviewTypeId eq type.id}">
		                    ${type.name}
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
                		<td class="tc">${packageExpert.expert.idCardNumber}</td>
		                <td class="tc">${packageExpert.expert.atDuty}</td>
		                <td class="tc">${packageExpert.expert.mobile}</td>
		            </tr>
		            </c:if>
		            </c:forEach>
            	</tbody>
           	</table>
           </div>
           </c:if>
       </c:forEach>
  </body>
</html>
