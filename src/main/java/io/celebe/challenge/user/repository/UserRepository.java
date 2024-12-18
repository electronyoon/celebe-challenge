package io.celebe.challenge.user.repository;

import io.celebe.challenge.user.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRepository {
    User findByPublicId(String publicId);

    Long findIdByPublicId(String publicId);
}