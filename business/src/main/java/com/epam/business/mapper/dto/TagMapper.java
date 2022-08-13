package com.epam.business.mapper.dto;

import com.epam.business.mapper.config.ConfigMapper;
import com.epam.business.mapper.config.DtoMapper;
import com.epam.business.mapper.config.EntityMapper;
import com.epam.business.model.dto.TagDto;
import com.epam.domain.entity.certificate.Tag;
import org.mapstruct.Mapper;

/**
 * This interface inherits {@link EntityMapper} and {@link DtoMapper}, and sets to mapper parameters
 * <p>
 * {@link Tag} to parameter T
 * {@link TagDto} to parameter S
 * <p>
 * All configs retrieved from {@link ConfigMapper}
 * The implementation of mapper will be created by mapstruct and will be passed to Spring as bean. Can be autowired.
 *
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 6/29/2022
 * @see ConfigMapper
 * @see Mapper
 * @see EntityMapper
 * @see DtoMapper
 * @see Tag
 * @see TagDto
 */
@Mapper(config = ConfigMapper.class)
public interface TagMapper extends EntityMapper<Tag, TagDto>, DtoMapper<Tag, TagDto> {
}