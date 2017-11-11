$('#submit').click(function() {
	$.ajax({
		type: 'POST',
		url: '/appointment/api/comments',
		data: {body: $('#message').val()}
	})
});

setInterval(function() {
	$.ajax({
		type: 'GET',
		url:'/appointment/api/comments',
		success: function(json){
			$('#board').empty();
			dataArray = json.comments;
			$.each(dataArray, function(i){
			$('#board').append('<p>' + dataArray[i].body + '</p>');		
			})
		}
	})
}, 1000);