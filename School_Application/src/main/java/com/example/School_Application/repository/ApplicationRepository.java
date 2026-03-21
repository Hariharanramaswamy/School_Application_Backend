package com.example.School_Application.repository;

import com.example.School_Application.entity.Application;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface ApplicationRepository extends MongoRepository<Application, String> {
    List<Application>findByUserId(String userId);
    boolean existsByAadhaarNumberAndGrade(String aadharNumber, String grade);


}
