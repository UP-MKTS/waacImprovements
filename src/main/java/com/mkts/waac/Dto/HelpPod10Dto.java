package com.mkts.waac.Dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class HelpPod10Dto {

    String date = "Запись не вносилась";

    String wasteName;

    Integer wasteCode;

    String powAndClassDanger;

    String wasteNorm;

    String departmentNames;

    Double sumGenerate = 0d;

    Double sumCountFromOther = 0d;

    Double sumCountFromPeople = 0d;

    Double sumCountUsed = 0d;

    Double sumCountNeutralized = 0d;

    Double fromSave = 0d;

    Double burial = 0d;

    List<GoalsPod10Dto> goalsPod10Dtos = new ArrayList<>();

    Double keeping = 0d;

    public Double getTransferredUsed(){

        for (GoalsPod10Dto goal: goalsPod10Dtos)
        {
            if(goal.getName().equals("Использование")){
                return goal.getSum();
            }
        }

        return 0d;
    }

    public Double getTransferredNeutralized(){

        for (GoalsPod10Dto goal: goalsPod10Dtos)
        {
            if(goal.getName().equals("Обезвреживание")){
                return goal.getSum();
            }
        }

        return 0d;
    }

    public Double getTransferredStored(){

        for (GoalsPod10Dto goal: goalsPod10Dtos)
        {
            if(goal.getName().equals("Хранение")){
                return goal.getSum();
            }
        }

        return 0d;
    }

    public Double getTransferredBuried(){

        for (GoalsPod10Dto goal: goalsPod10Dtos)
        {
            if(goal.getName().equals("Захоронение")){
                return goal.getSum();
            }
        }

        return 0d;
    }


}
