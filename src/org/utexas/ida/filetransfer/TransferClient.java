package org.utexas.ida.filetransfer;

import io.minio.BucketExistsArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.UploadObjectArgs;
import io.minio.errors.MinioException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class TransferClient {

    private static String createID()
    {
        return UUID.randomUUID().toString();
    }
    private final String uuidForUpload = createID();

    String getUuidForUpload(){
        return this.uuidForUpload;
    }

    public String transferData(String bucketName, String sourceDirectory, String directoryName, String fileName, String username, String secretKey){
        try {
            String aggregatePath = sourceDirectory + "/" + directoryName;
            String agg2Name = directoryName;

            MinioClient minioClient =
                    MinioClient.builder()
                            .endpoint("https://web.corral.tacc.utexas.edu:9004/")
                            .credentials(username, secretKey)
                            .build();
            String folderURoot = getUuidForUpload();
            //String folderU = folderURoot + "/";
            String folderU = getUuidForUpload() + "/";
            if(fileName.isEmpty()){
                minioClient.putObject(
                        PutObjectArgs.builder().bucket(bucketName).object(directoryName).stream(
                                        new ByteArrayInputStream(new byte[]{}), -1, 20971520)
                                //.contentType("application/zip")
                                .build());
                System.out.println("EmptyDir");
            } else {
                if(!fileName.equalsIgnoreCase(".DS_Store")) {
                    if (fileName.lastIndexOf(".") == -1) {
                        //TODO: Write Code for files without extensions to remedy File & Directory Parsing #2 github issue
                        // Do files without extensions need to be separate from those with extensions? Research 2023-12-04 BAP
                        minioClient.uploadObject(
                                UploadObjectArgs.builder()
                                        .bucket(bucketName)
                                        .object(folderU+"/"+agg2Name)
                                        .filename(aggregatePath)
                                        .build());
                    } else {
                        System.out.println("File Name is "+ fileName + " and "+ aggregatePath + " " +agg2Name);
                        minioClient.uploadObject(
                                UploadObjectArgs.builder()
                                        .bucket(bucketName)
                                        .object(folderU+"/"+agg2Name)
                                        .filename(aggregatePath)
                                        .build());
                    }
                }
            }
            return folderURoot;
        } catch (MinioException e) {

            System.out.println("HTTP trace: " + e.httpTrace());
            return "Error occurred: " + e.getMessage();

        } catch (IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            return "Error occurred: " + e.getMessage();
        }
    }

    public void transferDirectoryData(String bucketName, String directoryName, String username, String secretKey){
        try {
            MinioClient minioClient =
                    MinioClient.builder()
                            .endpoint("https://web.corral.tacc.utexas.edu:9004/")
                            .credentials(username, secretKey)
                            .build();
            String folderU = createID();
            folderU += "/";

            boolean found =
                    //minioClient.getObject("").object();
                    minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                minioClient.putObject(
                        PutObjectArgs.builder().bucket(bucketName).object(folderU+"/"+directoryName).stream(
                                        new ByteArrayInputStream(new byte[]{}), 0, -1)
                                .build());
                System.out.println("Bucket Doesn't Exist");
            }

        } catch (MinioException e) {
            System.out.println("Error occurred: " + e);
            System.out.println("HTTP trace: " + e.httpTrace());

        } catch (IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            System.out.println(e.getMessage());
        }
    }
}
