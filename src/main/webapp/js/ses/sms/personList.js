 
/**
 * 人员信息 监督人员 抽取人员 公共js
 */
var personType ;
  	//加载人员列表
	$(function() {
		//人员类型
		personType = $("#personType").val();
  		//getPersonList();
  	}); 
  	
  	 //全选全不选
    function checkAll(obj){
    	$(obj).parents("table").find(":checkbox").prop("checked",$(obj).is(':checked'));
    }
  	
  	
  	function getPersonList(){
  		 $("tbody").empty();
  		$.ajax({
            type: "POST",
            url: globalPath+"/"+personType+"/getPeronList.do?personType="+personType,
            data: $("form").serialize(),
            dataType: "json",
            success: function (data) {
            	for(var i=0;i<data.length;i++){
            	 var tr = "<tr>"+
		              "<td class='tc h30 lh30'> <input type='checkbox' name='id' value="+data[i].id+"> </td>"+
		              "<td class='tc h30 lh30'> "+(i+1)+" </td>"+
		              "<td class='h30 lh30'> "+data[i].name+" </td>"+
	              	  "<td class='h30 lh30'>"+data[i].compary+" </td>"+
	              	  "<td class='h30 lh30'>"+data[i].duty+" </td>"+
	              	  "<td class='h30 lh30'> "+data[i].rank+"</td>"+
		              "</tr>";
		            $("tbody").append(tr);
            	}
            }
        });
  	} 
  	 
    function chosePerson(obj) {
    	//当前已添加的人员序号
			//var index = $(obj).parents("form").find("tr:last").find("td:eq(1)").html();
			//if(null==index ||''==index || "undefined"== index){
			//	index=0;
			//}
	  var input_count = 0;
      var count = 0;
			var j = 1;
			var str = '';
      var tableTrObj = $(obj).parents("form").find("tbody tr");
			$("#personList").find(":checked").each(function() {
        var _this = $(this);
        tableTrObj.each(function (index) {
          // 判断复选框中的value是否和现有的重复，重复代表为相同的一条数据
          if (_this.val() === $(this).find('input[type=checkbox]').val()) {
            count = 5;
            return false;
          } else {
            if ($(this).find('td:eq(2) input').length > 0) {
              if (Trim(_this.parents('tr').find('td').eq(2).html(), 'g') === Trim($(this).find('td:eq(2) input').val(), 'g')) {
                count++;
              }
              if (Trim(_this.parents('tr').find('td').eq(3).html(), 'g') === Trim($(this).find('td:eq(3) input').val(), 'g')) {
                count++;
              }
              if (Trim(_this.parents('tr').find('td').eq(4).html(), 'g') === Trim($(this).find('td:eq(4) input').val(), 'g')) {
                count++;
              }
              if (Trim(_this.parents('tr').find('td').eq(5).html(), 'g') === Trim($(this).find('td:eq(5) input').val(), 'g')) {
                count++;
              }
              if (count === 4) {
                $(this).find('td:eq(2) input').val('');
                $(this).find('td:eq(3) input').val('');
                $(this).find('td:eq(4) input').val('');
                $(this).find('td:eq(5) input').val('');
                layer.msg('您输入的人员信息已存在，请重新填写', {
                  offset: '100px',
                  time: 1000
                });
                return false;
              }
            }
          }
        });
        if (count != 5) {
          str += '<tr>'+ _this.parents("tr").html() +'</tr>';
        }
        count = 0;
			});
      
      if (str != '') {
        var remove_num = 0;
        var inputTrnum = 0;
        var trNum = 0;
        $(obj).parents("form").find("tbody tr").each(function () {
          if ($(this).find('input[type=text]').length > 0) {
            inputTrnum++;
          }
          trNum++;
        });
  			$(obj).parents("form").find("tbody").prepend(str);
        // if (inputTrnum === trNum) {
          $(obj).parents("form").find("tbody tr").each(function(index) {
            if ($(this).find('input[type=text]').length > 0) {
              var name = Trim($(this).find('td:eq(2) input').val(), 'g');
              var compary = Trim($(this).find('td:eq(3) input').val(), 'g');
              var duty = Trim($(this).find('td:eq(4) input').val(), 'g');
              var rank = Trim($(this).find('td:eq(5) input').val(), 'g');
              if ($("#personList").find(":checked").length > remove_num) {
                if (name === '' && compary === '' && duty === '' && rank === '') {
                  $(this).next().find('input[type=checkbox]').eq(0).prop('name', 'list['+ input_count +'].id');
                  $(this).next().find('input[type=hidden]').eq(0).prop('name', 'list['+ input_count +'].id');
                  $(this).next().find('input[type=text]').eq(0).prop('name', 'list['+ input_count +'].name');
                  $(this).next().find('input[type=text]').eq(1).prop('name', 'list['+ input_count +'].compary');
                  $(this).next().find('input[type=text]').eq(2).prop('name', 'list['+ input_count +'].duty');
                  $(this).next().find('input[type=text]').eq(3).prop('name', 'list['+ input_count +'].rank');
                  $(this).remove();
                  remove_num++;
                  input_count++;
                }
              }
            }
    			});
        // }
        $(obj).parents("form").find("tbody tr").each(function() {
          $(this).find('td').eq(1).html(j);
  				j++;
        });
      }
			var layerIndex = parent.layer.getFrameIndex(window.name); //获取窗口索引
      parent.layer.close(layerIndex);
    }
    
// 去除空格
function Trim(str, is_global) {
  var result;
  result = str.replace(/(^\s+)|(\s+$)/g,"");
  if(is_global.toLowerCase()=="g") {
    result = result.replace(/\s/g,"");
  }
  return result;
}