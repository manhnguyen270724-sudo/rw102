package backend.service;

import backend.repository.IPositionRepository;
import backend.repository.PositionRepository;
import entity.Position;

import java.sql.SQLException;
import java.util.List;

public class PositionService implements IPositionService {

    private final IPositionRepository positionRepository;

    public PositionService() {
        this.positionRepository = new PositionRepository();
    }

    public PositionService(IPositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    @Override
    public List<Position> getListPositions() throws SQLException {
        return positionRepository.getListPositions();
    }

    @Override
    public Position getPositionById(int id) throws SQLException {
        return positionRepository.getPositionById(id);
    }

    @Override
    public boolean isPositionExistsById(int id) throws SQLException {
        return positionRepository.isPositionExistsById(id);
    }
}
