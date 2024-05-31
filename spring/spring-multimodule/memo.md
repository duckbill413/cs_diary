### `profile`을 설정하여 스프링 서버 실행하기
```text
java -jar -Dspring.profiles.active=local module-api-0.0.1-SNAPSHOT.jar
```
만약, `Error: Unable to access jarfile .profiles.active=local` 에러가 발생한 경우
```bash
java -jar "-Dspring.profiles.active=local" module-api-0.0.1-SNAPSHOT.jar
```
이 처럼 실행 `-Dspring`을 jar 파일로 인지 했을 수 있음