package util;

import io.github.cdimascio.dotenv.Dotenv;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BackupData {
    private static final Dotenv dotenv = Dotenv.load();
    private static final String BUCKET_NAME = dotenv.get("BUCKET_NAME");
    private static final String KEY_NAME = dotenv.get("KEY_NAME");
    private static final String ENDPOINT = dotenv.get("ENDPOINT");
    private static final String ACCESS_KEY = dotenv.get("ACCESS_KEY");
    private static final String SECRET_KEY = dotenv.get("SECRET_KEY");

    public static boolean updateData() {
        Path filePath = Paths.get(System.getProperty("user.dir"), "data", "userData.db");
        return uploadToS3(filePath);
    }

    private static boolean uploadToS3(Path filePath) {
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(ACCESS_KEY, SECRET_KEY);

        try (S3Client s3 = S3Client.builder()
                .region(Region.EU_SOUTH_1)
                .endpointOverride(URI.create(ENDPOINT))
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .serviceConfiguration(S3Configuration.builder().pathStyleAccessEnabled(true).build())
                .build()) {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(BUCKET_NAME)
                    .key(KEY_NAME)
                    .build();

            s3.putObject(putObjectRequest, filePath);
            System.out.println("File uploaded successfully to S3.");
            return true;
        } catch (S3Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            return false;
        }
    }
}