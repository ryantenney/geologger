#!/bin/sh
#
# This script copies alert strings into the application
# so that if the application cannot be launched on an 
# older version of the operating system, the user gets a 
# localized error message.  
#
# The particular localizations copied into the application
# are dependent upon the set of localizations installed on
# the machine the application is built on.
#

if [ "${TARGET_BUILD_DIR}" = "" ]; 
then
    TARGET_BUILD_DIR=${BUILD_DIR}
fi

ALERTSTRINGS="OldJavaAlert.strings"
APPLAUNCHRESDIR="/System/Library/PrivateFrameworks/JavaApplicationLauncher.framework/Resources";
LSH=`/bin/ls "$APPLAUNCHRESDIR" | /usr/bin/grep lproj`;
RESOURCEDIR="${TARGET_BUILD_DIR}/$TARGET_NAME.app/Contents/Resources"

for COPYDIR in $LSH
do
        /bin/mkdir -p "$RESOURCEDIR/$COPYDIR"
        /bin/cp "$APPLAUNCHRESDIR/$COPYDIR/$ALERTSTRINGS" "$RESOURCEDIR/$COPYDIR"
done
