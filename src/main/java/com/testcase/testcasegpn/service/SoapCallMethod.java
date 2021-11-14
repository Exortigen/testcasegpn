package com.testcase.testcasegpn.service;

import com.testcase.testcasegpn.entity.Request;
import org.springframework.stereotype.Service;

import javax.xml.soap.*;
import java.util.concurrent.TimeUnit;

@Service
public class SoapCallMethod {
    public Integer callWeb(Request request){
        String soapEndpointUrl = "http://www.dneonline.com/calculator.asmx";
        String soapActionTemplate = "http://tempuri.org/";
        Integer result;

        //Строим URL
        String soapAction = soapActionTemplate + request.getMethod();

        //Вызываем
        return (result = callSoapWebService(soapEndpointUrl, soapAction, request, request.hashCode()));

    }


    private void createSoapEnvelope(SOAPMessage soapMessage, Request request) throws SOAPException {
      SOAPPart soapPart = soapMessage.getSOAPPart();

        String myNamespace = "tem";
        String myNamespaceURI = "http://tempuri.org/";

        //Назначаем пространство имен
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration(myNamespace, myNamespaceURI);

        //Создаем тело запроса
        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem = soapBody.addChildElement(request.getMethod(), myNamespace);

        SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("intA", myNamespace);
        soapBodyElem1.addTextNode(String.valueOf(request.getIntA()));

        SOAPElement soapBodyElem2 = soapBodyElem.addChildElement("intB", myNamespace);
        soapBodyElem2.addTextNode(String.valueOf(request.getIntB()));
    }
    public Integer callSoapWebService(String soapEndpointUrl, String soapAction, Request request){
        Integer methodResponse = 0;
        try {
            //Открыть соед.
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();

            //Отправить SOAP message на SOAP сервер
            SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(soapAction, request), soapEndpointUrl);

            //Достаем body из ответа
            SOAPBody result = soapResponse.getSOAPBody();

            //Получаем ответ от сервера и оборачиваем в Integer
            methodResponse = Integer.valueOf(result.getElementsByTagName(request.getMethod() + "Result").item(0).getTextContent());

            soapConnection.close();
            return methodResponse;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return methodResponse;
    }
    private SOAPMessage createSOAPRequest(String soapAction, Request request) throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();

        createSoapEnvelope(soapMessage, request);

        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", soapAction);

        soapMessage.saveChanges();

        return soapMessage;
    }


}

