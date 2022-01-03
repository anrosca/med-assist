package inc.evil.medassist.teeth.service;

import inc.evil.medassist.common.exception.NotFoundException;
import inc.evil.medassist.teeth.model.Tooth;
import inc.evil.medassist.teeth.repository.ToothRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class ToothServiceImpl implements ToothService {
    private final ToothRepository toothRepository;

    @Override
    @Transactional(readOnly = true)
    public Set<Tooth> findAllByIdsIn(final List<String> ids) {
        return toothRepository.findAllByIdIn(ids);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Tooth> findAllByPatientId(String patientId) {
        return toothRepository.findAllByPatientIdWithPatient(patientId);
    }

    @Override
    @Transactional(readOnly = true)
    public Tooth findById(final String id) {
        return toothRepository.findById(id).orElseThrow(() -> new NotFoundException(Tooth.class, "id", id));
    }
}
