package com.company.dto;

        import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
        import lombok.Data;
        import lombok.NoArgsConstructor;
        import lombok.RequiredArgsConstructor;

        import java.util.List;
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginResponse {
    private String status;
    private String error;
    private String token;
}