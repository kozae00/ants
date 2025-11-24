# Ants Frontend (React + Vite)

백엔드(`Spring Boot`)에서 제공하는 `/api/v1` REST API를 빠르게 검증하기 위한 **MVP 대시보드**입니다.  
한 화면에서 로그인/회원가입, 종목 선택, 피드 확인, 게시글 작성·조회까지 기본 흐름을 테스트할 수 있습니다.

## 1. 개발 환경 구성

1. Node.js LTS(>=18)가 필요합니다. 없다면 `winget install OpenJS.NodeJS.LTS -e` 로 설치합니다.
2. 저장소 루트에서 프론트엔드로 이동합니다.
   ```powershell
   cd C:\Users\user\IdeaProjects\ants\frontend
   ```
3. 의존성 설치
   ```bash
   npm install
   ```
4. 환경 변수 템플릿을 복사해 백엔드 주소를 지정합니다.
   ```bash
   cp env.example .env
   # 필요 시 .env 수정 (기본값: http://localhost:8080)
   ```
5. 개발 서버 실행
   ```bash
   npm run dev
   ```
   Vite 개발 서버는 `/api` 요청을 `http://localhost:8080` 으로 프록시합니다.  
   백엔드 서버를 8080 포트에서 먼저 구동해 주세요.

## 2. 제공 화면

- **인증**: 회원가입 → 자동 로그인 흐름, 기존 계정 로그인/로그아웃
- **내 정보 패널**: `/api/v1/members/me` 로 프로필 확인
- **종목 선택기**: `/api/v1/stocks` 를 기반으로 필터링
- **피드**: `/api/v1/feed` 또는 `/api/v1/feed/stock/{id}` 최신 항목
- **게시글 목록**: 페이징(기본 5개)으로 `/api/v1/posts` 혹은 종목별 조회
- **게시글 작성**: `/api/v1/posts` POST

모든 보호된 API는 JWT(`Authorization: Bearer <token>`) 기반으로 호출되며, 토큰은 `localStorage` (`ants_token`)에 저장됩니다.

## 3. 주요 스크립트

| 명령어 | 설명 |
| --- | --- |
| `npm run dev` | Vite 개발 서버(기본 5173 포트) |
| `npm run build` | 타입 체크 후 프로덕션 번들 생성 |
| `npm run preview` | 빌드 산출물 로컬 미리보기 |
| `npm run lint` | ESLint 실행 |

## 4. 디렉터리 하이라이트

- `src/api/` : 백엔드 REST 호출 모듈 (axios 래퍼)
- `src/context/AuthContext.tsx` : 인증 상태/토큰 관리
- `src/pages/` : 로그인·대시보드 페이지
- `src/components/` : 피드, 게시글, 종목, 공통 UI 컴포넌트
- `src/routes/ProtectedRoute.tsx` : 로그인 상태 가드

필요한 추가 기능이 있다면 컴포넌트를 확장하거나 `src/api`에 새 엔드포인트 모듈을 추가해 확장할 수 있습니다. Backlog에 맞춰 최소 UI만 구성했으니, 비즈니스 요구에 맞춰 계속 다듬어 주세요. 💪
