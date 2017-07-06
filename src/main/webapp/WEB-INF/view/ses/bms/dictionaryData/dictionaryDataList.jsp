<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
<script type="text/javascript">
  	function show(code,page){
  	$("#kind").val(code);
	$.ajax({
    		contentType: "application/json;charset=UTF-8",
    		url:"${pageContext.request.contextPath}/dictionaryData/showList.do?kind="+code+"&page="+page,
    	    type:"POST",
    	    dataType: "json",
    	    success:function(data) {
    	    	var pageInfo = data.pageInfo;
    	    	var list = data.list;
    	    	var kind = data.kind;
    	    	$("#kind").val(kind);
    	    	$("#pages").val(pageInfo.pages);
    	    	$("#total").val(pageInfo.total);
    	    	$("#startRow").val(pageInfo.startRow);
    	    	$("#endRow").val(pageInfo.endRow);
    	    	$("#pageNum").val(pageInfo.pageNum);
                if (data) {
                    var tabhtml = "";
                    tabhtml +='<h2 class="search_detail ml0">'; 
                    tabhtml +='<ul class="demand_list" id = "form1"><li class="fl"><label class="fl">编码：</label><span><input type="text" id="code" value="" name="code" class=""/></span></li>'; 
                    tabhtml +='<li class="fl"><label class="fl">名称：</label><span><input type="text" id="name" value="" name="name" /></span></li>'; 
                    tabhtml +='<button type="button" onclick="search(1,'+kind+')" class="btn">查询</button><button type="button" onclick="resetQuery()" class="btn">重置</button></ul><div class="clear"></div></h2>';
                    tabhtml +='<div class="col-md-12 col-xs-12 col-sm-12 p0"><button class="btn btn-windows add" type="button" onclick="add()">新增</button><button class="btn btn-windows edit" type="button" onclick="edit()">修改</button><button class="btn btn-windows delete" type="button" onclick="del();">删除</button></div>';
                    tabhtml +='<div class="content table_box pl0"><table class="table table-bordered table-condensed table-hover table-striped">';
           			tabhtml +='<thead><tr><th class="info w30 tc"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>';
					tabhtml +='<th class="info w50">序号</th>';
					/* tabhtml +='<th class="info w50">排序</th>'; */
					tabhtml +='<th class="info" width="40%">编码</th>';
					tabhtml +='<th class="info">名称</th></tr></thead>';
					for(var i =0;i<list.length;i++){
						tabhtml +='<tr><td class="tc"><input onclick="check()" type="checkbox" name="chkItem" value="'+list[i].id+'" /></td>';
						tabhtml +='<td class="tc">'+(i+1+(pageInfo.pageNum-1)*(pageInfo.pageSize))+'</td>';
						/* tabhtml +='<td class="tc" >'+list[i].position+'</td>'; */
						tabhtml +='<td class="tl" >'+list[i].code+'</td>';
						tabhtml +='<td class="tl">'+list[i].name+'</td>';
						tabhtml +='</tr>';
					}
					tabhtml +='</table></div>';
					tabhtml +='<div id="pagediv" align="right"></div>';
	            	$("#show_content_div").html("");
	            	$("#show_content_div").append(tabhtml);
	            	
	            	laypage({
	    			    cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
	    			    pages: pageInfo.pages, //总页数
	    			    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
	    			    skip: true, //是否开启跳页
	    			    total: pageInfo.total,
	    			    startRow: pageInfo.startRow,
	    			    endRow: pageInfo.endRow,
	    			    groups: pageInfo.pages >=5?5:pageInfo.pages, //连续显示分页数
	    			    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
	    			        var page = location.search.match(/page=(\d+)/);
	    			        if(page==null){
	    			    		page = {};
	    			    		page[0]=pageInfo.pageNum;
	    			    		page[1]=pageInfo.pageNum;
	    			    	}
	    			        return page ? page[1] : 1;
	    			    }(), 
	    			    jump: function(e, first){ //触发分页后的回调
	    			        if(!first){ //一定要加此判断，否则初始时会无限刷新
	    			        	show(kind,e.curr);
	    			        
	    			        }
	    			    }
	    			});
                }
            }

    	});
  		
  	}

  	function search(page,kind){
  		var name= $("#name").val();
  		var code= $("#code").val();
  		$.ajax({
  	    		contentType: "application/json;charset=UTF-8",
  	    		url:"${pageContext.request.contextPath}/dictionaryData/showList.do?name="+name+"&page="+page+"&code="+code+"&kind="+kind,
  	    	    type:"POST",
  	    	    dataType: "json",
  	    	    success:function(data) {
  	    	    	var pageInfo = data.pageInfo;
  	    	    	var list = data.list;
  	    	    	var kind = data.kind;
  	    	    	$("#kind").val(kind);
  	    	    	$("#pages").val(pageInfo.pages);
  	    	    	$("#total").val(pageInfo.total);
  	    	    	$("#startRow").val(pageInfo.startRow);
  	    	    	$("#endRow").val(pageInfo.endRow);
  	    	    	$("#pageNum").val(pageInfo.pageNum);
  	                if (data) {
  	                    var tabhtml = "";
  	                    tabhtml +='<h2 class="search_detail ml0">'; 
  	                    tabhtml +='<ul class="demand_list" id = "form1"><li class="fl"><label class="fl">编码：</label><span><input type="text" id="code" value="" name="code" class=""/></span></li>'; 
  	                    tabhtml +='<li class="fl"><label class="fl">名称：</label><span><input type="text" id="name" value="" name="name" /></span></li>'; 
  	                    tabhtml +='<button type="button" onclick="search(1,'+kind+')" class="btn">查询</button><button type="button" onclick="resetQuery()" class="btn">重置</button></ul><div class="clear"></div></h2>';
  	                    tabhtml +='<div class="col-md-12 col-xs-12 col-sm-12 p0"><button class="btn btn-windows add" type="button" onclick="add()">新增</button><button class="btn btn-windows edit" type="button" onclick="edit()">修改</button><button class="btn btn-windows delete" type="button" onclick="del();">删除</button></div>';
  	                    tabhtml +='<div class="content table_box pl0"><table class="table table-bordered table-condensed table-hover table-striped">';
  	           			tabhtml +='<thead><tr><th class="info w30"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>';
  						tabhtml +='<th class="info w50">序号</th>';
  						tabhtml +='<th class="info">排序</th>';
  						tabhtml +='<th class="info">编码</th>';
  						tabhtml +='<th class="info">名称</th></tr></thead>';
  						for(var i =0;i<list.length;i++){
  							tabhtml +='<tr><td class="tc"><input onclick="check()" type="checkbox" name="chkItem" value="'+list[i].id+'" /></td>';
  							tabhtml +='<td class="tc">'+(i+1+(pageInfo.pageNum-1)*(pageInfo.pageSize))+'</td>';
  							tabhtml +='<td class="tc" >'+list[i].position+'</td>';
  							tabhtml +='<td class="tc" >'+list[i].code+'</td>';
  							tabhtml +='<td class="tc">'+list[i].name+'</td>';
  							tabhtml +='</tr>';
  						}
  						tabhtml +='</table></div>';
  						tabhtml +='<div id="pagediv" align="right"></div>';
  		            	$("#show_content_div").html("");
  		            	$("#show_content_div").append(tabhtml);
  		            	$("#name").val(name);
  		            	$("#code").val(code);
  		            	laypage({
  		    			    cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
  		    			    pages: pageInfo.pages, //总页数
  		    			    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
  		    			    skip: true, //是否开启跳页
  		    			    total: pageInfo.total,
  		    			    startRow: pageInfo.startRow,
  		    			    endRow: pageInfo.endRow,
  		    			    groups: pageInfo.pages >=5?5:pageInfo.pages, //连续显示分页数
  		    			    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
  		    			        var page = location.search.match(/page=(\d+)/);
  		    			        if(page==null){
  		    			    		page = {};
  		    			    		page[0]=pageInfo.pageNum;
  		    			    		page[1]=pageInfo.pageNum;
  		    			    	}
  		    			        return page ? page[1] : 1;
  		    			    }(), 
  		    			    jump: function(e, first){ //触发分页后的回调
  		    			        if(!first){ //一定要加此判断，否则初始时会无限刷新
  		    			        	search(e.curr,kind);
  		    			        
  		    			        }
  		    			    }
  		    			});
  	                }
  	            }

  	    	});
  	  		
  	  	}
  	
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

   function del(){
		var kind = $("#kind").val();
	   	if(kind!=null&&kind!=""){   
	   		var ids =[]; 
			$('input[name="chkItem"]:checked').each(function(){ 
				ids.push($(this).val()); 
			}); 
			if(ids.length>0){
				layer.confirm('您确定要删除吗?', {title:'提示',offset: '222px',shade:0.01}, function(index){
					layer.close(index);
					window.location.href="${pageContext.request.contextPath}/dictionaryData/deleteSoft.html?ids="+ids;
				});
			}else{
				layer.alert("请选择",{offset: '222px', shade:0.01});
			}
	   	}else{
			layer.alert("请选择一个字典类型",{offset: '222px', shade:0.01});
		}
   }
   
   function add(){
   	var kind = $("#kind").val();
   	if(kind!=null&&kind!=""){   	
	   	window.location.href="${pageContext.request.contextPath}/dictionaryData/add.html?kind="+kind;
   	}else{
		layer.alert("请选择一个字典类型",{offset: '222px', shade:0.01});
	}
   }
   
   function edit(){
   	var id=[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			id.push($(this).val());
		}); 
		if(id.length==1){
			var currPage = $("#pageNum").val();
			window.location.href="${pageContext.request.contextPath}/dictionaryData/edit.html?id="+id+"&page="+currPage;
		}else if(id.length>1){
			layer.alert("只能选择一个",{offset: '222px', shade:0.01});
		}else{
			layer.alert("请选择",{offset: '222px', shade:0.01});
		}
   }
   
   
	function resetQuery(){
		$("#form1").find(":input").not(":button,:submit,:reset,:hidden").val("").removeAttr("checked").removeAttr("selected");
	}
	
	$(function(){
		var kind = "${kind}";
		if(kind!=null&&kind!=""){
			show(kind,1);
		}
	})
