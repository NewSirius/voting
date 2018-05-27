package com.newsirius.voting.repository.vote;

import com.newsirius.voting.model.VoteUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Transactional(readOnly = true)
public interface CrudVoteUserEntityRepository extends JpaRepository<VoteUserEntity, Integer> {

    @Override
    @Transactional
    VoteUserEntity save(VoteUserEntity entity);

    @SuppressWarnings("JpaQlInspection")
    @Query("SELECT v FROM VoteUserEntity v WHERE v.user.id=:user_id AND v.localDateTime BETWEEN :startDate AND :endDate")
    VoteUserEntity getBetween(@Param("user_id") int user_id, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

}
