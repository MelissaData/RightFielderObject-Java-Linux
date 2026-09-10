import com.melissadata.*;
import java.io.*;
import java.util.Arrays;
import java.util.List;

/**
 * Right Fielder Object will identify, parse and reorganize input data into usable data
 * types, assuring that even the most inconsistent data entry will be properly validated
 * and stored.
 *
 * <p>High-level flow of this sample:
 * <ol>
 *   <li>SETUP     - create an mdRightFielder instance, hand it the license string and the
 *                   path to the data files, then InitializeDataFiles() (one time).</li>
 *   <li>INPUT     - feed one free-form contact string in.</li>
 *   <li>PROCESS   - Parse() splits the string into typed fields.</li>
 *   <li>READ      - pull the sorted fields back out with the Get* getters
 *                   (GetAddress, GetCity, GetState, GetPostalCode, ...).</li>
 *   <li>INTERPRET - GetResults() returns comma-separated result codes describing
 *                   what the object did/found; each code has a human description.</li>
 * </ol>
 *
 * <p>The pieces in this file map onto that flow:
 * <ul>
 *   <li>main / RunAsConsole / ParseArguments : console harness (argument parsing + the interactive loop).</li>
 *   <li>RightFielderObject                   : thin wrapper around mdRightFielder that owns setup + the call sequence.</li>
 *   <li>DataContainer                        : plain holder for one record's input and output.</li>
 * </ul>
 *
 * <p>Where mdRightFielder comes from:
 * The mdRightFielder and mdRightFielderJNI classes in com/melissadata come from mdRightFielder_JavaCode.zip,
 * which the accompanying MelissaRightFielderObjectLinuxJava.sh script downloads and
 * expands into com/melissadata on every run. mdRightFielderJNI declares the native methods
 * and loads libmdRightFielderJavaWrapper.so, the JNI shim that calls into libmdRightFielder.so.
 *
 * <p>Reference:
 * <ul>
 *   <li>Quickstart    : https://docs.melissa.com/on-premise-api/rightfielder-object/rightfielder-object-quickstart.html</li>
 *   <li>Release notes : https://releasenotes.melissa.com/on-premise-api/rightfielder-object/</li>
 *   <li>Result codes  : https://docs.melissa.com/on-premise-api/rightfielder-object/result-codes.html</li>
 * </ul>
 */
public class MelissaRightFielderObjectLinuxJava {

  /**
   * Entry point. Reads the optional command-line arguments, then hands control to
   * RunAsConsole, which performs the actual Right Fielder Object setup and processing.
   *
   * @param args The raw command-line arguments
   * @throws IOException if reading from standard input fails
   */
  public static void main(String args[]) throws IOException {
    // Populated by ParseArguments below.
    String[] arguments = ParseArguments(args);
    String license = arguments[0];
    String testInput = arguments[1];
    String dataPath = arguments[2];

    RunAsConsole(license, testInput, dataPath);
  }

  /**
   * Reads the supported command-line options and returns them.
   *
   * <p>Recognized flags (each followed by its value, e.g. "--rfinput \"John Smith 22382 Avenida Empresa\""):
   * <ul>
   *   <li>--license / -l   : the Melissa license string</li>
   *   <li>--rfinput / -n   : a free-form contact string to test in one-shot mode</li>
   *   <li>--dataPath / -d  : path to the Right Fielder Object data files</li>
   * </ul>
   *
   * @param args The raw command-line arguments to parse.
   * @return A String array of { license, testInput, dataPath }.
   */
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

