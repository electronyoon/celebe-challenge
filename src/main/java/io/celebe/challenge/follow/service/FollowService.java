package io.celebe.challenge.follow.service;

import io.celebe.challenge.follow.mapper.FollowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowMapper followMapper;

}
