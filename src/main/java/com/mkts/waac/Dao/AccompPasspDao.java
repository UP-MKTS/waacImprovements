package com.mkts.waac.Dao;

import com.mkts.waac.models.AccompPassp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AccompPasspDao extends JpaRepository<AccompPassp, Integer> {

    AccompPassp findFirstByNumber(Integer number);

    AccompPassp findFirstById(Integer id);

    AccompPassp findFirst1ByOrderByNumberDesc();

    AccompPassp findByNumber(Integer number);

//    @Query("SELECT a FROM AccompPassp a where a.department.id = ?1 and a.wasteType.id = ?2 and a.wasteWeight is not null order by a.transportationDate, a.number")
//    List<AccompPassp> findByDepartmentIdAndWasteTypeId(Integer departmentId, Integer wasteTypeId);
//
    @Query("SELECT distinct SUBSTRING(a.accompPasspDate, 1, 4) FROM AccompPassp a order by SUBSTRING(a.accompPasspDate, 1, 4) desc")
    List<String> findYears();

    @Query("SELECT distinct SUBSTRING(a.accompPasspDate, 6, 2) FROM AccompPassp a order by SUBSTRING(a.accompPasspDate, 6, 2) desc")
    List<String> findMonthNumber();
//
    @Query("SELECT a FROM AccompPassp a where SUBSTRING(a.accompPasspDate, 1, 4) = ?1 and SUBSTRING(a.accompPasspDate, 6, 2) = ?2")
    List<AccompPassp> getAllByYearAndMonth(String year, String month);
//
//    @Query("SELECT a FROM AccompPassp a where SUBSTRING(a.accompPasspDate, 1, 4) = ?1 and SUBSTRING(a.accompPasspDate, 6, 2) = ?2" +
//            " order by a.number desc")
//    List<AccompPassp> getAllByYearAndMonthJournal(String year, String month);
//
//    @Query("SELECT a FROM AccompPassp a where a.wasteWeight is null order by a.number")
//    List<AccompPassp> getAllWeightNull();
//
//    @Query("SELECT a FROM AccompPassp a where SUBSTRING(a.transportationDate, 6, 2) = ?1" +
//            " and a.wasteWeight is not null order by a.wasteType.name")
//    List<AccompPassp> findAllByMonth(String month);
//
//    @Query("SELECT sum(ap.wasteWeight) FROM AccompPassp ap where ap.wasteType.id = ?1 and ap.transportationDate <= ?2")
//    Double sumWasteTransferred(Integer wasteTypeId, LocalDate date);
}
