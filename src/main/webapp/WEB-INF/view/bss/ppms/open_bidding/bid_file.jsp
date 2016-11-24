<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="/WEB-INF/view/common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>My JSP 'bid_file.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
    <script type="text/javascript">
	function submit1(){
		
		var name = $("#name").val();
		if(!name){
			layer.tips("请填写名称", "#name");
			return ;
		}
		var id=[]; 
		$('input[name="kind"]:checked').each(function(){ 
			id.push($(this).val());
		}); 
		if(id.length==0){
			layer.tips("请选择类型", "#kind");
			return ;
		}
		$("#form1").submit();
	}
	 var index;
	function cancel(){
	   layer.close(index);
	}
	function openWindow(){
		index = layer.open({
	          type: 1, //page层
	          area: ['300px', '250px'],
	          title: '手动添加初审项',
	          shade:0.01, //遮罩透明度
	          moveType: 1, //拖拽风格，0是默认，1是传统拖动
	          shift: 1, //0-6的动画形式，-1不开启
	          offset: ['100px', '100px'],
	          shadeClose: true,
	          content:$('#openWindow') //数组第二项即吸附元素选择器或者DOM $('#openWindow')
		 });
	}
	function remove(){
		var count = 0;
	  	  var ids = document.getElementsByName("chkItem");
	  	 var id2="";
	 	 var num =0;
	      for(i=0;i<ids.length;i++) {
	    		 if(document.getElementsByName("chkItem")[i].checked){
		    		  id2 += document.getElementsByName("chkItem")[i].value+",";
		    		  num++;
	    		  }
	    		 //id.push(document.getElementsByName("check")[i].value);
	       		 count++;
	     }
	   	var id = id2.substring(0,id2.length-1);
	   	if(num>0){
		layer.confirm('您确定要删除吗?', {title:'提示',offset: ['222px','360px'],shade:0.01}, function(index){
	 			layer.close(index);
	 			$.ajax({
	 				url:"${pageContext.request.contextPath}/firstAudit/remove.html?id="+id,
	 				//data:{"id":id},
	 				//type:"post",
	 	       		success:function(){
	 	       			layer.msg('删除成功',{offset: ['222px', '390px']});
	 		       		window.setTimeout(function(){
	 		       			window.location.reload();
	 		       		}, 500);
	 	       		},
	 	       		error: function(){
	 					layer.msg("删除失败",{offset: ['222px', '390px']});
	 				}
	 	       	});
	 		});
   		}else{
   			layer.alert("请选择一条记录",{offset: ['222px', '390px'],shade:0.01});
		}
	}
	function edit(){
		var count = 0;
	  	  var ids = document.getElementsByName("chkItem");
	   
	       for(i=0;i<ids.length;i++) {
	     		 if(document.getElementsByName("chkItem")[i].checked){
	     		 var id = document.getElementsByName("chkItem")[i].value;
	     		//var value = id.split(",");
	     		 count++;
	      }
	    }   
	    		if(count>1){
	    			layer.alert("只能选择一条记录",{offset: ['222px', '390px'],shade:0.01});
	    		}else if(count<1){
	    			layer.alert("请选择一条记录",{offset: ['222px', '390px'],shade:0.01});
	    		}else if(count==1){
		  layer.open({
	          type: 2, //page层
	          area: ['300px', '250px'],
	          title: '修改初审项',
	          shade:0.01, //遮罩透明度
	          moveType: 1, //拖拽风格，0是默认，1是传统拖动
	          shift: 1, //0-6的动画形式，-1不开启
	          offset: ['100px', '100px'],
	          closeBtn: 1,
	          content:'${pageContext.request.contextPath}/firstAudit/toEdit.html?id='+id
	        		  //数组第二项即吸附元素选择器或者DOM $('#openWindow')
		 });
	   }
	}
	//打开模板窗口列表
	function openTemplat(){
		layer.open({
	          type: 2, //page层
	          area: ['700px', '400px'],
	          title: '选择模板',
	          shade:0.01, //遮罩透明度
	          moveType: 1, //拖拽风格，0是默认，1是传统拖动
	          shift: 1, //0-6的动画形式，-1不开启
	          offset: ['100px', '100px'],
	          closeBtn: 1,
	          content:'${pageContext.request.contextPath}/firstAudit/toTemplatList.html'
	        		  //数组第二项即吸附元素选择器或者DOM $('#openWindow')
		 });
		
	}
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
        
        function jump(url){
      	    $("#open_bidding_main").load(url);
        }
