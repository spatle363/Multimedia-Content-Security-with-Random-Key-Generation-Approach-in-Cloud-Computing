package cloudfile;

import com.dropbox.core.DbxDownloader;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWebAuth;
import com.dropbox.core.v1.DbxEntry;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.DbxPathV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.UploadErrorException;
import com.dropbox.core.v2.files.WriteMode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Date;
import java.util.Locale;

import uploader.path;

/**
 * An example command-line application that runs through the web-based OAuth
 * flow (using {@link DbxWebAuth}).
 */
public class DriveDownloader {
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
    private static void downloadFile(DbxClientV2 dbxClient, String localFile, String user, String dropboxPath) {
        try {
        	FileOutputStream outputStream = null;
        	outputStream = new FileOutputStream(path.path+user+"/"+localFile);
        	DbxDownloader<FileMetadata> downloadedFile = dbxClient.files().download(dropboxPath);
        	downloadedFile.download(outputStream);

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

    public static void DriveDownloder(String localPath, String user) throws IOException, DbxException {
    	Logger.getLogger("").setLevel(Level.WARNING);

        String dropboxPath = "/"+localPath;

        String pathError = DbxPathV2.findError(dropboxPath);
        if (pathError != null) {
            System.err.println("Invalid <dropbox-path>: " + pathError);
            System.exit(1);
            return;
        }

        // Create a DbxClientV2, which is what you use to make API calls.
        DbxRequestConfig requestConfig = new DbxRequestConfig("examples-upload-file", Locale.getDefault().toString());
        DbxClientV2 dbxClient = new DbxClientV2(requestConfig, ACCESS_TOKEN);
        
        downloadFile(dbxClient, localPath, user, dropboxPath);
    }
    
}