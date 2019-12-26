package org.woehlke.tools.jobs.images.impl;

import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.ImageMetadata;
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;
import org.apache.commons.imaging.formats.tiff.TiffField;
import org.apache.commons.imaging.formats.tiff.constants.TiffTagConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.woehlke.tools.db.LogbuchQueueService;
import org.woehlke.tools.jobs.images.ShrinkJpgImage;
import org.woehlke.tools.db.JpgImage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ShrinkJpgImageImpl implements ShrinkJpgImage {

    private final LogbuchQueueService log;

    @Autowired
    public ShrinkJpgImageImpl(final LogbuchQueueService log) {
        this.log = log;
    }

    private List<JpgImage> listJpgImage = new ArrayList<>();

    @Override
    public File shrienk(File srcFile) {
        log.info("JPEG : "+srcFile.getAbsolutePath());
        File fileObj = srcFile;
        final ImageMetadata metadata;
        try {
            metadata = Imaging.getMetadata(fileObj);
            if(metadata != null) {
                if (metadata instanceof JpegImageMetadata) {
                    log.info(metadata.toString());
                    final JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;
                    long width = 0L;
                    long length = 0L;
                    try {
                        final TiffField fieldWidth = jpegMetadata.findEXIFValueWithExactMatch(TiffTagConstants.TIFF_TAG_IMAGE_WIDTH);
                        if (fieldWidth != null) {
                            width = fieldWidth.getIntValue();
                            log.info("Width: " + width);
                        }
                    } catch (NullPointerException e) {
                    }
                    try {
                        final TiffField fieldLength = jpegMetadata.findEXIFValueWithExactMatch(TiffTagConstants.TIFF_TAG_IMAGE_LENGTH);
                        if (fieldLength != null) {
                            length = fieldLength.getIntValue();
                            log.info("Length: " + length);
                        }
                    } catch (NullPointerException e) {
                    }
                    if ((length > 0L) && (width > 0L)) {
                        JpgImage jpgImage = new JpgImage(srcFile, length, width);
                        listJpgImage.add(jpgImage);
                        int prozent = jpgImage.scaleFactor();
                        log.info("prozent: " + prozent);
                        String srcPath = srcFile.getAbsolutePath();
                        String targetPath = srcPath + "_bak.jpg";
                        String command = "magick convert " + srcPath + " -resize " + prozent + "% -density 72x72 " + targetPath;
                        log.info(command);
                        Process process = Runtime.getRuntime().exec(command);
                        try {
                            process.waitFor();
                            File tmpFile = new File(targetPath);
                            srcFile.delete();
                            srcFile = new File(srcPath);
                            tmpFile.renameTo(srcFile);
                            srcFile = new File(srcPath);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (ImageReadException e) { } catch (IOException e) { }
        return srcFile;
    }

    public List<JpgImage> getListJpgImage() {
        return listJpgImage;
    }
}