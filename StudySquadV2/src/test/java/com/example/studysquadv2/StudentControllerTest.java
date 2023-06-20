package com.example.studysquadv2;

import com.example.studysquadv2.ApiResponse.ApiResponse;
import com.example.studysquadv2.Model.MyUser;
import com.example.studysquadv2.Model.Student;
import com.example.studysquadv2.Model.Tutor;
import com.example.studysquadv2.Service.StudentService;
import com.example.studysquadv2.Service.TutorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = TutorControllerTest.class , excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class StudentControllerTest {
    @MockBean
    StudentService studentService;

    @Autowired
    MockMvc mockMvc;

    Student student1,student2,student3;
    MyUser myUser;

    ApiResponse apiResponse;

    List<Student> students,studentList;

    @BeforeEach
    void setUp(){
        myUser=new MyUser(null,"username","password","ADMIN",null,null);
        student1=new Student(null,"Raghad","email","female","Arabic","PhD",myUser,null);
        student2=new Student(null,"Raghad","email","female","Arabic","PhD",myUser,null);
        student3=new Student(null,"Raghad","email","female","Arabic","PhD",myUser,null);

        students= Arrays.asList(student1);
        studentList=Arrays.asList(student2);
    }

    @Test
    public void GetAllStudent() throws Exception {
        Mockito.when(studentService.getStudents()).thenReturn(students);
        mockMvc.perform(get("/api/v1/student/get-all"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("student1"));
    }

        @Test
    public void testAddStudent() throws  Exception {
        mockMvc.perform(post("/api/v1/student/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content( new ObjectMapper().writeValueAsString(student1)))
                .andExpect(status().isOk());

    }
}
