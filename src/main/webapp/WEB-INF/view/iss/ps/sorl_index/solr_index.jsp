<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

  <head>
    <link href="${pageContext.request.contextPath }/public/select2/css/select2.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath }/public/portal/css/css-loader.css" rel="stylesheet" />
    <%@ include file="/WEB-INF/view/common.jsp"%>
    <%@ include file="/WEB-INF/view/common/webupload.jsp"%>
    <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath }/public/select2/js/select2.js"></script>
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
		      		location.href = "${pageContext.request.contextPath}/index/indexImportUI.html?page=" + e.curr+"&name=${name}&isIndex=${isIndex}";
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
	    
    	// 文章全量导入索引库
    	function importAll(){
    		layer.confirm('确认要文章全量导入索引库吗？', {
    			title: '提示',
				shade: 0.01
   			}, function(index){
   				layer.close(index);
   				$("#processBar").addClass("loader loader-bar is-active");
   	   			$.post("${pageContext.request.contextPath }/index/importAll.do", '', function(data) {
   	   				$("#processBar").removeClass();
	   	   			layer.confirm(data.data,{
						btn:['确定']
					},function(){
							$("#queryForm").attr("action","${pageContext.request.contextPath}/index/indexImportUI.html");
							$("#queryForm").submit();
						}
					)
   	   			});
   			},function() {
   				layer.close();
   			});
    	}
    	
    	
    	<!--清空索引库-->
    	function clearIndex(){
    		layer.confirm('确认要清空文章索引库吗？', {
   				btn : [ '是', '否' ]
   			}, function(index){
   				layer.close(index);
   				$("#processBar").addClass("loader loader-bar is-active");
   				$.ajax({
   				    url: "${pageContext.request.contextPath }/index/clearIndex.do",
   				    type: "POST",
   				    dataType: "json",
   				    success: function(data) {
   				    	$("#processBar").removeClass();
   				    	layer.confirm(data.data,{
							btn:['确定']
						},function(){
								$("#queryForm").attr("action","${pageContext.request.contextPath}/index/indexImportUI.html");
								$("#queryForm").submit();
							}
						)
   				    }
   				});
   			},function() {
   				layer.close();
   			});
    	}
    	
    	// 清空选中的索引库
    	function clearSignalIndex(){
    		var id = [];
    		var flag;
    		var num;
    		$('input[name="chkItem"]:checked').each(function() {
    			var arr = $(this).val().split(",");
    			if(arr[1] != '1'){
    				flag = true;
    				num = arr[2];
    				return;
    			}
    			id.push(arr[0]);
    		});
    		if(flag){
				layer.alert("对不起，序号"+num+"未建立索引不能够清除");
    			return;
    		}
    		var ids = id.toString();
    		if(id.length > 0) {
    			layer.confirm('您确定要清空选中的索引库吗?', {
    				title: '提示',
    				shade: 0.01
    			}, function(index) {
    				layer.close(index);
    				$.ajax({
    					url: "${pageContext.request.contextPath}/index/deleteSignalIndex.do",
    					type: "POST",
    					data: {
    						id: ids
    					},
    					success: function(data) {
    						layer.confirm(data.data,{
    							btn:['确定']
    						},function(){
    								$("#queryForm").attr("action","${pageContext.request.contextPath}/index/indexImportUI.html");
    								$("#queryForm").submit();
    							}
    						)
    					},
    					error: function() {

    					}
    				});
    			});
    		} else {
    			layer.alert("请选择要清除索引库的版块", {
    				offset: ['222px', '255px'],
    				shade: 0.01
    			});
    		}
    	}
    	
    	// 添加到索引库
    	function addToIndex(){
    		var id = [];
    		$('input[name="chkItem"]:checked').each(function() {
    			var arr = $(this).val().split(",");
    			id.push(arr[0]);
    		});
    		var ids = id.toString();
    		if(id.length > 0) {
    			layer.confirm('您确定要将选中文章添加到索引库吗?', {
    				title: '提示',
    				shade: 0.01
    			}, function(index) {
    				layer.close(index);
    				$.ajax({
    					url: "${pageContext.request.contextPath}/index/addSignalIndex.do",
    					type: "POST",
    					data: {
    						id: ids
    					},
    					success: function(data) {
    						layer.confirm(data.data,{
    							btn:['确定']
    						},function(){
    								$("#queryForm").attr("action","${pageContext.request.contextPath}/index/indexImportUI.html");
    								$("#queryForm").submit();
    							}
    						)
    					},
    					error: function() {

    					}
    				});
    			});
    		} else {
    			layer.alert("请选择要添加到索引库的文章", {
    				offset: ['222px', '255px'],
    				shade: 0.01
    			});
    		}
    	}

    	<!--搜索-->
    	function query(){
    		$("#queryForm").attr("action","${pageContext.request.contextPath}/index/indexImportUI.html");
    		$("#queryForm").submit();
    	}
    </script>

  </head>

  <body>

    <!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
          <li>
            <a href="javascript:void(0)"> 首页</a>
          </li>
          <li>
            <a href="javascript:void(0)">信息服务</a>
          </li>
          <li>
            <a href="javascript:void(0)">索引库管理</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>

    <div class="container">
      <div class="headline-v2">
        <h2>索引库管理</h2>
      </div>

	<div class="search_detail">
       <form action="" method="post" id="queryForm" class="mb0">
    	<ul class="demand_list">
    	  <li>
	    	<label class="fl">标题名称：</label>
			<input name="name" type="text" value="${name}"/>
	      </li>
    	  <li>
	    	<label class="fl">是否建立索引：</label>
	    	  <select id="isIndex" name="isIndex" class="w178">
	    	    <option value="">--请选择--</option>
	    	    <option value="1" <c:if test="${'1' eq isIndex}">selected</c:if>>是</option>
	    	    <option value="0" <c:if test="${'0' eq isIndex}">selected</c:if>>否</option>
	    	  </select>
	      </li>
	    	<button type="button" onclick="query()" class="btn fl mt1">查询</button>
	    	<button type="reset" class="btn fl mt1 ml5">重置</button>  	
    	</ul>
    	  <div class="clear"></div>
       </form>
     </div>

      <input type="hidden" id="depid" name="depid">
	
      <div class="col-md-12 pl20 mt10">
        <button class="btn btn-windows edit" type="button" onclick="importAll()">文章全量导入索引库</button>
        <button class="btn btn-windows delete" type="button" onclick="clearIndex()">清空全部索引库</button>
        <button class="btn btn-windows delete" type="button" onclick="clearSignalIndex()">清空选中索引库</button>
        <button class="btn btn-windows add" type="button" onclick="addToIndex()">添加到索引库</button>
      </div>
	  <div id="processBar" style="padding: 5px" class="" data-text>
      </div>
      
      <div class="content table_box">
    	<table class="table table-bordered table-condensed table-hover table-striped">
		<thead>
		<tr>
		  <th class="w30 info"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
		  <th class="w50 info">序号</th>
		  <th class="info">标题名称</th>
		  <th class="info">发布时间</th>
		  <th class="info">浏览量</th>
		  <th class="info">下载量</th>
		  <th class="info">发布人</th>
		  <th class="info">发布范围</th>
		  <th class="info">是否索引</th>
		</tr>
		</thead>
		<tbody>
			<c:forEach items="${ info.list }" var="article" varStatus="vs">
				<tr>
				  <td class="tc w30"><input onclick="check()" type="checkbox" name="chkItem" value="${article.id },${article.isIndex},${(vs.index+1)+(info.pageNum-1)*(info.pageSize)}" /></td>
				  <td class="tc w50">${(vs.index+1)+(info.pageNum-1)*(info.pageSize)}</td>
				  <td class="tl">
				  	<c:set value="${article.name}" var="name"></c:set>
					<c:set value="${fn:length(name)}" var="length"></c:set>
					<c:if test="${length>50}">
						${fn:substring(name,0,50)}......
					</c:if>
					<c:if test="${length<=50}">
						${ article.name }
					</c:if>
				  </td>
				  <td class="tc">
				  	<fmt:formatDate value="${article.publishedAt}" pattern="yyyy-MM-dd HH:mm:ss"/>
				  </td>
				  <td class="tc">${article.showCount}</td>
				  <td class="tc">${article.downloadCount}</td>
				  <td class="tc">${article.publishedName}</td>
				  <td class="tc">
				  	<c:if test="${article.range == 0}">内网</c:if>
				  	<c:if test="${article.range == 1}">外网</c:if>
				  	<c:if test="${article.range == 2}">内外网</c:if>
				  </td>
				  <td class="tc">
				  	<c:if test="${article.isIndex == 1}">是</c:if>
				  	<c:if test="${article.isIndex == 0}">否</c:if>
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