</script>
  </head>
  
  <body>
	                     <div class="col-md-12 p0">
						   <ul class="flow_step">
						     <li class="active">
							   <a  href="${pageContext.request.contextPath}/firstAudit/toAdd.html?projectId=${projectId}&flowDefineId=${flowDefineId}" >01、符合性</a>
							   <i></i>
							 </li>
							 
							 <li>
							   <a  href="${pageContext.request.contextPath}/firstAudit/toPackageFirstAudit.html?projectId=${projectId}&flowDefineId=${flowDefineId}" >02、符合性关联</a>
							   <i></i>							  
							 </li>
						     <li>
							   <a  href="${pageContext.request.contextPath}/intelligentScore/packageList.html?projectId=${projectId}&flowDefineId=${flowDefineId}">03、评标细则</a>
							   <i></i>
							 </li>
							 <li>
							   <a  href="${pageContext.request.contextPath}/open_bidding/bidFile.html?id=${projectId}&flowDefineId=${flowDefineId}" >
							     <c:if test="${project.dictionary.code eq 'GKZB' }">
							     04、招标文件
							     </c:if>
							     <c:if test="${project.dictionary.code eq 'XJCG' }">
							     04、询价文件
							     </c:if>
							     <c:if test="${project.dictionary.code eq 'YQZB' }">
							     04、招标文件
							     </c:if>
							     <c:if test="${project.dictionary.code eq 'JZXTP' }">
							     04、竞谈文件
							     </c:if>
							     <c:if test="${project.dictionary.code eq 'DYLY' }">
							     04、单一来源文件
							     </c:if>
							   </a>
							 </li>
						   </ul>
						 </div>
<div class="tab-content clear step_cont">
	<!--第一个  -->
	<div class="col-md-12 tab-pane active"  id="tab-1">
	 <div class="headline-v2">
   <h2>初审项定义</h2>
   </div>
	  <form action="">
	  <c:if test="${project.confirmFile != 1}">
	  <input type="button" value="选择模板" onclick="openTemplat();" class="btn btn-windows add"/>
	  <input type="button" value="添加" onclick="openWindow();" class="btn btn-windows add"/>
	  <input type="button" value="修改" class="btn btn-windows edit" onclick="edit();">
	  <input type="button" value="删除" class="btn btn-windows delete" onclick="remove();">
	  </c:if>
	    <table class="table table-bordered table-condensed table-hover table-striped">
	    <thead>
	      <tr>
	      <c:if test="${project.confirmFile != 1}">
	      	<th class="info w30"><input type="checkbox" id="checkAll" onclick="selectAll()"  alt=""></th>
	      </c:if>
	        <th class="info">初审项名称</th>
	        <th class="info">要求类型</th>
	        <th class="info">创建人</th>
	        <th class="info">创建时间</th>
	      </tr>
	     </thead>
	      <c:forEach items="${list }" var="l" varStatus="vs">
	       <tr>
	       <c:if test="${project.confirmFile != 1}">
	       	<td class="tc w30"><input type="checkbox" value="${l.id }" name="chkItem"   alt=""></td>
	       </c:if>
	        <td align="center">${l.name }</td>
	        <td align="center">${l.kind }</td>
	        <td align="center">${l.creater }</td>
	        <td align="center"><fmt:formatDate type='date' value='${l.createdAt }' dateStyle="default" pattern="yyyy-MM-dd"/></td>
	      </tr>
	      </c:forEach>
	    </table>
	  </form>
	<!-- 按钮 -->
	  	<div class="padding-top-10 clear">
			<div class="col-md-12 pl200 ">
				<div class="mt40 tc mb50">
				</div>
		  	</div>
		 </div>	
		 <!--打开的窗口  -->
			<div id="openWindow"  style="display: none;">
				<form action="${pageContext.request.contextPath}/firstAudit/add.html" method="post" id="form1">
				   <ul class="list-unstyled">
	                <li class="mt10 col-md-12 p0">
	                  <label class="col-md-12 pl20">初审项名称</label>
	                  <span class="col-md-12">
	                    <input type="text" required="true" maxlength="30" name="name" id="name">
	                  </span>
	                </li>
	                <li class="mt10 col-md-12 p0">
	                  <label class="col-md-12 pl20">要求类型</label>
	                  <span class="col-md-12">
	                   <input type="radio"  name="kind" value="商务" >商务&nbsp;<input type="radio" name="kind" id="kind" value="技术" >技术
	                  </span>
	                </li>
                <div class="clear"></div>
                 <input name="creater" required="true" maxlength="10" id="creater" type="hidden" value="${sessionScope.loginUser.relName}">
			      <input type="hidden" name="projectId" id="projectId" value="${projectId }">
               </ul>
			    <input type="button"  value="添加" onclick="submit1();" class="btn btn-windows add"/>
			    <input type="button"  value="取消" onclick="cancel();"  class="btn btn-windows cancel"/>
			  </form>
			</div>
	  </div>
    </div>
  </body>
</html>
