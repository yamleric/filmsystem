package com.filmsystem.servlet;

import com.filmsystem.util.UploadPathUtil;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class UploadFileServlet extends HttpServlet {
    private static final int BUFFER_SIZE = 8192;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        File file = resolveRequestedFile(request);
        if (file == null || !file.isFile()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        long length = file.length();
        String contentType = getServletContext().getMimeType(file.getName());
        response.setContentType(contentType == null ? "application/octet-stream" : contentType);
        response.setHeader("Accept-Ranges", "bytes");
        response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");

        Range range = parseRange(request.getHeader("Range"), length);
        if (range == null) {
            response.setHeader("Content-Length", String.valueOf(length));
            copyRange(file, response.getOutputStream(), 0, length);
            return;
        }
        if (!range.valid) {
            response.setHeader("Content-Range", "bytes */" + length);
            response.sendError(HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE);
            return;
        }
        long partLength = range.end - range.start + 1;
        response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
        response.setHeader("Content-Range", "bytes " + range.start + "-" + range.end + "/" + length);
        response.setHeader("Content-Length", String.valueOf(partLength));
        copyRange(file, response.getOutputStream(), range.start, partLength);
    }

    @Override
    protected void doHead(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        File file = resolveRequestedFile(request);
        if (file == null || !file.isFile()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        String contentType = getServletContext().getMimeType(file.getName());
        response.setContentType(contentType == null ? "application/octet-stream" : contentType);
        response.setHeader("Accept-Ranges", "bytes");
        response.setHeader("Content-Length", String.valueOf(file.length()));
    }

    private File resolveRequestedFile(HttpServletRequest request) throws IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || "/".equals(pathInfo)) {
            return null;
        }
        File root = UploadPathUtil.resolveRoot(getServletContext()).getCanonicalFile();
        File file = new File(root, pathInfo).getCanonicalFile();
        String rootPath = root.getPath();
        String filePath = file.getPath();
        if (!filePath.equals(rootPath) && !filePath.startsWith(rootPath + File.separator)) {
            return null;
        }
        return file;
    }

    private Range parseRange(String header, long length) {
        if (header == null || !header.startsWith("bytes=") || header.indexOf(',') >= 0) {
            return null;
        }
        String spec = header.substring("bytes=".length()).trim();
        int dash = spec.indexOf('-');
        if (dash < 0) {
            return Range.invalid();
        }
        try {
            long start;
            long end;
            if (dash == 0) {
                long suffixLength = Long.parseLong(spec.substring(1));
                if (suffixLength <= 0) {
                    return Range.invalid();
                }
                start = Math.max(0, length - suffixLength);
                end = length - 1;
            } else {
                start = Long.parseLong(spec.substring(0, dash));
                end = dash == spec.length() - 1 ? length - 1 : Long.parseLong(spec.substring(dash + 1));
            }
            if (start < 0 || end < start || start >= length) {
                return Range.invalid();
            }
            return Range.valid(start, Math.min(end, length - 1));
        } catch (NumberFormatException e) {
            return Range.invalid();
        }
    }

    private void copyRange(File file, ServletOutputStream output, long start, long length) throws IOException {
        InputStream input = new FileInputStream(file);
        try {
            long skipped = 0;
            while (skipped < start) {
                long step = input.skip(start - skipped);
                if (step <= 0) {
                    if (input.read() == -1) {
                        return;
                    }
                    step = 1;
                }
                skipped += step;
            }

            byte[] buffer = new byte[BUFFER_SIZE];
            long remaining = length;
            while (remaining > 0) {
                int read = input.read(buffer, 0, (int) Math.min(buffer.length, remaining));
                if (read == -1) {
                    break;
                }
                output.write(buffer, 0, read);
                remaining -= read;
            }
        } finally {
            input.close();
        }
    }

    private static final class Range {
        private final boolean valid;
        private final long start;
        private final long end;

        private Range(boolean valid, long start, long end) {
            this.valid = valid;
            this.start = start;
            this.end = end;
        }

        private static Range valid(long start, long end) {
            return new Range(true, start, end);
        }

        private static Range invalid() {
            return new Range(false, 0, 0);
        }
    }
}
