def call(String appName = 'demo-app', String environment = 'dev', String status = 'STARTED') {
    echo "========================================================"
    echo " [SHARED LIBRARY] Application : ${appName}"
    echo " [SHARED LIBRARY] Environment : ${environment}"
    echo " [SHARED LIBRARY] Status      : ${status}"
    echo " [SHARED LIBRARY] Executed On : ${env.NODE_NAME} (Build #${env.BUILD_NUMBER})"
    echo "========================================================"
}
