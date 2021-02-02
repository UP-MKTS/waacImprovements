package com.mkts.waac.services;

import com.mkts.waac.Dto.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface AccompPasspReportService {

	String createAccompPasspReport(int id) throws IOException;

	String createAccompPasspJournalReport(int year) throws IOException;
}
