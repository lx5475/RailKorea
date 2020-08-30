
# RailKorea Plugin
RailKorea 플러그인은 마인크래프트 반야생에 도움되는 기능들을 모은 플러그인입니다.  
직접 사용 해보면서 부족한 점을 개선중에 있습니다.
`Spigot 1.16` 기준으로 개발되었습니다.

| 최신버전 |  업데이트 날짜 |
|--|--|
| v2.0.1 | 2020/08/30 |

## 기본 기능
### 1. 시간 확인
[Tab] 키를 누르면 현재 시간 확인이 가능합니다.   
낮/밤 여부와, 마인크래프트 시간을 현실 시간으로 전환한 시간을 표시합니다.  
이제 광질을 하면서도 시간을 확인 할 수 있어요 :)

### 2. 온/오프라인 유저 확인
[Tab] 키를 누르면 오프라인 유저까지 목록을 표시합니다.  
화이트리스트 된 유저를 기준으로 하며, 전체 인원체크도 편리하게 가능합니다.

### 3. 좌표 HUD
나의 좌표를 항상 표시합니다.   
[F3]을 누르지 않고도 간단하게 좌표를 보기위함입니다.

### 4. 죽음 카운팅
죽었을 때 사인과 몇 번째 죽음인지를 셉니다.   
왜 만들었냐구요?? ㅋㅋㅋㅋㅋ 님들이 너무 많이 죽어서 궁금했어요....

### 5. 바이옴 좌표 자동기록
새로운 바이옴을 최초발견시 서버에 알려집니다.  
또한 해당 좌표는 `/map` 명령어를 이용해 볼 수 있는 공용 좌표책에 자동 기록됩니다.

### 6. 플레이어 체력 표시
플레이어의 체력이 플레이어 이름 하단에 표시됩니다.  
상대방이 아프면 챙겨줍시다.  
_근데 제가...마크 계정이 2개가 없어 테스트 해보지는 못했습니다...ㅠㅠ_

## 일반 유저 명령어
1. **/home**
> 자신의 스폰지점 좌표를 알려줍니다.

2. **/destination** [x] [z]
> 지정한 x, z 좌표로 목적지를 설정합니다.  
> 만일 목적지를 지우고 싶다면, x, z 좌표를 생략한 `/destination`만 입력합니다.

3. **/map**
> 좌표를 기록할 수 있는 공용 책을 얻을 수 있습니다.  
> 책을 편집한 후 `완료` 버튼을 누르면 변경사항이 저장되어 다른 유저들도 해당 내용을 볼 수 있습니다.

4. **/hud** [on|off]
> 항상 뜨는 좌표 HUD를 켜고 끌 수 있습니다.  
> 예로 `/hud off`를 하면 좌표 정보가 표시되지 않습니다.

## OP 유저  명령어
1. **/money** [양|닉네임] [양]
> `닉네임`을 가진 유저에게 `양`만큼의 화폐를 지급합니다.  
>닉네임을 생략할 경우 자기자신에게 지급됩니다.

2. **/create_merchant** [닉네임]
> `닉네임`을 가진 유저 바로 앞에 상인을 소환합니다. 닉네임을 생략할 경우 자기자신 앞에 소환됩니다.

3. **/create_buyer** [닉네임]
> `닉네임`을 가진 유저 바로 앞에 매입인을 소환합니다. 닉네임을 생략할 경우 자기자신 앞에 소환됩니다.

4. **/edit_merchant**
> 상인의 판매 항목을 변경할 수 있는 창을 엽니다.  
> 가로로 2칸이 한 묶음이 되어서 왼쪽 칸이 `판매하는 아이템`, 오른쪽이 `판매가격`이됩니다.  
> 첨부된 사진은 다이아몬드를 64원에 판매하는 예시입니다.

5. **/edit_buyer**
> 매입인의 매입 항목을 변경할 수 있는 창을 엽니다.  
> 가로로 2칸이 한 묶음이 되어서 왼쪽 칸이 `매입하는 아이템`, 오른쪽이 `매입가격`이됩니다.

6. **/register** [닉네임] [직업] [레벨]
> 해당 `닉네임`에 직업을 등록합니다. 직업은 [Tab] 창에서도 확인 할 수 있으며 채팅시에도 항상 표시됩니다.  
>레벨은 0~5 사이의 숫자를 입력하시면되며, 생략시 0으로 지정됩니다.  
> 레벨이 설정된 유저는 **직업 글자색**이 바뀝니다. 색상은 모자단 색상변화와 동일합니다.
- 1: 노란색
- 2: 파란색
- 3: 초록색
- 4: 보라색
- 5: 빨간색


## 다운로드 링크
- RailKorea  
https://www.dropbox.com/s/qfgd3a9l8lphxwa/RailKorea.jar?dl=0
- ProtocolLib (필수 라이브러리)  
https://ci.dmulloy2.net/job/ProtocolLib/lastSuccessfulBuild/

## 주의사항
#### 리셋
새로운 사람들과 새로운 기록으로 시작하고 싶다면 서버의 `plugins` 폴더 내에 `RailKorea` 폴더를 삭제 후 , 서버를 재시작해주세요.
#### 의존성
이 플러그인은 `ProtocolLib` 이 설치된 서버에서 작동합니다.
`ProtocolLib`은 [이 사이트](https://ci.dmulloy2.net/job/ProtocolLib/lastSuccessfulBuild/)에서 `jar` 파일을 받아 Plugin 설치와 동일하게 해주시면됩니다.

## 만든이
파이리 (노란모자임)
