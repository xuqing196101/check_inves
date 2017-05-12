<%--
  Created by IntelliJ IDEA.
  User: yggc
  Date: 2017/5/10
  Time: 11:05
  用于异步加载附件控件,已解决附件的动态显示
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<%@ include file="/WEB-INF/view/common/webupload.jsp" %>

<u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="${uploadId}" maxcount="${maxcount}"
          businessId="${businessId}" sysKey="${sysKey}" typeId="${typeId}" auto="true" />
<u:show showId="${showId}" businessId="${businessId}" sysKey="${sysKey}" typeId="${typeId}" />


