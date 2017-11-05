$(function() {
	$('#deadLine').change(function() {
		var arrivalTime = $('input[name="arrivalTime"]').val();
		var deadLine = $('input[name="deadLine"]').val();
		if(deadLine != '' && arrivalTime !== ''){
			if(arrivalTime < deadLine) {
				alert('締切時刻≦到着時刻に設定してください！');
			}
		}
	});

	$('#arrivalTime').change(function() {
		var arrivalTime = $('input[name="arrivalTime"]').val();
		var deadLine = $('input[name="deadLine"]').val();
		if(deadLine != '' && arrivalTime != ''){
			if(arrivalTime < deadLine) {
				alert('締切時刻≦到着時刻に設定してください！');
			}
		}
	});
});