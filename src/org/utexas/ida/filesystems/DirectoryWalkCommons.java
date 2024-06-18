package org.utexas.ida.filesystems;

import org.utexas.ida.filetransfer.TransferClient;
import org.apache.commons.io.FileUtils;
import org.utexas.ida.properties.ReadInProperties;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/***************
 * Good resource here for Apache Commons IO File ops -
 * <a href="https://javadevcentral.com/apache-commons-fileutils-directory-operations">...</a>
 * and <a href="https://stackoverflow.com/questions/2534632/list-all-files-from-a-directory-recursively-with-java">...</a>
 * Look at "Dan's Solution" Stack
 * For the  relativizing methodology I read about it in this write-up
 * <a href="https://www.geeksforgeeks.org/path-relativize-method-in-java-with-examples/">...</a>
 *
 *****************/

public class DirectoryWalkCommons {

    public static void main(String[] args) {
        if(args.length<1){
            System.out.println("Sorry you need to pass 1 parameter along with the call \r\n" +
                    "The Parameter is the name of the parent folder that contains the archive folder(s)" +
                    "The credentials and the name of the bucket you pass in via the configuration file \r\n");
        } else {
            String username;
            String password;
            String bucketIn;

            ReadInProperties rip = new ReadInProperties();
            String[] propAry = rip.getProperties();
            if(propAry.length == 3) {
                username = propAry[0];
                password = propAry[1];
                bucketIn = propAry[2];
                String srcDir;
                //Example directoryParameterEntry = /Users/porterba/Downloads/twdb-region-16/content/
                String directoryParameterEntry;
                //Example /Users/porterba/Downloads/twdb-region-01/ of /Users/porterba/Downloads/twdb-region-01/content
                //Check for Directory Parameter and assign boolean flag
                if (args[0].length() == args[0].lastIndexOf("/") + 1) {
                    System.out.println("Correctly Constructed Request for Directory Access");
                    directoryParameterEntry = args[0].substring(0, args[0].lastIndexOf("/"));
                } else {
                    directoryParameterEntry = args[0];
                }
                srcDir = directoryParameterEntry.substring(0, directoryParameterEntry.lastIndexOf("/"));

                System.out.println(directoryParameterEntry);
                System.out.println(srcDir);

                final Path rootDir = Paths.get(srcDir);
                final Path pathiness = Paths.get(directoryParameterEntry);

                ArrayList<String> listNames = new ArrayList<>();

                //Gather the local dir folder names and file names
                ArrayList<String> list = getCorrectFileNames(listNames, pathiness, rootDir);

                //Instantiate the MinIO Client
                TransferClient transferClient = new TransferClient();
                String tmpDirectory = "";
                String tmpName;
                transferClient.transferDirectoryData(bucketIn, directoryParameterEntry, username, password);
                String returnDirectory = "Something went wrong";
                for (String outage : list) {
                    if (outage.endsWith("EMPTY")) {
                        tmpDirectory = outage.substring(tmpDirectory.lastIndexOf(directoryParameterEntry + "/") + directoryParameterEntry.length(), outage.lastIndexOf("EMPTY"));
                        //Do empty directory add
                        //transferClient.transferDirectoryData(bucketNameage, tmpDirectory);
                    } else if (outage.startsWith("MS2DIR")) {
                        System.out.println(outage + " directory only!");
                        //tmpDirectory = outage.substring(6);
                    } else {
                        //Do directory & file add
                        //String aggregatePath = srcDir + tmpDirectory;
                        tmpName = outage.substring(outage.lastIndexOf("/") + 1);
                        //System.out.println(srcDir + "\r\n "  + outage + "\r\n " +tmpName);
                        returnDirectory = transferClient.transferData(bucketIn, srcDir, outage, tmpName, username, password);
                    }
                }
                System.out.println(returnDirectory + " is the unique folder id for your uploaded data");
            } else {
                System.out.println("You need to read how to set up the properties file correctly " +
                        "before running this tool. Please see the readme.md or contact bporter@csr.utexas.edu " +
                        "for support if problems persist.");
            }
        }
    }

    private static ArrayList<String> getCorrectFileNames(ArrayList<String> fileNames, Path dir, Path absolutePath) {
        try(DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path path : stream) {
                if(path.toFile().isDirectory()) {
                    if(!FileUtils.isEmptyDirectory(path.toFile())) {
                        //Unused lines below
                        /* Path relativeness = absolutePath.relativize(path);
                        String directoryFlag = "MS2DIR"+relativeness;
                        fileNames.add("MS2DIR"+relativeness);
                        */
                        getCorrectFileNames(fileNames, path, absolutePath);
                    } else {
                        fileNames.add(path.toAbsolutePath()+"EMPTY");
                    }
                } else {
                    Path relativeness = absolutePath.relativize(path);
                   fileNames.add(relativeness.getParent() + "/" + path.getFileName());
                }
            }
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
        return fileNames;
    }
}
