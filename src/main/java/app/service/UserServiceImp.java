package app.service;

import app.dao.UserDao;
import app.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import app.model.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImp implements UserService {

    private final UserDao userDao;
    @Autowired
    public UserServiceImp(UserDao userDao, ModelMapper modelMapper) {
        this.userDao = userDao;
        this.modelMapper = modelMapper;
    }

    private final ModelMapper modelMapper;
    @Override
    public void add(UserDto userDto) {
        User user = new User(userDto);
        userDao.add(user);
    }

    @Override
    public List<UserDto> getListOfUsers() {
        return userDao.listUsers().stream().map(UserDto::new).collect(Collectors.toList());
    }

    @Override
    public UserDto findById(Long id) {
//        return new UserDto(userDao.findById(id));
        User user = this.userDao.findById(id);
        UserDto userDto = this.modelMapper.map(user, UserDto.class);
        return userDto;
    }

    @Override
    public void deleteById(Long id) {
        userDao.remove(userDao.findById(id));
    }

    @Override
    public void merge(UserDto userDto) {
        User user = userDao.findById(userDto.getId());
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setAge(userDto.getAge());
        userDao.merge(user);
    }


}
