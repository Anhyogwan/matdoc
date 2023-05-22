# SSAFY 8기 특화프로젝트 B108 '맞닥'

## : 한 눈에 들어오는 메디컬 서비스

![타이틀](DOCS/images/%ED%83%80%EC%9D%B4%ED%8B%80.png)

<br>

# ✔ Project Summary

### 개발 기간 : 2023.02.27 ~ 2023.04.07

### 팀원!
- 권지훈 (팀장) - Back-End, Front-End
- 이승민 - Back-End, ppt
- 조원희 - Back-End, Docs, presentation
- 한인환 - Front-End
- 송기라 - Front-End
- 안효관 - DevOps, Back-End
<br>
<br>

---
## 서비스 설명
- 병원을 고를 때 **신뢰성 있는** 병원 및 의약품 정보를 찾기 어려움
- 내가 원하는 **니즈를 충족**하는 병원을 찾기 힘듦
- 한 눈에 들어오는 **메디컬 서비스**

![Untitled](DOCS/images/%EC%84%9C%EB%B9%84%EC%8A%A4%EC%86%8C%EA%B0%9C1.png)

- 증상에 따른 병원을 **필터링**
- **신뢰성** 있는 병원 및 의약품 정보 제공
- **사용자 맞춤** 서비스 제공

<br>


# ✔ Information


## 메인 기능

- 현 위치에서 **5km 이내에 영업중**인 병원 정보 제공
- **증상에 따른** 병원정보 제공
- 병원 및 의약품의 **자세한** 정보 제공
- 복용중인 **약 관리** 및 병원 **즐겨찾기**
<br>

![Untitled](DOCS/images/%EC%8B%9C%EC%97%B0%EC%8B%9C%EB%82%98%EB%A6%AC%EC%98%A4/Untitled%204.png)
![Untitled](DOCS/images/%EC%8B%9C%EC%97%B0%EC%8B%9C%EB%82%98%EB%A6%AC%EC%98%A4/Untitled%2020.png)

## 기대효과

---

- 아플 때 **신속하게** 원하는 조건의 병원을 찾을 수 있다.
- 의약품의 **성분** 및 **함께 복용하면 안 되는** 약을 확인함으로써 유용하게 **건강을 관리**할 수 있다.
- 병원 및 의약품의 자세한 정보를 **맞닥**에서 **한 번에** 알 수 있다.

![Untitled](DOCS/images/%EA%B8%B0%EB%8C%80%ED%9A%A8%EA%B3%BC.png)

<br>

# 설계 및 산출물
## 🏣 [기획서](./DOCS/맞닥_기획서.md)
## 📜 [기능명세서](./DOCS/기능명세서.md)
## 💾 [ERD](./DOCS/ERD.md) 
## 🔑 [DB컬럼설명](./DOCS/DB컬럼설명.md)
## 📡 [API명세서](./DOCS/API명세서.md)
## 🗺 [아키텍쳐설계도](./DOCS/images/아키텍쳐설계도.png)
## 🏹 [포팅메뉴얼](./DOCS/포팅매뉴얼.md)
## 📺 [시연시나리오](./DOCS/시연시나리오.md)
<br>

### 배포(CI/CD)

---

- AWS EC2 - ubuntu os 20.04 LTS
- Docker
- k8s (kubeadm)
- Nginx
    - Nginx Ingress controller와 service로 분기처리 및 로드밸런싱
- Jenkins
    - Master/Agent로 병렬 처리
    - pipeline 스크립트 작성으로 CI/CD 적용(GitOps)
- ArgoCD
- https
    - cert-manager + letsencrypte를 통한 인증서 발급
- Grafana + Prometheus

<br>

# ✔ Cooperation
- Git
![깃](/DOCS/images/git.png)
- Jira
![지라](/DOCS/images/jira.png)
- Notion
![노션](/DOCS/images/%EB%85%B8%EC%85%98.png)
- MatterMost
- Webex
- Discord


