/**
 * Date -> Str method
 * @param {Date} date Date object
 * @return {str} parsed string object (yyyy/MM/dd hh:mm:ss style)
 */
function toLocaleString(date) {
    console.log(date);
    return [
        date.getFullYear(),
        date.getMonth() + 1,
        date.getDate()
    ].join('/') + ' '
        + date.toLocaleTimeString();
}

/**
 * javascript array[dict(name: value)] -> HTML table style
 * @param {array[dict[str:str]]} dictArr name: value dictionary Array
 * @param {array[str]} order table title order
 * @return {str} html string
 */
var dictArrayToHTMLTable = (title, dictArr, order) => {
    let res = '';

    res += '<tr>'
    $.each(order, (j) => {
        res += '<th>' + title[order[j]] + '</th>';
    });
    res += '</tr>'

    $.each(dictArr, (i) => {
        //header

        //content
        res += '<tr>';
        let dict = dictArr[i];
        $.each(order, (j) => {
            res += '<td>' + dict[order[j]] + '</td>';
        });
        res += '</tr>'
    });
    return res;
};

/**
 * format table content (object -> str)
 * @param {Dict[Arr[object: str]]} dictArr javascript data table
 * @return {Dict[Arr[str:str]]} dictArr html data table
 */
var make4recReqHTMLTable = (dictArr) => {
    let title = {
        'metTime': '集合時間',
        'restaurantName': 'お店',
        'place': '場所',
        'partner': '相手',
        'deadline': '募集期限',
        'budget': '予算',
        'cancel': '受けた依頼を取り消す'
    };

    $.each(dictArr, (i) => {
        let dict = dictArr[i];
        $.each(dict, (k, v) => {
            if (k === 'metTime' || k === 'deadline') {
                dict[k] = toLocaleString(v);
            }
            if (k === 'budget'){
                dict[k] = '&yen; ' + v;
            }
        });
    });

    let order = [
        'metTime',
        'restaurantName',
        'place',
        'partner',
        'deadline',
        'budget',
        'cancel',
    ];

    return dictArrayToHTMLTable(title, dictArr, order);
}

// ready...
$(() => {
    let sampleDictArr = [
        {
            'metTime': new Date(2017, 10, 27, 12, 0, 0),
            'restaurantName': '寿司',
            'place': '<a href="https://www.google.co.jp/maps/place/%E5%A4%A7%E9%98%AA%E5%A4%A7%E5%AD%A6%E4%B8%AD%E4%B9%8B%E5%B3%B6%E3%82%BB%E3%83%B3%E3%82%BF%E3%83%BC/@34.6927198,135.4882802,17z/data=!3m1!4b1!4m5!3m4!1s0x6000e6f427728823:0xd361d2a346f79d9c!8m2!3d34.6927154!4d135.4904689">大阪大学　中之島センター</a>',
            'partner': '相手のページへのリンク',
            'deadline': new Date(2017, 10, 25, 12, 0, 0),
            'budget': 3000,
            'cancel': '取り消すボタン'
        },
        {
            'metTime': new Date(2017, 10, 27, 12, 0, 0),
            'restaurantName': '寿司',
            'place': '<a href="https://www.google.co.jp/maps/place/%E5%A4%A7%E9%98%AA%E5%A4%A7%E5%AD%A6%E4%B8%AD%E4%B9%8B%E5%B3%B6%E3%82%BB%E3%83%B3%E3%82%BF%E3%83%BC/@34.6927198,135.4882802,17z/data=!3m1!4b1!4m5!3m4!1s0x6000e6f427728823:0xd361d2a346f79d9c!8m2!3d34.6927154!4d135.4904689">大阪大学　中之島センター</a>',
            'partner': '相手のページへのリンク',
            'deadline': new Date(2017, 10, 25, 12, 0, 0),
            'budget': 3000,
            'cancel': '取り消すボタン'
        }
    ];

    $("#recReqTable").html(make4recReqHTMLTable(sampleDictArr));
}
);