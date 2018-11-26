FROM centos:7
# 构建时传入的变量
ARG BUILD_GROUP_ID
ARG BUILD_REGION
# 安装打包必备软件
RUN yum -y install wget
# 准备运行时环境变量
ENV JAVA_HOME /usr/java/latest
ENV PATH $PATH:$JAVA_HOME/bin
# 准备SchedulerX环境变量
ENV SCX_HOME /home/admin/schedulerx
# 此前创建的任务分组
ENV SCX_GROUP_ID $BUILD_GROUP_ID
# 任务集群要部署的地域
ENV SCX_REGION $BUILD_REGION
# 设置 EDAS-Container 版本
ENV EDAS_CONTAINER_VERSION V3.5.0
LABEL pandora="V3.5.0"
# 下载安装 JDK 8
RUN wget http://edas-hz.oss-cn-hangzhou.aliyuncs.com/agent/prod/files/jdk-8u65-linux-x64.rpm -O /tmp/jdk-8u65-linux-x64.rpm && \
    yum -y install /tmp/jdk-8u65-linux-x64.rpm && \
    rm -rf /tmp/jdk-8u65-linux-x64.rpm
# 下载安装schedulerx-client运行包
RUN mkdir -p ${SCX_HOME}
#RUN wget http://schedulerx.oss-cn-hangzhou.aliyuncs.com/Yanxun/schedulerx-client-script.tar -O ${SCX_HOME}/schedulerx-client-edas.tar && \
#    tar -xvf ${SCX_HOME}/schedulerx-client-edas.tar -C ${SCX_HOME} && \
#    mv ${SCX_HOME}/target/* ${SCX_HOME}/ && \
#    rm -rf ${SCX_HOME}/schedulerx-client-edas.tar ${SCX_HOME}/target

# 生成执行命令
RUN echo 'java -cp lib/*:ding-isv-access-taskjob.jar ClientMain' > ${SCX_HOME}/start.sh && chmod +x ${SCX_HOME}/start.sh
# 设定运行目录
WORKDIR ${SCX_HOME}
# 运行命令
CMD ["/bin/bash", "/home/admin/schedulerx/start.sh"]