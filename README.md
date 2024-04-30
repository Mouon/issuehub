# Issurhub
## Stack
<img src="https://img.shields.io/badge/Elasticsearch-005571?style=for-the-badge&logo=Elasticsearch&logoColor=white"> <img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=OpenJDK&logoColor=white"> <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"> <img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white"> <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"> <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">
## 소개
Everysource는 오픈소스 프로젝트의 이슈를 쉽고 빠르게 찾을 수 있는 웹사이트입니다. 이 사이트는 엘라스틱 서치를 주 검색 엔진으로 활용하며, 자바 스프링 부트 프레임워크 위에서 JPA를 사용하여 개발되었습니다. 이 프로젝트는 깃허브의 공개 레포지토리에서 이슈와 README 파일을 자동으로 가져와 사용자에게 제공합니다. 또한 errorhub라는 카테고리를 누르면 사용자들끼리 에러와 그 해결책을 공유할 수 있는 커뮤니티가 제공됩니다. 작성은 MarkDown형식으로 작설 할 수 있으며, errorhub의 검색엔진역시 엘라스틱 서치를 통해 빠르게 검색이 가능합니다.
엘라스틱서치의 쿼리문 개선 데이터 베이스와 관련 컨트롤러와 서비스계층의 개선작업 중입니다.

## 실행 화면
<img width="1602" alt="스크린샷 2024-04-25 오후 8 21 57" src="https://github.com/Mouon/issuehub/assets/137624597/ad04175c-5320-4a9c-a124-047a265b8360">
<img width="1624" alt="스크린샷 2024-04-25 오후 8 22 32" src="https://github.com/Mouon/issuehub/assets/137624597/0af37291-76eb-447f-aa78-987b5b4b198f">

<img width="1624" alt="스크린샷 2024-04-25 오후 8 22 46" src="https://github.com/Mouon/issuehub/assets/137624597/12754963-c9a2-407d-a50f-e57c6d382586">
<img width="1624" alt="스크린샷 2024-04-29 오후 3 24 34" src="https://github.com/Mouon/issuehub/assets/137624597/25fee05d-7c50-4a43-85c6-d0394f5c8b21">
<img width="1624" alt="스크린샷 2024-04-29 오후 3 24 34" src="https://github.com/Mouon/issuehub/assets/137624597/d0e7120f-36a4-4827-ae4f-219d6ca71853">
<img width="1624" alt="스크린샷 2024-04-29 오후 3 24 48" src="https://github.com/Mouon/issuehub/assets/137624597/d1f69845-6f43-406f-bf14-231f6f05a608">
<img width="1624" alt="스크린샷 2024-04-29 오후 3 28 33" src="https://github.com/Mouon/issuehub/assets/137624597/182451ad-994d-4901-b471-75829ebf6f92">

## 로컬 실행 주의점
엘라스틱서치 서버 실행:
엘라스틱서치가 로컬 머신에 설치되어 있고, 서비스가 시작되어 있는지 확인하세요.

## 기능
이슈 검색: 깃허브의 오픈소스 프로젝트 이슈를 실시간으로 검색합니다.  
자동 업데이트: 스케줄러를 통해 정기적으로 최신 이슈 정보를 업데이트합니다.  
README 조회: 각 프로젝트의 README 파일을 자동으로 가져와 보여줍니다.  
멀티 리포지토리 지원: 다양한 깃허브 리포지토리를 지원합니다.  



## 아키택처
<img width="459" alt="스크린샷 2024-04-25 오후 8 26 32" src="https://github.com/Mouon/issuehub/assets/137624597/018bb2f7-9402-40e5-a8d2-3cbcac7a12ab">

