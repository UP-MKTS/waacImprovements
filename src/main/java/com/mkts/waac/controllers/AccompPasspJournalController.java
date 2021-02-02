package com.mkts.waac.controllers;

import com.mkts.waac.Dao.AccompPasspDao;
import com.mkts.waac.Dao.AccompPasspWasteDao;
import com.mkts.waac.Dao.WasteTypeDao;
import com.mkts.waac.Dto.AccompPasspDto;
import com.mkts.waac.Dto.AccompPasspJournalDto;
import com.mkts.waac.Dto.MonthDto;
import com.mkts.waac.models.AccompPasspWaste;
import com.mkts.waac.models.WasteType;
import com.mkts.waac.services.AccompPasspService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AccompPasspJournalController {

    private AccompPasspService accompPasspService;

    @Autowired
    private AccompPasspWasteDao accompPasspWasteDao;
    @Autowired
    private AccompPasspDao accompPasspDao;
    @Autowired
    private WasteTypeDao wasteTypeDao;

    @Autowired
    public AccompPasspJournalController(AccompPasspService accompPasspService) {
        this.accompPasspService = accompPasspService;
    }

    @GetMapping("/accomp-passp-journal")
    public String ShowAccPassp( Model model ) {
        List<String> years = accompPasspService.getYears();
        List<MonthDto> months = accompPasspService.getMonth();
        List<AccompPasspJournalDto> helpJournalDtos = accompPasspService.getAllByYearAndMonth(years.get(0),months.get(0).getMonthNumber());

        model.addAttribute("accompPassps", helpJournalDtos);
        model.addAttribute("years", years);
        model.addAttribute("months", months);
        return "accomp-passp-journal";
    }
}
