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
          // 判断状态输出
          if (list_content.list[i].status === '-3') {
            list_content.list[i].status = '公示中';
          } else if (list_content.list[i].status === '-2') {
            list_content.list[i].status = '预复审结束';
          } else if (list_content.list[i].status === '-1') {
            list_content.list[i].status = '暂存';
          } else if (list_content.list[i].status === '0') {
            list_content.list[i].status = '待初审';
          } else if (list_content.list[i].status === '4' && list_content.list[i].auditTemporary === '4') {
            list_content.list[i].status = '复审中';
          } else if (list_content.list[i].status === '5') {
            list_content.list[i].status = '复审不合格';
          } else if (list_content.list[i].status === '6') {
            list_content.list[i].status = '待复查';
          } else if (list_content.list[i].status === '7') {
            list_content.list[i].status = '复查合格';
          } else if (list_content.list[i].status === '8') {
            list_content.list[i].status = '复查不合格';
          } else if (list_content.list[i].status === '10') {
            list_content.list[i].status = '复审退回修改';
          } else if (list_content.list[i].status === '11') {
            list_content.list[i].status = '待分配';
          } else if (list_content.list[i].status === '12') {
            list_content.list[i].status = '处罚中';
          } else if (list_content.list[i].status === '13') {
            list_content.list[i].status = '无产品专家';
          } else if (list_content.list[i].status === '14') {
            list_content.list[i].status = '复审待分组专家';
          }
          
          // 审核组为空输出
          if (typeof(list_content.list[i].groupName) == 'undefined') {
            list_content.list[i].groupName = '';
          }
          
          // 判断复审专家输出
          if (typeof(list_content.list[i].status) == 'undefined') {
            list_content.list[i].status = '';
          } else if (list_content.list[i].status === '4' || list_content.list[i].status === '11' || list_content.list[i].status === '14') {
            list_content.list[i].auditor = '';
          }
          
          $('#list_content').append('<tr>'
            +'<td class="text-center">'+ list_content.list[i].batchDetailsNumber +'</td>'
            +'<td class="text-center">'+ list_content.list[i].orgName +'</td>'
            +'<td class="text-center">'+ list_content.list[i].realName +'</td>'
            +'<td class="text-center">'+ list_content.list[i].gender +'</td>'
            +'<td class="text-center">'+ list_content.list[i].workUnit +'</td>'
            +'<td class="text-center">'+ list_content.list[i].professTechTitles +'</td>'
            +'<td class="text-center">'+ list_content.list[i].updateTime +'</td>'
            +'<td class="text-center">'+ list_content.list[i].groupName +'</td>'
            +'<td class="text-center">'+ list_content.list[i].auditor +'</td>'
            +'<td class="text-center">'+ list_content.list[i].status +'</td>'
            +'<td class="text-center">'+ list_content.list[i].auditAt +'</td>'
          +'</tr>');
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