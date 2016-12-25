<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/WEB-INF/view/common.jsp"%>
<script type="text/javascript">
  /*分页  */
  $(function() {
    laypage({
      cont : $("#pageDiv"), //容器。值支持id名、原生dom对象，jquery对象,
      pages : "${info.pages}", //总页数
      skin : '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
      skip : true, //是否开启跳页
      total : "${info.total}",
      startRow : "${info.startRow}",
      endRow : "${info.endRow}",
      groups : "${info.pages}" >= 5 ? 5 : "${info.pages}", //连续显示分页数
      curr : function() { //通过url获取当前页，也可以同上（pages）方式获取
    	  var page = location.search.match(/page=(\d+)/);
    	  if(page==null){
	    		page = {};
	    		page[0]="${info.pageNum}";
	    		page[1]="${info.pageNum}";
	    	}
				return page ? page[1] : 1;
      }(),
      jump : function(e, first) { //触发分页后的回调
        if (!first) { //一定要加此判断，否则初始时会无限刷新
          $("#page").val(e.curr);
          $("#form1").submit();
        }
      }
    });
  });

  /** 全选全不选 */
  function selectAll() {
    var checklist = document.getElementsByName("chkItem");
	var checkAll = document.getElementById("checkAll");
	if (checkAll.checked) {
	  for ( var i = 0; i < checklist.length; i++) {
		checklist[i].checked = true;
	  }
	} else {
	  for ( var j = 0; j < checklist.length; j++) {
		checklist[j].checked = false;
	  }
	}
  }

  /** 单选 */
  function check() {
	var count = 0;
	var checklist = document.getElementsByName("chkItem");
	var checkAll = document.getElementById("checkAll");
	for ( var i = 0; i < checklist.length; i++) {
	  if (checklist[i].checked == false) {
		checkAll.checked = false;
		break;
	  }
	  for ( var j = 0; j < checklist.length; j++) {
		if (checklist[j].checked == true) {
		  checkAll.checked = true;
		  count++;
	    }
	  }
	}
  }
  
  /** 取消任务 */
  function deleted() {
    var id = [];
    $('input[name="chkItem"]:checked').each(function() {
	  	id.push($(this).val());
		});
    var status = $("input[name='chkItem']:checked").parents("tr").find("td").eq(5).text();
    status = $.trim(status);
	if (id.length == 1) {
		if(status=="已取消"){
			layer.alert("任务已经取消", {
				offset : [ '30%', '40%' ],
				shade : 0.01
			});
		}else{
			layer.open({
				type : 2, //page层
				area : [ '600px', '400px' ],
				title : '上传附件',
				shade : 0.01, //遮罩透明度
				moveType : 1, //拖拽风格，0是默认，1是传统拖动
				shift : 1, //0-6的动画形式，-1不开启
				offset : [ '30%', '40%' ],
				shadeClose : true,
				content : '${pageContext.request.contextPath}/task/delTask.html?id=' + id,
		  });
		}
	} else if (id.length > 1) {
	  layer.alert("只能选择一个", {
		offset : [ '30%', '40%' ],
		shade : 0.01
	  });
	} else {
	  layer.alert("请选择需要取消的任务", {
		offset : [ '30%', '40%' ],
		shade : 0.01
	  });
	}
  }
  
  /** 受领任务 */
  function start() {
		var ids = [];
		$('input[name="chkItem"]:checked').each(function() {
	  	ids.push($(this).val());
		});
		var status = $("input[name='chkItem']:checked").parents("tr").find("td").eq(5).text();
		status = $.trim(status);
		if (ids.length == 1) {
	 	 if (status == "未受领") {
				layer.confirm('您确定要受领吗?',{
		  	title : '提示',
		  	offset: ['30%', '40%'],
		  	shade : 0.01
			},
		function(index) {
		  layer.close(index);
          $.ajax({
			url : "${pageContext.request.contextPath}/task/startTask.do",
			data : "ids=" + ids,
			type : "post",
			dateType : "json",
			success : function() {
			  layer.msg("受领成功", {
				  offset: ['30%', '40%'],
			  });
			  window.setTimeout(function() {
				location.reload();
			  }, 1000);
			},
			error : function() {
			  layer.msg("受领失败", {
				  offset: ['30%', '40%'],
			  });
			}
		  });
		});
	  } else if(status=="已受领"){
			layer.alert("任务已经受领", {
				offset: ['30%', '40%'],
		  	shade : 0.01
			});
	  }else if(status=="已取消"){
		  layer.alert("已取消的任务不能受领，请重新选择", {
			  offset: ['30%', '40%'],
			  shade : 0.01
			});
	  }
	}else if(ids.length>1){
		layer.alert("只能选择一项任务", {
			offset: ['30%', '40%'],
			shade : 0.01
		});
	}else {
	  layer.alert("请选择要受领的任务", {
		  offset: ['30%', '40%'],
			shade : 0.01
	  });
	}
  }
  
  /** 修改任务 */
  function edit() {
    var id = [];
		$('input[name="chkItem"]:checked').each(function() {
	  	id.push($(this).val());
		});
		var status = $("input[name='chkItem']:checked").parents("tr").find("td").eq(5).text();
		status = $.trim(status);
		if (id.length == 1) {
	 	if(status == "已取消"){
	    	layer.alert("任务已取消不能修改", {
          offset : [ '222px', '730px' ],
         	shade : 0.01
       	});
	  }else{
	    window.location.href = "${pageContext.request.contextPath}/task/edit.html?id=" + id;
	  }
	} else if (id.length > 1) {
	  layer.alert("只能选择一个", {
		offset : [ '222px', '730px' ],
		shade : 0.01
	  });
	} else {
	  layer.alert("请选择需要调整的任务", {
		offset : [ '222px', '730px' ],
		shade : 0.01
	  });
	}
  }
  
  /** 查看任务 */
  function viewd(id) {
	window.location.href = "${pageContext.request.contextPath}/task/view.html?id=" + id;
  }
  
  /** 重置任务 */
  function clearSearch() {
	$("#name").attr("value", "");
	$("#documentNumber").attr("value", "");
	$("#purchaseId").attr("value", "");
	//还原select下拉列表只需要这一句
	$("#status option:selected").removeAttr("selected");
  }
  
  /** 上传附件 */
  function delTask() {
	var attach = $("input[name='attach']").val();
	if (!attach) {
	  layer.alert("请上传凭证", {
		offset : [ '50px', '90px' ],
		shade : 0.01
	  });
	} else {
	  layer.confirm('此操作后果严重，您确认要取消任务吗?', {
		offset : [ '300px', '600px' ],
		shade : 0.01,
		btn : [ '是', '否' ],
	  }, function() {
		$("#att").submit();
	  }, function() {
		parent.layer.close();
	  });
	}
  }
  
  /** 关闭页面 */
  function cancel() {
	layer.closeAll();
  }
