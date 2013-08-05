$(document).ready(function() {
	$("#cancel").click(function() {
		var $form = $(this).parents("form");
		$form.attr('action', 'shop.do?method=cancel');
		$form.submit();
	});
});