package trading.services;

import org.springframework.stereotype.Service;
import trading.controller.dto.PositionSummaryDto;
import trading.repository.PositionRepository;

import java.util.List;

@Service
public class PositionService {

    private final PositionRepository positionRepository;

    public PositionService(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    public List<PositionSummaryDto> listAllPositions() {
        return positionRepository.findAllPositions();
    }
}
