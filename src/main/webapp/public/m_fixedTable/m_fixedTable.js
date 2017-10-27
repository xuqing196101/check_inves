(function($) {
  $.fn.m_fixedTable = function(options) {
    //默认参数
    var defaults = {
      fixedNumber: 1
    };

    //覆盖默认参数
    var opts = $.extend(defaults, options);
    var el = this;
    var fixedHeader = '<div class="mfixed-header"></div>';
    var fixedHeader_columns = '<div class="mfixed-header-columns"></div>';
    var fixedColumns = '<div class="mfixed-columns"><table class="'+ $(el).attr('class') +'"><tbody></tbody></table></div>';
    
    var build_structure = function () {
      // 初始化结构
      if (!$(el).parents().hasClass('mfixed-main')) {
        $(el).wrapAll('<div class="mfixed-main"></div>');
        $(el).before(fixedHeader + fixedHeader_columns);
        $(el).after(fixedColumns);
        $(el).wrapAll('<div class="mfixed-body"></div>');
      }
      
      // 初始化保存html的变量
      var header_html = '';
      var header_columns_html = '';
      var columns_html = '';
      
      // 构造表头html
      $(el).parent().siblings('.mfixed-header').css({
        width: $('.mfixed-main').width()
      });
      $(el).find('thead th').each(function (index) {
        if ($(this).attr('style') != undefined) {
          header_html += '<th style="'+ $(this).attr('style') +'width: '+ $(this).outerWidth(true) +'px; height: '+ $(this).outerHeight(true) +'">'+ $(this).html() +'</th>';
          if (index < opts.fixedNumber) {
            header_columns_html += '<th style="'+ $(this).attr('style') +'width: '+ $(this).outerWidth(true) +'px; height: '+ $(this).outerHeight(true) +'px">'+ $(this).html() +'</th>';
          }
        } else {
          header_html += '<th style="width: '+ $(this).outerWidth(true) +'px; height: '+ $(this).outerHeight(true) +'">'+ $(this).html() +'</th>';
          if (index < opts.fixedNumber) {
            header_columns_html += '<th style="width: '+ $(this).outerWidth(true) +'px; height: '+ $(this).outerHeight(true) +'px">'+ $(this).html() +'</th>';
          }
        }
      });
      header_html = '<table class="'+ $(el).attr('class') +'" style="width: '+ $(el).find('thead').width() +'px"><thead>'+ header_html +'</thead></table>';
      header_columns_html = '<table class="'+ $(el).attr('class') +'"><thead>'+ header_columns_html +'</thead></table>';
      
      // 构造冻结列html
      var columns_startIndex = 0;
      if ($(el).parent().siblings('.mfixed-columns').find('tbody').html() != '') {
        columns_startIndex = $(el).parent().siblings('.mfixed-columns').find('tbody tr').length;
      }
      $(el).find('tbody tr').each(function (index) {
        var td_html = '';
        if (columns_startIndex != 0) {
          // console.log(columns_startIndex+','+index);
          if (index >= columns_startIndex) {
            $(this).find('td').each(function (td_index) {
              if (td_index < opts.fixedNumber) {
                // console.log($(this).html());
                td_html += '<td>'+ $(this).html() +'</td>';
              }
            });
            columns_html += '<tr>'+ td_html +'</tr>';
          }
        } else {
          $(this).find('td').each(function (td_index) {
            if (td_index < opts.fixedNumber) {
              td_html += '<td>'+ $(this).html() +'</td>';
            }
          });
          columns_html += '<tr>'+ td_html +'</tr>';
        }
      });
      $(el).parent().siblings('.mfixed-columns').css({
        top: $(el).find('thead th').outerHeight()
      });
      
      // 填充生成的html
      $(el).parent().siblings('.mfixed-header').html(header_html);
      $(el).parent().siblings('.mfixed-header-columns').html(header_columns_html);
      $(el).parent().siblings('.mfixed-columns').find('tbody').append(columns_html);
      
      // 添加冻结列宽度高度
      $(el).find('tbody tr').each(function (index) {
        $(this).find('td').each(function (td_index) {
          if (td_index < opts.fixedNumber) {
            $(el).parent().siblings('.mfixed-columns').find('tbody tr').eq(index).find('td').eq(td_index).css({
              width: $(this).outerWidth(),
              height: $(this).outerHeight()
            });
          }
        });
      });
    };

    var start = function() {
      el.each(function() {
        build_structure();
        
        // 滚动超过表头位置，表头和冻结列跟随
        $(window).scroll(function () {
          if ($(window).scrollTop() >= $(el).offset().top) {
            $(el).parent().siblings('.mfixed-header').css({
              position: 'fixed',
              left: 'auto'
            });
            
            $(el).parent().siblings('.mfixed-header-columns').css({
              position: 'fixed',
              left: 'auto'
            });
          } else {
            $(el).parent().siblings('.mfixed-header').css({
              position: 'absolute',
              left: 0
            });
            
            $(el).parent().siblings('.mfixed-header-columns').css({
              position: 'absolute',
              left: 0
            });
          }
        });
        
        
        // 左右滚动表头跟随表格
        $(el).parent().bind('scroll', function () {
          var offset = $(this).scrollLeft();
          
          $(el).parent().siblings('.mfixed-header').find('table').css({
            marginLeft: -(offset)
          });
        });
        
        // 同步hover效果
        $('.mfixed-body tbody tr').hover(function () {
          var index = $(this).index();
          $(this).siblings().removeClass('hover');
          $(this).parents('.mfixed-body').siblings('.mfixed-columns').find('tbody tr').removeClass('hover');
          $(this).parents('.mfixed-body').siblings('.mfixed-columns').find('tbody tr').eq(index).addClass('hover');
        }, function () {
          $(this).siblings().removeClass('hover');
          $(this).parents('.mfixed-body').siblings('.mfixed-columns').find('tbody tr').removeClass('hover');
        });
        $('.mfixed-columns tbody tr').hover(function () {
          var index = $(this).index();
          $(this).siblings().removeClass('hover');
          $(this).parents('.mfixed-columns').siblings('.mfixed-body').find('tbody tr').removeClass('hover');
          $(this).parents('.mfixed-columns').siblings('.mfixed-body').find('tbody tr').eq(index).addClass('hover');
        }, function () {
          $(this).siblings().removeClass('hover');
          $(this).parents('.mfixed-columns').siblings('.mfixed-body').find('tbody tr').removeClass('hover');
        });
      });
    }

    $(window).resize(function() {
      start();
    });

    return start();
  }
})(jQuery);