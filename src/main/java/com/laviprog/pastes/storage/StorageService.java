package com.laviprog.pastes.storage;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Slf4j
@Service
public class StorageService implements Storage {
    private final AmazonS3 s3Client;
    private final String serviceEndpoint;

    public StorageService(
            @Value("${aws.service-endpoint}") String serviceEndpoint,
            @Value("${aws.access-key-id}") String accessKeyId,
            @Value("${aws.secret-access-key}") String secretAccessKey,
            @Value("${aws.region}") String region
    ) {
        this.serviceEndpoint = serviceEndpoint;
        s3Client = AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(
                        new EndpointConfiguration(
                                serviceEndpoint,
                                region
                        )
                )
                .withCredentials(
                        new AWSStaticCredentialsProvider(
                                new BasicAWSCredentials(
                                        accessKeyId, secretAccessKey
                                )
                        )
                )
                .build();
    }

    @Override
    public String getFileUrl(String bucket, String key) {
        return String.format("%s/%s/%s", serviceEndpoint, bucket, key);
    }

    @Override
    public void uploadFile(String bucket, String key, InputStream input, ObjectMetadata metadata) {
        log.debug("Uploading file {} to bucket {}", key, bucket);
        s3Client.putObject(new PutObjectRequest(bucket, key, input, metadata));
    }

    @Override
    public void deleteFile(String bucket, String key) {
        log.debug("Deleting file {} from bucket {}", key, bucket);
        s3Client.deleteObject(new DeleteObjectRequest(bucket, key));
    }

}
