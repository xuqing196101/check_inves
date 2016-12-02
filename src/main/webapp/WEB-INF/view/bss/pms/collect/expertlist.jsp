<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
<jsp:include page="/WEB-INF/view/common.jsp"/> 
  <script type="text/javascript">
  
  
  /*分页  */
  $(function(){
      laypage({
            cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
            pages: "${info.pages}", //总页数
            skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
            total: "${info.total}",
            startRow: "${info.startRow}",
            endRow: "${info.endRow}",
            skip: true, //是否开启跳页
            groups: "${info.pages}">=3?3:"${info.pages}", //连续显示分页数
            curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
//                  var page = location.search.match(/page=(\d+)/);
//                  return page ? page[1] : 1;
                return "${info.pageNum}";
            }(), 
            jump: function(e, first){ //触发分页后的回调
                    if(!first){ //一定要加此判断，否则初始时会无限刷新
                    $("#page").val(e.curr);
                     $("#add_form").submit();
                    
                }  
            }
        });
  });
  
  
	function closede(){
		var id  = $('input[name="chkItem"]:checked').val(); 
		var index = parent.layer.getFrameIndex(window.name); 

		if(id==""||id==null){
			layer.alert("请选择要汇总的计划",{offset: ['100px', '100px'], shade:0.01});
		}else{
			var cid=parent.id;
			$("#cid").val(cid);
		$("#aid").val(id);
			$.ajax({
			url: "${pageContext.request.contextPath}/set/add.html",
			type: "post",
			data:$("#collected_form").serialize(),
			success: function(result) {
				parent.location.reload(); // 父页面刷新
				parent.layer.close(index);
			
		
			},
			error: function(message){
				layer.msg("删除失败",{offset: ['222px', '390px']});
				parent.layer.close(index);
			}
			
			
		});
		
		}
			
			
 	
		 
		 
			
		 
	}
	
 	function cancels(){
 		 var index = parent.layer.getFrameIndex(window.name); 
 		 
		 parent.layer.close(index);  
 	}
	
 	function ss(){
 		
 	}
  </script>
  </head>
  
  <body>
<div class="container">
<!-- 录入采购计划开始-->
   <div class="headline-v2">
      <h2>专家列表</h2>
   </div> 
<!-- 项目戳开始 -->
  <h2 class="search_detail">
    <form id="add_form" class="mb0" action="${pageContext.request.contextPath }/set/expert.html" method="post" >
    <input type="hidden" name="page" id="page">
    <ul class="demand_list">
          <li>
            <label class="fl"> 姓名：</label><span><input type="text" id="topic" name="relName" value="${expert.relName }"/></span>
          </li>
            <button type="submit" class="btn">查询</button>
    </ul>
    <div class="clear"></div>
  </form> 
  </h2>
  <div class="col-md-12 pl20 mt10">
             <input type="button" class="btn btn-windows git"  onclick="closede()" value="确定" />
            <input type="button" class="btn btn-windows cancel"   onclick="cancels()" value="取消">
   </div>
   <div class="content table_box">
        <table class="table table-bordered table-condensed table-hover table-striped">
		<thead>
		<tr>
		  <th class="info w30"></th>
		  <th class="info w50">序号</th>
		  <th class="info">姓名</th>
		  <th class="info">电话</th>
		  <th class="info">身份证号</th>
		</tr>
		</thead>
		<c:forEach items="${info.list}" var="obj" varStatus="vs">
			<tr style="cursor: pointer;">
			  <td class="tc w30"><input type="radio" value="${obj.id }" name="chkItem"></td>
			  <td class="tc w50"   >${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
			    <td class="tc"  >
			  			${obj.relName}
			    </td>
			    <td class="tc"  >${obj.mobile }</td>
			 	<td class="tc"  >${obj.idNumber }</td>
			</tr>
	 
		 </c:forEach>
		 

      </table>
      
   </div>
   <div id="pagediv" align="right"></div>
   </div>
   <form id="collected_form" action="" method="post">
     <input type="hidden" value="" name="id" id="aid">
     <input type="hidden" value="1"  name="type" >
      <input type="hidden" name="collectId" value="" id="cid">
     </form>
	 </body>
</html>
