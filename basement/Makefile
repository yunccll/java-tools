NAMESPACE   := com.chenglun
ARTIFACT_ID := basement
#NAMESPACE 	:= com.chenglun.app
#ARTIFACT_ID := Hello
ENTRY_CLASS := App

APP_VERSION := 1.0

REF_FULL_JARS := /Users/chenglun/.m2/repository/org/slf4j/slf4j-api/1.7.25/slf4j-api-1.7.25.jar \
	/Users/chenglun/.m2/repository/org/slf4j/slf4j-log4j12/1.7.6/slf4j-log4j12-1.7.6.jar \
	/Users/chenglun/.m2/repository/log4j/log4j/1.2.17/log4j-1.2.17.jar

REF_JARS 	:= #mybatis-3.4.4.jar mysql-connector-java-6.0.2.jar


#------------------- fixed ---------
HIDE=
SPACE=$(NULLSTRING) #end

TARGET_DIR	:= ./target
APP_JAR		:= $(ARTIFACT_ID)-$(APP_VERSION)-SNAPSHOT.jar
MAIN_CLASS 	:= $(NAMESPACE).$(ENTRY_CLASS)

APP_JAR_FULL_PATH 	:= $(addprefix $(TARGET_DIR)/, $(APP_JAR))
REF_JARS_FULL_PATH	:= $(addprefix $(TARGET_DIR)/dependency/, $(REF_JARS))

JAR_FILES := $(subst $(SPACE),:,$(strip $(APP_JAR_FULL_PATH) $(REF_JARS_FULL_PATH) $(REF_FULL_JARS)))


all:
	$(HIDE)mvn package
	$(HIDE)java -cp $(JAR_FILES)  $(MAIN_CLASS)

test:
	$(HIDE)mvn test

run:
	$(HIDE)java -cp $(JAR_FILES)  $(MAIN_CLASS)

clean:
	$(HIDE)mvn clean

compile:
	$(HIDE)mvn compile 

depend:
	$(HIDE)mvn clean install -e -U

create:
	$(HIDE)mvn -B archetype:generate -DgroupId=$(NAMESPACE) -DartifactId=$(ARTIFACT_ID) -DarchetypeArtifactId=maven-archetype-quickstart
