package org.woehlke.tools.filesystem.impl;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileFilter;

@Component
public class FileFilterDirectory implements FileFilter {

    @Override
    public boolean accept(File pathname) {
        return pathname.isDirectory();
    }
}
