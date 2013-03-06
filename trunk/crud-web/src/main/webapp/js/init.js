$(function() {
	// trim文本框
	$("form input[type=text]").blur(function() {
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