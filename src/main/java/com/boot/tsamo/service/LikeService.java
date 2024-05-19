package com.boot.tsamo.service;


import com.boot.tsamo.config.multikey;
import com.boot.tsamo.dto.addLikesDTO;
import com.boot.tsamo.entity.Board;
import com.boot.tsamo.entity.Likes;
import com.boot.tsamo.entity.Users;
import com.boot.tsamo.repository.BoardRepository;
import com.boot.tsamo.repository.LikeRepository;
import com.boot.tsamo.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final ReplyService replyService;


    @Transactional
    public boolean checklikes(addLikesDTO addLikesdto) throws Exception{

        //뷰에서 넘어온 정보를 바탕으로 멀티키 생성
        Users users = userRepository.findById(addLikesdto.getUserId()).get();
        Board board = boardRepository.findById(addLikesdto.getBoardId()).get();
        multikey multikey = new multikey();
        multikey.setBoardId(board);
        multikey.setUserId(users);

        boolean checkLikes = likeRepository.existsById(multikey);


        System.out.println(addLikesdto.getCheck()+"서비스 들어온 체크"); 
        
        if(!addLikesdto.getCheck().equals("check")) {

            //멀티키가 존재한다면 삭제(좋아요 취소)
            if (checkLikes) {
                likeRepository.deleteById(multikey);
            } else {
                //멀티키가 존재하지 않는다면 추가(좋아요)
                Likes likesBuilder = Likes.builder().multikey(multikey).build();

                likeRepository.save(likesBuilder);
            }

        }

        return checkLikes;

    }



}
