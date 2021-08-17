package mapping;

import dto.OrderProcessingPointDto;
import dto.UserDto;
import dto.WarehouseDto;
import entity.OrderProcessingPoint;
import entity.User;
import entity.Warehouse;
import org.modelmapper.ModelMapper;

public class UserMapper {
    private static final ModelMapper modelMapper = new ModelMapper();

    private UserMapper() {

    }

    public static UserDto toDto(User userToConvert) {
        UserDto convertedToDto = UserDto.builder()
                .id(userToConvert.getId())
                .name(userToConvert.getName())
                .surname(userToConvert.getSurname())
                .roles(userToConvert.getRoles())
                .build();
        if (userToConvert.getWorkingPlace() instanceof OrderProcessingPoint) {
            convertedToDto.setWorkingPlace(modelMapper.map(userToConvert.getWorkingPlace(), OrderProcessingPointDto.class));
        } else if (userToConvert.getWorkingPlace() instanceof Warehouse) {
            convertedToDto.setWorkingPlace(modelMapper.map(userToConvert.getWorkingPlace(), WarehouseDto.class));
        }
        return convertedToDto;
    }

    public static User toUser(UserDto userDtoToConvert) {
        User convertedToUser = User.builder()
                .id(userDtoToConvert.getId())
                .name(userDtoToConvert.getName())
                .surname(userDtoToConvert.getSurname())
                .roles(userDtoToConvert.getRoles())
                .build();
        if (userDtoToConvert.getWorkingPlace() instanceof OrderProcessingPointDto) {
            convertedToUser.setWorkingPlace(modelMapper.map(userDtoToConvert.getWorkingPlace(), OrderProcessingPoint.class));
        } else if (userDtoToConvert.getWorkingPlace() instanceof WarehouseDto) {
            convertedToUser.setWorkingPlace(modelMapper.map(userDtoToConvert.getWorkingPlace(), Warehouse.class));
        }
        return convertedToUser;
    }
}
