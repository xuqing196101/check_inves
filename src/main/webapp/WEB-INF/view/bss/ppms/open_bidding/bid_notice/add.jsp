<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html class=" js cssanimations csstransitions" lang="en">
<head>
	<%@ include file="/WEB-INF/view/common.jsp"%>
	<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
    <script type="text/javascript">
    $(function() {
  		//获取查看或操作权限
       	var isOperate = $('#isOperate', window.parent.document).val();
       	if(isOperate == 0) {
       		//只具有查看权限，隐藏操作按钮
			$(":button").each(function(){ 
				$(this).hide();
            }); 
            $("#a").hide();
            $("input[type='radio']").each(function(){
            	$(this).attr("disabled",true); 
            });
            $("input[name='categoryName']").attr("disabled",true); 
		}
    })  
    
	var treeid = null;
	function beforeClick(treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("treeCategory");
		zTree.checkNode(treeNode, !treeNode.checked, null, true);
		return false;
	}
	
	function zTreeBeforeCheck(treeId, treeNode) {
      if (treeNode.isParent == true) {
          layer.msg("请在末节点上进行操作！");
          return false;
        } else {
        return true;        
        }
    }
	
	function onCheck(e, treeId, treeNode) {
		var clickFlag;
        if(treeNode.checked) {
          	clickFlag = "1";
        } else {
          	clickFlag = "0";
        }
        var articleId = "${articleId}";
        var categoryIds = $("#cId").val();
        var categoryNames = $("#categorySel").val();
        if(clickFlag == "1") {
          $.ajax({
            url: "${pageContext.request.contextPath}/article/saveArtCategory.do",
            contentType:'application/json;charset=UTF-8',
            async: false,
            data: {
              "categoryIds":categoryIds,
              "categoryNames":encodeURI(categoryNames),
              "articleId": articleId,
              "categoryId": treeNode.id,
              "type": clickFlag
            },
            dataType: "json",
            success: function(data){
            	$("#cId").val(data.categoryIds);
        		$("#categorySel").val(data.categoryNames);
            }
          });
        } else {
          $.ajax({
            url: "${pageContext.request.contextPath}/article/saveArtCategory.do",
            contentType:'application/json;charset=UTF-8',
            async: false,
            data: {
              "categoryIds":categoryIds,
              "categoryNames":encodeURI(categoryNames),
              "articleId": articleId,
              "categoryId": treeNode.id,
              "type": clickFlag
            },
            dataType: "json",
            success: function(data){
            	$("#cId").val(data.categoryIds);
        		$("#categorySel").val(data.categoryNames);
            }
	      });
       	}
	}
	
	/*点击事件*/
    function zTreeOnClick(event,treeId,treeNode){
    
  	  if (treeNode.isParent == true) {
          layer.msg("请选择末节点");
          return false;
      }
	  if (!treeNode.isParent) {
	  	$("#cId").val(treeNode.id);
        $("#categorySel").val(treeNode.name);
	    hideCategory();
	  }
    }
	
	function showCategory(articleId) {
		//回显勾选
		//var backCategoryIds = $("#cId").val();
		//栏目类型
		var rootCode = "${rootCode}";
		var zTreeObj;
		var zNodes;
		var setting = {
			async: {
				autoParam: ["id"],
				enable: true,
				url: "${pageContext.request.contextPath}/article/categoryTree.do",
				otherParam: {
					"articleId": articleId,
					//"backCategoryIds":backCategoryIds,
					"rootCode":rootCode,
				},
				dataFilter: ajaxDataFilter,
				dataType: "json",
				type: "post"
			},
			/* check: {
				enable: true,
				chkStyle: "checkbox",
				chkboxType: {
					"Y": "ps",
					"N": "ps"
				}, //勾选checkbox对于父子节点的关联关系  
			}, */
			view: {
				dblClickExpand: false
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				/* beforeClick: beforeClick,
				onCheck: onCheck,
				beforeCheck: zTreeBeforeCheck, */
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
    
    
    
  	//导入模板
    function inputTemplete(projectId){
        var iframeWin;
        layer.open({
          type: 2, //page层
          area: ['700px', '350px'],
          title: '导入模板',
          closeBtn: 1,
          shade:0.01, //遮罩透明度
          shift: 1, //0-6的动画形式，-1不开启
          offset: '10px',
          shadeClose: false,
          content: '${pageContext.request.contextPath}/resultAnnouncement/getAll.html?projectId='+projectId,
          success: function(layero, index){
            iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
          }
        });
    }
        //导出
        function exportWord(){
        	var content = ue.getContent();
        	if(content == null || content == ""){
        		layer.alert("请填写公告内容",{offset: '222px', shade:0.01});
        	}else{
	            $("#form").attr("action",'${pageContext.request.contextPath}/open_bidding/export.html');   
	            $("#form").submit();
        	}
        }
        //预览
        function pre_view(){
             //$("#form").attr("action",'${pageContext.request.contextPath}/open_bidding/printView.html');   
             //$("#form").submit();
             var ue = UE.getEditor('editor'); 
    		 var content = ue.getContent();
             $("#preview").removeClass("dnone");
             $("#pre_name").append($("#name").val());
             $("#pre_content").append(content);
             $("#form").addClass("dnone");
        }
        
        //预览返回
        function pre_back(){
        	$("#preview").addClass("dnone");
        	$("#form").removeClass("dnone");
        	$("#pre_content").empty();
        	$("#pre_name").empty();
        }
        
        //提交
        function publish(){
        	var categoryId = $("#cId").val();
       		if (categoryId != null && categoryId != "") {
	        	var articleId = $("#articleId").val();
	        	var saveStatus = $("#is_saveNotice").val();
	        	var noticeType = $("#noticeType").val();
	        	var flowDefineId = $("#flowDefineId").val();
        		 $.ajax({
					    type: 'post',
					    url: "${pageContext.request.contextPath}/open_bidding/saveBidNotice.do?flag=1",
					    data : $('#form').serializeArray(),
					    dataType:'json',
					    success:function(result){
		                    if(!result.success){
		                        layer.msg(result.message,{offset: ['220px']});
		                    }else{
		                        parent.window.setTimeout(function(){
		                        	if (noticeType == 'win') {
										window.location.href = "${pageContext.request.contextPath}/open_bidding/winNotice.html?projectId="+result.obj.projectId+"&flowDefineId="+flowDefineId;
									} else if (noticeType == 'purchase' ) {
		                            	window.location.href = "${pageContext.request.contextPath}/open_bidding/bidNotice.html?projectId="+result.obj.projectId+"&flowDefineId="+flowDefineId;
									}
		                        }, 500);
		                        //layer.msg(result.message,{offset: ['220px']});
		                    }
		                },
		                error: function(result){
		                    layer.msg("提交失败",{offset: ['220px']});
		                }
				});
			} else {
				layer.msg("请选择产品类别",{offset: '222px'});
			}
	        	 /* var iframeWin;
	            layer.open({
	              type: 2, //page层
	              area: ['400px', '200px'],
	              title: '发布招标公告',
	             // skin: 'layui-layer-rim',
	              closeBtn: 1,
	              shade:0.01, //遮罩透明度
	              shift: 1, //0-6的动画形式，-1不开启
	              offset: '100px',
	              shadeClose: false,
	              content: '${pageContext.request.contextPath}/open_bidding/publishEdit.html?id='+articleId+'&noticeType='+noticeType+'&flowDefineId='+flowDefineId,
	              success: function(layero, index){
	                iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
	              }
	            }); */
        } 
       
       function save(){
       		var categoryId = $("#cId").val();
       		if (categoryId != null && categoryId != "") {
	       		$.ajax({
				    type: 'post',
				    url: "${pageContext.request.contextPath}/open_bidding/saveBidNotice.do?flag=0",
				    dataType:'json',
				    data : $('#form').serialize(),
				    success: function(data) {
				    	if(!data.success){
	                        layer.msg(data.message,{offset: ['220px']});
	                    }else{
					    	//$("#is_saveNotice").val("isok");
					        layer.msg(data.message,{offset: '222px'});
	                    }
				    }
				});
			} else {
				layer.msg("请选择产品类别",{offset: '222px'});
			}
       }
       
       $(function(){
			var range="${article.range}";
			if(range !=null && range != ""){
				if(range==2){
					$("input[name='ranges']").attr("checked",true); 
				}else{
					$("input[name='ranges'][value="+range+"]").attr("checked",true); 
				}
			}
		});
        
        function searchs(articleId){
		var rootCode = "${rootCode}";
		var name=$("#search").val();
		if(name!=""){
		 	var zNodes;
			var zTreeObj;
			var setting = {
				async: {
						autoParam: ["id"],
						enable: true,
						url: "${pageContext.request.contextPath}/article/categoryTree.do",
						otherParam: {
							"articleId": articleId,
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
			// 加载中的菊花图标
			var loading = layer.load(1);
			
			$.ajax({
				url: "${pageContext.request.contextPath}/article/searchCategory.do",
				data: { "name" : encodeURI(name), "rootCode" : rootCode},
				async: false,
				dataType: "json",
				success: function(data){
					if (data.length == 1) {
						layer.msg("没有符合查询条件的产品类别信息！");
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
    </script>
</head>

<body>
   	<div id="categoryContent" class="categoryContent" style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
		<div class=" input_group col-md-3 col-sm-6 col-xs-12 col-lg-12 p0">
		    <div class="w100p">
		    	<input type="text" id="search" class="fl m0">
			      <img alt="" style="position:absolute; top:8px;right:10px;" src="${pageContext.request.contextPath }/public/backend/images/view.png"  onclick="searchs('${articleId}')">
		    </div>
		    <ul id="treeCategory" class="ztree" style="margin-top:0;"></ul>
		</div>
   	</div>
	 <form  method="post" id="form" > 
	    <input type="hidden" id="is_saveNotice" value="${saveStatus}">
	    <input type="hidden" name="flowDefineId" id="flowDefineId" value="${flowDefineId}">
	    <input type="hidden" id="noticeType" name="noticeType" value="${noticeType}">
	    <input type="hidden" name="articleTypeId" id="articleTypeId" value="${article.articleType.id}">
	    <input type="hidden" name="secondArticleTypeId" id="articleTypeId" value="${article.secondArticleTypeId}">
	    <input type="hidden" name="threeArticleTypeId" id="articleTypeId" value="${article.threeArticleTypeId}">
	    <input type="hidden" name="fourArticleTypeId" id="articleTypeId" value="${article.fourArticleTypeId}">
	    <input type="hidden" name="lastArticleTypeId" id="articleTypeId" value="${article.lastArticleType.id}">
	    <input type="hidden" name="id" id="articleId" value="${articleId}">
	    <input type="hidden" name="projectId" value="${projectId}">
		<ul class="clear col-md-12 col-sm-12 col-xs-12 p0 mb10">
			<li class="col-md-12 col-sm-12 col-xs-12">
				<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">
				<div class="star_red">*</div>公告标题：</span>
				<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
				 <c:if test="${article.name == null && noticeType == 'purchase'}">
				 	<input type="text" id="name" name="name" value="${project.name}采购公告(${project.projectNumber})">
				 </c:if>
				 <c:if test="${article.name == null && noticeType == 'win'}">
				 	<input type="text" id="name" name="name" value="${project.name}中标公示(${project.projectNumber})">
				 </c:if>
				 <c:if test="${article.name != null}">
				 	<input type="text" id="name" name="name" value="${article.name}"><br>
				 </c:if>
			 	</div>
			 </li>
			 <li class="col-md-3 col-sm-6 col-xs-12 clear pl0">
			 	<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">
					<div class="star_red">*</div>发布范围：
				</span>
				<div class="input-append col-md-12 col-sm-12 col-xs-12 p0">
		            <label class="fl margin-bottom-0"><input type="radio" name="ranges"  value="0">内网</label>
		            <label class="ml30 fl"><input type="radio" name="ranges" value="2" >内外网</label>
				</div>
			</li>
			<li class="col-md-3 col-sm-6 col-xs-12 pl0">
				<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">
					<div class="star_red">*</div>选择产品类别：
				</span>
				<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 col-lg-12 p0">
					<input id="cId" name="categoryIds"  type="hidden" value="${categoryIds}">
			        <input id="categorySel"  type="text" name="categoryName"  readonly value="${categoryNames}"  onclick="showCategory('${articleId}');" />
					<div class="drop_up" onclick="showCategory('${articleId}');">
					    <img src="${pageContext.request.contextPath}/public/backend/images/down.png" />
			        </div>
					<div class="cue" id="ERR_category">${ERR_category}</div>
				</div>
			</li>
	        <li class="col-md-12 col-sm-12 col-xs-12 pl0">
	        	<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">
	        		<div class="star_red">*</div>信息正文：
	        	</span>
				 <c:if test='${article.name == null || article.name == ""}'>
		             <input type="hidden" id="articleContent" value='${article1.content}'>
				 </c:if>
				 <c:if test='${article.name != null && article.name != ""}'>
		             <input type="hidden" id="articleContent" value='${article.content}'>
				 </c:if>
				 <div class="col-md-12 col-sm-12 col-xs-12 p0">
             	 	<script id="editor" name="content" type="text/plain" class="ml125 w900 edit-posit"></script>
			 	 </div>
			 </li>
                          <%-- 上传附件： 
             <u:upload id="a" groups="a,c" businessId="${articleId }" multiple="true" sysKey="${sysKey }" typeId="${typeId }" auto="true" />
             <u:show  showId="b" groups="b,d,c"  businessId="${articleId }" sysKey="${sysKey }" typeId="${typeId }"/> --%>
     			<c:if test="${operatorId eq null}">
     			
     		  <li class="col-md-3 col-sm-6 col-xs-12 pl15 mt5">
	              <span class="fl" >公告附件：</span>
	               <u:upload id="a" groups="a,c,e"  buttonName="上传附件" businessId="${articleId}" multiple="true" sysKey="${sysKey}" typeId="${typeId}" auto="true" />
             		 <u:show  showId="b" groups="b,d,f,g"  businessId="${articleId}" sysKey="${sysKey}" typeId="${typeId}"/>
              </li>
              <%-- <li class="col-md-3 col-sm-6 col-xs-12 pl15">
	              <span class="" >审批附件: </span>
	                <u:upload id="c"  groups="a,c,e" businessId="${articleId}"  sysKey="${sysKey}" typeId="${typeId_examine}" auto="true" />
                  <u:show  showId="d"  groups="b,d,f,g" businessId="${articleId}" sysKey="${sysKey}" typeId="${typeId_examine}"/>
              </li> --%>
              <li class="col-md-6 col-sm-6 col-xs-12 pl15 mt5">
	              <span class="fl" >单位及保密委员会审核表： </span>
	                <u:upload id="e" exts="png,jpeg,jpg,bmp" groups="a,c,f" multiple="true" businessId="${articleId}"  sysKey="${sysKey}" typeId="${security}" auto="true" />
                  <u:show  showId="f"  groups="b,d,f,g" businessId="${articleId}" sysKey="${sysKey}" typeId="${security}"/>
              </li>
              </c:if>	
           </ul>
                  <!-- 按钮 -->
        <div class="w100p tc fl">
		     <%-- <input type="button" class="btn btn-windows input" onclick="inputTemplete('${projectId }')" value="模板导入"></input> --%>
	         <!-- <input type="button" class="btn btn-windows output" onclick="exportWord()" value="导出"></input> -->
	         <!-- <input type="button" class="btn btn-windows git" onclick="pre_view()" value="预览"></input>   -->
	         <input type="button" class="btn btn-windows save" onclick="save()" value="保存"></input>
	         <!-- <input type="button" class="btn btn-windows apply" onclick="publish()" value="提交"></input>  --> 
	    </div>
      </form>
      
	<div class="dnone" id="preview">
	   	<!-- <div class="col-md-12 p30_40 border1 margin-top-20"> -->
	   		<div class="col-md-12 col-xs-12 col-sm-12 p0 mb5">
            <input type="button" class="btn " value="打印" onclick="window.print();" id="print"/>
            <input class="btn btn-windows back" onclick="pre_back();" value="返回" type="button">
        	</div>
		     <h3 class="tc f22">
			   <div class="title bbgrey" id="pre_name"></div>
			 </h3>
			 <div class="source" >
			 </div>
			 <div class="clear margin-top-20 new_content" id="pre_content">
			 </div>
			 <div class="extra_file">
			 	<div class="">
			 		<li class="col-md-3 col-sm-6 col-xs-12 pl15">
	              <span class="" >公告附件：</span>
             		 <u:show  showId="g" groups="b,d,f,g"  businessId="${articleId}" delete="false" sysKey="${sysKey}" typeId="${typeId}"/>
	              </li>
			 	</div>
			 </div>
	</div>
   <script type="text/javascript">
    var option ={
    	toolbars: [
          [
            'fullscreen', 'source', '|', 'undo', 'redo', '|','bold', 
            'italic', 'underline', 'fontborder', 'strikethrough',
            'superscript', 'subscript', 'removeformat', 'formatmatch', 
            'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor',
            'backcolor', 'insertorderedlist', 'insertunorderedlist', 
            'selectall', 'cleardoc', '|','rowspacingtop', 'rowspacingbottom',
            'lineheight', '|','customstyle', 'paragraph', 'fontfamily', 
            'fontsize', '|', 'directionalityltr', 'directionalityrtl', 'indent', '|',
            'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|',
            'anchor','pagebreak', '|', 'horizontal', 'date', 'time', 'spechars', '|',
            'preview', 'searchreplace', 'help',
          ]
        ]	
    }
    var ue = UE.getEditor('editor', option);
    var content = $("#articleContent").val(); 
    ue.ready(function(){
        //需要ready后执行，否则可能报错
       // ue.setContent("<h1>欢迎使用UEditor！</h1>");
        ue.setContent(content);
        ue.setHeight(200);
    });
    </script>
</body>
</html>



