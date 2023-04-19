package jp.co.axa.apidemo.mapper;

import static org.mapstruct.factory.Mappers.getMapper;

import jp.co.axa.apidemo.dto.EmployeeDTO;
import jp.co.axa.apidemo.entities.Employee;
import org.mapstruct.Mapper;

/**
 * employee mapper class to map from dto to entity and vice versa
 */
@Mapper
public interface EmployeeMapper {

    EmployeeMapper INSTANCE = getMapper(EmployeeMapper.class);

    /**
     * maps from {@link EmployeeDTO} to {@link Employee}
     *
     * @param dto the employee dto
     * @return employee entity
     */
    Employee dtoToEntity(EmployeeDTO dto);

    /**
     * maps from {@link Employee} to {@link EmployeeDTO}
     *
     * @param entity the employee entity
     * @return employee dto
     */
    EmployeeDTO entityToDto(Employee entity);
}
