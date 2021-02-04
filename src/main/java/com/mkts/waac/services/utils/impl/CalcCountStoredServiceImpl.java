package com.mkts.waac.services.utils.impl;

import com.mkts.waac.Dao.AccompPasspDao;
import com.mkts.waac.Dao.Pod9OwnWasteDao;
import com.mkts.waac.models.AccompPassp;
import com.mkts.waac.models.AccompPasspDepartment;
import com.mkts.waac.models.AccompPasspWaste;
import com.mkts.waac.models.Pod9OwnWaste;
import com.mkts.waac.services.Pod9OwnWasteService;
import com.mkts.waac.services.helpers.NumHelper;
import com.mkts.waac.services.utils.CalcCountStoredService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Predicate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.cglib.core.CollectionUtils.filter;


@Service
@Transactional
public class CalcCountStoredServiceImpl implements CalcCountStoredService {

    private AccompPasspDao accompPasspDao;

    private Pod9OwnWasteDao pod9OwnWasteDao;

    @Autowired
    private Pod9OwnWasteService pod9OwnWasteService;

    @Autowired
    public CalcCountStoredServiceImpl(AccompPasspDao accompPasspDao, Pod9OwnWasteDao pod9OwnWasteDao) {
        this.accompPasspDao = accompPasspDao;
        this.pod9OwnWasteDao = pod9OwnWasteDao;
    }

    @Override
    public void recalcCountStored(List<Integer> wasteTypeIds, List<Integer> departmentIds, String date) {
        String year =  date.substring(6,date.length());
        for (Integer departmentId:departmentIds) {
            for(Integer wasteTypeId: wasteTypeIds) {
                List<Pod9OwnWaste> pod9OwnWastes = pod9OwnWasteDao.getPod9ByWasteIdAndYear(wasteTypeId, year);
                List<Pod9OwnWaste> pod9OwnWastesLast = pod9OwnWasteDao.getLastFromLastYearAndWastetype(String.valueOf(Integer.valueOf(year)-1), wasteTypeId);
                Predicate departmentFilter = new Predicate() {
                    @Override
                    public boolean evaluate(Object o) {
                        Pod9OwnWaste pod9OwnWaste = (Pod9OwnWaste) o;

                        if (pod9OwnWaste.getAccompPasspWaste() != null) {
                            if(pod9OwnWaste.getAccompPasspWaste().getDepartment().getId() == departmentId )
                            {
                                return true;
                            }
                        }
                        else{
                            if(pod9OwnWaste.getDepartment().getId()==departmentId){
                                return true;
                            }
                        }


                        return false;
                    }
                };

                filter(pod9OwnWastes, departmentFilter);
                filter(pod9OwnWastesLast,departmentFilter);
                Double lastKeeping = 0d;
                if(pod9OwnWastes.stream().count()==0){
                    continue;
                }
                if(pod9OwnWastesLast.stream().count()!=0)
                {
                    lastKeeping = pod9OwnWastesLast.get(0).getCountKeeping();
                }
                for (int i = 0; i<pod9OwnWastes.stream().count();i++)
                {
                    if(pod9OwnWastes.get(i).getAccompPasspWaste()!=null) {
                        lastKeeping -= pod9OwnWastes.get(i).getAccompPasspWaste().getWasteWeightPod9();
                    }
                    lastKeeping += pod9OwnWastes.get(i).getCountFromOtherPod9();
                    lastKeeping += pod9OwnWastes.get(i).getWasteGeneratePod9();
                    pod9OwnWastes.get(i).setCountKeeping(lastKeeping);
                    pod9OwnWasteService.save(pod9OwnWastes.get(i));

                }
            }
        }
    }

    @Override
    public void recalcCountStoredAll(List<Integer> wasteTypeIds, List<Integer> departmentIds, List<String> dates) {

        for (String year: dates) {
            for (Integer departmentId : departmentIds) {
                for (Integer wasteTypeId : wasteTypeIds) {
                    List<Pod9OwnWaste> pod9OwnWastes = pod9OwnWasteDao.getPod9ByWasteIdAndYear(wasteTypeId, year);
                    List<Pod9OwnWaste> pod9OwnWastesLast = pod9OwnWasteDao.getLastFromLastYearAndWastetype(String.valueOf(Integer.valueOf(year) - 1), wasteTypeId);
                    Predicate departmentFilter = new Predicate() {
                        @Override
                        public boolean evaluate(Object o) {
                            Pod9OwnWaste pod9OwnWaste = (Pod9OwnWaste) o;

                            if (pod9OwnWaste.getAccompPasspWaste() != null) {
                                if (pod9OwnWaste.getAccompPasspWaste().getDepartment().getId() == departmentId) {
                                    return true;
                                }
                            } else {
                                if (pod9OwnWaste.getDepartment().getId() == departmentId) {
                                    return true;
                                }
                            }


                            return false;
                        }
                    };

                    filter(pod9OwnWastes, departmentFilter);
                    filter(pod9OwnWastesLast, departmentFilter);
                    Double lastKeeping = 0d;
                    if (pod9OwnWastes.stream().count() == 0) {
                        continue;
                    }
                    if (pod9OwnWastesLast.stream().count() != 0) {
                        lastKeeping = pod9OwnWastesLast.get(0).getCountKeeping();
                    }
                    for (int i = 0; i < pod9OwnWastes.stream().count(); i++) {
                        if (pod9OwnWastes.get(i).getAccompPasspWaste() != null) {
                            lastKeeping -= pod9OwnWastes.get(i).getAccompPasspWaste().getWasteWeightPod9();
                        }
                        lastKeeping += pod9OwnWastes.get(i).getCountFromOtherPod9();
                        lastKeeping += pod9OwnWastes.get(i).getWasteGeneratePod9();
                        pod9OwnWastes.get(i).setCountKeeping(lastKeeping);
                        pod9OwnWasteService.save(pod9OwnWastes.get(i));

                    }
                }
            }
        }
    }
}
