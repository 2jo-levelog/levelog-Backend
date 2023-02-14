package com.team2.levelog.user.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

// 1. 기능   : 랜덤 이미지 enum 클래스
// 2. 작성자 : 서혁수
@Getter
@AllArgsConstructor
public enum RandomImg {
    ONE("https://ichef.bbci.co.uk/news/800/cpsprodpb/E172/production/_126241775_getty_cats.png"),
    TWO("https://s3.ap-northeast-2.amazonaws.com/elasticbeanstalk-ap-northeast-2-176213403491/media/magazine_img/magazine_270/%EC%8D%B8%EB%84%A4%EC%9D%BC.jpg"),
    THREE("https://s3.ap-northeast-2.amazonaws.com/elasticbeanstalk-ap-northeast-2-176213403491/media/magazine_img/magazine_270/4.jpg"),
    FOUR("https://s3.ap-northeast-2.amazonaws.com/elasticbeanstalk-ap-northeast-2-176213403491/media/magazine_img/magazine_270/7.jpg"),
    FIVE("https://i0.wp.com/dailypetcare.net/wp-content/uploads/2020/11/Screen-Shot-2020-11-24-at-9.10.35-PM-edited-e1606302091776.png?w=1236&ssl=1");

    private final String url;
}
