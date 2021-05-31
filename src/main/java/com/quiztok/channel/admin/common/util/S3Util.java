package com.quiztok.channel.admin.common.util;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Component
public class S3Util {

    private AmazonS3 amazonS3;

    private String bucket;

    public S3Util(){}

    private String accessKey;
    private String secretKey;
    private String region;

    public S3Util(String accessKey, String secretKey, String region, String bucket) {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        ClientConfiguration config = new ClientConfiguration();
        config.setRequestTimeout(10*60*1000);
        config.setMaxConnections(100);
        amazonS3 = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.fromName(region))
                .withClientConfiguration(config)
                .build();
        this.bucket = bucket;
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.region = region;
    }

    public void reset() {

        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        amazonS3.shutdown();
        ClientConfiguration config = new ClientConfiguration();
        config.setRequestTimeout(10*60*1000);
        config.setMaxConnections(100);
        amazonS3 = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.fromName(region))
                .withClientConfiguration(config)
                .build();
    }

    /**
     * <pre>
     * 파일 업로드
     * </pre>
     * @param path
     * @param file
     * @return
     * @throws FileNotFoundException
     */
    public PutObjectResult upload(String path, File file) throws FileNotFoundException{
        AccessControlList acl = new AccessControlList();
        acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
        acl.grantPermission(GroupGrantee.AuthenticatedUsers, Permission.FullControl);
        return upload(new PutObjectRequest(bucket, path, file).withAccessControlList(acl));
    }

    /**
     * <pre>
     * 파일 업로드
     * </pre>
     * @param path
     * @param file
     * @return
     * @throws FileNotFoundException
     */
    public PutObjectResult upload(String path) throws FileNotFoundException{
        File file = new File(path);

        AccessControlList acl = new AccessControlList();
        acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
        acl.grantPermission(GroupGrantee.AuthenticatedUsers, Permission.FullControl);
        PutObjectResult result= upload(new PutObjectRequest(bucket, path, file).withAccessControlList(acl));
        file.delete();
        return result;
    }

    /**
     * <pre>
     * 파일 업로드
     * </pre>
     * @param path
     * @param inputStream
     * @return
     */
    public PutObjectResult upload(String path, InputStream inputStream) {
        if(path.indexOf('/') == 0) {
            path = path.substring(1);
        }
        AccessControlList acl = new AccessControlList();
        acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
        acl.grantPermission(GroupGrantee.AuthenticatedUsers, Permission.FullControl);
        PutObjectResult result =  upload(new PutObjectRequest(bucket, path, inputStream, new ObjectMetadata())
                .withAccessControlList(acl)
        );
        IOUtils.closeQuietly(inputStream);
        return result;
    }

    public PutObjectResult upload(String path, InputStream inputStream, String bucket) {
        if(path.indexOf('/') == 0) {
            path = path.substring(1);
        }

        AccessControlList acl = new AccessControlList();
        acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
        acl.grantPermission(GroupGrantee.AuthenticatedUsers, Permission.FullControl);

        PutObjectResult result =  upload(new PutObjectRequest(bucket, path, inputStream, new ObjectMetadata())
                .withAccessControlList(acl)
        );
        IOUtils.closeQuietly(inputStream);
        return result;
    }

    /**
     * <pre>
     * 파일 업로드
     * </pre>
     * @param path
     * @param url
     * @return
     */
    public PutObjectResult upload(String path, String url) {
        AccessControlList acl = new AccessControlList();
        acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
        acl.grantPermission(GroupGrantee.AuthenticatedUsers, Permission.FullControl);
        return upload(new PutObjectRequest(bucket, path, url).withAccessControlList(acl));
    }

    /**
     * <pre>
     * 파일 업로드
     * </pre>
     * @param path
     * @param file
     * @return
     * @throws FileNotFoundException
     */
    public PutObjectResult uploadWithType(String path, File file, String type) throws FileNotFoundException{
        AccessControlList acl = new AccessControlList();
        acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
        acl.grantPermission(GroupGrantee.AuthenticatedUsers, Permission.FullControl);
        return upload(new PutObjectRequest(bucket, path, file).withAccessControlList(acl), type);
    }

    /**
     * <pre>
     * 파일 업로드
     * </pre>
     * @param path
     * @param file
     * @return
     * @throws FileNotFoundException
     */
    public PutObjectResult uploadWithType(String path, String type) throws FileNotFoundException{
        File file = new File(path);

        AccessControlList acl = new AccessControlList();
        acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
        acl.grantPermission(GroupGrantee.AuthenticatedUsers, Permission.FullControl);
        PutObjectResult result= upload(new PutObjectRequest(bucket, path, file).withAccessControlList(acl), type);
        file.delete();
        return result;
    }

    /**
     * <pre>
     * 파일 업로드
     * </pre>
     * @param path
     * @param inputStream
     * @return
     */
    public PutObjectResult uploadWithType(String path, InputStream inputStream, String type) {
        if(path.indexOf('/') == 0) {
            path = path.substring(1);
        }
        AccessControlList acl = new AccessControlList();
        acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
        acl.grantPermission(GroupGrantee.AuthenticatedUsers, Permission.FullControl);

        PutObjectResult result =  upload(new PutObjectRequest(bucket, path, inputStream, new ObjectMetadata())
                        .withAccessControlList(acl)
                , type
        );
        IOUtils.closeQuietly(inputStream);
        return result;
    }

    public PutObjectResult uploadWithType(String path, InputStream inputStream, String bucket, String type) {
        if(path.indexOf('/') == 0) {
            path = path.substring(1);
        }

        AccessControlList acl = new AccessControlList();
        acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
        acl.grantPermission(GroupGrantee.AuthenticatedUsers, Permission.FullControl);

        PutObjectResult result =  upload(new PutObjectRequest(bucket, path, inputStream, new ObjectMetadata())
                        .withAccessControlList(acl)
                , type
        );
        IOUtils.closeQuietly(inputStream);
        return result;
    }

    /**
     * <pre>
     * 파일 업로드
     * </pre>
     * @param path
     * @param url
     * @return
     */
    public PutObjectResult uploadWithType(String path, String url, String type) {
        AccessControlList acl = new AccessControlList();
        acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
        acl.grantPermission(GroupGrantee.AuthenticatedUsers, Permission.FullControl);
        return upload(new PutObjectRequest(bucket, path, url).withAccessControlList(acl), type);
    }


    /**
     * <pre>
     * 파일 업로드
     * </pre>
     * @param request
     * @return
     */
    public PutObjectResult upload(PutObjectRequest request) {
        if(request.getKey().endsWith("pdf")) {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("application/pdf");
            request.setMetadata(metadata);
        } else if(request.getKey().endsWith("mp4")) {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("video/mp4");
            request.setMetadata(metadata);
        }

        PutObjectResult result =  amazonS3.putObject(request);
//		AccessControlList acl = new AccessControlList();
//		acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
//		request.setAccessControlList(acl);
        return result;
    }

    public PutObjectResult upload(PutObjectRequest request, String type) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(type);
        request.setMetadata(metadata);

        PutObjectResult result =  amazonS3.putObject(request);