</script>
</head>

<body>
  <!--面包屑导航开始-->
  <div class="margin-top-10 breadcrumbs ">
	<div class="container">
	  <ul class="breadcrumb margin-left-0">
		<li><a href="javascript:void(0)">首页</a></li>
		<li><a href="javascript:void(0)">保障作业系统</a></li>
		<li><a href="javascript:void(0)">采购任务管理</a></li>
		<li class="active"><a href="javascript:void(0)">采购任务管理</a></li>
	  </ul>
	  <div class="clear"></div>
	</div>
  </div>
  <!-- 录入采购计划开始-->
  <div class="container">
	<div class="headline-v2">
	  <h2>任务列表</h2>
	</div>
	<!-- 项目戳开始 -->
	<h2 class="search_detail">
	  <form id="form1" action="${pageContext.request.contextPath}/task/list.html" method="post" class="mb0">
		<input type="hidden" name="page" id="page">
		<ul class="demand_list">
		  <%--<li>
		    <label class="fl">需求部门：</label>
				<span><input type="text" name="name" id="purchaseRequiredId" value="${task.name}" /></span>
		  </li>
          --%>
      		<li>
		    		<label class="fl">采购任务名称：</label>
						<span><input type="text" name="name" id="name" value="${task.name}" /></span>
		  		</li>
          <li>
            <label class="fl">采购任务文号：</label>
            <span><input type="text" name="documentNumber" id="documentNumber" value="${task.documentNumber }" class=""/></span>
          </li>
          <li>
            <label class="fl">状态：</label>
            <span class="">
              <select  name="status" id="status">
                <option selected="selected" value="">请选择</option>
                <option value="0" <c:if test="${'0'==task.status}">selected="selected"</c:if>>未受领</option>
                <option value="1" <c:if test="${'1'==task.status}">selected="selected"</c:if>>已受领</option>
                <option value="2" <c:if test="${'2'==task.status}">selected="selected"</c:if>>已取消</option>
              </select>
            </span>
          </li>
          <li>
            <label class="fl">任务性质：</label>
            <span class="">
              <select  name="taskNature" id="taskNature">
                <option selected="selected" value="">请选择</option>
                <option value="1" <c:if test="${'1'==task.taskNature}">selected="selected"</c:if>>预研任务</option>
                <option value="0" <c:if test="${'0'==task.taskNature}">selected="selected"</c:if>>正常任务</option>
              </select>
            </span>
          </li>
		</ul>
		<div class="col-md-12 clear tc mt10">
		  <button class="btn" type="submit">查询</button>
		  <button class="btn" type="reset" onclick="clearSearch()">重置</button>
	    </div>
		<div class="clear"></div>
    </form>
	</h2>
	<c:if test="${admin!=1 }">
		<div class="col-md-12 pl20 mt10">
	  	<button class="btn btn-windows edit" onclick="edit()" type="button">任务调整</button>
	  	<button class="btn btn-windows delete" onclick="deleted()">任务取消</button>
	  	<button class="btn btn-windows git" onclick="start()">受领</button>
		</div>
	</c:if>
	<div class="content table_box">
      <table class="table table-bordered table-condensed table-hover table-striped">
		<thead>
		  <tr class="info">
				<th class="w30"><input type="checkbox" id="checkAll"onclick="selectAll()"></th>
				<th class="w50">序号</th>
				<th>采购任务名称</th>
				<th>采购管理部门</th>
				<th>采购任务文号</th>
				<th>状态</th>
				<th>任务性质</th>
				<th>下达时间</th>
		  </tr>
		</thead>
		<c:forEach items="${info.list}" var="obj" varStatus="vs">
		  <c:if test="${orgId eq obj.purchaseId}"></c:if>
		  <tr class="pointer">
			<td class="tc w30"><input type="checkbox" value="${obj.id }"name="chkItem" onclick="check()"></td>
			<td class="tc w50">${(vs.index+1)+(info.pageNum-1)*(info.pageSize)}</td>
			<td><a href="javascript:void(0)" onclick="viewd('${obj.id}');">${obj.name}</a></td>
			<td>
			  <a href="javascript:void(0)" onclick="viewd('${obj.id}');">
			  <c:forEach items="${list2}" var="list" varStatus="vs">
			   <c:if test="${obj.purchaseRequiredId eq list.id}">${list.name}</c:if>
			  </c:forEach>
			  </a></td>
			<td><a href="javascript:void(0)" onclick="viewd('${obj.id}');">${obj.documentNumber}</a></td>
			<td class="tc">
			  <c:if test="${'0'==obj.status}">
				<span class="label rounded-2x label-u">未受领</span>
			  </c:if> 
			  <c:if test="${'1'==obj.status}">
				<span class="label rounded-2x label-u">已受领</span>
			  </c:if>
			  <c:if test="${'2'==obj.status}">
				<span class="label rounded-2x label-dark">已取消</span>
			  </c:if>
			</td>
			<td class="tc">
				<c:if test="${'1'==obj.taskNature}">
				<span class="label rounded-2x label-u">预研任务</span>
			  </c:if> 
			  <c:if test="${'0'==obj.taskNature}">
				<span class="label rounded-2x label-u">正常任务</span>
			  </c:if>
			</td>
			<td class="tc"><fmt:formatDate value="${obj.giveTime }" /></td>
		  </tr>
		</c:forEach>
	  </table>
	</div>
	<div id="pageDiv" align="right"></div>
  </div>
</body>
</html>
