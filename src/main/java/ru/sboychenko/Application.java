package ru.sboychenko;

import org.apache.log4j.Logger;
import ru.sboychenko.helpers.Downloader;
import ru.sboychenko.helpers.FileDownloader;
import ru.sboychenko.model.File;

import java.net.URL;
import java.util.List;

/**
 * Main app class
 */
public class Application {
    private static Logger log = Logger.getLogger(Application.class.getName());

    public static void main(String[] args) {

        if (args != null && args.length == 1 && args[0] != null) {

            log.info("start process >> ");

            try {
                Downloader downloader = new FileDownloader();

                URL url = new URL(args[0]);
                List<File> files = downloader.download(url.openStream());

                if (files == null) {
                    System.exit(1);
                }

                downloader.check(files);

            } catch (Exception e) {
                log.error("Error app " + e.getMessage(), e);
            }

            log.info(" << end process");

        } else {
            // do our job
            System.out.println("Start app with url param");
        }

    }
}
