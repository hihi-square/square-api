pipeline {
    agent any // 사용 가능한 에이전트에서 이 파이프라인 또는 해당 단계를 실행
    

    tools{
        jdk 'jdk17'
    }

    //환경 변수
    environment{
        JAVA_HOME = "tool jdk17" //java 17gg
        BE_IMAGE_NAME = "square_api" //도커 이미지 이름은 소문자를 권장한다.
        BE_CONTAINER_NAME = "square_api"
        // DOCKER_NETWORK = "special_network"
    }

    stages {
        // stage('Git Clone') {
        //     steps {
        //         dir('/var/jenkins_home/Special') {
        //             sh '''echo 깃허브 체크아웃 및 클론 '''
        //             git branch: 'Back', credentialsId: 'pyeonhallae-access-token', url: 'https://github.com/PyeonHalLae/Special'
        //         }
        //     }
        // }

                stage('jdk 17') {
            steps {
                withEnv(["JAVA_HOME=${tool 'jdk-17.0.2+8'}", "PATH=${tool 'jdk-17.0.2+8'}/bin:${env.PATH}"]) {
                    echo "JDK17 ============================="
                    sh 'java -version'
                    sh 'javac -version'
                }
            }

        stage('BackEnd build'){
            steps{
                // 기존 빌드 결과물 삭제
                sh 'rm -rf build'

                sh'''
                echo 깃허브 BackEnd 빌드 Start
                '''
            
                //application.yml을 복사한다.
                // dir('/var/jenkins_home/special_config/spring'){
                //     sh '''
                //         cp application.yml /var/jenkins_home/Special/BackEnd/src/main/resources
                //     '''
                // }
                // 프로젝트가 있는 폴더로 이동한다.
                dir('/var/jenkins_home/workspace/SQUARECICD_square-api_master') {
                    //권한 문제가 발생해 777로 변경 후 데몬 없이 빌드를 시작한다.
                    //데몬이 있을 경우 가끔 메모리 터지는 경우가 있어서..
                    sh '''
                        chmod +x gradlew
                        ./gradlew clean bootJar --no-daemon
                        ls -al
                        '''
                }
            }
            post {
                // build 성공시
                success {
                    echo 'gradle build success'
                }
                // build 실패시
                failure {
                    echo 'gradle build failed'
                }
            }
        }
        
        stage('Dockerization BackEnd'){
            steps{
                sh 'echo " BackEnd Image Bulid Start"'
                // var/lib/jenkins에서 dockerfile이 있는 곳으로 경로 이동
                // 도커 빌드와 캐시를 삭제한다, || true는 에러가 발생해도 진행되도록한다.
                // dockerfile을 수행하여 지정한 이름으로 이미지를 만든다.
                // 도커에 로그인한 후, 생성한 이미지를 push한다.
                // 푸시한 후 빌드한 이미지는 삭제한다. 메모리 관리를 위해 -&gt; 도커 허브로 push 하는거 잠시 보류
                dir('/var/jenkins_home/workspace/SQUARECICD_square-api_master') {
                    sh '''
                        docker build --no-cache -t ${BE_IMAGE_NAME}:latest .
                        '''
                        // echo ${DOCKER_PW} | docker login -u ${DOCKER_ID} --password-stdin
                        // docker push ${BE_IMAGE_NAME}
                        // docker rmi -f ${BE_IMAGE_NAME}
                }
            }
            post {
                // 이미지 전송 성공시
                success {
                    sh 'echo "build Docker BE Image Success"'
                }
                // 이미지 전송 실패시
                failure {
                    sh 'echo "build Docker BE Image Fail"'
                }
            }
        }     
        
        stage('BE Deploy'){
            steps{
                dir('/var/jenkins_home/Special'){
                    script {
                        def isRunning = sh(script: "docker ps -q -f name=${BE_CONTAINER_NAME}", returnStdout: true).trim()
                        if (isRunning) {
                            // 컨테이너가 실행 중인 경우, 중지 및 삭제fsddsdfedsf
                            sh "docker stop ${BE_CONTAINER_NAME}"
                            sh "docker rm -f ${BE_CONTAINER_NAME}"
                        }
            
                        // 새 컨테이너 시작
                        sh "docker run --name ${BE_CONTAINER_NAME} -d -p 9999:9999 ${BE_IMAGE_NAME}"
                        // 추가적인 작업을 수행할 수 있습니다.
                    }    
                }
                
            }
            //ssl 적용시 사용할 코드
            //docker run -d --name ${BE_CONTAINER_NAME}  --network ${DOCKER_NETWORK} -e TZ=Asia/Seoul -p 9999:9999 minyumanyu/special_be:latest   
        }
    }
}