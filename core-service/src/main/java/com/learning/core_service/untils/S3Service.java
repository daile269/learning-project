package com.learning.core_service.untils;

import java.io.IOException;

public interface S3Service {
    String uploadFile(byte[] bytes, String originalFilename,String contentType) throws IOException;
}
