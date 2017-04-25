<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE html>
<html>
  <head>
	<%@ include file="/WEB-INF/view/common.jsp" %>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/public/webupload/js/display.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/public/upload/upload.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/public/upload/upload.css" type="text/css" />  
    <link rel="stylesheet" href="${pageContext.request.contextPath}/public/webupload/css/uploadView.css" type="text/css" />
    <script type="text/javascript">
	  $(function(){
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
		      		location.href = "${pageContext.request.contextPath}/product_lib/findAllProductLibBasicInfo.html?name=${name}&&status=${status}&&page=" + e.curr;
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
		
		// 录入产品信息
		function add(){
	    	window.location.href="${pageContext.request.contextPath}/product_lib/addSMSProductUI.html";
		}
		
		// 查看基本信息
	  	function show(id){
	  		window.location.href="${pageContext.request.contextPath}/product_lib/findSignalProductInfo.html?id="+id;
	  	}
		
		// 修改
	    function edit(){
	    	var id=[]; 
			$('input[name="chkItem"]:checked').each(function(){ 
				id.push($(this).val());
			}); 
			if(id.length==1){
				var flag = id[0].split(':');
				if(flag[1] !=0 && flag[1] !=2 ){
					layer.alert("只能修改暂存和审核未通过的产品信息");
					return;
				}
				window.location.href="${pageContext.request.contextPath}/product_lib/editSignalProductInfoUI.html?id="+flag[0];
			}else if(id.length>1){
				layer.alert("只能选择一个",{offset: ['222px', '390px'], shade:0.01});
			}else{
				layer.alert("请选择需要修改的产品",{offset: ['222px', '390px'], shade:0.01});
			}
	    }
		// 删除
	    function del(){
	    	var ids =[];
	    	var idss=[];
			$('input[name="chkItem"]:checked').each(function(){ 
				ids.push($(this).val()); 
			}); 
			if(ids.length>0){
			    for(var i=0; i< ids.length;i++){
			    	var flag = ids[i].split(':');
			    	if(flag[1] != 0){
			    		layer.alert("只能删除暂存的产品信息");
			    		return;
			    	}
			    	idss.push(flag[0]);
			    }
				layer.confirm('您确定要删除吗?', {title:'提示',offset: ['222px','360px'],shade:0.01}, function(index){
					layer.close(index);
					$.ajax({
						url: "${pageContext.request.contextPath }/product_lib/deleteProductLibInfo.do",
						type: "POST",
						data: {"idss":idss},
						dataType: "json",
						success: function(data) {
							if(data.status==200){
								layer.confirm("删除成功",{
									btn:['确定']
								},function(){
										// 确认后加载商品信息 
										$("#queryForm").attr("action","${pageContext.request.contextPath}/product_lib/findAllProductLibBasicInfo.html");
										$("#queryForm").submit();
									}
								)
							}
						},
						error: function() {
	
						}
					});
				});
			}else{
				layer.alert("请选择要删除的产品",{offset: ['222px', '390px'], shade:0.01});
			}
	    }
	    function showPic(url,name){
	    	var pic = $("#"+url.toString());
			layer.open({
				  type: 1,
				  title: false,
				  closeBtn: 0,
				  area: '516px',
				  skin: 'layui-layer-nobg', //没有背景色
				  shadeClose: true,
				  content: pic
				});
		};
		
		
		//搜索
		function query(){
			$("#queryForm").attr("action","${pageContext.request.contextPath}/product_lib/findAllProductLibBasicInfo.html");
			$("#queryForm").submit();
		}
		
		//重置按钮事件  
	    function resetAll(){
	        $("#name").val("");  
	        $("#status").val("");  
	    }
  </script>
 </head>
  <body>
	<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0)"> 首页</a></li><li><a href="javascript:void(0)">产品库管理</a></li></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   <div class="container">
	   <div class="headline-v2">
	   		<h2>产品列表</h2>
	   </div>

   <!-- 查询 -->
     <div class="search_detail">
	   	 <form action="" name="queryForm" id="queryForm" method="post">
			 <ul class="demand_list">
			   <li><label class="fl">名称：</label><span><input type="text" value="${ name }" name="name" id="name" class="mb0" /></span></li>
			   <li>
			   	<label class="fl">审核状态：</label>
		    	  <select id="status" name="status" class="w178">
		    	    <option value="">--请选择--</option>
		    	    <option value="0" <c:if test="${'0' eq status}">selected</c:if>>暂存</option>
		    	    <option value="1" <c:if test="${'1' eq status}">selected</c:if>>待审核</option>
		    	    <option value="2" <c:if test="${'2' eq status}">selected</c:if>>审核未通过</option>
		    	    <option value="3" <c:if test="${'3' eq status}">selected</c:if>>审核通过</option>
		   	  	  </select>
			   </li>
			   <button type="button" onclick="query()" class="btn fl mt1">查询</button>
	    	   <button onclick="resetAll()" class="btn fl ml5 mt1">重置</button> 
			 </ul>
		 </form>
	 <div class="clear"></div>
    </div>

   
	<!-- 表格开始-->

    <div class="col-md-12 pl20 mt10">
   		<button class="btn btn-windows add" type="button" onclick="add()">录入</button>
		<button class="btn btn-windows edit" type="button" onclick="edit()">修改</button>
		<button class="btn btn-windows delete" type="button" onclick="del()">删除</button>      
   </div>
   <div class="content table_box">
    	<table class="table table-bordered table-condensed">
		<thead>
		<tr>
		  <th class="info w30"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
		  <th class="info w50">序号</th>
		  <th class="info">名称</th>
		  <th class="info">价格(元)</th>
		  <th class="info">型号</th>
		  <th class="info">商品品目</th>
		  <th class="info">审核状态</th>
		</tr>
		</thead>
		<c:forEach items="${info.list}" var="p" varStatus="vs">
			<tr>
				<td class="tc"><input onclick="check()" type="checkbox" name="chkItem" value="${p.id}:${p.status}" /></td>
				<td class="tc pointer" onclick="show('${p.id},${p.status}')">${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
				<td class="tl pl20 pointer" onclick="show('${p.id},${p.status}')">${p.name}</td>
				<td class="tl pl20 pointer" onclick="show('${p.id},${p.status}')">
					<fmt:formatNumber value='${p.price}' pattern='#,##,###.00'/>
				</td>
				<td class="tl pl20 pointer" onclick="show('${p.id},${p.status}')">${p.typeNum}</td>
				<td class="tl pl20 pointer" onclick="show('${p.id},${p.status}')">${p.category.name}</td>
				<td class="tc pointer" onclick="show('${p.id},${p.status}')">
					<c:if test="${ p.status == 0 }">
						暂存
					</c:if>
					<c:if test="${ p.status == 1 }">
						待审核
					</c:if>
					<c:if test="${ p.status == 2 }">
						审核未通过
					</c:if>
					<c:if test="${ p.status == 3 }">
						审核通过
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
