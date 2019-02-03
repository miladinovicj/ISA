package com.example.ISA_AMA_projekat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.example.ISA_AMA_projekat.model.FriendRequest;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Integer>
{
	
	@Modifying
	@Transactional
	@Query("select friend_request from FriendRequest friend_request where friend_request.stanje=0 and (friend_request.prima.id = ?1 or friend_request.salje.id = ?1)")
	List<FriendRequest> findBySaljeOrPrima(Integer id);
}
