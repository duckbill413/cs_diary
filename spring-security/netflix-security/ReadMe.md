- docker-compose 실행 명령어
  ```
  docker-compose -f ./infra/docker-compose.yml up -d
  ```

### React Project 생성
1. `NodeJS` 설치
2. `NPX` 설치
   ```
   npm install npx -g
   ```
3. React Project 생성
   ```
   npx create-react-app netflix-fonrtend
   ```
4. React 패키지 설치
   ```
   npm install
   ```
   - `24.12.08` 현재 `node 20.9.0`에서 프로젝트 실행시 에러가 발생
     ```
     npm i web-vitals --save-dev
     ```
     - 위의 `web-vitals` 패키지를 추가적으로 설치해 주었을때 정상 실행 확인
5. React Project 실행
   ```
   npm run start
   ```

- 현재 front는 `3000`번 포트를 사용하고 서버는 `8080` 포트를 사용
- `3000`번 포트에서 `8080`포트로 proxy 설정이 필요
- React Project의 `package-lock.json` 파일을 일부 수정
    ```javascript
    {
        "name": "netflix-frontend",
        "version": "0.1.0",
        "proxy": "http://localhost:8080", <= 추가된 라인
        "private": true,
        "dependencies": {...}
        ...
    }
    ```