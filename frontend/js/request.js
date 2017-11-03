$(function() {
	$('#deadline').change(function() {
		var arrival_time = $('input[name="arrival_time"]').val();
		var deadline = $('input[name="deadline"]').val();
		if(deadline != '' && arrival_time != ''){
			if(arrival_time < deadline) {
				alert('締切時刻≦到着時刻に設定してください！');
			}
		}
	});	
	
	$('#arrival_time').change(function() {
		var arrival_time = $('input[name="arrival_time"]').val();
		var deadline = $('input[name="deadline"]').val();
		if(deadline != '' && arrival_time != ''){
			if(arrival_time < deadline) {
				alert('締切時刻≦到着時刻に設定してください！');				
			}
		}
	});	
	
});