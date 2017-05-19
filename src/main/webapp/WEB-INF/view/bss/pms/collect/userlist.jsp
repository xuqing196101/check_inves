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
  
	
	function closeds(){
		 
		var nature=parent.nature;
		var turn=parent.turns;
 		/* $("#aduit_nature").val(nature); */
 		/* $("#audit_turn").val(turn); */
 
 		$("#aduit_nature").val(parent.document.getElementById("audit_nature").value);
		var id  = $('input[name="chkItem"]:checked').val(); 
		var index = parent.layer.getFrameIndex(window.name); 
		var cid=parent.id;
		$("#cid").val(cid);
		if(id==""||id==null){
			layer.alert("请选择一条用户数据");
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
<div class="container">
<!-- 录入采购计划开始-->
 <div class="container">
   <div class="headline-v2">
      <h2>用户列表</h2>
   </div>
<!-- 项目戳开始 -->
   <h2 class="search_detail">
   <form id="add_form" class="mb0" action="${pageContext.request.contextPath }/set/user.html" method="post" >
    <input type="hidden" name="page" id="page">
    <ul class="demand_list">
          <li>
            <label class="fl">姓名 ：</label><span><input type="text" id="topic" name="relName" value="${user.relName }"/></span>
          </li>
            <button type="submit" class="btn">查询</button>
    </ul>
   <div class="clear"></div>
   </form>  
  </h2>
  <div class="col-md-12 pl20 mt10">
             <input type="button" class="btn btn-windows git"  onclick="closeds()" value="确定" />
            <input type="button" class="btn btn-windows cancel"   onclick="cancel()" value="取消">
   </div>
   <div class="content table_box">
        <table class="table table-bordered table-condensed table-hover table-striped">
		<thead>
		<tr>
		  <th class="info w30"></th>
		  <th class="info w50">序号</th>
		  <th class="info">姓名</th>
		  <th class="info">电话</th>
		  <th class="info">单位名称</th>
		</tr>
		</thead>
		<c:forEach items="${info.list}" var="obj" varStatus="vs">
			<tr class="pointer">
			  <td class="tc w30"><input type="radio" value="${obj.id }" name="chkItem"></td>
			  <td class="tc w50">${(vs.index+1)+(info.pageNum-1)*(info.pageSize)}</td>
			    <td class="tl pl20">
			  			${obj.relName}
			    </td>
			    <td class="tl pl20">${obj.mobile }</td>
			 	<td class="tl pl20">${obj.org.name }</td>
			</tr>
		 </c:forEach>
      </table>
   </div>
   <div id="pagediv" align="right"></div>
   </div>
   
   <form id="collected_form" action="" method="post" >
	 <input type="hidden" value="" name="id" id="aid">
	  <input type="hidden" name="type" value="2">
	  <input type="hidden" name="collectId" value="" id="cid">
	  <input type="hidden" name="auditStaff" id="aduit_nature" value=""/>
     <input type="hidden" name="auditRound" id="audit_turn" value="${type }"/>
     <input type="hidden" value="${type }"  id="type" >
     <input type="hidden" name="name"   value=""/>
      <input type="hidden" name="mobile"   value=""/>
      <input type="hidden" name="userId" id="user_id"  value=""/>
      <input type="hidden" name="idNumber"   value=""/>
      <input type="hidden" name="unitName"   value=""/>
      
	 </form>
	 
	 	<ul class="list-unstyled dnone mt10" id="audit">
			<li class="col-md-12 col-sm-12 col-xs-12 pl15">
				<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><div class="red star_red">*</div>审核人员性质：</span>
				<div class="col-md-3 col-xs-3 col-sm-3 input-append input_group">
				    <input type="text" id="auditStaff"/>
				    <div class="cue" id="errorType"></div>
				</div>
			</li>
		</ul>
			<!-- <div class="col-md-12 col-sm-12 col-xs-12 mt10 tc">
				<button class="btn btn-windows save" type="button" onclick="save()">保存</button>
				<button class="btn btn-windows cancel" type="button" onclick="cancelss()">取消</button>
			</div> -->
	 </body>
</html>
