function generate_single_post(msgid) {
    var post = data[global_subject][msgid];
    // var post = global_msgs[msgid];

    // console.log(post);
    
    $('.single-title').append(post.title);

    var main_head = $('.single-main-head');
    $(main_head.children()[1]).append(post.author);

    $('.single-main-favor').append(post.favor);

    $('.single-main-avatar').append('<img src="' + post.img + '">');

    var main_body = $('.single-main-body');
    main_body.append(post.content);

    var main_time = $('.single-main-time');
    main_time.append(post.time);

    var responses = post.responses;
    for (var i = 0; i < responses.length; i = i + 1) {
        generate_single_post_response(responses[i]);
    }

    function generate_single_post_response(response) {
        var tag = generate_response_tag(response);
        // $('.response').append('<li class="response-post"><div class="response-left"><div class="response-avatar"><img src="'+ response.img +'"></div></div><div class="response-middle"><div class="response-name">' + response.author + '</div><div class="response-content">' + response.content + '</div></div><div class="response-right"><div class="response-like"><img src="img/like.png"><div class="response-favor">' + response.favor + '</div></div></div><div class="response-foot"><div class="response-time">' + response.time + '</div></div></li>');
        $('.response').append(tag);

        var len = $('.response-like img').length;
        $($('.response-like img')[len-1]).click(function() {
            if ($(this).attr('src') == "img/like.png") {
                $($(this).next()[0]).text(parseInt($($(this).next()[0]).text()) + 1);
                $(this).attr('src', 'img/liked.png')
            } else {
                $($(this).next()[0]).text(parseInt($($(this).next()[0]).text()) - 1);
                $(this).attr('src', 'img/like.png')
            }
            modify_post_response(msgid, len - 1, 'favor', parseInt($($(this).next()[0]).text()));
            // data.posts[msgid].responses[i].favor = parseInt($($(this).next()[0]).text());
            // localStorage.setItem('json', JSON.stringify(data));
        });
    }

    $('.single-main-like img').click(function() {
        if ($('.single-main-like img').attr('src') == "img/like.png") {
            $('.single-main-favor').text(parseInt($('.single-main-favor').text()) + 1);
            $('.single-main-like img').attr('src', 'img/liked.png');
        } else {
            $('.single-main-favor').text(parseInt($('.single-main-favor').text()) - 1);
            $('.single-main-like img').attr('src', 'img/like.png');
        }
        modify_post(msgid, 'favor', parseInt($('.single-main-favor').text()));
        // data.posts[msgid].favor = parseInt($('.single-main-favor').text());
        // localStorage.setItem('json', JSON.stringify(data));
    })

    /*
    $('.response-like img').click(function() {
        if ($(this).attr('src') == "img/like.png") {
            $($(this).next()[0]).text(parseInt($($(this).next()[0]).text()) + 1);
            $(this).attr('src', 'img/liked.png')
        } else {
            $($(this).next()[0]).text(parseInt($($(this).next()[0]).text()) - 1);
            $(this).attr('src', 'img/like.png')
        }
    })
    */

    $('.comment').click(function() {
        var input = $('.response-action');
        if (input.val() != "") {
            var date = new Date();
            var time = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate() + " " + date.getHours() + ":" + date.getMinutes();
            var author = global_username;
            var favor = 0;
            var content = input.val();
            var img = global_image;

            var response = {
                author: author,
                img: img,
                time: time,
                favor: favor,
                content: content
            };

            // data[global_subject][msgid].responses.push(response);
            // localStorage.setItem('json', JSON.stringify(data));
            add_post_response(msgid, response);
            generate_single_post_response(response);
            // $('#single-post-container').remove();
            // show_single_post(msgid);

            input.val("");

            /*
            $('.response').append('<li class="response-post"><div class="response-left"><div class="response-avatar"><img src="img/avatar.png"></div></div><div class="response-middle"><div class="response-name">' + data.user + '</div><div class="response-content">' + input.val() + '</div></div><div class="response-right"><div class="response-like"><img src="img/like.png"><div class="response-favor">' + 0 + '</div></div></div><div class="response-foot"><div class="response-time">' + time + '</div></div></li>');
            input.val("");

            var len = $('.response-like img').length;
            $($('.response-like img')[len-1]).click(function() {
                 if ($(this).attr('src') == "img/like.png") {
                    $($(this).next()[0]).text(parseInt($($(this).next()[0]).text()) + 1);
                    $(this).attr('src', 'img/liked.png')
                } else {
                    $($(this).next()[0]).text(parseInt($($(this).next()[0]).text()) - 1);
                    $(this).attr('src', 'img/like.png')
                }
            })
            */
        }
    });
}


function back_from_single_post() {

    $('#single-post-container').remove();
    $('#main-container').show();

//    var timeout = 500;
//    $('#single-post-container').animate({'left':width}, timeout);
//    $('#main-container').animate({'left':"0"}, timeout);
//    setTimeout(function() {
//        $('#single-post-container').remove();
//        $('#main-container').css({'z-index':'0'});
//        $('#subject-container').css("right", "100%");
//    }, timeout + 50);
}
