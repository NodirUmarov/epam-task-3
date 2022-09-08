package com.epam.domain.repository;

import com.epam.domain.config.DomainConfig;
import com.epam.domain.entity.user.UserDetails;
import com.epam.domain.repository.provider.UserDetailsProvider;
import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import javax.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

@DataJpaTest
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") // IDE thinks there is no bean of the repository
@ContextConfiguration(classes = DomainConfig.class)
class UserDetailsRepositoryTest {

    private final EntityManager entityManager;
    private final UserDetailsRepository userDetailsRepository;

    @Autowired
    public UserDetailsRepositoryTest(EntityManager entityManager, UserDetailsRepository userDetailsRepository) {
        this.entityManager = entityManager;
        this.userDetailsRepository = userDetailsRepository;
    }

    @DisplayName("Should save users details")
    @ParameterizedTest
    @ArgumentsSource(UserDetailsProvider.class)
    void shouldSaveUserDetails(UserDetails userDetails) {
        userDetailsRepository.save(userDetails);
        entityManager.detach(userDetails);

        Optional<UserDetails> entity = userDetailsRepository.findById(userDetails.getId());
        Assertions.assertThat(entity).isPresent();

        Assertions.assertThat(entity.get()).usingRecursiveComparison().isEqualTo(userDetails);
    }

}
