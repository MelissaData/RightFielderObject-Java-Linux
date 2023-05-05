import com.melissadata.*;
import java.io.*;
import java.util.Arrays;
import java.util.List;

public class MelissaRightFielderObjectLinuxJava {

  public static void main(String args[]) throws IOException {
    // Variables
    String[] arguments = ParseArguments(args);
    String license = arguments[0];
    String testInput = arguments[1];
    String dataPath = arguments[2];

    RunAsConsole(license, testInput, dataPath);
  }

  public static String[] ParseArguments(String[] args) {
    String license = "", testInput = "", dataPath = "";
    List<String> argumentStrings = Arrays.asList("--license", "-l", "--rfinput", "-r", "--dataPath", "-d");
    for (int i = 0; i < args.length; i++) {
      if (args[i].equals("--license") || args[i].equals("-l")) {
        if (args[i + 1] != null) {
          license = args[i + 1];
        }
      }
      if (args[i].equals("--rfinput") || args[i].equals("-n")) {
        if (args[i + 1] != null) {
          testInput = args[i + 1];
          int wordCount = 2;
          while ((args.length - 1 >= i + wordCount) && (!argumentStrings.contains(args[i + wordCount]))) {
            testInput += " " + args[i + wordCount];
            wordCount += 1;
          }
        }
      }
      if (args[i].equals("--dataPath") || args[i].equals("-d")) {
        if (args[i + 1] != null) {
          dataPath = args[i + 1];
        }
      }
    }
    return new String[] { license, testInput, dataPath };

  }

  public static void RunAsConsole(String license, String testInput, String dataPath) throws IOException {
    System.out.println("\n\n============ WELCOME TO MELISSA RIGHT FIELDER OBJECT LINUX JAVA ============\n");
    RightFielderObject rightFielderObject = new RightFielderObject(license, dataPath);
    Boolean shouldContinueRunning = true;

    if (!rightFielderObject.mdRightFielderObj.GetInitializeErrorString().equals("No Error"))
      shouldContinueRunning = false;

    while (shouldContinueRunning) {
      DataContainer dataContainer = new DataContainer();
      BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

      if (testInput == null || testInput.trim().isEmpty()) {
        System.out.println("\nFill in each value to see the Right Fielder Object results");
        System.out.print("Right Fielder Input: ");

        dataContainer.Input = stdin.readLine();
      } else {
        dataContainer.Input = testInput;
      }

      // Print user input
      System.out.println("\n================================== INPUTS ==================================\n");
      System.out.println("\tRight Fielder Input: " + dataContainer.Input);

      // Execute Right Fielder Object
      rightFielderObject.ExecuteObjectAndResultCodes(dataContainer);

      // Print output
      System.out.println("\n================================== OUTPUT ==================================\n");
      System.out.println("\n\tRightFielder Object Information:");
      // System.out.println($"\t Right Fielder Input: {dataContainer.Input}");
      System.out.println("\t          AddressLine1: " + rightFielderObject.mdRightFielderObj.GetAddress());
      System.out.println("\t          AddressLine2: " + rightFielderObject.mdRightFielderObj.GetAddress2());
      System.out.println("\t          AddressLine3: " + rightFielderObject.mdRightFielderObj.GetAddress3());
      System.out.println("\t                  City: " + rightFielderObject.mdRightFielderObj.GetCity());
      System.out.println("\t                 State: " + rightFielderObject.mdRightFielderObj.GetState());
      System.out.println("\t                   Zip: " + rightFielderObject.mdRightFielderObj.GetPostalCode());
      // rightFielderObject.mdRightFielderObj.GetFullNameNext();
      // System.out.println("\t FullName: " +
      // rightFielderObject.mdRightFielderObj.GetFullName());
      // rightFielderObject.mdRightFielderObj.GetDepartmentNext();
      // System.out.println("\t Department: " +
      // rightFielderObject.mdRightFielderObj.GetDepartment());
      // rightFielderObject.mdRightFielderObj.GetCompanyNext();
      // System.out.println("\t Company: " +
      // rightFielderObject.mdRightFielderObj.GetCompany());
      // System.out.println("\t Country: " +
      // rightFielderObject.mdRightFielderObj.GetCountry());
      // System.out.println("\t LastLine: " +
      // rightFielderObject.mdRightFielderObj.GetLastLine());
      // rightFielderObject.mdRightFielderObj.GetPhoneNext();
      // System.out.println("\t Phone: " +
      // rightFielderObject.mdRightFielderObj.GetPhone());
      // rightFielderObject.mdRightFielderObj.GetPhoneTypeNext();
      // System.out.println("\t PhoneType: " +
      // rightFielderObject.mdRightFielderObj.GetPhoneType());
      // rightFielderObject.mdRightFielderObj.GetEmailNext();
      // System.out.println("\t Email: " +
      // rightFielderObject.mdRightFielderObj.GetEmail());
      // rightFielderObject.mdRightFielderObj.GetURLNext();
      // System.out.println("\t Url: " +
      // rightFielderObject.mdRightFielderObj.GetURL());
      // rightFielderObject.mdRightFielderObj.GetUserFieldNext("SSN");
      // System.out.println("\t UserField: " +
      // rightFielderObject.mdRightFielderObj.GetUserField("SSN"));
      // rightFielderObject.mdRightFielderObj.GetUnrecognizedNext();
      // System.out.println("\t Unrecognized: " +
      // rightFielderObject.mdRightFielderObj.GetUnrecognized());

      System.out.println("\t  Result Codes: " + dataContainer.ResultCodes);

      String[] rs = dataContainer.ResultCodes.split(",");
      for (String r : rs) {
        System.out.println("        " + r + ":"
            + rightFielderObject.mdRightFielderObj.GetResultCodeDescription(r,
                mdRightFielder.ResultCdDescOpt.ResultCodeDescriptionLong));
      }

      Boolean isValid = false;
      if (testInput != null && !testInput.trim().isEmpty()) {
        isValid = true;
        shouldContinueRunning = false;
      }

      while (!isValid) {
        System.out.println("\nTest Right Fielder Again? (Y/N)");
        String testAnotherResponse = stdin.readLine();

        if (testAnotherResponse != null && !testAnotherResponse.trim().isEmpty()) {
          testAnotherResponse = testAnotherResponse.toLowerCase();
          if (testAnotherResponse.equals("y")) {
            isValid = true;
          } else if (testAnotherResponse.equals("n")) {
            isValid = true;
            shouldContinueRunning = false;
          } else {
            System.out.println("Invalid Response, please respond 'Y' or 'N'");
          }
        }
      }
    }
    System.out.println("\n=================== THANK YOU FOR USING MELISSA JAVA OBJECT ================\n");

  }
}

