/*	SerialDeviceConnection.h

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

#define ASYNC	 2
#define OPEN	 1
#define CLOSED	 0
#define FAILED  -1
#define CLOSING	-2
#define ATEOF	-3
#define ERROR	-4

#define RECEIVE  1
#define TRANSMIT 2

JNIEXPORT void JNICALL Java_SerialDeviceConnection_open( JNIEnv *jenv, jobject obj, jstring jport );
JNIEXPORT void JNICALL Java_SerialDeviceConnection_puts( JNIEnv *jenv, jobject obj, jstring jdata );
JNIEXPORT jstring JNICALL Java_SerialDeviceConnection_gets( JNIEnv *jenv, jobject obj );
JNIEXPORT void JNICALL Java_SerialDeviceConnection_startGetsAsync( JNIEnv *jenv, jobject obj );
JNIEXPORT void JNICALL Java_SerialDeviceConnection_endGetsAsync( JNIEnv *jenv, jobject obj );
JNIEXPORT jint JNICALL Java_SerialDeviceConnection_getBaud( JNIEnv *jenv, jobject obj );
JNIEXPORT void JNICALL Java_SerialDeviceConnection_setBaud( JNIEnv *jenv, jobject obj, jint baudrate );
JNIEXPORT void JNICALL Java_SerialDeviceConnection_close( JNIEnv *jenv, jobject obj );
JNIEXPORT jint JNICALL Java_SerialDeviceConnection_getState( JNIEnv *jenv, jobject obj );
