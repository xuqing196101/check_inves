<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/tld/upload" prefix="up" %>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

  <head>
    <title>查看</title>

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
    </script>
  </head>

  <body>

    <!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
          <li>
            <a href="javascript:void(0)"> 首页</a>
          </li>
          <li>
            <a href="javascript:void(0)">信息服务</a>
          </li>
          <li>
            <a href="javascript:void(0)">信息管理</a>
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
          <div class="tab-content padding-top-20 over_hideen">
            <div class="tab-pane fade active in">
              <h2 class="count_flow jbxx">基本信息</h2>
              <table class="table table-bordered">
                <tbody>
                  <tr>
                    <td class="bggrey" width="10%">信息标题：</td>
                    <td colspan="3">${article.name }</td>
                  </tr>
                  <tr>
                    <td class="bggrey" width="10%">信息栏目：</td>
                    <td colspan="3">
                      ${article.articleType.name }
                    </td>
                  </tr>
                  <tr>
                    <td class="bggrey" width="10%">链接来源：</td>
                    <td colspan="3">
                      ${article.sourceLink }
                    </td>
                  </tr>
                  <tr>
                    <td class="bggrey" width="10%">发布范围：</td>
                    <td>
                      <c:if test="${article.range=='0' }">
                        内网
                      </c:if>
                      <c:if test="${article.range=='1' }">
                        外网
                      </c:if>
                      <c:if test="${article.range=='2' }">
                        内网&外网
                      </c:if>
                    </td>
                    <td class="bggrey" width="10%">
                      图片展示：
                    </td>
                    <td>${article.isPicShow }</td>
                  </tr>
                </tbody>
              </table>
              <h2 class="count_flow jbxx">信息正文</h2>
              <div class="col-md-12 col-xs-12 col-sm-12 border1">${article.content}</div>
              <c:if test="${article.status==2 }">
                <h2 class="count_flow jbxx clear">审核结果:审核通过</h2>
              </c:if>
              <c:if test="${article.status==3 }">
                <h2 class="count_flow jbxx clear">退回理由</h2>
                <div class="col-md-12 col-xs-12 col-sm-12 border1">${article.content}</div>
              </c:if>

              <ul class="clear p0 col-md-12 col-xs-12 col-sm-12 ">
                <li class="col-md-6 col-sm-12 col-xs-12 mt10">
                  <span class="fl">已上传的附件：</span>
                  <div class="fl mt5">
                    <c:forEach items="${article.articleAttachments}" var="a">
                      ${fn:split(a.fileName, '_')[1]},
                    </c:forEach>
                  </div>
                </li>
                <li class="col-md-6 col-sm-12 col-xs-12 mt10" id="picNone">
                  <span class="fl">已上传的图片：</span>
                  <div class="fl">
                    <up:show showId="artice_show" businessId="${article.id }" sysKey="${sysKey}" typeId="${attachTypeId }" />
                  </div>
                </li>
              </ul>
            </div>
          </div>
          <div class="col-md-12 col-sm-12 col-xs-12 mt20 tc">
            <input class="btn btn-windows back" value="返回" type="button" onclick="location.href='javascript:history.go(-1);'">
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
          var content = "${article.content}";
          ue.ready(function() {
            ue.setContent(content);
            ue.setDisabled(true);
          });
        </script>
  </body>

</html>