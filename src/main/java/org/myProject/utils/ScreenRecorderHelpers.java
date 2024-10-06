package org.myProject.utils;

import org.monte.media.Format;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

import static org.monte.media.FormatKeys.*;
import static org.monte.media.VideoFormatKeys.*;

public class ScreenRecorderHelpers extends ScreenRecorder {
    private static ThreadLocal<ScreenRecorderHelpers> screenRecorderThreadLocal = new ThreadLocal<>();
    private String fileName;
    private File currentFile;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");

    public ScreenRecorderHelpers(GraphicsConfiguration cfg, Rectangle captureArea, Format fileFormat, Format screenFormat, Format mouseFormat, Format audioFormat, File movieFolder) throws IOException, AWTException {
        super(cfg, captureArea, fileFormat, screenFormat, mouseFormat, audioFormat, movieFolder);
    }

    public static ScreenRecorderHelpers getInstance() throws IOException, AWTException {
        if (screenRecorderThreadLocal.get() == null) {
            screenRecorderThreadLocal.set(new ScreenRecorderHelpers(
                    GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration(),
                    new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()),
                    new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI),
                    new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE, CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE, DepthKey, 24, FrameRateKey,
                            Rational.valueOf(15), QualityKey, 1.0f, KeyFrameIntervalKey, 15 * 60),
                    new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black", FrameRateKey, Rational.valueOf(30)),
                    null,
                    new File(System.getProperty("user.dir") + "/Reports/video/")));
        }
        return screenRecorderThreadLocal.get();
    }

//    @Override
//    protected File createMovieFile(Format fileFormat) throws IOException {
//        if (!movieFolder.exists()) {
//            movieFolder.mkdirs();
//        } else if (!movieFolder.isDirectory()) {
//            throw new IOException("\"" + movieFolder + "\" is not a directory.");
//        }
//
//        currentFile = getFileWithUniqueName(movieFolder.getAbsolutePath() + File.separator + fileName + "_" + dateFormat.format(new Date()) + "." + Registry.getInstance().getExtension(fileFormat));
//        return currentFile;
//    }

//    private File getFileWithUniqueName(String fileName) {
//        String extension = "";
//        String name = "";
//
//        int idxOfDot = fileName.lastIndexOf('.');
//        if (idxOfDot > 0) {
//            extension = fileName.substring(idxOfDot + 1);
//            name = fileName.substring(0, idxOfDot);
//        }
//
//        Path path = Paths.get(fileName);
//        int counter = 1;
//        while (Files.exists(path)) {
//            fileName = name + "-" + counter + "." + extension;
//            path = Paths.get(fileName);
//            counter++;
//        }
//        return new File(fileName);
//    }

    public void startRecording(String fileName) {
        this.fileName = fileName;
        try {
            start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void stopRecording(boolean keepFile) {
        try {
            stop();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (!keepFile) {
            deleteRecording();
        }
    }

    private void deleteRecording() {
        boolean deleted = false;
        try {
            if (currentFile != null && currentFile.exists()) {
                deleted = currentFile.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (deleted) {
            currentFile = null;
        } else {
            System.out.println("Could not delete the screen record!");
        }
    }
}