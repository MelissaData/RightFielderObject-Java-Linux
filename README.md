# Melissa Right Fielder Object Linux Java

## Purpose
This code showcases the Melissa Right Fielder Object using Java.

Please feel free to copy or embed this code to your own project. Happy coding!

For the latest Melissa Right Fielder Object release notes, please visit: https://releasenotes.melissa.com/on-premise-api/rightfielder-object/

The console will ask the user for:

- Right Fielder Input (rfinput)

And return 

- Address Line 1
- Address Line 2
- City
- State 
- Zip
- Result Codes

## Tested Environments
- Linux 64-bit Java 19.0.2, Ubuntu 20.04.05 LTS
- Melissa data files for 2023-08

## Required Files and Programs

#### libmdRightFielder.so

This is the c++ code of the Melissa Object. This file will be downloaded by Melissa Updater!

#### libmdRightFielderJavaWrapper.so

This file needs to be added as a Project Dependency.  This wrapper will need to be in the same directory as the program using it.

#### Data File(s)

- mdRightFielder.cfg
- mdRightFielder.dat

## Getting Started
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.
This project is compatible with Java 19

#### Install Java
Before starting, make sure that Java has been correctly installed on your machine and your environment paths are configured. 

You may find detailed instructions here:
https://javahelps.com/install-oracle-jdk-19-on-linux

You can download Java 19 here: 
https://www.oracle.com/java/technologies/downloads/#jdk19-linux

Or you may also download Java 19 onto your Linux-based computer with
`wget --no-check-certificate -c --header "Cookie: oraclelicense=accept-securebackup-cookie" https://download.oracle.com/java/19/latest/jdk-19_linux-x64_bin.tar.gz`

You may have to add "sudo" before the above command as well if you are using a virtual machine.

Next, navigate to where the JDK was downloaded and extract the contents. You may use the command:
`sudo tar -xvzf ~/Downloads/jdk-19_linux-x64_bin.tar.gz`

Next, set up your environment. Start by entering the command `sudo nano /etc/environment` to bring up your environment PATH and add `/usr/lib/jvm/jdk-19.0.1/bin`. PATH variable must be separated by a colon so be sure to add one to the beginning of the path if there are already variables present.

Note that the version of the Java 19 JDK you downloaded may be slightly different, in which case be sure to adjust your statements accordingly. For example you may need to add `/usr/lib/jvm/jdk-19.0.2/bin` to you PATH instead depending on if Java has updated their Java 19 JDK

Next add the line `JAVA_HOME="/usr/lib/jvm/jdk-19.0.1"` to the end of the file.
Your file should look something like this:
```
PATH="/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/games:/usr/local/games:/usr/lib/jvm/jdk-19.0.1/bin"
JAVA_HOME="/usr/lib/jvm/jdk-19.0.1"
```

Press ctrl + X exit and Y to save the file.

If you already have a version of JDK installed then you will want to add Java 19 JDK as an alternative shortcut. To do so enter:
`sudo update-alternatives --install "/usr/bin/java" "java" "/usr/lib/jvm/jdk-19.0.1/bin/java" 0` and
`sudo update-alternatives --install "/usr/bin/javac" "javac" "/usr/lib/jvm/jdk-19.0.1/bin/javac" 0`

Then to set these shortcuts enter:
`sudo update-alternatives --set java /usr/lib/jvm/jdk-19.0.1/bin/java` and
`sudo update-alternatives --set javac /usr/lib/jvm/jdk-19.0.1/bin/javac`

Again note that you may need to change the specific version of Java to the one you downloaded.

You can check that your environment is set up correctly by opening a command prompt window and typing the following:
`java --version`

![alt text](/screenshots/java_version.PNG)

If you see the version number then you have installed Java and set up your environment paths correctly!

----------------------------------------

#### Download this project
```
$ git clone https://github.com/MelissaData/RightFielderObject-Java-Linux
$ cd RightFielderObject-Java-Linux
```

#### Set up Melissa Updater 
Melissa Updater is a CLI application allowing the user to update their Melissa applications/data. 

- In the root directory of the project, create a folder called `MelissaUpdater` by using the command: 

  `mkdir MelissaUpdater`

