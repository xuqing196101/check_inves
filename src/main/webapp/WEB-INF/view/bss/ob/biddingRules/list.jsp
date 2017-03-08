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
	      		location.href = "${pageContext.request.contextPath }/obrule/ruleList.do?page=" + e.curr;
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
	
	/* 删除 */
	function del(){
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
				offset: ['222px', '360px'],
				shade: 0.01
			});
		}
	}
	/* 设为默认 */
	 function setDefault() {
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
            offset: ['222px', '390px'],
            shade: 0.01
          });
        } else {
          layer.alert("请选择要设置的默认项", {
            offset: ['222px', '390px'],
            shade: 0.01
          });
        }
      }
	
	
	<!--搜索-->
	function query(){
		$("#queryForm").attr("action","${pageContext.request.contextPath}/obrule/ruleList.html");
		$("#queryForm").submit();
	}
	
	function createOBRules(){
		window.location.href="${pageContext.request.contextPath}/obrule/addRuleUI.html";
	}
</script>
</head>
<body>

<!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0)"> 首页</a></li><li><a href="javascript:void(0)">保障作业</a></li><li><a href="javascript:void(0)">定型产品竞价</a></li>
		   <li class="active"><a href="javascript:void(0)">竞价规则管理</a></li><li class="active"><a href="javascript:void(0)">竞价规则列表</a></li>
		   </ul>
        <div class="clear"></div>
      </div>
    </div>
    
<!-- 竞价规格列表页面开始 -->
	<div class="container">
    <div class="search_detail">
       <form action="" method="post" id="queryForm" class="mb0">
    	<ul class="demand_list">
    	  <li>
	    	<label class="fl">竞价规则名称：</label>
			<input name="name" type="text" value="${name}"/>
	      </li>
    	  <li>
	    	<label class="fl">报价时间（分钟）：</label>
	    	  <select id="quoteTime" name="quoteTime" class="w178">
	    	    <option value="">--请选择--</option>
	    	    <option value="10" <c:if test="${10 eq quoteTime}">selected</c:if>>10分钟</option>
	    	    <option value="20" <c:if test="${20 eq quoteTime}">selected</c:if>>20分钟</option>
	    	    <option value="30" <c:if test="${30 eq quoteTime}">selected</c:if>>30分钟</option>
	    	  </select>
	      </li>
    	  <li>
	    	<label class="fl">间隔工作日（天）：</label>
			<input name="intervalWorkday" type="text" value="${ intervalWorkday }"/>
	      </li> 
	    	<button type="button" onclick="query()" class="btn">查询</button>
	    	<button type="reset" class="btn">重置</button>  	
    	</ul>
    	  <div class="clear"></div>
       </form>
     </div>
     
<!-- 表格开始 -->
	<div class="col-md-12 pl20 mt10">
		<button class="btn btn-windows add" onclick="createOBRules()">创建竞价规则</button>
		<button class="btn" onclick="setDefault()">设为默认</button>
		<button class="btn btn-windows delete" onclick="del()">删除</button>
	</div>   
	<div class="content table_box">
    	<table class="table table-bordered table-condensed table-hover table-striped">
		<thead>
		<tr>
		  <th class="w30 info"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
		  <th class="w50 info">序号</th>
		  <th class="info">竞价规则名称</th>
		  <th class="info">间隔工作日（天）</th>
		  <th class="info">具体时间点</th>
		  <th class="info">报价时间（分钟）</th>
		  <th class="info">确认时间（第一轮）（分钟）</th>
		  <th class="info">确认时间（第二轮）（分钟）</th>
		  <th class="info">项目数量</th>
		  <th class="info">是否为默认</th>
		</tr>
		<c:forEach items="${ info.list }" var="obRule" varStatus="vs">
			<tr>
			  <td class="tc w30"><input onclick="check()" type="checkbox" name="chkItem" value="${obRule.id }" /></td>
			  <td class="tc w50">${vs.index+1}</td>
			  <td class="tc">${obRule.name}</td>
			  <td class="tc">${obRule.intervalWorkday}</td>
			  <td class="tc"><fmt:formatDate value="${obRule.definiteTime}" pattern="HH:ss:mm"/></td>
			  <td class="tc">${obRule.quoteTime}</td>
			  <td class="tc">${obRule.confirmTime}</td>
			  <td class="tc">${obRule.confirmTimeSecond}</td>
			  <td class="tc">${obRule.bidingCount}</td>
			  <td class="tc">
				<c:if test="${ obRule.status == 1 }">
					默认
				</c:if>
			  </td>
			</tr>		
		</c:forEach>
		</thead>
	</table>
   </div>
      <div id="pagediv" align="right"></div>
   </div>

</body>
</html>