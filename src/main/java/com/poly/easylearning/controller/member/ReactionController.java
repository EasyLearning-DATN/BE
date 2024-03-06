package com.poly.easylearning.controller.member;

import com.poly.easylearning.constant.SystemConstant;
import com.poly.easylearning.entity.User;
import com.poly.easylearning.payload.request.ReactionRQ;
import com.poly.easylearning.service.IReactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(SystemConstant.API_MEMBER + SystemConstant.VERSION_1 + SystemConstant.API_REACTION)
public class ReactionController {
    private final IReactionService reactionService;
    @PostMapping
    public ResponseEntity<?> doReaction(
            @AuthenticationPrincipal User user,
            @RequestBody ReactionRQ reactionRQ
            ){
        return ResponseEntity
                .status(SystemConstant.STATUS_CODE_SUCCESS)
                .body(reactionService.doReaction(user, reactionRQ));
    }
}
