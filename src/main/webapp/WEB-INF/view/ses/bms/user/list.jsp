<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]-->
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
	
  	function view(id){
  		window.location.href="${pageContext.request.contextPath}/user/show.html?id="+id;
  	}
  	
    function edit(){
    	var id=[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			id.push($(this).val());
		}); 
		if(id.length==1){
			var currPage = ${list.pageNum};
			window.location.href="${pageContext.request.contextPath}/user/edit.html?id="+id+"&page="+currPage;
		}else if(id.length>1){
			layer.alert("只能选择一个",{offset: '222px', shade:0.01});
		}else{
			layer.alert("请选择需要修改的用户",{offset: '222px', shade:0.01});
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
				window.location.href="${pageContext.request.contextPath}/user/delete_soft.html?ids="+ids;
			});
		}else{
			layer.alert("请选择要删除的用户",{offset: '222px', shade:0.01});
		}
    }
    
    function add(){
    	window.location.href="${pageContext.request.contextPath}/user/add.html";
    }
    
    function openPreMenu(){
		var ids =[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			ids.push($(this).val()); 
		}); 
		if(ids.length==1){
			var iframeWin;
			layer.open({
			  type: 2, //page层
			  area: ['300px', '500px'],
			  title: '配置权限',
			  closeBtn: 1,
			  shade:0.01, //遮罩透明度
			  moveType: 1, //拖拽风格，0是默认，1是传统拖动
			  shift: 1, //0-6的动画形式，-1不开启
			  offset: '110px',
			  shadeClose: false,
			  content: '${pageContext.request.contextPath}/user/openPreMenu.html?id='+ids,
			  success: function(layero, index){
			    iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
			  },
			  btn: ['保存', '关闭'] 
			  ,yes: function(){
			    iframeWin.onCheck(ids);
			  }
			  ,btn2: function(){
			    layer.closeAll();
			  }
			});
		}else if(ids.length>1){
			layer.alert("只能同时选择一个用户",{offset: '222px', shade:0.01});
		}else{
			layer.alert("请选择一个用户",{offset: '222px', shade:0.01});
		}
	
	}
	
	function resetQuery(){
		$("#form1").find(":input").not(":button,:submit,:reset,:hidden").val("").removeAttr("checked").removeAttr("selected");
	}
  </script>
  <body>
   <!--面包屑导航开始-->
	   <div class="margin-top-10 breadcrumbs ">
	      <div class="container">
			   <ul class="breadcrumb margin-left-0">
			   <li><a href="#"> 首页</a></li><li><a href="#">支撑系统</a></li><li><a href="#">后台管理</a></li><li class="active"><a href="#">用户管理</a></li>
			   </ul>
			<div class="clear"></div>
		  </div>
	   </div>
      <div class="container">
		  <div class="headline-v2">
			  <h2>用户管理</h2>
		  </div>
		     <h2 class="search_detail">
		       	<form action="${pageContext.request.contextPath}/user/list.html" id="form1" method="post" class="mb0">
		       		<input type="hidden" name="page" id="page">
			    	<ul class="demand_list">
			    	  <li>
				    	<label class="fl">用户名：</label><span><input type="text" id="loginName" value="${user.loginName }" name="loginName" class=""/></span>
				      </li>
			    	  <li>
				    	<label class="fl">姓名：</label><span><input type="text" id="relName" value="${user.relName }" name="relName" class=""/></span>
				      </li>
			    	  <li>
				    	<label class="fl">用户类型：</label>
				    	   <span>
					        <select id="typeName" name="typeName">
					        	<option value="">请选择</option>
					        	<option value="2" <c:if test="${'2' eq user.typeName}">selected</c:if>>需求人员</option>
					        	<option value="1" <c:if test="${'1' eq user.typeName}">selected</c:if>>采购人员</option>
					        	<option value="0" <c:if test="${'0' eq user.typeName}">selected</c:if>>采购管理人员</option>
					        	<option value="3" <c:if test="${'3' eq user.typeName}">selected</c:if>>其他人员</option>
					        	<option value="4" <c:if test="${'4' eq user.typeName}">selected</c:if>>供应商</option>
					        	<option value="5" <c:if test="${'5' eq user.typeName}">selected</c:if>>专家</option>
					        	<option value="6" <c:if test="${'6' eq user.typeName}">selected</c:if>>进口供应商</option>
					        	<option value="7" <c:if test="${'7' eq user.typeName}">selected</c:if>>进口代理商</option>
					        	<option value="8" <c:if test="${'8' eq user.typeName}">selected</c:if>>监督人员</option>
					        </select>
					        </span>
				      </li> 
				    	<button type="submit"  class="btn">查询</button>
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
				<button class="btn btn-windows edit" type="button" onclick="openPreMenu()">设置权限</button>
	  </div>
	  
	    <div class="content table_box">
            <table class="table table-bordered table-condensed table-hover table-striped">
            <thead>
					<tr>
					  <th class="info w30"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
					  <th class="info w50">序号</th>
					  <th class="info">用户名</th>
					  <th class="info">姓名</th>
					  <th class="info">单位</th>
					  <th class="info">联系电话</th>
					  <th class="info">类型</th>
					</tr>
		      <thead>
		      <tbody>
				<c:forEach items="${list.list}" var="user" varStatus="vs">
					<tr>
					  <td class="tc"><input onclick="check()" type="checkbox" name="chkItem" value="${user.id}" /></td>
					  <td class="tc">${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
					  <td class="tc" ><a href="#" onclick="view('${user.id}');">${user.loginName}</a></td>
					  <td class="tc">${user.relName}</td>
					  <td class="tc">${user.org.name}</td>
					  <td class="tc">${user.telephone}</td>
					  <td class="tc">
						<c:if test="${user.typeName == 0}">
							采购管理人员
						</c:if>
						<c:if test="${user.typeName == 1}">
							采购机构人员
						</c:if>
						<c:if test="${user.typeName == 2}">
							需求人员
						</c:if>
						<c:if test="${user.typeName == 3}">
							其他人员
						</c:if>
						<c:if test="${user.typeName == 4}">
							供应商
						</c:if>
						<c:if test="${user.typeName == 5}">
							专家
						</c:if>
						<c:if test="${user.typeName == 6}">
							进口供应商
						</c:if>
						<c:if test="${user.typeName == 7}">
							进口代理商
						</c:if>
						<c:if test="${user.typeName == 8}">
							监督人员
						</c:if>
					  </td>
					</tr>
				</c:forEach>
				</tbody>
		       </table>
		    </div>
		  <div id="pagediv" align="right"></div>
	  </div>
  </body>
</html>
