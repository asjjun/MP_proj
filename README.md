# SW(Sangmyung 友) 
<p align="center">
<img width="400px" src="https://user-images.githubusercontent.com/29851772/120697106-468c7900-c4e8-11eb-9b9f-0e5a608e5bfc.png">
  
</p>

<!-- TABLE OF CONTENTS -->
<details open="open">
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#1-어플-소개">어플 소개</a>
      <ul>
        <li><a href="#환경-설정">환경 설정</a></li>
      </ul>
    </li>
    <li>
      <a href="#2-본문">본문</a>
      <ul>
        <li><a href="#로고-의미">로고 의미</a></li>
        <li><a href="#주요-기능-설명">주요 기능 설명</a></li>
        <li><a href="#영상">영상</a></li>
      </ul>
    </li>
    <li><a href="#3-앱-다운로드-링크">앱 다운로드 링크</a></li>
  </ol>
</details>

## 1. 어플 소개
SW는 (Sangmyung 友) 코로나 시국에 비대면으로 하는 수업이 많아지다보니 신입생들은 동기들과 친해지는데 어려움이 있고
<br>학교나 본인 학과에 대한 정보를 얻기도 힘들고 아는 사람이 없어 혼밥 하는 학생들도 많습니다. 
<br>이를 해소하기 위해 학교 학생들끼리 편하게 교류 할 수 있는 여러 기능이 구현 되어 있는 어플 입니다. 

 <h3>환경 설정</h3>

     Android Studio
       - 에뮬레이터로 실행 시 Pixel 4 XL 사용 권장
         화면이 작은 기기로 실행 시 디자인 일그러지는 부분 발생
         
       - 회원가입시 이메일과 비밀번호를 입력해야하는데 비밀번호를 6글자 이상으로 설정해주어야한다.
  
## 2. 본문
 <h3>로고 의미</h3>

 <h4>저희 로고는 상명대학교와 친구를 의미하는 ‘友 벗 우’를 합친 슴우라 하여 영어로 SW입니다.</h4>
 <br><br> 
 <h3>주요 기능 설명</h3>
 
 ### <로딩 화면>


 <img width="300px" height = "550px"  src="https://user-images.githubusercontent.com/79883776/121015827-649ef580-c7d6-11eb-8cc5-06c5dd287bf9.png ">
 
 
 ### <로그인 화면>

<img width="300px" height = "550px"  src="https://user-images.githubusercontent.com/79883776/121015875-72547b00-c7d6-11eb-86b1-32653126c0c0.png"> <img width="300px" height = "550px"  src="https://user-images.githubusercontent.com/29851772/121023850-a3d14480-c7de-11eb-8bcc-6c164519a61c.PNG">

 어플리케이션을 실행 시킨 후 로그인 화면이 나온다. <br>회원가입 버튼을 누르고 학교 이메일과 사용할 비밀번호를 입력한다. 
 
 ### <이메일 인증>
 
<img width="300px" height = "550px"  src="https://user-images.githubusercontent.com/29851704/121016972-b98f3b80-c7d7-11eb-9aa9-15ae993e8f74.png"> <img width="300px" height = "550px"  src="https://user-images.githubusercontent.com/29851704/121016938-b431f100-c7d7-11eb-88ca-d34b4f790d2a.png">
 
회원가입한 이메일로 로그인을 해주고 인증 이메일 보내기 버튼을 클릭하면 자신이 등록한 이메일로 메일이 간다. <br>그걸 클릭해주면 이메일 인증 완료.

### <회원정보 입력>

<img width="300px" height = "550px"  src="https://user-images.githubusercontent.com/79888537/120705692-1b5b5700-c4f3-11eb-994a-3edb0a2754cd.gif">

이메일 인증을 완료하면 회원정보를 입력할 수 있는 화면이 나온다.
<br>
이름과 주소, 전화번호, 생년월일은 EditText밑에 정해진 숫자 범위를 넘어가면 빨간불이 들어온다.
<br>
이는 정해진 문자열 길이가 초과했다는 것을 의미한다. 
<br>
만약 잘못 입력했다면 EditText 오른쪽 맨끝에 버튼을 눌러 입력했던 값을 다 지워줄 수 있다.

<img width="300px" height = "550px"  src="https://user-images.githubusercontent.com/79888537/120705698-1e564780-c4f3-11eb-9261-bd3888188fbe.gif">

만약 저 범위를 벗어난채로 회원정보 등록을 하면 
글자수가 너무 길다는 토스트 메세지가 나오게 된다. 

