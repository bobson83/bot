# Post bot
Send any post request to a service and receive a notification email.
Fire and forget, the programm will be terminate automaticaly.

# How to:

1. Clone the repo
2. Customize "/resources/application.properties" to configure your email notification
3. Customize "/resources/json/config.json" to configure your request
4. Build with "./mvnm clean install" for linux or "mvnm.cmd clean install" for Windows
5. Run artifact from /target with "java -jar bot-0.0.1-SNAPSHOT.jar"
