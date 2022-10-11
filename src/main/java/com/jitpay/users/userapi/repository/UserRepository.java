package com.jitpay.users.userapi.repository;

import com.jitpay.users.userapi.dto.CaptureUserLocationData;
import com.jitpay.users.userapi.dto.CreateUserDataRequestDTO;
import com.jitpay.users.userapi.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @Query("SELECT new com.jitpay.users.userapi.dto.CreateUserDataRequestDTO(u.userId, u.createdOn, u.email, u.firstName, u.lastName, l.latitude, l.longitude) " +
            " from User u join Location l on u.userId = l.user where u.userId= :userId  order by l.createdOn desc")
    List<CreateUserDataRequestDTO> findTopUserDetails(String userId, Pageable pageable);

    @Query("SELECT new com.jitpay.users.userapi.dto.CaptureUserLocationData(u.userId, l.createdOn, l.latitude, l.longitude) " +
            " from User u join Location l on u.userId = l.user where u.userId= :userId and l.createdOn between :fromDate and :toDate")
    List<CaptureUserLocationData> getUserLocationDetailsInDateRange(String userId, Date fromDate, Date toDate);

}
