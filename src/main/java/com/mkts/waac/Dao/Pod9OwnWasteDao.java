package com.mkts.waac.Dao;

import com.mkts.waac.models.Pod9OwnWaste;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface Pod9OwnWasteDao extends JpaRepository<Pod9OwnWaste, Integer> {

    void deleteByAccompPasspWaste_Id(Integer id);

    List<Pod9OwnWaste> findAllByAccompPasspWaste_WasteTypes_Id(Integer wasteType);

    Pod9OwnWaste findByAccompPasspWaste_Id(Integer id);

    @Query("select a from Pod9OwnWaste a left join AccompPasspWaste b on a.accompPasspWaste.id = b.id where (b.wasteWeight<>0.0 or a.wasteGenerate<>0.0) and (a.wasteType.id =?1 or b.wasteTypes.id =?1) and substring(a.transparentDate,1,4)=?2 order by a.transparentDate asc ")
    List<Pod9OwnWaste> getPod9ByWasteIdAndYear(Integer wasteTypeId, String year);

    @Query("select a from Pod9OwnWaste a left join AccompPasspWaste b on a.accompPasspWaste.id = b.id where (a.wasteType.id =?2 or b.wasteTypes.id =?2) and substring(a.transparentDate,1,4)=?1 order by a.transparentDate desc ")
    List<Pod9OwnWaste> getLastFromLastYearAndWastetype(String year, Integer wasteTypeId);

    @Query("select a from Pod9OwnWaste a order by a.transparentDate asc")
    List<Pod9OwnWaste> getAll();

    @Query("select a from Pod9OwnWaste a where substring(a.transparentDate,1,4)=?2 and substring(a.transparentDate,6,2)=?1 order by a.transparentDate asc")
    List<Pod9OwnWaste> getAllByDate(String month, String Year);

    @Query("select a from Pod9OwnWaste a where substring(a.transparentDate,1,4)=?2 and substring(a.transparentDate,6,2)=?1")
    List<Pod9OwnWaste> findAllByDate(String month, String year);

    @Query("select sum(a.wasteGenerate) from Pod9OwnWaste a where a.wasteType.id =?1 and substring(a.transparentDate,1,4)=?3 and substring(a.transparentDate,6,2)=?2")
    Double sumGenerateByWasteId(Integer id,String month, String year);

    @Query("select sum(a.countFromOther) from Pod9OwnWaste a where a.wasteType.id =?1 and substring(a.transparentDate,1,4)=?3 and substring(a.transparentDate,6,2)=?2")
    Double sumCountFromOtherByWasteId(Integer id, String month, String year);

    @Query("select sum(a.countFromPeople) from Pod9OwnWaste a where a.wasteType.id =?1 and substring(a.transparentDate,1,4)=?3 and substring(a.transparentDate,6,2)=?2")
    Double sumCountFromPeopleByWasteId(Integer id, String month, String year);

    @Query("select sum(a.countUsed) from Pod9OwnWaste a where a.wasteType.id =?1 and substring(a.transparentDate,1,4)=?3 and substring(a.transparentDate,6,2)=?2")
    Double sumCountUsedByWasteId(Integer id, String month, String year);

    @Query("select sum(a.countNeutralized) from Pod9OwnWaste a where a.wasteType.id =?1 and substring(a.transparentDate,1,4)=?3 and substring(a.transparentDate,6,2)=?2")
    Double sumCountNeutralizedByWasteId(Integer id, String month, String year);
}
