package com.carvalho.solution.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseError {
   private String message;
   private String type;

   public static ResponseError defaultError(String message) {
        return new ResponseError(message, "default");
   }
   
   public static ResponseError bussinessError(String message) {
       return new ResponseError(message, "bussiness");
   }

}
