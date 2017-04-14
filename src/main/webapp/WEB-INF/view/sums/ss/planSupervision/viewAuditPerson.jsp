<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

  <head>
    <%@ include file="/WEB-INF/view/common.jsp"%>
    <%@ include file="/WEB-INF/view/common/webupload.jsp"%>

  </head>
  
  <body>
    <div class="drop_window">
      <ul class="list-unstyled">
        <li class="col-md-12 col-sm-6 col-xs-12">
          <label class="col-md-12 col-sm-12 col-xs-12 padding-left-5">审核意见</label>
          <span class="col-md-12 col-sm-12 col-xs-12 p0">
            <textarea class="w100p h80 p0" rows="3" cols="1" disabled="disabled">${advice}</textarea>
          </span>
        </li>
        <div class="clear"></div>
      </ul>
    </div>
  </body>
</html>
