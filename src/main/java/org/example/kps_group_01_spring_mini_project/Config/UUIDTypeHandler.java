package org.example.kps_group_01_spring_mini_project.Config;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UUIDTypeHandler extends BaseTypeHandler<UUID> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, UUID parameter, JdbcType jdbcType) throws SQLException {
        ps.setObject(i, parameter.toString(), jdbcType.TYPE_CODE); // Store UUID as string
    }

    @Override
    public UUID getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String uuidStr = rs.getString(columnName);
        return uuidStr != null ? UUID.fromString(uuidStr) : null;
    }

    @Override
    public UUID getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String uuidStr = rs.getString(columnIndex);
        return uuidStr != null ? UUID.fromString(uuidStr) : null;
    }

    @Override
    public UUID getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String uuidStr = cs.getString(columnIndex);
        return uuidStr != null ? UUID.fromString(uuidStr) : null;
    }
}

