package io.celebe.challenge.user.mapper;

import io.celebe.challenge.model.dto.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User selectUserByPublicId(String publicId);

}