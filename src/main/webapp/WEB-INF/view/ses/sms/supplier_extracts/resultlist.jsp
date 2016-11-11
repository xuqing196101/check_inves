<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
<script src="<%=basePath%>public/layer/extend/layer.ext.js"></script>
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
    	  btn: ['确定','取消'],offset: ['100px', '200px'], shade:0.01
    	}, function(index){
    		var strs= new Array();
    		var v=select.value;
    		 strs=v.split(",");
    		 layer.close(index);
    		if(strs[2]=="3"){
    			layer.prompt({
    				  formType: 2,
    				  shade:0.01,
    				  title: '不参加理由'
    				}, function(value, index, elem){
    				     ajaxs(select.value,value);
    				     layer.close(index);
    				});
    		}else{
    		select.disabled=true;
    		   ajaxs(select.value,'');
    		}
    	}, function(index){
    		layer.close(index);
    	});
    }
    
   function ajaxs(id,v){
	   $.ajax({
           type: "POST",
           url: "<%=basePath%>SupplierExtracts/resultextract.do",
           data: {id:id,reason:v},
           dataType: "json",
           success: function(data){
                       list=data;
                       if('sccuess'==list){
                           alert("ss");
                       }else{
                       var tex='';
                       for(var i=0;i<list.length;i++){
                           if(list[i]!=null){
                        	   if(list[0]!=null){
                                   var html="";
                                   $("#extcontype").empty();
                                   for(var l=0;l<list[0].conType.length;l++){
                                	   
                                	   if(list[0].conType[l].categoryName!=null){
                                		   var cName=list[0].conType[l].categoryName.replace("^",",");
                                		   cName=cName.substring(0,cName.length-1);
                                		   html+="抽取品目:"+cName+",";
                                	   }
                                	   if(list[0].conType[l].isMulticondition==1){
                                		  html+="满足一个条件,"; 
                                	   }else if(list[0].conType[l].isMulticondition==2){
                                		   html+="满足多个条件,";
                                	   }
                                        html+="抽取数量:"+list[0].conType[l].alreadyCount+"/"+list[0].conType[l].supplieCount;
                                       html+="<br/>";
                                   }
                                   $("#extcontype").append(html);
                               } 
                        	   
                           tex+="<tr class='cursor'>"+
                               "<td class='tc' onclick='show();'>"+(i+1)+"</td>"+
                               "<td class='tc' onclick='show();'>"+list[i].supplier.supplierName+"</td>"+
                               "<td class='tc' onclick='show();'>"+list[i].supplier.supplierName+"</td>"+
                               "<td class='tc' onclick='show();'>"+list[i].supplier.contactName+"</td>"+
                               "<td class='tc' onclick='show();'>"+list[i].supplier.contactTelephone+"</td>"+
                               "<td class='tc' onclick='show();'>"+list[i].supplier.contactMobile+"</td>"+
                           " <td class='tc' >"+
                             "<select id='select' onchange='operation(this);'>";
                             
                              if(list[i].operatingType==1){
                                  tex+="<option value='"+list[i].id+","+list[i].supplierConditionId+",1' selected='selected' disabled='disabled'>能参加</option>";
                              }else if(list[i].operatingType==2){
                                  tex+="<option value='"+list[i].id+","+list[i].supplierConditionId+",1'>能参加</option>"+
                                  "<option value='"+list[i].id+","+list[i].supplierConditionId+",3'>不能参加</option>"+
                                  "<option selected='selected' value='"+list[i].id+","+list[i].supplierConditionId+",2'>待定</option>";
                              }else if(list[i].operatingType==3){
                                  tex+="<option value='"+list[i].id+","+list[i].supplierConditionId+",1' selected='selected' disabled='disabled'>不能参加</option>";
                              }else{
                                  tex+= "<option >请选择</option>"+
                                      "<option value='"+list[i].id+","+list[i].supplierConditionId+",1'>能参加</option>"+
                                  "<option value='"+list[i].id+","+list[i].supplierConditionId+",3'>不能参加</option>"+
                                  "<option  value='"+list[i].id+","+list[i].supplierConditionId+",2'>待定</option>";
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
   }
  </script>
<body>

	<!--=== Content Part ===-->
	<div class="container content height-350">
		<div class="row">
			<!-- Begin Content -->
			<div class="col-md-12" id="count" style="min-height: 400px;">
				<div id="extcontype">
					<c:forEach var="con" items="${extConType}">
						<c:if test="${con.categoryName != null && con.categoryName != ''}">
                                                                 抽取品目 :${fn:replace(con.categoryName, "^", ",")}
                    </c:if>
						<c:if test="${con.isMulticondition != null && isMulticondition != ''}">

							<c:if test="${con.isMulticondition==1}">
                            满足一个条件,
                                                               
                    </c:if>
							<c:if test="${con.isMulticondition==2}">
                          满足多个条件,                             
                    </c:if>
                                                                    抽取数量${con.alreadyCount}/${con.supplieCount }
                        </c:if>
						<br />
					</c:forEach>
				</div>
				<div class="col-md-12" style="min-height: 400px;">

					<div class="clear"></div>
					<table id="table" class="table table-bordered table-condensed">
						<thead>
							<tr>
								<th class="info w50">序号</th>
								<th class="info">供应商名称</th>
								<th class="info">类型，级别</th>
								<th class="info">联系人名称</th>
								<th class="info">联系人电话</th>
								<th class="info">联系人手机</th>
								<th class="info">操作</th>
							</tr>
						</thead>
						<tbody id="tbody">
							<c:forEach items="${extRelateListYes}" var="listyes"
								varStatus="vs">
								<tr class='cursor '>
									<td class='tc' onclick='show();'>${vs.index+1}</td>
									<td class='tc' onclick='show();'>${listyes.supplier.supplierName}</td>
									<td class='tc' onclick='show();'>${listyes.supplier.supplierName}</td>
									<td class='tc' onclick='show();'>${listyes.supplier.contactName}</td>
									<td class='tc' onclick='show();'>${listyes.supplier.contactTelephone}</td>
									<td class='tc' onclick='show();'>${listyes.supplier.contactMobile}</td>
									<td class='tc'><select id='select'
										onchange='operation(this);'>
											<c:choose>
												<c:when test="${listyes.operatingType==1}">
													<option selected="selected" disabled="disabled"
														value='${listyes.id},${listyes.supplierConditionId},1'>能参加</option>
												</c:when>
												<c:when test="${listyes.operatingType==2}">
													<option
														value='${listyes.id},${listyes.supplierConditionId},1'>能参加</option>
													<option
														value='${listyes.id},${listyes.supplierConditionId},3'>不能参加</option>
													<option selected="selected" disabled="disabled"
														value='${listyes.id},${listyes.supplierConditionId},2'>待定</option>
												</c:when>
												<c:when test="${listyes.operatingType==3}">
													<option selected="selected" disabled="disabled"
														value='${listyes.id},${listyes.supplierConditionId},3'>不能参加</option>
												</c:when>
												<c:otherwise>
													<option>请选择</option>
													<option
														value='${listyes.id},${listyes.supplierConditionId},1'>能参加</option>
													<option
														value='${listyes.id},${listyes.supplierConditionId},3'>不能参加</option>
													<option
														value='${listyes.id},${listyes.supplierConditionId},2'>待定</option>
												</c:otherwise>
											</c:choose>
									</select></td>
								</tr>
							</c:forEach>
							<c:forEach items="${extRelateListNo }" var="listno"
								varStatus="vs">
								<tr class='cursor'>
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
				</div>
			</div>
			<!-- End Content -->
		</div>
		<!--/container-->
		<!--=== End Content Part ===-->
</body>
</html>
