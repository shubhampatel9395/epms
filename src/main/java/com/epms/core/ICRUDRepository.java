package com.epms.core;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

public interface ICRUDRepository<E, I> {
	public List<E> findAll();

	public List<E> findByFieldValue(String fieldName, Object fieldValue);

	public default List<E> findByNamedParameters(MapSqlParameterSource paramSource) {
		throw new UnsupportedOperationException();
	}

	public E findById(I id);

	public E insert(E entity);

	public void delete(I id);

	public E update(E entity);

	public default E insertOrUpdate(E entity) {
		throw new UnsupportedOperationException();
	}

	public default boolean softDelete(I id) {
		return false;
	}

}
