function strStartsWith(str, prefix) {
    return str.indexOf(prefix) === 0;
}

function getAllInfo(url) {

    return $.get(url);

}

function calculator(fcRate, fcUnit, fcAmt) {

    return (fcRate / fcUnit) * fcAmt;
}

function getInfoWAjax(url, data) {    
    return $.ajax({
        url: url,
        type: "get",
        data: data
    });
}
