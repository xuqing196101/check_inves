<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
<script type="text/javascript">
  
  /*分页  */
  $(function(){
	  laypage({
		    cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
		    pages: "${info.pages}", //总页数
		    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
		    skip: true, //是否开启跳页
		    total: "${info.total}",
		    startRow: "${info.startRow}",
		    endRow: "${info.endRow}",
		    groups: "${info.pages}">=3?3:"${info.pages}", //连续显示分页数
		    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
//			        var page = location.search.match(/page=(\d+)/);
//			        return page ? page[1] : 1;
				return "${info.pageNum}";
		    }(), 
		    jump: function(e, first){ //触发分页后的回调
		            if(!first){ //一定要加此判断，否则初始时会无限刷新
		        //	$("#page").val(e.curr);
		        	// $("#form1").submit();
		        	
		         location.href = '${pageContext.request.contextPath}/purchaser/list.do?page='+e.curr;
		        }  
		    }
		});
  });
  
  
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
	function up(obj,id,position){
		var tr=$(obj).parent().parent().prev();

		 var val= $(tr).children(":last").children().val();
		 if(val!=null){
			  window.location.href="${pageContext.request.contextPath}/view/update.html?sid="+val+"&&xid="+id+"&&postion="+position;
		 }else{
			 alert("已经是最高的");
		 }
		
		 }
	function down(obj,id){
		var tr=$(obj).parent().parent().next();

		 var val= $(tr).children(":last").children().val();
		 if(val!=null){
			  window.location.href="${pageContext.request.contextPath}/view/update.html?xid="+val+"&&sid="+id;
		 }else{
			 alert("已经是最下面的");
		 }
	}
	
 function viewdetail(){
		var id=[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			id.push($(this).val());
		}); 
		if(id.length==1){
			
			window.location.href="${pageContext.request.contextPath}/view/detail.html?id="+id;
		}else{
			layer.alert("请选择一个查看",{offset: ['222px', '390px'], shade:0.01});
		}
		
 }
  </script>
  </head>
  
  <body>
<!--面包屑导航开始-->


 <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0);"> 首页</a></li><li><a href="javascript:void(0);">保障作业系统</a></li><li><a href="javascript:void(0);">采购计划管理</a></li><li class="active"><a href="javascript:void(0);">采购计划查看</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
<!-- 录入采购计划开始-->
<div class="container">
 <div class="col-md-12 pl20 mt10">
  
 
		<input type="button"  class="btn" onclick="viewdetail()" value="需求单位查看">
	
		<!-- <button   class="btn" >历史记录</button> -->
 </div>
   <div class="content  table_box">
        <table class="table table-bordered table-condensed table-hover table-striped">
		<thead>
		<tr>
		  <th class="info w30"><input type="checkbox" id="checkAll" onclick="selectAll()"  alt=""></th>
		  <th class="info w50">序号</th>
		  <th class="info">需求部门名称</th>
		  <th class="info">采购总金额</th>
		  <th class="info">汇总时间</th>
		  <th class="info">状态</th>
		    <th class="info">操作</th>
		</tr>
		</thead>
		<c:forEach items="${info.list}" var="obj" varStatus="vs">
			<tr style="cursor: pointer;">
			  <td class="tc w30"><input type="checkbox" value="${obj.planNo }" name="chkItem" onclick="check()"  alt=""></td>
			  <td class="tc w50"   >${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
			  
			  <td class="tl pl20"  >${obj.department }</td>
			
			
			  <td class="tr pr20"  ><fmt:formatNumber>${obj.budget }</fmt:formatNumber> </td>
			    <td class="tc"  ><fmt:formatDate value="${obj.createdAt }"/></td>
			  <td class="tl pl20"  >
			  <c:if test="${obj.status=='1' }">
			   未下达
			  </c:if>
			      <c:if test="${obj.status=='2' }">
			  		 已审核
			  </c:if>
			     <c:if test="${obj.status=='3' }">
			  		   已下达
			  </c:if>
			  </td>
			  
			  <td class="tc">
			  <input type="hidden" value="${obj.id}"/>
			  <a onclick="up(this,'${obj.id}','${obj.position}')">  上移</a><a onclick="down(this,'${obj.id}')">下移</a> 
			  
			  </td>
			</tr>
	 
		 </c:forEach>
		 

      </table>
      
      <div id="pagediv" align="right"></div>
   </div>


</div>
 
 
	 </body>
</html>
