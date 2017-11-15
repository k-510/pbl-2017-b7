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
        'state': '状態',
        'datetime': '集合時間',
        'restaurantName': 'お店',
        'address': '場所',
        'condition': '条件',
        'budget': '予算',
    };

    $.each(dictArr, (i, dict) => {
        $.each(dict, (k, v) => {
            if (k === 'datetime' || k === 'deadline') {
                dict[k] = toLocaleString(v);
            }
            if (k === 'budget') {
                dict[k] = '&yen; ' + v;
            }
            if (k === 'state') {
                if (dict[k] === 'accepted') dict[k] = 'マッチング完了'
                if (dict[k] === 'completed') dict[k] = '終了'
            }
        });
    });

    let order = [
        'state',
        'datetime',
        'restaurantName',
        'address',
        'condition',
        'budget',
    ];

    return dictArrayToHTMLTable(title, dictArr, order);
}

var drawrecReqTable = (gurunaviKey) => {
    $.ajax({
        url: '../api/user/requests?type=accepted',
        type: 'GET',
        headers: {
            Accept: 'application/json',
            Authorization: 'Session 45b287c4-8b68-483b-abba-b61acaf1ce76'
        },
    }).done((data, textStatus, jqXHR) => {
        console.log(data);
        dictArr = [];
        let promises = [];
        $.each(data.request, (index, reqDic) => {
            let dic = {};
            dic.datetime = new Date(reqDic.datetime);
            dic.condition = reqDic.condition.keyword;
            dic.deadline = new Date(reqDic.deadline);
            dic.budget = reqDic.budget;
            dic.state = reqDic.status;
            dictArr.push(dic);
            // Shop status
            promises.push($.ajax({
                url: 'https://api.gnavi.co.jp/RestSearchAPI/20150630/?keyid=' + gurunaviKey + '&format=json&id=' + reqDic.shop_id,
                dataType: 'jsonp',
                type: 'GET',
                async: true,
                context: { some: 'value' },
            }));
        });

        Promise.all(promises).then((results) => {
            $.each(results, (index, result) => {
                if ('error' in result) {
                    dictArr[index].address = "店舗idが見つかりません";
                    dictArr[index].restaurantName = "店舗idが見つかりません";
                }
                else {
                    dictArr[index].address = result.rest.address;
                    dictArr[index].restaurantName = result.rest.name;
                }
            });
            console.log(dictArr);
            $("#recReqTable").html(makerecReqHTMLTable(dictArr));
            $("#recReqTable").tablesorter();
        });
    });
}

/**
 * format table content (object -> str)
 * @param {Dict[Arr[object: str]]} dictArr javascript data table
 * @return {Dict[Arr[str:str]]} dictArr html data table
 */
var makemyReqHTMLTable = (dictArr) => {
    let title = {
        'state': '状態',
        'datetime': '集合時間',
        'restaurantName': 'お店',
        'address': '場所',
        'condition': '条件',
        'deadline': '募集期限',
        'budget': '予算',
    };

    $.each(dictArr, (i, dict) => {
        $.each(dict, (k, v) => {
            if (k === 'datetime' || k === 'deadline') {
                dict[k] = toLocaleString(v);
            }
            if (k === 'budget') {
                dict[k] = '&yen; ' + v;
            }
            if (k === 'state') {
                if (dict[k] === 'accepted') dict[k] = 'マッチング完了'
                if (dict[k] === 'completed') dict[k] = '終了'
            }
        });
    });

    let order = [
        'state',
        'datetime',
        'restaurantName',
        'address',
        'condition',
        'deadline',
        'budget',
    ];

    return dictArrayToHTMLTable(title, dictArr, order);
}

var drawmyReqTable = (gurunaviKey) => {
    $.ajax({
        url: '../api/user/requests?type=registered',
        type: 'GET',
        headers: {
            Accept: 'application/json',
            Authorization: 'Session 45b287c4-8b68-483b-abba-b61acaf1ce76'
        },
    }).done((data, textStatus, jqXHR) => {
        console.log(data);
        dictArr = [];
        let promises = [];
        $.each(data.request, (index, reqDic) => {
            let dic = {};
            dic.datetime = new Date(reqDic.datetime);
            dic.condition = reqDic.condition.keyword;
            dic.deadline = new Date(reqDic.deadline);
            dic.budget = reqDic.budget;
            dic.state = reqDic.status;
            dictArr.push(dic);
            // Shop status
            promises.push($.ajax({
                url: 'https://api.gnavi.co.jp/RestSearchAPI/20150630/?keyid=' + gurunaviKey + '&format=json&id=' + reqDic.shop_id,
                dataType: 'jsonp',
                type: 'GET',
                async: true,
                context: { some: 'value' },
            }));
        });

        Promise.all(promises).then((results) => {
            $.each(results, (index, result) => {
                if ('error' in result) {
                    dictArr[index].address = "店舗idが見つかりません";
                    dictArr[index].restaurantName = "店舗idが見つかりません";
                }
                else {
                    dictArr[index].address = result.rest.address;
                    dictArr[index].restaurantName = result.rest.name;
                }
            });
            console.log(dictArr);
            $("#myReqTable").html(makerecReqHTMLTable(dictArr));
            $("#myReqTable").tablesorter();
        });
    });
}

// ready...
$(() => {
    if ('gurunavi-key' in Cookies.get()) {
        let gurunaviKey = Cookies.get('gurunavi-key');
        $('#keyid-textbox').val(gurunaviKey);
        //drawrecReqTable(gurunaviKey);
        //drawmyReqTable(gurunaviKey);
    }

    $('#keyid-button').click((e) => {
        let gurunaviKey = $('#keyid-textbox').val();
        Cookies.set('gurunavi-key', gurunaviKey);
        drawrecReqTable(gurunaviKey);
        drawmyReqTable(gurunaviKey);
    });

    let tableIds = ['#myReq', '#recReq'];
    $.each(tableIds, (i, tableId) => {
        flatpickr(tableId + 'Calendar', {
            dateFormat: 'Y/m/d',
            onChange: (dateObj, dateStr) => {
                let re = new RegExp($(tableId + 'Calendar').val());
                $.each($(tableId + 'Table tbody tr'), (index, element) => {
                    let row_text = $(element).text();
                    if (row_text.match(re) != null) {
                        $(element).css("display", "table-row");
                    }
                    else {
                        $(element).css("display", "none");
                    }
                });
            }
        });
        $('#myReqClearButton').click((e) => {
            $('.my-req-input-form').each((i, elem) => {
                $(elem).val('');
            });
            $.each($('#myReqTable tbody tr'), (index, element) => {
                $(element).css("display", "table-row");
            });
        })
    });

    $('#recReqClearButton').click((e) => {
        $('.rec-req-input-form').each((i, elem) => {
            $(elem).val('');
        });
        $.each($('#recReqTable tbody tr'), (index, element) => {
            $(element).css("display", "table-row");
        });
    })

    $('.my-req-input-form').keyup((e) => {
        let re = new RegExp($('#myReqSearch').val());
        $.each($('#myReqTable tbody tr'), (index, element) => {
            let row_text = $(element).text();
            console.log($(element));
            console.log($(element)[0].childNodes[$(element)[0].childNodes.length-1].outerText.replace(/[^0-9]/g, ''));
            if (row_text.match(re) != null) {
                $(element).css("display", "table-row");
            }
            else {
                $(element).css("display", "none");
            }
        });
    });
});