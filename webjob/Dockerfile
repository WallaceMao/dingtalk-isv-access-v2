FROM registry-vpc.cn-beijing.aliyuncs.com/rsq-public/tomcat:8.0.50-jre8

LABEL name="dingtalk-isv-access-job" \
       description="job server for integration of rishiqing and dingtalk" \
       maintainer="Wallace Mao" \
       org="workbei"

# set default time zone to +0800 (Shanghai)
ENV TIME_ZONE=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TIME_ZONE /etc/localtime && echo $TIME_ZONE > /etc/timezone

ENV CATALINA_HOME /usr/local/tomcat
WORKDIR $CATALINA_HOME

ADD target/ding-isv-access-webjob-*.war webapps/job.war

CMD ["catalina.sh", "run"]