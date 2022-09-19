package pro.sky.skyprospinghomework23.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import pro.sky.skyprospinghomework23.exception.IncorrectNameException;
import pro.sky.skyprospinghomework23.exception.IncorrectSurnameException;

import static org.apache.commons.lang3.StringUtils.isAlpha;

@Service
public class ValidatorService {

    public String validateName(String name) {
        if (!StringUtils.isAlpha(name)) {
            throw new IncorrectNameException();
        }
            return StringUtils.capitalize(name.toLowerCase());
        }
  public String validateSurname(String surname) {
        if (!StringUtils.isAlpha(surname)) {
            throw new IncorrectSurnameException();
        }
        return StringUtils.capitalize(surname.toLowerCase());
    }


}


