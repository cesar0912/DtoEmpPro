package repositories;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T, ID> {
	List<T> findAll();

    boolean save(T entity);

    boolean delete(T entity);

}