</script>
</head>

<body>
  
<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		<ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a></li>
		   <li><a href="javascript:void(0);">支撑系统</a></li><li><a href="javascript:void(0);">后台管理</a></li>
		   <li><a href="javascript:void(0);">数据字典</a></li>
		   <li class="active"><a href="javascript:jumppage('${pageContext.request.contextPath}/dictionaryData/dictionaryDataList.html')">数据字典管理</a></li>
		</ul>
	  </div>
   </div>

   <div class="container content height-350">
       <div class="row">
                <!-- Begin Content -->
                <div class="col-md-12 col-sm-12 col-xs-12">
                <div class="headline-v2"><h2>数据字典</h2></div>
					<div class="col-md-2 col-sm-4 col-xs-12 mt5" id="show_tree_div">
							<ul id="ztree_show" class="btn_list p0">
								<!-- 菜单树-->
								<c:forEach items="${list}" var="dt" varStatus="vs">
									<li id="ztree_show_1" class="level0" tabindex="0" hidefocus="true" treenode="">
<!-- 									<span id="ztree_show_1_span">·</span> -->
									<a id="ztree_show_1_a" class="level0" href="javascript:void(0);" onclick="show('${dt.code}',1)"  title="${dt.name }">
										<span id="ztree_show_1_span">${dt.name }</span>
									</a>
									</li>
								</c:forEach>
							</ul>
					</div>

					<input type="hidden" id="mid">
					<input name="kind" type="hidden" id="kind" value="">
                    <input name="pages" type="hidden" id="pages" value="">
                    <input name="total" type="hidden" id="total" value="">
                    <input name="startRow" type="hidden" id="startRow" value="">
                    <input name="endRow" type="hidden" id="endRow" value="">
                    <input name="pageNum" type="hidden" id="pageNum" value="">
					<div class="tag-box tag-box-v4 col-md-10 col-sm-8 col-xs-12 mt5">
						
						<div class="col-md-12 col-xs-12 col-sm-12 p0"  id="show_content_div">
						    <div id="pagediv" class="hide" align="right"></div>
						</div>
						
			        </div>
             	 </div>
       </div>
   </div>
</body>
</html>
