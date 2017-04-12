<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
<script type="text/javascript">

  /*分页  */
  $(function(){
      laypage({
            cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
            pages: "${info.pages}", //总页数
            skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
          //  total: "需求部门共"+"${info.total}",
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
  
	
	function closeds(){
		 
		var nature=parent.nature;
		var turn=parent.turns;
 		$("#aduit_nature").val(nature);
 		/* $("#audit_turn").val(turn); */
 
 		
		var id  = $('input[name="chkItem"]:checked').val(); 
		var index = parent.layer.getFrameIndex(window.name); 
		var cid=parent.id;
		$("#cid").val(cid);
		if(id==""||id==null){
			layer.alert("请选择要汇总的计划",{offset: ['100px', '100px'], shade:0.01});
		}else{
			$("#user_id").val(id);
		  
			$.ajax({
				url: "${pageContext.request.contextPath}/set/addUser.do",
				type: "post",
				data:$("#collected_form").serialize(),
				//dataType:"json",
				success: function(result) {
					if(result==1){
						layer.alert("人员已被添加，请重新选择", {
							offset: ['30%', '40%']
						});
						$(".layui-layer-shade").remove();
					}else{
						  var el = document.createElement("a");
	                      document.body.appendChild(el);
	                      el.href = "${pageContext.request.contextPath}/set/list.html?staff="+result+"&&id="+cid+"&type="+$("#type").val();  
	                      el.target = '_parent'; //指定在新窗口打开
	                      el.click();
	                      document.body.removeChild(el);
						//parent.location.reload(); 
					/* 	layer.open({
							type: 1,
							title: '信息',
							skin: 'layui-layer-rim',
							shadeClose: true,
							area: ['580px', '210px'],
							content: $("#audit")
						});
						$(".layui-layer-shade").remove(); */
					}
				},
				error: function (){
				}
			});
		}
	}
	
 	function cancel(){
 		 var index = parent.layer.getFrameIndex(window.name); 
 		 
		 parent.layer.close(index);  
 	}
	
 	function ss(){
 		
 	}
 	
 	//保存
/*  	function save(){
 		var auditStaff = $("#auditStaff").val();
 		$.ajax({
			type: "POST",
			url: "${pageContext.request.contextPath }/set/addStaff.html?auditStaff=" + auditStaff,
			success: function(data) {
				if(data == 0) {
					$("#errorType").html("审核人员性质不能为空");
				} else if(data == 1) {
					var id  = $('input[name="chkItem"]:checked').val(); 
					var index = parent.layer.getFrameIndex(window.name); 
					var cid=parent.id;
					$("#cid").val(cid);
					$("#aid").val(id);
					$.ajax({
						url: "${pageContext.request.contextPath}/set/addUser.html?staff="+auditStaff,
						type: "post",
						data:$("#collected_form").serialize(),
						success: function(result) {
							parent.location.reload(); // 父页面刷新
							parent.layer.close(index);
						}
					});
				}
			}
		});
 	} */
 	
 	//取消
 	function cancelss(){
 		layer.closeAll();
 	}
  </script>
  </head>
  
  <body>
<!-- 录入采购计划开始-->
 <div class="container">
   <div class="headline-v2">
      <h2>下载列表</h2>
   </div>
<!-- 项目戳开始 -->
   <h2 class="search_detail">
    </h2>
    <form id="add_form" action="${pageContext.request.contextPath }/look/organddep.html" method="post">
        <input type="hidden" name="page" id="page">
    </form>
    
 
   <div class="content table_box">
        <table class="table table-bordered table-condensed table-hover table-striped">
		<tr class="pointer">
		      <td class="tc w30">一</td>
			  <td class="tc w30">汇总表</td>
			  <td class="tc w50"> <a href="${pageContext.request.contextPath }/set/excel.html?id=${uniqueId}" >下载 </a> </td>
		</tr>
			
		<tr class="pointer">
		      <td class="tc w30">二</td>
			  <td class="tc w30">采购机构</td>
			  <td class="tc w50"> <a href="${pageContext.request.contextPath }/set/excel.html?id=${uniqueId}" >全部下载 </a> </td>
		</tr>
		<c:forEach items="${list}" var="obj" varStatus="vs">
			<tr class="pointer">
			  <td class="tc w30">${vs.index+1}</td>
			  <td class="tc w30">${obj.shortName }</td>
			  <td class="tc w30"> <a href="${pageContext.request.contextPath }/set/excel.html?id=${uniqueId}&&org=${obj.id}&&flag=1" >下载</td>
			 	  
		 </tr>
		 </c:forEach>
		 <tr class="pointer">
		      <td class="tc w30">三</td>
			  <td class="tc w30">需求部门</td>
			  <td class="tc w50"> <a href="${pageContext.request.contextPath }/set/excel.html?id=${uniqueId}" >全部下载 </a> </td>
		</tr>
		
		<c:forEach items="${info.list}" var="dep" varStatus="de">
			<tr class="pointer">
			  <td class="tc w30">${de.index+1}</td>
			  <td class="tc w30">${dep}</td>
			  <td class="tc w30"> <a href="${pageContext.request.contextPath }/set/excel.html?id=${uniqueId}&&dep=${dep}&&flag=1" >下载</td>
		 </tr>
		 </c:forEach>
      </table>
   </div>
   <div id="pagediv" align="right"></div>
   </div>
   
 
			<!-- <div class="col-md-12 col-sm-12 col-xs-12 mt10 tc">
				<button class="btn btn-windows save" type="button" onclick="save()">保存</button>
				<button class="btn btn-windows cancel" type="button" onclick="cancelss()">取消</button>
			</div> -->
	 </body>
</html>
