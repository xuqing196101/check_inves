 <%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
 
 <c:forEach items="${lists }" var="obj" varStatus="vs">
  <tr name="detailRow" <c:if test="${obj.price==''||obj.price==null}">attr="true"</c:if>>
  		<td class="tc">${vs.index+1}</td>
  
				 						<td class="tc  p0">
											<input type="hidden" name="list[${vs.index }].id"  value="${obj.id }">
										
											<input type="text" name="list[${vs.index }].seq" value="${obj.seq}" onblur="getSeq(this)"    class="m0 border0 w50 tc">
											<input type="hidden" name="list[${vs.index }].parentId"  value="${obj.parentId}">
										</td>
										<td class=" p0" >
										
									<%-- 	<input type="text" name="list[${vs.index }].department"   value="${obj.department}"> --%>
										<%-- 	<input type="hidden" name="list[${vs.index }].department" value="${orgId }" > --%>
								  			<input type="text"  name="list[${vs.index }].department" readonly="readonly" value="${shortName}"  class="m0 border0 w80" >
											
										
										</td>
										<td>
											<input type="text"  class="m0 border0" name="list[${vs.index }].goodsName" onkeyup="listName(this)"  value="${obj.goodsName}" />
										</td>
										<td><input type="text" class="stand" name="list[${vs.index }].stand" value="${obj.stand}"></td>
										<td><input type="text" class="qualitstand" name="list[${vs.index }].qualitStand" value="${obj.qualitStand}" class=""></td>
										<td><input type="text" class="item" name="list[${vs.index }].item"  value="${obj.item}" ></td>
										<td>
											<input type="hidden"  value="${obj.id }" >
										    <input type="text" class="purchasecount"  onblur='sum2(this)' <c:if test="${obj.price==''||obj.price==null}">readonly="readonly"</c:if> name="list[${vs.index }].purchaseCount" onkeyup="checkNum(this,1)" value="${obj.purchaseCount}" >
											<input type="hidden"   value="${obj.parentId }" >
										</td>
										<td>
											<input type='hidden'  value='${obj.id }' >
											 <input type="text" class="price" <c:if test="${obj.price==''||obj.price==null}">readonly="readonly"</c:if>  onblur='sum1(this)' name="list[${vs.index }].price" onkeyup="checkNum(this,2)" value="${obj.price}" >
											<input type="hidden"   value="${obj.parentId }">
										</td>
										
										<td>
											<input type='hidden'  value='${obj.id }' >
											<input type="text" readonly="readonly" class="budget" name="list[${vs.index }].budget"   value="${obj.budget}" >
											<input type="hidden"   value="${obj.parentId }">
										</td>
										
										
										<td><input type="text" class="deliverdate" name="list[${vs.index }].deliverDate" value="${obj.deliverDate}" ></td>
										<td>
											<select name="list[${vs.index }].purchaseType" class="purchasetype" onchange="changeType(this)" >
												<option value="">请选择</option>
												<c:forEach items="${list2 }" var="objd">
														<option value="${objd.name}" <c:if test="${objd.name==obj.purchaseType}"> selected='selected'</c:if> > ${objd.name }</option>
													 
												</c:forEach>
											</select>
										</td>
										<td>
										
										<input type="text" name="list[${vs.index }].supplier" onblur="checkSupplierName('${vs.index }')" onmouseover="supplierReadOnly(this)" class="m0 w260 border0"   value="${obj.supplier}">
										
											
										    <%-- <select name="list[${vs.index }].supplier" class="purchasename" onchange="changeType(this)" id="pType[0]">
												<option value="">请选择</option>
												<c:forEach items="${suppliers }" var="sup">
													<option value="${sup.supplierName }">${sup.supplierName}</option>
												</c:forEach>
											</select> --%>
										</td>
										<td name="userNone"><input type="text" name="list[${vs.index }].isFreeTax" class="freetax" value="${obj.isFreeTax}"></td>
									 	<td name='userNone'><input type="text" name="list[${vs.index }].goodsUse" class="goodsuse"></td>
										 <td name='userNone' ><input type="text" name="list[${vs.index }].useUnit" class="useunit"></td>
									     <td><input type="text" name="list[${vs.index }].memo"  class="memo" value="${obj.memo}" ></td>
									     <td>
											   <div class="extrafile">
													<u:upload id="pUp${vs.index }" multiple="true" buttonName="上传文件"  businessId="${obj.id}" sysKey="2" typeId="${attId}" auto="true" />
													<u:show showId="pShow${vs.index }" businessId="${obj.id}" sysKey="2" typeId="${attId}" />
											   </div>										
										 </td>
										
										
									 	<td><button type="button" class="btn" onclick="delRowIndex(this)">删除</button></td>
 		</tr>
 	</c:forEach>