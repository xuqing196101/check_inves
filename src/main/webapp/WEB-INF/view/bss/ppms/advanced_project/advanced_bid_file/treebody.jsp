<%@page import="bss.model.ppms.ScoreModel"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>


<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/WEB-INF/view/common.jsp"%>
       
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
ScoreModel scoreModel = (ScoreModel)request.getAttribute("scoreModel");
System.out.print(scoreModel);
%>
<script src="${pageContext.request.contextPath}/public/validate/jquery.validate.min.js"></script>
<script type="text/javascript">
  function addRows() {
     $("#guding").before("<tr><td><span class='star_red'>*</span>选择项名称</td><td><input onkeyup='gernerator();' ></td><td><span class='star_red'>*</span>对应分数</td><td><input onkeyup='gernerator();'></td><td class='tc'><button class='btn btn-windows delete' type=button onclick=deleteRow(this)>删除</button></td></tr>");
  }
  function deleteRow(obj) {
      if ($("#show_table").get(0).rows.length == 5) {
        layer.msg("请填写数据");
      } else {
        $(obj).parent().parent().remove(); 
      }
  }

function judge(index) {
    gernerator();
    if (index == 0) {
      var trArr = new Array();
      trArr = $("tr");
      var i;
      for (i = 0; i < trArr.length; i++) {
        if ($(trArr[i]).hasClass("show")) {
          $(trArr[i]).removeClass("hide");
        }
      }
    } else {
      var trArr = new Array();
      trArr = $("tr");
      var i;
      for (i = 0; i < trArr.length; i++) {
        if ($(trArr[i]).hasClass("show")) {
          $(trArr[i]).addClass("hide");
        }
      }
    }
  }
  $(function(){
    if ('${scoreModel.isHave}' == 1) {
      var trArr = new Array();
      trArr = $("tr");
      var i;
      for (i = 0; i < trArr.length; i++) {
        if ($(trArr[i]).hasClass("show")) {
          $(trArr[i]).addClass("hide");
        }
      }
    }
    var ischeck = '${scoreModel.ischeck}';
    $("#check").val(ischeck);
  });
  function judgeRelationScore(index) {
    gernerator();
      var relation = $("#relation").find("option:checked").val();
    if (index == 1) {
      if (relation == 1) {
        //最低分
          $("#relationScore").val(1);
      } else {
        //最高分 
          $("#relationScore").val(0);
      }
    } else {
      if (relation == 1) {
        //最高分
          $("#relationScore").val(0);
      } else {
        //最低分
          $("#relationScore").val(1);
      }
    }
  }
  
  function judgeRelationScore1(index) {
    gernerator();
    var relation = $("#addSubtractTypeName").find("option:checked").val();
    if (index == 1) {
      if (relation == 1) {
        //最低分
        $("#relationScore").val(1);
      } else {
        //最高分
        $("#relationScore").val(0);
      }
    } else {
      if (relation == 1) {
        //最高分
        $("#relationScore").val(0);
      } else {
        //最低分
        $("#relationScore").val(1);
      }
    }
  }
  
  function judgeRelationScore2(index) {
    gernerator();
    var relation = $("#addSubtractTypeName").find("option:checked").val();
    if (index == 1) {
      if (relation == 1) {
        //最低分
        $("#relationScore").val(0);
      } else {
        //最高分
        $("#relationScore").val(1);
      }
    } else {
      if (relation == 1) {
        //最高分
        $("#relationScore").val(1);
      } else {
        //最低分
        $("#relationScore").val(0);
      }
    }
  }
  
  function judgeRelationScore3(index) {
    gernerator();
      var relation = $("#relation").find("option:checked").val();
    if (index == 1) {
      if (relation == 1) {
        //最低分
          $("#relationScore").val(0);
      } else {
        //最高分 
          $("#relationScore").val(1);
      }
    } else {
      if (relation == 1) {
        //最高分
          $("#relationScore").val(1);
      } else {
        //最低分
          $("#relationScore").val(0);
      }
    }
  }
  
  
  function choseModel(){
      $("#biaoshi").addClass("hide");
    var model = $("#model").val();
    console.dir(model);
    $("#showParamButton").hide();
    $("#model73").hide();//隐藏区间参数table
    if(model==""){
      $("#showbutton").hide();
      $("#show_table tbody tr").remove();
    }else if(model=="0"){
      $("#show_table tbody tr").remove();
      $("#model1 tbody tr").clone().appendTo("#show_table tbody");
      $("#showbutton").show();
    }else if(model=="1"){
      var addSubtractTypeName = $("#sm2").val();
      $("#show_table tbody tr").remove();
      if(addSubtractTypeName=="0"){
        $("#model21 tbody tr").clone().appendTo("#show_table tbody");
      }else if(addSubtractTypeName=="1"){
        $("#model22 tbody tr").clone().appendTo("#show_table tbody");
      }else{
        //默认加分实例
        $("#model21 tbody tr").clone().appendTo("#show_table tbody");
      }
      $("#showbutton").show();
    }else if(model=="2"){
      $("#show_table tbody tr").remove();
      $("#model3 tbody tr").clone().appendTo("#show_table tbody");
      $("#showbutton").show();
    }else if(model=="3"){
      $("#show_table tbody tr").remove();
      $("#model4 tbody tr").clone().appendTo("#show_table tbody");
      $("#showbutton").show();
    }else if(model=="4"){
      $("#show_table tbody tr").remove();
      $("#model5 tbody tr").clone().appendTo("#show_table tbody");
      $("#showbutton").show();
    }else if(model=="5"){
        $("#biaoshi").removeClass("hide");
      $("#show_table tbody tr").remove();
      $("#model6 tbody tr").clone().appendTo("#show_table tbody");
      $("#showbutton").show();
    }else if(model=="6"){
      $("#show_table tbody tr").remove();
      //$("#model7 tbody tr").clone().appendTo("#show_table tbody");
      var intervalTypeName71 = $("#sm7").val();
      if(intervalTypeName71!=undefined && intervalTypeName71=="1"){
        $("#showParamButton").show();
        $("#showbutton").hide();
        $("#model72 tbody tr").clone().appendTo("#show_table tbody");
        $("#model73").show();
      }else if(intervalTypeName71=="0"){
        $("#showbutton").show();
        $("#showParamButton").hide();
        $("#model71 tbody tr").clone().appendTo("#show_table tbody");
        $("#model73 tr:not(:first)").remove();//删除除了第一行的所有tr 
        $("#model73").hide();
      }else{
        $("#showbutton").show();
        $("#showParamButton").hide();
        $("#model71 tbody tr").clone().appendTo("#show_table tbody");
        $("#model73 tr:not(:first)").remove();//删除除了第一行的所有tr 
        $("#model73").hide();
      }
    }else if(model=="7"){
      $("#show_table tbody tr").remove();
      var intervalTypeName71 = $("#sm7").val();
      if(intervalTypeName71!=undefined && intervalTypeName71=="1"){
        $("#showParamButton").show();
        $("#showbutton").hide();
        $("#model82 tbody tr").clone().appendTo("#show_table tbody");
        $("#model73").show();
      }else if(intervalTypeName71=="0"){
        $("#showbutton").show();
        $("#showParamButton").hide();
        $("#model81 tbody tr").clone().appendTo("#show_table tbody");
        $("#model73 tr:not(:first)").remove();//删除除了第一行的所有tr 
        $("#model73").hide();
      }else{
        $("#showbutton").show();
        $("#showParamButton").hide();
        $("#model81 tbody tr").clone().appendTo("#show_table tbody");
        $("#model73 tr:not(:first)").remove();//删除除了第一行的所有tr 
        $("#model73").hide();
      }
    }else if (model == "8") {
      $("#show_table tbody tr").remove();
      $("#model9 tbody tr").clone().appendTo("#show_table tbody");
      $("#showbutton").show();
    } else if (model == "9") {
      $("#show_table tbody tr").remove();
      $("#model4B tbody tr").clone().appendTo("#show_table tbody");
      $("#showbutton").show();
    }
  }
  function modelTwoAddSubstact21(){
    var model = $("#addSubtractTypeName").val();
    if(model=="0"){
      $("#show_table tbody tr").remove();
      $("#model21 tbody tr").clone().appendTo("#show_table tbody");
    }else{
      $("#show_table tbody tr").remove();
      $("#model22 tbody tr").clone().appendTo("#show_table tbody");
    }
  }
  function modelTwoAddSubstact22(){
    var model = $("#addSubtractTypeName").val();
    if(model=="0"){
      $("#show_table tbody tr").remove();
      $("#model21 tbody tr").clone().appendTo("#show_table tbody");
    }else{
      $("#show_table tbody tr").remove();
      $("#model22 tbody tr").clone().appendTo("#show_table tbody");
    }
  }
  function modelSevenAddSubstact71(){
    var model = $("#intervalTypeName71").val();
    if(model=="0"){
      $("#show_table tbody tr").remove();
      $("#model71 tbody tr").clone().appendTo("#show_table tbody");
      $("#model73 tr:not(:first)").remove();//删除除了第一行的所有tr 
      $("#model73").hide();
      $("#showParamButton").hide();
      $("#showbutton").show();
    }else{
      $("#show_table tbody tr").remove();
      $("#model72 tbody tr").clone().appendTo("#show_table tbody");
      $("#model73").show();
      $("#showParamButton").show();
      $("#showbutton").hide();
    }
  }
  function modelSevenAddSubstact72(){
    var model = $("#intervalTypeName72").val();
    if(model=="0"){
      $("#show_table tbody tr").remove();
      $("#model71 tbody tr").clone().appendTo("#show_table tbody");
      $("#model73 tr:not(:first)").remove();//删除除了第一行的所有tr 
      $("#model73").hide();
      $("#showParamButton").hide();
      $("#showbutton").show();
    }else{
      $("#show_table tbody tr").remove();
      $("#model72 tbody tr").clone().appendTo("#show_table tbody");
      $("#model73").show();
      $("#showParamButton").show();
      $("#showbutton").hide();
    }
  }
  function modelSevenAddSubstact81(){
    var model = $("#intervalTypeName81").val();
    if(model=="0"){
      $("#show_table tbody tr").remove();
      $("#model81 tbody tr").clone().appendTo("#show_table tbody");
      $("#model73 tr:not(:first)").remove();//删除除了第一行的所有tr 
      $("#model73").hide();
      $("#showParamButton").hide();
      $("#showbutton").show();
    }else{
      $("#show_table tbody tr").remove();
      $("#model82 tbody tr").clone().appendTo("#show_table tbody");
      $("#model73").show();
      $("#showParamButton").show();
      $("#showbutton").hide();
    }
  }
  function modelSevenAddSubstact82(){
    var model = $("#intervalTypeName82").val();
    if(model=="0"){
      $("#show_table tbody tr").remove();
      $("#model81 tbody tr").clone().appendTo("#show_table tbody");
      $("#model73 tr:not(:first)").remove();//删除除了第一行的所有tr 
      $("#model73").hide();
      $("#showParamButton").hide();
      $("#showbutton").show();
    }else{
      $("#show_table tbody tr").remove();
      $("#model82 tbody tr").clone().appendTo("#show_table tbody");
      $("#model73").show();
      $("#showParamButton").show();
      $("#showbutton").hide();
    }
  }
  function gernerator(){
    var model = $("#model").val();
    if(model=="0"){
      gerneratorOne();
    }else if(model=="1"){
      gerneratorTwo();
    }else if(model=="2"){
      gerneratorThree();
    }else if(model=="3"){
      gerneratorFour();
    }else if(model=="4"){
      gerneratorFive();
    }else if(model=="5"){
      gerneratorSix();
    }else if(model=="6"){
      gerneratorSeven();
    }else if(model=="7"){
      gerneratorEight();
    }else if(model=="8"){
      gerneratorNine();
    }else if(model=="9"){
      gerneratorTen();
    }
    
  }
  //动态添加参数区间
  var num2 =1;
  function addParamInterval(){
    var table = document.getElementById("model73");
    var pinum = $("#num2").val();
    if(pinum>0){
      num2 = Number(pinum) + Number(1);
    }
    num2 = table.rows.length;
    var tr ="";
    tr += "<tr>";
    tr += "<td class='w50 tc'>"+num2+"</td>";
    tr += "<td class='tc'><input style='width:60px' type='text' onblur='checkNum(this.value)' id=startParam"+num2+" name='pi.startParam'></td>";
    tr += "<td class='tc'><select onchange='checkNum()' name='pi.startRelation'><option value='0' ><</option><option value='1'><=</option></select></td>";
    tr += "<td class='tc'>参数值</td>";
    tr += "<td class='tc'><select onchange='checkNum()' name='pi.endRelation'><option value='0' ><</option><option value='1'><=</option></select></td>";
    tr += "<td class='tc'><input style='width:60px' type='text' onblur='checkNum(this.value)' id=endParam"+num2+" name='pi.endParam'></td>";
    tr += "<td class='tc'><input style='width:60px' onblur='checkNum(this.value)' type='text' id=score"+num2+" name='pi.score'></td>";
    tr += "<td></td>";
    tr += "<td><a href='javascript:void(0);' onclick='delTr(this)'>删除</a></td>";
    tr += "</tr>";
    $("#model73 tbody").append(tr);
    num2++;
  }
  
  $(function() {
    setTimeout("checkNum()", 100);
  });
  
  var scoreStr;//这个是检查为空的情况
  var scoreCount;//这个是检查起始值要小于结束值的情况
  var checkScore;//这个是要检查区间重复的情况
  function checkNum() {
    var table = document.getElementById("model73");
    scoreStr = '';
    scoreCount = 0;
    checkScore = 0;
    var j = 1;
    for (j; j < table.rows.length; j++) {
      var startParam = $(table.rows).eq(j).find("td").eq("1").find("input").val();
      var startParamRelation = $(table.rows).eq(j).find("td").eq("2").find("select").find("option:checked").text();
      var endParamRelation = $(table.rows).eq(j).find("td").eq("4").find("select").find("option:checked").text();
      var endParam = $(table.rows).eq(j).find("td").eq("5").find("input").val();
      var score = $(table.rows).eq(j).find("td").eq("6").find("input").val();
      if (startParam == '' || startParamRelation == '' || endParamRelation == '' || endParam == '' || score == '') {
        scoreCount ++;
        break;
      }
      var str = startParam + "," + startParamRelation + "," + endParamRelation + "," + endParam + "," + score + ",";
      var canshuzhi = $(table.rows).eq(j).find("td").eq("3").text();
      $(table.rows).eq(j).find("td").eq("7").text(startParam + startParamRelation + canshuzhi + endParamRelation + endParam + " 得分:" + score);
      scoreStr += str;
    }
    var scoreArr = new Array();
    scoreArr = scoreStr.split(",");
    for (var i = 0; i < scoreArr.length - 1; i++) {//长度减一是因为后面多了一个逗号
      if (i % 5 == 0) {//验证某个区间是否合格
          if (parseFloat(scoreArr[i + 3]) > parseFloat(scoreArr[i])) {
        } else if (scoreArr[i] == scoreArr[i + 3] && scoreArr[i+1] == scoreArr[i+2] && scoreArr[i+1] == '<=') {
          } else {
              layer.msg("起始值要小于等于结束值");
              checkScore ++;
              break;
          }
      }
    };
    labe : for (var h = 0; h < scoreArr.length -1; h++) {
      if (h % 5 == 0) {
        for (var k = h+1; k < scoreArr.length - 1; k++) {
          if (k % 5 == 0) {//验证区间是否包含别的区间
            if (parseFloat(scoreArr[h]) < parseFloat(scoreArr[k]) && parseFloat(scoreArr[h + 3]) < parseFloat(scoreArr[k])) {
            } else if (scoreArr[h] == scoreArr[k] && parseFloat(scoreArr[h]) == parseFloat(scoreArr[k + 3]) && scoreArr[k + 1] == scoreArr[k + 2] && scoreArr[k + 2] == '<=' && scoreArr[h + 1] == '<') {
            } else if (scoreArr[h] == scoreArr[k] && parseFloat(scoreArr[h + 3]) == parseFloat(scoreArr[k]) && scoreArr[h + 1] == scoreArr[h + 2] && scoreArr[h + 2] == '<=' && scoreArr[k + 1] == '<') {
            } else if (scoreArr[h] > scoreArr[k] && parseFloat(scoreArr[h]) == parseFloat(scoreArr[k + 3]) && scoreArr[h + 1] == scoreArr[k + 2] && scoreArr[h + 1] == '<') {
            } else if (scoreArr[h] > scoreArr[k] && parseFloat(scoreArr[h]) == parseFloat(scoreArr[k + 3]) && scoreArr[h + 1] != scoreArr[k + 2]) {
            } else if ((parseFloat(scoreArr[h]) < parseFloat(scoreArr[k]) && parseFloat(scoreArr[h + 3]) == parseFloat(scoreArr[k]) && scoreArr[h + 2] == scoreArr[k + 1] && scoreArr[k + 1] == '<' ) ) {
            } else if ((parseFloat(scoreArr[h]) < parseFloat(scoreArr[k]) && parseFloat(scoreArr[h + 3]) == parseFloat(scoreArr[k]) && scoreArr[h + 2] != scoreArr[k + 1]) ) {
            } else if (parseFloat(scoreArr[h]) > parseFloat(scoreArr[k + 3]) && parseFloat(scoreArr[h + 3]) >= parseFloat(scoreArr[k + 3])) {
            } else if (parseFloat(scoreArr[h]) > parseFloat(scoreArr[k + 3]) && parseFloat(scoreArr[h + 3]) == parseFloat(scoreArr[k + 3]) && scoreArr[h + 1] == scoreArr[k + 2] && scoreArr[k + 2] == '<') {
            } else if (parseFloat(scoreArr[h]) > parseFloat(scoreArr[k + 3]) && parseFloat(scoreArr[h + 3]) == parseFloat(scoreArr[k + 3]) && scoreArr[h + 1] != scoreArr[k + 2]) {
            } else {
              checkScore ++;
              layer.msg("区间重复,请重新录入"); 
              //console.dir(checkScore);
              break labe;
            };
          }
        };
      }
    };
  }
  function delTr(obj){
    var tr=obj.parentNode.parentNode;
        tr.parentNode.removeChild(tr);
    //$(obj).parent.remove();//删除当前行   
    var num = $("#model73 tbody tr").length;
    var trs = $("#model73 tbody tr");
    //console.dir(trs.find("td:eq(0)"));
    for (i = 0; i < num; i++) {
      trs.find("td:eq(0)").each(function(i) {
        $(this).text(i + 1);
      });
    }  
    num2--;
  }
  function gerneratorOne(){
    var judgeContent = $("#judgeContent").val();
    var standardScore = $("#standardScore").val();
    //var judgeNumber = $("#judgeNumber").val();
    var str = judgeContent  + " "+"是"+standardScore+"分 "+"否0分";
    $("#easyUnderstandContent1").text(str);
  }
  function gerneratorTwo(){
    var reviewParam = $("#reviewParam").val();
    var addSubtractTypeName = $("#addSubtractTypeName").val();
    var reviewStandScore = $("#reviewStandScore").val();
    var maxScore = $("#maxScore").val();
    var minScore = $("#minScore").val();
    var unitScore = $("#unitScore").val();
    var unit = $("#unit").val();
    
    if(addSubtractTypeName=="0"){
      var str = " 加分类型：" + reviewParam + " 最低分为" +minScore +"分" + " 每" + unit + "加" + unitScore+"分"+" 最高分为"+maxScore+"分";
      $("#easyUnderstandContent21").text(str);
    }else{
      var str = " 减分类型：" + reviewParam + "最高分为"+maxScore+"分" +" 每" + unit + "减"+unitScore+"分"+" 最低分值为"+minScore+"分";
      $("#easyUnderstandContent21").text(str);
    }
    
  }
  function gerneratorThree(){
    var reviewParam = $("#reviewParam").val();
    var unit = $("#unit").val();
    var score = $("#score").val();
    var standScores = $("#standScores").val();
    var maxScore = $("#maxScore").val();
    var minScore = $("#minScore").val();
    var relation = $("#relation").val();
    var unitScore = $("#unitScore").val();
    var isHave = $("#isHave").val();
    var type ="";
    var addSubtractTypeName = $("#addSubtractTypeName").val();
    
    if (isHave == "1") {
      if(addSubtractTypeName=="0"){
        var str = "加分实例:以"+reviewParam+"最高值为基准排序递减，第一名得"+ minScore + "分,其余依次递增" + unitScore + "分,最高分为" + maxScore + "分";
        $("#easyUnderstandContent3").text(str);
        return;
      } else {
        var str = "减分实例:以"+reviewParam+"最高值为基准排序递减，第一名得"+ maxScore + "分,其余依次递减" + unitScore + "分,最低分为" + minScore + "分";
        $("#easyUnderstandContent3").text(str);
        return;
      }
    }
    
    if(addSubtractTypeName=="0"){
      if (relation == "0") {
        var str = "加分实例:以"+reviewParam+"最高值为基准排序递减，大于等于"+ standScores + unit + "得最低分" + minScore +"分,其余依次递增" + unitScore + "分,最高分为" + maxScore + "分";
      } else {
        var str = "加分实例:以"+reviewParam+"最高值为基准排序递减，小于等于"+ standScores + unit + "得最高分" + maxScore +"分,其余从最低分依次递增" + unitScore + "分,最低分为" + minScore + "分";
      }
      $("#easyUnderstandContent3").text(str);
    }else{
      if (relation == "0") {
        var str = "减分实例:以"+reviewParam+"最高值为基准排序递减，大于等于"+ standScores + unit + "得最高分" + maxScore +"分,其余依次递减" + unitScore + "分,最低分为" + minScore + "分";
      } else {
        var str = "减分实例:以"+reviewParam+"最高值为基准排序递减，小于等于"+ standScores + unit + "得最低分" + minScore +"分,其余从最高分依次递减" + unitScore + "分,最高分为" + maxScore + "分";
      }
      $("#easyUnderstandContent3").text(str);
    }
  }
  function gerneratorFour(){
    var reviewParam = $("#reviewParam").val();
    var unit = $("#unit").val();
    var score = $("#score").val();
    var standScores = $("#standScores").val();
    var maxScore = $("#maxScore").val();
    var minScore = $("#minScore").val();
    var relation = $("#relation").val();
    var unitScore = $("#unitScore").val();
    var isHave = $("#isHave").val();
    var type ="";
    var addSubtractTypeName = $("#addSubtractTypeName").val();
    if (isHave == "1") {
      if(addSubtractTypeName=="0"){
        var str = "加分实例:以"+reviewParam+"以最低值为基准值排序递增，第一名得"+ minScore + "分,其余依次递增" + unitScore + "分,最高分为" + maxScore + "分";
        $("#easyUnderstandContent4").text(str);
        return;
      } else {
        var str = "减分实例:以"+reviewParam+"以最低值为基准值排序递增，第一名得"+ maxScore + "分,其余依次递减" + unitScore + "分,最低分为" + minScore + "分";
        $("#easyUnderstandContent4").text(str);
        return;
      }
    }
    if(addSubtractTypeName=="0"){
      if (relation == "0") {
        var str = "加分实例:以"+reviewParam+"最低值为基准排序递增，大于等于"+ standScores + unit + "得最高分" + maxScore +"分,其余从最低分依次递增" + unitScore + "分,最低分为" + minScore + "分";
      } else {
        var str = "加分实例:以"+reviewParam+"最低值为基准排序递增，小于等于"+ standScores + unit + "得最低分" + minScore +"分,其余依次递增" + unitScore + "分,最高分为" + maxScore + "分";
      }
      $("#easyUnderstandContent4").text(str);
    }else{
      if (relation == "0") {
        var str = "减分实例:以"+reviewParam+"最低值为基准排序递增，大于等于"+ standScores + unit + "得最低分" + minScore +"分,其余从最高分依次递减" + unitScore + "分,最高分为" + maxScore + "分";
      } else {
        var str = "减分实例:以"+reviewParam+"最低值为基准排序递增，小于等于"+ standScores + unit + "得最高分" + maxScore +"分,其余从最高分依次递减" + unitScore + "分,最低分为" + minScore + "分";
      }
      $("#easyUnderstandContent4").text(str);
    }
  }
  function gerneratorFive(){
    var reviewParam = $("#reviewParam").val();
    var standardScore = $("#standardScore").val();
    var unit = $("#unit").val();
    var str = "以" + reviewParam +"最高为基准,得分=("+reviewParam+"/基准值)*"+standardScore;
    $("#easyUnderstandContent5").text(str);
  }
  function gerneratorSix(){
    var reviewParam = $("#reviewParam").val();
    var standardScore = $("#standardScore").val();
    var unit = $("#unit").val();
    var str = "以" + reviewParam +"最低为基准,得分=(基准值/"+reviewParam+")*"+standardScore;
    $("#easyUnderstandContent6").text(str);
  }
  function gerneratorSeven(){
    var reviewParam  = $("#reviewParam").val();
    var unit   = $("#unit").val();
    var reviewStandScore   = $("#reviewStandScore").val();
    var intervalNumber    = $("#intervalNumber").val();
    var score   = $("#score").val();
    var deadlineNumber   = $("#deadlineNumber").val();
    var maxScore   = $("#maxScore").val();
    var minScore  = $("#minScore").val();
    var type ="";
    var addSubtractTypeName = $("#addSubtractTypeName7").val();
    if(addSubtractTypeName=="0"){
      var str =  "加分实例:" + reviewParam +",低于" +reviewStandScore+unit+"为"+ minScore+"分,每增加"+intervalNumber+unit+"加"+score+ "分, 最高分为"+maxScore+"分, 高于"+deadlineNumber+unit+"得"+maxScore+"分";
      //加分实例：手机按键正常次数，低于4%以下为0分，每增加4%加4分，最高分为4分，高于4%，得4分
      $("#easyUnderstandContent7").text(str);
    }else{
      var str ="减分实例:" +  reviewParam +",低于" +reviewStandScore+unit+"为"+maxScore+"分,每增加"+intervalNumber+unit+"减"+score+ " 分,最低分为"+minScore+"分,高于"+deadlineNumber+unit+ "得"+minScore+"分";
      // 减分实例：百公里耗油，低于4%以下为4分，每增加4%减4分，最低分为4分，高于4%，得0分
      $("#easyUnderstandContent7").text(str);
    }
  }
  function gerneratorEight(){
    var reviewParam  = $("#reviewParam").val();
    var unit   = $("#unit").val();
    var reviewStandScore   = $("#reviewStandScore").val();
    var intervalNumber    = $("#intervalNumber").val();
    var score   = $("#score").val();
    var deadlineNumber   = $("#deadlineNumber").val();
    var maxScore   = $("#maxScore").val();
    var minScore  = $("#minScore").val();
    var type ="";
    var addSubtractTypeName = $("#addSubtractTypeName8").val();
    if(addSubtractTypeName=="0"){
      var str =  "加分实例:" + reviewParam +",高于" +reviewStandScore+unit+"为"+minScore+"分,每减少"+intervalNumber+unit+"加"+score+ "分, 最高分为"+maxScore+"分,低于"+deadlineNumber+unit+ "得"+maxScore+"分";
       // 加分实例：汽车尾气排放量，高于2%以上为0分，每减少1%加3分，最高分为4分，低于3%，得4分 
      $("#easyUnderstandContent8").text(str);
    }else{
      var str ="减分实例:" +  reviewParam +",高于" +reviewStandScore+unit+"为"+maxScore+"分,每减少"+intervalNumber+unit+"减"+score+ " 最低分为"+minScore+"分, 低于"+deadlineNumber+unit+ "得"+minScore+"分";
      //减分实例：汽车尾气排放量，高于2%以上为4分，每减少1%减3分，最低分为0分，低于3%，得0分 
      $("#easyUnderstandContent8").text(str);
    }
  }
  
  function gerneratorNine(){
    var str = "";
    for (var i = 1; i<$("#show_table").get(0).rows.length -3 ; i++) {
      var name = $("#show_table").find("tr").eq(i).find("td").eq("1").find("input").val();
      var score = $("#show_table").find("tr").eq(i).find("td").eq("3").find("input").val();
      if (name == "" || score == "") {
      } else {
        if (name.trim() != "" && score.trim() != "") {
          str = str + name + "等于" +score + "分  ";
        }
      }
    }
    $("#easyUnderstandContent9").text(str);
  }
  
  function gerneratorTen(){
    var reviewParam = $("#reviewParam").val();
    var unit = $("#unit").val();
    var score = $("#score").val();
    var maxScore = $("#maxScore").val();
    var minScore = $("#minScore").val();
    var type ="";
    var addSubtractTypeName = $("#addSubtractTypeName").val();
    if(addSubtractTypeName=="0"){
      var str = "加分实例:以"+reviewParam+",第一名得"+minScore+"分,依次递增"+score+"分,最高分为"+maxScore+"分";
      $("#easyUnderstandContent4").text(str);
    }else{
      var str = "减分实例:以"+reviewParam+",第一名得"+maxScore+"分,依次递减"+score+"分,最低分为"+minScore+"分";
      $("#easyUnderstandContent4").text(str);
    }
  }
  
  
  function associate(){
  	if(!checkSpace("评审指标名称",$("#name").val())){
			return false;
		}
		if(!checkSpace("评审指标内容及规则说明",$("#reviewContent").val())){
			return false;
		}
    var text = $("#show_table").find("tr").eq("1").find("td:last").text();
    var aa = $("#show_table").find("tr").eq("1").find("td:last").prev().text();
		if(aa == null || aa == ''){
			layer.msg("该项内容为必填项");
			return;
		}
    if (text == '删除') {
      var result = "";
      var standardScore = 0;
      for (var i = 1; i<$("#show_table").get(0).rows.length -3 ; i++) {
        var name = $("#show_table").find("tr").eq(i).find("td").eq("1").find("input").val();
        var score = $("#show_table").find("tr").eq(i).find("td").eq("3").find("input").val();
        if (name == "" || score == "") {
          layer.msg("请填写选择项名称");
          return;
        }
        
        if (name.trim() == "" || score.trim() == "") {
          layer.msg("请填写选择项名称");
          return;
        }
        
        if (score > standardScore) {
          standardScore = score;
        }
        result = result + name.replace(/\|/g, "").replace(/-/g, "") + "-" +score.replace(/\|/g, "").replace(/-/g, "") + "|";
      }
      $("#standardScore").val(standardScore);
      $("#judgeContent").val(result);
    }
    if (scoreCount > 0) {
      layer.msg("参数没有填写完整,请录入");
      return;
    }
    if (checkScore > 0) {
      layer.msg("区间不成立,请重新录入");
      return;
    }
      var standScore = $("#standardScore").val();
      var maxScore = $("#maxScore").val();
      var id = $("#id").val();
      var isChecked = $("#check").val();
    var s = validteModel().form();
    //console.dir(s);
    if(s){
      $.ajax({   
              type: "get",  
              url: "${pageContext.request.contextPath}/adIntelligentScore/checkScore.do?standScore="+standScore+"&id="+id+"&maxScore="+maxScore+"&projectId=${projectId}"+"&packageId=${packageId}" + "&checked="+isChecked,        
              dataType:'json',
              success:function(result){
                    if (result == 0){
               layer.msg("评分项已超过100分,请检查",{offset: ['150px']});       
                    }  else if (result == 2) {
                       layer.msg("每个包必须要有一个评审计算价格得分的唯一标识,有且只能为一个",{offset: ['150px']});    
                    }  else {
                      $("#formID").attr('action','${pageContext.request.contextPath}/adIntelligentScore/operatorScoreModel.do').submit();
                    }
              },
              error: function(result){
                  layer.msg("添加失败",{offset: ['150px']});
              }
          });   
      
    }else{
      return;
    }
  }
  
  function pageOnLoad(){
    var model = $("#sm").val();
    $("#showParamButton").hide();
    if('${addStatus}' !=1){
      $("#model").val(model);
    }
    //console.dir(model==undefined);
    if(model !=undefined && model==""){
      $("#show_table tbody tr").remove();
    }else if(model=="0"){
      $("#show_table tbody tr").remove();
      if('${addStatus}' !=1){
        $("#model1 tbody tr").clone().appendTo("#show_table tbody");
      }
      $("#showbutton").show();
      gernerator();
    }else if(model=="1"){
      var addSubtractTypeName = $("#sm2").val();
      $("#addSubtractTypeName").val(addSubtractTypeName);
      $("#show_table tbody tr").remove();
      if('${addStatus}' !=1){
        if(addSubtractTypeName!=undefined ){
          $("#model21 tbody tr").clone().appendTo("#show_table tbody");
        }else if(addSubtractTypeName=="1"){
          $("#model22 tbody tr").clone().appendTo("#show_table tbody");
        }
      }
      $("#showbutton").show();
      gernerator();
    }else if(model=="2"){
      $("#show_table tbody tr").remove();
      if('${addStatus}' !=1){
        $("#model3 tbody tr").clone().appendTo("#show_table tbody");
      }
      $("#showbutton").show();
      gernerator();
    }else if(model=="3"){
      $("#show_table tbody tr").remove();
      if('${addStatus}' !=1){
        $("#model4 tbody tr").clone().appendTo("#show_table tbody");
      }
      $("#showbutton").show();
      gernerator();
    }else if(model=="4"){
      $("#show_table tbody tr").remove();
      if('${addStatus}' !=1){
        $("#model5 tbody tr").clone().appendTo("#show_table tbody");
      }
      $("#showbutton").show();
      gernerator();
    }else if(model=="5"){
        $("#biaoshi").removeClass("hide");
      $("#show_table tbody tr").remove();
      if('${addStatus}' !=1){
        $("#model6 tbody tr").clone().appendTo("#show_table tbody");
      } 
      $("#showbutton").show();
      gernerator();
    }else if(model=="6"){
      $("#show_table tbody tr").remove();
      //$("#model7 tbody tr").clone().appendTo("#show_table tbody");
      var intervalTypeName71 = $("#sm7").val();
      if(intervalTypeName71!=undefined && intervalTypeName71=="1"){
        $("#showParamButton").show();
        $("#showbutton").hide();
        if('${addStatus}' !=1){
          $("#model72 tbody tr").clone().appendTo("#show_table tbody");
        }
        $("#model73").show();
        $("#model73").append("${scoreStr}");
        gernerator();
      }else if(intervalTypeName71=="0"){
        $("#showbutton").show();
        $("#showParamButton").hide();
        if('${addStatus}' !=1){
          $("#model71 tbody tr").clone().appendTo("#show_table tbody");
        } 
        $("#model73 tr:not(:first)").remove();//删除除了第一行的所有tr 
        $("#model73").hide();
        gernerator();
      }else{
        $("#showbutton").show();
        $("#showParamButton").hide();
        if('${addStatus}' !=1){
          $("#model71 tbody tr").clone().appendTo("#show_table tbody");
        }
        $("#model73 tr:not(:first)").remove();//删除除了第一行的所有tr 
        $("#model73").hide();
        gernerator();
      }
    }else if(model=="7"){
      /* $("#show_table tbody tr").remove();
      $("#model8 tbody tr").clone().appendTo("#show_table tbody");
      //$("#showbutton").show();
      $("#showParamButton").show(); */
      $("#show_table tbody tr").remove();
      var intervalTypeName71 = $("#sm7").val();
      if(intervalTypeName71!=undefined && intervalTypeName71=="1"){
        $("#showParamButton").show();
        $("#showbutton").hide();
        if('${addStatus}' !=1){
          $("#model82 tbody tr").clone().appendTo("#show_table tbody");
        }
        $("#model73").append("${scoreStr}");
        $("#model73").show();
        gernerator();
      }else if(intervalTypeName71=="0"){
        $("#showbutton").show();
        $("#showParamButton").hide();
        if('${addStatus}' !=1){
          $("#model81 tbody tr").clone().appendTo("#show_table tbody");
        }
        $("#model73 tr:not(:first)").remove();//删除除了第一行的所有tr 
        $("#model73").hide();
        gernerator();
      }else{
        $("#showbutton").show();
        $("#showParamButton").hide();
        if('${addStatus}' !=1){
          $("#model81 tbody tr").clone().appendTo("#show_table tbody");
        }
        $("#model73 tr:not(:first)").remove();//删除除了第一行的所有tr 
        $("#model73").hide();
        gernerator();
      }
    }else if (model == "8") {
      $("#show_table tbody tr").remove();
      if('${addStatus}' !=1){
        $("#model9 tbody tr").clone().appendTo("#show_table tbody");
      }
      $("#showbutton").show();
      gernerator();
    } else if (model == "9") {
      $("#show_table tbody tr").remove();
      if('${addStatus}' !=1){
        $("#model4B tbody tr").clone().appendTo("#show_table tbody");
      }
      $("#showbutton").show();
      gernerator();
    }
  }
