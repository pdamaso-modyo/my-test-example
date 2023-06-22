FROM amazoncorretto:17.0.7-al2023

ENV STAGE_NAME certification

WORKDIR /usr/app

COPY extras/newrelic/ newrelic/

COPY build/libs/*[^\\-plain].jar .
RUN export JAR_FILE=$(ls -t *.jar | head -n1); jar -xf $JAR_FILE; rm *.jar

RUN yum update -y --security && \
  yum clean all && \
  rm -rf /var/cache/yum

CMD java \
 -javaagent:newrelic/newrelic.jar -Dnewrelic.environment=$STAGE_NAME \
 -XX:MaxRAMPercentage=75.0 -XX:MinRAMPercentage=75.0 -XX:InitialRAMPercentage=75.0 \
 -Dhttps.protocols=TLSv1.2 org.springframework.boot.loader.JarLauncher
