package com.testcase.testcasegpn.service;

import com.testcase.testcasegpn.entity.Request;
import com.testcase.testcasegpn.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MessageService {
    @Autowired
    private RequestRepository requestRepository;
    SoapCallMethod callMethod = new SoapCallMethod();

    private Request functionSwitch(Request request, String func) {
        if (request.getIntA() != 0 && request.getIntB() != 0) {
            switch (func) {
                case "add":
                    request.setMethodInt(0);
                    request.setMethod("Add");
                    request = checkRequest(request);
                    break;
                case "divide":
                    request.setMethodInt(1);
                    request.setMethod("Divide");
                    request = checkRequest(request);
                    break;
                case "multiply":
                    request.setMethodInt(3);
                    request.setMethod("Multiply");
                    request = checkRequest(request);
                    break;
                case "subtract":
                    request.setMethodInt(4);
                    request.setMethod("Subtract");
                    request = checkRequest(request);
                    break;
            }

        }
    return request;
    }
    private Request checkRequest(Request request){
        Optional<Request> requestOptional = requestRepository.findByPersonalhash(request.getPersonalhash());
        if (requestOptional.isPresent()) {
            request = requestOptional.get();
        } else {
            request.setResult(callMethod.callWeb(request));
            requestRepository.save(request);
        }
        return request;
    }
}
