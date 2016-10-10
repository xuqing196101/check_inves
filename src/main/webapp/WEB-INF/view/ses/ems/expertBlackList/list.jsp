<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>专家黑名单</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/supplier/css/supplier.css" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/laypage-v1.3/laypage/laypage.js"></script>
<script type="text/javascript">
    $(function(){
      laypage({
          cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
          pages: "${result.pages}", //总页数
          skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
          skip: true, //是否开启跳页
          total: "${result.total}",
          startRow: "${result.startRow}",
          endRow: "${result.endRow}",
          groups: "${result.pages}">=3?3:"${result.pages}", //连续显示分页数
          curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
          return "${result.pageNum}";
          }(), 
          jump: function(e, first){ //触发分页后的回调
              if(!first){ //一定要加此判断，否则初始时会无限刷新
                $("#page").val(e.curr);
                $("#form1").submit();
              }
          }
      });
    });

  
</script>
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

	/**添加页面*/
	function add(){
		window.location.href="<%=basePath%>expert/addBlacklist.html";
	}
	
	//更新
   function update(){
    	var id=[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			id.push($(this).val());
		}); 
		if(id.length==1){
			window.location.href="<%=basePath%>expert/editBlacklist.html?id="+id;
		}else if(id.length>1){
			layer.alert("只能选择一个",{offset: ['222px', '390px'], shade:0.01});
		}else{
			layer.alert("请选择需要修改的信息",{offset: ['222px', '390px'], shade:0.01});
		}
    }
    
    //删除
  function del(){
		var ids =[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			ids.push($(this).val()); 
		}); 
		if(ids.length>0){
			layer.confirm('您确定要删除吗?', {title:'提示',offset: ['222px','360px'],shade:0.01}, function(index){
				layer.close(index);
				$.ajax({
		       		url:"<%=basePath%>expert/deleteBlacklist.html",
		       		data:"ids="+ids,
		       		success:function(){
		       			layer.msg("删除成功",{offset: ['222px', '390px']});
			       		window.setTimeout(function(){
			       			location.href = "<%=basePath%>expert/blacklist.html";
			       		}, 1000);
		       		},
		       		error: function(message){
						layer.msg("删除失败",{offset: ['222px', '390px']});
					}
		       	});
			});
		}else{
			layer.alert("请选择要删除的信息",{offset: ['222px', '390px'], shade:0.01});
		}
    }
</script>
</head>
<body>

<!--面包屑导航开始-->
	<div class="margin-top-10 breadcrumbs ">
	  <div class="container">
	    <ul class="breadcrumb margin-left-0">
	      <li><a href="#"> 首页</a></li><li><a href="#">评审专家管理</a></li><li><a href="#">不良专家名单管理</a></li><li class="active"><a href="#">不良专家名单</a></li>
	    </ul>
	  </div>
	</div>
	<!-- 搜索 -->
	<div class="container">
    <div style="padding-left: 20px;">
	    <form action="<%=basePath %>expert/blacklist.html"  method="post" id="form1" enctype="multipart/form-data" class="registerform"> 
	      <input type="hidden" name="page" id="page">
	        <div align="center">
	          <ul class="demand_list list-unstyled">
	            <li>
                <label class="fl mt10">专家姓名：</label>
                  <select name="relName" class="mb0 mt5" >
			              <option value="">请选择</option>
			              <c:forEach var="expert"  items="${expertName}">
			              <option value="${expert.relName}">${expert.relName}</option>
			              </c:forEach>
                  </select> 
	           </li>
	           <li>
               <label class="fl mt10">处罚方式：</label>
	               <select name="punishDate" class="mb0 mt5" >
			            <option value="">请选择</option>
										<option value="3个月">3月</option>
										<option value="6个月">6个月</option>
										<option value="一年">一年</option>
										<option value="两年">两年</option>
										<option value="三年">三年</option>
								</select>
	          </li>
	          <li>
              <label class="fl mt10" >处罚时限：</label>
						    <select name="punishType" class="mb0 mt5" >
		            <option value=''>-请选择-</option>
							  <option value="1">警告</option>
							  <option value="2">严重警告</option>
							  <option value="3">取消资格</option>
							</select>
				   </li>
           <li>
             <input type="submit" class="btn btn_back fl ml10 mt6" value="查询" />
             <!-- <input type="button" class="btn btn_back fl ml10 mt6" value="重置" onclick="resetForm()"> -->
          </li>
	    </ul>
	  </div>
	</form>
	</div>
	</div>
	<!-- 表格开始-->
	<div class="container">
	  <div class="col-md-8">
	 	  <button class="btn btn-windows add" type="button" onclick="add();">新增</button>
	    <button class="btn btn-windows edit" type="button" onclick="update();">修改</button>
	    <button class="btn btn-windows delete" type="button" onclick="del();">删除</button>
	  </div>
	</div>
  <div class="container margin-top-5">
    <div class="content padding-left-25 padding-right-25 padding-top-5">
      <table class="table table-bordered table-condensed">
        <thead>
          <tr>
						<th class="info w30"><input type="checkbox" onclick="selectAll();"  id="checkAll"></th>
					  <th class="info w50">序号</th>
					  <th class="info">姓名</th>
					  <th class="info">入库时间</th>
					  <th class="info">处罚日期</th>
					  <th class="info">处罚时限</th>
					  <th class="info">处罚方式</th>
					  <th class="info">处罚理由</th>
          </tr>
        </thead>
         <c:forEach items="${expertList }" var="e" varStatus="s">
	        <tr>
	          <td class="tc w30"><input type="checkbox" value="${e.id }" name="chkItem" onclick="check()"></td>
							<td class="tc w50">${s.count}</td>
							<td class="tc">${e.relName }</td>
							<td class="tc"><fmt:formatDate type='date' value='${e.storageTime }' dateStyle="default" pattern="yyyy-MM-dd"/></td>
							<td class="tc"><fmt:formatDate type='date' value='${e.dateOfPunishment }' dateStyle="default" pattern="yyyy-MM-dd"/></td>
							<td class="tc">${e.punishDate }</td>
							<td class="tc">
								<c:if test="${e.punishType == 1}">警告</c:if>
								<c:if test="${e.punishType == 2}">严重警告</c:if>
								<c:if test="${e.punishType == 3}">取消资格</c:if>
							</td>
	          <td class="tc">${e.reason }</td>
	        </tr>
        </c:forEach>
      </table>
      <div id="pagediv" align="right"></div>
    </div>
  </div>
</body>
</html>
