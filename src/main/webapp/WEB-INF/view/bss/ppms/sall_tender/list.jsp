<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="${pageContext.request.contextPath}/" >

<title>模版管理</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/public/supplier/css/supplieragents.css"
    type="text/css">

</head>
<script src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
<script src="${pageContext.request.contextPath}/public/laypage-v1.3/laypage/laypage.js"></script>
<script type="text/javascript">
  $(function(){
	  laypage({
		    cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
		    pages: "${list.pages}", //总页数
		    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
		    skip: true, //是否开启跳页
		    total:"${list.total}",
		    startRow:"${list.startRow}",
		    endRow:"${list.endRow}",
		    groups: "${list.pages}">=5?5:"${list.pages}", //连续显示分页数
		    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
		        var page = location.search.match(/page=(\d+)/);
		        return page ? page[1] : 1;
		    }(), 
		    jump: function(e, first){ //触发分页后的回调
		        if(!first){ //一定要加此判断，否则初始时会无限刷新
		            location.href = '${pageContext.request.contextPath}/saleTender/list.do?page='+e.curr+'&&projectId=${projectId}';
		        }
		    }
		});
	  $("#statusBond").find("option[value='${saleTender.statusBond}']").attr("selected",true);
	  $("#statusBid").find("option[value='${saleTender.statusBid}']").attr("selected",true);
	  
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
  	function view(id){
  		window.location.href="${pageContext.request.contextPath}/templet/view.do?id="+id;
  	}
  	
    function upload(){
    	var id=[]; 
        $('input[name="chkItem"]:checked').each(function(){ 
            id.push($(this).val());
        }); 
        if(id.length==1){
        	var status=id.toString().split("^");
        	if(status[1]!=2){
        		var iframeWin;
                layer.open({
                    type: 2,
                    title: '上传',
                    shadeClose: true,
                    shade: 0.01,
                    area: ['500px', '230px'], //宽高
                    content: '${pageContext.request.contextPath}/saleTender/showUpload.html?projectId=${projectId}&&id='+status[0],
                    success: function(layero, index){
                        iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
                      },
                    btn: ['上传', '关闭'] 
                        ,yes: function(){
                            iframeWin.upload();
                        }
                        ,btn2: function(){
                          layer.closeAll();
                        }
                  });	
        	}else{
        	     layer.alert("已缴纳保证金",{offset: ['40px', '400px'], shade:0.01});
        	}
                    
        }else if(id.length>1){
            layer.alert("只能选择一个",{offset: ['40px', '400px'], shade:0.01});
        }else{
            layer.alert("请选择",{offset: ['40px', '400px'], shade:0.01});
        }
    }
    
     function uploadBsf(){
    	var id=[]; 
        $('input[name="chkItem"]:checked').each(function(){ 
            id.push($(this).val());
        }); 
        if(id.length==1){
        	var status=id.toString().split("^");
        	if(status[2]!=2){
        		var iframeWin;
                layer.open({
                    type: 2,
                    title: '上传',
                    shadeClose: true,
                    shade: 0.01,
                    area: ['500px', '230px'], //宽高
                    content: '${pageContext.request.contextPath}/saleTender/uploadBsf.html?projectId=${projectId}&&id='+status[0],
                    success: function(layero, index){
                        iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
                      },
                    btn: ['上传', '关闭'] 
                        ,yes: function(){
                            iframeWin.uploadBsf();
                        
                            
                        }
                        ,btn2: function(){
                          layer.closeAll();
                        }
                  });	
        	}else{
        	     layer.alert("已缴纳标书费",{offset: ['40px', '400px'], shade:0.01});
        	}
                    
        }else if(id.length>1){
            layer.alert("只能选择一个",{offset: ['40px', '400px'], shade:0.01});
        }else{
            layer.alert("请选择",{offset: ['40px', '400px'], shade:0.01});
        }
    }
  
    function add(){
		window.location.href='${pageContext.request.contextPath}/saleTender/showSupplier.html?projectId=${projectId}';
    }
   
    function download(){
        window.location.href="${pageContext.request.contextPath}/saleTender/download.do?projectId=${projectId}";         
    }
    function resetQuery(){
        $("#form1").find(":input").not(":button,:submit,:reset,:hidden").val("").removeAttr("checked").removeAttr("selected");
    }
</script>
<body>
	<!--面包屑导航开始-->
	<h2 class="search_detail">
		<form action="${pageContext.request.contextPath}/saleTender/list.do" method="post" id="form1" class="mb0"  >
		<input type="hidden" name="projectId" value="${projectId}">
			<ul class="demand_list">
				<li><label class="fl">供应商名称：</label><span><input
						type="text" id="topic" class="w147" value="${supplierName}" name="supplierName" /></span></li>
				<li><label class="fl">标书状态：</label><span> <select id="statusBid" name="statusBid" class="w147">
							<option value="" selected="selected" >-请选择-</option>
							<option value="2">已缴纳</option>
							<option value="1">未缴纳</option>
					</select>
				</span></li>
				<li><label class="fl">保证金状态：</label><span> <select id="statusBond" name="statusBond" class="w147">
							<option value="" selected="selected" >-请选择-</option>
							<option value="2">已缴纳</option>
							<option value="1">未缴纳</option>
					</select>

				</span></li>
			</ul>
			  <div class="col-md-12 clear tc mt10">
            <input type="submit"  class="btn" value="查询"/>
            <button type="button" onclick="resetQuery();" class="btn">重置</button> 
            </div>
             <div class="clear"></div>
		</form>
	
	</h2>
	<!-- 表格开始-->
	<div class="col-md-12 col-xs-12 col-sm-12 p0 mb5">
		<button class="btn btn-windows withdraw" onclick="download();"
			type="button">下载标书</button>
		<button class="btn btn-windows add" onclick="add();" type="button">新增</button>
		<button class="btn btn-windows edit" onclick="upload();" type="button">缴纳保证金</button>
		<button class="btn btn-windows edit" onclick="uploadBsf();" type="button">缴纳标书费</button>
	</div>
		<table class="table table-bordered table-condensed table-hover table-striped">
			<thead>
				<tr>
					<th class="info w30"><input id="checkAll" type="checkbox"
						onclick="selectAll()" /></th>
					<th class="info w50">供应商名称</th>
					<th class="info">包名</th>
					<th class="info">联系人</th>
					<th class="info">联系电话</th>
					<th class="info">发售日期</th>
					<th class="info">标书状态</th>
					<th class="info">保证金状态</th>
				</tr>
			</thead>
			<c:forEach items="${list.list}" var="sale" varStatus="vs">
				<tr>
					<td class="tc opinter"><input onclick="check()"
						type="checkbox" name="chkItem"
						value="${sale.id}^${sale.statusBond}^${sale.statusBid}" /></td>
					<td class="tc opinter w200" title="${sale.suppliers.supplierName}">
					  <c:choose>
                               <c:when test="${fn:length(sale.suppliers.supplierName) > 12}">  
                                                     ${fn:substring(sale.suppliers.supplierName, 0, 10)}......
                               </c:when>
                               <c:otherwise>  
                                         ${sale.suppliers.supplierName}
                               </c:otherwise>
                          </c:choose>
					
					</td>
					<td class="tc opinter w100">${sale.packageNames}</td>
					<td class="tc opinter w100">${sale.suppliers.contactName}</td>

					<td class="tc opinter w110">${sale.suppliers.contactTelephone}</td>

					<td class="tc opinter w150"><fmt:formatDate
							value='${sale.createdAt}' pattern='yyyy-MM-dd  HH:mm:ss' /></td>
							
					<td class="tc opinter w60"><c:if test="${sale.statusBid==1}">
                               未缴纳
                               </c:if> <c:if test="${sale.statusBid==2}">
                               已缴纳
                               </c:if></td>
					<td class="tc opinter w75"><c:if test="${sale.statusBond==1}">
                               未缴纳
                               </c:if> <c:if test="${sale.statusBond==2}">
                               已缴纳
                               </c:if></td>
				</tr>
			</c:forEach>
		</table>
		<div id="pagediv" align="right"></div>
</body>
</html>
