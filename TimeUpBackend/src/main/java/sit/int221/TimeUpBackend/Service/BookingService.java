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
        List<Booking> checkCompare = bookingRepository.findBookingByEventCategoryEventCategoryName(booking.getEventCategory().getEventCategoryName());
        if (checkCompare.stream().count() == 0 ){
                 bookingRepository.save(newBooking);
                 return ResponseEntity.ok(HttpStatus.OK);
        }
//       return ResponseEntity.status(400).body(checkTimeOverLap2(checkCompare , newBooking));
        if (!checkTimeOverLap(checkCompare , newBooking)){
            bookingRepository.save(newBooking);
            return ResponseEntity.status(201).body("Inserted Successfully!");
        }
        else {
            return ResponseEntity.status(400).body("Can't Insert Date is Overlap!!");
        }
    }

    public boolean checkTimeOverLap(List<Booking> allBooking , Booking booking ){
        for(Booking book : allBooking){
            if ((booking.getEventStartTime().toEpochMilli() >= book.getEventStartTime().toEpochMilli() && booking.getEventStartTime().toEpochMilli() <= endTimeMs(book))){
                return true;
            }
        }
       return false;
    }

    public long endTimeMs(Booking time){
        return (time.getEventStartTime().toEpochMilli() + (time.getEventCategory().getEventDuration() * 60000));
    }


// delete
        public void deleteById (Integer idBooking){
            bookingRepository.deleteById(idBooking);
        }
        public void deleteAll () {
            bookingRepository.deleteAll();
        }
    }
