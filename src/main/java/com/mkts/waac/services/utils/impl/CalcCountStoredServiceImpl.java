package com.mkts.waac.services.utils.impl;

import com.mkts.waac.Dao.AccompPasspDao;
import com.mkts.waac.Dao.Pod9OwnWasteDao;
import com.mkts.waac.models.AccompPassp;
import com.mkts.waac.models.AccompPasspDepartment;
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

                        if(pod9OwnWaste.getAccompPasspWaste()!=null) {
                            List<AccompPasspDepartment> departments = pod9OwnWaste.getAccompPasspWaste().getAccompPassps().getAccompPasspDepartments();

                            for (AccompPasspDepartment department : departments) {
                                if (department.getDepartment().getId() == departmentId) {
                                    return true;
                                }
                            }
                        }else
                        {
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
                if(pod9OwnWastesLast.stream().count()!=0)
                {
                    lastKeeping = pod9OwnWastesLast.get(0).getCountKeeping();
                }
                for (int i = 0; i<pod9OwnWastes.stream().count();i++)
                {
                    if(pod9OwnWastes.get(i).getAccompPasspWaste() !=null && pod9OwnWastes.get(i).getWasteType()!=null)
                    {
                        lastKeeping -= pod9OwnWastes.get(i).getAccompPasspWaste().getWasteWeight();
                        lastKeeping += pod9OwnWastes.get(i).getCountFromOther();
                        pod9OwnWastes.get(i).setCountKeeping(lastKeeping);
                        pod9OwnWasteService.save(pod9OwnWastes.get(i));
                        continue;
                    }

                    if(pod9OwnWastes.get(i).getAccompPasspWaste() !=null) {
                        lastKeeping -= pod9OwnWastes.get(i).getAccompPasspWaste().getWasteWeight();
                    }else
                    {
                        lastKeeping += pod9OwnWastes.get(i).getWasteGenerate();
                    }
                    pod9OwnWastes.get(i).setCountKeeping(lastKeeping);
                    pod9OwnWasteService.save(pod9OwnWastes.get(i));
                }
            }
        }
    }

//    @Override
//    public void recalcCountStored(Integer wasteTypeId, Integer departmentId) {
//        List<Pod9OwnWaste> pod9OwnWasteList = pod9OwnWasteDao.findAllByDepartmentIdAndWasteTypeIdOrderByDate(departmentId, wasteTypeId);
//        List<AccompPassp> accompPasspList = accompPasspDao.findByDepartmentIdAndWasteTypeId(departmentId, wasteTypeId);
//
//        int APIndex = 0;
//        int OWIndex = 0;
//        Double countStored = 0d;
//        if (pod9OwnWasteList.size() > 0 && accompPasspList.size() > 0) {
//            LocalDate accompPasspDate = accompPasspList.get(0).getTransportationDate();
//            LocalDate ownWasteDate = pod9OwnWasteList.get(0).getDate();
//            if (ownWasteDate.isBefore(accompPasspDate) || ownWasteDate.isEqual(accompPasspDate)) {
//                OWIndex = 1;
//                countStored = pod9OwnWasteList.get(0).getCountKeeping();
//            } else {
//                APIndex = 1;
//            }
//
//            while (APIndex < accompPasspList.size() && OWIndex < pod9OwnWasteList.size()) {
//                accompPasspDate = accompPasspList.get(APIndex).getTransportationDate();
//                ownWasteDate = pod9OwnWasteList.get(OWIndex).getDate();
//
//                if (ownWasteDate.isBefore(accompPasspDate) || ownWasteDate.isEqual(accompPasspDate)) {
//                    pod9OwnWasteList.get(OWIndex).setCountKeeping(getOWCountStored(pod9OwnWasteList.get(OWIndex), countStored));
//                    countStored = pod9OwnWasteList.get(OWIndex).getCountKeeping();
//                    OWIndex++;
//                    continue;
//                }
//
//                if (ownWasteDate.isAfter(accompPasspDate)) {
//                    accompPasspList.get(APIndex).setCountStored(getAPCountStored(accompPasspList.get(APIndex), countStored));
//                    countStored = accompPasspList.get(APIndex).getCountStored();
//                    APIndex++;
//                }
//            }
//        }
//
//        if (OWIndex < pod9OwnWasteList.size()) {
//            for (int i = OWIndex; i < pod9OwnWasteList.size(); i++) {
//                pod9OwnWasteList.get(i).setCountKeeping(getOWCountStored(pod9OwnWasteList.get(i), countStored));
//                countStored = pod9OwnWasteList.get(i).getCountKeeping();
//            }
//        }
//
//        if (APIndex < accompPasspList.size()) {
//            for (int i = APIndex; i < accompPasspList.size(); i++) {
//                accompPasspList.get(i).setCountStored(getAPCountStored(accompPasspList.get(i), countStored));
//                countStored = accompPasspList.get(i).getCountStored();
//            }
//        }
//
//        accompPasspDao.saveAll(accompPasspList);
//        pod9OwnWasteDao.saveAll(pod9OwnWasteList);
//    }
//
//    private static Double getAPCountStored (AccompPassp accompPassp, Double countStored){
//        Double countGenerate = 0d;
//        double countTransferred = accompPassp.getWasteWeight() == null ? 0d : accompPassp.getWasteWeight();
//        return NumHelper.round(calcCountStored(countStored, countGenerate, countTransferred));
//    }
//
//    private static Double getOWCountStored (Pod9OwnWaste pod9OwnWaste, Double countStored){
//        Double countGenerate = pod9OwnWaste.getWasteGenerate();
//        double countTransferred = pod9OwnWaste.getWasteWeight() == null ? 0d : pod9OwnWaste.getWasteWeight();
//        return NumHelper.round(calcCountStored(countStored, countGenerate, countTransferred));
//    }
//
//    private static Double calcCountStored(Double countStored, Double countGenerate, Double countTransferred) {
//        double result = countStored + countGenerate - countTransferred;
//        return result < 0 ? 0d : result;
//    }
}
