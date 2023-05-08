# Progress Report
프로젝트 진행 보고서

## 게임 소개
![게임 움짤](https://user-images.githubusercontent.com/100705763/236852545-4efc5924-820f-4c7b-9c47-f7a7981cdc9a.gif)


## 현재까지의 진행 상황

##### 플레이어 이동 : 80 %
##### 맵 확장 : X
##### 오브젝트 랜덤 배치 및 자동 획득 : 100 %
##### 적 생성, 추격, 충돌 : 80 %
##### 플레이어 기본 기술 구현 : 90 %
##### 레벨 업 처리 : 60 %
##### 기술 강화 : 80 %
##### 다른 기술들 구현 : 50 %

## 커밋 내역
![커밋 내역1](https://user-images.githubusercontent.com/100705763/236833324-9957b6e1-d5d6-4d59-adce-f670329790e5.PNG)

## class 구성 정보

### Generator Class
![Generator Class](https://user-images.githubusercontent.com/100705763/236841137-61a8b587-0c02-41a1-b559-f34fe3520f56.PNG)

 - 적, 아이템, 몬스터 들의 생성 처리
 - 생성 간격, 생성 횟수를 상속받은 클래스에서 달리하여 처리
 - 시간에 따라 Enemy 종류에 따른 Generator 를 add / remove 하여 나중에 강한 몬스터만 나오도록 함
 - MagicManager 는 MagicType, AttackType 이 enum 으로 정의되어 있고, 마법 관련 생성기에서 MagicType, AttackType 에 따른 값을 사용
 - MagicManager 에서 마법의 데미지 비율, 마법 생성 갯수, 쿨타임 조절, 마법의 레벨 업 처리, 가까운 적 탐색, 랜덤 적 탐색 을 담당
 - Bullet/Thunder Generator 가 해당 마법에 대한 데미지, 갯수, 쿨타임 등을 가지고 있고 마법 생성시 데미지를 전달하는 방식


### Enemy Class
![Enemy Class](https://user-images.githubusercontent.com/100705763/236841237-2c979bd9-f5b3-4831-9dc5-257696cda6a3.PNG)

 - 적이 공통적으로 가지는 플레이어 추격, 경험치/체력 처리를 담당
 - collisionFlag 를 이용해 관통형 마법에 대해서 한번만 피격되도록 함


### Item Class
![Item Class](https://user-images.githubusercontent.com/100705763/236841282-bb71608b-3280-4df5-b38b-fda81ea644af.PNG)

 - 플레이어가 Item 을 획득 시 처리해야 하는 일들을 onCollision 함수에 작성하고, 이를 상속받는 클래스에서 클래스 별 내용을 처리
 - 경험치 구슬은 EventCollision 인터페이스를 상속받아서 사용
 - 경험치 구슬의 eventCollisionRect 와 플레이어가 충돌 시 경험치 구슬이 플레이어를 추격하도록 함
 - 경험치 구슬은 Enum Type 을 정의하여 소형, 중형, 대형 경험치 구슬에 따라 획득하는 경험치 양, 크기, 이미지 를 다르게 함


### Magic Class
![Magic Class](https://user-images.githubusercontent.com/100705763/236841352-88fdc825-0c69-4d4c-9387-c192b73c841a.PNG)

 - MagicType, AttackType 로 해당 마법이 무슨 마법인지, 공격 방식은 어떤지를 마법들이 가지고 있음
 - Bullet 의 경우 BulletGenerator 에서 생성되고, 생성 당시 가까운 적의 방향을 향해 날아가도록 함
 - Thunder 는 ThunderGenerator 에서 생성되고, 일정 시간만 충돌이 발생하며, lifeTime 이 지나면 삭제됨
 - Thunder 는 이미지 전체가 아닌 바닥에 내리 꽂히는 부분만 충돌처리를 하도록 조절


### Player Class
![Player Class](https://user-images.githubusercontent.com/100705763/236841393-1baf76e1-856b-4a06-a43f-b46ab041d139.PNG)

 - 체력, 경험치, 레벨, 무적시간, 데미지를 변수로 가지고 있고, 체력과 경험치는 게이지로 화면에 표시
 - 피격 시 일정시간 무적상태가 되고, 무적상태에선 몬스터와 충돌해도 체력이 감소하지 않음
 - 레벨업 시 현재는 BULLET, THUNDER 의 레벨을 증가하지만 선택지를 통해 특정 마법의 레벨을 올릴 수 있게 해야 함


### FLOAT Class
![FLOAT class](https://user-images.githubusercontent.com/100705763/236841472-71e1830d-10d8-4f86-81d1-6b479029971e.PNG)

 - 게임에서 변하는 값을 저장하기 위한 클래스
 - 기본값, 증가 비율, 결과값 을 가지고 있음
 - 예를 들어 플레이어의 시작 공격력이 30이고, 아이템을 통해 20% 강해지면 기본값은 30, 비율은 1.2가 되어 결과값은 36이 됨
 - 게임 내에서 증가 비율은 초기값에 곱해서 결과를 구해야 하는데 변화하는 값마다 변수를 여러개 두면 복잡할 것 같아 추가 한 클래스
 - set, get 으로 값을 정하고 얻을 수 있고, increaseRatio, changeRatio 로 증가 비율을 조절할 수 있음


### 상호작용
 - 플레이어와 Enemy의 충돌, 충돌 시 플레이어 체력 감소 및 일정 시간 무적
 - 플레이어와 아이템의 충돌, 아이템 별 처리 (체력 증가, 경험치 증가)
 - 마법과 몬스터의 충돌, 충돌 시 몬스터의 체력 감소, 체력이 0이되면 삭제
    - 일반형 마법은 충돌하면 마법이 사라짐
    - 관통형 마법은 충돌하면 몬스터의 collisionFlag를 변화시키고 사라지지 않음, collisionFlag 로 한번만 충돌되게끔 처리

