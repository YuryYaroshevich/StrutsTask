$(document).ready(function() {
	$("form#update-goods").submit(function() {
		alert("s");
		var $tableRows = $(this).find('tr.good-parameters');
		var validationResult = true;
		for (var i = 0; i < $tableRows.length; i++) {
			validationResult = validationResult && isGoodValid($tableRows[i]);
		}
		return validationResult;
	});
});

function isGoodValid($tableRow) {
	//alert($tableRow.find('input.producer'));
	var $producerInput = $tableRow.find('input.producer');
	alert($producerInput);
	if (!isProducerValid($producerInput)) {
		return false;
	}
	var $modelInput = $tableRow.find('input.model');
	if (!isModelValid($modelInput)) {
		return false;
	}
	var $dateOfIssueInput = $tableRow.find('input.date-of-issue');
	if (!isDateOfIssueValid($dateOfIssueInput)) {
		return false;
	}
	var $colorInput = $tableRow.find('input.color');
	if (!isColorValid($colorInput)) {
		return false;
	}
	var $priceInput = $tableRow.find('input.price');
	if (!isPriceValid($priceInput)) {
		return false;
	}
	return true;
}

function isProducerValid($producerInput) {
	alert(emptyProducerMsg);
	return !isInputEmpty($producerInput, emptyProducerMsg, 'empty-producer');
}

function isModelValid($modelInput) {
	if (isInputEmpty($modelInput, emptyModelMsg, 'empty-model')) {
		return false;
	}

	// validate model format
	var errorMsgClass = 'wrong-model-format';
	var modelFormatRegexp = /[a-zA-Z]{2}\d{3}/;
	if (isInputFormatWrong($modelInput, 
			modelFormatRegexp, wrongModelFormatMsg, errorMsgClass)) {
		return false;
	}
	
	return true;
}

function isDateOfIssueValid($dateOfIssueInput) {
	if (isInputEmpty($dateOfIssueInput, emptyDateMsg, 'empty-date')) {
		return false;
	}
	
	// validate date format
	var errorMsgClass = 'wrong-date-format';
	var dateFormatRegexp = /(0[1-9]|1\\d|2\\d|3[01])-(0[1-9]|1[0-2])-(\\d{4})/;
	if (isInputFormatWrong($dateOfIssueInput, 
			dateFormatRegexp, wrongDateFormatMsg, errorMsgClass)) {
		return false;
	}
	
	// validate date range
	errorMsgClass = 'wrong-date-range';
	var dateRangeRegexp = /(0[1-9]|1\\d|2\\d|3[01])-(0[1-9]|1[0-2])-(19\\d{2}|2\\d{3})/;
	if (isInputFormatWrong($dateOfIssueInput, 
			dateRangeRegexp, wrongDateRangeMsg, errorMsgClass)) {
		return false;
	}
	
	return true;
}

function isColorValid($colorInput) {
	return !isInputEmpty($colorInput, emptyColorMsg, 'empty-color');
}

function isPriceValid($priceInput) {
	alert("price validation");
	if (isInputEmpty($priceInput, emptyPriceMsg, 'empty-price')) {
		return false;
	}
	
	//validate price format
	errorMsgClass = 'wrong-price-format';
	var priceFormatRegexp = /\d+|not in stock/;
	if (isInputFormatWrong($priceInput, 
			priceFormatRegexp, wrongPriceFormatMsg, errorMsgClass)) {
		return false;
	}
	
	return true;
}

function isInputEmpty($input, msg, errorMsgClass) {
	var $tableRow = $input.parent().parent();
	if ($input.val() == '') {
		if ($tableRow.find('.'+errorMsgClass).length == 0) {
			$msgTD = prepareMsgElem(msg, errorMsgClass);
			$msgTD.appendTo($tableRow);
		}
		return true;
	} else {
		$tableRow.find('.'+errorMsgClass).remove();
	}
	return false;
}

function isInputFormatWrong($input, regexp, msg, errorMsgClass) {
	var $tableRow = $input.parent().parent();
	if (!regexp.test($input.val())) {
		if ($tableRow.find('.'+errorMsgClass).length == 0) {
			$msgTD = prepareMsgElem(msg, errorMsgClass);
			$msgTD.appendTo($tableRow);
		}
		return true;
	} else {
		$tableRow.find('.'+errorMsgClass).remove();
	}
	return false;
}

function prepareMsgElem(msg, msgClass) {
	return $('<td class="' + msgClass + ' error">'+ msg +'</td>');
}

