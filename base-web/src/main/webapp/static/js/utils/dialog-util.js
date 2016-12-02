/**
 * 依赖于 artDialog, msgBox
 */

var App = App || {}, dialogUtil = {
    /**
     * 普通对话框
     *  @param btn 当前点击对象 
     *         dia 对话框HTML
     *         initCallback 初始化回调
     *         closeCallback 关闭对话框回调
     */
		
    dialog : function (btn, dia, initCallback, closeCallback) {
         var myDialog = $.dialog({
        	content : dia[0],
        	init : initCallback && initCallback() || $.noop,
        	close : closeCallback && closeCallback() || $.noop
         });
    },
    /**
     * 普通对话框关闭
     *  @param btn 当前点击对象 
     */
    
    dialogClose : function (btn) {
    	$(btn).parents('.aui_dialog').find('.aui_close').trigger('click');
   },
   /**
    * 弹出对话框
    *  @param msg 提示内容 
    *         okCallback 点击确定回调，不回调点击直接关闭
    *         config 暂留可扩展
    */
   
   alert : function(msg, okCallback, config){
	   var el = $('#yile-alert-pop');
		if(el.length === 0) {
			var tCon = ''
				+'<div id="yile-alert-pop" class="pop pop-zy-delete w430">'
				+'<h2> 提示 <em class="btn-pop-close"></em></h2>'
				+'<div class="pop-cont ta-c">'
				+'<p class="pop-tip pt15 yile-alert-msg">'+ msg +'</p>'
				+' </div>'
				+'<div class="btn-box ta-r">'
				+'<a href="#" class="btn-reset mr0 btn-alert-ok"> 确定 </a>'
				+'</div>'
				+'</div>';
			el = $(tCon).appendTo('body');
		}else{
		    el.find('.yile-alert-msg').html(msg);
		}
		var alertDia = $.dialog({
	        content:el[0],
	        init : function(){},
	        close: $.noop,
	        fixed:true
		});
	    el.find('.btn-alert-ok').unbind().click(function(){
	        if(Object.prototype.toString.call(okCallback) == '[object Function]'){
	        	okCallback && okCallback();
	        }else{
	        	alertDia.close();
	        }
	    });
   },
   /**
    * 弹出对话框（是/否）
    *  @param msg 提示内容 
    *         okCallback 点击确定回调
    *         closeCallback 不回调点击直接关闭
    *         config 暂留可扩展
    */
   
   confirm : function(msg, okCallback, closeCallback, config){
	   var el = $('#yile-confirm-pop');
		if(el.length == 0) {
			var tCon = ''
				+'<div id="yile-confirm-pop" class="pop pop-zy-delete w430">'
				+'<h2> 提示  <em class="btn-pop-close"></em></h2>'
				+'<div class="pop-cont ta-c">'
				+'<p class="pop-tip pt15 pb15 yile-confirm-msg"> '+ msg +' </p>'
				+'</div>'
				+'<div class="btn-box ta-r">'
				+'<a href="#" class="btn-reset btn-confirm-close btnDialogClose">取消</a>'
				+'<a href="#" class="btn-submit mr0 btn-confirm-ok">确认</a>'
				+' </div>'
				+'</div>';
	    	el = $(tCon).appendTo('body');
		} else {
			el.find('.yile-confirm-msg').html(msg);
		}
		 $.dialog({
	        content : el[0],
	        init : function(){},
	        close: $.noop,
	        fixed:true
		});
		el.find('.btn-confirm-ok').unbind().bind("click", function() {
			okCallback && okCallback();
		});
		el.find(".btn-confirm-close").unbind().bind("click", function() {
			closeCallback && closeCallback();
		});
   },
    /**
     * 提示对话框
     * @param msg 提示框内容 
     *        type图标类型
     *          1：提示图标 
     *          4:成功图标
     *          5：失败图标
     *          6：加载中图标
     *        tiemout自动关闭时间
     *        
     * @returns ""
     */
    
    msgbox : {
    	show : function(msg, type, timeout, opts){
    		ZENG.msgbox.show(msg, type, timeout, opts);
    	},
    	hide : function(){
    		ZENG.msgbox.hide(500);
    	}
    }
};