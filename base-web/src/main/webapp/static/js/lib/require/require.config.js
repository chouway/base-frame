var App = App || {};
App._j_l = App["ctx"] + "/static/js/lib/";
App._j_l_j = App._j_l + "jquery/";
App._j_l_r = App._j_l + "require/";
App._j_l_u = App._j_l + "underscore/";

App._j_p = App["ctx"] + "/static/js/plugins/";
App._j_p_ajxu = App._j_p + "jquery-ajaxfileupload/";
App._j_p_pop = App._j_p + "jquery-paypop/";
App._j_p_zc = App._j_p + "jquery-zclip/";
App._j_p_pg = App._j_p + "jquery-pagination/";
App._j_p_tf = App._j_p + "jquery-transform/";
App._j_p_fm = App._j_p + "jquery-form/";
App._j_p_mf = App._j_p + "jquery-moneyfrm/";
App._j_p_mb = App._j_p + "msgbox/";
App._j_p_dp = App._j_p + "datepicker/";
App._j_p_ec = App._j_p + "echarts/";
App._j_p_lt = App._j_p + "jquery-laytable/";
App._j_p_rsa = App._j_p + "rsa/";

App._j_c = App["ctx"] + "/static/js/common/";

var require = {
		  "map": {
		        "*": {
		            "css": App["isDebug"] ? App._j_l_r + "css/css.js" : App._j_l_r + "css/css.min.js?v=" + App["staticVersion"]
		        }
		    },
		"shim": {
	        "jQuery": {
	            "deps": []
	        },
	        "jQueryForm": {
	            "deps": ["jQuery"]
	        },
	        "underScore": {
	        	"deps": []
	        },
	        "payPop": {
	            "deps": ["jQuery", "css!" + App._j_p_pop + "skins/jquery.paypop"]
	        },
	        "zClip": {
	            "deps": ["jQuery"]
	        },
	        "pagination": {
	            "deps": ["jQuery"]
	        },
	        "transform": {
	            "deps": ["jQuery", "css!" + App._j_p_tf + "skins/jquery.transform"]
	        },
	        "msgBox": {
	            "deps": ["jQuery", "css!" + App._j_p_mb + "skins/msgbox"]
	        },
	        "datePicker": {
	            "deps": App["isDebug"] ? ["jQuery", "css!" + App._j_p_dp + "skin/WdatePicker"] :
	                ["jQuery", "css!" + App._j_p_dp + "skin/WdatePicker"]
	        },
	        "ajaxFileUpload": {
	            "deps": ["jQuery", "jQueryForm", "payPop", "msgBox"]
	        },
	        "moneyFrm": {
	            "deps": ["jQuery"]
	        },
	        "common": {
	            "deps": ["jQuery", "payPop", "msgBox", "pagination"]
	        },
	        "echarts": {
	            "deps": [""]
	        },
	        "layTable": {
	            "deps": ["jQuery", "css!" + App._j_p_lt + "skins/laytable"]
	        },
	        "rsa": {
	            "deps": [""]
	        }
		},
	    "paths": {
	        "jQuery": ((App["isDebug"]) ? App._j_l_j + "jquery" :
	        App._j_l_j + "jquery.min.js?v=" + App["staticVersion"]),
	        
	        "underScore": ((App["isDebug"]) ? App._j_l_u + "underscore" :
	            App._j_l_u + "underscore-min.js?v=" + App["staticVersion"]), 
	            
	        "payPop": ((App["isDebug"]) ? App._j_p_pop + "jquery.paypop" :
			        App._j_p_pop + "jquery.paypop.min.js?v=" + App["staticVersion"]),
			        
			"zClip": ((App["isDebug"]) ? App._j_p_zc + "jquery.zclip" :
				        App._j_p_zc + "jquery.zclip.min.js?v=" + App["staticVersion"]),

			"pagination": ((App["isDebug"]) ? App._j_p_pg + "jquery.myPagination" :
					        App._j_p_pg + "jquery.myPagination.min.js?v=" + App["staticVersion"]),
		     
			 "transform": ((App["isDebug"]) ? App._j_p_tf + "jquery.transform" :
						        App._j_p_tf + "jquery.transform.min.js?v=" + App["staticVersion"]),
				        
		    "jQueryForm": ((App["isDebug"]) ? App._j_p_fm + "jquery.form" :
				        App._j_p_fm + "jquery.form.min.js?v=" + App["staticVersion"]),
			        
			"msgBox": ((App["isDebug"]) ? App._j_p_mb + "msgbox" :
				        App._j_p_mb + "msgbox.min.js?v=" + App["staticVersion"]),
				        
			"datePicker" : ((App["isDebug"]) ? App._j_p_dp + "WdatePicker" :
					        App._j_p_dp + "WdatePicker.js?v=" + App["staticVersion"]),
					        
			"ajaxFileUpload": ((App["isDebug"]) ? App._j_p_ajxu + "jquery.ajaxfileupload" :
		        App._j_p_ajxu + "jquery.ajaxfileupload.min.js?v=" + App["staticVersion"]), 
	        
		    "moneyFrm": ((App["isDebug"]) ? App._j_p_mf + "jquery.moneyfrm" :
			        App._j_p_mf + "jquery.moneyfrm.min.js?v=" + App["staticVersion"]),    
		        
	        "common": ((App["isDebug"]) ? App._j_c + "common" :
	        	App._j_c + "common.min.js?v=" + App["staticVersion"]),
	        	
	        "echarts":  App._j_p_ec + "echarts-all",
	        
	        "rsa":  App._j_p_rsa + "rsa",
	        
	        "layTable": ((App["isDebug"]) ? App._j_p_lt + "jquery.laytable" :
		        App._j_p_lt + "jquery.laytable.min.js?v=" + App["staticVersion"])
	     }

    }