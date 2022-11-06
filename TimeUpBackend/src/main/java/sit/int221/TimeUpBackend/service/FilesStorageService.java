package sit.int221.TimeUpBackend.service;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface FilesStorageService {
    public void init();

    public void save(MultipartFile file , Integer number);

    public Resource load(Integer id);

    public ResponseEntity deleteById(Integer id) throws IOException;

    public Stream<Path> loadAll();


}
