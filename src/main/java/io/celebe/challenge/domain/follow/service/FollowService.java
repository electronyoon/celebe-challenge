package io.celebe.challenge.domain.follow.service;

import io.celebe.challenge.domain.follow.mapper.FollowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowMapper followMapper;

}
