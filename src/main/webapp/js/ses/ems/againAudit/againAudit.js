(function($) {
  $.fn.listConstructor = function(options) {
    var list_content = [];  // 初始化数据
    
    //默认参数
    var defaults = {
      type: 'POST',
      dataType: 'json',
      url: '',
      data: {},
      success: function (data) {
        list_content = data.object;  // 储存所需数据到变量
        if (is_init === 0) {
          var str = '';
          for (var orgName_i in list_content.allOrg) {
            str += '<option value="'+ list_content.allOrg[orgName_i].shortName +'">'+ list_content.allOrg[orgName_i].shortName +'</option>';
          }
          $('[name=orgName]').html('<option value="">请选择</option>' + str);
          str = '';
          for (var expertsFrom_i in list_content.lyTypeList) {
            str += '<option value="'+ list_content.lyTypeList[expertsFrom_i].id +'">'+ list_content.lyTypeList[expertsFrom_i].name +'</option>';
          }
          $('[name=expertsFrom]').html('<option value="">全部</option>' + str);
          str = '';
          for (var expertsTypeId_i in list_content.expTypeList) {
            str += '<option value="'+ list_content.expTypeList[expertsTypeId_i].id +'">'+ list_content.expTypeList[expertsTypeId_i].name +'</option>';
          }
          $('[name=expertsTypeId]').html(str);
          str = '';
          $('[name=expertsTypeId]').select2({
            placeholder: '全部',
            closeOnSelect: false,
            minimumResultsForSearch: -1
          });
        }
        is_init ++;
        
        if (typeof(list_content) != 'undefined') {
          $('#list_content').html('');
          for (var i in list_content.expertList) {
            if (typeof(list_content.expertList[i].orgName) === 'undefined') {
              list_content.expertList[i].orgName = '';
            }
            if (typeof(list_content.expertList[i].relName) === 'undefined') {
              list_content.expertList[i].relName = '';
            }
            if (typeof(list_content.expertList[i].sex) === 'undefined') {
              list_content.expertList[i].sex = '';
            }
            if (typeof(list_content.expertList[i].workUnit) === 'undefined') {
              list_content.expertList[i].workUnit = '';
            }
            if (typeof(list_content.expertList[i].professTechTitles) === 'undefined') {
              list_content.expertList[i].professTechTitles = '';
            }
            if (typeof(list_content.expertList[i].updateTime) === 'undefined') {
              list_content.expertList[i].updateTime = '';
            }
            if (typeof(list_content.expertList[i].expertsTypeId) === 'undefined') {
              list_content.expertList[i].expertsTypeId = '';
            }
            if (typeof(list_content.expertList[i].expertsFrom) === 'undefined') {
              list_content.expertList[i].expertsFrom = '';
            }
            
            $('#list_content').append('<tr>'
              +'<td class="text-center"><input name="id" type="checkbox" value="'+ list_content.expertList[i].id +'" class="select_item"></td>'
              +'<td class="text-center">'+ (parseInt(i) + 1) +'</td>'
              +'<td>'+ list_content.expertList[i].orgName +'</td>'
              +'<td>'+ list_content.expertList[i].relName +'</td>'
              +'<td class="text-center">'+ list_content.expertList[i].sex +'</td>'
              +'<td>'+ list_content.expertList[i].expertsTypeId +'</td>'
              +'<td class="text-center">'+ list_content.expertList[i].expertsFrom +'</td>'
              +'<td>'+ list_content.expertList[i].workUnit +'</td>'
              +'<td>'+ list_content.expertList[i].professTechTitles +'</td>'
              +'<td class="text-center">'+ list_content.expertList[i].updateTime +'</td>'
            +'</tr>');
          }
          
          // 勾选翻页之前选中的项
          var allchecked = 0;
          for (var i in select_ids) {
            $('.select_item').each(function () {
              if ($(this).val() === select_ids[i]) {
                allchecked++;
                $(this).prop('checked', true);
                return false;
              }
            });
            if (allchecked === $('.select_item').length) {
              $('[name=checkAll]').prop('checked', true);
            } else {
              $('[name=checkAll]').prop('checked', false);
            }
          }
          
          // 绑定列表框点击事件，获取选中id集合
          var select_checkbox = $('.againAudit_table').find('.select_item');
          if (select_checkbox.length > 0) {
            select_checkbox.bind('click', function () {
              var this_val = $(this).val().toString();
              
              if ($(this).is(':checked')) {
                select_ids.push(this_val);
              } else {
                for (var i in select_ids) {
                  if (select_ids[i] == this_val) {
                    select_ids.splice(i, 1);
                    break;
                  }
                }
              }
              
              var sum = 0;
              $(this).parents('tbody').find('.select_item').each(function () {
                if ($(this).is(':checked')) {
                  sum++;
                }
              });
              
              if (sum === $(this).parents('tbody').find('.select_item').length) {
                $(this).parents('tbody').siblings('thead').find('[name=checkAll]').prop('checked', true);
              } else {
                $(this).parents('tbody').siblings('thead').find('[name=checkAll]').prop('checked', false);
              }
            });
          }
          
          // 构造分页
          // laypageConstructor();
        }
      },
      error: function (data) {
        layer.msg(data.message, {
          offset: '100px'
        });
      }
    };

    //覆盖默认参数
    var opts = $.extend(defaults, options);
    var el = this;

    var start = function() {
      el.each(function() {
        $.ajax({
          type: opts.type,
          dataType: opts.dataType,
          url: opts.url,
          data: opts.data,
          success: opts.success
        });
      });
    }
    
    // 分页
//    function laypageConstructor() {
//      laypage({
//        cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
//        pages: list_content.pages, //总页数
//        skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
//        skip: true, //是否开启跳页
//        total: list_content.total,
//        startRow: list_content.startRow,
//        endRow: list_content.endRow,
//        groups: list_content.pages >= 3 ? 3 : list_content.pages, //连续显示分页数
//        curr: function() { //合格url获取当前页，也可以同上（pages）方式获取
//          return list_content.pageNum;
//        }(),
//        jump: function(e, first) { //触发分页后的回调
//          if(!first) { //一定要加此判断，否则初始时会无限刷新
//            $("#pageNum").val(e.curr);
//            opts.data.pageNum = e.curr;
//            $.ajax({
//              type: opts.type,
//              dataType: opts.dataType,
//              url: opts.url,
//              data: opts.data,
//              success: opts.success
//            });
//          }
//        }
//      });
//    }

    return start();
  }
})(jQuery);