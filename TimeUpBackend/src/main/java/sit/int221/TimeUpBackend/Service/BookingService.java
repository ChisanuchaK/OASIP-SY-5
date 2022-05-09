package sit.int221.TimeUpBackend.Service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sit.int221.TimeUpBackend.DTO.BookingDTO;
import sit.int221.TimeUpBackend.DTO.BookingMoreDetailDTO;
import sit.int221.TimeUpBackend.Entity.Booking;
import sit.int221.TimeUpBackend.Repository.BookingRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;
    private ModelMapper modelMapper = new ModelMapper();

//    get
    public List<BookingDTO> getAllBookingDTO(){
        List<Booking>bookings= bookingRepository.findAll();
        return bookings.stream().map(e -> modelMapper.map(e, BookingDTO.class)).collect(Collectors.toList());
    }

    public List<BookingMoreDetailDTO> getAllBookingDetailDTO(){
        List<Booking> bookings= bookingRepository.findAll();
        return bookings.stream().map(e -> modelMapper.map(e, BookingMoreDetailDTO.class)).collect(Collectors.toList());
    }
    public BookingMoreDetailDTO getBookingDetailDTOById(Integer id){
        Booking bookings= bookingRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return modelMapper.map(bookings , BookingMoreDetailDTO.class);
    }


    // post
    public ResponseEntity create(Booking newBooking) {
        Booking booking = newBooking;
        List<Booking> checkCompare = bookingRepository.findAllByEventCategoryEventCategoryId(booking.getEventCategory().getEventCategoryId());

        if (checkCompare.stream().count() == 0 ){
            bookingRepository.save(newBooking);
            return ResponseEntity.ok(HttpStatus.OK);
        }
        if (!checkTimeOverLap(checkCompare , newBooking)){
            bookingRepository.save(newBooking);
            return ResponseEntity.status(201).body("Inserted Successfully!");
        }
        else {
            return ResponseEntity.status(400).body("Can't Insert Date is Overlap!!");
        }
    }


    public boolean checkTimeOverLap(List<Booking> allBooking , Booking booking ) {

        for (Booking book : allBooking) {
            if (((booking.getEventStartTime().toEpochMilli() >= book.getEventStartTime().toEpochMilli())
                    && ((booking.getEventStartTime().toEpochMilli() <= endTimeMs(book))))
                    || (( (endTimeMs(booking)) >= book.getEventStartTime().toEpochMilli())
                    && ((endTimeMs(booking))<= endTimeMs(book)))
            ) {
                return true;
            }
        }
        return false;
    }

    public long endTimeMs(Booking time){
        return (time.getEventStartTime().toEpochMilli() + ((time.getEventDuration() * 60000) - 1));
    }



    // delete
    public void deleteById (Integer idBooking){
        bookingRepository.deleteById(idBooking);
    }

    //put
    public ResponseEntity editBooking(Booking editBooking, Integer id) {
        Booking booking = bookingRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
        List<Booking> checkCompare = bookingRepository.findAllByEventCategoryEventCategoryId(booking.getEventCategory().getEventCategoryId());
        if (!checkTimeOverLap(checkCompare , editBooking)){
            modelMapper.map(editBooking , Booking.class);
            bookingRepository.save(editBooking);
            return ResponseEntity.status(201).body("Edit Successfully!");
        }
        else {
            return ResponseEntity.status(400).body("Can't Edit Date is Overlap!!");
        }
    }
}
