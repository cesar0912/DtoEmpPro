package repositories;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T, ID> {
	List<T> findAll();
	Optional<T> findById(ID id);
    boolean save(T entity);
    boolean update(T entity);
    boolean delete(T entity);

}
