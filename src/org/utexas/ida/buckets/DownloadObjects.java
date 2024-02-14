package org.utexas.ida.buckets;

import io.minio.DownloadObjectArgs;
import io.minio.MinioClient;
import io.minio.errors.*;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class DownloadObjects {

    public static void main(String[] args) {
        try {

            MinioClient minioClient =
                    MinioClient.builder()
                            .endpoint("https://web.corral.tacc.utexas.edu:9004/")
                            .credentials("<Insert User>", "<Insert Key>")
                            .build();

            minioClient.downloadObject(
                    DownloadObjectArgs.builder()
                            .bucket("lrgv-region-flood-studies")
                            .object("0714d2ae-4414-4bf8-a714-de4ccb8bbe6b/LMI_WFL1.xml")
                            //.object("lower_brazos/AustinOyster/AustinOyster/LandCover/Landcover.tif")
                            .filename("LMI_WFL.xml")
                            .build());

        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException e) {
            System.out.println("Error occurred: " + e);
        } catch (NullPointerException npe) {
            System.out.println("No bucket with that name");
        }
    }
}
