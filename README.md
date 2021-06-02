# deathnote-api-server
<h3><p align="center">
💻 deathnote.gg ver2.0 API 서버
</p></h3>
<p align="center"><img src="https://github.com/dong149/deathnote-api-server/blob/develop/images/deathtnoe_main.gif" width="70%"/></p>
<h4><p align="center">
리그 오브 레전드의 사용자 Report 불완전성을 해결하려합니다.
</p></h4>


## 😢 Problem

리그 오브 레전드에는 고질적으로 사용자 Report 시스템의 불완전성의 문제가<br/>
존재해왔습니다.

여기서 Report 시스템이란, 사용자를 신고하는 기능을 의미합니다.<br/>
리그 오브 레전드는 한 게임 평균 시간이 20~30분입니다.<br/><br/>

트롤(게임을 망치는 유저)는 같은 팀인 나머지 4명 뿐아니라, 상대 팀 5명에게까지 게임의
재미를 떨어지게 만들기 때문에, 트롤 한 명이 가져오는 시간적 손실은 대략 200~300분에 달할 수 있습니다. <br/>

Report 시스템의 문제는 개발사인 Riot과 프로게이머들 또한 지적해온 문제입니다.<br/>
Deathnote.gg 서비스에서는 이런 문제를 해결하기 위해서 노력하는 서비스입니다.

<br/><br/>


## ⭐️ Feature

### 1. Troller 점수 확인 기능

![deathnote](/images/trollerscore.png)

#### 해결 과정

자체적으로 100점 만점으로 점수를 환산하여 보여주는 기능을 구현하여,
사용자들이 직관적으로 트롤 여부를 판단할 수 있는 기준을 제공하려 합니다.

1. Riot API로부터 Match Data들을 수집한다.
   Summoner API - Get EncryptedSummonerID <br/>
   MatchList API - Get MatchListID<br/>
   Match API - Get MatchInfo<br/>

   Riot API에게 여러 번의 요청을 연쇄적으로 진행해야 합니다.
   각각의 요청 단계에서 이전 단계의 데이터들이 필요하기 때문입니다.

2. 데이터들을 전처리합니다.
   각 지표들 (ex. Kills, Deaths, TowerKills .. )을 총 10명의 플레이어들 중에서 몇 등을 기록했는지 정리합니다.
   이 과정이 필요한 이유는 매게임마다 플레이어가 트롤인지 아닌지는 상대적이기 때문입니다.

3. 여러 지표들에 가중치를 다양하게 매겨봅니다.

4. 실제 유저들의 최근 20게임의 데이터를 통해 가중치가 제대로 매겨졌는지 확인합니다.

5. Troller Score 로직을 구체화합니다.

### 2. 특정 유저에게 리뷰 작성 기능

![deathnote](/images/review.png)
![deathnote](/images/review2.png)

#### 해결 과정

자체적으로 Report 시스템을 구현하여, 유저들의 피드백을 모아 트롤러 점수에 반영하는
기능을 제공하려 합니다.<br/>

이 기능은 사용자의 수에 비례하여 기능의 유용성이 높아집니다.<br/>
트롤러 점수 기능은 사용자 수를 증가시키기 위해서 추가하려 하는 기능입니다.

1. 서버단에서, CRUD 기능을 구현합니다.

2. 브라우저 캐시를 통해서 사용자는 자신이 Report한 부분에 대해서 U,D 기능이 가능하게 합니다.

3. 유의미한 데이터가 쌓였을 때부터 실제 트롤러 점수 연산 알고리즘에 해당 지표를 추가합니다.

4. 서버 단에서, Report 내용에 부적절한 말이 들어갔는지, 혹은 어뷰징이 있는지를 체크합니다.

   

### 3. 최근 20게임 분석


![deathnote](/images/match.png)

#### 해결 과정

이 기능 또한, 결국은 Report 시스템의 사용성을 증가시키기 위한 과정입니다.
보통의 전적 검색 사이트의 경우 모든 전적 그리고, 함께 플레이한 다른 유저의 정보까지 모두
가져오지만, 데스노트 서비스의 경우는 각 게임 당의 여러 지표의 순위 정보를 제공하려 합니다.
( 이는, 실제로 트롤러 점수 연산시 사용하기 때문에, 제공하는 것이 어렵지 않습니다. 

<br/>

1. DB를 조회했을 때, 이전 정보가 존재하지 않으면 Riot API에 재요청합니다.
2. 데이터를 받아온 이후에, 각 지표별 Rank를 Return 하는 함수를 이용합니다.
3. Client에 Response 합니다.

<br/>

<br/>

## 🧐 Difficulty

### 1. Riot API 요청에 제한이 있다.

Riot측에서 어느 정도의 Public함을 증명하기 전까지는 API의 요청을 제한하고, 단계적으로 제한을 풀어줍니다. 이에 따라, 요청을 최소화하기 위해서 요청한 데이터에 관해서 자체 DB에 저장해놓는 작업이 필요합니다. 그리고, 사용자가 전적 업데이트를 진행하면 다시 Riot API에 정보를 새롭게 요청합니다.<br/>
이 때, 재요청할 때는 몇몇 중요 정보를 DB에 저장해놓기 때문에 여러 API를 연쇄적으로 요청하지 않아 API 요청 횟수를 줄일 수 있습니다. <br/>

데스노트 서비스 특성상 예전 데이터를 모두 보관할 필요가 없기 때문에, Update된 시간 기준으로 적정 시간이 지난 데이터에 대해서 ( Match 정보만 ) 주기적으로 삭제해주는 작업을 진행합니다.
<br/>

### 2. 트롤러 점수 연산 알고리즘 구현

트롤러 점수가 유의미하기 위해서는 사용자가 신뢰할 수 있는 기준을 가지고 연산을 진행하고, 이런 과정을 서비스 내에서 사용자들이 열람 가능하게끔 해주어야 한다고 판단이 들었습니다.<br/>

따라서 임의로 기준을 정하는 것이 아니라, 가중치를 구하는 작업을 진행해야 했습니다.<br/>

해당 작업은 이렇게 진행했습니다.

1. 데이터를 RIOT API에 일정 시간 간격을 두고 요청하여 수집합니다. 
2. 수집한 데이터를 10명 중에 몇 등의 지표인지를 알아내기 위해서 Rank를 구하는 함수에 넣어줍니다.
3. 수집한 데이터를 CSV 파일로 Export 합니다. ( OpenCSV 사용 ) 
4. Lightgbm 모델을 사용하여 Feature 들의 중요도를 추출합니다.
5. 알고리즘에 해당 지표들을 반영합니다.

※왜 Lightgbm 모델을 사용했나?<br/>

승리에 영향을 미치는 여러 Feature들의 상대적 가중치를 알아내야 했기 때문에, Classification 모델 중, Feature Selection이 가능한 모델을 선택해서 진행해야 했습니다.


<br/><br/>

## 🔨 Dev stack

- Spring Boot
- Spring MVC
- Spring JPA
- AWS EC2


<br/><br/>



## Swagger

![deathnote](/images/swagger.png)


## Commit Convention

- **feat**: A new feature
- **fix**: A bug fix
- **docs**: Changes to documentation
- **style**: Formatting, missing semi colons, etc; no code change
- **refactor**: Refactoring production code
- **test**: Adding tests, refactoring test; no production code change
- **chore**: Updating build tasks, package manager configs, etc; no production code change



## Reference

- **Commit Convention**<br/>
  https://udacity.github.io/git-styleguide/

- Riot API<br/>
  https://developer.riotgames.com/

