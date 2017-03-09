<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
	<title>供应商报价列表页面</title>
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
	
	$(function() {
		laypage({
          cont: $("#pagediv"), 
          pages: "${listInfo.pages}", 
          skin: '#2c9fA6',
          skip: true, 
          total: "${listInfo.total}",
          startRow: "${listInfo.startRow}",
          endRow: "${listInfo.endRow}",
          groups: "${listInfo.pages}" >= 5 ? 5 : "${listInfo.pages}", 
          curr: function() { 
            var page = location.search.match(/page=(\d+)/);
            return page ? page[1] : 1;
          }(),
          jump: function(e, first) {
            if(!first) {
              var articleTypeId = "${article.articleType.id}";
              var range = $("#range").val();
              var status = $("#status").val();
              var name = "${articleName}";
              window.location.href = "${pageContext.request.contextPath }/article/serch.html?page=" + e.curr + "&articleTypeId=" + articleTypeId + "&range=" + range + "&status=" + status + "&name=" + name;
            }
          }
        });
	});
	
</script>
</head>
<body>

<!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0)">提供单价</a></li><li><a href="javascript:void(0)">供应商报价列表</a></li>
		   </ul>
        <div class="clear"></div>
      </div>
    </div>
    
<!-- 竞价信息列表页面开始 -->
	<div class="container">
    <div class="search_detail">
       <form action="" method="post" class="mb0">
    	<ul class="demand_list">
    	  <li>
	    	<label class="fl">竞价标题：</label>
			<input type="text" id="topic" class=""/>
	      </li>
	      </li>
    	  <li>
	    	<label class="fl">发布时间：</label>
			<input type="text" id="topic" class=""/>
	      </li> 
	    	<button type="button" onclick="query()" class="btn">查询</button>
	    	<button type="reset" class="btn">重置</button>  	
    	</ul>
    	  <div class="clear"></div>
       </form>
     </div>
     
<!-- 表格开始 -->
	<div class="col-md-12 pl20 mt10">
	</div>   
	<div class="content table_box">
    	<table class="table table-bordered table-condensed table-hover table-striped">
		<thead>
		<tr>
		  <th class="w30 info"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
		  <th class="w50 info">序号</th>
		  <th class="info">竞价标题</th>
		  <th class="info">报价开始时间</th>
		  <th class="info">报价截止时间</th>
		  <th class="info">状态</th>
		  <th class="info">恢复操作</th>
		</tr>
		</thead>
		<c:forEach  items="${listInfo.list}" var="quote" varStatus="vs">
		<tr>
		  <td class="tc w30"><input onclick="check()" type="checkbox" name="chkItem" value="" /></td>
		  <td class="tc w50">${vs.index + 1 }</td>
		  <td><a href="javascript:void(0)">${quote.name }</a></td>
		  <td class="tc">${quote.startTime }</td>
		  <td class="tc">${quote.endTime }</td>
		  <td class="tc">${quote.status }</td>
		  <td class="tc"><a href="javascript:void(0)">${quote.name }</a></td>
		</tr>
		</c:forEach>
	</table>
   </div>
      <div id="pagediv" align="right"></div>
   </div>

</body>
</html>