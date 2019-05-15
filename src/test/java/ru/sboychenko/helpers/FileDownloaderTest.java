package ru.sboychenko.helpers;

import org.junit.Before;
import org.junit.Test;
import ru.sboychenko.model.File;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class FileDownloaderTest {

    FileDownloader fd;

    @Before
    public void prepare() {
        fd = new FileDownloader();
    }

    @Test
    public void download() {

        List<File> download = fd.download(getClass().getClassLoader().getResourceAsStream("file_list.txt"));
        assertEquals("Test count of file line", download.size(), 7);

        download = fd.download(getClass().getClassLoader().getResourceAsStream("file_list11.txt"));
        assertNull("When bad url", download);

        download = fd.download(getClass().getClassLoader().getResourceAsStream("file_list_bad.txt"));
        assertEquals("Test count of not full file", download.size(), 5);
    }

    @Test
    public void check() {
        List<File> list = new ArrayList();
        list.add(new File(Objects.requireNonNull(getClass().getClassLoader().getResource("1.txt")).toString(), "8d03919680d240dea1e81f5dd9829d67"));
        list.add(new File(Objects.requireNonNull(getClass().getClassLoader().getResource("2.txt")).toString(), "bcbc3e6d126f6886e16953e3204464df"));
        list.add(new File(Objects.requireNonNull(getClass().getClassLoader().getResource("3.txt")).toString(), "zzz"));

        Result check = fd.check(list);

        assertEquals("All okay", check, Result.ok);
    }
}