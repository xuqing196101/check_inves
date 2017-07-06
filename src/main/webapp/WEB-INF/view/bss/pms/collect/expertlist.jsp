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
  
  
	function closede(){
		var nature=parent.nature;
		var turn=parent.turns;
 		/* $("#aduit_nature").val(nature); */
 	/* 	$("#audit_turn").val(turn); */
 		$("#aduit_nature").val(parent.document.getElementById("audit_nature").value);
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
			dataType:"json",
			success: function(result) {
				if(result=="error"){
					layer.alert("人员已被添加，请重新选择", {
						offset: ['30%', '40%']
					});
					$(".layui-layer-shade").remove();
				}else{
// 					$(nature).val("ss");
// 				//	$(".layui-layer-shade").remove();
// 					 parent.location.reload(); // 父页面刷新
// 					 $(nature).val("ss");
// 					 alert();
// 					//parent.layer.close(index);
					  var el = document.createElement("a");
                      document.body.appendChild(el);
                      el.href = "${pageContext.request.contextPath}/set/list.html?staff="+result+"&&id="+cid+"&type="+$("#type").val(); //url 是你得到的连接
                      el.target = '_parent'; //指定在新窗口打开
                      el.click();
                      document.body.removeChild(el);
				}
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
 	
 	//保存
 	function save(){
 		var auditStaff = $("#auditStaff").val();
 		$.ajax({
			type: "POST",
			url: "${pageContext.request.contextPath }/set/addStaff.html?auditStaff=" + auditStaff,
			success: function(data) {
				if(data == 0) {
					$("#errorType").html("审核人员性质不能为空");
				} else if(data == 1) {
					var id  = $('input[name="chkItem"]:checked').val();
					if(typeof(id)=="undefined"){
						layer.alert("请选择一条专家数据");
						return false;
					}
					var index = parent.layer.getFrameIndex(window.name); 
					var cid=parent.id;
					$("#cid").val(cid);
					$("#aid").val(id);
					$.ajax({
						url: "${pageContext.request.contextPath}/set/addExpert.html?staff="+auditStaff,
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
 	}
 	
 	//取消
 	function cancel(){
 		var index = parent.layer.getFrameIndex(window.name); 
 		parent.layer.close(index);
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
    <input type="hidden" value="${type }"  name="type" >
    <ul class="demand_list">
          <li>
            <label class="fl"> 姓名：</label><span><input type="text" id="topic" name="relName" value="${expert.relName }"/></span>
          </li>  
    </ul>
      <button type="submit" class="btn fl">查询</button>
    <div class="clear"></div>
  </form> 
  </h2>
  <div class="col-md-12 pl20 mt10">
   <!--  onclick="closede()" 不知道写一个保存一个确定是什么意思-->
             <input type="button" class="btn btn-windows git"  onclick="closede()" value="确定" />
            <input type="button" class="btn btn-windows cancel"   onclick="cancels()" value="取消">
   </div>
   <div class="content table_box">
        <table class="table table-bordered table-condensed table-hover table-striped">
		<thead>
		<tr>
		  <th class="info w50">选择</th>
		  <th class="info w50">序号</th>
		  <th class="info" width="25%">姓名</th>
		  <th class="info" width="25%">电话</th>
		  <th class="info">单位名称</th>
		</tr>
		</thead>
		<c:forEach items="${info.list}" var="obj" varStatus="vs">
			<tr class="pointer">
			  <td class="tc w30"><input type="radio" value="${obj.id }" name="chkItem"></td>
			  <td class="tc w50"   >${(vs.index+1)+(info.pageNum-1)*(info.pageSize)}</td>
			    <td class="tl"  >
			  			${obj.relName}
			    </td>
			    <td class="tc"  >${obj.mobile }</td>
			 	<td class="tl"  >${obj.workUnit }</td>
			</tr>
	 
		 </c:forEach>
		 

      </table>
      
   </div>
   <div id="pagediv" align="right"></div>
   </div>
   		<form id="collected_form" action="" method="post">
     	<input type="hidden" value="" name="id" id="aid">
     	<input type="hidden" value="1"  name="type" >
     	<%-- <input type="hidden" value="${type }"  id="type" > --%>
      	<input type="hidden" name="collectId" value="" id="cid">
      	<input type="hidden" name="auditStaff" id="aduit_nature" value=""/>
      	<input type="hidden" name="auditRound" id="type" value="${type}"/>
     	</form>
     
     <!-- <ul class="list-unstyled list-flow dnone mt10" id="audit">
			<li class="col-md-12 ml15">
				<span class="span3 fl mt5"><div class="red star_red">*</div>审核人员性质：</span>
				<input type="text" id="auditStaff"/>
				<div class="clear red" id="errorType"></div>
			</li> -->
			<!-- <div class="col-md-12 col-xs-12 col-sm-12 mt10 tc">
				<button class="btn btn-windows save" type="button" onclick="save()">保存</button>
				<button class="btn btn-windows cancel" type="button" onclick="cancel()">取消</button>
			</div> -->
		<!-- </ul>-->
     
	 </body>
</html>
