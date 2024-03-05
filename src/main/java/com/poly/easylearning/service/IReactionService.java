package com.poly.easylearning.service;

import com.poly.easylearning.entity.User;
import com.poly.easylearning.payload.request.ReactionRQ;
import com.poly.easylearning.payload.response.RestResponse;

public interface IReactionService {
    RestResponse doReaction(User user, ReactionRQ reactionRQ);
}
