<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]--><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>专家信息列表</title>
<!-- Meta -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<script src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
<script src="${pageContext.request.contextPath}/public/laypage-v1.3/laypage/laypage.js"></script>
<script type="text/javascript">
   $(function(){
		  laypage({
			    cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
			    pages: "${result.pages}", //总页数
			    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
			    skip: true, //是否开启跳页
			    total: "${result.total}",
			    startRow: "${result.startRow}",
			    endRow: "${result.endRow}",
			    groups: "${result.pages}">=5?5:"${result.pages}", //连续显示分页数
			    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
 			        //var page = location.search.match(/page=(\d+)/);
 			        //return page ? page[1] : 1;
					return "${result.pageNum}";
			    }(), 
			    jump: function(e, first){ //触发分页后的回调
			        if(!first){ //一定要加此判断，否则初始时会无限刷新
			        	$("#page").val(e.curr);
			        	$("#formSearch").submit();
			        }
			    }
			});
	  });
   /** 全选全不选 */
	function selectAll(){
		 var checklist = document.getElementsByName ("check");
		 var checkAll = document.getElementById("allId");
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
 
   	//修改
   	function edit(){
  	  var count = 0;
  	  var ids = document.getElementsByName("check");
   
       for(i=0;i<ids.length;i++) {
     		 if(document.getElementsByName("check")[i].checked){
     		 var id = document.getElementsByName("check")[i].value;
     		var value = id.split(",");
     		 count++;
      }
    }   
    		if(count>1){
    			layer.alert("只能选择一条记录",{offset: ['222px', '390px'],shade:0.01});
    		}else if(count<1){
    			layer.alert("请选择一条记录",{offset: ['222px', '390px'],shade:0.01});
    		}else if(count==1){
    			window.location.href="${pageContext.request.contextPath}/expert/toEditBasicInfo.html?id="+value[0];
       	}
    }
   	//删除
   	function dell(){
   	 var count = 0;
 	  var ids = document.getElementsByName("check");
 	 var id2="";
 	 var num =0;
      for(i=0;i<ids.length;i++) {
    		 if(document.getElementsByName("check")[i].checked){
	    		  id2 += document.getElementsByName("check")[i].value+",";
	    		  num++;
    		  }
    		 //id.push(document.getElementsByName("check")[i].value);
       		 count++;
     }
   	var id = id2.substring(0,id2.length-1);
   		if(num>0){
   			layer.confirm('您确定要删除吗?', {title:'提示',offset: ['222px','360px'],shade:0.01}, function(index){
   	 			layer.close(index);
   	 			$.ajax({
   	 				url:"${pageContext.request.contextPath}/expert/deleteAll.html",
   	 				data:{"ids":id},
   	 				type:"post",
   	 	       		success:function(){
   	 	       			layer.msg('删除成功',{offset: ['222px', '390px']});
   	 		       		window.setTimeout(function(){
   	 		       			window.location.reload();
   	 		       				for(var i = 0;i<info.length;i++){
   	 						info[i].checked = false;
   	 						}
   	 		       		}, 1000);
   	 	       		},
   	 	       		error: function(){
   	 					layer.msg("删除失败",{offset: ['222px', '390px']});
   	 				}
   	 	       	});
   	 		});
   		}else{
   			layer.alert("请选择一条记录",{offset: ['222px', '390px'],shade:0.01});
      	}
   		
   	}
   	function clearSearch(){
   		$("#relName").attr("value","");
   	    //还原select下拉列表只需要这一句
	   	$("#expertsFrom option:selected").removeAttr("selected");
	   	$("#status option:selected").removeAttr("selected");
	   	$("#expertsTypeId option:selected").removeAttr("selected");
   	}
   	//查看信息
   	function view(id){
   		window.location.href="${pageContext.request.contextPath}/expert/view.html?id="+id;
   	}
   	function shenhe(){
    	  var count = 0;
    	  var ids = document.getElementsByName("check");
     
         for(i=0;i<ids.length;i++) {
       		 if(document.getElementsByName("check")[i].checked){
       		 var id = document.getElementsByName("check")[i].value;
       		 var value = id.split(",");
       		 count++;
        }
      }   
      		if(count>1){
      			layer.alert("只能选择一条记录",{offset: ['222px', '390px'],shade:0.01});
      		}else if(count<1){
      			layer.alert("请选择一条记录",{offset: ['222px', '390px'],shade:0.01});
      		}else if(count==1){
      			if(value[1]==0){
      			window.location.href="${pageContext.request.contextPath}/expert/toShenHe.do?id="+value[0];
      			}else{
      				layer.alert("请选择未审核的",{offset: ['222px', '390px'],shade:0.01});
      			}
        
         	}
      }
   	//诚信登记
   	function creadible(){
   	 var count = 0;
	  var ids = document.getElementsByName("check");

    for(i=0;i<ids.length;i++) {
  		 if(document.getElementsByName("check")[i].checked){
  		 var id = document.getElementsByName("check")[i].value;
  		 var value = id.split(",");
  		 count++;
   }
 }   
 		if(count>1){
 			layer.alert("只能选择一条记录",{offset: ['222px', '390px'],shade:0.01});
 		}else if(count<1){
 			layer.alert("请选择一条记录",{offset: ['222px', '390px'],shade:0.01});
 		}else if(count==1){
 			if(value[1]==1){
 				index = layer.open({
 			          type: 2, //page层
 			          area: ['900px', '600px'],
 			          title: '诚信登记',
 			          shade:0.01, //遮罩透明度
 			          moveType: 1, //拖拽风格，0是默认，1是传统拖动
 			          shift: 1, //0-6的动画形式，-1不开启
 			          offset: ['100px', '250px'],
 			          shadeClose: true,
 			          content:"${pageContext.request.contextPath}/credible/findAll.html?id="+value[0]
 			          //数组第二项即吸附元素选择器或者DOM $('#openWindow')
 				 });
 			}else{
 				layer.alert("请选择审核通过的",{offset: ['222px', '390px'],shade:0.01});
 			}
   
    	}
 }
   		
</script>
</head>
<body>
  <div class="wrapper">
<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">业务管理</a></li><li><a href="#">专家列表</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
<!-- 我的订单页面开始-->
   <div class="container">
   <div class="headline-v2">
   <h2>评标专家列表</h2>
   </div>
   </div>
  
   <form action="${pageContext.request.contextPath}/expert/findAllExpert.html"  method="post" id="formSearch"  class="registerform"> 
  <input type="hidden" name="page" id="page">
  <input type="hidden" name="flag" value="0">
   <div align="center">
                    <table>
                    <tr>
                    <td>
                    <span>姓名：</span><input type="text" id="relName" name="relName" value="${expert.relName }">
                    </td>
                    <td>
						 <span>来源：</span>
						   <select  name="expertsFrom" id="expertsFrom" class="w178">
						    <option selected="selected" value=''>-请选择-</option>
						   	<option <c:if test="${expert.expertsFrom =='军队' }">selected</c:if> value="军队">军队</option>
						   	<option <c:if test="${expert.expertsFrom =='地方' }">selected</c:if> value="地方">地方</option>
						   	<option <c:if test="${expert.expertsFrom =='其他' }">selected</c:if> value="其他">其他</option>
						   </select>
					</td>
					<td>
                      	<span>状态：</span>
							   <select name="status" id="status">
							   		<option selected="selected" value=''>-请选择-</option>
							   		<option <c:if test="${expert.status =='0' }">selected</c:if> value="0">未审核</option>
							   		<option <c:if test="${expert.status =='1' }">selected</c:if> value="1">审核通过</option>
							   		<option <c:if test="${expert.status =='2' }">selected</c:if> value="2">审核未通过</option>
							   		<option <c:if test="${expert.status =='3' }">selected</c:if> value="3">退回修改</option>
							   </select>
                     </td>
                     <td> 	
                         		<span >专家类型：</span>
							   <select name="expertsTypeId" id="expertsTypeId">
							   		<option selected="selected"  value=''>-请选择-</option>
							   		<option <c:if test="${expert.expertsTypeId =='1' }">selected</c:if> value="1">技术</option>
							   		<option <c:if test="${expert.expertsTypeId =='2' }">selected</c:if> value="2">法律</option>
							   		<option <c:if test="${expert.expertsTypeId =='3' }">selected</c:if> value="3">商务</option>
							   </select>
					</td>
					<td>
                          <input class="btn"  value="搜索" type="submit">
                          <input class="btn" id="button" onclick="clearSearch();" value="重置" type="reset">
                     </td>
                        </tr>
                        </table>
                   </form>
                  </div>  
<!-- 表格开始-->
   <div class="container">
   <div class="col-md-8">
    <!-- <button class="btn btn-windows add" type="submit">新增</button>
	<button class="btn btn-windows edit" type="submit">修改</button>
	<button class="btn btn-windows delete" type="submit">删除</button> -->
	<button class="btn btn-windows edit" type="button" onclick="edit();">修改</button>
	<button class="btn btn-windows delete" type="button" onclick="dell();">删除</button>
	<button class="btn btn-windows edit" type="button" onclick="shenhe();">审核</button>
	<button class="btn btn-windows git" type="button" onclick="creadible();">诚信登记</button>
	</div>
    </div>
   
   <div class="container margin-top-5">
     <div class="content padding-left-25 padding-right-25 padding-top-5">
        <table class="table table-bordered table-condensed">
		<thead>
		<tr>
		  <th class="info w30"><input type="checkbox" onclick="selectAll();"  id="allId" alt=""></th>
		  <th class="info w50">序号</th>
		  <th class="info">专家姓名</th>
		  <th class="info">性别</th>
		  <th class="info">类型</th>
		  <th class="info">毕业院校</th>
		  <th class="info">工作单位</th>
		  <th class="info">创建时间</th>
		  <th class="info">审核状态</th>
		  <th class="info">诚信积分</th>
		</tr>
		</thead>
		<c:forEach items="${result.list }" var="e" varStatus="vs">
		<tr>
		  <td class="tc w30"><input type="checkbox" name="check" id="checked" alt="" value="${e.id },${e.status}"></td>
		  <td onclick="view('${e.id}');" class="tc w50">${(vs.index+1)+(result.pageNum-1)*(result.pageSize)}</td>
		  <td onclick="view('${e.id}');" class="tc">${e.relName}</td>
		  <c:choose>
		  	<c:when test="${e.gender =='M'}">
		  		<td onclick="view('${e.id}');" class="tc">男</td>
		  	</c:when>
		  	<c:when test="${e.gender =='F'}">
		  		<td onclick="view('${e.id}');" class="tc">女</td>
		  	</c:when>
		  	<c:otherwise>
		  	<td onclick="view('${e.id}');" class="tc"></td>
		  	</c:otherwise>
		  </c:choose>
		  <c:if test="${e.expertsTypeId ==null}">
		   <td onclick="view('${e.id}');" class="tc"></td>
		  </c:if>
		  <c:if test="${e.expertsTypeId =='1' || e.expertsTypeId ==1}">
		   <td onclick="view('${e.id}');" class="tc">技术</td>
		  </c:if>
		  <c:if test="${e.expertsTypeId =='2' || e.expertsTypeId ==2}">
		   <td  onclick="view('${e.id}');"class="tc">法律</td>
		  </c:if>
		   <c:if test="${e.expertsTypeId =='3' || e.expertsTypeId ==3}">
		   <td onclick="view('${e.id}');" class="tc">商务</td>
		  </c:if>
		 <td onclick="view('${e.id}');" class="tc">${e.graduateSchool }</td>
		 <td onclick="view('${e.id}');" class="tc">${e.workUnit }</td>
		 <td  onclick="view('${e.id}');" class="tc"><fmt:formatDate type='date' value='${e.createdAt }' dateStyle="default" pattern="yyyy-MM-dd"/></td>
		 <c:if test="${e.status==null || e.status eq '0' }">
		 	<td onclick="view('${e.id}');" class="tc"><span class="label rounded-2x label-dark">未审核</span></td>
		 </c:if>
		 <c:if test="${e.status eq '1' }">
		 	<td onclick="view('${e.id}');" class="tc"><span class="label rounded-2x label-u">审核通过</span></td>
		 </c:if>
		 <c:if test="${e.status eq '2' }">
		 	<td onclick="view('${e.id}');" class="tc"><span class="label rounded-2x label-dark">审核未通过</span></td>
		 </c:if>
		 <c:if test="${e.status eq '3' }">
		 	<td onclick="view('${e.id}');" class="tc"><span class="label rounded-2x label-dark">退回修改</span></td>
		 </c:if>
		 <td onclick="view('${e.id}');" class="tc">${e.honestyScore }</td>
		</tr>
		</c:forEach>
        </table>
        <div id="pagediv" align="right"></div>

     </div>
   </div>
   
 </div>
</body>
</html>
