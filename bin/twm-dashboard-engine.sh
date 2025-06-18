APP_NAME="twm-dashboard-engine-service"
JAVA_PARAM="-Xmx256m"

BIN_PATH=$DASHBOARD_ENGINE_J/$APP_NAME/bin     #DASHBOARD_ENGINE_J :: exported in .bashrc
cd $BIN_PATH/../target/
JAR_NAME=`ls *jar`
JAR_PATH=$BIN_PATH/../target/$JAR_NAME
JAVA_PATH=$HOME/.jdks/jdk17/bin/java

echo "Starting '$APP_NAME' with java param: '$JAVA_PARAM', at '$JAR_PATH'"
$JAVA_PATH $JAVA_PARAM -jar $JAR_PATH
