<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>代办管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  <script src="<%=basePath%>public/layer/layer.js"></script>
    <script src="<%=basePath%>public/laypage-v1.3/laypage/laypage.js"></script>
  <script type="text/javascript">
  $(function(){
	  laypage({
		    cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
		    pages: "${getListSupplier.pages}", //总页数
		    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
		    skip: true, //是否开启跳页
		    groups: "${getListSupplier.pages}">=3?3:"${getListSupplier.pages}", //连续显示分页数
		    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
		        var page = location.search.match(/page=(\d+)/);
		        return page ? page[1] : 1;
		    }(), 
		    jump: function(e, first){ //触发分页后的回调
		        if(!first){ //一定要加此判断，否则初始时会无限刷新
		            location.href = '<%=basePath%>supplierAgents/listSupplierAgents.do?page='+e.curr+'&&type='+${type};
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
  		window.location.href="<%=basePath%>user/view.html?id="+id;
  	}
    function edit(){
    	var id=[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			id.push($(this).val());
		}); 
		if(id.length==1){
			
			window.location.href="<%=basePath%>user/edit.html?id="+id;
		}else if(id.length>1){
			layer.alert("只能选择一个",{offset: ['222px', '390px'], shade:0.01});
		}else{
			layer.alert("请选择需要修改的用户",{offset: ['222px', '390px'], shade:0.01});
		}
    }
    function del(){
    	var ids =[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			ids.push($(this).val()); 
		}); 
		if(ids.length>0){
			layer.confirm('您确定要删除吗?', {title:'提示',offset: ['222px','360px'],shade:0.01}, function(index){
				layer.close(index);
				window.location.href="<%=basePath%>supplierAgents/deleteSoftSupplierAgents.do?ids="+ids+'&&type='+${type};
			});
		}else{
			layer.alert("请选择要删除的用户",{offset: ['222px', '390px'], shade:0.01});
		}
    }
    function add(){
    	window.location.href="<%=basePath%>user/add.html";
    }
  </script>
  <body>
	<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">支撑系统</a></li><li><a href="#">后台管理</a></li><li class="active"><a href="#">代办管理</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   <div class="container">
	   <div class="headline-v2">
	   		<h2>代办管理</h2>
	   </div>
   </div>
<!-- 表格开始-->
   <div class="container">
   <div class="col-md-8">
<!--     <button class="btn btn-windows add" type="button" onclick="add()">新增</button> -->
<!-- 	<button class="btn btn-windows edit" type="button" onclick="edit()">修改</button> -->
	<button class="btn btn-windows delete" type="button" onclick="del();">删除</button>
	</div>
            <div class="col-md-4 ">
              <div class="search-block-v2">
                <div class="">
                  <form accept-charset="UTF-8" action="" method="get"><div style="display:none"><input name="utf8" value="✓" type="hidden"></div>
                    <input id="t" name="t" value="search_products" type="hidden">
                    <div class="col-md-12 pull-right">
                      <div class="input-group">
                        <input class="form-control bgnone h37 p0_10" id="k" name="k" placeholder="" type="text">
                        <span class="input-group-btn">
                          <input class="btn-u" name="commit" value="搜索" type="submit">
                        </span>
                      </div>
                    </div>
                  </form>               
			   </div>
              </div>
            </div>	
    </div>
   
   <div class="container margin-top-5">
     <div class="content padding-left-25 padding-right-25 padding-top-5">
        <table class="table table-bordered table-condensed">
		<thead>
		<tr>
		  <th class="info w30"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
		  <th class="info w50">序号</th>
		   <th class="info">标题</th>
		  <th class="info">操作人</th>
		  <th class="info">代办人</th>
		  <th class="info">代办类型</th>
		  <th class="info">结果</th>
		  <c:if test="${type==0}">
		   <th class="info">操作</th>
		  </c:if>
		</tr>
		</thead>
		<c:forEach items="${getListSupplier.list}" var="agents" varStatus="vs">
			<tr>
				  <td class="tc">
				  <c:choose>
				  	<c:when test="${id!=null&&agents.id==id}">
				  			  <input onclick="check()" type="checkbox" checked="checked" name="chkItem" value="${agents.id}" />
				  	</c:when>
				  	<c:otherwise>
				  			  <input onclick="check()" type="checkbox" name="chkItem" value="${agents.id}" />
				  	</c:otherwise>
				  </c:choose>
				  
				  </td>
				  <td class="tc">${(vs.index+1)+(getListSupplier.pageNum-1)*(getListSupplier.pageSize)}</td>
				  <td class="tc w100">${agents.title}</td>
				  <td class="tc">${agents.operatorName}</td>
				  <td class="tc">${agents.usersName}</td>
				  <td class="tc">
				  <c:if test="${agents.undoType==1}">
				 	未审核
				  </c:if>
				   <c:if test="${agents.undoType==2}">
					  已审核
				  </c:if>
				   <c:if test="${agents.undoType==3}">
					 审核中
				  </c:if>
				  </td>
				  <td class="tc">${agents.result}</td>
				 <c:if test="${type==0}">
				  <td class="tc">
				  	<a href="supplierAgents/updateIsReminders.do?type=0&id=${agents.id}" class="btn btn-windows">加急</a>
				  </td>
				 </c:if>
			</tr>
		</c:forEach>
        </table>
     </div>
     <div id="pagediv" align="right">
     </div>
   </div>
  </body>
</html>
