#!/bin/bash

# Name:    MelissaRightFielderObjectLinuxJava
# Purpose: Use the Melissa Updater to make the MelissaRightFielderObjectLinuxJava usable

######################### Constants ##########################

RED='\033[0;31m' #RED
NC='\033[0m' # No Color

######################### Parameters ##########################

rfinput=""
license=""
quiet="false"

while [ $# -gt 0 ] ; do
  case $1 in
    -r | --rfinput) 
        rfinput="$2" 

        if [ "$rfinput" == "-l" ] || [ "$rfinput" == "--license" ] || [ "$rfinput" == "-q" ] || [ "$rfinput" == "--quiet" ] || [ -z "$rfinput" ];
        then
            printf "${RED}Error: Missing an argument for parameter \'rfinput\'.${NC}\n"  
            exit 1
        fi 
        ;;
    -l | --license) 
        license="$2" 

        if [ "$license" == "-q" ] || [ "$license" == "--quiet" ] || [ "$license" == "-r" ] || [ "$license" == "--rfinput" ] || [ -z "$license" ];
        then
            printf "${RED}Error: Missing an argument for parameter \'license\'.${NC}\n"  
            exit 1
        fi   
        ;;
    -q | --quiet) 
        quiet="true" 
        ;;
  esac
  shift
done


# ######################### Config ###########################
RELEASE_VERSION='2023.11'
ProductName="RF_DATA"

# Uses the location of the .sh file 
# Modify this if you want to use 
CurrentPath=$(pwd)
ProjectPath="$CurrentPath/MelissaRightFielderObjectLinuxJava"
BuildPath="$ProjectPath"
DataPath="$ProjectPath/Data"

if [ ! -d $DataPath ];
then
    mkdir $DataPath
fi

if [ ! -d $BuildPath ];
then
    mkdir $BuildPath
fi

# Config variables for download file(s)
Config_FileName="libmdRightFielder.so"
Config_ReleaseVersion=$RELEASE_VERSION
Config_OS="LINUX"
Config_Compiler="GCC41"
Config_Architecture="64BIT"
Config_Type="BINARY"

Wrapper_FileName="libmdRightFielderJavaWrapper.so"
Wrapper_ReleaseVersion=$RELEASE_VERSION
Wrapper_OS="LINUX"
Wrapper_Compiler="JAVA"
Wrapper_Architecture="64BIT"
Wrapper_Type="INTERFACE"

Com_FileName="mdRightFielder_JavaCode.zip"
Com_ReleaseVersion=$RELEASE_VERSION
Com_OS="ANY"
Com_Compiler="ANY"
Com_Architecture="ANY"
Com_Type="DATA"

# ######################## Functions #########################
DownloadDataFiles()
{
    printf "=============================== MELISSA UPDATER ==============================\n"
    printf "MELISSA UPDATER IS DOWNLOADING DATA FILE(S)...\n"

    ./MelissaUpdater/MelissaUpdater manifest -p $ProductName -r $RELEASE_VERSION -l $1 -t $DataPath 

    if [ $? -ne 0 ];
    then
        printf "\nCannot run Melissa Updater. Please check your license string!\n"
        exit 1
    fi     
    
    printf "Melissa Updater finished downloading data file(s)!\n"
}

DownloadSO() 
{
    printf "\nMELISSA UPDATER IS DOWNLOADING SO(S)...\n"
    
    # Check for quiet mode
    if [ $quiet == "true" ];
    then
        ./MelissaUpdater/MelissaUpdater file --filename $Config_FileName --release_version $Config_ReleaseVersion --license $1 --os $Config_OS --compiler $Config_Compiler --architecture $Config_Architecture --type $Config_Type --target_directory $BuildPath &> /dev/null
        if [ $? -ne 0 ];
        then
            printf "\nCannot run Melissa Updater. Please check your license string!\n"
            exit 1
        fi
    else
        ./MelissaUpdater/MelissaUpdater file --filename $Config_FileName --release_version $Config_ReleaseVersion --license $1 --os $Config_OS --compiler $Config_Compiler --architecture $Config_Architecture --type $Config_Type --target_directory $BuildPath 
        if [ $? -ne 0 ];
        then
            printf "\nCannot run Melissa Updater. Please check your license string!\n"
            exit 1
        fi
    fi
    
    printf "Melissa Updater finished downloading $Config_FileName!\n"
}

