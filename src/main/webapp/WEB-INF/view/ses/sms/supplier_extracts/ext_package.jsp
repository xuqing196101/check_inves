<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
<%@ include file="../../../common.jsp"%>

<title>项目管理</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">



<script type="text/javascript">
  
  /*分页  */
  $(function(){
	  
	  laypage({
          cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
          pages: "${info.pages}", //总页数
          skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
          skip: true, //是否开启跳页
          total : "${info.total}",
          startRow : "${info.startRow}",
          endRow : "${info.endRow}",
          groups: "${info.pages}">=5?5:"${info.pages}", //连续显示分页数
          curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
//                var page = location.search.match(/page=(\d+)/);
//                return page ? page[1] : 1;
              return "${info.pageNum}";
          }(), 
          jump: function(e, first){ //触发分页后的回调
                  if(!first){ //一定要加此判断，否则初始时会无限刷新
               $("#page").val(e.curr);
                  $("#form1").submit();
                  
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
    
    function opens(){
        var id=[]; 
        $('input[name="chkItem"]:checked').each(function(){ 
            id.push($(this).val());
        }); 
        if(id.length>1){
            layer.alert("只能选择一个",{ shade:0.01});
        }else if(id.length<=0){
         var	typeclassId="${typeclassId}";
        	if(typeclassId != null && typeclassId != ''){
        		window.location.href="${pageContext.request.contextPath}/SupplierExtracts/Extraction.html?projectId=${projectId}";	
        	}else{
        		layer.alert("请选择一个",{shade:0.01});
        	}
        }else{
        	window.location.href="${pageContext.request.contextPath}/SupplierExtracts/Extraction.html?projectId=${projectId}&&typeclassId=${typeclassId}";
        }
    }
    
    function record(){
    	var  typeclassId ="${typeclassId}";
    	if(typeclassId != null && typeclassId != ''){
    		 location.href = '${pageContext.request.contextPath}/SupplierExtracts/resuleRecordlist.do';
    	}else{
    		   location.href = '${pageContext.request.contextPath}/SupplierExtracts/showRecord.do?projectId=${projectId}';
    	}
    	  
    }
    function resetQuery(){
        $("#form1").find(":input").not(":button,:submit,:reset,:hidden").val("").removeAttr("checked").removeAttr("selected");
    }
  </script>
</head>

<body>
	<!--面包屑导航开始-->
	<c:if test="${typeclassId!=null && typeclassId !='' }">
	<div class="margin-top-10 breadcrumbs ">
		<div class="container">
			<ul class="breadcrumb margin-left-0">
                <li><a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a></li>
                <li><a href="javascript:void(0);">支撑环境系统</a></li>
                <li><a href="javascript:void(0);">供应商管理</a></li>
                <li><a href="javascript:void(0);" onclick="jumppage('${pageContext.request.contextPath}/SupplierExtracts/projectList.html?typeclassId=${typeclassId}')">供应商抽取</a></li>
                <li class="active"><a href="javascript:void(0);">抽取项目</a></li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>
	<div class="container">
        <div class="headline-v2">
            <h2>包列表</h2>
        </div>
    </div>
	</c:if>
	<!-- 录入采购计划开始-->
	<div class="container">
     <h2 class="search_detail">
     <form  action="${pageContext.request.contextPath}/SupplierExtracts/packageList.html" id="form1" method="post" class="mb0">
     <ul class="demand_list">
     <li class="fl">
     <span>
       <label class="fl">包名称：</label>
	        <input type="hidden" name="page" id="page">
	        <input type="text" name="packName" id="proName" value="${packName}"/>
	        <input type="hidden" name="projectId"  value="${projectId}"  > 
        </span>
        
       </li>
         <button class="btn fl mt1" type="submit">查询</button>
         <button type="button" class="btn fl mt1" onclick="resetQuery();">重置</button> 
     </ul>
     <div class="clear"></div>
    </form>
    </h2>
	    <div class="col-md-12 pl20 mt10">
            <button class="btn"
                onclick="opens();">人工抽取</button>
            <button class="btn"
                onclick="record();">抽取记录</button>
        </div>
		<div class="table_box">
		
         <table class="table table-striped table-bordered table-hover">
        <thead>
        <tr>
          <th class="info w30"><input type="checkbox" id="checkAll" onclick="selectAll()"  alt=""></th>
          <th class="info w50">序号</th>
          <th class="info">包名称</th>
          <th class="info">抽取次数</th>
<!--           <th class="info">已抽取数量</th> -->
        </tr>
        </thead>
        
        <tbody id="tbody_id">

                        <c:forEach items="${info.list}" var="obj" varStatus="vs">
                            <tr style="cursor: pointer;">
                                <td class="tc w30"><input type="checkbox"
                                    value="${obj.id}" name="chkItem" onclick="check()" alt="">
                                </td>
                                <td class="tc w50">${(vs.index+1)+(info.pageNum-1)*(info.pageSize)}</td>
                                <td class="tc " onclick="view('${obj.id}');">${obj.packages.name}</td>
                                <td class="tc " onclick="view('${obj.id}');">${obj.count}</td>
<%--                                 <td class="tc " onclick="view('${obj.id}');">${obj.number}</td> --%>
                            </tr>
                        </c:forEach>
                    </tbody>

      </table>
      </div>
           <div id="pagediv" align="right"></div>
	</div>



</body>
</html>
