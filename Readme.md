# BODMAS EVALUATOR

### Prerequisite

#### Java
You need have OpenJDK 11+ installed in your system.
In case you don't have OpenJDK 11, you can download the binaries it from [here](https://jdk.java.net/java-se-ri/11). \
Once you are done with the download unzip the contents of the .zip file in a directory and set the `JAVA_HOME` environment variable. You also need to add a path variable as `PATH= %JAVA_HOME%/bin`.
#### Gradle
If you are going to run the application using the source code you need to install and configure gradle. Refer [gradle.org](https://gradle.org/install/) for installation procedure.

### Running the application

#### Using binary
You can download the zip file for the application from here [bodmas_evaluator.zip](https://github.com/EathanHunt/beehyv/files/6489030/bodmas_evaluator.zip), 
extract the contents of the zip file in a suitable folder. After extracting the content just run the _run.bat_ file.

#### Using source
If you want to run the application using the source file there are multiple options available to do the same.
* The simplest way is to execute the _run.bat_ file included in the source.
* You can open a terminal in the root folder of the source and execute the following command `gradlew bootRun`
* If you are using IntelliJ Idea you can select RunApplication in the run configuration option and run it

After running the application using any one of the above methods you should be redirected to home page of swagger, if not you can mannually open [http://localhost:8080/swagger/](http://localhost:8080/swagger/) in your browser
