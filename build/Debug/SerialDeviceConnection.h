/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class SerialDeviceConnection */

#ifndef _Included_SerialDeviceConnection
#define _Included_SerialDeviceConnection
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     SerialDeviceConnection
 * Method:    open
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_SerialDeviceConnection_open
  (JNIEnv *, jobject, jstring);

/*
 * Class:     SerialDeviceConnection
 * Method:    puts
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_SerialDeviceConnection_puts
  (JNIEnv *, jobject, jstring);

/*
 * Class:     SerialDeviceConnection
 * Method:    gets
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_SerialDeviceConnection_gets
  (JNIEnv *, jobject);

/*
 * Class:     SerialDeviceConnection
 * Method:    startGetsAsync
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_SerialDeviceConnection_startGetsAsync
  (JNIEnv *, jobject);

/*
 * Class:     SerialDeviceConnection
 * Method:    endGetsAsync
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_SerialDeviceConnection_endGetsAsync
  (JNIEnv *, jobject);

/*
 * Class:     SerialDeviceConnection
 * Method:    setBaud
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_SerialDeviceConnection_setBaud
  (JNIEnv *, jobject, jint);

/*
 * Class:     SerialDeviceConnection
 * Method:    getBaud
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_SerialDeviceConnection_getBaud
  (JNIEnv *, jobject);

/*
 * Class:     SerialDeviceConnection
 * Method:    close
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_SerialDeviceConnection_close
  (JNIEnv *, jobject);

/*
 * Class:     SerialDeviceConnection
 * Method:    getState
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_SerialDeviceConnection_getState
  (JNIEnv *, jobject);

#ifdef __cplusplus
}
#endif
#endif