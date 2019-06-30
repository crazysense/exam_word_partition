Exam_Word_Partition
===

# 목차
---
* [시스템 요구사항](#시스템-요구사항)  
* [개발 환경](#개발-환경)  
* [빌드 및 실행](#빌드-및-실행)  
    * [빌드](#빌드)  
    * [실행](#실행)  
* [로깅](#로깅)  
* [클래스 설계(작성중)](#클래스-설계)  
* [실행 결과(작성중)](#실행-결과)  

## 시스템 요구사항
* JDK 8 또는 그 이상

## 개발 환경
* Java 1.8, IntelliJ IDEA
* Maven3
* JUnit4, Mockito, PowerMock

## 빌드 및 실행
### 빌드
* Maven Download: https://maven.apache.org/download.cgi
```console
$ git clone https://github.com/crazysense/exam_word_partition.git
$ cd exam_word_partition
$ git checkout develop
$ git pull origin develop
$ mvn clean package [-Dmaven.test.skip=true]
```

### 실행
```console
$ cd target
$ java -jar ./word-partition-0.1.jar [SOURCE_PATH] [TARGET_DIR_PATH] [PATITION_NUMBER]
```

* 실행시 반드시 아래 세가지 인자가 순서대로 전달되어야 합니다.  

Args|Label|Description|Required
:---:|---|:---:|:---:
0|`SOURCE_PATH`|읽어올 파일의 경로. 접근 가능한 파일이 존재해야 함|O
1|`TARGET_DIR_PATH`|결과가 저장될 디렉토리의 경로. 생성 가능해야 함|O
2|`PARTITION_NUMBER`|1 < (N) < 27 범위에서 지정 가능|O

## 로깅
* Logger 는 java.util.logging.Logger 를 사용하였습니다.  
Logger 를 변경하려면 빌드가 완료된 target 디렉토리의 logging.properties 파일을 수정하고  
프로그램 실행시 JVM Argument 에 -Djava.util.logging.config.file 옵션을 추가하십시오.  
  
  *Logger 를 설정하지 않은 경우에는 ConsoleHandler 의 INFO Level 로 동작합니다.*
```console
$ java -Djava.util.logging.config.file=./logging.properties \
> -jar ./word-partition-0.1.jar [READ_FILE_PATH] [WRITE_DIR_PATH] [PATITION_NUMBER]
```

## 클래스 설계
### Channel
### Producer
### Consumer
### Partitioner
### Selector
### Stream

## 실행 결과
이미지 붙일 것.
