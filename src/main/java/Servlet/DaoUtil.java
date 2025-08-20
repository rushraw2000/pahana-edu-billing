package Servlet;

import util.DBconnection;
import java.sql.*;

public class DaoUtil {
    public interface Binder { void bind(PreparedStatement ps) throws Exception; }
    public static int execUpdate(String sql, Binder b) {
        try (Connection c = DBconnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            if (b != null) b.bind(ps);
            return ps.executeUpdate();
        } catch (Exception e) { throw new RuntimeException(e); }
    }
}
