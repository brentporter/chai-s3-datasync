package org.utexas.ida.buckets;

/*
 * MinIO Java SDK for Amazon S3 Compatible Cloud Storage
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * @Author Brent Porter brent@csr.utexas.edu
 */

import io.minio.ListObjectsArgs;
import io.minio.MinioClient;
import io.minio.Result;
import io.minio.errors.MinioException;
import io.minio.messages.Item;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class ListObjects {
    /** MinioClient.listObjects() example. */
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
            /*
            minioClient.downloadObject(
  DownloadObjectArgs.builder()
  .bucket("my-bucketname")
  .object("my-objectname")
  .filename("my-object-file")
  .build());
             */
            // MinioClient minioClient =
            //     MinioClient.builder()
            //         .endpoint("https://s3.amazonaws.com")
            //         .credentials("YOUR-ACCESSKEY", "YOUR-SECRETACCESSKEY")
            //         .build();

            {
                // Lists objects information.
                /*Iterable<Result<Item>> results =
                        minioClient.listObjects(ListObjectsArgs.builder().bucket("twdb-region-16").build());

                for (Result<Item> result : results) {
                    Item item = result.get();
                    System.out.println(item.lastModified() + "\t" + item.size() + "\t" + item.objectName());
                }*/
            }

            {
                // Lists objects information recursively.
                Iterable<Result<Item>> results =
                        minioClient.listObjects(
                                ListObjectsArgs.builder().bucket("region-ms2-01").recursive(true).build());
                                                        //lrgv-region-flood-studies
                                                        //twdb-region-04
                for (Result<Item> result : results) {
                    Item item = result.get();
                    System.out.println(item.lastModified() + "\t" + item.size() + "\t" + item.objectName());
                }
            }

          /*  {
                // Lists maximum 100 objects information those names starts with 'E' and after
                // 'ExampleGuide.pdf'.
                Iterable<Result<Item>> results =
                        minioClient.listObjects(
                                ListObjectsArgs.builder()
                                        .bucket("my-bucketname")
                                        .startAfter("ExampleGuide.pdf")
                                        .prefix("E")
                                        .maxKeys(100)
                                        .build());

                for (Result<Item> result : results) {
                    Item item = result.get();
                    System.out.println(item.lastModified() + "\t" + item.size() + "\t" + item.objectName());
                }
            }

            {
                // Lists maximum 100 objects information with version those names starts with 'E' and after
                // 'ExampleGuide.pdf'.
                Iterable<Result<Item>> results =
                        minioClient.listObjects(
                                ListObjectsArgs.builder()
                                        .bucket("my-bucketname")
                                        .startAfter("ExampleGuide.pdf")
                                        .prefix("E")
                                        .maxKeys(100)
                                        .includeVersions(true)
                                        .build());

                for (Result<Item> result : results) {
                    Item item = result.get();
                    System.out.println(
                            item.lastModified()
                                    + "\t"
                                    + item.size()
                                    + "\t"
                                    + item.objectName()
                                    + " ["
                                    + item.versionId()
                                    + "]");
                }
            }*/
        } catch (MinioException e) {
            System.out.println("Error occurred: " + e);
        } catch (NullPointerException npe) {
            System.out.println("No bucket with that name");
        }
    }
}
