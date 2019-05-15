package ru.sboychenko.helpers;

import ru.sboychenko.model.File;

import java.io.InputStream;
import java.util.List;

public interface Downloader {

    /**
     * Download file with list of links
     * @param url for download file
     * @return List of File, or null - if error
     */
    List<File> download(InputStream url);

    /**
     * Check md5 of files. Parallel download
     * @param files
     * @return result
     */
    Result check(List<File> files);
}
