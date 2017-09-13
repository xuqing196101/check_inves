 
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
		              "<td class='tc h30 lh30'> "+data[i].name+" </td>"+
	              	  "<td class='tc h30 lh30'>"+data[i].compary+" </td>"+
	              	  "<td class='tc h30 lh30'>"+data[i].duty+" </td>"+
	              	  "<td class='tc h30 lh30'> "+data[i].rank+"</td>"+
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
    	var index = 0;
			var j = 1;
			var str = '';
      var tableTrObj = $(obj).parents("form").find("tbody tr");
			$("#personList").find(":checked").each(function() {
        var _this = $(this);
        var count = 0;
        tableTrObj.each(function (index) {
          // 判断复选框中的value是否和现有的重复，重复代表为相同的一条数据
          if (_this.val() === $(this).find(':checked').val()) {
            conut = 4;
            return false;
          } else {
            if (Trim(_this.parents('tr').find('td').eq(2).html(), 'g') === Trim($(this).find('[name="list['+index+'].name"]').val(), 'g')) {
              count++;
            }
            if (Trim(_this.parents('tr').find('td').eq(3).html(), 'g') === Trim($(this).find('[name="list['+index+'].compary"]').val(), 'g')) {
              count++;
            }
            if (Trim(_this.parents('tr').find('td').eq(4).html(), 'g') === Trim($(this).find('[name="list['+index+'].duty"]').val(), 'g')) {
              count++;
            }
            if (Trim(_this.parents('tr').find('td').eq(5).html(), 'g') === Trim($(this).find('[name="list['+index+'].rank"]').val(), 'g')) {
              count++;
            }
            if (count === 4) {
              return false;
            }
          }
        });
        // console.log(count);
        if (count != 4) {
          // tableTrObj.html(parseInt(index)+j);
          str += '<tr>'+ _this.parents("tr").html() +'</tr>';
        }
			});
      
			$(obj).parents("form").find("tbody").prepend(str);
      $(obj).parents("form").find("tbody tr").each(function() {
				$(this).find('td').eq(1).html(j);
				j++;
			});
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