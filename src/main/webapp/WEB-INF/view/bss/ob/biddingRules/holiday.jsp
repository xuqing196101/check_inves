<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
	<title>节假日管理列表页面</title>
<script type="text/javascript">
		$(function() {
		    laypage({
		      cont : $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
		      pages : "${info.pages}", //总页数
		      skin : '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
		      skip : true, //是否开启跳页
		      total : "${info.total}",
		      startRow : "${info.startRow}",
		      endRow : "${info.endRow}",
		      groups : "${info.pages}" >= 3 ? 3 : "${info.pages}", //连续显示分页数
		      curr : function() { //通过url获取当前页，也可以同上（pages）方式获取
		        return "${info.pageNum}";
		      }(),
		      jump : function(e, first) { //触发分页后的回调
		    	if(!first){ //一定要加此判断，否则初始时会无限刷新
		      		location.href = "${pageContext.request.contextPath }/obrule/holidayList.do?specialDate=${specialDateStr}&&dateType=${dateType}&&page=" + e.curr;
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
	
	//修改
    function edit() {
		var authType='${authType}'; 
	    if(authType!='4'){
	    layer.msg("只有资源服务中心才能操作");
	    return;
	    }    
       var id = [];
	   $('input[name="chkItem"]:checked').each(function() {
	   		id.push($(this).val());
	   });
	   if(id.length == 1) {
        	window.location.href = '${pageContext.request.contextPath}/obrule/editSpecialdate.html?id=' + id;
       } else if(id.length > 1) {
          layer.alert("只能选择一个", {
            offset: ['222px', '255px'],
            shade: 0.01,
          });
	   } else {
          layer.alert("请选择需要修改的特殊日期", {
            offset: ['222px', '255px'],
            shade: 0.01,
          });
        }
    }	
	
	/* 删除 */
	function del(){
		var authType='${authType}'; 
	    if(authType!='4'){
	    layer.msg("只有资源服务中心才能操作");
	    return;
	    }	
		var id = [];
		$('input[name="chkItem"]:checked').each(function() {
			id.push($(this).val());
		});
		var ids = id.toString();
		if(id.length > 0) {
			layer.confirm('您确定要删除吗?', {
				title: '提示',
				offset: ['222px', '360px'],
				shade: 0.01
			}, function(index) {
				layer.close(index);
				$.ajax({
					url: "${pageContext.request.contextPath }/obrule/deleteSpecialDate.do",
					type: "POST",
					data: {
						id: ids
					},
					success: function(data) {
						layer.confirm(data.data,{
							btn:['确定']
						},function(){
								$("#queryForm").attr("action","${pageContext.request.contextPath}/obrule/holidayList.html");
								$("#queryForm").submit();
							}
						)
					},
					error: function() {

					}
				});
			});
		} else {
			layer.alert("请选择要删除的版块", {
				offset: ['222px', '360px'],
				shade: 0.01
			});
		}
	}
	
	// 创建特殊日期
	function createSpecialdate(){
		var authType='${authType}'; 
	    if(authType!='4'){
	    layer.msg("只有资源服务中心才能操作");
	    return;
	    }	
		window.location.href="${pageContext.request.contextPath}/obrule/createSpecialdateUI.html";
	}
	
	//!--搜索-->
	function query(){
		var authType='${authType}'; 
	    if(authType!='4'){
	    layer.msg("只有资源服务中心才能操作");
	    return;
	    }	
		$("#queryForm").attr("action","${pageContext.request.contextPath}/obrule/holidayList.html");
		$("#queryForm").submit();
	}
	
	//重置按钮事件  
    function resetAll(){
 		var authType='${authType}'; 
	    if(authType!='4'){
	    layer.msg("只有资源服务中心才能操作");
	    return;
	    }   
        $("#specialDate").val("");  
        $("#dateType").val("");  
    }
</script>
</head>
<body>

<!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		  <ul class="breadcrumb margin-left-0">
			  <li><a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a></li>
			  <li><a href="javascript:void(0)">保障作业</a></li>
			  <li><a href="javascript:void(0)">网上竞价</a></li>
			  <li class="active"><a href="javascript:jumppage('${pageContext.request.contextPath}/obrule/holidayList.html')">节假日管理</a></li>
		  </ul>
		  <div class="clear"></div>
	  </div>
    </div>
    
<!-- 竞价规格列表页面开始 -->
	<div class="container">
		<div class="headline-v2">
     		<h2>节假日管理列表</h2>
		</div> 
    <div class="search_detail">
       <form id="queryForm" action="" method="post" class="mb0">
    	<ul class="demand_list">
    	  <li>
	    	<label class="fl">设置日期：</label>
			<input id="specialDate" name="specialDate" value="${ specialDateStr }" class="Wdate" type="text" id="d17" onfocus="WdatePicker({firstDayOfWeek:1})"/>
	      </li>
    	  <li>
	    	<label class="fl">类型：</label>
	    	  <select id="dateType" name="dateType" class="w178">
	    	    <option value="">--请选择--</option>
	    	    <option value="1" <c:if test="${'1' eq dateType}">selected</c:if>>上班</option>
	    	    <option value="0" <c:if test="${'0' eq dateType}">selected</c:if>>放假</option>
	    	  </select>
	      </li>
	    	<button type="button" onclick="query()" class="btn fl mt1">查询</button>
	    	<button onclick="resetAll()" class="btn fl ml5 mt1">重置</button>  	
    	</ul>
    	  <div class="clear"></div>
       </form>
     </div>
     
<!-- 表格开始 -->
	<div class="col-md-12 pl20 mt10">
		<button class="btn btn-windows add" onclick="createSpecialdate()">创建特殊日期</button>
		<button class="btn btn-windows edit" onclick="edit()">修改 </button>
		<button class="btn btn-windows delete" onclick="del()">删除</button>
		系统默认周末为放假，如有特殊情况请手动标记为上班，特殊法定节假日请手动管理！
	</div>   
	<div class="content table_box">
    	<table class="table table-bordered table-condensed table-hover table-striped">
		<thead>
		<tr>
		  <th class="w30 info"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
		  <th class="w50 info">序号</th>
		  <th class="info" width="25%">设置日期</th>
		  <th class="info" width="25%">创建人</th>
		  <th class="info" width="25%">创建时间</th>
		  <th class="info">类型</th>
		</tr>
		</thead>
		<c:forEach items="${info.list }" var="obspecialdate" varStatus="vs">
			<tr>
			  <td class="tc w30"><input onclick="check()" type="checkbox" name="chkItem" value="${ obspecialdate.id }" /></td>
			  <td class="tc w50">${(vs.index+1)+(info.pageNum-1)*(info.pageSize)}</td>
			  <td class="tc"><fmt:formatDate value="${ obspecialdate.specialDate }" pattern="yyyy-MM-dd"/></td>
			  <td class="tl">${ obspecialdate.createrName }</td>
			  <td class="tc"><fmt:formatDate value="${ obspecialdate.createdAt }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			  <td class="tc">
				<c:if test="${ obspecialdate.dateType=='1' }">
					上班
				</c:if>
				<c:if test="${ obspecialdate.dateType=='0' }">
					放假
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