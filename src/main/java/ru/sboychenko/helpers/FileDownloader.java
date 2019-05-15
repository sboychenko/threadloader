package ru.sboychenko.helpers;

import org.apache.log4j.Logger;
import ru.sboychenko.Md5Checker;
import ru.sboychenko.model.File;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Download file from url
 */
public class FileDownloader implements Downloader {
    private static Logger log = Logger.getLogger(FileDownloader.class.getName());

    public static AtomicBoolean status = new AtomicBoolean(true);

    @Override
    public List<File> download(InputStream is) {
        status.set(true);
        log.debug("start download >> ");

        List<File> list = new ArrayList<>();

        try {
            Scanner s = new Scanner(is);
            // read from your scanner

            while (s.hasNext()) {
                String line = s.nextLine();
                //log.debug("> " + line);
                String[] split = line.split(",");
                if (split.length == 2) {
                    list.add(new File(split[0], split[1]));
                }
            }
            log.debug("Download file with " + list.size() + " elements");

            return list;
        } catch (Exception e) {
            log.error("Error in download file " + e.getMessage(), e);
            return null;
        }
    }

    @Override
    public Result check(List<File> files) {
        try {
            log.debug("Start parallel download");
            ExecutorService executor = Executors.newFixedThreadPool(files.size());

            for (File file : files) {
                executor.execute(new Md5Checker(file));
            }

            executor.shutdown();
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);

            //long usedBytes = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
            //log.info("USED memory: " + (usedBytes / 1024 / 1024) + " mb");

            log.info("All done!");
            return status.get() ? Result.ok : Result.notall;
        } catch (InterruptedException e) {
            log.error("Error in download file " + e.getMessage(), e);
            return Result.error;
        }
    }
}
