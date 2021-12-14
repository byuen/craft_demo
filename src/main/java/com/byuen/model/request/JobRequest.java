package com.byuen.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobRequest {

    String firstName;
    String lastName;
    String email;
    String phone;
    String description;
    String requirement;


}
