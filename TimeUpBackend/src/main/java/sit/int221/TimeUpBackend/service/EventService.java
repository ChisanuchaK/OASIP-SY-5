package sit.int221.TimeUpBackend.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;
import sit.int221.TimeUpBackend.dtos.*;
import sit.int221.TimeUpBackend.entities.Event;
import sit.int221.TimeUpBackend.entities.EventCategory;
import sit.int221.TimeUpBackend.repositories.EventRepository;
import sit.int221.TimeUpBackend.repositories.EventCategoryRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private EventCategoryRepository eventCategoryRepository;
    private ModelMapper modelMapper = new ModelMapper();

    //    get
    public List<EventDto> getAllEvent(){
        List<Event>bookings= eventRepository.findAll();
        return bookings.stream().map(e -> modelMapper.map(e, EventDto.class)).collect(Collectors.toList());
    }

    public EventMoreDetailDto getEventDetailDTOById(Integer id){
        Event bookings= eventRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return modelMapper.map(bookings , EventMoreDetailDto.class);
    }
    public List<EventDto> getEventByIdCategory(@PathVariable Integer id){
            EventCategory eventCategory = eventCategoryRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            List<Event> bookingList = eventRepository.findAllByEventCategoryEventCategoryId(eventCategory.getEventCategoryId());
        return bookingList.stream().map(e -> modelMapper.map(e, EventDto.class)).collect(Collectors.toList());
    }
    // post

    public ResponseEntity create( EventPostDto eventPostDto) {
        Event booking = modelMapper.map(eventPostDto , Event.class);
        EventCategory eventCategory = eventCategoryRepository.findById(eventPostDto.getEventCategory().getEventCategoryId()).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        booking.setEventDuration(eventCategory.getEventDuration());
        List<Event> checkCompare = eventRepository.findAllByEventCategoryEventCategoryId(booking.getEventCategory().getEventCategoryId());
        if (!checkTimeOverLap(checkCompare , booking)){
            eventRepository.save(booking);
            return ResponseEntity.status(201).body("Inserted Successfully");
        }
        else {
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST , "overlapped with other events");
        }

    }
    public boolean checkTimeOverLap(List<Event> allEvent, Event event ) {

        for (Event book : allEvent) {
            if (((event.getEventStartTime().toEpochMilli() >= book.getEventStartTime().toEpochMilli())
                    && ((event.getEventStartTime().toEpochMilli() <= endTimeMs(book))))
                    || (( (endTimeMs(event)) >= book.getEventStartTime().toEpochMilli())
                    && ((endTimeMs(event))<= endTimeMs(book)))
                    || ((event.getEventStartTime().toEpochMilli() <= book.getEventStartTime().toEpochMilli())
                    && (endTimeMs(event) >= endTimeMs(book)))) {
                return true;
            }
        }
        return false;
    }

    public long endTimeMs(Event time){
        return (time.getEventStartTime().toEpochMilli() + ((time.getEventDuration() * 60000)));
    }
    // delete
    public void delete ( Integer id){
        eventRepository.deleteById(id);
    }

    //put
    public ResponseEntity editEvent(EventPutDto editEventPutDTO, int id) {

        Event event = eventRepository.findById(id).orElseThrow( ()->{
            return new ResponseStatusException(HttpStatus.NOT_FOUND);
        });
        List<Event> checkCompare = eventRepository.findAllByEventCategoryEventCategoryId(event.getEventCategory().getEventCategoryId());
        int i = 0;
        int index = 0;
        for(Event b: checkCompare){
            if(b.getIdBooking() == id){
                index = i;
            }
            i++;
        }
        checkCompare.remove(index);
                modelMapper.map(editEventPutDTO , event);

        if ((!checkTimeOverLap(checkCompare , event))){
                   eventRepository.saveAndFlush(event);
                   return ResponseEntity.status(200).body("Edited Successfully");
        }
        else {
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST , "overlapped with other events");
        }

    }
}