<img width="300px" height = "550px"  src="https://user-images.githubusercontent.com/79888537/120705707-21513800-c4f3-11eb-8045-43356da01620.gif">

또한 빈칸이 존재한 채로 회원정보 등록 버튼을 누르게 되면 빈칸을 입력하라는 토스트 메세지가 뜬다.

### <회원정보 수정>

<img width="300px" height = "550px"  src="https://user-images.githubusercontent.com/79888537/120705755-2dd59080-c4f3-11eb-808b-0d0c130325ce.jpg">

회원가입하고 등록했던 정보들을 확인할 수 있는 액티비티이다. 
<br>메인엑티비티 우측 최상단 사람 모양 아이콘을 클릭하면 들어갈 수 있다.


<img width="300px" height = "550px"  src="https://user-images.githubusercontent.com/79888537/120705780-362dcb80-c4f3-11eb-9e6d-bcc7ce47c8db.gif">

회원정보중에 수정할 수 있는 데이터는 전화번호, 주소, 학과를 변경 할 수 있다.
<br> 
변경을 원하면  해당 텍스트뷰 오른쪽에 수정버튼을 클릭하면 수정할 데이터를 넣을 수 있는 EditText가 나오게 된다. 
<br>
이곳에 데이터를 집어넣고 정보수정을 누르면 수정을 원하는 부분만 바꿀 수 있게 된다.

### <매칭 기능>

<img width="400px" height = "400px"  src="https://user-images.githubusercontent.com/29851772/121031978-ce72cb80-c7e5-11eb-96b3-de71d616ac3d.gif"> <img width="400px" height = "400px"  src="https://user-images.githubusercontent.com/29851772/121032164-fcf0a680-c7e5-11eb-91b0-338c6977ceb6.gif">

왼쪽을 보면 매칭 버튼 클릭 시 매칭 대기중이라는 표시로 로딩 애니메이션이 돌아가고 한번 더 클릭 시 해제되는 모습을 볼 수 있다.<br>
오른쪽은 매칭 버튼 클릭 후 관심사에 맞는 인원이 매칭 되었을때 매칭이 잡히고 채팅방이 만들어지는 모습이다. <br>
매칭 후 생기는 채팅방과 채팅하는 장면은 아래 <a href="#영상">영상</a>에서 더 자세히 확인 할 수 있다.

<img width="850px" height = "60%"  src="https://user-images.githubusercontent.com/29851772/121031679-8784d600-c7e5-11eb-8304-887e31362403.gif">

매칭이 잡히는 순간을 데이터베이스와 함께 보면 매칭을 누른 사람들이 테이블에 들어오고<br>
오른쪽 에뮬레이터에서도 매칭버튼을 눌러주어 관심사의 인원을 충족 하는 순간 매칭이 잡히면서 테이블은 삭제된다. 

<img width="850px" height = "60%"  src="https://user-images.githubusercontent.com/29851772/121031506-5b695500-c7e5-11eb-8094-780151777903.gif">

관심사 별로 각각 테이블이 있어 관심사가 다르다면 함께 매칭되지않고 같은 관심사인 사람들끼리만 매칭 되는 모습이다.















### <게시판>

<img width="300px" height = "550px" alt="채팅" src="https://user-images.githubusercontent.com/29966841/120702452-0da3d280-c4ef-11eb-82ea-b51aa76cc468.jpg"> <img width="300px" height = "550px" alt="채팅" src="https://user-images.githubusercontent.com/29966841/120702563-31671880-c4ef-11eb-9f98-fba3d69e435a.jpg">

어플 내 게시판은 '학과 게시판', '학교 게시판' 두가지로 나뉜다.<br>
'학과 게시판'은  회원가입시 설정한 학과가 같은 이용자들 끼리만 사용할 수 있는 게시판이다.<br>
'학교 게시판'은 학과 상관없이 모든 학교 학생들이 사용할 수 있는 게시판이다. <br><br><br>


<img width="300px" height = "550px" alt="채팅" src="https://user-images.githubusercontent.com/29966841/120928998-29db8580-c722-11eb-9a7c-058e21b90698.jpg"> <img width="300px" height = "550px" alt="채팅" src="https://user-images.githubusercontent.com/29966841/120929012-3c55bf00-c722-11eb-93d9-6544f5b89169.jpg">

게시판 내의 게시글에는 이용자들끼리 댓글을 작성할 수도 있고, 오른쪽 사진과 같이 게시글에 사진을 첨부할 수도 있다.<br/><br/>

