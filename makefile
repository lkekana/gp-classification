CLASSPATH=..

make:
	javac -cp $(CLASSPATH) *.java functions/*.java

run:
	make
	java -cp $(CLASSPATH) GP.Main
	make clean

macrun:
	jenv local 1.8
	make run

clean:
	rm *.class

zipmac:
	rm -f "GP.zip"
	zip -r0vrT "GP.zip" *.java makefile

jar:
	javac -cp . -d . *.java functions/*.java
	jar cfm Main.jar Manifest.txt -C . GP -C . functions -C . "Euro_USD Stock"
	rm -r GP

jarzip:
	rm -f "GP.zip"
	make jar
	cp -r *.java "Euro_USD Stock/" makefile src/
	zip -r0vrT src.zip src
	zip -r0vrT "GP.zip" config.properties Main.jar src.zip "Euro_USD Stock/" README.md