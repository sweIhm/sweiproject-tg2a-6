package base.admin;

import org.springframework.data.repository.CrudRepository;

public interface EMailRepository extends CrudRepository<EMail, Long> {
}