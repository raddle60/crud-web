$(function() {
	// trim文本框
	$("form input[type=text],form textarea").blur(function() {
		$(this).val($.trim($(this).val()));
	});
	// table颜色
	$("table.list tr:odd").addClass("odd");
	$("table.list tr:even").addClass("even");
	$("table.list tr").hover(function() {
		$(this).addClass("over")
	}, function() {
		$(this).removeClass("over")
	});
});
// //////////////
function ajaxSubmit(form) {
	if ($(form).data("submiting")) {
		alert("处理中，请不要重复提交");
	} else {
		$(form).data("submiting", true)
		jQuery.ajax({
			url : $(form).attr("action"),
			type : $(form).attr("method"),
			data : $(form).serialize(),
			dataType : "json",
			success : function(data) {
				if(data.success){
					if(window.opener != null && window.opener.location && window.opener.location.reload){
						try{
							window.opener.location.reload();
						}catch(e){
							alert("刷新父页面失败,"+e);
						}
					}
				}
				alert(data.message);
				if(data.success){
					if(window.opener != null && window.opener.location && window.opener.location.reload){
						try{
							window.close();
						}catch(e){
							alert("关闭本页面失败,"+e);
						}
					}
				}
			},
			complete : function() {
				$(form).data("submiting", false);
			}
		});
	}
}