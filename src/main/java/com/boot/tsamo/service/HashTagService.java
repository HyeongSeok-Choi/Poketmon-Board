package com.boot.tsamo.service;

import com.boot.tsamo.entity.Board;
import com.boot.tsamo.entity.HashTag;
import com.boot.tsamo.repository.HashTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HashTagService {

    private final HashTagRepository hashTagRepository;

    public void saveHashTags(String hashTagValue, Board board) {
        System.out.println(hashTagValue);

        String arr[] = hashTagValue.split(",");

        for (int i = 0; i < arr.length; i++) {
            HashTag hashTag = new HashTag();
            String hash = arr[i].substring(1);
            System.out.println(hash+"이거보드레이"+i);

            hashTag.setHashTag_content(hash);
            hashTag.setBoardId(board);

            hashTagRepository.save(hashTag);


        }
    }
}
