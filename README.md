# android-shopping-order

### 기능 목록
- [X] 데이터가 로딩되기 전 스켈레톤 UI를 추가한다

- [X] 여러 서버 중 하나를 선택할 수 있는 페이지를 추가한다
- [X] 상품 및 상품 목록 조회 API를 연동한다
- [X] 장바구니 추가, 수정, 삭제 API를 연동한다
- [ ] 사용자 인증 정보를 저장한다
  - [ ] 사용자 인증 정보가 존재해야만 장바구니를 이용할 수 있다

- [ ] 주문 하기 기능 구현
  - [X] 장바구니에 담긴 상품들을 주문한다.
  - [X] 주문이 완료되면 장바구니를 비운다.
  - [ ] 주문이 완료되면 메인 화면으로 이동한다.
  - [X] 주문 목록을 볼 수 있다.
  - [X] 다음 정보를 확인할 수 있다.
    - [X] 주문 상품 목록
      - [X] 상품명
      - [X] 상품 대표 사진
      - [X] 상품당 가격
      - [X] 상품 수량
    - [X] 할인 정보
      - [ ] 보유 포인트
      - [ ] 사용 포인트
        - [ ] 사용자가 입력할 수 있다.
    - [ ] 결제 금액
      - [ ] 할인 전 금액
      - [ ] 할인 금액
      - [ ] 최종 결제 금액

- [X] 주문 내역 목록 구현
  - [X] 주문 들을 스크롤 형태로 볼 수 있다.
  - [X] 주문 마다
    - [X] 주문 상세로 이동 하는 버튼이 있다.
    - [X] 주문에 해당 하는 상품들이 있다.
    - [X] 주문 일자
  - [X] 상품 마다
    - [X] 누르면 해당 상품 상세 보기로 이동 한다.
    - [X] 다음 정보가 표시 된다.
      - [X] 상품명
      - [X] 상품 대표 사진
      - [X] 상품당 가격
      - [X] 상품 수량

- [ ] 주문 상세 구현
  - [ ] 상품 마다
    - [ ] 눌르면 해당 상품 상세 보기로 이동 한다.
    - [ ] 다음 정보가 표시 된다.
      - [ ] 상품명
      - [ ] 상품 대표 사진
      - [ ] 상품당 가격
      - [ ] 상품 수량
  - [X] 주문 상세 정보가 표시 된다.
    - [X] 주문 일자
    - [X] 주문 금액
    - [X] 할인 금액
    - [X] 총 결제 금주문