  /**
   * Sets up the Right Fielder Object once, then drives the input -> process -> output cycle.
   *
   * <p>In interactive mode (no --rfinput) it loops, asking for a new string each pass until
   * the user answers "N". In one-shot mode (--rfinput supplied) it runs a single pass
   * and exits.
   *
   * @param license   The Melissa license string used to initialize the object.
   * @param testInput A free-form contact string to process in one-shot mode; if empty, the program prompts interactively.
   * @param dataPath  Path to the Right Fielder Object data files.
   * @throws IOException if reading from standard input fails
   */
  public static void RunAsConsole(String license, String testInput, String dataPath) throws IOException {
    System.out.println("\n\n============ WELCOME TO MELISSA RIGHT FIELDER OBJECT LINUX JAVA ============\n");

    // Construct the wrapper. This is where the object is licensed, pointed at the
    // data files, and initialized (see the RightFielderObject constructor below).
    RightFielderObject rightFielderObject = new RightFielderObject(license, dataPath);
    Boolean shouldContinueRunning = true;

    // Gate the program on a successful initialization. If the data files could not
    // be loaded (bad/expired license, missing or wrong-path data files, ...),
    // GetInitializeErrorString() returns the reason instead of "No Error" and we
    // skip the processing loop entirely.
    if (!rightFielderObject.mdRightFielderObj.GetInitializeErrorString().equals("No Error"))
      shouldContinueRunning = false;

    while (shouldContinueRunning) {
      // Holder for this pass's input and result codes.
      DataContainer dataContainer = new DataContainer();
      BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

      if (testInput == null || testInput.trim().isEmpty()) {
        // Interactive mode: prompt the user for a free-form contact string.
        System.out.println("\nFill in each value to see the Right Fielder Object results");
        System.out.print("Right Fielder Input: ");

        dataContainer.Input = stdin.readLine();
      } else {
        // One-shot mode: use the string passed on the command line.
        dataContainer.Input = testInput;
      }

      // Print user input
      System.out.println("\n================================== INPUTS ==================================\n");
      System.out.println("\tRight Fielder Input: " + dataContainer.Input);

      // Execute Right Fielder Object
      // Runs the Parse and stores the result codes on dataContainer.
      rightFielderObject.ExecuteObjectAndResultCodes(dataContainer);

      // Print output
      // Each Get* getter below returns one field the object pulled out of the input
      // string. These read directly from the mdRightFielder instance, which still holds
      // the results from the Execute call above. (Many more getters are available - see
      // the commented lines for the full set.)
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

      // Result codes come back as a single comma-separated string. Split it and ask the
      // object for a readable description of each code. ResultCodeDescriptionLong requests
      // the long-form text; a short form is also available via ResultCodeDescriptionShort
      String[] rs = dataContainer.ResultCodes.split(",");
      for (String r : rs) {
        System.out.println("        " + r + ":"
            + rightFielderObject.mdRightFielderObj.GetResultCodeDescription(r,
                mdRightFielder.ResultCdDescOpt.ResultCodeDescriptionLong));
      }

      Boolean isValid = false;

      // In one-shot mode there is nothing more to do after a single pass: mark the
      // input handled and stop the outer loop.
      if (testInput != null && !testInput.trim().isEmpty()) {
        isValid = true;
        shouldContinueRunning = false;
      }

      // Interactive mode: ask whether to process another string. Keep prompting until
      // we get a valid Y/N. "N" ends the program; "Y" falls through to another pass.
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

/**
 * Wrapper that owns a single Melissa Right Fielder Object instance and encapsulates the
 * two things every Melissa object needs: one-time setup (license + data files) and the
 * per-record processing sequence. Reuse one instance across many inputs; do NOT
 * re-initialize per input.
 */
class RightFielderObject {
  // Path to the Right Fielder Object data files.
  String dataFilePath;

  // The underlying Melissa Right Fielder Object instance.
  mdRightFielder mdRightFielderObj = new mdRightFielder();

  /**
   * Performs the mandatory one-time setup, in this required order:
   * <ol>
   *   <li>SetLicenseString            - authorize the object.</li>
   *   <li>SetPathToRightFielderFiles  - tell it where the data files live.</li>
   *   <li>InitializeDataFiles         - load the data into memory.</li>
   * </ol>
   *
   * @param license  The Melissa license string used to authorize the object.
   * @param dataPath Path to the folder containing the Right Fielder Object data files.
   */
  public RightFielderObject(String license, String dataPath) {
    // Set license string and set path to data files
    mdRightFielderObj.SetLicenseString(license);
    dataFilePath = dataPath;

    // Point the object at the Right Fielder Object data files.
    mdRightFielderObj.SetPathToRightFielderFiles(dataFilePath);

    // Load the data files. The returned ProgramStatus reports whether initialization succeeded.
    // If you see a different date than expected, check your license string and either download the new data files
    // or use the Melissa Updater program to update your data files.
    mdRightFielder.ProgramStatus pStatus = mdRightFielderObj.InitializeDataFiles();

    // If an issue occurred, please investigate the common causes.
    // Common causes: an invalid/expired license, or missing/wrong-path data files.
    if (pStatus != mdRightFielder.ProgramStatus.NoError) {
      System.out.println("Failed to Initialize Object.");
      System.out.println(pStatus);
      return;
    }

    // Diagnostic information, handy for confirming the object loaded the data you expect:

    // Build date of the data files
    System.out.println("                DataBase Date: " + mdRightFielderObj.GetDatabaseDate());

    // When the license stops working
    System.out.println("              Expiration Date: " + mdRightFielderObj.GetLicenseExpirationDate());

    // This number should match with the file properties of the Melissa Object binary file.
    // If TEST appears with the build number, there may be a license key issue.
    System.out.println("               Object Version: " + mdRightFielderObj.GetBuildNumber());
    System.out.println();

  }

  /**
   * Runs the full Right Fielder Object processing sequence for one input string and
   * captures its result codes. This is the canonical per-record call pattern to copy
   * into your own application:
   *   Parse -> GetResults
   *
   * @param data The record to process. Its Input is read as input, and ResultCodes is
   *             populated with this run's result codes.
   */
  public void ExecuteObjectAndResultCodes(DataContainer data) {

    // Optional: register a custom field pattern (e.g. a Social Security Number) so the
    // object recognizes and extracts it. Uncomment the SetUserPattern call to use one.
    // mdRightFielderObj.SetUserPattern("SSN", "[0-9]{3}-[0-9]{2}-[0-9]{4}");

    // Split the free-form input into typed fields
    mdRightFielderObj.Parse(data.Input);

    // Collect the result codes for this run
    // ResultsCodes explain any issues Right Fielder Object has with the object.
    // List of result codes for Right Fielder Object
    // https://docs.melissa.com/on-premise-api/rightfielder-object/result-codes.html
    data.ResultCodes = mdRightFielderObj.GetResults();
  }
}

/**
 * Data holder for a single record: carries the input string in and the result codes out.
 */
class DataContainer {
  // Input: the free-form contact string to parse.
  public String Input;

  // Output: comma-separated result codes from GetResults().
  public String ResultCodes;
}
