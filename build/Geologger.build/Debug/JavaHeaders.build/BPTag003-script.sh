#!/bin/sh
javah -classpath "${BUILT_PRODUCTS_DIR}/JavaHeaders.jar" -force -o "${BUILT_PRODUCTS_DIR}/SerialDeviceConnection.h" "SerialDeviceConnection"