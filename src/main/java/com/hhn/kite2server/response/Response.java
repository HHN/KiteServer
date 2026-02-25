package com.hhn.kite2server.response;

import com.hhn.kite2server.data.DataObject;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {
    private int resultCode;
    private String resultText;
    private String completion;
    private List<DataObject> dataObjects;
    private int version;
    private int userRole;

}
