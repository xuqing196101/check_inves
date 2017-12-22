(function($) {
  $.fn.m_fixedTable = function(options) {
    //默认参数
    var defaults = {
      fixedNumber: 0  // 锁定列数
    };

    //覆盖默认参数
    var opts = $.extend(defaults, options);
    var el = this;
    var fixedHeader = '<div class="mfixed-header"></div>';
    var fixedHeader_columns = '<div class="mfixed-header-columns"></div>';
    var fixedColumns = '<div class="mfixed-columns"><table class="table table-hover table-bordered"><tbody></tbody></table></div>';
    
    var build_structure = function (obj) {
      // 初始化结构
      if (!$(obj).parents().hasClass('mfixed-main')) {
        $(obj).wrapAll('<div class="mfixed-main"></div>');
        $(obj).before(fixedHeader + fixedHeader_columns);
        $(obj).after(fixedColumns);
        $(obj).wrapAll('<div class="mfixed-body"></div>');
      }
      
      // 初始化保存html的变量
      var header_html = '';
      var header_columns_html = '';
      var columns_html = '';
      
      // 构造表头html
      $(obj).parent().siblings('.mfixed-header').css({
        width: $(obj).parents('.mfixed-main').width()
      });
      $(obj).find('thead tr').each(function (index_tr) {
        var header_html_temp = '';
        var header_columns_html_temp = '';
        $(this).find('th').each(function (index) {
          var colspan = 0;
          var rowspan = 0;
          if (typeof($(this).attr('colspan')) != 'undefined') {
            colspan = $(this).attr('colspan');
          }
          if (typeof($(this).attr('rowspan')) != 'undefined') {
            rowspan = $(this).attr('rowspan');
          }
          
          if ($(this).attr('style') != undefined) {
            header_html_temp += '<th colspan="'+ colspan +'" rowspan="'+ rowspan +'" style="'+ $(this).attr('style') +'width: '+ $(this).outerWidth(true) +'px; height: '+ $(this).outerHeight(true) +'px">'+ $(this).html() +'</th>';
            if (index < opts.fixedNumber && index_tr == 0) {
              header_columns_html_temp += '<th colspan="'+ colspan +'" rowspan="'+ rowspan +'" style="'+ $(this).attr('style') +'width: '+ $(this).outerWidth(true) +'px; height: '+ $(this).outerHeight(true) +'px">'+ $(this).html() +'</th>';
            }
          } else {
            header_html_temp += '<th colspan="'+ colspan +'" rowspan="'+ rowspan +'" style="width: '+ $(this).outerWidth(true) +'px; height: '+ $(this).outerHeight(true) +'px">'+ $(this).html() +'</th>';
            if (index < opts.fixedNumber && index_tr == 0) {
              header_columns_html_temp += '<th colspan="'+ colspan +'" rowspan="'+ rowspan +'" style="width: '+ $(this).outerWidth(true) +'px; height: '+ $(this).outerHeight(true) +'px">'+ $(this).html() +'</th>';
            }
          }
        });
        header_html += '<tr>'+ header_html_temp +'</tr>';
        if (header_columns_html_temp != '') {
          header_columns_html = '<tr>'+ header_columns_html_temp +'</tr>';
        }
      });
      header_html = '<table class="table table-hover table-bordered" style="width: '+ $(obj).width() +'px"><thead>'+ header_html +'</thead></table>';
      header_columns_html = '<table class="table table-hover table-bordered"><thead>'+ header_columns_html +'</thead></table>';
      
      // 构造冻结列html
      var columns_startIndex = 0;
      var mfixed_columns_width = 0;
      if ($(obj).parent().siblings('.mfixed-columns').find('tbody').html() != '') {
        columns_startIndex = $(obj).parent().siblings('.mfixed-columns').find('tbody tr').length;
      }
      $(obj).find('tbody tr').each(function (index) {
        if (index == 0) {
          $(this).find('td').each(function (td_index) {
            if (td_index < opts.fixedNumber) {
              mfixed_columns_width += parseInt($(this).outerWidth(true));
            }
          });
        }
        // var td_html = '';
        // if (columns_startIndex != 0) {
        //   if (index >= columns_startIndex) {
        //     $(this).find('td').each(function (td_index) {
        //       var colspan = 0;
        //       var rowspan = 0;
        //       if (typeof($(this).attr('colspan')) != 'undefined') {
        //         colspan = columns_colspan = $(this).attr('colspan');
        //       }
        //       if (typeof($(this).attr('rowspan')) != 'undefined') {
        //         rowspan = $(this).attr('rowspan');
        //       }
        // 
        //       if (td_index < opts.fixedNumber) {
        //         td_html += '<td colspan="'+ colspan +'" rowspan="'+ rowspan +'" class="'+ $(this).attr('class') +'">'+ $(this).html() +'</td>';
        //       }
        //     });
        //     columns_html += '<tr>'+ td_html +'</tr>';
        //   }
        // } else {
        //   $(this).find('td').each(function (td_index) {
        //     var colspan = 0;
        //     var rowspan = 0;
        //     if (typeof($(this).attr('colspan')) != 'undefined') {
        //       colspan = $(this).attr('colspan');
        //     }
        //     if (typeof($(this).attr('rowspan')) != 'undefined') {
        //       rowspan = $(this).attr('rowspan');
        //     }
        // 
        //     if (td_index < opts.fixedNumber) {
        //       td_html += '<td colspan="'+ colspan +'" rowspan="'+ rowspan +'" class="'+ $(this).attr('class') +'">'+ $(this).html() +'</td>';
        //     }
        //   });
        //   columns_html += '<tr>'+ td_html +'</tr>';
        // }
      });
      $(obj).parent().siblings('.mfixed-columns').css({
        top: $(obj).find('thead th').outerHeight()
      });
      
      // 填充生成的html
      $(obj).parent().siblings('.mfixed-header').html(header_html);
      $(obj).parent().siblings('.mfixed-header-columns').html(header_columns_html);
      $(obj).parent().siblings('.mfixed-columns').find('table').css('width', $(obj).width());
      $(obj).parent().siblings('.mfixed-columns').find('tbody').html($(obj).find('tbody').html());
      $(obj).parent().siblings('.mfixed-columns').css('width', mfixed_columns_width + 1);
      
      // 添加冻结列宽度高度
      $(obj).find('tbody tr').each(function (index) {
        $(this).find('td').each(function (td_index) {
          if (td_index < opts.fixedNumber) {
            if (typeof($(this).attr('colspan')) == 'undefined') {
              $(obj).parent().siblings('.mfixed-columns').find('tbody tr').eq(index).find('td').eq(td_index).css({
                width: $(this).outerWidth(),
                height: $(this).outerHeight()
              });
            } else {
              $(obj).parent().siblings('.mfixed-columns').find('tbody tr').eq(index).find('td').eq(td_index).css({
                height: $(this).outerHeight(),
                borderRight: 'none'
              });
            }
          }
        });
      });
      
      // 滚动超过表头位置，表头和冻结列跟随
      fixed_position();
      function fixed_position() {
        if ($(window).scrollTop() >= $(obj).offset().top) {
          $(obj).parent().siblings('.mfixed-header').css({
            position: 'fixed',
            left: 'auto'
          });
          
          $(obj).parent().siblings('.mfixed-header-columns').css({
            position: 'fixed',
            left: 'auto'
          });
        } else {
          $(obj).parent().siblings('.mfixed-header').css({
            position: 'absolute',
            left: 0
          });
          
          $(obj).parent().siblings('.mfixed-header-columns').css({
            position: 'absolute',
            left: 0
          });
        }
      };
      
      // 页面滚动表头和冻结列跟随
      $(window).scroll(function () {
        fixed_position();
      });
      
      // 左右滚动表头跟随表格
      $(obj).parent().bind('scroll', function () {
        var offset = $(this).scrollLeft();
        
        $(obj).parent().siblings('.mfixed-header').find('table').css({
          marginLeft: -(offset)
        });
      });
      
      // 同步hover效果
      $(obj).find('tbody tr').hover(function () {
        var index = $(this).index();
        $(this).siblings().removeClass('hover');
        $(this).parents('.mfixed-body').siblings('.mfixed-columns').find('tbody tr').removeClass('hover');
        $(this).parents('.mfixed-body').siblings('.mfixed-columns').find('tbody tr').eq(index).addClass('hover');
      }, function () {
        $(this).siblings().removeClass('hover');
        $(this).parents('.mfixed-body').siblings('.mfixed-columns').find('tbody tr').removeClass('hover');
      });
      $(obj).parents('.mfixed-body').siblings('.mfixed-columns').find('tbody tr').hover(function () {
        var index = $(this).index();
        $(this).siblings().removeClass('hover');
        $(this).parents('.mfixed-columns').siblings('.mfixed-body').find('tbody tr').removeClass('hover');
        $(this).parents('.mfixed-columns').siblings('.mfixed-body').find('tbody tr').eq(index).addClass('hover');
      }, function () {
        $(this).siblings().removeClass('hover');
        $(this).parents('.mfixed-columns').siblings('.mfixed-body').find('tbody tr').removeClass('hover');
      });
    };

    var start = function() {
      el.each(function() {
        build_structure(this);
      });
    }

    $(window).resize(function() {
      start();
    });

    return start();
  }
})(jQuery);