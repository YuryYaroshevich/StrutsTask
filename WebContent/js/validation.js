$(document).ready(function() {
	$("form#update-goods").submit(function() {
		var tableRows = $(this).find('tr.good-parameters');
		var validationResult = 0;
		for (var i = 0; i < tableRows.length; i++) {
			validationResult += isGoodValid(tableRows[i]);
		}
		return validationResult == 0;
	});
});

function isGoodValid(tableRow) {
	var isValid = 0;
	var errorMsgs = [];
	var goodId = $(tableRow).attr('id');

	errorMsgs.push(producerValidation($(tableRow).find('input.producer')));
	errorMsgs.push(modelValidation($(tableRow).find('input.model')));
	errorMsgs.push(dateOfIssueValidation($(tableRow)
			.find('input.date-of-issue')));
	errorMsgs.push(colorValidation($(tableRow).find('input.color')));
	errorMsgs.push(priceValidation($(tableRow).find('input.price')));
	
	$('#error-row-' + goodId).remove();
	var $trError = $('<tr class="error" id="error-row-' + goodId
			+ '"><td></td><td></td><td></td><td></td><td></td></tr>');
	var tds = $trError.children();
	for (var i = 0; i < errorMsgs.length; i++) {
		if (errorMsgs[i] != '') {
			$(tds[i]).text(errorMsgs[i]);
			isValid = 1;
		}
	}
	
	if (isValid == 1) {
		$trError.insertAfter(tableRow);
	} 
	return isValid;
}

function producerValidation($producerInput) {
	if (isInputEmpty($producerInput)) {
		$producerInput.css('border','1px solid red');
		return emptyProducerMsg;
	}
	$producerInput.css('border','1px solid #C0C0C0');
	return '';
}

function modelValidation($modelInput) {
	if (isInputEmpty($modelInput)) {
		$modelInput.css('border','1px solid red');
		return emptyModelMsg;
	}
	$modelInput.css('border','1px solid #C0C0C0');
	
	// validate model format
	var modelFormatRegexp = /^[a-zA-Z]{2}\d{3}$/;
	if (isInputFormatWrong($modelInput, modelFormatRegexp)) {
		$modelInput.css('border','1px solid red');
		return wrongModelFormatMsg;
	}
	$modelInput.css('border','1px solid #C0C0C0');
	return '';
}

function dateOfIssueValidation($dateOfIssueInput) {
	if (isInputEmpty($dateOfIssueInput)) {
		$dateOfIssueInput.css('border','1px solid red');
		return emptyDateMsg;
	}
	$dateOfIssueInput.css('border','1px solid #C0C0C0');
	
	// validate date format
	var dateFormatRegexp = /^(0[1-9]|1\d|2\d|3[01])-(0[1-9]|1[0-2])-(\d{4})$/;
	if (isInputFormatWrong($dateOfIssueInput, dateFormatRegexp)) {
		$dateOfIssueInput.css('border','1px solid red');
		return wrongDateFormatMsg;
	}
	$dateOfIssueInput.css('border','1px solid #C0C0C0');

	// validate date range
	var dateRangeRegexp = /^(0[1-9]|1\d|2\d|3[01])-(0[1-9]|1[0-2])-(19\d{2}|2\d{3})$/;
	if (isInputFormatWrong($dateOfIssueInput, dateRangeRegexp)) {
		$dateOfIssueInput.css('border','1px solid red');
		return wrongDateRangeMsg;
	}
	$dateOfIssueInput.css('border','1px solid #C0C0C0');
	
	return '';
}

function colorValidation($colorInput) {
	if (isInputEmpty($colorInput)) {
		$colorInput.css('border','1px solid red');
		return emptyColorMsg;
	}
	$colorInput.css('border','1px solid #C0C0C0');
	return '';
}

function priceValidation($priceInput) {
	if (isInputEmpty($priceInput)) {
		$priceInput.css('border','1px solid red');
		return emptyPriceMsg;
	}
	$priceInput.css('border','1px solid #C0C0C0');
	
	// validate price format
	var priceFormatRegexp = /^\d+$|^not in stock$/;
	if (isInputFormatWrong($priceInput, priceFormatRegexp)) {
		$priceInput.css('border','1px solid red');
		return wrongPriceFormatMsg;
	}
	$priceInput.css('border','1px solid #C0C0C0');
	
	return '';
}

function isInputEmpty($input) {
	return $input.val() == '';
}

function isInputFormatWrong($input, regexp) {
	return !regexp.test($input.val());
}

