package com.laviprog.pastes.utils;

import java.util.UUID;

public class FileUtils {

    public static String generateFilenameWithUUID(String filename) {

        String extension = "";
        int lastDotIndex = filename.lastIndexOf('.');

        if (lastDotIndex > 0 && lastDotIndex < filename.length() - 1) {
            extension = filename.substring(lastDotIndex);
        }

        return UUID.randomUUID() + extension;

    }
}
