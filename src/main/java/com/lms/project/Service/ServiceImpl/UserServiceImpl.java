package com.lms.project.Service.ServiceImpl;
import com.lms.project.Service.UserService;
import com.lms.project.configure.requestDtos.EmailVerifyDto;
import com.lms.project.dto.UserContextDto;
import com.lms.project.dto.UserContextHolder;
import com.lms.project.dto.UserDto;
import com.lms.project.dto.UserProfileDto;
import com.lms.project.exceptions.ApplicationException;
import com.lms.project.exceptions.ErrorCode;
import com.lms.project.model.User;
import com.lms.project.repository.UserRepository;
import com.lms.project.request.UserSignUpRequestDto;
import com.lms.project.response.PageResponse;
import com.lms.project.responseDtos.UserResponseDto;
import com.lms.project.utils.Constants;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    MongoTemplate mongoTemplate;

    private static String formatDob(LocalDate dob) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
        return dob.format(formatter);
    }

    private static LocalDateTime getLocalDateTime(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy")).atStartOfDay();
    }

    @Override
    public String userSignup(UserSignUpRequestDto userSignUpRequestDto) throws ApplicationException {
        if (userSignUpRequestDto.getId() == null) {
            Optional<com.lms.project.model.User> existingUser = userRepository.findByEmailOrMobileNo(userSignUpRequestDto.getUserEmailID(), userSignUpRequestDto.getMobile());
            if (existingUser.isPresent()) {
                if (existingUser.get().getMobile().equals(userSignUpRequestDto.getMobile())) {
                    throw new ApplicationException(ErrorCode.CAP_1018);
                } else if (existingUser.get().getEmail().equals(userSignUpRequestDto.getUserEmailID())) {
                    throw new ApplicationException(ErrorCode.CAP_1017);
                }
            }
        }
        try {
            User user = userRepository.findById(userSignUpRequestDto.getId()).orElse(new User());
            boolean isNewUser = user.getId() == null;
            user.setUserName(userSignUpRequestDto.getName());
            user.setUserType(userSignUpRequestDto.getUserType());
            user.setEmail(userSignUpRequestDto.getUserEmailID());
            user.setDob(getLocalDateTime(userSignUpRequestDto.getDob()));
            user.setDoj(getLocalDateTime(userSignUpRequestDto.getDoj()));
            user.setOgP(userSignUpRequestDto.getOgP());
            user.setMobile(userSignUpRequestDto.getMobile());
            user.setOccupation(userSignUpRequestDto.getOccupation());
            user.setStatus(userSignUpRequestDto.getStatus());
            user.setGender(userSignUpRequestDto.getGender());
            if (isNewUser) {
                if (userSignUpRequestDto.getPassword() != null) {
                    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
                    user.setPassword(bCryptPasswordEncoder.encode(userSignUpRequestDto.getPassword()));
                }
                user.setCreatedOn(LocalDateTime.now());
                if (userSignUpRequestDto.getCreatedBy() != null && !userSignUpRequestDto.getCreatedBy().isEmpty()) {
                    user.setCreatedBy(userSignUpRequestDto.getCreatedBy());
                }
            } else {
                user.setUpdatedBy(userSignUpRequestDto.getUpdatedBy());
                user.setUpdatedOn(LocalDateTime.now());
            }
            userRepository.save(user);
            return Constants.USER_CREATED_SUCCESSFULLY;
        } catch (Exception e) {
            throw new ApplicationException(ErrorCode.CAP_1001);
        }
    }

    @Override
    public UserDto getUserById(String id) throws ApplicationException {
        UserDto userDto = new UserDto();
        User user = userRepository.findById(id).orElseThrow(() -> new ApplicationException(ErrorCode.CAP_1016));
        userDto = modelMapper.map(user, UserDto.class);
        return userDto;
    }

    @Override
    public PageResponse<Object> getUser(Integer pageNo, String search) throws ApplicationException {
        PageResponse<Object> pageResponse = new PageResponse<>();
        Pageable pageable = PageRequest.of(pageNo - 1, 14);
        List<UserResponseDto> userResponseDtos = new LinkedList<>();
        try {
            Page<User> userPage;
            if (search == null || search.trim().isEmpty()) {
                userPage = userRepository.findAll(pageable);
            } else {
                userPage = userRepository.findByUsername(search, pageable);
            }
            userPage.forEach(user -> {
                UserResponseDto userResponseDTO = new UserResponseDto();
                userResponseDTO.setId(user.getId());
                userResponseDTO.setUsername(user.getUserName());
                userResponseDTO.setEmail(user.getEmail());
                userResponseDTO.setStatus(user.isStatus());
                userResponseDTO.setOccupation(user.getOccupation());
                userResponseDTO.setUserType(user.getUserType());
                userResponseDTO.setCreatedOn(String.valueOf(user.getCreatedOn()));
                userResponseDtos.add(userResponseDTO);
            });
            Page<UserResponseDto> userResponseDTOPage = new PageImpl<>(userResponseDtos, pageable, userPage.getTotalElements());
            pageResponse.setData(userResponseDTOPage.getContent());
            pageResponse.setHasNext(userResponseDTOPage.hasNext());
            pageResponse.setHasPrevious(userResponseDTOPage.hasPrevious());
            pageResponse.setTotalRecordCount(userResponseDTOPage.getTotalElements());
        } catch (Exception e) {
            throw new ApplicationException(ErrorCode.CAP_1001);
        }
        return pageResponse;
    }

    @Override
    public UserProfileDto addProfile() {
        UserContextDto userDto = UserContextHolder.getUserDto();
        User user = userRepository.findById(userDto.getId()).orElseThrow(() -> new RuntimeException("User Not Found"));
        UserProfileDto userProfileDto = new UserProfileDto();
        userProfileDto.setFullName(user.getUserName());
        userProfileDto.setDob(String.valueOf(user.getDob()));
        userProfileDto.setEmail(user.getEmail());
        userProfileDto.setStatus(user.isStatus());
        userProfileDto.setMobileNo(user.getMobile());
        return userProfileDto;
    }

    @Override
    public EmailVerifyDto findByEmailToName(String email) {
        User user = userRepository.findByEmailId(email);
        EmailVerifyDto emailVerifyDto = new EmailVerifyDto();
        emailVerifyDto.setEmail(user.getEmail());
        emailVerifyDto.setName(user.getUserName());
        return emailVerifyDto;
    }

    @Override
    public User getAggregation() {
        org.springframework.data.mongodb.core.query.Query query = new Query();
        query.addCriteria(Criteria.where("email").is("bharani.c@hepl.com"));
        List<User> userList = mongoTemplate.find(query, User.class);
        if (userList != null && !userList.isEmpty()) {
            return userList.get(0);
        }
        return null;
    }

//    public String getNoReturnName(String mobile) {
//        Aggregation aggregation = Aggregation.newAggregation(
//                Aggregation.match(Criteria.where(mobile).is(mobile)
//                ), Aggregation.project("userName"));
//        AggregationResults<User> results = mongoTemplate.aggregate(aggregation, "m_users", User.class);
//        if (results != null && !results.getMappedResults().isEmpty()) {
//            User user = results.getMappedResults().get(0);
//            return user.getUserName();
//        }
//        return null;
//    }
public String getNoReturnName(String mobile) {
    Aggregation aggregation = Aggregation.newAggregation(
            Aggregation.match(Criteria.where("mobile").is(mobile)),
            Aggregation.project("name")
    );
    AggregationResults<User> results = mongoTemplate.aggregate(aggregation, "m_users", User.class);
    if (results != null && !results.getMappedResults().isEmpty()) {
        User user = results.getMappedResults().get(0);
        return user.getUserName();
    }
    return null;
}

}