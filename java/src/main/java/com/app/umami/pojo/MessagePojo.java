package com.app.umami.pojo;

import com.app.umami.entity.Meal;
import com.app.umami.entity.User;
import lombok.Data;

@Data
public class MessagePojo {

    private Meal meal;

    private User user;

}
