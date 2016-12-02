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
         /**添加临时专家*/
         function addexp(){
               var id=[]; 
                $('input[name="chkItem"]:checked').each(function(){ 
                    id.push($(this).val());
                }); 
                if(id.length==1){
                    window.location.href="${pageContext.request.contextPath}/ExpExtract/showTemporaryExpert.html?packageId="+id+"&&projectId=${project.id}&&&&flowDefineId=${flowDefineId}";
                }else if(id.length>1){
                    layer.alert("只能选择一个",{offset: ['100px', '300px'], shade:0.01});
                }else{
                    layer.alert("请选择包",{offset: ['100px', '300px'], shade:0.01});
                }
         }
         
         /**添加组长*/
         function addLeader(){
               var id=[]; 
                $('input[name="chkItem"]:checked').each(function(){ 
                    id.push($(this).val());
                }); 
                if(id.length==1){
                    window.location.href="${pageContext.request.contextPath}/packageExpert/showExpert.html?packageId="+id+"&&flowDefineId=${flowDefineId}";
                }else if(id.length>1){
                    layer.alert("只能选择一个",{offset: ['100px', '300px'], shade:0.01});
                }else{
                    layer.alert("请选择包",{offset: ['100px', '300px'], shade:0.01});
                }
         }
         /** 执行完成*/
         function finish(){
             layer.confirm('确定之后不可修改，是否确定？', {
                  btn: ['确定','取消'],offset: ['100px', '300px'], shade:0.01
                }, function(index){
                    $.ajax({
                        type: "POST",
                        url:"${pageContext.request.contextPath}/packageExpert/executeFinish.html?flowDefineId=${flowDefineId}&&projectId=${project.id}",
                        dataType:"json",
                        success: function(data) {
                            if(data == "SCCUESS"){
                                  window.location.href = '${pageContext.request.contextPath}/packageExpert/assignedExpert.html?projectId=${project.id}&&flowDefineId=${flowDefineId}';
                            }else{
                                   layer.alert("请选择组长",{offset: ['222px', '390px'], shade:0.01});
                            }
                        }
                    });
                    
                }, function(index){
                    layer.close(index);
                });
             
             
        
         }
         
  </script>
  <body>
        <h2 class="list_title">专家抽取包列表</h2>
        <c:if test="${execute != 'SCCUESS' }">
          <div class="col-md-12 col-xs-12 col-sm-12 p0 mb5">
             <button class="btn btn-windows add" onclick="addexp();" type="button">添加专家</button>
             <button class="btn " onclick="addLeader();" type="button">分配组长</button>
             <button class="btn " onclick="finish();" type="button">执行完成</button>
          </div>
        </c:if>
        <table class="table table-bordered table-condensed table-hover table-striped">
            <thead>
            <tr>
              <th class="info w30"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
              <th class="w50 info">序号</th>
              <th class="info">包名</th>
              <th class="info">组长</th>
              <th class="info">专家人数</th>
            </tr>
            </thead>
            <c:forEach items="${packageList }" var="pack" varStatus="vs">
                <tr>
                    <td class="tc"><input onclick="check()" type="checkbox" name="chkItem" value="${pack.id}" /></td>
                    <td class="tc w30">${vs.count }</td>
                    <td class="tc">${pack.name }</td>
                     <td class="tc">
                      <c:set value="0" var="num"></c:set>
                      <c:forEach items="${selectList}" var="list"> 
                         <c:if test="${pack.id==list.packageId}">
                                ${list.expert.relName}
                           <c:set value="1" var="num"></c:set>
                         </c:if>
                      </c:forEach>
                      <c:if test="${num==0}">
                                                                             暂无
                      </c:if>
                    </td>
                    <td class="tc"><a href="${pageContext.request.contextPath}/packageExpert/showExpert.html?packageId=${pack.id}&&flowDefineId=${flowDefineId}&&execute=${execute}">${fn:length(pack.listExperts)}</td>
                </tr>
            </c:forEach>
        </table>
  </body>
</html>
