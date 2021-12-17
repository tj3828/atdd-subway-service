# 3단계 - 인증을 통한 기능 구현 
### 요구사항
* 토큰 발급기능 (로그인) 인수 테스트 작성
    * 로그인 성공 시나리오
    * 로그인 실패 시나리오
    * 토큰이 유효하지 않은 경우의 시나리오


* 내 정보 조회, 수정, 삭제 기능 인수 테스트 작성
  * 내 정보 조회 성공 시나리오
  * 내 정보 수정 성공 시나리오
  * 내 정보 삭제 성공 시나리오
  * 내 정보 조회 실패 시나리오
  * 내 정보 수정 실패 시나리오
  * 내 정보 삭제 실패 시나리오


* 즐겨찾기 기능 구현 및 인수테스트 작성
  * 즐겨찾기 생성 시나리오
  * 즐겨찾기 목록 조회 시나리오
  * 즐겨찾기 삭제 시나리오

* 즐겨찾기 serivec관련 가짜객체 테스트 코드 작성
  
---

### ATDD 시나리오 
 
#### Feature: 토큰 발급기능 (로그인) 기능구현

Scenario: 로그인을 시도한다.(성공)<br>
Given 회원 등록되어 있음<br>
When 로그인 요청<br>
Then 로그인 됨<br>

Scenario: 로그인을 시도한다.(실패)<br>
Given 회원 등록되어 있음<br>
When 존재하지 않는 회원정보로 로그인 요청<br>
Then 로그인 실패<br>

Scenario: 로그인을 시도한다.(실패)<br>
Given 회원 등록되어 있음<br>
When 틀린 패스워드로 로그인 요청<br>
Then 로그인 실패<br>

Scenario: 비 유효한 토큰으로 조회 요청을 시도한다.<br>
Given 임의의 토큰 문자열 생성<br>
When 내 정보 조회 요청<br>
Then 토큰 검증에서 에러 발생해서 실패

---

##### Feature: 내 정보 조회, 수정, 삭제 기능 구현

Scenario: 내 정보를 관리한다.(통합 성공 시나리오)<br>
Given 회원 등록 및 로그인 되어 있음<br>
When 내 정보 조회 요청<br>
Then 내 정보 조회 성공<br>
When 내 정보 수정 요청<br>
Then 내 정보 수정 성공<br>
When 내 정보 삭제 요청<br>
Then 내 정보 삭제 성공<br>

Scenario: 내 정보를 조회한다. (실패)<br>
Given 회원 등록 및 로그인 후 회원 삭제<br>
When 내 정보 조회 요청 과정에서 회원정보가 미존재<br>
Then 내 정보 조회 실패<br>

Scenario: 내 정보를 수정한다. (실패)<br>
Given 회원 등록 및 로그인 후 회원 삭제<br>
When 내 정보 수정 요청 과정에서 회원정보가 미존재<br>
Then 내 정보 수정 실패<br>

Scenario: 내 정보를 삭제한다. (실패)<br>
Given 회원 등록 및 로그인 후 회원 삭제<br>
When 내 정보 삭제 요청 과정에서 회원정보가 미존재<br>
Then 내 정보 수정 실패<br>

---

##### Feature: 즐겨찾기 기능 구현

Scenario: 즐겨찾기를 관리 (통합 성공 시나리오)<br>
When 즐겨찾기 생성을 요청<br>
Then 즐겨찾기 생성됨<br>
When 즐겨찾기 목록 조회 요청<br>
Then 즐겨찾기 목록 조회됨<br>
When 즐겨찾기 삭제 요청<br>
Then 즐겨찾기 삭제됨<br>

Scenario: 즐겨찾기 삭제 실패 시나리오 <br>
When 즐겨찾기 생성을 요청<br>
Then 즐겨찾기 생성됨<br>
When 즐겨찾기 다른 사용자가 내 즐겨찾기 삭제 요청<br>
Then 즐겨찾기 삭제 실패<br>