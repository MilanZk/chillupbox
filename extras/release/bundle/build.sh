#!/bin/bash

# Usage function
usage(){
	echo "Usage: $0 aab_filename [jks_file] [jks_password] [jks_alias]"
	exit 0
}

bundleToolFileName="bundletool-all-0.9.0.jar" 

# Default values for Java Keys Store file
defaultJKSFile="hiberus.jks"
defaultPassword="ndrddvlpr_2019"
defaultAlias="key0"

# Parse AAB filename
aabFileName=$(basename -- "$1")
extension="${aabFileName##*.}"
fileName="${aabFileName%.*}"

# Initialise parameters
case $# in
1)
echo "Using default values..."
jks_file=$defaultJKSFile
jks_password=$defaultPassword
jks_alias=$defaultAlias
;;
4)
jks_file=%1
jks_password=%2
jks_alias=%3
;;
*)
usage
esac

if [ -f $fileName.apks ] ; then
    rm $fileName.apks
fi

echo "Building APK library for connected device..."
java -jar $bundleToolFileName build-apks --connected-device --bundle=$aabFileName --output=$fileName.apks --ks=$jks_file --ks-pass=pass:$jks_password --ks-key-alias=$jks_alias --key-pass=pass:$jks_password

if [ -f $fileName.apks ] ; then
	echo "Installing APK in connected device..."
	java -jar $bundleToolFileName install-apks --apks=$fileName.apks
	
	echo "Done!"
fi