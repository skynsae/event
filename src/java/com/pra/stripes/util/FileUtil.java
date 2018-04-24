/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pra.stripes.util;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import javax.imageio.ImageIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author fikri
 */
public class FileUtil {
    String uploadLocation;
    private static Logger LOG = LoggerFactory.getLogger(FileUtil.class);    

    public FileUtil() {
    }

    public FileUtil(String uploadLocation) {
        this.uploadLocation = uploadLocation;
    }

    public String saveFile(String fileName, InputStream inputStream, long fileSize) throws Exception {
//        String genFileName = System.nanoTime() + "_" + fileName;
        File dir = new File(uploadLocation);
        if (!dir.exists()) {
            dir.mkdirs();
            dir.setReadable(true, false);
            dir.setExecutable(true, false);
        }
        File file = new File(uploadLocation + (uploadLocation.endsWith(File.separator) ? "" : File.separator) + fileName);
        LOG.debug("<saveFile> filesize : "+fileSize);
        
        if ((fileName.toLowerCase().endsWith("jpg") || fileName.toLowerCase().endsWith("tiff") || fileName.toLowerCase().endsWith("gif")
                || fileName.toLowerCase().endsWith("png") || fileName.toLowerCase().endsWith("bmp") || fileName.toLowerCase().endsWith("jpeg")) && fileSize > 300000) { //300KB
            LOG.debug("convert");
            BufferedImage originalImage = ImageIO.read(inputStream);
            int type = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();            
            BufferedImage resizeImageJpg = resizeImageWithHint(originalImage, type);            
            String t = fileName.substring(fileName.indexOf(".")+1);        
            ImageIO.write(resizeImageJpg, t, file);            
        } else {    
            LOG.debug("direct");
            FileOutputStream out = new FileOutputStream(file);
            FileUtil.copyStream(inputStream, out);
            out.close();
            file.setReadable(true, false);
        }
        
        
        return file.getName();
    }
    
     private static BufferedImage resizeImageWithHint(BufferedImage originalImage, int type){
 
	BufferedImage resizedImage = new BufferedImage(originalImage.getWidth()/4, originalImage.getHeight()/4, type);  // resize image 1:4
	Graphics2D g = resizedImage.createGraphics();
	g.drawImage(originalImage, 0, 0, originalImage.getWidth()/4, originalImage.getHeight()/4, null);
	g.dispose();	
	g.setComposite(AlphaComposite.Src);
 
	g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
	RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	g.setRenderingHint(RenderingHints.KEY_RENDERING,
	RenderingHints.VALUE_RENDER_QUALITY);
	g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	RenderingHints.VALUE_ANTIALIAS_ON);
 
	return resizedImage;
    }	
    

    public File getFile(String fileName) {
        File file = new File(uploadLocation + (uploadLocation.endsWith(File.separator) ? "" : File.separator) + fileName);
        if (file.exists()) {
            return file;
        }
        return null;
    }

    public void deleteFile(String fileName) {
        File file = new File(uploadLocation + (uploadLocation.endsWith(File.separator) ? "" : File.separator) + fileName);
        if (file.exists()) {
            file.delete();
        }
    }

    public static String writeAsString(File file, String contents) {
        Writer out = null;
        try {
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists() && !parentDir.mkdirs()) {
                return "unable to make parent dir for " + file;
            }
            Reader in = new StringReader(contents);
            out = new FileWriter(file);
            FileUtil.copyStream(in, out);
            return null;
        } catch (IOException e) {
            return "[FileUtil] ERROR writing file : " + e.getMessage();
        } finally {
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                } // ignored
            }
        }
    }

    public static void copyStream(InputStream in, OutputStream out) throws IOException {
        final int MAX = 4096;
        byte[] buf = new byte[MAX];
        for (int bytesRead = in.read(buf, 0, MAX);
                bytesRead != -1;
                bytesRead = in.read(buf, 0, MAX)) {
            out.write(buf, 0, bytesRead);
        }
    }

    public static void copyStream(Reader in, Writer out) throws IOException {
        final int MAX = 4096;
        char[] buf = new char[MAX];
        for (int bytesRead = in.read(buf, 0, MAX);
                bytesRead != -1;
                bytesRead = in.read(buf, 0, MAX)) {
            out.write(buf, 0, bytesRead);
        }
    }
    
    public static String readAsString(InputStream is) {
        StringBuilder b = new StringBuilder();
        try {
            BufferedReader r = new BufferedReader(new InputStreamReader(is));
            while (true) {
                int ch;
                ch = r.read();
                if (ch == -1) {
                    break;
                }
                b.append((char) ch);
            }
            r.close();
        } catch (IOException ex) {
            LOG.error("error {}", ex.getCause().getMessage());
        }
        return b.toString();
    }
}

