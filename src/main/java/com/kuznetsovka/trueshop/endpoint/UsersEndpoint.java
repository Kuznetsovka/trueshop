package com.kuznetsovka.trueshop.endpoint;

import com.kuznetsovka.trueshop.dto.UserDto;
import com.kuznetsovka.trueshop.service.user.UserService;
import com.kuznetsovka.trueshop.ws.users.GetUsersRequest;
import com.kuznetsovka.trueshop.ws.users.GetUsersResponse;
import com.kuznetsovka.trueshop.ws.users.UserWS;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.datatype.DatatypeConfigurationException;

@Endpoint
public class UsersEndpoint {

    public static final String NAMESPACE_URL = "http://kuznetsovka.com/trueshop/ws/users";

    private final UserService userService;

    public UsersEndpoint(UserService userService) {
        this.userService = userService;
    }

    @PayloadRoot(namespace = NAMESPACE_URL, localPart = "getUsersRequest")
    @ResponsePayload
    public GetUsersResponse getGreeting(@RequestPayload GetUsersRequest request)
            throws DatatypeConfigurationException {
        GetUsersResponse response = new GetUsersResponse();
        userService.findAll()
                .forEach(dto -> response.getUsers ().add(createUserWS(dto)));
        return response;
    }

    private UserWS createUserWS(UserDto dto){
        UserWS ws = new UserWS();
        ws.setName(dto.getName());
        ws.setEmail(dto.getEmail());
        ws.setRole(dto.getRole());
        return ws;
    }
}
