REM -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=0.0.0.0:5005
C:\soft\java-se-8u41-ri\bin\java.exe ^
    -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=0.0.0.0:5005 ^
    -jar nioclient/target/nioclient-1.0-SNAPSHOT-jar-with-dependencies.jar ^
    -consoleEncoding0 UTF-8
pause
