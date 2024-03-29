//package com.hodik.elastic.service;
//
//import com.google.gson.Gson;
//import com.hodik.elastic.dto.SearchCriteriaDto;
//import com.hodik.elastic.dto.SearchSort;
//import com.hodik.elastic.exception.EntityAlreadyExistsException;
//import com.hodik.elastic.mapper.PageableMapper;
//import com.hodik.elastic.model.Vacancy;
//import com.hodik.elastic.repository.VacancyRepository;
//import com.hodik.elastic.repository.VacancySearchRepository;
//import io.micrometer.core.instrument.Timer;
//import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.*;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.mockito.junit.jupiter.MockitoSettings;
//import org.mockito.quality.Strictness;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.elasticsearch.core.ResourceUtil;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//@MockitoSettings(strictness = Strictness.LENIENT)
//class EsVacancyServiceTest {
//    public static final long ID = 1L;
//    public static final String ABOUT_PROJECT = "about project";
//    public static final String CREATOR = "Creator";
//    public static final String DESCRIPTION = "Description";
//    public static final String EXPECTED = "Expected";
//    public static final long PROJECT_ID = 1L;
//    public static final String JOB_POSITION = "job position";
//    public static final int PAGE = 0;
//    public static final int SIZE = 2;
//
//    private final Vacancy expectedVacancy = getVacancyBuild();
//    private final List<Vacancy> expectedVacancyList = List.of(expectedVacancy);
//    private final SearchSort searchSort = new SearchSort("Creator", true);
//    private final List<SearchSort> searchSortList = List.of(searchSort);
//
//    private final Gson gson= new Gson();
//    private final SearchCriteriaDto searchCriteriaDtoSuccess = gson.fromJson(ResourceUtil.readFileFromClasspath("search.criteria.vacancy.success.json"), SearchCriteriaDto.class);
//    private final SearchCriteriaDto searchCriteriaDtoWrong = gson.fromJson(ResourceUtil.readFileFromClasspath("search.criteria.vacancy.wrong.json"), SearchCriteriaDto.class);
//    private final SearchCriteriaDto searchCriteriaDtoFiltersNull = new SearchCriteriaDto(null, PAGE, SIZE, searchSortList);
//    private final SearchCriteriaDto searchCriteriaDtoFiltersEmpty = new SearchCriteriaDto(List.of(), PAGE, SIZE, searchSortList);
//
//    private final PageRequest expectedPage = PageRequest.of(PAGE, SIZE, Sort.by(Sort.Direction.ASC, CREATOR));
//
//    @Mock
//    private VacancyRepository vacancyRepository;
//    @Mock
//    private VacancySearchRepository vacancySearchRepository;
//    @Mock
//    private PageableMapper pageableMapper;
//    @Mock
//    private SimpleMeterRegistry registry;
//    @Spy
//    private Timer timer;
//    @InjectMocks
//    private EsVacancyService vacancyService;
//    @Captor
//    private ArgumentCaptor<Pageable> pageableCaptor;
//
//    @BeforeEach
//    void setUp() {
//        Timer.Sample sample = Timer.start();
//        when(registry.timer(any())).thenReturn(timer);
//        vacancyService.setTimer(registry.timer("elastic.vacancies.search.timer"));
////        when(timer.record(() -> sample.stop(timer) / 1000000)).thenReturn(1L);
//    }
//
//    @Test
//    void shouldCreateVacancy() throws EntityAlreadyExistsException {
//        //given
//        when(vacancyRepository.findById(anyLong())).thenReturn(Optional.empty());
//        //when
//        vacancyService.create(expectedVacancy);
//        //then
//        verify(vacancyRepository).save(expectedVacancy);
//        verify(vacancyRepository).findById(expectedVacancy.getId());
//    }
//
//    @Test
//    void shouldTrowExceptionWhenFindByIsPresent() {
//        //given
//        when(vacancyRepository.findById(anyLong())).thenReturn(Optional.of(expectedVacancy));
//        //when
//        EntityAlreadyExistsException exception = assertThrows(EntityAlreadyExistsException.class, () -> vacancyService.create(expectedVacancy));
//        //then
//        verify(vacancyRepository).findById(expectedVacancy.getId());
//        assertEquals("[ELASTIC] Vacancy already exists id= 1", exception.getMessage());
//    }
//
//    @Test
//    void shouldUpdateVacancy() {
//        vacancyService.update(expectedVacancy.getId(), expectedVacancy);
//        verify(vacancyRepository).save(expectedVacancy);
//    }
//
//    @Test
//    void shouldDeleteVacancy() {
//        vacancyService.delete(ID);
//        verify(vacancyRepository).deleteById(expectedVacancy.getId());
//    }
//
//    @Test
//    void shouldReturnVacancyById() {
//        //given
//        when(vacancyRepository.findById(anyLong())).thenReturn(Optional.of(expectedVacancy));
//        //when
//        Optional<Vacancy> vacancy = vacancyService.findById(expectedVacancy.getId());
//        //then
//        assertEquals(Optional.of(expectedVacancy), vacancy);
//        verify(vacancyRepository).findById(expectedVacancy.getId());
//
//
//    }
//
//    @Test
//    void shouldReturnAllVacancies() {
//        //given
//        when(vacancyRepository.findAll()).thenReturn(expectedVacancyList);
//        //when
//        List<Vacancy> vacancies = vacancyService.findAll();
//        //then
//        verify(vacancyRepository).findAll();
//        assertEquals(expectedVacancyList, vacancies);
//    }
//
//    @Test
//    void shouldReturnAllVacanciesByPageable() {
//        //given
//        when(vacancyRepository.findAll(expectedPage)).thenReturn(new PageImpl<>(expectedVacancyList, expectedPage, 1));
//        //when
//        List<Vacancy> vacancies = vacancyService.findAll(expectedPage);
//        //then
//        assertEquals(expectedVacancyList, vacancies);
//        verify(vacancyRepository).findAll(expectedPage);
//
//    }
//
//    @Test
//    void shouldReturnVacanciesWithFilters() {
//        //given
//        when(vacancySearchRepository.findAllWithFilters(any())).thenReturn(expectedVacancyList);
//        //when
//        List<Vacancy> vacancies = vacancyService.findAllWithFilters(searchCriteriaDtoSuccess);
//        //then
//        assertEquals(expectedVacancyList, vacancies);
//        verify(vacancySearchRepository).findAllWithFilters(searchCriteriaDtoSuccess);
//    }
//
//    @Test
//    void shouldTrowExceptionWhenWrongColumn() {
//
//        //when
//       assertThrows(IllegalArgumentException.class, ()->vacancyService.findAllWithFilters(searchCriteriaDtoWrong));
//        //then
//
//        verify(vacancySearchRepository, never()).findAllWithFilters(searchCriteriaDtoWrong);
//    }
//
//    @Test
//    void shouldReturnVacanciesWithNullFilters() {
//        //given
//        when(vacancyRepository.findAll(expectedPage)).thenReturn(new PageImpl<>(expectedVacancyList, expectedPage, 1));
//        when(pageableMapper.getPageable(searchCriteriaDtoFiltersNull)).thenCallRealMethod();
//        //when
//        List<Vacancy> vacancies = vacancyService.findAllWithFilters(searchCriteriaDtoFiltersNull);
//        //then
//        assertEquals(expectedVacancyList, vacancies);
//        verify(vacancyRepository).findAll(pageableCaptor.capture());
//        verify(pageableMapper).getPageable(searchCriteriaDtoFiltersNull);
//        Pageable value = pageableCaptor.getValue();
//        assertEquals(expectedPage, value);
//    }
//
//    @Test
//    void shouldReturnVacanciesWithEmptyFilters() {
//        //given
//        when(vacancyRepository.findAll(expectedPage)).thenReturn(new PageImpl<>(expectedVacancyList, expectedPage, 1));
//        when(pageableMapper.getPageable(searchCriteriaDtoFiltersEmpty)).thenCallRealMethod();
//        //when
//        List<Vacancy> vacancies = vacancyService.findAllWithFilters(searchCriteriaDtoFiltersEmpty);
//        //then
//        assertEquals(expectedVacancyList, vacancies);
//        verify(pageableMapper).getPageable(searchCriteriaDtoFiltersEmpty);
//        verify(vacancyRepository).findAll(pageableCaptor.capture());
//        Pageable value = pageableCaptor.getValue();
//        assertEquals(expectedPage, value);
//    }
//
//    private Vacancy getVacancyBuild() {
//        return Vacancy.builder()
//                .id(ID)
//                .aboutProject(ABOUT_PROJECT)
//                .creator(CREATOR)
//                .description(DESCRIPTION)
//                .expected(EXPECTED)
//                .projectId(PROJECT_ID)
//                .jobPosition(JOB_POSITION)
//                .build();
//    }
//}