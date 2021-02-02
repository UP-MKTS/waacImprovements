package com.mkts.waac.services.impl;

import com.mkts.waac.Dao.Pod9OwnWasteDao;
import com.mkts.waac.Dto.GoalsPod10Dto;
import com.mkts.waac.Dto.HelpPod10Dto;
import com.mkts.waac.Dto.Pod10Dto;
import com.mkts.waac.models.AccompPasspDepartment;
import com.mkts.waac.models.Department;
import com.mkts.waac.models.Pod9OwnWaste;
import com.mkts.waac.models.WasteType;
import com.mkts.waac.services.AccompPasspService;
import com.mkts.waac.services.Pod10Service;
import com.mkts.waac.services.Pod9OwnWasteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Predicate;
import org.springframework.stereotype.Indexed;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.cglib.core.CollectionUtils.filter;

@Service
@Transactional
public class Pod10ServiceImpl implements Pod10Service {

	private AccompPasspService accompPasspService;

	private Pod9OwnWasteService pod9OwnWasteService;

	@Autowired
	private Pod9OwnWasteDao pod9OwnWasteDao;

	@Autowired
	public Pod10ServiceImpl(AccompPasspService accompPasspService, Pod9OwnWasteService pod9OwnWasteService) {
		this.accompPasspService = accompPasspService;
		this.pod9OwnWasteService = pod9OwnWasteService;
	}

