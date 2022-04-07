package hellgate.api.controller.account;

import hellgate.common.model.account.IdentityCard;
import hellgate.common.model.account.IdentityCardForm;
import hellgate.common.model.account.IdentityCardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;

@Slf4j
@Service
public class IdentityCardService {

    private final IdentityCardRepository repository;

    public IdentityCardService(IdentityCardRepository repository) {
        this.repository = repository;
    }

    @Nullable
    public IdentityCard convert(IdentityCardForm form) {
        String number = form.getNumber();
        IdentityCard card = repository.findByNumber(number).orElse(new IdentityCard());
        // 此身份证已经绑定了五个账号，不能再绑定
        if (card.getHolders() != null && card.getHolders().size() >= 5) {
            return null;
        }
        card.setNumber(number);
        card.setFullName(form.getFullName());
        repository.save(card);
        return card;
    }
}
