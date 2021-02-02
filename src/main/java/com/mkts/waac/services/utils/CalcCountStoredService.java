package com.mkts.waac.services.utils;

import java.util.List;

public interface CalcCountStoredService {

    void recalcCountStored (List<Integer> wasteTypeIds, List<Integer> departmentIds, String date);
}
