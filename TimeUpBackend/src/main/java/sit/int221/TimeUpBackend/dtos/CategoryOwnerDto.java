package sit.int221.TimeUpBackend.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sit.int221.TimeUpBackend.entities.EventCategory;
import sit.int221.TimeUpBackend.entities.User;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryOwnerDto {
    private EventCategory eventcategoryEventcategory;
    private User userIduser;
}
