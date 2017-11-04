/**
 * javascript array[dict(name: value)] -> HTML table style
 * @param {array[dict[str:str]]} dictArr name: value dictionary Array
 * @param {array[str]} order table title order
 * @return {str} html string
 */
var dictArrayToHTMLTable = (dictArr, order) => {
    let res = '';

    res += '<tr>'
    $.each(order, (j) => {
        res += '<th>' + order[j] +'</th>';
    });
    res += '</tr>'

    $.each(dictArr, (i) => {
        //header

        //content
        res += '<tr>';
        let dict = dictArr[i];
        $.each(order, (j) => {
            res += '<td>' + dict[order[j]] +'</td>';
        });
        res += '</tr>'
    });
    return res;
};

// ready...
$( () => {
    let sampleDictArr = {
        'metTime': '2017-10-27 12:00',
        'restaurantName': '寿司',
        'place': '<a href="https://www.google.co.jp/maps/place/%E5%A4%A7%E9%98%AA%E5%A4%A7%E5%AD%A6%E4%B8%AD%E4%B9%8B%E5%B3%B6%E3%82%BB%E3%83%B3%E3%82%BF%E3%83%BC/@34.6927198,135.4882802,17z/data=!3m1!4b1!4m5!3m4!1s0x6000e6f427728823:0xd361d2a346f79d9c!8m2!3d34.6927154!4d135.4904689">大阪大学　中之島センター</a>',
        'partner': '相手のページへのリンク',
        'deadline': '2017-10-25 12:00',
        'budget': '&yen; 3000',
        'cancel': '取り消すボタン'
    };
    order = [
        'metTime',
        'restaurantName',
        'place',
        'partner',
        'deadline',
        'budget',
        'cancel',
    ];
    console.log(dictArrayToHTMLTable([sampleDictArr, sampleDictArr], order));
}
);