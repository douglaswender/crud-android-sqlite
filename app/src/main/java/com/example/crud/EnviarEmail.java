package com.example.crud;


import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class EnviarEmail {

    public final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();


    //SENDGRID_API_KEY = "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";
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer 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")
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                    Headers responseHeaders = response.headers();
                    for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                        System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                    }

                    System.out.println(responseBody.string());
                }
            }
        });
    }

    String emailJSON(Pessoa pessoa) {
        return "{\n" +
                "  \"personalizations\": [\n" +
                "    {\n" +
                "      \"to\": [\n" +
                "        {\n" +
                "          \"email\": \"dglswender@gmail.com\"\n" +
                "        }\n" +
                "      ],\n" +
                "      \"subject\": \"Inserção no Banco de Dados!!!\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"from\": {\n" +
                "    \"email\": \"dglswender@gmail.com\"\n" +
                "  },\n" +
                "  \"content\": [\n" +
                "    {\n" +
                "      \"type\": \"text/html\",\n" +
                "      \"value\": \"<strong>Nome: </strong>"+pessoa.getNome()+"<br><strong>Idade: </strong>"+pessoa.getIdade()+"<br><strong>Endereço: </strong>"+pessoa.getEndereco()+"<br>\" "+
                "    }\n" +
                "  ]\n" +
                "}";

    }

    public void enviar(Pessoa pessoa)throws IOException{

        String json = emailJSON(pessoa);

        post("https://api.sendgrid.com/v3/mail/send",json);



//        Email from = new Email("dglswender@gmail.com");
//        String subject = "Sending with SendGrid is Fun";
//        Email to = new Email("dglswender@gmail.com");
//        Content content = new Content("text/plain", "and easy to do anywhere, even with Java");
//        Mail mail = new Mail(from, subject, to, content);
//        SendGrid sg = new SendGrid(System.getenv("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"));
//        Request request = new Request();
//
//        try {
//            request.setMethod(Method.POST);
//            request.setEndpoint("mail/send");
//            request.setBody(mail.build());
//            Response response = sg.api(request);
//            System.out.println(response.getStatusCode());
//            System.out.println(response.getBody());
//            System.out.println(response.getHeaders());
//        } catch (IOException ex) {
//            throw ex;
//        }


    }

}
