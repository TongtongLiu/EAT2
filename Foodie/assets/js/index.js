var width 	= window.innerWidth;
var height 	= window.innerHeight;

var data; // virtual database using localStorage
var global_subject;
var global_msgs;
var global_username;
var global_image;

var localization = {
    healthcare : "保养专题",
    dental : "病症专题",
    food : "食物专题",
    orthodontics : '正畸专题'
};

function get_messages() {
	global_msgs = get_global_data();
	for (var i = 0; i < global_msgs.length; i++) {
		global_msgs[i]['id'] = i;
	}
	return global_msgs;
}

function show_post_new() {
//    var timeout = 500;
//    $('#main-container').animate({
//        'top' : height + 'px'
//    }, timeout);
//

//	$('#post-new-container').show();
//    $('#post-new-container').animate({
//        'top':'0px'
//    }, timeout);
//
//    setTimeout(function(){
//        $('#main-container').hide();
//        $('#main-container').css({
//            'top':0,
//            'left':'-'+width+'px'
//        });
//    }, timeout+50);


    $('#post-new-container').css({
        "width" : width+'px'
    });
    $('#main-container').hide();
    $('#post-new-container').show();
	window.scrollTo(0, 0);
}

function show_single_post(msgid) {
	$('body').append(single_post_container);

	generate_single_post(msgid);


//
//    var timeout = 500;
//    //$('#subject-container').css("right", "200%");
//	$('#single-post-container').show();
//    $('#main-container').animate({'left':'-'+width+'px'}, timeout);
//    $('#single-post-container').animate({'left':'0px'}, timeout);
//
//    setTimeout(function(){
//        window.scrollTo(0, 0);
//        $('#main-container').css({
//           "z-index" : "-1"
//        });
//    },timeout);

    $('#single-post-container').css({
        "width" : width,
        "position" : "absolute"
    });
    $('#main-container').hide();
    $('#single-post-container').show();
    window.scrollTo(0, 0);
}

function show_message(msg)
{
	var div = '';
	var tit = msg.title;
	var fav = msg.favor;
	var icon = msg.img;
	var cont = msg.content;
	var id = msg.id;

	if (tit.length > 10) {
		tit = tit.substr(0, 10) + '...';
	}

	if (cont.length > 40) {
		cont = cont.substr(0, 40) + '...';
	}

	div = generate_msg_tag(tit, fav, icon, cont, id);

	$('#main-container').append(div);
}

function show_button() {

	$('#main-container').append(index_footer);

    //修改dropdown样式
//    $('.dropdown').on('show.bs.dropdown', function(e){
//        $(this).find('.dropdown-menu').first().stop(true, true).slideDown();
//    });
//    $('.dropdown').on('hide.bs.dropdown', function(e){
//        $(this).find('.dropdown-menu').first().stop(true, true).slideUp();
//    });
}

function back_to_subject() {
//	var timeout = 500;
//	$('#main-container').animate({
//		'left' : width + 'px'
//	}, timeout);

    $('#main-container').remove();
	$('#subject-container').show();

//    $('#subject-container').animate({
//		'right' : 0
//	}, timeout);
//	setTimeout(function(){
//		$('#main-container').remove();
//	}, timeout + 50);
}

function show_main_page() {
	var msgs = get_messages();

	$('body').append('<div id="main-container""></div>');
	//$('#main-container').append('<div onclick="back_to_subject()" class="glyphicon glyphicon-chevron-left back-from-main"></div><div class="main-title">'+localization[global_subject]+'</div><img class="head_img" width="' + width + '" height="' + width * 0.25 + '" src="img/' + global_subject + '-title.jpg">');
	$('#main-container').append('<div class="main-title">'+localization[global_subject]+'</div><img class="head_img" width="' + width + '" height="' + width * 0.25 + '" src="img/' + global_subject + '-title.jpg">');


	//返回标签垂直居中
    var h = $('.head_img');
    var t = $('.main-title')
    // var b = $('.back-from-main');
    // b.css('margin-top', (h.height() - b.height())/2+'px');
    t.css({'margin-top':(h.height() - t.height())/2+'px', 'margin-left': (parseInt(h.css('width'))- parseInt(t.css('width')))/2+'px'});
    
	if (msgs.length == 0) {

		var div = 
		'<div class="no-posts">'+
			'现在还没有人发贴哦，快来做第一个吧~'
		'</div>';

		$('#main-container').append(div);

	} else {

		for (var i = msgs.length - 1; i >= 0; i = i - 1) {
			show_message(msgs[i]);
		}
		$('.msg').show();

	}

	show_button();

    // $('#subject-container').hide();
    $('#main-container').show();

//	var timeout = 500;
//    $('#subject-container').animate({
//        'right' : '100%'
//    }, timeout);
//
//    $('#main-container').animate({
//    	'left' : 0
//    }, timeout);
//
//    setTimeout(function() {
//    	$('#subject-container').hide();
//    	window.scrollTo(0, 0);
//    }, timeout + 50);
    
}

function initialize() {
	global_username = '匿名用户';
	global_image = 'img/avatar.png';
	global_subject = 'food';
	
	get_database();

	setTimeout(function(){
		$('body').css('width', width);
		show_main_page();
	}, 1000);
}

initialize();




