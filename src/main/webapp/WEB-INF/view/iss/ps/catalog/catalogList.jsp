<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp"%>

<!DOCTYPE HTML>
<html>
<head>
<jsp:include page="/index_head.jsp"></jsp:include>
<link href="${pageContext.request.contextPath}/public/ztree/css/zTreeStyle.css" type="text/css" rel="stylesheet">
<script type="text/javascript">
	var treeid = null, nodeName = null, level = null, typesObj = null;
	var datas;
	function ztreeInit() {
		var setting = {
			async : {
				autoParam : [ "id" ],
				enable : true,
				url : "${pageContext.request.contextPath}/categorys/createtree.do",
				otherParam : {
					"otherParam" : "zTreeAsyncTest"
				},
				dataType : "json",
				datafilter : filter,
				type : "get",
			},
			callback : {
				onClick : zTreeOnClick,//点击节点触发的事件
			},
			data : {
				keep : {
					parent : true
				},
				key : {
					title : "title"
				},
				simpleData : {
					enable : true,
					idKey : "id",
					pIdKey : "pId",
					rootPId : "0",
				}
			},
			view : {
				selectedMulti : false,
				showTitle : false,
			},
		};
		$.fn.zTree.init($("#ztree"), setting, datas);
	}

	function query() {
		var param = $("#name").val();
		if (param != null && param != "") {
			var zNodes;
			var zTreeObj;
			var setting = {
				async : {
					autoParam : [ "id" ],
					enable : true,
				},
				data : {
					simpleData : {
						enable : true,
						idKey : "id",
						pIdKey : "parentId",
					}
				},
				callback : {
					onClick : zTreeOnClick
				},
				view : {
					showLine : true
				}
			};
			// 加载中的菊花图标
			var loading = layer.load(1);

			$.ajax({
						url : "${pageContext.request.contextPath}/categorys/createtree.do",
						data : {
							"param" : encodeURI(param)
						},
						async : false,
						dataType : "json",
						success : function(data) {
							if (data.length == 1) {
								layer.msg("没有符合查询条件的产品类别信息！");
							} else {
								zNodes = data;
								zTreeObj = $.fn.zTree.init($("#ztree"),
										setting, zNodes);
								zTreeObj.expandAll(true);//全部展开
							}
							// 关闭加载中的菊花图标
							layer.close(loading);

						}
					});
		} else {
			ztreeInit();
		}
	}

	function filter(treeId, parentNode, childNode) {
		if (!childNodes)
			return null;
		for (var i = 0; i < childNodes.length; i++) {
			childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
		}
		return childNodes;
	}
	function zTreeOnClick(event, treeId, treeNode) {
		treeid = treeNode.id;
    	var node = treeNode.getParentNode();
    	if (node && node != null ) {
    		level = node.level + 2;
    		resetTips();
    		$("#generalIQuaName").attr("disabled", "true");
    		$("#profileIQuaName").attr("disabled", "true");
    		$("#profileSalesName").attr("disabled", "true");
			if (treeNode.level == 3) {
				if (node.getParentNode().getParentNode().name == "工程") {
					/* $("#engLevelTrId").removeClass("dis_none"); */
					/* $("#levelTrId").addClass("dis_none"); */
				} else {
					/* $("#levelTrId").removeClass("dis_none"); */
					/* $("#engLevelTrId").addClass("dis_none"); */
				}
			} else {
				/* $("#levelTrId").addClass("dis_none"); */
				/* $("#engLevelTrId").addClass("dis_none"); */
			}
	    	nodeName = node.name;
	    	onClickTree(treeNode);
    		selectedNode = treeNode;
    	} else {
    		level = 1;
    		selectedNode = treeNode;
    	}
	}
	function onClickTree(nodes){
    	$("#result").css("display","");
    	hideQuas();
 	    if (treeid==null){
 			layer.msg("请选择一个节点");
		}else{
		var node = getCurrentRoot(nodes);
		  $.ajax({
			url:"${pageContext.request.contextPath}/category/update.do?id="+treeid,
			dataType:"json",
			type:"POST",
			success:function(cate){
					$("#parentNId").html(nodeName);
					$("#cateNameId").html(cate.name);
					$("#posNameId").html(cate.code);
					$("#description").html(cate.description);
					if (node.classify && node.classify == "PROJECT" ){
						showQuas(cate, node.classify);
					}
					if (node.classify && node.classify == "GOODS"){
						showQuas(cate, node.classify);
					}
					if (node.classify && node.classify == "SERVICE"){
						showQuas(cate, node.classify);
					}
					if (node.classify && node.classify == "GOODS"){
						$("#typeTrId").show();
						if(cate.classify=="3"){
							$("#typeId").html("物资生产，物资销售");
						}else if(cate.classify=="2"){
							$("#typeId").html("物资销售");
						}else if(cate.classify=="1"){
							$("#typeId").html("物资生产");
						}
					} else {
						$("#typeTrId").hide();
					}
					/* if(cate.isPublish=="1"){
						$("#openId").html("不公开");
					}else if(cate.isPublish=="0"){
						$("#openId").html("公开");
					} */
		      }
            });
        }
    }
	 function showQuas(cate, type){
	   	 $("#generaQuaTr").hide();
	   	 $("#profileQuaTr").show();
	   	 if (type == "GOODS") {
	   		 $("#profileQuaTr_sales").show();
	   		 var tdArr = $("#profileQuaTr").children();
	   		 tdArr.eq(0).html("物资生产型专业资质要求");
	   	 } else {
	   		 $("#profileQuaTr_sales").hide();
	   	     var tdArr = $("#profileQuaTr").children();
	   		 tdArr.eq(0).html("专业资质要求");
	   	 }
	   	 if (cate != null && cate !="" && cate !="undefined" && cate !="null"){
	   		 $("#generalIQuaName").html(cate.generalQuaNames);
	   		 $("#profileIQuaName").html(cate.profileQuaNames);
	   		 $("#profileSalesName").html(cate.profileSalesQuaNames);
	   	 }
	    }
	function hideQuas(){
	 	 $("#profileSalesIds").val("");
	 	 $("#profileIQuaIds").val("");
	 	 $("#generalIQuaIds").val("");   
	 	 $("#profileSalesNames").val("");
	 	 $("#profileIQuaNames").val("");
	 	 $("#generalIQuaNames").val("");
		 $("#generaQuaTrs").hide();
		 $("#profileQuaTrs").hide();
		 $("#profileQuaTr_saless").hide();
	 }
	function resetTips(){
    	$("#cateTipsId").text("");
    	$("#posTipsId").text("");
    	$("#descTipsId").text("");
    	$("#parentNIds").text("");
	}
	function getCurrentRoot(treeNode){ 
	 	if(treeNode.getParentNode()!=null){  
	 		var parentNode = treeNode.getParentNode(); 
	 		return getCurrentRoot(parentNode);
	 	} else {
	 		return treeNode;
	 	}
	 }
