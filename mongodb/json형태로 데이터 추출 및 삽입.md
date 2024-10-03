MongoDB Command Line Database Tool
[https://www.mongodb.com/try/download/database-tools](https://www.mongodb.com/try/download/database-tools)

- MongoDB 데이터베이스 도구는 MongoDB 배포 작업을 위한 명령줄 유틸리티 모음입니다.
- 이러한 도구는 몽고DB 서버 일정과 독립적으로 출시되므로 더 자주 업데이트를 받고 새로운 기능이 제공되는 즉시 활용할 수 있습니다. 자세한 내용은 몽고DB 데이터베이스 도구 설명서를 참조하세요.
- `./mongoexport.exe --help` 와 같은 `--help` 명령으로 쉽게 도움을 받을 수 있음

> 현재 connection uri 예시
> `mongodb+srv://{username}:{password}@cluster0.6mmuwjy.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0`

### json export
- mongodb sample data 사용
- sample data 의 `sample_airbnb` db 의 `listingsAndReviews` collection 사용
- `use sample_airbnb`
- `./mongoexport.exe --uri="mongodb connection uri" -d sample_airbnb -c listingsAndReviews -o dump.json`
- 실행이 완료되면 `dump.json` 파일로 추출된 것을 확인할 수 있음

### json import
- mongodb test db 에 앞서 만들어진 json 파일을 import
- `use test`
- `.\mongoimport.exe --uri="mongodb+srv://duckbill:Hkug0AtjqWc7gGEG@cluster0.6mmuwjy.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0" -d test -c airbnb dump.json`
- `dump.json` 파일이 test db 의 airbnb collection 에 삽입되는것을 확인할 수 있음

