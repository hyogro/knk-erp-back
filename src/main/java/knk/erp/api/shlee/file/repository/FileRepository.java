package knk.erp.api.shlee.file.repository;

import knk.erp.api.shlee.file.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FileRepository extends JpaRepository<File, Long> {
    File findByFileName(String fileName);
}
