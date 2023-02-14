<img src="https://capsule-render.vercel.app/api?type=waving&color=auto&height=150&section=header&text=Levelog&fontSize=90" />

-----
## 1. 프로젝트 소개
- 개발자들을 위한 블로그인 velog를 클론 코딩하여 코딩 능력을 향상 시키는 레벨 업 프로젝트입니다. <br><br>
<br>

----
## 2. 시연 영상
![레벨로그](https://user-images.githubusercontent.com/111271565/218298213-ff34badd-a5e4-4333-9aab-bf5399d3481f.gif)
<br><br>

----
## 3. 기능 명세서

#### 1. 로그인 / 회원가입

- 로그인 및 회원가입 (JWT Token 사용)
- 로그아웃
- 프로필 이미지 추가

#### 2. 게시글

- 텍스트 작성
- 이미지 첨부
- 좋아요
- 태그

#### 3. 댓글

- 댓글
- 좋아요
- 대댓글

#### 4. 추가기능

- 검색
- 게시글 정렬 (최신순, 조회순)
- 시간별 카테고리
- 무한스크롤
- 페이징
- 마이 페이지
- 회원가입 시 메일 인증
- Refresh token, Access Token
  <br><br>

----

## 4. 기술 스택
<div align=center> 

## * Frontend Tech Stack
<br>
  <img src="https://img.shields.io/badge/html5-E34F26?style=for-the-badge&logo=html5&logoColor=white"> 
  <img src="https://img.shields.io/badge/css-1572B6?style=for-the-badge&logo=css3&logoColor=white"> 
  <img src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black">
  <img src="https://img.shields.io/badge/react Hook form-EC5990?style=for-the-badge&logo=reacthookform&logoColor=black">
<br>
  <img src="https://img.shields.io/badge/react router-CA4245?style=for-the-badge&logo=reactrouter&logoColor=black">
  <img src="https://img.shields.io/badge/styled components-DB7093?style=for-the-badge&logo=styledcomponents&logoColor=black">
<br>
  <img src="https://img.shields.io/badge/axios-5A29E4?style=for-the-badge&logo=axios&logoColor=white">
  <img src="https://img.shields.io/badge/redux-764ABC?style=for-the-badge&logo=redux&logoColor=white">
  <img src="https://img.shields.io/badge/react-61DAFB?style=for-the-badge&logo=react&logoColor=black"> 
    <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">
    <img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white">
  <br>


## * Backend Tech Stack
<br>
    <img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white">
    <img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white">
    <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
    <img src="https://img.shields.io/badge/spring security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white">
    </br>
    <img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white">
    <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
    <img src="https://img.shields.io/badge/ubuntu-E95420?style=for-the-badge&logo=ubuntu&logoColor=black">
    <img src="https://img.shields.io/badge/apache tomcat-F8DC75?style=for-the-badge&logo=apachetomcat&logoColor=white">
    </br>
    <img src="https://img.shields.io/badge/amazon s3-569A31?style=for-the-badge&logo=amazons3&logoColor=white">
    <img src="https://img.shields.io/badge/amazon rds-527FFF?style=for-the-badge&logo=amazonrds&logoColor=white"> 
    <img src="https://img.shields.io/badge/amazon aws-232F3E?style=for-the-badge&logo=amazonaws&logoColor=white"> 
    <br>
    <img src="https://img.shields.io/badge/redis-DC382D?style=for-the-badge&logo=redis&logoColor=white">
    <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">
    <img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white">
    <br>
    </div>
<br>

------

## 5. 서비스 아키텍쳐

![levelog_서비스-아키텍쳐 (2)](https://user-images.githubusercontent.com/111271565/209953282-c972624b-fd96-400d-b54e-6a1d89a7a9f8.jpg)

-----

## 6. 트러블 슈팅


✅  **CORS 관련한 트러블 슈팅** <br><br>
커스텀한 Token 을 불러오는 과정에서 문제 였다.
addExposedHeader("커스텀한 헤드명") 이 부분에다가 우리가 추가한 헤더 부분을 가져오지 못하는 것에서 생긴 403 에러 였다.
브라우저가 접근할 수 있는 헤더를 추가 즉, addExposedHeader 에 직접 커스텀한 헤더 명을 추가해주니까 해결이 되었다.
<br><br>
✅  **multipartFile 이미지 첨부 문제** <br><br>
프로필 이미지 업로드를 할 때 이미지 첨부가 되지 않아서 발생한 문제가 있었는데, 이 부분은 multipartFile 이 null 이면
즉, 아무런 요청이 없으면 nullpointException 을 띄우는 것이 원인이었다.
그래서 try-catch 를 통해서 해당 부분을 해결을 해서 마무리 지을 수 있었다.
<br><br>
✅  **상세페이지 좋아요 상태 표시** <br><br>
게시글 상세페이지에 좋아요 버튼 표시 시, 좋아요를 누른 회원 / 누르지 않은 회원 / 비회원 으로 구분하여 표시해야 함,
HttpServletRequest로 객체를 전달받아 유저 정보를 얻음 -> JPA를 활용해서 유저가 해당 게시글에 좋아요를 눌렀는지 안 눌렀는지 확인 후
True / False로 반환 -> 토큰값이 없을 경우 비회원이기 때문에 무조건 False 반환
<br><br>
✅  **스프링 시큐리티 필터 안에서 공통 응답 처리** <br><br>
시큐리티 필터 안에서는 커스텀한 예외 메세지로 응답이 되지 않는 문제가 있었는데 커스텀한 예외 응답이 바로 반환 되지 못하고
다음 필터로 넘어가는 것이 문제였다. 다음 필터로 넘어가지 않게 바로 return문을 통해 메소드를 종료 시켜 커스텀한 예외 응답을
반환 시키는 것으로 해결하였다.
<br><br>
✅  **서버시간 트러블** <br><br>
클라이언트를 통해 글 작성, 댓글 작성 시 시간이 9시간 전의 시간으로 작성되는 문제였는데 EC2 서버의 시간이 세계협정시 (UTC)로
적용되어있는 것이 원인으로 EC2 서버에서 dpkg-reconfigure 명령어를 사용해 EC2 서버의 시간을 KST로 변경하여 해결하였다.
<br><br>

-----

## 7. 관련 링크

📌 프론트엔드 깃허브 : https://github.com/2jo-levelog/levelog-Frontend <br>
📌 백엔드 깃허브 : https://github.com/2jo-levelog/levelog-Backend

📌 팀 노션 : https://www.notion.so/10-2-levelog-3c77ed1eb7234fe3be33faeb67e93bf9

🔗 [API명세서 (Postman)](https://documenter.getpostman.com/view/24654654/2s8Z6yWCgh)</br></br>

----

## 8. 팀원 정보

| 이름  | 깃 허브 주소                          |
|-----|----------------------------------|
| 강민승 | https://github.com/minseung-gang |
|박소연| https://github.com/syp0812       |
| 서혁수 | https://github.com/SHsus1122 |
|정다솔| https://github.com/ssori0421     |
| 조소영 | https://github.com/littlezero48  |
| 홍윤재 | https://github.com/PigletHong |
