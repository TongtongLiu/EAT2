function onclick_sort_popularity() {
    var msgs = global_msgs;
    msgs = sort(msgs, 'favor');
    $('.msg').remove();
    for (var i = 0; i < msgs.length; i++) {
        show_message(msgs[i]);
    }
    $('.msg').show();
}

function date_convert(a) {
    return parseInt(a.split('-')[0])*100000000+parseInt(a.split('-')[1])*1000000+parseInt(a.split('-')[2])*10000+parseInt(a.split('-')[2].split(' ')[1])*100+parseInt(a.split(':')[1]);
}

function onclick_sort_time() {
	var msgs = global_msgs;
    for (var i = 0; i < msgs.length; i++) {
        msgs[i]['comparable_time'] = date_convert(msgs[i]['time']);
    }
    msgs = sort(msgs, 'comparable_time');
    $('.msg').remove();
    for (var i = 0; i < msgs.length; i++) {
        show_message(msgs[i]);
    }
    $('.msg').show();
}

function sort(arr, key) {
    quickSort(arr, 0, arr.length - 1, key);
    function quickSort(arr, l, r, key) {
        if (l >= r) return;         
        var mid = arr[parseInt((l + r) / 2)][key] , i = l, j = r;         
        while(i < j) {
            while(arr[i][key] > mid) i++;
            while(arr[j][key] < mid) j--;    
            if (i <= j) {
                var temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                i++;
                j--;
            }
        }       
        quickSort(arr, l, j, key);
        quickSort(arr, i, r, key);
    }
    return arr;
}
