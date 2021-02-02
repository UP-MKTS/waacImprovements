package com.mkts.waac.services.utils;

public interface ReportManager {

    byte[] downloadReport(String reportFileName);

    void removeReport(String reportFileName);

}