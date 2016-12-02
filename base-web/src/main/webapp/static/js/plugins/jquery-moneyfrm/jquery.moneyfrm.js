;(function(){
	$.fn.moneyFrm=function(ops){
		var defaults = {
				redData : "data-tex",
				inteEl : "em",
				inteClass : "f28",
				pointEl : '',
	    		pointClass : ''
		};
		var settings = $.extend(defaults, ops);
		return this.each(function(){
			var me = $(this), meValue;
			if(settings.redData){
				meValue = me.attr(settings.redData);
			}else{
				meValue = me.text();
			}
			var meVal = removeDh(meValue);
			var meInte, mePoint;
				meVal = gsMoney(meVal);
				meInte = meVal.substring(0, meVal.indexOf('.'));
				mePoint = meVal.substring(meVal.indexOf('.') + 1, meVal.length);
			if(settings.inteEl){
				me.html('￥<'+ settings.inteEl +' class="'+ settings.inteClass +'">'+ meInte +'</'+ settings.inteEl +'>.'+mePoint+'');
			}else if(settings.inteEl && settings.pointEl){
				
			}else{
				me.html(''+ meInte +'.<'+ settings.pointEl +' class="'+ settings.pointClass +'">'+mePoint+'</'+ settings.pointEl +'>');	
			}
		});
       function gsMoney(val){
    	   if(val !== null && val !== "null" && /^\d+(\.\d+)?$/.test(val)){
    		   var returnVal;
    		   val == "0" ? (returnVal="0.00") : (returnVal = parseFloat(val).toFixed(2)+"",returnVal = returnVal.replace(/(\d{1,3})(?=(\d{3})+(?:$|\.))/g,"$1,"));
    		   return returnVal;
    	   }else{
    			return "0.00";
    		}
       }
      function removeDh(str){
		var reg = /,/g;
		str = str.replace(reg, "");
		return str;
      }
	};
})(jQuery);