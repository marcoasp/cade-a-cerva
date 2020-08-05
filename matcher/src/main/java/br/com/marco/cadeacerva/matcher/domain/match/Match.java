package br.com.marco.cadeacerva.matcher.domain.match;

import br.com.marco.cadeacerva.matcher.domain.sale.Sale;
import br.com.marco.cadeacerva.matcher.domain.user.User;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document
@RequiredArgsConstructor
public class Match {

    @Id
    private String id;
    private final String userEmail;
    private final ObjectId saleId;
    private final ObjectId userId;

    public Match(final String email, final String saleId, final String userId) {
        this.userEmail = email;
        this.saleId = new ObjectId(saleId);
        this.userId = new ObjectId(userId);
    }
}
