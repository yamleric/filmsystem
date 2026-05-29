package com.filmsystem.util;

import javax.servlet.ServletContext;
import java.io.File;

public final class UploadPathUtil {
    private static final String UPLOAD_DIR_PROPERTY = "filmsystem.upload.dir";
    private static final String UPLOAD_DIR_ENV = "FILMSYSTEM_UPLOAD_DIR";

    private UploadPathUtil() {
    }

    public static File resolveRoot(ServletContext context) {
        String configured = System.getProperty(UPLOAD_DIR_PROPERTY);
        if (StringUtil.isBlank(configured)) {
            configured = System.getenv(UPLOAD_DIR_ENV);
        }
        if (!StringUtil.isBlank(configured)) {
            return new File(configured.trim());
        }
        String realPath = context.getRealPath("/uploads");
        if (!StringUtil.isBlank(realPath)) {
            return new File(realPath);
        }
        return new File(System.getProperty("java.io.tmpdir"), "filmsystem-uploads");
    }
}
