(function(w, $){
   var win = $(window),
       doc = $(document);
   var settings = {
       type : 1,
       popId : 'ztr-paypop',
       title : '提示',
       content : '提交失败，请重试.',
       mask : true,
       loadInit : function(){},
       okCallback : function(){},
       cancelCallback : function(){},
       okText : '确定',
       cancelText : '取消',
       width : 'auto',
       height : 'auto',
       zInd : 9988,
       time : 2000
   };
   var indCount = 1;
   var payPop = function(options){
       var curOps = {};
       $.extend(curOps, settings);
       if(typeof options === 'string') {
           curOps.content = options;
       } else if(Object.prototype.toString.call(options) == '[object Object]') {
           $.extend(curOps, options);
       }
         return new Dialog(curOps);
     }
   window.payPop = $.payPop = payPop;
   function Dialog(ops){
      var me = this;
      this.config = ops;
      this.init();
   }
  Dialog.prototype = {
      config : null,
      dialog : null,
      tmpls : '<div class="paypop-warp">' +
                 '<div class="paypop-header">' +
                 '<h2>提示</h2></div>' +
                 '<div class="paypop-content">' +
                 '</div>' +
                 '<div class="paypop-footer">' +
                 '</div></div>' +
                 '<div class="paypop-mask"></div>',
     init : function(){
        this.initElems();
     },
     initElems : function(){
       var that = this,
           popList = $('#' + that.config.popId),
           zInd = that.config.zInd + (indCount ++);
         if(popList.length === 0){
            if(that.config.type === 1){
                //alert
                that.dialog = $('<div>').attr({
                    'class' : 'paypop',
                    'id' : that.config.popId
                }).css({zIndex : zInd}).html(that.tmpls).prependTo('body');
                that.initAlert();
            }
            else if(that.config.type === 2){
                //confirm
            }
            else if(that.config.type === 3){
                //bigPop
                that.dialog = $('<div>').attr({
                    'class' : 'paypop'
                }).css({zIndex : zInd}).html(that.config.content).prependTo('body');
                that.initEmpty();
            }else{

            }
         }else{
             if(that.config.type === 1){
            	 that.dialog = $('<div>').attr({
                     'class' : 'paypop',
                     'id' : that.config.popId + parseInt(Math.random()*10000 + 1)
                 }).css({zIndex : zInd}).html(that.tmpls).prependTo('body');
                 that.initAlert();
             }
             else if(that.config.type === 2){

             }
             else if(that.config.type === 3){
                 that.initEmpty();
             }else{

             }
         }
     },
     initAlert : function(){
         var that = this,
             _til = that.dialog.find('.paypop-header h2'),
             _con = that.dialog.find('.paypop-content'),
             _footer = that.dialog.find('.paypop-footer'),
             _mask = that.dialog.find('.paypop-mask'); 
         _til.html(that.config.title);
         _con.html(that.config.content);
         $('<a>', {
             href : '#',
             text : that.config.okText
         }).on("click", function() {
             var okCallback = that.config.okCallback;
                 that.close();
                 setTimeout(function(){
                	 okCallback && okCallback(); 
                 }, 200);
             return false;
         }).css({'margin-left' : '0'}).addClass('paypop-btn-ok').prependTo(_footer);
         _mask.on("click", function(){
        	 that.close();
        	return false; 
         });
         that.position();
     },
    initConfirm : function(){

    },
    initEmpty : function(){
        var that = this,
            wWidth = win.width(),
            popList = $('#' + that.config.popId);
            that.dialog = popList;
            that.dialog.css({
                "left" : "-"+ wWidth +"px",
                "display" : "block"
            }).stop().animate({left: '0'}, "normal");
            that.dialog.find(".btn-pop-close").on("click", function(){
            that.dialog.stop().animate({left: "-"+ wWidth +"px"}, "100", function(){
                $(this).hide();
            });
            return false;
        });
    },
    position : function(){
        var that = this,
            winWidth = win.width(),
            winHeight = win.height(),
            scrollTop = 0,
            popWarp = that.dialog.find('.paypop-warp');
            popWarp.width(that.config.width).height(that.config.height);
        var _mtop = (winHeight - popWarp.outerHeight(true)) / 2 + scrollTop -80,
            mtop = Math.max(5, _mtop);
            popWarp.css({
            left : (winWidth - popWarp.outerWidth(true)) / 2,
            top : mtop
        }).fadeIn("300");
    },
    close : function() {
          var that = this,
              wWidth = win.width(),
              popWarp = that.dialog.find('.paypop-warp');
              popMask = that.dialog.find('.paypop-mask');
              popMask.fadeOut("50");
              popWarp.animate({left: "-"+ wWidth +"px"}, "200", function(){
              that.dialog.remove();
        });
      }
  };
 })(window, jQuery);