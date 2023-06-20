package com.example.studysquadv2.Service;

import com.example.studysquadv2.DTO.RatingDTO;
import com.example.studysquadv2.Model.*;
import com.example.studysquadv2.Repository.AuthRepository;
import com.example.studysquadv2.Repository.RatingRepository;
import com.example.studysquadv2.Repository.ReservationRepository;
import com.example.studysquadv2.Repository.TutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;
    private final AuthRepository authRepository;
    private final TutorRepository tutorRepository;
    private final ReservationRepository reservationRepository;




    //----------Get All Rating By Tutor Id---------//
    public List<Rating> GetAllByTutorId(Integer auth, Integer tutorId){
        return ratingRepository.findAllByTutorId(tutorId);
    }


//    //-------------------ADD Rate-----------------------//
//    public void addRate(Integer studentId, Integer resid,Rating rating,Integer tutorId){
//
//        MyUser student=authRepository.findMyUserById(studentId);
//        Tutor tutor=tutorRepository.findTutorById(tutorId);
//        Reservation reservation=reservationRepository.findReservationById(resid);
//
//
//        if(student==null){
//            throw new RuntimeException("Student not found");
//        }
//
//        if(tutor==null){
//            throw new RuntimeException("Tutor not found");
//        }
//    Rating rating1=new Rating(null,rating.getReview(),rating.getRate(),reservation,tutor);
//
////        rating.setReview(rating.getReview()); //by user
////        rating.setRate(rating.getRate()); //by user
////        rating.setTutor(tutorRepository.findTutorById(tutorId)); //by default
//
//        ratingRepository.save(rating);
//    }


        //-------------------ADD Rate-----------------------//

    public void addRate(RatingDTO ratingDTO,Integer studentId){
        MyUser student=authRepository.findMyUserById(studentId);
        Reservation reservation=reservationRepository.findReservationById(ratingDTO.getReservation_id());

        if(student==null){
            throw new RuntimeException("Student not found");
        }

        if(reservation==null){
            throw new RuntimeException("Reservation not found");
        }


        if(reservation.getStudent().getId()!=studentId){
            throw new RuntimeException("UnAuthorized");
        }

        Rating rating=new Rating(null,ratingDTO.getReview(), ratingDTO.getRate(), reservation,reservation.getTutor());

        reservation.setRating(rating);
//        reservation.setTutor(reservation.getTutor());

//        reservation.getTutor().setRatings(calculateRate(reservation.getTutor().getId()));
        ratingRepository.save(rating);
    }


    ///------------Calculate Rate-------------------//

    public Integer calculateRate(MyUser myUser,Integer tutorId){
        MyUser myUser1=authRepository.findMyUserById(myUser.getId());
        Tutor tutor=tutorRepository.findTutorById(tutorId);

        if(tutor==null){
            throw new RuntimeException("Tutor not found");
        }

        List<Rating>ratings=ratingRepository.findRatingsByTutor(tutor);
        if (ratings.isEmpty()){
            throw new RuntimeException(" No Rates Found");
        }

        Integer totalRate=0;

        for (Rating rating:ratings){
         totalRate=totalRate+rating.getRate();
        }
        totalRate=(totalRate/ratings.size());
        return totalRate;
    }




}