### <게시판 데이터베이스 부분>

<img width="600px" height = "380px" alt="채팅" src="https://user-images.githubusercontent.com/29966841/120955102-9ccd1680-c78b-11eb-8ac9-8a558331f417.png">

데이터베이스에 접속해보면 위의 사진과 같이 'SchoolPosts', 'deptPosts'로 학교게시판과 학과게시판이 나눠져 구성 되어있는것을 확인할 수 있다.<br/>

<img width="600px" height = "380px" alt="채팅" src="https://user-images.githubusercontent.com/29966841/120955306-077e5200-c78c-11eb-8a6d-e57467ced53f.png">

'SchoolPosts'테이블에는 학교게시판에 작성된 게시물들의 작성 일시, 제목, 내용, 작성자UID가 저장되게 된다.<br/>

<img width="600px" height = "380px" alt="채팅" src="https://user-images.githubusercontent.com/29966841/120955454-5b893680-c78c-11eb-9c54-0908a8b3441f.png">

'deptPosts'테이블에는 학과게시판에 작성된 게시물들의 작성 일시, 제목, 내용, 작성자UID가 저장되게 된다. 또한 작성자들의 학과별로 구분되어 데이터베이스에 저장되는것을 확인할 수 있다.


### <인앱 결제>

- Google Console에 들어가 개발자 등록을 먼저 해준다 ($25 1회 최초 등록을 해야한다.)


### <라이센스 테스트>

결제 리스트를 할 때, 실제 돈이 빠져나가지 않도록 해야하기 때문에 라이센스 테스트를 신청해야한다.

구글 플레이 콘솔 > 설정 > 라이센스 테스트에 라이센스 

테스터를 등록하면 아이디로 결제하는 건은

테스트 건으로 처리되어 실제 돈이 청구되지 않는다. 

<img width="600px" height = "450px" alt="채팅" src="https://user-images.githubusercontent.com/29851772/200575210-6aa9849e-5a11-4027-b43f-c754df9a88a8.png">


### <앱 만들기>

구글 플레이 콘솔에서 앱을 만듭니다.

-[내부 테스트 설정]

앱에 있는 apk 파일을 업로드하고 테스터를 지정하면, 등록한 이메일을 사용하는 

Google Play 이용자는 내부 테스트를 진행 할 수 있게 됩니다.

### <인앱상품>

APK가 업로드되면 수익창출 > 제품 > 인앱상품 순으로 들어가 

어플리케이션에서 사용할 아이템을 만들어 줍니다.

<img width = "800px" height ="100px" src = "https://user-images.githubusercontent.com/79883776/120707555-71c99500-c4f5-11eb-8d5c-e1a760ca4eb3.JPG">

(단, 인앱상품의 id를 지정해줄때 android studio의 MarketFragment에 있는 id값과 같게 해야함. )


<br>

<img width = "30%" height ="550dp" src = "https://user-images.githubusercontent.com/79883776/120707098-df28f600-c4f4-11eb-81d1-aa3425e6e430.gif"> <img width = "50%" height ="550dp" src = "https://user-images.githubusercontent.com/29851772/121029154-54d9de00-c7e3-11eb-88eb-3a20ec065e8c.gif"> 


 다이아몬드 품목별 인앱상품과 결제 영상


<img width = "30%" height ="30%" src = "https://user-images.githubusercontent.com/29851704/121016557-3b329980-c7d7-11eb-9861-d47c009b403e.jpg">

 다이아몬드 결제 내역



 <h3>영상</h3>
  
  
  
  
https://www.youtube.com/watch?v=q2RzgrhNzbU  

어플리케이션의 간단한 소개와 기능 설명 영상입니다. 
  
## 3. 앱 다운로드 링크

https://github.com/asjjun/SMU  <- 링크에 들어가셔서 어플 다운로드 가능합니다.



  <br><br><hr>
  <h4> ** 결제 공개 테스트 심사중 ** <h4> 
  
  실제로 어플을 다운받고 결제 기능까지 구현해보고 싶은 분들은 
  
  아직 구글 콘솔에서 어플을 심사중이니
  
  내부 테스트를 진행 할 수 있게 아래 주소로 연락을 주시면 도와드리겠습니다.
  
  <연락 주소 :  904rla@gmail.com,
               weihyuk39@gmail.com,
               asjjun@naver.com,
               ckjjw5573@gmail.com
               >
  
  
  
  
  
  
  
  
  
  
  
