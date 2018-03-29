build: clean jnilib java

clean:
	rm -f *.class SerialDeviceConnection.o SerialDeviceConnection.so*

java:
	javac *.java

jnilib:
	gcc -c -fPIC -I/System/Library/Frameworks/JavaVM.framework/Headers/ -o SerialDeviceConnection.o SerialDeviceConnection.c
	#gcc -shared -fPIC -Wl,-install_name,SerialDeviceConnection.so.1 -o SerialDeviceConnection.so.1.0.0 SerialDeviceConnection.o
	gcc -shared -fPIC -Wl,-install_name,SerialDeviceConnection -o SerialDeviceConnection.so SerialDeviceConnection.o
	#ln -s SerialDeviceConnection.so.1.0.0 SerialDeviceConnection.so.1
	#ln -s SerialDeviceConnection.so.1 SerialDeviceConnection.so
