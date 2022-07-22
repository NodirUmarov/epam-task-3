package com.epam.business.mapper.config;

import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;

/**
 * The interface that defines two basic configs for all application mappers:
 * <ul>
 *     <li>Value {@code "spring"} passed to {@link MapperConfig#componentModel()} to let Spring treat the mapper like bean</li>
 *     <li>{@link ReportingPolicy#IGNORE} passed to {@link MapperConfig#unmappedTargetPolicy()}
 *     to let mapper ignore missing attributes</li>
 * </ul>
 *
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 6/29/2022
 */
@MapperConfig(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ConfigMapper {
}