	@Override
	public Pod10Dto getPod10ByMonth(YearMonth yearMonth) {
		List<Pod10Dto> pod10Dtos = new ArrayList<>();
		String month = String.valueOf(yearMonth.getMonthValue());
        String year = String.valueOf(yearMonth.getYear());
		month = month.length() < 2 ? "0" + month : month;
        List<Pod9OwnWaste> pod9ByByMonthAndYear = pod9OwnWasteService.getPod9ByByMonthAndYear(month, year);
		LocalDate date = yearMonth.atEndOfMonth();
        List<Integer> wasteTypesId = new ArrayList<>();

        for (Pod9OwnWaste pod9OwnWaste:pod9ByByMonthAndYear){
            if(pod9OwnWaste.getWasteType()!=null) {
                wasteTypesId.add(pod9OwnWaste.getWasteType().getId());
            }else
            {
                wasteTypesId.add(pod9OwnWaste.getAccompPasspWaste().getWasteTypes().getId());

            }
        }

        wasteTypesId = wasteTypesId.stream().distinct().collect(Collectors.toList());

        Pod10Dto pod10Dto = new Pod10Dto();
        pod10Dto.setDtoList(new ArrayList<>());
        int i =0;
        for (Integer index:wasteTypesId)
        {
            pod10Dto.getDtoList().add(new HelpPod10Dto());
            List<Pod9OwnWaste> temp = new ArrayList<>();
            List<String> goalsNames = new ArrayList<>();
            List<String> departmentsNames = new ArrayList<>();
            List<Integer> departmentsIndexs = new ArrayList<>();
            for (Pod9OwnWaste pod9OwnWaste:pod9ByByMonthAndYear){
                if(pod9OwnWaste.getAccompPasspWaste()!=null) {
                    if(pod9OwnWaste.getAccompPasspWaste().getWasteTypes().getId()==index && pod9OwnWaste.getAccompPasspWaste().getWasteWeight() != 0d)
                    {
                        temp.add(pod9OwnWaste);
                        goalsNames.add(pod9OwnWaste.getAccompPasspWaste().getGoal().getName());
                    }
                }
            }
            goalsNames = goalsNames.stream().distinct().collect(Collectors.toList());
            for (int goal = 0; goal< goalsNames.stream().count(); goal++){
                pod10Dto.getDtoList().get(i).getGoalsPod10Dtos().add(new GoalsPod10Dto());
                pod10Dto.getDtoList().get(i).getGoalsPod10Dtos().get(goal).setName(goalsNames.get(goal));
            }

            for (Pod9OwnWaste pod9OwnWaste:temp){
                if(pod9OwnWaste.getAccompPasspWaste()!=null) {
                    if(pod9OwnWaste.getAccompPasspWaste().getWasteTypes().getId()==index && pod9OwnWaste.getAccompPasspWaste().getWasteWeight() != 0d)
                    {

                        for (AccompPasspDepartment department :pod9OwnWaste.getAccompPasspWaste().getAccompPassps().getAccompPasspDepartments())
                        {
                            departmentsNames.add(department.getDepartment().getShortName());
                            departmentsIndexs.add(department.getDepartment().getId());
                        }
                        for (GoalsPod10Dto goalsPod10Dto: pod10Dto.getDtoList().get(i).getGoalsPod10Dtos())
                        {
                            if(goalsPod10Dto.getName()==pod9OwnWaste.getAccompPasspWaste().getGoal().getName()){
                                goalsPod10Dto.setSum(goalsPod10Dto.getSum()+pod9OwnWaste.getAccompPasspWaste().getWasteWeight());
                            }
                        }
                    }
                    else
                    {
                        departmentsNames.add(pod9OwnWaste.getDepartment().getShortName());
                        departmentsIndexs.add(pod9OwnWaste.getDepartment().getId());
                    }
                }
            }
            departmentsNames = departmentsNames.stream().distinct().collect(Collectors.toList());
//            Double summ = sum(temp);
            pod10Dto.getDtoList().get(i).setWasteCode(temp.get(0).getAccompPasspWaste().getWasteTypes().getCode());
            pod10Dto.getDtoList().get(i).setWasteName(temp.get(0).getAccompPasspWaste().getWasteTypes().getName());
            pod10Dto.getDtoList().get(i).setPowAndClassDanger(
                    temp.get(0).getAccompPasspWaste().getWasteTypes().getDangerousClass().getName() + " - "+
                            temp.get(0).getAccompPasspWaste().getWasteTypes().getDangerousPow().getName());
            pod10Dto.getDtoList().get(i).setWasteNorm(temp.get(0).getAccompPasspWaste().getWasteTypes().getWasteNorm());
            pod10Dto.getDtoList().get(i).setSumCountFromOther(pod9OwnWasteDao.sumCountFromOtherByWasteId(index,month,year));
            pod10Dto.getDtoList().get(i).setSumCountFromPeople(pod9OwnWasteDao.sumCountFromPeopleByWasteId(index,month,year));
            pod10Dto.getDtoList().get(i).setSumCountNeutralized(pod9OwnWasteDao.sumCountNeutralizedByWasteId(index,month,year));
            pod10Dto.getDtoList().get(i).setSumCountUsed(pod9OwnWasteDao.sumCountUsedByWasteId(index,month,year));
            pod10Dto.getDtoList().get(i).setSumGenerate(pod9OwnWasteDao.sumGenerateByWasteId(index,month,year));
            String departments = "";

            for (int j=0; j<departmentsNames.stream().count();j++)
            {
                if(j!=departmentsNames.stream().count()-1) {
                    departments +=departmentsNames.get(j) + "; ";
                }else {
                    departments +=departmentsNames.get(j);
                }
            }

            pod10Dto.getDtoList().get(i).setKeeping(0d);
            for (Integer departmentId: departmentsIndexs) {
                List<Pod9OwnWaste> all = pod9OwnWasteDao.getPod9ByWasteIdAndYear(index, year);
                Predicate departmentFilter = new Predicate() {
                    @Override
                    public boolean evaluate(Object o) {
                        Pod9OwnWaste pod9OwnWaste = (Pod9OwnWaste) o;

                        if (pod9OwnWaste.getAccompPasspWaste() != null) {
                            List<AccompPasspDepartment> departments = pod9OwnWaste.getAccompPasspWaste().getAccompPassps().getAccompPasspDepartments();

                            for (AccompPasspDepartment department : departments) {
                                if (department.getDepartment().getId() == departmentId) {
                                    return true;
                                }
                            }
                        } else {
                            if (pod9OwnWaste.getDepartment().getId() == departmentId) {
                                return true;
                            }
                        }

                        return false;
                    }
                };
                filter(all,departmentFilter);
                Integer ind = (int)all.stream().count();
                pod10Dto.getDtoList().get(i).setKeeping(pod10Dto.getDtoList().get(i).getKeeping()+all.get(ind-1).getCountKeeping());
            }

            pod10Dto.getDtoList().get(i).setDepartmentNames(departments);

            i++;
        }

		return pod10Dto;
	}

	private Double sum(List<Pod9OwnWaste> temp){
	    Double result = 0d;
	    for (Pod9OwnWaste pod9OwnWaste:temp){
	        result+= pod9OwnWaste.getAccompPasspWaste().getWasteWeight();
        }

	    return result;
    }
}
