<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

  <head>
  <%@ include file="../../../common.jsp"%>
    <link href="${pageContext.request.contextPath}/public/accordion/SpryAccordion.css" rel="stylesheet" type="text/css">
    <script src="${pageContext.request.contextPath}/public/accordion/SpryAccordion.js"></script>
    <title>待办事项</title>
    <script type="text/javascript">
    $(function(){
  	  laypage({
  		    cont: $("#supplierTodos"), //容器。值支持id名、原生dom对象，jquery对象,
  		    pages: "${supplierTodos.pages}", //总页数
  		    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
  		    skip: true, //是否开启跳页
  		    total: "${supplierTodos.total}",
  		    startRow: "${supplierTodos.startRow}",
  		    endRow: "${supplierTodos.endRow}",
  		    groups: "${supplierTodos.pages}">=3?3:"${supplierTodos.pages}", //连续显示分页数
  		    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
  		    	return "${supplierTodos.pageNum}";
  		    }(), 
  		    jump: function(e, first){ //触发分页后的回调
  		        if(!first){ //一定要加此判断，否则初始时会无限刷新
  		      		window.location.href="${pageContext.request.contextPath}/todo/todos.html?supplierPage="+e.curr+"&type=supplier";
  		        }
  		    }
  		});
  	    laypage({
		    cont: $("#expertTodos"), //容器。值支持id名、原生dom对象，jquery对象,
		    pages: "${expertTodos.pages}", //总页数
		    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
		    skip: true, //是否开启跳页
		    total: "${expertTodos.total}",
		    startRow: "${expertTodos.startRow}",
		    endRow: "${expertTodos.endRow}",
		    groups: "${expertTodos.pages}">=3?3:"${expertTodos.pages}", //连续显示分页数
		    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
		    	return "${expertTodos.pageNum}";
		    }(), 
		    jump: function(e, first){ //触发分页后的回调
		        if(!first){ //一定要加此判断，否则初始时会无限刷新
		      		window.location.href="${pageContext.request.contextPath}/todo/todos.html?expertPage="+e.curr+"&type=expert";
		        }
		    }
		});
  		 laypage({
 		    cont: $("#projectTodos"), //容器。值支持id名、原生dom对象，jquery对象,
 		    pages: "${projectTodos.pages}", //总页数
 		    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
 		    skip: true, //是否开启跳页
 		    total: "${projectTodos.total}",
 		    startRow: "${projectTodos.startRow}",
 		    endRow: "${projectTodos.endRow}",
 		    groups: "${projectTodos.pages}">=3?3:"${projectTodos.pages}", //连续显示分页数
 		    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
 		    	return "${projectTodos.pageNum}";
 		    }(), 
 		    jump: function(e, first){ //触发分页后的回调
 		        if(!first){ //一定要加此判断，否则初始时会无限刷新
 		      		window.location.href="${pageContext.request.contextPath}/todo/todos.html?projectPage="+e.curr+"&type=project";
 		        }
 		    }
 		});
  	    $(document).keyup(function(event) {
  			if (event.keyCode == 13) {
  				query();
  			}
  		});
    });
      /** 全选全不选 */
      function selectAll() {
        var checklist = document.getElementsByName("chkItem");
        var checkAll = document.getElementById("checkAll");
        if(checkAll.checked) {
          for(var i = 0; i < checklist.length; i++) {
            checklist[i].checked = true;
          }
        } else {
          for(var j = 0; j < checklist.length; j++) {
            checklist[j].checked = false;
          }
        }
      }
      
      /** 单选 */
      function check() {
        var count = 0;
        var checklist = document.getElementsByName("chkItem");
        var checkAll = document.getElementById("checkAll");
        for(var i = 0; i < checklist.length; i++) {
          if(checklist[i].checked == false) {
            checkAll.checked = false;
            break;
          }
          for(var j = 0; j < checklist.length; j++) {
            if(checklist[j].checked == true) {
              checkAll.checked = true;
              count++;
            }
          }
        }
      }

      function view(url) {
        $("#a").attr("href", url);
        var el = document.getElementById('a');
        el.click(); //触发打开事件
      }

      function del() {
        var ids = [];
        $('input[name="chkItem"]:checked').each(function() {
          ids.push($(this).val());
        });
        if(ids.length > 0) {
          layer.confirm('您确定要删除吗?', {
            title: '提示',
            offset: ['222px', '360px'],
            shade: 0.01
          }, function(index) {
            layer.close(index);
            window.location.href = "${pageContext.request.contextPath}/StationMessage/deleteSoftSMIsDelete.do?ids=" + ids;
          });
        } else {
          layer.alert("请选择要删除的用户", {
            offset: ['222px', '390px'],
            shade: 0.01
          });
        }
      }
      
      
    </script>

    <body >
      <!-- 查看项目-->
      <div id="con_one_2" class="dnonev">
        <div id="Accordion2" class="Accordion" >
        <div  class="AccordionPanel AccordionPanelOpen">
              <div class="AccordionPanelTab" onclick="view(this,'supplierTodos')">
                                  供应商待办(${supplierTodos.total})
              </div>
              <div class=""  id="agentslist0" style="width: 100%;height:300px; overflow:auto; ">
                <a id="a" href="#" target="_parent"></a>
                <table  class="hand table table-striped table-bordered">
                    <tr>
                      <th class="info w50">序号</th>
                      <th class="info">标题</th>
                      <th class="info">发送人</th>
                      <th class="info">创建时间</th>
                    </tr>
                    <c:forEach items="${supplierTodos.list}" var="agents" varStatus="s">
                      <tr class="cursor" onclick="view('${pageContext.request.contextPath}/${ agents.url}');">
                        <td class="tc w50">${s.index+1}</td>
                        <td class="tl pl20" title="${agents.name }">
                          <c:choose>
                            <c:when test="${fn:length(agents.name) > 20}">
                              ${fn:substring(agents.name, 0, 20)}......
                            </c:when>
                            <c:otherwise>
                              ${agents.name }
                            </c:otherwise>
                          </c:choose>
                        </td>
                        <td class="tc w100">${agents.senderName}</td>
                        <td class="tc w150">
                          <fmt:formatDate value="${agents.createdAt}" pattern="YYY-MM-dd HH:mm:ss" />
                        </td>
                      </tr>
                    </c:forEach>
                </table>
                <div id="supplierTodos" align="right"></div>
              </div>
         </div>
         <div  class="AccordionPanel">
              <div class="AccordionPanelTab" onclick="view(this,'expertTodos')">
                                  专家待办(${expertTodos.total})
              </div>
              <div class=""  id="agentslist1" style="width: 100%;height:300px; overflow:auto;display: none; ">
                <a id="a" href="#" target="_parent"></a>
                <table  class="hand table table-striped table-bordered">
                    <tr>
                      <th class="info w50">序号</th>
                      <th class="info">标题</th>
                      <th class="info">发送人</th>
                      <th class="info">创建时间</th>
                    </tr>
                    <c:forEach items="${expertTodos.list}" var="agents" varStatus="s">
                      <tr class="cursor" onclick="view('${pageContext.request.contextPath}/${ agents.url}');">
                        <td class="tc w50">${s.index+1}</td>
                        <td class="tl pl20" title="${agents.name }">
                          <c:choose>
                            <c:when test="${fn:length(agents.name) > 20}">
                              ${fn:substring(agents.name, 0, 20)}......
                            </c:when>
                            <c:otherwise>
                              ${agents.name }
                            </c:otherwise>
                          </c:choose>
                        </td>
                        <td class="tc w100">${agents.senderName}</td>
                        <td class="tc w150">
                          <fmt:formatDate value="${agents.createdAt}" pattern="YYY-MM-dd HH:mm:ss" />
                        </td>
                      </tr>
                    </c:forEach>
                </table>
                <div id="expertTodos" align="right"></div>
              </div>
         </div>
        <div  class="AccordionPanel">
              <div class="AccordionPanelTab" onclick="view(this,'projectTodos')">
                                  项目待办(${projectTodos.total})
              </div>
              <div class=""  id="agentslist1" style="width: 100%;height:300px; overflow:auto;display: none; ">
                <a id="a" href="#" target="_parent"></a>
                <table  class="hand table table-striped table-bordered">
                    <tr>
                      <th class="info w50">序号</th>
                      <th class="info">标题</th>
                      <th class="info">发送人</th>
                      <th class="info">创建时间</th>
                    </tr>
                    <c:forEach items="${expertTodos.list}" var="agents" varStatus="s">
                      <tr class="cursor" onclick="view('${pageContext.request.contextPath}/${ agents.url}');">
                        <td class="tc w50">${s.index+1}</td>
                        <td class="tl pl20" title="${agents.name }">
                          <c:choose>
                            <c:when test="${fn:length(agents.name) > 20}">
                              ${fn:substring(agents.name, 0, 20)}......
                            </c:when>
                            <c:otherwise>
                              ${agents.name }
                            </c:otherwise>
                          </c:choose>
                        </td>
                        <td class="tc w100">${agents.senderName}</td>
                        <td class="tc w150">
                          <fmt:formatDate value="${agents.createdAt}" pattern="YYY-MM-dd HH:mm:ss" />
                        </td>
                      </tr>
                    </c:forEach>
                </table>
                <div id="projectTodos" align="right"></div>
              </div>
         </div>
        <%-- 
          <c:forEach items="${listTodos }" var="agentslist" varStatus="s">
            <div class="AccordionPanel">
              <div class="AccordionPanelTab">
                <c:if test="${agentslist[0].undoType==1}">
                                                  供应商待办
                </c:if>
                <c:if test="${agentslist[0].undoType==2}">
                                                     专家待办
                </c:if>
                 <c:if test="${agentslist[0].undoType==3}">
                                                     项目待办
                </c:if>
                (${agentslist.size()})
              </div>
              <div class=""  id="agentslist${s.index}" style="width: 100%;height:300px; overflow:auto; ">
                <a id="a" href="#" target="_parent"></a>
                <table  class="hand table table-striped table-bordered">
                    <tr>
                      <th class="info w50">序号</th>
                      <th class="info">标题</th>
                      <th class="info">发送人</th>
                      <th class="info">创建时间</th>
                    </tr>
                    <c:forEach items="${agentslist }" var="agents" varStatus="s">
                      <tr class="cursor" onclick="view('${pageContext.request.contextPath}/${ agents.url}');">
                        <td class="tc w50">${s.index+1}</td>
                        <td class="tl pl20" title="${agents.name }">
                          <c:choose>
                            <c:when test="${fn:length(agents.name) > 20}">
                              ${fn:substring(agents.name, 0, 20)}......
                            </c:when>
                            <c:otherwise>
                              ${agents.name }
                            </c:otherwise>
                          </c:choose>
                        </td>
                        <td class="tc w100">${agents.senderName}</td>
                        <td class="tc w150">
                          <fmt:formatDate value="${agents.createdAt}" pattern="YYY-MM-dd HH:mm:ss" />
                        </td>
                      </tr>
                    </c:forEach>
                </table>
              </div>
            </div>
          </c:forEach> --%>
        </div>
      </div>
      <script type="text/javascript">
      var acc=$("#Accordion2").children();
        if("${type}"!=""){
        	if("${type}"=="expert"){
        		commNum(1);
        	}
        	if("${type}"=="project"){
        		commNum(2);
        	}
        	if("${type}"=="supplier"){
        		commNum(0);
        	}
        }
        function view(obj,name){
        		if(name=="projectTodos"){
        			commObj(obj,2);
        		}else if(name=="expertTodos"){
        			commObj(obj,1);
        		}else if(name=="supplierTodos"){
        			commObj(obj,0);
        		}
        }
        function commObj(obj,num){
        	for(var i=0;i<acc.length;i++){
				if(i==num){
					$(obj).parent().attr("class","AccordionPanel AccordionPanelOpen");
					$(obj).next()[0].style.display='';
    				$(obj).next()[0].style.height="300px";
				}else{
					$(acc[i]).attr("class","AccordionPanel AccordionPanelClosed");
    				$(acc[i]).children()[1].style.display='none';
    				$(acc[i]).children()[1].style.height="0px";
				}
			}
        }
        function commNum(index){
        	for(var i=0;i<acc.length;i++){
    			if(i==index){
    				$(acc[i]).attr("class","AccordionPanel AccordionPanelOpen");
    				$(acc[i]).children()[1].style.display='';
    				$(acc[i]).children()[1].style.height="300px";
    			}else{
    				$(acc[i]).attr("class","AccordionPanel AccordionPanelClosed");
    				$(acc[i]).children()[1].style.display='none';
    				$(acc[i]).children()[1].style.height="0px";
    			}
    		}
        }
        /* $("#agentslist1").scroll(function(){
        	if(parseFloat($("#agentslist1").height())+parseFloat($("#agentslist1").scrollTop())>=parseFloat(document.getElementById("agentslist1").scrollHeight)-1){
        		
        	}
        }) */
      </script>

    </body>

</html>