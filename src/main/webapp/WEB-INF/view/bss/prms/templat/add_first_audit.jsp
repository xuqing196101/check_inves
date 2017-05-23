<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
<script type="text/javascript">
var index;
function cancel(){
   layer.close(index);
}
function openWindow(){
	index = layer.open({
          type: 1, //page层
          area: ['50%','310px'],
          title: '新增初审项',
          shade:0.01, //遮罩透明度
          moveType: 1, //拖拽风格，0是默认，1是传统拖动
          shift: 1, //0-6的动画形式，-1不开启
          offset: ['110px', '25%'],
          shadeClose: true,
          content:$('#openWindow') //数组第二项即吸附元素选择器或者DOM $('#openWindow')
	 });
}
function edit(id){
	var count = 0;
	  var ids = document.getElementsByName("chkItem");
 
	     for(i=0;i<ids.length;i++) {
	   		 if(document.getElementsByName("chkItem")[i].checked){
	   		 var id = document.getElementsByName("chkItem")[i].value;
	   		//var value = id.split(",");
	   		 count++;
	         }
	     }   
  		if(count>1){
  			layer.alert("只能选择一条记录",{offset: ['222px', '390px'],shade:0.01});
  		}else if(count<1){
  			layer.alert("请选择一条记录",{offset: ['222px', '390px'],shade:0.01});
  		}else if(count==1){
		  layer.open({
	        type: 2, //page层
	        area: ['700px', '300px'],
	        title: '修改初审项',
	        shade:0.01, //遮罩透明度
	        moveType: 1, //拖拽风格，0是默认，1是传统拖动
	        shift: 1, //0-6的动画形式，-1不开启
	        offset: ['110px', '20%'],
	        closeBtn: 1,
	        content:'${pageContext.request.contextPath}/auditTemplat/toEditFirstAudit.html?id='+id
	      		  //数组第二项即吸附元素选择器或者DOM $('#openWindow')
		 });
  		}
}
function remove(){
	var count = 0;
	  var ids = document.getElementsByName("chkItem");
	 var id2="";
	 var num =0;
	    for(i=0;i<ids.length;i++) {
	  		 if(document.getElementsByName("chkItem")[i].checked){
		    		  id2 += document.getElementsByName("chkItem")[i].value+",";
		    		  num++;
	  		  }
	     		 count++;
	    }
 	var id = id2.substring(0,id2.length-1);
 	if(num>0){
		layer.confirm('您确定要删除吗?', {title:'提示',offset: ['222px','360px'],shade:0.01}, function(index){
	 			layer.close(index);
	 			$.ajax({
	 				url:"${pageContext.request.contextPath}/auditTemplat/deleteFirstAudit.html?ids="+id,
	 				//data:{"id":id},
	 				//type:"post",
	 	       		success:function(){
	 	       			layer.msg('删除成功',{offset: ['222px', '390px']});
	 		       		window.setTimeout(function(){
	 		       			window.location.reload();
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
function submit1(){
	
	var name = $("#name").val();
	if(!name){
		layer.tips("请填写名称", "#name");
		return ;
	}
	var id=[]; 
	$('input[name="kind"]:checked').each(function(){ 
		id.push($(this).val());
	}); 
	if(id.length==0){
		layer.tips("请选择类型", "#kind");
		return ;
	}
	
	/* var creater = $("#creater").val();
	if(!creater){
		layer.tips("请填写名称", "#creater");
		return ;
	} */
	$("#form1").submit();
}
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
</script>
</head>
<body>
<div class="margin-top-10 breadcrumbs ">
      <div class="container">
           <ul class="breadcrumb margin-left-0">
           <li><a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')" target="_blank">首页</a></li><li><a href="javascript:void(0)">业务管理</a></li><li><a href="javascript:void(0)">订单中心</a></li><li class="active"><a href="javascript:void(0)">初审项信息</a></li>
           </ul>
        <div class="clear"></div>
      </div>
   </div>
   <!-- 新增窗口 -->
   <div class="container">
       <div class="tab-content mt20">
          <div class="tab-v2">
            <ul class="nav nav-tabs bgwhite">
              <li class="active"><a href="#dep_tab-0" data-toggle="tab" class="f18">模板信息</a></li>
            </ul>
            <div class="tab-content">
                <div class="tab-pane fade in active" id="dep_tab-0">
                    <h2 class="count_flow jbxx">模板信息</h2>
                    <table class="table table-bordered">
		                 <tbody>
		                    <tr>
			                  <td class="bggrey" width="20%">初审项模板名称:</td>
			                  <td width="30%">${templat.name }</td>
			                  <td class="bggrey " width="20%">初审项模板类型:</td>
			                  <td width="30%">${templat.kind}</td>
		                    </tr>
		                    <tr>
                              <td class="bggrey">创建人:</td>
                              <td>${templat.creater}</td>
                              <td class="bggrey ">创建时间:</td>
                              <td><fmt:formatDate type='date' value='${templat.createdAt }' dateStyle="default" pattern="yyyy-MM-dd"/></td>
                            </tr>
		                 </tbody>
		            </table>
		            <h2 class="count_flow jbxx">初审项信息</h2>
		            <div class="col-md-12 col-xs-12 col-sm-12 mt10 p0">
					      <input type="button" value="添加初审项" onclick="openWindow();" class="btn btn-windows add"/>
						  <input type="button" value="修改" class="btn btn-windows edit" onclick="edit();">
						  <input type="button" value="删除" class="btn btn-windows delete" onclick="remove();">
				    </div>
				    <div class="content table_box pl0">
                        <table class="table table-bordered table-condensed table-hover table-striped">
                             <thead>
							      <tr>
							        <th class="info w30"><input type="checkbox" id="checkAll" onclick="selectAll()"  alt=""></th>
							        <th class="info">初审项名称</th>
							        <th class="info">初审项类型</th>
							        <th class="info">创建人</th>
							        <th class="info">创建时间</th>
							      </tr>
							 </thead>
							 <c:forEach items="${list }" var="l" varStatus="vs">
							      <thead>
								       <tr>
								        <td class="tc w30"><input type="checkbox" value="${l.id }" name="chkItem"   alt=""></td>
								        <td align="center">${l.name }</td>
								        <td align="center">${l.kind }</td>
								        <td align="center">${l.creater }</td>
								        <td align="center"><fmt:formatDate type='date' value='${l.createdAt }' dateStyle="default" pattern="yyyy-MM-dd"/></td>
								      </tr>
							      </thead>
							</c:forEach>
                        </table>
                    </div>
                </div>
            </div>
            <div class="col-md-12 col-sm-12 col-xs-12 mt20 tc">
            <button class="btn btn-windows back" onclick="history.go(-1)" type="button">返回</button>
            </div>
          </div>
       </div>
   </div>

<div id="openWindow" class="dnone layui-layer-wrap"  style="display: none;">
    <div class="drop_window">
        <form action="${pageContext.request.contextPath}/auditTemplat/saveFirstAudit.html" method="post" id="form1">
              <ul class="list-unstyled">
                <li class="col-md-6 col-sm-6 col-xs-6 pl15">
                  <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">初审项名称</span>
                  <div class="col-md-12 col-sm-12 col-xs-12 p0 input_group input-append">
                    <input type="text" id="name" maxlength="30" name="name" >
                  </div>
                </li>
                <li class="col-md-6 col-sm-6 col-xs-6">
                  <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">要求类型</span>
                  <div class="col-md-12 col-sm-12 col-xs-12 p0 input-append">
                    <input type="radio" name="kind" value="符合性" >符合性&nbsp;<input type="radio" name="kind" id="kind" value="资格性" >资格性
                    <input name="creater" readonly="readonly" id="creater" maxlength="10" type="hidden" value="${sessionScope.loginUser.relName}">
                    <input type="hidden" name="templatId" value="${templat.id }">
                  </div>
                </li>
                <div class="clear"></div>
               </ul>
               <div class="tc mt10 col-md-12 col-sm-12 col-xs-12">
                <input type="button"  value="添加 " onclick="submit1();"   class="btn btn-windows add"/>
                <input type="button"  value="取消" onclick="cancel();"  class="btn btn-windows cancel"/>
              </div>
         </form>
     </div>
</div>
</body>
</html>