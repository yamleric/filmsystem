package com.filmsystem.servlet;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class CaptchaServlet extends HttpServlet {
    public static final String SESSION_KEY = "CAPTCHA_CODE";
    private static final String CODES = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";
    private final Random random = new Random();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = createCode();
        request.getSession().setAttribute(SESSION_KEY, code);

        int width = 116;
        int height = 40;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(new Color(250, 247, 239));
        g.fillRect(0, 0, width, height);
        g.setColor(new Color(34, 126, 102));
        g.setFont(new Font("SansSerif", Font.BOLD, 24));
        for (int i = 0; i < code.length(); i++) {
            g.drawString(String.valueOf(code.charAt(i)), 14 + i * 23, 28 + random.nextInt(4));
        }
        g.setColor(new Color(180, 77, 63, 120));
        for (int i = 0; i < 5; i++) {
            g.drawLine(random.nextInt(width), random.nextInt(height), random.nextInt(width), random.nextInt(height));
        }
        g.dispose();

        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/png");
        ImageIO.write(image, "png", response.getOutputStream());
    }

    private String createCode() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            builder.append(CODES.charAt(random.nextInt(CODES.length())));
        }
        return builder.toString();
    }
}
