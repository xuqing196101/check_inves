<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

  <head>
   	<%@ include file="/WEB-INF/view/common.jsp"%>
   	<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
    <script type="text/javascript">
      function cheClick(id, name) {
        $("#articleTypeId").val(id);
        $("#articleTypeName").val(name);
      }

      $(function() {
        var range = "${article.range}";
        if(range == 2) {
          $("input[name='range']").attr("checked", true);
        } else {
          $("input[name='range'][value=" + range + "]").attr("checked", true);
        }
        $("#articleTypeId").val("${article.articleType.id }");
      });

      function sub() {
        var id = $("#id").val();
        alert(id);
        layer.confirm('您确定需要提交吗?', {
          title: '提示',
          offset: ['222px', '360px'],
          shade: 0.01
        }, function(index) {
          layer.close(index);
          window.location.href = "${ pageContext.request.contextPath }/article/sumbit.html?id=" + id + "&status=1";
        });
      }
      
      function goBack(){
	  	var curpage = $("#curpage").val();
	  	var status = $("#status").val();
	  	var range = $("#range").val();
	  	var articleTypeId = "${articleTypeId}";
	  	var name = "${title}";
	  	 window.location.href = "${ pageContext.request.contextPath }/article/serch.html?page="+curpage+"&status="+status+"&range="+range+"&articleTypeId="+articleTypeId+"&name="+name;
	  }
    </script>
    <style type="text/css">
    	.margin-left-0 li {
    		float: none;
    	}
    </style>
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
            <a href="javascript:void(0)">查看</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>
    <div class="container content pt0">
      <div class="row magazine-page">
        <div class="col-md-12 col-sm-12 col-xs-12 tab-v2 mt10">
          <ul class="nav nav-tabs bgwhite">
            <li class="active">
              <a class="s_news f18">详细信息</a>
            </li>
          </ul>
          <input type="hidden" id="curpage" value="${curpage}">
          <input type="hidden" id="status" value="${status}">
          <input type="hidden" id="range" value="${range}">
          <div class="tab-content padding-top-20 over_hideen">
            <div class="tab-pane fade active in">
              <h2 class="count_flow jbxx">基本信息</h2>
              <table class="table table-bordered">
                <tbody>
                  <tr>
                    <td class="bggrey" width="10%">信息标题：</td>
                    <td colspan="3">${article.name}</td>
                  </tr>
                  <tr>
                    <td class="bggrey" width="10%">信息栏目：</td>
                    <td width="40%">
                      ${article.articleType.name }
                    </td>
                    <td class="bggrey" width="10%">栏目属性：</td>
                    <td width="40%">
                      ${second}
                    </td>
                  </tr>
                  <c:if test="${three != '' && three != null}">
                  <tr>
                    <td class="bggrey" width="10%">采购类型：</td>
                    <td width="40%">
                      ${three}
                    </td>
                    <c:if test="${four != '' && four != null}">
	                    <td class="bggrey" width="10%">采购方式：</td>
	                    <td width="40%">
	                      ${four}
	                    </td>
                    </c:if>
                    <c:if test="${four == '' || four == null}">
                    	 <td class="bggrey" width="10%">发布范围：</td>
	                     <td width="40%">
	                      <c:if test="${article.range=='0' }">
	                       	 内网
	                      </c:if>
	                      <c:if test="${article.range=='2' }">
	                       	 内外网
	                      </c:if>
	                    </td>
                    </c:if>
                  </tr>
                  </c:if>
                  <c:if test="${three == '' || three == null}">
                  	<tr>
	                    <td class="bggrey" width="10%">发布范围：</td>
	                    <td width="40%">
	                      <c:if test="${article.range=='0' }">
	                        	内网
	                      </c:if>
	                      <c:if test="${article.range=='2' }">
	                        	内外网
	                      </c:if>
	                    </td>
	                  </tr>
                  </c:if>
                  <c:if test="${three != '' || three != null}">
	                  <c:if test="${four != '' && four != null}">
		                  <tr>
		                    <td class="bggrey" width="10%">发布范围：</td>
		                    <td width="40%">
		                      <c:if test="${article.range=='0' }">
		                        内网
		                      </c:if>
		                      <c:if test="${article.range=='2' }">
		                        内外网
		                      </c:if>
		                    </td>
		                    <c:if test="${categoryNames != '' && categoryNames != null}">
			                    <td class="bggrey" width="10%">产品类别：</td>
			                    <td width="40%">
			                      ${categoryNames}
			                    </td>
		                    </c:if>
		                  </tr>
	                  </c:if>
	                  <c:if test="${four != '' || four != null}">
	                  	<c:if test="${categoryNames != '' && categoryNames != null}">
		                    <td class="bggrey" width="10%">产品类别：</td>
		                    <td width="40%">
		                      ${categoryNames}
		                    </td>
	                    </c:if>
	                  </c:if>
                  </c:if>
                </tbody>
              </table>
             <h2 class="count_flow jbxx">信息正文</h2>
             <div class="col-md-12 col-xs-12 col-sm-12 border1 min-h130 article-content-backend">
             	${article.content}
             </div>
              <c:if test="${article.status==2 }">
                <h2 class="count_flow jbxx clear">审核结果:审核通过</h2>
              </c:if>
              <c:if test="${article.status==3 }">
                <h2 class="count_flow jbxx clear">退回理由</h2>
                <div class="col-md-12 col-xs-12 col-sm-12 border1 h80">${article.reason}</div>
              </c:if>

              <ul class="clear p0 col-md-12 col-xs-12 col-sm-12 clearli">
                <li class="col-md-6 col-sm-12 col-xs-12 mt10">
                  <span class="fl">已上传的附件：</span>
                  <u:show showId="artice_file_show" delete="false" groups="artice_show,artice_file_show,artice_secret_show" businessId="${article.id}" sysKey="${sysKey}" typeId="${artiAttachTypeId}" />
                </li>
                
                <li class="col-md-6 col-sm-12 col-xs-12 mt10">
                  <span class="fl">单位及保密委员会审核表：</span>
                  <u:show showId="artice_secret_show" delete="false" groups="artice_show,artice_file_show,artice_secret_show" businessId="${article.id}" sysKey="${sysKey}" typeId="${secretTypeId }" />
                </li>
                
               <c:if test="${article.lastArticleType.id=='111' }">
                <li class="col-md-6 col-sm-12 col-xs-12 mt10 pl0" id="picNone">
                  <span class="fl">已上传的图片：</span>
                  <div class="fl">
                    <u:show showId="artice_show" delete="false" groups="artice_show,artice_file_show,artice_secret_show" businessId="${article.id }" sysKey="${sysKey}" typeId="${attachTypeId }" />
                  </div>
                </li>
               </c:if>
                
              </ul>
            </div>
          </div>
          <div class="col-md-12 col-sm-12 col-xs-12 mt20 tc">
            <input class="btn btn-windows back" value="返回" type="button" onclick="goBack()">
          </div>
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
      var content = '${article.content}';
      ue.ready(function() {
        ue.setContent(content);
        ue.setDisabled(true);
      });
    </script>
    
    <script src="${pageContext.request.contextPath }/public/ueditor/ueditor.parse.js"></script>
<script>
setTimeout(function(){uParse('div',
{
 'highlightJsUrl':'${pageContext.request.contextPath }/public/ueditor/third-party/SyntaxHighlighter/shCore.js',
 'highlightCssUrl':'${pageContext.request.contextPath }/public/ueditor/third-party/SyntaxHighlighter/shCoreDefault.css'})
},300);
</script>
  </body>

</html>