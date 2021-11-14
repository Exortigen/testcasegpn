package com.testcase.testcasegpn.service;

import com.testcase.testcasegpn.entity.Request;
import com.testcase.testcasegpn.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service

public class MessageService {

    SoapCallMethod callMethod = new SoapCallMethod();


    public Request functionSwitch(Request request, String func, RequestRepository requestRepository) {
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
            System.out.println(request.hashCode());
            request = checkRequest(request, requestRepository);
            System.out.println("Passed");
        }
    return request;
    }

    private Request checkRequest(Request request, RequestRepository requestRepository){
        System.out.println("Зашел в чек реквест");
        System.out.println(" A " + request.getIntA() + " B " + request.getIntB());
        System.out.println("Personalhash: " + request.getPersonalhash());
        Optional<Request> requestOptional = requestRepository.findByPersonalhash(request.getPersonalhash());
        if (requestOptional.isPresent()) {
            request = requestOptional.get();
            System.out.println("From DB");
        } else {
            request.setResult(callMethod.callWeb(request));
            requestRepository.save(request);
            System.out.println("CallWeb started");
        }
        return request;
    }
}
