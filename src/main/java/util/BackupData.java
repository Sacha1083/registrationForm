package util;

import io.github.cdimascio.dotenv.Dotenv;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * This class is responsible for backing up the data to the S3 bucket.
 * @see Dotenv - Loads the environment variables from the .env file
 * @see AwsBasicCredentials - Represents the basic AWS credentials
 * @see StaticCredentialsProvider - Provides the AWS credentials
 * @see Region - Represents an AWS region
 * @see S3Client - Client for accessing the Amazon S3 service
 * @see S3Configuration - Represents the configuration for the Amazon S3 client
 * @see PutObjectRequest - Represents the input for the putObject method
 * @see S3Exception - Represents an error response from an Amazon S3 service
 * @see Paths - Provides methods for converting path strings to Path objects
 * @see System#getProperty(String) - Gets the system property indicated by the specified key
 */
public class BackupData {
    private static final Dotenv dotenv = Dotenv.load();
    private static final String BUCKET_NAME = dotenv.get("BUCKET_NAME");
    private static final String KEY_NAME = dotenv.get("KEY_NAME");
    private static final String ENDPOINT = dotenv.get("ENDPOINT");
    private static final String ACCESS_KEY = dotenv.get("ACCESS_KEY");
    private static final String SECRET_KEY = dotenv.get("SECRET_KEY");
    private static final AwsBasicCredentials awsCreds = AwsBasicCredentials.create(ACCESS_KEY, SECRET_KEY);

    /**
     * Default constructor.
     */
    public BackupData() {}

    /**
     * Uploads the data to the S3 bucket.
     * @return true if the data was uploaded successfully, false otherwise
     */
    public static boolean updateData() {
        Path filePath = Paths.get(System.getProperty("user.dir"), "data", "userData.db");
        return uploadToS3(filePath);
    }

    /**
     * Downloads the data from the S3 bucket.
     * @return true if the data was downloaded successfully, false otherwise
     */
    public static boolean downloadData() {
        Path filePath = Paths.get(System.getProperty("user.dir"), "data", "userData.db");
        return downloadToS3(filePath);
    }

    /**
     * Checks if the S3 bucket exists and the user has access to it.
     * @return true if the bucket exists, false otherwise
     */
    public static boolean checkS3Bucket() {
        try (S3Client s3 = S3Client.builder()
                .region(Region.EU_SOUTH_1)
                .endpointOverride(URI.create(Objects.requireNonNull(ENDPOINT)))
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .serviceConfiguration(S3Configuration.builder().pathStyleAccessEnabled(true).build())
                .build()) {
            s3.headBucket(builder -> builder.bucket(BUCKET_NAME));
            Log.success("Bucket exists and you have access to it.");
            return true;
        } catch (S3Exception e) {
            Log.error(e.awsErrorDetails().errorMessage());
            return false;
        }
    }

    /**
     * Uploads the file to the S3 bucket.
     * @param filePath the path of the file to upload
     * @return true if the file was uploaded successfully, false otherwise
     */
    private static boolean uploadToS3(Path filePath) {
        try (S3Client s3 = S3Client.builder()
                .region(Region.EU_SOUTH_1)
                .endpointOverride(URI.create(Objects.requireNonNull(ENDPOINT)))
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .serviceConfiguration(S3Configuration.builder().pathStyleAccessEnabled(true).build())
                .build()) {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(BUCKET_NAME)
                    .key(KEY_NAME)
                    .build();

            s3.putObject(putObjectRequest, filePath);
            Log.success("File uploaded successfully to S3.");
            return true;
        } catch (S3Exception e) {
            Log.error(e.awsErrorDetails().errorMessage());
            return false;
        }
    }

    /**
     * Downloads the data from the S3 bucket.
     * @return true if the data was downloaded successfully, false otherwise
     */
    private static boolean downloadToS3(Path filePath) {
        try (S3Client s3 = S3Client.builder()
                .region(Region.EU_SOUTH_1)
                .endpointOverride(URI.create(Objects.requireNonNull(ENDPOINT)))
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .serviceConfiguration(S3Configuration.builder().pathStyleAccessEnabled(true).build())
                .build()) {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(BUCKET_NAME)
                    .key(KEY_NAME)
                    .build();

            s3.getObject(getObjectRequest, filePath);
            Log.success("File downloaded successfully from S3.");
            return true;
        } catch (S3Exception e) {
            Log.error(e.awsErrorDetails().errorMessage());
            return false;
        }
    }
}