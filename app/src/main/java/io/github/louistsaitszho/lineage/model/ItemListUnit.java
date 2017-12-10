package io.github.louistsaitszho.lineage.model;

/**
 * Created by lsteamer on 15/09/2017.
 */

public class ItemListUnit {

    //Description of the element
    private String textUnitDescription;

    //URL of the Image/Thumbnail to be downloaded
    private String urlUnitImage;

    //URL of the Video to be downloaded-played
    private String urlUnitVideo;


    //Generic constructor
    public ItemListUnit(String textUnitDescription, String urlUnitImage, String urlUnitVideo) {
        this.textUnitDescription = textUnitDescription;
        this.urlUnitImage = urlUnitImage;
        this.urlUnitVideo = urlUnitVideo;
    }


    //Getter methods
    public String getTextUnitDescription() {
        return textUnitDescription;
    }

    public String getUrlUnitImage() {
        return urlUnitImage;
    }

    public String getUrlUnitVideo() {
        return urlUnitVideo;
    }
}