//		AccessControlList acl = new AccessControlList();
//		acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
//		request.setAccessControlList(acl);
        return result;
    }

    /**
     * <pre>
     * 파일 다운로드
     * </pre>
     * @param key
     * @return
     * @throws IOException
     */
    public S3Object download(String key) throws IOException {
        if(key.indexOf('/') == 0) {
            key = key.substring(1);
        }

        GetObjectRequest getObjectRequest = new GetObjectRequest(bucket, key);
        S3Object s3Object = amazonS3.getObject(getObjectRequest);
        return s3Object;
    }

    public S3Object download(String bucket, String key) throws IOException {
        if(key.indexOf('/') == 0) {
            key = key.substring(1);
        }
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucket, key);
        S3Object s3Object = amazonS3.getObject(getObjectRequest);
        return s3Object;
    }

    /**
     * 삭제
     * @param bucketName
     * @param key
     */
    public void remove(String bucketName, String key) {
        key = key.startsWith("/") ? key.substring(1) : key;
        amazonS3.deleteObject(bucketName, key);
    }

    /**
     * 삭제
     * @param key
     */
    public void remove(String key) {
        key = key.startsWith("/") ? key.substring(1) : key;
        amazonS3.deleteObject(bucket, key);
    }

    /**
     * 버켓간 복제
     * @param sourceBucketName
     * @param sourceKey
     * @param destinationBucketName
     * @param destinationKey
     */
    public void copy(String sourceKey, String destinationBucketName, String destinationKey) {
        if(sourceKey.contains(bucket)){
            sourceKey = sourceKey.substring(sourceKey.indexOf(bucket) + bucket.length());
        }
        CopyObjectRequest request = new CopyObjectRequest(bucket, sourceKey, destinationBucketName, destinationKey);
        AccessControlList acl = new AccessControlList();
        acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
        acl.grantPermission(GroupGrantee.AuthenticatedUsers, Permission.FullControl);
        request.setAccessControlList(acl);
        amazonS3.copyObject(request);
    }

    public String makeRealName(String name) {
        return UUID.randomUUID().toString().replaceAll("-", "") + name.substring(name.lastIndexOf('.'));
    }
}
