<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

  <head>
    <link href="${pageContext.request.contextPath }/public/select2/css/select2.css" rel="stylesheet" />
    <%@ include file="/WEB-INF/view/common.jsp"%>
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
        $.ajax({
          contentType: "application/json;charset=UTF-8",
          url: "${pageContext.request.contextPath }/article/selectAritcleType.do",
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
            typeId = "${article.articleType.name }";
            if(typeId == "工作动态") {
              $("#picNone").removeClass().addClass("col-md-6 col-sm-6 col-xs-12 mt10");
              $("#picshow").show();
            } else {
              $("#picNone").removeClass().addClass("col-md-6 col-sm-6 col-xs-12 mt10 dis_hide");
              $("#picshow").hide();
            }
          }
        });

      })
      
      function typeInfo() {
        $("#picshow").hide();
        var typeId = $("#articleTypes").select2("data").text;
        if(typeId == "工作动态") {
          $("#picNone").removeClass().addClass("col-md-6 col-sm-6 col-xs-12 mt10");
          $("#picshow").show();
        } else {
          $("#picNone").removeClass().addClass("col-md-6 col-sm-6 col-xs-12 mt10 dis_hide");
          $("#picshow").hide();
        }
      }

      function goBack() {
        window.location.href = "${pageContext.request.contextPath }/article/getAll.html";
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
            <a href="javascript:void(0)">修改</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>

    <div class="container container_box">
      <form id="newsForm" action="${pageContext.request.contextPath }/article/updateEditor.html" enctype="multipart/form-data" method="post">
        <input type="hidden" id="ids" name="ids" />
        <h2 class="list_title">修改信息</h2>

        <ul class="ul_list mb20">
          <li class="col-md-3 col-sm-6 col-xs-12 pl15">
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

          <li class="col-md-3 col-sm-6 col-xs-12 ">
            <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><div class="star_red">*</div>发布范围：</span>
            <div class="input-append col-md-12 col-xs-12 col-sm-12 p0">
              <label class="fl margin-bottom-0"><input type="checkbox" name="ranges" value="0" class="mt0">内网</label>
              <label class="ml10 fl"><input type="checkbox" name="ranges" value="1" class="mt0">外网</label>
            </div>
            <div class="cue">${ERR_range}</div>
          </li>
          <li class="col-md-3 col-sm-6 col-xs-12" id="picshow">
            <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">图片展示：</span>
            <div class="input-append col-md-12 col-xs-12 col-sm-12 input_group p0">
              <input id="isPicShow" name="isPicShow" type="text" value="${article.isPicShow }">
              <span class="add-on">i</span>
              <div class="cue">${ERR_isPicShow}</div>
            </div>
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
                <u:upload id="artice_file_up" buttonName="上传文档" groups="artice_up,artice_file_up,artice_secret_up" businessId="${articleId }" sysKey="${articleSysKey}" typeId="${artiAttachTypeId }" multiple="true" auto="true" />
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
              <span class="fl">图片上传：</span>
              <div>
                <u:upload id="artice_up" groups="artice_up,artice_file_up,artice_secret_up" businessId="${articleId }" sysKey="${sysKey}" typeId="${attachTypeId }" auto="true" />
                <u:show showId="artice_show" groups="artice_show,artice_file_show,artice_secret_show" businessId="${articleId }" sysKey="${sysKey}" typeId="${attachTypeId }" />
              </div>
            </li>

        </ul>

        <div class="col-md-12 col-xs-12 col-sm-12 tc">
          <button class="btn btn-windows edit" type="submit">编辑</button>
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