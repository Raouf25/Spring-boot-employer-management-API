package com.mak.springbootemployermanagementapi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmployerServiceTests {
    private EmployerService employerService;

    @Mock
    private EmployerRepository employerRepository;

    @BeforeEach
    public void setup() {
        employerService = new EmployerService(employerRepository);
    }

    @Test
    void testDelete() {
        // Given
        Integer id = 1;
        Employer employer = new Employer();
        employer.setId(id);
        Mockito.when(employerRepository.findById(id)).thenReturn(Optional.of(employer));

        // When
        employerService.delete(id);

        // Then
        Mockito.verify(employerRepository).delete(employer);
    }

    @Test
    void testGet() {
        // Given
        Integer id = 1;
        Employer employer = new Employer();
        employer.setId(id);
        Mockito.when(employerRepository.findById(id)).thenReturn(Optional.of(employer));

        // When
        Employer result = employerService.get(id);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(id);
    }

    @Test
    void testGetAll() {
        // Given
        List<Employer> employers = Arrays.asList(new Employer(), new Employer());
        Mockito.when(employerRepository.findAll()).thenReturn(employers);

        // When
        List<Employer> result = employerService.get();

        // Then
        assertThat(result).isNotNull()
                .hasSize(2);
    }

    @Test
    void testCreate() {
        // Given
        Employer employer = new Employer();
        Mockito.when(employerRepository.save(employer)).thenReturn(employer);

        // When
        Employer result = employerService.create(employer);

        // Then
        assertThat(result).isNotNull().isEqualTo(employer);
    }

    @Test
    void update_shouldSaveEmployer() {
        // Given
        Employer employer = new Employer(1, "Jane", "Brown", 85000D);
        doReturn(employer).when(employerRepository).save(any(Employer.class));

        // When
        Employer updatedEmployer = employerService.update(1, employer);

        // Then
        verify(employerRepository).save(employer);
        assertThat(updatedEmployer).isEqualTo(employer);
    }

    @Test
    void update_shouldThrowException_whenEmployerIsNull() {
        // Given
        Employer employer = null;

        // When/Then
        assertThatThrownBy(() -> employerService.update(1, employer))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Can not update entity, entity is null.");
    }

    @Test
    void update_shouldThrowException_whenIdsDoNotMatch() {
        // Given
        Employer employer = new Employer(2, "Alex", "Johnson", 75000D);

        // When/Then
        assertThatThrownBy(() -> employerService.update(1, employer))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Can not update entity, the resource ID (1) not match the objet ID (2).");
    }
}
