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
        $('#list_content').html('');
        for (var i in list_content.list) {
          $('#list_content').append('<tr>'
            +'<td class="text-center"><input name="id" type="checkbox" value="'+ list_content.list[i].id +'" class="select_item"></td>'
            +'<td class="text-center">'+ (parseInt(i) + 1) +'</td>'
            +'<td>'+ list_content.list[i].orgName +'</td>'
            +'<td>'+ list_content.list[i].relName +'</td>'
            +'<td class="text-center">'+ list_content.list[i].sex +'</td>'
            +'<td>'+ list_content.list[i].workUnit +'</td>'
            +'<td>'+ list_content.list[i].professTechTitles +'</td>'
            +'<td class="text-center">'+ list_content.list[i].updatedAt +'</td>'
          +'</tr>');
        }
        
        // 勾选翻页之前选中的项
        for (var i in select_ids) {
          $('.select_item').each(function () {
            if ($(this).val() === select_ids[i]) {
              $(this).prop('checked', true);
              return false;
            }
          });
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
          });
        }
        
        // 构造分页
        laypageConstructor();
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
    function laypageConstructor() {
      laypage({
        cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
        pages: list_content.pages, //总页数
        skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
        skip: true, //是否开启跳页
        total: list_content.total,
        startRow: list_content.startRow,
        endRow: list_content.endRow,
        groups: list_content.pages >= 3 ? 3 : list_content.pages, //连续显示分页数
        curr: function() { //合格url获取当前页，也可以同上（pages）方式获取
          return list_content.pageNum;
        }(),
        jump: function(e, first) { //触发分页后的回调
          if(!first) { //一定要加此判断，否则初始时会无限刷新
            $("#pageNum").val(e.curr);
            $.ajax({
              type: opts.type,
              dataType: opts.dataType,
              url: opts.url,
              data: {
                pageNum: e.curr
              },
              success: opts.success
            });
          }
        }
      });
    }

    return start();
  }
})(jQuery);