package org.utexas.ida.buckets;

import io.minio.MinioClient;
import io.minio.errors.MinioException;
import io.minio.messages.Bucket;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class ListBuckets {
    /** MinioClient.listBuckets() example. */
    public static void main(String[] args)
            throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        try {
            /* play.min.io for test and development. */
            MinioClient minioClient =
                    MinioClient.builder()
                            .endpoint("https://web.corral.tacc.utexas.edu:9004/")
                            .credentials("<InsertUserKey", "<InsertSecretKey>")
                            .build();

            /* Amazon S3: */
            // MinioClient minioClient =
            //     MinioClient.builder()
            //         .endpoint("https://s3.amazonaws.com")
            //         .credentials("YOUR-ACCESSKEY", "YOUR-SECRETACCESSKEY")
            //         .build();

            // List buckets we have atleast read access.
            List<Bucket> bucketList = minioClient.listBuckets();
            for (Bucket bucket : bucketList) {
                System.out.println(bucket.creationDate() + ", " + bucket.name());
            }
        } catch (MinioException e) {
            System.out.println("Error occurred: " + e);
        }
    }

    //TODO: Work on Array Return for another method here. Can I populate a set of json objects?
    public ArrayList<String> returnCollection() throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        ArrayList<String> aList;
        try {
            aList = new ArrayList<>();
            MinioClient minioClient =
                    MinioClient.builder()
                            .endpoint("https://web.corral.tacc.utexas.edu:9004/")
                            .credentials("<InsertUserKey", "<InsertSecretKey>")
                            .build();

            /* Amazon S3: */
            // MinioClient minioClient =
            //     MinioClient.builder()
            //         .endpoint("https://s3.amazonaws.com")
            //         .credentials("YOUR-ACCESSKEY", "YOUR-SECRETACCESSKEY")
            //         .build();


            // List buckets we have atleast read access.
            List<Bucket> bucketList = minioClient.listBuckets();
            for (Bucket bucket : bucketList) {
                aList.add(bucket.name());
            }
            return aList;
        } catch (MinioException e) {
            System.out.println("Error occurred: " + e);
            return null;
        }
    }
}
