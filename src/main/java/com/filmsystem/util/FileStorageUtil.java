package com.filmsystem.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Locale;
import java.util.UUID;

public final class FileStorageUtil {
    private FileStorageUtil() {
    }

    public static String save(File source, String originalName, String folderPath) throws IOException {
        if (source == null) {
            return null;
        }
        File folder = new File(folderPath);
        if (!folder.exists() && !folder.mkdirs()) {
            throw new IOException("无法创建上传目录: " + folderPath);
        }
        String extension = extensionOf(originalName);
        String savedName = UUID.randomUUID().toString().replace("-", "") + extension;
        File target = new File(folder, savedName);
        Files.copy(source.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING);
        return savedName;
    }

    private static String extensionOf(String name) {
        if (StringUtil.isBlank(name)) {
            return "";
        }
        String safeName = name.replace('\\', '/');
        int slash = safeName.lastIndexOf('/');
        if (slash >= 0) {
            safeName = safeName.substring(slash + 1);
        }
        int dot = safeName.lastIndexOf('.');
        if (dot < 0 || dot == safeName.length() - 1) {
            return "";
        }
        return safeName.substring(dot).toLowerCase(Locale.ENGLISH);
    }
}
