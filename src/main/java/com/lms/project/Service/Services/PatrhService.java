package com.lms.project.Service.Services;

import com.lms.project.entity1.dtos.PathDtos;

import java.util.List;

public interface PatrhService {
    String addPath(PathDtos pathDto);
    List<String> filterPathName();
}
