<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<jsp:include page="/WEB-INF/view/common.jsp"></jsp:include>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  </head>
  <script type="text/javascript">
    $(function(){
    laypage({
      cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
        pages: "${list.pages}", //总页数
        skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
        skip: true, //是否开启跳页
        total: "${list.total}",
        startRow: "${list.startRow}",
        endRow: "${list.endRow}",
        groups: "${list.pages}">=5?5:"${list.pages}",
        curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
      return "${list.pageNum}";
      }(), 
        jump: function(e, first){ //触发分页后的回调
            if(!first){ //一定要加此判断，否则初始时会无限刷新
              $("#page").val(e.curr);
              $("#form1").submit();
            }
        }
    });
  });
   
    function cancel(){
	    var index=parent.layer.getFrameIndex(window.name);
	    parent.layer.close(index);
	    
	}
    
    function resetQuery(){
        $("#form1").find(":input").not(":button,:submit,:reset,:hidden").val("").removeAttr("checked").removeAttr("selected");
    }
    
    //显示包评审项信息
    function view(packageId,projectId){
    	window.open("${pageContext.request.contextPath}/adFirstAudit/editPackageFirstAudit.html?packageId="+packageId+"&projectId="+projectId+"&flag="+1);   
    }
    
    //引入包评审项
    function loadPackage(id){
    	var packageId = $("#oldpackageId").val();
    	var projectId = $("#oldprojectId").val();
    	var flowDefineId = "${flowDefineId}";
    	$.ajax({  
            type: "POST",  
            url: "${pageContext.request.contextPath}/adFirstAudit/saveLoadPackage.html",  
            data: {"id":id,"packageId":packageId,"projectId":projectId},  
            dataType: 'json',  
            success:function(result){
                 if(!result.success){
                     layer.msg(result.msg,{offset: ['150px']});
                 }else{
                     parent.window.setTimeout(function(){
                         parent.window.location.href = '${pageContext.request.contextPath}/adFirstAudit/editPackageFirstAudit.html?packageId='+packageId+'&projectId='+projectId;
                     }, 1000);
                     layer.msg(result.msg,{offset: ['150px']});
                 }
             },
             error: function(result){
                 layer.msg("引用 失败",{offset: ['150px']});
             }
         });
    	
    }
  </script>
  <body>
    <div class="container">
    <h2 class="search_detail">
       <form action="${pageContext.request.contextPath}/adFirstAudit/loadOtherPackage.html" method="post" class="mb0" id="form1">
        <input type="hidden" name="page" id="page">
        <input type="hidden" name="oldPackageId" id="oldpackageId" value="${oldPackageId}">
        <input type="hidden" name="oldProjectId" id="oldprojectId" value="${oldProjectId}">
        <ul class="demand_list">
          <li>
            <label class="fl">项目名称：</label><span><input type="text" name="projectName" value="${projectName}"/></span>
          </li>
          <li>
            <label class="fl">包名：</label><span><input type="text" name="packageName" value="${packageName}"/></span>
          </li>
            <button type="submit" class="btn">查询</button>
            <button type="button" onclick="resetQuery()" class="btn">重置</button>    
        </ul>
          <div class="clear"></div>
       </form>
     </h2>
     <div class="content table_box">
        
        <table class="table table-bordered table-condensed table-hover table-striped">
          <thead>
          <tr>
            <th class="w50 info">序号</th>
            <th class="info">项目名称</th>
            <th class="info">包名</th>
            <th class="info">操作</th>
          </tr>
          </thead>
          <c:forEach items="${list.list}" var="pa" varStatus="vs">
              <tr>
                  <td class="tc">${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
                  <td>${pa.projectName}</td>
                  <td class="tc">${pa.name}</td>
                  <td class="tc">
                      <button class="btn" type="button" onclick="view('${pa.id}','${pa.project.id}');">包信息</button>
                      <button class="btn" type="button" onclick="loadPackage('${pa.id}');">引入</button>
                  </td>
              </tr>
          </c:forEach>
        </table>
    </div>
   	<div id="pagediv" align="right"></div>
   </div>
  </body>
</html>
