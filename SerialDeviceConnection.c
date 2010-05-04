/*	SerialDeviceConnection.c

	This program is free software; you can redistribute it and/or modify
	it under the terms of the GNU General Public License version 2.x,
	as published by	the Free Software Foundation;

	This program is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU General Public License for more details.

	You should have received a copy of the GNU General Public License
	along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

#include <stdlib.h>
#include <termios.h>
#include <string.h>
#include "SerialDeviceConnection.h"

#define ASYNC	 2
#define OPEN	 1
#define CLOSED	 0
#define FAILED  -1
#define CLOSING	-2
#define ATEOF	-3
#define ERROR	-4

#define RECEIVE  1
#define TRANSMIT 2

static FILE *serial;
static int state;

/*
 * Class:     SerialDeviceConnection
 * Method:    open
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_SerialDeviceConnection_open( JNIEnv *jenv, jobject obj, jstring jport )
{
	// convert port name passed to this method
	const char *port = (*jenv)->GetStringUTFChars( jenv, jport, 0 );

	// get a reference to the calling java class
	jclass myClass = (*jenv)->GetObjectClass( jenv, obj );
	
	// get the method ids to allow calling methods within the calling java class
	jmethodID deviceStateEventID = (*jenv)->GetMethodID( jenv, myClass, "deviceStateEvent", "(I)V" );

	// open a connection to the port specified
	serial = fopen( port, "r+" );
	
	// test if port opened
	if( serial == NULL )
	{
		// port could not be opened
		state = FAILED;
	}
	else
	{
		// port opened
		state = OPEN;
	}

	// raise state event
	(*jenv)->CallVoidMethod( jenv, obj, deviceStateEventID, state );
	
	// garbage collect port string
	(*jenv)->ReleaseStringUTFChars( jenv, jport, port );
	
	return;
}

/*
 * Class:     SerialDeviceConnection
 * Method:    puts
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_SerialDeviceConnection_puts( JNIEnv *jenv, jobject obj, jstring jdata )
{
	// verify port is connected before proceeding
	if( serial != NULL && state > CLOSED )
	{
		// get native var from jni type
		const char *data = (*jenv)->GetStringUTFChars( jenv, jdata, 0 );

		// put string to serial port
		fputs( data, serial );
		
		// release passed string
		(*jenv)->ReleaseStringUTFChars( jenv, jdata, data );
	}
	
	return;
}

/*
 * Class:     SerialDeviceConnection
 * Method:    gets
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_SerialDeviceConnection_gets( JNIEnv *jenv, jobject obj )
{
	// verify port is connected before proceeding
	if( serial != NULL && state == OPEN )
	{
		// allocate storage for incoming data
		char *data = malloc( 8192 );
		
		// get incoming data on this port
		fgets( data, 8192, serial );
		
		// convert to jni string type
		jstring java_str = (*jenv)->NewStringUTF( jenv, data );
		
		// free memory
		free( data );
		
		return java_str;
	}
	
	return;
}

/*
 * Class:     SerialDeviceConnection
 * Method:    startGetsAsync
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_SerialDeviceConnection_startGetsAsync( JNIEnv *jenv, jobject obj )
{
	// verify port is connected before proceeding
	if( serial != NULL && state == OPEN )
	{
		// get a reference to the calling java class
		jclass myClass = (*jenv)->GetObjectClass( jenv, obj );
		
		// get the method ids to allow calling methods within the calling java class
		jmethodID deviceStateEventID = (*jenv)->GetMethodID( jenv, myClass, "deviceStateEvent", "(I)V" );
		jmethodID deviceDataEventID = (*jenv)->GetMethodID( jenv, myClass, "raiseDataEvent", "(ILjava/lang/String;)V" );
		
		// indicate getsAsync is running
		state = ASYNC;
		
		// raise state event
		(*jenv)->CallVoidMethod( jenv, obj, deviceStateEventID, state );
		
		// allocate data array
		//char data[ 8192 ];
		char *data = malloc( 8192 );
		
		// while data is available and there is no request to close port
		while( !feof( serial ) && state == ASYNC )
		{
			if( fgets( data, 8192, serial ) )
			{
				// create a jstring to allow passing through JNI
				jstring java_str = (*jenv)->NewStringUTF( jenv, data );
				
				// raise data event with received data
				(*jenv)->CallVoidMethod( jenv, obj, deviceDataEventID, strlen( data ), java_str );
			}
		}
	
		// frees the 8k block of memory
		free( data );
	
		if( feof( serial ) )
		{
			// close due to eof
			fclose( serial );
			
			// port reached EOF
			state = ATEOF;
		}
		else if( state == CLOSING )
		{
			// close has been called
			fclose( serial );
			
			// port should now be closed, update status
			state = CLOSED;
		}
		else if( state == OPEN )
		{
			// endGetsAsync has been called
		}
		else
		{
			// encountered an error
			state = ERROR;
		}
	
		// raise state event
		(*jenv)->CallVoidMethod( jenv, obj, deviceStateEventID, state );
	}
	
	return;
}

/*
 * Class:     SerialDeviceConnection
 * Method:    endGetsAsync
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_SerialDeviceConnection_endGetsAsync( JNIEnv *jenv, jobject obj )
{
	if( serial != NULL && state == ASYNC )
	{
		// change state to end async
		state = OPEN;
		
		// don't raise deviceStateEvent from here... let it run from the async method
//		// get a reference to the calling java class
//		jclass myClass = (*jenv)->GetObjectClass( jenv, obj );
//		
//		// get the method ids to allow calling methods within the calling java class
//		jmethodID deviceStateEventID = (*jenv)->GetMethodID( jenv, myClass, "deviceStateEvent", "(I)V" );
//		
//		// raise state event
//		(*jenv)->CallVoidMethod( jenv, obj, deviceStateEventID, state );
	}
	
	return;
}

/*
 * Class:     SerialDeviceConnection
 * Method:    getBaud
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_SerialDeviceConnection_getBaud( JNIEnv *jenv, jobject obj )
{
	if( serial != NULL && state > CLOSED )
	{
		int baudrate = 0;
		struct termios options;
		
		// get the file descriptor
		int fd = fileno( serial );
		
		// get current termio settings
		tcgetattr( fd, &options );
		
		// get the current baud rate
		baudrate = cfgetispeed( &options );
		
		// select the constant value returned and return the baud rate as an int
		switch( baudrate )
		{
			case B50     : return 50;
			case B75     : return 75;
			case B110    : return 110;
			case B134    : return 134;
			case B150    : return 150;
			case B200    : return 200;
			case B300    : return 300;
			case B600    : return 600;
			case B1200   : return 1200;
			case B1800   : return 1800;
			case B2400   : return 2400;
			case B4800   : return 4800;
			case B9600   : return 9600;
			case B19200  : return 19200;
			case B38400  : return 38400;
			case B57600  : return 57600;
			case B115200 : return 115200;
			case B230400 : return 230400;
		}
	}
	
	return;
}

/*
 * Class:     SerialDeviceConnection
 * Method:    setBaud
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_SerialDeviceConnection_setBaud( JNIEnv *jenv, jobject obj, jint baudrate )
{
	if( serial != NULL && state == OPEN )
	{
		int setbaud = 0;
		struct termios options;
		
		// take int baud rate input and select the associated constant
		switch( baudrate )
		{
			case 50     : setbaud = B50;     break;
			case 75     : setbaud = B75;     break;
			case 110    : setbaud = B110;    break;
			case 134    : setbaud = B134;    break;
			case 150    : setbaud = B150;    break;
			case 200    : setbaud = B200;    break;
			case 300    : setbaud = B300;    break;
			case 600    : setbaud = B600;    break;
			case 1200   : setbaud = B1200;   break;
			case 1800   : setbaud = B1800;   break;
			case 2400   : setbaud = B2400;   break;
			case 4800   : setbaud = B4800;   break;
			case 9600   : setbaud = B9600;   break;
			case 19200  : setbaud = B19200;  break;
			default:
			case 38400  : setbaud = B38400;  break;
			case 57600  : setbaud = B57600;  break;
			case 115200 : setbaud = B115200; break;
			case 230400 : setbaud = B230400; break;
		}
	
		// get the file descriptor
		int fd = fileno( serial );

		// get current termio settings
		tcgetattr( fd, &options );
	
		// set baud rate (both input and output)
		cfsetispeed( &options, setbaud );
		cfsetospeed( &options, setbaud );
	
		// set modified termio settings
		tcsetattr( fd, TCSANOW, &options );
	}
	
	return;
}

/*
 * Class:     SerialDeviceConnection
 * Method:    close
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_SerialDeviceConnection_close( JNIEnv *jenv, jobject obj )
{
	// verify port is connected before proceeding
	if( serial != NULL && state > CLOSED )
	{
		// get a reference to the calling java class
		jclass myClass = (*jenv)->GetObjectClass( jenv, obj );
		
		// get the method ids to allow calling methods within the calling java class
		jmethodID deviceStateEventID = (*jenv)->GetMethodID( jenv, myClass, "deviceStateEvent", "(I)V" );
		
		if( state == ASYNC )
		{
			// the async method takes care of the rest
			state = CLOSING;
			
			// raise state event
			(*jenv)->CallVoidMethod( jenv, obj, deviceStateEventID, state );
		}
		else if( state == OPEN )
		{
			// port is closing
			state = CLOSING;
			
			// raise state event
			(*jenv)->CallVoidMethod( jenv, obj, deviceStateEventID, state );
			
			// close the port
			fclose( serial );
			
			// port has closed
			state = CLOSED;
			
			// raise state event
			(*jenv)->CallVoidMethod( jenv, obj, deviceStateEventID, state );
		}
	}
	
	return;
}

/*
 * Class:     SerialDeviceConnection
 * Method:    getState
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_SerialDeviceConnection_getState( JNIEnv *jenv, jobject obj )
{
	// return port state
	return state;
}