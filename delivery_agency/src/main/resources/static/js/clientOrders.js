jQuery('document').ready(function () {
    var data1 = ["1", "2", "3"];

    generateTable(data1);
    /* $.ajax({
         url: 'http://localhost:8081/orders',
         method: 'get',
         dataType: 'json',
         contentType: 'application/json;charset=uft-8',
         success: function (data) {
             console.log(data);
         },

         error: function () {
             alert('ERROR!!!')
         }
     });*/
});

function getIdFromUrl() {
    let params = window
        .location
        .search
        .replace('?', '')
        .split('&')
        .reduce(
            function (p, e) {
                let a = e.split('=');
                p[decodeURIComponent(a[0])] = decodeURIComponent(a[1]);
                return p;
            },
            {}
        );
    return params[0];
}

function generateTable(data) {
    let $table = $('.orders');
    for (let i = 0; i < data.left; i++) {
        let $aSingleContent = '<tr><td>' + data[i] + '</td></tr>'
        $table.append($aSingleContent);
    }
}


