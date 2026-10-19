package backend.controller;

import backend.service.IPositionService;
import backend.service.PositionService;
import entity.Position;

import java.util.List;

public class PositionController implements IPositionController {

    private final IPositionService positionService;

    public PositionController() {
        this.positionService = new PositionService();
    }

    public PositionController(IPositionService positionService) {
        this.positionService = positionService;
    }

    @Override
    public List<Position> getListPositions() throws Exception {
        return positionService.getListPositions();
    }

    @Override
    public Position getPositionById(int id) throws Exception {
        return positionService.getPositionById(id);
    }

    @Override
    public boolean isPositionExistsById(int id) throws Exception {
        return positionService.isPositionExistsById(id);
    }
}