</script>
</head>

<body>
	<div class="margin-top-10 breadcrumbs">
		<div class="container">
			<ul class="breadcrumb margin-left-0">
				<li><a
					href="${pageContext.request.contextPath}/index/selectIndexNews.html">首页</a></li>
				<li><a href="#">产品目录</a></li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>
	<div class="container job-content ">
		<div class="search_box col-md-12 col-sm-12 col-xs-12">
			<input name="name" type="text" id="name" value="${name }" />
			<button type="button" onclick="query()" class="btn btn-u-light-grey">查询</button>
		</div>
		<div class="content table_box">
			<div class="col-md-3 col-sm-4 col-xs-12">
				<div class="tag-box tag-box-v3 mt15">
					<ul id="ztree" class="ztree s_ztree"></ul>
				</div>
			</div>
			<div class=" tag-box tag-box-v3 mt15 col-md-9 col-sm-8 col-xs-12">
			<table id="result" style="display: none;"  class="table table-bordered table-condensedb"  >
           	  <tbody>
           	 	<tr id="parentNameIdTr" class="dnone">
       			  <td class='info w180'>上级目录</td>
       			  <td id="parentNId">
       			      <div class="input_group col-md-6 col-sm-6 col-xs-12 p0" id="cateparentId" >
       		    	  </div>
       			  </td>
           		</tr>
           		<tr id="cateIdTr" class="dnone">
           		  <td class='info'>品目名称</td>
           		  <td id="cateTdId">
       		        <div class="input_group col-md-6 col-sm-6 col-xs-12 p0" id="cateNameId" >
       		    	</div>
           		  </td>
           		</tr>
           		<tr id="posIdTr" class="dnone">
       			  <td class='info'>编码</td>
       			  <td id="posTdId">
       				<div class="input_group col-md-6 col-sm-6 col-xs-12 p0" id ="posNameId">
       				</div>
       		      </td>
           	    </tr>
           	    <tr id="generaQuaTr" class="dnone">
       			  <td class='info'>通用资质要求</td>
       			  <td>
       				<div class="input_group col-md-6 col-sm-6 col-xs-12 p0"  id="cateNameId">
       				  <input id="generalIQuaId" type="hidden" name="generalQuaIds" />
       				  <input id="generalIQuaName" readonly="readonly" type="text" name='generalQuaNames' onclick="openLayer(1);"/>
       				  <span class="add-on">i</span>
       				</div>
       				  <span id="posTipsId" class="red clear span_style"></span>
       		      </td>
           	    </tr>
           	    <tr id="typeTrId">
       			  <td class='info'>类型</td>
       			  <td>
       				<div class="col-md-8 col-sm-8 col-xs-7 p0" id="typeId" >
       				</div>
       		      </td>
           	    </tr>
           	    <tr id="profileQuaTr" class="dnone">
       			  <td class='info'>专业资质要求</td>
       			  <td>
       				<div class="input_group col-md-6 col-sm-6 col-xs-12 p0" id="profileIQuaName">
       				</div>
       				  <span id="posTipsId" class="red clear span_style"></span>
       		      </td>
           	    </tr>
           	    <tr id="profileQuaTr_sales" class="dnone"> 
       			  <td class='info'>物资销售型专业资质要求</td>
       			  <td>
       				<div class="input_group col-md-6 col-sm-6 col-xs-12 p0" id="profileSalesName">
       				</div>
       				  <span id="posTipsId" class="red clear span_style"></span>
       		      </td>
           	    </tr>
           	    <!-- <tr>
       			  <td class='info'>是否公开</td>
       			  <td>
       				<div class="col-md-8 col-sm-8 col-xs-7 p0" id="openId" >
       				</div>
       		      </td>
           	    </tr> -->
           	    <!-- <tr class="dis_none" id="levelTrId">
           		  <td class='info'>供应商注册等级要求<span class="red">*</span></td>
           		  <td id="levelTdId">
       		        <div class="input_group col-md-6 col-sm-6 col-xs-12 p0" id="level" >
       		    	  <input id="levelId" type="text" name='level' maxlength="1" onkeyup="value=value.replace(/[^\d]/g,'')"/>
       		    	  <span class="add-on">i</span>
       		    	</div>
       		    	  <span id="levelTipsId" class="red clear span_style" />
           		  </td>
           		</tr> -->
           	    <%-- <tr class="dis_none" id="engLevelTrId">
           		  <td class='info'>供应商注册等级要求<span class="red">*</span></td>
           		  <td id="engLevelTdId">
       		        <div class="input_group col-md-6 col-sm-6 col-xs-12 p0" id="engLevel" >
       		    	  <input id="engLevelId" type="hidden" name='engLevel'/>
       		    	  <select multiple="multiple" id="engLevelSelect">
       		    	  	<c:forEach items="${levelList}" var="level">
       		    	  	  <option value="${level.id}">${level.name}</option>
       		    	  	</c:forEach>
       		    	  </select>
       		    	</div>
       		    	<span id="engLevelTipsId" class="red clear span_style" />
           		  </td>
           		</tr> --%>
           	    <%-- <tr id="uploadBtnIdTr">
       	    	  <td class='info'>图片</td>
       	    	  <td><!-- cnjewfn start -->
       	    		<div id="uploadBtnId" class="dis_none">
       	    		  <u:upload  id="uploadId"   businessId="${id}" multiple="true" exts="png,jpeg,jpg,bmp,gif"  auto="true" sysKey="2"/>
       	    		</div><!-- cnjewfn end -->
       	    		<div id="showFileId" class="picShow">
       	    		  <u:show showId="fileId" businessId="${id}" sysKey="2"/>
       	    		</div>
       	    	  </td>
           	    </tr> --%>
           	    <tr id="descIdTr" class="dnone">
       	          <td class='info'>描述</td>
       	          <td id="description">
       	        	<textarea name='description' class="col-md-10 col-sm-10 col-xs-12 h80 textArea_resizeB"   id="descId"></textarea>
       	        	<span class="red" id="descTipsId"></span>
       	          </td>
           	    </tr>
           	   </tbody>
            </table>
			
			
			
			</div>
		</div>
		<%-- <div class="content table_box">
				<table class="table table-bordered table-condensed table-hover">
					<thead>
						<tr class="info">
							<th class="w50">序号</th>
							<th class="w180">品目名称</th>
							<th class="w180">编码</th>
							<th class="w180">通用资质要求</th>
							<th class="w110">图片</th>
							<th class="w300">描述</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${list.list }" var="data" varStatus="vs">
							<tr class="pointer">
								
								<td class="tc" >${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
								<c:if test="${fn:length(data.name)>22}">
									<td class="tl pl20" title="${data.name }">${fn:substring(data.name,0,22)}...</td>
								</c:if>
								<c:if test="${fn:length(data.name)<=22}">
									<td class="tl pl20">${data.name }</td>
								</c:if>
								<td class="tl pl20" >
									${data.code }
								</td>
								<td class="tc"  title="${data.generalQuaNames}">
									<c:if test="${fn:length(data.generalQuaNames)<=13}">
										${data.generalQuaNames}
									</c:if>
									<c:if test="${fn:length(data.generalQuaNames)>13}">
									    ${fn:substring(data.generalQuaNames,0,13)}...
									</c:if>
								</td>
									<td class="tl pl20" ></td>
									<td class="tl pl20"  title="${data.description}">
										<c:if test="${fn:length(data.description)<=20}">
											${data.description}
										</c:if>
										<c:if test="${fn:length(data.description)>20}">
										    ${fn:substring(data.description,0,20)}...
										</c:if>
									</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div id="pagediv" align="right"></div> --%>

	</div>

	<jsp:include page="/index_bottom.jsp"></jsp:include>
</body>
<script type="text/javascript">
	ztreeInit();
</script>
</html>
