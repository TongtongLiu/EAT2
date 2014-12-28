function back_from_post_new() {

    $('#post-new-container').hide();
	$('#main-container').show();

//    $('#main-container').animate({
//        'left':0
//    }, timeout);
//    $('#post-new-container').animate({
//        'left':width+'px'
//    }, timeout);
//
//    setTimeout(function(){
//
//        $('#post-new-container').css({'left':0});
//    }, timeout+50);

	window.scrollTo(0, 0);
}

function invalid_post(post) {
	if (post.title == '') {
		show_info('标题不能为空');
		return true;
	}
	if (post.content == '') {
		show_info('内容不能为空');
		return true;
	}
	return false;
}

function post_new_action() {
	var title = $("#title").val();
	var author = global_username;
	var img = global_image;
	var content = $("#content").val();
	var favor = 0;
	var response = [];
	var time = new Date().Format("yyyy-MM-dd hh:mm");
	var post = {
		title : title,
		time : time,
		author : author,
		img : img,
		favor : favor,
		content : content,
		responses : response
	};

	if (invalid_post(post) == true) {
		return;
	}

	$("#title").val("");
	$("#content").val("");

	add_post(post);
	//location.reload(true);
	global_msgs = get_global_data();
	show_new_mainpage();
}

function change_main_view_after_post() {
	$('.msg').remove();
	var msgs = global_msgs;
	for (var i = msgs.length - 1; i >= 0; i--) {
		msgs[i]['id'] = i;
		show_message(msgs[i]);
	}
	$('.msg').show();
}

function show_new_mainpage() {
	change_main_view_after_post();

//	$('#main-container').css({
//		'top' : height + 'px',
//		'left' : 0
//	});

    $('#post-new-container').hide();
	$('#main-container').show();
	
//	var timeout = 500;
//	$('#main-container').animate({
//		'top' : 0
//	}, timeout);
//	$('#post-new-container').animate({
//		'top' : '-' + height + 'px'
//	}, timeout);
//
//	setTimeout(function(){
//		$('#post-new-container').hide();
//	}, timeout + 50);
}

Date.prototype.Format = function(fmt)   
{
	//author: meizz   
	var o = {   
    	"M+" : this.getMonth()+1,                 //月份   
    	"d+" : this.getDate(),                    //日   
    	"h+" : this.getHours(),                   //小时   
    	"m+" : this.getMinutes(),                 //分   
    	"s+" : this.getSeconds(),                 //秒   
    	"q+" : Math.floor((this.getMonth()+3)/3), //季度   
    	"S"  : this.getMilliseconds()             //毫秒   
	};   
  
  	if(/(y+)/.test(fmt))   
  		fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
  	for(var k in o)   
    	if(new RegExp("("+ k +")").test(fmt))   
  	fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
  	return fmt;   
} 
