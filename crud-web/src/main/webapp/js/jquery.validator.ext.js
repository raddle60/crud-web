/**
 * 扩展jquery验证器
 */
/**   从rule里取消息   **/
$.validator.prototype.orgCustomMessage = $.validator.prototype.customMessage;
$.validator.prototype.customMessage = function( name, method ) {
	var m = this.settings.rules[name];
	var ruleParam = m && (m.constructor === String ? m : m[method]);
    if(ruleParam != null && ruleParam.msg){
    	var message = "";
    	if ( typeof ruleParam.msg === "function" ) {
			message = ruleParam.msg.call(ruleParam);
			if(message == null || message == ""){
				alert("msg方法必须要返回错误提示");
			}
    	} else {
    		message = ruleParam.msg;
    	}
        //  替换消息中的变量${xxx}或{xxx}
        var placeHolder = /\$?\{(\w+)\}/;
        if(placeHolder.test(message)){
            return message.replace(placeHolder,function(group0, group1){
                return ruleParam[group1];
            });
        }
        return message;
    } else {
        return this.orgCustomMessage(name, method);
    }
}
/**  修改显示逻辑
 *  label 一直都是错误元素,不包含wrapper
 *  增加error错误回调
**/
$.validator.prototype.showLabel = function(element, message) {
	var label = this.errorsFor( element );
	if ( label.length ) {
		// refresh error/success class
		label.removeClass( this.settings.validClass ).addClass( this.settings.errorClass );

		// check if we have a generated label, replace the message then
		if ( label.attr("generated") ) {
			label.html(message);
		}
	} else {
		// create label
		label = $("<" + this.settings.errorElement + "/>")
			.attr({"for":  this.idOrName(element), generated: true})
			.addClass(this.settings.errorClass)
			.html(message || "");
		var orgLabel = label; // 添加的代码
		if ( this.settings.wrapper ) {
			// make sure the element is visible, even in IE
			// actually showing the wrapped element is handled elsewhere
			label = label.hide().show().wrap("<" + this.settings.wrapper + "/>").parent();
		}
		if ( !this.labelContainer.append(label).length ) {
			if ( this.settings.errorPlacement ) {
				this.settings.errorPlacement(label, $(element) );
			} else {
			label.insertAfter(element);
			}
		}
		// 恢复成label , 添加的代码
		label = orgLabel;
	}
	if (!message && this.settings.success) {
		label.text("");
		if (typeof this.settings.success === "string") {
			label.addClass(this.settings.success);
		} else {
			this.settings.success(label, element);
		}
	} else if (message && this.settings.error) { // 添加的代码
		if (typeof this.settings.error === "error") {
			label.addClass(this.settings.error);
		} else {
			this.settings.error(label, element);
		}
	}
	this.toShow = this.toShow.add(label);
}
/************   一些通用的带消息的验证器   **********/
/** 从jquery复制,增加消息**/
$.validator.addMethod("msg_required", function(value, element, param) {
	// check if dependency is met
	if ( !this.depend(param, element) ) {
		return "dependency-mismatch";
	}
	if ( element.nodeName.toLowerCase() === "select" ) {
		// could be an array for select-multiple or a string, both are fine this way
		var val = $(element).val();
		return val && val.length > 0;
	}
	if ( this.checkable(element) ) {
		return this.getLength(value, element) > 0;
	}
	return $.trim(value).length > 0;
}, '不能为空');

$.validator.addMethod("msg_minlength", function(value, element, param) {
	if (!param.minlength) {
		alert("缺少验证属性minlength");
		return false;
	}
	var length = $.isArray( value ) ? value.length : this.getLength($.trim(value), element);
	return this.optional(element) || length >= param.minlength;
}, '长度不能小于{minlength}');

$.validator.addMethod("msg_maxlength", function(value, element, param) {
	if (!param.maxlength) {
		alert("缺少验证属性maxlength");
		return false;
	}
	var length = $.isArray( value ) ? value.length : this.getLength($.trim(value), element);
	return this.optional(element) || length >= param.maxlength;
}, '长度不能大于{maxlength}');

$.validator.addMethod("msg_rangelength", function(value, element, param) {
	if (!param.minlength) {
		alert("缺少验证属性minlength");
		return false;
	}
	if (!param.maxlength) {
		alert("缺少验证属性maxlength");
		return false;
	}
	var length = $.isArray( value ) ? value.length : this.getLength($.trim(value), element);
	return this.optional(element) || ( length >= param.minlength && length <= param.maxlength );
}, '长度必须介于{minlength}和{maxlength}之间');

$.validator.addMethod("msg_min", function(value, element, param) {
	if (!param.min) {
		alert("缺少验证属性min");
		return false;
	}
	return this.optional(element) || value >= param.min;
}, '不能小于{min}');

$.validator.addMethod("msg_max", function(value, element, param) {
	if (!param.max) {
		alert("缺少验证属性max");
		return false;
	}
	return this.optional(element) || value <= param.max;
}, '不能大于{max}');

