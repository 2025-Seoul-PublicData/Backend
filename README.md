# Backend

# 📦 서울시 사회적경제 기반 플랫폼 API 명세서

> Spring Boot + Swagger + OAuth2 기반 백엔드 REST API

---

## 🧭 소개

이 프로젝트는 **서울시 사회적경제 공공데이터**를 활용해 사회적 기업과 소비자를 연결하고, 가치를 소비하는 플랫폼을 제공합니다.

각 API는 다음 형식을 따릅니다:

* **요약 설명**
* **HTTP Method / URL**
* **요청 파라미터**
* **응답 코드 및 예시**

---

## 🔐 인증 & 회원 API

### 🔑 카카오 로그인 콜백

> 카카오 인가 코드를 받아 회원 여부에 따라 리다이렉트 및 쿠키 발급

* **Method**: `GET`
* **URL**: `/api/auth/kakao/callback`

**응답 코드**

* `302 Found`: 기존 회원 `/` 리다이렉트, 신규 회원 `/signup`
* `400 INVALID_KAKAO_REQUEST`
* `500 KAKAO_SERVER_ERROR`

---

### 📝 회원가입

> 신규 회원 정보를 입력 받아 가입 처리 및 JWT 발급

* **Method**: `POST`
* **URL**: `/api/auth/signup`

**요청 바디**

```json
{
  "name": "홍길동",
  "location": "서울특별시 강남구",
  "role": "CONSUMER",
  "profileColor": "gray"
}
```

**응답 예시**

* `200 OK`: 쿠키(`accessToken`) 발급
* `400 INVALID_INPUT_VALUE`
* `404 MEMBER_NOT_FOUND`
* `409 DUPLICATE_MEMBER`

---

### 🚪 로그아웃

> accessToken 쿠키를 제거하여 로그아웃 처리

* **Method**: `POST`
* **URL**: `/api/auth/logout`

**응답 코드**

* `204 No Content`
* `500 INTERNAL_SERVER_ERROR`

---

### 👤 내 정보 조회

> 로그인한 사용자의 이름 및 프로필 색상 조회

* **Method**: `GET`
* **URL**: `/api/member/me`

**응답 예시**

```json
{
  "name": "test_user",
  "profileColor": "gray"
}
```

---

### 💳 소비 내역 전체 조회

> 로그인한 사용자의 기업 유형별 소비 총합 반환

* **Method**: `GET`
* **URL**: `/api/member/consumption`

**응답 예시**

```json
[
  {
    "companyType": "사회서비스제공형",
    "totalPrice": 13000
  }
]
```

---

### 💳 특정 소비 유형 조회

* **Method**: `GET`
* **URL**: `/api/member/consumption/type?companyType=SOCIAL_SERVICE`

**응답 예시**

```json
{
  "companyType": "SOCIAL_SERVICE",
  "totalPrice": 12000
}
```

---

## 🏢 기업 API

### 🗺️ 전체 기업 마커 조회

* **Method**: `GET`
* **URL**: `/api/company/public/all-companies`

**응답 예시**

```json
[
  {
    "companyId": 1,
    "companyName": "행복나눔협동조합",
    "latitude": 37.5665,
    "longitude": 126.978,
    "companyCategory": "LIFE"
  }
]
```

---

### 💖 찜한 기업 목록

* **Method**: `GET`
* **URL**: `/api/company/saved`

---

### ➕ 기업 찜 등록

* **Method**: `POST`
* **URL**: `/api/company/save?companyId=1`

**응답 코드**

* `200 OK`
* `409 CONFLICT`: 이미 찜한 경우

---

### ➖ 기업 찜 해제

* **Method**: `DELETE`
* **URL**: `/api/company/unsave?companyId=1`

---

### 🧾 기업 프리뷰 조회

* **Method**: `GET`
* **URL**: `/api/company/public/preview?companyId=1`

**응답 예시**

```json
{
  "companyId": 1,
  "companyName": "Test Company",
  "companyCategory": "RESTAURANT",
  "temperature": 36.5,
  "reviewCount": 10,
  "companyLocation": "Seoul",
  "business": "Restaurant",
  "companyType": "PRE"
}
```

---

## 📝 리뷰 API

### ✏️ 리뷰 작성

* **Method**: `POST`
* **URL**: `/api/review`

**요청 바디**

```json
{
  "companyId": 1,
  "review": "좋은 가게였습니다.",
  "temperature": 88.5,
  "reviewCategories": ["CLEAN", "REVISIT"]
}
```

---

### 🛠 리뷰 수정

* **Method**: `PATCH`
* **URL**: `/api/review/{reviewId}`

---

### 🗑 리뷰 삭제

* **Method**: `DELETE`
* **URL**: `/api/review/{reviewId}`

---

### 🧾 회사 리뷰 조회

* **Method**: `GET`
* **URL**: `/api/review/company/{companyId}/all`

---

### 📄 리뷰 페이지 조회

* **Method**: `GET`
* **URL**: `/api/review/company/{companyId}/paging?page=0&size=10`

---

### 🙋 회원 리뷰 조회

* **Method**: `GET`
* **URL**: `/api/review/member`

---

### 🔢 리뷰 개수 조회

* **Method**: `GET`
* **URL**: `/api/review/count?companyId=1`

---

### 🌡 온도 평균 조회

* **Method**: `GET`
* **URL**: `/api/review/temperature?companyId=1`

---

## 🧾 OCR (영수증 분석)

### 🧠 NAVER OCR 영수증 분석

* **Method**: `POST`
* **URL**: `/api/receipt/naver`
* **형식**: `multipart/form-data`

| 파라미터      | 타입   | 위치   | 설명         |
| --------- | ---- | ---- | ---------- |
| file      | file | form | 영수증 이미지 파일 |
| companyId | Long | form | 기업 ID      |

**응답 예시**

```json
{
  "storeName": "스타벅스",
  "confirmNumber": "12345678",
  "orderDateTime": "2025-05-01T15:24:34"
}
```

---

## 🧡 스토리 API

### 📜 전체 스토리 미리보기

* **Method**: `GET`
* **URL**: `/api/story`

### 🥇 인기 TOP3 스토리

* **Method**: `GET`
* **URL**: `/api/story/best`

### 🔎 스토리 상세 조회

* **Method**: `GET`
* **URL**: `/api/story/{storyId}`

### 👍 좋아요 등록

* **Method**: `POST`
* **URL**: `/api/story/{storyId}/like`

### 👎 좋아요 취소

* **Method**: `DELETE`
* **URL**: `/api/story/{storyId}/like`

---

## 📢 지원 공고 API

### 🔍 프리뷰 목록 조회

* **Method**: `GET`
* **URL**: `/api/support/public/preview`

### 📄 상세 내용 조회

* **Method**: `GET`
* **URL**: `/api/support/public/detail?id=123`

**응답 예시**

```json
{
  "title": "2025 사회적기업 성장 지원 사업",
  "startDate": "2025-05-01",
  "endDate": "2025-05-31"
}
```

---

## 📚 문서 접근

* Swagger UI: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

## ⚙️ 기술 스택

* Spring Boot 3.x
* Spring Security (JWT + OAuth2)
* JPA (MySQL)
* Firebase SDK
* NAVER OCR API
* SpringDoc (Swagger/OpenAPI)

---

> 문서는 필요 시 Notion, Swagger YAML, Postman JSON 등으로 변환 가능
