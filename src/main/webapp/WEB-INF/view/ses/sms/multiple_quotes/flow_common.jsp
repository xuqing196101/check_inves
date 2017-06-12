<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="container clear mt20 div-center">
      <div class="list-unstyled padding-10 breadcrumbs-v3 div-center">
      <c:if test="${ not empty saleTenderList}">
        <span>
          <c:if test="${saleTenderList.get(0).bidFinish > 0}">
          <a href="${pageContext.request.contextPath}/mulQuo/openBid.html?projectId=${project.id}" class="img-v1">开标一览表</a>
          <span class="green_link">→</span>
          </c:if>
          <c:if test="${saleTenderList.get(0).bidFinish == 0}">
          <a href="javascript:void(0);" class="img-v2 orange_link" >开标一览表</a>
          <span class="">→</span>
          </c:if>
        </span>
       <span>
          <c:if test="${saleTenderList.get(0).bidFinish > 1}">
          <a href="${pageContext.request.contextPath}/mulQuo/priceBuild.html?projectId=${project.id}" class="img-v1">价格构成表</a>
          <span class="green_link">→</span>
          </c:if>
           <c:if test="${saleTenderList.get(0).bidFinish == 1}">
          <a href="javascript:void(0);" class="img-v2 orange_link">价格构成表</a>
          <span class="">→</span>
          </c:if>
           <c:if test="${saleTenderList.get(0).bidFinish < 1}">
          <a href="javascript:void(0);" onclick="show('${saleTenderList.get(0).bidFinish}')" class="img-v3">价格构成表</a>
          <span class="">→</span>
          </c:if>
      </span>
     <%--  <span>
          <c:if test="${saleTenderList.get(0).bidFinish > 2}">
          <a href="${pageContext.request.contextPath}/mulQuo/priceView.html?projectId=${project.id}" class="img-v1">明细表</a><!--货物材料、部件、工具价格明细表  -->
          <span class="green_link">→</span>
          </c:if>
          <c:if test="${saleTenderList.get(0).bidFinish == 2}">
          <a href="javascript:void(0);" class="img-v2 orange_link">明细表</a><!--货物材料、部件、工具价格明细表  -->
          <span class="">→</span>
          </c:if>
          <c:if test="${saleTenderList.get(0).bidFinish < 2}">
          <a href="javascript:void(0);" onclick="show('${saleTenderList.get(0).bidFinish}')"  class="img-v3">明细表</a><!--货物材料、部件、工具价格明细表  -->
          <span class="">→</span>
          </c:if>
      </span> --%>
        <span>
          <c:if test="${saleTenderList.get(0).bidFinish == 3}">
          <a href="javascript:void(0);" class="img-v2 orange_link">编制标书</a>
          <span class="">→</span>
          </c:if>
          <c:if test="${saleTenderList.get(0).bidFinish > 3}">
          <a href="${pageContext.request.contextPath}/supplierProject/bidDocument.html?projectId=${project.id}" class="img-v1">编制标书</a>
          <span class="green_link">→</span>
          </c:if>
          <c:if test="${saleTenderList.get(0).bidFinish < 3}">
          <a href="javascript:void(0)" onClick="show('${saleTenderList.get(0).bidFinish}')"  class="img-v3">编制标书</a>
          <span class="" >→</span>
          </c:if>
      </span>
      <span>
        <c:if test="${saleTenderList.get(0).bidFinish == 4}">
          <a href="javascript:void(0);" class="img-v2 orange_link">绑定指标</a>
          <span class="">→</span>
        </c:if>
        <c:if test="${saleTenderList.get(0).bidFinish > 4}">
          <a href="${pageContext.request.contextPath}/supplierProject/toBindingIndex.html?projectId=${project.id}" class="img-v1">绑定指标</a>
          <span class="green_link">→</span>
        </c:if>
        <c:if test="${saleTenderList.get(0).bidFinish < 4}">
          <a href="javascript:void(0)" onclick="show('${saleTenderList.get(0).bidFinish}')" class="img-v3">绑定指标</a>
          <span class="">→</span>
        </c:if>
      </span>
      <span>
          <c:if test="${saleTenderList.get(0).bidFinish < 5 }">
            <a href="javascript:void(0);" onclick="show('${saleTenderList.get(0).bidFinish}')" class="img-v3">完成</a>
          </c:if>
          <c:if test="${saleTenderList.get(0).bidFinish == 5 }">
          <a href="javascript:void(0);" class="img-v3">完成</a>
        </c:if>
      </span>
      </c:if>
      </div>
    </div>
