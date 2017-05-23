<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
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
			    groups: "${list.pages}">=5?5:"${list.pages}", //连续显示分页数
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
			var currPage = ${list.pageNum};
			window.location.href="${pageContext.request.contextPath}/dictionaryData/edit.html?id="+id+"&page="+currPage;
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
				window.location.href="${pageContext.request.contextPath}/dictionaryData/deleteSoft.html?ids="+ids;
			});
		}else{
			layer.alert("请选择",{offset: '222px', shade:0.01});
		}
    }
    
    function add(){
    	var kind = $("#kind").val();
    	window.location.href="${pageContext.request.contextPath}/dictionaryData/add.html?kind="+kind;
    }
    
	function resetQuery(){
		$("#form1").find(":input").not(":button,:submit,:reset,:hidden").val("").removeAttr("checked").removeAttr("selected");
	}
  </script>
  </head>
  <body>
   <!--面包屑导航开始-->
	   <div class="margin-top-10 breadcrumbs ">
	      <div class="container">
			   <ul class="breadcrumb margin-left-0">
				   <li><a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')" target="_blank"> 首页</a></li>
				   <li><a href="javascript:void(0);">支撑系统</a></li>
				   <li><a href="javascript:void(0);">后台管理</a></li>
				   <li class="active"><a href="javascript:void(0);">数据字典</a></li>
			   </ul>
			<div class="clear"></div>
		  </div>
	   </div>
      <div class="container">
		  <div class="headline-v2">
            <h2>数据字典</h2>
          </div> 
		  
		  <h2 class="search_detail">
		       	<form action="${pageContext.request.contextPath}/dictionaryData/list.html" id="form1" method="post" class="mb0">
			    	<input name="kind" type="hidden" id="kind" value="${dd.kind}">
			    	<input type="hidden" name="page" id="page">
			    	<ul class="demand_list">
			    	  <li class="fl">
				    	<label class="fl">编码：</label><span><input type="text" id="code" value="${dd.code }" name="code" class=""/></span>
				      </li>
				      <li class="fl">
				    	<label class="fl">名称：</label><span><input type="text" id="name" value="${dd.name }" name="name" class=""/></span>
				      </li>
			    	  <%-- <li class="fl">
				    	<label class="fl">描述：</label><span><input type="text" id="description" value="${dd.description }" name="description" class=""/></span>
				      </li> --%>
				    	<button type="submit" class="btn">查询</button>
				    	<button type="button" onclick="resetQuery()" class="btn">重置</button>  	
			    	</ul>
		    	  	<div class="clear"></div>
		        </form>
		     </h2>

   	  <!-- 表格开始-->
	  <div class="col-md-12 pl20 mt10">
			    <button class="btn btn-windows add" type="button" onclick="add()">新增</button>
				<button class="btn btn-windows delete" type="button" onclick="del();">删除</button>
	  </div>
   
	  <div class="content table_box">
        <table class="table table-bordered table-condensed table-hover">
				<thead>
					<tr>
					  <th class="info w30"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
					  <th class="info w50">序号</th>
					  <th class="info">编码</th>
					  <th class="info">名称</th>
					</tr>
				</thead>
				<c:forEach items="${list.list}" var="dd" varStatus="vs">
					<tr>
					  <td class="tc"><input onclick="check()" type="checkbox" name="chkItem" value="${dd.id}" /></td>
					  <td class="tc">${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
					  <td class="tc" >${dd.code}</td>
					  <td class="tc">${dd.name}</td>
					</tr>
				</c:forEach>
		       </table>
		    </div>
		  <div id="pagediv" align="right"></div>
	 </div>
  </body>
</html>
