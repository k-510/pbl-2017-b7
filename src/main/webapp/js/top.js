var loc = "http://localhost:3000"

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

    res += '<thead>'
    res += '<tr>'
    $.each(order, (j) => {
        res += '<th>' + title[order[j]] + '</th>';
    });
    res += '</tr>'
    res += '</thead>'

    res += '<tbody>'
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
    res += '</tbody>'
    return res;
};

/**
 * format table content (object -> str)
 * @param {Dict[Arr[object: str]]} dictArr javascript data table
 * @return {Dict[Arr[str:str]]} dictArr html data table
 */
var makerecReqHTMLTable = (dictArr) => {
    let title = {
        'datetime': '集合時間',
        'restaurantName': 'お店',
        'address': '場所',
        'condition': '相手',
        'deadline': '募集期限',
        'budget': '予算',
        'cancel': '受けた依頼を取り消す'
    };

    $.each(dictArr, (i, dict) => {
        $.each(dict, (k, v) => {
            if (k === 'datetime' || k === 'deadline') {
                dict[k] = toLocaleString(v);
            }
            if (k === 'budget') {
                dict[k] = '&yen; ' + v;
            }
        });
    });

    let order = [
        'datetime',
        'restaurantName',
        'address',
        'condition',
        'deadline',
        'budget',
        'cancel',
    ];

    return dictArrayToHTMLTable(title, dictArr, order);
}

var drawrecReqTable = () => {
    let sampleDictArr = [
        {
            'datetime': new Date(2017, 10, 27, 12, 0, 0),
            'restaurantName': '寿司',
            'address': '<a href="https://www.google.co.jp/maps/address/%E5%A4%A7%E9%98%AA%E5%A4%A7%E5%AD%A6%E4%B8%AD%E4%B9%8B%E5%B3%B6%E3%82%BB%E3%83%B3%E3%82%BF%E3%83%BC/@34.6927198,135.4882802,17z/data=!3m1!4b1!4m5!3m4!1s0x6000e6f427728823:0xd361d2a346f79d9c!8m2!3d34.6927154!4d135.4904689">大阪大学　中之島センター</a>',
            'condition': '相手のページへのリンク',
            'deadline': new Date(2017, 10, 25, 12, 0, 0),
            'budget': 3000,
            'cancel': 2
        },
        {
            'datetime': new Date(2017, 10, 27, 12, 0, 0),
            'restaurantName': '寿司',
            'address': '<a href="https://www.google.co.jp/maps/address/%E5%A4%A7%E9%98%AA%E5%A4%A7%E5%AD%A6%E4%B8%AD%E4%B9%8B%E5%B3%B6%E3%82%BB%E3%83%B3%E3%82%BF%E3%83%BC/@34.6927198,135.4882802,17z/data=!3m1!4b1!4m5!3m4!1s0x6000e6f427728823:0xd361d2a346f79d9c!8m2!3d34.6927154!4d135.4904689">大阪大学　中之島センター</a>',
            'condition': '相手のページへのリンク',
            'deadline': new Date(2017, 10, 25, 12, 0, 0),
            'budget': 3000,
            'cancel': 3
        }
    ];
    // [TODO] ?type=registerdでするべきなのだが、うまくいかない requestsで仮実装
    $.ajax({
        url: loc+'/requests',
        type: 'GET',
        headers: {
            Accept: 'application/json',
        },
    }).done((data, textStatus, jqXHR) => {
        dictArr = [];
        let promises = [];
        $.each(data, (index, reqDic) => {
            let dic = {};
            dic.datetime = new Date(reqDic.datetime);
            dic.condition = reqDic.condition;
            dic.deadline = new Date(reqDic.deadline);
            dic.cancel = reqDic.request_id;
            dic.budget = 3000;
            dictArr.push(dic);
            // Shop status
            promises.push($.ajax({
                // temporary key id
                url: 'https://api.gnavi.co.jp/RestSearchAPI/20150630/?keyid=ac8febbabfdd2ab10f7d0c907d688663&format=json&id=' + reqDic.shop_id,
                dataType: 'jsonp',
                type: 'GET',
                async: true,
                context: { some: 'value' },
            }));
        });

        Promise.all(promises).then((results) => {
            $.each(results, (index, result) => {
                console.log(result);
                dictArr[index].address = result.rest.address;
                dictArr[index].restaurantName = result.rest.name;
            });
            // dummy data
            dictArr.push(sampleDictArr[0]);
            dictArr.push(sampleDictArr[1]);
            console.log(dictArr);
            $("#recReqTable").html(makerecReqHTMLTable(dictArr));
        });
    });
}

// ready...
$(() => {
    $("#recReqTable").tablesorter();
    drawrecReqTable();

    flatpickr('#calendar');

    // button action(search)
    $('#search').keyup((e) => {
        let re = new RegExp($('#search').val());
        $.each($('#recReqTable tbody tr'), (index, element) => {
            let row_text = $(element).text();
            if(row_text.match(re) != null){
                $(element).css("display", "table-row");
            }
            else{
                $(element).css("display", "none");
            }
        });
    });
});