</script>  
<script type="text/javascript">
  //validate
  function validteModel(){
    return $("#formID").validate({
      ignore: [],
      focusInvalid : false, //当为false时，验证无效时，没有焦点响应  
      onkeyup : false,
      rules : {
        standardScore : {
          required : true,
          number:true
        },
        judgeContent : {
          required : true
        },
        judgeNumber :{
          required : true,
          number:true
        },
        /* easyUnderstandContent : {
          required : true
        }, */
        reviewParam : {
          required : true
        },
        reviewStandScore : {
          required : true,
          number:true
        },
        maxScore : {
          required : true,
          number:true
        },
        unitScore : {
          required : true,
          number:true
        },
        
        minScore : {
          required : true,
          number:true
        },
        intervalNumber : {
          required : true,
          number:true
        },
        "pi.startParam" : {
          required : true,
          number:true
        },
          "pi.endParam" : {
          required : true,
          number:true
        }, 
        "pi.score" : {
          required : true,
          number:true
        },
        reviewContent : {
          required : true
        },
        name : {
          required : true
        },
        unit : {required : true}
      },
      messages : {
        standardScore : {
          required : "该项满分值为必填项",
          number:"必须为数字"
        },
        judgeContent : {
          required : "该项内容为必填项"
        },
        judgeNumber :{
          required : "该项内容为必填项",
          number:"必须为数字"
        },
        /* easyUnderstandContent : {
          required : "请点击生成白话文"
        }, */
        reviewParam : {
          required : "该项内容为必填项"
        },
        reviewStandScore : {
          required : "该项内容为必填项",
          number:"必须为数字"
        },
        maxScore : {
          required : "该项内容为必填项",
          number:"必须为数字"
        },
        unitScore : {
          required : "该项内容为必填项",
          number:"必须为数字"
        },
        minScore : {
          required : "该项内容为必填项",
          number:"必须为数字"
        },
        intervalNumber : {
          required : "该项内容为必填项",
          number:"必须为数字"
        },
          "pi.startParam" : {
          required : "必填",
          number:"数字项"
        },
        "pi.endParam" : {
          required : "必填",
          number:"数字项"
        },  
        "pi.score" : {
          required : "必填",
          number:"数字项"
        },
        reviewContent : {
          required : "必填"
        },
        name : {
          required : "必填"
        },
        unit : {required : "必填"}
      },
      showErrors: function(errorMap, errorList) {
             $.each(this.successList, function(index, value) {
               return $(value).popover("hide");
             });
               return $.each(errorList, function(index, value) {
                var _popover;
                _popover = $(value.element).popover({
                    trigger: "manual",
                    placement: "top",
                    content: value.message,
                    template: "<div class=\"popover\"><div class=\"arrow\"></div> <div class=\"popover-inner\"><div class=\"popover-content\"><p></p></div></div></div>"
               });
             _popover.data("bs.popover").options.content = value.message;
             return _popover.popover("show");
           });
         }
    }); 
  }
