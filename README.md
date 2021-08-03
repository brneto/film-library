# Film Library Backend
<!--
![Build Status](https://img.shields.io/jenkins/build?jobUrl=http://ec2-34-252-201-217.eu-west-1.compute.amazonaws.com/job/film-library-ci/&style=for-the-badge)
-->

System to manager the home film library. This project is a backend restful API with Spring Boot 2.

## Requirements

- :whale: [Docker Engine](https://docs.docker.com/engine/install/)

## Getting started

Run following command in project root directory.

1. Build and run your app with Compose
```shell
$ docker compose up --build -d 
```
2. Stop containers
```
docker compose stop backend db
```
3. Remove containers, networks, images, and volumes
```shell
$ docker compose down
```
