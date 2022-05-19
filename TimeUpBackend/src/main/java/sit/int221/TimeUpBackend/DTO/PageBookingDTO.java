package sit.int221.TimeUpBackend.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageBookingDTO {
    private List<BookingDTO>  content;
    private  int numberPage;
    private  int size;
    private int totalPages;
    private  int totalElements;
    private boolean first;
    private boolean last;
}