$.validator.addMethod("msg_range", function(value, element, param) {
	if (!param.min) {
		alert("缺少验证属性min");
		return false;
	}
	if (!param.max) {
		alert("缺少验证属性max");
		return false;
	}
	return this.optional(element) || ( value >= param.min && value <= param.max );
}, '必须介于{min}和{max}之间');

$.validator.addMethod("msg_digits", function(value, element, param) {
	return this.optional(element) || /^\d+$/.test(value);
}, '不是数字');

$.validator.addMethod("msg_mobile", function(value, element, param) {
	return this.optional(element) || /^1[3|4|5|8][0-9]\d{8,9}$/.test(value);
}, '手机号码格式不正确');

$.validator.addMethod("msg_email", function(value, element, param) {
	// contributed by Scott Gonzalez: http://projects.scottsplayground.com/email_address_validation/
	return this.optional(element) || /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))$/i.test(value);
}, '邮箱地址格式不正确');

$.validator.addMethod("msg_idcard", function(value, element, param) {
	return this.optional(element) || /^(\d{15}$|^\d{18}$|^\d{17}(\d|X|x))$/.test(value);
}, '身份证号码格式不正确');

$.validator.addMethod("msg_equalTo", function(value, element, param) {
	if (!param.target) {
		alert("缺少验证属性target");
		return false;
	}
	var target = $(param.target);
	return value === target.val()
}, '值不相等');

$.validator.addMethod("msg_regex", function(value, element, param) {
	if (!param.regex) {
		alert("缺少验证属性regex");
		return false;
	}
	if (typeof param.regex.test != "function") {
		alert("regex不是正则表达式");
		return false;
	}
	return this.optional(element) || param.regex.test(value);
}, '格式不匹配');

$.validator.addMethod("msg_depend", function(value, element, param) {
	if (!param.depend) {
		alert("缺少验证属性depend");
		return false;
	}
	if ($(param.depend).length == 0) {
		return false;
	}
	if (typeof param.depend == "function") {
		return this.optional(element) || param.depend(value, element, param);
	} else {
		if (this.pending[$(param.depend)[0].name]) {
			return false;
		}
		return this.optional(element) || this.check($(param.depend)[0]);
	}
}, '依赖项校验失败');
$.validator.addMethod("msg_remote", function(value, element, param) {
	if (!param.url) {
		alert("缺少验证属性url");
		return false;
	}
	if (!param.data) {
		alert("缺少验证属性url");
		return false;
	}
	if ( this.optional(element) ) {
		return "dependency-mismatch";
	}
	var previous = this.previousValue(element);
	if (this.pending[element.name]) {
		return "pending";
	}
	if (previous.old === value) {
		if(!previous.valid){
			param.msg = previous.message;
		}
		return previous.valid;
	}
	previous.old = value;
	var url = "";
	if (typeof param.url == "function") {
		url = param.url(value, element, param);
	} else {
		url = param.url;
	}
	var data = {};
	if (typeof param.data == "function") {
		data = param.data(value, element, param);
	} else {
		data = param.data;
	}
	data[element.name] = value;
	var validator = this;
	this.startRequest(element);
	var errorMsgs = {};
	errorMsgs[element.name] = "验证中,请稍后";
	this.invalid[element.name] = true;
	this.showErrors(errorMsgs);
	if (typeof param.start == "function") {
		param.start(value, element, param);
	}
	if (param.disableWhenPending) {
		element.disabled = true;
	}
	$.ajax($.extend(true, {
		url: url,
		mode: "abort",
		port: "validate" + element.name,
		dataType: "json",
		data: data,
		timeout : param.timeout || 30000,
		success: function(response) {
			var valid = response.isSuccess || false;
			if ( valid ) {
				var submitted = validator.formSubmitted;
				validator.prepareElement(element);
				validator.formSubmitted = submitted;
				validator.successList.push(element);
				delete validator.invalid[element.name];
				validator.showErrors();
				if (typeof param.success == "function") {
					param.success(value, element, param, response);
				}
			} else {
				var errors = {};
				var message = validator.defaultMessage( element, "msg_remote" );
				errors[element.name] = response.errorMsg || ($.isFunction(param.msg) ? param.msg(param) : param.msg) || message;
				previous.message = errors[element.name];
				validator.invalid[element.name] = true;
				validator.showErrors(errors);
				if (typeof param.fail == "function") {
					param.fail(value, element, param, response);
				}
			}
			previous.valid = valid;
			validator.stopRequest(element, valid);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			var errors = {};
			errors[element.name] = "发送验证失败,请重试";
			validator.invalid[element.name] = true;
			validator.showErrors(errors);
			previous.valid = false;
			previous.message = errors[element.name];
			validator.stopRequest(element, false);
			if (typeof param.error == "function") {
				param.error(value, element, param, textStatus, errorThrown);
			}
		},
		complete : function(jqXHR, textStatus) {
			if (param.disableWhenPending) {
				element.disabled = false;
			}
			if (typeof param.complete == "function") {
				param.complete(value, element, param, textStatus);
			}
		}
	}, param.ajaxOptions || {} ));
	return "pending";
}, 'ajax验证失败');
