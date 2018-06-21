/**
 * Created by Daniel on 2016/5/16.
 */

var server_url = "192.168.1.103:8080"

/**
 * 按照传入的键值，提取出datas数组里的对应值
 * 如datas=[{'date':'1970-01-01','value'=1},{'date':'1970-01-02','value'=2}]
 * 调用getArray(datas,'date')将返回datas中的date域构成的数组['1970-01-01','1970-01-02']
 * @param datas
 * @returns {Array}
 */
function getArray(datas) {
    var array = [];
    if (arguments.length < 2) {
        alert("wrong size");
        return null;
    }
    for (var i = 0; i < datas.length; i++) {
        var tem;
        if (arguments.length >= 3) {
            tem = [];
            for (var j = 1; j < arguments.length; j++) {
                tem.push(datas[i][arguments[j]]);
            }
        } else {
            tem = datas[i][arguments[1]];
        }
        array[i] = tem;
    }
    return array;
}

/**
 *
 * @param type
 * @returns {*}
 */
function getTypeDes(type) {
    switch (type) {
        case 'day':
        case 'DAY':
            return "日K";
        case 'week':
        case 'WEEK':
            return "周K";
        case 'month':
        case 'MONTH':
            return "月K";
        case 'quarter':
        case 'QUARTER':
            return "季K";
    }
}

function getParamaters(name) {
    var href = window.location.href.replace(/[#]/, "");
    var args = href.split('?')
    if (args.length < 2) {
        return null;
    }
    args = args[1].split("&")
    for (var i = 0; i < args.length; i++) {
        var arg = args[i].split("=")
        if (arg[0] == name)
            return decodeURI(arg[1])
    }
}

function showMessage(tips) {
    $('#tip-msg').html(tips)
    $('#tip-modal').modal()
}

function isSuccess(data) {
    return data.respStatus.success
}