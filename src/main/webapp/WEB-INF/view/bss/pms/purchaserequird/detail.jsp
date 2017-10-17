 <%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
 
  <tr name="detailRow">
  		<td class="tc">${index+1}</td>
  
				 						<td class="tc  p0">
											<input type="hidden" name="list[${indNum }].id"  value="${id }">
										
											<input type="text" name="list[${indNum }].seq" value="" onblur="getSeq(this)"    class="m0 border0 w50 tc">
											<input type="hidden" name="list[${indNum }].parentId"  value="">
										</td>
										<td class=" p0" >
										
									<%-- 	<input type="text" name="list[${index }].department"   value="${obj.department}"> --%>
										<%-- 	<input type="hidden" name="list[${index }].department" value="${orgId }" > --%>
								  			<input type="text"  name="list[${indNum }].department" readonly="readonly" value="${orgName}"  class="m0 border0 w80" >
											
										
										</td>
										<td name="hidden">
											<input type="text"  class="m0 border0" name="list[${indNum }].goodsName" onkeyup="listName(this)"  value="" />
										</td>
										<td><input type="text" class="stand" name="list[${indNum }].stand" value="${objs.stand}"></td>
										<td><input type="text" class="qualitstand" name="list[${indNum }].qualitStand" value="" class=""></td>
										<td><input type="text" class="item" name="list[${indNum }].item" value="" ></td>
										<td>
											<input type="hidden"  value="${id }" >
										    <input type="text" class="purchasecount"  onblur='sum2(this)' name="list[${indNum }].purchaseCount" onkeyup="checkNum(this,1)" value="" >
											<input type="hidden"   value="" >
										</td>
										<td>
											<input type='hidden'  value='${id }' >
											 <input type="text" class="price"  onblur='sum1(this)' name="list[${indNum }].price" onkeyup="checkNum(this,2)" value="" >
											<input type="hidden"   value="">
										</td>
										
										<td>
											<input type="hidden"   value='${id }' >
											<input type="text" readonly="readonly" class="budget" name="list[${indNum }].budget"   value="" >
											<input type="hidden"   value="" >
										</td>
										
										
										<td><input type="text" class="deliverdate" name="list[${indNum }].deliverDate" value=" " ></td>
										<td>
											<select name="list[${indNum }].purchaseType" class="purchasetype" onchange="changeType(this)" >
												<option value="">请选择</option>
												<c:forEach items="${list2 }" var="objd">
														<option value="${objd.name }"> ${objd.name }</option>
													 
												</c:forEach>
											</select>
										</td>
										<td>
										
										<input type="text" name="list[${indNum }].supplier" onblur="checkSupplierName('${indNum }')" onmouseover="supplierReadOnly(this)" class="m0 w260 border0" >
										
											
										    <%-- <select name="list[${index }].supplier" class="purchasename" onchange="changeType(this)" id="pType[0]">
												<option value="">请选择</option>
												<c:forEach items="${suppliers }" var="sup">
													<option value="${sup.supplierName }">${sup.supplierName}</option>
												</c:forEach>
											</select> --%>
										</td>
										<td name="userNone"><input type="text" name="list[${indNum }].isFreeTax" class="freetax"></td>
									 	<td name='userNone'><input type="text" name="list[${indNum }].goodsUse" class="goodsuse"></td>
										 <td name='userNone' ><input type="text" name="list[${indNum }].useUnit" class="useunit"></td>
									     <td><input type="text" name="list[${indNum }].memo" value="" class="memo" ></td>
									     <td>
											   <div class="extrafile">
													<u:upload id="pUp${uuId}" multiple="true" buttonName="上传文件"  businessId="${id}" sysKey="2" typeId="${attId}" auto="true" />
													<u:show showId="pShow${uuId}" businessId="${id}" sysKey="2" typeId="${attId}" />
											   </div>											
										 </td>
										
										
									 	<td><button type="button" class="btn" onclick="delRowIndex(this)">删除</button></td>
 		</tr>