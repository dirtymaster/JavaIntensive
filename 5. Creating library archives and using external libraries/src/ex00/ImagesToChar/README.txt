mkdir target
javac src/java/edu/school21/printer/app/Program.java src/java/edu/school21/printer/logic/Logic.java -d target
java -classpath target edu.school21.printer/app/Program . 0 ../../../materials/it.bmp