package org.example.projectapp.mapper;

import org.example.projectapp.controller.dto.SearchDto;
import org.example.projectapp.mapper.dto.ElasticFilterDto;
import org.example.projectapp.mapper.dto.ElasticOperation;
import org.example.projectapp.mapper.dto.SearchElasticCriteriaDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SearchProjectElasticCriteriaDtoMapper implements SearchElasticCriteriaDtoMapper {
    public static final String NAME = "name";
    public static final String CATEGORY = "category";
    public static final String DESCRIPTION = "description";
    private final ElasticFilterDtoMapper filterDtoMapper;


    public SearchProjectElasticCriteriaDtoMapper(ElasticFilterDtoMapper mapper) {
        this.filterDtoMapper = mapper;
    }

    public SearchElasticCriteriaDto convertToSearchElasticCriteriaDto(SearchDto searchDto) {
        List<ElasticFilterDto> filterDtoList = getElasticFilterDtos(searchDto, filterDtoMapper);
        addSearchToFilterDtoList(searchDto, filterDtoList);

        return getElasticCriteriaDto(searchDto, filterDtoList);
    }

    private void addSearchToFilterDtoList(SearchDto searchDto, List<ElasticFilterDto> filterDtoList) {
        String search = searchDto.getSearch();
        if (search != null && !search.isBlank()) {
            boolean orPredicate = true;

            filterDtoList.add(getFilter(search, NAME, ElasticOperation.FULL_TEXT, orPredicate));
            filterDtoList.add(getFilter(search, CATEGORY, ElasticOperation.FULL_TEXT, orPredicate));
            filterDtoList.add(getFilter(search, DESCRIPTION, ElasticOperation.FULL_TEXT, orPredicate));
        }
    }
}
