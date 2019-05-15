package ru.sboychenko;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import ru.sboychenko.helpers.FileDownloader;
import ru.sboychenko.model.File;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;

public class Md5Checker implements Runnable {
    private static Logger log = Logger.getLogger(Md5Checker.class.getName());

    private File file;

    public Md5Checker(File file) {
        this.file = file;
    }

    @Override
    public void run() {
        log.debug("start download file: " + file.getUrl());

        try {
            try (InputStream is = new URL(file.getUrl()).openStream()) {
                String md5 = DigestUtils.md5Hex(is);

                log.info("Downloaded file: " + file.getUrl() + ", expected md5: " + file.getMd5() + ", received md5: " + md5 + ", " + (file.getMd5().equals(md5) ? "ОК" : "NOT OK"));
            } catch (FileNotFoundException e) {
                log.warn("File not found: " + e.getMessage());
                FileDownloader.status.set(false);
            }
        } catch (Exception e) {
            log.error("Some error: " + e.getMessage());
            //TODO to stacktrace to log
            e.printStackTrace();
        }
    }
}
