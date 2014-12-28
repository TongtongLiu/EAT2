function get_substr_count(key, str) {
	var res = 0;
	var index = 0;
	while (true) {
		index = str.indexOf(key, index);
		if (index == -1) {
			break;
		}
		res = res + 1;
		index = index + 1;
	}
	return res;
}

function get_search_result(key, posts) {
	var res = [];
	for (var i = 0; i < posts.length; i++) {
		post = posts[i];
		var occur_count = 0;
		occur_count += get_substr_count(key, post.title);
		occur_count += get_substr_count(key, post.content);
		if (occur_count != 0) {
			post['count'] = occur_count;
			post['id'] = i;
			res.push(post);
		}
	}
	sort(res, 'count');
	return res;
}

function do_search(arg) {
	var key = arg;
	if (key == '') {
		return;
	}

	var posts = data[global_subject];
	var res = get_search_result(key, posts);

	if (res == null) {
		return;
	}

	global_msgs = res;

	change_main_view();

	//$('#main-container').remove();
	//show_main_page(res);
}

function search_action() {
    var s = $('.search-input');
    var f = $('.filter-icon');
    var p = $('.post-action');

    if(!s.hasClass('search-input-active')) {
        s.addClass('search-input-active');
        f.hide();
        p.hide();
        s.css({'width':'70%'});

    } else {
        s.css({'width':'0'});
        f.show();
        p.show();
        s.removeClass('search-input-active');
        do_search(s.val());
        s.val('');
    }
}

function change_main_view() {
	$('.msg').remove();
	var msgs = global_msgs;
	for (var i = 0; i < msgs.length; i++) {
		show_message(msgs[i]);
	}
	$('.msg').show();
}
