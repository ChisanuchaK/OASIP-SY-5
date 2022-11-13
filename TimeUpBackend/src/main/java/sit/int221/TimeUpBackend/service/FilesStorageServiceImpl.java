package sit.int221.TimeUpBackend.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import sit.int221.TimeUpBackend.dtos.EventMoreDetailDto;
import sit.int221.TimeUpBackend.entities.Event;
import sit.int221.TimeUpBackend.entities.EventCategoryOwner;
import sit.int221.TimeUpBackend.entities.User;
import sit.int221.TimeUpBackend.repositories.EventCategoryOwnerRepository;
import sit.int221.TimeUpBackend.repositories.EventRepository;
import sit.int221.TimeUpBackend.repositories.UserRepository;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static antlr.build.ANTLR.root;
@Service
public class FilesStorageServiceImpl implements FilesStorageService{

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventCategoryOwnerRepository eventCategoryOwnerRepository;

    private ModelMapper modelMapper = new ModelMapper();
    private final Path root = Paths.get("uploads");
    @Override
    public void init() {
        try {
            Files.createDirectory(root);
        } catch (IOException e) {
         return;
        }
    }

    @Override
    public void save(MultipartFile file , Integer number) {

        Event eventId = eventRepository.findById(number).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        try {
            Event event = eventRepository.findByFileName(file.getOriginalFilename());
            if(event == null){
                Files.copy(file.getInputStream() , this.root.resolve(  file.getOriginalFilename()));
                eventId.setFileName( file.getOriginalFilename());
            }
            else {
                Files.copy(file.getInputStream() , this.root.resolve("(" + number + ")" + file.getOriginalFilename()));
                eventId.setFileName("(" + number + ")" + file.getOriginalFilename());
            }
        } catch (Exception e) {
            file = null;
        }
    }

    @Override
    public Resource load(Integer id) {
        UserDetails getCurrentAuthentication = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByEmailUser(getCurrentAuthentication.getUsername());
        Event event = eventRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        try {
            if (user.getRoleUser().equals("admin")) {
                Path file = root.resolve(event.getFileName());
                Resource resource = new UrlResource(file.toUri());
                if (resource.exists() || resource.isReadable()) {
                    return resource;
                } else {
                    throw new RuntimeException("Could not read the file!");
                }
            } else if (user.getRoleUser().equals("student") && event.getBookingEmail().equals(getCurrentAuthentication.getUsername())) {
                Path file = root.resolve(event.getFileName());
                    Resource resource = new UrlResource(file.toUri());
                    if (resource.exists() || resource.isReadable()) {
                        return resource;
                    } else {
                        throw new RuntimeException("Could not read the file!");
                    }

            } else if (user.getRoleUser().equals("lecturer")) {
                List<EventCategoryOwner> eventCategoryOwner = eventCategoryOwnerRepository.findAllByUserIduser(user);
                ArrayList<Integer> arrayIdCategory = new ArrayList<>();
                for (int i = 0 ; i < eventCategoryOwner.size() ; i ++){
                    arrayIdCategory.add(eventCategoryOwner.get(i).getEventcategoryEventcategory().getEventCategoryId());
                }
                if(arrayIdCategory.indexOf(event.getEventCategory().getEventCategoryId()) != -1){
                    Event bookings = eventRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
                    Path file = root.resolve(bookings.getFileName());
                    Resource resource = new UrlResource(file.toUri());
                    if (resource.exists() || resource.isReadable()) {
                        return resource;
                    } else {
                        throw new RuntimeException("Could not read the file!");
                    }
                } else {
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "email is not the same as student's email");
                }
            }

        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "email is not the same as student's email");
    }

    @Override
    public ResponseEntity deleteById(Integer id) throws IOException {
        UserDetails getCurrentAuthentication = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByEmailUser(getCurrentAuthentication.getUsername());
        Event event = eventRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if(user.getRoleUser().equals("admin")){
            FileSystemUtils.deleteRecursively(root.resolve(event.getFileName()));
            event.setFileName(null);
            eventRepository.saveAndFlush(event);
            return ResponseEntity.ok().body("Delete file success");
        }
        else if(user.getRoleUser().equals("student") && event.getBookingEmail().equals(getCurrentAuthentication.getUsername())){
          if(event.getFileName() != null){
              FileSystemUtils.deleteRecursively(root.resolve(event.getFileName()));
              event.setFileName(null);
              eventRepository.saveAndFlush(event);
              return ResponseEntity.ok().body("Delete file success");
          }
          else {
              throw new ResponseStatusException(HttpStatus.BAD_REQUEST , " don't has no file in this event");
          }
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN);

    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }

}
