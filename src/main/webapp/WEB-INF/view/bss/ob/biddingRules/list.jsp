<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
	<title>竞价规则列表页面</title>
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
	      		location.href = "${pageContext.request.contextPath }/obrule/ruleList.do?name=${name}&&quoteTime=${quoteTime}&&intervalWorkday=${intervalWorkday}&&page=" + e.curr;
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
        	window.location.href = '${pageContext.request.contextPath}/obrule/editobRule.html?id=' + id;
       } else if(id.length > 1) {
          layer.alert("只能选择一个", {
            offset: ['222px', '255px'],
            shade: 0.01,
          });
	   } else {
          layer.alert("请选择需要修改的竞价规则", {
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
				offset: ['222px', '255px'],
				shade: 0.01
			}, function(index) {
				layer.close(index);
				$.ajax({
					url: "${pageContext.request.contextPath }/obrule/delete.do",
					type: "POST",
					data: {
						id: ids
					},
					success: function(data) {
						layer.confirm(data.data,{
							btn:['确定']
						},function(){
								$("#queryForm").attr("action","${pageContext.request.contextPath}/obrule/ruleList.html");
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
				offset: ['222px', '255px'],
				shade: 0.01
			});
		}
	}
	/* 设为默认 */
	 function setDefault() {
	 var authType='${authType}'; 
	    if(authType!='4'){
	    layer.msg("只有资源服务中心才能操作");
	    return;
	    }
        var id = [];
        var status = "";
        var data = "";
        $('input[name="chkItem"]:checked').each(function() {
          id.push($(this).val());
          status=$(this).parent().next().text();
        });
        if(id.length == 1) {
   			layer.confirm('要您将该项设置成默认吗？', {
   				btn : [ '是', '否' ]
   			//按钮
   			}, function() {
   				$.ajax({
   				    url: "${pageContext.request.contextPath }/obrule/setDefaultRule.do",
   				    type: "POST",
   				    dataType: "json",
   				 	data: {
						id: id[0]
					},
   				    success: function(data) {
   				    	// 成功后提示
   				    	layer.confirm(data.data,{
							btn:['确定']
						},function(){
							location.reload();
							}
						)
   				    }
   				});
   			}, function() {
   				layer.close();
   			});
        } else if(id.length > 1) {
          layer.alert("只能选择一个", {
            offset: ['222px', '255px'],
            shade: 0.01
          });
        } else {
          layer.alert("请选择要设置的默认项", {
            offset: ['222px', '255px'],
            shade: 0.01
          });
        }
      }
	
	
	//<!--搜索-->
	function query(){
	var authType='${authType}'; 
	    if(authType!='4'){
	    layer.msg("只有资源服务中心才能操作");
	    return;
	    }
		var quoteTime =  $("#quoteTime").val();
		if(isNaN(quoteTime)){
			layer.alert("第一轮报价时间请输入整数");
			return;
		}
		var intervalWorkday =  $("#intervalWorkday").val();
		if(isNaN(intervalWorkday)){
			layer.alert("间隔工作日请输入整数");
			return;
		}
		$("#queryForm").attr("action","${pageContext.request.contextPath}/obrule/ruleList.html");
		$("#queryForm").submit();
	}
	
	function createOBRules(){
	var authType='${authType}'; 
	    if(authType!='4'){
	    layer.msg("只有资源服务中心才能操作");
	    return;
	    }
		window.location.href="${pageContext.request.contextPath}/obrule/addRuleUI.html";
	}
	
	//重置按钮事件  
    function resetAll(){
    var authType='${authType}'; 
	    if(authType!='4'){
	    layer.msg("只有资源服务中心才能操作");
	    return;
	    }
        $("#name").val("");  
        $("#quoteTime").val("");  
        $("#intervalWorkday").val("");
    }
</script>
</head>
<body>

<!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0)"> 首页</a></li><li><a href="javascript:void(0)">保障作业</a></li><li><a href="javascript:void(0)">定型产品竞价</a></li>
		   <li class="active"><a href="javascript:void(0)">竞价规则管理</a></li>
		   </ul>
        <div class="clear"></div>
      </div>
    </div>
    
<!-- 竞价规格列表页面开始 -->
	<div class="container">
	<div class="headline-v2">
		<h2>竞价规则列表</h2>
	 </div>
    <div class="search_detail">
       <form action="" method="post" id="queryForm" class="mb0">
    	<ul class="demand_list">
    	  <li>
	    	<label class="fl">竞价规则名称：</label>
			<input id="name" name="name" type="text" value="${ name }"/>
	      </li>
    	  <li>
	    	<label class="fl">第一轮报价时间（分钟）：</label>
	    	  <input class="input_group" name="quoteTime" id="quoteTime" value="${ quoteTime }" type="text" class="mb0 border0" onkeyup="this.value=this.value.replace(/\D/g,'')"
				     onafterpaste="this.value=this.value.replace(/\D/g,'')">
	    	<%-- <label class="fl">第二轮报价时间（分钟）：</label>
	    	  <input class="input_group" name="quoteTime" id="quoteTime" value="${ quoteTime }" type="text" class="mb0 border0" onkeyup="this.value=this.value.replace(/\D/g,'')"
				     onafterpaste="this.value=this.value.replace(/\D/g,'')"> --%>
	      </li>
    	  <li>
	    	<label class="fl">间隔工作日（天）：</label>
			<input name="intervalWorkday" id="intervalWorkday" type="text" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" value="${ intervalWorkday }"/>
	      </li> 
	    	<button type="button" onclick="query()" class="btn fl mt1">查询</button>
	    	<button onclick="resetAll()" class="btn fl mt1 ml5">重置</button>  	
    	</ul>
    	  <div class="clear"></div>
       </form>
     </div>
     
<!-- 表格开始 -->
     <div class="col-md-12 pl20 mt10 col-sm-12 col-xs-12">
		<button class="btn btn-windows add" onclick="createOBRules()">创建竞价规则</button>
		<button class="btn" onclick="setDefault()">设为默认</button>
		<button class="btn btn-windows edit" onclick="edit()">修改 </button>
		<button class="btn btn-windows delete" onclick="del()">删除</button>
	</div>  
	<div class="content table_box over_auto">
    	<table class="table table-bordered table-condensed table-hover table-striped">
		<thead>
		<tr>
		  <th class="w30 info"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
		  <th class="w50 info">序号</th>
		  <th class="info" width="15%">竞价规则名称</th>
		  <th class="info">间隔工作日<br/>（天）</th>
		  <th class="info">竞价开始<br/>时间</th>
		  <th class="info">第一轮报价时间<br/>（分钟）</th>
		  <th class="info">第二轮报价时间<br/>（分钟）</th>
		  <th class="info">第一轮确认时间<br/>（分钟）</th>
		  <th class="info">第二轮确认时间<br/>（分钟）</th>
		  <th class="info">最少报价<br/>供应商数</th>
		  <th class="info">有效供应商报价<br/>平均值的百分比</th>
		  <th class="info">项目<br/>数量</th>
		  <th class="info">是否为<br/>默认</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${ info.list }" var="obRule" varStatus="vs">
			<tr>
			  <td class="tc"><div class="w30"><input onclick="check()" type="checkbox" name="chkItem" value="${obRule.id }" /></div></td>
			  <td class="tc"><div class="w50">${(vs.index+1)+(info.pageNum-1)*(info.pageSize)}</div></td>
			  <td class="tl"><div class="w200">${obRule.name}</div></td>
			  <td class="tc"><div class="w150">${obRule.intervalWorkday}</div></td>
			  <td class="tc"><div class="w150"><fmt:formatDate value="${obRule.definiteTime}" pattern="HH:mm:ss"/></div></td>
			  <td class="tc"><div class="w150">${obRule.quoteTime}</div></td>
			  <td class="tc"><div class="w150">${obRule.quoteTimeSecond}</div></td>
			  <td class="tc"><div class="w150">${obRule.confirmTime}</div></td>
			  <td class="tc"><div class="w150">${obRule.confirmTimeSecond}</div></td>
			  <td class="tc"><div class="w150">${obRule.leastSupplierNum}</div></td>
			  <td class="tc"><div class="w150">${obRule.percent}%</div></td>
			  <td class="tc"><div class="w150">${obRule.bidingCount}</div></td>
			  <td class="tc">
			  	<div class="w30">
				<c:if test="${ obRule.status == 1 }">
					默认
				</c:if>
				</div>
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