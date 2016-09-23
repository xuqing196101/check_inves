<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>站内消息</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link href="<%=basePath%>public/supplier/css/supplieragents.css"
	media="screen" rel="stylesheet">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
</head>
<script src="<%=basePath%>public/layer/layer.js"></script>
<script src="<%=basePath%>public/laypage-v1.3/laypage/laypage.js"></script>
<script type="text/javascript">
  $(function(){
	  laypage({
		    cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
		    pages: "${listStationMessage.pages}", //总页数
		    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
		    skip: true, //是否开启跳页
		    groups: "${listStationMessage.pages}">=3?3:"${listStationMessage.pages}", //连续显示分页数
		    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
// 		        var page = location.search.match(/page=(\d+)/);
		        return "${listStationMessage.pageNum}";
		    }(), 
		    jump: function(e, first){ //触发分页后的回调
		        if(!first){ //一定要加此判断，否则初始时会无限刷新
		        	$("#pages").val(e.curr);
		        	$("form:first").submit();
		        }
		    }
		});
  });
  	/** 全选全不选 */
	function selectAll(){
		 var checklist = document.getElementsByName("chkItem");
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
  		window.location.href="<%=basePath%>StationMessage/showStationMessage.do?id="+id+"&&type='view'";
  	}
    function edit(){
    	var id=[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			id.push($(this).val());
		}); 
		if(id.length==1){
			window.location.href="<%=basePath%>StationMessage/showStationMessage.do?id="+id+"&&type='edit'";
		}else if(id.length>1){  
			layer.alert("只能选择一个",{offset: ['222px', '390px'], shade:0.01});
		}else{
			layer.alert("请选择需要修改的用户",{offset: ['222px', '390px'], shade:0.01});
		}
    }
    function del(){
    	var ids =[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			ids.push($(this).val()); 
		}); 
		if(ids.length>0){
			layer.confirm('您确定要删除吗?', {title:'提示',offset: ['222px','360px'],shade:0.01}, function(index){
				layer.close(index);
				window.location.href="<%=basePath%>StationMessage/deleteSoftSMIsDelete.do?ids="+ids;
			});
		}else{
			layer.alert("请选择要删除的用户",{offset: ['222px', '390px'], shade:0.01});
		}
    }
    function add(){
    	window.location.href="<%=basePath%>StationMessage/showInsertSM.do";
    }
    function show(id){
<%--     	window.location.href="<%=basePath%>StationMessage/showStationMessage.do?id="+id+"&&type=view"; --%>
    }
    function continues(id){
    	   window.location.href="<%=basePath%>SupplierExtracts/conditions.do?id="+id;
    }
    function operation(select){
    	layer.confirm('确定本次操作吗？', {
    	  btn: ['确定','取消'],offset: ['222px', '390px'], shade:0.01
    	}, function(index){
    		
    		select.disabled=true;
    		 layer.close(index);
    		    $.ajax({
    	             type: "POST",
    	             url: "<%=basePath%>SupplierExtracts/resultSupplier.do",
    	             data: {id:select.value},
    	             dataType: "json",
    	             success: function(data){
    	                         list=data;
    	                         if('sccuess'==list){
    	                        	 alert("ss");
    	                         }else{
    	                         var tex='';
    	                         for(var i=0;i<list.length;i++){
    	                        	 if(list[i]!=null){
    	                        	 tex+="<tr class='cursor'>"+
                                     "<td onclick='null' class='tc'><input onclick='check()'"+
                                         "type='checkbox' name='chkItem' value='"+list[i].id+"' /></td>"+
                                         "<td class='tc' onclick='show();'>"+(i+1)+"</td>"+
                                         "<td class='tc' onclick='show();'>"+list[i].suppliers.supplierName+"</td>"+
                                         "<td class='tc' onclick='show();'>"+list[i].suppliers.contactName+"</td>"+
                                         "<td class='tc' onclick='show();'>"+list[i].suppliers.contactName+"</td>"+
                                         "<td class='tc' onclick='show();'>"+list[i].suppliers.contactTelephone+"</td>"+
                                         "<td class='tc' onclick='show();'>"+list[i].suppliers.contactMobile+"</td>"+
                                     " <td class='tc' >"+
                                       "<select id='select' onchange='operation(this);'>";
                                       
                                        if(list[i].operatingType==1){
                                        	tex+="<option value='"+list[i].id+","+list[i].extractsId+",1' selected='selected' disabled='disabled'>能参加</option>";
                                        }else if(list[i].operatingType==2){
                                        	tex+="<option value='"+list[i].id+","+list[i].extractsId+",1'>能参加</option>"+
                                            "<option value='"+list[i].id+","+list[i].extractsId+",3'>不能参加</option>"+
                                            "<option selected='selected' value='"+list[i].id+","+list[i].extractsId+",2'>待定</option>";
                                        }else if(list[i].operatingType==3){
                                        	tex+="<option value='"+list[i].id+","+list[i].extractsId+",1' selected='selected' disabled='disabled'>不能参加</option>";
                                        }else{
                                        	tex+= "<option >请选择</option>"+
                                        		"<option value='"+list[i].id+","+list[i].extractsId+",1'>能参加</option>"+
                                            "<option value='"+list[i].id+","+list[i].extractsId+",3'>不能参加</option>"+
                                            "<option  value='"+list[i].id+","+list[i].extractsId+",2'>待定</option>";
                                        }
                                        tex+="</select>"+
                                      "</td>"+
                                 "</tr>";
    	                         }
    	                         }
                                 $('#tbody tr:lt('+list.length+')').remove();
                                $("#tbody").prepend(tex);
    	                       }
    	             }
    	         });
    	}, function(index){
    		layer.close(index);
    	});
    }
  </script>
