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
<script src="<%=basePath%>public/layer/layer.js"></script>
<script src="<%=basePath%>public/laypage-v1.3/laypage/laypage.js"></script>
<script type="text/javascript">
   $(function(){
		  laypage({
			    cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
			    pages: "${result.pages}", //总页数
			    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
			    skip: true, //是否开启跳页
			    groups: "${result.pages}">=3?3:"${result.pages}", //连续显示分页数
			    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
// 			        var page = location.search.match(/page=(\d+)/);
// 			        return page ? page[1] : 1;
					return "${result.pageNum}";
			    }(), 
			    jump: function(e, first){ //触发分页后的回调
			        if(!first){ //一定要加此判断，否则初始时会无限刷新
			        	$("#page").val(e.curr);
			        	$("#form1").submit();
			        	
			          //location.href = '<%=basePath%>expert/findAllExpert.do?page='+e.curr;
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
  /*  	function shenhe(){
   	 var spCodesTemp = "";
     $('input:checkbox[name=check]:checked').each(function(i){
      if(0==i){
       spCodesTemp = $(this).val();
      }else{
       spCodesTemp += (","+$(this).val());
      }
     });
     //$("#txt_spCodes").val(spCodesTemp);
   		alert(spCodesTemp);
   	} */
   	//修改
   	function edit(){
  	  var count = 0;
  	  var ids = document.getElementsByName("check");
   
       for(i=0;i<ids.length;i++) {
     		 if(document.getElementsByName("check")[i].checked){
     		 var id = document.getElementsByName("check")[i].value;
     		 count++;
      }
    }   
    		if(count>1){
    			layer.alert("只能选择一条记录",{offset: ['222px', '390px'],shade:0.01});
    		}else if(count<1){
    			layer.alert("请选择一条记录",{offset: ['222px', '390px'],shade:0.01});
    		}else if(count==1){
    			window.location.href="<%=basePath%>expert/toEditBasicInfo.html?id="+id;
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
   	 				url:"<%=basePath%>expert/deleteAll.html",
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
</script>
</head>
<body>
  <div class="wrapper">

<div class="header-v4 header-v5">
    <!-- Navbar -->
    <div class="navbar navbar-default mega-menu" role="navigation">
      <div class="container">
        <!-- logo和搜索 -->
        <div class="navbar-header">
          <div class="row container">
            <div class="col-md-4 padding-bottom-30">
              <a href="">
                 <img alt="Logo" src="<%=basePath%>public/ZHH/images/logo_2.png" id="logo-header">
              </a>
            </div>
			<!--菜单开始-->
            <div class="col-md-8 topbar-v1 col-md-12 ">
              <ul class="top-v1-data padding-0">
			    <li>
				<a href="#">
				  <div><img src="<%=basePath%>public/ZHH/images/top_01.png"/></div>
				  <span>决策支持</span>
				 </a>
				</li>
			    <li>
				<a href="#">
				  <div><img src="<%=basePath%>public/ZHH/images/top_02.png"/></div>
				  <span>业务监管</span>
				 </a>
				</li>
			    <li>
				<a href="#">
				  <div><img src="<%=basePath%>public/ZHH/images/top_03.png"/></div>
				  <span>障碍作业</span>
				 </a>
				</li>	
			    <li>
				<a href="#">
				  <div><img src="<%=basePath%>public/ZHH/images/top_04.png"/></div>
				  <span>信息服务</span>
				 </a>
				</li>
			   <li class="dropdown">
			     	<a aria-expanded="false" data-toggle="dropdown" class="dropdown-toggle p0_30 " href="">
				  		<div><img src="<%=basePath%>public/ZHH/images/top_05.png"/></div>
				  		<span>支撑环境</span>
				 	</a>
					<ul class="dropdown-menu">
                   		<li class="line-block">
                   			<a href="#" target="_blank" class="son-menu"><span class="mr5">◇</span>后台管理</a>
                   			<ul class="dropdown-menuson dropdown-menu">
                   				<li><a href="#" target="_blank" class="son-menu"><span class="mr5">◇</span>用户管理</a></li>
                   			</ul>
                   		</li>
               		</ul>
				</li>
			    <li>
				<a href="#">
				  <div><img src="<%=basePath%>public/ZHH/images/top_06.png"/></div>
				  <span>配置配置</span>
				 </a>
				</li>
			    <li>
				<a href="#">
				  <div><img src="<%=basePath%>public/ZHH/images/top_07.png"/></div>
				  <span>后台首页</span>
				 </a>
				</li>
			    <li>
				<a href="#">
				  <div><img src="<%=basePath%>public/ZHH/images/top_08.png"/></div>
				  <span>安全退出</span>
				 </a>
				</li>
			  </ul>
			</div>
    </div>
	</div>
	</div>
   </div>
</div>

<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">业务管理</a></li><li><a href="#">协议采购</a></li><li class="active"><a href="#">我的订单</a></li>
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
  
   <form action="<%=basePath %>expert/findAllExpert.html"  method="post" id="form1" enctype="multipart/form-data" class="registerform"> 
  <input type="hidden" name="page" id="page">
  <input type="hidden" name="flag" value="0">
   <div align="center">
                    <table>
                    <tr>
                    <td>
                    <span>姓名：</span><input type="text" name="relName" value="${expert.relName }">
                    </td>
                    <td>
						 <span>来源：</span>
						   <select  name="expertsFrom" id="expertsFrom">
						    <option value=''>-请选择-</option>
						   	<option <c:if test="${expert.expertsFrom =='军队' }">selected = "true"</c:if> value="军队">军队</option>
						   	<option <c:if test="${expert.expertsFrom =='地方' }">selected = "true"</c:if> value="地方">地方</option>
						   	<option <c:if test="${expert.expertsFrom =='其他' }">selected = "true"</c:if> value="其他">其他</option>
						   </select>
					</td>
					<td>
                      	<span>状态：</span>
							   <select name="status" id="status">
							   		<option value=''>-请选择-</option>
							   		<option <c:if test="${expert.status =='0' }">selected = "true"</c:if> value="0">未审核</option>
							   		<option <c:if test="${expert.status =='1' }">selected = "true"</c:if> value="1">审核通过</option>
							   		<option <c:if test="${expert.status =='2' }">selected = "true"</c:if> value="2">审核未通过</option>
							   </select>
                     </td>
                     <td> 	
                         		<span >专家类型：</span>
							   <select name="expertsTypeId" id="expertsTypeId">
							   		<option value=''>-请选择-</option>
							   		<option <c:if test="${expert.expertsTypeId =='1' }">selected = "true"</c:if> value="1">技术</option>
							   		<option <c:if test="${expert.expertsTypeId =='2' }">selected = "true"</c:if> value="2">法律</option>
							   		<option <c:if test="${expert.expertsTypeId =='3' }">selected = "true"</c:if> value="3">商务</option>
							   </select>
					</td>
					<td>
                        <span class="input-group-btn">
                          <input class="btn-u" name="commit" value="搜索" type="submit">
                        </span>
                     </td>
                        </tr>
                        </table>
                  
                  </div>  
<!-- 表格开始-->
   <div class="container">
   <div class="col-md-8">
    <!-- <button class="btn btn-windows add" type="submit">新增</button>
	<button class="btn btn-windows edit" type="submit">修改</button>
	<button class="btn btn-windows delete" type="submit">删除</button> -->
	<button class="btn btn-windows edit" type="button" onclick="edit();">修改</button>
	<button class="btn btn-windows delete" type="button" onclick="dell();">删除</button>
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
		</tr>
		</thead>
		<c:forEach items="${result.list }" var="e" varStatus="s">
		<tr>
		  <td class="tc w30"><input type="checkbox" name="check" id="checked" alt="" value="${e.id }"></td>
		  <td class="tc w50">${s.count }</td>
		  <td class="tc">${e.relName}</td>
		  <c:choose>
		  	<c:when test="${e.gender =='M'}">
		  		<td class="tc">男</td>
		  	</c:when>
		  	<c:when test="${e.gender =='F'}">
		  		<td class="tc">女</td>
		  	</c:when>
		  	<c:otherwise>
		  	<td class="tc"></td>
		  	</c:otherwise>
		  </c:choose>
		  <c:if test="${e.expertsTypeId ==null}">
		   <td class="tc"></td>
		  </c:if>
		  <c:if test="${e.expertsTypeId =='1' || e.expertsTypeId ==1}">
		   <td class="tc">技术</td>
		  </c:if>
		  <c:if test="${e.expertsTypeId =='2' || e.expertsTypeId ==2}">
		   <td class="tc">法律</td>
		  </c:if>
		   <c:if test="${e.expertsTypeId =='3' || e.expertsTypeId ==3}">
		   <td class="tc">商务</td>
		  </c:if>
		 <td class="tc">${e.graduateSchool }</td>
		 <td class="tc">${e.workUnit }</td>
		 <td class="tc"><fmt:formatDate type='date' value='${e.createdAt }' dateStyle="default" pattern="yyyy-MM-dd"/></td>
		 <c:if test="${e.status==null || e.status eq '0' }">
		 	<td class="tc">未审核</td>
		 </c:if>
		 <c:if test="${e.status eq '1' }">
		 	<td class="tc">审核通过</td>
		 </c:if>
		 <c:if test="${e.status eq '2' }">
		 	<td class="tc">审核未通过</td>
		 </c:if>
		</tr>
		</c:forEach>
        </table>
        <div id="pagediv" align="right"></div>

     </div>
   </div>
    </form>
 </div>
<!--底部代码开始-->
<div class="footer-v2" id="footer-v2">

      <div class="footer">

            <!-- Address -->
              <address class="">
			  Copyright © 2016 版权所有：中央军委后勤保障部 京ICP备09055519号
              </address>
              <div class="">
		       浏览本网主页，建议将电脑显示屏的分辨率调为1024*768
              </div> 
            <!-- End Address -->

<!--/footer--> 
      </div>
</div>
</body>
</html>
