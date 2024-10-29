package com.lms.project.Service.Impl;

import com.lms.project.Service.Services.PatrhService;
import com.lms.project.dto.UserContextDto;
import com.lms.project.dto.UserContextHolder;
import com.lms.project.entity1.Path.Path;
import com.lms.project.entity1.dtos.PathDtos;
import com.lms.project.model.User;
import com.lms.project.repository.PathRepository;
import com.mongodb.client.result.DeleteResult;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PathServiceImpl implements PatrhService {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    PathRepository pathRepository;
    @Autowired
    MongoTemplate mt;

    @Override
    public String addPath(PathDtos pathDto) {
        UserContextDto userDto = UserContextHolder.getUserDto();
        User user = modelMapper.map(userDto, User.class);
        Path path = pathRepository.findById(pathDto.getId()).orElse(new Path());
        if (StringUtils.isBlank(pathDto.getId())) {
            if (pathRepository.findByPathName(StringUtils.capitalize(pathDto.getPathName()).trim()).isPresent()) {
                throw new RuntimeException("PathName Already Exists");
            }
            path.setCreatedAt(LocalDateTime.now());
            path.setCreatedBy(user);
        } else if (pathDto.getId() != null) {
            path.setPathName(pathDto.getPathName());
            path.setUpdatedAt(LocalDateTime.now());
            path.setUpdatedBy(user);
            mt.insert(path);
        }
        return StringUtils.isBlank(pathDto.getId()) ? "Path Added" : "Path Updated";
    }

    @Override
    public List<String> filterPathName( ) {
        Query query=new Query();
        query.addCriteria(Criteria.where("pathName").is("Product"));
        List<Path>paths=mt.find(query,Path.class,"path");
        List<String>pathName=new ArrayList<>();
        for (Path path:paths){
            pathName.add(path.getPathName());
        }
        return pathName;
    }

    public Optional<DeleteResult> findById(String id) {
        Query query=new Query();
        query.addCriteria(Criteria.where("id").is(id));
        DeleteResult path=mt.remove(query,Path.class);
        return Optional.ofNullable(path);
    }
}
