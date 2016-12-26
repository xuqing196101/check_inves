<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

  <head>
    <base href="${pageContext.request.contextPath}/">

    <title>确定中标供应商</title>

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
  /**展示信息**/
  function view(id){
	  window.location.href="${pageContext.request.contextPath}/winningSupplier/packageSupplier.html?packageId="+id+"&&flowDefineId=${flowDefineId}&&projectId=${projectId}&&view=1";
  }
      
      /** 确认中标供应商  */
     function confirm(id){
//     	 var id=[]; 
//          $('input[name="chkItem"]:checked').each(function(){ 
//              id.push($(this).val());
//          }); 
//          if(id.length==1){
//         	 <c:forEach items="${packList }" var="pack" varStatus="vs">
//         	  if(id=="${pack.id}"){
//         		  var pass="${pack.listCheckPasses}";
//         		  if(pass.length>2){
//         			  layer.alert("已选择",{offset: ['100px', '300px'], shade:0.01});
//         		  }else{
        			   window.location.href="${pageContext.request.contextPath}/winningSupplier/packageSupplier.html?packageId="+id+"&&flowDefineId=${flowDefineId}&&projectId=${projectId}";
//         		  }
        		 
//         	  }
//         	 </c:forEach>
//          }else if(id.length>1){
//              layer.alert("只能选择一个",{offset: ['100px', '300px'], shade:0.01});
//          }else{
//              layer.alert("请选择包",{offset: ['100px', '300px'], shade:0.01});
//          }
     }
      
     /** 执行完成*/
     function finish(){
         layer.confirm('确定之后不可修改，是否确定？', {
              btn: ['确定','取消'],offset: ['100px', '300px'], shade:0.01
            }, function(index){
                $.ajax({
                    type: "POST",
                    url:"${pageContext.request.contextPath}/winningSupplier/executeFinish.html?flowDefineId=${flowDefineId}&&projectId=${projectId}",
                    dataType:"json",
                    success: function(data) {
                        if(data == "SCCUESS"){
                              window.location.href = '${pageContext.request.contextPath}/winningSupplier/selectSupplier.html?projectId=${projectId}&&flowDefineId=${flowDefineId}';
                        }else{
                               layer.alert("请选择中标供应商",{offset: ['100', '300px'], shade:0.01});
                        }
                    }
                });
                
            }, function(index){
                layer.close(index);
            });
         
         
    
     }
     
     /** 中标供应商 */
     function tabone(){
    	 window.location.href="${pageContext.request.contextPath}/winningSupplier/selectSupplier.html?projectId=${projectId}";
     }
     
     /** 中标通知 */
     function tabtwo(){
    	 var error = "${error}";
    	 if(error != null && error == "ERROR"){
    		 layer.alert("请选择中标供应商",{offset: ['100', '300px'], shade:0.01});
    	 }else{
    		 window.location.href="${pageContext.request.contextPath}/winningSupplier/template.do?projectId=${projectId}";
    	 }
    	 
       
     }
     
     /** 未中标通知 */
     function tabthree(){
    	 var error = "${error}";
    	 if (error != null && error == "ERROR" ){
    		 layer.alert("请选择中标供应商",{offset: ['100', '300px'], shade:0.01});
    	 } else{
    		   window.location.href="${pageContext.request.contextPath}/winningSupplier/notTemplate.do?projectId=${projectId}";  
    	 }
     }
      
  </script>

  <body>
    <div class="col-md-12 col-xs-12 col-sm-12 p0">
      <ul class="flow_step">
        <li class="active">
          <a href="javascript:void(0);" onclick="tabone();">01、确认中标供应商</a>
          <i></i>
        </li>
        <li>
	          <a href="javascript:void(0);" onclick="tabtwo();">02、中标通知书</a>
	          <i></i>
        </li>
        <li>
              <a href="javascript:void(0);" onclick="tabthree();">03、未中标通知书</a>
            <i></i>
        </li>
      </ul>
    </div>
      <h2 class="list_title mb0 clear">包列表</h2>
      <c:if test="${execute != 'SCCUESS' }">
        <div class="col-md-12 col-xs-12 col-sm-12 mt10 p0">
            <button class="btn " onclick="finish();" type="button">执行完成</button>
        </div>
      </c:if>
      <div class="content table_box pl0">
        <table class="table table-bordered table-condensed table-hover table-striped">
          <thead>
            <tr>
              <th class="w50 info">序号</th>
              <th class="info">包名</th>
              <th class="info">中标供应商信息</th>
            </tr>
          </thead>
          <c:forEach items="${packList }" var="pack" varStatus="vs">
            <tr >
              <td class="tc w30" >${vs.count }</td>
              <td class="tc" >${pack.name }</td>
              <td class="tc" >
              <c:choose>
                <c:when test="${fn:length(pack.listCheckPasses) != 0}">
                <a href="javascript:void(0);" onclick="view('${pack.id}');">
                  <c:forEach items="${pack.listCheckPasses}" var="list">
                   ${list.supplier.supplierName}
                  </c:forEach>
                  </a>
                </c:when>
                <c:otherwise>
                 <button class="btn btn-windows add" onclick="confirm('${pack.id}');" type="button">选择供应商</button>
                 <c:set value="1" var="values" />
                </c:otherwise>
              </c:choose>
              </td>
            </tr>
          </c:forEach>
        </table>
      </div>
  </body>

</html>