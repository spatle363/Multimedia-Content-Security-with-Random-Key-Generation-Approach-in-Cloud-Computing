package cloudfile;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWebAuth;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.DbxPathV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.UploadErrorException;
import com.dropbox.core.v2.files.WriteMode;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Date;
import java.util.Locale;

/**
 * An example command-line application that runs through the web-based OAuth
 * flow (using {@link DbxWebAuth}).
 */
public class DriveUploader {
    // Adjust the chunk size based on your network speed and reliability. Larger chunk sizes will
    // result in fewer network requests, which will be faster. But if an error occurs, the entire
    // chunk will be lost and have to be re-uploaded. Use a multiple of 4MiB for your chunk size.
    private static final long CHUNKED_UPLOAD_CHUNK_SIZE = 8L << 20; // 8MiB
    private static final int CHUNKED_UPLOAD_MAX_ATTEMPTS = 5;
    private static final String ACCESS_TOKEN = "TyCz7-4-YhAAAAAAAAAAy-7D7WBT2BZXafzZ7BgQTcXE4eHJl4GpNK0veSIiK814";

    /**
     * Uploads a file in a single request. This approach is preferred for small files since it
     * eliminates unnecessary round-trips to the servers.
     *
     * @param dbxClient Dropbox user authenticated client
     * @param localFIle local file to upload
     * @param dropboxPath Where to upload the file to within Dropbox
     */
    private static void uploadFile(DbxClientV2 dbxClient, File localFile, String dropboxPath) {
        try  {
        	
        	InputStream in = new FileInputStream(localFile);
            FileMetadata metadata = dbxClient.files().uploadBuilder(dropboxPath)
                .withMode(WriteMode.ADD)
                .withClientModified(new Date(localFile.lastModified()))
                .uploadAndFinish(in);

            //System.out.println(metadata.toStringMultiline());
        } catch (UploadErrorException ex) {
            System.err.println("Error uploading to Dropbox: " + ex.getMessage());
            System.exit(1);
        } catch (DbxException ex) {
            System.err.println("Error uploading to Dropbox: " + ex.getMessage());
            System.exit(1);
        } catch (IOException ex) {
            System.err.println("Error reading from file \"" + localFile + "\": " + ex.getMessage());
            System.exit(1);
        }
    }

    private static void printProgress(long uploaded, long size) {
        System.out.printf("Uploaded %12d / %12d bytes (%5.2f%%)\n", uploaded, size, 100 * (uploaded / (double) size));
    }

    private static void sleepQuietly(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ex) {
            // just exit
            System.err.println("Error uploading to Dropbox: interrupted during backoff.");
            System.exit(1);
        }
    }

    public static void DriveUploder(String localPath, String desName) throws IOException, DbxException {
    	Logger.getLogger("").setLevel(Level.WARNING);

        //String localPath = "C:/Users/Sumit/Desktop/1.jpeg";
        String dropboxPath = "/"+desName;

        String pathError = DbxPathV2.findError(dropboxPath);
        if (pathError != null) {
            System.err.println("Invalid <dropbox-path>: " + pathError);
            System.exit(1);
            return;
        }

        File localFile = new File(localPath);
        if (!localFile.exists()) {
            System.err.println("Invalid <local-path>: file does not exist.");
            System.exit(1);
            return;
        }

        if (!localFile.isFile()) {
            System.err.println("Invalid <local-path>: not a file.");
            System.exit(1);
            return;
        }

        // Create a DbxClientV2, which is what you use to make API calls.
        DbxRequestConfig requestConfig = new DbxRequestConfig("examples-upload-file", Locale.getDefault().toString());
        DbxClientV2 dbxClient = new DbxClientV2(requestConfig, ACCESS_TOKEN);
        
        uploadFile(dbxClient, localFile, dropboxPath);

//        System.exit(0);
    }
    
    /*public static void main(String[] args) throws IOException {
        // Only display important log messages.
        Logger.getLogger("").setLevel(Level.WARNING);

        String localPath = "C:/Users/Sumit/Desktop/1.jpeg";
        String dropboxPath = "/1.jpeg";

        String pathError = DbxPathV2.findError(dropboxPath);
        if (pathError != null) {
            System.err.println("Invalid <dropbox-path>: " + pathError);
            System.exit(1);
            return;
        }

        File localFile = new File(localPath);
        if (!localFile.exists()) {
            System.err.println("Invalid <local-path>: file does not exist.");
            System.exit(1);
            return;
        }

        if (!localFile.isFile()) {
            System.err.println("Invalid <local-path>: not a file.");
            System.exit(1);
            return;
        }

        // Create a DbxClientV2, which is what you use to make API calls.
        DbxRequestConfig requestConfig = new DbxRequestConfig("examples-upload-file", Locale.getDefault().toString());
        DbxClientV2 dbxClient = new DbxClientV2(requestConfig, ACCESS_TOKEN);//authInfo.getAccessToken(), authInfo.getHost());

        // upload the file with simple upload API if it is small enough, otherwise use chunked
        // upload API for better performance. Arbitrarily chose 2 times our chunk size as the
        // deciding factor. This should really depend on your network.
        //if (localFile.length() <= (2 * CHUNKED_UPLOAD_CHUNK_SIZE)) {
            uploadFile(dbxClient, localFile, dropboxPath);
        //} else {
//            chunkedUploadFile(dbxClient, localFile, dropboxPath);
        //}

        System.exit(0);
    }*/
}