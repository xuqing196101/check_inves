<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
  <head>
    <%@ include file="/WEB-INF/view/common.jsp"%>
    <script type="text/javascript">
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
		$("#form1").submit();
	}
	 var index;
	function cancel(){
	   layer.close(index);
	}
	function openWindow(){
		index = layer.open({
	          type: 1, //page层
	          area: ['300px', '250px'],
	          title: '手动添加初审项',
	          shade:0.01, //遮罩透明度
	          moveType: 1, //拖拽风格，0是默认，1是传统拖动
	          shift: 1, //0-6的动画形式，-1不开启
	          offset: ['100px', '100px'],
	          shadeClose: true,
	          content:$('#openWindow') //数组第二项即吸附元素选择器或者DOM $('#openWindow')
		 });
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
	    		 //id.push(document.getElementsByName("check")[i].value);
	       		 count++;
	     }
	   	var id = id2.substring(0,id2.length-1);
	   	if(num>0){
		layer.confirm('您确定要删除吗?', {title:'提示',offset: ['222px','360px'],shade:0.01}, function(index){
	 			layer.close(index);
	 			$.ajax({
	 				url:"${pageContext.request.contextPath}/firstAudit/remove.html?id="+id,
	 				//data:{"id":id},
	 				//type:"post",
	 	       		success:function(){
	 	       			layer.msg('删除成功',{offset: ['222px', '390px']});
	 		       		window.setTimeout(function(){
	 		       			window.location.reload();
	 		       		}, 500);
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
	function edit(){
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
	          area: ['300px', '250px'],
	          title: '修改初审项',
	          shade:0.01, //遮罩透明度
	          moveType: 1, //拖拽风格，0是默认，1是传统拖动
	          shift: 1, //0-6的动画形式，-1不开启
	          offset: ['100px', '100px'],
	          closeBtn: 1,
	          content:'${pageContext.request.contextPath}/firstAudit/toEdit.html?id='+id
	        		  //数组第二项即吸附元素选择器或者DOM $('#openWindow')
		 });
	   }
	}
	//打开模板窗口列表
	function openTemplat(){
		layer.open({
	          type: 2, //page层
	          area: ['700px', '400px'],
	          title: '选择模板',
	          shade:0.01, //遮罩透明度
	          moveType: 1, //拖拽风格，0是默认，1是传统拖动
	          shift: 1, //0-6的动画形式，-1不开启
	          offset: ['100px', '100px'],
	          closeBtn: 1,
	          content:'${pageContext.request.contextPath}/firstAudit/toTemplatList.html'
	        		  //数组第二项即吸附元素选择器或者DOM $('#openWindow')
		 });
		
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
        
        function jump(url){
      	    $("#open_bidding_main").load(url);
        }
        //显示或隐藏各包评审项
	  function viewAndHidden(obj,index){
		  var classNames = $(obj).attr("class");
		  if (classNames.indexOf("jbxx") != -1) {
	          $("#"+index).removeAttr("class");
	          //隐藏
	          $("#"+index).attr("class","dnone");
	          $(obj).removeAttr("class");
	          $(obj).attr("class","count_flow hand zhxx");
	      }
	      if (classNames.indexOf("zhxx") != -1) {
	          //显示
	          $("#"+index).removeAttr("class");
	          $(obj).removeAttr("class");
	          $(obj).attr("class","count_flow jbxx hand");
	      }
	  }
	  
    //编辑符合性审查内容
    function editPackageFirstAudit(packageId,projectId){
        window.location.href = "${pageContext.request.contextPath}/firstAudit/editPackageFirstAudit.html?packageId="+packageId+"&projectId="+projectId+"&flowDefineId=${flowDefineId}";
    }
    
    //查看符合性审查内容
    function view(packageId,projectId){
    	window.open("${pageContext.request.contextPath}/firstAudit/editPackageFirstAudit.html?packageId="+packageId+"&projectId="+projectId+"&flag="+1);   
    }
    
    //初始化化加载
    $(function(){ 
    	if ($("#tipMsg").val() == "noFirst") {
    		$("#tipMsg").val("");
			layer.alert("请先完成各包资格性和符合性审查项的编写",{offset: '50px'});
		}
    });
</script>
  </head>
  <body>
     <div class="col-md-12 p0">
	   <ul class="flow_step">
	     <li class="active">
		   <a  href="${pageContext.request.contextPath}/firstAudit/toAdd.html?projectId=${projectId}&flowDefineId=${flowDefineId}" >01、资格性和符合性审查</a>
		   <i></i>
		 </li>
		 
		 <%-- <li>
		   <a  href="${pageContext.request.contextPath}/firstAudit/toPackageFirstAudit.html?projectId=${projectId}&flowDefineId=${flowDefineId}" >02、符合性关联</a>
		   <i></i>							  
		 </li> --%>
	     <li>
		   <a  href="${pageContext.request.contextPath}/intelligentScore/packageList.html?projectId=${projectId}&flowDefineId=${flowDefineId}">02、经济和技术评审细则</a>
		   <i></i>
		 </li>
		 <li>
		   <a  href="${pageContext.request.contextPath}/open_bidding/bidFile.html?id=${projectId}&flowDefineId=${flowDefineId}" >
		     03、采购文件
		     <%-- <c:if test="${project.dictionary.code eq 'GKZB' }">
		     03、采购文件
		     </c:if>
		     <c:if test="${project.dictionary.code eq 'XJCG' }">
		     03、询价文件
		     </c:if>
		     <c:if test="${project.dictionary.code eq 'YQZB' }">
		     03、采购文件
		     </c:if>
		     <c:if test="${project.dictionary.code eq 'JZXTP' }">
		     03、竞谈文件
		     </c:if>
		     <c:if test="${project.dictionary.code eq 'DYLY' }">
		     03、单一来源文件
		     </c:if> --%>
		   </a>
		   <i></i>
		 </li>
		 <li>
		   <a  href="${pageContext.request.contextPath}/Auditbidding/viewAudit.html?projectId=${projectId}&flowDefineId=${flowDefineId}">04、审核意见</a>
		 </li>
	   </ul>
	 </div>
	 <h2 class="list_title">拟制符合性审查项</h2>
	 <input type="hidden" id="tipMsg" value="${msg}">
     <input type="hidden" id="projectId" value="${projectId}">
     <table class="table table-bordered table-condensed table-hover table-striped">
         <thead>
         <tr>
           <th class="w50 info">序号</th>
           <th class="info">包名</th>
           <th class="info">状态</th>
           <th class="info">操作</th>
         </tr>
         </thead>
         <c:forEach items="${packages}" var="p" varStatus="vs">
            <tr>
                <td class="tc w30">${vs.count}</td>
                <td class="tc">${p.name}</td>
                <td class="tc">
                	<c:if test="${p.isEditFirst == 0}">
                		未维护检查数据
                	</c:if>
                	<c:if test="${p.isEditFirst == 1}">
                		已维护检查数据
                	</c:if>
                </td>
                <td class="tc">
                	<c:if test="${project.confirmFile == 1}">
                		<button class="btn" type="button" onclick="view('${p.id}','${projectId}')">查看</button>
                	</c:if>
                	<c:if test="${project.confirmFile != 1}">
	                    <button class="btn btn-windows edit" type="button" onclick="editPackageFirstAudit('${p.id}','${projectId}')">编辑</button>
                	</c:if>
                </td>
         </c:forEach>
     </table>
	  
	 <div id="openDiv" class="dnone layui-layer-wrap" >
      <form id="form2" method="post" >
        <div class="drop_window">
              <input type="hidden" name="templatId" id="templetId" value="${templetId}">
              <input type="hidden" name="kind" id="itemKind" > 
              <ul class="list-unstyled">
                  <li class="col-sm-6 col-md-6 col-lg-6 col-xs-6 pl15">
                    <label class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>评审名称</label>
                    <span class="col-md-12 col-sm-12 col-xs-12 p0">
                       <input name="name" id="itemName" maxlength="30" type="text">
                    </span>
                  </li>
                  <li class="col-sm-6 col-md-6 col-lg-6 col-xs-6">
                    <label class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>序号</label>
                    <div class="col-md-12 col-sm-12 col-xs-12 p0">
                       <input  name="position" id="itemPosition" maxlength="10" type="text">
                    </div>
                 </li>
                 <li class="col-md-12 col-sm-12 col-xs-12 mb20">
                   <label class="col-md-12 pl20 col-xs-12 padding-left-5"><div class="star_red">*</div>评审内容</label>
                   <span class="col-md-12 col-sm-12 col-xs-12 p0">
                    <textarea class="col-md-12 col-sm-12 col-xs-12 h80" id="itemContent" name="content" maxlength="200" title="" placeholder=""></textarea>
                   </span>
                 </li>
              </ul>
              <div class="mt40 tc mb50">
                <input class="btn btn-windows save"  onclick="saveItem();" value="保存" type="button"> 
                <input class="btn btn-windows back"  onclick="cancel();" value="取消" type="button"> 
              </div>
            </div>
         </form>
      </div>
<%-- <div class="tab-content clear step_cont">
	<!--第一个  -->
		<div class="" id="tab-1">
			<div class="headline-v2">
				<h2>初审项定义</h2>
			</div>
			<form action="">
			<div class="col-md-12 pl20 mt10">
				<c:if test="${project.confirmFile != 1}">
					<input type="button" value="选择模板" onclick="openTemplat();"
						class="btn btn-windows add" />
					<input type="button" value="添加" onclick="openWindow();"
						class="btn btn-windows add" />
					<input type="button" value="修改" class="btn btn-windows edit"
						onclick="edit();">
					<input type="button" value="删除" class="btn btn-windows delete"
						onclick="remove();">
				</c:if>
			</div>
			<div class="content table_box">
				<table
					class="table table-bordered table-condensed table-hover table-striped">
					<thead>
						<tr>
							<c:if test="${project.confirmFile != 1}">
								<th class="info w30"><input type="checkbox" id="checkAll"
									onclick="selectAll()" alt="">
								</th>
							</c:if>
							<th class="info">初审项名称</th>
							<th class="info">要求类型</th>
							<th class="info">创建人</th>
							<th class="info">创建时间</th>
						</tr>
					</thead>
					<c:forEach items="${list }" var="l" varStatus="vs">
						<tr>
							<c:if test="${project.confirmFile != 1}">
								<td class="tc w30"><input type="checkbox" value="${l.id }"
									name="chkItem" alt="">
								</td>
							</c:if>
							<td align="center">${l.name }</td>
							<td align="center">${l.kind }</td>
							<td align="center">${l.creater }</td>
							<td align="center"><fmt:formatDate type='date'
									value='${l.createdAt }' dateStyle="default"
									pattern="yyyy-MM-dd" />
							</td>
						</tr>
					</c:forEach>
				</table>
			</div>
			</form>
			<!-- 按钮 -->
			<div class="padding-top-10 clear">
				<div class="col-md-12 pl200 ">
					<div class="mt40 tc mb50"></div>
				</div>
			</div>
			<!--打开的窗口  -->
			<div id="openWindow" style="display: none;">
				<form
					action="${pageContext.request.contextPath}/firstAudit/add.html"
					method="post" id="form1">
					<ul class="list-unstyled">
						<li class="mt10 col-md-12 p0"><label class="col-md-12 pl20">初审项名称</label>
							<span class="col-md-12"> <input type="text"
								required="true" maxlength="30" name="name" id="name"> </span></li>
						<li class="mt10 col-md-12 p0"><label class="col-md-12 pl20">要求类型</label>
							<span class="col-md-12"> <input type="radio" name="kind"
								value="商务">商务&nbsp;<input type="radio" name="kind"
								id="kind" value="技术">技术 </span></li>
						<div class="clear"></div>
						<input name="creater" required="true" maxlength="10" id="creater"
							type="hidden" value="${sessionScope.loginUser.relName}">
						<input type="hidden" name="projectId" id="projectId"
							value="${projectId }">
					</ul>
					<input type="button" value="添加" onclick="submit1();"
						class="btn btn-windows add" /> <input type="button" value="取消"
						onclick="cancel();" class="btn btn-windows cancel" />
				</form>
			</div>
		</div>
	</div> --%>
  </body>
</html>
