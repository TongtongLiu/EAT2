function show_info(str) {
	$('#login-label').html(str);
}

function get_global_image(username) {
	var res = 0;
	for (var i = 0; i < username.length; i++) {
		var number = username.charCodeAt(i);
		res = (res * 128 * 128 + number) % 21;
	}
	return 'img/' + res + '.png';
}

function initialize_subject() {
    $('.subject').height(($(window).height()-$(".subject-head").height()-5*$("#bottom_space").innerHeight())/4);
    $('.subject-title').css('padding-top',($('.subject-head').height()-$('.subject-title').height())/2+'px');
    $('.subject').click(function(){
        var type = $(this).children().attr('class');
        //Into_Subject(type);
        global_subject = type;
        show_main_page();
    })

    var s = $('.subject');
    var t = $('.title-text');
    var top = (parseInt(s.css('height'))- parseInt(t.css('height')))/2;
    t.css('padding-top', top+'px');
}

function login_action() {
	var username = $('#username-text').val();
	
	if (username == '') {
		show_info('昵称不能为空！！');
		return;
	} else {
		global_username = username;
		global_image = get_global_image(username);
	}

//	$('#login-container').hide();
//    var timeout= 500;
//    $('#login-container').animate({
//        'margin-top' : '-'+height+'px'
//    },timeout);
//    setTimeout(function(){
//        $('#login-container').hide();
//    }, timeout);

	
	//show_main_page();
    $('#login-container').hide();
	$('#subject-container').show();
	initialize_subject();
}

function initialize() {
	
	get_database();

	$('body').css('width', width);

	//initialize_subject();

	// $('#login-container').css('padding-top',($(window).height()-$('#login-container').height())/2);
	// $('#login-container').css('padding-bottom',($(window).height()-$('#login-container').height())/2);
}

initialize();
