package sit.int221.TimeUpBackend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sit.int221.TimeUpBackend.DTO.BookingDTO;
import sit.int221.TimeUpBackend.DTO.BookingMoreDetailDTO;
import sit.int221.TimeUpBackend.Entity.Booking;
import sit.int221.TimeUpBackend.Repository.BookingRepository;
import sit.int221.TimeUpBackend.Service.BookingService;

import javax.servlet.annotation.HttpConstraint;
import java.util.List;

@RestController
@RequestMapping("/api/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private BookingRepository bookingRepository;

    @GetMapping("/more-detail")
    public List<BookingMoreDetailDTO> getAllBookingDetail(){
        return bookingService.getAllBookingDetailDTO();
    }

    @GetMapping("")
    public List<BookingDTO> getAllBooking(){
        return bookingService.getAllBookingDTO();
    }

    @GetMapping("/{id}")
    public BookingMoreDetailDTO getBookingById(@PathVariable Integer id){
        return bookingService.getBookingDetailDTOById(id);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity createBooking(@RequestBody Booking newBooking){
        return bookingService.create(newBooking);
    }

    @DeleteMapping("/{idBooking}")
    public  void DeleteBookingById(@PathVariable Integer idBooking){
        bookingService.deleteById(idBooking);
    }
    @DeleteMapping("")
    public  void DeleteAllBooking(){
        bookingService.deleteAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity editBooking(@RequestBody Booking editBooking , @PathVariable Integer id){
        return bookingService.editBooking(editBooking , id);
    }
}


