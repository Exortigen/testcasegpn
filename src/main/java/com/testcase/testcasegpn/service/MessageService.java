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

    public Request functionSwitch(Request request, String func) {
        if (request.getIntA() != 0 && request.getIntB() != 0) {
            switch (func) {
                case "add":
                    request.setMethodInt(0);
                    request.setMethod("Add");
                    break;
                case "divide":
                    request.setMethodInt(1);
                    request.setMethod("Divide");
                    break;
                case "multiply":
                    request.setMethodInt(3);
                    request.setMethod("Multiply");
                    break;
                case "subtract":
                    request.setMethodInt(4);
                    request.setMethod("Subtract");
                    break;
                default: break;
            }
            request.hashCode();
            request = checkRequest(request);
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
