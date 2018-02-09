package com.krayc.repository;

import com.krayc.model.MemberBookEntity;
import com.krayc.model.MemberEntity;
import com.krayc.util.PayType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberBookRepository extends JpaRepository<MemberBookEntity, Integer> {

    public List<MemberBookEntity> findByMemberIsAndTypeIsNot(MemberEntity memberEntity, PayType type);

}
