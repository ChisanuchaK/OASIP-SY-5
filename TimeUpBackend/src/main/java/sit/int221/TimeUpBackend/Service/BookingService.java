package sit.int221.TimeUpBackend.Service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.web.server.ResponseStatusException;
import sit.int221.TimeUpBackend.DTO.*;
import sit.int221.TimeUpBackend.Entity.Booking;
import sit.int221.TimeUpBackend.Entity.EventCategory;
import sit.int221.TimeUpBackend.Repository.BookingRepository;
import sit.int221.TimeUpBackend.Repository.EventCategoryRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private EventCategoryRepository eventCategoryRepository;
    private ModelMapper modelMapper = new ModelMapper();

    //    get
    public List<BookingDTO> getAllBooking(){
        List<Booking>bookings= bookingRepository.findAll();
        return bookings.stream().map(e -> modelMapper.map(e, BookingDTO.class)).collect(Collectors.toList());
    }
    public PageBookingDTO getAllBookingTest(int page , int pageSize , String sortBy){
        Sort sort  =  Sort.by(Sort.Direction.DESC , sortBy);
        return modelMapper.map(bookingRepository.findAll(PageRequest.of(page , pageSize , sort)) , PageBookingDTO.class);
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
//    public ResponseEntity create(  Booking newBooking) {
//
//        List<Booking> checkCompare = bookingRepository.findAllByEventCategoryEventCategoryId(newBooking.getEventCategory().getEventCategoryId());
//        if (!checkTimeOverLap(checkCompare , newBooking)){
//            bookingRepository.save(newBooking);
//            return ResponseEntity.status(201).body("Edited Successfully");
//        }
//        else {
//            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST , "overlapped with other events");
//        }
//
//    }

    public ResponseEntity create( BookingPOSTDTO newBooking) {
        Booking booking = modelMapper.map(newBooking , Booking.class);
        EventCategory eventCategory = eventCategoryRepository.findById(newBooking.getEventCategory().getEventCategoryId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        booking.setEventDuration(eventCategory.getEventDuration());
        List<Booking> checkCompare = bookingRepository.findAllByEventCategoryEventCategoryId(booking.getEventCategory().getEventCategoryId());
        if (!checkTimeOverLap(checkCompare , booking)){
            bookingRepository.save(booking);
            return ResponseEntity.status(201).body("Inserted Successfully");
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
