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
      function cheClick(id, name) {
        $("#articleTypeId").val(id);
        $("#articleTypeName").val(name);
      }

      $(function() {
        var range = "${article.range}";
        if(range == 2) {
          $("input[name='ranges']").attr("checked", true);
        } else {
          $("input[name='ranges'][value=" + range + "]").attr("checked", true);
        }
      });

      function addAttach() {
        html = "<input id='pic' type='file' class='toinline' name='attaattach'/><a href='#' onclick='deleteattach(this)' class='toinline red redhover'>x</a><br/>";
        $("#uploadAttach").append(html);
      }

      function deleteattach(obj) {
        $(obj).prev().remove();
        $(obj).next().remove();
        $(obj).remove();
      }

      var ids = "";

      function deleteAtta(id, obj) {
        ids += id + ",";
        $("#ids").val(ids);
        alert(ids);
        $(obj).prev().remove();
        $(obj).remove();
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
              }else if(typeId == "采购公告"){
                  $("#second").show();
                  $("#three").show();
                  $("#four").show();
               }else if(typeId == "中标公示"){
                   $("#second").show();
                   $("#three").show();
                   $("#four").show();
               }else if(typeId == "单一来源公示"){
                   $("#second").show();
                   $("#three").show();
                   $("#four").hide();
               }else if(typeId == "商城竞价公告"){
                  $("#second").show();
                  $("#three").hide();
                  $("#four").hide();
               }else if(typeId == "网上竞价公告"){
                  $("#second").show();
                  $("#three").hide();
                  $("#four").hide();
               }else if(typeId == "采购法规"){
                  $("#second").show();
                  $("#three").hide();
                  $("#four").hide();
               }
            }
          });
          
          var parentId = "${article.articleType.id }";
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
              url: "${pageContext.request.contextPath }/article/aritcleTypeParentId.do?parentId="+sparentId,
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
              }
            });
          
          var fparentId = "${article.threeArticleTypeId }";
          $.ajax({
              contentType: "application/json;charset=UTF-8",
              url: "${pageContext.request.contextPath }/article/aritcleTypeParentId.do?parentId="+fparentId,
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

      function goBack() {
          window.location.href = "${pageContext.request.contextPath }/article/auditlist.html";
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
                               }
                        }
                 }
             
            }else{
              return true;
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
            <a href="javascript:void(0)">编辑</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>

    <div class="container container_box">
     <form id="newsForm" action="${pageContext.request.contextPath }/article/updateApply.html" onsubmit="return clk()" enctype="multipart/form-data" method="post">
        <input type="hidden" id="ids" name="ids" />
        <h2 class="list_title">编辑信息</h2>

        <ul class="ul_list mb20">
          <li class="col-md-12 col-sm-6 col-xs-12 pl15">
            <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><div class="star_red">*</div>信息标题：</span>
            <div class="input-append col-md-12 col-xs-12 col-sm-12 input_group p0">
              <input type="hidden" name="id" value="${article.id }">
              <input type="hidden" name="status" id="status" value="${article.status }">
              <input type="hidden" name="user.id" id="user.id" value="${article.user.id }">
              <input id="name" name="name" type="text" value="${article.name }">
              <span class="add-on">i</span>
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

          <li class="col-md-3 col-sm-6 col-xs-12 ">
            <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><div class="star_red">*</div>发布范围：</span>
            <div class="input-append col-md-12 col-xs-12 col-sm-12 p0">
              <label class="fl margin-bottom-0"><input type="checkbox" name="ranges" value="0" class="mt0">内网</label>
              <label class="ml10 fl"><input type="checkbox" name="ranges" value="1" class="mt0">外网</label>
            </div>
            <div class="cue">${ERR_range}</div>
          </li>

          <li class="col-md-12 col-xs-12 col-s  m-12">
            <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><div class="star_red">*</div>信息正文：</span>
            <div class="col-md-12 col-xs-12 col-sm-12 p0">
              <script id="editor" name="content" type="text/plain" class="col-md-12 col-xs-12 col-sm-12 p0"></script>
            </div>
            <div class="red f14 clear col-ms-12 col-xs-12 col-sm-12 p0">${ERR_content}</div>
          </li>

          <li class="col-md-6 col-sm-6 col-xs-12 mt10">
              <span class="fl">附件上传：</span>
              <div>
                <u:upload id="artice_file_up" buttonName="上传文档" groups="artice_up,artice_file_up,artice_secret_up" businessId="${articleId }" sysKey="${articleSysKey}" typeId="${artiAttachTypeId}" multiple="true" auto="true" />
                <u:show showId="artice_file_show" groups="artice_show,artice_file_show,artice_secret_show" businessId="${articleId }" sysKey="${articleSysKey}" typeId="${artiAttachTypeId }" />
              </div>
            </li>
            
            <li class="col-md-6 col-sm-6 col-xs-12 mt10">
              <span class="fl"><div class="star_red">*</div>单位及保密委员会审核表：</span>
              <div>
                <u:upload id="artice_secret_up" groups="artice_up,artice_file_up,artice_secret_up" businessId="${articleId }" sysKey="${secretSysKey}" typeId="${secretTypeId }" auto="true" />
                <u:show showId="artice_secret_show" groups="artice_show,artice_file_show,artice_secret_show" businessId="${articleId }" sysKey="${secretSysKey}" typeId="${secretTypeId }" />
              </div>
              <div class="cue">${ERR_auditDoc}</div>
            </li>
            
            <li class="col-md-6 col-sm-6 col-xs-12 mt10 dis_hide" id="picNone">
              <span class="fl"><div class="star_red">*</div>图片上传：</span>
              <div>
                <u:upload id="artice_up" groups="artice_up,artice_file_up,artice_secret_up" businessId="${articleId }" sysKey="${sysKey}" typeId="${attachTypeId }" auto="true" />
                <u:show showId="artice_show" groups="artice_show,artice_file_show,artice_secret_show" businessId="${articleId }" sysKey="${sysKey}" typeId="${attachTypeId }" />
              </div>
              <div class="cue">${ERR_auditPic}</div>
            </li>
        </ul>

        <div class="col-md-12 col-xs-12 col-sm-12 tc">
          <button class="btn btn-windows apply" type="submit">发布</button>
          <input class="btn btn-windows back" value="返回" type="button" onclick="goBack()">
        </div>
    </div>

    </form>

    </div>
    <script type="text/javascript">
      //实例化编辑器
      //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
      var option = {
        toolbars: [
          [
            'undo', 'redo', '|',
            'bold', 'italic', 'underline', 'formatmatch', 'autotypeset', '|', 'forecolor', 'backcolor',
            'fontfamily', 'fontsize', '|',
            'indent', '|',
            'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify',
          ]
        ]

      }
      var ue = UE.getEditor('editor', option);
      var content = "${article.content}";
     
      ue.ready(function() {
        ue.setContent(content);
      });
    </script>
  </body>

</html>