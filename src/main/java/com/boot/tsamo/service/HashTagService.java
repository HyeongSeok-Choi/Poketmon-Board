package com.boot.tsamo.service;

import com.boot.tsamo.entity.Board;
import com.boot.tsamo.entity.HashTag;
import com.boot.tsamo.repository.HashTagRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HashTagService {

    private final HashTagRepository hashTagRepository;


    public void saveHashTags(String hashTagValue, Board board) {
        System.out.println(hashTagValue);

        String arr[] = hashTagValue.split(",");

        for (int i = 0; i < arr.length; i++) {
            HashTag hashTag = new HashTag();
            if (arr[i].length() > 0) {
                String hash = arr[i].substring(1);
                System.out.println(hash + "이거보드레이" + i);

                hashTag.setHashTagContent(hash);
                hashTag.setBoardId(board);

                hashTagRepository.save(hashTag);

            }
        }
    }

    public List<HashTag> getHashTagsByHashTagValue(String hashTagValue) {

        String arr[] = hashTagValue.split(",");
        List<HashTag> hashTagList =new ArrayList<>();

        for (int i = 0; i < arr.length; i++) {
            HashTag hashTag = new HashTag();
            if (arr[i].length() > 0) {
                String hash = arr[i].substring(1);
                hashTag.setHashTagContent(hash);
                hashTagList.add(hashTag);
            }
        }
        return hashTagList;
    }

    public List<HashTag> getHashTags(Board board) {

        List<HashTag> hashTags =  board.getHashTags();

        return hashTags;
    }

    @Transactional
    public void deleteHashTags(Board board) {

        hashTagRepository.deleteByBoardId(board);

    }
}
