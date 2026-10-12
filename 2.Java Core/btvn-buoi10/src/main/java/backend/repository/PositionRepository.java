package backend.repository;

import entity.Position;
import utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PositionRepository implements IPositionRepository {

    @Override
    public List<Position> getListPositions() throws SQLException {
        List<Position> list = new ArrayList<>();
        String sql = "SELECT PositionID, PositionName FROM `Position` ORDER BY PositionID ASC";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DatabaseUtils.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Position(rs.getInt("PositionID"), rs.getString("PositionName")));
            }
        } finally {
            DatabaseUtils.close(rs, ps, conn);
        }

        return list;
    }

    @Override
    public Position getPositionById(int id) throws SQLException {
        String sql = "SELECT PositionID, PositionName FROM `Position` WHERE PositionID = ?";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DatabaseUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                return new Position(rs.getInt("PositionID"), rs.getString("PositionName"));
            }
        } finally {
            DatabaseUtils.close(rs, ps, conn);
        }

        return null;
    }

    @Override
    public boolean isPositionExistsById(int id) throws SQLException {
        String sql = "SELECT 1 FROM `Position` WHERE PositionID = ?";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DatabaseUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            return rs.next();
        } finally {
            DatabaseUtils.close(rs, ps, conn);
        }
    }
}