</script>
    </head>
<body onload="pageOnLoad();">
  <input type="hidden" id="sm" value="${scoreModel.typeName }">
  <input type="hidden" id="sm2" value="${scoreModel.addSubtractTypeName }">
  <input type="hidden" id="sm7" value="${scoreModel.intervalTypeName }">
  <div>
    <form action="" method="post"  id="formID">
       <ul class="list-unstyled">
                  <li class="col-sm-6 col-md-6 col-lg-6 col-xs-6 pl15">
                    <div class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>评审指标名称：</div>
                  <div class="col-md-12 col-sm-12 col-xs-12 p0 input-append input_group">
                     <input name="name" id="name" value="${scoreModel.name}" type="text">
                  </div>
                  </li>
                   <li id ="biaoshi" class="col-sm-6 col-md-6 col-lg-6 col-xs-6 pl15 hide">
                    <div class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>是否标识(评审计算价格得分的唯一标识) ：</div>
                  <div class="col-md-12 col-sm-12 col-xs-12 p0 input-append input_group">
                       <select id="check" name="ischeck">
                  <option value="">请选择</option>
                  <option value="1">是</option>
                  <option value="0">否</option>
              </select>
                  </div>
                  </li>
                  <li class="col-sm-6 col-md-6 col-lg-6 col-xs-6">
                     <div class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>选择模型 ：</div>
                   <div class="col-md-12 col-sm-12 col-xs-12 p0 select_common">
                     <select id="model" name="typeName" onchange="choseModel();">
              <option value="">请选择</option>
              <option value="0">模型一A（是否判断）</option>
              <option value="8">模型一B（按项匹配分值）</option>
              <option value="1">模型二（按项加减分）</option>
              <option value="2">模型三（以评审数额最高分值为基准排序递减）</option>
              <option value="3">模型四A（以评审数额最低值为基准排序递增）</option>
              <option value="9">模型四B（按照排名递减/递增分值）</option>
              <option value="4">模型五（以评审数额最高值为基准按比例计算）</option>
              <option value="5">模型六（以评审数额最低为基准按比例计算）</option>
              <option value="6">模型七（以评审数额最低区间为基准递增排序）</option>
              <option value="7">模型八（以评审数额最高区间为基准递减排序）</option>
            </select>
                  </div>
                  </li>
                  <li class="col-sm-12 col-md-12 col-lg-12 col-xs-12">
                    <div class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>评审指标内容及规则说明</div>
                  <div class="col-md-12 col-sm-12 col-xs-12 p0">
                    <textarea  class="col-md-12 col-sm-12 col-xs-12 h80 mb10" name="reviewContent" id="reviewContent" >${scoreModel.reviewContent}</textarea>
                </div>
                  </li>
              </ul>
      <input id="packageId" name="packageId" type="hidden" value="${packageId }">
      <input id="projectId" name="projectId" type="hidden" value="${projectId }">
      <input id="markTermId" name="markTermId" type="hidden" value="${markTermId }">
      <c:if test="${addStatus != 1 }">
        <input id="id" type="hidden" name="id" value="${scoreModel.id}">
      </c:if>
      <input type="hidden" id="num2" value="${fn:length(scoreModel.paramIntervalList)}">
      <table class="table table-striped table-bordered table-hover mt20"  id="show_table">
        <tbody>
        </tbody>
      </table>
      <table id="model73" style="display: none;" class="table table-striped table-bordered table-hover mt20 w499">
        <thead>
          <tr id="paramIntervalTr">
            <!-- <th class="w30"><input type="checkbox">
            </th> -->
            <th class="w50">序号</th>
            <th class="">起始值</th>
            <th class="">参数和<br/>起始值关系</th>
            <th class="">评审参数<br/>对应数值</th>
            <th class="">参数和<br/>结束值关系</th>
            <th class="">结束值</th>
            <th class="">得分</th>
            <th class="">解释</th>
            <th class="">操作</th>
          </tr>
        </thead>
        
        <tbody>
          <c:forEach items="${scoreModel.paramIntervalList }" var="pi" varStatus="vs">
              <tr>
                
              </tr>
          </c:forEach>
        </tbody>
      </table>
    </form>
  </div>
  <div class="col-md-12" id="showbutton">
    <div class="mt40 tc mb50">
      <input type="button" class="btn btn-windows save" onclick="associate();" value="保存">
      <button class="btn btn-windows back" onclick="history.go(-1)" type="button">返回</button>
    </div>
  </div>
  <div class="col-md-12" id="showParamButton" style="display: none;">
    <div class="mt40 tc mb50">
      <input type="button" class="btn  padding-right-20 btn_back margin-5" onclick="addParamInterval();" value="添加参数区间"> 
      <input type="button" class="btn btn-windows save" onclick="associate();" value="保存">
      <button class="btn btn-windows back" onclick="history.go(-1)" type="button">返回</button>
    </div>
  </div>
  <!-- 八大模型 -->
  <table id="model1" class="hand table hide">
    <tbody>
        <tr>
        <td class="tc w180"><span class="star_red">*</span>判断内容</td>
        <td><textarea class="col-md-12 col-sm-12 col-xs-12 h80" onkeyup="gernerator();" name="judgeContent" id="judgeContent">${scoreModel.judgeContent }</textarea></td>
        <td><span class="blue">该项内容为判断的唯一依据</span></td>
      </tr>
      <tr>
        <td class=" tc"><span class="star_red">*</span>标准分值</td>
        <td><input name="standardScore" type="text" onkeyup="gernerator();" id="standardScore" value="${scoreModel.standardScore }"></td>
        <td><span class="blue">该项的满分值为多少</span></td>
      </tr>
      
      <tr>
        <td class=" tc">翻译成白话文内容</td>
        <td colspan="2" id="easyUnderstandContent1"></td>
      </tr>
      <tr>
        <td class=" tc">当前模型标准解释</td>
        <td colspan="2"><span class="blue">是否判断。采购文件明确满足或不满足项的指标临界值或有无的项目要求。评审系统自动识别满足或不满足项，生成通过或否决的结果。(如：必要设备，员工人数，关键技术指标参数等)</span></td>
      </tr>
    </tbody>
  </table>
  <table id="model21" class="hide">
    <tbody>
      <tr>
        <td class="w180 tc"><span class="star_red">*</span>评审参数</td>
        <td><input name="reviewParam" id="reviewParam" onkeyup="gernerator();" value="${scoreModel.reviewParam }" ></td>
        <td><span class="blue">
          该参数代表需要供应商录入的参数<br/>
          减分例子：近三年企业在投标过程中违规次数,1项减0.5分,最高分为2分,最低得0分,其中</span><span class="red">近三年企业在投标过程中违规次数</span><span class="blue">就为评审参数<br/>
          加分例子：近五年内获得过省以上工商部门颁发知名品牌商标的数量,1项得0.5分,最多得2分,其中近<span class="red">五年内获得过省以上工商部门颁发知名品牌商标的数量</span>就为评审参数</span>
        </td>
      </tr>
      <tr>
        <td class="w180 tc"><span class="star_red">*</span>加减分类型<input type="hidden" name="judgeModel" value="2" /></td>
        <td>
          <select name="addSubtractTypeName" id="addSubtractTypeName">
            <option value="0" <c:if test="${scoreModel.addSubtractTypeName == 0}">selected="selected"</c:if> >加分</option>
            <option value="1" <c:if test="${scoreModel.addSubtractTypeName == 1}">selected="selected"</c:if> >减分</option></select>
        </td>
        
        <td><span class="blue">该项加分类型或减分类型</span></td>
      </tr>
      
      <tr>
        <td class="w180 tc"><span class="star_red">*</span>最高分</td>
        <td><input name="maxScore" onkeyup="gernerator();" id="maxScore" value="${scoreModel.maxScore }">
        </td>
        <td><span class="blue">最高分为多少分,[加分]类型时起始分为[最低分],最高分为此分数,[减分]类型此分数为减分基准分,依次递减</span></td>
      </tr>
      <tr>
        <td class="w180 tc"><span class="star_red">*</span>最低分</td>
        <td><input name="minScore" id="minScore" onkeyup="gernerator();"  value="${scoreModel.minScore }"></td>
        <td><span class="blue">最低分为多少分,通常为0分,[加分]类型是此分数为起始分,[减分]类型时此分数为最低得分</span></td>
      </tr>
      <tr>
        <td class="w180 tc"><span class="star_red">*</span>每单位分值</td>
        <td><input name="unitScore" onkeyup="gernerator();" id="unitScore" value="${scoreModel.unitScore }">
        </td>
        <td><span class="blue">该项为每单位的对应的值,加分每单位加多少分,减分每单位减多少分</span></td>
      </tr>
      <tr>
        <td class="w180 tc"><span class="star_red">*</span>单位</td>
        <td><input name="unit" id="unit" value="${scoreModel.unit }"></td>
        <td><span class="blue">该项为评审参数的单位</span></td>
      </tr>
      <tr>
        <td class="w180 tc">翻译成白话文内容</td>
        <td colspan="2" id="easyUnderstandContent21"></td>
      </tr>
      <tr>
        <td class="w180 tc">当前模型标准解释</td>
        <td colspan="2"><span class="blue">按项加减分。采购文件明确标准分值，加减分项，加减分值和最高最低分值限制。按照加，减分项的项目名称，系统自动识别计数，并按加减分规则计算得分。(如：技术偏离表中任何一项，正偏离，负偏离等)</span></td>
      </tr>
    </tbody>
  </table>
  
  <table id="model3" class="w499 hide">
    <tbody>
      <tr>
        <td class="w180 tc"><span class="star_red">*</span>评审参数</td>
        <td><input name="reviewParam" id="reviewParam" value="${scoreModel.reviewParam }"></td>
        <td><span class="blue">
          该参数代表需要录入供应商的参数名称。<br/>
          减分例子：上年度缴纳社保总金额由大至小排序评分,第一名得1分,其余依次递减0.15分,最低分为0分,其中</span><span class="red">上年度缴纳社保总金额</span><span class="blue">就为评审参数<br/>
          加分例子：上年度消费管理局罚款金额大小排序评分,第一名得0分,其余依次递增0.15分,最高分为1分,其中</span><span class="red">上年度消费管理局罚款金额</span><span class="blue">就为评审参数</span>
        </td>
      </tr>
      <tr>
        <td class="w180 tc"><span class="star_red">*</span>加减分类型</td>
        <td>
          <select name="addSubtractTypeName" id="addSubtractTypeName" onchange="judgeRelationScore(this.options[this.options.selectedIndex].value)">
            <option value="0" <c:if test="${scoreModel.addSubtractTypeName == 0}">selected="selected"</c:if> >加分</option>
            <option value="1" <c:if test="${scoreModel.addSubtractTypeName == 1}">selected="selected"</c:if> >减分</option>
          </select>
        </td>
        <td><span class="blue">以最高值为基准值排序递减,是加分还是减分</span></td>
      </tr>
       <tr>
          <td class="w180 tc"><span class="star_red">*</span>是否有基准数额</td>
          <td>
            <select name="isHave" id="isHave" onchange="judge(this.options[this.options.selectedIndex].value)">
              <option value="0" <c:if test="${scoreModel.isHave == 0}"> selected="selected"</c:if>>是</option>
              <option value="1" <c:if test="${scoreModel.isHave == 1}"> selected="selected"</c:if>>否</option>
            </select>
          </td>
          <td><span class="blue">是否有基准数额</span></td>
        </tr>
        <tr class="show">
           <td class="w180 tc"><span class="star_red">*</span>基准数额</td>
           <td><input name="standScores" id="standScores" value="${scoreModel.standScores }"  /></td>
           <td><span class="blue">评审数额低于（等于）[基准数额]时，[加分]类型得[最高分]，[减分]类型得[最低分],其他按照排序得分</span></td>
        </tr>
        <tr class="show">
          <td class="w180 tc"><span class="star_red">*</span>与基准数额关系</td>
          <td>
              <select name="relation" id="relation" onchange="judgeRelationScore1(this.options[this.options.selectedIndex].value)">
                     <option <c:if test="${scoreModel.relation == 0}"> selected="selected" </c:if> value="0">大于等于</option>
                     <option <c:if test="${scoreModel.relation == 1}"> selected="selected" </c:if> value="1">小于等于</option>
               </select>
          </td>
          <td><span class="blue">与基准数额关系,大于等于还是小于等于</span></td>
        </tr>
        <tr class="show">
          <td class="w180 tc"><span class="star_red">*</span>关系分数</td>
          <td>
              <select name="relationScore" id="relationScore" disabled>
                <option value="0" <c:if test="${scoreModel.relationScore == 0}"> selected="selected"</c:if> >最高分</option>
                <option value="1" <c:if test="${scoreModel.relationScore == 1}"> selected="selected"</c:if> >最低分</option>
               </select>
          </td>
          <td><span class="blue">基准数额为限制,最高分还是最低分</span></td>
        </tr>
      <tr>
        <td class="w180 tc"><span class="star_red">*</span>最高分</td>
        <td><input name="maxScore" id="maxScore" onkeyup="gernerator();" value="${scoreModel.maxScore }"></td>
        <td><span class="blue">最高分为多少分,[加分]类型时起始分为[最低分],最高分为此分数,[减分]类型此分数为减分基准分,依次递减</span></td>
      </tr>
      <tr>
        <td class="w180 tc"><span class="star_red">*</span>最低分</td>
        <td><input name="minScore" id="minScore" onkeyup="gernerator();" value="${scoreModel.minScore }"></td>
        <td><span class="blue">最低分为多少分,通常为0分,[加分]类型是此分数为起始分,[减分]类型时此分数为最低得分</span></td>
      </tr>
      <tr>
        <td class="w180 tc"><span class="star_red">*</span>分差</td>
        <td><input name="unitScore" onkeyup="gernerator();" id="unitScore" value="${scoreModel.unitScore }"></td>
        <td><span class="blue">依次排序递减/递增分值</span></td>
      </tr>
      <tr>
        <td class="w180 tc"><span class="star_red">*</span>单位</td>
        <td><input name="unit" id="unit" value="${scoreModel.unit }"></td>
        <td><span class="blue">该项目内容为评审参数的单位</span></td>
      </tr>
      <tr>
        <td class="w180 tc">翻译成白话文内容</td>
        <td colspan="2" id="easyUnderstandContent3"></td>
      </tr>
      <tr>
        <td class="w180 tc">当前模型标准解释</td>
        <td colspan="2"><span class="blue">以评审数额最高值为基准排序递减。采购文件明确标准分值，排序分差和最高最低分值限制。评审系统按照绝对数值，自动识别由高到低进行排序，并按分差计分规则计算得分。(如：业绩，销售额，资产总额，净资产，指标参数等)</span></td>
      </tr>
    </tbody>
  </table>
  <table id="model4" class="w499 hide">
    <tbody>
       <tr>
        <td class="w180 tc"><span class="star_red">*</span>评审参数</td>
        <td><input name="reviewParam" onkeyup="gernerator();" id="reviewParam" value="${scoreModel.reviewParam }" ></td>
        <td><span class="blue">
          该参数代表需要录入供应商的参数名称。<br/>
          减分例子：碳纤维自行车重量参数由小至大排序评分,第一名得1分,其余依次递减0.15分,最低分为0分,其中</span><span class="red">碳纤维自行车重量</span><span class="blue">就为评审参数<br/>
          加分例子：矿泉水容量大小排序评分,第一名得0分,其余依次递增0.15分,最高分为1分,其中</span><span class="red">矿泉水容量</span><span class="blue">就为评审参数</span>
        </td>
      </tr>
      <tr>
        <td class="w180 tc"><span class="star_red">*</span>加减分类型</td>
        <td>
          <select name="addSubtractTypeName" id="addSubtractTypeName" onchange="judgeRelationScore3(this.options[this.options.selectedIndex].value)">
            <option value="0" <c:if test="${scoreModel.addSubtractTypeName == 0}">selected="selected"</c:if> >加分</option>
            <option value="1" <c:if test="${scoreModel.addSubtractTypeName == 1}">selected="selected"</c:if> >减分</option>
          </select>
        </td>
        <td><span class="blue">以最低值为基准值排序递增,是加分还是减分</span></td>
      </tr>
       <tr>
          <td class="w180 tc"><span class="star_red">*</span>是否有基准数额</td>
          <td>
            <select name="isHave" id="isHave" onchange="judge(this.options[this.options.selectedIndex].value)">
              <option value="0" <c:if test="${scoreModel.isHave == 0} "> selected="selected"</c:if>>是</option>
              <option value="1" <c:if test="${scoreModel.isHave == 1}"> selected="selected"</c:if>>否</option>
            </select>
          </td>
          <td><span class="blue">是否有基准数额</span></td>
        </tr>
        <tr class="show">
           <td class="w180 tc"><span class="star_red">*</span>基准数额</td>
           <td><input name="standScores" id="standScores" value="${scoreModel.standScores }"  /></td>
           <td><span class="blue">评审数额低于（等于）[基准数额]时，[加分]类型得[最高分]，[减分]类型得[最低分],其他按照排序得分</span></td>
        </tr>
        <tr class="show">
          <td class="w180 tc"><span class="star_red">*</span>与基准数额关系</td>
          <td>
              <select name="relation" id="relation" onchange="judgeRelationScore2(this.options[this.options.selectedIndex].value)">
                     <option <c:if test="${scoreModel.relation == 0}"> selected="selected" </c:if> value="0">大于等于</option>
                     <option <c:if test="${scoreModel.relation == 1}"> selected="selected" </c:if> value="1">小于等于</option>
               </select>
          </td>
          <td><span class="blue">与基准数额关系,大于等于还是小于等于</span></td>
        </tr>
        <tr class="show">
          <td class="w180 tc"><span class="star_red">*</span>关系分数</td>
          <td>
              <select name="relationScore" id="relationScore" disabled>
                <option value="0" <c:if test="${scoreModel.relationScore == 0}"> selected="selected"</c:if> >最高分</option>
                <option value="1" <c:if test="${scoreModel.relationScore == 1}"> selected="selected"</c:if> >最低分</option>
               </select>
          </td>
          <td><span class="blue">基准数额为限制,最高分还是最低分</span></td>
        </tr>
      
      <tr>
        <td class="w180 tc"><span class="star_red">*</span>最高分</td>
        <td><input name="maxScore" id="maxScore" onkeyup="gernerator();" value="${scoreModel.maxScore }"></td>
        <td><span class="blue">最高分为多少分,[加分]类型时起始分为[最低分],最高分为此分数,[减分]类型此分数为减分基准分,依次递减</span></td>
      </tr>
      <tr>
        <td class="w180 tc"><span class="star_red">*</span>最低分</td>
        <td><input name="minScore" id="minScore" onkeyup="gernerator();" value="${scoreModel.minScore }"></td>
        <td><span class="blue">最低分为多少分,通常为0分,[加分]类型是此分数为起始分,[减分]类型时此分数为最低得分</span></td>
      </tr>
      <tr>
        <td class="w180 tc"><span class="star_red">*</span>分差</td>
        <td><input name="unitScore" id="unitScore" onkeyup="gernerator();" value="${scoreModel.unitScore }"></td>
        <td><span class="blue">依次排序递减/递增分值</span></td>
      </tr>
      <tr>
        <td class="w180 tc"><span class="star_red">*</span>单位</td>
        <td><input name="unit" id="unit" value="${scoreModel.unit }"></td>
        <td><span class="blue">该项目内容为评审参数的单位</span></td>
      </tr>
      
      <tr>
        <td class="w180 tc">翻译成白话文内容</td>
        <td colspan="2" id="easyUnderstandContent4" ></td>
      </tr>
      <tr>
        <td class="w180 tc">当前模型标准解释</td>
        <td colspan="2"><span class="blue">以评审数额最低值为基准排序递增。采购文件明确标准分值，排序分差和最高最低分值限制。评审系统按照绝对数值，自动识别由高到低进行排序，并按分差计分规则计算得分。(如：产品重量，包装品重量，某些工艺指标用品参数等)</span></td>
      </tr>
    </tbody>
  </table>
  <table id="model5" class="w499 hide">
    <tbody>
      <tr>
        <td class="w180 tc"><span class="star_red">*</span>评审参数</td>
        <td><input name="reviewParam" onkeyup="gernerator();" id="reviewParam" value="${scoreModel.reviewParam }"></td>
        <td><span class="blue">
          该参数代表需要供应商需要录入的参数。<br/>
          例：根据企业近三年平均资产总额评分，平均资产总额最高的为评审基准值，得分=（企业平均资产总额/基准值）*2，其中</span><span class="red">平均资产总额</span><span class="blue">就是评审参数</span>
        </td>
      </tr>
      <tr>
        <td class="w180 tc"><span class="star_red">*</span>标准分值</td>
        <td><input name="standardScore" onkeyup="gernerator();" id="standardScore" value="${scoreModel.standardScore }"></td>
        <td><span class="blue">该项内容代表当前评审项的满分值是多少</span></td>
      </tr>
      <tr>
        <td class="w180 tc"><span class="star_red">*</span>单位</td>
        <td><input name="unit" id="unit" value="${scoreModel.unit }"></td>
        <td><span class="blue">该项内容为评审参数的单位,如果没有单位请为空</span></td>
      </tr>
      <tr>
        <td class="w180 tc">翻译成白话文内容</td>
        <td colspan="2" id="easyUnderstandContent5"></td>
      </tr>
      <tr>
        <td class="w180 tc">当前模型标准解释</td>
        <td colspan="2"><span class="blue">以评审数额最高值为基准按比例计算。采购文件明确标准分值和最低最高分限制。系统自动按公式计算得分。评审得分=(投标人数值/绝对值最高数值)×满分。(如：售后服务，合同金额，财务指标，技术指标参数等)</span></td>
      </tr>
    </tbody>
  </table>
  <table id="model6"  class="w499 hide">
    <tbody>
      <tr>
        <td class="w180 tc"><span class="star_red">*</span>评审参数</td>
        <td><input name="reviewParam" onkeyup="gernerator();" id="reviewParam" value="${scoreModel.reviewParam }"></td>
        <td><span class="blue">
          该参数代表需要供应商需要录入的参数。<br/>
          例：满足招标文件要求且报价最低得评审基准价，得分=（评审基准价/企业报价）*标准分值，其中</span><span class="red">企业报价</span><span class="blue">就是评审参数</span>
        </td>
      </tr>
      <tr>
        <td class="w180 tc"><span class="star_red">*</span>标准分值</td>
        <td><input name="standardScore" onkeyup="gernerator();" id="standardScore" value="${scoreModel.standardScore }"></td>
        <td><span class="blue">该项内容代表评审项的满分值是多少</span></td>
      </tr>
      <tr>
        <td class="w180 tc"><span class="star_red">*</span>单位</td>
        <td><input name="unit" id="unit" value="${scoreModel.unit }"></td>
        <td><span class="blue">该项内容为评审参数的单位,如果没有单位请为空</span></td>
      </tr>
      <tr>
        <td class="w180 tc">翻译成白话文内容</td>
        <td colspan="2" id="easyUnderstandContent6"></td>
      </tr>
      <tr>
        <td class="w180 tc">当前模型标准解释</td>
        <td colspan="2"><span class="blue">以评审数额最低值为基准按比例计算。采购文件明确标准分值和最低最高分限制。系统自动按公式计算得分。评审得分=(投标人数值/绝对值最高数值)×满分。(如：售后服务，合同金额，财务指标，技术指标参数等)</span></td>
      </tr>
    </tbody>
  </table>
  <table id="model71" class="w499 hide">
    <tbody>
      <tr>
        <td class="w180 tc"><span class="star_red">*</span>评审参数</td>
        <td><input name="reviewParam" onkeyup="gernerator();" id="reviewParam" value="${scoreModel.reviewParam }" ></td>
        <td><span class="blue">
          该参数代表需要录入供应商的参数。<br/>
          减分例子：百公里耗油,6升（不包括此值）以下为满分,每增加1升扣0.5分,最低分为0分,其中</span><span class="red">百公里耗油</span><span class="blue">就为评审参数<br/>
          加分例子：手机按键正常次数,低于10万次（不包括此值）以下为0分,每增加1万次加0.5分,最高分为10分高于15万次,得10分,其中</span><span class="red">手机按键正常次数</span><span class="blue">就为评审参数</span>
        </td>
      </tr>
      <tr>
        <td class="w180 tc"><span class="star_red">*</span>加减分类型</td>
        <td>
          <select name="addSubtractTypeName"  id="addSubtractTypeName7" onchange="judgeRelationScore(this.options[this.options.selectedIndex].value)">
            <option value="0" <c:if test="${scoreModel.addSubtractTypeName == 0}">selected="selected"</c:if> >加分</option>
            <option value="1" <c:if test="${scoreModel.addSubtractTypeName == 1}">selected="selected"</c:if> >减分</option>
          </select>
        </td>
        <td><span class="blue">如果为[加分],那么低于[评审基准数]为0分,高于[评审基准数]按照规则加分;如果为[减分]，那么低于[评审基准数]为满分,高于[评审基准数]按照规则减分</span></td>
      </tr>
      <tr>
        <td class="w180 tc"><span class="star_red">*</span>加减分分值</td>
        <td><input name="unitScore" id="score" onkeyup="gernerator();" value="${scoreModel.unitScore }"></td>
        <td><span class="blue">每个区间的分之差,加分加多少分,减分减多少分</span></td>
      </tr>
      <tr>
        <td class="w180 tc"><span class="star_red">*</span>区间类型</td>
        <td><select name="intervalTypeName" id="intervalTypeName71" onchange="modelSevenAddSubstact71();">
        <option value="0" selected="selected">以基准值,每个区间的差额相等</option><option value="1">以区间,每个区间的差额不等</option>
        </select></td>
        <td><span class="blue">如果每个区间差额都相等建议选用此区间类型</span></td>
      </tr>
      <tr>
        <td class="w180 tc"><span class="star_red">*</span>每区间等差额</td>
        <td><input name="intervalNumber" onkeyup="gernerator();" id="intervalNumber" value="${scoreModel.intervalNumber }"></td>
        <td><span class="blue">该项内容为每个区间之间的差额</span></td>
      </tr>
      <tr>
        <td class="w180 tc"><span class="star_red">*</span>评审基准数</td>
        <td><input name="reviewStandScore" onkeyup="gernerator();" id="reviewStandScore" value="${scoreModel.reviewStandScore }"></td>
        <td><span class="blue">该项内容为评审参数的参照数值</span></td>
      </tr>
      <tr>
        <td class="w180 tc"><span class="star_red">*</span>评审参数截止数</td>
        <td><input name="deadlineNumber" onkeyup="gernerator();" id="deadlineNumber" value="${scoreModel.deadlineNumber }"></td>
        <td><span class="blue">评审参数的数额高于[截止数],如果[加分],高于[截止数]就是满分,如果[减分],高于[截止数]就是0分</span></td>
      </tr>
      <tr>
        <td class="w180 tc"><span class="star_red">*</span>最高分</td>
        <td><input name="maxScore" onkeyup="gernerator();" id="maxScore" value="${scoreModel.maxScore }"></td>
        <td><span class="blue">该项为评审项供应商所得最高分,通常为该评审项的标准分值</span></td>
      </tr>
      <tr>
        <td class="w180 tc"><span class="star_red">*</span>最低分</td>
        <td><input name="minScore" onkeyup="gernerator();" id="minScore" value="${scoreModel.minScore }"></td>
        <td><span class="blue">该项为评审项供应商所得最低分,通常为0分</span></td>
      </tr>
      <tr>
        <td class="w180 tc"><span class="star_red">*</span>单位</td>
        <td><input name="unit" id="unit" value="${scoreModel.unit }" ></td>
        <td><span class="blue">该项内容为评审参数的单位</span></td>
      </tr>
      <tr>
        <td class="w180 tc">翻译成白话文内容</td>
        <td colspan="2" id="easyUnderstandContent7"></td>
      </tr>
      <tr>
        <td class="w180 tc">当前模型标准解释</td>
        <td colspan="2"><span class="blue">以评审数额最低区间为基准递增排序。采购文件明确标准分值，区间排序分差和最高最低分值限制。系统自动识别区间由低到高进行排序，并按分差计分规则计算得分。(如:汽车油耗，耗水耗电量等指标)</span></td>
      </tr>
      
    </tbody>
  </table>
  <table id="model72"  class="w499 hide">
    <tbody>
      <tr>
        <td class="w180 tc"><span class="star_red">*</span>评审参数</td>
        <td><input name="reviewParam" id="reviewParam" value="${scoreModel.reviewParam }"></td>
        <td><span class="blue">
          该参数代表需要录入供应商的参数。<br/>
          减分例子：百公里耗油,6升（不包括此值）以下为满分,每增加1升扣0.5分,最低分为0分,其中</span><span class="red">百公里耗油</span><span class="blue">就为评审参数<br/>
          加分例子：手机按键正常次数,低于10万次（不包括此值）以下为0分,每增加1万次加0.5分,最高分为10分高于15万次,得10分,其中</span><span class="red">手机按键正常次数</span><span class="blue">就为评审参数</span>
        </td>
      </tr>
      <tr>
        <td class="w180 tc"><span class="star_red">*</span>区间类型</td>
        <td><select name="intervalTypeName" id="intervalTypeName72" onchange="modelSevenAddSubstact72();">
        <option value="0">以基准值,每个区间的差额相等</option><option value="1" selected="selected">以区间,每个区间的差额不等</option>
        </select></td>
        <td><span class="blue">如果每个区间差额都相等建议选用此区间类型</span></td>
      </tr>
      <tr>
        <td class="w180 tc"><span class="star_red">*</span>最高分</td>
        <td><input name="maxScore" id="maxScore" value="${scoreModel.maxScore }"></td>
        <td><span class="blue">该项为评审项供应商所得最高分,通常为该评审项的标准分值</span></td>
      </tr>
      <tr>
        <td class="w180 tc"><span class="star_red">*</span>最低分</td>
        <td><input name="minScore" id="minScore" value="${scoreModel.minScore }"></td>
        <td><span class="blue">该项为评审项供应商所得最低分,通常为0分</span></td>
      </tr>
      <tr>
        <td class="w180 tc"><span class="star_red">*</span>单位</td>
        <td><input name="unit" id="unit" value="${scoreModel.unit }"></td>
        <td><span class="blue">该项内容为评审参数的单位</span></td>
      </tr>
      
    </tbody>
  </table>
  <table id="model81" class="w499 hide">
    <tbody>
      <tr>
        <td class="w180 tc"><span class="star_red">*</span>评审参数</td>
        <td><input name="reviewParam" onkeyup="gernerator();" id="reviewParam" value="${scoreModel.reviewParam }"></td>
        <td><span class="blue">
          该参数代表需要录入供应商的参数。<br/>
          减分例子：生产工序,10道（不包括此值）以上为满分,每减少2道工序扣0.5分,最低分为0分,其中</span><span class="red">生产工序</span><span class="blue">就为评审参数<br/>
          加分例子：汽车尾气排放量,高于100立方米（不包括此值）以下为0分,每减少5立方米加0.5分,最高为15分,低于50立方米得15分,其中</span><span class="red">汽车尾气排放量</span><span class="blue">就为评审参数</span>
        </td>
      </tr>
      <tr>
        <td class="w180 tc"><span class="star_red">*</span>加减分类型</td>
        <td>
        <select name="addSubtractTypeName" id="addSubtractTypeName8" onchange="judgeRelationScore(this.options[this.options.selectedIndex].value)">
            <option value="0" <c:if test="${scoreModel.addSubtractTypeName == 0}">selected="selected"</c:if> >加分</option>
            <option value="1" <c:if test="${scoreModel.addSubtractTypeName == 1}">selected="selected"</c:if> >减分</option>
        </select>
        </td>
        <td><span class="blue">如果为[加分],那么低于[评审基准数]为0分,高于[评审基准数]按照规则加分;如果为[减分]，那么低于[评审基准数]为满分,高于[评审基准数]按照规则减分</span></td>
      </tr>
      <tr>
        <td class="w180 tc"><span class="star_red">*</span>加减分分值</td>
        <td><input name="unitScore" id="score" onkeyup="gernerator();" value="${scoreModel.unitScore }"></td>
        <td><span class="blue">每个区间的分之差,加分加多少分,减分减多少分</span></td>
      </tr>
      
      <tr>
        <td class="w180 tc"><span class="star_red">*</span>区间类型</td>
        <td><select name="intervalTypeName" id="intervalTypeName81" onchange="modelSevenAddSubstact81();">
        <option value="0" selected="selected">以基准值,每个区间的差额相等</option><option value="1">以区间,每个区间的差额不等</option>
        </select></td>
        <td><span class="blue">如果每个区间差额都相等建议选用此区间类型</span></td>
      </tr>
      <tr>
        <td class="w180 tc"><span class="star_red">*</span>每区间等差额</td>
        <td><input name="intervalNumber" onkeyup="gernerator();" id="intervalNumber" value="${scoreModel.intervalNumber }"></td>
        <td><span class="blue">该项内容为每个区间之间的差额</span></td>
      </tr>
      <tr>
        <td class="w180 tc"><span class="star_red">*</span>评审基准数</td>
        <td><input name="reviewStandScore" id="reviewStandScore" value="${scoreModel.reviewStandScore }"></td>
        <td><span class="blue">该项内容为评审参数的参照数值</span></td>
      </tr>
      
      <tr>
        <td class="w180 tc"><span class="star_red">*</span>评审参数截止数</td>
        <td><input name="deadlineNumber" onkeyup="gernerator();" id="deadlineNumber" value="${scoreModel.deadlineNumber }"></td>
        <td><span class="blue">评审参数的数额高于[截止数],如果[加分],高于[截止数]就是满分,如果[减分],高于[截止数]就是0分</span></td>
      </tr>
      <tr>
        <td class="w180 tc"><span class="star_red">*</span>最高分</td>
        <td><input name="maxScore" onkeyup="gernerator();" id="maxScore" value="${scoreModel.maxScore }"></td>
        <td><span class="blue">该项为评审项供应商所得最高分,通常为该评审项的标准分值</span></td>
      </tr>
      <tr>
        <td class="w180 tc"><span class="star_red">*</span>最低分</td>
        <td><input name="minScore" onkeyup="gernerator();" id="minScore" value="${scoreModel.minScore }"></td>
        <td><span class="blue">该项为评审项供应商所得最低分,通常为0分</span></td>
      </tr>
      <tr>
        <td class="w180 tc"><span class="star_red">*</span><span class="star_red">*</span>单位</td>
        <td><input name="unit" id="unit" value="${scoreModel.unit }"></td>
        <td><span class="blue">该项内容为评审参数的单位</span></td>
      </tr>
      <tr>
        <td class="w180 tc">翻译成白话文内容</td>
        <td colspan="2" id="easyUnderstandContent8"></td>
      </tr>
      <tr>
        <td class="w180 tc">当前模型标准解释</td>
        <td colspan="2"><span class="blue">以评审数额最高区间为基准递减排序。采购文件明确标准分值，区间排序分差和最高最低分值限制。系统自动识别区间由高到低进行排序，并按分差计分规则计算得分。(如:制氧量，工序等指标)</span></td>
      </tr>
      
    </tbody>
  </table>
  <table id="model82"  class="w499 hide">
    <tbody>
      <tr>
        <td class="w180 tc"><span class="star_red">*</span>评审参数</td>
        <td><input name="reviewParam" id="reviewParam" value="${scoreModel.reviewParam }"></td>
        <td><span class="blue">
        该参数代表需要录入供应商的参数。<br/>
        减分例子：生产工序,10道（不包括此值）以上为满分,每减少2道工序扣0.5分,最低分为0分,其中</span><span class="red">生产工序</span><span class="blue">就为评审参数<br/>
        加分例子：汽车尾气排放量,高于100立方米（不包括此值）以下为0分,每减少5立方米加0.5分,最高为15分,低于50立方米得15分,其中</span><span class="red">汽车尾气排放量</span><span class="blue">就为评审参数</span>
        </td>
      </tr>
      <tr>
        <td class="w180 tc"><span class="star_red">*</span>最高分</td>
        <td><input name="maxScore" id="maxScore" value="${scoreModel.maxScore }"></td>
        <td><span class="blue">该项为评审项供应商所得最高分,通常为该评审项的标准分值</span></td>
      </tr>
      <tr>
        <td class="w180 tc"><span class="star_red">*</span>最低分</td>
        <td><input name="minScore" id="minScore" value="${scoreModel.minScore }"></td>
        <td><span class="blue">该项为评审项供应商所得最低分,通常为0分</span></td>
      </tr>
      <tr>
        <td class="w180 tc"><span class="star_red">*</span>区间类型</td>
        <td><select name="intervalTypeName" id="intervalTypeName82" onchange="modelSevenAddSubstact82();">
        <option value="0">以基准值,每个区间的差额相等</option><option value="1" selected="selected">以区间,每个区间的差额不等</option>
        </select></td>
        <td><span class="blue">如果每个区间差额都相等建议选用此区间类型</span></td>
      </tr>
      <tr>
        <td class="w180 tc"><span class="star_red">*</span>单位</td>
        <td><input name="unit" id="unit" value="${scoreModel.unit }"></td>
        <td><span class="blue">该项内容为评审参数的单位</span></td>
      </tr>
    </tbody>
  </table>
  
  <!-- 模型一B -->
  <table id="model9" class="w499 hide">
    <tbody>
      <tr>
        <td class="w300">评审参数<input type="hidden" name="judgeContent" id="judgeContent" /><input type="hidden" name="standardScore" id="standardScore" /></td>
        <td><input name="reviewParam" id="reviewParam" value="${scoreModel.reviewParam}" ></td>
        <td colspan="3"><span class="blue">该参数代表需要供应商录入的参数</span>
      </tr>
      <c:if test="${not empty scoreModel.model1BJudgeContent}">
        <c:forEach items="${scoreModel.model1BJudgeContent}" var="se" varStatus="vs"> 
          <tr>
          <td><span class="star_red">*</span>选择项名称</td>
          <td><input value="${fn:substringBefore(se, '-')}" ></td>
          <td><span class="star_red">*</span>对应分数</td>
          <td><input value="${fn:substringAfter(se, '-')}" ></td>
          <td class="tc"><button class="btn btn-windows delete" type="button" onclick="deleteRow(this)">删除</button></td>
          </tr>
        </c:forEach>
      </c:if>
      <c:if test="${empty scoreModel.model1BJudgeContent}">
        <tr>
          <td><span class="star_red">*</span>选择项名称</td>
          <td><input onkeyup="gernerator();" ></td>
          <td><span class="star_red">*</span>对应分数</td>
          <td><input onkeyup="gernerator();"  ></td>
          <td class="tc"><button class="btn btn-windows delete" type="button" onclick="deleteRow(this)">删除</button></td>
        </tr>
      </c:if>
      <tr id="guding">
        <td colspan="5" class="tc"><button class="btn btn-windows add" type="button" onclick="addRows()">添加一行</button></td>
      </tr>
      <tr>
        <td>翻译成白话文内容</td>
        <td colspan="4" id="easyUnderstandContent9"></td>
      </tr>
      <tr>
        <td>当前模型标准解释</td>
        <td colspan="4"><span class="blue">按项匹配分值</span></td>
      </tr>
    </tbody>
  </table>  
  
  <table id="model4B" class="w499 hide">
    <tbody>
       <tr>
        <td class=" w300 tc"><span class="star_red">*</span>评审参数</td>
        <td><input name="reviewParam" onkeyup="gernerator();" id="reviewParam" value="${scoreModel.reviewParam }" ></td>
        <td><span class="blue">
          *该参数代表需要录入供应商的参数名称。<br/>
           减分例子：产品生产工序符合产品技术标准情况评分排名，排名第一的得最高分，其余其余依次递减0.1分。其中</span><span class="red">产品生产工序符合产品技术标准情况排名</span><span class="blue">就为评审参数<br/>
          加分例子：上年度消费管理局罚款金额大小排名情况,第一名得最低分,其余依次递增0.1分,最高分为1分,其中</span><span class="red">上年度消费管理局罚款金额排名</span><span class="blue">就为评审参数</span>
        </td>
      </tr>
      <tr>
        <td class=" w300 tc"><span class="star_red">*</span>加减分类型</td>
        <td>
          <select name="addSubtractTypeName" id="addSubtractTypeName" onchange="judgeRelationScore(this.options[this.options.selectedIndex].value)">
            <option value="0" <c:if test="${scoreModel.addSubtractTypeName == 0}">selected="selected"</c:if> >加分</option>
            <option value="1" <c:if test="${scoreModel.addSubtractTypeName == 1}">selected="selected"</c:if> >减分</option>
          </select>
        </td>
        <td><span class="blue">以最高值为基准值排序递减,是加分还是减分</span></td>
      </tr>
      
      <tr>
        <td class=" w300 tc"><span class="star_red">*</span>最高分</td>
        <td><input name="maxScore" id="maxScore" onkeyup="gernerator();" value="${scoreModel.maxScore }"></td>
        <td><span class="blue">最高分为多少分,[加分]类型时起始分为[最低分],最高分为此分数,[减分]类型此分数为减分基准分,依次递减</span></td>
      </tr>
      <tr>
        <td class=" w300 tc"><span class="star_red">*</span>最低分</td>
        <td><input name="minScore" id="minScore" onkeyup="gernerator();" value="${scoreModel.minScore }"></td>
        <td><span class="blue">最低分为多少分,通常为0分,[加分]类型是此分数为起始分,[减分]类型时此分数为最低得分</span></td>
      </tr>
      <tr>
        <td class=" w300 tc"><span class="star_red">*</span>分差</td>
        <td><input name="unitScore" id="score" onkeyup="gernerator();" value="${scoreModel.unitScore }"></td>
        <td><span class="blue">依次排序递减/递增分值</span></td>
      </tr>
      <tr>
        <td class=" w300 tc">翻译成白话文内容</td>
        <td colspan="2" id="easyUnderstandContent4"></td>
      </tr>
      <tr>
        <td class=" w300 tc">当前模型标准解释</td>
        <td colspan="2"><span class="blue">以评审数额最低值为基准排序递增。采购文件明确标准分值，排序分差和最高最低分值限制。评审系统按照绝对数值，自动识别由高到低进行排序，并按分差计分规则计算得分。(如：产品重量，包装品重量，某些工艺指标用品参数等)</span></td>
      </tr>
    </tbody>
  </table>
  
  <!-- 八大模型 -->
</body>
</html>