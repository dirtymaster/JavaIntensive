mkdir target
unzip lib/JCDP-4.0.2.jar -d target
unzip -n lib/jcommander-1.82.jar -d target
javac src/java/edu/school21/printer/app/Program.java src/java/edu/school21/printer/logic/Logic.java src/java/edu/school21/printer/logic/Args.java -cp "lib/JCDP-4.0.2.jar:lib/jcommander-1.82.jar" -d target
/bin/cp -R src/resources target
cd target
jar -cfm images-to-chars-printer.jar ../src/manifest.txt -C . *
java -jar images-to-chars-printer.jar --white=RED --black=GREEN