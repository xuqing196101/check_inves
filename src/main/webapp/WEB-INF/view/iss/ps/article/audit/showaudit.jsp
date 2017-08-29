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
        layer.confirm('您确定需要退回吗?', {
          title: '提示',
          offset: ['222px', '360px'],
          shade: 0.01
        }, function(index) {
          layer.close(index);
          window.location.href = "${pageContext.request.contextPath }/article/audit.html?id=" + id + "&reason=" + reason + "&status=3";
        });
      }

      function goBack1() {
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
            	$("#artCategory").hide();
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
              $("#artCategory").hide();
            } else if(typeId == "网上竞价公告") {
              $("#second").show();
              $("#three").hide();
              $("#four").hide();
              $("#artCategory").hide();
            } else if(typeId == "采购法规") {
              $("#second").show();
              $("#three").hide();
              $("#four").hide();
              $("#artCategory").hide();
            } else if(typeId == "处罚公告") {
              $("#second").show();
              $("#artCategory").hide();
              var secId = "${article.secondArticleTypeId}";
	           if (secId == '114') {
			     $("#three").show();
			   }
			   if (secId == '115') {
                 $("#three").hide();
			   }
              $("#four").hide();
            }
          }
        });

        var parentId = "${article.articleType.id }";
        $.ajax({
          contentType: "application/json;charset=UTF-8",
          url: "${pageContext.request.contextPath }/article/aritcleTypeParentId.do?parentId=" + parentId,
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
				$("#artCategory").show();
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
         }else if(typeId == "网上竞价公告"){
            $("#second").show();
            $("#three").hide();
            $("#four").hide();
            $("#lmsx").removeClass("tphide");
            $("#picNone").removeClass().addClass("col-md-6 col-sm-6 col-xs-12 mt10 dis_hide");
            getSencond(parentId);
         }else if(typeId == "采购法规"){
            $("#second").show();
            $("#three").hide();
            $("#four").hide();
            $("#lmsx").removeClass("tphide");
            $("#picNone").removeClass().addClass("col-md-6 col-sm-6 col-xs-12 mt10 dis_hide");
            getSencond(parentId);
         }else if(typeId == "处罚公告"){
            $("#second").show();
            $("#three").hide();
            $("#four").hide();
            $("#lmsx").removeClass("tphide");
            $("#picNone").removeClass().addClass("col-md-6 col-sm-6 col-xs-12 mt10 dis_hide");
            getSencond(parentId);
         }else {
          $("#picNone").removeClass().addClass("col-md-6 col-sm-6 col-xs-12 mt10 dis_hide");
          $("#second").hide();
          $("#three").hide();
          $("#four").hide();
          $("#lmsx").removeClass("tphide");
          $("#secondType").empty();
          $("#threeType").empty();
          $("#fourType").empty();
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
          $("#fourType").empty();
          var parentId = $("#threeType").select2("val");
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
              url: "${pageContext.request.contextPath }/article/aritcleTypeParentId.do?parentId="+parentId,
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
      
      function goBack(){
	  	var curpage = $("#curpage").val();
	  	var status = $("#status").val();
	  	var range = $("#range").val();
	  	var articleTypeId = "${articlesArticleTypeId}";
	  	var name = "${articleName}";
	    var startDate = null;
	    var endDate = null;
	  	var secondArticleTypeId = "${secondArticleTypeId}";
	  	if('${reqType}' != ''){
		  	window.location.href = "${ pageContext.request.contextPath }/article/readOnlyList.html?publishYear=${ articleAnalyzeVo.publishYear }&threeArticleTypeId=${articleAnalyzeVo.threeArticleTypeId}&fourArticleTypeId=${articleAnalyzeVo.fourArticleTypeId}&categoryId=${articleAnalyzeVo.categoryId}&page="+curpage+"&status="+status+"&range="+range+"&articleTypeId="+articleTypeId+"&name="+name+"&secondArticleTypeId="+secondArticleTypeId;
  	  }else{
		  	window.location.href = "${ pageContext.request.contextPath }/article/auditlist.html?page="+curpage+"&status="+status+"&range="+range+"&articleTypeId="+articleTypeId+"&name="+name+"&secondArticleTypeId="+secondArticleTypeId;
  	  }
	  }
    </script>
  </head>

  <body>

    <!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
          <li><a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a></li>
          <li><a>信息服务</a></li>
          <li><a>门户管理</a></li>
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
	<input type="hidden" id="curpage" value="${curpage}">
	<input type="hidden" id="status" value="${articlesStatus}">
    <input type="hidden" id="range" value="${articlesRange}">
    <div class="container container_box">
      <form action="${pageContext.request.contextPath }/article/audit.html?status=2" method="post" id="form">
        <div>
          <h2 class="count_flow"><i>1</i>审核信息</h2>
          <input type="hidden" name="id" id="id" value="${article.id }">
          <input type="hidden" name="user.id" id="user.id" value="${article.user.id }">

          <ul class="ul_list mb20">
            <li class="col-md-12 col-sm-6 col-xs-12 pl15">
              <span class="ol-md-12 col-sm-12 col-xs-12 padding-left-5">信息标题：</span>
              <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                <input type="text" name="name" disabled="disabled" value="${article.name }">
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><div class="star_red">*</div>信息栏目：</span>
              <div class="select_common col-md-12 col-xs-12 col-sm-12 input_group p0">
                <select id="articleTypes" name="articleType.id" disabled="disabled" class="p0 select col-md-12 col-xs-12 col-sm-12 " onchange="typeInfo()">
                </select>
                <div class="cue">${ERR_typeId}</div>
              </div>
            </li>

            <li class="col-md-3 col-sm-6 col-xs-12 hide" id="second">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div id="lmsx" class="star_red">*</div>栏目属性：</span>
              <div class=" select_common col-md-12 col-sm-12 col-xs-12 p0">
                <select id="secondType" name="secondArticleTypeId" disabled="disabled" class="select col-md-12 col-sm-12 col-xs-12 p0" onchange="secondTypeInfo()">
                </select>
                <div class="cue" id="ERR_secondType"></div>
              </div>
            </li>

            <li class="col-md-3 col-sm-6 col-xs-12 hide" id="three">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>栏目类型：</span>
              <div class=" select_common col-md-12 col-sm-12 col-xs-12 p0">
                <select id="threeType" name="threeArticleTypeId" disabled="disabled" class="select col-md-12 col-sm-12 col-xs-12 p0" onchange="threeTypeInfo()">
                </select>
                <div class="cue" id="ERR_threeType"></div>
              </div>
            </li>

            <li class="col-md-3 col-sm-6 col-xs-12 hide" id="four">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>采购方式：</span>
              <div class=" select_common col-md-12 col-sm-12 col-xs-12 p0">
                <select id="fourType" name="fourArticleTypeId" disabled="disabled" class="select col-md-12 col-sm-12 col-xs-12 p0" onchange="fourTypeInfo()">
                </select>
                <div class="cue" id="ERR_fourType"></div>
              </div>
            </li>

            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">发布范围：</span>
              <div class="input-append col-md-12 col-sm-12 col-xs-12 p0">
                <label class="fl margin-bottom-0"><input type="radio" disabled="disabled" name="ranges" value="0">内网</label>
                <label class="ml10 fl"><input type="radio" disabled="disabled" name="ranges" value="2">内外网</label>
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12 pl15 dnone" id="artCategory">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>产品类别：</span>
              <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                <input type="text"  disabled="disabled"  value="${categoryNames}">
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">提交时间：</span>
              <div class="input-append col-md-12 col-sm-12 col-xs-12 p0">
                <label class="fl margin-bottom-0"><fmt:formatDate value='${article.submitAt }' pattern="yyyy-MM-dd   HH:mm:ss" /></label>
              </div>
            </li>
            <li class="col-md-12 col-sm-12 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">信息正文：</span>
              <div class="col-md-12 col-sm-12 col-xs-12 p0 ">
              	<input type="hidden" id="articleContent" value='${article.content}'>
                <script id="editor" type="text/plain" class="col-md-12 p0"></script>
              </div>
            </li>
            <li class="col-md-6 col-xs-6 col-sm-12 mt10">
              <span class="fl">已上传的附件：</span>
              <div class="fl">
                <u:show showId="artice_file_show" delete="false" groups="artice_show,artice_file_show,artice_secret_show" businessId="${articleId }" sysKey="${sysKey}" typeId="${artiAttachTypeId }" />
              </div>
            </li>

            <li class="col-md-6 col-sm-6 col-xs-12 mt10">
              <span class="fl">单位及保密委员会审核表：</span>
              <div>
                <u:show showId="artice_secret_show" delete="false" groups="artice_show,artice_file_show,artice_secret_show" businessId="${articleId }" sysKey="${sysKey}" typeId="${secretTypeId }" />
              </div>
            </li>

            <li class="col-md-6 col-sm-6 col-xs-12 mt10 dis_hide" id="picNone">
              <span class="fl">图片上传：</span>
              <div>
                <u:show showId="artice_show" delete="false" groups="artice_show,artice_file_show,artice_secret_show" businessId="${articleId }" sysKey="${sysKey}" typeId="${attachTypeId }" />
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
                <textarea class="h130 col-md-12 col-sm-12 col-xs-12" disabled="disabled" id="reason" name="reason" >${article.reason }</textarea>
              </div>
            </li>
          </ul>
          <div class="col-md-12 tc">
            <input class="btn btn-windows back" value="返回" type="button" onclick="goBack();">
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
      ue.ready(function() {
        ue.setContent(content);
        ue.setDisabled([]);
      });
    </script>

  </body>

</html>