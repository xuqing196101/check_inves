<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
  <head>
    <title>经济技术审查项</title>
    <jsp:include page="/WEB-INF/view/common.jsp"></jsp:include>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page"> 
    <!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->
  </head>
  <script type="text/javascript">
    //新增一个评审项
    function addItem(obj,kindId){
    	//得到点击坐标。
        var y;  
        oRect = obj.getBoundingClientRect();  
        y=oRect.top-150;  
    	$("#faKind").val(kindId);
    	layer.open({
            type: 1,
            title: '添加评审项信息',
            area: ['500px', '300px'],
            closeBtn: 1,
            shade:0.01, //遮罩透明度
            moveType: 1, //拖拽风格，0是默认，1是传统拖动
            shift: 1, //0-6的动画形式，-1不开启
            offset: y,
            shadeClose: false,
            content: $("#openDiv"),
          });
    }
    
    //修改评审项
    function editItem(obj,id){
    	var flowDefineId = "${flowDefineId}";
    	//得到点击坐标。
        var y;  
        oRect = obj.getBoundingClientRect();  
        y=oRect.top-150; 
        layer.open({
            type: 2,
            title: '修改评审项信息',
            area: ['500px', '300px'],
            closeBtn: 1,
            shade:0.01, //遮罩透明度
            moveType: 1, //拖拽风格，0是默认，1是传统拖动
            shift: 1, //0-6的动画形式，-1不开启
            offset: y,
            shadeClose: false,
            content: '${pageContext.request.contextPath}/firstAudit/editItem.html?id='+id+'&isConfirm=1'+'&flowDefineId='+flowDefineId
          });
    }
    
    //删除评审项 
    function delItem(id){
    	var flowDefineId = "${flowDefineId}";
    	layer.confirm('您确定要删除吗?', {title:'提示',offset: '50px',shade:0.01}, function(index){
	    	$.ajax({   
	            type: "POST",  
	            url: "${pageContext.request.contextPath}/firstAudit/delItem.html?id="+id,        
	            dataType:'json',
	            success:function(result){
	                if(!result.success){
	                    layer.msg(result.msg,{offset: ['150px']});
	                }else{
	                	 var packageId = $("#packageId").val();
	                	 var projectId = $("#projectId").val();
	                     window.location.href = '${pageContext.request.contextPath}/intelligentScore/editPackageScore.html?packageId='+packageId+'&projectId='+projectId+'&flowDefineId='+flowDefineId;
	                    layer.msg(result.msg,{offset: ['150px']});
	                    layer.close(index);
	                }
	            },
	            error: function(result){
	                layer.msg("删除失败",{offset: ['150px']});
	            }
	       });       	
        });
    }
    
    //关闭弹窗
    function cancel(){
        layer.closeAll();
    }
    
    //返回模板列表
    function goBack(){
    	
    }
    
    //保存评审项
    function saveItem(){
    	var flowDefineId = "${flowDefineId}";
    	$.ajax({   
            type: "POST",  
            url: "${pageContext.request.contextPath}/firstAudit/savePackageFirstAudit.html",        
            data : $('#form2').serializeArray(),
            dataType:'json',
            success:function(result){
                if(!result.success){
                    layer.msg(result.msg,{offset: ['150px']});
                }else{
                    var packageId = $("#packageId").val();
                    var projectId = $("#projectId").val();
                    window.location.href = '${pageContext.request.contextPath}/intelligentScore/editPackageScore.html?packageId='+packageId+'&projectId='+projectId+'&flowDefineId='+flowDefineId;
                    layer.closeAll();
                    layer.msg(result.msg,{offset: ['150px']});
                }
            },
            error: function(result){
                layer.msg("添加失败",{offset: ['150px']});
            }
       });    
    }
    
    //引入模板内容
    function loadTemplat(projectId, packageId){
   	 	var flowDefineId = "${flowDefineId}";
    	var fatId = $("#fatId").val();
    	if (fatId != null && fatId != '') {
    		var index = layer.load(1, {
					  shade: [0.2,'#BFBFBF'], //0.1透明度的白色背景
					  offset: ['222px', '390px']
					});
	    	$.ajax({   
	            type: "POST",  
	            url: "${pageContext.request.contextPath}/firstAudit/loadTemplat.html?isConfirm=1",   
	            data:{"id":fatId,"projectId":projectId,"packageId":packageId},
	            dataType:'json',
	            success:function(result){
	                if(!result.success){
	                    layer.msg(result.msg,{offset: ['150px']});
	                }else{
	                    var packageId = $("#packageId").val();
	                    var projectId = $("#projectId").val();
	                    window.location.href = '${pageContext.request.contextPath}/intelligentScore/editPackageScore.html?packageId='+packageId+'&projectId='+projectId+'&flowDefineId='+flowDefineId;
	                    layer.closeAll();
	                    layer.msg(result.msg,{offset: ['150px']});
	                }
	            },
	            error: function(result){
	                layer.msg("添加失败",{offset: ['150px']});
	            }
	       }); 
	    }else {
			layer.msg("请选择模板",{offset: ['150px']});
		}
    }
    
    
    /*点击事件*/
    function zTreeOnClick(event,treeId,treeNode){
  	  if (treeNode.isParent == true) {
          layer.msg("请选择末节点",{offset: ['150px']});
          return false;
      }
	  if (!treeNode.isParent) {
	  	$("#cId").val(treeNode.id);
        $("#categorySel").val(treeNode.name);
	    hideCategory();
	    findTem();
	  }
    }
	
	function showCategory(tempId) {
		var rootCode = null;
		var zTreeObj;
		var zNodes;
		var setting = {
			async: {
				autoParam: ["id"],
				enable: true,
				url: "${pageContext.request.contextPath}/auditTemplat/categoryTree.do",
				otherParam: {
					"tempId": tempId,
					"rootCode":rootCode,
				},
				dataFilter: ajaxDataFilter,
				dataType: "json",
				type: "post"
			},
			view: {
				dblClickExpand: false
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				onClick:zTreeOnClick,
			}
		};
		zTreeObj = $.fn.zTree.init($("#treeCategory"), setting, zNodes);
		zTreeObj.expandAll(true); //全部展开
		var cityObj = $("#categorySel");
		var cityOffset = $("#categorySel").offset();
		$("#categoryContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
		$("body").bind("mousedown", onBodyDownOrg);
	}
	
	function ajaxDataFilter(treeId, parentNode, childNodes) {
		// 判断是否为空
		if(childNodes) {
			// 判断如果父节点是第二级,则将查询出来的子节点全部改为isParent = false
			if(parentNode != null && parentNode != "undefined" && parentNode.level == 1) {
				for(var i = 0; i < childNodes.length; i++) {
					childNodes[i].isParent += false;
				}
			}
		}
		return childNodes;
	}
	function hideCategory() {
		$("#categoryContent").fadeOut("fast");
		$("body").unbind("mousedown", onBodyDownOrg);
	}
	function onBodyDownOrg(event) {
		if (!(event.target.id == "menuBtn" || event.target.id == "categorySel" || event.target.id == "categoryContent" || $(event.target).parents("#categoryContent").length>0)) {
			hideCategory();
		}
	}
	
	function searchs(tempId){
		var name=$("#search").val();
		if(name!=""){
		 	var zNodes;
			var zTreeObj;
			var setting = {
				async: {
						autoParam: ["id"],
						enable: true,
						url: "${pageContext.request.contextPath}/auditTemplat/categoryTree.do",
						otherParam: {
							"tempId": tempId,
						},
						dataFilter: ajaxDataFilter,
						dataType: "json",
						type: "post"
					},
				view: {
					dblClickExpand: false
				},
				data: {
					simpleData: {
						enable: true
					}
				},
				callback: {
					onClick:zTreeOnClick,
				}
			};
			// 加载中的菊花图标
			var loading = layer.load(1);
			
			$.ajax({
				url: "${pageContext.request.contextPath}/auditTemplat/searchCategory.do",
				data: { "name" : encodeURI(name)},
				async: false,
				dataType: "json",
				success: function(data){
					if (data.length == 3) {
						layer.msg("没有符合查询条件的产品类别信息！",{offset: ['150px']});
					} else {
						zNodes = data;
						zTreeObj = $.fn.zTree.init($("#treeCategory"), setting, zNodes);
						zTreeObj.expandAll(true);//全部展开
					}
					// 关闭加载中的菊花图标
					layer.close(loading);
				}
			});
		}else{
			showCategory();
		}
	}
    
    function getTotal(){
		var allTr = document.getElementsByTagName("tr");
		var totalScore = 0.0 ;
		for (var i = 1; i < allTr.length; i++) {
			var score = $(allTr[i]).find("td:last").text();
			var reg = /^\d+\.?\d*$/;
			var flag = false;
			if(!reg.exec(score)) {
				score = 0;
			}
			totalScore += parseFloat(score) ;
		};
		$("#totalScore").text(totalScore);
		var score = $("#totalScore").text();
		var projectId = '${projectId}';
		var packageId = '${packageId}';
        if (score == 100) {
           $.ajax({   
                type: "get",  
                url: "${pageContext.request.contextPath}/intelligentScore/checkIsCheck.do?projectId="+projectId+"&packageId="+packageId,  
                dataType:'json',
                success:function(result){
                    if (result == 1) {
                        layer.msg("请选择评审计算价格得分的唯一标识,必须要有一个",{offset: ['150px']});
                    }
                },
                error: function(result){
                }
           });
        }
    }
       
    $(function() {
		initTem();
	});
	
	function initTem(){
		var html = "<option value=''>请选择</option>";
		$.ajax({
				url: "${pageContext.request.contextPath}/firstAudit/find.do",
				data: {"type" : "REVIEW_CHECK_ET"},
				dataType: 'json',
				success: function(result){
					$("#fatId").empty();	
					if (result.success == false && typeof(result.success) != "undefined") {
						//layer.msg(result.msg,{offset: ['150px']});
					} else {
						if (result.length > 0) {
							for (var i = 0; i < result.length; i++) {
								html += "<option value='"+result[i].id+"'>"+result[i].name+"</option>";	
							}
						}
					}
					$("#fatId").append(html);
				}
			});
	}
	
	function findTem(){
		var categoryId = $("#cId").val();
		var html = "<option value=''>请选择</option>";
		$.ajax({
				url: "${pageContext.request.contextPath}/firstAudit/find.do",
				data: {"categoryId" : categoryId, "type" : "REVIEW_CHECK_ET"},
				dataType: 'json',
				success: function(result){
					$("#fatId").empty();	
					if (result.success == false && typeof(result.success) != "undefined") {
						layer.msg(result.msg,{offset: ['150px']});
					} else {
						if (result.length > 0) {
							for (var i = 0; i < result.length; i++) {
								html += "<option value='"+result[i].id+"'>"+result[i].name+"</option>";	
							}
						}
					}
					$("#fatId").append(html);
				}
			});
	}
	
	function clearSearch() {
				$("#categorySel").val("");
        $("#fatId option:selected").removeAttr("selected");
        initTem();
      }
  </script>
<body>  
	<div id="categoryContent" class="categoryContent" style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
		<div class=" input_group col-md-3 col-sm-6 col-xs-6 col-lg-12 p0">
		    <div class="w100p">
		    	<input type="text" id="search" class="fl m0">
			      <img alt="" style="position:absolute; top:8px;right:10px;" src="${pageContext.request.contextPath }/public/backend/images/view.png"  onclick="searchs()">
		    </div>
		    <ul id="treeCategory" class="ztree" style="margin-top:0;"></ul>
		</div>
   	</div>
    <h2 class="list_title">${packages.name}经济技术评审项编辑</h2>
    <c:if test="${isView != 1}">
	    <div class="search_detail ml0">
	        <ul class="demand_list">
	           <li class="w300">
	             <label class="fl">所属产品目录：</label>
	            	<div class="input_group w200">
						     <input id="cId" name="categoryId"  type="hidden" value="${categoryId}">
				        <input id="categorySel"  type="text" name="categoryName" readonly value="${categoryName}"  onclick="showCategory();" />
					     </div>
		       </li>
		       <li>
		       <label class="fl">模板选择</label>
		       </li>
		       <li>
					<select id="fatId" class="w180">
		            </select>
	           </li>
	           <button type="button" onclick="loadTemplat('${projectId}','${packageId}')" class="btn">确定选择</button>
	           <button type="reset" class="btn" onclick="clearSearch();">重置</button>
	           <%-- <div class="pull-right">
	              <button type="button" onclick="loadOtherPackage('${packageId}','${projectId}')" class="btn">引入模板</button>
	           </div> --%>
	        </ul>
	        <div class="clear"></div>
	     </div>
    </c:if>
    <div class="content">
        <table class="table table-bordered table-condensed table-hover">
            <thead>
               <tr>
                  <th class="info" colspan="2">评审名称</th>
                  <th class="info">评审内容</th>
               </tr>
            </thead>
            <c:forEach items="${dds}" var="d" varStatus="vs">
               <!-- 如果没有评审项 ，显示空td-->
               <c:if test="${d.code == 'ECONOMY' && items1.size() == 0}">
                 <tr id="${d.id}">
                    <td rowspan="2" class="w150">
                        <input type="hidden" value="2">
                        <span class="fl">${d.name}</span>
                        <c:if test="${flag != '1' }">
                            <a class="addItem item_size" onclick="addItem(this,'${d.id}');" ></a>
                        </c:if>
                    </td>
                 </tr>
                 <tr>
                     <td></td>
                     <td></td>
                 </tr>
               </c:if>
               <c:if test="${d.code == 'TECHNOLOGY' && items2.size() == 0}">
                 <tr id="${d.id}">
                    <td rowspan="2" class="w150">
                        <input type="hidden" value="2">
                        <span class="fl">${d.name}</span>
                        <c:if test="${flag != '1' }">
                            <a class="addItem item_size" onclick="addItem(this,'${d.id}');" ></a>
                        </c:if>
                    </td>
                 </tr>
                 <tr>
                     <td></td>
                     <td></td>
                 </tr>
               </c:if>
               <!-- 如果有评审项 ，加载符合性评审项-->
               <c:if test="${d.code == 'ECONOMY' && items1.size() > 0}">
                 <tr id="${d.id}">
                    <td rowspan="${items1.size() + 1}" class="w150">
                        <input type="hidden" value="${items1.size() + 1}">
                        <span class="fl">${d.name}</span>
                        <c:if test="${flag != '1' }">
                            <a class="addItem item_size" onclick="addItem(this,'${d.id}');" ></a>
                        </c:if>
                    </td>
                 </tr>
                 <c:forEach items="${items1}" var="i" varStatus="iv">
                 <tr>
                     <td class="w260">
                         <c:if test="${i.kind == d.id}">
                             <span class="fl">${i.name}</span>
                           <c:if test="${flag != '1' }">
                              <div class="fr">
	                           <a href="javascript:void(0);" title="编辑" onclick="editItem(this,'${i.id}');" class="item_size editItem"></a>
	                           <a href="javascript:void(0);" title="删除" onclick="delItem('${i.id}')" class="item_size deleteItem" ></a>
                              </div>
                           </c:if>
                         </c:if>
                     </td>
                     <td>
                         <c:if test="${i.kind == d.id}">
                         ${i.content}
                      </c:if>
                     </td>
                 </tr>  
                 </c:forEach>
                </c:if>
                <!-- 如果有评审项 ，加载资格性评审项-->
                <c:if test="${d.code == 'TECHNOLOGY' && items2.size() > 0}">
                 <tr id="${d.id}">
                    <td rowspan="${items2.size() + 1}" class="w150">
                        <input type="hidden" value="${items2.size() + 1}">
                        <span class="fl">${d.name}</span>
                        <c:if test="${flag != '1' }">
                            <a class="addItem item_size" onclick="addItem(this,'${d.id}');" ></a>
                        </c:if>
                    </td>
                 </tr>
                 <c:forEach items="${items2}" var="i" varStatus="iv">
                 <tr>
                     <td class="w260">
                         <c:if test="${i.kind == d.id}">
                             <span class="fl">${i.name}</span>
                             <c:if test="${flag != '1' }">
                             <div class="fr">
                              <a href="javascript:void(0);" title="编辑" onclick="editItem(this,'${i.id}');" class="item_size editItem"></a>
                              <a href="javascript:void(0);" title="删除" onclick="delItem('${i.id}')" class="item_size deleteItem" ></a>
                              </div>
                             </c:if>
                         </c:if>
                     </td>
                     <td>
                         <c:if test="${i.kind == d.id}">
                            ${i.content}
                         </c:if>
                     </td>
                 </tr>  
                 </c:forEach>
                </c:if>
             </c:forEach>
        </table>
    </div>
    <c:if test="${flag != '1' }">
	    <div class="mt40 tc mb50">
	    	<button class="btn btn-windows back" onclick="window.location.href='${pageContext.request.contextPath}/intelligentScore/packageList.html?projectId=${projectId}&flowDefineId=${flowDefineId}'">返回</button>
	    </div>
    </c:if>
    <div id="openDiv" class="dnone layui-layer-wrap" >
      <form id="form2" method="post" >
        <div class="drop_window">
              <input type="hidden" name="projectId" id="projectId" value="${projectId}">
              <input type="hidden" name="packageId" id="packageId" value="${packageId}">
              <input type="hidden" name="flowDefineId" id="flowDefineId" value="${flowDefineId}">
              <input type="hidden" name="kind" id="faKind" > 
              <input type="hidden" name="isConfirm" value="1">
              <ul class="list-unstyled">
                  <li class="col-sm-6 col-md-6 col-lg-6 col-xs-6 pl15">
                    <label class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>评审名称</label>
	                <span class="col-md-12 col-sm-12 col-xs-12 p0 input-append input_group">
	                   <input name="name" id="itemName" maxlength="30" type="text">
	                </span>
                  </li>
                  <li class="col-sm-6 col-md-6 col-lg-6 col-xs-6">
                    <label class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>序号</label>
	                <div class="col-md-12 col-sm-12 col-xs-12 p0 input-append input_group">
	                   <input  name="position" id="itemPosition" maxlength="10" type="text">
	                </div>
                 </li>
                 <li class="col-md-12 col-sm-12 col-xs-12 mb20">
                   <label class="col-md-12 pl20 col-xs-12 padding-left-5"><div class="star_red">*</div>评审内容</label>
                   <span class="col-md-12 col-sm-12 col-xs-12 p0">
                    <textarea class="w100p h80" id="itemContent" name="content" maxlength="200" title="" placeholder=""></textarea>
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
</body>
</html>