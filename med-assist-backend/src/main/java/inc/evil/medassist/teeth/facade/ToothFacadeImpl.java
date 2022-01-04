package inc.evil.medassist.teeth.facade;

import inc.evil.medassist.teeth.service.ToothService;
import inc.evil.medassist.teeth.web.ToothResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
class ToothFacadeImpl implements ToothFacade {
    private final ToothService toothService;

    @Override
    public ToothResponse findById(String id) {
        return ToothResponse.from(toothService.findById(id));
    }

    @Override
    public List<ToothResponse> findAllByPatientId(String patientId) {
        return toothService.findAllByPatientId(patientId)
                .stream()
                .map(ToothResponse::from)
                .toList();
    }

}
