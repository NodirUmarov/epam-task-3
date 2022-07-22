package com.epam.business.mapper.requestMapper;

import com.epam.business.mapper.config.ConfigMapper;
import com.epam.business.mapper.config.DtoMapper;
import com.epam.business.mapper.config.EntityMapper;
import com.epam.business.model.request.TagRequest;
import com.epam.domain.entity.certificate.Tag;
import org.mapstruct.Mapper;

/**
 * This interface inherits {@link EntityMapper} and sets to mapper parameters
 * <p>
 * {@link Tag} to parameter T
 * {@link TagRequest} to parameter S
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
 * @see TagRequest
 */
@Mapper(config = ConfigMapper.class)
public interface CreateTagMapper extends EntityMapper<Tag, TagRequest> {
}
