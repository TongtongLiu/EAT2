function generate_msg_tag(title, favor, icon, content, msgid) {
	var div = 
	'<div class="msg" style="width:'+(width-15)+'px;display:none;">'+
		'<a class="post-link" onclick="show_single_post(' + msgid + ')">'+
		'<div class="title-area">'+
			'<div class="title">'+
					title+
			'</div>'+
			'<div class="title-arrow">'+
				//'<img class="arrow-img" src="'+arrowicon+'">'+
			'</div>'+
		'</div>'+
		'<div class="under-area">'+
			'<div class="under-left">'+
				'<div class="icon">'+
					'<img class="icon-img" src="'+icon+'">'+
				'</div>'+
				'<div class="favor">'+
					favor+
				'</div>'+
			'</div>'+
			'<div class="under-right">'+
				content+
			'</div>'+
		'</div>'+
		'</a>'+
	'</div>';
	return div;
}

function generate_response_tag(response) {
    var tag = 
    '<li class="response-post">' +
        '<div class="response-left">' + 
            '<div class="response-avatar">' + 
                '<img src="' + response.img + '">' +
            '</div>' + 
        '</div>' + 
        '<div class="response-middle">' + 
            '<div class="response-name">' +
                response.author +
            '</div>' +
            '<div class="response-content">' +
                response.content +
            '</div>' +
        '</div>' +
        '<div class="response-right">' +
            '<div class="response-like">' +
                '<img src="img/like.png">' +
                '<div class="response-favor">' + 
                    response.favor +
                '</div>' +
            '</div>' +
        '</div>' +
        '<div class="response-foot">' +
            '<div class="response-time">' + 
                response.time +
            '</div>' +
        '</div>' +
    '</li>';
    return tag;
}


var single_post_container = 
'<div id="single-post-container" style="display:none;">' + 
    '<div class="single-head">' + 
        '<div class="single-back-icon-area">' + 
            '<div class="single-back-icon glyphicon glyphicon-chevron-left" onclick="back_from_single_post()"></div>' +
        '</div>' + 
        '<div class="single-title"></div>' +
    '</div>' + 
    '<div class="single-post-content">' + 
        '<div class="single-main-post">' + 
            '<div class="single-main-head">' + 
                '<div class="single-main-avatar">' +  
                '</div>' +
                '<div class="single-main-name"></div>' + 
                '<div class="single-main-like">' + 
                    '<img src="img/like.png">' + 
                    '<div class="single-main-favor"></div>' +
                '</div>' +
                '<div class="clearfloat"></div>'+
            '</div>' + 
            '<div class="single-main-body">' + 
            '</div>' + 
            '<div class="single-main-foot">' + 
                '<div class="single-main-time">' +
                '</div>' +
                '<div style="clear:both">' + 
                '</div>' +
            '</div>' + 
        '</div>' +
        '<ul class="response">' +
        '</ul>' +
    '</div>' + 
    '<div class="single-post-empty">' + 
    '</div>' +
    '<div class="single-foot">' + 
        '<input class="response-action form-control" type="text"/>' +
        '<div class="comment text-shadow glyphicon glyphicon-send"></div>' +
    '</div>' + 
'</div>';


var index_footer =
'<div class="footer-box">' +
    '<div class="filter-toggle dropdown">' +
        '<div class="glyphicon glyphicon-filter filter-icon dropdown-toggle" data-toggle="dropdown""></div>' +
        '<ul class="dropdown-menu filter-content" >'+
            '<li><a class="glyphicon glyphicon-time filter-content-icon" onclick="onclick_sort_time()"></a></li>' +
            '<li><a class="glyphicon glyphicon-fire filter-content-icon" onclick="onclick_sort_popularity()"></a></li>' +
        '</ul>' +
    '</div>' +
    '<div class="post-action"  onclick="show_post_new()">' +
    '<div class="glyphicon glyphicon-edit post-icon"></div>' +
    '<div class="footer-text">' +
        '发贴' +
    '</div>' +
    '</div>' +
    '<div class="glyphicon glyphicon-search search-icon" onclick="search_action()"></div>' +
    '<input class="form-control search-input" />' +
'</div>';


