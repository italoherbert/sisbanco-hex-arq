package italo.sisbanco.infra.util;

import org.springframework.stereotype.Component;

@Component
public class PasswordEncoder2 {
    
    public String encode(String password) {
        StringBuilder sb = new StringBuilder();
           
        int len = password.length();
        for( int i = 0; i < len; i++ )
            sb.append( Integer.toHexString( password.charAt( i ) ) );        

        return sb.toString();
    }

}
