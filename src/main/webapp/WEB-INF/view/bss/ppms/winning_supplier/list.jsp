<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
		 var checklist = document.getElementsByName("chkItem");
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
	function check(check){
	var bo = check.checked;
	if(bo==true){
		/**获取选中的值 */
		$(check).attr("checked","true");
		var ck=$(check).parent().parent().prev().find("td:eq(0)").html();
		var Ranking= $(check).parent().parent().find("td:eq(5)").text();
        if(Ranking!=1){
	        if($(ck).attr("checked")){
	            
	        }else{
	        	var iframeWin;
	        	layer.open({
	                type: 2,
	                title: '上传',
	                shadeClose: false,
	                shade: 0.01,
	                area: ['367px', '180px'], //宽高
	                content: '${pageContext.request.contextPath}/winningSupplier/upload.html',
	                success: function(layero, index){
	                    iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
	                  },
	                  cancel: function(){ 
	                	  $(check).removeAttr("checked"); 
                          layer.closeAll();
	                	},    
	                btn: ['保存', '关闭'] 
	                    ,yes: function(){
// 	                        iframeWin.add();
	                    	   layer.closeAll();
	                    }
	                    ,btn2: function(){
	                    	  $(check).removeAttr("checked"); 
	                      layer.closeAll();
	                    }//iframe的url
	              });
	        }
		}
		
	}else{
		$(check).removeAttr("checked"); 
	}
	
// 		 var count=0;
// 		 var checklist = document.getElementsByName ("chkItem");
// 		 var checkAll = document.getElementById("checkAll");
// 		 for(var i=0;i<checklist.length;i++){
// 			   if(checklist[i].checked == false){
// 				   checkAll.checked = false;
// 				   break;
// 			   }
// 			   for(var j=0;j<checklist.length;j++){
// 					 if(checklist[j].checked == true){
// 						   checkAll.checked = true;
// 						   count++;
// 					   }
// 				 }
// 		   }
// 	}
  
    function save(){
    	var id=[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			id.push($(this).val());
		}); 
		 if(id.length>=1){
			  $.post("{pageContext.request.contextPath}/winningSupplier/updateBid.do?id="+id,{email:$('#email').val(),address:$('#address').val()},
                      function(data){
                      window.location.href="{pageContext.request.contextPath}/winningSupplier/template.do?projectId=${projectId}";
				  
                      },
                      "json");
		}else{
			layer.alert("请选择供应商",{offset: ['200px', '390px'], shade:0.01});
		}
    }
   
</script>
<body>
    <div class="col-md-12 p0">
      <ul class="flow_step">
                             <li class="active">
                               <a  href="${pageContext.request.contextPath}/winningSupplier/selectSupplier.html?projectId=${projectId}" >01、确认中标供应商</a>
                               <i></i>
                             </li>
                             <li >
                               <a  href="${pageContext.request.contextPath}/template.do?projectId=${projectId}" >02、中标通知书</a>
                               <i></i>                            
                             </li>
                             <li>
                               <a  href="${pageContext.request.contextPath}/winningSupplier/notTemplate.do?projectId=${projectId}">03、未中标通知书</a>
                             </li>
                           </ul>
      </div>
	<div class="container">
        <div class="headline-v2">
            <h2>专家抽取包列表</h2>
        </div>
        <c:if test="${execute != 'SCCUESS' }">
          <div class="col-md-12 pl20 mt10">
             <button class="btn btn-windows add" onclick="addexp();" type="button">添加专家</button>
             <button class="btn " onclick="addLeader();" type="button">分配组长</button>
             <button class="btn " onclick="finish();" type="button">执行完成</button>
          </div>
        </c:if>
        <div class="content table_box">
            <table class="table table-bordered table-condensed table-hover table-striped">
                <thead>
                <tr>
                  <th class="info w30"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
                  <th class="w50 info">序号</th>
                  <th class="info">包名</th>
                  <th class="info">中标供应商信息</th>
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
                        <td class="tc"><a href="${pageContext.request.contextPath}/packageExpert/showExpert.html?packageId=${pack.id}&&flowDefineId=${flowDefineId}&&execute=${execute}"></td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</body>
</html>
