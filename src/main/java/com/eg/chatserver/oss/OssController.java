package com.eg.chatserver.oss;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("oss")
public class OssController {
    public String GetPostBody(InputStream is, int contentLen) {
        if (contentLen > 0) {
            int readLen = 0;
            int readLengthThisTime = 0;
            byte[] message = new byte[contentLen];
            try {
                while (readLen != contentLen) {
                    readLengthThisTime = is.read(message, readLen, contentLen - readLen);
                    if (readLengthThisTime == -1) {// Should not happen.
                        break;
                    }
                    readLen += readLengthThisTime;
                }
                return new String(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    @RequestMapping("aliyunCallback")
    public String callback(HttpServletRequest request) throws IOException {
        String s = GetPostBody(request.getInputStream(),
                Integer.parseInt(request.getHeader("content-length")));
        System.out.println(s);
        return "";
    }


}