class RightFielderObject {
  // Path to Right Fielder Object data files (.dat, etc)
  String dataFilePath;

  // Create instance of Melissa Right Fielder Object
  mdRightFielder mdRightFielderObj = new mdRightFielder();

  public RightFielderObject(String license, String dataPath) {
    // Set license string and set path to data files (.dat, etc)
    mdRightFielderObj.SetLicenseString(license);
    dataFilePath = dataPath;

    // If you see a different date than expected, check your license string and
    // either download the new data files or use the Melissa Updater program to
    // update your data files.
    mdRightFielderObj.SetPathToRightFielderFiles(dataFilePath);
    mdRightFielder.ProgramStatus pStatus = mdRightFielderObj.InitializeDataFiles();

    if (pStatus != mdRightFielder.ProgramStatus.NoError) {
      // Problem during initialization
      System.out.println("Failed to Initialize Object.");
      System.out.println(pStatus);
      return;
    }

    System.out.println("                DataBase Date: " + mdRightFielderObj.GetDatabaseDate());
    System.out.println("              Expiration Date: " + mdRightFielderObj.GetLicenseExpirationDate());

    /**
     * This number should match with the file properties of the Melissa Object
     * binary file.
     * If TEST appears with the build number, there may be a license key issue.
     */
    System.out.println("               Object Version: " + mdRightFielderObj.GetBuildNumber());
    System.out.println();

  }

  // This will call the lookup function to process the input as well as
  // generate the result codes
  public void ExecuteObjectAndResultCodes(DataContainer data) {

    // These are the configuarble pieces of the Right Fielder Object. We are setting
    // what kind of information we want to be looked up
    // SetUserPattern Method - Ex. Social Security Number

    // mdRightFielder.SetUserPattern("SSN", "[0-9]{3}-[0-9]{2}-[0-9]{4}");
    mdRightFielderObj.Parse(data.Input);
    data.ResultCodes = mdRightFielderObj.GetResults();

    // ResultsCodes explain any issues Right Fielder Object has with the object.
    // List of result codes for Right Fielder Object
    // https://wiki.melissadata.com/?title=Result_Code_Details#RightFielder_Object

  }
}

class DataContainer {
  public String Input;
  public String ResultCodes;
}
