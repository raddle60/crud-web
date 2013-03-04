// trim文本框
$("form input[type=text]").blur(function() {
	$(this).val($.trim($(this).val()));
});