package knk.erp.api.shlee.Fixtures.service;

import knk.erp.api.shlee.Fixtures.repository.FixturesFormRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FixturesFormService {
    private final FixturesFormRepository fixturesFormRepository;
}
