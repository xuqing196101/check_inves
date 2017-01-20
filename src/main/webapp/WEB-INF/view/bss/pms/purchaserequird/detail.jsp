 <%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
 
  <tr name="detailRow">
  		<td class="tc">${index+1}</td>
  
				 						<td class="tc  p0">
											<input type="hidden" name="list[${index }].id"  value="${id }">
										
											<input type="text" name="list[${index }].seq" value="" onblur="getSeq(this)"    class="m0 border0 w50 tc">
											<input type="hidden" name="list[${index }].parentId"  value="">
										</td>
										<td class=" p0" >
										
									<%-- 	<input type="text" name="list[${index }].department"   value="${obj.department}"> --%>
										<%-- 	<input type="hidden" name="list[${index }].department" value="${orgId }" > --%>
								  			<input type="text"  name="list[${index }].department" readonly="readonly" value="${orgName}"  class="m0 border0 w260" >
											
										
										</td>
										<td class=" p0">
											<input type="text"  class="m0 border0 w200" name="list[${index }].goodsName" onkeyup="listName(this)" onblur="lossValue()" value="" />
										</td>
										<td class="tc  p0"><input type="text" class="m0 w200 border0" name="list[${index }].stand" value="${objs.stand}"></td>
										<td class="tc  p0"><input type="text" class="m0 w140 border0" name="list[${index }].qualitStand" value="" class=""></td>
										<td class="tc  p0"><input type="text" class="m0 w50 border0" name="list[${index }].item" value="" ></td>
										
										
										<td class="tc p0" >
											<input type="hidden"  value="${id }" >
										<input type="text" class="m0 border0 w50"  onblur='sum2(this)' name="list[${index }].purchaseCount" onkeyup="checkNum(this,1)" value="" >
											<input type="hidden"   value="" >
										</td>
										
										
										<td class="tc  p0" >
											<input type='hidden'  value='${id }' >
											 <input type="text" class="m0 border0 w80"  onblur='sum1(this)' name="list[${index }].price" onkeyup="checkNum(this,2)" value="" >
											<input type="hidden"   value="">
										</td>
										
										<td class="tc  p0">
											<input type="hidden"   value='${id }' >
											<input type="text" readonly="readonly" class="m0 w80 border0" name="list[${index }].budget"   value="" >
											<input type="hidden"   value="" >
										</td>
										
										
										<td class=" p0"><input type="text" class="m0 border0" name="list[${index }].deliverDate" value=" " ></td>
										<td class="p0">
											<select name="list[${index }].purchaseType" class="m0 border0" onchange="changeType(this)" >
												<option value="">请选择</option>
												<c:forEach items="${list2 }" var="objd">
														<option value="${objd.id }"> ${objd.name }</option>
													 
												</c:forEach>
											</select>
										</td>
										<td class="tc  p0">
										
								<%-- 		<input type="text" name="list[${index }].supplier"  class="m0 w260 border0" > --%>
										
											
										<select name="list[${index }].supplier" class="m0 border0" onchange="changeType(this)" id="pType[0]">
												<option value="">请选择</option>
												<c:forEach items="${suppliers }" var="sup">
												
													<option value="${sup.supplierName }">${sup.supplierName }</option>
												</c:forEach>
											</select>
											
											
										</td>
										<td class="tc  p0"><input type="text" name="list[${index }].isFreeTax" class="m0 border0"></td>
									 	<td class="tc  p0" name='userNone'><input type="text" name="list[${index }].goodsUse" class="m0 border0"></td>
										 <td class="tc p0"  name='userNone' ><input type="text" name="list[${index }].useUnit" class="m0 w260 border0"></td>
									     <td class="tc  p0"><input type="text" name="list[${index }].memo" value=" " class="m0 border0" ></td>
									      <td style="width:300px;" class="p0">
											   <div class="w200">
													<u:upload id="pUp${index }" multiple="true"  businessId="${id}" sysKey="2" typeId="${attId}" auto="true" />
													<u:show showId="pShow${index }" businessId="${id}" sysKey="2" typeId="${attId}" />
											   </div>											
										 </td>
										
										
									 	<td class="tc p0"><button type="button" class="btn" onclick="delRowIndex(this)">删除</button></td>
 		</tr>