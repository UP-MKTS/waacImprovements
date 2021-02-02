package com.mkts.waac.services.utils.impl;

import com.mkts.waac.services.utils.ReportManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Transactional
public class ReportManagerImpl implements ReportManager {

    @Override
    public byte[] downloadReport(String reportFileName) {
        Path path = Paths.get(reportFileName);
        byte[] response;
        try {
            response = Files.readAllBytes(path);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка чтения данных в поток вывода.");
        }
        return response;
    }

    @Override
    public void removeReport(String reportFileName) {
        File file = new File(reportFileName);
        file.delete();
    }
}
