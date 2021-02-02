package com.mkts.waac.services;


import com.mkts.waac.Dto.Pod10Dto;

import java.time.YearMonth;
import java.util.List;

public interface Pod10Service {

    Pod10Dto getPod10ByMonth(YearMonth yearMonth);
}
