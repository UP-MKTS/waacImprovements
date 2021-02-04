package com.mkts.waac.services.utils;

import java.util.List;

public interface CalcCountStoredService {

    void recalcCountStored (List<Integer> wasteTypeIds, List<Integer> departmentIds, String date);

    void recalcCountStoredAll (List<Integer> wasteTypeIds, List<Integer> departmentIds, List<String> dates);
}
