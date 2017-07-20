<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
	<%@ include file="/WEB-INF/view/common.jsp" %>
  <script type="text/javascript" src="${pageContext.request.contextPath}/public/common/RSA.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/ses/bms/user/initPWD.js"></script>
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
    	var checktd;
		$('input[name="chkItem"]:checked').each(function(){ 
			id.push($(this).val());
			checktd = $(this);
        });
		if(id.length==1){
			var trObj = checktd.parent().parent();
			var tdArr = trObj.children("td");
		    var roleCode = tdArr.eq(7).find("input").val();
		    //判断：如果该用户拥有供应商、专家（临时专家）、进口供应商、进口代理商中的任何一个角色都不能进行修改操作
		    if(roleCode.indexOf("SUPPLIER_R") > -1 || roleCode.indexOf("EXPERT_R") > -1 || roleCode.indexOf("EXPERT_TEMP_R") > -1 || roleCode.indexOf("IMP_SUPPLIER_R") > -1 || roleCode.indexOf("IMPORT_AGENT_R") > -1){
				layer.msg("该（角色）用户不能进行信息修改",{offset: ['222px']});
			} else {
				var currPage = ${list.pageNum};
				window.location.href="${pageContext.request.contextPath}/user/edit.html?id="+id+"&page="+currPage;
			}
		} else  if (id.length>1) {
			layer.alert("只能选择一个",{offset: '222px', shade:0.01});
		} else {
			layer.alert("请选择需要修改的用户",{offset: '222px', shade:0.01});
		}
    }
    
    function del(){
    	var ids =[]; 
    	var flag = 0;
		$('input[name="chkItem"]:checked').each(function(){ 
			ids.push($(this).val()); 
			var trObj = $(this).parent().parent();
			var tdArr = trObj.children("td");
		    var roleCode = tdArr.eq(7).find("input").val();
		    //判断：如果该用户拥有供应商、专家（临时专家）、进口供应商、进口代理商中的任何一个角色都不能进行修改操作
		    if(roleCode.indexOf("SUPPLIER_R") > -1 || roleCode.indexOf("EXPERT_R") > -1 || roleCode.indexOf("EXPERT_TEMP_R") > -1 || roleCode.indexOf("IMP_SUPPLIER_R") > -1 || roleCode.indexOf("IMPORT_AGENT_R") > -1){
				flag = 1;
			}
		}); 
		if(ids.length>0){
			if (flag == 1) {
				layer.msg("不能在此删除供应商、专家或进口代理商",{offset: '222px'});
			} 
			if (flag == 0) {
				layer.confirm('您确定要删除吗?', {title:'提示',offset: '222px',shade:0.01}, function(index){
					layer.close(index);
					window.location.href="${pageContext.request.contextPath}/user/delete_soft.html?ids="+ids;
				});
			}
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
			  area: ['300px', '420px'],
			  title: '配置权限',
			  closeBtn: 1,
			  shade:0.01, //遮罩透明度
			  moveType: 1, //拖拽风格，0是默认，1是传统拖动
			  shift: 1, //0-6的动画形式，-1不开启
			  offset: '60px',
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
	//设置数据权限
	 function openDataMenu(){
		var ids =[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			ids.push($(this).val()); 
		}); 
		if(ids.length==1){
			var iframeWin;
			layer.open({
			  type: 2, //page层
			  area: ['300px', '420px'],
			  title: '配置数据权限',
			  closeBtn: 1,
			  shade:0.01, //遮罩透明度
			  moveType: 1, //拖拽风格，0是默认，1是传统拖动
			  shift: 1, //0-6的动画形式，-1不开启
			  offset: '60px',
			  shadeClose: false,
			  content: '${pageContext.request.contextPath}/user/openDataMenu.html?id='+ids,
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
	function resetPaw(){
		var id=[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			id.push($(this).val());
		}); 
		if(id.length==1){
			$("#userId").val(id);
			layer.open({
					  type: 1,
					  title: '重置密码',
					  area: ['270', '260px'],
					  closeBtn: 1,
					  shade:0.01, //遮罩透明度
					  moveType: 1, //拖拽风格，0是默认，1是传统拖动
					  shift: 1, //0-6的动画形式，-1不开启
					  offset: '150px',
					  shadeClose: false,
					  content: $("#openDiv"),
					});
		}else if(id.length>1){
			layer.alert("只能选择一个",{offset: '222px', shade:0.01});
		}else{
			layer.alert("请选择用户",{offset: '222px', shade:0.01});
		}
	
	}
	
	function resetQuery(){
		$("#form1").find(":input").not(":button,:submit,:reset,:hidden").val("").removeAttr("checked").removeAttr("selected");
	}
	
	function cancel(){
		layer.closeAll();
	}
	
	function viewPermission(id){
		layer.open({
			  type: 2, //page层
			  area: ['300px', '420px'],
			  title: '用户权限',
			  closeBtn: 1,
			  shade:0.01, //遮罩透明度
			  moveType: 1, //拖拽风格，0是默认，1是传统拖动
			  shift: 1, //0-6的动画形式，-1不开启
			  offset: '60px',
			  shadeClose: false,
			  content: '${pageContext.request.contextPath}/user/viewPreMenu.html?id='+id
		});
	}
	//查看数据权限
	function dataViewPermission(id){
		layer.open({
			  type: 2, //page层
			  area: ['300px', '420px'],
			  title: '用户权限',
			  closeBtn: 1,
			  shade:0.01, //遮罩透明度
			  moveType: 1, //拖拽风格，0是默认，1是传统拖动
			  shift: 1, //0-6的动画形式，-1不开启
			  offset: '60px',
			  shadeClose: false,
			  content: '${pageContext.request.contextPath}/user/viewDataMenu.html?id='+id
		});
	}

	</script>
  </head>
  <body>
   <!--面包屑导航开始-->
	   <div class="margin-top-10 breadcrumbs ">
	      <div class="container">
			   <ul class="breadcrumb margin-left-0">
			   <li><a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')" target="_blank"> 首页</a></li><li><a href="javascript:void(0);">支撑系统</a></li><li><a href="javascript:void(0);">后台管理</a></li><li class="active"><a href="javascript:jumppage('${pageContext.request.contextPath}/user/list.html')" >用户管理</a></li>
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
				    	<label class="fl">用户名：</label><span><input type="text" id="loginName" value="${user.loginName}" name="loginName" class="w220"/></span>
				      </li>
			    	  <li>
				    	<label class="fl">姓名：</label><span><input type="text" id="relName" value="${user.relName}" name="relName" class="w220"/></span>
				      </li>
				      <li>
				    	<label class="fl">单位：</label><span><input type="text" id="orgName" value="${user.orgName}" name="orgName" class="w220"/></span>
				      </li>
			    	  <li>
				    	<label class="fl">角色：</label>
				    	   <span class="fl">
					        <select id="" name="roleId" class="w220">
					        	<option value="">全部</option>
					        	<c:forEach items="${roles}" var="r" varStatus="vs">
					        		<c:if test="${r.code != 'SUPPLIER_R' && r.code != 'EXPERT_R' && r.code != 'IMPORT_AGENT_R'}">
						        		<option value="${r.id}" <c:if test="${r.id eq user.roleId}">selected</c:if> >${r.name}</option>
					        		</c:if>
					        	</c:forEach>
					        </select>
					        </span>
				      </li>
			    	  <li>
				    	<label class="fl">机构类型：</label>
				    	   <span class="fl">
					        <select id="" name="typeName" class="w220" >
                             <option value="" >全部</option>
                             <option value="1" <c:if test="${user.typeName == '1'}">selected</c:if>>采购机构</option>
                             <option value="2" <c:if test="${user.typeName == '2'}">selected</c:if>>采购管理部门</option>
                             <option value="0" <c:if test="${user.typeName == '0'}">selected</c:if>>需求部门</option>
                             <option value="4" <c:if test="${user.typeName == '4'}">selected</c:if>>资源服务中心</option>
                             <option value="5" <c:if test="${user.typeName == '5'}">selected</c:if>>监管部门</option>
                             <option value="3" <c:if test="${user.typeName == '3'}">selected</c:if>>其他</option>  
                            </select>
					        </span>
				      </li>
				      <li>
				    	<label class="fl">联系电话：</label><span><input type="text" id="mobile" value="${user.mobile}" name="mobile" class="w220"/></span>
				      </li>
			    	</ul>
			    	<div class="col-md-12 col-xs-12 col-sm-12 tc">
				      	<button type="submit"  class="btn mt1">查询</button>
				    		<button type="button" onclick="resetQuery()" class="btn mt1">重置</button>  	
				      </div>
		    	  	<div class="clear"></div>
		        </form>
		     </h2>
      
   	  <!-- 表格开始-->
	  <div class="col-md-12 pl20 mt10">
	      <c:if test="${menu=='show' }">
		    <button class="btn btn-windows add" type="button" onclick="add()">新增</button>
			<button class="btn btn-windows edit" type="button" onclick="edit()">修改</button>
			<button class="btn btn-windows delete" type="button" onclick="del();">删除</button>
			<button class="btn btn-windows edit" type="button" onclick="openPreMenu()">设置权限</button>
			<!-- <button class="btn btn-windows edit" type="button" onclick="openDataMenu()">设置数据权限</button> -->
			<button class="btn btn-windows edit" type="button" onclick="resetPaw()">重置密码</button>
		 </c:if>
	  </div>
	  
	    <div class="content table_box">
            <table class="table table-bordered table-condensed table-hover table-striped">
            <thead>
					<tr>
					  <th class="info w30" ><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
					  <th class="info w50">序号</th>
					  <th class="info hidden">用户名</th>
					  <th class="info" width="12%">姓名</th>
					  <th class="info" width="13%">机构类型</th>
					  <th class="info"  width="25%">单位</th>
					  <th class="info"  width="140">联系电话</th>
					  <th class="info"  width="">角色</th>
					  <th class="info w50">权限</th>
					  <!-- <th class="info w80">数据权限</th> -->
					</tr>
		      </thead>
		      <tbody>
		      <c:if test="${menu=='show' }">
				<c:forEach items="${list.list}" var="user" varStatus="vs">
					<tr>
					  <td class="tc"><input onclick="check()" type="checkbox" name="chkItem" value="${user.id}" /></td>
					  <td class="tc">${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
					  <td class="tl hidden" ><a href="#" onclick="view('${user.id}');">${user.loginName}</a></td>
					  <td class="tl"><a href="#" onclick="view('${user.id}');">${user.relName}</a></td>
					 <td class="tl"> <c:if test="${user.typeName == '1'}">采购机构</c:if>
						        		<c:if test="${user.typeName == '2'}">采购管理部门</c:if>
						        		<c:if test="${user.typeName == '0'}">需求部门</c:if>
						        		<c:if test="${user.typeName == '4'}">资源服务中心</c:if>
						        		<c:if test="${user.typeName == '5'}">监管部门</c:if>
						        		<c:if test="${user.typeName == '3'}">其他</c:if>
						        	</td>
					  <td class="tl">
					   	<c:choose>
						    <c:when test="${user.typeName=='0'}">
		                        <c:if test="${user.org != null && user.org.shortName != null }">
		                            ${user.org.shortName}
		                        </c:if>
		                        <c:if test="${user.org != null && (user.org.shortName == null || user.org.shortName == '')}">
		                            ${user.org.name} 
		                        </c:if>
		                        <c:if test="${user.org == null }">${user.orgName}</c:if>
	                        </c:when>
	                        <c:when test="${user.typeName!='4' && user.typeName!='5'}">
							  	<c:if test="${user.org != null && user.org.fullName != null && user.org.fullName != ''}">
							  		${user.org.fullName}
							  	</c:if>
							  	<c:if test="${user.org != null && (user.org.fullName == null || user.org.fullName == '')}">
							  		${user.org.name}
							  	</c:if>
							  	<c:if test="${user.org == null }">${user.orgName}</c:if>
						  	</c:when> 
	                        <c:otherwise>
	                           ${user.orgName}
						  	</c:otherwise>
                        </c:choose>
					  </td>
					  <td class="tc">${user.mobile}</td>
					  <td class="tl">
					  	<c:set var="roleCode" value=""/>
					  	<c:forEach items="${user.roles}" var="r" varStatus="vs">
			        		<c:if test="${vs.index == 0 }">
			        			${r.name}
			        			<c:set var="roleCode" value="${roleCode}${r.code}"/>
			        		</c:if>
			        		<c:if test="${vs.index > 0 }">
			        			,${r.name}
			        			<c:set var="roleCode" value="${roleCode},${r.code}"/>
			        		</c:if>
			        	</c:forEach>
			        		<input type="hidden" id="role_code" value="${roleCode}">
					  </td>
					  <td class="tc">
					  	<a href="#" onclick="viewPermission('${user.id}');">查看</a>
					  </td>
					 <%--   <td class="tc">
					  	<a href="#" onclick="dataViewPermission('${user.id}');">查看</a>
					  </td> --%>
					</tr>
				</c:forEach>
				</c:if>
				</tbody>
		       </table>
		    </div>
		  <div id="pagediv" align="right"></div>
	  </div>
	  
	  <div id="openDiv" class="dnone layui-layer-wrap" >
	  <form id="form2" method="post" >
	  	<div class="drop_window">
	  		  <input type="hidden" name="id" id="userId" >
			  <ul class="list-unstyled">
	          	  <div class="col-md-6 col-sm-6 col-xs-12 pl15">
	                <label class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><a class="star_red">*</a>输入新密码：</label> 
	                <div class="col-md-12 col-sm-12 col-xs-12 input-append input_group p0">
	                 	<input type="password" name="password" id="password" maxlength="50">
	                </div>
	              </div>
	              <div class="col-md-6  col-sm-6 col-xs-12 ">
	                <label class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><a class="star_red">*</a>确认新密码：</label> 
	                <div class="col-md-12 col-sm-12 col-xs-12 input-append input_group p0">
	                  <input type="password" name="password2"  id="password2" maxlength="50">
	                </div>
	              </div>
			  </ul>
              <div class="tc col-md-12 col-sm-12 col-xs-12 mt10">
                <input class="btn" id="inputb" name="addr" onclick="userResetPasswSubmit();" value="确定" type="button"> 
				<input class="btn" id="inputa" name="addr" onclick="cancel();" value="取消" type="button"> 
              </div>
		    </div>
		 </form>
	  </div>
  </body>
</html>
