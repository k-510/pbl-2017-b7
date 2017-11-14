//request_idの取得
var arg = new Object;
var url = location.search.substring(1).split('&');
for(i=0; url[i]; i++) {
	var k = url[i].split('=');
	arg[k[0]] = k[1];
}
var request_id = arg.request_id;

//メッセージの投稿
$('#submit').click(function() {
	$.ajax({
		type: 'POST',
		url: '/api/user/requests/' + request_id + '/chats',
		data: {body: $('#message').val()}
	})
});

//メッセージの取得
setInterval(function() {
	$.ajax({
		type: 'GET',
		url:'/api/user/requests/' + request_id + '/chats',
		success: function(json){
			$('#board').empty();
			dataArray = json.comments;
			$.each(dataArray, function(i){
			$('#board').append('<p>' + dataArray[i].body + '</p>');		
			})
		}
	})
}, 1000);

//shop_idの取得
$.ajax({
	type: 'GET',
	url:'/api/user/requests/' + request_id,
	success: function(json){
		var shop_id = json.shop_id;
	}
})

//map
$.ajax({
	type: 'GET',
	url:'http://api.gnavi.co.jp/RestSearchAPI/20150630/?keyid=2a35bca1f2e78679ef339c42db623e71&format=json&id=' + shop_id,
	dataType: 'jsonp',
	success: function(json){
		//店舗の住所の取得
		var address = json.rest.address;

		var geocoder = new google.maps.Geocoder();
		geocoder.geocode({
			'address': address
		}, function(result, status){
			if(status == google.maps.GeocoderStatus.OK){
				//住所→緯度経度へ変換
				var latlng = result[0].geometry.location;
				var options = {
						zoom: 19,
						center: latlng,
						mapTypeId: google.maps.MapTypeId.ROADMAP
				};
				//mapの表示
				var map = new google.maps.Map(document.getElementById('map'), options);
				//マーカーの表示
				var marker = new google.maps.Marker({
					position: latlng,
					map: map
				});
			} else {
				alert('エラーです！')
			}
		});
	}
})