package com.mkts.waac.mappers;

import com.mkts.waac.Dto.*;
import com.mkts.waac.models.*;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccompPasspMapper {

    @Mapping(source = "number", target = "number")
    @Mapping(source = "accompPasspDate",target = "accompPasspDate",dateFormat="dd.MM.yyyy")
    @Mapping(source = "contract.id",target = "contractId")
    @Mapping(source = "contract.number", target = "contractNumber")
    @Mapping(source = "contract.contractDate",target = "contractDate")
    @Mapping(source = "contract.organization.name", target = "recipientOrganizationName")
    @Mapping(source = "contract.organization.address",target = "recipientOrganizationAddress")
    @Mapping(source = "contract.organization.wasteDestination", target = "wasteDestination")
    @Mapping(source = "carrierOrganization.id",target = "carrierOrganizationId")
    @Mapping(source = "carrierOrganization.name", target = "carrierOrganizationName")
    @Mapping(source = "carrierOrganization.address",target = "carrierOrganizationAddress")
    @Mapping(source = "transportationDate",target = "transportationDate",dateFormat="dd.MM.yyyy")
    @Mapping(source = "carModel",target = "carModel")
    @Mapping(source = "carNumber",target = "carNumber")
    @Mapping(source = "driverFio",target = "driverFio")
    @Mapping(source = "boxing",target = "boxing")
    @Mapping(source = "countStored",target = "countStored")
    @Mapping(source = "accompPasspWastes",target = "wasteTypeIdList")
    @Mapping(source = "accompPasspDepartments", target = "departmentDtos")
    AccompPasspDto convertToDto(AccompPassp entity);

    @Mapping(target = "wasteTypeId", source = "wasteTypes")
    @Mapping(target = "accompPassps", source = "accompPassps", ignore = true)
    @Mapping(target = "wasteWeight", source = "wasteWeight")
    @Mapping(target = "goal", source = "goal")
    AccompPasspWasteDto ACCOMP_PASSP_WASTE_DTO(AccompPasspWaste source);

    @Mapping(target = "accompPassps", source = "accompPassps", ignore = true)
    @Mapping(source = "department",target = "department")
    AccompPasspDepartmentDto ACCOMP_PASSP_DEPARTMENT_DTO(AccompPasspDepartment source);

    @Mapping(source = "contract.number", target = "contractNumber")
    @Mapping(source = "contract.contractDate", target = "contractDate", dateFormat="dd.MM.yyyy")
    @Mapping(source = "transportationDate", target = "transportationDate", dateFormat="dd.MM.yyyy")
    @Mapping(source = "accompPasspWastes", target = "accompPasspWasteDtoList")
    @Mapping(target = "carModel", source = "carModel")
    @Mapping(target = "carNumber", source = "carNumber")
    @Mapping(target = "driverFio", source = "driverFio")
    AccompPasspDetailsDto convertToDetailsDto(AccompPassp source);

    @Mapping(target = "accompPasspNumber", source = "number")
    @Mapping(target = "accompPasspDate", source = "accompPasspDate", dateFormat="dd.MM.yyyy")
    @Mapping(target = "transportationDate", source = "transportationDate", dateFormat="dd.MM.yyyy")
    @Mapping(target = "contractNumber", source = "contract.number")
    @Mapping(target = "contractDate", source = "contract.contractDate", dateFormat="dd.MM.yyyy")
    @Mapping(target = "recipientOrganizationName", source = "contract.organization.name")
    @Mapping(target = "wasteDangerousClassName", source = "contract.organization.wasteDestination")
    @Mapping(target = "accompPasspWasteDtoList", source = "accompPasspWastes")
    AccompPasspJournalDto convertToJounalDto(AccompPassp source);

    List<AccompPasspJournalDto> convertToJournalsDtoList(List<AccompPassp> entityList);

    @Mapping(source = "code", target = "code")
    @Mapping(source = "name",target = "name")
    @Mapping(source = "dangerousPow.id",target = "dangerousPowId")
    @Mapping(source = "dangerousPow.name",target = "dangerousPowName")
    @Mapping(source = "dangerousClass.id",target = "dangerousClassId")
    @Mapping(source = "dangerousClass.name",target = "dangerousClassName")
    @Mapping(source = "activityKinds.id",target = "activityKindId")
    @Mapping(source = "activityKinds.name",target = "activityKindName")
    @Mapping(source = "wasteNorm",target = "wasteNorm")
    WasteTypeDto WASTE_TYPE_DTO(WasteType source);

    @InheritInverseConfiguration( name = "convertToDto" )
    AccompPassp convertToEntity(AccompPasspDto dto);

    List<AccompPasspDto> convertToDtoList(List<AccompPassp> entityList);

    List<AccompPassp> convertToEntityList(List<AccompPasspDto> dtoList);


}
