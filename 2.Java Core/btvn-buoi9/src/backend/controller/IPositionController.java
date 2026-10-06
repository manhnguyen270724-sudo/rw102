package backend.controller;

import entity.Position;

import java.util.List;

public interface IPositionController {
    List<Position> getListPositions() throws Exception;

    Position getPositionById(int id) throws Exception;

    boolean isPositionExistsById(int id) throws Exception;
}
