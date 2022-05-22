package sit.int221.TimeUpBackend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import sit.int221.TimeUpBackend.DTO.*;
import sit.int221.TimeUpBackend.Entity.Booking;
import sit.int221.TimeUpBackend.Service.BookingService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import static sit.int221.TimeUpBackend.Controller.EventCategoryController.getStringStringMap;

@RestController
@RequestMapping("/api/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping("")
    public List<BookingDTO> getAllBooking(){
        return bookingService.getAllBooking();
    }

    @GetMapping("/{id}")
    public BookingMoreDetailDTO getBookingById(@PathVariable Integer id){
        return bookingService.getBookingDetailDTOById(id);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity createBooking(@Valid @RequestBody BookingPOSTDTO newBooking){
        return bookingService.create(newBooking);
    }

    @DeleteMapping("/{idBooking}")
    public  void DeleteBookingById(@PathVariable Integer idBooking){
        bookingService.deleteById(idBooking);
    }


    @PutMapping("/{id}")
    public ResponseEntity editBooking(@Valid @RequestBody BookingPUTDTO editBooking , @PathVariable int id ){
        return bookingService.editBooking(editBooking , id);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        return getStringStringMap(ex);
    }
}


