package backend.repository;

import entity.Position;

import java.sql.SQLException;
import java.util.List;

public interface IPositionRepository {
    List<Position> getListPositions() throws SQLException;

    Position getPositionById(int id) throws SQLException;

    boolean isPositionExistsById(int id) throws SQLException;
}
