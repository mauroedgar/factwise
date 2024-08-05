package br.com.livecenografia.fact_wise2.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MyUsersDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    @MyUsersEmailUnique
    private String email;

    @NotNull
    @Size(max = 255)
    private String password;

    @NotNull
    private Boolean enabled;

    @Size(max = 255)
    private String name;

    private Long roles;

    private Long apps;

}
