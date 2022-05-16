package sit.int221.TimeUpBackend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import sit.int221.TimeUpBackend.DTO.BookingDTO;
import sit.int221.TimeUpBackend.DTO.BookingMoreDetailDTO;
import sit.int221.TimeUpBackend.DTO.BookingPUTDTO;
import sit.int221.TimeUpBackend.Entity.Booking;
import sit.int221.TimeUpBackend.Service.BookingService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;


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
    public ResponseEntity createBooking(@Valid @RequestBody Booking newBooking){
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
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}


