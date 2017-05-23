<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
  </head>
  <script type="text/javascript">
	  $(function(){
		  laypage({
			    cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
			    pages: "${info.pages}", //总页数
			    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
			    skip: true, //是否开启跳页
			    total: "${info.total}",
			    startRow: "${info.startRow}",
			    endRow: "${info.endRow}",
			    groups: "${info.pages}">=5?5:"${info.pages}", //连续显示分页数
			    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
			        var page = location.search.match(/page=(\d+)/);
			        return page ? page[1] : 1;
			    }(), 
			    jump: function(e, first){ //触发分页后的回调
			        if(!first){ //一定要加此判断，否则初始时会无限刷新
			        	$("#page").val(e.curr);
			        	  $("#param_form").submit();
			           <%--  window.location.href = '${pageContext.request.contextPath}/dictionaryData/list.html?page='+e.curr; --%>
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
	
    function edit(){
    	var id=[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			id.push($(this).val());
		}); 
		if(id.length==1){
			var currPage = ${info.pageNum};
			window.location.href="${pageContext.request.contextPath}/param/edit.html?id="+id+"&page="+currPage;
		}else if(id.length>1){
			layer.alert("只能选择一个",{offset: '222px', shade:0.01});
		}else{
			layer.alert("请选择",{offset: '222px', shade:0.01});
		}
    }
    
    function del(){
    	var ids =[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			ids.push($(this).val()); 
		}); 
		if(ids.length>0){
			layer.confirm('您确定要删除吗?', {title:'提示',offset: '222px',shade:0.01}, function(index){
				layer.close(index);
				window.location.href="${pageContext.request.contextPath}/param/deletes.html?ids="+ids;
			});
		}else{
			layer.alert("请选择",{offset: '222px', shade:0.01});
		}
    }
    
    function add(){
    	window.location.href="${pageContext.request.contextPath}/param/add.html";
    }
    
	function query(){
		$("#param_form").submit();
	}
	
	function resetQuery(){
		$("#param_form").find(":input").not(":button,:submit,:reset,:hidden").val("").removeAttr("checked").removeAttr("selected");
	}
  </script>
  <body>
   <!--面包屑导航开始-->
	   <div class="margin-top-10 breadcrumbs ">
	      <div class="container">
			   <ul class="breadcrumb margin-left-0">
			   <li><a href="javascript:void(0);"> 首页</a></li><li><a href="javascript:void(0);">支撑系统</a></li><li><a href="javascript:void(0);">后台管理</a></li><li class="active"><a href="javascript:void(0);">审核参数维护</a></li>
			   </ul>
			<div class="clear"></div>
		  </div>
	   </div>
      <div class="container">
		  <div class="headline-v2">
			  <h2>数据字典</h2>
		  </div>

		     <h2 class="search_detail">
		       	<form id="param_form" action="${pageContext.request.contextPath }/param/list.html"  method="post" class="mb0">
		       	<input type="hidden" name="page" id="page">
			    	<ul class="demand_list">
			    	  <li>
				    	<label class="fl">审核轮次：</label><span>
				    	<select name="dictioanryId" >
				    		<option value="">请选择</option>
				    		<c:forEach items="${dic }" var="obj">
				    		
				    			<option value="${obj.id }" <c:if test="${ obj.id==auditParam.dictioanryId}"> selected="selected"</c:if> >${obj.name }</option>
				    		</c:forEach>
				    	</select>
				    	
				    	</span>
				      </li>
				   
				    	<button type="button" onclick="query()" class="btn">查询</button>
				    	<button type="button" onclick="resetQuery()" class="btn">重置</button>  	
			    	</ul>
		    	  	<div class="clear"></div>
		        </form>
		     </h2>
	   	 
  
   	  <!-- 表格开始-->
 
		  <div class="col-md-12 pl20 mt10">
			    <button class="btn btn-windows add" type="button" onclick="add()">新增</button>
				<button class="btn btn-windows edit" type="button" onclick="edit()">修改</button>
				<button class="btn btn-windows delete" type="button" onclick="del();">删除</button>
			</div>
 
   
	 
		  <div class="content table_box">
		       <table class="table table-striped table-bordered table-hover">
				<thead>
					<tr>
					  <th class="info w30"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
					  <th class="info w50">序号</th>
					  <th class="info">审核轮次</th>
					  <th class="info">审核参数</th>
			
					</tr>
				</thead>
				<c:forEach items="${info.list}" var="p" varStatus="vs">
					<tr>
					  <td class="tc"><input onclick="check()" type="checkbox" name="chkItem" value="${p.id}" /></td>
					  <td class="tc">${(vs.index+1)+(info.pageNum-1)*(info.pageSize)}</td>
					  <td class="tl" >
							<c:forEach items="${dic }" var="obj">
				    		<c:if test="${obj.id==p.dictioanryId }">
				    		 ${obj.name }
				    		</c:if>
				    		 
				    		 
				    		</c:forEach>
					   
					  
					  </td>
					  <td class="tc">
					  <c:if test="${p.param=='1'}">
					  	采购方式
					  </c:if>
					   <c:if test="${p.param=='2'}">
					  	采购机构
					  </c:if>
					
					   <c:if test="${p.param=='3'}">
							 技术参数意见
					  </c:if>
					    <c:if test="${p.param=='4'}">
					  	其他建议
					  </c:if>
					  </td>
			
					</tr>
				</c:forEach>
		       </table>
		    </div>
		  <div id="pagediv" align="right"></div>
	  </div>
  </body>
</html>