<body>

	<!--面包屑导航开始-->
	<div class="margin-top-10 breadcrumbs ">
		<div class="container">
			<ul class="breadcrumb margin-left-0">
				<li><a href="#"> 首页</a></li>
				<li><a href="#">支撑系统</a></li>
				<li><a href="#">后台管理</a></li>
				<li class="active"><a href="#">模版管理</a></li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>
	<div class="container">
		<div class="headline-v2">
			<h2>查看抽取结果</h2>
		</div>
	</div>
	<!--=== End Breadcrumbs ===-->

	<!--=== Content Part ===-->
	<div class="container content height-350">
		<div class="row">
			<!-- Begin Content -->
			<div class="col-md-12" style="min-height: 400px;">
				<div class="col-md-3 md-margin-bottom-40" id="show_tree_div">
					<div class="tag-box tag-box-v3">
						<ul class=" list-unstyled ">
							<li class=" col-md-12 p0"><label class="">计划名称：</label><span><input
									type="text"></span></li>
							<li class=" col-md-12 p0"><label class="">地区：</label> <span
								class="clear">${sextractslist.extractionSites }</span></li>
							<li class="mt10 col-md-12 p0"><label class="">类型：</label> <span
								class="clear">${condition.salesType}</span>
								<div class="clear"></div></li>
							<li class=" mt10 col-md-12 p0"><label class="fl mt5">抽取数量：</label>
								<span>${condition.count}</span></li>
							<li class=" col-md-12 p0"><label class="">供应商级别：</label> <span
								class="clear">
									<div class="ml5 fl">一级以下</div>
							</span></li>
							<div class="clear"></div>
						</ul>
						<div class="mt20 clear">
							<input type="submit" class="btn ml20" value="暂存" /> <input
								type="submit" class="btn ml20" value="打印" />
						</div>
						<div class="mt20 clear">
							<input type="submit" class="btn ml20" value="结束" /> <input
								type="button" onclick="continues('${sextractslist.id}');" class="btn ml20" value="继续抽取" />
						</div>
						<div class="clear"></div>
					</div>
				</div>
				<div class="tag-box tag-box-v4 col-md-9">
					<ul class="demand_list">
						<li class="fl mr8"><label class="fl mt0">项目名称：</label>${sextractslist.projectName }</li>
						<li class="fl mr8"><label class="fl mt0">采购编号：</label>123-002</li>
						<li class="fl mr8"><label class="fl mt0">采购方式：</label><span>邀请招标</span></li>
					</ul>
					<div class="clear"></div>
					<table id="table" class="table table-bordered table-condensed">
						<thead>
							<tr>
								<th class="info w30"><input id="checkAll" type="checkbox"
									onclick="selectAll()" /></th>
								<th class="info w50">序号</th>
								<th class="info">供应商名称</th>
								<th class="info">类型，级别</th>
								<th class="info">联系人</th>
								<th class="info">座机</th>
								<th class="info">手机</th>
								<th class="info">操作</th>
							</tr>
						</thead>
						<tbody id="tbody">
							<c:forEach items="${extRelateListYes}" var="listyes"
								varStatus="vs">
								<tr class='cursor '>
									<td class='tc'><input onclick='check()' type='checkbox'
										name='chkItem' value='${listyes.id}' /></td>
									<td class='tc' onclick='show();'>${vs.index+1}</td>
									<td class='tc' onclick='show();'>${listyes.suppliers.supplierName}</td>
									<td class='tc' onclick='show();'>${listyes.suppliers.contactName}</td>
									<td class='tc' onclick='show();'>${listyes.suppliers.contactName}</td>
									<td class='tc' onclick='show();'>${listyes.suppliers.contactTelephone}</td>
									<td class='tc' onclick='show();'>${listyes.suppliers.contactMobile}</td>
									<td class='tc'><select id='select'
										onchange='operation(this);'>
										<c:choose>
										  <c:when test="${listyes.operatingType==1}">
										       <option selected="selected" disabled="disabled" value='${listyes.id},${listyes.extractsId},1'>能参加</option>
										  </c:when>
										  <c:when test="${listyes.operatingType==3}">
									       <option selected="selected" disabled="disabled"  value='${listyes.id},${listyes.extractsId},2'>待定</option>
										  </c:when>
										  <c:when test="${listyes.operatingType==2}">
										         <option selected="selected" disabled="disabled"  value='${listyes.id},${listyes.extractsId},3'>不能参加</option>
										  </c:when>
										  <c:otherwise>
										     <option>请选择</option>
										     <option value='${listyes.id},${listyes.extractsId},1'>能参加</option>
                                             <option value='${listyes.id},${listyes.extractsId},3'>不能参加</option>
                                             <option value='${listyes.id},${listyes.extractsId},2'>待定</option>
										  </c:otherwise>
										</c:choose>
									</select></td>
								</tr>
							</c:forEach>
							<c:forEach items="${extRelateListNo }" var="listno"
								varStatus="vs">
								<tr class='cursor'>
									<td class='tc'><input onclick='check()' type='checkbox'
										name='chkItem' value='${listno.id}' /></td>
									<td class='tc' onclick='show();'>${(vs.index+1)+1}</td>
									<td class='tc' onclick='show();'>*****</td>
									<td class='tc' onclick='show();'>*****</td>
									<td class='tc' onclick='show();'>*****</td>
									<td class='tc' onclick='show();'>*****</td>
									<td class='tc' onclick='show();'>*****</td>
									<td class='tc'>请选择</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<!--        <div id="pagediv" align="right"></div> -->
				</div>
			</div>
		</div>
		<!-- End Content -->
	</div>
	<!--/container-->
	<!--=== End Content Part ===-->

</body>
</html>