- Enter the newly created folder using the command:

  `cd MelissaUpdater`

- Proceed to install the Melissa Updater using the curl command: 

  `curl -L -O https://releases.melissadata.net/Download/Library/LINUX/NET/ANY/latest/MelissaUpdater`

- After the Melissa Updater is installed, you will need to change the Melissa Updater to an executable using the command:

  `chmod +x MelissaUpdater`

- Now that the Melissa Updater is set up, you can now proceed to move back into the project folder by using the command:
  
   `cd ..`

----------------------------------------

#### Different ways to get data file(s)
1.  Using Melissa Updater
	- It will handle all of the data download/path and dll(s) for you. 
2.  If you already have the latest DQS Release (ZIP), you can find the data file(s) and dll(s) in there
	- Use the location of where you copied/installed the data and update the "$DataPath" variable in the powershell script.
	- Copy all the dll(s) mentioned above into the `MelissaRightFielderObjectWindowsJava` project folder.

#### Change Bash Script Permissions
To be able to run the bash script, you must first make it an executable using the command:

`chmod +x MelissaNameObjectLinuxJava.sh`

Then you need to add permissions to the build directory with the command:

`chmod +rwx MelissaNameObjectLinuxJava`

As an indicator, the filename will change colors once it becomes an executable.
	
## Run Bash Script
Parameters:
- -r or --rfinput: a test right fielder input to parse
 	
  This is convenient when you want to get results for a specific right fielder input in one run instead of testing multiple right fielder inputs in interactive mode.  

- -l or --license (optional): a license string to test the Right Fielder Object  
- -q or --quiet (optional): add to the command if you do not want to get any console output from the Melissa Updater

When you have modified the script to match your data location, let's run the script. There are two modes:
- Interactive 

	The script will prompt the user for the right fielder input, then use the provided right fielder input to test the Right Fielder Object. For example:
	```
	$ ./MelissaRightFielderObjectLinuxJava.sh
	```
    For quiet mode:
    ```
    $ ./MelissaRightFielderObjectLinuxJava.sh --quiet
    ```
- Command Line 

	You can pass a right fielder input in ```--rfinput``` parameter and a license string in ```--license``` parameter to test Right Fielder Object. For example:
	```
    $ ./MelissaRightFielderObjectLinuxJava.sh --rfinput "22382 Avenida Empresa, Rancho Santa Margarita, CA 92688"
	$ ./MelissaRightFielderObjectLinuxJava.sh --rfinput "22382 Avenida Empresa, Rancho Santa Margarita, CA 92688" --license "<your_license_string>" 
    ```
	For quiet mode:
    ```
    $ ./MelissaRightFielderObjectLinuxJava.sh --rfinput "22382 Avenida Empresa, Rancho Santa Margarita, CA 92688" --quiet
	$ ./MelissaRightFielderObjectLinuxJava.sh --rfinput "22382 Avenida Empresa, Rancho Santa Margarita, CA 92688" --license "<your_license_string>" --quiet
    ```
This is the expected output from a successful setup for interactive mode:

![alt text](/screenshots/output.png)

## Troubleshooting
Troubleshooting for errors found while running your program.

### Errors:
| Error      | Description |
| ----------- | ----------- |
| ErrorRequiredFileNotFound      | Program is missing a required file. Please check your Data folder and refer to the list of required files above. If you are unable to obtain all required files through the Melissa Updater, please contact technical support below. |
| ErrorDatabaseExpired   | .db file(s) are expired. Please make sure you are downloading and using the latest release version. (If using the Melissa Updater, check powershell script for '$RELEASE_VERSION = {version}'  and change the release version if you are using an out of date release).     |
| ErrorFoundOldFile   | File(s) are out of date. Please make sure you are downloading and using the latest release version. (If using the Melissa Updater, check powershell script for '$RELEASE_VERSION = {version}'  and change the release version if you are using an out of date release).    |
| ErrorLicenseExpired   | Expired license string. Please contact technical support below. |

## Contact Us
For free technical support, please call us at 800-MELISSA ext. 4
(800-635-4772 ext. 4) or email us at tech@Melissa.com.

To purchase this product, contact Melissa sales department at
800-MELISSA ext. 3 (800-635-4772 ext. 3).
