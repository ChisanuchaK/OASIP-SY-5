package sit.int221.TimeUpBackend.Service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.web.server.ResponseStatusException;
import sit.int221.TimeUpBackend.DTO.BookingDTO;
import sit.int221.TimeUpBackend.DTO.BookingMoreDetailDTO;
import sit.int221.TimeUpBackend.DTO.BookingPUTDTO;
import sit.int221.TimeUpBackend.Entity.Booking;
import sit.int221.TimeUpBackend.Repository.BookingRepository;

import java.util.*;
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
    public ResponseEntity create(  Booking newBooking) {

        List<Booking> checkCompare = bookingRepository.findAllByEventCategoryEventCategoryId(newBooking.getEventCategory().getEventCategoryId());
        if (!checkTimeOverLap(checkCompare , newBooking)){
                    bookingRepository.save(newBooking);
            return ResponseEntity.status(201).body("Edited Successfully");
        }
        else {
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST , "overlapped with other events");
        }

    }
    public boolean checkTimeOverLap(List<Booking> allBooking , Booking booking ) {

        for (Booking book : allBooking) {
            if (((booking.getEventStartTime().toEpochMilli() >= book.getEventStartTime().toEpochMilli())
                    && ((booking.getEventStartTime().toEpochMilli() <= endTimeMs(book))))
                    || (( (endTimeMs(booking)) >= book.getEventStartTime().toEpochMilli())
                    && ((endTimeMs(booking))<= endTimeMs(book)))
                    || ((booking.getEventStartTime().toEpochMilli() <= book.getEventStartTime().toEpochMilli()) && (endTimeMs(booking) >= endTimeMs(book)))) {
                return true;
            }
        }
        return false;
    }

    public long endTimeMs(Booking time){
        return (time.getEventStartTime().toEpochMilli() + ((time.getEventDuration() * 60000)));
    }

    // delete
    public void deleteById (Integer idBooking){
        bookingRepository.deleteById(idBooking);
    }

    //put
    public ResponseEntity editBooking(BookingPUTDTO editBooking, int id) {

        Booking booking = bookingRepository.findById(id).orElseThrow( ()->{
            return new ResponseStatusException(HttpStatus.NOT_FOUND);
        });
        List<Booking> checkCompare = bookingRepository.findAllByEventCategoryEventCategoryId(booking.getEventCategory().getEventCategoryId());
        int i = 0;
        int index = 0;
        for(Booking b: checkCompare){
            if(b.getIdBooking() == id){
                index = i;
            }
            i++;
        }
        checkCompare.remove(index);
                modelMapper.map(editBooking , booking);

        if ((!checkTimeOverLap(checkCompare , booking))){
                   bookingRepository.saveAndFlush(booking);
                   return ResponseEntity.status(200).body("Edited Successfully");
        }
        else {
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST , "overlapped with other events");
        }
    }
}
