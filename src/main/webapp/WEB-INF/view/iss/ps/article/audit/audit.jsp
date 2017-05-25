<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

  <head>
    <link href="${pageContext.request.contextPath }/public/select2/css/select2.css" rel="stylesheet" />
    <%@ include file="/WEB-INF/view/common.jsp"%>
    <%@ include file="/WEB-INF/view/common/webupload.jsp"%>
    <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath }/public/select2/js/select2.js"></script>
    <script type="text/javascript">
      	<!-- ztree 产品类别 --> 
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
	        var articleId = "${article.id}";
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
		var threeType = $("#threeType").select2("data").text;
		var rootCode = null;
		if (threeType == "进口" || threeType == "物资") {
			rootCode = "GOODS";
		}
		if (threeType == "工程") {
			rootCode = "PROJECT";
		}
		if (threeType == "服务") {
			rootCode = "SERVICE";
		}
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
				type: "get"
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
    
    
      $(function() {
        var range = "${article.range}";
         $("input[name='ranges']").each(function(){
		  	  if($(this).val()==range){
   			      $(this).attr('checked','true');
   		  	  }
    	});
        $("#articleTypeId").val("${article.articleType.id }");
      });

      function sub() {
        //var id = $("#id").val();
        layer.confirm('您确定需要发布吗?', {
          title: '提示',
          offset: '222px',
          shade: 0.01
        }, function(index) {
          layer.close(index);
          //window.location.href = "${pageContext.request.contextPath }/article/audit.html?id=" + id + "&status=2";
        	$("#form").submit();
        });
      }

      function back() {
        var id = $("#id").val();
        var reason = $("#reason").val();
        if (reason == null || reason == '') {
			layer.msg("请填写退回理由", {offset: '222px',shade: 0.01});
		} else {
	        layer.confirm('您确定需要退回吗?', {
	          title: '提示',
	          offset: '222px',
	          shade: 0.01
	        }, function(index) {
	          layer.close(index);
	          $("#status").val(3);
	          $("#form").submit();
	          //window.location.href = "${pageContext.request.contextPath }/article/audit.html?id=" + id + "&reason=" + reason + "&status=3";
	        });
		}
      }
      
      function publish(){
      	$("#status").val(2);
      	$("#form").submit();
      }

      function goBack() {
        window.location.href = "${pageContext.request.contextPath }/article/auditlist.html?status=1";
      }

      $(function() {
        var typeId;
        $("#secondType").empty();
        $("#secondType").select2("val", "");
        $("#threeType").empty();
        $("#threeType").select2("val", "");
        $("#fourType").empty();
        $("#fourType").select2("val", "");
        
        $.ajax({
          contentType: "application/json;charset=UTF-8",
          url: "${pageContext.request.contextPath }/article/aritcleTypeParentId.do?parentId=0",
          type: "POST",
          dataType: "json",
          success: function(articleTypes) {
            if(articleTypes) {
              $("#articleTypes").append("<option></option>");
              $.each(articleTypes, function(i, articleType) {
                if(articleType.name != null && articleType.name != '') {
                  $("#articleTypes").append("<option value=" + articleType.id + ">" + articleType.name + "</option>");
                }
              });
            }
            $("#articleTypes").select2();
            $("#articleTypes").select2("val", "${article.articleType.id }");
            var typeId = $("#articleTypes").select2("data").text;
            if(typeId == "工作动态") {
            	$("#second").show();
            	$("#lmsx").addClass("tphide");
            	$("#choseCategory").hide();
				hideCategory();
				$("#cId").val("");
	        	$("#categorySel").val("");
            } else if(typeId == "采购公告") {
              $("#second").show();
              $("#three").show();
              $("#four").show();
            } else if(typeId == "中标公示") {
              $("#second").show();
              $("#three").show();
              $("#four").show();
            } else if(typeId == "单一来源公示") {
              $("#second").show();
              $("#three").show();
              $("#four").hide();
            } else if(typeId == "商城竞价公告") {
              $("#second").show();
              $("#three").hide();
              $("#four").hide();
              $("#choseCategory").hide();
			  hideCategory();
			  $("#cId").val("");
        	  $("#categorySel").val("");
            } else if(typeId == "网上竞价公告") {
              $("#second").show();
              $("#three").hide();
              $("#four").hide();
              $("#choseCategory").hide();
			  hideCategory();
			  $("#cId").val("");
        	  $("#categorySel").val("");
            } else if(typeId == "采购法规") {
              $("#second").show();
              $("#three").hide();
              $("#four").hide();
              $("#choseCategory").hide();
			  hideCategory();
			  $("#cId").val("");
        	  $("#categorySel").val("");
            } else if(typeId == "处罚公告") {
              $("#second").show();
              var secId = "${article.secondArticleTypeId}";
	           if (secId == '114') {
			     $("#three").show();
			   }
			   if (secId == '115') {
                 $("#three").hide();
			   }
              $("#four").hide();
              $("#choseCategory").hide();
			  hideCategory();
			  $("#cId").val("");
        	  $("#categorySel").val("");
            }
          }
        });

        var parentId = "${article.articleType.id }";
        $.ajax({
          contentType: "application/json;charset=UTF-8",
          url: "${pageContext.request.contextPath }/article/aritcleTypeParentId.do?parentId=" + parentId+ "&type=1",
          type: "POST",
          dataType: "json",
          success: function(articleTypes) {
            if(articleTypes) {
              $("#secondType").append("<option></option>");
              $.each(articleTypes, function(i, articleType) {
                if(articleType.name != null && articleType.name != '') {
                  $("#secondType").append("<option value=" + articleType.id + ">" + articleType.name + "</option>");
                }
              });
            }
            $("#secondType").select2();
            $("#secondType").select2("val", "${article.secondArticleTypeId }");
            var TtypeId = $("#secondType").select2("data").text;
            if(TtypeId == "图片新闻"){
                $("#picNone").removeClass().addClass("col-md-6 col-sm-6 col-xs-12 mt10");
            }
            /* if(TtypeId == "部队采购"){
              $("input[name='ranges']").each(function(){
			  	  if($(this).val()== 0){
	   			      $(this).attr('checked','true');
	   		  	  }
	   		  	  if($(this).val()==2){
		   		  	  $(this).attr('disabled', true);
	   		  	  }
	    	  });
           }else{
          	  $("input[name='ranges']").each(function(){
	   		  	 $(this).attr('disabled', false);
	    	  });
           } */
          }
        });

        var sparentId = "${article.secondArticleTypeId }";
        $.ajax({
          contentType: "application/json;charset=UTF-8",
          url: "${pageContext.request.contextPath }/article/aritcleTypeParentId.do?parentId=" + sparentId,
          type: "POST",
          dataType: "json",
          success: function(articleTypes) {
            if(articleTypes) {
              $("#threeType").append("<option></option>");
              $.each(articleTypes, function(i, articleType) {
                if(articleType.name != null && articleType.name != '') {
                  $("#threeType").append("<option value=" + articleType.id + ">" + articleType.name + "</option>");
                }
              });
            }
            $("#threeType").select2();
            $("#threeType").select2("val", "${article.threeArticleTypeId }");
            var threeTypeName = $("#threeType").select2("data").text;
			if (threeTypeName == '进口' || threeTypeName == '物资' || threeTypeName == '工程' || threeTypeName == '服务') {
				$("#choseCategory").show();
			}
          }
        });

        var fparentId = "${article.threeArticleTypeId }";
        $.ajax({
          contentType: "application/json;charset=UTF-8",
          url: "${pageContext.request.contextPath }/article/aritcleTypeParentId.do?parentId=" + fparentId,
          type: "POST",
          dataType: "json",
          success: function(articleTypes) {
            if(articleTypes) {
              $("#fourType").append("<option></option>");
              $.each(articleTypes, function(i, articleType) {
                if(articleType.name != null && articleType.name != '') {
                  $("#fourType").append("<option value=" + articleType.id + ">" + articleType.name + "</option>");
                }
              });
            }
            $("#fourType").select2();
            $("#fourType").select2("val", "${article.fourArticleTypeId }");
          }
        });

      })
      
      function typeInfo() {
        var typeId = $("#articleTypes").select2("data").text;
        var parentId = $("#articleTypes").select2("val");
        $("#secondType").empty();
        $("#threeType").empty();
        $("#threeType").select2("val", "");
        $("#fourType").empty();
        $("#fourType").select2("val", "");
        if(typeId == "工作动态") {
          $("#picNone").removeClass().addClass("col-md-6 col-sm-6 col-xs-12 mt10 dis_hide");
          $("#second").show();
          $("#three").hide();
          $("#four").hide();
          $("#lmsx").addClass("tphide");
          getSencond(parentId);
          $("#choseCategory").hide();
		  hideCategory();
		  $("#cId").val("");
          $("#categorySel").val("");
        }else if(typeId == "采购公告"){
            $("#second").show();
            $("#three").show();
            $("#four").show();
            $("#lmsx").removeClass("tphide");
            $("#picNone").removeClass().addClass("col-md-6 col-sm-6 col-xs-12 mt10 dis_hide");
            getSencond(parentId);
         }else if(typeId == "中标公示"){
             $("#second").show();
             $("#three").show();
             $("#four").show();
             $("#lmsx").removeClass("tphide");
             $("#picNone").removeClass().addClass("col-md-6 col-sm-6 col-xs-12 mt10 dis_hide");
             getSencond(parentId);
         }else if(typeId == "单一来源公示"){
             $("#second").show();
             $("#three").show();
             $("#four").hide();
             $("#lmsx").removeClass("tphide");
             $("#picNone").removeClass().addClass("col-md-6 col-sm-6 col-xs-12 mt10 dis_hide");
             getSencond(parentId);
         }else if(typeId == "商城竞价公告"){
        	  $("#second").show();
        	  $("#three").hide();
            $("#four").hide();
            $("#lmsx").removeClass("tphide");
            $("#picNone").removeClass().addClass("col-md-6 col-sm-6 col-xs-12 mt10 dis_hide");
        	getSencond(parentId);
        	$("#choseCategory").hide();
			hideCategory();
		    $("#cId").val("");
            $("#categorySel").val("");
         }else if(typeId == "网上竞价公告"){
            $("#second").show();
            $("#three").hide();
            $("#four").hide();
            $("#lmsx").removeClass("tphide");
            $("#picNone").removeClass().addClass("col-md-6 col-sm-6 col-xs-12 mt10 dis_hide");
            getSencond(parentId);
            $("#choseCategory").hide();
			hideCategory();
		    $("#cId").val("");
            $("#categorySel").val("");
         }else if(typeId == "采购法规"){
            $("#second").show();
            $("#three").hide();
            $("#four").hide();
            $("#lmsx").removeClass("tphide");
            $("#picNone").removeClass().addClass("col-md-6 col-sm-6 col-xs-12 mt10 dis_hide");
            getSencond(parentId);
            $("#choseCategory").hide();
			hideCategory();
		    $("#cId").val("");
            $("#categorySel").val("");
         }else if(typeId == "处罚公告"){
            $("#second").show();
            $("#three").hide();
            $("#four").hide();
            $("#lmsx").removeClass("tphide");
            $("#picNone").removeClass().addClass("col-md-6 col-sm-6 col-xs-12 mt10 dis_hide");
            getSencond(parentId);
            $("#choseCategory").hide();
			hideCategory();
		    $("#cId").val("");
            $("#categorySel").val("");
         }else {
          $("#picNone").removeClass().addClass("col-md-6 col-sm-6 col-xs-12 mt10 dis_hide");
          $("#second").hide();
          $("#three").hide();
          $("#four").hide();
          $("#lmsx").removeClass("tphide");
          $("#secondType").empty();
          $("#threeType").empty();
          $("#fourType").empty();
          $("#choseCategory").hide();
		  hideCategory();
		  $("#cId").val("");
          $("#categorySel").val("");
        }
      }
      
      function secondTypeInfo(){
    	  $("#threeType").empty();
    	  $("#fourType").empty();
    	  $("#fourType").select2("val", "");
    	  var parentId = $("#secondType").select2("val");
    	  var TtypeId = $("#secondType").select2("data").text;
        if(TtypeId == "图片新闻"){
            $("#picNone").removeClass().addClass("col-md-6 col-sm-6 col-xs-12 mt10");
        }
        if(TtypeId == "供应商处罚公告"){
              $("#three").show();
          }
          if(TtypeId == "专家处罚公告"){
              $("#three").hide();
          }
          /* if(TtypeId == "部队采购"){
              $("input[name='ranges']").each(function(){
			  	  if($(this).val()== 0){
	   			      $(this).attr('checked','true');
	   		  	  }
	   		  	  if($(this).val()==2){
		   		  	  $(this).attr('disabled', true);
	   		  	  }
	    	  });
          }else{
          	  $("input[name='ranges']").each(function(){
	   		  	 $(this).attr('disabled', false);
	    	  });
          } */
    	  $.ajax({
              contentType: "application/json;charset=UTF-8",
              url: "${pageContext.request.contextPath }/article/aritcleTypeParentId.do?parentId="+parentId,
              type: "POST",
              dataType: "json",
              success: function(articleTypes) {
                if(articleTypes) {
                  $("#threeType").append("<option></option>");
                  $.each(articleTypes, function(i, articleType) {
                    if(articleType.name != null && articleType.name != '') {
                      $("#threeType").append("<option value=" + articleType.id + ">" + articleType.name + "</option>");
                    }
                  });
                }
                $("#threeType").select2();
              }
            });
      }
      
      function threeTypeInfo(){
      	  hideCategory();
      	  $("#cId").val("");
          $("#categorySel").val("");
          $("#fourType").empty();
          var parentId = $("#threeType").select2("val");
          var threeTypeName = $("#threeType").select2("data").text;
		  if (threeTypeName == '进口' || threeTypeName == '物资' || threeTypeName == '工程' || threeTypeName == '服务') {
				$("#choseCategory").show();
		  }
          $.ajax({
                contentType: "application/json;charset=UTF-8",
                url: "${pageContext.request.contextPath }/article/aritcleTypeParentId.do?parentId="+parentId,
                type: "POST",
                dataType: "json",
                success: function(articleTypes) {
                  if(articleTypes) {
                    $("#fourType").append("<option></option>");
                    $.each(articleTypes, function(i, articleType) {
                      if(articleType.name != null && articleType.name != '') {
                        $("#fourType").append("<option value=" + articleType.id + ">" + articleType.name + "</option>");
                      }
                    });
                  }
                  $("#fourType").select2();
                }
              });
        }
      
      
      function getSencond(parentId){
    	  $("#secondType").empty();
    	  $("#threeType").empty();
    	  $("#fourType").empty();
    	  $.ajax({
              contentType: "application/json;charset=UTF-8",
              url: "${pageContext.request.contextPath }/article/aritcleTypeParentId.do?parentId="+parentId+"&type=1",
              type: "POST",
              dataType: "json",
              success: function(articleTypes) {
                if(articleTypes) {
                  $("#secondType").append("<option></option>");
                  $.each(articleTypes, function(i, articleType) {
                    if(articleType.name != null && articleType.name != '') {
                      $("#secondType").append("<option value=" + articleType.id + ">" + articleType.name + "</option>");
                    }
                  });
                }
                $("#secondType").select2();
              }
            });
      }
      
      function clk(){
    	 var second = $("#secondType").select2("val");
    	 var three = $("#threeType").select2("val");
       var four =  $("#fourType").select2("val");
       var articleTypes = $("#articleTypes").select2("data").text;
        if(articleTypes == "工作动态"){
        	return true;
        }else if($("#second").is(":visible")){
    		  if(second==null||second==""){
    			  $("#ERR_secondType").html("栏目属性不能为空");
    			  return false;
    		  }else if($("#three").is(":visible")){
    	             if(three==null||three==""){
    	               $("#ERR_threeType").html("采购类型不能为空");
    	                 return false;
    	               }else if($("#four").is(":visible")){
    	                   if(four==null||four==""){
    	                       $("#ERR_fourType").html("采购方式不能为空");
    	                        return false;
    	                      }else if (articleTypes == "单一来源公示" || articleTypes == "采购公告" || articleTypes == "中标公示"){
		          		   var categoryIds = $("#cId").val();
				           if (categoryIds == "" || categoryIds == null || categoryIds== "undefined") {
								$("#ERR_category").html("产品类别不能为空");
								return false;
						   }
		              }
                  }else if (articleTypes == "单一来源公示" || articleTypes == "采购公告" || articleTypes == "中标公示"){
	          		   var categoryIds = $("#cId").val();
			           if (categoryIds == "" || categoryIds == null || categoryIds== "undefined") {
							$("#ERR_category").html("产品类别不能为空");
							return false;
					   }
	              }
    	      }
         }else{
           return true;
       }
      }
      
      function searchs(articleId){
		var threeType = $("#threeType").select2("data").text;
		var rootCode = null;
		if (threeType == "进口" || threeType == "物资") {
			rootCode = "GOODS";
		}
		if (threeType == "工程") {
			rootCode = "PROJECT";
		}
		if (threeType == "服务") {
			rootCode = "SERVICE";
		}
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
						type: "get"
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

    <!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
          <li>
            <a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a>
          </li>
          <li>
            <a href="javascript:void(0)">信息服务</a>
          </li>
          <li><a  href="javascript:void(0)">门户管理</a></li>
          <li>
            <a href="javascript:jumppage('${pageContext.request.contextPath}/article/getAll.html')">信息管理</a>
          </li>
          <li class="active">
            <a href="javascript:void(0)">信息审核</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>

    <div class="container container_box">
    	<div id="categoryContent" class="categoryContent" style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
			<div class=" input_group col-md-3 col-sm-6 col-xs-12 col-lg-12 p0">
			    <div class="w100p">
			    	<input type="text" id="search" class="fl m0">
				      <img alt="" style="position:absolute; top:8px;right:10px;" src="${pageContext.request.contextPath }/public/backend/images/view.png"  onclick="searchs('${articleId}')">
			    </div>
			    <ul id="treeCategory" class="ztree" style="margin-top:0;"></ul>
			</div>
	   	</div>
      <form action="${pageContext.request.contextPath }/article/audit.html" onsubmit="return clk()" method="post" id="form">
        <div>
          <input type="hidden" id="status" name="status">
          <h2 class="count_flow"><i>1</i>审核信息</h2>
          <input type="hidden" name="id" id="id" value="${article.id }">
          <input type="hidden" name="user.id" id="user.id" value="${article.user.id }">

          <ul class="ul_list mb20">
            <li class="col-md-12 col-sm-6 col-xs-12 pl15">
              <span class="ol-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>信息标题：</span>
              <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                <input type="text" name="name" maxlength="200" value="${article.name }">
                <div class="cue">${ERR_name}</div>
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><div class="star_red">*</div>信息栏目：</span>
              <div class="select_common col-md-12 col-xs-12 col-sm-12 input_group p0">
                <select id="articleTypes" name="articleType.id" class="p0 select col-md-12 col-xs-12 col-sm-12 " onchange="typeInfo()">
                </select>
                <div class="cue">${ERR_typeId}</div>
              </div>
            </li>

            <li class="col-md-3 col-sm-6 col-xs-12 hide" id="second">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div id="lmsx" class="star_red">*</div>栏目属性：</span>
              <div class=" select_common col-md-12 col-sm-12 col-xs-12 p0">
                <select id="secondType" name="secondArticleTypeId" class="select col-md-12 col-sm-12 col-xs-12 p0" onchange="secondTypeInfo()">
                </select>
                <div class="cue" id="ERR_secondType"></div>
              </div>
            </li>

            <li class="col-md-3 col-sm-6 col-xs-12 hide" id="three">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>栏目类型：</span>
              <div class=" select_common col-md-12 col-sm-12 col-xs-12 p0">
                <select id="threeType" name="threeArticleTypeId" class="select col-md-12 col-sm-12 col-xs-12 p0" onchange="threeTypeInfo()">
                </select>
                <div class="cue" id="ERR_threeType"></div>
              </div>
            </li>

            <li class="col-md-3 col-sm-6 col-xs-12 hide" id="four">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>采购方式：</span>
              <div class=" select_common col-md-12 col-sm-12 col-xs-12 p0">
                <select id="fourType" name="fourArticleTypeId" class="select col-md-12 col-sm-12 col-xs-12 p0" onchange="fourTypeInfo()">
                </select>
                <div class="cue" id="ERR_fourType"></div>
              </div>
            </li>

            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>发布范围：</span>
              <div class="input-append col-md-12 col-sm-12 col-xs-12 p0">
                <label class="fl margin-bottom-0"><input type="radio" name="ranges" value="0">内网</label>
                <label class="ml10 fl"><input type="radio" name="ranges" value="2">内外网</label>
                <div class="cue">${ERR_range}</div>
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12 dnone" id="choseCategory">
				<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">
				<div class="star_red">*</div>选择产品类别：</span>
				<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 col-lg-12 p0">
					<input id="cId" name="categoryId"  type="hidden" value="${categoryIds}">
			        <input id="categorySel"  type="text" name="categoryName" readonly value="${categoryNames}"  onclick="showCategory('${article.id}');" />
					<div class="drop_up" onclick="showCategory('${article.id}');">
					    <img src="${pageContext.request.contextPath}/public/backend/images/down.png" />
			        </div>
					<div class="cue" id="ERR_category">${ERR_category}</div>
				</div>
			</li>
            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">提交时间：</span>
              <div class="input-append col-md-12 col-sm-12 col-xs-12 p0">
                <label class="fl margin-bottom-0"><fmt:formatDate value='${article.submitAt }' pattern="yyyy-MM-dd   HH:mm:ss" /></label>
              	<input  name=submitAt value='<fmt:formatDate value="${article.submitAt }" pattern="YYYY-MM-dd"/>' class="Wdate w100" type="hidden" />
              </div>
            </li>
            <li class="col-md-12 col-sm-12 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>信息正文：</span>
              <div class="col-md-12 col-sm-12 col-xs-12 p0">
              	<input type="hidden" id="articleContent" value='${article.content}'>
                <script id="editor" name="content" type="text/plain" class="col-md-12 edit-posit p0"></script>
              </div>
              <div class="red f14 clear col-ms-12 col-xs-12 col-sm-12 p0">${ERR_content}</div>
            </li>
            <li class="col-md-6 col-sm-6 col-xs-12 mt10">
              <span class="fl">附件上传：</span>
              <div>
                <u:upload id="artice_file_up" buttonName="上传文档" groups="artice_up,artice_file_up,artice_secret_up" businessId="${articleId }" sysKey="${sysKey}" typeId="${artiAttachTypeId }" multiple="true" auto="true" />
                <u:show showId="artice_file_show" groups="artice_show,artice_file_show,artice_secret_show" businessId="${articleId }" sysKey="${sysKey}" typeId="${artiAttachTypeId }" />
              </div>
            </li>
            <li class="col-md-6 col-sm-6 col-xs-12 mt10">
              <span class="fl">单位及保密委员会审核表：</span>
              <div>
                <u:upload id="artice_secret_up" multiple="true" groups="artice_up,artice_file_up,artice_secret_up" businessId="${articleId }" sysKey="${sysKey}" typeId="${secretTypeId }" auto="true" />
                <u:show showId="artice_secret_show" groups="artice_show,artice_file_show,artice_secret_show" businessId="${articleId }" sysKey="${sysKey}" typeId="${secretTypeId }" />
              </div>
              <div class="cue">${ERR_auditDoc}</div>
            </li>
            <li class="col-md-6 col-sm-6 col-xs-12 mt10 dis_hide" id="picNone">
              <span class="fl"><div class="star_red">*</div>图片上传：</span>
              <div class="mb20 h30">
                <u:upload id="artice_up" groups="artice_up,artice_file_up,artice_secret_up" businessId="${articleId }" sysKey="${sysKey}" typeId="${attachTypeId }" auto="true" />
                <u:show showId="artice_show" groups="artice_show,artice_file_show,artice_secret_show" businessId="${articleId }" sysKey="${sysKey}" typeId="${attachTypeId }" />
                <div class="cue">${ERR_auditPic}</div>
              </div>
            </li>
          </ul>
        </div>
        <div class="padding-top-10 clear">
          <h2 class="count_flow"><i>2</i>审核</h2>
          <ul class="ul_list mb20">
            <li class=" col-md-12 col-sm-12 col-xs-12">
              <span class=" col-md-12 col-sm-12 col-xs-12 padding-left-5">退回理由：</span>
              <div class=" col-md-12 col-sm-12 col-xs-12 p0">
                <textarea class="h130 col-md-12 col-sm-12 col-xs-12" id="reason" name="reason" title="不超过300个字" placeholder="不超过300个字">${article.reason}</textarea>
              </div>
              <div class="red f14 clear col-ms-12 col-xs-12 col-sm-12 p0">${ERR_reason}</div>
            </li>
          </ul>
          <div class="col-md-12 tc">
            <button class="btn btn-windows check" type="button" onclick="publish()">发布</button>
            <button class="btn btn-windows withdraw" type="button" onclick="back()">退回</button>
            <input class="btn btn-windows back" value="返回" type="button" onclick="goBack()">
          </div>
        </div>

      </form>

    </div>

    <script type="text/javascript">
      //实例化编辑器
      //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
      var option = {
      	initialFrameHeight:550,
        toolbars: [
          [
            'fullscreen', 'source', '|','undo', 'redo', '|',
            'bold', 'italic', 'underline', 'formatmatch', 'autotypeset', '|', 'forecolor', 'backcolor',
            'fontfamily', 'fontsize', '|',
            'indent', '|',
            'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify',
          ]
        ]
      }
      
      
      
	  var ue = UE.getEditor('editor', option);
      var content = $("#articleContent").val();
      var messageTip = "${properties['messageTip']}";
      ue.ready(function() {
    	  
    	  UE.dom.domUtils.setStyles(self.ue.body, {
				'color' : 'black'
			});
          ue.setContent(content);
      });
    </script>

  </body>

</html>