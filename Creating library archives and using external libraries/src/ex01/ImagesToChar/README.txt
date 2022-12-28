mkdir target
javac src/java/edu/school21/printer/app/Program.java src/java/edu/school21/printer/logic/Logic.java -d target
/bin/cp -R src/resources target
cd target
jar -cfm images-to-chars-printer.jar ../src/manifest.txt -C . *
java -jar images-to-chars-printer.jar 0 .