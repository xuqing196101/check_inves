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
			        var page = location.search.match(/page=(\d+)/);
			        return page ? page[1] : 1;
			    }(), 
			    jump: function(e, first){ //触发分页后的回调
			        if(!first){ //一定要加此判断，否则初始时会无限刷新
			            location.href = '${pageContext.request.contextPath}/role/list.html?page='+e.curr;
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
	
	function openPreMenu(){
		var ids =[]; 
		var rkind = "";
		$('input[name="chkItem"]:checked').each(function(){
			var trObj = $(this).parent().parent();
			var tdArr = trObj.children("td");
		    rkind = tdArr.eq(5).find("input").val();
			ids.push($(this).val()); 
		}); 
		if(ids.length==1){
			var iframeWin;
			layer.open({
			  type: 2, //page层
			  area: ['300px', '420px'],
			  title: '配置权限',
			  closeBtn: 1,
			  shade:0.01, //遮罩透明度
			  moveType: 1, //拖拽风格，0是默认，1是传统拖动
			  shift: 1, //0-6的动画形式，-1不开启
			  offset: '50px',
			  shadeClose: false,
			  //content: menucon,
			  content: '${pageContext.request.contextPath}/role/openPreMenu.do?id='+ids+'&kind='+rkind,
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
			layer.alert("只能同时选择一个角色",{offset: '222px', shade:0.01});
		}else{
			layer.alert("请选择一个角色",{offset: '222px', shade:0.01});
		}
	
	}
	
  	function view(id){
  		wi.htmlw.location.href="${pageContext.request.contextPath}/role/view.html?id="+id;
  	}
  	
    function edit(){
    	var id=[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			id.push($(this).val());
		}); 
		if(id.length==1){
			layer.open({
			  type: 2, //page层
			  area: ['600px','430px'],
			  title: '修改角色',
			  closeBtn: 1,
			  shade:0.01, //遮罩透明度
			  moveType: 1, //拖拽风格，0是默认，1是传统拖动
			  shift: 1, //0-6的动画形式，-1不开启
			  offset : '50px',
			  shadeClose: false,
			  content: '${pageContext.request.contextPath}/role/edit.html?id='+id
			});
		}else if(id.length>1){
			layer.alert("只能选择一个角色",{offset: '222px', shade:0.01});
		}else{
			layer.alert("请选择需要修改的角色",{offset: '222px', shade:0.01});
		}
    }
    
    function opera(){
    	var ids =[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			ids.push($(this).val()); 
		}); 
		if(ids.length == 1){
			$.ajax({  
               type: "POST",  
               url: "${pageContext.request.contextPath}/role/opera.html?ids="+ids,  
               dataType: 'json',  
               success:function(result){
               		if(result.msg == '已禁用'){
               			$("#"+ids).html('<span class="label rounded-2x label-dark">禁用</span>');
               		}
               		if(result.msg == '已启用'){
               			$("#"+ids).html('<span class="label rounded-2x label-u">启用</span>');
               		}
                    layer.msg(result.msg,{offset: '222px'});
                },
                error: function(result){
                    layer.msg("操作失败",{offset: '222px'});
                }
            });
		}else if(ids.length>1){
			layer.alert("只能选择一个角色",{offset: '222px', shade:0.01});
		}else{
			layer.alert("请选择角色",{offset: '222px', shade:0.01});
		}
    }
    
    function del(){
    	var ids =[]; 
    	var count = 0;
		$('input[name="chkItem"]:checked').each(function(){ 
			var trObj = $(this).parent().parent();
			var tdArr = trObj.children("td");
		    var roleCode = tdArr.eq(4).text();
		    if (roleCode == 'SUPPLIER_R' || roleCode == 'NEED_R' ||  roleCode == 'SUPERVISER_R' || roleCode == 'IMPORT_AGENT_R' || roleCode == 'EXPERT_TEMP_P' || roleCode == 'PURCHASE_ORG_R' || roleCode == 'MODERATOR_R' || roleCode == 'PURCHASE_R' || roleCode == 'IMPORT_SUPPLIER_R' || roleCode == 'ADMIN_R' || roleCode == 'EXPERT_R') {
				count += 1;
			} else {
				ids.push($(this).val()); 
			}
		});  
		if (count > 0 ) {
			layer.msg("系统初始角色请找管理员确认删除",{offset: ['222px']});
		}
		if (count == 0) {
			if(ids.length>0){
				layer.confirm('您确定要删除吗?', {title:'提示',offset: '222px',shade:0.01}, function(index){
				layer.close(index);
				window.location.href="${pageContext.request.contextPath}/role/delete.html?ids="+ids;
			});
			}else{
				layer.alert("请选择要删除的角色",{offset: '222px', shade:0.01});
			}
		}
    }
    
    function add(){
    	layer.open({
			  type: 2, //page层
			  area: ['600px','430px'],
			  title: '新增角色',
			  closeBtn: 1,
			  shade:0.01, //遮罩透明度
			  moveType: 1, //拖拽风格，0是默认，1是传统拖动
			  shift: 1, //0-6的动画形式，-1不开启
			  offset: '50px',
			  shadeClose: false,
			  content: '${pageContext.request.contextPath}/role/add.html'
			});
    }
    
    function query(){
		$("#form1").submit();
	}
	
	function resetQuery(){
	    $("#topic").val("");
	    $("#topStatus").val(""); 
		$("#form1").submit();
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
			   <li class="active"><a href="javascript:jumppage('${pageContext.request.contextPath}/role/list.html')">角色管理</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   <div class="container">
	   <div class="headline-v2">
	   		<h2>角色管理</h2>
	   </div>
		    <h2 class="search_detail">
		       	<form action="${pageContext.request.contextPath}/role/list.html" id="form1" method="post" class="mb0">
			    	<ul class="demand_list">
			    	  <li>
				    	<label class="fl">名称：</label><span><input type="text" value="${role.name }" id="topic" name="name" class=""/></span>
				      </li>
			    	  <li>
				    	<label class="fl">状态：</label>
                        <span>
					        <select name="status" id="topStatus" class="w178">
					        	<option value="">全部</option>
					        	<option value="0" <c:if test="${'0' eq role.status}">selected</c:if>>启用</option>
					        	<option value="1" <c:if test="${'1' eq role.status}">selected</c:if>>禁用</option>
					        </select>
				        </span>
				      </li> 
				    </ul>
				    	<button type="button" onclick="query()" class="btn fl mt1">查询</button>
				    	<button type="button" onclick="resetQuery()" class="btn fl mt1">重置</button>  	
			    	</ul>
		    	  	<div class="clear"></div>
		        </form>
		     </h2>
  
	<!-- 表格开始-->
   <div class="col-md-12 pl20 mt10">
	    <button class="btn btn-windows add" type="button" onclick="add()">新增</button>
		<button class="btn btn-windows edit" type="button" onclick="edit()">修改</button>
		<button class="btn btn-windows reset" type="button" onclick="opera();">激活/禁用</button>
		<button class="btn btn-windows edit" type="button" onclick="openPreMenu()">设置权限</button>
		<button class="btn btn-windows delete" type="button" onclick="del();">删除</button>
   </div>

		<div class="content table_box">
            <table class="table table-bordered table-condensed table-hover table-striped">
				<thead>
					<tr>
						<th class="info w30"><input id="checkAll" type="checkbox"
							onclick="selectAll()" />
						</th>
						<th class="info w50">序号</th>
						<th class="info">名称</th>
						<th class="info">状态</th>
						<th class="info">唯一编码</th>
						<th class="info">所属后台</th>
						<th class="info w450">描述</th>
						<th class="info">用户数量</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${list.list}" var="role" varStatus="vs">
						<tr>
							<td class="tc"><input onclick="check()" type="checkbox"
								name="chkItem" value="${role.id}" />
							</td>
							<td class="tc">${role.position}</td>
							<td class="tl pl20">${role.name}</td>
							<td class="tc" id="${role.id}"><c:if test="${role.status == 0}">
									<span class="label rounded-2x label-u" >启用</span>
								</c:if> <c:if test="${role.status == 1}">
									<span class="label rounded-2x label-dark">禁用</span>
								</c:if>
							</td>
							<td class="tl pl20">${role.code}</td>
							<td class="tl pl20">
								<c:forEach items="${dds}" var="dd" varStatus="vs">
		                   			<c:if test="${dd.id eq role.kind}">
		                   				<c:set var="rkind" value="${role.kind}"></c:set>
			                   			<c:if test="${'PURCHASE_BACK' eq dd.code}">采购后台</c:if>
			                   			<c:if test="${'EXPERT_BACK' eq dd.code}">专家后台</c:if>
			                   			<c:if test="${'SUPPLIER_BACK' eq dd.code}">供应商后台</c:if>
			                   			<c:if test="${'IMPORT_SUPPLIER_BACK' eq dd.code}">进口供应商后台</c:if>
			                   			<c:if test="${'IMPORT_AGENT_BACK' eq dd.code}">进口代理商后台</c:if>
			                   		</c:if>
		                   		</c:forEach>
		                   		<input type="hidden" value="${rkind}">
							</td>
							<td class="tl pl20">${role.description}</td>
							<td class="tc"><a href="${pageContext.request.contextPath}/user/listByRole.do?rId=${role.id}" title="用户列表">${role.userNumber}</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<div id="pagediv" align="right"></div>
		</div>
		
   <!-- 菜单树-->
   <div id="menu">
       <div id="menuTree" class="ztree"></div>
   </div>
	</div>
  </body>
</html>
