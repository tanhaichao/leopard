package io.leopard.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

public class PageableRowMapperResultSetExtractor<T> implements ResultSetExtractor<List<T>> {

	private final RowMapper<T> rowMapper;

	private final int start;
	private final int size;

	private int rowCount;

	public PageableRowMapperResultSetExtractor(RowMapper<T> rowMapper, int start, int size) {
		this.rowMapper = rowMapper;
		this.start = start;
		this.size = size;
	}

	@Override
	public List<T> extractData(ResultSet rs) throws SQLException {
		List<T> results = new ArrayList<T>();
		int rowNum = 0;
		if (start > 0) {
			rs.absolute(start);
		}
		while (rs.next()) {
			results.add(this.rowMapper.mapRow(rs, rowNum++));
			if (rowNum >= size) {
				break;
			}
		}

		{
			rs.last();
			this.rowCount = rs.getRow();
		}

		return results;
	}

	public int getCount() {
		return rowCount;
	}

}
