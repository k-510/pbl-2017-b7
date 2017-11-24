//$(function() {
	$('#limit').change(function() {
		var arrivalTime = $('input[name="datetime"]').val();
		var deadLine = $('input[name="deadline"]').val();
		if(deadLine != '' && arrivalTime !== ''){
			if(arrivalTime < deadLine) {
				alert('締切時刻≦到着時刻に設定してください！');
			}
		}
	});

	$('#arrivalTime').change(function() {
		var arrivalTime = $('input[name="datetime"]').val();
		var deadLine = $('input[name="deadline"]').val();
		if(deadLine != '' && arrivalTime != ''){
			if(arrivalTime < deadLine) {
				alert('締切時刻≦到着時刻に設定してください！');
			}
		}
	});
	
	// form値のPOST
	$(':submit').click(function(){
		tags = []
		$("[name=checkbox]:checked").each(function() {
			tags.push($(this).val());
		});
		
		$.ajax({
			type: 'POST',
			 url: '/pbl-2017-b7/api/user/requests/',
			  headers: {
			    'Authorization': 'Session ' + Cookies.get('kuishiro-session'),
			  },
			  traditional: true,
			  data: {
			    deadline: $('#limit').val().replace("T"," ") + ":00",
			    datetime: $('#arrivalTime').val().replace("T"," ") + ":00",
			    shop_id: $('#shopId').val(),
			    budget: Number($('#cost').val()),
			    tag_id: tags,
			    keyword: ""
			  }	
		}).done(function(data, status, xhr) {
			location.href = "my/top.html";
		});
	});	
//});