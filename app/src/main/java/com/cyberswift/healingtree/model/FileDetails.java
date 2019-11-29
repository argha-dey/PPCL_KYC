package com.cyberswift.healingtree.model;

import android.net.Uri;

import java.io.InputStream;
import java.io.Serializable;

public class FileDetails implements Serializable {

    private InputStream inputStream;
    private int fileSize;
    private String fileName;
    private String filePath;

    public Uri getFileUri() {
        return fileUri;
    }

    public void setFileUri(Uri fileUri) {
        this.fileUri = fileUri;
    }

    private Uri fileUri ;

    public String getStringFileUri() {
        return stringFileUri;
    }

    public void setStringFileUri(String stringFileUri) {
        this.stringFileUri = stringFileUri;
    }

    private String stringFileUri;
    private int actionType;
    private String selectedFilePath;
    private String mimeType;

    public String getFromWhere() {
        return fromWhere;
    }

    public void setFromWhere(String fromWhere) {
        this.fromWhere = fromWhere;
    }

    private String fromWhere;

    public FileDetails(){

    }
    public FileDetails(String fileName, String filePath, String _stringFileUri) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.stringFileUri = _stringFileUri;

    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }


    /**
     * @return the inputStream
     */
    public InputStream getInputStream() {
        return inputStream;
    }

    /**
     * @param inputStream
     *            the inputStream to set
     */
    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    /**
     * @return the fileSize
     */
    public int getFileSize() {
        return fileSize;
    }

    /**
     * @param fileSize
     *            the fileSize to set
     */
    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName
     *            the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * @return the actionType
     */
    public int getActionType() {
        return actionType;
    }

    /**
     * @param actionType
     *            the actionType to set
     */
    public void setActionType(int actionType) {
        this.actionType = actionType;
    }

    public String getSelectedFilePath() {
        return selectedFilePath;
    }

    public void setSelectedFilePath(String selectedFilePath) {
        this.selectedFilePath = selectedFilePath;
    }

}