DownloadWrappers() 
{    
    # Check for quiet mode
    if [ $quiet == "true" ];
    then
        # Download the wrapper
        ./MelissaUpdater/MelissaUpdater file --filename $Wrapper_FileName --release_version $Wrapper_ReleaseVersion --license $1 --os $Wrapper_OS --compiler $Wrapper_Compiler --architecture $Wrapper_Architecture --type $Wrapper_Type --target_directory $ProjectPath &> /dev/null
        if [ $? -ne 0 ];
        then
            printf "\nCannot run Melissa Updater. Please check your license string!\n"
            exit 1
        fi

        # Download the com zip
        ./MelissaUpdater/MelissaUpdater file --filename $Com_FileName --release_version $Com_ReleaseVersion --license $1 --os $Com_OS --compiler $Com_Compiler --architecture $Com_Architecture --type $Com_Type --target_directory $ProjectPath &> /dev/null
        if [ $? -ne 0 ];
        then
            printf "\nCannot run Melissa Updater. Please check your license string!\n"
            exit 1
        fi
    else
        #Download the wrapper
        ./MelissaUpdater/MelissaUpdater file --filename $Wrapper_FileName --release_version $Wrapper_ReleaseVersion --license $1 --os $Wrapper_OS --compiler $Wrapper_Compiler --architecture $Wrapper_Architecture --type $Wrapper_Type --target_directory $ProjectPath 
        if [ $? -ne 0 ];
        then
            printf "\nCannot run Melissa Updater. Please check your license string!\n"
            exit 1
        fi

        # Download the com zip
        ./MelissaUpdater/MelissaUpdater file --filename $Com_FileName --release_version $Com_ReleaseVersion --license $1 --os $Com_OS --compiler $Com_Compiler --architecture $Com_Architecture --type $Com_Type --target_directory $ProjectPath 
        if [ $? -ne 0 ];
        then
            printf "\nCannot run Melissa Updater. Please check your license string!\n"
            exit 1
        fi
    fi
    
    printf "Melissa Updater finished downloading $Wrapper_FileName!\n"

    printf "Melissa Updater finished downloading $Com_FileName!\n"

    # Check for the zip folder and extract from the zip folder if it was downloaded
    if [ ! -f "$ProjectPath/mdRightFielder_JavaCode.zip" ];
    then
        printf "mdRightFielder_JavaCode.zip not found.\n"
        printf "Aborting program, see above.\n"

        exit 1
    else
        if [ ! -d "$ProjectPath/com" ];
        then
            unzip "$ProjectPath/mdRightFielder_JavaCode.zip" -d "$ProjectPath"
        else
            rm -r "$ProjectPath/com"

            unzip "$ProjectPath/mdRightFielder_JavaCode.zip" -d "$ProjectPath"
        fi
    fi
}

CheckSOs() 
{
    if [ ! -f $BuildPath/$Config_FileName ];
    then
        echo "false"
    else
        echo "true"
    fi
}

########################## Main ############################
printf "\n======================== Melissa Right Fielder Object ========================\n                    [ Java | Linux | 64BIT ]\n"

# Get license (either from parameters or user input)
if [ -z "$license" ];
then
  printf "Please enter your license string: "
  read license
fi

# Check license from Environment Variables 
if [ -z "$license" ];
then
  license=`echo $MD_LICENSE` 
fi

if [ -z "$license" ];
then
  printf "\nLicense String is invalid!\n"
  exit 1
fi

# Use Melissa Updater to download data file(s) 
# Download data file(s) 
DownloadDataFiles $license      # comment out this line if using DQS Release

# Set data file(s) path
#DataPath=""      # uncomment this line and change to your DQS Release data file(s) directory 

#if [ ! -d $DataPath ]; # uncomment this section of code if you are using your own DQS Release data file(s) directory
#then
    #printf "\nData path is invalid!\n"
    #exit 1
#fi

# Download SO(s)
DownloadSO $license 

# Download wrapper and com folder
DownloadWrappers $license

# Check if all SO(s) have been downloaded. Exit script if missing
printf "\nDouble checking SO file(s) were downloaded...\n"

SOsAreDownloaded=$(CheckSOs)

if [ "$SOsAreDownloaded" == "false" ];
then
    printf "\n$Config_FileName not found"
    printf "\nMissing the above data file(s).  Please check that your license string and directory are correct.\n"

    printf "\nAborting program, see above.\n"
    exit 1
fi

printf "\nAll file(s) have been downloaded/updated!\n"

# Start
# Start
# Build project
cd $ProjectPath
printf "\n=============================== BUILD PROJECT ==============================\n"
javac -cp .:com/melissadata/*.java MelissaRightFielderObjectLinuxJava.java
export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:/.
jar cvfm MelissaRightFielderObjectLinuxJava.jar manifest.txt com/melissadata/*.class *.class *.so

# Run project
if [ -z "$rfinput" ];
then
    java -jar MelissaRightFielderObjectLinuxJava.jar --license $license --dataPath $DataPath
else
    java -jar MelissaRightFielderObjectLinuxJava.jar --license $license --dataPath $DataPath --rfinput $rfinput
fi
